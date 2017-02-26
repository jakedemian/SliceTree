package com.slicetree.db.helpers;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.naming.NamingException;

import org.apache.commons.lang3.StringUtils;

import com.slicetree.common.logging.LogLevel;
import com.slicetree.common.logging.LoggingHelper;

public class UserCredentialsHelper extends DatabaseHelper {
	private final String CLASSNAME = getClass().getCanonicalName();
	private final LoggingHelper logger = new LoggingHelper();

	// Each token produced by this class uses this identifier as a prefix.
	public static final String ID = "$31$";

	// The minimum recommended cost, used by default
	public static final int DEFAULT_COST = 16;

	// TODO investigate SHA256, or other options
	private static final String ALGORITHM = "PBKDF2WithHmacSHA1";
	private static final int SIZE = 128;
	private static final Pattern layout = Pattern.compile("\\$31\\$(\\d\\d?)\\$(.{43})");
	private final SecureRandom random;
	private final int cost;

	private String verifiedUserEmail = null;

	public UserCredentialsHelper() throws NamingException, SQLException {
		this(DEFAULT_COST);
	}

	/**
	 * Create a password manager with a specified cost
	 * 
	 * @param cost
	 *            the exponential computational cost of hashing a password, 0 to
	 *            30
	 */
	public UserCredentialsHelper(int cost) throws NamingException, SQLException {
		iterations(cost); /* Validate cost */
		this.cost = cost;
		this.random = new SecureRandom();
	}

	/**
	 * Add a new set of user credentials to the database.
	 * 
	 * @param email
	 *            The email to add to the row.
	 * @param password
	 *            The password to hash and add to the row.
	 * @param userId
	 *            The user id to cross reference with the users table.
	 * @throws SQLException
	 * @throws NamingException
	 */
	public void createUserCredentials(String email, String password)
			throws SQLException, NamingException {
		String hash = hashPassword(password.toCharArray());
		String sql = "INSERT INTO usercredentials (email,hash) VALUES (?,?);";
		Object[] params = { email, hash };

		this.execute(sql, params);
	}

	/**
	 * Update the user's password.
	 * 
	 * @param email
	 *            The email to identify the row to update.
	 * @param password
	 *            The new password.
	 * @throws SQLException
	 * @throws NamingException
	 */
	public void updateUserPassword(String email, String password)
			throws SQLException, NamingException {
		String hash = hashPassword(password.toCharArray());
		String sql = "UPDATE usercredentials SET hash=? where email=?;";
		String[] params = { hash, email };

		this.execute(sql, params);
	}

	/**
	 * Delete the user's credentials from the database.
	 * 
	 * @param email
	 *            The email identifying the user credentials row to remove.
	 * @throws SQLException
	 * @throws NamingException
	 */
	public void deleteUserCredentials(String email) throws SQLException, NamingException {
		String sql = "DELETE FROM usercredentials where email=?;";
		String[] params = { email };

		this.execute(sql, params);
	}

	/**
	 * Verify that a user's email/password match the email/hash from the
	 * database.
	 * 
	 * @param email
	 *            The email entered by the user
	 * @param password
	 *            The password entered by the user
	 * @return true if the user was successfully verified, false if there was
	 *         some mismatch
	 * 
	 * @throws SQLException
	 * @throws NamingException
	 */
	public boolean verifyUserCredentials(String email, String password)
			throws SQLException, NamingException {
		final String METHODNAME = "verifyUserCredentials";
		logger.entering(CLASSNAME, METHODNAME);
		boolean res = false;

		String sql = "SELECT * FROM usercredentials WHERE email=?";
		String[] params = { email };
		List userCredentials = this.query(sql, params);

		if (userCredentials != null && userCredentials.size() == 1) {
			Map userCredsRow = (Map) userCredentials.get(0);
			String storedUserHash = (String) userCredsRow.get("hash");
			String storedUserEmail = (String) userCredsRow.get("email");
			if (StringUtils.isNotBlank(storedUserHash)
					&& StringUtils.isNoneBlank(storedUserEmail)) {
				res = authenticatePassword(password.toCharArray(), storedUserHash);
				if (res) {
					verifiedUserEmail = storedUserEmail;
				}
			} else {
				logger.log(LogLevel.WARN, CLASSNAME, METHODNAME,
						"Problem occurred with " + "retreiving stored user hash from database "
								+ "for user with email " + email + ".  This might be the way "
								+ "the data is formatted after it has been retreived.");
			}
		} else {
			// TODO future logic here for locking a user out after too many
			// failed attempts
			logger.log(LogLevel.ALL, CLASSNAME, METHODNAME,
					"A user typed in their username/password incorrectly.");
		}

		// TODO Should I execute some sort of thread "wait" here to slow a user
		// down when verifying credentials? doing a 1 or 2 second wait here
		// would drastically improve brute force security

		logger.exiting(CLASSNAME, METHODNAME);
		return res;
	}

	private static int iterations(int cost) {
		if ((cost & ~0x1F) != 0)
			throw new IllegalArgumentException("cost: " + cost);
		return 1 << cost;
	}

	/**
	 * Generates a password hash from a provided password.
	 * 
	 * @param password
	 *            The password to hash.
	 * @return The hashed password.
	 */
	private String hashPassword(char[] password) {
		byte[] salt = new byte[SIZE / 8];
		random.nextBytes(salt);
		byte[] dk = pbkdf2(password, salt, 1 << cost);
		byte[] hash = new byte[salt.length + dk.length];
		System.arraycopy(salt, 0, hash, 0, salt.length);
		System.arraycopy(dk, 0, hash, salt.length, dk.length);
		Base64.Encoder enc = Base64.getUrlEncoder().withoutPadding();
		return ID + cost + '$' + enc.encodeToString(hash);
	}

	/**
	 * Authenticate with a password and a stored password token.
	 * 
	 * @return true if the password and token match
	 */
	private boolean authenticatePassword(char[] password, String token) {
		Matcher m = layout.matcher(token);
		if (!m.matches())
			throw new IllegalArgumentException("Invalid token format");
		int iterations = iterations(Integer.parseInt(m.group(1)));
		byte[] hash = Base64.getUrlDecoder().decode(m.group(2));
		byte[] salt = Arrays.copyOfRange(hash, 0, SIZE / 8);
		byte[] check = pbkdf2(password, salt, iterations);
		int zero = 0;
		for (int idx = 0; idx < check.length; ++idx)
			zero |= hash[salt.length + idx] ^ check[idx];
		return zero == 0;
	}

	private static byte[] pbkdf2(char[] password, byte[] salt, int iterations) {
		KeySpec spec = new PBEKeySpec(password, salt, iterations, SIZE);
		try {
			SecretKeyFactory f = SecretKeyFactory.getInstance(ALGORITHM);
			return f.generateSecret(spec).getEncoded();
		} catch (NoSuchAlgorithmException ex) {
			throw new IllegalStateException("Missing algorithm: " + ALGORITHM, ex);
		} catch (InvalidKeySpecException ex) {
			throw new IllegalStateException("Invalid SecretKeyFactory", ex);
		}
	}

	public String getVerifiedUserEmail() {
		return this.verifiedUserEmail;
	}

}

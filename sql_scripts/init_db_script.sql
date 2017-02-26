-- create orgs table
drop table orgs;
create table orgs(
  org_id            int(10) not null primary key AUTO_INCREMENT,
  org_name          varchar(256) not null,
  task_states       varchar(32) not null default '1|2|3|4|5|6',
  org_creation      varchar(256) not null
);
insert into orgs (org_name, task_states, org_creation) values ('jakedemian_org', '1|2|3|4|7|5|6', 'somedatetimestr');

-- create table users
drop table users;
create table users(
  user_id           int(10) not null PRIMARY KEY AUTO_INCREMENT,
  org_id            int(10) not null,
  email	            varchar(32) not null unique,
  firstname			varchar(32) not null,
  lastname			varchar(32) not null,
  user_role         varchar(1) not null,
  user_creation     varchar(256) not null
);
insert into users (org_id, email, firstname, lastname, user_role, user_creation) values (1, 'jakedemian@gmail.com', 'Jake', 'Demian', 'A', 'somedatetimestr');

-- create tasks table
drop table tasks;
create table tasks(
  task_id           int(10) not null PRIMARY KEY AUTO_INCREMENT,
  org_id            int(10) not null,
  display_id        varchar(16) not null,
  task_title        varchar(256) not null,
  task_type         varchar(1) not null,
  task_desc         varchar(10000) not null,
  parent_id         int(10),
  user_id           int(10),
  task_state_id     int(10) not null,
  task_creation     varchar(256) not null
);
insert into tasks (org_id, display_id, task_title, task_type, task_desc, task_state_id, user_id, task_creation) values (1, 'S0001', 'Add New Ajax Call', 'S', 'desc', 1, 1, 'somedatetimestr');
insert into tasks (org_id, display_id, task_title, task_type, task_desc, task_state_id, user_id, task_creation) values (1, 'S0002', 'Fix CSS on main page', 'S', 'desc', 1, 1, 'somedatetimestr');
insert into tasks (org_id, display_id, task_title, task_type, task_desc, parent_id, task_state_id, user_id, task_creation) values (1, 'D0001', 'Ajax Call Throws Error', 'D', 'desc', 1, 1, 1, 'somedatetimestr');

-- create taskstate table
drop table taskstate;
create table taskstate(
  taskstate_id      int(10) not null PRIMARY KEY AUTO_INCREMENT,
  org_id            int(10), -- null means it's a globally available task state
  display_name      varchar(32) not null,
  shorthand         varchar(3) not null,
  state_creation    varchar(256)
);
insert into taskstate (display_name, shorthand, state_creation) values ('Identified', 'I', 'somedatetimestr');
insert into taskstate (display_name, shorthand, state_creation) values ('In Progress', 'P', 'somedatetimestr');
insert into taskstate (display_name, shorthand, state_creation) values ('Code Review', 'C', 'somedatetimestr');
insert into taskstate (display_name, shorthand, state_creation) values ('Accepted', 'A', 'somedatetimestr');
insert into taskstate (display_name, shorthand, state_creation) values ('Validated', 'V', 'somedatetimestr');
insert into taskstate (display_name, shorthand, state_creation) values ('Released', 'R', 'somedatetimestr');
insert into taskstate (org_id, display_name, shorthand, state_creation) values (1, 'DUT', 'DUT', 'somedatetimestr'); -- this means org 1 created their own custom state named 'DUT'

drop table config;
create table config(
  config_id         int(10) not null PRIMARY KEY AUTO_INCREMENT,
  config_key        varchar(256) not null,
  config_val        varchar(1024) not null
);
insert into config (config_key, config_val) values ('LOG_LEVEL', '0');

drop table usercredentials;
create table usercredentials(
  user_credentials_id int(10) not null PRIMARY KEY AUTO_INCREMENT,
  email varchar(256) not null unique,
  hash varchar(256) not null
);
insert into usercredentials (email, hash) values ('jakedemian@gmail.com', '$31$16$-xwqQsVClNO-KjxcVbkUem5VB_iicj1rmvu5l4c_UNE');

-- verify tables created
show tables;

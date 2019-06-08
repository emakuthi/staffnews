SET MODE PostgreSQL;

CREATE TABLE IF NOT EXISTS departments (
 id int PRIMARY KEY auto_increment,
 name VARCHAR,
 description VARCHAR,
);

CREATE TABLE IF NOT EXISTS users (
 id int PRIMARY KEY auto_increment,
 name VARCHAR
);

CREATE TABLE IF NOT EXISTS articles (
 id int PRIMARY KEY auto_increment,
 content VARCHAR,
 departmentid INTEGER
);

CREATE TABLE IF NOT EXISTS departments_users (
 id int PRIMARY KEY auto_increment,
 usersid INTEGER,
 departmentid INTEGER
);
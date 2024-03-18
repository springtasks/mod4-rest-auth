CREATE TABLE USER (
  id INTEGER NOT NULL auto_increment,
  emailAddress VARCHAR(100) NOT NULL,
  password VARCHAR(100) NOT NULL,
  isLocked BOOLEAN DEFAULT FALSE,
  failureCounter INTEGER DEFAULT 0,
  lockDate DATE,
  PRIMARY KEY (ID)
);

CREATE TABLE AUTHORITY (
id INTEGER NOT NULL auto_increment,
  emailAddress VARCHAR(100) NOT NULL,
  role VARCHAR(50) NOT NULL,
  FOREIGN KEY (emailAddress) REFERENCES USER(emailAddress)
);

CREATE TABLE Secret (id INTEGER NOT NULL auto_increment, url varchar(255), token_v varchar(255), primary key (id));

CREATE UNIQUE INDEX USER_EMAIL
  on AUTHORITY (emailAddress,role);

  -- User user/pass
  INSERT INTO USER
    values (1, 'user1@ROOT.COM',
      '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a', FALSE, 0, NULL);

    INSERT INTO USER
      values ('user2@ROOT.COM',
        '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a', FALSE, 0, NULL);


  INSERT INTO AUTHORITY
    values (1, 'user1@ROOT.COM', 'ROLE_USER');
  INSERT INTO AUTHORITY
    values (2, 'user2@ROOT.COM', 'ROLE_ADMIN');
  INSERT INTO AUTHORITY
    values (3, 'user3@ROOT.COM', 'ROLE_USER');
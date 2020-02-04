# @author: Peter He

DROP TABLE IF EXISTS articles_and_comments;
DROP TABLE IF EXISTS user_info;
DROP TABLE IF EXISTS user_authentication;

CREATE TABLE IF NOT EXISTS user_authentication
(
  userId         INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  userName       VARCHAR(20)     NOT NULL UNIQUE,
  hashedPassword CHAR(88)        NOT NULL,
  salt           VARCHAR(44)     NOT NULL,
  hashNum        INT             NOT NULL
);

CREATE TABLE IF NOT EXISTS user_info
(
  userId      INT PRIMARY KEY NOT NULL,
  blogName    VARCHAR(100),
  firstName   VARCHAR(50)     NOT NULL,
  lastName    VARCHAR(50)     NOT NULL,
  dateOfBirth DATE            NOT NULL,
  avatarURL   TEXT,
  profile     TEXT,
  FOREIGN KEY (userId) REFERENCES user_authentication (userId) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS articles_and_comments
(
  id             INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  datePosted     TIMESTAMP,
  title          VARCHAR(100),
  content        TEXT,
  parentId       INT,
  userBelongedId INT NOT NULL,
  FOREIGN KEY (parentId) REFERENCES articles_and_comments (id) ON DELETE CASCADE,
  FOREIGN KEY (userBelongedId) REFERENCES user_authentication (userId) ON DELETE CASCADE
);

ALTER TABLE user_authentication ADD thirdPartyId INT;

ALTER TABLE articles_and_comments ADD timeEdited TIMESTAMP;
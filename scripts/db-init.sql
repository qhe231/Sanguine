# @author: Peter He

DROP TABLE IF EXISTS articles_and_comments;
DROP TABLE IF EXISTS user_authentication;

CREATE TABLE IF NOT EXISTS user_authentication
(
  userId         INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  username       VARCHAR(20)     NOT NULL,
  blogName       VARCHAR(100),
  avatarURL      TEXT,
  hashedPassword CHAR(32)        NOT NULL,
  salt           VARCHAR(32)     NOT NULL,
  hashNum        INT             NOT NULL
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
  FOREIGN KEY (userBelongedId) REFERENCES user_authentication (userId)
);


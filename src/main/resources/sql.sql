CREATE TABLE hiddennode
(
	id INT PRIMARY KEY AUTO_INCREMENT,
	create_key VARCHAR(128)
);

CREATE TABLE wordhidden(
	id INT PRIMARY KEY AUTO_INCREMENT,
	fromid INT,
	toid INT,
	strength FLOAT
);
CREATE TABLE hiddenurl(
	id INT PRIMARY KEY AUTO_INCREMENT,
	fromid INT,
	toid INT,
	strength FLOAT
);
use work;
CREATE TABLE blog_author(
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  NAME VARCHAR(56),
  url VARCHAR(128) NOT NULL UNIQUE KEY,
  description VARCHAR(256),
  gmt_create DATETIME,
  gmt_modified DATETIME
);

CREATE TABLE blog_blogger(
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	author_id BIGINT NOT NULL,
	title VARCHAR(128),
	url VARCHAR(126),
	description VARCHAR(256),
	category VARCHAR(128),
	gmt_create DATETIME,
	gmt_modified DATETIME
);
ALTER TABLE blog_bloger ADD category VARCHAR(128);
ALTER TABLE blog_bloger RENAME blog_blogger;
CREATE TABLE blog_bloger_dict(
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	bloger_id BIGINT NOT NULL,
	dict_ID BIGINT NOT NULL,
	COUNT INT ,
	gmt_create DATETIME,
	gmt_modified DATETIME
);

CREATE TABLE blog_dict(
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	word VARCHAR(28) NOT NULL UNIQUE KEY,
	gmt_create DATETIME,
	gmt_modified DATETIME
);
DESC aa;
SELECT LENGTH(old_value) FROM aa;
SELECT * FROM  aa;
INSERT INTO aa(old_value) VALUES('你好我是中国人');

SELECT * FROM blog_dict;
INSERT INTO blog_dict(word, gmt_create,gmt_modified) VALUES('中国',NOW(),NOW()),('中国人',NOW(),NOW()) ON DUPLICATE KEY UPDATE gmt_modified = NOW() ;


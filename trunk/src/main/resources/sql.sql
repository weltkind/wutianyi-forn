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
USE WORK;
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
INSERT INTO blog_dict(word, gmt_create,gmt_modified) VALUES('中国',NOW(),NOW()),('中国人',NOW(),NOW()) ON DUPLICATE KEY UPDATE gmt_modified=NOW() ;

ALTER TABLE blog_bloger_dict RENAME blog_blogger_dict;

SELECT * FROM blog_author;
SELECT * FROM blog_blogger;
SELECT * FROM blog_dict;
SELECT * FROM blog_blogger_dict;

SELECT a.id author_id ,NAME, a.url author_url,a.description author_description,b.id blogger_id,b.author_id,b.title,b.category,b.url blogger_url,b.description blogger_description FROM blog_author a LEFT JOIN blog_blogger b ON a.id=b.author_id  WHERE a.id=2689;
SELECT * FROM blog_blogger WHERE author_id=2669;
DESC blog_author;
CREATE INDEX blog_author_name ON blog_author(NAME);

DESC blog_blogger;
CREATE INDEX blog_blogger_author_id ON blog_blogger(author_id);
SELECT COUNT(*) FROM blog_author;
SELECT COUNT(*) FROM blog_blogger;
SELECT COUNT(*) FROM blog_dict;
SELECT COUNT(*) FROM blog_blogger_dict;

DESC blog_blogger;

SHOW TABLE STATUS LIKE 'blog_author';

SHOW CREATE TABLE blog_dict;
CREATE TABLE `blog_dict` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `word` VARCHAR(28) NOT NULL,
  `gmt_create` DATETIME DEFAULT NULL,
  `gmt_modified` DATETIME DEFAULT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `word` (`word`)
) ENGINE=INNODB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
SET CHARSET utf8;
SELECT * FROM blog_dict;

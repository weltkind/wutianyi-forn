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
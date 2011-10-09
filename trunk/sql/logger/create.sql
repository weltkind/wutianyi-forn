create table logger(
	id bigint primary key auto_increment,
	gmt_create datetime,
	gmt_modified datetime,
	old_value varchar(3998),
	new_value varchar(3998),
	operation_type varchar(128),
	operator varchar(64)
);



create index gmt_create_index on logger(gmt_create);
alter table logger modify;

create table a(
	aId int(1) auto_increment primary key,
	aNum char(20)
);
create table b(
	bId int(1) auto_increment primary key,
	bName char(20)
)
INSERT INTO a 
VALUES ( 1, 'a20050111' ) , ( 2, 'a20050112' ) , ( 3, 'a20050113' ) , ( 4, 'a20050114' ) , ( 5, 'a20050115' ) ; 

INSERT INTO b 
VALUES ( 1, ' 2006032401' ) , ( 2, '2006032402' ) , ( 3, '2006032403' ) , ( 4, '2006032404' ) , ( 8, '2006032408' ) ;
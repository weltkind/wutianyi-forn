create table logger(
	id bigint primary key,
	gmt_create datetime,
	gmt_modified datetime,
	old_value varchar(3998),
	new_value varchar(3998),
	operation_type varchar(128),
	operator varchar(64)
);
create index gmt_create_index on logger(gmt_create);
alter table logger modify
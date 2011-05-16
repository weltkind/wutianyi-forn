create table tl_commodity (
	id int,
	name varchar(256),
	cn_name varchar(512),
	description varchar(512),
	group_id int
)

create table tl_commodity_attr_value_rel(
	id int,
	commodity_id int,
	attribute_id int,
	value_ids varchar(4000),
	value_ids_1 varchar(4000),
	value_ids_2 varchar(4000),
	valud_ids_3 varchar(4000)
)

create table tl_attr_dict(
	id int,
	cn_name varchar(512),
	description varchar(512),
	name varchar(256)
)

create table tl_value_dict (
	id int,
	cn_name varchar(512),
	description varchar(512),
	name varchar(256)
)


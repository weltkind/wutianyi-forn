create table records (
	zone varchar(256),
	ttl int,
	type varchar(64),
	host varchar(128),
	mx_priority int,
	data varchar(128),
	primary_ns varchar(128),
	resp_contact varchar(128),
	serial int,
	refresh int,
	retry int
)
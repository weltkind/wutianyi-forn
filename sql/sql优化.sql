
SHOW STATUS LIKE 'com_%';--显示数据中select，insert语句执行的情况
SHOW STATUS LIKE 'innodb_row_%';--针对innodb类型的数据库
SHOW STATUS LIKE 'con_%';--显示数据连接的请求
SHOW STATUS LIKE 'up_%';--显示数据库启动的时间
SHOW STATUS LIKE 'slow_%';--显示slow的sql
show status like '%table%';
show status like 'innodb_row_lock%';
show create table a;
create table myisam_char(name char(10)) engine=myisam;

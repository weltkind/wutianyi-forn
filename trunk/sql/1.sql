select * from code_activity;
select * from gamecenter_privilege_game;
select * from noble;
select 1 from noble where username = 'wutianyi';
select * from gamecenter_privilege_game;
select * from gamecenter_subscribe_prvg_msg limit 10;
select * from gamecenter_privilege_act_code limit 10;
select * from gamecenter_acquired_privilege limit 10;
select * from gamecenter_privilege_act_code limit 10;

alter table noble add gmt_create datetime;
alter table noble add gmt_modified datetime;
alter table noble add noble_fail char(1);
desc noble;
insert into noble(username, gmt_create, gmt_modified, noble_fail) values('wutest', sysdate(), sysdate(), 'Y');
select curtime from noble;
select current_timestamp from noble;
select current_date from noble;
select sysdate() from noble;
select * from noble;
select now() from noble;
insert into noble(username) values('username');
insert into noble(username) values('dw_wuhanjie');
select * from noble where username='dw_wuhanjie';
select * from gamecenter_privilege where game_id=170;
select * from gamecenter_privilege_act_code limit 10;

select * from gamecenter_acquired_privilege limit 10;
select * from gamecenter_privilege limit 10;
select * from gamecenter_privilege_game limit 10;
select * from gamecenter_privilege_act_code limit 10;
select * from gamecenter_recommend limit 10;
select * from gamecenter_recommend_game limit 10;
select * from code_activity limit 10;
select * from gamecenter_recommend_hist limit 10;
select * from gamecenter_subscribe_prvg_msg limit 10;


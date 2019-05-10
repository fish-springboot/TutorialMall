drop database if exists tutorials_mall;
create database if not exists tutorials_mall;
use tutorials_mall;
show tables ;
select * from tutorial;
select * from order_info;
select * from user;

delete from order_info where id = 1;

insert into order_info(owner, price, repository_name) values ('fish56', 30, 'aa');
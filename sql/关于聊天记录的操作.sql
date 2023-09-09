create table Allmessage(
id int primary key auto_increment COMMENT '主键',
name VARCHAR(10) comment '用户名字',
message varchar(50) comment '内容',
create_time DATE comment '创建时间'
)comment '群聊表';

create table primessage(
id int primary key auto_increment comment '主键',
fromid varchar(8) comment '发消息人userid',
toid varchar(8) comment '接收人userid',
message varchar(50) comment '内容',
create_time date comment '创建时间'
)comment '私聊表';

insert into primessage(fromid,toid,message) values ('asdfasdf','zxcvzxcv','呵呵');
insert into friend(userid,friend_id) values ('asdfasdf','qwerqwer');
insert into primessage(fromid,toid,message) values ('asdfasdf','qwerqwer','呵呵');

delete from friend where userid='asdfasdf' and friend_id='qwerqwer';

#每天0点清除群聊消息
CREATE EVENT delete_allmessgae 
ON SCHEDULE EVERY 1 DAY STARTS '2023-9-9 00:00:00' 
ON COMPLETION PRESERVE 
DO TRUNCATE table allmessage

#建立索引
create index getmessage on primessage(fromid,toid);
create index getfriend on friend(userid,friend_id);
#创建外键约束，删除好友时删除聊天记录
alter table primessage add CONSTRAINT deletemessage foreign key (fromid,toid) references friend(userid,friend_id) on delete cascade;



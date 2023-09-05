#创建表
create table user(
 id int PRIMARY KEY auto_increment comment '主键',
 userid char(8) not null unique comment '用户唯一id',
 name varchar(10) not null UNIQUE COMMENT '用户展示名字',
 username varchar(20) not null unique comment '用户账号',
 password varchar(20) not null  comment '用户密码',
 avatar varchar(50) not null comment '用户头像',
 create_time date comment '创建日期',
 salt char(4) commit '盐值'
)COMMENT '用户表';

create table user_detail(
 id int PRIMARY KEY auto_increment comment '主键',
 userid char(8) not null unique comment '用户唯一id',
 age TINYINT check(age>0 && age<120) comment '用户年龄',
 gender char(1) comment '性别',
 email VARCHAR(20) comment '邮箱',
 phone int comment '电话号码'
)COMMENT '用户详情表';

#建立索引
create index user_login on user(username,password);

#建立外键关系
alter table user_detail add CONSTRAINT user_userdetail foreign key (userid) REFERENCES user(userid) on delete cascade;
#查看建表语句看看是否创建成功
show create table user_detail;
#创建触发器
create trigger adduserdetail 
after insert on user 
for each row
begin
		 insert into user_detail(userid) 
		 values (new.userid);
end;
#测试插入数据
INSERT into user(userid,name,username,password,avatar) 
values (12545658, '谷', 'asdasd', '145457812000', 'asfas');
#测试插入数据
INSERT into user(userid,name,username,password,avatar) 
values (22545658, '文', '啊啊啊asd', '14545451200', 'asfa4');
#测试删除数据
delete from user where name = '谷';



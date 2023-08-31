# online-chat
springboot+vue聊天室

# 1.建表，此处是mysql
## 1.1 用户表和用户详情表

<code>
create table user(
 id int PRIMARY KEY auto_increment comment '主键',
 userid char(8) not null unique comment '用户唯一id',
 name varchar(10) not null UNIQUE COMMENT '用户展示名字',
 username varchar(20) not null unique comment '用户账号',
 password varchar(20) not null  comment '用户密码',
 avatar varchar(50) not null comment '用户头像',
 create_time date comment '创建日期'
)COMMENT '用户表';

create table user_detail(
id int PRIMARY KEY auto_increment comment '主键',
userid char(8) not null unique comment '用户唯一id',
age TINYINT check(age>0 && age<120) comment '用户年龄',
gender char(1) comment '性别',
email VARCHAR(20) comment '邮箱',
phone int comment '电话号码'
)COMMENT '用户详情表';
</code>
### 1.1.1 创建外键约束
让用户表在删除相关用户时用户详情表相关信息一并删除
<code>
alter table user_detail add CONSTRAINT user_userdetail foreign key (userid) REFERENCES user(userid) on delete cascade;
</code>

### 1.1.2 构建触发器
让用户表创建时在用户详情表中也存入用户id
<code>
create trigger adduserdetail
after insert on user
for each row
begin
insert into user_detail(userid)
values (new.userid);
end;
</code>

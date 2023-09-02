# online-chat
springboot+vue聊天室
# 一、数据库表的创建
## 1.关于用户
### 1.1 创建用户表和用户详情表
<code> 
  create table user(
 id int PRIMARY KEY auto_increment comment '主键',
 userid char(8) not null unique comment '用户唯一id',
 name varchar(10) not null UNIQUE COMMENT '用户展示名字',
 username varchar(20) not null unique comment '用户账号',
 password varchar(20) not null  comment '用户密码',
 avatar varchar(50) not null comment '用户头像',
 create_time date comment '创建日期',
 salt char(4) comment '盐值'
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
### 1.2 创建外键约束
让用户表删除时用户详情表一块删除
<code>
alter table user_detail add CONSTRAINT user_userdetail foreign key (userid) REFERENCES user(userid) on delete cascade;
</code>
### 1.3 创建触发器
让用户表创建时吧userid存到用户详情表中
<code>
create trigger adduserdetail 
after insert on user 
for each row
begin
		 insert into user_detail(userid) 
		 values (new.userid);
end;
</code>
# 二、登录注册实现
## 2.注册
### 2.1 头像（文件上传）
前端可以给出默认头像以及头像地址，这里只阐述后端获取用户上传，这里的图片信息都是存储在存储通中的，具体实现类看TXCloudUtils和HuaweiObs两个工具类，
本项目是用的腾讯云（华为云步骤大差不差）
### 2.2 基本信息
上传图片后会获取图片的url，带着基本信息进行简单查询就行，注意重名检测，就是插入前写一个根据name查
**注：这里的返回值类型会返回null，**

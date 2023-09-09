show index from friend;

#创建外键约束，用户删除时删除好友
alter table friend add CONSTRAINT user_friend FOREIGN key (userid)  REFERENCES user(userid) on delete cascade;

#创建外键约束，用户删除时删除好友请求
alter table friend add CONSTRAINT user_friendrequest FOREIGN key (userid)  REFERENCES user(userid) on delete cascade;

#创建存储过程，根据status执行不同操作
create procedure  addfriend(in status int,in userid char(8),in friend_id char(8)) 
begin 
        if status = 2 then
           INSERT INTO friend(userid,friend_id) values(userid,friend_id);
        end if;
end;

#删除存储过程
drop procedure if EXISTS addfriend;
#创建触发器，在修改status时调用存储函数
create trigger setfriend
after update 
on friendrequest for each row
begin
		 call addfriend(new.status,new.userid,new.friend_id);
end;
#删除触发器
drop trigger setfriend;
#测试插入数据
INSERT INTO friendrequest(userid,friend_id,status) values('asdfasdf','zxcvzxcv',0);
#测试修改数据
UPDATE friendrequest set status = 2 where userid = 'asdfasdf' and friend_id = 'zxcvzxcv';
#创建定时任务，当前时间10s后删除该条sql语句
CREATE EVENT delete_request
          ON SCHEDULE AT CURRENT_TIMESTAMP + INTERVAL 10 SECOND
          DO  DELETE FROM friendrequest where userid = userid and friend_id = friend_id;
#创建定时任务，每周删除一次好友请求
CREATE EVENT delete_request 
ON SCHEDULE EVERY 7 DAY
ON COMPLETION PRESERVE 
DO TRUNCATE table friendrequest
#删除事件
DROP EVENT IF EXISTS delete_request;					
					
#关于为什么不直接根据status=1进行判断删除当前行
#（1）直接在存储过程中删除会触发行锁问题，（2）在存储过程中写定时器也会出错，因为DDL语句里面不能在放DDL语句，不然可能造成DDL递归
#所以我是在服务器端进行操作的，当执行update操作成功之后执行delete操作删除请求表中的数据，当然也可以不管这个数据，可以写个定时器
#定期删除


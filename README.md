# online-chat
springboot+vue聊天室
# 所用技术
### springboot
### vue
### websocket
### satoken
### Mybatis
### redis
### mysql

# 使用
###  前端目前只写了登录注册和群聊的（也比较简洁）
###  下载后也就是修改配置文件，其他没什么特别的
###  对应sql脚本放在sql目录下，注意注释里面包含测试数据

# 以下是项目的基本逻辑有兴趣的可以看看

## 一、数据库表的创建
### 1.关于用户
#### 1.1 创建用户表和用户详情表

#### 1.2 创建外键约束
当用户表删除对应详情表数据清空，对应聊天记录清空等

#### 1.3 创建触发器
注册账号时在详情表插入userid

#### 1.4 创建索引
登录时根据名字查找到密码更快速

## 二、登录注册实现

### 1.注册
#### 1.1 头像（文件上传）
前端可以给出默认头像以及头像地址，这里只阐述后端获取用户上传，这里的图片信息都是存储在存储通中的，具体实现类看TXCloudUtils和HuaweiObs两个工具类，
本项目是用的腾讯云（华为云步骤大差不差）
#### 1.2 基本信息
上传图片后会获取图片的url，带着基本信息进行简单查询就行，注意重名检测，就是插入前写一个根据name查
**注：这里的返回值类型会返回null，如果方法写int会报非空错误**

### 2.登录
#### 1.1 登录后将token记入，拦截器放行

## 三、聊天
### 1.1 服务端搭建websocket服务
连接、接收与发送信息、断开连接等
### 1.2 客户端搭建相应服务

### 1.3 聊天记录存放
#### 1.3.1 群聊表
只记录名字，内容和时间，写个定时器每天0点清零，用户进入时只加载十几条数据
#### 1.3.2 私聊表
涵盖基本信息，与好友列表(userid,friend_id)建立外键约束，这样不是好友就不能插入信息，当好友删除时信息也删除

## 四、好友
### 1.1 创建好友表和好友请求表
发起好友请求，同意好友请求，拒绝好友请求，这三种放入好友请求表，当同意后放入好友表

### 1.2 创建外键约束
删除用户时，吧相应的friend表和请求表内容清空

### 1.3 创建存储过程
根据不同请求状态进行不同的sql操作和返回值






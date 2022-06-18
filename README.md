# KeepHealthy健康服务平台项目 

#### 介绍
  基于微服务架构，包括健康社区业务、体检预约业务和肺癌AI风险评估业务，帮助大众维护身体健康。
  
  健康社区业务仓库如下：
  https://github.com/CONTINUE12/RIREE
  
  肺癌AI风险评估业务仓库如下：
  https://github.com/CONTINUE12/DeepLearning-for-Medical-Image-Processing

#### 软件架构
  软件架构说明: 前后端分离 

  后端：springboot+springcloud搭建

  前端: 1、用户的前端架构是使用了服务端渲染技术nuxt; 
        2、管理员的前端使用了 Vue 2.x 的 Element UI 

#### 使用的技术点
后端技术：
- 1、SpringBoot 
- 2、SpringCloud
（1）Eureka注册中心
（2）Feign
（3）GateWay
- 3、Redis
（1）使用Redis作为缓存
（2）验证码有效时间、支付二维码有效时间
- 4、MongoDB
（1）使用MongoDB存储医院相关数据
- 5、EasyExcel
（1）操作excel表格，进行读和写操作
- 6、MyBatisPlus
- 7、RabbitMQ
（1）订单相关操作时，用mq发送短信消息给短信消费者
- 8、Docker
（1）下载镜像 docker pull 
（2）创建容器 docker run
- 9、阿里云OSS
- 10、容联云短信服务
- 11、微信登录/支付
- 12、定时任务

前端技术：

- 1、vue
 （1）指令
- 2、Element-ui
- 3、nuxt
- 4、npm
- 5、ECharts图表

#### 注意点
- 阿里云存储模块service_oss的application配置文件中accessKeyId等值请填写自己的，出于安全考虑，这里我删掉了自己的
- aliyun.oss.endpoint=请设置为自己的
- aliyun.oss.accessKeyId=请设置为自己的
- aliyun.oss.secret=请设置为自己的
- aliyun.oss.bucket=请设置为自己的

- 同理，容联云短信功能的RLYServiceImpl类中的方法用到的哪些id请设置为自己的
- String accountSId = 请设置为自己的
- String accountToken = 请设置为自己的
- String appId = 请设置为自己的

#### 其他仓库项目链接

1.基于Spring+SpringMVC+Mybatis的图书管理系统
https://github.com/CONTINUE12/SSM_LibrarySystem

2.基于SpringBoot2.0+Mybatis的学生成绩管理系统
https://github.com/CONTINUE12/SpringBoot_StudentManagerSystem

3.基于JAVAWEB的超市订单管理系统SMBMS
https://github.com/CONTINUE12/Javaweb-SMBMS

4.基于AWT和Swing及MYSQL开发的学校教务系统
https://github.com/CONTINUE12/JavaSE_School_Management_System


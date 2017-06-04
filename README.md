# 云快速开发与管理平台
产品定位：
YP 3.0是基于JEE开源、轻量级的企业敏捷开发平台

技术特点：  
JEE轻量级技术架构平台，集优秀开源技术整合
跨平台，跨数据库，跨浏览器
开发快速稳定
功能强大
升级容易

第三方组件集成：  
Office组件，在线流程设计器，手机短信集成，即时通信

代码生成器：  
基于Ant生成方式
基于Freemark代码模板
基于多种数据库表生成
单表、多表生成
在线配置访问生成功能页面

在线流程设计器：  
BPMN2.0标准支持
开放、轻量级的流程体系
基于流行的JAVA 流程引擎Activiti 5.10扩展
强大的中国特色流程支持
灵活的在线流程定义
灵活的第三方表单集成
灵活的组织结构支持
灵活的流程的导入与导出
流程多版本管理
流程表单多版本管理
流程任务的灵活人员运算处理

权限管理：  
1、RBAC（Role Based Access Control）
2、基于Spring Security 3.0.3，通过拦截任意URL资源是否分配给用户的角色来判断有无访问权限。
3、抽象出7张表，同时平台通过角色管理和权限分配界面来管理，统一方便。

桌面管理：  
1、桌面栏目
2、桌面布局
3、个人桌面设置

邮件管理：  
1、主流邮箱都支持，网易、新浪、搜狐、腾讯
2、邮件管理的灵感来自foxmail
3、用户自定义邮箱配置，可设置自己的默认邮箱

消息管理：  
1、使用DWR自动推送功能，浏览器连接服务器60S
2、发消息可以发选择的若干人，或发给组织
3、可回复消息，桌面右下角自动推送来新消息的条数，点击进入用户收到的消息模块。

组织管理：  
1、机构以树+列表结构，方便操作，新建机构时可添加人员
2、新建用户可直接选机构、岗位、角色
3、岗位以树+列表结构，方便操作，新建岗位时可添加人员

系统管理：  
1、主题可任意切换，设置默认立即生效
2、Logo可任意添加，设置默认立即生效
3、系统名称和企业信息可任意切换，设置默认立即生效
4、编码规则统一管理，供平台其它模块调用
5、日志管理可复杂模糊查询
6、缓存管理可刷新全部，可选择性地刷新
7、数据源管理可配置MySQL、Oracle、DB2等数据库

缓存管理：  
1、使用Spring Cache，加注解的方式，方便开发
2、可集成第三方缓存方案，如Memcache、Ehcache
3、支持分布式缓存机制，可组成缓存服务器集群
4、对业务设计的表单和列表设置都进行了缓存，KP1.1时的性能问题得以解决，对业务操作达到高可用。
5、对权限、桌面、主题都进行缓存，提高了几倍系统的相应速度
6、支持编写代码来使用缓存，使用iCache

日志管理：  
1、日志可记录到文件
2、日志可记录到数据库，日志表可自定义按月存日志
3、使用AOP记录log4j日志
4、可配置从数据库记录日志切换到文件记录日志
5、可配置从文件记录日志切换到数据库记录日志
6、也可同时记录数据库日志和文件日志
7、可从AOP方式转化为代码方式
8、可代码方式转化为AOP方式
9、完全能支持后续开发OA5.0和电子文件的复杂日志需求

性能优化：  
使用Memcached、Spring AOP构建数据库前端缓存框架
应用服务器应该负载均衡方案
数据库集群解决方案
调整JVM的参数
调整数据库连接池参数
调整应用服务器参数
调整数据库参数
压缩JS、CSS脚本大小，缩小图片和页面大小

总结：  
平台复用
子系统复用
代码复用
标准规范复用
























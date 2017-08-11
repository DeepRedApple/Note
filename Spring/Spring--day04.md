# 一、spring day03回顾

## 1.1事务管理

- 基于xml配置

  ```xml
  1.配置事务管理器
    jdbc：DataSourceTransactionManager
    hibernate：HibernateTransactionManager
  2.事务通知（详情、属性）
  <tx:advice id="" transaction-manager="">
    <tx:attributes>
       <tx:method name="add*">
       <tx:method name="update*">
       <tx:method name="delete*">
       <tx:method name="find* read-only="true">

  3. AOP编程，ABCD--> ABC
  <aop:config>
    <aop:advisor advice-ref="txAdvice"  pointcut="execution(* com..*.*(..))">
  ```


- 基于注解

```xml
xml 配置
1.事务管理器

2.将配置事务管理器交予spring
<tx:annotation-driven transaction-manager="....">


目标类
@Transactional  类 | 方法
```

## 1.2整合Junit

@RunWith(SpringJunit4RnnerClass.class)

@ContextConfiguration(locations=&quot;classpath:...xml&quot;)

@Autowired 注入

@Test 测试

## 1.3整合web

web.xml 配置

1.确定xml位置

 &lt;context-param&gt;

  name：contextConfigLocation

  value：classpath:...xml

2.加载xml文件，配置监听器

 ContextLoaderListener

## 1.4整合

1. hibernate po （domain、bean）
2. dao层

 需要HibernateTemplate 相当于session操作PO类--&gt; 必须提供setter方法，让spring注入，xml配置模板

  save()  update  delete  saveOrUpdate   find

 继承HibernateDaoSupport，需要注入SessionFactory，底层自动创建模板

  dao方法中this.getHibernateTemplate()

  3.service 层，spring配置

 properties &lt;context:property-placeholder location&gt;

 配置数据源：ComboPooledDataSource

 配置SessionFactory：LocalSessionFactoryBean

 事务管理

4.web层，aciton

 struts.xml  &lt;action name=&quot;&quot; class=&quot;全限定类名&quot;&gt; 前面看到一位struts，底层使用spring

 默认与spring整合之后，按照【名称】自动注入

# 二、SVN

## 2.1版本控制

### 2.1.1什么版本控制

- 版本控制（Revision Control）：是维护工程蓝图的标准做法，能追踪工程蓝图从诞生一直到定案的过程。是一种记录若干文件内容变化，以便将来查阅特定版本修订情况的系统。也是一种软体工程技巧，籍以在开发的过程中，确保由不同人所编辑的同一档案都得到更新。

### 2.1.2版本控制软件

- CVS（Concurrent Versions System）代表协作版本系统或者并发版本系统，是一种版本控制系统，方便软件的开发和使用者协同工作。
- VSS （Visual Source Safe ）只能在windows下,作为Microsoft Visual Studio 的一名成员，它主要任务就是负责项目文件的管理
- Git是用于Linux内核开发的版本控制工具。它采用了分布式版本库的方式，不必服务器端软件支持，使源代码的发布和交流极其方便。Git的速度很快，这对于诸如Linux kernel这样的大项目来说自然很重要。Git最为出色的是它的合并跟踪（merge tracing）能力。 www.github.org


- SVN（Subversion ），是一个开放源代码的版本控制系统，采用了分支管理系统，它的设计目标就是取代CVS。

## 2.2SVN特点

- 统一的版本号。CVS是对每个文件顺序编排版本号，在某一时间各文件的版本号各不相同。而Subversion下，任何一次提交都会对所有文件增加到同一个新版本号，即使是提交并不涉及的文件。所以，各文件在某任意时间的版本号是相同的。版本号相同的文件构成软件的一个版本。
- 原子提交。一次提交不管是单个还是多个文件，都是作为一个整体提交的。在这当中发生的意外例如传输中断，不会引起数据库的不完整和数据损坏。
- 重命名、复制、删除文件等动作都保存在版本历史记录当中。
- 对于二进制文件，使用了节省空间的保存方法。（简单的理解，就是只保存和上一版本不同之处）
- 目录也有版本历史。整个目录树可以被移动或者复制，操作很简单，而且能够保留全部版本记录。
- 分支的开销非常小。
- 优化过的数据库访问，使得一些操作不必访问数据库就可以做到。这样减少了很多不必要的和数据库主机之间的网络流量。
- 支持元数据（Metadata）管理。每个目录或文件都可以定义属性（Property），它是一些隐藏的键值对，用户可以自定义属性内容，而且属性和文件内容一样在版本控制范围内。
- 支持FSFS和Berkeley DB两种资料库格式。
- 不足：只能设置目录的访问权限，无法设置单个文件的访问权限。

## 2.3体系结构

![](http://upload-images.jianshu.io/upload_images/1540531-89067284b445cc5a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 2.4安装

![](http://upload-images.jianshu.io/upload_images/1540531-1bd1217bb5200abb.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

- 测试安装

    ![](http://upload-images.jianshu.io/upload_images/1540531-30a19260333cec8f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

- 检查path系统环境变量

    ![](http://upload-images.jianshu.io/upload_images/1540531-a406b3d127fd6953.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 2.5创建仓库

- 格式：cmd&gt;  svnadmin create 路径

  ![](http://upload-images.jianshu.io/upload_images/1540531-78a81e7411f59830.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


- 仓库目录结构

    ![](http://upload-images.jianshu.io/upload_images/1540531-8bce08c7e020ee9f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

- 注意： 创建仓库时，目录必须是空的，及新建文件夹

## 2.6启动

- 格式：cmd&gt;  svnserve  -d  -r  仓库的路径

    ```cmake
    -d后台执行
    -r版本库的根目录
    ```

启动时，指定&quot;仓库路径&quot;不同，分类：多仓库和单仓库

- 多仓库【掌握】

    ![](http://upload-images.jianshu.io/upload_images/1540531-0a9be1e323e9de00.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


        启动：svnserve -d -r 仓库父目录  ，表示启动时多仓库

 例如：svnserve -d -r G:\repository\svn

    访问：svn://localhost:3690/bbs

- 单仓库

    ![](http://upload-images.jianshu.io/upload_images/1540531-88e017f7a0af2543.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


        启动：svnserve -d -r 仓库的根  ，表示启动时单仓库

        例如：svnserve -d -r G:\repository\svn\bbs

        访问：svn://localhost:3690

- 将操作注册成操作系统的&quot;服务&quot;，开机启动。

    1.&quot;运行&quot;，services.msc 打开&quot;服务&quot;

2. 删除&quot;服务&quot;

   ![](http://upload-images.jianshu.io/upload_images/1540531-a37772e5da2bee54.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

3. 注册&quot;服务&quot;

   ![](http://upload-images.jianshu.io/upload_images/1540531-b3c15f10a5e28e77.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```
sc create svn binpath= "D:\java\Subversion\bin\svnserve.exe --service -r G:\repository\svn" displayname= "SVN-Service" start= auto depend= Tcpip
```

4. 启动或停止&quot;服务&quot;

   ![](http://upload-images.jianshu.io/upload_images/1540531-6206d387278e17b8.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 2.7操作【掌握思想】

![](http://upload-images.jianshu.io/upload_images/1540531-937e32839e94cdb4.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### 2.7.1checkout

- 格式：svn  checkout  服务器地址  下载地址

    ![](http://upload-images.jianshu.io/upload_images/1540531-c3c9b99f742b3404.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### 2.7.2commit

- 格式：svn commit  资源

问题1：没有纳入版本控制

![](http://upload-images.jianshu.io/upload_images/1540531-0cc8477f78d15316.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240) 

使用add子命令添加到本地版本库

![](http://upload-images.jianshu.io/upload_images/1540531-208e269705bd7fe2.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


问题2：没有编写日志

![](http://upload-images.jianshu.io/upload_images/1540531-c3bfca17d7bf24f1.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


 采用-m  参数设置日志信息

![](http://upload-images.jianshu.io/upload_images/1540531-0d344bcb078025a5.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


问题3：没有权限

![](http://upload-images.jianshu.io/upload_images/1540531-44c8bfc03eea4a90.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


 修改权限，设置匿名访问

  G:\repository\svn\bbs\conf\svnserve.conf

![](http://upload-images.jianshu.io/upload_images/1540531-9d06def8c1982abc.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


### 2.7.3update

- 格式：svn update

  ![](http://upload-images.jianshu.io/upload_images/1540531-3dc55b059074e16c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 2.8图形化：TortoiseSVN 安装

![](http://upload-images.jianshu.io/upload_images/1540531-2f7a5a3b79aed58b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

- 安装成功之后，所有的操作都是&quot;右键&quot;

## 2.9svn权限

- 权限需要3个配置文件

    ![](http://upload-images.jianshu.io/upload_images/1540531-bb5f18fcaab5b028.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

- svnserve.conf

 开启认证

![](http://upload-images.jianshu.io/upload_images/1540531-a68c8a53042e29e6.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


 确定账号配置文件位置

![](http://upload-images.jianshu.io/upload_images/1540531-27129484597a1b5f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

 确定认证配置文件位置

![](http://upload-images.jianshu.io/upload_images/1540531-ea8847620024c6c2.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


- passwd  账号配置（一行一个账号，账号用户名和密码组成，使用等号分隔）

    ![](http://upload-images.jianshu.io/upload_images/1540531-166987cbf7f65865.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

- authz 认证配置文件

 配置组，格式：组名= 用户1 ，用户2，....

![](http://upload-images.jianshu.io/upload_images/1540531-7579787e26f1a198.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


 认证细节配置

多仓库

​                     [bbs:/]                 -->确定仓库名称 。[bbs:/doc]  给bbs仓库的doc目录配置权限

​                     @itheima= rw       --> 给itheima组设置权限。

​                                                        read('r') ，read-write('rw') ，or no access('').

​                     user3= r                --> 给user3 指定权限

​                     *=                        --> 其他用户没有权限

![](http://upload-images.jianshu.io/upload_images/1540531-57b068f24c90fca5.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

单仓库

   [/]    --&gt;当仓库的根  [/doc]  单仓库doc目录

![](http://upload-images.jianshu.io/upload_images/1540531-c5dfb7cc727c1150.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 2.10TortoiseSVN 常见图标

![](http://upload-images.jianshu.io/upload_images/1540531-729c2e614ca4a99c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 2.11myeclipse svn 插件

### 2.11.1安装插件

![](http://upload-images.jianshu.io/upload_images/1540531-61bff8a075149523.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

  如果3个都不能使用，直接换eclipse

- 安装方式1：直接复制

    ![](http://upload-images.jianshu.io/upload_images/1540531-bfb382881ad82ed0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

 eclipse插件规范

  eclipse 目录

   | -- features目录

   | -- plugins 目录

- 方式2：使用link文件

 将插件解压到任意目录（不含中文、空格），在myeclipse/dropins目录添加一个link文件

  文件名：自定义

  文件扩展名：link

  文件内容：

   path = 插件完整目录，需要指定到eclipse，及可以看到（features 、plugins）

   例如：

    path=D:\\java\\MyEclipse\\MyEclipse 10\\svn\\eclipse

    path=D:/java/MyEclipse/MyEclipse 10/svn/eclipse

- 方式3：在线安装

- 安装成功标志

    ![](http://upload-images.jianshu.io/upload_images/1540531-f834aa745e03a902.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### 2.11.2操作

## 2.12svn目录规范

![](http://upload-images.jianshu.io/upload_images/1540531-17ee1fb6f2db8d19.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](http://upload-images.jianshu.io/upload_images/1540531-de8f06a36b40a6a8.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

trunk  ，主线，用于存放程序整个进度

branches ，分支，例如：bug修复、特殊功能等

tags，标签（版本），此目录下的内容不能修改
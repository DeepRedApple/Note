# 1spring day02回顾

- AOP ：切面编程

 切面：切入点和通知结合

- spring aop 编程

```xml
<aop:config>
方法1：
  <aop:pointcut expression="切入点表达式" id="">
  <aop:advisor  advice-ref="通知引用" pointcut-ref="切入点的引用">

方法2：
   <aop:advisor  advice-ref="通知引用" pointcut="切入点表达式">
```



- AspectJ xml

```xml
<aop:config>
  <aop:aspect ref="切面类">
     <aop:pointcut>
     <aop:before>  前置
     <aop:afterReturning  returning="第二个参数名称"> 后置
     <aop:around> 环绕
     <aop:afterThrowing throwing="第二。。。"> 抛出异常
     <aop:after> 最终
```



- AspectJ annotation

 @Aspect

 @Pointcut(&quot;表达式&quot;)  private void xxx(){}

 @Before @...

- 切入点表达式

 &lt;aop:pointcut expression=&quot;execution(\* com.itheima.crm.\*.service..\*.\*(..))&quot; id="";&gt;

# 2事务管理

## 2.1回顾事务

- 事务：一组业务操作ABCD，要么全部成功，要么全部不成功。
- 特性：ACID

 原子性：整体

 一致性：完成

 隔离性：并发

 持久性：结果

- 隔离问题：

 脏读：一个事务读到另一个事务没有提交的数据

 不可重复读：一个事务读到另一个事务已提交的数据（update）

 虚读(幻读)：一个事务读到另一个事务已提交的数据（insert）

- 隔离级别：

 read uncommitted：读未提交。存在3个问题

 read committed：读已提交。解决脏读，存在2个问题

 repeatable read：可重复读。解决：脏读、不可重复读，存在1个问题。

 serializable ：串行化。都解决，单事务。

![](http://upload-images.jianshu.io/upload_images/1540531-d3332f4ebb65f0d3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


- mysql 事务操作--简单

```java
//ABCD 一个事务
Connection conn = null;
try{
  //1 获得连接
  conn = ...;
  //2 开启事务
  conn.setAutoCommit(false);
  A
  B
  C
  D
  //3 提交事务
  conn.commit();
} catche(){
  //4 回滚事务
  conn.rollback();
}

```



- mysql 事务操作--Savepoint

```java
需求：AB（必须），CD（可选） 
Connection conn = null;
Savepoint savepoint = null;  //保存点，记录操作的当前位置，之后可以回滚到指定的位置。（可以回滚一部分）
try{
  //1 获得连接
  conn = ...;
  //2 开启事务
  conn.setAutoCommit(false);
  A
  B
  savepoint = conn.setSavepoint();
  C
  D
  //3 提交事务
  conn.commit();
} catche(){
  if(savepoint != null){   //CD异常
     // 回滚到CD之前
     conn.rollback(savepoint);
     // 提交AB
     conn.commit();
  } else{   //AB异常
     // 回滚AB
     conn.rollback();
  }
}

```



## 2.2事务管理介绍

### 2.2.1导入jar包

transaction  --&gt;  tx

![](http://upload-images.jianshu.io/upload_images/1540531-48446549524e3989.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### 2.2.2三个顶级接口

![](http://upload-images.jianshu.io/upload_images/1540531-8f57cbc75f2a0864.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

- PlatformTransactionManager  平台事务管理器，spring要管理事务，必须使用事务管理器

 进行事务配置时，必须 **配置事务管理器** 。

- TransactionDefinition：事务详情（事务定义、事务属性），spring用于确定事务具体详情，

 例如：隔离级别、是否只读、超时时间等

 进行事务配置时， **必须配置详情** 。spring将配置项封装到该对象实例。

- TransactionStatus：事务状态，spring用于记录当前事务运行状态。例如：是否有保存点，事务是否完成。

 spring底层根据状态进行相应操作。

### 2.2.3PlatformTransactionManager  事务管理器

- 导入jar包：需要时平台事务管理器的实现类

    ![](http://upload-images.jianshu.io/upload_images/1540531-61513febf1041c5c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

- 常见的事务管理器

    DataSourceTransactionManager  ，jdbc开发时事务管理器，采用JdbcTemplate

    HibernateTransactionManager，hibernate开发时事务管理器，整合hibernate

    ![](http://upload-images.jianshu.io/upload_images/1540531-f4c4691134b7e22a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


- api详解

TransactionStatus getTransaction(TransactionDefinition definition) ，事务管理器通过&quot;事务详情&quot;，获得&quot;事务状态&quot;，从而管理事务。

void commit(TransactionStatus status)  根据状态提交

void rollback(TransactionStatus status) 根据状态回滚

### 2.2.4TransactionStatus

![](http://upload-images.jianshu.io/upload_images/1540531-69953ff2b684d1de.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### 2.2.5TransactionDefinition

![](http://upload-images.jianshu.io/upload_images/1540531-6a1ff14df8179d1c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

- 传播行为：在两个业务之间如何共享事务。

PROPAGATION\_REQUIRED , required , 必须  【默认值】

 支持当前事务，A如果有事务，B将使用该事务。

 如果A没有事务，B将创建一个新的事务。

PROPAGATION\_SUPPORTS ，supports ，支持

 支持当前事务，A如果有事务，B将使用该事务。

 如果A没有事务，B将以非事务执行。

PROPAGATION\_MANDATORY，mandatory ，强制

 支持当前事务，A如果有事务，B将使用该事务。

 如果A没有事务，B将抛异常。

PROPAGATION\_REQUIRES\_NEW ，requires\_new ，必须新的

 如果A有事务，将A的事务挂起，B创建一个新的事务

 如果A没有事务，B创建一个新的事务

PROPAGATION\_NOT\_SUPPORTED ，not\_supported ,不支持

 如果A有事务，将A的事务挂起，B将以非事务执行

 如果A没有事务，B将以非事务执行

PROPAGATION\_NEVER ，never，从不

 如果A有事务，B将抛异常

 如果A没有事务，B将以非事务执行

PROPAGATION\_NESTED ，nested ，嵌套

 A和B底层采用保存点机制，形成嵌套事务。

掌握：PROPAGATION\_REQUIRED、PROPAGATION\_REQUIRES\_NEW、PROPAGATION\_NESTED





## 2.3案例：转账

### 2.3.1搭建环境

#### 2.3.1.1创建表

```mysql
create database ee19_spring_day03;
use ee19_spring_day03;
create table account(
  id int primary key auto_increment,
  username varchar(50),
  money int
);
insert into account(username,money) values('jack','10000');
insert into account(username,money) values('rose','10000');
```

#### 2.3.1.2导入jar包

- 核心：4+1

- aop ：4 (aop联盟、spring aop、aspectj规范、spring aspect)

- 数据库：2  （jdbc/tx）

- 驱动：mysql

- 连接池：c3p0

  ![](http://upload-images.jianshu.io/upload_images/1540531-e50f73cd8cea3df6.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

#### 2.3.1.3dao层

```java
public class AccountDaoImpl extends JdbcDaoSupport implements AccountDao {

	@Override
	public void out(String outer, Integer money) {
		this.getJdbcTemplate().update("update account set money = money - ? where username = ?", money,outer);
	}

	@Override
	public void in(String inner, Integer money) {
		this.getJdbcTemplate().update("update account set money = money + ? where username = ?", money,inner);
	}

}
```

#### 2.3.1.4service层

```java
public class AccountServiceImpl implements AccountService {

	private AccountDao accountDao;
	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}
	@Override
	public void transfer(String outer, String inner, Integer money) {
		accountDao.out(outer, money);
		//断电
//		int i = 1/0;
		accountDao.in(inner, money);
	}

}

```

#### 2.3.1.5spring配置

```xml
	<!-- 1 datasource -->
	<bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
		<property name="driverClass" value="com.mysql.jdbc.Driver"></property>
		<property name="jdbcUrl" value="jdbc:mysql://localhost:3306/ee19_spring_day03"></property>
		<property name="user" value="root"></property>
		<property name="password" value="1234"></property>
	</bean>
	
	<!-- 2 dao  -->
	<bean id="accountDao" class="com.itheima.dao.impl.AccountDaoImpl">
		<property name="dataSource" ref="dataSource"></property>
	</bean>
	<!-- 3 service -->
	<bean id="accountService" class="com.itheima.service.impl.AccountServiceImpl">
		<property name="accountDao" ref="accountDao"></property>
	</bean>
```

#### 2.3.1.6测试

```java
	@Test
	public void demo01(){
		String xmlPath = "applicationContext.xml";
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlPath);
		AccountService accountService =  (AccountService) applicationContext.getBean("accountService");
		accountService.transfer("jack", "rose", 1000);
	}

```



### 2.3.2手动管理事务（了解）

- spring底层使用TransactionTemplate 事务模板进行操作。
- 操作

1.service 需要获得TransactionTemplate

2.spring 配置模板，并注入给service

3.模板需要注入事务管理器

4.配置事务管理器：DataSourceTransactionManager ，需要注入DataSource

#### 2.3.2.1修改service

```java
	//需要spring注入模板
	private TransactionTemplate transactionTemplate;
	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}
	@Override
	public void transfer(final String outer,final String inner,final Integer money) {
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				accountDao.out(outer, money);
				//断电
//				int i = 1/0;
				accountDao.in(inner, money);
			}
		});

	}
```

#### 2.3.2.2修改spring配置

```xml
<!-- 3 service -->
	<bean id="accountService" class="com.itheima.service.impl.AccountServiceImpl">
		<property name="accountDao" ref="accountDao"></property>
		<property name="transactionTemplate" ref="transactionTemplate"></property>
	</bean>
	
	<!-- 4 创建模板 -->
	<bean id="transactionTemplate" class="org.springframework.transaction.support.TransactionTemplate">
		<property name="transactionManager" ref="txManager"></property>
	</bean>
	
	<!-- 5 配置事务管理器 ,管理器需要事务，事务从Connection获得，连接从连接池DataSource获得 -->
	<bean id="txManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
		<property name="dataSource" ref="dataSource"></property>
	</bean>
```

### 2.3.3工厂bean 生成代理：半自动

- spring提供管理事务的代理工厂bean TransactionProxyFactoryBean

  1.getBean() 获得代理对象

  2.spring 配置一个代理

#### 2.3.3.1spring配置

```xml-dtd
<!-- 4 service 代理对象 
		4.1 proxyInterfaces 接口 
		4.2 target 目标类
		4.3 transactionManager 事务管理器
		4.4 transactionAttributes 事务属性（事务详情）
			prop.key ：确定哪些方法使用当前事务配置
			prop.text:用于配置事务详情
				格式：PROPAGATION,ISOLATION,readOnly,-Exception,+Exception
					传播行为		隔离级别	是否只读		异常回滚		异常提交
				例如：
					<prop key="transfer">PROPAGATION_REQUIRED,ISOLATION_DEFAULT</prop> 默认传播行为，和隔离级别
					<prop key="transfer">PROPAGATION_REQUIRED,ISOLATION_DEFAULT,readOnly</prop> 只读
					<prop key="transfer">PROPAGATION_REQUIRED,ISOLATION_DEFAULT,+java.lang.ArithmeticException</prop>  有异常扔提交
	-->
	<bean id="proxyAccountService" class="org.springframework.transaction.interceptor.TransactionProxyFactoryBean">
		<property name="proxyInterfaces" value="com.itheima.service.AccountService"></property>
		<property name="target" ref="accountService"></property>
		<property name="transactionManager" ref="txManager"></property>
		<property name="transactionAttributes">
			<props>
				<prop key="transfer">PROPAGATION_REQUIRED,ISOLATION_DEFAULT</prop>
			</props>
		</property>
	</bean>


	<!-- 5 事务管理器 -->
	<bean id="txManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
		<property name="dataSource" ref="dataSource"></property>
	</bean>
```



#### 2.3.3.2测试

 ![](http://upload-images.jianshu.io/upload_images/1540531-1986fa72b9e203ad.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### 2.3.4AOP 配置基于xml【掌握】

- 在spring xml 配置aop 自动生成代理，进行事务的管理

  1.配置管理器

  2.配置事务详情

  3.配置aop

```xml
<!-- 4 事务管理 -->
	<!-- 4.1 事务管理器 -->
	<bean id="txManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
		<property name="dataSource" ref="dataSource"></property>
	</bean>
	<!-- 4.2 事务详情（事务通知）  ， 在aop筛选基础上，对ABC三个确定使用什么样的事务。例如：AC读写、B只读 等
		<tx:attributes> 用于配置事务详情（属性属性）
			<tx:method name=""/> 详情具体配置
				propagation 传播行为 ， REQUIRED：必须；REQUIRES_NEW:必须是新的
				isolation 隔离级别
	-->
	<tx:advice id="txAdvice" transaction-manager="txManager">
		<tx:attributes>
			<tx:method name="transfer" propagation="REQUIRED" isolation="DEFAULT"/>
		</tx:attributes>
	</tx:advice>
	<!-- 4.3 AOP编程，目标类有ABCD（4个连接点），切入点表达式 确定增强的连接器，从而获得切入点：ABC -->
	<aop:config>
		<aop:advisor advice-ref="txAdvice" pointcut="execution(* com.itheima.service..*.*(..))"/>
	</aop:config>

```



### 2.3.5AOP配置基于注解【掌握】

- 1.配置事务管理器，将并事务管理器交予spring
- 2.在目标类或目标方法添加注解即可@Transactional



#### 2.3.5.1spring配置

```xml
<!-- 4 事务管理 -->
	<!-- 4.1 事务管理器 -->
	<bean id="txManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
		<property name="dataSource" ref="dataSource"></property>
	</bean>
	<!-- 4.2 将管理器交予spring 
		* transaction-manager 配置事务管理器
		* proxy-target-class
			true ： 底层强制使用cglib 代理
	-->
	<tx:annotation-driven transaction-manager="txManager"/>
```

#### 2.3.5.2service 层

```java
@Transactional
public class AccountServiceImpl implements AccountService {

}
```

#### 2.3.5.3事务详情配置

![](http://upload-images.jianshu.io/upload_images/1540531-e861554da2c53f1c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```java
@Transactional(propagation=Propagation.REQUIRED , isolation = Isolation.DEFAULT)
public class AccountServiceImpl implements AccountService {

}
```



# 3整合Junit

- 导入jar包

 基本：4+1

 测试：spring-test...jar

​	1.让Junit通知spring加载配置文件

​	2.让spring容器自动进行注入

- 修改测试类

```xml
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
public class TestApp {
	
	@Autowired  //与junit整合，不需要在spring xml配置扫描
	private AccountService accountService;
	
	@Test
	public void demo01(){
//		String xmlPath = "applicationContext.xml";
//		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlPath);
//		AccountService accountService =  (AccountService) applicationContext.getBean("accountService");
		accountService.transfer("jack", "rose", 1000);
	}

}
```



# 4整合web

0导入jar包

 spring-web.xml

![](http://upload-images.jianshu.io/upload_images/1540531-04b4fcf728a09d2c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


1tomcat启动加载配置文件

 servlet --&gt; init(ServletConfig) --&gt; &lt;load-on-startup&gt;2

 filter --&gt; init(FilterConfig)  --&gt; web.xml注册过滤器自动调用初始化

 listener --&gt; ServletContextListener --&gt; servletContext对象监听【】

 spring提供监听器 **ContextLoaderListener**  --&gt; web.xml  &lt;listener&gt;&lt;listener-class&gt;....

  如果只配置监听器，默认加载xml位置：/WEB-INF/applicationContext.xml

![](http://upload-images.jianshu.io/upload_images/1540531-4176b8ee08cbf58d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


2确定配置文件位置，通过系统初始化参数

 ServletContext 初始化参数web.xml

  &lt;context-param&gt;

&lt;param-name&gt;contextConfigLocation\</param-name>

 &lt;param-value&gt;classpath: applicationContext.xml\</param-value>

```xml
  <!-- 确定配置文件位置 -->
  <context-param>
  	<param-name>contextConfigLocation</param-name>
  	<param-value>classpath:applicationContext.xml</param-value>
  </context-param>
  
  <!-- 配置spring 监听器，加载xml配置文件 -->
  <listener>
  	<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
  </listener>
```

3从servletContext作用域获得spring容器（了解）

```java
	// 从application作用域（ServletContext）获得spring容器
		//方式1： 手动从作用域获取
		ApplicationContext applicationContext = 
				(ApplicationContext) this.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		//方式2：通过工具获取
		ApplicationContext apppApplicationContext2 = 
				WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
		
```

# 5SSH整合

## 5.1jar整合

struts：2.3.15.3

hibernate : 3.6.10

spring: 3.2.0

### 5.1.1struts

struts-2.3.15.3\apps\struts2-blank\WEB-INF\lib

![](http://upload-images.jianshu.io/upload_images/1540531-937742e67b7dda4c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](http://upload-images.jianshu.io/upload_images/1540531-860f74ac3b0520a6.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

模板技术  ，一般用于页面静态化

freemarker：扩展名：\*.ftl

velocity ：扩展名  \*.vm

### 5.1.2spring

- 基础：4+1 ，beans、core、context、expression ，commons-logging (struts已经导入)

- AOP：aop联盟、spring aop 、aspect规范、spring aspect

- db：jdbc、tx

- 测试：test

- web开发：spring web

- 驱动：mysql

- 连接池：c3p0

- 整合hibernate：spring orm

  ​								![](http://upload-images.jianshu.io/upload_images/1540531-9f4c5aa5e43c075a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

  ![](http://upload-images.jianshu.io/upload_images/1540531-ed5936bcf675c35c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

  ​

### 5.1.3hibernate

%h%\hibernate3.jar                        核心

%h%\lib\required                        必须

![](http://upload-images.jianshu.io/upload_images/1540531-74ee31f8303cd8a5.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


%h%\lib\jpa                                jpa规范（java persistent api 持久api），hibernate注解开发@Entity @Id 等

- 整合log4j

 导入log4j...jar (struts已经导入)

 整合（过渡）：slf4j-log4j12-1.7.5.jar

![](http://upload-images.jianshu.io/upload_images/1540531-968679dc9357de75.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


- 二级缓存

 核心：ehcache-1.5.0.jar

 依赖：

  backport-util-concurrent-2.1.jar

  commons-logging  (存在)

### 5.1.4整合包

- spring整合hibernate：spring orm
- struts 整合spring：struts2-spring-plugin-2.3.15.3.jar

删除重复jar包

![](http://upload-images.jianshu.io/upload_images/1540531-8cb7d822435f197d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 5.2spring整合hibernate：有hibernate.cfg.xml

### 5.2.1创建表

```mysql
create table t_user(
  id int primary key auto_increment,
  username varchar(50),
  password varchar(32),
  age int 
);
```

### 5.2.2PO 类

![](http://upload-images.jianshu.io/upload_images/1540531-194b9fb0e5df569b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

- javabean

```java
public class User {
	private Integer id;
	private String username;
	private String password;
	private Integer age;
	//set与get方法省略
}
```



- 映射文件

  ```xml-dtd
  <!DOCTYPE hibernate-mapping PUBLIC 
      "-//Hibernate/Hibernate Mapping DTD 3.0//EN"
      "http://www.hibernate.org/dtd/hibernate-mapping-3.0.dtd">
  <hibernate-mapping>
  	<class name="com.itheima.domain.User" table="t_user">
  		<id name="id">
  			<generator class="native"></generator>
  		</id>
  		<property name="username"></property>
  		<property name="password"></property>
  		<property name="age"></property>
  	</class>

  </hibernate-mapping>
  ```

  ​

### 5.2.3dao层

- spring提供HibernateTemplate 用于操作PO对象，类似Hibernate Session对象。

```java
public class UserDaoImpl implements UserDao {
	
	//需要spring注入模板
	private HibernateTemplate hibernateTemplate;
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	@Override
	public void save(User user) {
		this.hibernateTemplate.save(user);
	}

}
```

### 5.2.4service层

```java

public class UserServiceImpl implements UserService {

	private UserDao userDao;
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	@Override
	public void register(User user) {
		userDao.save(user);
	}

}
```

### 5.2.5hibernate.cfg.xml

```xml
<session-factory>
		<!-- 1基本4项 -->
		<property name="hibernate.connection.driver_class">com.mysql.jdbc.Driver</property>
		<property name="hibernate.connection.url">jdbc:mysql:///ee19_spring_day03</property>
		<property name="hibernate.connection.username">root</property>
		<property name="hibernate.connection.password">1234</property>
		
		<!-- 2 配置方言 -->
		<property name="hibernate.dialect">org.hibernate.dialect.MySQL5Dialect</property>
		
		<!-- 3 sql语句 -->
		<property name="hibernate.show_sql">true</property>
		<property name="hibernate.format_sql">true</property>
		
		<!-- 4 自动生成表(一般没用) -->
		<property name="hibernate.hbm2ddl.auto">update</property>
		
		<!-- 5本地线程绑定 -->
		<property name="hibernate.current_session_context_class">thread</property>
		
		<!-- 导入映射文件 -->
		<mapping resource="com/itheima/domain/User.hbm.xml"/>
	
	</session-factory>
```

### 5.2.6applicationContext.xml

#### 5.2.6.1添加命名空间

```xml-dtd
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans 
       					   http://www.springframework.org/schema/beans/spring-beans.xsd
       					   http://www.springframework.org/schema/tx 
       					   http://www.springframework.org/schema/tx/spring-tx.xsd
       					   http://www.springframework.org/schema/aop 
       					   http://www.springframework.org/schema/aop/spring-aop.xsd
       					   http://www.springframework.org/schema/context 
       					   http://www.springframework.org/schema/context/spring-context.xsd">


```



#### 5.2.6.2加载hibernate配置文件

```xml
<!-- 1 加载hibenrate.cfg.xml 获得SessionFactory 
		* configLocation确定配置文件位置
	-->
	<bean id="sessionFactory" class="org.springframework.orm.hibernate3.LocalSessionFactoryBean">
		<property name="configLocation" value="classpath:hibernate.cfg.xml"></property>
	</bean>
	
	<!-- 2创建模板 
		* 底层使用session，session 有sessionFactory获得
	-->
	<bean id="hibernateTemplate" class="org.springframework.orm.hibernate3.HibernateTemplate">
		<property name="sessionFactory" ref="sessionFactory"></property>
	</bean>

```



#### 5.2.6.3dao和service

```xml
<!-- 3 dao -->
	<bean id="userDao" class="com.itheima.dao.impl.UserDaoImpl">
		<property name="hibernateTemplate" ref="hibernateTemplate"></property>
	</bean>
	
	<!-- 4 service -->
	<bean id="userService" class="com.itheima.service.impl.UserServiceImpl">
		<property name="userDao" ref="userDao"></property>
	</bean>

```



#### 5.2.6.4事务管理

```xml
<!-- 5 事务管理 -->
	<!-- 5.1 事务管理器 ：HibernateTransactionManager -->
	<bean id="txManager" class="org.springframework.orm.hibernate3.HibernateTransactionManager" >
		<property name="sessionFactory" ref="sessionFactory"></property>
	</bean>
	<!-- 5.2 事务详情 ，给ABC进行具体事务设置 -->
	<tx:advice id="txAdvice" transaction-manager="txManager">
		<tx:attributes>
			<tx:method name="register"/>
		</tx:attributes>
	</tx:advice>
	<!-- 5.3 AOP编程，ABCD 筛选 ＡＢＣ  -->
	<aop:config>
		<aop:advisor advice-ref="txAdvice" pointcut="execution(* com.itheima.service..*.*(..))"/>
	</aop:config>

```



### 5.2.7测试

```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
public class TestApp {
	
	@Autowired
	private UserService userService;
	
	@Test
	public void demo01(){
		User user = new User();
		user.setUsername("jack");
		user.setPassword("1234");
		user.setAge(18);
		
		userService.register(user);
	}
}


```



## 5.3spring整合hibernate：没有hibernate.cfg.xml 【】

- 删除hibernate.cfg.xml文件，但需要保存文件内容，将其配置spring中
- 修改dao层，继承HibernateDaoSupport

### 5.3.1修改spring，配置SessionFactory

```xml-dtd
<!-- 1.1加载properties文件 -->
	<!-- 1.2 配置数据源 -->
	<bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
		<property name="driverClass" value="com.mysql.jdbc.Driver"></property>
		<property name="jdbcUrl" value="jdbc:mysql:///ee19_spring_day03"></property>
		<property name="user" value="root"></property>
		<property name="password" value="1234"></property>
	</bean>
	
	<!-- 1.3配置 LocalSessionFactoryBean，获得SessionFactory 
		* configLocation确定配置文件位置
			<property name="configLocation" value="classpath:hibernate.cfg.xml"></property>
		1)dataSource 数据源
		2)hibernateProperties hibernate其他配置项
		3) 导入映射文件
			mappingLocations ，确定映射文件位置，需要“classpath:” ,支持通配符 【】
				<property name="mappingLocations" value="classpath:com/itheima/domain/User.hbm.xml"></property>
				<property name="mappingLocations" value="classpath:com/itheima/domain/*.hbm.xml"></property>
			mappingResources ，加载执行映射文件，从src下开始 。不支持通配符*
				<property name="mappingResources" value="com/itheima/domain/User.hbm.xml"></property>
			mappingDirectoryLocations ，加载指定目录下的，所有配置文件
				<property name="mappingDirectoryLocations" value="classpath:com/itheima/domain/"></property>
			mappingJarLocations ， 从jar包中获得映射文件
	-->
	<bean id="sessionFactory" class="org.springframework.orm.hibernate3.LocalSessionFactoryBean">
		<property name="dataSource" ref="dataSource"></property>
		<property name="hibernateProperties">
			<props>
				<prop key="hibernate.dialect">org.hibernate.dialect.MySQL5Dialect</prop>
				<prop key="hibernate.show_sql">true</prop>
				<prop key="hibernate.format_sql">true</prop>
				<prop key="hibernate.hbm2ddl.auto">update</prop>
				<prop key="hibernate.current_session_context_class">thread</prop>
			</props>
		</property>
		<property name="mappingLocations" value="classpath:com/itheima/domain/*.hbm.xml"></property>
	</bean>
```

### 5.3.2修改dao，使用HibernateDaoSupport

- 继承HibernateDaoSupport

  ```java
  // 底层需要SessionFactory，自动创建HibernateTemplate模板
  public class UserDaoImpl extends HibernateDaoSupport implements UserDao {

  	@Override
  	public void save(User user) {
  		this.getHibernateTemplate().save(user);
  	}

  }

  ```

  ​


- spring 删除模板，给dao注入SessionFactory

  ```xml-dtd
  	<!-- 3 dao -->
  	<bean id="userDao" class="com.itheima.dao.impl.UserDaoImpl">
  		<property name="sessionFactory" ref="sessionFactory"></property>
  	</bean>
  ```

![](http://upload-images.jianshu.io/upload_images/1540531-0658b8206be4f110.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 5.4struts整合spring：spring创建action

1编写action类，并将其配置给spring ，spring可以注入service

2编写struts.xml

3表单jsp页面

4web.xml 配置

​	 1.确定配置文件contextConfigLocation

​	 2.配置监听器ContextLoaderListener

​	 3.配置前端控制器StrutsPrepareAndExecuteFitler

### 5.4.1action类

- 通用

  ```java
  public class UserAction extends ActionSupport implements ModelDriven<User> {

  	//1 封装数据
  	private User user = new User();

  	@Override
  	public User getModel() {
  		return user;
  	}
  	
  	//2 service
  	private UserService userService;
  	public void setUserService(UserService userService) {
  		this.userService = userService;
  	}

  ```

  ​


- 功能

  ```java
  	/**
  	 * 注册
  	 * @return
  	 */
  	public String register(){
  		userService.register(user);
  		return "success";
  	}

  ```

  ​

### 5.4.2spring配置

```xml
<!-- 6 配置action -->
	<bean id="userAction" class="com.itheima.web.action.UserAction" scope="prototype">
		<property name="userService" ref="userService"></property>
	</bean>

```



### 5.4.3struts配置

```xml
<struts>
	<!-- 开发模式 -->
    <constant name="struts.devMode" value="true" />

    <package name="default" namespace="/" extends="struts-default">
    	<!-- 底层自动从spring容器中通过名称获得内容， getBean("userAction") -->
    	<action name="userAction_*" class="userAction" method="{1}">
    		<result name="success">/messag.jsp</result>
    	</action>
    </package>
</struts>

```



### 5.4.4jsp表单

```jsp
<form action="${pageContext.request.contextPath}/userAction_register" method="post">
		用户名：<input type="text" name="username"/> <br/>
		密码：<input type="password" name="password"/> <br/>
		年龄：<input type="text" name="age"/> <br/>
		<input type="submit" />
</form>
```



### 5.4.5配置web.xml

```xml
<!-- 1 确定spring xml位置 -->
  <context-param>
  	<param-name>contextConfigLocation</param-name>
  	<param-value>classpath:applicationContext.xml</param-value>
  </context-param>
  <!-- 2 spring监听器 -->
  <listener>
  	<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
  </listener>
  <!-- 3 struts 前端控制器 -->
  <filter>
  	<filter-name>struts2</filter-name>
  	<filter-class>org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter</filter-class>
  </filter>
  <filter-mapping>
  	<filter-name>struts2</filter-name>
  	<url-pattern>/*</url-pattern>
  </filter-mapping>

```



## 5.5struts整合spring：struts创建action 【】

- 删除spring action配置

- struts &lt;action class=&quot;全限定类名&quot;>;

  ```xml
  <package name="default" namespace="/" extends="struts-default">
      	<!-- 底层自动从spring容器中通过名称获得内容， getBean("userAction") -->
      	<action name="userAction_*" class="com.itheima.web.action.UserAction" method="{1}">
      		<result name="success">/messag.jsp</result>
      	</action>
  </package>
  ```

  ​


- 要求：Action类中，必须提供service名称与spring配置文件一致。（如果名称一样，将自动注入）

    ![](http://upload-images.jianshu.io/upload_images/1540531-8ecbaae5d7911361.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

分析：

1. struts 配置文件

 default.properties  ,常量配置文件

 struts-default.xml ，默认核心配置文件

 struts-plugins.xml ，插件配置文件

 struts.xml，自定义核心配置文件

 常量的使用，后面配置项，将覆盖前面的。

2.default.properties  ，此配置文件中确定按照【名称】自动注入

 /org/apache/struts2/default.properties

![](http://upload-images.jianshu.io/upload_images/1540531-464953c6a0f8e41a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


3. struts-plugins.xml ,struts整合spring

   ![](http://upload-images.jianshu.io/upload_images/1540531-13c6d4c659c94896.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


   ```xml
   <constant name="struts.objectFactory" value="spring" />
   ```

 struts的action将有spring创建

总结， **之后action有spring创建，并按照名称自动注入**



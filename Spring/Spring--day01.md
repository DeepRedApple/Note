# 1spring框架概述

## 1.1什么是spring

- .Spring是一个开源框架，Spring是于2003 年兴起的一个轻量级的Java 开发框架，由Rod Johnson 在其著作Expert One-On-One J2EE Development and Design中阐述的部分理念和原型衍生而来。它是为了解决企业应用开发的复杂性而创建的。框架的主要优势之一就是其分层架构，分层架构允许使用者选择使用哪一个组件，同时为J2EE 应用程序开发提供集成的框架。Spring使用基本的JavaBean来完成以前只可能由EJB完成的事情。然而，Spring的用途不仅限于服务器端的开发。从简单性、可测试性和松耦合的角度而言，任何Java应用都可以从Spring中受益。Spring的核心是控制反转（IoC）和面向切面（AOP）。简单来说，Spring是一个分层的JavaSE/EE full-stack(一站式) 轻量级开源框架。
- .轻量级：与EJB对比，依赖资源少，销毁的资源少。
- .分层：一站式，每一个层都提供的解决方案

 web层：struts，spring-MVC

 service层：spring

 dao层：hibernate，mybatis ，jdbcTemplate  --&gt; spring-data

## 1.2spring由来

- .Expert One-to-One J2EE Design and Development
- .Expert One-to-One J2EE Development without EJB

## 1.3spring核心

- .Spring的核心是 **控制反转（IoC）** 和 **面向切面（AOP）**

## 1.4spring优点

|
- 方便解耦，简化开发  （高内聚低耦合）
  - Spring就是一个大工厂（容器），可以将所有对象创建和依赖关系维护，交给Spring管理
  - spring工厂是用于生成bean
- AOP编程的支持
  - Spring提供面向切面编程，可以方便的实现对程序进行权限拦截、运行监控等功能
- 声明式事务的支持
  - 只需要通过配置就可以完成对事务的管理，而无需手动编程
- 方便程序的测试
  - Spring对Junit4支持，可以通过注解方便的测试Spring程序
- 方便集成各种优秀框架
  - Spring不排斥各种优秀的开源框架，其内部提供了对各种优秀框架（如：Struts、Hibernate、MyBatis、Quartz等）的直接支持
- 降低JavaEE API的使用难度
  - Spring 对JavaEE开发中非常难用的一些API（JDBC、JavaMail、远程调用等），都提供了封装，使这些API应用难度大大降低

## 1.5spring体系结构

![](http://upload-images.jianshu.io/upload_images/1540531-d1670e5393fc0637.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

 核心容器：beans、core、context、expression

# 2入门案例：IoC【掌握】

## 2.1导入jar包

- .4 + 1  ：4个核心（beans、core、context、expression）+ 1个依赖（commons-loggins...jar）

![](http://upload-images.jianshu.io/upload_images/1540531-d8c344b281df7dea.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](http://upload-images.jianshu.io/upload_images/1540531-29fb27ee41cda737.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 2.2目标类

- .提供UserService接口和实现类
- .获得UserService实现类的实例

 之前开发中，直接new一个对象即可。

 学习spring之后，将由Spring创建对象实例--&gt; IoC 控制反转（Inverse of  Control）

  之后需要实例对象时，从spring工厂（容器）中获得，需要将实现类的全限定名称配置到xml文件中

```java
public interface UserService {
	
	public void addUser();

}
public class UserServiceImpl implements UserService {

	@Override
	public void addUser() {
		System.out.println("a_ico add user");
	}

}
```

## 2.3配置文件

- .位置：任意，开发中一般在classpath下（src）
- .名称：任意，开发中常用applicationContext.xml
- .内容：添加schema约束

 约束文件位置：spring-framework-3.2.0.RELEASE\docs\spring-framework-reference\html\ xsd-config.html

```xml-dtd
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans 
       					   http://www.springframework.org/schema/beans/spring-beans.xsd">
	<!-- 配置service 
		<bean> 配置需要创建的对象
			id ：用于之后从spring容器获得实例时使用的
			class ：需要创建实例的全限定类名
	-->
	<bean id="userServiceId" class="com.itheima.a_ioc.UserServiceImpl"></bean>
</beans>
```


## 2.4测试

```Java
@Test
public void demo02(){
    //从spring容器获得
    //1 获得容器
    String xmlPath = "com/itheima/a_ioc/beans.xml";
    ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlPath);
    //2获得内容 --不需要自己new，都是从spring容器获得
    UserService userService = (UserService) applicationContext.getBean("userServiceId");
    userService.addUser();

}
```

# 3入门案例：DI【掌握】

- .DI Dependency Injection ,依赖注入

 is a ：是一个，继承。

 has a：有一个，成员变量，依赖。

  class B {	

​	 private A a;   //B类依赖A类

   }


 依赖：一个对象需要使用另一个对象

 注入：通过setter方法进行另一个对象实例设置。

- .例如：

 class BookServiceImpl{

​	    //之前开发：接口= 实现类  （service和dao耦合）


​       	  //private BookDao bookDao = new BookDaoImpl();

​	   //spring之后（解耦：service实现类使用dao接口，不知道具体的实现类）

​	  private BookDao bookDao;

​	  setter方法

   }

 模拟spring执行过程

 创建service实例：BookService bookService = new BookServiceImpl()  --&gt;IoC  &lt;bean&gt;

 创建dao实例：BookDao bookDao = new BookDaoImple()    --&gt;IoC

 将dao设置给service：bookService.setBookDao(bookDao);    --&gt;DI   &lt;property&gt;

## 3.1目标类

- .创建BookService接口和实现类
- .创建BookDao接口和实现类
- .将dao和service配置xml文件
- .使用api测试

### 3.1.1dao

```java
public interface BookDao {
	
	public void addBook();

}
public class BookDaoImpl implements BookDao {

	@Override
	public void addBook() {
		System.out.println("di  add book");
	}

}
```

### 3.1.2service

![](http://upload-images.jianshu.io/upload_images/1540531-cd8e3077c6f6bd3f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```java
public interface BookService {

	public abstract void addBook();

}
public class BookServiceImpl implements BookService {
	
	// 方式1：之前，接口=实现类
//	private BookDao bookDao = new BookDaoImpl();
	// 方式2：接口 + setter
	private BookDao bookDao;
	public void setBookDao(BookDao bookDao) {
		this.bookDao = bookDao;
	}
	
	@Override
	public void addBook(){
		this.bookDao.addBook();
	}

}
```

## 3.2配置文件

![](http://upload-images.jianshu.io/upload_images/1540531-817a97f947c4701b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```xml-dtd
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans 
       					   http://www.springframework.org/schema/beans/spring-beans.xsd">
	<!-- 
	模拟spring执行过程
		创建service实例：BookService bookService = new BookServiceImpl()	IoC  <bean>
		创建dao实例：BookDao bookDao = new BookDaoImpl()			IoC
		将dao设置给service：bookService.setBookDao(bookDao);		DI   <property>
		
		<property> 用于进行属性注入
			name： bean的属性名，通过setter方法获得
				setBookDao ##> BookDao  ##> bookDao
			ref ：另一个bean的id值的引用
	 -->

	<!-- 创建service -->
	<bean id="bookServiceId" class="com.itheima.b_di.BookServiceImpl">
		<property name="bookDao" ref="bookDaoId"></property>
	</bean>
	
	<!-- 创建dao实例 -->
	<bean id="bookDaoId" class="com.itheima.b_di.BookDaoImpl"></bean>
	

</beans>
```

## 3.3测试

```java
	@Test
	public void demo01(){
		//从spring容器获得
		String xmlPath = "com/itheima/b_di/beans.xml";
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlPath);
		BookService bookService = (BookService) applicationContext.getBean("bookServiceId");
		
		bookService.addBook();
		
	}

```



# 4myeclipse schema xml提示

- 步骤一：确定xsd文件位置

 spring-framework-3.2.0.RELEASE\schema\beans

![](http://upload-images.jianshu.io/upload_images/1540531-6022764afdca9ddb.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


- 步骤二：复制路径

    ![](http://upload-images.jianshu.io/upload_images/1540531-98e4ed5fee09c764.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

- 步骤三：搜索&quot;xml catalog&quot;

    ![](http://upload-images.jianshu.io/upload_images/1540531-85602a289ce633e3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

- 步骤四：添加约束提示

    ![](http://upload-images.jianshu.io/upload_images/1540531-e7ae9b8fb55b2061.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

# 5核心API

- api整体了解，之后不使用，在学习过程需要。

![](http://upload-images.jianshu.io/upload_images/1540531-c8cbcf60f12d541f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

- BeanFactory ：这是一个工厂，用于生成任意bean。

 采取延迟加载，第一次getBean时才会初始化Bean

- ApplicationContext：是BeanFactory的子接口，功能更强大。（国际化处理、事件传递、Bean自动装配、各种不同应用层的Context实现）。当配置文件被加载，就进行对象实例化。

   ClassPathXmlApplicationContext 用于加载classpath（类路径、src）下的xml

  加载xml运行时位置--&gt; /WEB-INF/classes/...xml

  FileSystemXmlApplicationContext 用于加载指定盘符下的xml

  加载xml运行时位置--&gt; /WEB-INF/...xml

  通过java web ServletContext.getRealPath() 获得具体盘符

  ```java
  	@Test
  	public void demo02(){
  		//使用BeanFactory  --第一次条用getBean实例化
  		String xmlPath = "com/itheima/b_di/beans.xml";
  		
  		BeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource(xmlPath));
  		
  		BookService bookService = (BookService) beanFactory.getBean("bookServiceId");
  		
  		bookService.addBook();
  		
  	}

  ```

  ​

# 6装配Bean 基于XML

## 6.1实例化方式

- 3种bean实例化方式：默认构造、静态工厂、实例工厂

### 6.1.1默认构造

```xml
<bean id="" class="">  <--必须提供默认构造-->
```



### 6.1.2静态工厂

- 常用与spring整合其他框架（工具）
- 静态工厂：用于生成实例对象，所有的方法必须是static

```xml
<bean id=""  class="工厂全限定类名"  factory-method="静态方法">
```



#### 6.1.2.1工厂

```java
public class MyBeanFactory {
	
	/**
	 * 创建实例
	 * @return
	 */
	public static UserService createService(){
		return new UserServiceImpl();
	}
}

```



#### 6.1.2.2spring配置

```xml
	<!-- 将静态工厂创建的实例交予spring 
		class 确定静态工厂全限定类名
		factory-method 确定静态方法名
	-->
	<bean id="userServiceId" class="com.itheima.c_inject.b_static_factory.MyBeanFactory" factory-method="createService"></bean>

```



### 6.1.3实例工厂

- .实例工厂：必须先有工厂实例对象，通过实例对象创建对象。提供所有的方法都是&quot;非静态&quot;的。

#### 6.1.3.1工厂

```java
/**
 * 实例工厂,所有方法非静态
 *
 */
public class MyBeanFactory {
	
	/**
	 * 创建实例
	 * @return
	 */
	public UserService createService(){
		return new UserServiceImpl();
	}

}

```



#### 6.1.3.2spring配置

 ![](http://upload-images.jianshu.io/upload_images/1540531-de4158909633dff7.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```xml
	<!-- 创建工厂实例 -->
	<bean id="myBeanFactoryId" class="com.itheima.c_inject.c_factory.MyBeanFactory"></bean>
	<!-- 获得userservice 
		* factory-bean 确定工厂实例
		* factory-method 确定普通方法
	-->
	<bean id="userServiceId" factory-bean="myBeanFactoryId" factory-method="createService"></bean>
```



## 6.2Bean种类

- 普通bean：之前操作的都是普通bean。&lt;bean id=""; class=&quot;A">; ，spring直接创建A实例，并返回
- FactoryBean：是一个特殊的bean，具有工厂生成对象能力，只能生成特定的对象。

 bean必须使用FactoryBean接口，此接口提供方法getObject() 用于获得特定bean。

 &lt;bean id=""; class=&quot;FB">; 先创建FB实例，使用调用getObject()方法，并返回方法的返回值

  FB fb = new FB();

  return fb.getObject();

- BeanFactory 和FactoryBean 对比？

 BeanFactory：工厂，用于生成任意bean。

 FactoryBean：特殊bean，用于生成另一个特定的bean。例如：ProxyFactoryBean ，此工厂bean用于生产代理。&lt;bean id=""; class=&quot;....ProxyFactoryBean"; 获得代理对象实例。AOP使用

## 6.3作用域

- 作用域：用于确定spring创建bean实例个数

    ​

    ![](http://upload-images.jianshu.io/upload_images/1540531-1311612b58fc74a0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

- 取值：

 singleton 单例，默认值。

 prototype 多例，每执行一次getBean将获得一个实例。例如：struts整合spring，配置action多例。

- 配置信息

```xml
<bean id="" class=""  scope="">
```

![](http://upload-images.jianshu.io/upload_images/1540531-457b7722dacca253.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```xml
<bean id="userServiceId" class="com.itheima.d_scope.UserServiceImpl" 
		scope="prototype" ></bean>
```



## 6.4生命周期

### 6.4.1初始化和销毁

- 目标方法执行前后执行后，将进行初始化或销毁。

```xml
<bean id="" class="" init-method="初始化方法名称"  destroy-method="销毁的方法名称">
```

#### 6.4.1.1目标类

```java
public class UserServiceImpl implements UserService {

	@Override
	public void addUser() {
		System.out.println("e_lifecycle add user");
	}
	
	public void myInit(){
		System.out.println("初始化");
	}
	public void myDestroy(){
		System.out.println("销毁");
	}

}

```



#### 6.4.1.2spring配置

```xml
<!--  
		init-method 用于配置初始化方法,准备数据等
		destroy-method 用于配置销毁方法,清理资源等
	-->
	<bean id="userServiceId" class="com.itheima.e_lifecycle.UserServiceImpl" 
		init-method="myInit" destroy-method="myDestroy" ></bean>

```



#### 6.4.1.3测试

```java
	@Test
	public void demo02() throws Exception{
		//spring 工厂
		String xmlPath = "com/itheima/e_lifecycle/beans.xml";
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlPath);
		UserService userService = (UserService) applicationContext.getBean("userServiceId");
		userService.addUser();
		
		//要求：1.容器必须close，销毁方法执行; 2.必须是单例的
//		applicationContext.getClass().getMethod("close").invoke(applicationContext);
		// * 此方法接口中没有定义，实现类提供
		applicationContext.close();
		
	}

```



### 6.4.2BeanPostProcessor 后处理Bean

- spring 提供一种机制，只要实现此接口BeanPostProcessor，并将实现类提供给spring容器，spring容器将自动执行，在初始化方法前执行before()，在初始化方法后执行after() 。配置&lt;bean class="";&gt;

    ​

    ![](http://upload-images.jianshu.io/upload_images/1540531-27a3ccb5eb49ebc9.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

- Factory hook(勾子) that allows for custom modification of new bean instances, e.g. checking for marker interfaces or wrapping them with proxies.

- spring提供工厂勾子，用于修改实例对象，可以生成代理对象，是AOP底层。

模拟

A a =new A();

a = B.before(a)   --&gt; 将a的实例对象传递给后处理bean，可以生成代理对象并返回。

a.init();

a = B.after(a);

a.addUser();  //生成代理对象，目的在目标方法前后执行（例如：开启事务、提交事务）

a.destroy()

#### 6.4.2.1编写实现类

```java
public class MyBeanPostProcessor implements BeanPostProcessor {

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("前方法 ： " + beanName);
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(final Object bean, String beanName) throws BeansException {
		System.out.println("后方法 ： " + beanName);
		// bean 目标对象
		// 生成 jdk 代理
		return Proxy.newProxyInstance(
					MyBeanPostProcessor.class.getClassLoader(), 
					bean.getClass().getInterfaces(), 
					new InvocationHandler(){
						@Override
						public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
							
							System.out.println("------开启事务");
							
							//执行目标方法
							Object obj = method.invoke(bean, args);
							
							System.out.println("------提交事务");
							return obj;
						}});
	}
}

```



#### 6.4.2.2配置

```xml
<!-- 将后处理的实现类注册给spring -->
<bean class="com.itheima.e_lifecycle.MyBeanPostProcessor"></bean>

```



- 问题1：后处理bean作用某一个目标类，还是所有目标类？

 所有

- 问题2：如何只作用一个？

 通过&quot;参数2&quot;beanName进行控制

## 6.5属性依赖注入

- 依赖注入方式：手动装配和自动装配
- 手动装配：一般进行配置信息都采用手动

 基于xml装配：构造方法、setter方法

 基于注解装配：

- 自动装配：struts和spring 整合可以自动装配

 byType：按类型装配

 byName：按名称装配

 constructor构造装配，

 auto：不确定装配。

### 6.5.1构造方法

#### 6.5.1.1目标类

```java
public class User {
	
	private Integer uid;
	private String username;
	private Integer age;
	
	public User(Integer uid, String username) {
		super();
		this.uid = uid;
		this.username = username;
	}
	
	public User(String username, Integer age) {
		super();
		this.username = username;
		this.age = age;
	}
	

```



#### 6.5.1.2spring配置

```xml
	<!-- 构造方法注入 
		* <constructor-arg> 用于配置构造方法一个参数argument
			name ：参数的名称
			value：设置普通数据
			ref：引用数据，一般是另一个bean id值
			
			index ：参数的索引号，从0开始 。如果只有索引，匹配到了多个构造方法时，默认使用第一个。
			type ：确定参数类型
		例如：使用名称name
			<constructor-arg name="username" value="jack"></constructor-arg>
			<constructor-arg name="age" value="18"></constructor-arg>
		例如2：【类型type 和  索引 index】
			<constructor-arg index="0" type="java.lang.String" value="1"></constructor-arg>
			<constructor-arg index="1" type="java.lang.Integer" value="2"></constructor-arg>
	-->
	<bean id="userId" class="com.itheima.f_xml.a_constructor.User" >
		<constructor-arg index="0" type="java.lang.String" value="1"></constructor-arg>
		<constructor-arg index="1" type="java.lang.Integer" value="2"></constructor-arg>
	</bean>

```



### 6.5.2setter方法

```xml
<!-- setter方法注入 
		* 普通数据 
			<property name="" value="值">
			等效
			<property name="">
				<value>值
		* 引用数据
			<property name="" ref="另一个bean">
			等效
			<property name="">
				<ref bean="另一个bean"/>
	
	-->
	<bean id="personId" class="com.itheima.f_xml.b_setter.Person">
		<property name="pname" value="阳志"></property>
		<property name="age">
			<value>1234</value>
		</property>
		
		<property name="homeAddr" ref="homeAddrId"></property>
		<property name="companyAddr">
			<ref bean="companyAddrId"/>
		</property>
	</bean>
	
	<bean id="homeAddrId" class="com.itheima.f_xml.b_setter.Address">
		<property name="addr" value="阜南"></property>
		<property name="tel" value="911"></property>
	</bean>
	<bean id="companyAddrId" class="com.itheima.f_xml.b_setter.Address">
		<property name="addr" value="北京八宝山"></property>
		<property name="tel" value="120"></property>
	</bean>

```



### 6.5.3P命令空间[了解]

- 对&quot;setter方法注入&quot;进行简化，替换&lt;property name=&quot;属性名";，而是在

 &lt;bean p:属性名=&quot;普通值&quot;  p:属性名-ref=&quot;引用值";

- p命名空间使用前提，必须添加命名空间

    ![](http://upload-images.jianshu.io/upload_images/1540531-2959d516eab30b07.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```xml
	<bean id="personId" class="com.itheima.f_xml.c_p.Person" 
		p:pname="禹太璞" p:age="22" 
		p:homeAddr-ref="homeAddrId" p:companyAddr-ref="companyAddrId">
	</bean>
	
	<bean id="homeAddrId" class="com.itheima.f_xml.c_p.Address"
		p:addr="DG" p:tel="东莞">
	</bean>
	<bean id="companyAddrId" class="com.itheima.f_xml.c_p.Address"
		p:addr="DG" p:tel="岛国">
	</bean>

```



### 6.5.4SpEL[了解]

- 对&lt;property&gt;进行统一编程，所有的内容都使用value

```xml
<property name="" value="#{表达式}">
```

\#{123}、#{"jack"}：数字、字符串

\#{beanId} ：另一个bean引用

\#{beanId.propName} ：操作数据

\#{beanId.toString()} ：执行方法

\#{T(类).字段|方法} ：静态方法或字段

```xml
	<!-- 
		<property name="cname" value="#{'jack'}"></property>
		<property name="cname" value="#{customerId.cname.toUpperCase()}"></property>
			通过另一个bean，获得属性，调用的方法
		<property name="cname" value="#{customerId.cname?.toUpperCase()}"></property>
			?.  如果对象不为null，将调用方法
	-->
	<bean id="customerId" class="com.itheima.f_xml.d_spel.Customer" >
		<property name="cname" value="#{customerId.cname?.toUpperCase()}"></property>
		<property name="pi" value="#{T(java.lang.Math).PI}"></property>
	</bean>
```

- 阅读：

    ![](http://upload-images.jianshu.io/upload_images/1540531-a098e3bc455942ae.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### 6.5.5集合注入

```xml
<!-- 
		集合的注入都是给<property>添加子标签
			数组：<array>
			List：<list>
			Set：<set>
			Map：<map> ，map存放k/v 键值对，使用<entry>描述
			Properties：<props>  <prop key=""></prop>  【】
			
		普通数据：<value>
		引用数据：<ref>
	-->
	<bean id="collDataId" class="com.itheima.f_xml.e_coll.CollData" >
		<property name="arrayData">
			<array>
				<value>DS</value>
				<value>DZD</value>
				<value>屌丝</value>
				<value>屌中屌</value>
			</array>
		</property>
		
		<property name="listData">
			<list>
				<value>于嵩楠</value>
				<value>曾卫</value>
				<value>杨煜</value>
				<value>曾小贤</value>
			</list>
		</property>
		
		<property name="setData">
			<set>
				<value>停封</value>
				<value>薄纸</value>
				<value>关系</value>
			</set>
		</property>
		
		<property name="mapData">
			<map>
				<entry key="jack" value="杰克"></entry>
				<entry>
					<key><value>rose</value></key>
					<value>肉丝</value>
				</entry>
			</map>
		</property>
		
		<property name="propsData">
			<props>
				<prop key="高富帅">嫐</prop>
				<prop key="白富美">嬲</prop>
				<prop key="男屌丝">挊</prop>
			</props>
		</property>
	</bean>
```

# 7装配Bean 基于注解

- 注解：就是一个类，使用@注解名称
- 开发中：使用注解取代xml配置文件。


  1、@Component取代&lt;bean class=""&gt;

   @Component(&quot;id&quot;) 取代&lt;bean id=""; class=""&gt;

​	2、web开发，提供3个@Component注解衍生注解（功能一样）取代&lt;bean class=&quot;&quot;&gt;

​	 @Repository ：dao层

​	 @Service：service层

​	 @Controller：web层

​	3、依赖注入 ，给私有字段设置，也可以给setter方法设置

​	 普通值：@Value(&quot;&quot;)

​	 引用值：

​	  方式1：按照【类型】注入

​	   @Autowired

​	  方式2：按照【名称】注入1

​	   @Autowired

​	   @Qualifier(&quot;名称&quot;)

​	  方式3：按照【名称】注入2

​	   @Resource(&quot;名称&quot;)

​	4、生命周期

​	 初始化：@PostConstruct

​	 销毁：@PreDestroy

​	5、作用域

​	 @Scope(&quot;prototype&quot;) 多例

- 注解使用前提，添加命名空间，让spring扫描含有注解类

  ​	 ![](http://upload-images.jianshu.io/upload_images/1540531-5954860ef18db1a1.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```xml
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans 
       					   http://www.springframework.org/schema/beans/spring-beans.xsd
       					   http://www.springframework.org/schema/context 
       					   http://www.springframework.org/schema/context/spring-context.xsd">
	<!-- 组件扫描，扫描含有注解的类 -->
	<context:component-scan base-package="com.itheima.g_annotation.a_ioc"></context:component-scan>
</beans>

```


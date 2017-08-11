# 1spring day01回顾

## 1.1编写流程(基于xml)

1导入jar包：4+1  --&gt; beans/core/context/expression  | commons-logging

2编写目标类：dao和service

3spring配置文件

```
IoC：<bean id="" class="" >
DI：<bean> <property name="" value="" | ref="">
实例化方式：
  默认构造
  静态工厂：<bean id="" class="工厂类" factory-method="静态方法">
  实例工厂：<bean id="工厂id" class="工厂类">  <bean id="" factory-bean="工厂id" factory-method="方法">
作用域：<bean id="" class="" scope="singleton | prototype">
生命周期：<bean id="" class="" init-method="" destroy-method="">
  后处理bean  BeanPostProcessor接口，<bean class="注册"> ，对容器中所有的bean都生效
属性注入
  构造方法注入：<bean><constructor-arg index="" type="" >
  setter方法注入：<bean><property>
  p命名空间：简化<property>   <bean p:属性名="普通值"  p:属性名-ref="引用值">  注意声明命名空间
  SpEL：<property name="" value="#{表达式}">
     #{123}  #{'abc'}
     #{beanId.propName?.methodName()}
     #{T(类).静态方法|字段}
  集合
     数组<array>
     List <list>
     Set <set>
     Map <map><entry key="" value="">
     Properties <props><prop key="">....

```

IoC：

4核心api

​	 BeanFactory，延迟实例化bean，第一次调用getBean

​	 ApplicationContext 一般常用，功能更强

 	 ClassPathXmlApplicationContext 加载classpath xml文件

​	  FileSystemXmlApplicationContext 加载指定盘符文件，ServletContext.getRealPath()

## 1.2后处理bean 对一个生效

```java
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		if("userServiceId".equals(beanName)){
			System.out.println("前方法 ： " + beanName);
		}
		return bean;
	}

```



## 1.3注解

1.扫描含有注解的类

```xml
	<context:component-scan base-package="....">
```

2.常见的注解

 @Component  组件，任意bean

 WEB

  @Controller  web层

  @Service service层

  @Repository dao层

 注入  --&gt; 字段或setter方法

  普通值：@Value

  引用值：

   类型：@Autowired

   名称1：@Autowired  @Qualifier(&quot;名称&quot;)

   名称2：@Resource(&quot;名称&quot;)

 作用域：@Scope(&quot;prototype&quot;)

 生命周期：

  初始化：@PostConstruct

  销毁方法：@PreDestroy

## 1.4注解和xml混合使用

1将所有的bean都配置xml中

```xml
	<context:component-scan base-package="....">
```

2将所有的依赖都使用注解

 @Autowired

 默认不生效。为了生效，需要在xml配置：&lt;context:annotation-config&gt;

总结：

​	注解1：&lt;context:component-scan base-package="";

​	注解2：&lt;context:annotation-config&gt;

1.一般情况两个注解不一起使用。

2. &quot;注解1&quot;扫描含有注解（@Component 等）类，注入注解自动生效。

 &quot;注解2&quot;只在xml和注解（注入）混合使用时，使注入注解生效。



# 2AOP

## 2.1AOP介绍

### 2.1.1什么是AOP

- 在软件业，AOP为Aspect Oriented Programming的缩写，意为：面向切面编程，通过预编译方式和运行期动态代理实现程序功能的统一维护的一种技术。AOP是OOP（面向对象编程）的延续，是软件开发中的一个热点，也是Spring框架中的一个重要内容，是函数式编程的一种衍生范型。利用AOP可以对业务逻辑的各个部分进行隔离，从而使得业务逻辑各部分之间的耦合度降低，提高程序的可重用性，同时提高了开发的效率。
- AOP采取 **横向抽取** 机制，取代了传统 **纵向继承** 体系重复性代码
- 经典应用：事务管理、性能监视、安全检查、缓存、日志等
- Spring AOP使用纯Java实现，不需要专门的编译过程和类加载器，在运行期通过代理方式向目标类织入增强代码
- AspectJ是一个基于Java语言的 **AOP框架** ，Spring2.0开始，Spring AOP引入对Aspect的支持，AspectJ扩展了Java语言，提供了一个专门的编译器，在编译时提供横向代码的织入



### 2.1.2AOP实现原理

- aop底层将采用代理机制进行实现。
- 接口+ 实现类：spring采用jdk 的动态代理Proxy。
- 实现类：spring 采用cglib字节码增强。



### 2.1.3AOP术语【掌握】

​	1、target：目标类，需要被代理的类。例如：UserService

​	2、Joinpoint(连接点):所谓连接点是指那些可能被拦截到的方法。例如：所有的方法

​	3、PointCut 切入点：已经被增强的连接点。例如：addUser()

​	4、advice 通知/增强，增强代码。例如：after、before

​	5、Weaving(织入):是指把增强advice应用到目标对象target来创建新的代理对象proxy的过程.

​	6、proxy 代理类

​	7、Aspect(切面): 是切入点pointcut和通知advice的结合

​	 一个线是一个特殊的面。

​	 一个切入点和一个通知，组成成一个特殊的面。

![](http://upload-images.jianshu.io/upload_images/1540531-7c2a566d98289571.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 2.2手动方式

### 2.2.1JDK动态代理

- JDK动态代理对&quot;装饰者&quot;设计模式简化。使用前提：必须有接口

  1.目标类：接口+ 实现类

  2.切面类：用于存通知MyAspect

  3.工厂类：编写工厂生成代理

  4.测试

![](http://upload-images.jianshu.io/upload_images/1540531-e0f1e6638608ded0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

#### 2.2.1.1目标类

```java
public interface UserService {
	
	public void addUser();
	public void updateUser();
	public void deleteUser();

}

```



#### 2.2.1.2切面类

```java
public class MyAspect {
	
	public void before(){
		System.out.println("鸡首");
	}
	
	public void after(){
		System.out.println("牛后");
	}

}


```



#### 2.2.1.3工厂

```java
public class MyBeanFactory {
	
	public static UserService createService(){
		//1 目标类
		final UserService userService = new UserServiceImpl();
		//2切面类
		final MyAspect myAspect = new MyAspect();
		/* 3 代理类：将目标类（切入点）和 切面类（通知） 结合 --> 切面
		 * 	Proxy.newProxyInstance
		 * 		参数1：loader ，类加载器，动态代理类 运行时创建，任何类都需要类加载器将其加载到内存。
		 * 			一般情况：当前类.class.getClassLoader();
		 * 					目标类实例.getClass().get...
		 * 		参数2：Class[] interfaces 代理类需要实现的所有接口
		 * 			方式1：目标类实例.getClass().getInterfaces()  ;注意：只能获得自己接口，不能获得父元素接口
		 * 			方式2：new Class[]{UserService.class}   
		 * 			例如：jdbc 驱动  --> DriverManager  获得接口 Connection
		 * 		参数3：InvocationHandler  处理类，接口，必须进行实现类，一般采用匿名内部
		 * 			提供 invoke 方法，代理类的每一个方法执行时，都将调用一次invoke
		 * 				参数31：Object proxy ：代理对象
		 * 				参数32：Method method : 代理对象当前执行的方法的描述对象（反射）
		 * 					执行方法名：method.getName()
		 * 					执行方法：method.invoke(对象，实际参数)
		 * 				参数33：Object[] args :方法实际参数
		 * 
		 */
		UserService proxService = (UserService)Proxy.newProxyInstance(
								MyBeanFactory.class.getClassLoader(), 
								userService.getClass().getInterfaces(), 
								new InvocationHandler() {
									
									@Override
									public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
										
										//前执行
										myAspect.before();
										
										//执行目标类的方法
										Object obj = method.invoke(userService, args);
										
										//后执行
										myAspect.after();
										
										return obj;
									}
								});
		
		return proxService;
	}

}

```



#### 2.2.1.4测试

```java
	@Test
	public void demo01(){
		UserService userService = MyBeanFactory.createService();
		userService.addUser();
		userService.updateUser();
		userService.deleteUser();
	}
```

### 2.2.2CGLIB字节码增强

- 没有接口，只有实现类。
- 采用字节码增强框架cglib，在运行时创建目标类的子类，从而对目标类进行增强。
- 导入jar包：

 自己导包（了解）：

  核心：hibernate-distribution-3.6.10.Final\lib\bytecode\cglib\cglib-2.2.jar

  依赖：struts-2.3.15.3\apps\struts2-blank\WEB-INF\lib\asm-3.3.jar

 spring-core..jar 已经整合以上两个内容

![](http://upload-images.jianshu.io/upload_images/1540531-ae42ecfea48478ff.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)



![](http://upload-images.jianshu.io/upload_images/1540531-ddd64f567a863c03.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](http://upload-images.jianshu.io/upload_images/1540531-b638025c551003a4.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

#### 2.2.2.1工厂类

```java
public class MyBeanFactory {
	
	public static UserServiceImpl createService(){
		//1 目标类
		final UserServiceImpl userService = new UserServiceImpl();
		//2切面类
		final MyAspect myAspect = new MyAspect();
		// 3.代理类 ，采用cglib，底层创建目标类的子类
		//3.1 核心类
		Enhancer enhancer = new Enhancer();
		//3.2 确定父类
		enhancer.setSuperclass(userService.getClass());
		/* 3.3 设置回调函数 , MethodInterceptor接口 等效 jdk InvocationHandler接口
		 * 	intercept() 等效 jdk  invoke()
		 * 		参数1、参数2、参数3：以invoke一样
		 * 		参数4：methodProxy 方法的代理
		 * 		
		 * 
		 */
		enhancer.setCallback(new MethodInterceptor(){

			@Override
			public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
				
				//前
				myAspect.before();
				
				//执行目标类的方法
				Object obj = method.invoke(userService, args);
				// * 执行代理类的父类 ，执行目标类 （目标类和代理类 父子关系）
				methodProxy.invokeSuper(proxy, args);
				
				//后
				myAspect.after();
				
				return obj;
			}
		});
		//3.4 创建代理
		UserServiceImpl proxService = (UserServiceImpl) enhancer.create();
		
		return proxService;
	}

}

```



## 2.3AOP联盟通知类型

- AOP联盟为通知Advice定义了org.aopalliance.aop.Advice
- Spring按照通知Advice在目标类方法的连接点位置，可以分为5类
  - 前置通知org.springframework.aop.MethodBeforeAdvice
    - 在目标方法执行前实施增强
  - 后置通知org.springframework.aop.AfterReturningAdvice
    - 在目标方法执行后实施增强
  - 环绕通知org.aopalliance.intercept.MethodInterceptor
    - 在目标方法执行前后实施增强
  - 异常抛出通知org.springframework.aop.ThrowsAdvice
    - 在方法抛出异常后实施增强
  - 引介通知org.springframework.aop.IntroductionInterceptor
    - 在目标类中添加一些新的方法和属性

       ```java
       //环绕通知，必须手动执行目标方法
       try{
          //前置通知
          //执行目标方法
          //后置通知
       } catch(){
          //抛出异常通知
       }

       ```

       ​

## 2.4spring编写代理:半自动

- 让spring 创建代理对象，从spring容器中手动的获取代理对象。
- 导入jar包：

 核心：4+1

 AOP：AOP联盟（规范）、spring-aop （实现）

![](http://upload-images.jianshu.io/upload_images/1540531-d7323e0eb9654487.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### 2.4.1目标类

```java
public interface UserService {
	
	public void addUser();
	public void updateUser();
	public void deleteUser();

}

```



### 2.4.2切面类

```java
/**
 * 切面类中确定通知，需要实现不同接口，接口就是规范，从而就确定方法名称。
 * * 采用“环绕通知” MethodInterceptor
 *
 */
public class MyAspect implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation mi) throws Throwable {
		
		System.out.println("前3");
		
		//手动执行目标方法
		Object obj = mi.proceed();
		
		System.out.println("后3");
		return obj;
	}
}
```

### 2.4.3spring配置

```xml
<!-- 1 创建目标类 -->
	<bean id="userServiceId" class="com.itheima.b_factory_bean.UserServiceImpl"></bean>
	<!-- 2 创建切面类 -->
	<bean id="myAspectId" class="com.itheima.b_factory_bean.MyAspect"></bean>

	<!-- 3 创建代理类 
		* 使用工厂bean FactoryBean ，底层调用 getObject() 返回特殊bean
		* ProxyFactoryBean 用于创建代理工厂bean，生成特殊代理对象
			interfaces : 确定接口们
				通过<array>可以设置多个值
				只有一个值时，value=""
			target : 确定目标类
			interceptorNames : 通知 切面类的名称，类型String[]，如果设置一个值 value=""
			optimize :强制使用cglib
				<property name="optimize" value="true"></property>
		底层机制
			如果目标类有接口，采用jdk动态代理
			如果没有接口，采用cglib 字节码增强
			如果声明 optimize = true ，无论是否有接口，都采用cglib
		
	-->
	<bean id="proxyServiceId" class="org.springframework.aop.framework.ProxyFactoryBean">
		<property name="interfaces" value="com.itheima.b_factory_bean.UserService"></property>
		<property name="target" ref="userServiceId"></property>
		<property name="interceptorNames" value="myAspectId"></property>
	</bean>

```



### 2.4.4测试

![](http://upload-images.jianshu.io/upload_images/1540531-59a4e5bffb946c07.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```java
	@Test
	public void demo01(){
		String xmlPath = "com/itheima/b_factory_bean/beans.xml";
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlPath);
		
		//获得代理类
		UserService userService = (UserService) applicationContext.getBean("proxyServiceId");
		userService.addUser();
		userService.updateUser();
		userService.deleteUser();
	}

```



## 2.5spring aop编程：全自动【掌握】

- 从spring容器获得目标类，如果配置aop，spring将自动生成代理。
- 要确定目标类，aspectj 切入点表达式，导入jar包

 spring-framework-3.0.2.RELEASE-dependencies\org.aspectj\com.springsource.org.aspectj.weaver\1.6.8.RELEASE



![](http://upload-images.jianshu.io/upload_images/1540531-4b7ebcee08d0a2e1.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)



### 2.5.1spring配置

 ![](http://upload-images.jianshu.io/upload_images/1540531-03260d4043d17a38.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans 
       					   http://www.springframework.org/schema/beans/spring-beans.xsd
       					   http://www.springframework.org/schema/aop 
       					   http://www.springframework.org/schema/aop/spring-aop.xsd">
	<!-- 1 创建目标类 -->
	<bean id="userServiceId" class="com.itheima.c_spring_aop.UserServiceImpl"></bean>
	<!-- 2 创建切面类（通知） -->
	<bean id="myAspectId" class="com.itheima.c_spring_aop.MyAspect"></bean>
	<!-- 3 aop编程 
		3.1 导入命名空间
		3.2 使用 <aop:config>进行配置
				proxy-target-class="true" 声明时使用cglib代理
			<aop:pointcut> 切入点 ，从目标对象获得具体方法
			<aop:advisor> 特殊的切面，只有一个通知 和 一个切入点
				advice-ref 通知引用
				pointcut-ref 切入点引用
		3.3 切入点表达式
			execution(* com.itheima.c_spring_aop.*.*(..))
			选择方法         返回值任意   包             类名任意   方法名任意   参数任意
		
	-->
	<aop:config proxy-target-class="true">
		<aop:pointcut expression="execution(* com.itheima.c_spring_aop.*.*(..))" id="myPointCut"/>
		<aop:advisor advice-ref="myAspectId" pointcut-ref="myPointCut"/>
	</aop:config>
</beans>

```



### 2.5.2测试

```xml
	@Test
	public void demo01(){
		String xmlPath = "com/itheima/c_spring_aop/beans.xml";
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlPath);
		
		//获得目标类
		UserService userService = (UserService) applicationContext.getBean("userServiceId");
		userService.addUser();
		userService.updateUser();
		userService.deleteUser();
	}

```



# 3AspectJ

## 3.1介绍

- AspectJ是一个基于Java语言的AOP框架
- Spring2.0以后新增了对AspectJ切点表达式支持
- @AspectJ 是AspectJ1.5新增功能，通过JDK5注解技术，允许直接在Bean类中定义切面
- 新版本Spring框架，建议使用AspectJ方式来开发AOP


- 主要用途：自定义开发

## 3.2切入点表达式【掌握】

1execution()  用于描述方法【掌握】

 语法：execution(修饰符  返回值  包.类.方法名(参数) throws异常)

​              修饰符，一般省略

​                     public            公共方法

​                     *                   任意

​              返回值，不能省略

​                     void               返回没有值

​                     String            返回值字符串

​                     *                  任意

​              包，[省略]

​                     com.itheima.crm                  固定包

​                     com.itheima.crm.*.service     com包下面子包任意（例如：com.itheima.crm.staff.service）

​                     com.itheima.crm..                com包下面的所有子包（含自己）

​                     com.itheima.crm.*.service..   com包下面任意子包，固定目录service，service目录任意包

​              类，[省略]

​                     UserServiceImpl                  指定类

​                     *Impl                                  以Impl结尾

​                     User*                                  以User开头

​                     *                                        任意

​              方法名，不能省略

​                     addUser                               固定方法

​                     add*                                   以add开头

​                     *Do                                    以Do结尾

​                     *                                        任意

​              (参数)

​                     ()                                        无参

​                     (int)                                    一个整型

​                     (int,int)                              两个

​                     (..)                                      参数任意

​              throws,可省略，一般不写。

综合1

 execution(\* com.itheima.crm.\*.service..\*.\*(..))

综合2

```xml
<aop:pointcut expression="execution(* com.itheima.*WithCommit.*(..)) || 
                          execution(* com.itheima.*Service.*(..))" id="myPointCut"/>

```


2within:匹配包或子包中的方法(了解)

 within(com.itheima.aop..\*)

3this:匹配实现接口的代理对象中的方法(了解)

 this(com.itheima.aop.user.UserDAO)

4target:匹配实现接口的目标对象中的方法(了解)

 target(com.itheima.aop.user.UserDAO)

5args:匹配参数格式符合标准的方法(了解)

 args(int,int)

6bean(id)  对指定的bean所有的方法(了解)

 bean("userServiceId";)

## 3.3AspectJ 通知类型

- aop联盟定义通知类型，具有特性接口，必须实现，从而确定方法名称。
- aspectj 通知类型，只定义类型名称。已经方法格式。
- 个数：6种，知道5种，掌握1中。

 before:前置通知(应用：各种校验)

  在方法执行前执行，如果通知抛出异常，阻止方法运行

 afterReturning:后置通知(应用：常规数据处理)

  方法正常返回后执行，如果方法中抛出异常，通知无法执行

  必须在方法执行后才执行，所以可以获得方法的返回值。

 around:环绕通知(应用：十分强大，可以做任何事情)

  方法执行前后分别执行，可以阻止方法的执行

  必须手动执行目标方法

 afterThrowing:抛出异常通知(应用：包装异常信息)

  方法抛出异常后执行，如果方法没有抛出异常，无法执行

 after:最终通知(应用：清理现场)

  方法执行完毕后执行，无论方法中是否出现异常

```java
//环绕

try{
     //前置：before
    //手动执行目标方法
    //后置：afterRetruning
} catch(){
    //抛出异常 afterThrowing
} finally{
    //最终 after
}

```

![](http://upload-images.jianshu.io/upload_images/1540531-46427be2a7b7d6c2.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](http://upload-images.jianshu.io/upload_images/1540531-3cbe4005cab6351f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](http://upload-images.jianshu.io/upload_images/1540531-6a6c25c5294c4764.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](http://upload-images.jianshu.io/upload_images/1540531-c492f799c67d32f6.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)



## 3.4导入jar包

- 4个：

 aop联盟规范

 spring aop 实现

 aspect 规范

 spring aspect 实现

![](http://upload-images.jianshu.io/upload_images/1540531-943bf9289bc9a8cb.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 3.5基于xml

1.目标类：接口+ 实现

2.切面类：编写多个通知，采用aspectj 通知名称任意（方法名任意）

3.aop编程，将通知应用到目标类

4.测试

### 3.5.1切面类

```java
/**
 * 切面类，含有多个通知
 */
public class MyAspect {
	
	public void myBefore(JoinPoint joinPoint){
		System.out.println("前置通知 ： " + joinPoint.getSignature().getName());
	}
	
	public void myAfterReturning(JoinPoint joinPoint,Object ret){
		System.out.println("后置通知 ： " + joinPoint.getSignature().getName() + " , -->" + ret);
	}
	
	public Object myAround(ProceedingJoinPoint joinPoint) throws Throwable{
		System.out.println("前");
		//手动执行目标方法
		Object obj = joinPoint.proceed();
		
		System.out.println("后");
		return obj;
	}
	
	public void myAfterThrowing(JoinPoint joinPoint,Throwable e){
		System.out.println("抛出异常通知 ： " + e.getMessage());
	}
	
	public void myAfter(JoinPoint joinPoint){
		System.out.println("最终通知");
	}

}
```

### 3.5.2spring配置

```xml
<!-- 1 创建目标类 -->
	<bean id="userServiceId" class="com.itheima.d_aspect.a_xml.UserServiceImpl"></bean>
	<!-- 2 创建切面类（通知） -->
	<bean id="myAspectId" class="com.itheima.d_aspect.a_xml.MyAspect"></bean>
	<!-- 3 aop编程 
		<aop:aspect> 将切面类 声明“切面”，从而获得通知（方法）
			ref 切面类引用
		<aop:pointcut> 声明一个切入点，所有的通知都可以使用。
			expression 切入点表达式
			id 名称，用于其它通知引用
	-->
	<aop:config>
		<aop:aspect ref="myAspectId">
			<aop:pointcut expression="execution(* com.itheima.d_aspect.a_xml.UserServiceImpl.*(..))" id="myPointCut"/>
			
			<!-- 3.1 前置通知 
				<aop:before method="" pointcut="" pointcut-ref=""/>
					method : 通知，及方法名
					pointcut :切入点表达式，此表达式只能当前通知使用。
					pointcut-ref ： 切入点引用，可以与其他通知共享切入点。
				通知方法格式：public void myBefore(JoinPoint joinPoint){
					参数1：org.aspectj.lang.JoinPoint  用于描述连接点（目标方法），获得目标方法名等
				例如：
			<aop:before method="myBefore" pointcut-ref="myPointCut"/>
			-->
			
			<!-- 3.2后置通知  ,目标方法后执行，获得返回值
				<aop:after-returning method="" pointcut-ref="" returning=""/>
					returning 通知方法第二个参数的名称
				通知方法格式：public void myAfterReturning(JoinPoint joinPoint,Object ret){
					参数1：连接点描述
					参数2：类型Object，参数名 returning="ret" 配置的
				例如：
			<aop:after-returning method="myAfterReturning" pointcut-ref="myPointCut" returning="ret" />
			-->
			
			<!-- 3.3 环绕通知 
				<aop:around method="" pointcut-ref=""/>
				通知方法格式：public Object myAround(ProceedingJoinPoint joinPoint) throws Throwable{
					返回值类型：Object
					方法名：任意
					参数：org.aspectj.lang.ProceedingJoinPoint
					抛出异常
				执行目标方法：Object obj = joinPoint.proceed();
				例如：
			<aop:around method="myAround" pointcut-ref="myPointCut"/>
			-->
			<!-- 3.4 抛出异常
				<aop:after-throwing method="" pointcut-ref="" throwing=""/>
					throwing ：通知方法的第二个参数名称
				通知方法格式：public void myAfterThrowing(JoinPoint joinPoint,Throwable e){
					参数1：连接点描述对象
					参数2：获得异常信息，类型Throwable ，参数名由throwing="e" 配置
				例如：
			<aop:after-throwing method="myAfterThrowing" pointcut-ref="myPointCut" throwing="e"/>
			-->
			<!-- 3.5 最终通知 -->			
			<aop:after method="myAfter" pointcut-ref="myPointCut"/>
			
			
			
		</aop:aspect>
	</aop:config>

```



## 3.6基于注解

### 3.6.1替换bean

```xml
<!-- 1 创建目标类 -->
	<bean id="userServiceId" class="com.itheima.d_aspect.b_anno.UserServiceImpl"></bean>
	<!-- 2 创建切面类（通知） -->
	<bean id="myAspectId" class="com.itheima.d_aspect.b_anno.MyAspect"></bean>

```

![](http://upload-images.jianshu.io/upload_images/1540531-666e427ce4cda145.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](http://upload-images.jianshu.io/upload_images/1540531-6f2d94bc6a9a7a41.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

- 注意：扫描

  ```xml
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns:context="http://www.springframework.org/schema/context"
         xmlns:aop="http://www.springframework.org/schema/aop"
         xsi:schemaLocation="http://www.springframework.org/schema/beans 
         					   http://www.springframework.org/schema/beans/spring-beans.xsd
         					   http://www.springframework.org/schema/aop 
         					   http://www.springframework.org/schema/aop/spring-aop.xsd
         					   http://www.springframework.org/schema/context 
         					   http://www.springframework.org/schema/context/spring-context.xsd">
  <!-- 1.扫描 注解类 -->
  	<context:component-scan base-package="com.itheima.d_aspect.b_anno"></context:component-scan>
  ```

### 3.6.2替换aop

- 必须进行aspectj 自动代理

```xml
<!-- 2.确定 aop注解生效 -->
	<aop:aspectj-autoproxy></aop:aspectj-autoproxy>
```

- 声明切面

```xml
 <aop:aspect ref="myAspectId">
```

![](http://upload-images.jianshu.io/upload_images/1540531-30919e1fa4cffe99.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

- 替换前置通知

  ```xml
  <aop:before method="myBefore" pointcut="execution(* com.itheima.d_aspect.b_anno.UserServiceImpl.*(..))"/>
  ```

  ```java
  	//切入点当前有效
  	@Before("execution(* com.itheima.d_aspect.b_anno.UserServiceImpl.*(..))")
  	public void myBefore(JoinPoint joinPoint){
  		System.out.println("前置通知 ： " + joinPoint.getSignature().getName());
  	}

  ```

  ​


- 替换公共切入点

  ```xml
  <aop:pointcut expression="execution(* com.itheima.d_aspect.b_anno.UserServiceImpl.*(..))" id="myPointCut"/>
  ```

  ```java
  //声明公共切入点
  	@Pointcut("execution(* com.itheima.d_aspect.b_anno.UserServiceImpl.*(..))")
  	private void myPointCut(){
  	}

  ```

  ​


- 替换后置

  ```xml
  <aop:after-returning method="myAfterReturning" pointcut-ref="myPointCut" returning="ret" />
  ```

  ```java
  	@AfterReturning(value="myPointCut()" ,returning="ret")
  	public void myAfterReturning(JoinPoint joinPoint,Object ret){
  		System.out.println("后置通知 ： " + joinPoint.getSignature().getName() + " , -->" + ret);
  	}

  ```

  ![](http://upload-images.jianshu.io/upload_images/1540531-1f229300514e19e7.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


- 替换环绕

```xml
<aop:around method="myAround" pointcut-ref="myPointCut"/>
```

```java
@Around(value = "myPointCut()")
	public Object myAround(ProceedingJoinPoint joinPoint) throws Throwable{
		System.out.println("前");
		//手动执行目标方法
		Object obj = joinPoint.proceed();
		
		System.out.println("后");
		return obj;
	}
```

- 替换抛出异常

```xml
<aop:after-throwing method="myAfterThrowing" pointcut="execution(* com.itheima.d_aspect.b_anno.UserServiceImpl.*(..))" throwing="e"/>
```

```java
@AfterThrowing(value="execution(* com.itheima.d_aspect.b_anno.UserServiceImpl.*(..))" ,throwing="e")
	public void myAfterThrowing(JoinPoint joinPoint,Throwable e){
		System.out.println("抛出异常通知 ： " + e.getMessage());
	}

```



### 3.6.3切面类

```java
/**
 * 切面类，含有多个通知
 */
@Component
@Aspect
public class MyAspect {
	
	//切入点当前有效
//	@Before("execution(* com.itheima.d_aspect.b_anno.UserServiceImpl.*(..))")
	public void myBefore(JoinPoint joinPoint){
		System.out.println("前置通知 ： " + joinPoint.getSignature().getName());
	}
	
	//声明公共切入点
	@Pointcut("execution(* com.itheima.d_aspect.b_anno.UserServiceImpl.*(..))")
	private void myPointCut(){
	}
	
//	@AfterReturning(value="myPointCut()" ,returning="ret")
	public void myAfterReturning(JoinPoint joinPoint,Object ret){
		System.out.println("后置通知 ： " + joinPoint.getSignature().getName() + " , -->" + ret);
	}
	
//	@Around(value = "myPointCut()")
	public Object myAround(ProceedingJoinPoint joinPoint) throws Throwable{
		System.out.println("前");
		//手动执行目标方法
		Object obj = joinPoint.proceed();
		
		System.out.println("后");
		return obj;
	}
	
//	@AfterThrowing(value="execution(* com.itheima.d_aspect.b_anno.UserServiceImpl.*(..))" ,throwing="e")
	public void myAfterThrowing(JoinPoint joinPoint,Throwable e){
		System.out.println("抛出异常通知 ： " + e.getMessage());
	}
	
	@After("myPointCut()")
	public void myAfter(JoinPoint joinPoint){
		System.out.println("最终通知");
	}

}


```



### 3.6.4spring配置

```xml
<!-- 1.扫描 注解类 -->
	<context:component-scan base-package="com.itheima.d_aspect.b_anno"></context:component-scan>
	
	<!-- 2.确定 aop注解生效 -->
	<aop:aspectj-autoproxy></aop:aspectj-autoproxy>

```



### 3.6.5aop注解总结

@Aspect  声明切面，修饰切面类，从而获得通知。

通知

 @Before 前置

 @AfterReturning 后置

 @Around 环绕

 @AfterThrowing 抛出异常

 @After 最终

切入点

 @PointCut ，修饰方法private void xxx(){}  之后通过&quot;方法名&quot;获得切入点引用



# 4JdbcTemplate

- spring 提供用于操作JDBC工具类，类似：DBUtils。
- 依赖连接池DataSource （数据源）

## 4.1环境搭建

### 4.1.1创建表

```mysql
create database ee19_spring_day02;
use ee19_spring_day02;
create table t_user(
  id int primary key auto_increment,
  username varchar(50),
  password varchar(32)
);

insert into t_user(username,password) values('jack','1234');
insert into t_user(username,password) values('rose','5678');
```

### 4.1.2导入jar包

![](http://upload-images.jianshu.io/upload_images/1540531-0586cff6c2617eee.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### 4.1.3javabean

```java
package com.itheima.domain;

public class User {
	
	private Integer id;
	private String username;
	private String password;
  	//省略get与set方法
}
```

## 4.2使用api（了解）

```java
public static void main(String[] args) {
		
		//1 创建数据源（连接池） dbcp
		BasicDataSource dataSource = new BasicDataSource();
		// * 基本4项
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/ee19_spring_day02");
		dataSource.setUsername("root");
		dataSource.setPassword("1234");
		
		
		//2  创建模板
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		jdbcTemplate.setDataSource(dataSource);
		
		
		//3 通过api操作
		jdbcTemplate.update("insert into t_user(username,password) values(?,?);", "tom","998");
		
	}
```

## 4.3配置DBCP

```xml
<!-- 创建数据源 -->
	<bean id="dataSourceId" class="org.apache.commons.dbcp.BasicDataSource">
		<property name="driverClassName" value="com.mysql.jdbc.Driver"></property>
		<property name="url" value="jdbc:mysql://localhost:3306/ee19_spring_day02"></property>
		<property name="username" value="root"></property>
		<property name="password" value="1234"></property>
	</bean>
	<!-- 创建模板 ,需要注入数据源-->
	<bean id="jdbcTemplateId" class="org.springframework.jdbc.core.JdbcTemplate">
		<property name="dataSource" ref="dataSourceId"></property>
	</bean>
	
	<!-- 配置dao -->
	<bean id="userDaoId" class="com.itheima.c_dbcp.UserDao">
		<property name="jdbcTemplate" ref="jdbcTemplateId"></property>
	</bean>

```



## 4.4配置C3P0

```xml
<!-- 创建数据源 c3p0-->
	<bean id="dataSourceId" class="com.mchange.v2.c3p0.ComboPooledDataSource">
		<property name="driverClass" value="com.mysql.jdbc.Driver"></property>
		<property name="jdbcUrl" value="jdbc:mysql://localhost:3306/ee19_spring_day02"></property>
		<property name="user" value="root"></property>
		<property name="password" value="1234"></property>
	</bean>

```



## 4.5使用JdbcDaoSupport

### 4.5.1dao层



 ![](http://upload-images.jianshu.io/upload_images/1540531-35da789754dab15f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### 4.5.2spring配置文件

```xml
	<!-- 配置dao 
		* dao 继承 JdbcDaoSupport，之后只需要注入数据源，底层将自动创建模板
	-->
	<bean id="userDaoId" class="com.itheima.e_jdbcdaosupport.UserDao">
		<property name="dataSource" ref="dataSourceId"></property>
	</bean>

```



### 4.5.3源码分析

 ![](http://upload-images.jianshu.io/upload_images/1540531-c1f3247cb59a92a9.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 4.6配置properties

### 4.6.1properties文件

```properties
jdbc.driverClass=com.mysql.jdbc.Driver
jdbc.jdbcUrl=jdbc:mysql://localhost:3306/ee19_spring_day02
jdbc.user=root
jdbc.password=1234
```

### 4.6.2spring配置

```xml
<!-- 加载配置文件 
		"classpath:"前缀表示 src下
		在配置文件之后通过  ${key} 获得内容
	-->
	<context:property-placeholder location="classpath:com/itheima/f_properties/jdbcInfo.properties"/>
	
	<!-- 创建数据源 c3p0-->
	<bean id="dataSourceId" class="com.mchange.v2.c3p0.ComboPooledDataSource">
		<property name="driverClass" value="${jdbc.driverClass}"></property>
		<property name="jdbcUrl" value="${jdbc.jdbcUrl}"></property>
		<property name="user" value="${jdbc.user}"></property>
		<property name="password"  value="${jdbc.password}"></property>
	</bean>
```
# 一、springmvc框架

    1. 1.1什么是springmvc

springmvc是spring框架的一个模块，springmvc和spring无需通过中间整合层进行整合。（struts2与Spring整合的时候需要借助单独的jar包）

springmvc是一个基于mvc的web框架。

![](http://upload-images.jianshu.io/upload_images/1540531-8c84e25614e4408a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 1.2mvc在b/s系统 下的应用

mvc是一个设计模式，mvc在b/s系统 下的应用：

![](http://upload-images.jianshu.io/upload_images/1540531-7e8af088a2c86433.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 1.3springmvc框架


第一步：发起请求到前端控制器(DispatcherServlet)

第二步：前端控制器请求HandlerMapping查找 Handler，可以根据xml配置、注解进行查找

第三步：处理器映射器HandlerMapping向前端控制器返回Handler

第四步：前端控制器调用处理器适配器去执行Handler

第五步：处理器适配器去执行Handler

第六步：Handler执行完成给适配器返回ModelAndView

第七步：处理器适配器向前端控制器返回ModelAndView，ModelAndView是springmvc框架的一个底层对象，包括 Model和view

第八步：前端控制器请求视图解析器去进行视图解析，根据逻辑视图名解析成真正的视图(jsp)

第九步：视图解析器向前端控制器返回View

第十步：前端控制器进行视图渲染，视图渲染将模型数据(在ModelAndView对象中)填充到request域

第十一步：前端控制器向用户响应结果

组件：

1、前端控制器DispatcherServlet（不需要程序员开发）

作用接收请求，响应结果，相当于转发器，中央处理器。

有了DispatcherServlet减少了其它组件之间的耦合度。

2、处理器映射器HandlerMapping(不需要程序员开发)

作用：根据请求的url查找Handler

3、处理器适配器HandlerAdapter

作用：按照特定规则（HandlerAdapter要求的规则）去执行Handler

4、处理器Handler(需要程序员开发)

注意：编写Handler时按照HandlerAdapter的要求去做，这样适配器才可以去正确执行Handler

5、视图解析器View resolver(不需要程序员开发)

作用：进行视图解析，根据逻辑视图名解析成真正的视图（view）

6、视图View(需要程序员开发jsp)

View是一个接口，实现类支持不同的View类型（jsp、freemarker、pdf...）

# 二、入门程序

## 2.1需求

以案例作为驱动。

springmvc和mybaits使用一个案例（商品订单管理）。

功能需求：商品列表查询

## 2.2环境准备

数据库环境：mysql5.1

![](http://upload-images.jianshu.io/upload_images/1540531-3369cf68c4366644.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

java环境：

jdk1.7.0\_72

eclipse indigo

springmvc版本：spring3.2

需要spring3.2所有jar（一定包括spring-webmvc-3.2.0.RELEASE.jar）

![](http://upload-images.jianshu.io/upload_images/1540531-b7d85c699cf1d1b5.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 2.3配置前端控制器

在web.xml中配置前端控制器。 	
![](http://upload-images.jianshu.io/upload_images/1540531-da507338a8462517.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


## 2.4配置处理器适配器

在classpath下的springmvc.xml中配置处理器适配器

![](http://upload-images.jianshu.io/upload_images/1540531-f8ac35451ac3e9e2.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)	

通过查看原代码：

![](http://upload-images.jianshu.io/upload_images/1540531-c04180ce5a2a4d00.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

此适配器能执行实现 Controller接口的Handler。

![](http://upload-images.jianshu.io/upload_images/1540531-786dd0dd539ac277.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 2.5开发Handler

需要实现 controller接口，才能由org.springframework.web.servlet.mvc.SimpleControllerHandlerAdapter适配器执行。

```java
public class ItemsController1 implements Controller {

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		//调用service查找 数据库，查询商品列表，这里使用静态数据模拟
		List<Items> itemsList = new ArrayList<Items>();
		//向list中填充静态数据
		
		Items items_1 = new Items();
		items_1.setName("联想笔记本");
		items_1.setPrice(6000f);
		items_1.setDetail("ThinkPad T430 联想笔记本电脑！");
		
		Items items_2 = new Items();
		items_2.setName("苹果手机");
		items_2.setPrice(5000f);
		items_2.setDetail("iphone6苹果手机！");
		
		itemsList.add(items_1);
		itemsList.add(items_2);

		//返回ModelAndView
		ModelAndView modelAndView =  new ModelAndView();
		//相当 于request的setAttribut，在jsp页面中通过itemsList取数据
		modelAndView.addObject("itemsList", itemsList);
		
		//指定视图
		modelAndView.setViewName("/WEB-INF/jsp/items/itemsList.jsp");

		return modelAndView;
	}

}
```

## 2.6视图编写

![](http://upload-images.jianshu.io/upload_images/1540531-2fb8c40471511d12.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 2.7配置Handler

将编写Handler在spring容器加载。

![](http://upload-images.jianshu.io/upload_images/1540531-b9f765b7ee0a2216.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 2.8配置处理器映射器

在classpath下的springmvc.xml中配置处理器映射器

 ![](data:image/*;base64,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)

## 2.9配置视图解析器

需要配置解析jsp的视图解析器。

​	![](http://upload-images.jianshu.io/upload_images/1540531-5cd076f6b95d455b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 2.10部署调试

访问地址：http://localhost:8080/springmvcfirst1208/queryItems.action

处理器映射器根据url找不到Handler，报下边的错误。说明url错误。

​		![](http://upload-images.jianshu.io/upload_images/1540531-9df48ac42237daa1.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

处理器映射器根据url找到了Handler，转发的jsp页面找到，报下边的错误，说明jsp页面地址错误了。		

​	![](http://upload-images.jianshu.io/upload_images/1540531-5f447250c0cf3b55.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

# 三、注解的处理器映射器和适配器

## 3.1非注解的处理器映射器

处理器映射器：

org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping

另一个映射器：

org.springframework.web.servlet.handler.SimpleUrlHandlerMapping

![](http://upload-images.jianshu.io/upload_images/1540531-a003755b30cdd1da.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

多个映射器可以并存，前端控制器判断url能让哪些映射器映射，就让正确的映射器处理。

## 3.2非注解的处理器适配器

org.springframework.web.servlet.mvc.SimpleControllerHandlerAdapter

要求编写的Handler实现 Controller接口。

org.springframework.web.servlet.mvc.HttpRequestHandlerAdapter

要求编写的Handler实现 HttpRequestHandler接口。

![](http://upload-images.jianshu.io/upload_images/1540531-56f9bc35b07ec7f9.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

   

```java
	//使用此方法可以通过修改response，设置响应的数据格式，比如响应json数据
	response.setCharacterEncoding("utf-8");
	response.setContentType("application/json;charset=utf-8");
	response.getWriter().write("json串");
```

# 四、DispatcherSerlvet.properties

![](http://upload-images.jianshu.io/upload_images/1540531-278e4d4cd8ef9f92.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

前端控制器从上边的文件中加载处理映射器、适配器、视图解析器等组件，如果不在springmvc.xml中配置，使用默认加载的。

# 五、注解的处理器映射器和适配器

## 5.1注解的处理器映射器

在spring3.1之前使用org.springframework.web.servlet.mvc.annotation.DefaultAnnotationHandlerMapping注解映射器。

在spring3.1之后使用org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping注解映射器。

## 5.2注解的处理器适配器

在spring3.1之前使用org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter注解适配器。

在spring3.1之后使用org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter注解适配器。

## 5.3配置注解映射器和适配器

![](http://upload-images.jianshu.io/upload_images/1540531-5ba1ef1b51dd081d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```xml
<!-- 使用 mvc:annotation-driven代替上边注解映射器和注解适配器配置
	mvc:annotation-driven默认加载很多的参数绑定方法，
	比如json转换解析器就默认加载了，如果使用mvc:annotation-driven不用配置上边的RequestMappingHandlerMapping和RequestMappingHandlerAdapter
	实际开发时使用mvc:annotation-driven
	 -->
	<!-- <mvc:annotation-driven></mvc:annotation-driven> -->
```

## 5.4开发注解Handler

使用注解的映射器和注解的适配器。（注解的映射器和注解的适配器必须配对使用）

```java
//使用Controller标识 它是一个控制器
@Controller
public class ItemsController3 {
	
	//商品查询列表
	//@RequestMapping实现 对queryItems方法和url进行映射，一个方法对应一个url
	//一般建议将url和方法写成一样
	@RequestMapping("/queryItems")
	public ModelAndView queryItems()throws Exception{
		
		//调用service查找 数据库，查询商品列表，这里使用静态数据模拟
		List<Items> itemsList = new ArrayList<Items>();
		//向list中填充静态数据
		
		Items items_1 = new Items();
		items_1.setName("联想笔记本");
		items_1.setPrice(6000f);
		items_1.setDetail("ThinkPad T430 联想笔记本电脑！");
		
		Items items_2 = new Items();
		items_2.setName("苹果手机");
		items_2.setPrice(5000f);
		items_2.setDetail("iphone6苹果手机！");
		
		itemsList.add(items_1);
		itemsList.add(items_2);
		
		//返回ModelAndView
		ModelAndView modelAndView =  new ModelAndView();
		//相当 于request的setAttribut，在jsp页面中通过itemsList取数据
		modelAndView.addObject("itemsList", itemsList);
		
		//指定视图
		modelAndView.setViewName("/WEB-INF/jsp/items/itemsList.jsp");
		
		return modelAndView;
		
	}
```

## 5.5在spring容器中加载Handler

```xml
	<!-- 对于注解的Handler可以单个配置
	实际开发中建议使用组件扫描
	 -->
	<!-- <bean class="cn.itcast.ssm.controller.ItemsController3" /> -->
	<!-- 可以扫描controller、service、...
	这里让扫描controller，指定controller的包
	 -->
	<context:component-scan base-package="cn.itcast.ssm.controller"></context:component-scan>
```

## 5.6部署调试

访问：http://localhost:8080/springmvcfirst1208/queryItems.action

# 六、源码分析（重点）

通过前端控制器源码分析springmvc的执行过程。

第一步：前端控制器接收请求

调用doDiapatch

![](http://upload-images.jianshu.io/upload_images/1540531-66d211857304c50f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

第二步：前端控制器调用处理器映射器查找 Handler![](http://upload-images.jianshu.io/upload_images/1540531-4ac1ce868ad6ba0c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](http://upload-images.jianshu.io/upload_images/1540531-d1ad782e0c1a1a60.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

第三步：调用处理器适配器执行Handler，得到执行结果ModelAndView

![](http://upload-images.jianshu.io/upload_images/1540531-6d427f57fe8164ab.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

第四步：视图渲染，将model数据填充到request域。视图解析，得到view:![](http://upload-images.jianshu.io/upload_images/1540531-a712080c865c125f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

调用view的渲染方法，将model数据填充到request域

渲染方法：

![](http://upload-images.jianshu.io/upload_images/1540531-167d17934781b0a0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

 ![](http://upload-images.jianshu.io/upload_images/1540531-955db444d049f28c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

# 七、入门程序小结

通过入门程序理解springmvc前端控制器、 处理器映射器、处理器适配器 、视图解析器用法。

前端控制器配置：

第一种：\*.action，访问以.action结尾 由DispatcherServlet进行解析

第二种：/，所以访问的地址都由DispatcherServlet进行解析，对于静态文件的解析需要配置不让DispatcherServlet进行解析。使用此种方式可以实现 RESTful风格的url


**处理器映射器：**

​	非注解处理器映射器（了解）

​	注解的处理器映射器（掌握）

对标记@Controller类中标识有@RequestMapping的方法进行映射。在@RequestMapping里边定义映射的url。使用注解的映射器不用在xml中配置url和Handler的映射关系。

**处理器适配器：**

​	非注解处理器适配器（了解）

​	注解的处理器适配器（掌握）

注解处理器适配器和注解的处理器映射器是配对使用。理解为不能使用非注解映射器进行映射。

```xml
<mvc:annotation-driven></mvc:annotation-driven>可以代替下边的配置：

	<!--注解映射器 -->
	<bean class="org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping"/>
	<!--注解适配器 -->
	<bean class="org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter"/>
```

实际开发使用：mvc:annotation-driven



视图解析器配置前缀和后缀：

![](http://upload-images.jianshu.io/upload_images/1540531-0b92e134de410c6b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

程序中不用指定前缀和后缀：

![](http://upload-images.jianshu.io/upload_images/1540531-ad78e2cfb85fbe20.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


# 八、springmvc和mybatis整合

## 8.1需求

使用springmvc和mybatis完成商品列表查询。

## 8.2整合思路

springmvc+mybaits的系统架构：

![](http://upload-images.jianshu.io/upload_images/1540531-32fc7c249ab364aa.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

第一步：整合dao层

​	mybatis和spring整合，通过spring管理mapper接口。

​	使用mapper的扫描器自动扫描mapper接口在spring中进行注册。

第二步：整合service层

​	通过spring管理 service接口。

​	使用配置方式将service接口配置在spring配置文件中。

​	实现事务控制。

第三步：整合springmvc

​	由于springmvc是spring的模块，不需要整合。

## 8.3准备环境

数据库环境：mysql5.1

![](http://upload-images.jianshu.io/upload_images/1540531-7f51795e7993b956.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

java环境：

jdk1.7.0\_72

eclipse indigo

springmvc版本：spring3.2

所需要的jar包：

​	数据库驱动包：mysql5.1

​	mybatis的jar包

​	mybatis和spring整合包

​	log4j包

​	dbcp数据库连接池包

​	spring3.2所有jar包

​	jstl包

工程结构：

![](http://upload-images.jianshu.io/upload_images/1540531-100da9fe0fa11fa4.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 8.4整合dao

mybatis和spring进行整合。

### 8.4.1sqlMapConfig.xml


mybatis自己的配置文件。

![](http://upload-images.jianshu.io/upload_images/1540531-a6a8fbe40b92caaa.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### 8.4.2applicationContext-dao.xml


配置：

数据源

SqlSessionFactory

mapper扫描器

 ![](http://upload-images.jianshu.io/upload_images/1540531-007ec610ae9d7d57.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### 8.4.3逆向工程生成po类及mapper(单表增删改查)


![](http://upload-images.jianshu.io/upload_images/1540531-4216eff80464b7b9.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

将生成的文件拷贝至工程 中。

### 8.4.4手动定义商品查询mapper


针对综合查询mapper，一般情况会有关联查询，建议自定义mapper

#### 8.4.4.1ItemsMapperCustom.xml

sql语句：

 SELECT \* FROM items  WHERE items.name LIKE &#39;%笔记本%&#39;

![](http://upload-images.jianshu.io/upload_images/1540531-bd2e302c81d742de.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

#### 8.4.4.2ItemsMapperCustom.java

​		 ![](http://upload-images.jianshu.io/upload_images/1540531-8fe911dd8d063264.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 8.5整合service

让spring管理service接口。

### 8.5.1定义service接口


![](http://upload-images.jianshu.io/upload_images/1540531-7bbaf811aecb33a7.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](http://upload-images.jianshu.io/upload_images/1540531-4108210e1da08eb5.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### 8.5.2在spring容器配置service(applicationContext-service.xml)


创建applicationContext-service.xml，文件中配置service。

![](http://upload-images.jianshu.io/upload_images/1540531-8e91922335816fce.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### 8.5.3事务控制(applicationContext-transaction.xml)


在applicationContext-transaction.xml中使用spring声明式事务控制方法。

![](http://upload-images.jianshu.io/upload_images/1540531-91168ca9e5f52acf.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 8.6整合springmvc

### 8.6.1springmvc.xml

创建springmvc.xml文件，配置处理器映射器、适配器、视图解析器。

```xml
<!-- 可以扫描controller、service、...
	这里让扫描controller，指定controller的包
	 -->
	<context:component-scan base-package="cn.itcast.ssm.controller"></context:component-scan>
	
		
	<!--注解映射器 -->
	<!-- <bean class="org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping"/> -->
	<!--注解适配器 -->
	<!-- <bean class="org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter"/> -->
	
	<!-- 使用 mvc:annotation-driven代替上边注解映射器和注解适配器配置
	mvc:annotation-driven默认加载很多的参数绑定方法，
	比如json转换解析器就默认加载了，如果使用mvc:annotation-driven不用配置上边的RequestMappingHandlerMapping和RequestMappingHandlerAdapter
	实际开发时使用mvc:annotation-driven
	 -->
	<mvc:annotation-driven></mvc:annotation-driven>
	

	<!-- 视图解析器
	解析jsp解析，默认使用jstl标签，classpath下的得有jstl的包
	 -->
	<bean
		class="org.springframework.web.servlet.view.InternalResourceViewResolver">
		<!-- 配置jsp路径的前缀 -->
		<property name="prefix" value="/WEB-INF/jsp/"/>
		<!-- 配置jsp路径的后缀 -->
		<property name="suffix" value=".jsp"/>
	</bean>
```

### 8.6.2配置前端控制器


参考入门程序。

### 8.6.3编写Controller(就是Handler)


![](http://upload-images.jianshu.io/upload_images/1540531-498e7716f7e2dcc0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### 8.6.4编写jsp


![](http://upload-images.jianshu.io/upload_images/1540531-d1dd866c67dd2dbd.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 8.7加载spring容器

将mapper、service、controller加载到spring容器中。

![](http://upload-images.jianshu.io/upload_images/1540531-12e8ff450c79c036.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

建议使用通配符加载上边的配置文件。

在web.xml中，添加spring容器监听器，加载spring容器。

![](http://upload-images.jianshu.io/upload_images/1540531-07d6dd6efc6f6ca9.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


# 九、商品修改功能开发

## 9.1需求

操作流程：

1、进入商品查询列表页面

2、点击修改，进入商品修改页面，页面中显示了要修改的商品（从数据库查询）

 要修改的商品从数据库查询，根据商品id(主键)查询商品信息

3、在商品修改页面，修改商品信息，修改后，点击提交

## 9.2开发mapper

mapper：

 根据id查询商品信息

 根据id更新Items表的数据

不用开发了，使用逆向工程生成的代码。

## 9.3开发service

接口功能：

 根据id查询商品信息

 修改商品信息

​		![](http://upload-images.jianshu.io/upload_images/1540531-7f59790517c54c53.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 9.4开发controller

方法：

 商品信息修改页面显示

 商品信息修改提交

# 十、@RequestMapping

- url映射

定义controller方法对应的url，进行处理器映射使用。

- 窄化请求映射

![](http://upload-images.jianshu.io/upload_images/1540531-3a221466483d72da.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

- 限制http请求方法

出于安全性考虑，对http的链接进行方法限制。

如果限制请求为post方法，进行get请求，报错：

![](http://upload-images.jianshu.io/upload_images/1540531-0489ea61cf2fda1a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](http://upload-images.jianshu.io/upload_images/1540531-3f109c9f29d8aaf8.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

# 十一、controller方法的返回值

- 返回ModelAndView

需要方法结束时，定义ModelAndView，将model和view分别进行设置。

- 返回string

如果controller方法返回string，

1、表示返回逻辑视图名。

真正视图(jsp路径)=前缀+逻辑视图名+后缀

![](http://upload-images.jianshu.io/upload_images/1540531-802c01c88cfa5371.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

2、redirect重定向

商品修改提交后，重定向到商品查询列表。

redirect重定向特点：浏览器地址栏中的url会变化。修改提交的request数据无法传到重定向的地址。因为重定向后重新进行request（request无法共享）

![](http://upload-images.jianshu.io/upload_images/1540531-9bedc8a21172c504.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

3、forward页面转发

通过forward进行页面转发，浏览器地址栏url不变，request可以共享。

![](http://upload-images.jianshu.io/upload_images/1540531-c00f357a2818093f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)



- 返回void

在controller方法形参上可以定义request和response，使用request或response指定响应结果：

1、使用request转向页面，如下：

request.getRequestDispatcher(&quot;页面路径&quot;).forward(request, response);

2、也可以通过response页面重定向：

response.sendRedirect(&quot;url&quot;)

3、也可以通过response指定响应结果，例如响应json数据如下：

response.setCharacterEncoding(&quot;utf-8&quot;);

response.setContentType(&quot;application/json;charset=utf-8&quot;);

response.getWriter().write(&quot;json串&quot;);

# 十二、参数绑定

## 12.1spring参数绑定过程

从客户端请求key/value数据，经过参数绑定，将key/value数据绑定到controller方法的形参上。

springmvc中，接收页面提交的数据是通过方法形参来接收。而不是在controller类定义成员变更接收！！！！

## 12.2默认支持的类型

直接在controller方法形参上定义下边类型的对象，就可以使用这些对象。在参数绑定过程中，如果遇到下边类型直接进行绑定。

### 12.1.1HttpServletRequest

通过request对象获取请求信息

### 12.1.2HttpServletResponse

通过response处理响应信息

### 12.1.3HttpSession

通过session对象得到session中存放的对象

### 12.1.4Model/ModelMap

model是一个接口，modelMap是一个接口实现 。

作用：将model数据填充到request域。

## 12.3简单类型

通过@RequestParam对简单类型的参数进行绑定。

如果不使用@RequestParam，要求request传入参数名称和controller方法的形参名称一致，方可绑定成功。

如果使用@RequestParam，不用限制request传入参数名称和controller方法的形参名称一致。

通过required属性指定参数是否必须要传入，如果设置为true，没有传入参数，报下边错误：

![](http://upload-images.jianshu.io/upload_images/1540531-a165bea7688d417d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](http://upload-images.jianshu.io/upload_images/1540531-14e73a6e394dea0f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

参考教案 对其它简单类型绑定进行测试。

## 12.4pojo绑定

页面中input的name和controller的pojo形参中的属性名称一致，将页面中数据绑定到pojo对应的属性。

页面定义：

![](http://upload-images.jianshu.io/upload_images/1540531-62f160720a3bc167.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

controller的pojo形参的定义：

![](http://upload-images.jianshu.io/upload_images/1540531-d59ab869c38c83b9.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

需要说明的是：简单类型的参数绑定和pojo参数绑定互不影响。

## 12.5自定义参数绑定实现日期类型绑定

对于controller形参中pojo对象，如果属性中有日期类型，需要自定义参数绑定。

将请求日期数据串传成 日期类型，要转换的日期类型和pojo中日期属性的类型保持一致。

![](http://upload-images.jianshu.io/upload_images/1540531-d1f4ac5a0d54a2e8.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

所以自定义参数绑定将日期串转成java.util.Date类型。

需要向处理器适配器中注入自定义的参数绑定组件。

### 12.5.1自定义日期类型绑定

![](http://upload-images.jianshu.io/upload_images/1540531-eaf6622338ce1116.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### 12.5.2配置方式

 ![](http://upload-images.jianshu.io/upload_images/1540531-c471fe647525f29e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](http://upload-images.jianshu.io/upload_images/1540531-d130a8db5bdc78d1.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


# 十三、springmvc和struts2的区别

1、springmvc基于方法开发的，struts2基于类开发的。

springmvc将url和controller方法映射。映射成功后springmvc生成一个Handler对象，对象中只包括了一个method。

方法执行结束，形参数据销毁。

springmvc的controller开发类似service开发。

2、springmvc可以进行单例开发，并且建议使用单例开发，struts2通过类的成员变量接收参数，无法使用单例，只能使用多例。（原因就是第一句）

3、经过实际测试，struts2速度慢，在于使用struts标签，如果使用struts建议使用jstl。



# 十四、问题

## 14.1post乱码

在web.xml添加post乱码filter

在web.xml中加入：

```xml
<filter>
<filter-name>CharacterEncodingFilter</filter-name>
<filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
<init-param>
<param-name>encoding</param-name>
<param-value>utf-8</param-value>
</init-param>
</filter>
<filter-mapping>
<filter-name>CharacterEncodingFilter</filter-name>
<url-pattern>/*</url-pattern>
</filter-mapping>
```

以上可以解决post请求乱码问题。

对于get请求中文参数出现乱码解决方法有两个：

修改tomcat配置文件添加编码与工程编码一致，如下：

```xml
<Connector URIEncoding="utf-8" connectionTimeout="20000" port="8080" protocol="HTTP/1.1" redirectPort="8443"/>
```

另外一种方法对参数进行重新编码：

```java 
String userName new 
String(request.getParamter("userName").getBytes("ISO8859-1"),"utf-8")
```

ISO8859-1是tomcat默认编码，需要将tomcat编码后的内容按utf-8编码

# 一、包装类型pojo参数绑定

## 2.1需求

 商品查询controller方法中实现商品查询条件传入。

## 2.2实现方法

第一种方法：在形参中 添加HttpServletRequest request参数，通过request接收查询条件参数。

第二种方法：在形参中让包装类型的pojo接收查询条件参数。

 分析：

 页面传参数的特点：复杂，多样性。条件包括 ：用户账号、商品编号、订单信息。。。

 如果将用户账号、商品编号、订单信息等放在简单pojo（属性是简单类型）中，pojo类属性比较多，比较乱。

 建议使用包装类型的pojo，pojo中属性是pojo。

## 2.3页面参数和controller方法形参定义

页面参数：

 商品名称：&lt;input name=&quot;itemsCustom.name&quot; /&gt;

 注意：itemsCustom和包装pojo中的属性一致即可。

controller方法形参：

 public  ModelAndView  queryItems(HttpServletRequest request,ItemsQueryVo itemsQueryVo) throws Exception

![](http://upload-images.jianshu.io/upload_images/1540531-0ac3d05f8c4a5890.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

# 二、集合类型绑定

## 2.1数组绑定

### 2.1.1需求


商品批量删除，用户在页面选择多个商品，批量删除。

### 2.1.2表现层实现


关键：将页面选择(多选)的商品id，传到controller方法的形参，方法形参使用数组接收页面请求的多个商品id。



controller方法定义：

![](http://upload-images.jianshu.io/upload_images/1540531-61da8c54f57d2eba.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

页面定义：

![](http://upload-images.jianshu.io/upload_images/1540531-3ce8e816c25961c2.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 2.2list绑定

### 2.2.1需求

通常在需要批量提交数据时，将提交的数据绑定到list&lt;pojo&gt;中，比如：成绩录入（录入多门课成绩，批量提交），

本例子需求：批量商品修改，在页面输入多个商品信息，将多个商品信息提交到controller方法中。

### 2.2.2表现层实现


controller方法定义：

 1、进入批量商品修改页面(页面样式参考商品列表实现)

 2、批量修改商品提交

 使用List接收页面提交的批量数据，通过包装pojo接收，在包装pojo中定义list&lt;pojo&gt;属性	

![](http://upload-images.jianshu.io/upload_images/1540531-de04f5bc1620d916.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)



![](http://upload-images.jianshu.io/upload_images/1540531-47b0c9b64acb4b2c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

页面定义：

​		 ![](http://upload-images.jianshu.io/upload_images/1540531-e5702019bed8e54b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 2.3map绑定

也通过在包装pojo中定义map类型属性。

在包装类中定义Map对象，并添加get/set方法，action使用包装对象接收。

包装类中定义Map对象如下：

```java
Public class QueryVo {
private Map<String, Object> itemInfo = new HashMap<String, Object>();
  //get/set方法..
}
```

页面定义如下：

```jsp
<tr>
<td>学生信息：</td>
<td>
姓名：<inputtype="text"name="itemInfo['name']"/>
年龄：<inputtype="text"name="itemInfo['price']"/>
.. .. ..
</td>
</tr>
```

Contrller方法定义如下：

```java
public String useraddsubmit(Model model,QueryVo queryVo)throws Exception{

	System.out.println(queryVo.getStudentinfo());

}
```

# 三、springmvc校验

## 3.1校验理解

项目中，通常使用较多是前端的校验，比如页面中js校验。对于安全要求较高点建议在服务端进行校验。

服务端校验：

 	控制层conroller：校验页面请求的参数的合法性。在服务端控制层conroller校验，不区分客户端类型（浏览器、手机客户端、远程调用）

 	业务层service（使用较多）：主要校验关键业务参数，仅限于service接口中使用的参数。

 	持久层dao：一般是不校验的。

## 3.2springmvc校验需求

springmvc使用hibernate的校验框架validation(和hibernate没有任何关系)。

校验思路：

​	页面提交请求的参数，请求到controller方法中，使用validation进行校验。如果校验出错，将错误信息展示到页面。

具体需求：

​	商品修改，添加校验（校验商品名称长度，生产日期的非空校验），如果校验出错，在商品修改页面显示错误信息。

## 3.3环境准备

hibernate的校验框架validation所需要jar包：

![](http://upload-images.jianshu.io/upload_images/1540531-fc91b883932ff9b3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 3.4配置校验器

![](http://upload-images.jianshu.io/upload_images/1540531-f40c450b9854a865.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 3.5校验器注入到处理器适配器中

​		![](http://upload-images.jianshu.io/upload_images/1540531-aed0df00206357c1.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 3.6在pojo中添加校验规则

在ItemsCustom.java中添加校验规则：

![](http://upload-images.jianshu.io/upload_images/1540531-3fbccc7d1f13ceec.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 3.7CustomValidationMessages.properties

在CustomValidationMessages.properties配置校验错误信息：

![](http://upload-images.jianshu.io/upload_images/1540531-485c9b61393ebd5a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 3.8捕获校验错误信息

![](http://upload-images.jianshu.io/upload_images/1540531-27faff604a9c0698.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

//在需要校验的pojo前边添加@Validated，在需要校验的pojo后边添加BindingResult bindingResult接收校验出错信息

//注意：@Validated和BindingResult bindingResult是配对出现，并且形参顺序是固定的（一前一后）。



## 3.9在页面显示校验错误信息

在controller中将错误信息传到页面即可。

 ![](http://upload-images.jianshu.io/upload_images/1540531-377fd2b13ca9a810.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

页面显示错误信息：

![](http://upload-images.jianshu.io/upload_images/1540531-09924717a1f0b7e2.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
## 3.10分组校验

### 3.10.1需求

在pojo中定义校验规则，而pojo是被多个 controller所共用，当不同的controller方法对同一个pojo进行校验，但是每个controller方法需要不同的校验。

解决方法：

定义多个校验分组（其实是一个java接口），分组中定义有哪些规则

每个controller方法使用不同的校验分组

### 3.10.2校验分组


![](http://upload-images.jianshu.io/upload_images/1540531-a6cdcc395c5ffa77.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### 3.10.3在校验规则中添加分组


 ![](http://upload-images.jianshu.io/upload_images/1540531-852f51566992e4aa.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

 
### 3.10.4在controller方法使用指定分组的校验


 ![](http://upload-images.jianshu.io/upload_images/1540531-459c6c5c1f6c1431.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


# 四、数据回显

## 4.1什么数据回显

提交后，如果出现错误，将刚才提交的数据回显到刚才的提交页面。

## 4.2pojo数据回显方法

1、springmvc默认对pojo数据进行回显。

pojo数据传入controller方法后，springmvc自动将pojo数据放到request域，key等于pojo类型（首字母小写）

使用@ModelAttribute指定pojo回显到页面在request中的key

2、@ModelAttribute还可以将方法的返回值传到页面

在商品查询列表页面，通过商品类型查询商品信息。

在controller中定义商品类型查询方法，最终将商品类型传到页面。

![](http://upload-images.jianshu.io/upload_images/1540531-3a7acad7266409b8.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

页面上可以得到itemTypes数据。

![](http://upload-images.jianshu.io/upload_images/1540531-d8dcb93bb7404e3c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

3、使用最简单方法使用model，可以不用@ModelAttribute

![](http://upload-images.jianshu.io/upload_images/1540531-cb6a23b69008370b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 4.3简单类型数据回显

使用最简单方法使用model。

```java
model.addAttribute("id", id);
```

# 五、异常处理

## 5.1异常处理思路

系统中异常包括两类：预期异常和运行时异常RuntimeException，前者通过捕获异常从而获取异常信息，后者主要通过规范代码开发、测试通过手段减少运行时异常的发生。

 系统的dao、service、controller出现都通过throws Exception向上抛出，最后由springmvc前端控制器交由异常处理器进行异常处理，如下图：

![](http://upload-images.jianshu.io/upload_images/1540531-27d068bb31674849.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

springmvc提供全局异常处理器（一个系统只有一个异常处理器）进行统一异常处理。

## 5.2自定义异常类

对不同的异常类型定义异常类，继承Exception。

![](http://upload-images.jianshu.io/upload_images/1540531-894e83bdc9baf1e3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 5.3全局异常处理器

思路：

​	系统遇到异常，在程序中手动抛出，dao抛给service、service给controller、controller抛给前端控制器，前端控制器调用全局异常处理器。

全局异常处理器处理思路：

解析出异常类型

​	如果该 异常类型是系统 自定义的异常，直接取出异常信息，在错误页面展示

​	如果该 异常类型不是系统 自定义的异常，构造一个自定义的异常类型（信息为&quot;未知错误&quot;）

springmvc提供一个HandlerExceptionResolver接口

```java
	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		//handler就是处理器适配器要执行Handler对象（只有method）
		
//		解析出异常类型
//		如果该 异常类型是系统 自定义的异常，直接取出异常信息，在错误页面展示
//		String message = null;
//		if(ex instanceof CustomException){
//			message = ((CustomException)ex).getMessage();
//		}else{
////			如果该 异常类型不是系统 自定义的异常，构造一个自定义的异常类型（信息为“未知错误”）
//			message="未知错误";
//		}
		//上边代码变为
		CustomException customException = null;
		if(ex instanceof CustomException){
			customException = (CustomException)ex;
		}else{
			customException = new CustomException("未知错误");
		}
		//错误信息
		String message = customException.getMessage();
		ModelAndView modelAndView = new ModelAndView();
		//将错误信息传到页面
		modelAndView.addObject("message", message);
		//指向错误页面
		modelAndView.setViewName("error");
		return modelAndView;
	}
```

## 4.4错误页面

![](http://upload-images.jianshu.io/upload_images/1540531-6ee438f69bb25d5e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 5.5在springmvc.xml配置全局异常处理器

![](http://upload-images.jianshu.io/upload_images/1540531-8e92ae354da7e77f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 5.6异常测试

在controller、service、dao中任意一处需要手动抛出异常。

如果是程序中手动抛出的异常，在错误页面中显示自定义的异常信息，如果不是手动抛出异常说明是一个运行时异常，在错误页面只显示&quot;未知错误&quot;。

在商品修改的controller方法中抛出异常

![](http://upload-images.jianshu.io/upload_images/1540531-57e473326a1aec63.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

在service接口中抛出异常：

![](http://upload-images.jianshu.io/upload_images/1540531-82e8d833639ea57f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

如果与业务功能相关的异常，建议在service中抛出异常。

与业务功能没有关系的异常，建议在controller中抛出。

上边的功能，建议在service中抛出异常。

# 六、上传图片

## 6.1需求

在修改商品页面，添加上传商品图片功能。

## 6.2springmvc中对多部件类型解析

在 页面form中提交enctype=&quot;multipart/form-data&quot;的数据时，需要springmvc对multipart类型的数据进行解析。

在springmvc.xml中配置multipart类型解析器。

​	![](http://upload-images.jianshu.io/upload_images/1540531-7e0e512b94c12a80.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)	

## 6.3加入上传图片的jar

上边的解析内部使用下边的jar进行图片上传。

![](http://upload-images.jianshu.io/upload_images/1540531-c12c7369c5b91cbb.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
## 6.4创建图片虚拟 目录 存储图片

通过图形界面配置：

​		 ![](http://upload-images.jianshu.io/upload_images/1540531-aaad0feb29ded8a2.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

也可以直接修改tomcat的配置：

在conf/server.xml文件，添加虚拟 目录 ：

![](http://upload-images.jianshu.io/upload_images/1540531-ccc114b92ad982e3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

注意：在图片虚拟目录 中，一定将图片目录分级创建（提高i/o性能），一般我们采用按日期(年、月、日)进行分级创建。

## 6.5上传图片代码

### 6.5.1页面

![](http://upload-images.jianshu.io/upload_images/1540531-2db7c9bb6492a4dc.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### 6.5.2controller方法


修改：商品修改controller方法：

![](http://upload-images.jianshu.io/upload_images/1540531-3e92aaa4bf855f24.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

 ![](http://upload-images.jianshu.io/upload_images/1540531-156ca94ddb04ad17.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


# 七、json数据交互

## 7.1为什么要进行json数据交互

json数据格式在接口调用中、html页面中较常用，json格式比较简单，解析还比较方便。

比如：webservice接口，传输json数据.

## 7.2springmvc进行json交互

![](http://upload-images.jianshu.io/upload_images/1540531-939cbdc80f347901.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

1、请求json、输出json，要求请求的是json串，所以在前端页面中需要将请求的内容转成json，不太方便。

2、请求key/value、输出json。此方法比较常用。

## 7.3环境准备

### 7.3.1加载json转的jar包

springmvc中使用jackson的包进行json转换（@requestBody和@responseBody使用下边的包进行json转），如下：	

​							![](http://upload-images.jianshu.io/upload_images/1540531-54a8fff2c8debf12.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### 7.3.2配置json转换器


在注解适配器中加入messageConverters

```xml
<!--注解适配器 -->
	<bean class="org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter">
		<property name="messageConverters">
		<list>
		<bean class="org.springframework.http.converter.json.MappingJacksonHttpMessageConverter"></bean>
		</list>
		</property>
	</bean>
```

注意：如果使用&lt;mvc:annotation-driven /&gt; 则不用定义上边的内容。

## 7.4json交互测试

### 7.4.1输入json串，输出是json串  

#### 7.4.1.1jsp页面

使用jquery的ajax提交json串，对输出的json结果进行解析。

​		![](http://upload-images.jianshu.io/upload_images/1540531-c78f6220d16d657f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

#### 7.4.1.2controller

​		![](http://upload-images.jianshu.io/upload_images/1540531-166b75c5c0133c12.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

#### 7.4.1.3测试结果

![](http://upload-images.jianshu.io/upload_images/1540531-0684c855d68cd5a1.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### 7.4.2输入key/value，输出是json串

#### 7.4.2.1jsp页面

使用jquery的ajax提交key/value串，对输出的json结果进行解析。

​		![](http://upload-images.jianshu.io/upload_images/1540531-9a7af1a0ed40022c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

#### 7.4.2.2controller

![](http://upload-images.jianshu.io/upload_images/1540531-73516ee791135dce.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)



####  7.4.2.3测试

![](http://upload-images.jianshu.io/upload_images/1540531-c6461a3cf9cb124f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


# 八、RESTful支持

## 8.1什么是RESTful

RESTful架构，就是目前最流行的一种互联网软件架构。它结构清晰、符合标准、易于理解、扩展方便，所以正得到越来越多网站的采用。

RESTful（即Representational State Transfer的缩写）其实是一个开发理念，是对http的很好的诠释。

1、对url进行规范，写RESTful格式的url

​	非REST的url：http://...../queryItems.action?id=001&amp;type=T01

​	REST的url风格：http://..../items/001

 特点：url简洁，将参数通过url传到服务端

2、http的方法规范

不管是删除、添加、更新。。使用url是一致的，如果进行删除，需要设置http的方法为delete，同理添加

后台controller方法：判断http方法，如果是delete执行删除，如果是post执行添加。

3、对http的contentType规范

请求时指定contentType，要json数据，设置成json格式的type。。

## 8.2REST的例子

### 8.2.1需求

查询商品信息，返回json数据。

### 8.2.2controller


定义方法，进行url映射使用REST风格的url，将查询商品信息的id传入controller .

输出json使用@ResponseBody将java对象输出json。

​	 ![](http://upload-images.jianshu.io/upload_images/1540531-8c143aa6e51de5cb.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

@RequestMapping(value=&quot;/itemsView/{id}&quot;)：{×××}占位符，请求的URL可以是&quot;/viewItems/1&quot;或&quot;/viewItems/2&quot;，通过在方法中使用@PathVariable获取{×××}中的×××变量。

@PathVariable用于将请求URL中的模板变量映射到功能处理方法的参数上。

如果RequestMapping中表示为&quot;/ itemsView /{id}&quot;，id和形参名称一致，@PathVariable不用指定名称。

### 8.2.3REST方法的前端控制器配置


在web.xml配置：

 ![](http://upload-images.jianshu.io/upload_images/1540531-1c606eaef1993700.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 8.3对静态资源的解析

配置前端控制器的url-parttern中指定/，对静态资源的解析出现问题：

![](http://upload-images.jianshu.io/upload_images/1540531-6c441111cefc27d1.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

在springmvc.xml中添加静态资源解析方法。

![](http://upload-images.jianshu.io/upload_images/1540531-54396d349848af53.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


# 九、拦截器

## 9.1拦截定义

定义拦截器，实现HandlerInterceptor接口。接口中提供三个方法。

```java
public class HandlerInterceptor1 implements HandlerInterceptor {

	
	//进入 Handler方法之前执行
	//用于身份认证、身份授权
	//比如身份认证，如果认证通过表示当前用户没有登陆，需要此方法拦截不再向下执行
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		//return false表示拦截，不向下执行
		//return true表示放行
		return false;
	}

	//进入Handler方法之后，返回modelAndView之前执行
	//应用场景从modelAndView出发：将公用的模型数据(比如菜单导航)在这里传到视图，也可以在这里统一指定视图
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		
	}

	//执行Handler完成执行此方法
	//应用场景：统一异常处理，统一日志处理
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
		
	}

}
```

## 9.2拦截器配置

### 9.2.1针对HandlerMapping配置


springmvc拦截器针对HandlerMapping进行拦截设置， 如果在某个HandlerMapping中配置拦截，经过该 HandlerMapping映射成功的handler最终使用该 拦截器。

```xml
<bean
	class="org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping">
	<property name="interceptors">
		<list>
			<ref bean="handlerInterceptor1"/>
			<ref bean="handlerInterceptor2"/>
		</list>
	</property>
</bean>
<bean id="handlerInterceptor1" class="springmvc.intercapter.HandlerInterceptor1"/>
<bean id="handlerInterceptor2" class="springmvc.intercapter.HandlerInterceptor2"/>
```


一般不推荐使用。

### 9.2.2类似全局的拦截器


springmvc配置类似全局的拦截器，springmvc框架将配置的类似全局的拦截器注入到每个HandlerMapping中。

​		![](http://upload-images.jianshu.io/upload_images/1540531-76fc291fdb325650.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 9.3拦截测试

### 9.3.1测试需求

测试多个拦截器各各方法执行时机。

### 9.3.2编写两个拦截


![](http://upload-images.jianshu.io/upload_images/1540531-44a5246f2e02af5c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### 9.3.3两个拦截器都放行


HandlerInterceptor1...preHandle

HandlerInterceptor2...preHandle

HandlerInterceptor2...postHandle

HandlerInterceptor1...postHandle

HandlerInterceptor2...afterCompletion

HandlerInterceptor1...afterCompletion

总结：

preHandle方法按顺序执行，

postHandle和afterCompletion按拦截器配置的逆向顺序执行。

### 9.3.4拦截器1放行，拦截器2不放行


HandlerInterceptor1...preHandle

HandlerInterceptor2...preHandle

HandlerInterceptor1...afterCompletion

总结：

拦截器1放行，拦截器2 preHandle才会执行。

拦截器2 preHandle不放行，拦截器2 postHandle和afterCompletion不会执行。

只要有一个拦截器不放行，postHandle不会执行。

### 9.3.5拦截器1不放行，拦截器2不放行


HandlerInterceptor1...preHandle

拦截器1 preHandle不放行，postHandle和afterCompletion不会执行。

拦截器1 preHandle不放行，拦截器2不执行。

### 9.3.6小结


根据测试结果，对拦截器应用。

比如：统一日志处理拦截器，需要该 拦截器preHandle一定要放行，且将它放在拦截器链接中第一个位置。

比如：登陆认证拦截器，放在拦截器链接中第一个位置。权限校验拦截器，放在登陆认证拦截器之后。（因为登陆通过后才校验权限）

## 9.4拦截器应用（实现登陆认证）

### 9.4.1需求


1、用户请求url

2、拦截器进行拦截校验

 如果请求的url是公开地址（无需登陆即可访问的url），让放行

 如果用户session 不存在跳转到登陆页面

 如果用户session存在放行，继续操作。

### 9.4.2登陆controller方法

```java
@Controller
public class LoginController {

	// 登陆
	@RequestMapping("/login")
	public String login(HttpSession session, String username, String password)
			throws Exception {

		// 调用service进行用户身份验证
		// ...

		// 在session中保存用户身份信息
		session.setAttribute("username", username);
		// 重定向到商品列表页面
		return "redirect：/items/queryItems.action";
	}

	// 退出
	@RequestMapping("/logout")
	public String logout(HttpSession session) throws Exception {

		// 清除session
		session.invalidate();

		// 重定向到商品列表页面
		return "redirect：/items/queryItems.action";
	}

}
```

### 9.4.3登陆认证拦截实现

#### 9.4.3.1代码实现

```java
public class LoginInterceptor implements HandlerInterceptor {

	
	//进入 Handler方法之前执行
	//用于身份认证、身份授权
	//比如身份认证，如果认证通过表示当前用户没有登陆，需要此方法拦截不再向下执行
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		//获取请求的url
		String url = request.getRequestURI();
		//判断url是否是公开 地址（实际使用时将公开 地址配置配置文件中）
		//这里公开地址是登陆提交的地址
		if(url.indexOf("login.action")>=0){
			//如果进行登陆提交，放行
			return true;
		}
		
		//判断session
		HttpSession session  = request.getSession();
		//从session中取出用户身份信息
		String username = (String) session.getAttribute("username");
		
		if(username != null){
			//身份存在，放行
			return true;
		}
		
		//执行这里表示用户身份需要认证，跳转登陆页面
		request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
		
		//return false表示拦截，不向下执行
		//return true表示放行
		return false;
	}
```

#### 9.4.3.2拦截器配置

 ![](http://upload-images.jianshu.io/upload_images/1540531-d405e7db7dba235b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

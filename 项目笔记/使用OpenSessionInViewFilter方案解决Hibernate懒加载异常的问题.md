# 使用OpenSessionInViewFilter方案解决Hibernate懒加载异常的问题

> 在项目练习的时候，遇到了这个懒加载异常，当时解决的方法是在配置文件中设置lazy="false"。该方法有很到缺点，效率极低，我们将所有相关联的数据都查询了，频繁的查询降低了效率！！不建议采用

## Web程序中的懒加载异常说明及解决方案

![图一](http://upload-images.jianshu.io/upload_images/1540531-fd3b34cdd9187c3b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### 异常说明

当一个请求来了之后，先执行Action，在执行结果。在action里面有Service业务层，调用Service，Service做业务处理。

开始执行Service方法的时候，开始开启事务和Session，Service方法结束或回滚提交事务，会自动关闭Session。

在Service里面查询列表加载对象的时候，但是其相关连的对象并没有加载，但是Session关闭了，关联对象最终没有加载，在页面中用到了懒加载属性，但是是在之前加载的，且Session已经关闭了，所以有了懒加载异常，说没有Session。

### 解决方案

![图二](http://upload-images.jianshu.io/upload_images/1540531-fee5f69b942018e0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

从上面的异常说明中可以得知，主要原因是在页面中没有Session，那么我们可以使Session不关闭，不关闭Session又会出现问题，那么我们就在整个请求的过程中添加一个过滤器或者拦截器，过滤器或拦截器是先进后出。我们在过滤器或拦截器中关闭Session，也就是在当页面显示一些数据后，再在过滤器或拦截器里面关闭Session就可以了。但是需要设置当事务提交之后，不需要关闭Session。在spring中已经有一个过滤器可以帮助我们在过滤器中关闭Session了。OpenSessionInViewFilter

### 配置方案

#### 第一步：web.xml配置

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://xmlns.jcp.org/xml/ns/javaee" xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd"
	id="WebApp_ID" version="3.1">
	<display-name>OA</display-name>

	<!-- 配置spring的监听器，用于初始化spring对象 -->
	<context-param>
		<param-name>contextConfigLocation</param-name>
		<param-value>classpath:applicationContext*.xml</param-value>
	</context-param>

	<listener>
		<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
	</listener>
	
	<!-- 配置Spring的用于解决懒加载问题的过滤器，一定要配置在Struts2之前 -->
	<filter>
		<filter-name>OpenSessionInViewFilter</filter-name>
		<filter-class>org.springframework.orm.hibernate4.support.OpenSessionInViewFilter</filter-class>
	</filter>
	
	<filter-mapping>
		<filter-name>OpenSessionInViewFilter</filter-name>
		<url-pattern>*.action</url-pattern>
	</filter-mapping>

	<!-- 配置Struts2的主过滤器 -->
	<filter>
		<filter-name>struts2</filter-name>
		<filter-class>org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter</filter-class>
	</filter>

	<filter-mapping>
		<filter-name>struts2</filter-name>
		<url-pattern>/*</url-pattern>
	</filter-mapping>

</web-app>
```

**注意:**拦截的是所有的action，而且在Action里面调用的是Service，与struts的配置Action的扩展名一样

#### 第二步：struts配置

```xml
<!-- 配置扩展名为action -->
<constant name="struts.action.extension" value="action" />
```

> 不过，当整个系统中出现两个请求的，并且不是一个请求的时候，还是会出现懒加载异常。比如，一个是SSH框架里面的Struts2里面的请求，里面已经已经通过OpenSessionInViewFilter解决的懒加载异常，但是当系统需要的Servlet的监听器里面需要初始化某些数据的时候，而且这些数据与其他数据有关系的时候，还是会出现懒加载异常，所以还要在实体配置文件中配置lazy="false"属性。
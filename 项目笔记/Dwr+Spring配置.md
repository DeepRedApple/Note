# Spring+dwr整合

# 1、Dwr介绍：

DWR（Direct Web Remoting）是一个用于改善web页面与Java类交互的远程服务器端Ajax开源框架,可以帮助开发人员开发包含AJAX技术的网站.它可以允许在浏览器里的代码使用运行在WEB服务器上的JAVA函数,就像它就在浏览器里一样。

# 2、Jar包

如果是maven工程加入以下依赖：

&lt;!-- dwr --&gt;

```xml
<!-- dwr -->
<dependency>
    <groupId>org.directwebremoting</groupId>
    <artifactId>dwr</artifactId>
    <version>3.0.M1</version>
</dependency>

```


如果不是maven工程则需要加入; dwr-3.0.M1.jar

# 3、Dwr servlet

在web.xml中加入：

```xml
<servlet>   
  <servlet-name>dwr-invoker</servlet-name>   
  <servlet-class>uk.ltd.getahead.dwr.DWRServlet</servlet-class>   
  <!-- 是否允许调试，如果要在浏览器中调试则必须设置为true --> 
  <init-param>   
   <param-name>debug</param-name>   
   <param-value>true</param-value>   
  </init-param>   
  <!-- 如果允许跨域请求，则必须将此值设置为false，默认值为true --> 
   <init-param>
            <param-name>crossDomainSessionSecurity</param-name>
            <param-value>false</param-value>
     </init-param>
     <init-param>
           <param-name>allowScriptTagRemoting</param-name>
           <param-value>true</param-value>
     </init-param>
 </servlet>   
 <servlet-mapping>   
  <servlet-name>dwr-invoker</servlet-name>   
  <url-pattern>/dwr/*</url-pattern>   
 </servlet-mapping>   
```

# 4、dwr.xml

在WEB-INF下配置dwr.xml文件：

```xml-dtd
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE dwr PUBLIC "-//GetAhead Limited//DTD Direct Web Remoting 2.0//EN" "http://getahead.ltd.uk/dwr/dwr30.dtd">
<dwr>
  <allow>
    <create creator="spring" javascript="dwrService">
      <param name="beanName" value="dwrService" />
    </create>
  </allow>
</dwr>
```

- Creator:

creator属性的值可以是new,struts,spring......因为此处是整合spring来做的，所以设置成&quot;spring&quot;

如果creator为new则param为：

&lt;param name=&quot;class&quot; value=&quot;类路径名&quot;/&gt;

- javascript=&quot;dwrService&quot;

规定网页访问js地址：

&lt;script type=&#39;text/javascript&#39; src=&#39;工程路径/dwr/interface/ dwrService.js&#39;&gt;&lt;/script&gt;

前这的/dwr/就是指定走dwr 的servlet

- 指向spring的bean

&lt;param name=&quot;beanName&quot; value=&quot;dwrService&quot; /&gt;

例如：

dwrService即在spring中定义的bean

```java
public String testdwr() throws Exception{
		return "helloworld";
}
```


# 5、使用dwr

网页中加入dwr的js引用。

```html
<script type='text/javascript' src='工程路径/dwr/engine.js'></script>   
<script type='text/javascript' src='工程路径/dwr/util.js'></script>  
<script type='text/javascript' src='工程路径/dwr/interface/dwrService.js'></script>  
```

上边两行红色为固定写法，使用dwr必须引用engine.js和util.js

第三行为编写的dwr方法，其中&quot;工程路径/dwr/interface/&quot;是固定的，后边的dwrService.js是dwr.xml中定义的javascript=&quot;dwrService&quot;。

调用dwr方法：

```javascript
dwrService.testdwr({
		     callback:function(data) {
		     	alert(data);
		     }});
```


dwrService为dwr.xml中定义的javascript=&quot;dwrService&quot;，testdwr为spring bean中定义的方法。Callback为回调函数。

一个传参数的例子：

Bean的方法：

```java
public String testdwrparam(String name) throws Exception{
	return "helloworld";+name;
}
```

页面调用：

```javascript
dwrService. testdwrparam (‘张三’,{
		     callback:function(data) {
		     	alert(data);
		     }});
```


实验结果发现，dwr 不支持重载函数/方法，有兴趣的试验下。


# 一、contextMap中的数据操作

| root根：List |
| :--------- |
| 元素1        |
| 元素2        |
| 元素3        |
| 元素4        |
| 元素5        |

contextMap：Map

|     key     | value |
| :---------: | :---: |
| application |  Map  |
|   session   |  Map  |
|   request   |  Map  |
|    attr     |  Map  |



##         1、存数据：

  需要熟悉ActionContext和valueStack的API。框架为我们存数据。

###                 1.1、利用ActionContext存数据

![](http://upload-images.jianshu.io/upload_images/1540531-6ef28097ddac6963.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

###                 1.2、利用ValueStack存数据

​	a、如何获取ValueStack：

![](http://upload-images.jianshu.io/upload_images/1540531-b184d048b449ce84.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

​	b、ValueStack中的getRoot()方法：

![](http://upload-images.jianshu.io/upload_images/1540531-fd72d8346a59476b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

​	c、CompoundRoot是什么：

![](http://upload-images.jianshu.io/upload_images/1540531-b7bf75fd2da82958.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

​	d、栈操作：

![](http://upload-images.jianshu.io/upload_images/1540531-45f76f8d8d5102b7.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

##         2、取数据：用Struts2的标签（OGNL表达式)在JSP上（用的最多）

   使用OGNL表达式来去，struts2的OGNL表达式必须写在struts2标签中。

###                 2.1、使用s:property取数据

​	a、取contextMap中的数据，需使用#

![](http://upload-images.jianshu.io/upload_images/1540531-88babbe64214a94a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](http://upload-images.jianshu.io/upload_images/1540531-6f6302effd737f25.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](http://upload-images.jianshu.io/upload_images/1540531-a082d42940b49c32.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

​	b、取contextMap里面ValueStack中对象的属性：直接写属性名

![](http://upload-images.jianshu.io/upload_images/1540531-a986103385cafcdb.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](http://upload-images.jianshu.io/upload_images/1540531-b4135ddda5935417.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

如果遇有对象属性重名，可以通过OGNL表达式，选择查找的起始位置

![](http://upload-images.jianshu.io/upload_images/1540531-c485522066673614.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](http://upload-images.jianshu.io/upload_images/1540531-126b4f7fbefb2c1e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](http://upload-images.jianshu.io/upload_images/1540531-559e0eef06acee58.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](http://upload-images.jianshu.io/upload_images/1540531-3905f8317dff622f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

特殊说明：当s:property不给定value属性时，默认取栈顶对象。

![](http://upload-images.jianshu.io/upload_images/1540531-f8364f6429d3d04a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](http://upload-images.jianshu.io/upload_images/1540531-6eecfeede5a7574f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

OGNL的使用总结：

​	1.取根中对象的属性，不使用#。

​	2.取contextMap中key的值，要使用#。

###                 2.2、ValueStack的其他方法：

​	a、setValue方法

![](http://upload-images.jianshu.io/upload_images/1540531-d8aeaba41a9e45dc.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](http://upload-images.jianshu.io/upload_images/1540531-f1f509f7cd288053.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](http://upload-images.jianshu.io/upload_images/1540531-f8af8ed5e3c59b29.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

在Jsp页面上获取【李四】 

![](http://upload-images.jianshu.io/upload_images/1540531-ffe39b545f2df8c1.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](http://upload-images.jianshu.io/upload_images/1540531-073aa94849fb6310.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

​	b、set方法

![](http://upload-images.jianshu.io/upload_images/1540531-27b6c0df47b1091b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](http://upload-images.jianshu.io/upload_images/1540531-2001450fe20d06ed.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](http://upload-images.jianshu.io/upload_images/1540531-dffae51ffaea3981.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

细节问题：

当按照如下方式，往根中存放数据时，根中元素是什么顺序？

![](http://upload-images.jianshu.io/upload_images/1540531-cba55a47b6b183d6.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

使用\<s:debug/\>标签查看：

![](http://upload-images.jianshu.io/upload_images/1540531-6dc2d208fb94a150.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

​	c、findValue：我们在Jsp上调用的都是findValue

![](http://upload-images.jianshu.io/upload_images/1540531-619c54cca5844831.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](http://upload-images.jianshu.io/upload_images/1540531-c6113bb9832aecd1.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

# 二、Struts2对EL的改变

##         1、Struts2中使用EL的问题：

   **前提：**

我们应该知道，如果我们没有往值栈(根)中放入数据的话，那么我们的动作类默认是在值栈的栈顶。

![](http://upload-images.jianshu.io/upload_images/1540531-9fe0c209b59af591.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](http://upload-images.jianshu.io/upload_images/1540531-1e42dd1d147324e3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

问题：

我们放到请求域中的属性，使用EL表达式取出来了。但是放到应用域中的属性，使用EL表达式没取出来。


##         2、关于EL问题的分析：

分析：


我们知道EL表达式是从四大域对象中依次查找属性。搜索范围是由小到大。page Scope————>request Scope————>sessionScope————>application Scope

但是通过测试发现，搜索完request范围后就没有继续搜索，而是返回了ValueStack中栈顶对象name属性的值。

![](http://upload-images.jianshu.io/upload_images/1540531-cdd4a157060186ec.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](http://upload-images.jianshu.io/upload_images/1540531-de3cfaa7eb039e2a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

##         3、Struts2中EL查找顺序改变总结：

EL表达式： page Scope————>request Scope————>sessionScope————>application Scope

 OGNL表达式：page Scope————>request Scope————> valueStack（根中）————>contextMap ————>sessionScope————>application Scope

##         4、OGNL的特殊说明:

![](http://upload-images.jianshu.io/upload_images/1540531-7194d87ac9081fdb.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](http://upload-images.jianshu.io/upload_images/1540531-0325983a1c054f1a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

注意：以下内容知道即可。

![](http://upload-images.jianshu.io/upload_images/1540531-cf4a6346c7ef2b2c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

# 三、OGNL配合通用标签的其他使用

##         1、iterator标签（很重要）

![](http://upload-images.jianshu.io/upload_images/1540531-c48a56c726e505df.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](http://upload-images.jianshu.io/upload_images/1540531-80105c22fd38a263.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](http://upload-images.jianshu.io/upload_images/1540531-a18eddec92a65c3a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

##         2、OGNL投影（了解）

###                 2.1、使用过滤条件投影

![](http://upload-images.jianshu.io/upload_images/1540531-f83a664bb86c4f8b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

###                 2.2、投影指定属性

![](http://upload-images.jianshu.io/upload_images/1540531-dc219a5d085256f6.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

##         3、Struts2中#,$,%符号的使用(重要)

###                 3.1、#

​	a、取contextMap中key时使用，例如<s:property value="#name" />

​	b、OGNL中创建Map对象时使用，例如：<s:radio list="#{'male':'男','female':'女'}" />


###                 3.2、$

 	a、在JSP中使用EL表达式时使用，例如${name}

​	b、在xml配置文件中，编写OGNL表达式时使用，例如文件下载时，文件名编码。

​	struts.xml——>${@java.net.URLEncoder.encode(filename)}


###                 3.3、%

 	在struts2中，有些标签的value属性取值就是一个OGNL表达式，例如<s:property value="OGNL Expression" />

​	还有一部分标签，value属性的取值就是普通字 符串，例如<s:textfield value="username"/>，如果想把一个普通的字符串强制看成时OGNL，就需要使用%{}把字符串套起来。

例如&lt;s:textfield value=&quot;%{username}&quot;/&gt;。当然在&lt;s:property value=&quot;%{OGNL Expression}&quot; /&gt;也可以使用，但不会这么用。


##         4、其他标签

###                 4.1、set标签

![](http://upload-images.jianshu.io/upload_images/1540531-03034499be3263c0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](http://upload-images.jianshu.io/upload_images/1540531-ef559c3b99b8c9b0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

###                 4.2、action标签

![](http://upload-images.jianshu.io/upload_images/1540531-47d70a673e55d1f7.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](http://upload-images.jianshu.io/upload_images/1540531-2e9bb2fc8e79fa96.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

###                 4.3、if标签，elseif标签 else标签

![](http://upload-images.jianshu.io/upload_images/1540531-a6a78219d8ced7d2.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](http://upload-images.jianshu.io/upload_images/1540531-23b2e6326e869ebd.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

###                 4.4、url和a标签（很有用）

![](http://upload-images.jianshu.io/upload_images/1540531-4644575ff47c4915.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


# 四、Struts2的UI标签和主题

##         1、Struts2中UI标签的优势

自动的数据回显和错误提示功能

自带的简单样式和排版


##         2、表单标签的通用属性

说明：UI标签中value的取值一般都是字符串。

###                 2.1、UI标签的通用属性

![](http://upload-images.jianshu.io/upload_images/1540531-bf3eb258bdeb1448.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

###                 2.2、关于checkboxlist的使用：

![](http://upload-images.jianshu.io/upload_images/1540531-0ae421ca26a4243a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](http://upload-images.jianshu.io/upload_images/1540531-3f6c7684c2f923b5.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](http://upload-images.jianshu.io/upload_images/1540531-2c53f79fb86ff97c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](http://upload-images.jianshu.io/upload_images/1540531-66d0d22185d14cc8.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

###                 2.3、UI标签的小案例以及模型驱动的分析

![](http://upload-images.jianshu.io/upload_images/1540531-e04a942507d28523.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](http://upload-images.jianshu.io/upload_images/1540531-cedb752ed23b09e9.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](http://upload-images.jianshu.io/upload_images/1540531-c1f07c9fd2dad6db.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

##         3、UI标签的模板（主题）

###                 3.1、struts2中默认主题

 默认主题 的名称是XHTML，都是在struts的默认属性文件中定义着:default.properties

![](http://upload-images.jianshu.io/upload_images/1540531-f4fdc48f396e1e25.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](http://upload-images.jianshu.io/upload_images/1540531-d32cf381f03b4019.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](http://upload-images.jianshu.io/upload_images/1540531-e582ece52cd6e6b4.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

###                 3.2、更改默认主题

​	a、更改表单某个元素的默认主题：使用的是表单元素的theme属性。

![](http://upload-images.jianshu.io/upload_images/1540531-c26d60e69f408c7f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

​	b、更改表单所有主题：使用的是form标签的theme属性。

![](http://upload-images.jianshu.io/upload_images/1540531-a9cd4b93aca7e997.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

​	c、更改全站所有表单主题：是在struts.xml配置文件中，覆盖原有主题的设置。

![](http://upload-images.jianshu.io/upload_images/1540531-e3c963d991b449f5.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

# 五、防止表单重复提交（拦截器）

##         1、回顾之前的解决办法：

![](http://upload-images.jianshu.io/upload_images/1540531-fc940ccecf1e2d50.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

##         2、Struts2中的解决办法：

###                 2.1、使用重定向

![](http://upload-images.jianshu.io/upload_images/1540531-5291f098c79b39bf.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

遗留的问题：防不住后退，再提交。

###                 2.2、使用\<s:token/\>生成令牌配合token拦截器

![](http://upload-images.jianshu.io/upload_images/1540531-648f29c3f34ee075.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](http://upload-images.jianshu.io/upload_images/1540531-aebecb838603112f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

点击后退的时候，会提示：

![](http://upload-images.jianshu.io/upload_images/1540531-758aaf26bea4562d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

配置结果视图：

![](http://upload-images.jianshu.io/upload_images/1540531-066156ce33f02b16.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

遗留的问题：此种解决方式，是产生了错误之后再告知用户，你错了。

###                 2.3、使用\<s:token/\>生成令牌配合tokensession拦截器

![](http://upload-images.jianshu.io/upload_images/1540531-22858903ea056309.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
# 一、jQuery介绍

## 1.1JS类库

- JavaScript 库封装了很多预定义的 **对象** 和实用 **函数** 。能帮助使用者建立有高难度交互客户端页面, 并且兼容各大浏览器。

## 1.2当前流行的JavaScript 库有：

- jQuery，最流行
- EXT\_JS，2.0开始收费
- Dojo ，很多js单独文件，优化：打包。（常见：开发小图标，一张图片）
- Prototype，对js扩展，框架开发。
- YUI(Yahoo! User Interface) ，taobao之前使用。
- 淘宝UI：http://docs.kissyui.com/
- Bootstrap ，来自Twitter，是目前很受欢迎的前端框架。Bootstrap 是基于HTML、CSS、JAVASCRIPT 的，它简洁灵活，使得Web 开发更加快捷。基于jQuery 一个UI工具

## 1.3jQuery介绍

- JQuery是继prototype之后又一个优秀的Javascript库。它是轻量级的js库，它兼容CSS3，还兼容各种浏览器（IE 6.0+, FF 1.5+, Safari 2.0+, Opera 9.0+），jQuery2.0及后续版本将不再支持IE6/7/8浏览器。jQuery使用户能更方便地处理HTML（标准通用标记语言下的一个应用）、events、实现动画效果，并且方便地为网站提供AJAX交互。jQuery还有一个比较大的优势是，它的文档说明很全，而且各种应用也说得很详细，同时还有许多成熟的插件可供选择。jQuery能够使用户的html页面保持代码和html内容分离，也就是说，不用再在html里面插入一堆js来调用命令了，只需要定义id即可。
- 轻量级：依赖程序少，占用的资源的少
- 特点：js代码和html代码分离
- jQuery已经成为最流行的javascript库，在世界前10000个访问最多的网站中，有超过55%在使用jQuery。
- 由美国人John Resig在2006年1月发布
- jQuery是免费、开源的
- jQuery分类：

   WEB版本：我们主要学习研究

   UI版本：集成了UI组件

   mobile版本：针对移动端开发

   qunit版本：用于js测试的



## 1.4版本介绍

![](http://upload-images.jianshu.io/upload_images/1540531-27a34af3af551d85.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](http://upload-images.jianshu.io/upload_images/1540531-cc97fdd1f3d264f6.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 1.5优点

- 核心理念是write less,do more(写得更少,做得更多)

    ![](http://upload-images.jianshu.io/upload_images/1540531-61471aadf95652a9.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

- 轻量级：源码1.11.js大小是286kb，压缩班1.11.min.js大小是94.1k。如果使用GZIP压缩更小。

- 兼容各种浏览器(IE 6.0+, FF 1.5+, Safari 2.0+, Opera 9.0+）

- jQuery的语法设计可以使开发者更加便捷

- 例如操作文档对象、选择DOM元素、制作动画效果、事件处理、使用Ajax以及其他功能

- jQuery能够使用户的html页面保持代码和html内容分离

- 不用再在html里面插入一堆js来调用命令了，只需要定义id即可

- jQuery提供API让开发者编写插件，有许多成熟的插件可供选择

- 文档说明很全

# 二、基本语法

## 2.1jQuery语法

```html
	<script type="text/javascript">
		//js 执行时，有加载顺序
		
		/* jQuery获得数据
		 * * 语法：$("选择器")   == jQuery("选择器")
		 * 
		 */
		
		var username = $("#username");
		// * val()函数 用于获得 value属性的值
		alert(username.val());
		
	</script>
```

## 2.2jQuery对象和dom对象转换

```html
<script type="text/javascript">
		//1 使用javascript获得value值
		var username = document.getElementById("username");
		//alert(username.value);
		
		//2 将 dom对象 转换 jQuery对象
		// * 语法：$(dom对象)
		// * 建议：jQuery对象变量名，建议为$开头
		var $username = $(username);
//		alert($username.val());
		
		//3 将 jQuery对象 转换 dom对象
		//3.1 jQuery对象内部使用【数组】存放所有的数据，可以数组的下标获取（索引）
		var username2 = $username[0];
		alert(username2.value);
		//3.2 jQuery提供函数 get() 转换成dom对象
		var username3 = $username.get(0);
		alert(username3.value);
	</script>
```



# 三、选择器

## 3.1基本【掌握】

![](http://upload-images.jianshu.io/upload_images/1540531-c7c6596538f9e364.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

id ，id选择器，&lt;xxx id=&quot;&quot;&gt; 通过id值获得元素

element，标签选择器，&lt;xxx&gt; 通过标签名获得元素

**.** class ，类选择器，&lt;xxx class=&quot;&quot;&gt; 通过class值获得元素。注意：使用点开头

s1,s2,...  多选择器，将多个选择器的结果添加一个数组中。

有

## 3.2层级

![](http://upload-images.jianshu.io/upload_images/1540531-754417653ccb7e14.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

A  B ，获得A元素内部所有的B后代元素。（爷孙）

A &gt; B ，获得A元素内部所有的B子元素。（父子）

A + B ，获得A元素后面的第一个兄弟B。（兄弟）

A ~ B ，获得A元素后面的所有的兄弟B。（兄弟）



## 3.3基本过滤

- 过滤选择器格式 &quot;：关键字&quot;

![](http://upload-images.jianshu.io/upload_images/1540531-81fbb7c1582d8fa6.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

:first  , 第一个

:last  ,最后一个

:eq(index) ，获得指定索引

:gt(index) 大于

:lt(index) 小于

:even 偶数，从0 开始计数。例如：查找表格的1、3、5...行（即索引值0、2、4...）

:odd 奇数

:not(selector) 去除所有与给定选择器匹配的元素

:header  获得所有标题元素。例如：&lt;h1&gt;...&lt;h6&gt;

:animated  获得所有动画

:focus 获得焦点

## 3.4内容过滤

![](http://upload-images.jianshu.io/upload_images/1540531-a503d0f081282b82.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

:empty  当前元素是否为空（是否有标签体--子元素、文本）

:has(...)  当前元素，是否含有指定的子元素

:parent 当前元素是否是父元素

:contains( text ) 标签体是否含有指定的文本

## 3.5可见性过滤[掌握]

![](http://upload-images.jianshu.io/upload_images/1540531-48d9a94423ee8f77.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

:hidden 隐藏。特指&lt;xxx style=&quot;display:none";>  ，获得&lt;input type=&quot;hidden"&gt;

:visible 可见（默认）

## 3.6属性【掌握】

![](http://upload-images.jianshu.io/upload_images/1540531-90d2caf5cc183a0e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

[属性名]    获得指定的属性名的元素

[属性名=值]   获得属性名等于指定值的的元素【1】

[属性名!=值]   获得属性名不等于指定值的的元素

\[as1\]\[as2\][as3]....  复合选择器，多个条件同时成立。类似where ...and...and【2】

[属性名^=值]   获得以属性值开头的元素

[属性名$=值]   获得以属性值结尾的元素

[属性名\*=值]   获得含有属性值  的元素

## 3.7子元素过滤

![](http://upload-images.jianshu.io/upload_images/1540531-099cda60b37cf136.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

:nth-child(index)  ，获得第几个孩子，从1开始。

:first-child ，获得第一个孩子

:last-child ，获得最后孩子

:only-child ，获得独生子

## 3.8表单过滤

![](http://upload-images.jianshu.io/upload_images/1540531-3a1cf359f09b371f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

:input  所有的表单元素。（&lt;input&gt; / &lt;select&gt; / &lt;textarea&gt; / &lt;button&gt;）

:text    文本框&lt;input type=&quot;text&quot;>

:password  密码框&lt;input type=&quot; password &quot;>

:radio   单选&lt;input type=&quot;radio&quot;>

:checkbox  复选框&lt;input type=&quot;checkbox&quot;>

:submit   提交按钮&lt;input type=&quot;submit&quot;>

:image   图片按钮&lt;input type=&quot;image&quot; src=&quot;">

:reset   重置按钮&lt;input type=&quot;reset&quot;">

:file    文件上传&lt;input type=&quot;file&quot;>

:hidden   隐藏域&lt;input type=&quot;hidden&quot;>  ,还可以获得&lt;xxx style=&quot;display:none&quot;>

​	其他值：&lt;br&gt; &lt;option&gt;  ,存在浏览器兼容问题


:button   所有普通按钮。&lt;button &gt;  或&lt;input type=&quot;button&quot;>

## 3.9表单对象属性过滤【掌握】

![](http://upload-images.jianshu.io/upload_images/1540531-b5e9ba79c353e734.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

:enabled  可用

:disabled  不可用。&lt;xxx disabled=&quot;disabled&quot;> 或&lt;xxx disabled=&quot;"&gt;  或  &lt;xxx disabled&gt;

:checked  选中（单选框radio、复选框checkbox）

:selected  选择（下拉列表select option）



# 四、属性和CSS

## 4.1属性【掌握】

![](http://upload-images.jianshu.io/upload_images/1540531-c3c513309056cc89.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

attr(name)   获得指定属性名的值

attr(key ,val ) 给一个指定属性名设置值

attr(prop ) 给多个属性名设置值。参数：prop  json数据

 {k : v , k : v , .....}

removeAttr(name)  移除指定属性

## 4.2CSS类

- &lt;xxx class=&quot;a  b  c  d  my &quot;>

![](http://upload-images.jianshu.io/upload_images/1540531-ae88e92d486b7eb4.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

addClass(&quot;my&quot;)  追加一个类

removeClass(&quot;my&quot;)  将指定类移除

toggleClass(&quot;my&quot;)  如果有my将移除，如果没有将添加。

## 4.3HTML代码/文本/值【掌握】

![](http://upload-images.jianshu.io/upload_images/1540531-b9f5533016115cd5.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

val()  获得value的值

val(text) 设置value的值

html()  获得html代码，含有标签

html(...) 设置html代码，如果有标签，将进行解析。

text() 获得文本值，将标签进行过滤

text(...) 设置文本值，如果有标签，将被转义  --&gt;   &lt;  &amp;lt;   &amp;  &amp;amp;  &gt;  &amp;lt;

## 4.4CSS

- &lt;xxx style=&quot;key:value; key:value; &quot;>

![](http://upload-images.jianshu.io/upload_images/1540531-dea59b865614f68c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

css(name)  获得指定名称的css值

css(name ,value) 设置一对值

css(prop) 设置一组值

## 4.5位置

![](http://upload-images.jianshu.io/upload_images/1540531-d0d908920eef6593.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

offset()  获得坐标，返回JSON对象，{&quot;top&quot;:200, &quot;left&quot; : 100}

![](http://upload-images.jianshu.io/upload_images/1540531-75c7f475626ef955.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


offset(...) 设置坐标。例如：$(this).offset({&quot;top&quot;:0 , &quot;left&quot; : 0})

scrollTop()  垂直滚动条滚过的距离

scrollLeft()  水平滚动条滚过的距离

## 4.6尺寸

![](http://upload-images.jianshu.io/upload_images/1540531-46cd9aeace593355.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

height([...]) 获得或设置高度

width([...])获得或设置宽度

# 五、文档处理

## 5.1内部插入【掌握】

![](http://upload-images.jianshu.io/upload_images/1540531-0a2caa3c9cc8bed8.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

A.append(B)  将B插入到A的内部后面（之后的串联操作，操作A）

 &lt;A&gt;

  ....

  &lt;B&gt;</B&gt;

 &lt;A&gt;

A.prepend(B) 将B插入到A的内部前面

 &lt;A&gt;

  &lt;B&gt;</B&gt;

  ....

 &lt;A&gt;

A.appendTo(B) 将A插入到B的内部后面（之后的串联操作，操作A）

A.prependTo(B) 将A插入到B的内部前面

## 5.2外部插入【掌握】

![](http://upload-images.jianshu.io/upload_images/1540531-d91326e013d57937.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

A.after(B)  , 将B插入到A后面（同级）

 &lt;A&gt;</A&gt;

 &lt;B&gt;</B&gt;

A.before(B) ，将B插入到A前面

 &lt;B&gt;</B&gt;

 &lt;A&gt;</A&gt;

A.insertAfter(B) , 将A插入到B后面（同级）

 &lt;B&gt;</B&gt;

 &lt;A&gt;</A&gt;

A.insertBefore(B) 将A插入到B前面

 &lt;A&gt;</A&gt;

 &lt;B&gt;</B&gt;

## 5.3删除[掌握]

![](http://upload-images.jianshu.io/upload_images/1540531-24975ec54baf411e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

empty()  清空标签体

remove() 删除当前对象。如果之后再使用，元素本身保留，绑定事件或绑定数据都会被移除

detach() 删除当前对象。如果之后再使用，元素本身保留，绑定事件或绑定数据都保留

- 绑定数据

 data(name) 获得

 data(name,value) 设置




## 5.4复制

![](http://upload-images.jianshu.io/upload_images/1540531-110f382ce010a158.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

clone() 克隆

even ：指示事件处理函数是否会被复制。V1.5以上版本默认值是：false

## 5.5替换

![](http://upload-images.jianshu.io/upload_images/1540531-75e9eafc345f2359.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

A.replaceWith(B) ，使用A将B替换掉

A.replaceAll(B) ，使用B将A替换掉

## 5.6包裹

![](http://upload-images.jianshu.io/upload_images/1540531-52d69b9a76cd27cc.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

A.wrap(B) ，使用B将每一个A进行包裹（多个B）

 &lt;B&gt;<A&gt;</A&gt;</B&gt;

 &lt;B&gt;<A>&lt;/A&gt;</B&gt;

![](http://upload-images.jianshu.io/upload_images/1540531-166254d2561f0101.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


A.wrapAll(B) ，使用B将所有A进行包裹（一个B）

 &lt;B&gt;

  &lt;A&gt;</A&gt;

  &lt;A&gt;</A&gt;

 &lt;/B&gt;

![](http://upload-images.jianshu.io/upload_images/1540531-c9adab6a6548d16b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


A.wrapInner(B) ,使用B将每一个A的标签体包裹。

&lt;A&gt;<B&gt;。。。&lt;/B&gt;</A&gt;

<A><B>。。。</B></A>

![](http://upload-images.jianshu.io/upload_images/1540531-29792897bab796fe.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


A.unwrap() ，将A的父元素删除，自己留着
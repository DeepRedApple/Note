# 一、回顾

- 语法：

 \$(&quot;选择器&quot;)  ，$(dom对象)   ，$(&quot;&lt;div&gt;&quot;)

- 选择器：

 基本：#id、element、.class、s1,s2,... 、\*

 层级：A B、A&gt;B、A+B、A~B

 基本过滤：:first  :last  :eq()  :gt()  :lt()   :even :odd  :header  :animated  :focus

 内容过滤：:contains()   :empty   :parent   :has()

 可见过滤：:visible  :hidden

 属性：[attr]  [attr=val]  [attr!=val]  [attr^=val]  [attr$=val]  [attr\*=val]  [attr=val][][]

 子元素：:nth-child()  :first-child   :last-child  :only-child

 表单过滤：:input  :text  :password  :radio :checkbox  :file  :submit :reset :image  :button  :hidden

 表单对象属性过滤：:enabled  :disabled  :checked  :selected

- 属性和CSS

 attr()  、removeAttr()

 addClass()  removeClass()  toggleClass()

 val()  html()  text()

 css()

 offset()  --&gt; {top  ,  left}

 scrollTop()  /scrollLeft()

 width()  height()

- 文档处理

 内部：append  prepend  appendTo  prependTo

 外部：after  before  insertAfter insertBefore

 删除：empty   remove  detach   --&gt; data()

 复制：clone(true)

 替换：replaceWith  replaceAll

 包裹：wrap  wrapAll  wrapInner unWrap()

# 二、筛选

- 选择器可以完成功能，筛选提供相同函数。

 选择器 :first

 筛选 first()

- 对比：

 $(&quot;div:first&quot;) 直接获得第一个div （永远只能操作第一个）

 $(&quot;div&quot;).first() 先获得所有的div，从所有的中筛选出第一个。（可以操作第一个，也可以操作所有）

## 2.1过滤

![](http://upload-images.jianshu.io/upload_images/1540531-5b66fc71224b7d1b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

eq(index | -index)  类型:eq()

 index：正数，从头开始获得指定索引的元素。这个元素的位置是从0算起。0表示第一个

 -index：负数，从尾开始获得指定索引的元素。1算起。-1表示最后一个

first() 第一个  :first

last() 最后一个  :last

is()  判断

hasClass() 判断是否是指定class 。&lt;xxx class=&quot;my&quot;>

 这其实就是is(&quot;.&quot; + class)。

filter()  筛选出与指定表达式匹配的元素集合

not() 将指定内容删除

has()  子元素是否有

slice(start , end)  截取元素，[2,4)  --&gt; 2,3

map()  jQuery对象拆分成  jQuery对象数组

## 2.2查找

![](http://upload-images.jianshu.io/upload_images/1540531-d27b77d8b01f096a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

&lt;A&gt;

 &lt;B&gt;

  &lt;C&gt;</C&gt;

  &lt;D&gt;</D&gt;

  &lt;E&gt;</E&gt;

  &lt;F&gt;</F&gt;

 &lt;/B&gt;

&lt;/A&gt;

B.children([...])   获得所有的子元素。CDEF

A.find(D)  从指定的区域查询指定元素。D

D.next() 下一个兄弟。E

D.nextAll() 后面的所有兄弟。EF

E.prev() 上一个兄弟。D  （previous）

E.prevAll()  前面的所有兄弟。CD

E.siblings() 所有兄弟。CDF

E.parent()  父元素。B

E.closest(A)  向上获得指定的父元素（含自己），如果获得返回一个对象，如果没有没有对象。

C.nextUntil(E)  获得后面所有兄弟，直到指定条件位置。DE

F.prevUntil(D)  获得前面所有兄弟，直到指定条件位置。DE

E.parents()   获得所有的父元素。AB

- closest和parents的主要区别是：

1，前者从当前元素开始匹配寻找，后者从父元素开始匹配寻找；

2，前者逐级向上查找，直到发现匹配的元素后就停止了，后者一直向上查找直到根元素，然后把这些元素放进一个临时集合中，再用给定的选择器表达式去过滤；

3，前者返回0或1个元素，后者可能包含0个，1个，或者多个元素。

## 2.3串联

- 将多条语句，改成一条。（得瑟代码）

![](http://upload-images.jianshu.io/upload_images/1540531-164038b88f85dd25.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

A.add(B)  将A和B组合一个对象。类型$(&quot;A,B&quot;)

andSelf() ：将之前对象添加到操作集合中

 A.children().andSelf()

​	A 孩子    孩子和A


end() ：回到最近的一个&quot;破坏性&quot;操作之前

 A.children().children().end() .end()

​	 A   孩子   孙子  孩子  A

contents()  获得所有的子节点（子元素和文本）

# 三、事件

## 3.1常见事件总结

![](http://upload-images.jianshu.io/upload_images/1540531-f82d32d602cd4601.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

- jQuery 提供额外的事件，用于完善javascript缺失的
- focusin 和focusout

 focusin 获得焦点。js focus。

  focusin事件跟focus事件区别在于，他可以在父元素上检测子元素获取焦点的情况。

 focusout 失去焦点。js blur。

  focusout事件跟blur事件区别在于，他可以在父元素上检测子元素失去焦点的情况。

- mouseenter 和mouseleave

 mouseenter 鼠标移入。js mouseover

  与mouseover 事件不同，只有在鼠标指针穿过被选元素时，才会触发mouseenter 事件。如果鼠标指针穿过任何子元素，同样会触发mouseover 事件。

 mouseleave 鼠标移出。js mouseout

  与mouseout 事件不同，只有在鼠标指针离开被选元素时，才会触发mouseleave 事件。如果鼠标指针离开任何子元素，同样会触发mouseout 事件。

## 3.2页面加载

![](http://upload-images.jianshu.io/upload_images/1540531-5a52c7fa973fa1a1.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

- 方式1：标准api

```javascript
$(document).ready(function(){

});
等效
jQuery(document).ready( fn );
```

![](http://upload-images.jianshu.io/upload_images/1540531-41e12a8541cddd82.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


- 方式2：简化版

```javascript
$(function(){
});
登录
jQuery(function(){
});
```

- 源码分析（了解）

步骤一：回顾js 函数创建

 方式1：function abc(){};   abc();

 方式2：var abc = function(){} ;   abc();

 $( fn )  == jQuery( fn )  == new jQuery.fn.init( fn )

![](http://upload-images.jianshu.io/upload_images/1540531-883ce791130ee172.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


步骤二：init( fn )  --&gt;  rootjQuery.ready( fn )

![](http://upload-images.jianshu.io/upload_images/1540531-b4b1ee62abb50340.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](http://upload-images.jianshu.io/upload_images/1540531-e814a2affdd1e435.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


步骤三：

![](http://upload-images.jianshu.io/upload_images/1540531-a129b5689a27f27e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


 总结：

  $( fn )  --&gt; init( fn )  --&gt; jQuery(document).ready( fn )

## 3.3事件绑定

### 3.3.1处理

![](http://upload-images.jianshu.io/upload_images/1540531-8ab8bdfd4cf2c91c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

bind(type ,fn)  给当前对象绑定一个事件。例如：A.bind(&quot;click&quot;, fn );  类型：A.click( fn );

unbind(type ) 解绑bind绑定事件

one(type ,fn ) 给当前对象绑定一次事件。

on(events , fn) 提供绑定事件处理程序所需的所有功能。完成3个方法功能.bind(), .delegate(), 和.live().

off(events) 解绑

trigger(type) 在每一个匹配的元素上触发某类事件。例如：A.trigger(&quot;submit&quot;)  ,类似A.submit();

triggerHandler(type) 在每一个匹配的元素上触发某类事件。但不会执行浏览器默认动作，也不会产生事件冒泡。

### 3.3.2委派

![](http://upload-images.jianshu.io/upload_images/1540531-3a81566f159f726a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

live(type , fn) 绑定事件，之后动态添加同样的成员，也具有相同的事件。

die(type) 解绑事件

### 3.3.3切换

![](http://upload-images.jianshu.io/upload_images/1540531-391901ac2a4a2e84.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

hover(over , out)

 简化版，鼠标移入和移出  ，A.mouseover( fn ).mouseout( fn)  简化A.hover( fn , fn )

toggle( fn , fn , fn ,...) 执行click事件，每点击一次执行一个fn

# 四、效果|动画

## 4.1基本

- 通过改变元素高度和宽度进行显示或隐藏

![](http://upload-images.jianshu.io/upload_images/1540531-830745bc6ea8c1e5.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

show( **speed,fn** ) 显示

 参数1：speed速度。显示的时间，单位：毫秒。固定字符串：(&quot;slow&quot;,&quot;normal&quot;, or &quot;fast&quot;)

 参数2：fn 回调函数。动画效果完成之后回调函数。

hide() 隐藏

toggle() 切换

## 4.2滑动

- 通过改变元素高度  进行显示或隐藏

![](http://upload-images.jianshu.io/upload_images/1540531-cb21c65cf4d0f7aa.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

slideDown()  显示

slideUp() 隐藏

slideToggle() 切换

## 4.3淡入淡出

- 通过改变元素  透明度  进行显示或隐藏

![](http://upload-images.jianshu.io/upload_images/1540531-48ac424115f29acb.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

fadeIn() 显示

fadeOut() 隐藏

fadeToggle() 切换

fadeTo( **speed,opacity,fn** ) 指定透明度

 参数2： **opacity**  **透明度，一个0至1之间表示透明度的数字**

# 五、Ajax【掌握】

- ajax ：异步请求，浏览器地址栏不改变，进行局部刷新。

- ajax 流程分析

    ![](http://upload-images.jianshu.io/upload_images/1540531-b60f0fabce3f2f30.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

- jQuery 的ajax

![](http://upload-images.jianshu.io/upload_images/1540531-4a8c59e14d8df8ee.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

第一层：$.ajax(...) 最底层ajax请求，编写最复杂，完成功能最全的。

第二层：load() 、$.get() 、$.post()  开发中常用3个

第三层：$.getJSON()  、$.getScript() 高级开发

 $.getJSON() 可以完成js&quot;跨域&quot;请求

  域名：域名+端口+项目，js默认不能跨域请求。

 $.getScript()  动态加载js文件。之前使用&lt;script src=&quot;&quot;&gt;加载页面时，一并加载js文件

- 回调函数参数

    ![](http://upload-images.jianshu.io/upload_images/1540531-0d0c0c062096c39a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

- 工具类，解析json

    ![](http://upload-images.jianshu.io/upload_images/1540531-d75a2a13d1e9af7e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 5.1load() 

```javascript
//0.2 请求参数，采用json
				var params = {"username":"杰克", "password":"1234"};
				
				/* 1 load()函数 ，必须使用jquery对象
				 * * 格式：load(url, [data], [callback])
				 * 		参数1：url ，请求路径
				 * 		参数2：data，请求参数
				 * 		参数3：callback，回调函数
				 * * 如果没有请求参数，发送的GET请求
				 * * 如果有请求参数，发送的POST请求。请求没有中文乱码
				 * * 回调函数的参数
				 * 		参数1：data，响应数据。load()永远获得字符串，如果需要使用，必须手动转换json对象。
				 */
				$(this).load(url,params,function(data){
					//转换json对象
					var jsonData = eval("("+data+")");
					alert(jsonData.message);
				});
```

## 5.2$.get() 

```javascript
/* 2 $.get() 全局函数，发送get请求
				 * * 格式：jQuery.get(url, [data], [callback], [type])
				 * 		* 参数4：type ，返回内容格式，xml, html, script, json, text, _default。
				 * * GET请求不适合发送中文数据，存放请求的中文乱码。
				 * 		必须手动解码   new String(username.getBytes("ISO-8859-1") ,"UTF-8")
				 * * 响应数据，如果使用  application/json;charset=UTF-8 ，jQuery自动将数据转换json对象。
				 * * 响应数据，如果使用  text/html;charset=UTF-8 ，回调函数获得字符串数据，需要手动转换。
				 * 		使用“参数4”，设置"json"，jQuery将字符串 转换成 json对象
				 */
				$.get(url,params,function(data){
					alert(data);
				},"json");

```



## 5.3$.post()  

```javascript
/* 3 $.post() 全局函数，发送post请求
				 * * 格式：jQuery.post(url, [data], [callback], [type])
				$.post(url,params,function(data){
					alert(data);
				},"json")
				 */

```



## 5.4$.ajax() 

```javascript
/* 4 $.ajax() 底层功能最强大的
				 * * 格式：jQuery.ajax([settings])
				 * 		参数settings：设置所有的参数
				 * 			url:发送请求的地址
				 * 			data:发送到服务器的数据,请求参数
				 * 			type:请求方式 ("POST" 或 "GET")， 
				 * 			success:成功的回调函数，success(data, textStatus, jqXHR)
				 * 			error:请求失败时调用此函数
				 * 			dataType:预期服务器返回的数据类型
				 * 				"xml": 返回 XML 文档，可用 jQuery 处理。
				 * 				"html": 返回纯文本 HTML 信息；包含的script标签会在插入dom时执行。
				 * 				"script": 返回纯文本 JavaScript 代码。不会自动缓存结果。除非设置了"cache"参数。'''注意：'''在远程请求时(不在同一个域下)，所有POST请求都将转为GET请求。(因为将使用DOM的script标签来加载)
				 * 				"json": 返回 JSON 数据 。
				 * 				"jsonp": JSONP 格式。使用 JSONP 形式调用函数时，如 "myurl?callback=?" jQuery 将自动替换 ? 为正确的函数名，以执行回调函数。
				 * 				"text": 返回纯文本字符串
				 */
				
				$.ajax({
					"url":url,
					"data":params,
					"type":"POST",
					"success":function(data){
						alert(data);
					},
					"error":function(){
						alert("服务器繁忙，请稍后重试");
					},
					"dataType":"json"
				});

```



# 六、其他

## 6.1表单序列化

![](http://upload-images.jianshu.io/upload_images/1540531-cf587984e93ff950.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

serialize() 将表单中所有选中项拼凑成一个字符串。类似get请求参数

 例如：username=jack&amp;password=1234&amp;gender=man&amp;....

![](http://upload-images.jianshu.io/upload_images/1540531-98f4d2939d3daad0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

serializeArray()将表单中所有选中项拼凑一个json数组

![](http://upload-images.jianshu.io/upload_images/1540531-c5bd138ad05d6ed2.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](http://upload-images.jianshu.io/upload_images/1540531-35f284f5c0252189.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


## 6.2事件冒泡

![](http://upload-images.jianshu.io/upload_images/1540531-894584a0d52b6dfb.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

event.stopPropagation()

## 6.3浏览器默认动作

![](http://upload-images.jianshu.io/upload_images/1540531-cbbfb17ecd718a5d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

event.preventDefault();
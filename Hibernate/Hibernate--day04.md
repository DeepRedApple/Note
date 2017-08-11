# 一、整合log4j(了解)

- slf4j 核心jar  ：slf4j-api-1.6.1.jar 。slf4j是日志框架，将其他优秀的日志第三方进行整合。

    ![](http://upload-images.jianshu.io/upload_images/1540531-6cf6b204edb1a043.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

- 整合导入jar包

 log4j 核心包：log4j-1.2.17.jar

 过渡jar（整合jar）：slf4j-log4j12-1.7.5.jar

- 导入配置文件

 log4j.properties  ，此配置文件通知log4j 如何输出日志

- 配置文件内容：

 1.记录器

 2.输出源

 3.布局

- 记录器

 例如：log4j.rootLogger=info, stdout,file

 格式：log4j.rootLogger=日志级别, 输出源1，输出源2，。。。。

 log4j 日志级别：fatal 致命错误error 错误warn 警告info 信息debug 调试信息trace 堆栈信息（由高到底顺序）

- 输出源：

 例如：log4j.appender.file=org.apache.log4j.FileAppender

 格式：log4j.appender.输出源的名称=输出源的实现类

  名称：自定义

  实现类：log4j提供

 输出源属性例如：log4j.appender.file.File=d\:mylog.log

 输出源属性格式：log4j.appender.名称.属性=值

  每一个输出源对应一个实现类，实现类都属性（setter），底层执行setter方法进行赋值

- 常见的输出源实现类

 org.apache.log4j.FileAppender  输出文件中

  file ,表示文件输出位置

 org.apache.log4j.ConsoleAppender 输出到控制台

  Target ，表示使用哪种输出方式，在控制台打印内容，取值：System.out / System.err

- 布局  -- 确定输出格式

 例如：log4j.appender.stdout.layout=org.apache.log4j.PatternLayout

 格式：log4j.appender.数据源.layout=org.apache.log4j.PatternLayout

 布局属性：log4j.appender. 数据源.layout.ConversionPattern=值

  12:56:30,123  info

- 扩展：对指定的目录设置日志级别

 例如：log4j.logger.org.hibernate.transaction=debug

 格式：log4j.logger.包结构=级别

# 二、一对一（了解）

- .情况1：主表的主键，与从表的外键（唯一），形成主外键关系
- .情况2：主表的主键，与从表的主键，形成主外键关系（从表的主键又是外键）

### 2.0.1情况1

​	 ![](http://upload-images.jianshu.io/upload_images/1540531-e8ed17e69c213331.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](http://upload-images.jianshu.io/upload_images/1540531-4e653b4f6d4edfc5.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### 2.0.2情况2

 ![](http://upload-images.jianshu.io/upload_images/1540531-7c0472e1281e8119.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](http://upload-images.jianshu.io/upload_images/1540531-e5470f7b353f68ff.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

# 三、二级缓存【掌握】

## 3.1介绍

### 3.1.1缓存

 缓存(Cache): 计算机领域非常通用的概念。它介于 **应用程序** 和 **永久性数据存储源** (如硬盘上的文件或者数据库)之间，其作用是降低应用程序直接读写硬盘（永久性数据存储源）的频率，从而提高应用的运行 **性能** 。缓存中的数据是数据存储源中数据的拷贝。缓存的物理介质通常是 **内存**

 缓存：程序&lt;--（内存）--&gt;硬盘

### 3.1.2什么是二级缓存

- hibernate 提供缓存机制：一级缓存、二级缓存

 一级缓存：session级别缓存，在一次请求中共享数据。

 二级缓存：sessionFactory级别缓存，整个应用程序共享一个会话工厂，共享一个二级缓存。

- SessionFactory的缓存两部分：        内置缓存：使用一个Map，用于存放配置信息，预定义HQL语句等，提供给Hibernate框架自己使用，对外只读的。不能操作。

 外置缓存：使用另一个Map，用于存放用户自定义数据。默认不开启。外置缓存hibernate只提供规范（接口），需要第三方实现类。外置缓存有成为二级缓存。

### 3.1.3二级缓存内部结构

![](http://upload-images.jianshu.io/upload_images/1540531-16e18ea51c709b62.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

- 二级就是由4部分构成
  - 类级别缓存
  - 集合级别缓存
  - 时间戳缓存
  - 查询缓存(二级缓存的第2大部分,三级缓存)

### 3.1.4并发访问策略

![](http://upload-images.jianshu.io/upload_images/1540531-536e2adefbe2ce48.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

- 访问策略：读写型（read-write）、只读型（read-only）

### 3.1.5应用场景

- 适合放入二级缓存中的数据:

 很少被修改

 不是很重要的数据, 允许出现偶尔的并发问题

- 不适合放入二级缓存中的数据:

 经常被修改

 财务数据, 绝对不允许出现并发问题

 与其他应用数据共享的数据

### 3.1.6二级缓存提供商

-  **EHCache** : 可作为进程（单机）范围内的缓存, 存放数据的物理介质可以是内存或硬盘, 对Hibernate 的查询缓存提供了支持。--支持集群。
- OpenSymphony `:可作为进程范围内的缓存, 存放数据的物理介质可以是内存或硬盘, 提供了丰富的缓存数据过期策略, 对Hibernate 的查询缓存提供了支持
- SwarmCache: 可作为集群范围内的缓存, 但不支持Hibernate 的查询缓存
- JBossCache:可作为集群范围内的缓存, 支持Hibernate 的查询缓存

![](http://upload-images.jianshu.io/upload_images/1540531-d8b3a1723aa2f634.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

X表示支持

## 3.2配置（操作）

1.导入jar包：ehcache-1.5.0.jar/ commons-logging.jar/ backport-util-concurrent.jar

2.开启二级缓存(我要使用二级缓存)

3.确定二级缓存提供商(我要使用哪个二级缓存)

4.确定需要缓存内容

​	 1&gt;配置需要缓存的类

 	2&gt;配置需要缓存的集合

5.配置ehcache自定义配置文件



### 3.2.1导入jar包

![](http://upload-images.jianshu.io/upload_images/1540531-847c6fe9ceee327f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### 3.2.2开启二级缓存

![](http://upload-images.jianshu.io/upload_images/1540531-1a5f261a8cadc1ff.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

- 在hibernate.cfg.xml 配置二级缓存

```xml
<!-- 9.1 开启二级缓存 -->
<property name="hibernate.cache.use_second_level_cache">true</property>
```

### 3.2.3确定提供商

![](http://upload-images.jianshu.io/upload_images/1540531-f18347ba24b4ef2a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

- hibernate.cfg.xml 配置

```xml
<!-- 9.2 提供商 -->
<property name="hibernate.cache.provider_class">org.hibernate.cache.EhCacheProvider</property>
```

### 3.2.4确定缓存内容

- 在hibernate.cfg.xml 确定类缓存和集合缓存配置项

![](http://upload-images.jianshu.io/upload_images/1540531-b73041cbd3b7ea6c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

- 配置

```xml
<!-- 9.3 确定缓存内容 -->
		<!-- 类缓存 -->
		<class-cache usage="read-write" class="com.itheima.a_init.Customer"/>
		<class-cache usage="read-write" class="com.itheima.a_init.Order"/>
		<!-- 集合缓存 -->
		<collection-cache usage="read-write" collection="com.itheima.a_init.Customer.orderSet"/>
```

### 3.2.5ehcache配置文件

步骤1：从jar包复制xml文件

![](http://upload-images.jianshu.io/upload_images/1540531-507f7cdbf5a30cba.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


步骤2：将xml重命名&quot;ehcache.xml&quot;

![](http://upload-images.jianshu.io/upload_images/1540531-749e059ec406f6c7.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


步骤3：将修改后的xml，拷贝到src下

![](http://upload-images.jianshu.io/upload_images/1540531-968ad12b4a0a82f6.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


## 3.3演示

### 3.3.1证明

```java
@Test
	public void demo01(){
		//1 证明二级缓存存在 
		// * 修改toString()
		// * 如果二级缓存开启，查询3 没有select语句，表示从二级缓存获得的。
		// * 将二级缓存关闭，查询3将触发select语句。
		Session s1 = factory.openSession();
		s1.beginTransaction();
		
		//1 查询id=1 -- 执行select （查询后，将数据存放在一级缓存，之后由一级缓存同步到二级缓存）
		Customer c1 = (Customer) s1.get(Customer.class, 1);
		System.out.println(c1);
		//2 查询id=1 --从一级缓存获取
		Customer c2 = (Customer) s1.get(Customer.class, 1);
		System.out.println(c2);
		
		s1.getTransaction().commit();
		s1.close();
		
		System.out.println("----------");
		
		Session s2 = factory.openSession();
		s2.beginTransaction();
		
		//3 查询id=1 -- 从二级缓存获取
		Customer c3 = (Customer) s2.get(Customer.class, 1);
		System.out.println(c3);
		
		s2.getTransaction().commit();
		s2.close();
	}

```



### 3.3.2类缓存

- 类缓存：只存放数据
- 一级缓存：存放对象本身

![](http://upload-images.jianshu.io/upload_images/1540531-7eb26e9e4545189c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```java
@Test
	public void demo02(){
		//2 类缓存：只存放数据，散装数据。
		// * 使用默认的toString();
		Session s1 = factory.openSession();
		s1.beginTransaction();
		
		//1 查询id=1 -- 执行select
		Customer c1 = (Customer) s1.get(Customer.class, 1);
		System.out.println(c1);
		//2 查询id=1 -- 从一级缓存获取，一级缓存存放对象本身
		Customer c2 = (Customer) s1.get(Customer.class, 1);
		System.out.println(c2);
		
		s1.getTransaction().commit();
		s1.close();
		
		System.out.println("----------");
		
		Session s2 = factory.openSession();
		s2.beginTransaction();
		
		//3 查询id=1 -- 对象不一样，数据一样
		Customer c3 = (Customer) s2.get(Customer.class, 1);
		System.out.println(c3);
		
		s2.getTransaction().commit();
		s2.close();
	}

```



### 3.3.3集合缓存

 ![](http://upload-images.jianshu.io/upload_images/1540531-23d1d6909ef19aa7.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

 ![](http://upload-images.jianshu.io/upload_images/1540531-e6cc1decd370f386.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```java
@Test
	public void demo03(){
		//3 集合缓存：只存放关联对象OID的值，如果需要数据，从类缓存中获取。
		// * 3.1 默认：第一条select 查询客户，第二天 select 查询客户所有订单
		// * 3.2 操作：在hibernate.cfg.xml 将 Order 类缓存删除
		// *** <!--  <class-cache usage="read-write" class="com.itheima.a_init.Order"/>-->
		// *** 多了10条select，通过订单的id查询订单
		Session s1 = factory.openSession();
		s1.beginTransaction();
		
		//1 查询id=1 
		Customer c1 = (Customer) s1.get(Customer.class, 1);
		System.out.println(c1);
		//2 获得订单
		for (Order o1 : c1.getOrderSet()) {
			System.out.println(o1);
		}
		
		
		s1.getTransaction().commit();
		s1.close();
		
		System.out.println("----------");
		
		Session s2 = factory.openSession();
		s2.beginTransaction();
		
		//3 查询id=1
		Customer c3 = (Customer) s2.get(Customer.class, 1);
		System.out.println(c3);
		//4 获得订单
		for (Order o2 : c3.getOrderSet()) {
			System.out.println(o2);
		}
		
		s2.getTransaction().commit();
		s2.close();
	}

```



### 3.3.4时间戳

- 时间戳：任何操作都在时间戳中记录操作时间。

![](http://upload-images.jianshu.io/upload_images/1540531-223ebecaef992523.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```java
@Test
	public void demo04(){
		//4 时间戳: 所有的操作都会在时间戳中进行记录，如果数据不一致，将触发select语句进行查询
		// * 修改toString()
		Session s1 = factory.openSession();
		s1.beginTransaction();
		
		//1 查询id=1 
		Integer cid = 1;
		Customer c1 = (Customer) s1.get(Customer.class, cid);
		System.out.println(c1);
		//2 绕过一级和二级缓存，修改数据库，修改客户cname=大东哥
		s1.createQuery("update Customer set cname = ? where cid = ?")
			.setString(0, "大东哥")
			.setInteger(1, cid)
			.executeUpdate();
		//3打印
		System.out.println(c1);
		
		s1.getTransaction().commit();
		s1.close();
		
		System.out.println("----------");
		
		Session s2 = factory.openSession();
		s2.beginTransaction();
		
		//4 查询id=1  -- ?
		Customer c3 = (Customer) s2.get(Customer.class, 1);
		System.out.println(c3);
		
		s2.getTransaction().commit();
		s2.close();
	}
```

### 3.3.5查询缓存

- 查询缓存又称为三级缓存（民间）
- 查询缓存默认不使用。需要手动开启
- 查询缓存：将HQL语句与查询结果进行绑定。通过HQL相同语句可以缓存内容。

 默认情况Query对象只将查询结果存放在一级和二级缓存，不从一级或二级缓存获取。

 查询缓存就是让Query可以从二级缓存获得内容。

步骤一：开启查询缓存

![](http://upload-images.jianshu.io/upload_images/1540531-ed88b8ebff192a85.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```xml
<!-- 9.4 开启查询缓存 -->
<property name="hibernate.cache.use_query_cache">true</property>
```

步骤二：在查询query对象，设置缓存内容（注意：存放和查询都需要设置）

![](http://upload-images.jianshu.io/upload_images/1540531-ea90d27f9d948cb0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](http://upload-images.jianshu.io/upload_images/1540531-2e8a44bb686922c1.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```java
@Test
	public void demo05(){
		//5 查询缓存
		Session s1 = factory.openSession();
		s1.beginTransaction();
		
		//1 query查询
		Query q1 = s1.createQuery("from Customer");
		q1.setCacheable(true);
		List<Customer> a1 = q1.list();
		for (Customer c1 : a1) {
			System.out.println(c1);
		}
		
		//2 cid =1  -- 一级缓存获得
		Customer customer = (Customer) s1.get(Customer.class, 1);
		System.out.println(customer);
		
		s1.getTransaction().commit();
		s1.close();
		
		System.out.println("----------");
		
		Session s2 = factory.openSession();
		s2.beginTransaction();
		
		//2 cid =1  -- 二级缓存获得
		Customer customer2 = (Customer) s2.get(Customer.class, 1);
		System.out.println(customer2);
		
		//3 query查询
		Query q2 = s2.createQuery("from Customer");
		q2.setCacheable(true);
		List<Customer> a2 = q2.list();
		for (Customer c2 : a2) {
			System.out.println(c2);
		}
		
		s2.getTransaction().commit();
		s2.close();
	}
```



## 3.4ehcache配置文件

- &lt;diskStore path=&quot;java.io.tmpdir&quot;/&gt;  设置临时文件存放位置。（缓存一般内存，一定程度时，写入硬盘。）

    ![](http://upload-images.jianshu.io/upload_images/1540531-cc9a5cf1efe96977.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

- 缓存详细设置

 &lt;defaultCache&gt; 所有的缓存对象默认的配置

 &lt;cache name=&quot;类&quot;&gt; 指定对象单独配置

- 参数设置

maxElementsInMemory=&quot;10000&quot;  内存最大数

eternal=&quot;false&quot;  是否永久（内存常驻留）

timeToIdleSeconds=&quot;120&quot;

timeToLiveSeconds=&quot;120&quot;

overflowToDisk=&quot;true&quot;  内存满了，是否写入到硬盘

maxElementsOnDisk=&quot;10000000&quot;  硬盘最大数

diskPersistent=&quot;false&quot;  关闭JVM，是否将内存保存硬盘中

diskExpiryThreadIntervalSeconds=&quot;120&quot;  轮询

memoryStoreEvictionPolicy=&quot;LRU&quot;

 Least Recently Used (specified as LRU).

 First In First Out (specified as FIFO)

 Less Frequently Used (specified as LFU)

```
•	maxElementsInMemory :设置基于内存的缓存中可存放的对象最大数目 
•	eternal:设置对象是否为永久的,true表示永不过期,此时将忽略timeToIdleSeconds 和 timeToLiveSeconds属性; 默认值是false 
•	timeToIdleSeconds:设置对象空闲最长时间,以秒为单位, 超过这个时间,对象过期。当对象过期时,EHCache会把它从缓存中清除。如果此值为0,表示对象可以无限期地处于空闲状态。 
•	timeToLiveSeconds:设置对象生存最长时间,超过这个时间,对象过期。
如果此值为0,表示对象可以无限期地存在于缓存中. 该属性值必须大于或等于 timeToIdleSeconds 属性值 
•	overflowToDisk:设置基于内在的缓存中的对象数目达到上限后,是否把溢出的对象写到基于硬盘的缓存中 
•	diskPersistent 当jvm结束时是否持久化对象 true false 默认是false
•	diskExpiryThreadIntervalSeconds 指定专门用于清除过期对象的监听线程的轮询时间
•	 memoryStoreEvictionPolicy - 当内存缓存达到最大，有新的element加入的时候， 移除缓存中element的策略。默认是LRU（最近最少使用），可选的有LFU（最不常使用）和FIFO（先进先出） 

```


# 一、抓取策略（优化）

## 1.1检索方式

- 立即检索：立即查询，在执行查询语句时，立即查询所有的数据。
- 延迟检索：延迟查询，在执行查询语句之后，在需要时在查询。（懒加载）

## 1.2检查策略

- 类级别检索：当前的类的属性获取是否需要延迟。
- 关联级别的检索：当前类关联另一个类是否需要延迟。

## 1.3类级别检索

- get：立即检索。get方法一执行，立即查询所有字段的数据。

- load：延迟检索。默认情况，load方法执行后，如果只使用OID的值不进行查询，如果要使用其他属性值将查询。Customer.hbm.xml  &lt;class  lazy=&quot;true | false&quot;>;

  lazy 默认值true，表示延迟检索，如果设置false表示立即检索。

  ![](http://upload-images.jianshu.io/upload_images/1540531-bc90c47b6c7e20cc.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

  ```java
  @Test
  	public void demo02() {
  		//类级别
  		Session session = factory.openSession();
  		session.beginTransaction();

  		//1立即
  //		Customer customer = (Customer) session.get(Customer.class, 1);
  		//2延迟
  		Customer customer = (Customer) session.load(Customer.class, 1);
  		
  		
  		
  		//打印
  		System.out.println(customer.getCid());
  		System.out.println(customer.getCname());

  		session.getTransaction().commit();
  		session.close();
  	}
  ```

  ​

## 1.4关联级别检索

### 1.4.1一对多或多对多

#### 1.4.1.1介绍

- 容器&lt;set&gt; 提供两个属性：fetch、lazy

 fetch：确定使用sql格式

 lazy：关联对象是否延迟。

- fetch：join、select、subselect

 join：底层使用迫切左外连接

 select：使用多个select语句（默认值）

 subselect：使用子查询

- lazy：false、true、extra

 false：立即

 true：延迟（默认值）

 extra：极其懒惰

![](http://upload-images.jianshu.io/upload_images/1540531-4f7153522ea521c0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

#### 1.4.1.2fetch=&quot;join&quot;

- fetch=&quot;join&quot; ，lazy无效。底层使用迫切左外连接，使用一条select将所有内容全部查询。

  ![](http://upload-images.jianshu.io/upload_images/1540531-3d82af84701289f1.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

  ​

```java
@Test
	public void demo03() {
		//关联级别：一对多，
		// * Customer.hbm.xml  <set fetch="join">
		// *** select语句使用左外连接，一次性查询所有
		Session session = factory.openSession();
		session.beginTransaction();

		//1 查询客户
		Customer customer = (Customer) session.get(Customer.class, 1);
		System.out.println(customer.getCname());
		
		//2 查询客户订单数
		Set<Order> orderSet = customer.getOrderSet();
		System.out.println(orderSet.size());
		
		//3 查询客户订单详情
		for (Order order : orderSet) {
			System.out.println(order);
		}
		

		session.getTransaction().commit();
		session.close();
	}
```

#### 1.4.1.3fetch=&quot;select&quot;

- 当前对象和关联对象使用多条select语句查询。
- lazy=&quot;false&quot; , 立即，先查询客户select，立即查询订单select
- lazy=&quot;true&quot;,延迟，先查询客户select，需要订单时，再查询订单select
- lazy=&quot;extra&quot;，极其懒惰（延迟），先查询客户select，如果只需要订单数，使用聚合函数（不查询详情）

![](http://upload-images.jianshu.io/upload_images/1540531-118cc96fd8017f60.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](http://upload-images.jianshu.io/upload_images/1540531-1903a070482817f3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

 ![](http://upload-images.jianshu.io/upload_images/1540531-d6f7e922185dba24.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)1.4.1.4fetch=&quot;subselect&quot;

- 将使用子查询。注意：必须使用Query否则看不到效果。

- lazy= 同上

  ```java
  @Test
  	public void demo04() {
  		//关联级别：一对多，
  		// 演示3：* Customer.hbm.xml  <set fetch="subselect">
  		Session session = factory.openSession();
  		session.beginTransaction();

  		//1 查询客户
  		List<Customer> allCustomer = session.createQuery("from Customer").list();
  		Customer customer = allCustomer.get(0);
  		System.out.println(customer.getCname());
  		
  		//2 查询客户订单数
  		Set<Order> orderSet = customer.getOrderSet();
  		System.out.println(orderSet.size());
  		
  		//3 查询客户订单详情
  		for (Order order : orderSet) {
  			System.out.println(order);
  		}
  		

  		session.getTransaction().commit();
  		session.close();
  	}

  ```

  ![](http://upload-images.jianshu.io/upload_images/1540531-6fa78e02c67af13f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

  ![](http://upload-images.jianshu.io/upload_images/1540531-6596f4cd801acf58.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

  ![](http://upload-images.jianshu.io/upload_images/1540531-955586aa5eeae91d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### 1.4.2多对一

#### 1.4.2.1介绍

- &lt;many-to-one fetch=&quot;" lazy=&quot;"  (&lt;one-to-one&gt;)
- fetch取值：join、select

 join：底层使用迫切左外连接

 select：多条select语句

- lazy取值：false、proxy、no-proxy

 false：立即

 proxy：采用关联对象类级别检索的策略。

  订单关联客户（多对一）

  订单立即获得客户，需要在客户Customer.hbm.xml &lt;class lazy=&quot;false&quot;&gt;

  订单延迟获得客户，需要在客户Customer.hbm.xml &lt;class lazy=&quot;true&quot;&gt;

 no-proxy 不研究

![](http://upload-images.jianshu.io/upload_images/1540531-6b96282a6ecbedd6.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

#### 1.4.2.2fetch=&quot;join&quot;

- fecth=&quot;join&quot; select语句使用左外连接，此时lazy无效。

 ![](http://upload-images.jianshu.io/upload_images/1540531-060ac8e15f2690d8.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```java
@Test
	public void demo05() {
		//关联级别：多对一，
		// 演示1：* Order.hbm.xml  <set fetch="join">  lazy无效
		// * 注意：检查Customer.hbm.xml 和 Order.hbm.xml 没有额外的配置
		Session session = factory.openSession();
		session.beginTransaction();

		//1 查询订单
		Order order = (Order) session.get(Order.class, 1);
		System.out.println(order.getPrice());
		
		//2 查询订单客户信息
		Customer customer = order.getCustomer();
		System.out.println(customer.getCname());
		

		session.getTransaction().commit();
		session.close();
	}

```



#### 1.4.2.3fetch=&quot;select&quot;

- 将采用多条select语句，lazy=&quot;proxy&quot;是否延迟，取决关联对象类级别检索策略。

- lazy=&quot;false&quot;

![](http://upload-images.jianshu.io/upload_images/1540531-bdc4d0bd516dbb61.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

- lazy=&quot;proxy&quot;

![](http://upload-images.jianshu.io/upload_images/1540531-473e31ad75b22ad7.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

 ![](http://upload-images.jianshu.io/upload_images/1540531-bc0d1cffb2465140.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 1.5批量查询

- 当客户关联查询订单，给每一个客户生产一个select语句查询订单。批量查询使用in语句减少查询订单语句个数。

 默认：select \* from t\_order where customer\_id = ?

 批量：select \* from t\_order where customer\_id in (?,?,?,?)

- &lt;set batch-size=&quot;5&quot;> 5表示括号中?个数。

  ![](http://upload-images.jianshu.io/upload_images/1540531-10f7fdcd3a4e6854.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```java
@Test
	public void demo06() {
		//批量查询
		Session session = factory.openSession();
		session.beginTransaction();
		
		//1 查询所有客户
		List<Customer> allCustomer = session.createQuery("from Customer").list();
		
		//2遍历
		for (Customer customer : allCustomer) {
			System.out.println(customer.getCname());
			System.out.println(customer.getOrderSet().size());
		}
		
		
		session.getTransaction().commit();
		session.close();
	}
```

## 1.6检索总结

| 检索策略  | 优点                                       | 缺点                                       | 优先考虑使用的场合                                |
| ----- | ---------------------------------------- | ---------------------------------------- | ---------------------------------------- |
| 立即检索  | 对应用程序完全透明，不管对象处于持久化状态还是游离状态，应用程序都可以从一个对象导航到关联的对象 | (1)select语句多(2)可能会加载应用程序不需要访问的对象，浪费许多内存空间。 | (1)类级别(2)应用程序需要立即访问的对象(3)使用了二级缓存         |
| 延迟检索  | 由应用程序决定需要加载哪些对象，可以避免执行多余的select语句，以及避免加载应用程序不需要访问的对象。因此能提高检索性能，并节省内存空间。 | 应用程序如果希望访问游离状态的代理类实例，必须保证她在持久化状态时已经被初始化。 | (1)一对多或者多对多关联(2)应用程序不需要立即访问或者根本不会访问的对象   |
| 表连接检索 | (1)对应用程序完全透明，不管对象处于持久化状态还是游离状态，都可从一个对象导航到另一个对象。(2)使用了外连接，select语句少 | (1)可能会加载应用程序不需要访问的对象，浪费内存。(2)复杂的数据库表连接也会影响检索性能。 | (1)多对一或一对一关联(2)需要立即访问的对象(3)数据库有良好的表连接性能。 |

Customer  Get(int id)

 Return Session.load(Customer.class,id);

1. layz=false
2. 在Service层获得在页面要上要用到的属性=&gt; 在Service层中确保数据已经

# 二、查询方式总结

1通过OID检索（查询）

 get（）立即、如果没有数据返回null

 load（）延迟，如果没有数据抛异常。

2导航对象图检索方式：关联查询

 customer.getOrderSet()

 user.getPost().getDepartment().getDepName();

3原始sql语句

 SQLQuery sqlQuery = session.createSQLQuery(&quot;sql 语句&quot;)   ---&gt;表，表字段（列）

  sqlQuery.list() 查询所有

  sqlQuery.uniqueResult() 查询一个

4HQL，hibernate query language hibernate 查询语言【1】

 Query query = session.createQuery(&quot;hql语句&quot;)  --&gt; 对象，对象属性

5.QBC，query by criteria 纯面对对象查询语言【2】

# 三、HQL【掌握】

## 3.1介绍

​				 ![](http://upload-images.jianshu.io/upload_images/1540531-6eb0639a1f8b36c8.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 3.2查询所有客户

```java
@Test
	public void demo01(){
		//1 查询所有
		Session session = factory.openSession();
		session.beginTransaction();
		
		//1  使用简单类名 ， 存在自动导包
		// * Customer.hbm.xml <hibernate-mapping auto-import="true">
//		Query query = session.createQuery("from Customer");
		//2 使用全限定类名
		Query query = session.createQuery("from com.itheima.a_init.Customer");
		
		List<Customer> allCustomer = query.list();
		for (Customer customer : allCustomer) {
			System.out.println(customer);
		}
		
		session.getTransaction().commit();
		session.close();
	}
```

## 3.3选择查询

```java
@Test
	public void demo02(){
		//2 简单条件查询
		Session session = factory.openSession();
		session.beginTransaction();
		
		
		//1 指定数据，cid OID名称
//		Query query = session.createQuery("from Customer where cid = 1");
		//2 如果使用id，也可以（了解）
//		Query query = session.createQuery("from Customer where id = 1");
		//3 对象别名 ,格式： 类 [as] 别名
//		Query query = session.createQuery("from Customer as c where c.cid = 1");
		//4 查询所有项，mysql--> select * from...
		Query query = session.createQuery("select c from Customer as c where c.cid = 1");
		
		Customer customer = (Customer) query.uniqueResult();
		System.out.println(customer);
		
		session.getTransaction().commit();
		session.close();
	}
```

## 3.4投影查询（部分）

```java
@Test
	public void demo04(){
		//4 投影
		Session session = factory.openSession();
		session.beginTransaction();
		
		//1 默认
		//如果单列 ，select c.cname from，需要List<Object>
		//如果多列，select c.cid,c.cname from ，需要List<Object[]>  ,list存放每行，Object[]多列
//		Query query = session.createQuery("select c.cid,c.cname from Customer c");
		//2 将查询部分数据，设置Customer对象中
		// * 格式：new Customer(c.cid,c.cname)
		// * 注意：Customer必须提供相应的构造方法。
		// * 如果投影使用oid，结果脱管态对象。
		Query query = session.createQuery("select new Customer(c.cid,c.cname) from Customer c");
		
		List<Customer> allCustomer = query.list();
		for (Customer customer : allCustomer) {
			System.out.println(customer.getCid() + " : " + customer.getOrderSet().size());
		}
		
		session.getTransaction().commit();
		session.close();
	}
```

## 3.5排序

```java
@Test
	public void demo03(){
		//3排序 ，mysql--> select... order by 字段  [asc]|desc ,....
		Session session = factory.openSession();
		session.beginTransaction();
		
		Query query = session.createQuery("from Customer order by cid desc");
		
		List<Customer> allCustomer = query.list();
		for (Customer customer : allCustomer) {
			System.out.println(customer.getCid());
		}
		
		session.getTransaction().commit();
		session.close();
	}
```

## 3.6分页

```java
@Test
	public void demo05(){
		//分页
		Session session = factory.openSession();
		session.beginTransaction();
		
		Query query = session.createQuery("from Customer");
		// * 开始索引 , startIndex 算法： startIndex = (pageNum - 1) * pageSize;
		// *** pageNum 当前页（之前的 pageCode）
		query.setFirstResult(0);
		// * 每页显示个数 ， pageSize
		query.setMaxResults(2);
		
		List<Customer> allCustomer = query.list();
		for (Customer customer : allCustomer) {
			System.out.println(customer.getCid());
		}
		
		session.getTransaction().commit();
		session.close();
	}
```

## 3.7绑定参数

```java
@Test
	public void demo06(){
		/* 6 绑定参数
		 * 方式1：占位符，使用? 在hql语句替换具体参数
		 * 		设置参数 query.setXxx(int , object)
		 * 			参数1：?位置，从0开始。
		 * 			参数2：实际参数
		 * 		例如：String --> query.setString(int,String)
		 * 方式2：别名 , 格式 “属性= :别名 ”
		 * 		设置参数 query.setXxx(String,object)
		 * 			参数1：别名
		 * 			参数2：实际参数
		 * 		例如：Integer --> query.setInteger(String,Integer)
		 * 提供公共设置方法
		 * 	setParameter(int|string , Object)
		 */
		Session session = factory.openSession();
		session.beginTransaction();
		
		Integer cid = 1;
		
		//方式1
//		Query query = session.createQuery("from Customer where cid = ?");
//		query.setInteger(0, cid);
		//方式2
		Query query = session.createQuery("from Customer where cid = :xxx");
//		query.setInteger("xxx", cid);
		query.setParameter("xxx", cid);
		
		Customer customer = (Customer) query.uniqueResult();
		System.out.println(customer);
		
		session.getTransaction().commit();
		session.close();
	}

```



## 3.8聚合函数和分组

```java
@Test
	public void demo07(){
		/* 7  聚合函数
		 */
		Session session = factory.openSession();
		session.beginTransaction();
		
		//1 
//		Query query = session.createQuery("select count(*) from Customer");
		//2 别名
//		Query query = session.createQuery("select count(c) from Customer c");
		//3 oid
		Query query = session.createQuery("select count(cid) from Customer");
		
		Long numLong = (Long) query.uniqueResult();
		int num = numLong.intValue();
		
		System.out.println(num);
		
		
		session.getTransaction().commit();
		session.close();
	}

```



## 3.9连接查询

![](http://upload-images.jianshu.io/upload_images/1540531-8277d06d4093cc37.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

1.交叉连接，等效sql 笛卡尔积

2.隐式内连接，等效sql 隐式内连接

3.内连接，等效sql内连接

4.迫切内连接，hibernate底层使用内连接。

5.左外连接，等效sql左外连接

6.迫切左外连接，hibernate底层使用左外连接

7.右外连接，等效sql右外连接

内连接和迫切内连接？

左外连接和迫切左外连接？

```java
@Test
	public void demo08(){
		/* 8 链接查询 ： 左外连接和迫切左外连接？
		 * * 左外连接 , left outer join
		 * 		底层使用sql的左外连接，hibernate进行数据自动封装，将一条记录，封装给两个对象（Customer，Order）
		 * 		将两个对象添加到一个对象数组中Object[Customer,Order]
		 * * 迫切左外链接 left outer join fetch
		 * 		底层使用sql的左外连接，hibernate将一条记录封装给Customer，讲order数据封装Order，并将order关联到Customer
		 * 			customer.getOrderSet().add(order)
		 * 		默认查询的数据重复
		 */
		Session session = factory.openSession();
		session.beginTransaction();
		
		//左外连接
//		List list = session.createQuery("from Customer c left outer join c.orderSet ").list();
		//迫切左外链接 (默认数据重复)
//		List list = session.createQuery("from Customer c left outer join fetch c.orderSet ").list();
		//迫切左外链接 (去重复)
		List list = session.createQuery("select distinct c from Customer c left outer join fetch c.orderSet ").list();
		
		
		session.getTransaction().commit();
		session.close();
	}
```

## 3.10命名查询

- 思想：将HQL从java源码中，提取到配置文件中。
- 分类：全局、布局
- 配置

 全局：\*.hbm.xml   &lt;class&gt;</class&gt;<query name=&quot;名称&quot;>HQL语句

![](http://upload-images.jianshu.io/upload_images/1540531-61b60745fee1219c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)




 局部：<class name="" table=""><id><property><query name="">HQL</class>

![](data:image/*;base64,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)


- 获得

 全局：

  session.getNamedQuery(&quot;queryName&quot;)

 局部：

  session.getNamedQuery(&quot;className.queryName&quot;)  需要使用类的全限定名称

![](http://upload-images.jianshu.io/upload_images/1540531-de2189cae70f6620.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)



@Test
​	public void demo09(){
​		/* 9 命名查询
​		 */
​		Session session = factory.openSession();
​		session.beginTransaction();
​		
​		//全局
​		//List list = session.getNamedQuery("findAll").list();
​		//局部
​		List list = session.getNamedQuery("com.itheima.a_init.Customer.findAll").list();
​		
​		System.out.println(list.size());
​		
​		session.getTransaction().commit();
​		session.close();
​	}



# 四、QBC【了解】

#### 4.0.0.1QBC查询:

QBC:Query By Criteria条件查询.面向对象的查询的方式.

#### 4.0.0.2QBC简单的查询

```java
// 简单查询:

List<Customer> list = session.createCriteria(Customer.class).list();

  for (Customer customer : list) {

   System.out.println(customer);

}
```

#### 4.0.0.3QBC分页的查询:

```java
  Criteria criteria = session.createCriteria(Order.class);

  criteria.setFirstResult(10);

  criteria.setMaxResults(10);

  List&lt;Order&gt; list = criteria.list();
```



#### 4.0.0.4QBC排序查询:

 

```java
 Criteria criteria = session.createCriteria(Customer.class);

//  criteria.addOrder(org.hibernate.criterion.Order.asc(&quot;age&quot;));

  criteria.addOrder(org.hibernate.criterion.Order.desc(&quot;age&quot;));

  List&lt;Customer&gt; list = criteria.list();
```



#### 4.0.0.5QBC条件查询:

```
  // 按名称查询:

  //Criteria criteria = session.createCriteria(Customer.class);

  criteria.add(Restrictions.eq("cname", "tom";));

  List&lt;Customer&gt; list = criteria.list();

  // 模糊查询;

  //Criteria criteria = session.createCriteria(Customer.class);

  criteria.add(Restrictions.like("cname", "t%"));

  List&lt;Customer&gt; list = criteria.list();

  // 条件并列查询

  Criteria criteria = session.createCriteria(Customer.class);

  criteria.add(Restrictions.like("cname", "t%";));

  criteria.add(Restrictions.ge("age", 35));

  List<Customer> list = criteria.list();
```



#### 4.0.0.6离线查询(了解)

- DetachedCriteria 离线查询对象，不需要使用Session就可以拼凑查询条件。一般使用在web层或service层拼凑。将此对象传递给dao层，此时将与session进行绑定执行查询。
- 离线查询条件与QBC一样的。

 ![](http://upload-images.jianshu.io/upload_images/1540531-ba8af9ead2fd3216.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```java
@Test
	public void demo10(){
		/* 10 离线查询
		 */
		
		//web & service
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Customer.class);
		detachedCriteria.add(Restrictions.eq("cid", 1));
		
		//---------------dao
		
		Session session = factory.openSession();
		session.beginTransaction();
		
		// 离线Criteria 与session绑定
		Criteria criteria = detachedCriteria.getExecutableCriteria(session);
		List<Customer> allCustomer = criteria.list();
		System.out.println(allCustomer.size());
		
		session.getTransaction().commit();
		session.close();
	}

```



# 五、常见配置

## 5.1整合c3p0(连接池) （了解）

- 整合c3p0

    ![](http://upload-images.jianshu.io/upload_images/1540531-58bef6f55cedc414.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

 步骤一：导入c3p0 jar包

![](http://upload-images.jianshu.io/upload_images/1540531-41a83dc657942419.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


 步骤二：hibernate.cfg.xml 配置

  hibernate.connection.provider\_class org.hibernate.connection.C3P0ConnectionProvider

![](http://upload-images.jianshu.io/upload_images/1540531-c466601c8a9c9692.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


- c3p0具体配置参数

\#C3P0 Connection Pool

\#hibernate.c3p0.max\_size 2

\#hibernate.c3p0.min\_size 2

\#hibernate.c3p0.timeout 5000

\#hibernate.c3p0.max\_statements 100

\#hibernate.c3p0.idle\_test\_period 3000

\#hibernate.c3p0.acquire\_increment 2

\#hibernate.c3p0.validate false

## 5.2事务

### 5.2.1回顾

- 事务：一组业务操作，要么全部成功，要么全部不成功。
- 特性：ACID

 原子性：整体

 一致性：数据

 隔离性：并发

 持久性：结果

- 隔离问题：

 脏读：一个事务读到另一个事务未提交的内容

 不可重复读：一个事务读到另一个事务已提交的内容（insert）

 虚读（幻读）：一个事务读到另一个事务已提交的内容（update）

- 隔离级别--解决问题

 read uncommittd，读未提交。存在3个问题。

 read committed，读已提交。解决：脏读。存在2个问题。

 repeatable read ，可重复读。解决：脏读、不可重复读。存在1个问题。

 serializable，串行化。单事务。没有问题。

![](http://upload-images.jianshu.io/upload_images/1540531-b813b3908a047774.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


### 5.2.2hibernate设置隔离级别

- 在hibernate.cfg.xml 配置

 hibernate.connection.isolation 4

![](http://upload-images.jianshu.io/upload_images/1540531-9e488cdcd758c7f9.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### 5.2.3lost update 丢失更新

![](http://upload-images.jianshu.io/upload_images/1540531-a291c7eb845a8bab.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

- 悲观锁：丢失更新肯定会发生。

 采用数据库锁机制。

 读锁：共享锁。

  select .... from  ... lock in share mode;

 写锁：排他锁。（独占）

  select ... from  ....  for update

![](http://upload-images.jianshu.io/upload_images/1540531-8573b94976d6621d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


- 乐观锁：丢失更新肯定不会发生

 在表中提供一个字段（版本字段），用于标识记录。如果版本不一致，不允许操作。

![](http://upload-images.jianshu.io/upload_images/1540531-637166dbc06607d0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


### 5.2.4hibernate处理丢失更新

- 悲观锁：写锁

    ![](http://upload-images.jianshu.io/upload_images/1540531-9de76ca60c8b1c2f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```java
@Test
	public void demo01(){
		//1 查询所有
		Session session = factory.openSession();
		session.beginTransaction();
		
		Customer customer = (Customer) session.get(Customer.class, 1 ,LockMode.UPGRADE);
		System.out.println(customer);
		
		session.getTransaction().commit();
		session.close();
	}

```

![](http://upload-images.jianshu.io/upload_images/1540531-91a9cf3abc346b27.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

- 乐观锁：

 在PO对象（javabean）提供字段，表示版本字段。一般Integer

 在\*.hbm.xml 文件配置&lt;version name=&quot;...&quot;>

步骤一：

![](http://upload-images.jianshu.io/upload_images/1540531-6c42d77b9fb79ede.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


步骤二：

![](http://upload-images.jianshu.io/upload_images/1540531-49dd1e53f5b9ab4e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](http://upload-images.jianshu.io/upload_images/1540531-2e81d4f3d63fb12e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


步骤三：测试

```java
@Test
	public void demo02(){
		//1 查询所有
		Session session = factory.openSession();
		session.beginTransaction();
		
//		Order order = new Order();
//		order.setPrice(998d);
//		session.save(order);
		
		Order order = (Order) session.get(Order.class, 32);
		order.setPrice(889d);
		
		
		session.getTransaction().commit();
		session.close();
	}
```


# Java Web Dao层设计

## UML设计图

![Dao层设计](http://upload-images.jianshu.io/upload_images/1540531-0a72df3b938f78a2.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 实体类

```java
package cn.zzuli.oa.domain;

public class Role {

}
```

```java
package cn.zzuli.oa.domain;

public class User {

	private Long id;
	private String username;

	public User() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
```

## BaseDao接口

```java
package cn.zzuli.oa.base;

import java.util.List;

public interface BaseDao<T> {
	
	/**
	 * 保存实体
	 * @param entity
	 */
	void save(T entity);
	
	/**
	 * 删除实体
	 * @param id
	 */
	void delete(Long id);
	
	/**
	 * 更新实体
	 * @param entity
	 */
	void update(T entity);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	T getById(Long id);
	
	/**
	 * 查询实体
	 * @param ids id的集合
	 * @return
	 */
	List<T> listByIds(Long[] ids);
	
	/**
	 * 查询所有
	 * @return
	 */
	List<T> listAll();
	
}
```

## BaseDaoImpl代码，实现BaseDao接口

```java
package cn.zzuli.oa.base.impl;

import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import cn.zzuli.oa.base.BaseDao;

@SuppressWarnings("unchecked")
public abstract class BaseDaoImpl<T> implements BaseDao<T> {

	@Resource
	private SessionFactory sessionFactory;

	protected Class<T> clazz;
	
	public BaseDaoImpl() {
		//通过反射得到T的真实类型
		ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();//获取这个父类的泛型类型
		this.clazz = (Class<T>) pt.getActualTypeArguments()[0]; //获取第一个泛型T的类型
		
		System.out.println("clazz = " + clazz.getName());
	}
	

	@Override
	public void save(T entity) {
		getSession().save(entity);
	}

	@Override
	public void delete(Long id) {
		Object obj = getSession().get(clazz, id);
		getSession().delete(obj);
	}

	@Override
	public void update(T entity) {
		getSession().update(entity);
	}

	@Override
	public T getById(Long id) {
		return (T) getSession().get(clazz, id);
	}

	@Override
	public List<T> listByIds(Long[] ids) {
		if (ids == null || ids.length == 0) {
			return Collections.EMPTY_LIST;
		}
		return getSession().createQuery("FROM " + clazz.getSimpleName() + " WHERE id IN(:ids)")
				.setParameterList("ids", ids).list();
	}

	@Override
	public List<T> listAll() {
		return getSession().createQuery("FROM " + clazz.getSimpleName()).list();
	}

	/**
	 * 获取当前可用的Session
	 * 
	 * @return 当前可用的Session
	 */
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

}
```

## RoleDao接口，继承BaseDao

```java
package cn.zzuli.oa.dao;

import cn.zzuli.oa.base.BaseDao;
import cn.zzuli.oa.domain.Role;

public interface RoleDao extends BaseDao<Role> {

}
```

## UserDao接口，继承BaseDao

> UserDao实现类UserDaoImpl可以实现的自己的功能代码

```java
package cn.zzuli.oa.dao;

import cn.zzuli.oa.base.BaseDao;
import cn.zzuli.oa.domain.User;

public interface UserDao extends BaseDao<User>{
	
}
```

## RoleDaoImpl代码，继承BaseDaoImpl，实现RoleDao

```java
package cn.zzuli.oa.dao.impl;

import org.springframework.stereotype.Repository;

import cn.zzuli.oa.base.impl.BaseDaoImpl;
import cn.zzuli.oa.dao.RoleDao;
import cn.zzuli.oa.domain.Role;

@Repository
public class RoleDaoImpl extends BaseDaoImpl<Role> implements RoleDao {

}
```

## UserDaoImpl代码，继承BaseDaoImpl，实现UserDao

```java
package cn.zzuli.oa.dao.impl;

import org.springframework.stereotype.Repository;

import cn.zzuli.oa.base.impl.BaseDaoImpl;
import cn.zzuli.oa.dao.UserDao;
import cn.zzuli.oa.domain.User;

@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao{

}
```

## 测试类

```java
package cn.zzuli.oa.base;

import org.junit.Test;

import cn.zzuli.oa.dao.RoleDao;
import cn.zzuli.oa.dao.UserDao;
import cn.zzuli.oa.dao.impl.RoleDaoImpl;
import cn.zzuli.oa.dao.impl.UserDaoImpl;
import cn.zzuli.oa.domain.Role;
import cn.zzuli.oa.domain.User;

public class BaseDaoTest {

	@Test
	public void testGetById() {
		UserDao userDao = new UserDaoImpl();
		RoleDao roleDao = new RoleDaoImpl();
		
	}

}
```

## 实现效果

![实现效果](http://upload-images.jianshu.io/upload_images/1540531-56ac78980d7b3c81.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

此处输出的正是BaseDao实现类BaseDaoImple中构造方法获取的泛型的类型，
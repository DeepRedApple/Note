# JavaWeb 各个层次简化设计

## UML类图

![UML类图](http://upload-images.jianshu.io/upload_images/1540531-aefd961f155d6b9a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## Dao层

### BaseDao接口

```java
package cn.zzuli.oa.base;

import java.util.List;

/**
 * Dao层的基类
 * @author LZH
 * @date 2017年2月25日
 * @param <T> 泛型 获取实体类
 */
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

### BaseDao实现类BaseDaoImpl

```java
package cn.zzuli.oa.base.impl;

import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import cn.zzuli.oa.base.BaseDao;

/**
 * dao层的实现类
 * 
 * @author LZH
 * @date 2017年2月25日
 * @param <T>
 *            泛型 用于获取实体类
 */
@Transactional // @Transactional可以被继承,即对子类也有效
@SuppressWarnings("unchecked")
public abstract class BaseDaoImpl<T> implements BaseDao<T> {

	@Resource
	private SessionFactory sessionFactory;

	protected Class<T> clazz;

	public BaseDaoImpl() {
		// 通过反射得到T的真实类型
		ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
		this.clazz = (Class<T>) pt.getActualTypeArguments()[0]; // 获取T的类型
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

## Service业务层设计

各个模块接口，在每个接口中，可以写每个模块所特有的方法，以供调用实现。这里面由于继承了BaseDaoImpl可是直接调用Session来执行操作数据，这样就可以写一些每个模块里面所独有的方法。

### RoleService

```java
package cn.zzuli.oa.service;

import cn.zzuli.oa.base.BaseDao;
import cn.zzuli.oa.domain.Role;

/**
 * 岗位业务层接口
 *   可以写自己模块所特有的方法
 * @author LZH
 * @date 2017年2月25日
 */
public interface RoleService extends BaseDao<Role>{

}
```

### UserService

```java
package cn.zzuli.oa.service;

import cn.zzuli.oa.base.BaseDao;
import cn.zzuli.oa.domain.User;

public interface UserService extends BaseDao<User>{

}
```

### RoleService实现类RoleServiceImpl

```java
package cn.zzuli.oa.service.impl;

import org.springframework.stereotype.Service;

import cn.zzuli.oa.base.impl.BaseDaoImpl;
import cn.zzuli.oa.domain.Role;
import cn.zzuli.oa.service.RoleService;

/**
 * 岗位业务层实现类
 * 
 * @author LZH
 * @date 2017年2月25日
 */
@Service
public class RoleServiceImpl extends BaseDaoImpl<Role> implements RoleService {

}
```

### UserService实现类UserServiceImpl

```java
package cn.zzuli.oa.service.impl;

import org.springframework.stereotype.Service;

import cn.zzuli.oa.base.impl.BaseDaoImpl;
import cn.zzuli.oa.domain.User;
import cn.zzuli.oa.service.UserService;

/**
 * 用户业务层
 * 
 * @author LZH
 * @date 2017年2月26日
 */
@Service
public class UserServiceImpl extends BaseDaoImpl<User> implements UserService {
	
}
```

## Action层设计

### BaseAction基类

```java
package cn.zzuli.oa.base;

import java.lang.reflect.ParameterizedType;

import javax.annotation.Resource;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.zzuli.oa.service.DepartmentService;
import cn.zzuli.oa.service.RoleService;
import cn.zzuli.oa.service.UserService;

public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {

	private static final long serialVersionUID = 1L;

	//填写各个Service的实现类，方便在控制层中调用各个不同之间的使用
	@Resource
	protected RoleService roleService;
	@Resource
	protected DepartmentService departmentService;
	@Resource
	protected UserService userSercice;
  	//.....

	protected T model;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public BaseAction() {
		try {
			// 得到model的类型信息
			ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
			Class clazz = (Class) pt.getActualTypeArguments()[0];
			
			// 生成model的实例 通过反射实例
			model = (T) clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public T getModel() {
		return model;
	}

}
```

### RoleAction

```java
package cn.zzuli.oa.view.action;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.zzuli.oa.base.BaseAction;
import cn.zzuli.oa.domain.Role;

/**
 * 岗位控制层
 *  @Controller
	@Scope("prototype") 这两个注解不能写到父类中，否则就失效了
 * @author LZH
 * @date 2017年2月25日
 */
@Controller
@Scope("prototype")
public class RoleAction extends BaseAction<Role> {

	private static final long serialVersionUID = 1L;

	/**
	 * 列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception {
		List<Role> roleList = roleService.listAll();
		ActionContext.getContext().put("roleList", roleList);
		return "list";
	}

	/**
	 * 添加
	 * 
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception {
		roleService.save(model);
		return "toList";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception {
		roleService.delete(model.getId());
		return "toList";
	}

	/**
	 * 修改
	 * 
	 * @return
	 * @throws Exception
	 */
	public String edit() throws Exception {
		Role role = roleService.getById(model.getId());
		role.setName(model.getName());
		role.setDescription(model.getDescription());
		roleService.update(role);
		return "toList";
	}

	/**
	 * 添加页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addUI() throws Exception {
		return "addUI";
	}

	/**
	 * 修改页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String editUI() throws Exception {
		Role role = roleService.getById(model.getId());
		model.setName(role.getName());
		model.setDescription(role.getDescription());

		// ActionContext.getContext().getValueStack().push(role);//放到对象栈的栈顶
		return "editUI";
	}

}
```

### UserAction

```java
package cn.zzuli.oa.view.action;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.zzuli.oa.base.BaseAction;
import cn.zzuli.oa.domain.User;

/**
 * 用户控制层
 * 
 * @author LZH
 * @date 2017年2月26日
 */
@Controller
@Scope("prototype")
public class UserAction extends BaseAction<User> {

	private static final long serialVersionUID = 1L;

	/**
	 * 列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception {
		return "list";
	}

	/**
	 * 添加
	 * 
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception {
		return "toList";
	}

	/**
	 * 添加页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addUI() throws Exception {
		return "addUI";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception {
		return "toList";
	}

	/**
	 * 修改
	 * 
	 * @return
	 * @throws Exception
	 */
	public String edit() throws Exception {
		return "toList";
	}

	/**
	 * 修改页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String editUI() throws Exception {
		return "editUI";
	}

	/**
	 * 初始化密码为1234
	 * 
	 * @return
	 * @throws Exception
	 */
	public String initPassword() throws Exception {
		return "toList";
	}

}
```


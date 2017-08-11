# Hibernate中表与表的关系模板代码

## 一对多的关系

```xml
<!-- users属性 与User的一对多 -->
<set name="users">
	<key column="departmentId"></key>
	<one-to-many class="User" />
</set>
```

![一对多](http://upload-images.jianshu.io/upload_images/1540531-8c243b1e4f3d31e2.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 多对一关系

```xml
<!-- department 用户与部门的多对一 -->
<many-to-one name="department" class="Department" column="departmentId">

</many-to-one>
```

![多对一](http://upload-images.jianshu.io/upload_images/1540531-581cdc614f1cef10.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 多对多关系

```xml
<!-- users属性，本类与User的多对多 -->
<set name="users" table="itcast_user_role">
	<key column="roleId"></key>
	<many-to-many class="User" column="userId"></many-to-many>
</set>
```

```xml
<!-- roles属性，本类与Role的多对多 -->
<set name="roles" table="itcast_user_role">
  	<key column="userId"></key>
  	<many-to-many class="Role" column="roleId"></many-to-many>
</set>
```

需要第三表来维护

## 模板实例

### 实体类

#### Department.java

```java
package cn.zzuli.oa.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * 部门
 * @author LZH
 * @date 2017年2月23日
 */
public class Department {
	private Long id;
	private Set<User> users = new HashSet<User>();
	private Department parent;
	private Set<Department> children = new HashSet<Department>();

	private String name;
	private String description;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Department getParent() {
		return parent;
	}

	public void setParent(Department parent) {
		this.parent = parent;
	}

	public Set<Department> getChildren() {
		return children;
	}

	public void setChildren(Set<Department> children) {
		this.children = children;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
```

#### Role.java

```java
package cn.zzuli.oa.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * 岗位
 * @author LZH
 * @date 2017年2月23日
 */
public class Role {
	private Long id;
	private String name;
	private String description;
	private Set<User> users = new HashSet<User>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

}
```

#### User.java

```java
package cn.zzuli.oa.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * 用户
 * @author LZH
 * @date 2017年2月23日
 */
public class User {
	private Long id;
	private Department department;
	private Set<Role> roles = new HashSet<Role>();

	private String loginName; // 登录名
	private String password; // 密码
	private String name; // 真实姓名
	private String gender; // 性别
	private String phoneNumber; // 电话号码
	private String email; // 电子邮件
	private String description; // 说明

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
```

### 对应配置文件

#### Department.hbm.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE hibernate-mapping PUBLIC "-//Hibernate/Hibernate Mapping DTD 3.0//EN"
"http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd">
<hibernate-mapping package="cn.zzuli.oa.domain">

	<class name="Department" table="department">
		<id name="id" column="id">
			<generator class="native"></generator>
		</id>

		<property name="name" column="name"></property>
		<property name="description" column="description"></property>

		<!-- users属性 与User的一对多 -->
		<set name="users">
			<key column="departmentId"></key>
			<one-to-many class="User" />
		</set>

		<!-- parent属性 与Department多对一 -->
		<many-to-one name="parent" class="Department" column="parentId">

		</many-to-one>


		<!-- children 与Department一对多 -->
		<set name="children">
			<key column="parentId"></key>
			<one-to-many class="Department" />
		</set>

	</class>

</hibernate-mapping>
```

#### Role.hbm.xml

```xml
<?xml version="1.0"?>
<!DOCTYPE hibernate-mapping PUBLIC
        "-//Hibernate/Hibernate Mapping DTD 3.0//EN"
        "http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd">

<hibernate-mapping package="cn.itcast.oa.domain">

	<class name="Role" table="itcast_role">
		<id name="id">
            <generator class="native"/>
		</id>
		<property name="name"/>
		<property name="description"/>
		
		<!-- users属性，本类与User的多对多 -->
		<set name="users" table="user_role">
			<key column="roleId"></key>
			<many-to-many class="User" column="userId"></many-to-many>
		</set>
		
	</class>
	
</hibernate-mapping>
```

#### User.hbm.xml

```xml
<?xml version="1.0"?>
<!DOCTYPE hibernate-mapping PUBLIC
        "-//Hibernate/Hibernate Mapping DTD 3.0//EN"
        "http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd">

<hibernate-mapping package="cn.itcast.oa.domain">

	<class name="User" table="itcast_user">
		<id name="id">
            <generator class="native"/>
		</id>
		<property name="loginName"/>
		<property name="password"/>
		<property name="name"/>
		<property name="gender" />
		<property name="phoneNumber"/>
		<property name="email"/>
		<property name="description"/>
		
		<!-- department属性，本类与Department的多对一 -->
		<many-to-one name="department" class="Department" column="departmentId"></many-to-one>

		<!-- roles属性，本类与Role的多对多 -->
		<set name="roles" table="user_role">
			<key column="userId"></key>
			<many-to-many class="Role" column="roleId"></many-to-many>
		</set>
		
	</class>
	
</hibernate-mapping>
```

### 数据表模型

![数据表模型](http://upload-images.jianshu.io/upload_images/1540531-dee9717ac8161626.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)



## 总结：

![图片总结](http://upload-images.jianshu.io/upload_images/1540531-4b3708277b62a85a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### 1、写注释

​	格式为：？属性，表达的是本对象与？的？关系。

​        例：“department属性，本对象与Department的多对一”

### 2、模版

#### 多对一

```xml
<many-to-one name="" class="" column=""/>
```

#### 一对多

```xml
<set name="">
        <key column=""></key>
        <one-to-many class=""/>
</set>
```

#### 多对多

```xml
<set name="" table="">
        <key column=""></key>
        <many-to-many class="" column=""/>
</set>
```

### 3、填空

•name属性：属性名（注释中的第1问号）

•class属性：关联的实体类型（注释中的第2个问号）

•column属性：

•\<many-to-onecolumn="..">：一般可以写成属性名加Id后缀，如属性为department，则column值写成departmentId。

•一对多中的\<keycolumn="..">：从关联的对方（对方是多对一）映射中把column值拷贝过来。

•多对多中的\<keycolumn=“..”>：一般可以写成本对象的名加Id后缀，如本对象名为User，则写为userId。

•多对多中的\<many-to-manycolumn=“..”>：一般可以写为关联对象的名称加Id后缀。
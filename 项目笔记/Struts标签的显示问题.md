# Struts2中修改自定义标签的源代码使标签不显示

> 某些页面中关于权限的显示及链接的问题。可以通过修改Struts2里的自定义标签的源代码来使标签不显示
>
> 我以\<s:a>为例

## 1、找到Struts2里面的标签配置文件struts-tag.tld

![Struts2标签的源代码配置文件struts-tag.tld](http://upload-images.jianshu.io/upload_images/1540531-55c5124e8b934a44.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 2、找到\<name>a\<name>节点

![结点标签](http://upload-images.jianshu.io/upload_images/1540531-2ca5f02846a9a7a0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

> 说明：tag节点代表一个struts2的标签，里面有名称、属性、说明、源文件等内容

## 3、打开org.apache.struts2.views.jsp.ui.AnchorTag

```java
/*
 * $Id$
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.struts2.views.jsp.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Anchor;
import org.apache.struts2.components.Component;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * @see Anchor
 */
public class AnchorTag extends AbstractClosingTag {

    private static final long serialVersionUID = -1034616578492431113L;

    protected String href;
    protected String includeParams;
    protected String scheme;
    protected String action;
    protected String namespace;
    protected String method;
    protected String encode;
    protected String includeContext;
    protected String escapeAmp;
    protected String portletMode;
    protected String windowState;
    protected String portletUrlType;
    protected String anchor;
    protected String forceAddSchemeHostAndPort;
    
    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new Anchor(stack, req, res);
    }
    
    protected void populateParams() {
        super.populateParams();

        Anchor tag = (Anchor) component;
        tag.setHref(href);
        tag.setIncludeParams(includeParams);
        tag.setScheme(scheme);
        tag.setValue(value);
        tag.setMethod(method);
        tag.setNamespace(namespace);
        tag.setAction(action);
        tag.setPortletMode(portletMode);
        tag.setPortletUrlType(portletUrlType);
        tag.setWindowState(windowState);
        tag.setAnchor(anchor);

        if (encode != null) {
            tag.setEncode(Boolean.valueOf(encode).booleanValue());
        }
        if (includeContext != null) {
            tag.setIncludeContext(Boolean.valueOf(includeContext).booleanValue());
        }
        if (escapeAmp != null) {
            tag.setEscapeAmp(Boolean.valueOf(escapeAmp).booleanValue());
        }
	    if (forceAddSchemeHostAndPort != null) {
   	     tag.setForceAddSchemeHostAndPort(Boolean.valueOf(forceAddSchemeHostAndPort).booleanValue());
        }
    }
    
    public void setHref(String href) {
        this.href = href;
    }

    public void setEncode(String encode) {
        this.encode = encode;
    }

    public void setIncludeContext(String includeContext) {
        this.includeContext = includeContext;
    }

    public void setEscapeAmp(String escapeAmp) {
        this.escapeAmp = escapeAmp;
    }

    public void setIncludeParams(String name) {
        includeParams = name;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setPortletMode(String portletMode) {
        this.portletMode = portletMode;
    }

    public void setPortletUrlType(String portletUrlType) {
        this.portletUrlType = portletUrlType;
    }

    public void setWindowState(String windowState) {
        this.windowState = windowState;
    }

    public void setAnchor(String anchor) {
        this.anchor = anchor;
    }

    public void setForceAddSchemeHostAndPort(String forceAddSchemeHostAndPort) {
        this.forceAddSchemeHostAndPort = forceAddSchemeHostAndPort;
    }
}
```

## 4、在自己的项目中建一个与这个源代码包名、类名一模一样的类

> 说明：里面的代码跟struts2里面的源代码一样复制过来

![类](http://upload-images.jianshu.io/upload_images/1540531-254a34e8076412c6.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 5、编辑

```java
/*
 * $Id$
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.struts2.views.jsp.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

import org.apache.struts2.components.Anchor;
import org.apache.struts2.components.Component;

import com.opensymphony.xwork2.util.ValueStack;

import cn.zzuli.oa.domain.User;

/**
 * @see Anchor
 */
public class AnchorTag extends AbstractClosingTag {

    private static final long serialVersionUID = -1034616578492431113L;

    protected String href;
    protected String includeParams;
    protected String scheme;
    protected String action;
    protected String namespace;
    protected String method;
    protected String encode;
    protected String includeContext;
    protected String escapeAmp;
    protected String portletMode;
    protected String windowState;
    protected String portletUrlType;
    protected String anchor;
    protected String forceAddSchemeHostAndPort;
    
	@Override
    public int doEndTag() throws JspException {
		//当前登录用户
    	User user = (User) pageContext.getSession().getAttribute("user");
    	
    	//当前准备显示链接对应的权限URL
    	String privUrl = action;
    	
    	//去掉参数
    	int pos = privUrl.indexOf("?");
    	if(pos > -1) {
    		privUrl = privUrl.substring(0, pos);
    	}
    	
    	//去掉UI后缀
    	if(privUrl.endsWith("UI")) {
    		privUrl = privUrl.substring(0, privUrl.length() - 2);
    	}
    	
    	//根据用户是否有这个权限，来判断是否在页面中显示链接信息
    	if(user.hasPrivilegeByUrl("/" + privUrl)) {
    		return super.doEndTag();//正常的生成并显示的超链接标签，并继续执行页面中后面的代码
    	}else {
    		return EVAL_PAGE;//不生成不显示超链接标签，只是继续执行页面中后面的代码
    	}
    }
    
    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new Anchor(stack, req, res);
    }
    
    protected void populateParams() {
        super.populateParams();

        Anchor tag = (Anchor) component;
        tag.setHref(href);
        tag.setIncludeParams(includeParams);
        tag.setScheme(scheme);
        tag.setValue(value);
        tag.setMethod(method);
        tag.setNamespace(namespace);
        tag.setAction(action);
        tag.setPortletMode(portletMode);
        tag.setPortletUrlType(portletUrlType);
        tag.setWindowState(windowState);
        tag.setAnchor(anchor);

        if (encode != null) {
            tag.setEncode(Boolean.valueOf(encode).booleanValue());
        }
        if (includeContext != null) {
            tag.setIncludeContext(Boolean.valueOf(includeContext).booleanValue());
        }
        if (escapeAmp != null) {
            tag.setEscapeAmp(Boolean.valueOf(escapeAmp).booleanValue());
        }
	    if (forceAddSchemeHostAndPort != null) {               tag.setForceAddSchemeHostAndPort(Boolean.valueOf(forceAddSchemeHostAndPort).booleanValue());
        }
    }
    
    public void setHref(String href) {
        this.href = href;
    }

    public void setEncode(String encode) {
        this.encode = encode;
    }

    public void setIncludeContext(String includeContext) {
        this.includeContext = includeContext;
    }

    public void setEscapeAmp(String escapeAmp) {
        this.escapeAmp = escapeAmp;
    }

    public void setIncludeParams(String name) {
        includeParams = name;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setPortletMode(String portletMode) {
        this.portletMode = portletMode;
    }

    public void setPortletUrlType(String portletUrlType) {
        this.portletUrlType = portletUrlType;
    }

    public void setWindowState(String windowState) {
        this.windowState = windowState;
    }

    public void setAnchor(String anchor) {
        this.anchor = anchor;
    }

    public void setForceAddSchemeHostAndPort(String forceAddSchemeHostAndPort) {
        this.forceAddSchemeHostAndPort = forceAddSchemeHostAndPort;
    }
}
```

## 6、说明

系统会先加载classpath目录下的代码，如果没有则再去jar包里面的加载。一般我们覆盖doEndTag()方法即可。


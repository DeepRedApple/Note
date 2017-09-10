# SpringMVC配置统一异常处理器

参考文章

[1].[Spring MVC全局异常后返回JSON异常数据](http://blog.csdn.net/chwshuang/article/details/48089203)

[2].[使用Spring MVC统一异常处理实战](http://cgs1999.iteye.com/blog/1547197)

[3].[springmvc学习笔记(16)-异常处理器](http://brianway.github.io/2016/03/30/springmvc-learn-16-exception/)

## 前提以及准备工作

在项目中，要求SpringMVC的每一个请求方法所返回的只能是String类型和JSON的数据，其中返回String类型的方法只能是请求到页面，返回JSON格式的方法主要是返回form表单请求的内容以及表格数据。

### 结果信息工具类ResultInfo

对结果信息进行封装

```java
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @User: LZH
 * @Date: 2017/8/14
 * @Time: 15:03
 */
public class ResultInfo {
    public static final int TYPE_RESULT_FAIL = 0;//失败
    public static final int TYPE_RESULT_SUCCESS = 1;//成功
    public static final int TYPE_RESULT_WARN = 2;//警告
    public static final int TYPE_RESULT_INFO = 3;//提示信息

    public ResultInfo() {
    }

    /**
     * 消息提示类型
     */
    private int type;


    /**
     * 提示代码
     */
    private int messageCode;


    /**
     * 提示信息
     */
    private String message;


    /**
     * 提示信息明细列表
     */
    private List<ResultInfo> details;

    public List<ResultInfo> getDetails() {
        return details;
    }


    public void setDetails(List<ResultInfo> details) {
        this.details = details;
    }

    /**
     * 提示消息对应操作的序号，方便用户查找问题，通常用于在批量提示信息中标识记录序号
     */
    private int index;


    /**
     * 提交后得到到业务数据信息从而返回给页面
     */
    private Map<String, Object> sysdata = new HashMap<String, Object>();

    /**
     * 构造函数,根据提交信息代码messageCode获取提示信息
     */
    public ResultInfo(final int type, int messageCode, String message) {
        this.type = type;
        this.messageCode = messageCode;
        this.message = message;
    }


    public int getMessageCode() {
        return messageCode;
    }


    public void setMessageCode(int messageCode) {
        this.messageCode = messageCode;
    }


    public String getMessage() {
        return message;
    }


    public void setMessage(String message) {
        this.message = message;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isSuccess() {
        if (this.type == TYPE_RESULT_SUCCESS) {
            return true;
        }
        return false;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }


    public Map<String, Object> getSysdata() {
        return sysdata;
    }


    public void setSysdata(Map<String, Object> sysdata) {
        this.sysdata = sysdata;
    }
}
```

### 结果工具类ResultUtil

```java
import cn.zzuli.yycg.utils.ResourcesUtil;


/**
 * 系统结果工具类
 */
public class ResultUtil {


    /**
     * 创建错误结果
     *
     * @return
     */
    public static ResultInfo createFail(String fileName, int messageCode, Object[] objs) {
        String message = null;
        if (objs == null) {
            message = ResourcesUtil.getValue(fileName, messageCode + "");
        } else {
            message = ResourcesUtil.getValue(fileName, messageCode + "", objs);
        }
        return new ResultInfo(ResultInfo.TYPE_RESULT_FAIL, messageCode, message);
    }

    /**
     * 创建敬告提示结果
     */
    public static ResultInfo createWarning(String fileName, int messageCode, Object[]
            objs) {
        String message = null;
        if (objs == null) {
            message = ResourcesUtil.getValue(fileName, messageCode + "");
        } else {
            message = ResourcesUtil.getValue(fileName, messageCode + "", objs);
        }
        return new ResultInfo(ResultInfo.TYPE_RESULT_WARN, messageCode, message);
    }

    /**
     * 创建成功提示结果
     */
    public static ResultInfo createSuccess(String fileName, int messageCode, Object[]
            objs) {

        String message = null;
        if (objs == null) {
            message = ResourcesUtil.getValue(fileName, messageCode + "");
        } else {
            message = ResourcesUtil.getValue(fileName, messageCode + "", objs);
        }
        return new ResultInfo(ResultInfo.TYPE_RESULT_SUCCESS, messageCode, message);
    }


    /**
     * 创建普通信息提示结果
     */
    public static ResultInfo createInfo(String fileName, int messageCode, Object[] objs) {

        String message = null;
        if (objs == null) {
            message = ResourcesUtil.getValue(fileName, messageCode + "");
        } else {
            message = ResourcesUtil.getValue(fileName, messageCode + "", objs);
        }
        return new ResultInfo(ResultInfo.TYPE_RESULT_INFO, messageCode, message);
    }


    /**
     * 抛出异常
     *
     * @param resultInfo
     * @throws ExceptionResultInfo
     */
    public static void throwExcepion(ResultInfo resultInfo) throws ExceptionResultInfo {
        throw new ExceptionResultInfo(resultInfo);
    }

    public static void throwExcepion(ResultInfo resultInfo, List<ResultInfo> details)
            throws ExceptionResultInfo {
        if (resultInfo != null) {
            resultInfo.setDetails(details);
        }
        throw new ExceptionResultInfo(resultInfo);
    }

    /**
     * 创建提交结果信息
     *
     * @param resultInfo
     * @return
     */
    public static SubmitResultInfo createSubmitResult(ResultInfo resultInfo) {
        return new SubmitResultInfo(resultInfo);
    }

    /**
     * 创建提交结果信息，包括明细信息
     *
     * @param resultInfo
     * @param details
     * @return
     */
    public static SubmitResultInfo createSubmitResult(ResultInfo resultInfo,
                                                      List<ResultInfo> details) {
        if (resultInfo != null) {
            resultInfo.setDetails(details);
        }
        return new SubmitResultInfo(resultInfo);
    }

    public static void main(String[] args) {

    }

}
```

### 表格结果信息类DataGridResultInfo

表格结果信息封装类，请求中可以返回此类信息

```java
import java.util.ArrayList;
import java.util.List;

/**
 * @User: LZH
 * @Date: 2017/8/14
 * @Time: 15:03
 */
public class DataGridResultInfo {

    public DataGridResultInfo() {
    }

    public DataGridResultInfo(ResultInfo resultInfo) {
        this.resultInfo = resultInfo;
    }

    //操作结果信息
    private ResultInfo resultInfo;

    //总条数
    private int total;

    //结果集
    private List rows = new ArrayList();

    //总计告诉footer
    private List footer = new ArrayList();

    public ResultInfo getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(ResultInfo resultInfo) {
        this.resultInfo = resultInfo;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }

    public List getFooter() {
        return footer;
    }

    public void setFooter(List footer) {
        this.footer = footer;
    }
}
```

### 提交结果信息类

主要封装了提交结果

```java
/**
 * 系统提交结果类型
 */
public class SubmitResultInfo {

    public SubmitResultInfo(ResultInfo resultInfo) {
        this.resultInfo = resultInfo;
    }

    //操作结果信息
    private ResultInfo resultInfo;

    public ResultInfo getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(ResultInfo resultInfo) {
        this.resultInfo = resultInfo;
    }

}
```

### 自定义系统异常类

```java
/**
 * 自定义系统异常类
 */
public class ExceptionResultInfo extends Exception {

    // 系统统一使用的结果类，包括了 提示信息类型和信息内容
    private ResultInfo resultInfo;

    public ExceptionResultInfo(ResultInfo resultInfo) {
        super(resultInfo.getMessage());
        this.resultInfo = resultInfo;
    }

    public ResultInfo getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(ResultInfo resultInfo) {
        this.resultInfo = resultInfo;
    }

}
```

### 系统参数配置类

```java
package cn.zzuli.yycg.base.process.context;

/**
 * 系统参数配置类，配置了系统用到参数
 */
public class Config {


    /**
     * 系统语言环境，默认为中文zh
     */
    public static final String LANGUAGE = "zh";

    /**
     * 系统国家环境，默认为中国CN
     */
    public static final String COUNTRY = "CN";

    /**
     * servletContext
     */
    public static final String SERVLETCONTEXT_KEY = "servletContext";

    /**
     * 提示信息配置文件名
     */
    public static final String MESSAGE = "resources.messages";


    /**
     * 系统版本文件名称
     */
    public static final String VERSION = "version";

    /**
     * 公共权限，需要给用户分配权限即可使用
     */
    public static final String COMMON_ACTIONS = "commonActions";

    /**
     * 公开权限，用户无需登录即可使用
     */
    public static final String ANONYMOUS_ACTIONS = "anonymousActions";

    /**
     * 系统参数配置文件名称
     */
    public static final String SYSCONFIG = "sysParams";

    /**
     * session中存放登录用户的key名称
     */
    public static final String ACTIVEUSER_KEY = "activeUser";

    /**
     * 配置文件中系统基础url的key名称
     */
    public static final String BASEURL_KEY = "baseurl";

    /**
     * 配置文件中系统管理url的key名称
     */
    public static final String SYSMANAGERURL_KEY = "sysmanagerurl";

    /**
     * 配置文件中系统配置url的key名称
     */
    public static final String SYSCONFIGURL_KEY = "sysconfigurl";

    /**
     * 配置文件中加密密钥的key名称
     */
    public static final String DESKEY_KEY = "deskey";

    /**
     * 系统管理及系统配置平台接入key参数
     */
    public static final String LOGINKEY = "loginkeyString";

    /**
     * 配置文件中系统版本名称的key名称
     */
    public static final String VERSION_NAME_KEY = "version_name";

    /**
     * 配置文件中系统版本号的key名称
     */
    public static final String VERSION_NUMBER_KEY = "version_number";

    /**
     * 配置文件中系统版本发布时间的key名称
     */
    public static final String VERSION_DATE_KEY = "version_date";

    /**
     * 系统提示信息ResultInfo对象key
     */
    public static final String RESULTINFO_KEY = "resultInfo";


    /**
     * 基础模块存放页面路径 ，在/WEB-INF/jsp下
     */
    public static final String PAGE_PATH_BASE = "/base";

    /**
     * 业务模块存放页面路径 ，在/WEB-INF/jsp下
     */
    public static final String PAGE_PATH_BUSINESS = "/business";


    /**
     * 一般错误提示页面,该路径需要匹配页面前后缀
     */
    public static final String ERROR_PAGE = "/base/error";
    /**
     * 登录页面地址,该路径需要匹配页面前后缀
     */
    public static final String LOGIN_PAGE = "/base/login";

    /**
     * 无权提示页面地址,该路径需要匹配页面前后缀
     */
    public static final String REFUSE_PAGE = "/base/refuse";

    /**
     * 系统公开action，无需登录即可使用
     */
    //private static List<Operation> anonymousActions;


    /**
     * 获取公开访问地址
     * @return
     */
    /*public static List<Operation> getAnonymousActions() {
        if(anonymousActions !=null){
			return anonymousActions;
		}
		anonymousActions = new ArrayList<Operation>();
		
		List<String> baseActions_list = ResourcesUtil.gekeyList(Config
		.ANONYMOUS_ACTIONS);
		for (String common_actionUrl:baseActions_list) {
			Operation o_i = new Operation(common_actionUrl);
			anonymousActions.add(o_i);
		}
		return anonymousActions;
	}
*/


}
```

### 资源目录以及文件

![](http://upload-images.jianshu.io/upload_images/1540531-1d7c81358ceb1204.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

messages.properties

```properties
101=用户名错误
#类似配置
```



## 统一异常处理器

### 自定义异常处理器流程

![](http://upload-images.jianshu.io/upload_images/1540531-9dd3d5cc5db2686d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### 编写自定义异常处理器

```java
import java.io.IOException;
import java.lang.reflect.Method;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import cn.zzuli.yycg.base.process.result.ExceptionResultInfo;
import cn.zzuli.yycg.base.process.result.ResultInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExceptionResolverCustom implements HandlerExceptionResolver {

    // json转换器
    // 将异常信息转json
    private HttpMessageConverter<ExceptionResultInfo> jsonMessageConverter;

    // 前端控制器调用此方法执行异常处理
    // handler，执行的action类就包装了一个方法（对应url的方法）
    @Override
    public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response, Object handler,
                                         Exception ex) {
        // 输出 异常信息
        ex.printStackTrace();
        // 转成springmvc底层对象（就是对action方法的封装对象，只有一个方法）
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        // 取出方法
        Method method = handlerMethod.getMethod();

        // 判断方法是否返回json
        // 只要方法上有responsebody注解表示返回json
        // 查询method是否有responsebody注解
        ResponseBody responseBody = AnnotationUtils.findAnnotation(method,
                ResponseBody.class);
        if (responseBody != null) {
            // 将异常信息转json输出
            return this.resolveJsonException(request, response, handlerMethod,
                    ex);

        }
        // 这里说明action返回的是jsp页面

        // 解析异常
        ExceptionResultInfo exceptionResultInfo = resolveExceptionCustom(ex);

        String view = "/base/error";
        //异常代码
        int code = exceptionResultInfo.getResultInfo().getMessageCode();
        //如果是106 = 此操作需要登录后进行 跳转到登陆页面
        if (code == 106) {
            view = "/base/login";
        }

        // 将异常信息在异常页面显示
        request.setAttribute("exceptionResultInfo",
                exceptionResultInfo.getResultInfo());

        // 转向错误页面
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exceptionResultInfo",
                exceptionResultInfo.getResultInfo());
        modelAndView.setViewName(view);// 逻辑视图名
        return modelAndView;
    }

    // 异常信息解析方法
    private ExceptionResultInfo resolveExceptionCustom(Exception ex) {
        ResultInfo resultInfo = null;
        if (ex instanceof ExceptionResultInfo) {
            // 抛出的是系统自定义异常
            resultInfo = ((ExceptionResultInfo) ex).getResultInfo();
        } else {
            // 重新构造“未知错误”异常
            resultInfo = new ResultInfo();
            resultInfo.setType(ResultInfo.TYPE_RESULT_FAIL);
            resultInfo.setMessage("未知错误！");
        }

        return new ExceptionResultInfo(resultInfo);

    }

    // 将异常信息转json输出
    private ModelAndView resolveJsonException(HttpServletRequest request,
                                              HttpServletResponse response, Object
                                                      handler, Exception ex) {

        // 解析异常
        ExceptionResultInfo exceptionResultInfo = resolveExceptionCustom(ex);

        HttpOutputMessage outputMessage = new ServletServerHttpResponse(response);

        try {
            //将exceptionResultInfo对象转成json输出
            jsonMessageConverter.write(exceptionResultInfo, MediaType.APPLICATION_JSON,
                    outputMessage);
        } catch (HttpMessageNotWritableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ModelAndView();

    }

    public HttpMessageConverter<ExceptionResultInfo> getJsonMessageConverter() {
        return jsonMessageConverter;
    }

    public void setJsonMessageConverter(
            HttpMessageConverter<ExceptionResultInfo> jsonMessageConverter) {
        this.jsonMessageConverter = jsonMessageConverter;
    }

}
```

### 配置同意异常处理器

```xml
<bean id="jsonMessageConverter"
          class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter"/>

<bean id="handlerExceptionResolver"
      class="cn.zzuli.xxx.ExceptionResolverCustom">
   <property name="jsonMessageConverter" ref="jsonMessageConverter"/>
</bean>
```

```xml
<!-- springmvc的前端控制器 -->
<servlet>
  <servlet-name>springmvc</servlet-name>
  <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
  <!-- contextConfigLocation不是必须的， 如果不配置contextConfigLocation， springmvc的配置文件默认在：WEB-INF/servlet的name+"-servlet.xml" -->
  <init-param>
    <param-name>contextConfigLocation</param-name>
    <param-value>classpath:spring/springmvc.xml</param-value>
  </init-param>
  <!-- 屏蔽springmvc自动注册的异常处理器 -->
  <init-param>
    <param-name>detectAllHandlerExceptionResolvers</param-name>
    <param-value>false</param-value>
  </init-param>

  <!-- <load-on-startup>1</load-on-startup> -->
</servlet>
```

detectAllHandlerExceptionResolvers：

屏蔽自动注册异常处理器，固定使用bean的id为handlerExceptionResolver的异常处理器。

### 使用同意异常处理器

系统中所有异常由全局异常处理器处理。

Dao方法向外抛出系统自定义异常。

Service方法向外抛出系统自定义异常。

Action方法向外抛出系统自定义异常。

不需要在进行try{}catch{}捕获

## 使用

### 返回SubmitResultInfo

```java
	@RequestMapping("/sysusersave")
    public @ResponseBody
    SubmitResultInfo addOrEditSysuserSubmit(String id, SysuserQueryVo sysuserQueryVo)
            throws Exception {
        //使用全局异常处理器后，在actoin不用进行try/catch捕获
        //操作

        return ResultUtil.createSubmitResult(ResultUtil.createSuccess(
                Config.MESSAGE, 906, null)
        );
    }
```

### 返回DataGridResultInfo

```java
@RequestMapping("/sysuserquery_result")
    public @ResponseBody
    DataGridResultInfo queryuser_result(XXX, xxx,int page,int rows) throws Exception {
       	//操作
      	DataGridResultInfo dataGridResultInfo = new DataGridResultInfo();

        return dataGridResultInfo;
    }
```

## 总结

使用这种方式，规定了返回的数据类型个主要是JSON的同时，也对返回的类型进行了封装处理。最主要的还是不需要在进行try{}catch{}捕获处理。使得开发更加快捷。
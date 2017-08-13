# 一、工作流的概念

![](http://upload-images.jianshu.io/upload_images/1540531-b9f885f6cc76c4fd.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](http://upload-images.jianshu.io/upload_images/1540531-cee118dddcc9dadd.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

 说明：

1. 1)假设：这两张图就是华谊兄弟的请假流程图
2. 2)图的组成部分：

3. 人物：范冰冰冯小刚王中军
4. 事件（动作）：请假、批准、不批准

activiti

![](http://upload-images.jianshu.io/upload_images/1540531-146eff0a402c9a50.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](http://upload-images.jianshu.io/upload_images/1540531-74b42f46827a787f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

工作流(Workflow)，就是&quot;业务过程的部分或整体在计算机应用环境下的自动化&quot;，它主要解决的是&quot;使在多个参与者之间按照某种预定义的规则传递文档、信息或任务的过程自动进行，从而实现某个预期的业务目标，或者促使此目标的实现&quot;。

工作流管理系统(Workflow Management System, WfMS)是一个软件系统，它完成工作量的定义和管理，并按照在系统中预先定义好的工作流逻辑进行工作流实例的执行。工作流管理系统不是企业的业务系统，而是为企业的业务系统的运行提供了一个软件的支撑环境。

工作流管理联盟(WfMC，Workflow Management Coalition)给出的关于工作流管理系统的定义是：工作流管理系统是一个软件系统，它通过执行经过计算的流程定义去支持一批专门设定的业务流程。工作流管理系统被用来定义、管理、和执行工作流程。

工作流管理系统的目标：管理工作的流程以确保工作在正确的时间被期望的人员所执行——在自动化进行的业务过程中插入人工的执行和干预。

# 二、工作流的过程

项目演示

# 三、Activiti介绍

Activiti5是由Alfresco软件在2010年5月17日发布的业务流程管理（BPM）框架，它是覆盖了业务流程管理、工作流、服务协作等领域的一个开源的、灵活的、易扩展的可执行流程语言框架。Activiti基于Apache许可的开源BPM平台，创始人Tom Baeyens是JBoss jBPM的项目架构师，它特色是提供了eclipse插件，开发人员可以通过插件直接绘画出业务流程图。

![](http://upload-images.jianshu.io/upload_images/1540531-83bf8eca1526baa1.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 3.1工作流引擎

 这是Activiti工作的核心。负责生成流程运行时的各种实例及数据、监控和管理流程的运行。

## 3.2 BPMN2.0 \*

 业务流程建模与标注（Business Process Model and Notation，BPMN) ，描述流程的基本符号，包括这些图元如何组合成一个业务流程图（Business Process Diagram）

## 3.3 数据库

Activiti的后台是有数据库的支持，所有的表都以ACT\_开头。第二部分是表示表的用途的两个字母标识。用途也和服务的API对应。

1. 1)ACT\_RE\_\*: 'RE'表示repository。这个前缀的表包含了流程定义和流程静态资源（图片，规则，等等）。
2. 2)ACT\_RU\_\*: 'RU'表示runtime。这些运行时的表，包含流程实例，任务，变量，异步任务，等运行中的数据。Activiti只在流程实例执行过程中保存这些数据，在流程结束时就会删除这些记录。这样运行时表可以一直很小速度很快。
3. 3)ACT\_ID\_\*:'ID'表示identity。这些表包含身份信息，比如用户，组等等。
4. 4)ACT\_HI\_\*:'HI'表示history。这些表包含历史数据，比如历史流程实例，变量，任务等等。
5. 5)ACT\_GE\_\*: 通用数据，用于不同场景下。

### 3.3.1资源库流程规则表

1. 1)act\_re\_deployment         部署信息表
2. 2)act\_re\_model          流程设计模型部署表
3. 3)act\_re\_procdef          流程定义数据表

### 3.3.2运行时数据库表

1. 1)act\_ru\_execution        运行时流程执行实例表
2. 2)act\_ru\_identitylink        运行时流程人员表，主要存储任务节点与参与者的相关信息
3. 3)act\_ru\_task                运行时任务节点表
4. 4)act\_ru\_variable        运行时流程变量数据表

### 3.3.3历史数据库表

1. 1)act\_hi\_actinst                 历史节点表
2. 2)act\_hi\_attachment                历史附件表
3. 3)act\_hi\_comment                历史意见表
4. 4)act\_hi\_identitylink                历史流程人员表
5. 5)act\_hi\_detail                        历史详情表，提供历史变量的查询
6. 6)act\_hi\_procinst                历史流程实例表
7. 7)act\_hi\_taskinst                历史任务实例表
8. 8)act\_hi\_varinst                历史变量表

### 3.3.4组织机构表

1. 1)act\_id\_group                用户组信息表
2. 2)act\_id\_info                用户扩展信息表
3. 3)act\_id\_membership        用户与用户组对应信息表
4. 4)act\_id\_user                用户信息表

这四张表很常见，基本的组织机构管理，关于用户认证方面建议还是自己开发一套，组件自带的功能太简单，使用中有很多需求难以满足

### 3.3.5通用数据表

1. 1)act\_ge\_bytearray                二进制数据表
2. 2)act\_ge\_property                属性数据表存储整个流程引擎级别的数据,初始化表结构时，会默认插入三条记录，

## 3.4 activiti.cfg.xml

Activiti核心配置文件，配置流程引擎创建工具的基本参数和数据库连接池参数。

定义数据库配置参数：

-  **jdbcUrl** : 数据库的JDBC URL。
-  **jdbcDriver** : 对应不同数据库类型的驱动。
-  **jdbcUsername** : 连接数据库的用户名。
-  **jdbcPassword** : 连接数据库的密码。

基于JDBC参数配置的数据库连接会使用默认的 **MyBatis** 连接池。下面的参数可以用来配置连接池（来自MyBatis参数）：

-  **jdbcMaxActiveConnections** : 连接池中处于被使用状态的连接的最大值。默认为10。
-  **jdbcMaxIdleConnections** : 连接池中处于空闲状态的连接的最大值。
-  **jdbcMaxCheckoutTime** : 连接被取出使用的最长时间，超过时间会被强制回收。默认为20000（20秒）。
-  **jdbcMaxWaitTime** : 这是一个底层配置，让连接池可以在长时间无法获得连接时，打印一条日志，并重新尝试获取一个连接。（避免因为错误配置导致沉默的操作失败）。默认为20000（20秒）。

示例数据库配置：

![](http://upload-images.jianshu.io/upload_images/1540531-45832b7e84baa626.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

 

也可以使用javax.sql.DataSource。（比如，Apache Commons的DBCP）：

![](http://upload-images.jianshu.io/upload_images/1540531-2b867d109d30de99.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240) 

## 3.5 logging.properties

 日志的配置文件

# 四、准备环境

## 4.1 activiti5 软件环境

1. 1)JDK1.6或者更高版本
2. 2)支持的数据库有：h2, mysql, oracle, postgres, mssql, db2等。
3. 3)支持activiti5运行的jar包
4. 4)开发环境为Eclipse3.7或者以上版本,myeclipse为8.6版本

## 4.2相关资源下载

1. 1)JDK可以到sun的官网下载

[http://www.oracle.com/technetwork/java/javase/downloads/index.html](http://www.oracle.com/technetwork/java/javase/downloads/index.html)

1. 2)数据库，例如：mysql可以在官网上下载。

[http://www.mysql.com](http://www.mysql.com/)

1. 3)activiti也可以到Activiti官方网站下载得到。

[http://activiti.org/download.html](http://activiti.org/download.html)

1. 4) Eclipse3.7或者MyEclipse8.6也可以到相应的网站上获得下载。

## 4.3安装流程设计器(eclipse插件)

### 4.3.1 在有网络的情况下，安装流程设计器步骤如下：

1. 1)打开  **Help -&gt; Install New Software**. 在如下面板中:

![](http://upload-images.jianshu.io/upload_images/1540531-3d9e0cc97991ddf4.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240) 

1. 2)在如下Install界面板中，点击Add按钮：

![](http://upload-images.jianshu.io/upload_images/1540531-7e7aafc570b8e1ad.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

   配置新装插件的地址和名称

1. 3)然后填入下列字段

Name: Activiti BPMN 2.0 designer

Location: http://activiti.org/designer/update/

如图所示：

![](http://upload-images.jianshu.io/upload_images/1540531-b0b270c09d8c6772.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

1. 4)回到Install界面，在面板正中列表中把所有展示出来的项目都勾上：

![](http://upload-images.jianshu.io/upload_images/1540531-e48c4841edd6235e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

1. 5)点击复选框

在Detail部分记得选中&quot;Contact all updates sites..&quot; , 因为它会检查所有当前安装所需要的插件并可以被Eclipse下载.

1. 6)安装完以后，点击新建工程new-&gt;Other…打开面板，如果看到下图内容：

![](http://upload-images.jianshu.io/upload_images/1540531-bbfbbb4237448040.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

说明安装成功了。

### 4.3.2 在没有网络的情况下，安装流程设计器步骤如下：

1. 解压老师发给大家的 ![](http://upload-images.jianshu.io/upload_images/1540531-de91e2c7b4b04820.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
2. 把压缩包中的内容放入eclipse根目录的dropins文件夹下

2. 重启eclipse，点击新建工程new-&gt;Other…打开面板，如果看到下图内容：

![](http://upload-images.jianshu.io/upload_images/1540531-5bd9a8595eb66c04.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 4.4对流程设计器的使用说明

打开菜单Windows-&gt;Preferences-&gt;Activiti-&gt;Save下流程流程图片的生成方式:

![](http://upload-images.jianshu.io/upload_images/1540531-af50d37ac248688c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

虽然流程引擎在单独部署bpmn文件时会自动生成图片，但在实际开发过程中，自动生成的图片会导致和BPMN中的坐标有出入，在实际项目中展示流程当前位置图会有问题。

所在完成以上配置后，会由我们自己来管理流程图片。在发布流程时把流程规则文件和流程图片一起上传就行了。

## 4.5准备Activiti5开发环境

### 4.5.1添加Activiti5的jar包

 在activiti-5.16.1-&gt;wars目录下是一些示例项目，解压activiti-rest项目，导入activiti-rest目录中WEB-INF\lib下所有包。添加到classpath中。

由于我们使用的是Mysql数据库，Mysql数据库的链接驱动Activiti官方包中并没有提供，需要我们自己导入。手动导入mysql-connector-java.jar，添加到classpath下。

### 4.5.2初始化数据库

![](http://upload-images.jianshu.io/upload_images/1540531-f01a959205199163.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

在Activiti中，在创建核心的流程引擎对象时会自动建表。如果程序正常执行，mysql会自动建库，然后创建23张表。

### 4.5.3添加并制定配置文件

在Actiiti5中定制流程必定会操作到数据库，如果都像上面那样写一大段代码会非常麻烦，所以我们可以把数据库连接配置写入配置文件。

在Activiti5的官方示例中并没有现成的配置文件，所以先得找到activiti-rest\WEB-INF\classes下有：

1. activiti-context.xml ：一个类似spring结构的配置文件，清空内容后改名为activiti.cfg.xml，用来做流程引擎的相关配置。

按照上面代码配置ProcessEngineConfiguration对象，主要定义数据库的连接配置和建表策略，配置文件代码如下：

![](http://upload-images.jianshu.io/upload_images/1540531-a356dfc8a22ba65f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

Java代码如下：

![](http://upload-images.jianshu.io/upload_images/1540531-969cf735dab1cc14.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

createProcessEngineConfigurationFromResource的参数值为我们添加的配置文件activiti.cfg.xml的名称，执行java代码，流程引擎对象创建成功运行后数据库会自动建表。

1. log4j.properties 日志配置文件

把两个文件放入resource目录下即可。

注意：数据库编码要指定为utf8

# 五、核心API

## 5.1ProcessEngine

 说明：

1. 1)在Activiti中最核心的类，其他的类都是由他而来。
2. 2)产生方式：

![](http://upload-images.jianshu.io/upload_images/1540531-ce742b80109fe274.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240) 

在前面看到了两种创建ProcessEngine（流程引擎）的方式，而这里要简化很多，调用ProcessEngines的getDefaultProceeEngine方法时会自动加载classpath下名为activiti.cfg.xml文件。

1. 3)可以产生RepositoryService

![](http://upload-images.jianshu.io/upload_images/1540531-4dca8f0ce186fac0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

1. 4)可以产生RuntimeService

![](http://upload-images.jianshu.io/upload_images/1540531-081702c3eb07a06f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

1. 5)可以产生TaskService

![](http://upload-images.jianshu.io/upload_images/1540531-ed8a3e4f3612e0ec.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

各个Service的作用：

| **RepositoryService** |         管理流程定义         |
| :-------------------: | :--------------------: |
|    RuntimeService     | 执行管理，包括启动、推进、删除流程实例等操作 |
|      TaskService      |          任务管理          |
|    HistoryService     |    历史管理(执行完的数据的管理)     |
|    IdentityService    |         组织机构管理         |
|      FormService      |     一个可选服务，任务表单管理      |
|    ManagerService     |                        |

## 5.2RepositoryService

 是Activiti的仓库服务类。所谓的仓库指流程定义文档的两个文件：bpmn文件和流程图片。

1. 1)产生方式

 ![](http://upload-images.jianshu.io/upload_images/1540531-ac22be380568ffe8.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

1. 2)可以产生DeploymentBuilder，用来定义流程部署的相关参数

![](http://upload-images.jianshu.io/upload_images/1540531-493b0d2864636757.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

1. 3)删除流程定义

![](http://upload-images.jianshu.io/upload_images/1540531-2a85e76df5ed8174.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 5.3RuntimeService

 是activiti的流程执行服务类。可以从这个服务类中获取很多关于流程执行相关的信息。

## 5.4TaskService

 是activiti的任务服务类。可以从这个类中获取任务的信息。

## 5.5ProcessDefinition

 流程定义类。可以从这里获得资源文件等。

## 5.6ProcessInstance

 代表流程定义的执行实例。如范冰冰请了一天的假，她就必须发出一个流程实例的申请。一个流程实例包括了所有的运行节点。我们可以利用这个对象来了解当前流程实例的进度等信息。

 如图为ProcessInstance的源代码：

![](http://upload-images.jianshu.io/upload_images/1540531-cecd4f285635293f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

 从源代码中可以看出ProcessInstance就是Execution。但在现实意义上有所区别：

![](http://upload-images.jianshu.io/upload_images/1540531-033985e5776a1fee.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

 在单线流程中，如上图的贷款流程，ProcessInstance与Execution是一致的。

![](http://upload-images.jianshu.io/upload_images/1540531-5b5f19020b2da85c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

 这个例子有一个特点：wire money(汇钱)和archive(存档)是并发执行的。 这个时候，总线路代表ProcessInstance，而分线路中每个活动代表Execution。

## 5.7Execution

 Activiti用这个对象去描述流程执行的每一个节点。在没有并发的情况下，同ProcessInstance。

# 六、管理流程定义

## 6.1设计流程定义文档

### 6.1.1图片

![](http://upload-images.jianshu.io/upload_images/1540531-a82d30bd1fa1af09.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### 6.1.2bpmn文件

BPMN 2.0根节点是definitions节点。这个元素中，可以定义多个流程定义（不过我们建议每个文件只包含一个流程定义，可以简化开发过程中的维护难度）。一个空的流程定义看起来像下面这样。注意，definitions元素最少也要包含xmlns 和targetNamespace的声明。targetNamespace可以是任意值，它用来对流程实例进行分类。

```xml-dtd
<definitions  xmlns="http://www.omg.org/spec/BPMN/20100524/MODEL"  xmlns:activiti="http://activiti.org/bpmn"  targetNamespace="Examples">        
<!-- 流程定义部分 -->  
<processid="myProcess"name="My First Process">           
    <startEvent id="startevent1" name="Start"></startEvent>                
    <endEvent id="endevent1" name="End"></endEvent>        
    <userTask id="usertask1" name="员工申请请假"  activiti:assignee="员工"></userTask>        
    <userTask id="usertask2" name="部门经理审批"  activiti:assignee="经理"></userTask>        
    <userTask id="usertask3" name="老板审批"  activiti:assignee="老板"></userTask>        
    <sequenceFlow id="flow1" sourceRef="startevent1" targetRef="usertask1"></sequenceFlow>        
    <sequenceFlow id="flow2" name="提交申请" sourceRef="usertask1"         
argetRef="usertask2"></sequenceFlow>        
    <sequenceFlow id="flow3" name="经理审批" sourceRef="usertask2"         
argetRef="usertask3"></sequenceFlow>        
    <sequenceFlow id="flow4" name="老板审批" sourceRef="usertask3"         
argetRef="endevent1"></sequenceFlow>        
</process>            
<!-- BPMN绘图规范定义部分（用来描述节点图标的大小和坐标） -->
<bpmndi:BPMNDiagram id="BPMNDiagram_myProcess">
....
</bpmndi:BPMNDiagram>
</definitions>
```

 说明：流程定义文档有两部分组成：

1. 1)bpmn文件

流程规则文件。在部署后，每次系统启动时都会被解析，把内容封装成流程定义放入项目缓存中。Activiti框架结合这个xml文件自动管理流程。

1. 2)展示流程图的图片

在系统里需要展示流程的进展图片

## 6.2部署流程定义

 部署流程定义也可以认为是增加流程定义。

![](http://upload-images.jianshu.io/upload_images/1540531-2ec02145573a8903.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

 说明：

1. 1)图中32首先获得默认的流程引擎，在创建时会自动加载classpath下得activiti.cfg.xml
2. 2)通过流程引擎获取了一个RepositoryService对象-&gt;仓库服务对象
3. 3)图中35由仓库的服务对象产生一个部署对象配置对象，用来封装部署环境的相关配置。
4. 4)图37可以看出这是一个链式编程，在部署配置对象中设置显示名，上传规则文件相对classpath的地址。
5. 5)部署，也是往数据库中存储流程定义的过程。
6. 6)这一步在数据库中将操作三张表：

7. a)act\_re\_deployment

 存放流程定义的显示名和部署时间，每部署一次增加一条记录

1. b)act\_re\_procdef

 存放流程定义的属性信息，部署每个新的流程定义都会在这张表中增加一条记录。

1. c)act\_ge\_bytearray

 存储流程定义相关的部署信息。即流程定义文档的存放地。每部署一次就会增加两条记录，一条是关于bpmn规则文件的，一条是图片的（如果部署时只指定了bpmn一个文件，activiti会在部署时解析bpmn文件内容自动生成流程图）。两个文件不是很大，都是以二进制形式存储在数据库中。

## 6.3查看流程定义

查询流程定义的信息

![](http://upload-images.jianshu.io/upload_images/1540531-3141d737e0ca1565.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

结果：

![](http://upload-images.jianshu.io/upload_images/1540531-2d97cef56b033888.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

 再部署一次运行结果为：

![](http://upload-images.jianshu.io/upload_images/1540531-d1bf7e480466dfb8.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

 说明：

1. 1)因为流程定义的信息存放在仓库中，所以在第53行应该获取RepositoryService。
2. 2)图55，创建流程定义查询对象，可以在ProcessDefinitionQuery上设置查询过滤参数
3. 3)图64，调用ProcessDefinitionQuery对象的list方法，执行查询，获得符合条件的流程定义列表
4. 4)由运行结果可以看出：

5. a)Key和Name的值为：bpmn文件process节点的id和name的属性值

![](http://upload-images.jianshu.io/upload_images/1540531-6a57ccc91134be5c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

1. b)key属性被用来区别不同的流程定义。
2. c)带有特定key的流程定义第一次部署时，version为1。之后每次部署都会在当前最高版本号上加1
3. d)Id的值的生成规则为:{processDefinitionKey}:{processDefinitionVersion}:{generated-id}, 这里的generated-id是一个自动生成的唯一的数字
4. e)重复部署一次，deploymentId的值以一定的形式变化
5. f)流程定义(ProcessDefinition)在数据库中没有相应的表对应，只是从act\_ge\_bytearray表中取出相应的bpmn和png图片，并进行解析。

## 6.4删除流程定义

 删除部署到activiti中的流程定义。

![](http://upload-images.jianshu.io/upload_images/1540531-a010e40b70d76f38.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

 说明：

1. 1)因为删除的是流程定义，而流程定义的部署是属于仓库服务的，所以应该先得到RepositoryService
2. 2)如果该流程定义下没有正在运行的流程，则可以用85行进行删除。如果是有关联的信息，用88行的方法进行级联删除。一般情况下用41行就可以。由于88删除涉及的数据比较多，一般只开放给超级管理员使用。

## 6.5获取流程定义文档的资源

 查询出流程定义文档。主要查的是图片，用于显示流程用。

![](http://upload-images.jianshu.io/upload_images/1540531-08ac1c7c425d23af.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

 说明：

1. 1)deploymentId为流程部署ID
2. 2)resourceName为act\_ge\_bytearray表中NAME\_列的值
3. 3)使用repositoryService的getDeploymentResourceNames方法可以获取指定部署下得所有文件的名称
4. 4)使用repositoryService的getResourceAsStream方法传入部署ID和文件名称可以获取部署下指定名称文件的输入流
5. 5)最后的有关IO流的操作，使用FileUtils工具的copyInputStreamToFile方法完成流程流程到文件的拷贝

# 七、流程实例管理

## 7.1启动流程实例

 在完成了流程定义部署后，就要启动流程实例了。

![](http://upload-images.jianshu.io/upload_images/1540531-fdd9c18ef7c37a45.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

 说明：

1. 1)结果为：

![](http://upload-images.jianshu.io/upload_images/1540531-411649536c15e309.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

1. 2)操作数据库的act\_ru\_execution表,如果是用户任务节点，同时也会在act\_ru\_task添加一条记录

## 7.2查询任务

在activiti任务中，主要分为两大类：

1. 确切指定了办理者的任务，这个任务将成为指定者的私有任务
2. 无法指定具体的某一个人来办理的任务，可以把任务分配给几个人或者一到        多个小组，让这个范围内的用户可以选择性（如有空余时间时）来办理这类任        务。

### 7.2.1查询指定用户的代办任务

 对指定用户的未完成的个人任务执行查询。![](http://upload-images.jianshu.io/upload_images/1540531-6c9b426cc62364f9.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240) 

 说明：

1. 1)因为是任务查询，所以从processEngine中应该得到TaskService
2. 2)使用TaskService获取到任务查询对象TaskQuery
3. 3)为查询对象添加查询过滤条件，使用taskAssignee指定任务的候选者（即查询指定用户的代办任务），添加分页排序等过滤条件
4. 4)调用list方法执行查询，返回办理者为指定用户的任务列表
5. 5)任务ID、名称、办理人、创建时间可以从act\_ru\_task表中查到。
6. 6)Execution与ProcessInstance见5.6章节的介绍。在这种情况下，ProcessInstance相当于Execution
7. 7)如果assignee属性为部门经理，结果为空。因为现在流程只到了&quot;填写请假申请&quot;阶段，后面的任务还没有执行，即在数据库中没有部门经理可以办理的任务，所以查询不到。
8. 8)一个Task节点和Execution节点是1对1的情况，在task对象中使用Execution\_来标示他们之间的关系
9. 9)任务ID在数据库表act\_ru\_task中对应&quot;ID\_&quot;列

### 7.2.2查询指定用户的可接任务（公共任务）

 对指定用户的可接取的公共任务执行查询。

![](http://upload-images.jianshu.io/upload_images/1540531-c36af7e385c91601.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240) 

说明：

1. 前面步骤类似，查询任务首先使用TaskService创建TaskQuery对象
2. 在查询对象上，添加taskCandidateUser过滤条件，代表过滤任务候        选者为自己的任务
3. 调用list方法返回指定用户的可接任务列表
4. 所有公共任务的assignee属性为空

## 7.3认领任务

 通常一个任务为公共任务任务都有一个以上的候选者，用户想要办理它应该先进行认领任务操作，即把自己变成任务的拥有者。

![](http://upload-images.jianshu.io/upload_images/1540531-795f756b8b9d7f9a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

说明：

1. 任务相关操作，首先得到taskService
2. 确定被认领的任务ID和认领人ID
3. 调用taskService的claim（认领）方法，把公共任务变成指定用户的        私有任务

## 7.4办理任务

 指定任务ID,完成该任务。

![](http://upload-images.jianshu.io/upload_images/1540531-55eec6e5ea2a4e34.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

 说明：

1. 1)是办理任务，所以从ProcessEngine得到的是TaskService。
2. 2)当执行完这段代码，再以员工的身份去执行查询的时候，会发现这个时候已经没有数据了。
3. 3)对于执行完的任务，activiti将从act\_ru\_task表中删除该任务，下一个任务会被插入进来。
4. 4)以&quot;部门经理&quot;的身份进行查询，可以查到结果。因为流程执行到部门经理审批这个节点了。
5. 5)再执行办理任务代码，执行完以后以&quot;部门经理&quot;身份进行查询，没有结果。
6. 6)重复第3和4步直到流程执行完。

## 7.5验证流程已经结束

在流程执行的过程中，创建的流程实例ID在整个过程中都不会变，当流程结束后，流程实例将会被删除    

![](http://upload-images.jianshu.io/upload_images/1540531-dabe37427e3ff565.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


 说明：

1. 1)因为是查询流程实例，所以先获取runtimeService
2. 2)创建流程历史查询对象，设置实例ID过滤参数
3. 3)由于一个流程实例ID只对应一个实例，使用singleResult执行查询返回一个唯一的结果，如果结果数量大于1，则抛出异常
4. 4)判断指定ID的实例是否存在，如果结果为空，则代表流程结束，实例已被删除

## 7.8流程历史

在前一个的例子中，大家可能会流程执行完毕后，究竟去了哪里有些疑问。虽然已完成的任务在act\_ru\_task和act\_ru\_execution表中都已被删除，但是这些数据还存在activiti的数据库中，作为历史改由HistoryService来管理。

历史是一个组件，它可以捕获发生在进程执行中的信息并永久的保存，与运行时数据不同的是，当流程实例运行完成之后它还会存在于数据库中。

在流程引擎配置对象中可以设置历史记录规则：

![](http://upload-images.jianshu.io/upload_images/1540531-855c584c26f23d9e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

由于数据库中保存着历史信息以及正在运行的流程实例信息，在实际项目中对已完成任务的查看频率远不及对代办和可接任务的查看，所以在activiti采用分开管理，把正在运行的交给runtimeService管理，而历史数据交给HistoryService来管理。

对已成为历史的数据主要进行查询操作，我们主要关心两种类型的历史数据：

HistoricProcessInstance 包含当前和已经结束的流程实例信息。

HistoricActivityInstance 包含一个活动(流程上的节点)的执行信息。

### 7.8.1查看历史流程实例

查看用户按照某个流程规则执行了多少次流程。

![](http://upload-images.jianshu.io/upload_images/1540531-d282e39ae6ca254a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

说明：

1. 通常查询历史流程实例都需要指定一个过滤条件，指定        processDefinitionId查看具体某一次部署所开启的流程或者指定        processDefinitionKey查看某个规则下不限版本的所有流程
2. 可以选择性添加finished方法控制是否查询未完成的流程实例。在流        程开启时，activiti同时在act\_ru\_execution表和act\_hi\_procinst表中        创建了一条记录，在流程完成之前act\_hi\_procinst表中实例的结束时间        为空

### 7.8.2查看历史流程活动

查看某次流程的执行执行经理。



![](http://upload-images.jianshu.io/upload_images/1540531-4aac7bd14bc16c07.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240) 

说明：

1. 通常查询历史流程活动都需要指定一个过滤条件，指定processInstanceId查看具体某一次流程执行过程中所经历的步奏

# 八、流程变量

 流程变量在整个工作流中扮演很重要的作用。例如：请假流程中有请假天数、请假原因等一些参数都为流程变量的范围。流程变量的作用域范围是流程实例。也就是说各个流程实例的流程变量是不相互影响的。流程实例结束完成以后流程变量还保存在数据库中。

![](http://upload-images.jianshu.io/upload_images/1540531-dccff1280249074c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240) 

## 8.1添加流程变量

### 8.1.1在启动流程实例时

 在启动流程实例时，可以添加流程变量。这是添加流程变量的一种时机。

![](http://upload-images.jianshu.io/upload_images/1540531-964540e05f2598b6.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

 说明：

1. 1)在启动流程实例时，通过重载startProcessInstanceByKey的方法可以加载流程变量。
2. 2)第二个参数要求是Map&lt;String ,Object&gt;类型，意味着可以添加多个流程变量。
3. 3)当这段代码执行完以后，会在数据库表act\_ru\_variable中添加两行记录。

### 8.1.2在办理任务时

 在办理任务时，有时候任务办理完成以后，要传递一些信息到系统中。这个时候可以利用TaskService这个类来添加流程实例。

![](http://upload-images.jianshu.io/upload_images/1540531-131939e395d201b4.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

 说明：

1. 1)利用setVariables方法在任务办理过程中添加一批流程变量。
2. 2)利用setVariable方法在任务办理过程中添加一个流程变量。
3. 3)TaskService有一个重载complete方法：

![](http://upload-images.jianshu.io/upload_images/1540531-60b46e6e8c997d2a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### 8.1.3执行流程实例时

 因为流程变量的作用域就是流程实例，所以只要设置就行了，不用管在哪个阶段。设置代码如下：

![](http://upload-images.jianshu.io/upload_images/1540531-37c03a21304e8aab.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 8.2获取流程变量

 可以通过runTimeService的方法来获取流程变量。

![](http://upload-images.jianshu.io/upload_images/1540531-1606d9b578c2f815.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


 说明：这些流程变量是从act\_ru\_variable这个表中读出来的。

## 8.3流程变量范围

 如图是从官网列出来的流程变量的类型：

![](http://upload-images.jianshu.io/upload_images/1540531-08c2b85a26121d3c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

 从图中可以看出包括了大部分封装类型和Date、String和实现了Serializable接口的类的类型。

## 8.4JavaBean流程变量

### 8.4.1实现了Serializable的JavaBean

 步骤如下：

1. 1)加一个javabean，这个javabean实现了Serializable接口

![](http://upload-images.jianshu.io/upload_images/1540531-8db0ae7b9e32e474.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

1. 2)添加流程变量

![](http://upload-images.jianshu.io/upload_images/1540531-fa29c4d90c6c7a46.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

1. 3)获取流程变量

![](http://upload-images.jianshu.io/upload_images/1540531-f719df305b1e7b38.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

# 九、流程定义语言

## 9.1流程(process)

 bpmn文件一个流程的根元素。一个流程就代表一个工作流。

## 9.2顺序流(sequenceFlow )

 顺序流是连接两个流程节点的连线，代表一个节点的出口。流程执行完一个节点后，会沿着节点的所有外出顺序流继续执行。就是说，BPMN 2.0默认的行为就是并发的：两个外出顺序流会创造两个单独的，并发流程分支。

顺序流主要由4个属性组成：

Id: 唯一标示，用来区分不同的顺序流

sourceRef：连线的源头节点ID

targetRef：连线的目标节点ID

name（可选）：连线的名称，不涉及业务，主要用于显示

 说明：

1. 1)结束节点没有出口
2. 2)其他节点有一个或多个出口。如果有一个出口，则代表是一个单线流程；如果有多个出口，则代表是开启并发流程。

## 9.3节点

- 开始节点
- 结束节点
- 任务节点
- 网关节点
- 事件节点

### 9.3.1开始事件节点(startEvent)

 开始事件用来指明流程在哪里开始。开始事件的类型（流程在接收事件时启动，还是在指定时间启动，等等），定义了流程如何启动，这通过事件中不同的小图表来展示。在XML中，这些类型是通过声明不同的子元素来区分的。

#### 9.3.1.1空开始事件

空开始事件技术上意味着没有指定启动流程实例的触发条件。最常用的一种开始，意味着流程的启动需要手动触发，通过调用api的startProcessInstanceByXXX方法。

注意：子流程都有一个空开始事件。

```javascript
ProcessInstanceprocessInstance =runtimeService.startProcessInstanceByXXX();
```

图形标记：

空开始事件显示成一个圆圈，没有内部图表（没有触发类型）

![](http://upload-images.jianshu.io/upload_images/1540531-efbbd96fc02f11fc.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

XML结构如下：

```xml
<startEventid="start"name="my start event"/>
```



#### 9.3.1.2定时开始事件

定时开始事件用来在指定的时间创建流程实例。它可以同时用于只启动一次的流程和应该在特定时间间隔启动多次的流程。

注意：

1. 子流程不能使用定时开始事件。
2. 定时开始事件在流程发布后就会开始计算时间。不需要调用startProcessInstanceByXXX，虽然也而已调用启动流程的方法，但是        那会导致调用startProcessInstanceByXXX时启动过多的流程。
3. 当包含定时开始事件的新版本流程部署时，对应的上一个定时器就会        被删除。这是因为通常不希望自动启动旧版本流程的流程实例。

图形标记

定时开始事件显示为一个圆圈，内部是一个表。

![](http://upload-images.jianshu.io/upload_images/1540531-dc78b234d6ef977b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

XML内容：

定时开始事件的XML内容是普通开始事件的声明，包含一个定时定义子元素。

示例：流程会启动4次，每次间隔5分钟，从2013年9月18日，12:10开始计时。

```xml
        <startEvent id="theStart">
          <timerEventDefinition>                
		<timeCycle>R4/2013-09-18T12:13/PT5M</timeCycle>            		 
	</timerEventDefinition>
        </startEvent>  

```

​        


示例：流程会根据选中的时间启动一次。

```xml
        <startEventid="theStart">            
	<timerEventDefinition>                
		<timeDate>2013-10-31T23:59:24</timeDate>            
	</timerEventDefinition>        
        </startEvent>           
```




### 9.3.2结束事件节点(endEvent)

结束事件表示（子）流程（分支）的结束。结束事件都是触发事件。这是说当流程达到结束事件，会触发一个结果。结果的类型是通过事件的内部黑色图标表示的。

9.3.2.1空结束事件

空结束事件意味着到达事件时不会指定抛出的结果。这样，引擎会直接结束当前执行的分支，不会做其他事情。

图形标记:

空结束事件是一个粗边圆圈，内部没有小图表（无结果类型）

![](http://upload-images.jianshu.io/upload_images/1540531-c09de4216682ffc7.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

XML内容:

空结束事件的XML内容是普通结束事件定义，不包含子元素（其他结束事件类型都会包含声明类型的子元素）。

```xml
<endEvent id="end" name="my end event"/>
```

### 9.3.3任务节点(Task)

#### 9.3.3.1接收任务节点 (receiveTask)

接收任务是一个简单任务，它会等待对应消息的到达。当前，官方只实现了这个任务的java语义。当流程达到接收任务，流程状态会保存到数据库中。

在任务创建后，意味着流程会进入等待状态，直到引擎接收了一个特定的消息，这会触发流程穿过接收任务继续执行。

图形标记：

接收任务显示为一个任务（圆角矩形），右上角有一个消息小标记。消息是白色的（黑色图标表示发送语义）

![](http://upload-images.jianshu.io/upload_images/1540531-d77d2028d3f98337.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

XML内容：

```xml
<receiveTask id="waitState" name="wait"/>   
```

当前任务（一般指机器自动完成，但需要耗费一定时间的工作）完成后，向后推移流程，可以调用runtimeService.signal(executionId)，传递接收任务上流程的id。

演示代码如下：

```java
ProcessInstance pi =runtimeService.startProcessInstanceByKey("receiveTask");
Execution execution=runtimeService.createExecutionQuery() .processInstanceId(pi.getId())         .activityId("waitState").singleResult();
assertNotNull(execution);
runtimeService.signal(execution.getId());         
```



#### 9.3.3.2用户任务节点(userTask)

用户任务用来设置必须由人员完成的工作。当流程执行到用户任务，会创建一个新任务，并把这个新任务加入到分配人或群组的任务列表中。

图形标记

用户任务显示成一个普通任务（圆角矩形），左上角有一个小用户图标。

![](http://upload-images.jianshu.io/upload_images/1540531-03f9435a19c913e4.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

XML内容

XML中的用户任务定义如下。id属性是必须的。name属性是可选的。

```xml
<userTask id="theTask" name="Important task"/>   
```

用户任务也可以设置描述(实际上所有BPMN 2.0元素都可以设置描述)。添加documentation元素可以定义描述。

```xml
<userTask id="theTask" name="Schedule meeting">  
<documentation>
          Schedule an engineering meeting for next week with the new hire.  
</documentation>
```

在实际应用中，用户接到任务后可以参照任务描述来办理任务，描述文本可以通过标准的java方法来获得：

```xml
task.getDescription()
```

任务分配

用户任务的办理都需要人工的参与。用户任务可以分为两大类。私有任务和公有任务（可接任务）。

- 私有任务

私有任务即有直接分配给指定用户的任务。只有一个用户可以成为任务 的执行者。在activiti中，用户叫做执行者。拥有执行者的用户任务 （即私有任务）对其他用户是不可见的。只能出现执行者的个人任务列表中.

直接把用户任务分配给指定用户使用assignee属性，XML代码如下：

```xml
<userTask id="theTask" name="my task" activiti:assignee="sirius"/>
```

Assignee属性对应的值为一个用户的ID。

直接分配给用户的任务可以通过TaskService像下面这样办理：

```java
List<Task>tasks =taskService.createTaskQuery().taskAssignee("sirius").list();
Task task = tasks.get(0);// 假设任务集合的第一条是要办理的任务
taskService.complete(task.getId());
```

- 公有任务

有的用户任务在指派时无法确定具体的办理者，这时任务也可以加入到 人员的候选任务列表中，然后让这些人员选择性认领和办理任务。

公有任务的分配可以分为指定候选用户和候选组两种。

a) 把任务添加到一批用户的候选任务列表中，使用candidateUsers 属 性，XML内容如下：

```xml
<userTaskid="theTask"name="my task"activiti:candidateUsers="sirius,kermit"/>
```

candidateUsers属性内为用户的ID，多个用户ID之间使用（半角）逗 号间隔。

b)  把任务添加到一个或多个候选组下，这时任务对组下的所有用户可 见，首先得保证每个组下面有用户，通过IdentityService对象创建用户 和组，然后把用户添加到对应的组下。

 然后配置组任务，使用candidateGroups属性，XML内容如下：

```xml
<userTask  
	id="theTask"   
	name="my task"    
    activiti:candidateGroups="testGroup，developGroup"/>
```


间接分配给用户的任务，可以通过TaskService像下面这样操作：

```java
ListTasktasks =taskService.createTaskQuery().taskCandidateUser("sirius").list();
Tasktask = tasks.get(0);// 假设任务集合的第一条是要办理的任务

String taskId = task.getId();

taskService.claim(taskId ,sirius); //认领任务，让用户成为任务的执行者

taskService.complete(taskId );
```


说明：

1. 要维护用户和组得使用用户管理服务对象，使用                processEngine        得到IdentityService。
2. 要分配组任务必须先创建组，而且组下得有用户，用户和组的        最关键属性是ID。
3. 使用newUser（userId）和newGroup（groupId）创建用户        和组。
4. 使用createMembership（userId，groupId）把用户挂到        组下。
5. 办理候选任务，首先得认领任务，让用户成为任务的执行者。

如果上面的方式还不够灵活，那么我们也可以自定义一个任务分配处理器，通过代码的方式来动态设置任务的属性。XML代码如下：

```xml
<userTask id="task1" name="My task"> 
    <extensionElements>    
<activiti:taskListener event="create" class="org.activiti.MyAssignmentHandler"/>  
    </extensionElements>
</userTask>
```

DelegateTask会传递给TaskListener的实现，通过它可以设置执行人，候选人和候选组：



```java
PublicclassMyAssignmentHandlerimplementsTaskListener{
	Publicvoidnotify(DelegateTaskdelegateTask){
	// 执行用户搜索相关代码
    // 然后把获取到的用户通过下面方法，设置给当前触发事件的任务
        delegateTask.setAssignee(&quot;sirius&quot;);
        //delegateTask.addCandidateUser(&quot;kermit&quot;);
       //delegateTask.addCandidateGroup(&quot;testGroup&quot;);

       ...
       }

}
```
上述两个虽然都可以统称为任务节点，但是还是有本质区别：

1. receiveTask主要代表机器自动执行的，userTask代表人工干预的。
2. receiveTask任务产生后会在act\_ru\_execution表中新增一条记录，        而userTask产生后会在act\_ru\_execution和act\_ru\_task（主要记录任        务的发布时间，办理人等信息）中各产生一条记录。
3. receiveTask任务提交方式使用RuntimeService的signal方法提交，        userTask任务提交方式使用TaskService的complete方法提交。

### 9.3.4网关(gateWay)

 网关用来控制流程的流向。

网关显示成菱形图形，内部有有一个小图标。图标表示网关的类型。

![](http://upload-images.jianshu.io/upload_images/1540531-aeb3cd543ee515b0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

#### 9.3.4.1排他网关(ExclusiveGateWay)

排他网关（也叫异或（XOR）网关，或更技术性的叫法基于数据的排他网关），用来在流程中实现决策。

图形标记

排他网关显示成一个普通网关（比如，菱形图形），内部是一个&quot;X&quot;图标，表示异或（XOR）语义。注意，没有内部图标的网关，默认为排他网关。BPMN 2.0规范不允许在同一个流程定义中同时使用没有X和有X的菱形图形。

![](http://upload-images.jianshu.io/upload_images/1540531-fcab5a76e2796a7c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

XML内容

排他网关的XML内容是很直接的：用一行定义了网关，条件表达式定义在外出顺序流中。参考条件顺序流获得这些表达式的可用配置。

![](http://upload-images.jianshu.io/upload_images/1540531-199f04c057cc0346.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

它对应的XML内容如下：

```xml
<exclusiveGatewayid="exclusiveGw"name="Exclusive Gateway"/>
<sequenceFlowid="flow2"sourceRef="exclusiveGw"targetRef="theTask1">  
        <conditionExpressionxsi:type="tFormalExpression">${input == 1}</conditionExpression> 
</sequenceFlow>	
<sequenceFlowid="flow3"sourceRef="exclusiveGw"targetRef="theTask2">    
        <conditionExpressionxsi:type="tFormalExpression">${input == 2}</conditionExpression> 
</sequenceFlow>
<sequenceFlowid="flow4"sourceRef="exclusiveGw"targetRef="theTask3">  
        <conditionExpressionxsi:type="tFormalExpression">${input == 3}</conditionExpression> 
</sequenceFlow>
```

说明：

1. 一个排他网关对应一个以上的顺序流
2. 由排他网关流出的顺序流都有个conditionExpression元素，在内部维护返回boolean类型的决策结果。
3. 决策网关只会返回一条结果。当流程执行到排他网关时，流程引擎会自动检索网关出口，从上到下检索如果发现第一条决策结果为true或者没有设置条件的(默认为成立)，则流出。
4. 如果没有任何一个出口符合条件则抛出异常。

#### 9.3.4.2并行网关(parallelGateWay)

网关也可以表示流程中的并行情况。最简单的并行网关是parallelGateWay，它允许将流程分成多条分支，也可以把多条分支汇聚到一起。

图形标记

并行网关显示成一个普通网关（菱形）内部是一个&quot;加号&quot;图标，表示&quot;与（AND）&quot;语义。

![](http://upload-images.jianshu.io/upload_images/1540531-505235c9284bdf55.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

XML内容

定义并行网关只需要一行XML：

```xml
<parallelGateway id="myParallelGateway"/>
```

实际发生的行为（分支，聚合，同时分支聚合），要根据并行网关的顺序流来决定。

参考如下代码：

```xml
<startEvent id="theStart"/>
<sequenceFlow id="flow1"sourceRef="theStart"targetRef="fork"/>      <parallelGatewayid="fork"/>         <sequenceFlowsourceRef="fork"targetRef="receivePayment"/>    <sequenceFlowsourceRef="fork"targetRef="shipOrder"/>    
<userTaskid="receivePayment"name="Receive Payment"/>    <sequenceFlowsourceRef="receivePayment"targetRef="join"/>    
<userTaskid="shipOrder"name="Ship Order"/>    <sequenceFlowsourceRef="shipOrder"targetRef="join"/>              <parallelGatewayid="join"/>                 <sequenceFlowsourceRef="join"targetRef="archiveOrder"/>   
<userTaskid="archiveOrder"name="Archive Order"/>    <sequenceFlowsourceRef="archiveOrder"targetRef="theEnd"/>          
<endEventid="theEnd"/>
```

上面例子中，流程启动之后，会创建两个任务：

```java
ProcessInstancepi =runtimeService.startProcessInstanceByKey("forkJoin");
TaskQueryquery=taskService.createTaskQuery().processInstanceId(pi.getId()).orderByTaskName().asc(); 
List<Task>tasks =query.list();
assertEquals(2,tasks.size());                                                             
Task task1 =tasks.get(0);
assertEquals("Receive Payment",task1.getName());Tasktask2 =tasks.get(1);
assertEquals("Ship Order",task2.getName());
```

当两个任务都完成时，第二个并行网关会汇聚两个分支，因为它只有一条外出连线，不会创建并行分支，只会创建归档订单任务。

说明：

1. 并行网关的功能是基于进入和外出的顺序流的：

分支 (fork)： 并行后的所有外出顺序流，为每个顺序流都创建一个并发分支。

汇聚 (join)： 所有到达并行网关，在此等待的进入分支，直到所有进入顺序流的分支都到达以后，流程就会通过汇聚网关。

1. 并行网关的进入和外出都是使用相同节点标示
2. 如果同一个并行网关有多个进入和多个外出顺序流，它就同时具有分支和汇聚功能。这时，网关会先汇聚所有进入的顺序流，然后再切分成多个并行分支。
3. 并行网关不会解析条件。即使顺序流中定义了条件，也会被忽略。
4. 并行网关不会解析条件。 即使顺序流中定义了条件，也会被忽略。
5. 并行网关不需要是&quot;平衡的&quot;（比如，对应并行网关的进入和外出节点数目相等）。如图中标示是合法的：

![](http://upload-images.jianshu.io/upload_images/1540531-c651357b1ffa13a4.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 9.4监听器(Listener)

在流程中我们有时会对整个流程或者一个节点的某种状态做出相应的处理。这时就会用到监听器。

在Activiti中流程的监听主要分为两大类，执行监听器和任务监听器。

### 9.4.1执行监听器(ExecutionListener)

执行监听器可以执行外部java代码或执行表达式，当流程定义中发生了某个事件。可以捕获的事件有:

- 流程实例的启动和结束。
- 选中一条连线。
- 节点的开始和结束。
- 网关的开始和结束。
- 中间事件的开始和结束。
- 开始时间结束或结束事件开始。

现在有这样一个简单流程，只包含开始、结束、接收任务和用户任务4个节点：

![](http://upload-images.jianshu.io/upload_images/1540531-565268a64d2ea4fa.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

配置监听器，XML代码如下：

![](http://upload-images.jianshu.io/upload_images/1540531-ac1b26b0c6014de9.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

说明：

1.任务监听器支持以下属性：

**event** （必选）：任务监听器会被调用的任务类型。可能的类型为：

-  **start** ：流程节点创建后触发。
-  **end** ：当任务完成，并尚未从运行数据中删除时触发。
-  **take** ：任务完成后，流程流出时触发

**class** ：必须调用的代理类。这个类必须实现org.activiti.engine.delegate.

ExecutionListener接口。实现类代码如下：

![](http://upload-images.jianshu.io/upload_images/1540531-19b872942c39e958.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

2.执行监听器配置可以放在以下三个地方，如图：![](http://upload-images.jianshu.io/upload_images/1540531-769da4ffcb5cb7bf.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240) 

1. a)监听整个流程的启动和结束状态，配置为process节点的子元素，如图①
2. b)监听一个节点的启动和结束状态，配置为一个节点的子元素，如图②和③
3. c)监听一条连线的执行，配置在sequenceFlow节点的内部，只有task一种事件，如图④

启动流程测试代码如下：

![](http://upload-images.jianshu.io/upload_images/1540531-b8239ed8e4b6aa73.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240) 

结果如下：

![](http://upload-images.jianshu.io/upload_images/1540531-6697b519eab9d664.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### 9.4.2任务监听器(TaskListener)

任务监听器可以在发生对应的任务相关事件时执行自定义java逻辑或表达式。

任务监听器只能添加到流程定义中的用户任务中。在之前任务节点上添加任务监听:

![](http://upload-images.jianshu.io/upload_images/1540531-5aaa1fcf36cc9460.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

说明：

1.任务监听器支持以下属性：

 **event** （必选）：任务监听器会被调用的任务类型。可能的类型为：

-  **create** ：任务创建并 **设置所有属性后** 触发。
-  **assignment** ：任务分配给一些人时触发。当流程到达userTask，_assignment_事件会在_create_事件 **之前** 发生。这样的顺序似乎不自然，但是原因很简单：当获得_create_时间时，我们想获得任务的所有属性，包括执行人。
-  **complete** ：当任务完成，并尚未从运行数据中删除时触发。

**class** ：必须调用的代理类。这个类必须实现org.activiti.engine.delegate.TaskListener        接口。Java代码如下：

![](http://upload-images.jianshu.io/upload_images/1540531-a3c8ebe6e6ee88b4.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

1. 运行测试代码得到结果:

流程结束，日志内容为：[Start start, Receive Task start, Receive Task end,         Receive Task take, User Task start, User Task assignment, User Task create,         User Task complete, User Task end, End end]

新添加的任务监听包裹在executionListener监听的内部，顺序为：execution Start--&gt; task Assignment--&gt;task Create--&gt;task Complete--&gt;execution End--&gt;execution take。

# 十、Spring集成

 虽然前面的例子中我们可以自己手动来创建相应的API实例，但是在一个项目中这些API都应该以单例形式存在的。和Spring的集成主要就是把Activiti的主要对象交给Spring容器管理。

## 10.1 ProcessEngineFactoryBean

可以把流程引擎（ProcessEngine）作为一个普通的Spring bean进行配置。类org.activiti.spring.ProcessEngineFactoryBean是集成的切入点。这个bean需要一个流程引擎配置对象来创建流程引擎。集成Spring后，配置文件如下：

```xml
<bean id="processEngineConfiguration"    
        class="org.activiti.spring.SpringProcessEngineConfiguration">
 ...
</bean>  
<bean id="processEngine"  class="org.activiti.spring.ProcessEngineFactoryBean">   
   <property name="processEngineConfiguration"  ref="processEngineConfiguration"/>
</bean>  
```

注意现在使用的processEngineConfiguration bean 是org.activiti.spring

.SpringProcessEngineConfiguration 类。

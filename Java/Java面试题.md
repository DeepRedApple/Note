# JAVASE笔试面试题汇总

## 一、静态变量和实例变量的区别？

静态变量也称作类变量，由static修饰，如：static int s; s就是静态变量，它只能通过类来访问。

实例变量没有static修饰符，它只能通过实例对象来访问。

同一类的不同的实例对象有自己的实例变量，但是它们共享同一个静态变量。当一个实例对象修改了它的实例变量时，不会影响其他的实例对象。如果一个实例对象修改了静态变量，则会影响其他的对象实例。

在java中类的静态变量在内存中只有一个，java虚拟机在加载类的过程中为静态变量分配内存，静态变量位于方法区，被类的所有实例共享。静态变量可以直接通过类名进行访问，其生命周期取决于类的生命周期。

而实例变量取决于类的实例。每创建一个实例，java虚拟机就会为实例变量分配一次内存，实例变量位于堆区中，其生命周期取决于实例的生命周期。

## 二、是否可以从一个static方法内部发出对非static方法的调用？

答：不可以,如果静态方法内部包含对象的非静态方法的调用则不能保证对象初始化，所以不能在静态的方法里调用非静态的方法。

## 三、描述一下JVM加载class文件的原理机制?

JVM中类的装载是由ClassLoader和它的子类来实现的,Java ClassLoader 是一个重要的Java运行时系统组件。它负责在运行时查找和装入类文件的类。

## 四、抽象类和接口的区别是什么？

abstractclass和interface是Java语言中对于抽象类定义进行支持的两种机制，正是由于这两种机制的存在，才赋予了Java强大的面向对象能力。abstractclass和interface之间在对于抽象类定义的支持方面具有很大的相似性，甚至可以相互替换，因此很多开发者在进行抽象类定义时对于abstractclass和interface的选择显得比较随意。其实，两者之间还是有很大的区别的，对于它们的选择甚至反映出对于问题领域本质的理解、对于设计意图的理解是否正确、合理。本文将对它们之间的区别进行一番剖析，试图给开发者提供一个在二者之间进行选择的依据。

理解抽象类

abstractclass和interface在Java语言中都是用来进行抽象类（本文中的抽象类并非从abstractclass翻译而来，它表示的是一个抽象体，而abstractclass为Java语言中用于定义抽象类的一种方法，请读者注意区分）定义的，

那么什么是抽象类，使用抽象类能为我们带来什么好处呢？

在面向对象的概念中，我们知道所有的对象都是通过类来描绘的，但是反过来却不是这样。并不是所有的类都是用来描绘对象的，如果一个类中没有包含足够的信息来描绘一个具体的对象，这样的类就是抽象类。抽象类往往用来表征我们在对问题领域进行分析、设计中得出的抽象概念，是对一系列看上去不同，但是本质上相同的具体概念的抽象。比如：如果我们进行一个图形编辑软件的开发，就会发现问题领域存在着圆、三角形这样一些具体概念，它们是不同的，但是它们又都属于形状这样一个概念，形状这个概念在问题领域是不存在的，它就是一个抽象概念。正是因为抽象的概念在问题领域没有对应的具体概念，所以用以表征抽象概念的抽象类是不能够实例化的。

在面向对象领域，抽象类主要用来进行类型隐藏。我们可以构造出一个固定的一组行为的抽象描述，但是这组行为却能够有任意个可能的具体实现方式。这个抽象描述就是抽象类，而这一组任意个可能的具体实现则表现为所有可能的派生类。模块可以操作一个抽象体。由于模块依赖于一个固定的抽象体，因此它可以是不允许修改的；同时，通过从这个抽象体派生，也可扩展此模块的行为功能。熟悉OCP的读者一定知道，为了能够实现面向对象设计的一个最核心的原则OCP(Open-ClosedPrinciple)，抽象类是其中的关键所在。

从语法定义层面看abstractclass和interface

在语法层面，Java语言对于abstractclass和interface给出了不同的定义方式，下面以定义一个名为Demo的抽象类为例来说明这种不同。

使用abstractclass的方式定义Demo抽象类的方式如下：
```java

abstract class Demo｛

	abstract void method1();

	abstract void method2();

…

｝
```

使用interface的方式定义Demo抽象类的方式如下：
```java

interface Demo{

	void method1();

	void method2();

…

}
```

在abstractclass方式中，Demo可以有自己的数据成员，也可以有非abstarct的成员方法，而在interface方式的实现中，Demo只能够有静态的不能被修改的数据成员（也就是必须是staticfinal的，不过在interface中一般不定义数据成员），所有的成员方法都是abstract的。从某种意义上说，interface是一种特殊形式的abstractclass。

从编程的角度来看，abstractclass和interface都可以用来实现&quot;designbycontract&quot;的思想。但是在具体的使用上面还是有一些区别的。

首先，abstractclass在Java语言中表示的是一种继承关系，一个类只能使用一次继承关系。但是，一个类却可以实现多个interface。也许，这是Java语言的设计者在考虑Java对于多重继承的支持方面的一种折中考虑吧。

其次，在abstractclass的定义中，我们可以赋予方法的默认行为。但是在interface的定义中，方法却不能拥有默认行为，为了绕过这个限制，必须使用委托，但是这会增加一些复杂性，有时会造成很大的麻烦。

在抽象类中不能定义默认行为还存在另一个比较严重的问题，那就是可能会造成维护上的麻烦。因为如果后来想修改类的界面（一般通过abstractclass或者interface来表示）以适应新的情况（比如，添加新的方法或者给已用的方法中添加新的参数）时，就会非常的麻烦，可能要花费很多的时间（对于派生类很多的情况，尤为如此）。但是如果界面是通过abstractclass来实现的，那么可能就只需要修改定义在abstractclass中的默认行为就可以了。

同样，如果不能在抽象类中定义默认行为，就会导致同样的方法实现出现在该抽象类的每一个派生类中，违反了&quot;onerule，oneplace&quot;原则，造成代码重复，同样不利于以后的维护。因此，在abstractclass和interface间进行选择时要非常的小心。

从设计理念层面看abstractclass和interface

上面主要从语法定义和编程的角度论述了abstractclass和interface的区别，这些层面的区别是比较低层次的、非本质的。本小节将从另一个层面：abstractclass和interface所反映出的设计理念，来分析一下二者的区别。作者认为，从这个层面进行分析才能理解二者概念的本质所在。

前面已经提到过，abstarctclass在Java语言中体现了一种继承关系，要想使得继承关系合理，父类和派生类之间必须存在&quot;isa&quot;关系，即父类和派生类在概念本质上应该是相同的（参考文献〔3〕中有关于&quot;isa&quot;关系的大篇幅深入的论述，有兴趣的读者可以参考）。对于interface来说则不然，并不要求interface的实现者和interface定义在概念本质上是一致的，仅仅是实现了interface定义的契约而已。为了使论述便于理解，下面将通过一个简单的实例进行说明。

考虑这样一个例子，假设在我们的问题领域中有一个关于Door的抽象概念，该Door具有执行两个动作open和close，此时我们可以通过abstractclass或者interface来定义一个表示该抽象概念的类型，定义方式分别如下所示：

使用abstractclass方式定义Door：
```java

abstract class Door{

	abstract void open();

	abstract void close()；

}
```

使用interface方式定义Door：

```java
interface Door{

	void open();

	void close();

}

```

其他具体的Door类型可以extends使用abstractclass方式定义的Door或者implements使用interface方式定义的Door。看起来好像使用abstractclass和interface没有大的区别。

如果现在要求Door还要具有报警的功能。我们该如何设计针对该例子的类结构呢（在本例中，主要是为了展示abstractclass和interface反映在设计理念上的区别，其他方面无关的问题都做了简化或者忽略）？下面将罗列出可能的解决方案，并从设计理念层面对这些不同的方案进行分析。

解决方案一：

简单的在Door的定义中增加一个alarm方法，如下：

abstractclassDoor{

abstractvoidopen();

abstractvoidclose()；

abstractvoidalarm();

}

或者

interfaceDoor{

voidopen();

voidclose();

voidalarm();

}

那么具有报警功能的AlarmDoor的定义方式如下：

classAlarmDoorextendsDoor{

voidopen(){…}

voidclose(){…}

voidalarm(){…}

}

或者

classAlarmDoorimplementsDoor｛

voidopen(){…}

voidclose(){…}

voidalarm(){…}

｝

这种方法违反了面向对象设计中的一个核心原则ISP（InterfaceSegregationPriciple），在Door的定义中把Door概念本身固有的行为方法和另外一个概念&quot;报警器&quot;的行为方法混在了一起。这样引起的一个问题是那些仅仅依赖于Door这个概念的模块会因为&quot;报警器&quot;这个概念的改变（比如：修改alarm方法的参数）而改变，反之依然。

解决方案二：

既然open、close和alarm属于两个不同的概念，根据ISP原则应该把它们分别定义在代表这两个概念的抽象类中。定义方式有：这两个概念都使用abstractclass方式定义；两个概念都使用interface方式定义；一个概念使用abstractclass方式定义，另一个概念使用interface方式定义。

显然，由于Java语言不支持多重继承，所以两个概念都使用abstractclass方式定义是不可行的。后面两种方式都是可行的，但是对于它们的选择却反映出对于问题领域中的概念本质的理解、对于设计意图的反映是否正确、合理。我们一一来分析、说明。

如果两个概念都使用interface方式来定义，那么就反映出两个问题：1、我们可能没有理解清楚问题领域，AlarmDoor在概念本质上到底是Door还是报警器？2、如果我们对于问题领域的理解没有问题，比如：我们通过对于问题领域的分析发现AlarmDoor在概念本质上和Door是一致的，那么我们在实现时就没有能够正确的揭示我们的设计意图，因为在这两个概念的定义上（均使用interface方式定义）反映不出上述含义。

如果我们对于问题领域的理解是：AlarmDoor在概念本质上是Door，同时它有具有报警的功能。我们该如何来设计、实现来明确的反映出我们的意思呢？前面已经说过，abstractclass在Java语言中表示一种继承关系，而继承关系在本质上是&quot;isa&quot;关系。所以对于Door这个概念，我们应该使用abstarctclass方式来定义。另外，AlarmDoor又具有报警功能，说明它又能够完成报警概念中定义的行为，所以报警概念可以通过interface方式定义。如下所示：

abstractclassDoor{

abstractvoidopen();

abstractvoidclose()；

}

interfaceAlarm{

voidalarm();

}

classAlarmDoorextendsDoorimplementsAlarm{

voidopen(){…}

voidclose(){…}

voidalarm(){…}

}

这种实现方式基本上能够明确的反映出我们对于问题领域的理解，正确的揭示我们的设计意图。其实abstractclass表示的是&quot;isa&quot;关系，interface表示的是&quot;likea&quot;关系，大家在选择时可以作为一个依据，当然这是建立在对问题领域的理解上的，比如：如果我们认为AlarmDoor在概念本质上是报警器，同时又具有Door的功能，那么上述的定义方式就要反过来了。

## 五、JSP中动态INCLUDE与静态INCLUDE的区别？

　　动态INCLUDE用jsp:include动作实现

&lt;jsp:include page=&quot;included.jsp&quot; flush=&quot;true&quot;/&gt;

　　它总是会检查所含文件中的变化，适合用于包含动态页面，并且可以带参数

　　静态INCLUDE用include伪码实现,定不会检查所含文件的变化，适用于包含静态页面

　　&lt;%@ include file=&quot;included.htm&quot; %&gt;

## 六、&amp;和&amp;&amp;的区别？

&amp;是位运算符，表示按位与运算，&amp;&amp;是逻辑运算符，表示逻辑与（and）。

## 七、swtich是否能作用在byte上，是否能作用在long上，是否能作用在String上?

switch（expr1）中，expr1是一个整数表达式。因此传递给switch 和case 语句的参数应该是int、short、char 或者byte。long,string 都不能作用于swtich。

## 八、heap和stack有什么区别。

栈是后进先出的线性表结构，存取速度比堆快。创建对象的时候new一个对象，引用存在栈上具体的内容存在堆上。

栈与堆都是Java用来在RAM中存放数据的地方。与C++不同，Java自动管理栈和堆，程序员不能直接地设置栈或堆。

Java的堆是一个运行时数据区,类的对象从中分配空间。这些对象通过new指令建立，它们不需要程序代码来显式的释放。堆是由垃圾回收来负责的，

堆的优势是可以动态地分配内存大小，生存期也不必事先告诉编译器，因为它是在运行时动态分配内存的，Java的垃圾收集器会自动收走这些不再使用的数据。但缺点是，

由于要在运行时动态分配内存，存取速度较慢。

栈的优势是，存取速度比堆要快，仅次于寄存器，栈数据可以共享。

但缺点是，存在栈中的数据大小与生存期必须是确定的，缺乏灵活性。栈中主要存放一些基本类型的变量（,int, short, long, byte, float, double, boolean, char）和对象句柄。

栈有一个很重要的特殊性，就是存在栈中的数据可以共享。假设我们同时定义：

int a = 3;

int b = 3；

编译器先处理int a = 3；首先它会在栈中创建一个变量为a的引用，然后查找栈中是否有3这个值，如果没找到，就将3存放进来，然后将a指向3。接着处理int b = 3；在创建完b的引用变量后，

因为在栈中已经有3这个值，便将b直接指向3。这样，就出现了a与b同时均指向3的情况。

这时，如果再令a=4；那么编译器会重新搜索栈中是否有4值，如果没有，则将4存放进来，并令a指向4；如果已经有了，则直接将a指向这个地址。因此a值的改变不会影响到b的值。

要注意这种数据的共享与两个对象的引用同时指向一个对象的这种共享是不同的，因为这种情况a的修改并不会影响到b, 它是由编译器完成的，它有利于节省空间。而一个对象引用变量修改了这个对象的内部状态，会影响到另一个对象引用变量。

String是一个特殊的包装类数据。可以用：

String str = new String(&quot;abc&quot;);

String str = &quot;abc&quot;;

两种的形式来创建，第一种是用new()来新建对象的，它会在存放于堆中。每调用一次就会创建一个新的对象。

而第二种是先在栈中创建一个对String类的对象引用变量str，然后查找栈中有没有存放&quot;abc&quot;，如果没有，则将&quot;abc&quot;存放进栈，并令str指向&quot;abc&quot;，如果已经有&quot;abc&quot; 则直接令str指向&quot;abc&quot;。

比较类里面的数值是否相等时，用equals()方法；当测试两个包装类的引用是否指向同一个对象时，用==，下面用例子说明上面的理论。

String str1 = &quot;abc&quot;;

String str2 = &quot;abc&quot;;

System.out.println(str1==str2); //true

可以看出str1和str2是指向同一个对象的。

String str1 =new String (&quot;abc&quot;);

String str2 =new String (&quot;abc&quot;);

System.out.println(str1==str2); // false

用new的方式是生成不同的对象。每一次生成一个。

因此用第一种方式创建多个&quot;abc&quot;字符串,在内存中其实只存在一个对象而已. 这种写法有利与节省内存空间. 同时它可以在一定程度上提高程序的运行速度，因为JVM会自动根据栈中数据的实际情况来决定是否有必要创建新对象。而对于String str = new String(&quot;abc&quot;)；的代码，则一概在堆中创建新对象，而不管其字符串值是否相等，是否有必要创建新对象，从而加重了程序的负担。

另一方面, 要注意: 我们在使用诸如String str = &quot;abc&quot;；的格式定义类时，总是想当然地认为，创建了String类的对象str。担心陷阱！对象可能并没有被创建！而可能只是指向一个先前已经创建的对象。只有通过new()方法才能保证每次都创建一个新的对象。

由于String类的immutable性质，当String变量需要经常变换其值时，应该考虑使用StringBuffer类，以提高程序效率。

2.2申请后系统的响应

栈：只要栈的剩余空间大于所申请空间，系统将为程序提供内存，否则将报异常提示栈溢出。

堆： 首先应该知道操作系统有一个记录空闲内存地址的链表，当系统收到程序的申请时，会遍历该链表，寻找第一个空间大于所申请空间的堆结点，然后将该结点从空闲 结点链表中删除，

并将该结点的空间分配给程序，另外，对于大多数系统，会在这块内存空间中的首地址处记录本次分配的大小，这样，代码中的delete语句才能正确的释放本内存空间。

另外，由于找到的堆结点的大小不一定正好等于申请的大小，系统会自动的将多余的那部分重新放入空闲链表中。

2.3申请大小的限制

栈：在Windows下,栈是向低地址扩展的数据结构，是一块连续的内存的区域。这句话的意思是栈顶的地址和栈的最大容量是系统预先规定好的，在WINDOWS下，栈的大小是2M

（也可能是1M，它是一个编译时就确定的常数），如果申请的空间超过栈的剩余空间时，将提示overflow。因此，能从栈获得的空间较小。

堆：堆是向高地址扩展的数据结构，是不连续的内存区域。这是由于系统是用链表来存储的空闲内存地址的，自然是不连续的，而链表的遍历方向是由低地址向高地址。

堆的大小受限于计算机系统中有效的虚拟内存。由此可见，堆获得的空间比较灵活，也比较大。

2.4申请效率的比较：

栈由系统自动分配，速度较快。但程序员是无法控制的。

堆是由new分配的内存，一般速度比较慢，而且容易产生内存碎片,不过用起来最方便.

另外，在WINDOWS下，最好的方式是用VirtualAlloc分配内存，他不是在堆，也不是在栈是直接在进程的地址空间中保留一快内存，虽然用起来最不方便。但是速度快，也最灵活。

2.5堆和栈中的存储内容

栈：在函数调用时，第一个进栈的是主函数中后的下一条指令（函数调用语句的下一条可执行语句）的地址，然后是函数的各个参数，在大多数的C编译器中，

参数是由右往左入栈的，然后是函数中的局部变量。注意静态变量是不入栈的。当本次函数调用结束后，局部变量先出栈，然后是参数，最后栈顶指针指向最开始存的地址，

也就是主函数中的下一条指令，程序由该点继续运行。

堆：一般是在堆的头部用一个字节存放堆的大小。堆中的具体内容有程序员安排。

2.6存取效率的比较        char s1[] = &quot;aaaaaaaaaaaaaaa&quot;;        char \*s2 = &quot;bbbbbbbbbbbbbbbbb&quot;;        aaaaaaaaaaa是在运行时刻赋值的；

而bbbbbbbbbbb是在编译时就确定的；        但是，在以后的存取中，在栈上的数组比指针所指向的字符串(例如堆)快。

比如： void main(){char a = 1;char c[] = &quot;1234567890&quot;;char \*p =&quot;1234567890&quot;;a = c[1];a = p[1];return;}对应的汇编代码 10: a = c[1];00401067 8A 4D F1 mov cl,byte ptr [ebp-0Fh]0040106A 88 4D FC mov byte ptr [ebp-4],cl11: a = p[1];0040106D 8B 55 EC mov edx,dword ptr [ebp-14h]00401070 8A 42 01 mov al,byte ptr [edx+1]00401073 88 45 FC mov byte ptr [ebp-4],al第一种在读取时直接就把字符串中的元素读到寄存器cl中，而第二种则要先把指针值读到edx中，在根据edx读取字符，显然慢了。        2.7小结：        堆和栈的区别可以用如下的比喻来看出：        使用栈就象我们去饭馆里吃饭，只管点菜（发出申请）、付钱、和吃（使用），吃饱了就走，不必理会切菜、洗菜等准备工作和洗碗、刷锅等扫尾工作，他的好处是快捷，但是自由度小。        使用堆就象是自己动手做喜欢吃的菜肴，比较麻烦，但是比较符合自己的口味，而且自由度大。

## 九、怎样用最有效率的方法算出2乘以几等於16?

2 << 3

## 十、编程题: 用最有效率的方法算出2乘以8等於几?

有C背景的程序员特别喜欢问这种问题。
2 << 3

## 十一、简述逻辑操作(&amp;,|,^)与条件操作(&amp;&amp;,||)的区别。

区别主要答两点：a.条件操作只能操作布尔型的,而逻辑操作不仅可以操作布尔型,而且可以操作数值型

b.逻辑操作不会产生短路

## 十二、名词解释

J2EE,J2ME,SOAP,WEBSERVICE,JNDI,JDO,IIOP,LDAP,JNI,ERP,CRM,JMS,DOM,MVC,EJB,UML,DTD,XML的两种解析方式和定义方式;JAXP,RMI,JTA,JAF,GDI类,UML,CORBA,LINUX下线程,web容器,EJB容器,JDO;WEB SERVICE名词解释。JSWDL开发包的介绍。JAXP、JAXM的解释。SOAP、UDDI,WSDL解释

1 . J2EE （Java 2 Enterprise Edition）是一种利用Java 2平台来简化企业解决方案的开发、部署和管理相关的复杂问题的体系结构。J2EE技术的基础就是核心Java平台或Java 2平台的标准版，J2EE不仅巩固了标准版中的许多优点，同时还提供了对EJB（Enterprise JavaBeans）、Java Servlets API、JSP（Java Server Pages）以及XML技术的全面支持。其最终目的就是成为一个能够使企业开发者大幅缩短投放市场时间的体系结构。

J2EE是Sun公司提出的多层(multi-diered),分布式(distributed),基于组件(component-base)的企业级应用模型(enterpriese application model).在这样的一个应用系统中，可按照功能划分为不同的组件，这些组件又可在不同计算机上，并且处于相应的层次(tier)中。所属层次包括客户层(clietn tier)组件,web层和组件,Business层和组件,企业信息系统(EIS)层。

J2EE也是一个框架，包括JDBC、JNDI、RMI、JMS、EJB、JTA等技术。

2 . J2ME (Java 2 Micro Edition)是Sun公司专门用于嵌入式设备的Java软件

3. SOAP(Simple Object Access Protocol）简单对象访问协议:是一种轻量的、简单的、基于XML 的协议，它被设计成在WEB 上交换结构化的和固化的信息。SOAP 可以和现存的许多因特网协议和格式结合使用，包括超文本传输协议（HTTP），简单邮件传输协议（SMTP），多用途网际邮件扩充协议（MIME）。它还支持从消息系统到远程过程调用（RPC）等大量的应用程序。

4. WEB SERVICE：是一个SOA（面向服务的编程）的架构，它是不依赖于语言，不依赖于平台，可以实现不同的语言间的相互调用，通过Internet进行基于Http协议的网络应用间的交互。

5. JNDI（Java Naming &amp; Directory Interface）JAVA命名目录服务。主要提供的功能是：提供一个目录系统，让其它各地的应用程序在其上面留下自己的索引，从而满足快速查找和定位分布式应用程序的功能。访问各种命名和目录服务的通用、统一的接口，类似JDBC都是构建在抽象层上。

6. JDO(java data object)是Java对象持久化的新的规范，也是一个用于存取某种数据仓库中的对象的标准化API。

7. IIOP（Internet Inter-ORB Protocol):互联网内部对象请求代理协议，Java中使得程序可以和其他语言的CORBA实现实现互操作性的协议。

8. LDAP（Lightweight Directory Access Protocol）轻量目录访问协议

9. JNI(Java Native Interface ) java本地编程接口,他能够使java代码与用其他编程语言编写的应用程序和库进行互操作。

10. ERP(Enterprise Resource Planning)企业资源计划,是指建立在信息技术基础上，以系统化的管理思想，为企业决策层及员工提供决策运行手段的管理平台。

11. CRM（Customer Relationship Management）是一套基于大型数据仓库的客户资料管理系统

12.JMS（Java Message Service）JAVA消息服务。主要实现各个应用程序之间的通讯。包括点对点和广播。

13.DOM（文档对象模型），用来解析XML。

14.MVC是Model－View－Controller的简写。&quot;Model&quot; 代表的是应用的业务逻辑（通过JavaBean，EJB组件实现），&quot;View&quot; 是应用的表示面（由JSP页面产生），&quot;Controller&quot; 是提供应用的处理过程控制（一般是一个Servlet），通过这种设计模型把应用逻辑，处理过程和显示逻辑分成不同的组件实现。这些组件可以进行交互和重用。

设计模式方面

15.EJB（企业级Java Bean），作为Model，可以封装数据（实体Bean），也可以表示业务功能（会话Bean），作为Model，可以处理消息（MDB）。

16.UML（统一建模语言），提供画图的规范。Rose，Visio。

17.DTD（文档类型定义），规定XML文件的格式。

18.XML（可扩展的标记语言）解析方式有：

DOM：处理大型文件时其性能下降的非常厉害。这个问题是由DOM的树结构所造成的，这种结构占用的内存较多，而且DOM必须在解析文件之前把整个文档装入内存,适合对XML的随机访问；

SAX：不同于DOM,SAX是事件驱动型的XML解析方式。它顺序读取XML文件，不需要一次全部装载整个文件。当遇到像文件开头，文档结束，或者标签开头与标签结束时，它会触发一个事件，用户通过在其回调事件中写入处理代码来处理XML文件，适合对XML的顺序访问。

定义方式：DTD(文档类型定义)和Schema。

19.JAXP（解析XML的Java API）.

20.RMI:（Remote Method Invocation /internet对象请求中介协议）他们主要用于通过远程调用服务。例如，远程有一台计算机上运行一个程序，它提供股票分析服务，我们可以在本地计算机上实现对其直接调用。当然这是要通过一定的规范才能在异构的系统之间进行通信。RMI是JAVA特有的。

21.JTA（Java Transaction API）JAVA事务服务。提供各种分布式事务服务。应用程序只需调用其提供的接口即可。

22.JAF（Java Action FrameWork）JAVA安全认证框架。提供一些安全控制方面的框架。让开发者通过各种部署和自定义实现自己的个性安全控制策略。

23.GDI类为图像设备编程接口类库。

24.UML方面标准建模语言UML。用例图,静态图(包括类图、对象图和包图),行为图,交互图(顺序图,合作图),实现图,

25.CORBA 标准是公共对象请求代理结构(Common Object Request Broker Architecture)，由对象管理组织(Object Management Group，缩写为OMG)标准化。它的组成是接口定义语言(IDL), 语言绑定(binding:也译为联编)和允许应用程序间互操作的协议。

其目的为：

用不同的程序设计语言书写

在不同的进程中运行

为不同的操作系统开发

26.LINUX实现的就是基于核心轻量级进程的&quot;一对一&quot;线程模型，一个线程实体对应一个核心轻量级进程，而线程之间的管理在核外函数库中实现。

27.web容器给处于其中的应用程序组件（JSP，SERVLET）提供一个环境，使JSP,SERVLET直接更容器中的环境变量接\*\*互，不必关注其它系统问题。主要有WEB服务器来实现。例如：TOMCAT,WEBLOGIC,WEBSPHERE等。该容器提供的接口严格遵守J2EE规范中的WEB APPLICATION 标准。我们把遵守以上标准的WEB服务器就叫做J2EE中的WEB容器。

28.EJB容器(Enterprise java bean)。更具有行业领域特色。他提供给运行在其中的组件EJB各种管理功能。只要满足J2EE规范的EJB放入该容器，马上就会被容器进行高效率的管理。并且可以通过现成的接口来获得系统级别的服务。例如邮件服务、事务管理

EJB是企业级的JavaBean，它提供了构建企业级业务逻辑的一种组件模型。

EJB分为三种：Session Bean Entity Bean Message-Driven Bean 三种，其中Session Bean分为有状态和无状态Session Bean两种,Entity Bean分为容器管理的Entity Bean ( CMP ) 和 Bean管理的 Entity Bean ( BMP )。每一个EJB由一个远程接口、一个本地接口和一个EJB容器实现组成，远程接口声明了提供给EJB客户调用的各种应用方法，本地接口声明了创建新的EJB实例的create方法、寻找EJB实例的查找(finder)方法以及刪除EJB实例的remove方法。EJB容器提供了EJB的运行环境和生命周期的管理。

1. JDO(java data object)是Java对象持久化的新的规范，为java data object的简称,也是一个用于存取某种数据仓库中的对象的标准化API。JDO提供了透明的对象存储，因此对开发人员来说，存储数据对象完全不需要额外的代码（如JDBC API的使用）。这些繁琐的例行工作已经转移到JDO产品提供商身上，使开发人员解脱出来，从而集中时间和精力在业务逻辑上。另外，JDO很灵活，因为它可以在任何数据底层上运行。JDBC只是面向关系数据库（RDBMS）JDO更通用，提供到任何数据底层的存储功能，比如关系数据库、文件、XML以及对象数据库（ODBMS）等等，使得应用可移植性更强。

30.Web ServiceWeb Service是基于网络的、分布式的模块化组件，它执行特定的任务，遵守具体的技术规范，这些规范使得Web Service能与其他兼容的组件进行互操作。

JAXP(Java API for XML Parsing) 定义了在Java中使用DOM, SAX, XSLT的通用的接口。这样在你的程序中你只要使用这些通用的接口，当你需要改变具体的实现时候也不需要修改代码。

JAXM(Java API for XML Messaging) 是为SOAP通信提供访问方法和传输机制的API。

WSDL是一种XML 格式，用于将网络服务描述为一组端点，这些端点对包含面向文档信息或面向过程信息的消息进行操作。这种格式首先对操作和消息进行抽象描述，然后将其绑定到具体的网络协议和消息格式上以定义端点。相关的具体端点即组合成为抽象端点（服务）。

SOAP即简单对象访问协议(Simple Object Access Protocol)，它是用于交换XML编码信息的轻量级协议。

UDDI 的目的是为电子商务建立标准；UDDI是一套基于Web的、分布式的、为Web Service提供的、信息注册中心的实现标准规范，同时也包含一组使企业能将自身提供的Web Service注册，以使别的企业能够发现的访问协议的实现标准。

## 十三、什么是Web Service?

　　Web Service就是为了使原来各孤立的站点之间的信息能够相互通信、共享而提出的一种接口。

Web Service所使用的是Internet上统一、开放的标准，如HTTP、XML、SOAP（简单对象访问协议）、WSDL等，所以Web

Service可以在任何支持这些标准的环境（Windows,Linux）中使用。

　　注：SOAP协议（Simple Object Access

Protocal,简单对象访问协议）,它是一个用于分散和分布式环境下网络信息交换的基于XML的通讯协议。在此协议下，软件组件或应用程序能够通过标准的HTTP协议进行通讯。它的设计目标就是简单性和扩展性，这有助于大量异构程序和平台之间的互操作性，从而使存在的应用程序能够被广泛的用户访问。

　　优势：

　　(1).跨平台。

　　(2).SOAP协议是基于XML和HTTP这些业界的标准的，得到了所有的重要公司的支持。

　　(3).由于使用了SOAP，数据是以ASCII文本的方式而非二进制传输，调试很方便；并且由于这样，它的数据容易通过防火墙，不需要防火墙为了程序而单独开一个&quot;漏洞&quot;。

　　(4).此外，WebService实现的技术难度要比CORBA和DCOM小得多。

　　(5).要实现B2B集成，EDI比较完善与比较复杂；而用WebService则可以低成本的实现，小公司也可以用上。

　　(6).在C/S的程序中，WebService可以实现网页无整体刷新的与服务器打交道并取数。

　　缺点：

　　(1).WebService使用了XML对数据封装，会造成大量的数据要在网络中传输。

　　(2).WebService规范没有规定任何与实现相关的细节，包括对象模型、编程语言，这一点，它不如CORBA。

## 十四、String s = new String(&quot;xyz&quot;);创建了几个String Object?

两个

String 里面又是一个字符串。

## 十五、short s1 = 1; s1 = s1 + 1;有什么错? short s1 = 1; s1 += 1;有什么错?

short s1 = 1; s1 = s1 + 1; （s1+1运算结果是int型，需要强制转换类型）

short s1 = 1; s1 += 1;（可以正确编译）

## 十六、写clone()方法时，通常都有一行代码，是什么？

Clone 有缺省行为，super.clone();他负责产生正确大小的空间，并逐位复制。

## 十七、一个&quot;.java&quot;源文件中是否可以包括多个类（不是内部类）？有什么限制？

可以。必须只有一个类名与文件名相同。Public的类必须和文件名相同，并且一个.java的源文件中只能有一个公有的类。

## 十八、Math.round(11.5)等於多少? Math.round(-11.5)等於多少?

Math.round(11.5)==12

Math.round(-11.5)==-11

round方法返回与参数最接近的长整数，参数加1/2后求其floor.

## 十九、char型变量中能不能存贮一个中文汉字?为什么?

能够定义成为一个中文的，因为java中以unicode编码，一个char占16个字节，所以放一个中文是没问题的，一个汉字两个字节。

## 二十、数组有没有length()这个方法? String有没有length()这个方法？

数组有length属性，没有length方法。String有length()方法。

## 二十一、String 和StringBuffer,StringBuilder的区别？

JAVA平台提供了两个类：String和StringBuffer，它们可以储存和操作字符串，即包含多个字符的字符数据。这个String类提供了数值不可改变的字符串。而这个StringBuffer类提供的字符串进行修改。当你知道字符数据要改变的时候你就可以使用StringBuffer。典型地，你可以使用 StringBuffers来动态构造字符数据。stringBuffer是线程安全的，stringBuilder是单线程使用的。相对效率高些。

## 二十二、是否可以继承String类?

String类是final类故不可以继承。

## 二十三、面向对象的特征有哪些方面

### 1.抽象：

抽象就是忽略一个主题中与当前目标无关的那些方面，以便更充分地注意与当前目标有关的方面。抽象并不打算了解全部问题，而只是选择其中的一部分，暂时不用部分细节。抽象包括两个方面，一是过程抽象，二是数据抽象。

### 2.继承：

继承是一种联结类的层次模型，并且允许和鼓励类的重用，它提供了一种明确表述共性的方法。对象的一个新类可以从现有的类中派生，这个过程称为类继承。新类继承了原始类的特性，新类称为原始类的派生类（子类），而原始类称为新类的基类（父类）。派生类可以从它的基类那里继承方法和实例变量，并且类可以修改或增加新的方法使之更适合特殊的需要。

### 3.封装：

封装是把过程和数据包围起来，对数据的访问只能通过已定义的界面。面向对象计算始于这个基本概念，即现实世界可以被描绘成一系列完全自治、封装的对象，这些对象通过一个受保护的接口访问其他对象。

只要有足够的方法，就没必要直接去操作对象属性，只要调用这些方法就可以实现要完成的任务，这种现象称为封装，它通过对象方法对其属性的操作把对象属性封装在一个对象内部，对象与外界打交道全部通过其自身的方法来实现，有效的把对象属性隐藏在对象内部。

### 4.多态性：

多态性是指允许不同类的对象对同一消息作出响应。多态性包括参数化多态性和包含多态性。多态性语言具有灵活、抽象、行为共享、代码共享的优势，很好的解决了应用程序函数同名问题。

## 二十四、什么是类与对象？

所谓对象就是真实世界中的实体，对象与实体是一一对应的，也就是说现实世界中每一个实体都是一个对象，它是一种具体的概念。

类是具备某些共同特征的实体的集合，它是一种抽象的概念，用程序设计的语言来说，类是一种抽象的数据类型，它是对所具有相同特征实体的抽象。

## 二十五、什么是属性与方法？

不同对象具有相同特点，就可能抽象为一定的类，那么这些特点基本上可以分为两类，一类是描述对象静态状态的，就是对象的属性，在程序设计中，可以称之为变量；另一类是描述对象的动作，就是对象的方法，在程序设计中我们称之为函数。属性和方法是一个对象所具备的两大基本要素，也是我们后面编程工作的核心。

## 二十六、什么是OOP？什么是类？请对比类和对象实例之间的关系。

OOP是Object\_oriented Programming(面向对象编程)的缩写。这主要是为了区别于以前的面向过程的程序设计！指的是用对象的观点来组织与构建系统，它综合了功能抽象和数据抽象，这样可以减少数据之间的耦合性和代码的出错几率。使用面向对象编程技术可以使得软件开发者按照现实世界里人们思考问题的模式编写代码,可以让软件开发者更好地利用代码直接表达现实中存在的对象,将问题空间直接映射到解空间!类：即class 在面向对象的程序设计中，专门用&quot;类&quot;来表示用户定义的抽象数据类型（user\_defined abstract type）。它将具有相同状态、操作和访问机制的多个对象进行了抽象。类具有继承、数据隐藏和多态三种主要特性。利用类的这三种特性可以更好地表示现实世界中事物。类是同一类对象实例的共性的抽象，对象是类的实例化。对象通常作为计算机模拟思维，表示真实世界的抽象，一个对象就像一个软件模块，可以为用户提供一系列的服务---可以改变对象的状态、测试、传递消息等。类定义了对象的实现细节或数据结构。类是静态的，对象是动态的，对象可以看作是运行中的类。类负责产生对象，可以将类当成生产对象的工厂（Object factory）

## 二十七、String是最基本的数据类型吗?

基本数据类型包括byte、int、char、long、float、double、boolean和short。

java.lang.String类是final类型的，因此不可以继承这个类、不能修改这个类。为了提高效率节省空间，我们应该用StringBuffer类

## 二十八、int 和Integer 有什么区别

Java 提供两种不同的类型：引用类型和原始类型（或内置类型）。Int是java的原始数据类型，Integer是java为int提供的封装类。Java为每个原始类型提供了封装类。

原始类型封装类

booleanBoolean

charCharacter

byteByte

shortShort

intInteger

longLong

floatFloat

doubleDouble

引用类型和原始类型的行为完全不同，并且它们具有不同的语义。引用类型和原始类型具有不同的特征和用法，它们包括：大小和速度问题，这种类型以哪种类型的数据结构存储，当引用类型和原始类型用作某个类的实例数据时所指定的缺省值。对象引用实例变量的缺省值为null，而原始类型实例变量的缺省值与它们的类型有关。

## 二十九、运行时异常与一般异常有何异同？

异常表示程序运行过程中可能出现的非正常状态，运行时异常表示虚拟机的通常操作中可能遇到的异常，是一种常见运行错误。java编译器要求方法必须声明抛出可能发生的非运行时异常，但是并不要求必须声明抛出未被捕获的运行时异常。

## 三十、说出ArrayList,Vector, LinkedList的存储性能和特性

ArrayList 和Vector都是使用数组方式存储数据，此数组元素数大于实际存储的数据以便增加和插入元素，它们都允许直接按序号索引元素，但是插入元素要涉及数组元素移动等内存操作，所以索引数据快而插入数据慢，Vector由于使用了synchronized方法（线程安全），通常性能上较ArrayList差，而LinkedList使用双向链表实现存储，按序号索引数据需要进行前向或后向遍历，但是插入数据时只需要记录本项的前后项即可，所以插入速度较快。

## 三十一、Collection 和Collections的区别。

Collection是集合类的上级接口，继承与他的接口主要有Set 和List.

Collections是针对集合类的一个帮助类，他提供一系列静态方法实现对各种集合的搜索、排序、线程安全化等操作。

## 三十二、HashMap和Hashtable的区别。

HashMap是Hashtable的轻量级实现（非线程安全的实现），他们都完成了Map接口，主要区别在于HashMap允许空（null）键值（key）,由于非线程安全，效率上可能高于Hashtable。

HashMap允许将null作为一个entry的key或者value，而Hashtable不允许。

HashMap把Hashtable的contains方法去掉了，改成containsvalue和containsKey。因为contains方法容易让人引起误解。

Hashtable继承自Dictionary类，而HashMap是Java1.2引进的Map interface的一个实现。

最大的不同是，Hashtable的方法是Synchronize的，而HashMap不是，在多个线程访问Hashtable时，不需要自己为它的方法实现同步，而HashMap 就必须为之提供外同步。

Hashtable和HashMap采用的hash/rehash算法都大概一样，所以性能不会有很大的差异。

Hashtable继承自Dictionary类，而HashMap是Java1.2引进的Map interface的一个实现
HashMap允许将null作为一个entry的key或者value，而Hashtable不允许
还有就是，HashMap把Hashtable的contains方法去掉了，改成containsvalue和containsKey。因为contains方法容易让人引起误解。
最大的不同是，Hashtable的方法是Synchronize的，而HashMap不是，在
多个线程访问Hashtable时，不需要自己为它的方法实现同步，而HashMap
就必须为之提供外同步。
Hashtable和HashMap采用的hash/rehash算法都大概一样，所以性能不会有很大的差异。

HashTable的原理:通过节点的关键码确定节点的存储位置,即给定节点的关键码k,通过一定的函数关系H(散列函数),得到函数值H(k),将此值解释为该节点的存储地址.

HashMap 与Hashtable很相似,但HashMap 是非同步(unsynchronizded)和可以以null为关键码的.

二者都实现了Map 接口，是将惟一键映射到特定的值上；主要区别在于：

1)HashMap 没有排序，允许一个null 键和多个null 值,而Hashtable 不允许；

2)HashMap 把Hashtable 的contains 方法去掉了，改成containsvalue 和

containsKey,因为contains 方法容易让人引起误解；

3)Hashtable 继承自Dictionary 类，HashMap 是Java1.2 引进的Map 接口的实现；

4)Hashtable 的方法是Synchronize 的，而HashMap 不是，在多个线程访问

Hashtable 时，不需要自己为它的方法实现同步，而HashMap 就必须为之提供外

同步。

Hashtable 和HashMap 采用的hash/rehash 算法大致一样，所以性能不会有很

大的差异。

## 三十三、Overload和Override的区别。Overloaded的方法是否可以改变返回值的类型?

方法的重写Overriding和重载Overloading是Java多态性的不同表现。重写Overriding是父类与子类之间多态性的一种表现，重载Overloading是一个类中多态性的一种表现。如果在子类中定义某方法与其父类有相同的名称和参数，我们说该方法被重写(Overriding)。子类的对象使用这个方法时，将调用子类中的定义，对它而言，父类中的定义如同被&quot;屏蔽&quot;了。如果在一个类中定义了多个同名的方法，它们或有不同的参数个数或有不同的参数类型，则称为方法的重载(Overloading)。Overloaded的方法是可以改变返回值的类型。

## 三十四、error和exception有什么区别?

error 表示恢复不是不可能但很困难的情况下的一种严重问题。比如说内存溢出。不可能指望程序能处理这样的情况。

exception 表示一种设计或实现问题。也就是说，它表示如果程序运行正常，从不会发生的情况。

## 三十五、同步和异步有何异同，在什么情况下分别使用他们？举例说明。

如果数据将在线程间共享。例如正在写的数据以后可能被另一个线程读到，或者正在读的数据可能已经被另一个线程写过了，那么这些数据就是共享数据，必须进行同步存取。

当应用程序在对象上调用了一个需要花费很长时间来执行的方法，并且不希望让程序等待方法的返回时，就应该使用异步编程，在很多情况下采用异步途径往往更有效率。

## 三十六、abstract class和interface有什么区别?

声明方法的存在而不去实现它的类被叫做抽象类（abstract class），它用于要创建一个体现某些基本行为的类，并为该类声明方法，但不能在该类中实现该类的情况。不能创建abstract 类的实例。然而可以创建一个变量，其类型是一个抽象类，并让它指向具体子类的一个实例。不能有抽象构造函数或抽象静态方法。Abstract 类的子类为它们父类中的所有抽象方法提供实现，否则它们也是抽象类为。取而代之，在子类中实现该方法。知道其行为的其它类可以在类中实现这些方法。

接口（interface）是抽象类的变体。在接口中，所有方法都是抽象的。多继承性可通过实现这样的接口而获得。接口中的所有方法都是抽象的，没有一个有程序体。接口只可以定义static final成员变量。接口的实现与子类相似，除了该实现类不能从接口定义中继承行为。当类实现特殊接口时，它定义（即将程序体给予）所有这种接口的方法。然后，它可以在实现了该接口的类的任何对象上调用接口的方法。由于有抽象类，它允许使用接口名作为引用变量的类型。通常的动态联编将生效。引用可以转换到接口类型或从接口类型转换，instanceof 运算符可以用来决定某对象的类是否实现了接口。

## 三十七、heap和stack有什么区别。

栈是一种线形集合，其添加和删除元素的操作应在同一段完成。栈按照后进先出的方式进行处理。

堆是栈的一个组成元素

## 三十八、Static Nested Class 和Inner Class的不同。

Static Nested Class是被声明为静态（static）的内部类，它可以不依赖于外部类实例被实例化。而通常的内部类需要在外部类实例化后才能实例化。

## 三十九、Servlet执行时一般实现哪几个方法？

答：

public void init(ServletConfig config)

public ServletConfig getServletConfig()

public String getServletInfo()

public void service(ServletRequest request,ServletResponse response)

public void destroy()

## 四十、什么时候用assert。

assertion (断言)在软件开发中是一种常用的调试方式，很多开发语言中都支持这种机制。在实现中，assertion就是在程序中的一条语句，它对一个boolean表达式进行检查，一个正确程序必须保证这个boolean表达式的值为true；如果该值为false，说明程序已经处于不正确的状态下，系统将给出警告或退出。一般来说，assertion用于保证程序最基本、关键的正确性。assertion检查通常在开发和测试时开启。为了提高性能，在软件发布后，assertion检查通常是关闭的。

断言是一个包含布尔表达式的语句，在执行这个语句时假定该表达式为true。如果表达式计算为false，那么系统会报告一个AssertionError。它用于调试目的：
  assert(a &gt; 0); // throws an AssertionError if a &lt;= 0
断言可以有两种形式
  assert Expression1 ;
  assert Expression1 : Expression2 ;
Expression1 应该总是产生一个布尔值。
Expression2 可以是得出一个值的任意表达式。这个值用于生成显示更多调试信息的String 消息。
断言在默认情况下是禁用的。要在编译时启用断言，需要使用source 1.4 标记：
  javac -source 1.4 Test.java
要在运行时启用断言，可使用-enableassertions 或者-ea 标记。
要在运行时选择禁用断言，可使用-da 或者-disableassertions 标记。
要系统类中启用断言，可使用-esa 或者-dsa 标记。还可以在包的基础上启用或者禁用断言。
可以在预计正常情况下不会到达的任何位置上放置断言。断言可以用于验证传递给私有方法的参数。不过，断言不应该用于验证传递给公有方法的参数，因为不管是否启用了断言，公有方法都必须检查其参数。不过，既可以在公有方法中，也可以在非公有方法中利用断言测试后置条件。另外，断言不应该以任何方式改变程序的状态。

## 四十一、简述HttpSession的作用，使用方法，可以用代码说明。

```java
HttpSession session = request.getSession();
session.setAttribute("name";, new Date());
Date date = (Date)session.getAttribute("name");
```

答：HttpSession中可以跟踪并储存用户信息，把值设置到属性中，有2个方法：setAttribute(),getAttrribute()；

例如：在一个方法中用session.setAttribute(&quot;student&quot;,student);在session中设置一个属性名为student,值为一个名为student的对象。而后可在同一session范围内用getAttribute(&quot;student&quot;)取出该属性，得到student对象。

## 四十二、GC是什么？为什么要用它？

GC 是垃圾收集的意思（Gabage Collection）,忘记或者错误的内存回收会导致程序或系统的不稳定甚至崩溃，Java 提供的GC 功能可以自动监测对象是否超过作用域从而达到自动回收内存的目的，Java 语言没有提供释放已分配内存的显示操作方法。Java 程序员不用担心内存管理，因为垃圾收集器会自动进行管理。

要请求垃圾收集，可以调用下面的方法之一：System.gc() 或Runtime.getRuntime().gc() 。GC是垃圾收集器。

## 四十三、Java有没有goto?

java中的保留字，现在没有在java中使用。

## 四十四、给我一个你最常见到的runtime exception。

ArithmeticException, ArrayStoreException, BufferOverflowException, BufferUnderflowException, CannotRedoException, CannotUndoException, ClassCastException, CMMException, ConcurrentModificationException, DOMException, EmptyStackException, IllegalArgumentException, IllegalMonitorStateException, IllegalPathStateException, IllegalStateException, ImagingOpException, IndexOutOfBoundsException, MissingResourceException, NegativeArraySizeException, NoSuchElementException, NullPointerException, ProfileDataException, ProviderException, RasterFormatException, SecurityException, SystemException, UndeclaredThrowableException, UnmodifiableSetException, UnsupportedOperationException

## 四十五、接口是否可继承接口? 抽象类是否可实现(implements)接口? 抽象类是否可继承实体类(concrete class)?

接口可以继承接口。抽象类可以实现(implements)接口，抽象类是否可继承实体类，但前提是实体类必须有明确的构造函数。

## 四十六、List, Set, Map是否继承自Collection接口?

List，Set是，Map不是

abstract的method是否可同时是static,是否可同时是native，是否可同时是synchronized?

都不能

## 四十七、数组有没有length()这个方法? String有没有length()这个方法？

数组没有length()这个方法，有length的属性。String有有length()这个方法。

## 四十八、Set里的元素是不能重复的，那么用什么方法来区分重复与否呢? 是用==还是equals()? 它们有何区别?

Set里的元素是不能重复的，那么用iterator()方法来区分重复与否。equals()是判读两个Set是否相等。

equals()和==方法决定引用值是否指向同一对象equals()在类中被覆盖，为的是当两个分离的对象的内容和类型相配的话，返回真值。

## 四十九、构造器Constructor是否可被override?

构造器Constructor不能被继承，因此不能重写Overriding，但可以被重载Overloading。

## 五十、是否可以继承String类?

String类是final类故不可以继承。

## 五十一、try {}里有一个return语句，那么紧跟在这个try后的finally {}里的code会不会被执行，什么时候被执行，在return前还是后?

会执行，在return前执行。

## 五十二、编程题: 用最有效率的方法算出2乘以8等於几?

2 &lt;< 3

## 五十三、两个对象值相同(x.equals(y) == true)，但却可有不同的hash code，这句话对不对?

不对，有相同的hash code。

## 五十四、当一个对象被当作参数传递到一个方法后，此方法可改变这个对象的属性，并可返回变化后的结果，那么这里到底是值传递还是引用传递?

是值传递。Java 编程语言只有值传递参数。当一个对象实例作为一个参数被传递到方法中时，参数的值就是对该对象的引用。对象的内容可以在被调用的方法中改变，但对象的引用是永远不会改变的。

## 五十五、编程题: 写一个Singleton出来。

Singleton模式主要作用是保证在Java应用程序中，一个类Class只有一个实例存在。

一般Singleton模式通常有几种种形式:

第一种形式: 定义一个类，它的构造函数为private的，它有一个static的private的该类变量，在类初始化时实例话，通过一个public的getInstance方法获取对它的引用,继而调用其中的方法。

```java
public class Singleton {

private Singleton(){}

　　//在自己内部定义自己一个实例，是不是很奇怪？

　　//注意这是private 只供内部调用

　　private static Singleton instance = new Singleton();

　　//这里提供了一个供外部访问本class的静态方法，可以直接访问　　

　　public static Singleton getInstance() {

　　　　return instance; 　　

　　}

}
```

第二种形式:

```java
public class Singleton {

　　private static Singleton instance = null;

　　public static synchronized Singleton getInstance() {

　　//这个方法比上面有所改进，不用每次都进行生成对象，只是第一次　　　　

　　//使用时生成实例，提高了效率！

　　if (instance==null)

　　　　instance＝new Singleton();

	return instance; 　
  	}
}
```

其他形式:

定义一个类，它的构造函数为private的，所有方法为static的。

一般认为第一种形式要更加安全些

## 五十六、Java的接口和C++的虚类的相同和不同处。

由于Java不支持多继承，而有可能某个类或对象要使用分别在几个类或对象里面的方法或属性，现有的单继承机制就不能满足要求。与继承相比，接口有更高的灵活性，因为接口中没有任何实现代码。当一个类实现了接口以后，该类要实现接口里面所有的方法和属性，并且接口里面的属性在默认状态下面都是public static,所有方法默认情况下是public.一个类可以实现多个接口。

## 五十七、Java中的异常处理机制的简单原理和应用。

当JAVA 程序违反了JAVA的语义规则时，JAVA虚拟机就会将发生的错误表示为一个异常。违反语义规则包括2种情况。一种是JAVA类库内置的语义检查。例如数组下标越界,会引发IndexOutOfBoundsException;访问null的对象时会引发NullPointerException。另一种情况就是JAVA允许程序员扩展这种语义检查，程序员可以创建自己的异常，并自由选择在何时用throw关键字引发异常。所有的异常都是java.lang.Thowable的子类。

## 五十八、垃圾回收的优点和原理。并考虑2种回收机制。

Java语言中一个显著的特点就是引入了垃圾回收机制，使c++程序员最头疼的内存管理的问题迎刃而解，它使得Java程序员在编写程序的时候不再需要考虑内存管理。由于有个垃圾回收机制，Java中的对象不再有&quot;作用域&quot;的概念，只有对象的引用才有&quot;作用域&quot;。垃圾回收可以有效的防止内存泄露，有效的使用可以使用的内存。垃圾回收器通常是作为一个单独的低级别的线程运行，不可预知的情况下对内存堆中已经死亡的或者长时间没有使用的对象进行清楚和回收，程序员不能实时的调用垃圾回收器对某个对象或所有对象进行垃圾回收。回收机制有分代复制垃圾回收和标记垃圾回收，增量垃圾回收。

## 五十九、请说出你所知道的线程同步的方法。

wait():使一个线程处于等待状态，并且释放所持有的对象的lock。

sleep():使一个正在运行的线程处于睡眠状态，是一个静态方法，调用此方法要捕捉InterruptedException异常。

notify():唤醒一个处于等待状态的线程，注意的是在调用此方法的时候，并不能确切的唤醒某一个等待状态的线程，而是由JVM确定唤醒哪个线程，而且不是按优先级。

Allnotity():唤醒所有处入等待状态的线程，注意并不是给所有唤醒线程一个对象的锁，而是让它们竞争。

## 六十、你所知道的集合类都有哪些？主要方法？

最常用的集合类是List 和Map。List 的具体实现包括ArrayList 和Vector，它们是可变大小的列表，比较适合构建、存储和操作任何类型对象的元素列表。List 适用于按数值索引访问元素的情形。

Map 提供了一个更通用的元素存储方法。Map 集合类用于存储元素对（称作&quot;键&quot;和&quot;值&quot;），其中每个键映射到一个值。

## 六十一、描述一下JVM加载class文件的原理机制?

JVM中类的装载是由ClassLoader和它的子类来实现的,Java ClassLoader 是一个重要的Java运行时系统组件。它负责在运行时查找和装入类文件的类。

## 六十二、多线程有几种实现方法,都是什么?同步有几种实现方法,都是什么?

多线程有两种实现方法，分别是继承Thread类与实现Runnable接口

同步的实现方面有两种，分别是synchronized,wait与notify

## 六十三、线程的基本概念、线程的基本状态以及状态之间的关系

线程指在程序执行过程中，能够执行程序代码的一个执行单位，每个程序至少都有一个线程，也就是程序本身。

## 六十四、Java中的线程有四种状态分别是：运行、就绪、挂起、结束。

## 六十五、简述synchronized和java.util.concurrent.locks.Lock的异同？

主要相同点：Lock能完成synchronized所实现的所有功能

主要不同点：Lock有比synchronized更精确的线程语义和更好的性能。synchronized会自动释放锁，而Lock一定要求程序员手工释放，并且必须在finally从句中释放。

## 六十六、排序都有哪几种方法？请列举。用JAVA实现一个快速排序。

排序的方法有：

插入排序（直接插入排序、希尔排序），

交换排序（冒泡排序、快速排序），

选择排序（直接选择排序、堆排序），

归并排序，

分配排序（箱排序、基数排序）

快速排序的伪代码。

/ /使用快速排序方法对a[0 :n- 1]排序

从a[0 :n- 1]中选择一个元素作为m i d d l e，该元素为支点

把余下的元素分割为两段left 和r i g h t，使得l e f t中的元素都小于等于支点，而right 中的元素都大于等于支点

递归地使用快速排序方法对left 进行排序

递归地使用快速排序方法对right 进行排序

所得结果为l e f t + m i d d l e + r i g h t

## 六十七、JAVA语言如何进行异常处理，关键字：throws,throw,try,catch,finally分别代表什么意义？在try块中可以抛出异常吗？

Java 通过面向对象的方法进行异常处理，把各种不同的异常进行分类，并提供了良好的接口。在Java中，每个异常都是一个对象，它是Throwable类或其它子类的实例。当一个方法出现异常后便抛出一个异常对象，该对象中包含有异常信息，调用这个对象的方法可以捕获到这个异常并进行处理。Java的异常处理是通过5个关键词来实现的：try、catch、throw、throws和finally。一般情况下是用try来执行一段程序，如果出现异常，系统会抛出（throws）一个异常，这时候你可以通过它的类型来捕捉（catch）它，或最后（finally）由缺省处理器来处理。

用try来指定一块预防所有&quot;异常&quot;的程序。紧跟在try程序后面，应包含一个catch子句来指定你想要捕捉的&quot;异常&quot;的类型。

throw语句用来明确地抛出一个&quot;异常&quot;。

throws用来标明一个成员函数可能抛出的各种&quot;异常&quot;。

Finally为确保一段代码不管发生什么&quot;异常&quot;都被执行一段代码。

可以在一个成员函数调用的外面写一个try语句，在这个成员函数内部写另一个try语句保护其他代码。每当遇到一个try语句，&quot;异常&quot;的框架就放到堆栈上面，直到所有的try语句都完成。如果下一级的try语句没有对某种&quot;异常&quot;进行处理，堆栈就会展开，直到遇到有处理这种&quot;异常&quot;的try语句。

## 六十八、垃圾回收器的基本原理是什么？垃圾回收器可以马上回收内存吗？有什么办法主动通知虚拟机进行垃圾回收？

对于GC来说，当程序员创建对象时，GC就开始监控这个对象的地址、大小以及使用情况。通常，GC采用有向图的方式记录和管理堆(heap)中的所有对象。通过这种方式确定哪些对象是&quot;可达的&quot;，哪些对象是&quot;不可达的&quot;。当GC确定一些对象为&quot;不可达&quot;时，GC就有责任回收这些内存空间。可以。程序员可以手动执行System.gc()，通知GC运行，但是Java语言规范并不保证GC一定会执行。

## 六十九、什么是java序列化，如何实现java序列化？

序列化就是一种用来处理对象流的机制，所谓对象流也就是将对象的内容进行流化。可以对流化后的对象进行读写操作，也可将流化后的对象传输于网络之间。序列化是为了解决在对对象流进行读写操作时所引发的问题。

序列化的实现：

将需要被序列化的类实现Serializable接口，该接口没有需要实现的方法，implements Serializable只是为了标注该对象是可被序列化的，然后使用一个输出流(如：FileOutputStream)来构造一个ObjectOutputStream(对象流)对象，接着，使用ObjectOutputStream对象的writeObject(Object obj)方法就可以将参数为obj的对象写出(即保存其状态)，要恢复的话则用输入流。

## 七十、是否可以从一个static方法内部发出对非static方法的调用？

不可以,如果其中包含对象的method()；不能保证对象初始化.

## 七十一、在JAVA中，如何跳出当前的多重嵌套循环？

用break; return 方法。

## 七十二、List、Map、Set三个接口，存取元素时，各有什么特点？

List 以特定次序来持有元素，可有重复元素。Set 无法拥有重复元素,内部排序。Map 保存key-value值，value可多值。

## 七十三、说出一些常用的类，包，接口，请各举5个

常用的类：BufferedReader BufferedWriter FileReader FileWirter String Integer

常用的包：java.lang java.awt java.io java.util java.sql

常用的接口：Remote List Map Document NodeList

## 七十四、Anonymous Inner Class (匿名内部类) 是否可以extends(继承)其它类，是否可以implements(实现)interface(接口)?

可以继承其他类或完成其他接口，在swing编程中常用此方式。

## 七十五、内部类可以引用他包含类的成员吗？有没有什么限制？

一个内部类对象可以访问创建它的外部类对象的内容

## 七十六、现在输入n个数字，以逗号，分开；然后可选择升或者降序排序；按提交键就在另一页面显示按什么排序，结果为，提供reset

```java
import java.util.*;
public class bycomma{
	public static String[] splitStringByComma(String source){
		if(source==null||source.trim().equals(""))
		return null;
		StringTokenizer commaToker = new StringTokenizer(source,",");
		String[] result = new String[commaToker.countTokens()];
		int i=0;
		while(commaToker.hasMoreTokens()){
			result[i] = commaToker.nextToken();
			i++;
		}
		return result;
	}
	public static void main(String args[]){
		String[] s = splitStringByComma("5,8,7,4,3,9,1");
		int[] ii = new int[s.length];
		for (int i = 0;iii[i] =Integer.parseint(s[i]);
	}
	Arrays.sort(ii);
	//asc
	for (int i=0;iSystem.out.println(ii[i]);
}
//desc
for (int i=(s.length-1);i>=0;i--){
	System.out.println(ii[i]);
}
}
```



## 七十七、金额转换，阿拉伯数字的金额转换成中国传统的形式如：（￥1011）－&gt;（一千零一拾一元整）输出。

```java
package test.format;
import java.text.NumberFormat;
import java.util.HashMap;
public class SimpleMoneyFormat {
	public static final String EMPTY = "";
	public static final String ZERO = "零";
	public static final String ONE = "壹";
	public static final String TWO = "贰";
	public static final String THREE = "叁";
	public static final String FOUR = "肆";
	public static final String FIVE = "伍";
	public static final String SIX = "陆";
	public static final String SEVEN = "柒";
	public static final String EIGHT = "捌";
	public static final String NINE = "玖";
	public static final String TEN = "拾";
	public static final String HUNDRED = "佰";
	public static final String THOUSAND = "仟";
	public static final String TEN_THOUSAND = "万";
	public static final String HUNDRED_MILLION = "亿";
	public static final String YUAN = "元";
	public static final String JIAO = "角";
	public static final String FEN = "分";
	public static final String DOT = ".";
	private static SimpleMoneyFormat formatter = null;
	private HashMap chineseNumberMap = new HashMap();
	private HashMap chineseMoneyPattern = new HashMap();
	private NumberFormat numberFormat = NumberFormat.getInstance();
	private SimpleMoneyFormat() {
		numberFormat.setMaximumFractionDigits(4);
		numberFormat.setMinimumFractionDigits(2);
		numberFormat.setGroupingUsed(false);
		chineseNumberMap.put("0", ZERO);
		chineseNumberMap.put("1", ONE);
		chineseNumberMap.put("2", TWO);
		chineseNumberMap.put("3", THREE);
		chineseNumberMap.put("4", FOUR);
		chineseNumberMap.put("5", FIVE);
		chineseNumberMap.put("6", SIX);
		chineseNumberMap.put("7", SEVEN);
		chineseNumberMap.put("8", EIGHT);
		chineseNumberMap.put("9", NINE);
		chineseNumberMap.put(DOT, DOT);
		chineseMoneyPattern.put("1", TEN);
		chineseMoneyPattern.put("2", HUNDRED);
		chineseMoneyPattern.put("3", THOUSAND);
		chineseMoneyPattern.put("4", TEN_THOUSAND);
		chineseMoneyPattern.put("5", TEN);
		chineseMoneyPattern.put("6", HUNDRED);
		chineseMoneyPattern.put("7", THOUSAND);
		chineseMoneyPattern.put("8", HUNDRED_MILLION);
	}
	public static SimpleMoneyFormat getInstance() {
		if (formatter == null)
		formatter = new SimpleMoneyFormat();
		return formatter;
	}
	public String format(String moneyStr) {
		checkPrecision(moneyStr);
		String result;
		result = convertToChineseNumber(moneyStr);
		result = addUnitsToChineseMoneyString(result);
		return result;
	}
	public String format(double moneydouble) {
		return format(numberFormat.format(moneydouble));
	}
	public String format(int moneyint) {
		return format(numberFormat.format(moneyint));
	}
	public String format(long moneylong) {
		return format(numberFormat.format(moneylong));
	}
	public String format(Number moneyNum) {
		return format(numberFormat.format(moneyNum));
	}
	private String convertToChineseNumber(String moneyStr) {
		String result;
		StringBuffer cMoneyStringBuffer = new StringBuffer();
		for (int i = 0; i < moneyStr.length(); i++) {
			cMoneyStringBuffer.append(chineseNumberMap.get(moneyStr.substring(i, i + 1)));
		}
		//拾佰仟万亿等都是汉字里面才有的单位，加上它们
		int indexOfDot = cMoneyStringBuffer.indexOf(DOT);
		int moneyPatternCursor = 1;
		for (int i = indexOfDot - 1; i > 0; i--) {
			cMoneyStringBuffer.insert(i, chineseMoneyPattern.get(EMPTY + moneyPatternCursor));
			moneyPatternCursor = moneyPatternCursor == 8 ? 1 : moneyPatternCursor + 1;
		}
		String fractionPart = cMoneyStringBuffer.substring(cMoneyStringBuffer.indexOf("."));
		cMoneyStringBuffer.delete(cMoneyStringBuffer.indexOf("."), cMoneyStringBuffer.length());
		while (cMoneyStringBuffer.indexOf("零拾") != -1) {
			cMoneyStringBuffer.replace(cMoneyStringBuffer.indexOf
			("零拾"), cMoneyStringBuffer.indexOf("零拾") + 2, ZERO);
		}
		while (cMoneyStringBuffer.indexOf("零佰") != -1) {
			cMoneyStringBuffer.replace(cMoneyStringBuffer.indexOf
			("零佰"), cMoneyStringBuffer.indexOf("零佰") + 2, ZERO);
		}
		while (cMoneyStringBuffer.indexOf("零仟") != -1) {
			cMoneyStringBuffer.replace(cMoneyStringBuffer.indexOf
			("零仟"), cMoneyStringBuffer.indexOf("零仟") + 2, ZERO);
		}
		while (cMoneyStringBuffer.indexOf("零万") != -1) {
			cMoneyStringBuffer.replace(cMoneyStringBuffer.indexOf
			("零万"), cMoneyStringBuffer.indexOf("零万") + 2, TEN_THOUSAND);
		}
		while (cMoneyStringBuffer.indexOf("零亿") != -1) {
			cMoneyStringBuffer.replace(cMoneyStringBuffer.indexOf
			("零亿"), cMoneyStringBuffer.indexOf("零亿") + 2, HUNDRED_MILLION);
		}
		while (cMoneyStringBuffer.indexOf("零零") != -1) {
			cMoneyStringBuffer.replace(cMoneyStringBuffer.indexOf
			("零零"), cMoneyStringBuffer.indexOf("零零") + 2, ZERO);
		}
		if (cMoneyStringBuffer.lastIndexOf(ZERO) == cMoneyStringBuffer.length() - 1)
		cMoneyStringBuffer.delete(cMoneyStringBuffer.length() - 1, cMoneyStringBuffer.length());
		cMoneyStringBuffer.append(fractionPart);
		result = cMoneyStringBuffer.toString();
		return result;
	}
	private String addUnitsToChineseMoneyString(String moneyStr) {
		String result;
		StringBuffer cMoneyStringBuffer = new StringBuffer(moneyStr);
		int indexOfDot = cMoneyStringBuffer.indexOf(DOT);
		cMoneyStringBuffer.replace(indexOfDot, indexOfDot + 1, YUAN);
```



## 七十八、作用域public,private,protected,以及不写时的区别　　

答：区别如下：　

|    作用域    | 当前类  | 同一package | 子孙类  | 其他package |
| :-------: | :--: | :-------: | :--: | :-------: |
|  public   |  √   |     √     |  √   |     √     |
| protected |  √   |     √     |  √   |     ×     |
| friendly  |  √   |     √     |  ×   |     ×     |
|  private  |  √   |     ×     |  ×   |     ×     |

不写时默认为friendly

## 七十九、Anonymous Inner Class (匿名内部类) 是否可以extends(继承)其它类，是否可以implements(实现)interface(接口) 　　

答：匿名的内部类是没有名字的内部类。可以继承抽象(SDK1.5没有限制)，但一个内部类可以作为一个接口，由另一个内部类实现

## 八十、String s = new String(&quot;xyz&quot;);创建了几个String Object 

答：&quot;xyz&quot;本身作为字符常量，在汇编语言中应该作为常量放在数据段，Java有一个类似数据段的constant pool保存这个常量，在classloader加载这个类的时候就把&quot;xyz&quot;和这个类的其他一些信息放在constant   pool  new   String(&quot;xyz&quot;)根据常量&quot;xyz&quot;在heap上创建String对象所以，一共两个对象

## 八十一、ArrayList和Vector的区别,HashMap和Hashtable的区别　　

答：就ArrayList与Vector主要从二方面来说：　　

1.同步性:Vector是线程安全的，也就是说是同步的，而ArrayList是线程序不安全的，不是同步的　　

2.数据增长:当需要增长时,Vector默认增长为原来一倍，而ArrayList却是原来的一半　　

就HashMap与HashTable主要从三方面来说：　　

1. 历史原因:Hashtable是基于陈旧的Dictionary类的，HashMap是Java 1.2引进的Map接口的一个实现　　

2. 同步性:Hashtable是线程安全的，也就是说是同步的，而HashMap是线程序不安全的，不是同步的　　

3. 值：只有HashMap可以将空值作为一个表的条目的key或value

## 八十二、char型变量中能不能存贮一个中文汉字?为什么? 　　

答：是能够定义成为一个中文的，因为java中以unicode编码，一个char占16个字节，所以放一个中文是没问题的

 如果用gbk的本地编码的话可以.如果用utf-8的话，可能不行。

## 八十三、float型float f=3.4是否正确? 　　

答:不正确。精度不准确,应该用强制类型转换，如下所示：float f=(float)3.4; float f = 3.4f;

## 八十四、介绍JAVA中的Collection FrameWork(包括如何写自己的数据结构)? 　　

答：Collection FrameWork如下：

Collection

  ├List

  │├LinkedList

  │├ArrayList

  │└Vector

  │　└Stack

  └Set

  Map

  ├Hashtable

  ├HashMap

  └WeakHashMap

![](http://upload-images.jianshu.io/upload_images/1540531-b53388b7fb06d40e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](http://upload-images.jianshu.io/upload_images/1540531-2e4f79caff74e446.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

​	Collection是最基本的集合接口，一个Collection代表一组Object，即Collection的元素（Elements）　　Map提供key到value的映射

## 八十五、String与StringBuffer的区别。　　

答：String的长度是不可变的，StringBuffer的长度是可变的。如果你对字符串中的内容经常进行操作，特别是内容要修改时，那么使用StringBuffer，如果最后需要String，那么使用StringBuffer的toString()方法

## 八十六、谈谈final, finally, finalize的区别　　

答：final—修饰符（关键字）如果一个类被声明为final，意味着它不能再派生出新的子类，不能作为父类被继承。因此一个类不能既被声明为abstract的，又被声明为final的。将变量或方法声明为final，可以保证它们在使用中不被改变。被声明为final的变量必须在声明时给定初值，而在以后的引用中只能读取，不可修改。被声明为final的方法也同样只能使用，不能重载　　

finally—再异常处理时提供finally 块来执行任何清除操作。如果抛出一个异常，那么相匹配的catch 子句就会执行，然后控制就会进入finally 块（如果有的话）　　

finalize—方法名。Java 技术允许使用finalize() 方法在垃圾收集器将对象从内存中清除出去之前做必要的清理工作。这个方法是由垃圾收集器在确定这个对象没有被引用时对这个对象调用的。它是在Object 类中定义的，因此所有的类都继承了它。子类覆盖finalize() 方法以整理系统资源或者执行其他清理工作。finalize() 方法是在垃圾收集器删除对象之前对这个对象调用的

## 八十七、forward和redirect的区别

forward: an internal transfer in servlet

redirect: 重定向,有2次request,第2次request将丢失第一次的attributs/parameters等

forward是服务器请求资源，服务器直接访问目标地址的URL，把那个URL的响应内容读取过来，然后把这些内容再发给浏览器，浏览器根本不知道服务器发送的内容是从哪儿来的，所以它的地址栏中还是原来的地址。

redirect就是服务端根据逻辑,发送一个状态码,告诉浏览器重新去请求那个地址，一般来说浏览器会用刚才请求的所有参数重新请求，所以session,request参数都可以获取。

## 八十八、Static Nested Class 和Inner Class的不同　　

答：Nested Class （一般是C++的说法），Inner Class (一般是JAVA的说法)。Java内部类与C++嵌套类最大的不同就在于是否有指向外部的引用上。

注：静态内部类（Inner Class）意味着1.创建一个static内部类的对象，不需要一个外部类对象，2.不能从一个static内部类的一个对象访问一个外部类对象

## 八十九、简述逻辑操作(&amp;,|,^)与条件操作(&amp;&amp;,||)的区别。

区别主要答两点：

a.条件操作只能操作布尔型的,而逻辑操作不仅可以操作布尔型,而且可以操作数值型

b.逻辑操作不会产生短路

## 九十、Java中的异常处理机制的简单原理和应用　　

答：当JAVA程序违反了JAVA的语义规则时，JAVA虚拟机就会将发生的错误表示为一个异常。违反语义规则包括2种情况。一种是JAVA类库内置的语义检查。例如数组下标越界,会引发IndexOutOfBoundsException;访问null的对象时会引发NullPointerException。另一种情况就是JAVA允许程序员扩展这种语义检查，程序员可以创建自己的异常，并自由选择在何时用throw关键字引发异常。所有的异常都是java.lang.Thowable的子类。

## 九十一、java中会存在内存泄漏吗，请简单描述。　　

答：会。自己实现堆载的数据结构时有可能会出现内存泄露，

如：int i,i2; return (i-i2); //when i为足够大的正数,i2为足够大的负数。结果会造成溢位，导致错误。可参看effective java.

## 九十二、C/S 与B/S 区别：

答：有如下八个方面的不同：

### １．硬件环境不同:

　　C/S 一般建立在专用的网络上, 小范围里的网络环境, 局域网之间再通过专门服务器提供连接和数据交换服务.

　　B/S 建立在广域网之上的, 不必是专门的网络硬件环境,例与电话上网, 租用设备. 信息自己管理. 有比C/S更强的适应范围, 一般只要有操作系统和浏览器就行

### ２．对安全要求不同

　　C/S 一般面向相对固定的用户群, 对信息安全的控制能力很强. 一般高度机密的信息系统采用C/S 结构适宜. 可以通过B/S发布部分可公开信息.

　　B/S 建立在广域网之上, 对安全的控制能力相对弱, 可能面向不可知的用户。

### ３．对程序架构不同

　　C/S 程序可以更加注重流程, 可以对权限多层次校验, 对系统运行速度可以较少考虑.

　　B/S 对安全以及访问速度的多重的考虑, 建立在需要更加优化的基础之上. 比C/S有更高的要求B/S结构的程序架构是发展的趋势, 从MS的.Net系列的BizTalk 2000 Exchange 2000等, 全面支持网络的构件搭建的系统. SUN 和IBM推的JavaBean 构件技术等,使B/S更加成熟.

### ４．软件重用不同

　　C/S 程序可以不可避免的整体性考虑, 构件的重用性不如在B/S要求下的构件的重用性好.

　　B/S 对的多重结构,要求构件相对独立的功能. 能够相对较好的重用.就入买来的餐桌可以再利用,而不是做在墙上的石头桌子

### ５．系统维护不同

　　C/S 程序由于整体性, 必须整体考察, 处理出现的问题以及系统升级. 升级难. 可能是再做一个全新的系统

　　B/S 构件组成,方面构件个别的更换,实现系统的无缝升级. 系统维护开销减到最小.用户从网上自己下载安装就可以实现升级.

### ６．处理问题不同

　　C/S 程序可以处理用户面固定, 并且在相同区域, 安全要求高需求, 与操作系统相关. 应该都是相同的系统

　　B/S 建立在广域网上, 面向不同的用户群, 分散地域, 这是C/S无法作到的. 与操作系统平台关系最小.

### ７．用户接口不同

　　C/S 多是建立的Window平台上,表现方法有限,对程序员普遍要求较高

B/S 建立在浏览器上, 有更加丰富和生动的表现方式与用户交流. 并且大部分难度减低,减低开发成本.

### ８．信息流不同

　　C/S 程序一般是典型的中央集权的机械式处理, 交互性相对低

　　B/S 信息流向可变化, B-B B-C B-G等信息、流向的变化, 更像交易中心。

## 九十三、JDBC，Hibernate 分页怎样实现？

答：方法分别为：

1) Hibernate 的分页：

Query query = session.createQuery(&quot;from Student&quot;);

query.setFirstResult(firstResult);//设置每页开始的记录号

query.setMaxResults(resultNumber);//设置每页显示的记录数

Collection students = query.list();

2) JDBC 的分页：根据不同的数据库采用不同的sql 分页语句

例如: Oracle 中的sql 语句为: &quot;SELECT \* FROM (SELECT a.\*, rownum r FROM

TB\_STUDENT) WHERE r between 2 and 10&quot; 查询从记录号2 到记录号10 之间的

所有记录

## 九十四、java中有几种方法可以实现一个线程？用什么关键字修饰同步方法? stop()和suspend()方法为何不推荐使用？　　

答：

有两种实现方法，分别是继承Thread类与实现Runnable接口　　

用synchronized关键字修饰同步方法　　

反对使用stop()，是因为它不安全。它会解除由线程获取的所有锁定，而且如果对象处于一种不连贯状态，那么其他线程能在那种状态下检查和修改它们。结果很难检查出真正的问题所在。suspend()方法容易发生死锁。调用suspend()的时候，目标线程会停下来，但却仍然持有在这之前获得的锁定。此时，其他任何线程都不能访问锁定的资源，除非被&quot;挂起&quot;的线程恢复运行。对任何线程来说，如果它们想恢复目标线程，同时又试图使用任何一个锁定的资源，就会造成死锁。所以不应该使用suspend()，而应在自己的Thread类中置入一个标志，指出线程应该活动还是挂起。若标志指出线程应该挂起，便用wait()命其进入等待状态。若标志指出线程应当恢复，则用一个notify()重新启动线程。

## 九十五、java中有几种类型的流？JDK为每种类型的流提供了一些抽象类以供继承，请说出他们分别是哪些类？

字节流，字符流。字节流继承于InputStream OutputStream，字符流继承于InputStreamReader OutputStreamWriter。在java.io包中还有许多其他的流，主要是为了提高性能和使用方便。

## 九十六、java中实现多态的机制是什么？

方法的重写Overriding和重载Overloading是Java多态性的不同表现。重写Overriding是父类与子类之间多态性的一种表现，重载Overloading是一个类中多态性的一种表现。

## 九十七、继承时候类的执行顺序问题,一般都是选择题,问你将会打印出什么? 　　

答:

```java
package test;
public class FatherClass {
  public FatherClass() {
    System.out.println("FatherClass Create");
  }
} 　　
//子类： 
package test;
import test.FatherClass;
public class  ChildClass extends FatherClass {
  public ChildClass() {
    System.out.println("ChildClass Create");
  }   public static void main(String[] args) {
    FatherClass fc = new FatherClass();
    ChildClass cc = new ChildClass();
  }
} 
```

输出结果：

C:\&gt;java test.ChildClass

FatherClass Create

FatherClass Create

ChildClass Create

内部类的实现方式? 　　

答：示例代码如下：

```java
package test;
public class OuterClass {
  private class InterClass {
    public InterClass() {
      System.out.println("InterClass Create");
    }
  }   
  public OuterClass() {
    InterClass ic = new InterClass();
    System.out.println("OuterClass Create");
  }   
  public static void main(String[] args) {
    OuterClass oc = new OuterClass();
  }
} 　
```

输出结果：

C:\&gt;java test/OuterClass

InterClass Create

OuterClass Create

再一个例题：

```java
public class OuterClass { 
  private double d1 = 1.0; 
  //inserｔ code here 
} 
//You need to inserｔ an inner class declaration at line 3. Which two inner class declarations are valid?(Choose two.) 
//A. 
  class InnerOne{
     public static double methoda() {
       return d1;
     }
   } 
//B. 
  public class InnerOne{
     static double methoda() {
       return d1;
     }
   } 
//C.
  private class InnerOne{
     double methoda() {
       return d1;
     }
   } 
//D. 
  static class InnerOne{
     protected double methoda() {
       return d1;
     }
   } 
//E. 
  abstract class InnerOne{
     public abstract double methoda();
   }  　　
```

说明如下：　　

1. 静态内部类可以有静态成员，而非静态内部类则不能有静态成员。故A、B 错　　
2. 静态内部类的非静态成员可以访问外部类的静态变量，而不可访问外部类的非静态变量；故D 错　　
3. 非静态内部类的非静态成员可以访问外部类的非静态变量。故C 正确　　
4. 答案为C、E
##九十八、Java 的通信编程，编程题(或问答)，用JAVA SOCKET编程，读服务器几个字符，再写入本地显示？　　

答：

```java
//Server端程序：
package test;
import java.net.*;
import java.io.*; 
public class Server{
  private ServerSocket ss;
  private Socket socket;
  private BufferedReader in;
  private PrintWriter out;
 
 public Server() {
  try {
   ss=new ServerSocket(10000);
   while(true) {
    socket = ss.accept();
    String RemoteIP = socket.getInetAddress().getHostAddress();
    String RemotePort = ":"+socket.getLocalPort();
    System.out.println("A client come in!IP:"+RemoteIP+RemotePort);
    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    String line = in.readLine();
    System.out.println("Cleint send is :" + line);
    out = new PrintWriter(socket.getOutputStream(),true);
    out.println("Your Message Received!");
    out.close();
    in.close();
    socket.close();
   }
  }catch (IOException e) {
   out.println("wrong");
  }
 }  
  public static void main(String[] args) {
  new Server();
 }
} 　
//Client端程序： package test;
import java.io.*;
import java.net.*;
public class Client {
 Socket socket;
 BufferedReader in;
 PrintWriter out;
 public Client() {
  try {
   System.out.println("Try to Connect to 127.0.0.1:10000");
   socket = new Socket("127.0.0.1",10000);
   System.out.println("The Server Connected!");
   System.out.println("Please enter some Character:");
   BufferedReader line = new BufferedReader(new InputStreamReader(System.in));
   out = new PrintWriter(socket.getOutputStream(),true);
   out.println(line.readLine());
   in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
   System.out.println(in.readLine());
   out.close();
   in.close();
   socket.close();
  }catch(IOException e) {
   out.println("Wrong");
  }
 }  public static void main(String[] args) {
  new Client();
 }
}
```

## 九十九、用JAVA实现一种排序，JAVA类实现序列化的方法(二种)？如在COLLECTION框架中，实现比较要实现什么样的接口？　　

答:用插入法进行排序代码如下：

```java
package test;
import java.util.*;
class  InsertSort {
	ArrayList al;
	public InsertSort(int num,int mod) {
		al = new ArrayList(num);
		Random rand = new Random();
		System.out.println("The ArrayList Sort Before:");
		for (int i=0;i<num ;i++) {
			al.add(new Integer(Math.abs(rand.nextint()) % mod + 1));
			System.out.println("al["+i+"]="+al.get(i));
		}
	}
	public void SortIt() {
		Integer tempint;
		int MaxSize=1;
		for (int i=1;i<al.size();i++) {
			tempint = (Integer)al.remove(i);
			if(tempint.intValue()>=((Integer)al.get(MaxSize-1)).intValue()) {
				al.add(MaxSize,tempint);
				MaxSize++;
				System.out.println(al.toString());
			} else {
				for (int j=0;j<MaxSize ;j++ ) {
					if (((Integer)al.get(j)).intValue()>=tempint.intValue()) {
						al.add(j,tempint);
						MaxSize++;
						System.out.println(al.toString());
						break;
					}
				}
			}
		}
		System.out.println("The ArrayList Sort After:");
		for (int i=0;i<al.size();i++) {
			System.out.println("al["+i+"]="+al.get(i));
		}
	}
	public static void main(String[] args) {
		InsertSort is = new InsertSort(10,100);
		is.SortIt();
	}
}
```

## 一百、文件读写,实现一个计数器

```java
public int getNum(){
	int i = -1;
	try{
		String stri="";
		BufferedReader in = new BufferedReader(new FileReader(f));
		while((stri=in.readLine())!=null){
			i = Integer.parseint(stri.trim());
		}
		in.close();
	}
	catch(Exception e){
		e.printStackTrace();
	}
	return i;
}
public void setNum(){
	int i = getNum();
	i++;
	try{
		PrintWriter out=new PrintWriter(new BufferedWriter(new 
		FileWriter(f,false)));
		out.write(String.valueOf(i));
		//可能是编码的原因，如果直接写入int的话，将出现java编码和windows编码的混乱，因此此处写入的是String
		out.close() ;
	}
	catch(Exception e){
		e.printStackTrace();
	}
}
```

## 一百一、编程题：设有ｎ个人依围成一圈，从第１个人开始报数，数到第ｍ个人出列，然后从出列的下一个人开始报数，数到第ｍ个人又出列，…，如此反复到所有的人全部出列为止。设ｎ个人的编号分别为1，2，…，n，打印出出列的顺序；要求用java实现。(Core Java)

答：代码如下：

```java
package test;
public class CountGame {
	private static Boolean same(int[] p,int l,int n){
		for (int i=0;i<l;i++){
			if(p[i]==n){
				return true;
			}
		}
		return false;
	}
	public static void play(int playerNum, int step){
		int[] p=new int[playerNum];
		int counter = 1;
		while(true){
			if(counter > playerNum*step){
				break;
			}
			for (int i=1;i<playerNum+1;i++){
				while(true){
					if(same(p,playerNum,i)==false) break; else i=i+1;
				}
				if(i > playerNum)break;
				if(counter%step==0){
					System.out.print(i + " ");
					p[counter/step-1]=i;
				}
				counter+=1;
			}
		}
		System.out.println();
	}
	public static void main(String[] args) {
		play(10, 7);
	}
}
```

## 一百二、写一个方法1000的阶乘。(C++)

答：C++的代码实现如下：

```c++
#include <iostream>
#include <iomanip>
#include <vector>
using namespace std;
class longint {
private:
vector<int> iv;
public:
longint(void) 
{
	iv.push_back(1);
}
longint& multiply(const int &);
friend ostream& operator<<(ostream &, const longint &);
}
;
ostream& operator<<(ostream &os, const longint &v) 
{
vector<int>::const_reverse_iterator iv_iter = v.iv.rbegin();
os << *iv_iter++;
for ( ; iv_iter < v.iv.rend(); ++iv_iter) 
{
	os << setfill('0') << setw(4) << *iv_iter;
}
return os;
}
longint& longint::multiply(const int &rv) 
{
vector<int>::iterator iv_iter = iv.begin();
int overflow = 0, product = 0;
for ( ; iv_iter < iv.end(); ++iv_iter) 
{
	product = (*iv_iter) * rv;
	product += overflow;
	overflow = 0;
	if (product > 10000) 
	{
		overflow = product / 10000;
		product -= overflow * 10000;
	}
	*iv_iter = product;
}
if (0 != overflow) 
{
	iv.push_back(overflow);
}
return *this;
}
　　int main(int argc, char **argv) 
{
　　longint result;
int l = 0;
　　if(argc==1) 
{
	　　cout << "like: multiply 1000" << endl;
	exit(0);
	　　
}
　　sscanf(argv[1], "%d", &l);
　　for (int i = 2; i <= l; ++i) 
{
	　　result.multiply(i);
	　　
}
　　cout << result << endl;
　　return 0;
　　
}
```

## 一百三、一个学生的信息是：姓名，学号，性别，年龄等信息，用一个链表，把这些学生信息连在一起，给出一个age, 在些链表中删除学生年龄等于age的学生信息。

程序代码程序代码

```c
＃i nclude "stdio.h"
＃i nclude "conio.h"
struct stu 
{
	char name[20];
	char sex;
	int no;
	int age;
	struct stu * next;
}
*linklist;
struct stu *creatlist(int n) 
{
	int i;
	//h为头结点，p为前一结点，s为当前结点
	struct stu *h,*p,*s;
	h = (struct stu *)malloc(sizeof(struct stu));
	h->next = NULL;
	p=h;
	for (i=0;i<n;i++) 
	{
		s = (struct stu *)malloc(sizeof(struct stu));
		p->next = s;
		printf("Please input the information of the student: name sex no age \n");
		scanf("%s %c %d %d",s->name,&s->sex,&s->no,&s->age);
		s->next = NULL;
		p = s;
	}
	printf("Create successful!");
	return(h);
}
void deletelist(struct stu *s,int a) 
{
	struct stu *p;
	while(s->age!=a) 
	{
		p = s;
		s = s->next;
	}
	if(s==NULL)
	printf("The record is not exist."); else 
	{
		p->next = s->next;
		printf("Delete successful!");
	}
}
void display(struct stu *s) 
{
	s = s->next;
	while(s!=NULL) 
	{
		printf("%s %c %d %d\n",s->name,s->sex,s->no,s->age);
		s = s->next;
	}
}
int main() 
{
	struct stu *s;
	int n,age;
	printf("Please input the length of seqlist:\n");
	scanf("%d",&n);
	s = creatlist(n);
	display(s);
	printf("Please input the age:\n");
	scanf("%d",&age);
	deletelist(s,age);
	display(s);
	return 0;
}
```

## 一百四、编程：编写一个截取字符串的函数，输入为一个字符串和字节数，输出为按字节截取的字符串。但是要保证汉字不被截半个，如&quot;我ABC&quot;4，应该截为&quot;我AB&quot;，输入&quot;我ABC汉DEF&quot;，6，应该输出为&quot;我ABC&quot;而不是&quot;我ABC+汉的半个&quot;。　　

答：代码如下：

```java
package test;
class  SplitString {
	String SplitStr;
	int Splitbyte;
	public SplitString(String str,int bytes) {
		SplitStr=str;
		Splitbyte=bytes;
		System.out.println("The String is:'"+SplitStr+"';SplitBytes="+Splitbyte);
	}
	public void SplitIt() {
		int loopCount;
		loopCount=(SplitStr.length()%Splitbyte==0)?(SplitStr.length()/Splitbyte):(SplitStr.length()/Splitbyte+1);
		System.out.println("Will Split into "+loopCount);
		for (int i=1;i<=loopCount ;i++ ) {
			if (i==loopCount){
				System.out.println(SplitStr.substring((i-1)*Splitbyte,SplitStr.length()));
			} else {
				System.out.println(SplitStr.substring((i-1)*Splitbyte,(i*Splitbyte)));
			}
		}
	}
	public static void main(String[] args) {
		SplitString ss = new SplitString("test中dd文dsaf中男大3443n中国43中国人0ewldfls=103",4);
		ss.SplitIt();
	}
}
```

## 一百五、设计4个线程，其中两个线程每次对j增加1，另外两个线程对j每次减少1。写出程序。　　以下程序使用内部类实现线程，对j增减的时候没有考虑顺序问题。

```java
public class ThreadTest1{
	private int j;
	public static void main(String args[]){
		ThreadTest1 tt=new ThreadTest1();
		Inc inc=tt.new Inc();
		Dec dec=tt.new Dec();
		for (int i=0;i<2;i++){
			Thread t=new Thread(inc);
			t.start();
			t=new Thread(dec);
			t.start();
		}
	}
	private synchronized void inc(){
		j++;
		System.out.println(Thread.currentThread().getName()+"-inc:"+j);
	}
	private synchronized void dec(){
		j--;
		System.out.println(Thread.currentThread().getName()+"-dec:"+j);
	}
	class Inc implements Runnable{
		public void run(){
			for (int i=0;i<100;i++){
				inc();
			}
		}
	}
	class Dec implements Runnable{
		public void run(){
			for (int i=0;i<100;i++){
				dec();
			}
		}
	}
}
```

## 一百六、金额转换，阿拉伯数字的金额转换成中国传统的形式如：（￥1011）－&gt;（一千零一拾一元整）输出。

 显示被隐藏内容

```java
package test.format;
import java.text.NumberFormat;
import java.util.HashMap;
public class SimpleMoneyFormat {
	public static final String EMPTY = "";
	public static final String ZERO = "零";
	public static final String ONE = "壹";
	public static final String TWO = "贰";
	public static final String THREE = "叁";
	public static final String FOUR = "肆";
	public static final String FIVE = "伍";
	public static final String SIX = "陆";
	public static final String SEVEN = "柒";
	public static final String EIGHT = "捌";
	public static final String NINE = "玖";
	public static final String TEN = "拾";
	public static final String HUNDRED = "佰";
	public static final String THOUSAND = "仟";
	public static final String TEN_THOUSAND = "万";
	public static final String HUNDRED_MILLION = "亿";
	public static final String YUAN = "元";
	public static final String JIAO = "角";
	public static final String FEN = "分";
	public static final String DOT = ".";
	private static SimpleMoneyFormat formatter = null;
	private HashMap chineseNumberMap = new HashMap();
	private HashMap chineseMoneyPattern = new HashMap();
	private NumberFormat numberFormat = NumberFormat.getInstance();
	private SimpleMoneyFormat() {
		numberFormat.setMaximumFractionDigits(4);
		numberFormat.setMinimumFractionDigits(2);
		numberFormat.setGroupingUsed(false);
		chineseNumberMap.put("0", ZERO);
		chineseNumberMap.put("1", ONE);
		chineseNumberMap.put("2", TWO);
		chineseNumberMap.put("3", THREE);
		chineseNumberMap.put("4", FOUR);
		chineseNumberMap.put("5", FIVE);
		chineseNumberMap.put("6", SIX);
		chineseNumberMap.put("7", SEVEN);
		chineseNumberMap.put("8", EIGHT);
		chineseNumberMap.put("9", NINE);
		chineseNumberMap.put(DOT, DOT);
		chineseMoneyPattern.put("1", TEN);
		chineseMoneyPattern.put("2", HUNDRED);
		chineseMoneyPattern.put("3", THOUSAND);
		chineseMoneyPattern.put("4", TEN_THOUSAND);
		chineseMoneyPattern.put("5", TEN);
		chineseMoneyPattern.put("6", HUNDRED);
		chineseMoneyPattern.put("7", THOUSAND);
		chineseMoneyPattern.put("8", HUNDRED_MILLION);
	}
	public static SimpleMoneyFormat getInstance() {
		if (formatter == null)
		      formatter = new SimpleMoneyFormat();
		return formatter;
	}
	public String format(String moneyStr) {
		checkPrecision(moneyStr);
		String result;
		result = convertToChineseNumber(moneyStr);
		result = addUnitsToChineseMoneyString(result);
		return result;
	}
	public String format(double moneydouble) {
		return format(numberFormat.format(moneydouble));
	}
	public String format(int moneyint) {
		return format(numberFormat.format(moneyint));
	}
	public String format(long moneylong) {
		return format(numberFormat.format(moneylong));
	}
	public String format(Number moneyNum) {
		return format(numberFormat.format(moneyNum));
	}
	private String convertToChineseNumber(String moneyStr) {
		String result;
		StringBuffer cMoneyStringBuffer = new StringBuffer();
		for (int i = 0; i < moneyStr.length(); i++) {
			cMoneyStringBuffer.append(chineseNumberMap.get(moneyStr.substring(i, i + 
			1)));
		}
		//拾佰仟万亿等都是汉字里面才有的单位，加上它们
		int indexOfDot = cMoneyStringBuffer.indexOf(DOT);
		int moneyPatternCursor = 1;
		for (int i = indexOfDot - 1; i > 0; i--) {
			cMoneyStringBuffer.insert(i, chineseMoneyPattern.get(EMPTY + 
			moneyPatternCursor));
			moneyPatternCursor = moneyPatternCursor == 8 ? 1 : moneyPatternCursor + 1;
		}
		String fractionPart = 
		cMoneyStringBuffer.substring(cMoneyStringBuffer.indexOf("."));
		cMoneyStringBuffer.delete(cMoneyStringBuffer.indexOf("."), 
		cMoneyStringBuffer.length());
		while (cMoneyStringBuffer.indexOf("零拾") != -1) {
			cMoneyStringBuffer.replace(cMoneyStringBuffer.indexOf("零拾"), 
			cMoneyStringBuffer.indexOf("零拾") + 2, ZERO);
		}
		while (cMoneyStringBuffer.indexOf("零佰") != -1) {
			cMoneyStringBuffer.replace(cMoneyStringBuffer.indexOf("零佰"), 
			cMoneyStringBuffer.indexOf("零佰") + 2, ZERO);
		}
		while (cMoneyStringBuffer.indexOf("零仟") != -1) {
			cMoneyStringBuffer.replace(cMoneyStringBuffer.indexOf("零仟"), 
			cMoneyStringBuffer.indexOf("零仟") + 2, ZERO);
		}
		while (cMoneyStringBuffer.indexOf("零万") != -1) {
			cMoneyStringBuffer.replace(cMoneyStringBuffer.indexOf("零万"), 
			cMoneyStringBuffer.indexOf("零万") + 2, TEN_THOUSAND);
		}
		while (cMoneyStringBuffer.indexOf("零亿") != -1) {
			cMoneyStringBuffer.replace(cMoneyStringBuffer.indexOf("零亿"), 
			cMoneyStringBuffer.indexOf("零亿") + 2, HUNDRED_MILLION);
		}
		while (cMoneyStringBuffer.indexOf("零零") != -1) {
			cMoneyStringBuffer.replace(cMoneyStringBuffer.indexOf("零零"), 
			cMoneyStringBuffer.indexOf("零零") + 2, ZERO);
		}
		if (cMoneyStringBuffer.lastIndexOf(ZERO) == cMoneyStringBuffer.length() - 1)
		      cMoneyStringBuffer.delete(cMoneyStringBuffer.length() - 1, 
		cMoneyStringBuffer.length());
		cMoneyStringBuffer.append(fractionPart);
		result = cMoneyStringBuffer.toString();
		return result;
	}
	private String addUnitsToChineseMoneyString(String moneyStr) {
		String result;
		StringBuffer cMoneyStringBuffer = new StringBuffer(moneyStr);
		int indexOfDot = cMoneyStringBuffer.indexOf(DOT);
		cMoneyStringBuffer.replace(indexOfDot, indexOfDot + 1, YUAN);
    }
```

## 一百七、Java编程,打印昨天的当前时刻

```java
public class YesterdayCurrent{
  public void main(String[] args){
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.DATE, -1);
    System.out.println(cal.getTime());
  }
}
```

## 一百八、字符串的操作：写一个方法，实现字符串的反转，如：输入abc，输出cba

```java
 public static String reverse(String s){
        int length=s.length();
        StringBuffer result=new StringBuffer(length);
        for(int i=length-1;i>=0;i--)
            result.append(s.charAt(i));
        return result.toString();
 }
```

## 一百九、程序设计，写出一个Servlet，实现以下功能，通过表单提取一个&quot;ds&quot;的参数（该参数代表一个可用的数据源），通过该参数获得一个可用的数据连接。

```java
 import javax.sql.*;
 public class MyServlet extends HttpServlet{
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String ds = request.getParemeter("ds");
        Context initial = new InitialContext();
        DataSource dss = (DataSource)initial.lookup(ds);
        Connection conn = dss.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select a, b, from mytable");
        while(rs.next()){
             rs.getXXX(); 
        }
}
```

## 一百十、Servlet方面

### 1、说一说Servlet的生命周期?

答:servlet有良好的生存期的定义，包括加载和实例化、初始化、处理请求以及服务结束。这个生存期由javax.servlet.Servlet接口的init,service和destroy方法表达。Servlet被服务器实例化后，容器运行其init方法，请求到达时运行其service方法，service方法自动派遣运行与请求对应的doXXX方法（doGet，doPost）等，当服务器决定将实例销毁的时候调用其destroy方法。与cgi的区别在于servlet处于服务器进程中，它通过多线程方式运行其service方法，一个实例可以服务于多个请求，并且其实例一般不会销毁，而CGI对每个请求都产生新的进程，服务完成后就销毁，所以效率上低于servlet。

Java中数据库连接池原理机制的详细讲解

### 2、基本概念及原理

由上面的分析可以看出，问题的根源就在于对数据库连接资源的低效管理。我们知道，对于共享资源，有一个很著名的设计模式：资源池(Resource Pool)。该模式正是为了解决资源的频繁分配?释放所造成的问题。为解决上述问题，可以采用数据库连接池技术。数据库连接池的基本思想就是为数据库连接建立一个&quot;缓冲池&quot;。预先在缓冲池中放入一定数量的连接，当需要建立数据库连接时，只需从&quot;缓冲池&quot;中取出一个，使用完毕之后再放回去。我们可以通过设定连接池最大连接数来防止系统无尽的与数据库连接。更为重要的是我们可以通过连接池的管理机制监视数据库的连接的数量?使用情况，为系统开发?测试及性能调整提供依据。

### 3、服务器自带的连接池

JDBC的API中没有提供连接池的方法。一些大型的WEB应用服务器如BEA的WebLogic和IBM的WebSphere等提供了连接池的机制，但是必须有其第三方的专用类方法支持连接池的用法。

连接池关键问题分析

#### （1）并发问题

为了使连接管理服务具有最大的通用性，必须考虑多线程环境，即并发问题。这个问题相对比较好解决，因为Java语言自身提供了对并发管理的支持，使用synchronized关键字即可确保线程是同步的。使用方法为直接在类方法前面加上synchronized关键字，如：

public synchronized Connection getConnection()

#### （2）多数据库服务器和多用户

对于大型的企业级应用，常常需要同时连接不同的数据库(如连接Oracle和Sybase)。如何连接不同的数据库呢?我们采用的策略是：设计一个符合单例模式的连接池管理类，在连接池管理类的唯一实例被创建时读取一个资源文件，其中资源文件中存放着多个数据库的url地址()?用户名()?密码()等信息。如tx.url=172.21.15.123：5000/tx\_it，tx.user=yang，tx.password=yang321。根据资源文件提供的信息，创建多个连接池类的实例，每一个实例都是一个特定数据库的连接池。连接池管理类实例为每个连接池实例取一个名字，通过不同的名字来管理不同的连接池。

对于同一个数据库有多个用户使用不同的名称和密码访问的情况，也可以通过资源文件处理，即在资源文件中设置多个具有相同url地址，但具有不同用户名和密码的数据库连接信息。

#### （3）事务处理

我们知道，事务具有原子性，此时要求对数据库的操作符合&quot;ALL-ALL-NOTHING&quot;原则,即对于一组SQL语句要么全做，要么全不做。

在Java语言中，Connection类本身提供了对事务的支持，可以通过设置Connection的AutoCommit属性为false,然后显式的调用commit或rollback方法来实现。但要高效的进行Connection复用，就必须提供相应的事务支持机制。可采用每一个事务独占一个连接来实现，这种方法可以大大降低事务管理的复杂性。

#### （4）连接池的分配与释放

连接池的分配与释放，对系统的性能有很大的影响。合理的分配与释放，可以提高连接的复用度，从而降低建立新连接的开销，同时还可以加快用户的访问速度。

对于连接的管理可使用空闲池。即把已经创建但尚未分配出去的连接按创建时间存放到一个空闲池中。每当用户请求一个连接时，系统首先检查空闲池内有没有空闲连接。如果有就把建立时间最长(通过容器的顺序存放实现)的那个连接分配给他(实际是先做连接是否有效的判断，如果可用就分配给用户，如不可用就把这个连接从空闲池删掉，重新检测空闲池是否还有连接);如果没有则检查当前所开连接池是否达到连接池所允许的最大连接数(maxConn),如果没有达到，就新建一个连接，如果已经达到，就等待一定的时间(timeout)。如果在等待的时间内有连接被释放出来就可以把这个连接分配给等待的用户，如果等待时间超过预定时间timeout,则返回空值(null)。系统对已经分配出去正在使用的连接只做计数，当使用完后再返还给空闲池。对于空闲连接的状态，可开辟专门的线程定时检测，这样会花费一定的系统开销，但可以保证较快的响应速度。也可采取不开辟专门线程，只是在分配前检测的方法。

#### （5）连接池的配置与维护

连接池中到底应该放置多少连接，才能使系统的性能最佳?系统可采取设置最小连接数(minConn)和最大连接数(maxConn)来控制连接池中的连接。最小连接数是系统启动时连接池所创建的连接数。如果创建过多，则系统启动就慢，但创建后系统的响应速度会很快;如果创建过少，则系统启动的很快，响应起来却慢。这样，可以在开发时，设置较小的最小连接数，开发起来会快，而在系统实际使用时设置较大的，因为这样对访问客户来说速度会快些。最大连接数是连接池中允许连接的最大数目，具体设置多少，要看系统的访问量，可通过反复测试，找到最佳点。

如何确保连接池中的最小连接数呢?有动态和静态两种策略。动态即每隔一定时间就对连接池进行检测，如果发现连接数量小于最小连接数，则补充相应数量的新连接,以保证连接池的正常运转。静态是发现空闲连接不够时再去检查。

连接池的实现

##### ①连接池模型

本文讨论的连接池包括一个连接池类(DBConnectionPool)和一个连接池管理类(DBConnetionPoolManager)。连接池类是对某一数据库所有连接的&quot;缓冲池&quot;，主要实现以下功能：①从连接池获取或创建可用连接;②使用完毕之后，把连接返还给连接池;③在系统关闭前，断开所有连接并释放连接占用的系统资源;④还能够处理无效连接(原来登记为可用的连接，由于某种原因不再可用，如超时，通讯问题)，并能够限制连接池中的连接总数不低于某个预定值和不超过某个预定值。

连接池管理类是连接池类的外覆类(wrapper),符合单例模式，即系统中只能有一个连接池管理类的实例。其主要用于对多个连接池对象的管理，具有以下功能：①装载并注册特定数据库的JDBC驱动程序;②根据属性文件给定的信息，创建连接池对象;③为方便管理多个连接池对象，为每一个连接池对象取一个名字，实现连接池名字与其实例之间的映射;④跟踪客户使用连接情况，以便需要是关闭连接释放资源。连接池管理类的引入主要是为了方便对多个连接池的使用和管理，如系统需要连接不同的数据库，或连接相同的数据库但由于安全性问题，需要不同的用户使用不同的名称和密码。

##### ②连接池实现

下面给出连接池类和连接池管理类的主要属性及所要实现的基本接口：

public class DBConnectionPool implements TimerListener{

private int checkedOut;//已被分配出去的连接数

private ArrayList freeConnections=new ArrayList();

//容器，空闲池，根据//创建时间顺序存放已创建但尚未分配出去的连接

private int minConn;//连接池里连接的最小数量

private int maxConn;//连接池里允许存在的最大连接数

private String name;//为这个连接池取个名字，方便管理

private String password;//连接数据库时需要的密码

private String url;//所要创建连接的数据库的地址

private String user;//连接数据库时需要的用户名

public Timer timer;//定时器

public DBConnectionPool(String name,String URL,String user,

String password,int maxConn)//公开的构造函数

public synchronized void freeConnection(Connection con)

//使用完毕之后，把连接返还给空闲池

public synchronized Connection getConnection(long timeout)

//得到一个连接，timeout是等待时间

public synchronized void release()

//断开所有连接，释放占用的系统资源

private Connection newConnection()

//新建一个数据库连接

public synchronized void TimerEvent()

//定时器事件处理函数

}

public class DBConnectionManager {

static private DBConnectionManager instance;

//连接池管理类的唯一实例

static private int clients;//客户数量

private ArrayList drivers=new ArrayList();

//容器，存放数据库驱动程序

private HashMap pools = new HashMap();

//以name/value的形式存取连接池对象的名字及连接池对象

static synchronized public DBConnectionManager getInstance()

/\*\*如果唯一的实例instance已经创建，直接返回这个实例;否则，调用私有构造函数，

创建连接池管理类的唯一实例\*/

private DBConnectionManager()

//私有构造函数,在其中调用初始化函数init()

public void freeConnection(String name,Connection con)

//释放一个连接，name是一个连接池对象的名字

public Connection getConnection(String name)

//从名字为name的连接池对象中得到一个连接

public Connection getConnection(String name,long time)

//从名字为name的连接池对象中取得一个连接，time是等待时间

public synchronized void release()//释放所有资源

private void createPools(Properties props)

//根据属性文件提供的信息，创建一个或多个连接池

private void init()//初始化连接池管理类的唯一实例，由私有构造函数调用

private void loadDrivers(Properties props)//装载数据库驱动程序

}

##### ③连接池使用

上面所实现的连接池在程序开发时如何应用到系统中呢?下面以Servlet为例说明连接池的使用。

Servlet的生命周期是：在开始建立servlet时，调用其初始化(init)方法。之后每个用户请求都导致一个调用前面建立的实例的service方法的线程。最后，当服务器决定卸载一个servlet时，它首先调用该servlet的destroy方法。

根据servlet的特点，我们可以在初始化函数中生成连接池管理类的唯一实例(其中包括创建一个或多个连接池)。如：

public void init() throws ServletException

{

connMgr=DBConnectionManager.getInstance();

}

然后就可以在service方法中通过连接池名称使用连接池，执行数据库操作。最后在destroy方法中释放占用的系统资源，如：

public void destroy(){

connMgr.release();

super.destroy();

}

### 4、结束语

在使用JDBC进行与数据库有关的应用开发中，数据库连接的管理是一个难点。很多时候，连接的混乱管理所造成的系统资源开销过大成为制约大型企业级应用效率的瓶颈。对于众多用户访问的Web应用，采用数据库连接技术的系统在效率和稳定性上比采用传统的其他方式的系统要好很多。本文阐述了使用JDBC访问数据库的技术?讨论了基于连接池技术的数据库连接管理的关键问题并给出了一个实现模型。文章所给出的是连接池管理程序的一种基本模式，为提高系统的整体性能，在此基础上还可以进行很多有意义的扩展。

## 一百十一、STRUTS2

### 1、该案例包括首页，用户登陆、网站向导页面。就这么简单，没有深奥的struts概念，主要靠动手，然后用心体会。

　　WEB Server用tomcat4。到http://jakarta.apache.org下载struts1.1，把zip文件释放到c:\struts，拷贝C:\struts\webapps\struts-example.war到c:\tomcat4\webapps中，启动tomcat，war包被释放为struts-example文件夹，删除war包，把struts-example文件夹更名为test。

### 2、把WEB-INF\web.xml改成：

&lt;?xml version=&quot;1.0&quot; encoding=&quot;ISO-8859-1&quot;?&gt;

&lt;!DOCTYPE web-app PUBLIC &quot;-//Sun Microsystems, Inc.//DTD Web Application 2.2//EN&quot; &quot;http://java.sun.com/j2ee/dtds/web-app\_2\_2.dtd&quot;&gt;

&lt;web-app&gt;

&lt;!—这是struts中的Controller（控制器），系统的指令中转由其，既ActionServlet 类负责，它从struts-config.xml中读取配置信息，并在服务器后台自动启动一个线程。如果没有特别的要求（如添加语言编转功能），程序员可以不管这部分，照用就可以了。--&gt;

&lt;servlet&gt;

&lt;servlet-name&gt;action&lt;/servlet-name&gt;

&lt;servlet-class&gt;org.apache.struts.action.ActionServlet&lt;/servlet-class&gt;

&lt;init-param&gt;

&lt;param-name&gt;config&lt;/param-name&gt;

&lt;param-value&gt;/WEB-INF/struts-config.xml&lt;/param-value&gt;

&lt;/init-param&gt;

&lt;load-on-startup&gt;1&lt;/load-on-startup&gt;

&lt;/servlet&gt;

&lt;!--该系统的servlet可以映射成cool为后缀的文件，而不是常见的.jspdo等，后缀名可以改成任何名称，当然名字要健康--&gt;

&lt;servlet-mapping&gt;

&lt;servlet-name&gt;action&lt;/servlet-name&gt;

&lt;url-pattern&gt;\*.cool&lt;/url-pattern&gt;

&lt;/servlet-mapping&gt;

&lt;!--该系统的默认首页是index.jsp，可以有多个，系统按次序找，类似IIS--&gt;

&lt;welcome-file-list&gt;

&lt;welcome-file&gt;index.jsp&lt;/welcome-file&gt;

&lt;/welcome-file-list&gt;

&lt;/web-app&gt;

### 3、把test\WEB-INF\ struts-config.xml改成：

&lt;?xml version=&quot;1.0&quot; encoding=&quot;ISO-8859-1&quot; ?&gt;

&lt;!DOCTYPE struts-config PUBLIC &quot;-//Apache Software Foundation//DTD Struts Configuration 1.1//EN&quot;

&quot;http://jakarta.apache.org/struts/dtds/struts-config\_1\_1.dtd&quot;&gt;

&lt;struts-config&gt;

&lt;!--FormBean是struts的一个概念，本质是JavaBean，用来自动存储页面表单中各个域的值，并在适当的时候回填表单域，不需要象传统那样request.getParameter(&quot;fieldName&quot;);，常被action-mappings中的action 使用--&gt;

&lt;form-beans&gt;

&lt;!—稍后我们会新增一个UserForm类，用来存储用户信息。--&gt;

&lt;form-bean name=&quot;userForm&quot; type=&quot;test.UserForm&quot;/&gt;

&lt;/form-beans&gt;

&lt;!--这里存放整个系统都可以使用的全局转向中转(Forward)地址，类似于javascript中的window.location(&#39;index.jsp&#39;);，也类似于电视控制器上的各种按钮，可以转频道、调色等等是基于Struts的Web应用的控制流程流转。一般情况下，一个Action处理完毕后，会转发到一个JSP页面进行显示。这也是JSP中的MVC的实现的要点。--&gt;

&lt;global-forwards&gt;

&lt;!--failed.cool将被当成servlet请求，到action-mappings中寻找对应的action处理。--&gt;

&lt;forward name=&quot;failed&quot; path=&quot;/failed.cool&quot;/&gt;

&lt;forward name=&quot;regist&quot; path=&quot;/regist.jsp&quot;/&gt;

&lt;/global-forwards&gt;

&lt;!--还记得web.xml中后缀为cool的请求吗？它们是转到这里处理的。这里相当于struts的Model部分，Model部分是struts中比较灵活的地方。--&gt;

&lt;action-mappings&gt;

&lt;!--处理regist.cools的请求，使用的FormBean是userForm，既test.UserForm类，当处理过程发生错误时将返回index.jsp--&gt;

&lt;action path=&quot;/regist&quot; type=&quot;test.RegistAction&quot; name=&quot;userForm&quot; scope=&quot;request&quot; input=&quot;/index.jsp&quot; /&gt;

&lt;action path=&quot;/overview&quot; forward=&quot;/hello.jsp&quot;/&gt;

&lt;action path=&quot;/failed&quot; forward=&quot;/wuwu.jsp&quot; /&gt;

&lt;/action-mappings&gt;

&lt;/struts-config&gt;

### 4、增加一个FormBean，类路径为test.UserForm，以下是这个类的内容：

package test;

import org.apache.struts.action.ActionForm;

public class UserForm extends ActionForm

{

　private String name=&quot;lpw&quot;;//用户名

　private String ps=&quot;1111&quot;;//密码

　public UserForm(){}

　public void setName(String s) {name=s;}

　public String getName() {return name;}

　public void setPs(String s) {ps=s;}

　public String getPs() {return ps;}

}

### 5、增加一个Action的子类，类路径为test. RegistAction，以下是这个类的内容：

package test;

import java.lang.reflect.InvocationTargetException;

import java.util.Locale;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpSession;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;

import org.apache.struts.action.ActionError;

import org.apache.struts.action.ActionErrors;

import org.apache.struts.action.ActionForm;

import org.apache.struts.action.ActionForward;

import org.apache.struts.action.ActionMapping;

import org.apache.struts.util.MessageResources;

import test.UserForm;

public final class RegistAction extends Action

{

　public ActionForward execute(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response)

　throws Exception

　{

　　Locale locale = getLocale(request);

　　MessageResources messages = getResources(request);

　　HttpSession session = request.getSession();

　　UserForm userform = (UserForm) form;

　　//此处可以调用其他类来执行数据库写入或其他逻辑判断

　　// 如果UserForm传来的参数name的值为默认的lpw，将forward到failed，

　　// 该名称将到struts-config.xml的&lt;global-forwards&gt;中寻找映射的url地址

　　// （可以是绝对路径，也可以是相对路径），对于本例，是转到failed.cool，

　　// 还记得吗？后缀为cool的请求全部到action-mappings中寻找

　　// 对应的action处理，最终目录是wuwu.jsp\*/

　　if( &quot;lpw&quot;.equals(userform.getName()) )

　　　return (mapping.findForward(&quot;failed&quot;));

　　else

　　　return (mapping.findForward(&quot;regist&quot;));

　}

}

### 6、以下所有新增或修改的页面相当于struts的View部分，把首页index.jsp改成：

&lt;%@ page contentType=&quot;text/html;charset=GBK&quot; language=&quot;java&quot; %&gt;

&lt;%@ page import = &quot;test.\*&quot; %&gt;

&lt;a href=&quot;overview.cool&quot;&gt;站点导航&lt;/a&gt;&lt;br&gt;

&lt;form action=&quot;regist.cool&quot; method=&quot;post&quot;&gt;

&lt;!—表单中的域的名称要和UserForm中的参数一样，就可以实现数据自动获取功能，不需要用request.getParameter(&quot;param&quot;);--&gt;

用户:&lt;input type=&quot;text&quot; name=&quot;name&quot;&gt;&lt;br&gt;

密码:&lt;input type=&quot;password&quot; name=&quot;ps&quot;&gt;&lt;br&gt;

&lt;input type=submit value=&quot;新增用户&quot;&gt;

&lt;/form&gt;

### 7、增加hello.jsp，用于站点导航：

&lt;h1&gt;site map&lt;/h1&gt;The following is content filling by reader

### 8、增加wuwu.jsp，当没有新用户登陆时，将转到这个页面：

&lt;%@ page contentType=&quot;text/html;charset=GBK&quot; language=&quot;java&quot; %&gt;

&lt;jsp:useBean id=&quot;beanlpw&quot; class=&quot;test.UserForm&quot; scope=&quot;session&quot;/&gt;

现有用户：&lt;%=beanlpw.getName()%&gt;&lt;br&gt;

密码：&lt;%=beanlpw.getPs()%&gt;&lt;br&gt;

　　没有得到新的用户！

### 9、增加regist.jsp，当有新用户登陆时，将转到这个页面：

&lt;%@ page contentType=&quot;text/html;charset=GBK&quot; language=&quot;java&quot; %&gt;

&lt;jsp:useBean id=&quot;beanlpw&quot; class=&quot;test.UserForm&quot; scope=&quot;session&quot;/&gt;

新用户帐号：&lt;%=beanlpw.getName()%&gt;&lt;br&gt;

密码：&lt;%=beanlpw.getPs()%&gt;

### 10、启动tomcat4，浏览器中键入http://localhost:8080/test/index.jsp，操作一下，就可以看到结果，并初步理解struts的M、V、C各部分的协同工作原理，

## 一百十二、对Java多线程技术中所有方法的详细解析

### 1、run()和start()

这两个方法应该都比较熟悉，把需要并行处理的代码放在run()方法中，start()方法启动线程将自动调用run()方法，这是由Java的内存机制规定的。并且run()方法必须是public访问权限，返回值类型为void。

### 2、启动一个线程是用run()还是start()?

启动一个线程是调用start()方法，使线程所代表的虚拟处理机处于可运行状态，这意味着它可以由JVM调度并执行。这并不意味着线程就会立即运行。run()方法可以产生必须退出的标志来停止一个线程。

### 3、当一个线程进入一个对象的一个synchronized方法后，其它线程是否可进入此对象的其它方法?

不能，一个对象的一个synchronized方法只能由一个线程访问。

### 4、关键字Synchronized

这个关键字用于保护共享数据，当然前提是要分清哪些数据是共享数据。每个对象都有一个锁标志，当一个线程访问该对象时，被Synchronized修饰的数据将被&quot;上锁&quot;，阻止其他线程访问。当前线程访问完这部分数据后释放锁标志，其他线程就可以访问了。

public ThreadTest implements Runnable

{

public synchronized void run(){

for(int i=0;i&lt;10;i++)

{

System.out.println(&quot; &quot; + i);

}

}

public static void main(String[] args)

{

Runnable r1 = new ThreadTest();

Runnable r2 = new ThreadTest();

Thread t1 = new Thread(r1);

Thread t2 = new Thread(r2);

t1.start();

t2.start();

}

}

以上这段程序中的i 变量并不是共享数据，也就是这里的ynchronized关键字并未起作用。因为t1,t2两个线程是两个对象(r1,r2)的线程。不同的对象其数据是不同的，所以r1和r2两个对象的i变量是并不是共享数据。

当把代码改成如下：Synchronized关键字才会起作用

Runnable r = new ThreadTest();

Thread t1 = new Thread(r);

Thread t2 = new Thread(r);

t1.start();

t2.start();

### 5、sleep()

使当前线程(即调用该方法的线程)暂停执行一段时间，让其他线程有机会继续执行，但它并不释放对象锁。也就是如果有Synchronized同步块，其他线程仍然不同访问共享数据。注意该方法要捕获异常比如有两个线程同时执行(没有Synchronized)，一个线程优先级为MAX\_PRIORITY，另一个为MIN\_PRIORITY，如果没有Sleep()方法，只有高优先级的线程执行完成后，低优先级的线程才能执行；但当高优先级的线程sleep(5000)后，低优先级就有机会执行了。

总之，sleep()可以使低优先级的线程得到执行的机会，当然也可以让同优先级、高优先级的线程有执行的机会。

### 6、join()

join()方法使调用该方法的线程在此之前执行完毕，也就是等待调用该方法的线程执行完毕后再往下继续执行。注意该方法也要捕获异常。

### 7、yield()

它与sleep()类似，只是不能由用户指定暂停多长时间，并且yield()方法只能让同优先级的线程有执行的机会。

### 8、wait()和notify()、notifyAll()

这三个方法用于协调多个线程对共享数据的存取，所以必须在Synchronized语句块内使用这三个方法。前面说过Synchronized这个关键字用于保护共享数据，阻止其他线程对共享数据的存取。但是这样程序的流程就很不灵活了，如何才能在

当前线程还没退出Synchronized数据块时让其他线程也有机会访问共享数据呢？此时就用这三个方法来灵活控制。

wait()方法使当前线程暂停执行并释放对象锁标志，让其他线程可以进入Synchronized数据块，当前线程被放入对象等

待池中。当调用notify()方法后，将从对象的等待池中移走一个任意的线程并放到锁标志等待池中，只有锁标志等待池中的线程能够获取锁标志；如果锁标志等待池中没有线程，则notify()不起作用。

notifyAll()则从对象等待池中移走所有等待那个对象的线程并放到锁标志等待池中。

注意这三个方法都是java.lang.Ojbect的方法!

### 9、sleep() 和wait() 有什么区别? 　　

答：

sleep是线程类（Thread）的方法，导致此线程暂停执行指定时间，给执行机会给其他线程，但是监控状态依然保持，到时后会自动恢复。调用sleep不会释放对象锁。　　

wait是Object类的方法，对此对象调用wait方法导致本线程放弃对象锁，进入等待此对象的等待锁定池，只有针对此对象发出notify方法（或notifyAll）后本线程才进入对象锁定池准备获得对象锁进入运行状态。

sleep()方法是使线程停止一段时间的方法。在sleep 时间间隔期满后，线程不一定立即恢复执行。这是因为在那个时刻，其它线程可能正在运行而且没有被调度为放弃执行，除非

1. (a)&quot;醒来&quot;的线程具有更高的优先级
  (b)正在运行的线程因为其它原因而阻塞。
  wait()是线程交互时，如果线程对一个同步对象x 发出一个wait()调用，该线程会暂停执行，被调对象进入等待状态，直到被唤醒或等待时间到。

### 10、请说出你所知道的线程同步的方法。　　

答：

wait():使一个线程处于等待状态，并且释放所持有的对象的lock。　　

sleep():使一个正在运行的线程处于睡眠状态，是一个静态方法，调用此方法要捕捉InterruptedException异常。　　

notify():唤醒一个处于等待状态的线程，注意的是在调用此方法的时候，并不能确切的唤醒某一个等待状态的线程，而是由JVM确定唤醒哪个线程，而且不是按优先级。　　

Allnotity():唤醒所有处入等待状态的线程，注意并不是给所有唤醒线程一个对象的锁，而是让它们竞争。

### 11、线程的基本概念、线程的本状态以及状态之间的关系?

新建(Born) : 新建的线程处于新建状态

就绪(Ready) : 在创建线程后，它将处于就绪状态，等待start() 方法被调用

运行(Running) : 线程在开始执行时进入运行状态

睡眠(Sleeping) : 线程的执行可通过使用sleep() 方法来暂时中止。在睡眠后，线程将进入就绪状态

等待(Waiting) : 如果调用了wait() 方法，线程将处于等待状态。用于在两个或多个线程并发运行时。

挂起(Suspended) : 在临时停止或中断线程的执行时，线程就处于挂起状态。

恢复(Resume) : 在挂起的线程被恢复执行时，可以说它已被恢复。

阻塞(Blocked) – 在线程等待一个事件时（例如输入/输出操作），就称其处于阻塞状态。死亡(Dead) – 在run() 方法已完成执行或其stop() 方法被调用之后，线程就处于死亡状态。

### 12、线程的同步、如何实现线程的同步

答：当两个或多个线程同时访问同一个变量，并且以个线程需要修改这个变量。就要用到线程同步。在Java 中，同步是通过synchronized 关键字来定义的。诺是想同步化某程序段，可以使用synchronized(object){}方法，其中{}内的程序语句被同步化。

## 一百十三、Xml方面

### 1、xml有哪些解析技术?区别是什么?

答：有DOM,SAX,STAX等

DOM:处理大型文件时其性能下降的非常厉害。这个问题是由DOM的树结构所造成的，这种结构占用的内存较多，而且DOM必须在解析文件之前把整个文档装入内存,适合对XML的随机访问SAX:不现于DOM,SAX是事件驱动型的XML解析方式。它顺序读取XML文件，不需要一次全部装载整个文件。当遇到像文件开头，文档结束，或者标签开头与标签结束时，它会触发一个事件，用户通过在其回调事件中写入处理代码来处理XML文件，适合对XML的顺序访问

STAX:Streaming API for XML (StAX)

### 2、你在项目中用到了xml技术的哪些方面?如何实现的?

答：用到了数据存贮，信息配置两方面。在做数据交换平台时，将不能数据源的数据组装成XML文件，然后将XML文件压缩打包加密后通过网络传送给接收者，接收解密与解压缩后再同XML文件中还原相关信息进行处理。在做软件配置时，利用XML可以很方便的进行，软件的各种配置参数都存贮在XML文件中。

### 3、XML文档定义有几种形式？它们之间有何本质区别？解析XML文档有哪几种方式？

答：

a: 两种形式dtd  schema，

b: 本质区别:schema本身是xml的，可以被XML解析器解析(这也是从DTD上发展schema的根本目的)，

c:有DOM,SAX,STAX等

    DOM:处理大型文件时其性能下降的非常厉害。这个问题是由DOM的树结构所造成的，这种结构占用的内存较多，而且DOM必须在解析文件之前把整个文档装入内存,适合对XML的随机访问

SAX:不现于DOM,SAX是事件驱动型的XML解析方式。它顺序读取XML文件，不需要一次全部装载整个文件。当遇到像文件开头，文档结束，或者标签开头与标签结束时，它会触发一个事件，用户通过在其回调事件中写入处理代码来处理XML文件，适合对XML的顺序访问

STAX:Streaming API for XML (StAX)

### 4、用jdom解析xml文件时如何解决中文问题?如何解析?

答:看如下代码,用编码方式加以解决

package test;

import java.io.\*;

public class DOMTest

{

 private String inFile = &quot;c:\\people.xml&quot;;

 private String outFile = &quot;c:\\people.xml&quot;;

 public static void main(String args[])

 {

     new DOMTest();

    }

 public DOMTest()

 {

  try

     {

      javax.xml.parsers.DocumentBuilder builder =

javax.xml.parsers.DocumentBuilderFactory.newInstance().newDocumentBuilder();

      org.w3c.dom.Document doc = builder.newDocument();

      org.w3c.dom.Element root = doc.createElement(&quot;老师&quot;);

      org.w3c.dom.Element wang = doc.createElement(&quot;王&quot;);

   org.w3c.dom.Element liu = doc.createElement(&quot;刘&quot;);

      wang.appendChild(doc.createTextNode(&quot;我是王老师&quot;));

      root.appendChild(wang);

      doc.appendChild(root);

      javax.xml.transform.Transformer transformer =

       javax.xml.transform.TransformerFactory.newInstance().newTransformer();

      transformer.setOutputProperty(javax.xml.transform.OutputKeys.ENCODING, &quot;gb2312&quot;);

      transformer.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, &quot;yes&quot;);

      transformer.transform(new javax.xml.transform.dom.DOMSource(doc),

            new

javax.xml.transform.stream.StreamResult(outFile));

     }

     catch (Exception e)

     {

      System.out.println (e.getMessage());

     }

    }

}

### 5、编程用JAVA解析XML的方式.

答:用SAX方式解析XML，XML文件如下：

&lt;?xml version=&quot;1.0&quot; encoding=&quot;gb2312&quot;?&gt;

&lt;person&gt;

  &lt;name&gt;王小明&lt;/name&gt;

  &lt;college&gt;信息学院&lt;/college&gt;

  &lt;telephone&gt;6258113&lt;/telephone&gt;

  &lt;notes&gt;男,1955年生,博士，95年调入海南大学&lt;/notes&gt;

 &lt;/person&gt;

 事件回调类SAXHandler.java

 import java.io.\*;

import java.util.Hashtable;

import org.xml.sax.\*;

public class SAXHandler extends HandlerBase

  {

  private Hashtable table = new Hashtable();

  private String currentElement = null;

  private String currentValue = null;

  public void setTable(Hashtable table)

    {

    this.table = table;

    }

  public Hashtable getTable()

    {

    return table;

    }

  public void startElement(String tag, AttributeList attrs)

  throws SAXException

    {

    currentElement = tag;

    }

  public void characters(char[] ch, int start, int length)

  throws SAXException

    {

    currentValue = new String(ch, start, length);

    }

  public void endElement(String name) throws SAXException

    {

    if (currentElement.equals(name))

      table.put(currentElement, currentValue);

    }

  }

JSP内容显示源码,SaxXml.jsp:

&lt;HTML&gt;

&lt;HEAD&gt;

&lt;TITLE&gt;剖析XML文件people.xml&lt;/TITLE&gt;

&lt;/HEAD&gt;

&lt;BODY&gt;

&lt;%@ page errorPage=&quot;ErrPage.jsp&quot;

contentType=&quot;text/html;charset=GB2312&quot; %&gt;

&lt;%@ page import=&quot;java.io.\*&quot; %&gt;

&lt;%@ page import=&quot;java.util.Hashtable&quot; %&gt;

&lt;%@ page import=&quot;org.w3c.dom.\*&quot; %&gt;

&lt;%@ page import=&quot;org.xml.sax.\*&quot; %&gt;

&lt;%@ page import=&quot;javax.xml.parsers.SAXParserFactory&quot; %&gt;

&lt;%@ page import=&quot;javax.xml.parsers.SAXParser&quot; %&gt;

&lt;%@ page import=&quot;SAXHandler&quot; %&gt;

&lt;%

File file = new File(&quot;c:\\people.xml&quot;);

FileReader reader = new FileReader(file);

Parser parser;

SAXParserFactory spf = SAXParserFactory.newInstance();

SAXParser sp = spf.newSAXParser();

SAXHandler handler = new SAXHandler();

sp.parse(new InputSource(reader), handler);

Hashtable hashTable = handler.getTable();

out.println(&quot;&lt;TABLE BORDER=2&gt;&lt;CAPTION&gt;教师信息表&lt;/CAPTION&gt;&quot;);

out.println(&quot;&lt;TR&gt;&lt;TD&gt;姓名&lt;/TD&gt;&quot; + &quot;&lt;TD&gt;&quot; +

  (String)hashTable.get(new String(&quot;name&quot;)) + &quot;&lt;/TD&gt;&lt;/TR&gt;&quot;);

out.println(&quot;&lt;TR&gt;&lt;TD&gt;学院&lt;/TD&gt;&quot; + &quot;&lt;TD&gt;&quot; +

  (String)hashTable.get(new String(&quot;college&quot;))+&quot;&lt;/TD&gt;&lt;/TR&gt;&quot;);

out.println(&quot;&lt;TR&gt;&lt;TD&gt;电话&lt;/TD&gt;&quot; + &quot;&lt;TD&gt;&quot; +

  (String)hashTable.get(new String(&quot;telephone&quot;)) + &quot;&lt;/TD&gt;&lt;/TR&gt;&quot;);

out.println(&quot;&lt;TR&gt;&lt;TD&gt;备注&lt;/TD&gt;&quot; + &quot;&lt;TD&gt;&quot; +

  (String)hashTable.get(new String(&quot;notes&quot;)) + &quot;&lt;/TD&gt;&lt;/TR&gt;&quot;);

out.println(&quot;&lt;/TABLE&gt;&quot;);

%&gt;

&lt;/BODY&gt;

&lt;/HTML&gt;

## 一百十四、EJB方面

### 1、EJB2.0有哪些内容?分别用在什么场合? EJB2.0和EJB1.1的区别?

答：规范内容包括Bean提供者，应用程序装配者，EJB容器，EJB配置工具，EJB服务提供者，系统管理员。这里面，EJB容器是EJB之所以能够运行的核心。EJB容器管理着EJB的创建，撤消，激活，去活，与数据库的连接等等重要的核心工作。JSP,Servlet,EJB,JNDI,JDBC,JMS.....

### 2、EJB是基于哪些技术实现的？并说出SessionBean和EntityBean的区别，StatefulBean和StatelessBean的区别。

 答：EJB包括Session Bean、Entity Bean、Message Driven Bean，基于JNDI、RMI、JAT等技术实现。

SessionBean在J2EE应用程序中被用来完成一些服务器端的业务操作，例如访问数据库、调用其他EJB组件。EntityBean被用来代表应用系统中用到的数据。

对于客户机，SessionBean是一种非持久性对象，它实现某些在服务器上运行的业务逻辑。

对于客户机，EntityBean是一种持久性对象，它代表一个存储在持久性存储器中的实体的对象视图，或是一个由现有企业应用程序实现的实体。

Session Bean 还可以再细分为Stateful Session Bean 与Stateless Session Bean ，这两种的Session Bean都可以将系统逻辑放在method之中执行，不同的是Stateful Session Bean 可以记录呼叫者的状态，因此通常来说，一个使用者会有一个相对应的Stateful Session Bean 的实体。Stateless Session Bean 虽然也是逻辑组件，但是他却不负责记录使用者状态，也就是说当使用者呼叫Stateless Session Bean 的时候，EJB Container 并不会找寻特定的Stateless Session Bean 的实体来执行这个method。换言之，很可能数个使用者在执行某个Stateless Session Bean 的methods 时，会是同一个Bean 的Instance 在执行。从内存方面来看，Stateful Session Bean 与Stateless Session Bean 比较，Stateful Session Bean 会消耗J2EE Server 较多的内存，然而Stateful Session Bean 的优势却在于他可以维持使用者的状态。

### 3、EJB与JAVA BEAN的区别？

答：Java Bean 是可复用的组件，对Java Bean并没有严格的规范，理论上讲，任何一个Java类都可以是一个Bean。但通常情况下，由于Java Bean是被容器所创建（如Tomcat）的，所以Java Bean应具有一个无参的构造器，另外，通常Java Bean还要实现Serializable接口用于实现Bean的持久性。Java Bean实际上相当于微软COM模型中的本地进程内COM组件，它是不能被跨进程访问的。Enterprise Java Bean 相当于DCOM，即分布式组件。它是基于Java的远程方法调用（RMI）技术的，所以EJB可以被远程访问（跨进程、跨计算机）。但EJB必须被布署在诸如Webspere、WebLogic这样的容器中，EJB客户从不直接访问真正的EJB组件，而是通过其容器访问。EJB容器是EJB组件的代理，EJB组件由容器所创建和管理。客户通过容器来访问真正的EJB组件。

EJB包括（SessionBean,EntityBean）说出他们的生命周期，及如何管理事务的？

SessionBean：Stateless Session Bean 的生命周期是由容器决定的，当客户机发出请求要建立一个Bean的实例时，EJB容器不一定要创建一个新的Bean的实例供客户机调用，而是随便找一个现有的实例提供给客户机。当客户机第一次调用一个Stateful Session Bean 时，容器必须立即在服务器中创建一个新的Bean实例，并关联到客户机上，以后此客户机调用Stateful Session Bean 的方法时容器会把调用分派到与此客户机相关联的Bean实例。

EntityBean：Entity Beans能存活相对较长的时间，并且状态是持续的。只要数据库中的数据存在，Entity beans就一直存活。而不是按照应用程序或者服务进程来说的。即使EJB容器崩溃了，Entity beans也是存活的。Entity Beans生命周期能够被容器或者Beans自己管理。

EJB通过以下技术管理实务：对象管理组织（OMG）的对象实务服务（OTS），Sun Microsystems的Transaction Service（JTS）、Java Transaction API（JTA），开发组（X/Open）的XA接口。

### 4、EJB的角色和三个对象

答：一个完整的基于EJB的分布式计算结构由六个角色组成，这六个角色可以由不同的开发商提供，每个角色所作的工作必须遵循Sun公司提供的EJB规范，以保证彼此之间的兼容性。这六个角色分别是EJB组件开发者（Enterprise Bean Provider）、应用组合者（Application Assembler）、部署者（Deployer）、EJB 服务器提供者（EJB Server Provider）、EJB 容器提供者（EJB Container Provider）、系统管理员（System Administrator）

三个对象是Remote（Local）接口、Home（LocalHome）接口，Bean类

### 5、EJB容器提供的服务

答：主要提供声明周期管理、代码产生、持续性管理、安全、事务管理、锁和并发行管理等服务。

### 6、EJB规范规定EJB中禁止的操作有哪些？

 答：1.不能操作线程和线程API(线程API指非线程对象的方法如notify,wait等)，2.不能操作awt，3.不能实现服务器功能，4.不能对静态属生存取，5.不能使用IO操作直接存取文件系统，6.不能加载本地库.，7.不能将this作为变量和返回，8.不能循环调用。

### 7、remote接口和home接口主要作用

答：remote接口定义了业务方法，用于EJB客户端调用业务方法。

home接口是EJB工厂用于创建和移除查找EJB实例

### 8、bean 实例的生命周期

答：对于Stateless Session Bean、Entity Bean、Message Driven Bean一般存在缓冲池管理，而对于Entity Bean和Statefull Session Bean存在Cache管理，通常包含创建实例，设置上下文、创建EJB Object（create）、业务方法调用、remove等过程，对于存在缓冲池管理的Bean，在create之后实例并不从内存清除，而是采用缓冲池调度机制不断重用实例，而对于存在Cache管理的Bean则通过激活和去激活机制保持Bean的状态并限制内存中实例数量。

### 9、EJB的激活机制

答：以Stateful Session Bean 为例：其Cache大小决定了内存中可以同时存在的Bean实例的数量，根据MRU或NRU算法，实例在激活和去激活状态之间迁移，激活机制是当客户端调用某个EJB实例业务方法时，如果对应EJB Object发现自己没有绑定对应的Bean实例则从其去激活Bean存储中（通过序列化机制存储实例）回复（激活）此实例。状态变迁前会调用对应的ejbActive和ejbPassivate方法。

### 10、EJB的几种类型

答：会话（Session）Bean ，实体（Entity）Bean 消息驱动的（Message Driven）Bean

会话Bean又可分为有状态（Stateful）和无状态（Stateless）两种

实体Bean可分为Bean管理的持续性（BMP）和容器管理的持续性（CMP）两种

### 11、客服端调用EJB对象的几个基本步骤

答：设置JNDI服务工厂以及JNDI服务地址系统属性，查找Home接口，从Home接口调用Create方法创建Remote接口，通过Remote接口调用其业务方法。

应用服务器方面

### 12、如何给weblogic指定大小的内存?

答：在启动Weblogic的脚本中（位于所在Domian对应服务器目录下的startServerName），增加set MEM\_ARGS=-Xms32m -Xmx200m，可以调整最小内存为32M，最大200M

EJB需直接实现它的业务接口或Home接口吗，请简述理由。

远程接口和Home接口不需要直接实现，他们的实现代码是由服务器产生的，程序运行中对应实现类会作为对应接口类型的实例被使用。

### 13、应用服务器有那些？

答：BEA WebLogic Server，

IBM WebSphere Application Server，

Oracle9i Application Server，

jBoss

Tomcat

### 14、如何设定的weblogic的热启动模式(开发模式)与产品发布模式?

答：可以在管理控制台中修改对应服务器的启动模式为开发或产品模式之一。或者修改服务的启动文件或者commenv文件，增加set PRODUCTION\_MODE=true。

### 15、如何启动时不需输入用户名与密码?

答：修改服务启动文件，增加WLS\_USER和WLS\_PW项。也可以在boot.properties文件中增加加密过的用户名和密码.

### 16、在weblogic管理制台中对一个应用域(或者说是一个网站,Domain)进行jms及ejb或连接池等相关信息进行配置后,实际保存在什么文件中?

答：保存在此Domain的config.xml文件中，它是服务器的核心配置文件。

### 17、说说weblogic中一个Domain的缺省目录结构?比如要将一个简单的helloWorld.jsp放入何目录下,然的在浏览器上就可打入aspectratio=&quot;t&quot; v:ext=&quot;edit&quot;&gt;http://主机:端口号//helloword.jsp就可以看到运行结果了?  又比如这其中用到了一个自己写的javaBean该如何办?

答：Domain目录服务器目录applications，将应用目录放在此目录下将可以作为应用访问，如果是Web应用，应用目录需要满足Web应用目录要求，jsp文件可以直接放在应用目录中，Javabean需要放在应用目录的WEB-INF目录的classes目录中，设置服务器的缺省应用将可以实现在浏览器上无需输入应用名。

### 18、在weblogic中发布ejb需涉及到哪些配置文件

答：不同类型的EJB涉及的配置文件不同，都涉及到的配置文件包括ejb-jar.xml,weblogic-ejb-jar.xmlCMP实体Bean一般还需要weblogic-cmp-rdbms-jar.xml

### 19、如何查看在weblogic中已经发布的EJB?

答：可以使用管理控制台，在它的Deployment中可以查看所有已发布的EJB

说说在weblogic中开发消息Bean时的persistent与non-persisten的差别

persistent方式的MDB可以保证消息传递的可靠性,也就是如果EJB容器出现问题而JMS服务器依然会将消息在此MDB可用的时候发送过来，而non－persistent方式的消息将被丢弃。

J2EE,MVC方面

### 20、如何在weblogic中进行ssl配置与客户端的认证配置或说说j2ee(标准)进行ssl的配置

缺省安装中使用DemoIdentity.jks和DemoTrust.jks  KeyStore实现SSL，需要配置服务器使用Enable SSL，配置其端口，在产品模式下需要从CA获取私有密钥和数字证书，创建identity和trust keystore，装载获得的密钥和数字证书。可以配置此SSL连接是单向还是双向的。

### 21、STRUTS的应用(如STRUTS架构)

答：Struts是采用Java Servlet/JavaServer Pages技术，开发Web应用程序的开放源码的framework。采用Struts能开发出基于MVC(Model-View-Controller)设计模式的应用构架。Struts有如下的主要功能：

一.包含一个controller servlet，能将用户的请求发送到相应的Action对象。

二.JSP自由tag库，并且在controller servlet中提供关联支持，帮助开发员创建交互式表单应用。

三.提供了一系列实用对象：XML处理、通过Java reflection APIs自动处理JavaBeans属性、国际化的提示和消息。

### 22、开发中都用到了那些设计模式?用在什么场合?

答：每个模式都描述了一个在我们的环境中不断出现的问题，然后描述了该问题的解决方案的核心。通过这种方式，你可以无数次地使用那些已有的解决方案，无需在重复相同的工作。主要用到了MVC的设计模式。用来开发JSP/Servlet或者J2EE的相关应用。简单工厂模式等。

### 23、1.0.66．说说你所熟悉或听说过的j2ee中的几种常用模式?及对设计模式的一些看法

答：Session Facade Pattern：使用SessionBean访问EntityBean

Message Facade Pattern：实现异步调用

EJB Command Pattern：使用Command JavaBeans取代SessionBean，实现轻量级访问

Data Transfer Object Factory：通过DTO Factory简化EntityBean数据提供特性

Generic Attribute Access：通过AttibuteAccess接口简化EntityBean数据提供特性

Business Interface：通过远程（本地）接口和Bean类实现相同接口规范业务逻辑一致性

ＥＪＢ架构的设计好坏将直接影响系统的性能、可扩展性、可维护性、组件可重用性及开发效率。项目越复杂，项目队伍越庞大则越能体现良好设计的重要性。

### 24、j2ee常用的设计模式？说明工厂模式。

 答：Java中的23种设计模式：

Factory（工厂模式），      Builder（建造模式），       Factory Method（工厂方法模式），

Prototype（原始模型模式），Singleton（单例模式），    Facade（门面模式），

Adapter（适配器模式），    Bridge（桥梁模式），        Composite（合成模式），

Decorator（装饰模式），    Flyweight（享元模式），     Proxy（代理模式），

Command（命令模式），      Interpreter（解释器模式），Visitor（访问者模式），

Iterator（迭代子模式），   Mediator（调停者模式），    Memento（备忘录模式），

Observer（观察者模式），   State（状态模式），         Strategy（策略模式），

Template Method（模板方法模式），Chain Of Responsibleity（责任链模式）

工厂模式：工厂模式是一种经常被使用到的模式，根据工厂模式实现的类可以根据提供的数据生成一组类中某一个类的实例，通常这一组类有一个公共的抽象父类并且实现了相同的方法，但是这些方法针对不同的数据进行了不同的操作。首先需要定义一个基类，该类的子类通过不同的方法实现了基类中的方法。然后需要定义一个工厂类，工厂类可以根据条件生成不同的子类实例。当得到子类的实例后，开发人员可以调用基类中的方法而不必考虑到底返回的是哪一个子类的实例。

## 一百十五、spring

### 1、Spring的优点有什么?

1. Spring是分层的架构，你可以选择使用你需要的层而不用管不需要的部分
2. Spring是POJO编程，POJO编程使得可持续构建和可测试能力提高
3. 依赖注入和IoC使得JDBC操作简单化
4. Spring是开源的免费的
5. Spring使得对象管理集中化合简单化

### 2、描述一下spring中实现DI（dependency injection）的几种方式

方式一：接口注入，在实际中得到了普遍应用，即使在IOC的概念尚未确立时，这样的方法也已经频繁出现在我们的代码中。
方式二：Type2 IoC: Setter injection对象创建之后，将被依赖对象通过set方法设置进去
方式三：Type3 IoC: Constructor injection对象创建时，被依赖对象以构造方法参数的方式注入
Spring的方式

### 3、简单描述下IOC(inversion of control)的理解

一个类需要用到某个接口的方法，我们需要将类A和接口B的实现关联起来，最简单的方法是类A中创建一个对于接口B的实现C的实例，但这种方法显然两者的依赖（Dependency）太大了。而IoC的方法是只在类A中定义好用于关联接口B的实现的方法，将类A，接口B和接口B的实现C放入IoC的 容器（Container）中，通过一定的配置由容器（Container）来实现类A与接口B的实现C的关联。

### 4、Spring对很多ORM框架提供了很好支持，描述下在spring使用hibernate的方法

在context中定义DataSource，创建SessionFactoy，设置参数；DAO类继承HibernateDaoSupport，实现具体接口，从中获得HibernateTemplate进行具体操作。在使用中如果遇到OpenSessionInView的问题，可以添加OpenSessionInViewFilter或OpenSessionInViewInterceptor

### 5、请介绍下spring的事务管理

spring提供了几个关于事务处理的类：
TransactionDefinition //事务属性定义
TranscationStatus //代表了当前的事务，可以提交，回滚。
PlatformTransactionManager这个是spring提供的用于管理事务的基础接口，其下有一个实现的抽象类AbstractPlatformTransactionManager,我们使用的事务管理类例如DataSourceTransactionManager等都是这个类的子类。

一般事务定义步骤：

```java
TransactionDefinition td = new TransactionDefinition();
TransactionStatus ts = transactionManager.getTransaction(td);
try
{ 
	//do sth
	transactionManager.commit(ts);
}
catch(Exception e){
  transactionManager.rollback(ts);
}
```

spring提供的事务管理可以分为两类：编程式的和声明式的。编程式的，比较灵活，但是代码量大，存在重复的代码比较多；声明式的比编程式的更灵活。

编程式主要使用transactionTemplate。省略了部分的提交，回滚，一系列的事务对象定义，需注入事务管理对象.

```java
void add(){
	transactionTemplate.execute( new TransactionCallback(){
      pulic Object doInTransaction(TransactionStatus ts){ 
      //do sth
      }
	}
}
```

声明式：

使用TransactionProxyFactoryBean:

PROPAGATION\_REQUIRED PROPAGATION\_REQUIRED PROPAGATION\_REQUIRED,readOnly

围绕Poxy的动态代理 能够自动的提交和回滚事务
org.springframework.transaction.interceptor.TransactionProxyFactoryBean

PROPAGATION\_REQUIRED–支持当前事务，如果当前没有事务，就新建一个事务。这是最常见的选择。

PROPAGATION\_SUPPORTS–支持当前事务，如果当前没有事务，就以非事务方式执行。

PROPAGATION\_MANDATORY–支持当前事务，如果当前没有事务，就抛出异常。

PROPAGATION\_REQUIRES\_NEW–新建事务，如果当前存在事务，把当前事务挂起。

PROPAGATION\_NOT\_SUPPORTED–以非事务方式执行操作，如果当前存在事务，就把当前事务挂起。

PROPAGATION\_NEVER–以非事务方式执行，如果当前存在事务，则抛出异常。

PROPAGATION\_NESTED–如果当前存在事务，则在嵌套事务内执行。如果当前没有事务，则进行与PROPAGATION\_REQUIRED类似的操作。

### 6、如何在spring的applicationContext.xml使用JNDI而不是DataSource

可以使用&quot;org.springframework.jndi.JndiObjectFactoryBean&quot;来实现。示例如下：
```xml
<bean id="dataSource">
    <property name="jndiName">
        <value>java:comp/env/jdbc/appfuse</value>
    </property>
</bean>
```

### 7/在spring中是如何配置数据库驱动的

org.springframework.jdbc.datasource.DriverManagerDataSource&quot;数据源来配置数据库驱动。示例如下：
```xml
<bean id="dataSource">
    <property name="driverClassName">
        <value>org.hsqldb.jdbcDriver</value>
    </property>
    <property name="url">
        <value>jdbc:hsqldb:db/appfuse</value>
    </property>
    <property name="username"><value>sa</value></property>
    <property name="password"><value></value></property>
</bean>
```


### 8、spring中的applicationContext.xml能不能改为其他名字

ContextLoaderListener是一个ServletContextListener, 它在你的web应用启动的时候初始化。缺省情况下， 它会在WEB-INF/applicationContext.xml文件找Spring的配置。 你可以通过定义一个&lt;context-param&gt;元素名字为&quot;contextConfigLocation&quot;来改变Spring配置文件的位置。示例如下：

```xml
<listener>
    <listener-class>org.springframework.web.context.ContextLoaderListener
 
    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>/WEB-INF/xyz.xml</param-value>
    </context-param>
 
    </listener-class>
</listener>
```

### 9、在web中如何配置spring

在J2EE的web应用里面配置spring非常简单，最简单的只需要把spring得ContextLoaderListener添加到你的web.xml文件里面就可以了，示例如下：
```xml
<listener>
    <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
</listener>
```


### 10、在spring中如何定义hibernate Mapping？

添加hibernate mapping 文件到web/WEB-INF目录下的applicationContext.xml文件里面。示例如下：
```xml
<property name=”mappingResources”>
    <list>
        <value>org/appfuse/model/User.hbm.xml</value>
    </list>
</property>
```


### 11、两种依赖注入的类型是什么?

两种依赖注入的类型分别是setter注入和构造方法注入。

#### （1）setter注入：

 一般情况下所有的java bean, 我们都会使用setter方法和getter方法去设置和获取属性的值，示例如下：
```java
public class namebean {
	String name;
	public void setName( String a )
	{
		name = a;
	}


	public String getName()
	{
		return(name);
	}
}
```
我们会创建一个bean的实例然后设置属性的值，spring的配置文件如下：
```xml
<bean id="bean1">
   <property   name="name">
       <value>tom</value>
   </property>
</bean>
```

Spring会调用setName方法来只是name熟悉为tom

#### （2）构造方法注入：

构造方法注入中，我们使用带参数的构造方法如下：
```java
public class namebean {
	String name;
 	public namebean(String a) {
   	 	name = a;
 	}
}
```
我们会在创建bean实例的时候以new namebean(&quot;tom&quot;)的方式来设置name属性, Spring配置文件如下：
```xml
<bean id="bean1" >
    <constructor-arg>
       <value>My Bean Value</value>
   </constructor-arg>
</bean>
```

使用constructor-arg标签来设置构造方法的参数。

### 12、解释一下Dependency Injection(DI)和IOC（inversion of control）?

参考答案：依赖注入DI是一个程序设计模式和架构模型，一些时候也称作控制反转，尽管在技术上来讲，依赖注入是一个IOC的特殊实现，依赖注入是指一个对象应用另外一个对象来提供一个特殊的能力，例如：把一个数据库连接已参数的形式传到一个对象的结构方法里面而不是在那个对象内部自行创建一个连接。控制反转和依赖注入的基本思想就是把类的依赖从类内部转化到外部以减少依赖
应用控制反转，对象在被创建的时候，由一个调控系统内所有对象的外界实体，将其所依赖的对象的引用，传递给它。也可以说，依赖被注入到对象中。所以，控制反转是，关于一个对象如何获取他所依赖的对象的引用，这个责任的反转

### 13、Spring中BeanFactory和ApplicationContext的作用和区别

作用：

1. BeanFactory负责读取bean配置文档，管理bean的加载，实例化，维护bean之间的依赖关系，负责bean的生命周期。
2. ApplicationContext除了提供上述BeanFactory所能提供的功能之外，还提供了更完整的框架功能：

a. 国际化支持
b. 资源访问：Resource rs = ctx. getResource(&quot;classpath:config.properties&quot;), &quot;file:c:/config.properties&quot;
c. 事件传递：通过实现ApplicationContextAware接口
3. 常用的获取ApplicationContext的方法：
  FileSystemXmlApplicationContext：从文件系统或者url指定的xml配置文件创建，参数为配置文件名或文件名数组
  ClassPathXmlApplicationContext：从classpath的xml配置文件创建，可以从jar包中读取配置文件
  WebApplicationContextUtils：从web应用的根目录读取配置文件，需要先在web.xml中配置，可以配置监听器或者servlet来实现

  ```xml
  <listener>
  	<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
  </listener>
  <servlet>
  	<servlet-name>context</servlet-name>
  	<servlet-class>org.springframework.web.context.ContextLoaderServlet</servlet-class>
  	<load-on-startup>1</load-on-startup>
  </servlet>
  ```

  这两种方式都默认配置文件为web-inf/applicationContext.xml，也可使用context-param指定配置文件

  ```xml
  <context-param>
  	<param-name>contextConfigLocation</param-name>
  	<param-value>/WEB-INF/myApplicationContext.xml</param-value>
  </context-param>
  ```

### 14、在web环境下如何配置applicationContext.xml文件

```xml
<listener>
  <listener-class>
   org.springframework.web.context.ContextLoaderListener
  </listener-class>
</listener>
```

 或：
```xml
 <servlet>
  <servlet-name>context</servlet-name>
   <servlet-class>
    org.springframework.web.context.ContextLoaderServlet
   </servlet-class>
  <load-on-startup>1</load-on-startup>
 </servlet>
```

 通过如下方法取出applicationContext实例:
 ApplicationContext ac=WebApplicationContextUtils.getWebApplicationContext(this.getServletContext);

### 15、如何配置spring + struts？

在struts-config.xml加入一个插件，通过它加载applicationContext.xml
在struts-config.xml修改action-mapping标记，具体action交给了DelegateActionProxy
通过DelegateActionProxy进入一spring的环境。
在spring的applicationContext.xml加入<bean name=&quot;/login&quot; class= &quot; "singleton=&quot;false&quot;/&gt;

### 16、Spring 和hibernate的配置文件中的主要类型有哪些？如何配置?

在myeclipse中先加入spring环境再加入hibernate环境。
 如果spring与hibernate结合在一起可以不需要hibernate.cfg.xml文件是否正确?
 spring+hibernate的配置文件中的主要类有那些?如何配置?
  dataSource
  sessionFactory:hibernate.cfg.xml
  transactionManager
  userDao (extends HibernateDaoSupport)
   sessionFactory
  facade
  proxy
   sessionFactory
   transactionManager
   facade

### 17、在spring中如何配置容器的事物管理，相关的类有哪些?

Datasouce
   transactionManager
   userDao要注入Datasouce

   Proxy代理

Target:userDao：代理对象(目标对象)
transactionAttributes(那些方法需要事务处理)
transactionManager(事务处理服务)

### 18、在spring中如何配代码的事务管理器

Datasouce
   transactionManager
   userDao要注入Datasouce、transactionManager

  通过如下类实现

TransactionTemplate
JdbcTemplate

### 19、Spring中有几种事物管理，分别是什么?

代码管理的事务处理
TransactonTemplate的execute方法中的内部类TransactionCallback中的doInTransaction方法中使用。

```java
public void make()
 { 
  TransactionTemplate jtm=new TransactionTemplate(this.getTransactionManager());
  jtm.execute(new myClass1());
 }
 public class myClass1 implements TransactionCallback
 {
  public Object doInTransaction(TransactionStatus trans)
  {
   JdbcTemplate jdbc=new JdbcTemplate(dataSource);
   jdbc.execute(”insert into customer(customerName) values(’b')”);
   jdbc.execute(”insert into customer(customerName) values(’b')”);
   return null;
  }  
 }

```

   容器管理的事务处理

### 20、spring中的jdbc与传统的jdbc有什么区别?

Spring的jdbc:节省代码，不管连接(Connection)，不管事务、不管异常、不管关闭(con.close() ps.close )

  JdbcTemplate(dataSource):增、删、改、查
  TransactionTemplate(transactionManager):进行事务处理

### 21、Spring配置的主要标签有什么?有什么作用?

```xml
<beans>
   <bean id="" class="" init="" destroy="" singleton="">
    <property name="">
     <value></value>
    </property>
    <property name="">
     <ref></ref>
    </property>
   </bean>
</beans>
```

### 22、如何在spring中实现国际化?

在applicationContext.xml加载一个bean

```xml
<bean id="messageSource" class="org.springframework.context.support.ResourceBundleMessageSource">
  <property name="basename">
   <value>message</value>
  </property>
</bean>
```


 在src目录下建多个properties文件
 对于非英文的要用native2ascii -encoding gb2312 源 目转化文件相关内容
 其命名格式是message\_语言\_国家。
 页面中的中显示提示信息，键名取键值。
 当给定国家，系统会自动加载对应的国家的proerties信息。
 通过applictionContext.getMessage(&quot;键名&quot;,&quot;参数&quot;,&quot;区域&quot;)取出相关信息。

### 23、在spring中如何实现事件处理

事件
  Extends  ApplicationEvent
监听器
  Implements  ApplicationListener
事件源
  Implements  ApplicationContextAware
在applicationContext.xml中配置事件源、监听器
先得到事件源，调用事件源的方法，通知监听器。

### 24、如何将spring加入web容器中

在web.xml中加入如下同容,在启动web服务器时加载/WEB-INF/applicationContext.xml中的内容。

```xml
<servlet>
<servlet-name>context</servlet-name>
<servlet-class>
org.springframework.web.context.ContextLoaderServlet
</servlet-class>
<load-on-startup>1</load-on-startup>
</servlet>
```

通过如下类得到ApplicationContext实例
   WebApplicationContextUtils.getWebApplicationContext

### 25、Spring如何实现资源管理?

使用
applicationContext.getResource(&quot;classpath:文件名&quot;):在src根目录下，在类路径下
applicationContext.getResource(&quot;classpath:/chap01/文件名&quot;): 以src根目录下的基准往下走。
applicationContext.getResource(&quot;file:c:/a.properties&quot;)：在系统文件目录下。

### 26、Spring的ApplicationContext的作用?

beanFactory
国际化(getMesage)
资源管理:可以直接读取一个文件的内容(getResource)
加入web框架中(加入一个servlet或监听器)
事件处理

### 27、spring的核心是什么，各有什么作用？

BeanFactory：产生一个新的实例，可以实现单例模式
BeanWrapper：提供统一的get及set方法
ApplicationContext:提供框架的实现，包括BeanFactory的所有功能

### 28、Spring中aop的关键名词有哪些？各有什么作用?

拦截器: 代理
装备(advice)
目标对象
关切点:条件
连接点:方法、属性

### 29、Spring与struts的区别？

strusts：是一种基于MVC模式的一个web层的处理。
Spring:提供了通用的服务，ioc/di aop,关心的不仅仅web层，应当j2ee整体的一个服务，可以很容易融合不同的技术struts hibernate ibatis ejb remote springJDBC springMVC

### 30、spring与struts的面试题

#### （1）strutsAction是不是线程安全的？如果不是，有什么方式可以保证Action的线程安全？如果是，说明原因

Struts1 Action是单例模式并且必须是线程安全的，因为仅有Action的一个实例来处理所有的请求。单例策略限制了Struts1 Action能作的事，并且要在开发时特别小心。Action资源必须是线程安全的或同步的。
Struts2 Action对象为每一个请求产生一个实例，因此没有线程安全问题。（实际上，servlet容器给每个请求产生许多可丢弃的对象，并且不会导致性能和垃圾回收问题）

#### （2）MVC，分析一下struts是如何实现MVC的

struts是用一组类,servlet 和jsp规范实现mvc的

#### （3）struts中的几个关键对象的作用(说说几个关键对象的作用)

ActionFrom ActionServlet Action struts-config.xml

#### （4）spring  说说AOP和IOC的概念以及在spring中是如何应用的

spring的核心就是IOC,通过指定对象的创建办法,描述对象与服务之间的关系,而不生成对象

#### （5）Hibernate有哪几种查询数据的方式

3种,hql 条件查询() 原生sql

#### （6）load()和get()的区别

load()方法认为该数据一定存在,可以放心的使用代理来延时加载,如果使用过程中发现了问题,就抛出异常;
get()方法一定要获取到真实的数据,否则返回null

### 31、Spring, hibernate ,struts面试题

#### （1）Hibernate工作原理及为什么要用？

原理：

1. 读取并解析配置文件
2. 读取并解析映射信息，创建SessionFactory
3. 打开Sesssion
4. 创建事务Transation
5. 持久化操作
6. 提交事务
7. 关闭Session
8. 关闭SesstionFactory

为什么要用：

 对JDBC访问数据库的代码做了封装，大大简化了数据访问层繁琐的重复性代码。
 Hibernate是一个基于JDBC的主流持久化框架，是一个优秀的ORM实现。他很大程度的简化DAO层的编码工作
 hibernate使用Java反射机制，而不是字节码增强程序来实现透明性。
 hibernate的性能非常好，因为它是个轻量级框架。映射的灵活性很出色。它支持各种关系数据库，从一对一到多对多的各种复杂关系。

#### （2）Hibernate是如何延迟加载?

 Hibernate2延迟加载实现：a)实体对象 b)集合（Collection）
 Hibernate3 提供了属性的延迟加载功能

当Hibernate在查询数据的时候，数据并没有存在与内存中，当程序真正对数据的操作时，对象才存在与内存中，就实现了延迟加载，他节省了服务器的内存开销，从而提高了服务器的性能。

#### （3）Hibernate中怎样实现类之间的关系?(如：一对多、多对多的关系)

类与类之间的关系主要体现在表与表之间的关系进行操作，它们都市对对象进行操作，我们程序中把所有的表与类都映射在一起，它们通过配置文件中的many-to-one、one-to-many、many-to-many、

#### （4）说下Hibernate的缓存机制

内部缓存存在Hibernate中又叫一级缓存，属于应用事物级缓存
二级缓存：

a)应用及缓存

b)分布式缓存

条件：数据不会被第三方修改、数据大小在可接受范围、数据更新频率低、同一数据被系统频繁使用、非关键数据

c)  第三方缓存的实现

#### （5）Hibernate的查询方式

Sql、Criteria,object comptosition
Hql：

\* 属性查询
\* 参数查询、命名参数查询
\* 关联查询
\* 分页查询
\* 统计函数

#### （6）如何优化Hibernate？

\* 使用双向一对多关联，不使用单向一对多
\* 灵活使用单向一对多关联
\* 不用一对一，用多对一取代
\* 配置对象缓存，不使用集合缓存
\* 一对多集合使用Bag,多对多集合使用Set
\* 继承类使用显式多态
\* 表字段要少，表关联不要怕多，有二级缓存撑腰

#### （7）Struts工作机制？为什么要使用Struts？

工作机制：
Struts的工作流程:
在web应用启动时就会加载初始化ActionServlet,ActionServlet从struts-config.xml文件中读取配置信息,把它们存放到各种配置对象当ActionServlet接收到一个客户请求时,将执行如下流程.
(1)检索和用户请求匹配的ActionMapping实例,如果不存在,就返回请求路径无效信息;
(2)如果ActionForm实例不存在,就创建一个ActionForm对象,把客户提交的表单数据保存到ActionForm对象中;
(3)根据配置信息决定是否需要表单验证.如果需要验证,就调用ActionForm的validate()方法;
(4)如果ActionForm的validate()方法返回null或返回一个不包含ActionMessage的ActuibErrors对象, 就表示表单验证成功;
(5)ActionServlet根据ActionMapping所包含的映射信息决定将请求转发给哪个Action,如果相应的Action实例不存在,就先创建这个实例,然后调用Action的execute()方法;
(6)Action的execute()方法返回一个ActionForward对象,ActionServlet在把客户请求转发给ActionForward对象指向的JSP组件;
(7)ActionForward对象指向JSP组件生成动态网页,返回给客户;

为什么要用：

JSP、Servlet、JavaBean技术的出现给我们构建强大的企业应用系统提供了可能。但用这些技术构建的系统非常的繁乱，所以在此之上，我们需要一个规则、一个把这些技术组织起来的规则，这就是框架，Struts便应运而生。

基于Struts开发的应用由3类组件构成：控制器组件、模型组件、视图组件
Struts的validate框架是如何验证的？
在struts配置文件中配置具体的错误提示，再在FormBean中的validate()方法具体调用。

#### （8）说下Struts的设计模式

MVC模式:  web应用程序启动时就会加载并初始化ActionServler。用户提交表单时，一个配置好的ActionForm对象被创建，并被填入表单相应的数据，ActionServler根据Struts-config.xml 文件配置好的设置决定是否需要表单验证，如果需要就调用ActionForm的Validate（）验证后选择将请求发送到哪个Action，如果Action不存在，ActionServlet会先创建这个对象，然后调用Action的execute（）方法。Execute（）从ActionForm对象中获取数据，完成业务逻辑，返回一个ActionForward对象，ActionServlet再把客户请求转发给ActionForward对象指定的jsp组件，ActionForward对象指定的jsp生成动态的网页，返回给客户。

单例模式

Factory(工厂模式)：

定义一个基类===》实现基类方法（子类通过不同的方法）===》定义一个工厂类（生成子类实例）

===》开发人员调用基类方法

Proxy(代理模式)

#### （9）spring工作机制及为什么要用?

工作机制:

1.spring mvc请所有的请求都提交给DispatcherServlet,它会委托应用系统的其他模块负责负责对请求进行真正的处理工作。
2.DispatcherServlet查询一个或多个HandlerMapping,找到处理请求的Controller.
3.DispatcherServlet请请求提交到目标Controller
4.Controller进行业务逻辑处理后，会返回一个ModelAndView
5.Dispathcher查询一个或多个ViewResolver视图解析器,找到ModelAndView对象指定的视图对象
6.视图对象负责渲染返回给客户端。

为什么用：
AOP 让开发人员可以创建非行为性的关注点，称为横切关注点，并将它们插入到应用程序代码中。使用 AOP 后，公共服务   （比 如日志、持久性、事务等）就可以分解成方面并应用到域对象上，同时不会增加域对象的对象模型的复杂性。

IOC 允许创建一个可以构造对象的应用环境，然后向这些对象传递它们的协作对象。正如单词 倒置 所表明的，IOC 就像反      过来的 JNDI。没有使用一堆抽象工厂、服务定位器、单元素（singleton）和直接构造（straight construction），每一个对象都是用     其协作对象构造的。因此是由容器管理协作对象（collaborator）。

Spring即使一个AOP框架，也是一IOC容器。 Spring 最好的地方是它有助于您替换对象。有了 Spring，只要用 JavaBean 属性和配置文件加入依赖性（协作对象）。然后可以很容易地在需要时替换具有类似接口的协作对象。

### 32、一些spring与hibernate的面试题

#### （1）简述你对IoC（Inversion of Control）的理解，描述一下Spring中实现DI（Dependency Injection）的几种方式。

IoC将创建的职责从应用程序代码搬到了框架中。Spring对Setter注入和构造方法注入提供支持。（详见http://martinfowler.com/articles/injection.html，以及http: //www.redsaga.com/spring\_ref/2.0/html/beans.html#beans-factory- collaborators）

#### （2）Spring的Bean有多种作用域，

包括：

singleton、prototype、request、session、global session、application、自定义

除application（详见Spring framework 2.0 Reference的3.4节bean的作用域）

#### （3）简单描述Spring framework与Struts的不同之处，整合Spring与Struts有哪些方法，哪种最好，为什么？

Spring是完整的一站式框架，而Struts仅是MVC框架，且着重于MVC中的C。Spring有三种方式整合Struts：使用 Spring 的 ActionSupport 类整合 Struts；使用 Spring 的 DelegatingRequestProcessor 覆盖 Struts 的 RequestProcessor；将 Struts Action 管理委托给 Spring 框架，动作委托最好。（详见使用Spring 更好地处理Struts 动作）

Spring 2.0新增一种方式：AutowiringRequestProcessor。（详见http://www.javaeye.com/topic/24239）

#### （4）Hibernate中的update()和saveOrUpdate()的区别

saveOrUpdate()方法可以实现update()的功能，但会多些步骤，具体如下：

如果对象在该session中已经被持久化，不进行操作；

对象的标识符属性(identifier property)在数据库中不存在或者是个暂时的值，调用save()方法保存它；

如果session中的另一个对象有相同的标识符抛出一个异常；

以上皆不符合则调用update()更新之。

#### （5）Spring对多种ORM框架提供了很好的支持，简单描述在Spring中使用Hibernate的方法，并结合事务管理。

在context中定义DataSource，创建SessionFactoy，设置参数；DAO类继承HibernateDaoSupport，实现具体接口，从中获得HibernateTemplate进行具体操作。

在使用中如果遇到OpenSessionInView的问题，可以添加OpenSessionInViewFilter或OpenSessionInViewInterceptor。（详见Spring framework 2.0 Reference的12.2节Hibernate）

声明式事务需声明事务管理器，在context中设置指定属性，用确定和。

### 33、在spring中如何更加高效的使用JDBC

使用Spring框架提供的模板类JdbcTemplete可以是JDBC更加高效

代码如下：JdbcTemplate template = new JdbcTemplate(myDataSource);

DAO类的例子：

```java
public class StudentDaoJdbc implements StudentDao {
private JdbcTemplate jdbcTemplate;

public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
this.jdbcTemplate = jdbcTemplate;
}
more..
}
```

配置文件：

```xml
<bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
<property name="dataSource">
<ref bean="dataSource"/>
</property>
</bean>
<bean id="studentDao" class="StudentDaoJdbc">
<property name="jdbcTemplate">
<ref bean="jdbcTemplate"/>
</property>
</bean>
<bean id="courseDao" class="CourseDaoJdbc">
<property name="jdbcTemplate">
<ref bean="jdbcTemplate"/>
</property>
</bean>
```

### 34、在spring如何创建一个数据连接池

```xml
<bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource">
<property name="driver">
<value>${db.driver}</value>
</property>
<property name="url">
<value>${db.url}</value>
</property>
<property name="username">
<value>${db.username}</value>
</property>
<property name="password">
<value>${db.password}</value>
</property>
</bean>
```

### 35、在spring中如何配置一个bean来从JNDI到dataSource

```xml
<bean id="dataSource" class="org.springframework.jndi.JndiObjectFactoryBean">
<property name="jndiName"> <value>java:comp/env/jdbc/myDatasource</value>
</property>
</bean>
```

### 36、请介绍下spring中bean的作用域

在spring2.0之前bean只有2种作用域即：singleton(单例)、non-singleton（也称 prototype），Spring2.0以后，增加了session、request、global session三种专用于Web应用程序上下文的Bean。因此，默认情况下Spring2.0现在有五种类型的Bean。

&lt;bean id=&quot;role&quot; class=&quot;spring.chapter2.maryGame.Role&quot; scope=&quot;singleton&quot;/&gt;

这里的scope就是用来配置spring bean的作用域，它标识bean的作用域。

在spring2.0之前bean只有2种作用域即：singleton(单例)、non-singleton（也称 prototype），Spring2.0以后，增加了session、request、global session三种专用于Web应用程序上下文的Bean。因此，默认情况下Spring2.0现在有五种类型的Bean。当然，Spring2.0对 Bean的类型的设计进行了重构，并设计出灵活的Bean类型支持，理论上可以有无数多种类型的Bean，用户可以根据自己的需要，增加新的Bean类型，满足实际应用需求。

#### （1）singleton作用域

当一个bean的作用域设置为singleton，那么Spring IOC容器中只会存在一个共享的bean实例，并且所有对bean的请求，只要id与该bean定义相匹配，则只会返回bean的同一实例。换言之，当把一个bean定义设置为singleton作用域时，Spring IOC容器只会创建该bean定义的唯一实例。这个单一实例会被存储到单例缓存（singleton cache）中，并且所有针对该bean的后续请求和引用都将返回被缓存的对象实例，这里要注意的是singleton作用域和GOF设计模式中的单例是完全不同的，单例设计模式表示一个ClassLoader中只有一个class存在，而这里的singleton则表示一个容器对应一个bean，也就是说当一个bean被标识为singleton时候，spring的IOC容器中只会存在一个该bean。

配置实例：

```xml
<bean id="role" class="spring.chapter2.maryGame.Role" scope="singleton"/>
```

或者

```xml
<bean id="role" class="spring.chapter2.maryGame.Role" singleton="true"/>
```

#### （2）prototype

prototype作用域部署的bean，每一次请求（将其注入到另一个bean中，或者以程序的方式调用容器的getBean()方法）都会产生一个新的bean实例，相当于一个new的操作，对于prototype作用域的bean，有一点非常重要，那就是Spring不能对一个 prototype bean的整个生命周期负责，容器在初始化、配置、装饰或者是装配完一个prototype实例后，将它交给客户端，随后就对该prototype实例不闻不问了。不管何种作用域，容器都会调用所有对象的初始化生命周期回调方法，而对prototype而言，任何配置好的析构生命周期回调方法都将不会被调用。清除prototype作用域的对象并释放任何prototype bean所持有的昂贵资源，都是客户端代码的职责。（让Spring容器释放被singleton作用域bean占用资源的一种可行方式是，通过使用 bean的后置处理器，该处理器持有要被清除的bean的引用。）

配置实例：

&lt;bean id=&quot;role&quot; class=&quot;spring.chapter2.maryGame.Role&quot; scope=&quot;prototype&quot;/&gt;

或者

&lt;beanid=&quot;role&quot; class=&quot;spring.chapter2.maryGame.Role&quot; singleton=&quot;false&quot;/&gt;

#### （3）request

request表示该针对每一次HTTP请求都会产生一个新的bean，同时该bean仅在当前HTTP request内有效，配置实例：

request、session、global session使用的时候，首先要在初始化web的web.xml中做如下配置：

如果你使用的是Servlet 2.4及以上的web容器，那么你仅需要在web应用的XML声明文件web.xml中增加下述ContextListener即可：

&lt;web-app&gt;
…
&lt;listener&gt;
&lt;listener-class&gt;org.springframework.web.context.request.RequestContextListener&lt;/listener-class&gt;
&lt;/listener&gt;
…
&lt;/web-app&gt;

如果是Servlet2.4以前的web容器,那么你要使用一个javax.servlet.Filter的实现：

&lt;web-app&gt;
..
&lt;filter&gt;
&lt;filter-name&gt;requestContextFilter&lt;/filter-name&gt;
&lt;filter-class&gt;org.springframework.web.filter.RequestContextFilter&lt;/filter-class&gt;
&lt;/filter&gt;
&lt;filter-mapping&gt;
&lt;filter-name&gt;requestContextFilter&lt;/filter-name&gt;
&lt;url-pattern&gt;/\*&lt;/url-pattern&gt;
&lt;/filter-mapping&gt;
…
&lt;/web-app&gt;

接着既可以配置bean的作用域了：

#### （4）session

&lt;bean id=&quot;role&quot; class=&quot;spring.chapter2.maryGame.Role&quot; scope=&quot;request&quot;/&gt;

session

session作用域表示该针对每一次HTTP请求都会产生一个新的bean，同时该bean仅在当前HTTP session内有效，配置实例：

配置实例：

和request配置实例的前提一样，配置好web启动文件就可以如下配置：

&lt;bean id=&quot;role&quot; class=&quot;spring.chapter2.maryGame.Role&quot; scope=&quot;session&quot;/&gt;

#### （5）global session

global session作用域类似于标准的HTTP Session作用域，不过它仅仅在基于portlet的web应用中才有意义。Portlet规范定义了全局Session的概念，它被所有构成某个 portlet web应用的各种不同的portlet所共享。在global session作用域中定义的bean被限定于全局portlet Session的生命周期范围内。如果你在web中使用global session作用域来标识bean，那么，web会自动当成session类型来使用。

配置实例：

和request配置实例的前提一样，配置好web启动文件就可以如下配置：

&lt;bean id=&quot;role&quot; class=&quot;spring.chapter2.maryGame.Role&quot; scope=&quot;global session&quot;/&gt;

#### （6）自定义bean装配作用域

在spring 2.0中作用域是可以任意扩展的，你可以自定义作用域，甚至你也可以重新定义已有的作用域（但是你不能覆盖singleton和 prototype），spring的作用域由接口org.springframework.beans.factory.config.Scope来定义，自定义自己的作用域只要实现该接口即可，下面给个实例：

我们建立一个线程的scope，该scope在表示一个线程中有效，代码如下：

publicclass MyScope implements Scope …{
privatefinal ThreadLocal threadScope = new ThreadLocal() …{
protected Object initialValue() …{
returnnew HashMap();
}
};
public Object get(String name, ObjectFactory objectFactory) …{
Map scope = (Map) threadScope.get();
Object object = scope.get(name);
if(object==null) …{
object = objectFactory.getObject();
scope.put(name, object);
}
return object;
}
public Object remove(String name) …{
Map scope = (Map) threadScope.get();
return scope.remove(name);
}
publicvoid registerDestructionCallback(String name, Runnable callback) …{
}
public String getConversationId() …{
// TODO Auto-generated method stub
returnnull;
}
}

### 37、请介绍一下spring的bean的生命周期

#### （1）Bean的定义   Spring通常通过配置文件定义Bean。

如：

```xml-dtd
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns=”http://www.springframework.org/schema/beans
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance
xsi:schemaLocation=”http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-2.0.xsd">
<bean id="HelloWorld" class="com.pqf.beans.HelloWorld">
<property name="msg">
<value>HelloWorld</value>
</property>
</bean>
</beans>
```

这个配置文件就定义了一个标识为 HelloWorld 的Bean。在一个配置文档中可以定义多个Bean。

#### （2）Bean的初始化  有两种方式初始化Bean。

##### 2.1在配置文档中通过指定init-method 属性来完成

在Bean的类中实现一个初始化Bean属性的方法，如init()，如：
public class HelloWorld{
public String msg=null;
public Date date=null;

public void init() {
msg=&quot;HelloWorld&quot;;
date=new Date();
}
……
}
然后，在配置文件中设置init-mothod属性：
&lt;bean id=&quot;HelloWorld&quot; class=&quot;com.pqf.beans.HelloWorld&quot; init-mothod=&quot;init&quot; &gt;
&lt;/bean&gt;

##### 2.2实现org.springframwork.beans.factory.InitializingBean接口

Bean实现InitializingBean接口，并且增加 afterPropertiesSet() 方法：

public class HelloWorld implement InitializingBean {
public String msg=null;
public Date date=null;

public void afterPropertiesSet() {
msg=&quot;向全世界问好！&quot;;
date=new Date();
}
……
}

那么，当这个Bean的所有属性被Spring的BeanFactory设置完后，会自动调用afterPropertiesSet()方法对Bean进行初始化，于是，配置文件就不用指定 init-method属性了。

#### （3）Bean的调用  有三种方式可以得到Bean并进行调用：

##### 3.1使用BeanWrapper

HelloWorld hw=new HelloWorld();
BeanWrapper bw=new BeanWrapperImpl(hw);
bw.setPropertyvalue(&quot;msg&quot;,&quot;HelloWorld&quot;);
system.out.println(bw.getPropertyCalue(&quot;msg&quot;));

##### 3.2使用BeanFactory

InputStream is=new FileInputStream(&quot;config.xml&quot;);
XmlBeanFactory factory=new XmlBeanFactory(is);
HelloWorld hw=(HelloWorld) factory.getBean(&quot;HelloWorld&quot;);
system.out.println(hw.getMsg());

##### 3.3使用ApplicationConttext

ApplicationContext actx=new FleSystemXmlApplicationContext(&quot;config.xml&quot;);
HelloWorld hw=(HelloWorld) actx.getBean(&quot;HelloWorld&quot;);
System.out.println(hw.getMsg());

#### （4）Bean的销毁

##### 4.1使用配置文件中的destory-method 属性

与初始化属性 init-methods类似，在Bean的类中实现一个撤销Bean的方法，然后在配置文件中通过 destory-method指定，那么当bean销毁时，Spring将自动调用指定的销毁方法。

##### 4.2实现org.springframwork.bean.factory.DisposebleBean接口

如果实现了DisposebleBean接口，那么Spring将自动调用bean中的Destory方法进行销毁，所以，Bean中必须提供Destory方法。

### 38、Spring中如何获取bean

通过xml配置文件

bean配置在xml里面，spring提供多种方式读取配置文件得到ApplicationContext.

第一种方式：FileSystemXmlApplicationContext

通过程序在初始化的时候，导入Bean配置文件，然后得到Bean实例:
ApplicationContext ac = new FileSystemXmlApplicationContext(&quot;applicationContext.xml&quot;)
ac.getBean(&quot;beanName&quot;);

第二种方式：WebApplicationContextUtil

在B/S系统中,通常在web.xml初始化bean的配置文件，然后由WebAppliCationContextUtil得到ApplicationContext.例如：
ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(ServletContext sc);
ApplicationContext ctx =   WebApplicationContextUtils.getWebApplicationContext(ServletContext sc);

其中 servletContext sc 可以具体 换成 servlet.getServletContext()或者 this.getServletContext() 或者 request.getSession().getServletContext();

另外，由于spring是注入的对象放在ServletContext中的，所以可以直接在ServletContext取出WebApplicationContext 对象：

WebApplicationContext webApplicationContext = (WebApplicationContext) servletContext.getAttribute(WebApplicationContext.ROOT\_WEB\_APPLICATION\_CONTEXT\_ATTRIBUTE);

### 39、Spring框架有哪几部分组成?

Spring框架有七个模块组成组成，这7个模块(或组件)均可以单独存在，也可以与其它一个或多个模块联合使用，主要功能表现如下：

#### （1） Spring  核心容器（Core）：

提供Spring框架的基本功能。核心容器的主要组件是BeanFactory，她是工厂模式的实现。BeanFactory使用控制反转（Ioc）模式将应用程序的配置和依赖性规范与实际的应用代码程序分开。

#### （2）Spring AOP ：

通过配置管理特性，Spring AOP模块直接面向方面的编程功能集成到了Spring框架中，所以可以很容易的使Spring框架管理的任何对象支持 AOP。Spring AOP模块为基于Spring的应用程序中的对象提供了事务管理服务。通过使用Spring AOP，不用依赖于EJB组件，就可以将声明性事务管理集成到应用程序中。

#### （3）Spring ORM ：

Spring框架集成了若干ORM框架,从而提供了ORM的对象关系工具,其中包括 JDO、Hibernate、iBatis和TopLink。所有这些都遵从Spring的通用事务和DAO异常层结构。

#### （4）Spring DAO ：

JDBC DAO抽象层提供了有意义的异常层次的结构，可用该结构来管理异常处理和不同数据供应商抛出的异常错误信息。异常层次结构简化了错误处理，并且大大的降低 了需要编写的异常代码数量（例如，打开和关系连接）。Spring DAO的面向JDBC的异常遵从通用的DAO异常层结构。

#### （5）Spring WEB ：

Web上下文模块建立在上下文模块（Context）的基础之上，为基于Web服务的应用程序提供了上下文的服务。所以Spring框架支持 Jakarta Struts的集成。Web模块还简化了处理多部分请求及将请求参数绑定到域对象的工作。

#### （6）Spring上下文（Context）：

Spring上下文是一个配置文件，向Spring框架提供上下文信息。Spring上下文包括企业服务，例如 JNDI、EJB、电子邮件、国际化校验和调度功能。

#### （7）Spring MVC：

Spring的MVC框架是一个全功能的构建Web应用程序的MVC实现。通过策略接口，MVC框架变成为高度可配置的，MVC容纳的大量视图技术，包括JSP、Velocity、Tiles、iText和Pol

### 40、使用spring有什么好处?

◆Spring能有效地组织你的中间层对象,无论你是否选择使用了EJB。如果你仅仅使用了Struts或其他的包含了J2EE特有APIs的framework，你会发现Spring关注了遗留下的问题，。

◆Spring能消除在许多工程上对Singleton的过多使用。根据我的经验，这是一个主要的问题，它减少了系统的可测试性和面向对象特性。
◆Spring能消除使用各种各样格式的属性定制文件的需要,在整个应用和工程中，可通过一种 一致的方法来进行配置。曾经感到迷惑，一个特定类要查找迷幻般的属性关键字或系统属性,为此不得不读Javadoc乃至源编码吗？有了Spring，你可 很简单地看到类的JavaBean属性。倒置控制的使用(在下面讨论)帮助完成这种简化。
◆Spring能通过接口而不是类促进好的编程习惯，减少编程代价到几乎为零。
◆Spring被设计为让使用它创建的应用尽可能少的依赖于他的APIs。在Spring应用中的大多数业务对象没有依赖于Spring。
◆使用Spring构建的应用程序易于单元测试。
◆Spring能使EJB的使用成为一个实现选择,而不是应用架构的必然选择。你能选择用POJOs或local EJBs来实现业务接口，却不会影响调用代码。
◆Spring帮助你解决许多问题而无需使用EJB。Spring能提供一种EJB的替换物，它们适于许多web应用。例如,Spring能使用AOP提供声明性事务而不通过使用EJB容器，如果你仅仅需要与单个的数据库打交道，甚至不需要JTA实现。
■Spring为数据存取提供了一致的框架,不论是使用JDBC或O/R mapping产品（如Hibernate）。
Spring确实使你能通过最简单可行的解决办法解决你的问题。这些特性是有很大价值的。
总结起来，Spring有如下优点：
◆低侵入式设计，代码污染极低
◆ 独立于各种应用服务器，可以真正实现Write Once,Run Anywhere的承诺
◆Spring的DI机制降低了业务对象替换的复杂性
◆Spring并不完全依赖于Spring，开发者可自由选用Spring框架的部分或全部

### 41、什么是spring，它有什么特点?

Spring是一个轻量级的控制反转(IoC)和面向切面(AOP)的容器框架。

#### （1）◆ **轻量**

——从大小与开销两方面而言Spring都是轻量的。完整的Spring框架可以在一个大小只有1MB多的JAR文件里发布。并 且Spring所需的处理开销也是微不足道的。此外，Spring是非侵入式的：典型地，Spring应用中的对象不依赖于Spring的特定类。

#### （2）◆ **控制反转**

——Spring通过一种称作控制反转（IoC）的技术促进了松耦合。当应用IoC，一个对象依赖的其它对象会通过被动的方式传递进来，而不是这个对象自己创建或者查找依赖对象。你可以认为IoC与JNDI相反——不 是对象从容器中查找依赖，而是容器在对象初始化时不等对象请求就主动将依赖传递给它。

#### （3）◆ **面向切面**

——Spring提供了面向切面编程的丰富支持，允许通过分离应用的 业务逻辑与系统级服务（例如审计（auditing）和事务（）管理）进行内聚性的开发。应用对象只实现它们应该做的——完成业务逻辑——仅此而已。它们 并不负责（甚至是意识）其它的系统级关注点，例如日志或事务支持。

#### （4）◆ **容器**

——Spring包含并管理应用对象的配置和生命周期，在这个意义上它是 一种容器，你可以配置你的每个bean如何被创建——基于一个可配置原型（prototype），你的bean可以创建一个单独的实例或者每次需要时都生 成一个新的实例——以及它们是如何相互关联的。然而，Spring不应该被混同于传统的重量级的EJB容器，它们经常是庞大与笨重的，难以使用。

#### （5）◆ **框架**

——Spring可以将简单的组件配置、组合成为复杂的应用。在Spring中，应用对象被声明式地组合，典型地是在一个XML文件里。Spring也提供了很多基础功能（事务管理、持久化框架集成等等），将应用逻辑的开发留给了你。

## 一百十六、串行化的注意事项以及如何实现串行化

答：如果有循环引用是不可以串行化的。对象输出流的WriteObject方法和对象输入流的ReadObect 方法

## 一百十七、socket通信（tcp/udp区别及JAVA的实现方式）

TCP——传输控制协议，具有极高的可靠性，保证数据包按照顺序准确到达，但其也有着很高的额外负担。UDP——使用者数据元协议，并不能保证数据包会被成功的送达，也不保证数据包到达的顺序，但其传输速度很快。大多数我们会使用TCP，偶尔才会动用UDP，如声音讯号，即使少量遗失，也无关紧要。

## 一百十八、JAVA的事件委托机制和垃圾回收机制

java 事件委托机制的概念,一个源产生一个事件并将它送到一个或多个监听器那里。在这种方案中，监听器简单的等待，直到它收到一个事件。一旦事件被接受，监听器将处理这个事件，然后返回。垃圾回收机制垃圾收集是将分配给对象但不在使用的内存回收或释放的过程。如果一个对象没有指向它的引用或者其赋值为null,则次对象适合进行垃圾回收

## 一百十九、文件和目录（I/O）操作  如何列出某个目录下的所有文件  如何列出某个目录下的所有子目录  判断一个文件或目录是否存在  如何读写文件描述一下你最常用的编程风格。

(1) 类名首字母应该大写。字段、方法以及对象（句柄）的首字母应小写。对于所有标识符，其中包含的所有单词都应紧靠在一起，而且大写中间单词的首字母。Java包（Package）属于一种特殊情况：它们全都是小写字母，即便中间的单词亦是如此。对于域名扩展名称，如com，org，net或者edu 等，全部都应小写（这也是Java 1.1和Java 1.2的区别之一）。

(2) 为了常规用途而创建一个类时，请采取&quot;经典形式&quot;，并包含对下述元素的定义：equals()hashCode()toString()clone()（implement Cloneable）implement Serializable

(3) 对于自己创建的每一个类，都考虑置入一个main()，其中包含了用于测试那个类的代码。为使用一个项目中的类，我们没必要删除测试代码。若进行了任何形式的改动，可方便地返回测试。这些代码也可作为如何使用类的一个示例使用。

(4) 应将方法设计成简要的、功能性单元，用它描述和实现一个不连续的类接口部分。理想情况下，方法应简明扼要。若长度很大，可考虑通过某种方式将其分割成较短的几个方法。这样做也便于类内代码的重复使用（有些时候，方法必须非常大，但它们仍应只做同样的一件事情）。

(5) 设计一个类时，请设身处地为客户程序员考虑一下（类的使用方法应该是非常明确的）。然后，再设身处地为管理代码的人考虑一下（预计有可能进行哪些形式的修改，想想用什么方法可把它们变得更简单）。

(6) 使类尽可能短小精悍，而且只解决一个特定的问题。下面是对类设计的一些建议：一个复杂的开关语句：考虑采用&quot;多形&quot;机制数量众多的方法涉及到类型差别极大的操作：考虑用几个类来分别实现许多成员变量在特征上有很大的差别：考虑使用几个类

(7) 让一切东西都尽可能地&quot;私有&quot;——private。可使库的某一部分&quot;公共化&quot;（一个方法、类或者一个字段等等），就永远不能把它拿出。若强行拿出，就可能破坏其他人现有的代码，使他们不得不重新编写和设计。若只公布自己必须公布的，就可放心大胆地改变其他任何东西。在多线程环境中，隐私是特别重要的一个因素——只有private字段才能在非同步使用的情况下受到保护。

(8) 谨惕&quot;巨大对象综合症&quot;。对一些习惯于顺序编程思维、且初涉OOP领域的新手，往往喜欢先写一个顺序执行的程序，再把它嵌入一个或两个巨大的对象里。根据编程原理，对象表达的应该是应用程序的概念，而非应用程序本身。

(9) 若不得已进行一些不太雅观的编程，至少应该把那些代码置于一个类的内部。

(10) 任何时候只要发现类与类之间结合得非常紧密，就需要考虑是否采用内部类，从而改善编码及维护工作（参见第14章14.1.2小节的&quot;用内部类改进代码&quot;）。

(11) 尽可能细致地加上注释，并用javadoc注释文档语法生成自己的程序文档。

(12) 避免使用&quot;魔术数字&quot;，这些数字很难与代码很好地配合。如以后需要修改它，无疑会成为一场噩梦，因为根本不知道&quot;100&quot;到底是指&quot;数组大小&quot;还是&quot;其他全然不同的东西&quot;。所以，我们应创建一个常数，并为其使用具有说服力的描述性名称，并在整个程序中都采用常数标识符。这样可使程序更易理解以及更易维护。

(13) 涉及构建器和异常的时候，通常希望重新丢弃在构建器中捕获的任何异常——如果它造成了那个对象的创建失败。这样一来，调用者就不会以为那个对象已正确地创建，从而盲目地继续。

(14) 当客户程序员用完对象以后，若你的类要求进行任何清除工作，可考虑将清除代码置于一个良好定义的方法里，采用类似于cleanup()这样的名字，明确表明自己的用途。除此以外，可在类内放置一个boolean（布尔）标记，指出对象是否已被清除。在类的finalize()方法里，请确定对象已被清除，并已丢弃了从RuntimeException继承的一个类（如果还没有的话），从而指出一个编程错误。在采取象这样的方案之前，请确定finalize ()能够在自己的系统中工作（可能需要调用System.runFinalizersOnExit(true)，从而确保这一行为）。

(15) 在一个特定的作用域内，若一个对象必须清除（非由垃圾收集机制处理），请采用下述方法：初始化对象；若成功，则立即进入一个含有finally从句的try块，开始清除工作。

(16) 若在初始化过程中需要覆盖（取消）finalize()，请记住调用super.finalize()（若Object属于我们的直接超类，则无此必要）。在对finalize()进行覆盖的过程中，对super.finalize()的调用应属于最后一个行动，而不应是第一个行动，这样可确保在需要基础类组件的时候它们依然有效。

(17) 创建大小固定的对象集合时，请将它们传输至一个数组（若准备从一个方法里返回这个集合，更应如此操作）。这样一来，我们就可享受到数组在编译期进行类型检查的好处。此外，为使用它们，数组的接收者也许并不需要将对象&quot;造型&quot;到数组里。

(18) 尽量使用interfaces，不要使用abstract类。若已知某样东西准备成为一个基础类，那么第一个选择应是将其变成一个interface（接口）。只有在不得不使用方法定义或者成员变量的时候，才需要将其变成一个abstract（抽象）类。接口主要描述了客户希望做什么事情，而一个类则致力于（或允许）具体的实施细节。

(19) 在构建器内部，只进行那些将对象设为正确状态所需的工作。尽可能地避免调用其他方法，因为那些方法可能被其他人覆盖或取消，从而在构建过程中产生不可预知的结果（参见第7章的详细说明）。

(20) 对象不应只是简单地容纳一些数据；它们的行为也应得到良好的定义。

(21) 在现成类的基础上创建新类时，请首先选择&quot;新建&quot;或&quot;创作&quot;。只有自己的设计要求必须继承时，才应考虑这方面的问题。若在本来允许新建的场合使用了继承，则整个设计会变得没有必要地复杂。

(22) 用继承及方法覆盖来表示行为间的差异，而用字段表示状态间的区别。一个非常极端的例子是通过对不同类的继承来表示颜色，这是绝对应该避免的：应直接使用一个&quot;颜色&quot;字段。

(23) 为避免编程时遇到麻烦，请保证在自己类路径指到的任何地方，每个名字都仅对应一个类。否则，编译器可能先找到同名的另一个类，并报告出错消息。若怀疑自己碰到了类路径问题，请试试在类路径的每一个起点，搜索一下同名的.class文件。

(24) 在Java 1.1 AWT中使用事件&quot;适配器&quot;时，特别容易碰到一个陷阱。若覆盖了某个适配器方法，同时拼写方法没有特别讲究，最后的结果就是新添加一个方法，而不是覆盖现成方法。然而，由于这样做是完全合法的，所以不会从编译器或运行期系统获得任何出错提示——只不过代码的工作就变得不正常了。

(25) 用合理的设计方案消除&quot;伪功能&quot;。也就是说，假若只需要创建类的一个对象，就不要提前限制自己使用应用程序，并加上一条&quot;只生成其中一个&quot;注释。请考虑将其封装成一个&quot;独生子&quot;的形式。若在主程序里有大量散乱的代码，用于创建自己的对象，请考虑采纳一种创造性的方案，将些代码封装起来。

(26) 警惕&quot;分析瘫痪&quot;。请记住，无论如何都要提前了解整个项目的状况，再去考察其中的细节。由于把握了全局，可快速认识自己未知的一些因素，防止在考察细节的时候陷入&quot;死逻辑&quot;中。

(27) 警惕&quot;过早优化&quot;。首先让它运行起来，再考虑变得更快——但只有在自己必须这样做、而且经证实在某部分代码中的确存在一个性能瓶颈的时候，才应进行优化。除非用专门的工具分析瓶颈，否则很有可能是在浪费自己的时间。性能提升的隐含代价是自己的代码变得难于理解，而且难于维护。

(28) 请记住，阅读代码的时间比写代码的时间多得多。思路清晰的设计可获得易于理解的程序，但注释、细致的解释以及一些示例往往具有不可估量的价值。无论对你自己，还是对后来的人，它们都是相当重要的。如对此仍有怀疑，那么请试想自己试图从联机Java文档里找出有用信息时碰到的挫折，这样或许能将你说服。

如果系统要使用超大整数（超过long长度范围），请你设计一个数据结构来存储这种超大型数字以及设计一种算法来实现超大整数加法运算）。public class BigInt() { public static final long maxlong = ^0; long[] ArrOne = new long[1000]; String intString=&quot;&quot;;   public int[] Arr(String s){ intString = s; for(int i=0;i&lt;ArrOne.leght;i++)

...

列出某文件夹下的所有文件；

调用系统命令实现删除文件的操作；

实现从文件中一次读出一个字符的操作；

编写了一个服务器端的程序实现在客户端输入字符然后在控制台上显示，直到输入&quot;END&quot;为止，让你写出客户端的程序；

## 一百二十、常用的数据结构有哪些？请枚举一些。

（不少于5个）链表、堆栈、二叉树、队列、图、堆，集合

## 一百二十一、谈谈你对swing mvc模式的理解？

答：Swing号称是完全按照MVC的思路来进行设计的。在设计开始前，Swing的希望能够达到的目标就包括：

模型驱动（Model-Driven）的编程方式。提供一套单一的API，但是能够支持多种视感（look-and-feel），为用户提供不同的界面。严格的说，Swing中的MVC实际上是MVC的一个变体：M-VC。Swing中只显示的定义了Model接口，而在一个UI对象中集成了视图和控制器的部分机制。View和Control比较松散的交叉组合在一起，而更多的控制逻辑是在事件监听者部分引入的。但是，这并没有妨碍在Swing中体现MVC的精髓。事实上，在Swing的开发初期，Swing确实是按照标准的MVC模式来设计的，但是很快的问题就出现了：View和Controller实际上是紧密耦合的，很难作出一个能够适应不同View的一般化的Controller来，而且，一般也没有很大的必要。

## 一百二十二、Java程序怎么优化？

答：提高JAVA的性能，一般考虑如下的四个主要方面：

(1)程序设计的方法和模式   （2）JAVA布署的环境。  （3）JAVA应用程序的实现(4) 硬件和操作系统为了提高JAVA程序的性能，需要遵循如下的六个步骤。a) 明确对性能的具体要求b) 了解当前程序的性能c) 找到程序的性能瓶颈  d) 采取适当的措施来提高性能e) 只进行某一方面的修改来提高性能f) 返回到步骤c,继续作类似的工作，一直达到要求的性能为止。

数据类型之间的转换  如何将数值型字符转换为数字（Integer，Double）  如何将数字转换为字符  如何去小数点前两位，并四舍五入

日期和时间

如何取得年月日，小时分秒

Date dat=new Date();dat.getYear();dat.getMonth();dat.getDay();dat.getHours();...

如何取得从1970年到现在的毫秒数long now=dat.getTime();

如何获取某个日期是当月的最后一天如何格式化日期

DateFormate df=DateFormate.getInstance();df.Format(dat);

## 一百二十三、int类型在java中有多少位？

（如果面试题目中有这样的问题，不是公司太牛就是公司太差）

## 一百二十四、每个类实力化时都调用父类的构造函数吗？如果是，那么都调用object类的构造函数吗


## 一百二十五、你懂得Ftp协议吗？

如果不懂请问我告诉你Ftp协议命令格式及数据包的解析方法，你能用多长时间用java基本apI搞定一个ftp客户端程序（是这样的问题主要看你个人学习能力，一般也就是一人五天的工作量，不必要害怕，一般他不会给你五天做的，就是想看一下你的自信心及对工作的理解能力）

## 一百二十六、你知道java与C的通信?吗你会用那些协议进行通信？

其实也就是问socret通信

## 一百二十七、请问java中的网络通信有那些方式，有什么区别？

String a=&quot;&quot;For limit I=0;I&lt;100000;I++)A=a+&quot;A&quot;把字符串成&quot;A&quot;连接100000次，上面方法不够好，请优化上面代码？（采用stringBuffer进行优化）

## 一百二十八、JAVA代码查错

1.

abstract class Name {

private String name;

public abstract boolean isStupidName(String name) {}

}

大侠们，这有何错误?

答案: 错。abstract method必须以分号结尾，且不带花括号。

2.

public class Something {

void doSomething () {

private String s = &quot;&quot;;

int l = s.length();

}

}

有错吗?

答案: 错。局部变量前不能放置任何访问修饰符(private，public，和protected)。final可以用来修饰局部变量

(final如同abstract和strictfp，都是非访问修饰符，strictfp只能修饰class和method而非variable)。

3.

abstract class Something {

private abstract String doSomething ();

}

这好像没什么错吧?

答案: 错。abstract的methods不能以private修饰。abstract的methods就是让子类implement(实现)具体细节的，怎么可以用private把abstract

method封锁起来呢? (同理，abstract method前不能加final)。

4.

public class Something {

public int addOne(final int x) {

return ++x;

}

}

这个比较明显。

答案: 错。int x被修饰成final，意味着x不能在addOne method中被修改。

5.

public class Something {

public static void main(String[] args) {

Other o = new Other();

new Something().addOne(o);

}

public void addOne(final Other o) {

o.i++;

}

}

class Other {

public int i;

}

和上面的很相似，都是关于final的问题，这有错吗?

答案: 正确。在addOne method中，参数o被修饰成final。如果在addOne method里我们修改了o的reference

(比如: o = new Other();)，那么如同上例这题也是错的。但这里修改的是o的member vairable

(成员变量)，而o的reference并没有改变。

6.

class Something {

int i;

public void doSomething() {

System.out.println(&quot;i = &quot; + i);

}

}

有什么错呢? 看不出来啊。

答案: 正确。输出的是&quot;i = 0&quot;。int i属於instant variable (实例变量，或叫成员变量)。instant variable有default value。int的default value是0。

7.

class Something {

final int i;

public void doSomething() {

System.out.println(&quot;i = &quot; + i);

}

}

和上面一题只有一个地方不同，就是多了一个final。这难道就错了吗?

答案: 错。final int i是个final的instant variable (实例变量，或叫成员变量)。final的instant variable没有default value，必须在constructor (构造器)结束之前被赋予一个明确的值。可以修改为&quot;final int i = 0;&quot;。

8.

public class Something {

public static void main(String[] args) {

Something s = new Something();

System.out.println(&quot;s.doSomething() returns &quot; + doSomething());

}

public String doSomething() {

return &quot;Do something ...&quot;;

}

}

看上去很完美。

答案: 错。看上去在main里call doSomething没有什么问题，毕竟两个methods都在同一个class里。但仔细看，main是static的。static method不能直接call non-static methods。可改成&quot;System.out.println(&quot;s.doSomething() returns &quot; + s.doSomething());&quot;。同理，static method不能访问non-static instant variable。

9.

此处，Something类的文件名叫OtherThing.java

class Something {

private static void main(String[] something\_to\_do) {

System.out.println(&quot;Do something ...&quot;);

}

}

这个好像很明显。

答案: 正确。从来没有人说过Java的Class名字必须和其文件名相同。但public class的名字必须和文件名相同。

10．

interface A{

int x = 0;

}

class B{

int x =1;

}

class C extends B implements A {

public void pX(){

System.out.println(x);

}

public static void main(String[] args) {

new C().pX();

}

}

答案：错误。在编译时会发生错误(错误描述不同的JVM有不同的信息，意思就是未明确的x调用，两个x都匹配（就象在同时import java.util和java.sql两个包时直接声明Date一样）。对于父类的变量,可以用super.x来明确，而接口的属性默认隐含为public static final.所以可以通过A.x来明确。

11.

interface Playable {

void play();

}

interface Bounceable {

void play();

}

interface Rollable extends Playable, Bounceable {

Ball ball = new Ball(&quot;PingPang&quot;);

}

class Ball implements Rollable {

private String name;

public String getName() {

return name;

}

public Ball(String name) {

this.name = name;

}

public void play() {

ball = new Ball(&quot;Football&quot;);

System.out.println(ball.getName());

}

}

这个错误不容易发现。

答案: 错。&quot;interface Rollable extends Playable, Bounceable&quot;没有问题。interface可继承多个interfaces，所以这里没错。问题出在interface Rollable里的&quot;Ball ball = new Ball(&quot;PingPang&quot;);&quot;。任何在interface里声明的interface variable (接口变量，也可称成员变量)，默认为public static final。也就是说&quot;Ball ball = new Ball(&quot;PingPang&quot;);&quot;实际上是&quot;public static final Ball ball = new Ball(&quot;PingPang&quot;);&quot;。在Ball类的Play()方法中，&quot;ball = new Ball(&quot;Football&quot;);&quot;改变了ball的reference，而这里的ball来自Rollable interface，Rollable interface里的ball是public static final的，final的object是不能被改变reference的。因此编译器将在&quot;ball = new Ball(&quot;Football&quot;);&quot;这里显示有错。
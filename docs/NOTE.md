### java泛型擦除、类型擦除
上限通配符 <? extends T> 限定为T和T的子类型  
下限通配符 <? super T> 限定为T和T的父类型

Context数量=Activity数量+Service数量+1(Application)

### AndroidStudio点击build

* aapt 资源打包生成R.java和Resource文件
* aidl 处理aidl文件，生成java文件
* javac 编译生成class文件
* dex 转换class为dex文件
* jarsigner 签名
* zipalign 对签名后的apk对齐处理，所有资源文件距离文件起始偏移4字节的整数倍，加快访问速度

### 对象逃逸状态
* 全局级别逃逸：一个对象可能从一个方法或者当前线程中逃逸
* 参数级别逃逸：如果一个对象被作为参数传递给另一方法，但在这个方法之外无法访问或对其他线程不可见，这个对象标记为参数级别逃逸
* 无逃逸状态

###  双亲委派
> 虽然有“父子”关系，但实际上并不是继承关系，而是通过组合方式实现

分层：
1. 根加载器BootStrap ClassLoader（通过getParent获取为null）
2. 扩展类加载器 ExtClassLoader
3. 应用程序加载器 AppClassLoader（如无自定义加载器，这个就是默认加载器，代码一般通过它加载）

过程：  
一个类加载器收到了类加载请求，加载器并不会直接加载，而是委派个父类加载器完成，只有父类加载器无法完成时，才到子类加载器中加载。
1. 先根据findLoadedClass确定是不是已经加载过
2. 如果没有，就委派父类加载器进行加载
3. 如果父类没有加载成功，抛出ClassNotFoundException，并调用本加载器的findClass进行加载

### 加固与混淆壳dex文件包括：
1. 自定义DexClassLoader
2. ProxyApplication
	1. attachBaseContext方法：读取加密dex并解密，使用自定义DexClassLoader加载解密后的dex
	2. onCreate方法：创建正确的Application并设置给ActivityThread

Dex加解密  
![](https://tva1.sinaimg.cn/large/007S8ZIlgy1ghgwvrdoewj30ph0ilacq.jpg)
加固  
( [原dex文件] -> [加密后的dex文件] ) + [壳dex文件] = [新的classes.dex文件] -> 重打包签名 -> [加固后的apk]

启动  
[App启动] -> [解密源程序] -> [初始化自定义类加载器] -> [反射设置LoadedApk中加载器对象为自定义加载器] -> [获取源程序中Application名称] -> [反射产生正确的Application] -> [反射设置ActivityThread中Application信息] -> [源程序正常运行]

### Hook
不影响原有逻辑，以Hook点击事件为例  
1. 获取View#getListenerInfo方法  
2. 获取mListenerInfo的mOnClickListener成员变量  
3. 使用自定义View.OnClickListener包裹

注意：需先设置点击事件再hook才会生效

```java
private fun hook() {
    //拿到mListenerInfo
    val method: Method = View::class.java.getDeclaredMethod("getListenerInfo")
    method.isAccessible = true
    val mListenerInfo = method.invoke(btn_normal)
    //拿到mOnClickListener
    val field = Class.forName("android.view.View\$ListenerInfo").getDeclaredField("mOnClickListener")
    val originalListener: View.OnClickListener = field.get(mListenerInfo) as View.OnClickListener
    field.set(mListenerInfo, HookClickListener(originalListener))
}

class HookClickListener(private val original: View.OnClickListener?) : View.OnClickListener {
    override fun onClick(v: View) {
        Toast.makeText(v.context, "Hooked !", Toast.LENGTH_SHORT).show()
        original?.onClick(v)
    }
}
```

### Bitmap复用

**存储**

*  2.3.3及以前版本，像素点保存在native memory，bitmap对象保存在dalvik heap
*  3.0以后，像素点数据与bitmap数据一起保存在dalvik heap
*  8.0以后，像素点数据保存在native memory

**复用**

在Android 3.0开始引入了inBitmap设置，通过设置这个参数，在图片加载的时候可以使用之前已经创建了的Bitmap，但是需要大小一样,以便节省内存，避免再次创建一个Bitmap。在Android4.4，新增了允许inBitmap设置的图片与需要加载的图片的大小不同的情况，只要inBitmap的图片比当前需要加载的图片大就好了。

复用注意点：  

* Bitmap一定要是可变的，即inmutable设置一定为ture
* Android4.4以下的平台，须要保证inBitmap和即将要得到decode的Bitmap的尺寸规格一致
* Android4.4及其以上的平台，仅仅须要满足inBitmap的尺寸大于要decode得到的Bitmap的尺寸规格就可以


### 启动流程
#### 理论
**Launcher**

AndroidManifest.xml **android.intent.category.HOME**

**zygote**

zygote意为“受精卵“。Android是基于Linux系统的，而在Linux中，所有的进程都是由init进程直接或者是间接fork出来的，zygote进程也不例外。在Android系统里面，zygote是一个进程的名字。Android是基于Linux System的，当你的手机开机的时候，Linux的内核加载完成之后就会启动一个叫“init“的进程。在Linux System里面，所有的进程都是由init进程fork出来的，我们的zygote进程也不例外。每个APP都是：

> 一个单独的dalvik虚拟机  
> 一个单独的进程

所以当系统里面的第一个zygote进程运行之后，在这之后再开启App，就相当于开启一个新的进程。而为了实现资源共用和更快的启动速度，Android系统开启新进程的方式，是通过fork第一个zygote进程实现的。所以说，除了第一个zygote进程，其他应用所在的进程都是zygote的子进程。这下你明白为什么这个进程叫“受精卵”了吧？因为就像是一个受精卵一样，它能快速的分裂，并且产生遗传物质一样的细胞！

**SystemServer**

SystemServer也是一个进程，而且是由zygote进程fork出来的。
知道了SystemServer的本质，我们对它就不算太陌生了，这个进程是Android Framework里面两大非常重要的进程之一,另外一个进程就是上面的zygote进程。为什么说SystemServer非常重要呢？因为系统里面重要的服务都是在这个进程里面开启的，比如ActivityManagerService、PackageManagerService、WindowManagerService等等。

**ActivityManagerService**

AMS是Android中最核心的服务之一，主要负责系统中四大组件的启动、切换、调度及应用进程的管理和调度等工作，其职责与操作系统中的进程管理和调度模块相类似，因此它在Android中非常重要，它本身也是一个Binder的实现类。
ActivityManagerService进行初始化的时机很明确，就是在SystemServer进程开启的时候，就会初始化。

**Instrumentation和ActivityThread**

每个Activity都持有Instrumentation对象的一个引用，但是整个进程只会存在一个Instrumentation对象。  
Instrumentation这个类里面的方法大多数和Application和Activity有关，这个类就是完成对Application和Activity初始化和生命周期的工具类。Instrumentation这个类很重要，对Activity生命周期方法的调用根本就离不开他，他可以说是一个大管家。  
ActivityThread，就是UI线程，应用的入口类，通过调用main方法，开启消息循环队列。App和AMS是通过Binder传递信息的，那么ActivityThread就是专门与AMS的外交工作的。

**流程图**
![](https://tva1.sinaimg.cn/large/007S8ZIlgy1ghp0uy77o9j30ic0e30tw.jpg)

1. 点击桌面App图标，Launcher进程采用Binder IPC向system_server进程发起startActivity请求；
2. system_server进程接收到请求后，向zygote进程发送创建进程的请求；
3. Zygote进程fork出新的子进程，即App进程；
4. App进程，通过Binder IPC向sytem_server进程发起attachApplication请求；
5. AMS向ApplicationThreadProxy发送realStartActivityLocked请求。
6. system_server进程在收到请求后，进行一系列准备工作后，再通过binder IPC向App进程发送scheduleLaunchActivity请求；
7. App进程的binder线程（ApplicationThread）在收到请求后，通过handler向主线程发送LAUNCH_ACTIVITY消息；
8. 主线程在收到Message后，通过调用handleLaunchActivity创建目标Activity，并回调Activity.onCreate()等方法。
9. 到此，App便正式启动，开始进入Activity生命周期，执行完onCreate/onStart/onResume方法，UI渲染结束后便可以看到App的主界面。

### LeakCanary原理
![](https://tva1.sinaimg.cn/large/007S8ZIlgy1ghqb8ma6h9j30jp0bgdgi.jpg)

### 电量优化
* **监听手机充电状态**  
得到充电状态信息之后，我们可以有针对性的对部分代码做优化。比如我们可以判断只有当前手机为 AC 充电状态时 才去执行一些非常耗电的操作。可以通过下面的方法判断手机当前的充电状态
![](https://tva1.sinaimg.cn/large/007S8ZIlgy1ghqeev4e2mj30xc07fdhl.jpg)

* **屏幕唤醒**  
当 Android 设备空闲时，屏幕会变暗，然后关闭屏幕，最后会停止 CPU 的运行，这样可以防 止电池电量掉的快。但有些时候我们需要改变 Android 系统默认的这种状态:比如玩游戏时我 们需要保持屏幕常亮，比如一些下载操作不需要屏幕常亮但需要 CPU 一直运行直到任务完成。  
保持屏幕常亮比较好的方式是在 Activity 中使用 FLAG_KEEP_SCREEN_ON 的 Flag，这个方法的好处是不像唤醒锁(wake locks)，需要一些特定的权限(permission)。并且能 正确管理不同 app 之间的切换，不用担心无用资源的释放问题。
![](https://tva1.sinaimg.cn/large/007S8ZIlgy1ghqet88wwij30xc03y3zb.jpg)
另一个方式是在布局文件中使用 android:keepScreenOn 属性:
![](https://tva1.sinaimg.cn/large/007S8ZIlgy1ghqeu7nji8j30xc0bejti.jpg)

* **WakeLock**  
wake_lock 锁主要是相对系统的休眠而言的，意思就是程序给 CPU 加了这个锁那系统就不会 休眠了，这样做的目的是为了全力配合我们程序的运行。  
	1. 添加唤醒锁权限
	![](https://tva1.sinaimg.cn/large/007S8ZIlgy1ghqglof01xj30xc01gjrl.jpg)
	2. 直接使用唤醒锁
	![](https://tva1.sinaimg.cn/large/007S8ZIlgy1ghqgm36xhgj30xc03fq3n.jpg)
	
	> **注意**：在使用该类的时候，必须保证 acquire 和 release 是成对出现的。不然当我们业务已经不需要时， 当 CPU 处于唤醒状态，这个时候就会损耗多余的电量。

* **JobScheduler**  
自 Android 5.0 发布以来，JobScheduler 已成为执行后台工作的很好的方式，其工作方式有 利于用户在适当的时机执行正确的事情。应用可以在安排作业的同时允许系统基于内存、电源 和连接情况进行优化。JobSchedule 的宗旨就是把一些不是特别紧急的任务放到更合适的时机 批量处理。这样做有两个好处:
	* 避免频繁的唤醒硬件模块，造成不必要的电量消耗
	* 避免在不合适的时间(例如低电量情况下、弱网络或者移动网络情况下的)执行过多的
任务消耗电量

* **GPS**  
**选择合适的 Location Provider**  
Android 系统支持多个 Location Provider:  
	* **GPS_PROVIDER**: GPS 定位，利用 GPS 芯片通过卫星获得自己的位置信息。定位精准度高，一般在 10 米左右， 耗电量大;但是在室内，GPS 定位基本没用。
	* **NETWORK_PROVIDER**: 网络定位，利用手机基站和 WIFI 节点的地址来大致定位位置，这种定位方式取决于服务器，
即取决于将基站或 WIF 节点信息翻译成位置信息的服务器的能力。
	* **PASSIVE_PROVIDER**: 被动定位，就是用现成的，当其他应用使用定位更新了定位信息，系统会保存下来，该应用接 收到消息后直接读取就可以了。

* **传感器**  
使用传感器，选择合适的采样率，越高的采样率类型则越费电。
	* **SENSOR_DELAY_NOMAL** (200000 微秒)
	* **SENSOR_DELAY_UI** (60000 微秒)
	* **SENSOR_DELAY_GAME** (20000 微秒)
	* **SENSOR_DELAY_FASTEST** (0 微秒)

### class文件结构
* Class文件是一组以8位字节为基础单位的的二进制流。
* 各数据项目之间没有任何分隔符
* Class文件格式采用的结构只有两种数据结构：无符号数和表。
	* **无符号数**：以u1,u2等表示1，2个字节的无符号数，无符号数可用于描述数字、索引引用、数量值、字符串值。
	* **表**：以多个无符号数或者表组成，通常以_info结尾，整个Class文件就是一张表。

![](https://tva1.sinaimg.cn/large/007S8ZIlgy1ghqjkz7tenj30xc0o243g.jpg)

* **魔数**：Class文件开始四个字节是魔数，用于验证该文件是否能被虚拟机执行
* **版本号**：魔数后四个字节则是Class文件的主版本号和次版本号
* **常量池**：版本号后面紧跟着的则是常量池部分。常量池不同与java中，它是由1开始计数的，常量痴表的数目是常量个数减一。第0项为保留项目，用于指示当前Class文件不需要引用任何一个常量池。常量池中主要包含两大类常量：
	* **字面量**：包括文本字符串，被声明为final的常量值等。
	* **符号引用**：包括类和接口的权限定名，字段的名称和描述符，方法的名称和描述符
* **访问标志**：常量池部分结束后则是访问标志部分，该部分包括信息：是类还是接口，是否定义为public，是否final，是否abstract等信息。
* **类索引，父类索引，接口索引**：该部分的主要信息为：该类的信息的索引，父类的信息的索引，实现接口的个数和对应接口信息的索引。（通过指向一个类型为CONSTANT_Class_info的类描述符常量，从而在对应的常量池找到相关信息）
* **字段表**：接下来的部分则是用于描述类声明的变量信息，包括变量的作用域，是否为static，是否为final等信息。
* **方法表**：方法表则是用于描述类中方法的信息，与字段表类似。
* **属性表**：属性表在字段表和方法表中都会出现，主要用于描述某些场景特有的信息。比如方法是否抛出异常，被final声明的变量的值，内部类列表等信息。

### Dex文件结构
Dex是Android平台上(Dalvik虚拟机，art虚拟机)的可执行文件，每个APK压缩包中都包含一个（或者多个MultiDex）Dex文件，Dex文件中包含了app的所有源码。

* Dex文件是一组以8位字节为基础单位的的二进制流。
* Dex文件的各数据项目之间也没有任何分隔符
* Dex文件由文件头，索引区，数据区三个部分组成

![](https://tva1.sinaimg.cn/large/007S8ZIlgy1ghqm4iengjj30dm0czwf2.jpg)

* **header：dex**文件头部，记录整个dex文件的相关属性
* **string_ids**：字符串数据索引，记录了每个字符串在数据区的偏移量
* **type_ids**：类型数据索引，记录了每个类型的字符串索引
* **proto_ids**：原型数据索引，记录了方法声明的字符串，返回类型字符串，参数列表
* **field_ids**：字段数据索引，记录了所属类，类型以及方法名
* **method_ids**：类方法索引，记录方法所属类名，方法声明以及方法名等信息
* **class_defs**：类定义数据索引，记录指定类各类信息，包括接口，超类，类数据偏移量
* **data**：数据区，保存了各个类的真实数据
* **link_data**：连接数据区













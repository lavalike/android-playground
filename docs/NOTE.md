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












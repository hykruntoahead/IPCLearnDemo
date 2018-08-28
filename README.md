# IPCLearnDemo
Android IPC 机制


一.  一般来说 使用多进程会造成如下几个方面问题：

    1.静态成员和单例模式会完全失效
    2.线程同步机制完全失效
    3.SP可靠性显著下降
    4.Application 会多次创建

二. Binder 工作机制 p55

三. AIDL

    asInterface ---> 用于将服务端的Binder对象转换成客户端所需的AIDL接口类型的对象;这种转换过程是区分进程，如果客户端和服务端位于同一进程，那么此方法返回的就是服务端的“Stub”对象本身，否则返回的是系统封装后的Stub.proxy对象
    
    
    
   

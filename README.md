# IPCLearnDemo
Android IPC 机制


一.  一般来说 使用多进程会造成如下几个方面问题：

    1.静态成员和单例模式会完全失效
    2.线程同步机制完全失效
    3.SP可靠性显著下降
    4.Application 会多次创建

二. Binder 工作机制 


    AIDL
    
    
    asInterface  用于将服务端的Binder对象转换成客户端所需的AIDL接口类型的对象;这种转换过程是区分进程，如果客户端和服务端位于同一进程，那么此方法返回的就是服务端的“Stub”对象本身，否则返回的是系统封装后的Stub.proxy对象
    asBinder  返回当前binder对象
    onTransact  运行在服务端的binder线程池中,当客户端发起跨进程请求时，远程请求会通过系统底层封装后交由此方法来处理。action:如果此方法返回false，那么客户端请求会失败，因此可以利用改特性来做权限验证
   
    注意：
    首先，当客户端发起远程请求时，由于当前线程会被挂起直至服务端进程返回数据，所以如果一个远程方法是很耗时的，那么不能在UI线程中发起此远程请求；
    其次，由于服务端的Binder方法运行在Binder的线程池中，所以Binder方法不管是否耗时都应该采用同步的方式去实现，因为它已经运行在一个线程中了。
    
    Binder两个重要方法linkToDeath 和 unlinkToDeath
   
三  Android中的IPC
  
  1.Bundle
  
  2.使用文件共享 
    
        文件共享方式适合在对数据同步要求不高的进程之间通信，并且要妥善处理并发读写问题
        不建议在进程通信中使用Sharedprefrence
     
   3.Mesenger
        
        Messenger 是一种轻量级的IPC方案，它的底层实现时AIDL
        
        服务端进程：
            1.创建一个Service来处理客户端连接请求，同时创建一个Handler并通过它来创建一个Messenger对象
            2.在Service中返回这个Messenger对象底层的Binder
        客户端进程：
            绑定服务端Service，绑定成功后用服务端返回的IBinder对象创建一个Messenger，通过这个messenger就可以向服务器发送消息了，发送类型为Message对象。
            如果需要服务器端能够回应客户端，就和服务端一样，我们还需要创建一个Handler并创建一个新的Messenger，并把这个Messenger对象通过Message的replyTo参数传递给服务端，服务端通过这个replyTo参数就可以回应客户端
            
   4.AIDL
   
        服务端：
            首先创建一个Service用来监听客户端的连接请求，
            然后创建一个AIDL文件，将暴露给客户端的接口在这个AIDL文件中声明，
            最后在Service中实现这个AIDL接口即可
       客户端：
            首先需要绑定服务端的Service，绑定成功后，
            将服务端返回的Binder对象转成AIDL接口所属的类型，
            接着就可以调用aidl中的方法了。
       注：Binder会把客户端传递过来的对象重新转化并生成一个新对象
       
       RemoteCallBackList:
           remoteCallbackList<E extends IInterface> 是系统专门提供的用于删除进程listener的接口
           内部有一个Map保存所有AIDl回调--> 
                    ArrayMap<IBinder,Callback> mCallBacks = new ArrayMap()
           其中key和value:
                    IBinder key = listener.asBinder()
                    Callbcak value = new Callback(listener,cookie)
           内部自动实现了线程同步功能+当客户端进程终止后，它能够自动移除客户端注册的listener。
      
      Binder意外死亡重连服务方法：
            第一种：给Binder设置DeathRecipient监听，当Binder死亡时，收到binderDied回调，在该方法中重连
            第二种：在onServiceDisconnected中重连
      AIDL权限验证：
            第一种：在onBind中进行验证
            第二种：在onTransact方法中进行权限验证（还可采用PID，UID）
            
            
   5.ContentProvider
   
        ContentProvider 的底层实现同样也是Binder
        android:authorities 是ContentProvider的唯一标识
  
   6.socket
   
   
         分为流式套接字和用户数据报套接字两种
         
         
 
       
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
                    

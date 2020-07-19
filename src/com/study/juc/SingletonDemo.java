package com.study.juc;

/**
* @Description:    单例模式
* @Author:         zhangl
* @CreateDate:     2020/7/16 20:53
*/

public class SingletonDemo {
    private static volatile SingletonDemo instance = null;
    /**
    * 单例模式构造方法是私有的
    * @author      作者姓名
    * @return
    */
    private SingletonDemo(){
        System.out.println(Thread.currentThread().getName()+"单例模式私有构造方法");
    }
    /**
    * 单线程：饿汉模式（需要用到对像的时候才会去创建），单线程下线程安全，多线程不安全
    * @author
    * @return
    */
   /* public static SingletonDemo getInstance(){
        if(singletonDemo==null){
            singletonDemo = new SingletonDemo();
        }
        return singletonDemo;
    }*/


    /**
    * DCL （double check lock ）双端检索机制
     * 给代码块加锁，但是还是存在线程安全问题，由于指令重排问题
     * 给代码块加锁，但是还是存在线程安全问题，由于指令重排问题
     instance = new SingletonDemo();分为以下3步完成
     1. memory=allocate() 分配对象内存空间
     2. instance(memory) 初始化对象
     3. instance = memory 设置instance指向刚分配的内存地址，此时instance!=null
     步骤2与步骤3不存在依赖关系，指令重排是允许的，所以还是会存在线程安全问题，可能会返回isntance=null
     解决方案：在instance前面加上volatile关键字
    * @author      作者姓名
    * @return
    */
      public static SingletonDemo getInstance(){
         if(instance==null){
             synchronized(SingletonDemo.class){
                 if(instance==null){
                     instance = new SingletonDemo();
                 }
             }
         }
        return instance;
    }

    public static void main(String[] args) {
//单线程下，只生成一个对象能够，打印三个true
//        System.out.println(SingletonDemo.getInstance()==SingletonDemo.getInstance());
//        System.out.println(SingletonDemo.getInstance()==SingletonDemo.getInstance());
//        System.out.println(SingletonDemo.getInstance()==SingletonDemo.getInstance());


//多线程下线程不安全
        for (int i = 0; i <10 ; i++) {
            new Thread(()->{
                SingletonDemo.getInstance();
            },"线程"+String.valueOf(i)).start();
        }

    }
}

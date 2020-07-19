package com.study.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
* @Description:    手写自旋锁
* @Author:         zhangl
* @CreateDate:     2020/7/17 15:02
*/
public class SipnLockDemo {
    /**
    * 获取锁
    * @author      作者姓名
    * @return
    */
    AtomicReference<Thread> atomicReference = new AtomicReference<>();
    void myLock(){
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName()+"\t invoked myLock()");
        while(!atomicReference.compareAndSet(null,thread));

    }
    /**
    * 释放锁
    * @author      作者姓名
    * @return
    */
    void myUnLock(){
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread,null);
        System.out.println(Thread.currentThread().getName()+"\t invoked myUnLock()");
    }
    public static void main(String[] args) {
        SipnLockDemo sipnLockDemo = new SipnLockDemo();
        new Thread(()->{
            sipnLockDemo.myLock();
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sipnLockDemo.myUnLock();
        },"A").start();

        new Thread(()->{
            sipnLockDemo.myLock();
            sipnLockDemo.myUnLock();
        },"B").start();
    }
}

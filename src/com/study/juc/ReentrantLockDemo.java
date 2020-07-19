package com.study.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
* 可重入锁 synchronized 与reentrantLock 是典型的可重入锁
* @author      作者姓名
* @return
*/
class Phone implements  Runnable{
    public synchronized void sendMS(){
        System.out.println(Thread.currentThread().getName()+"\t invoked sendMS()");
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sendEmail();
    }
    public synchronized  void sendEmail(){
        System.out.println(Thread.currentThread().getName()+"\t invoked sendEmail()");
    }
   //======lOCK======
    Lock lock = new ReentrantLock();

    void get() {
        lock.lock();
        try{
            System.out.println(Thread.currentThread().getName()+"\t invoked get()");
            set();
        } finally{
            lock.unlock();
        }
    }
    void set(){
        lock.lock();
        try{
            System.out.println(Thread.currentThread().getName()+"\t invoked set()");
        } finally{
            lock.unlock();
        }
    }

    @Override
    public void run() {
        get();
    }
}

public class ReentrantLockDemo {
    public static void main(String[] args) throws InterruptedException {
        Phone phone = new Phone();
        new Thread(()->{
            phone.sendMS();;
        },"t1").start();
        new Thread(()->{
            phone.sendEmail();
        },"t2").start();
        TimeUnit.SECONDS.sleep(2);
        Thread t3 = new Thread(phone);
        Thread t4 = new Thread(phone);
        t3.start();
        t4.start();
    }
}

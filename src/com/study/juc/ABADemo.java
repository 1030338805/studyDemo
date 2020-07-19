package com.study.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
* @Description:    ABA问题demo
* @Author:         zhangl
* @CreateDate:     2020/7/17 9:47
*/
public class ABADemo {

    static AtomicReference<Integer> atomicReference = new AtomicReference<>(100);
    static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100,1);
    public static void main(String[] args) {
        System.out.println("======ABA问题演示======");
        new Thread(()->{
            atomicReference.compareAndSet(100,101);
        },"t1").start();
        new Thread(()->{
            atomicReference.compareAndSet(101,100);
        },"t2").start();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("======以下是ABA解决======");
        new Thread(()->{
            int stamp=atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName()+"\t"+"第一次版本号"+stamp);
            try {
                TimeUnit.SECONDS.sleep(1);//此处睡眠1s的作用是让t4线程获取与t3线程相同的值
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            atomicStampedReference.compareAndSet(100,101,stamp,stamp+1);
            System.out.println(Thread.currentThread().getName()+"\t"+"第二次版本号"+atomicStampedReference.getStamp());
            atomicStampedReference.compareAndSet(101,100,atomicStampedReference.getStamp(),atomicStampedReference.getStamp()+1);
            System.out.println(Thread.currentThread().getName()+"\t"+"第三次版本号"+atomicStampedReference.getStamp());
        },"t3").start();

        new Thread(()->{
            int stamp=atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName()+"\t"+"第一次版本号"+stamp);
            try {
                TimeUnit.SECONDS.sleep(3);//此处睡眠13s的作用是让t3线程先执行完，演示ABA问题
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            atomicStampedReference.compareAndSet(100,2020,stamp,stamp+1);
            System.out.println(Thread.currentThread().getName()+"\t"+"第二次版本号"+atomicStampedReference.getStamp());
            System.out.println(Thread.currentThread().getName()+"\t"+"当前最新的值为"+atomicStampedReference.getReference());
            atomicStampedReference.compareAndSet(100,2020,atomicStampedReference.getStamp(),atomicStampedReference.getStamp()+1);
            System.out.println(Thread.currentThread().getName()+"\t"+"加上最新的版本号后-当前最新的值为"+atomicStampedReference.getReference());

        },"t4").start();

    }
}

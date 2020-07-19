package com.study.juc;

import com.study.juc.util.CountryEnum;

import java.util.concurrent.CountDownLatch;

/**
* @Description:    java类作用描述
* @Author:         zhangl
* @CreateDate:     2020/7/17 16:24
*/
public class CountDownLatchDemo {

    public static void main(String[] args) {
        closeDoor();
        unite();
    }

    static void closeDoor(){
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 0; i <6 ; i++) {
           new Thread(()->{
               System.out.println(Thread.currentThread().getName()+"\t 离开教室");
               countDownLatch.countDown();
           },String.valueOf(i)).start();
        }
        try {
            countDownLatch.await();
            System.out.println(Thread.currentThread().getName()+"\t"+"最后离开教室，锁门");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static void unite(){
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 1; i <=6 ; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"国\t 被灭");
                countDownLatch.countDown();
            }, CountryEnum.forEachRetMsg(i).getRetMsg()).start();
        }
        try {
            countDownLatch.await();
            System.out.println(Thread.currentThread().getName()+"国\t"+"统一六国");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

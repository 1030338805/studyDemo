package com.study.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
* @Description:    多线程之间按顺序调用，实现A-B-C三个线程启动，要求如下
 * AA打印5次，BB打印10次，CC打印15次
 * 紧接着
 * AA打印5次，BB打印10次，CC打印15次
 * 。。。求10轮
* @Author:         zhangl
* @CreateDate:     2020/7/17 23:30
*/
class Shareprint{
     private int startFlag; //0 AA 1 BB 2CC
     Lock lock = new ReentrantLock();
     Condition condition = null;
     Condition conditionAA =lock.newCondition();
     Condition conditionBB =lock.newCondition();
     Condition conditionCC =lock.newCondition();
     void print5(){
        try{
            lock.lock();
            while(startFlag!=0){
                conditionAA.await();
            }
            for (int i = 0; i <=5 ; i++) {
                System.out.println(Thread.currentThread().getName()+"\t 打印次数");
            }
            startFlag=1;
            conditionBB.signal();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
    }
    void print10(){
        try{
            lock.lock();
            while(startFlag!=1){
                conditionBB.await();
            }
            for (int i = 0; i <=10 ; i++) {
                System.out.println(Thread.currentThread().getName()+"\t 打印次数");
            }
            startFlag=2;
            conditionCC.signal();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
    }

    void print15(){
        try{
            lock.lock();
            while(startFlag!=2){
                conditionCC.await();
            }
            for (int i = 0; i <=15 ; i++) {
                System.out.println(Thread.currentThread().getName()+"\t 打印次数");
            }
            startFlag=0;
            conditionAA.signal();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
    }
}

public class SynAndReentrantLockDemo {
    public static void main(String[] args) {
        Shareprint shareprint = new Shareprint();
        new Thread(()->{
            for (int i = 0; i <10 ; i++) {
                shareprint.print5();
            }
        },"AA").start();
        new Thread(()->{
            for (int i = 0; i <10 ; i++) {
                shareprint.print10();
            }
        },"BB").start();
        new Thread(()->{
            for (int i = 0; i <10 ; i++) {
                shareprint.print15();
            }
        },"CC").start();
    }

}

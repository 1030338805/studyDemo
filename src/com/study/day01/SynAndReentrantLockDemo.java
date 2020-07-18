package com.study.day01;

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
    private int num; //0 AA 1 BB 2CC
    Lock lock = new ReentrantLock();
    Condition conditionAA =lock.newCondition();
    Condition conditionBB =lock.newCondition();
    Condition conditionCC =lock.newCondition();
    void print(int num){
        try{
            lock.lock();
            while(num!=0);
            for (int i = 0; i <num ; i++) {
                System.out.println(Thread.currentThread().getName()+"\t 打印次数");
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
    }
}
public class SynAndReentrantLockDemo {


    public static void main(String[] args) {
        new Thread(()->{
            for (int i = 0; i <10 ; i++) {
                printAA();
            }
        },"AA").start();
        new Thread(()->{
            for (int i = 0; i <10 ; i++) {
                printBB();
            }
        },"BB").start();
        new Thread(()->{
            for (int i = 0; i <10 ; i++) {
                printBB();
            }
        },"CC").start();
    }
    /**
    * 打印方法
    * @author      作者姓名
    * @return
    */
    static void printAA(){
        try{
            lock.lock();
            for (int i = 0; i <5 ; i++) {
                System.out.println(Thread.currentThread().getName()+"\t 打印次数");
            }
            conditionAA.await();
            conditionBB.signal();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
    }
    static void printBB(){
        try{
            lock.lock();
            for (int i = 0; i <10 ; i++) {
                System.out.println(Thread.currentThread().getName()+"\t 打印次数");
            }
            conditionBB.await();
            conditionCC.signal();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
    }
    static void printCC(){
        try{
            lock.lock();
            for (int i = 0; i <15 ; i++) {
                System.out.println(Thread.currentThread().getName()+"\t 打印次数");
            }
            conditionCC.await();
            conditionAA.signal();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
    }
}

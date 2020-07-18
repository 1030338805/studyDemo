package com.study.day01;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.sql.Time;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
* @Description:    题目：初始值为0的变量，两个线程交替操作，一个加1一个减1，来5轮
 * 1.线程  操作（方法） 资源类
 * 2.判断  干活  通知
 * 3.防止虚假互相唤醒机制
* @Author:         zhangl
* @CreateDate:     2020/7/17 20:21
*/
public class ProConsumer_TraditionDemo {
    public static void main(String[] args) {
        ShareData shareData = new ShareData();
        new Thread(()->{
            for (int i = 0; i <5; i++) {
                shareData.increase();
            }
        },"t1").start();

        new Thread(()->{
            for (int i = 0; i <5; i++) {
                shareData.decrease();
            }
        },"t2").start();

        new Thread(()->{
            for (int i = 0; i <5; i++) {
                shareData.increase();
            }
        },"t3").start();

        new Thread(()->{
            for (int i = 0; i <5; i++) {
                shareData.decrease();
            }
        },"t4").start();

    }
}

/**
* @Description:    线程操作资源类
* @Author:         zhangl
* @CreateDate:     2020/7/17 20:22
*/
class ShareData{
    private int num;
    private Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();
    public void increase(){
        try{
            lock.lock();
            while(num>0){ //不能用if作为条件判断，要放在循环里面,多线程会有问题
                condition.await();
            }
            num++;
            System.out.println(Thread.currentThread().getName()+"\t"+num);
            condition.signalAll();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
    }
    public void decrease(){
        try{
            lock.lock();
            while(num==0){ //不能用if作为条件判断，要放在循环里面
                //1。等待，不能生产
                condition.await();
            }
            //2。干活
            num--;
            System.out.println(Thread.currentThread().getName()+"\t"+num);
            //3.通知唤醒线程
            condition.signalAll();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
    }
}
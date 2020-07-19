package com.study.juc;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
* @Description:    java类作用描述
 * 手写生产者，消费者模式
 * volatile/CAS/atomicInteger/BlockQueue/线程交互，原子引用
* @Author:         zhangl
* @CreateDate:     2020/7/18 11:27
*/
class MyResource{
    private volatile boolean flag=true;   //生产与消费开关  ，默认打开
    private AtomicInteger atomicInteger= new AtomicInteger();
    private BlockingQueue<String> blockingQueue;

    public MyResource(BlockingQueue blockingQueue) {
        this.blockingQueue=blockingQueue;
    }
    /**
    * 生产
    * @author      作者姓名
    * @return
    */
    public void myProd(){
        String data = null;
        boolean result;
        while(flag){  //多线程环境下要用while
            try {
                data = atomicInteger.incrementAndGet()+"";
                result=blockingQueue.offer(data,1, TimeUnit.SECONDS); //阻塞队列已满，如果超过1s，则放弃
                if(result){
                    System.out.println(Thread.currentThread().getName()+"\t 插入队列成功"+data);
                }else{
                    System.out.println(Thread.currentThread().getName()+"\t 插入队列失败");
                }
                TimeUnit.SECONDS.sleep(1);  //每秒钟生产一个
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void myConsumer(){
        String msg = null;
        while(flag){
            try {
                msg=blockingQueue.poll(2,TimeUnit.SECONDS); //生产者没有生产，超过2s拿不到，则放弃
                System.out.println(Thread.currentThread().getName()+"\t 消费者消费了"+msg);
                if(msg==null || msg.equalsIgnoreCase("")){
                    //flag = true;
                    System.out.println(Thread.currentThread().getName()+"\t 超过2s没有取到值，消费退出");
                    //return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void stop(){
        this.flag = false;
    }
}

public class ProdConsumer_BlockingQueue {
    public static void main(String[] args) {
        MyResource myResource = new MyResource(new ArrayBlockingQueue(10));
        new Thread(()->{
            myResource.myProd();
        },"prod").start();
        new Thread(()->{
            myResource.myConsumer();
        },"consumer").start();
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+"\t 大Boss叫停生产");
        myResource.stop();
    }
}

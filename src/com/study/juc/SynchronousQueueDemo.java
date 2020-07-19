package com.study.juc;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
* @Description:    同步队列
 * 只有被消费了才可以被继续生产，否则一直阻塞
* @Author:         zhangl
* @CreateDate:     2020/7/17 20:05
*/
public class SynchronousQueueDemo {
    public static void main(String[] args) {
        BlockingQueue<String> blockingQueue = new SynchronousQueue();
        new Thread(()->{
            try {
                blockingQueue.put("a");
                blockingQueue.put("b");
                blockingQueue.put("c");
                blockingQueue.put("d");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"生产者线程AAA").start();
        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println(blockingQueue.take());
                TimeUnit.SECONDS.sleep(5);
                System.out.println(blockingQueue.take());
                TimeUnit.SECONDS.sleep(5);
                System.out.println(blockingQueue.take());
                TimeUnit.SECONDS.sleep(5);
                System.out.println(blockingQueue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"生消费者线程BBB").start();
        }
    }


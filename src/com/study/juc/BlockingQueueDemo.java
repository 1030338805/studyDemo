package com.study.juc;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
* @Description:
 * 1.队列
 * 2.阻塞队列:
 * 当阻塞队列是空的时候，从队里获取元素将会阻塞
 * 当阻塞队列是满的时候，从队里获添加元素将会阻塞
 *  2.1阻塞队列有没有好的一面
 *  2.2不得不阻塞，如何管理
* @Author:         zhangl
* @CreateDate:     2020/7/17 18:33
*/
public class BlockingQueueDemo {

    public static void main(String[] args) {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(4); //定义阻塞队列的固定长度为3
        //addAndRemove(blockingQueue);
        offerAndPoll(blockingQueue);
       // putAndTake(blockingQueue);


    }
    static void addAndRemove(BlockingQueue blockingQueue) {
        System.out.println(blockingQueue.add("a"));
        System.out.println(blockingQueue.add("b"));
        System.out.println(blockingQueue.add("c"));
        //System.out.println(blockingQueue.add("d"));  //超过阻塞队列 返回异常 java.lang.IllegalStateException: Queue full
        System.out.println(blockingQueue.element());  //检索但不删除 队列的头

        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        //System.out.println(blockingQueue.remove());  //java.util.NoSuchElementException
    }

    static void offerAndPoll(BlockingQueue blockingQueue) {
       /* System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.offer("b"));
        System.out.println(blockingQueue.offer("c"));
        System.out.println(blockingQueue.offer("d"));  //超过阻塞队列 不会报异 System.out.println(blockingQueue.element());


        System.out.println(blockingQueue.poll());   //阻塞队列先进先出
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());  //不会报异常，返回null*/

        try {
            new Thread(()->{
                try {
                    blockingQueue.offer("a",2,TimeUnit.SECONDS);
                    blockingQueue.offer("b",2,TimeUnit.SECONDS);
                    blockingQueue.offer("c",2,TimeUnit.SECONDS);
                    blockingQueue.offer("d",2,TimeUnit.SECONDS);
                    blockingQueue.offer("e",2,TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            },"").start();
            TimeUnit.SECONDS.sleep(3);
            System.out.println(blockingQueue.poll(1,TimeUnit.SECONDS));
            System.out.println(blockingQueue.poll(1,TimeUnit.SECONDS));
            System.out.println(blockingQueue.poll(1,TimeUnit.SECONDS));
            System.out.println(blockingQueue.poll(3,TimeUnit.SECONDS));
            System.out.println(blockingQueue.poll(1,TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static void putAndTake(BlockingQueue blockingQueue){
        try {
            blockingQueue.put(("a"));
            blockingQueue.put(("b"));
            blockingQueue.put(("c"));

            System.out.println (blockingQueue.take());
            blockingQueue.put(("d"));  //队列已满，则等待空间可用
            System.out.println (blockingQueue.take());
            System.out.println (blockingQueue.take());
            System.out.println (blockingQueue.take());

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

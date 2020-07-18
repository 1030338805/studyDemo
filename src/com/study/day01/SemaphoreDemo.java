package com.study.day01;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
* 模拟多台车争取有限停车位
* @author      作者姓名
* @return
*/
public class SemaphoreDemo {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);//3个停车为
        for (int i = 0; i <=6 ; i++) {  //7辆车争取3个停车位
            new Thread(()->{
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName()+"\t 占取一个停车位");
                    TimeUnit.SECONDS.sleep(3);
                    System.out.println(Thread.currentThread().getName()+"\t 停车3秒钟后离开");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally{
                    semaphore.release();
                }
            },String.valueOf(i)).start();
        }
    }
}

package com.study.day01;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
* @Description:    读写锁
* @Author:         zhangl
* @CreateDate:     2020/7/17 15:34
 * 写=独占+原子 不允许打断，不允许被分割
*/


public class ReadWriteLockDemo {
    public static void main(String[] args) {
        MyCache myCache = new MyCache();
        for (int i = 0; i <5 ; i++) {
            final int s =i;
            new Thread(()->{
                myCache.put(Thread.currentThread().getName(),s);
            },String.valueOf(i)).start();
        }

        for (int i = 0; i <5 ; i++) {
            new Thread(()->{
                myCache.get(Thread.currentThread().getName());
            },String.valueOf(i)).start();
        }
    }
}

class MyCache{
    private volatile Map<String,Object> map = new HashMap<>();
    ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    /**
    * 写操作
    * @author      作者姓名
    * @return
    */
    void put(String key,Object value){
        try{
            readWriteLock.writeLock().lock();
            System.out.println(Thread.currentThread().getName()+"\t 写入开始");
            try {
                TimeUnit.SECONDS.sleep(1);
                map.put(key,value);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"\t 写入完成");
        }finally{
            readWriteLock.writeLock().unlock();
        }
    }
    /**
    * 读操作
    * @author      作者姓名
    * @return
    */
    void get(String key){
        readWriteLock.readLock().lock();
        System.out.println(Thread.currentThread().getName()+"\t 读开始");
        try {
            TimeUnit.SECONDS.sleep(1);
            map.get(key);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally{
            readWriteLock.readLock().unlock();
        }
        System.out.println(Thread.currentThread().getName()+"\t 读完成");
    }
    /**
    * 清除缓存
    * @author      作者姓名
    * @return
    */
    void clearCache(){
        map.clear();
    }
}

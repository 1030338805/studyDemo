package com.study.juc;

import java.util.concurrent.TimeUnit;

/**
* @Description:    死锁演示
 * 死锁是指两个或两个以上的线程在执行过程中
 * 因争夺资源而造成的一种互相等待的现象
 * 若无外力干涉那它们都将无法推进下去
* @Author:         zhangl
* @CreateDate:     2020/7/18 20:24
*/
class HoldLockthread implements Runnable{

    private String lockA;
    private String lockB;
    public HoldLockthread(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }

    @Override
    public void run() {
        synchronized (lockA){
            System.out.println(Thread.currentThread().getName()+"\t自己持有"+lockA+"尝试获取"+lockB);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lockB){
                System.out.println(Thread.currentThread().getName()+"\t自己持有"+lockB+"尝试获取"+lockA+".......");
            }
        }
    }
}

public class DeadLockDemo {
    public static void main(String[] args) throws InterruptedException {
        String lockA ="lockA";
        String lockB ="lockB";
        new Thread(new HoldLockthread(lockA,lockB),"A").start();
        //TimeUnit.SECONDS.sleep(1);
        new Thread(new HoldLockthread(lockB,lockA),"B").start();
    }
}

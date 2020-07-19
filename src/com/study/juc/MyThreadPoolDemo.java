package com.study.juc;

import java.util.concurrent.*;

/**
 * @Description: 手写线程池
 * 四种使用多线程的方式
 * 1.继承Thread类
 * 2.实现Runnabl接口  无返回值
 * 3.实现Callable接口 有返回值
 * 4.使用java多线程，线程池
 * 获得使用
 * @Author: zhangl
 * @CreateDate: 2020/7/18 16:22
 */
public class MyThreadPoolDemo {

    public static void main(String[] args) {
        //System.out.println("查看系统的线程数量\t"+Runtime.getRuntime().availableProcessors());
        //手写线程池,创建核心线程数为2，最大线程数为5，空闲线程回收时间，时间单位，任务队列大小为3，拒绝策略为3的线程池
        //******什么时候开启拒绝测略？（maximunPoolSize+workQueue）<请求线程数时开启
        //******什么时候开启空余线程数？(corePoolSize+workQueue）<请求线程数时开启<=（maximunPoolSize+workQueue）
        //******什么时候只使用核心线程数？（corePoolSize+workQueue）>=请求线程数时开启


        //AbortPolicy 拒絕策略的线程最大数量不能操作（最大线程数+任务队列数）个。否则报异常 java.util.concurrent.RejectedExecutionException
        //CallerRunsPolicy 不抛弃，不报异常  将任务回调给调用者，即主线程
        //DiscardOldestPolicy 抛弃等待时间最久的任务队列
        //DiscardPolicy 抛弃超出范围的任务队列
        ExecutorService threadTool = new ThreadPoolExecutor(2,
                5,
                2,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue(4),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardPolicy());
        try {
            for (int i = 1; i <= 10; i++) {
                threadTool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadTool.shutdown();
        }
    }

    /**
     * 方创建线程池的方式
     * 使用Executors工具类创建线程池
     * 1.创建线程池不适用Executors创建线程池，用ThreadToolExcutor创建
     *
     * @return
     * @author 作者姓名
     */
    public void threadPoolInit() {
        ExecutorService threadTool = Executors.newFixedThreadPool(5); //创建固定数量的线程数
        ExecutorService threadTool1 = Executors.newSingleThreadExecutor(); //一池一线程
        ExecutorService threadTool2 = Executors.newCachedThreadPool();  //一池多线程
        //模拟10个用户来办业务，每个用户相当于外部的线程
        try {
            for (int i = 1; i <= 10; i++) {
                threadTool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadTool.shutdown();
        }
    }
}

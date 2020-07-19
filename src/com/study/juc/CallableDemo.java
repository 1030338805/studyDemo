package com.study.juc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;

public class CallableDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        MyThread myThread = new MyThread();
        RunnableFuture<String> runnableFuture = new FutureTask(myThread);
        RunnableFuture<String> runnableFuture1 = new FutureTask(myThread);
        new Thread(runnableFuture,"ss").start();
        new Thread(runnableFuture1,"bb").start();
        String begin = "hello";
        String result=runnableFuture.get(); //获取Callable的结果放在最后，没有计算完就要求取强求，会导致阻塞
        System.out.println(begin+"\t"+result);
    }
}


/**
* Callable 类似与Runnable接口
 * 有返回值
* @author      作者姓名
* @return
*/
class MyThread implements Callable<String>{

    @Override
    public String call() throws Exception {
        System.out.println(Thread.currentThread().getName()+"\t enter callable");
        return "callable";
    }
}
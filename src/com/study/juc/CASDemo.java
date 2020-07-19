package com.study.juc;

import java.util.concurrent.atomic.AtomicInteger;

/**
* @Description:    CAS(compare and swapper)比较并未交换
* @Author:         zhangl
* @CreateDate:     2020/7/16 21:54
*/
public class CASDemo {

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(5);
        atomicInteger.getAndIncrement();
        boolean result=atomicInteger.compareAndSet(5,60);
        System.out.println(result+"\t"+atomicInteger.get());
        result=atomicInteger.compareAndSet(5,40);
        System.out.println(result+"\t"+atomicInteger.get());

    }
}

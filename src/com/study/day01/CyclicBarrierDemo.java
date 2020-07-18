package com.study.day01;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
* @Description:    java类作用描述
* @Author:         zhangl
* @CreateDate:     2020/7/17 17:44
*/
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7,()->{
            System.out.println(Thread.currentThread().getName()+"\t"+"收集完七颗龙珠，召集神龙");
        });

        for (int i = 1; i <=7 ; i++) {
            final int longzhu=i;
            new Thread(()->{
                try {
                    System.out.println("收集的第"+longzhu+"颗龙珠");
                    cyclicBarrier.await();
                    TimeUnit.SECONDS.sleep(5);
                    System.out.println(Thread.currentThread().getName()+"\t 收集一颗");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            },Thread.currentThread().getName()+"\t").start();
        }
    }
}

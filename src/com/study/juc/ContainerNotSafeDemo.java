package com.study.juc;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
* @Description:    集合类线程安全问题
* @Author:         zhangl
* @CreateDate:     2020/7/17 10:45
*/
public class ContainerNotSafeDemo {

    public static void main(String[] args) {
        mapNoSafe();
        //setNoSafe();
        // new ArrayList<String>(); //ArrayList线程非安全，默认大小为10
       /* List<String> list= new CopyOnWriteArrayList<String>();
                          //new Vector<String>();
                          //Collections.synchronizedList(new ArrayList<String>());
        for (int i = 0; i <30 ; i++) {
            new Thread(()->{
                list.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(list);
            },String.valueOf(i)).start();
        }*/
        /**
         * 1.故障 java.util.ConcurrentModificationException
         *
         * 2.导致原因:并发争抢导致
         * 一个在写。另外一个在读导致
         *
         * 3.解决方案
         * Vector
         * Collections.synchronizedList(new ArrayList<String>());
         * JUC包里的CopyOnWriteArrayList
         */

    }
    /**
    * set线程不安全
    * @author      作者姓名
    * @return
    */
    static void setNoSafe(){
        //HashSet 线程不安全
        //用CopyOnWriteArraySet
        Set<String> set = new CopyOnWriteArraySet<>();
        for (int i = 0; i <30 ; i++) {
            new Thread(()->{
            set.add(UUID.randomUUID().toString().substring(0,8));
            System.out.println(set);
            },String.valueOf(i)).start();
        }
    }

    /**
     * set线程不安全
     * @author      作者姓名
     * @return
     */
    static void mapNoSafe(){
        //HashSet 线程不安全
        //用CopyOnWriteArraySet
        Map<String,String> map = new ConcurrentHashMap<>();
        for (int i = 0; i <30 ; i++) {
            new Thread(()->{
                map.put(Thread.currentThread().getName(),UUID.randomUUID().toString().substring(0,8));
                System.out.println(map);
            },String.valueOf(i)).start();
        }
    }
}

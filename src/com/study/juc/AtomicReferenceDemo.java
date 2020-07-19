package com.study.juc;

import java.util.concurrent.atomic.AtomicReference;

/**
* @Description:    原子引用
* @Author:         zhangl
* @CreateDate:     2020/7/17 7:55
*/
class User{
    String name;
    int age;
    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }
}

public class AtomicReferenceDemo {
    public static void main(String[] args) {
        User zhangsan = new User("zhangsan",5);
        User lisi =  new User("lisi",10);
        AtomicReference<User> atomicReference = new AtomicReference<User>();
        atomicReference.set(zhangsan);
        System.out.println(atomicReference.compareAndSet(zhangsan,lisi)+"\t"+atomicReference.get().name);
        System.out.println(atomicReference.compareAndSet(zhangsan,lisi)+"\t"+atomicReference.get().name);
    }
}

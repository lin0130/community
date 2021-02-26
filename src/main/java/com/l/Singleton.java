package com.l;
/*
    场景：单线程环境
    单线程的单例{
        私有构造方法
        持有自己类的实例的静态方法
        多线程场景下是不安全的
    }
 */
public class Singleton {
    //持有自己的引用
    private static Singleton instance = null;
    private Singleton(){
        //私有构造函数,防止new对象
    }

    public static Singleton getInstance(){
        if (instance == null){
            instance = new Singleton();
        }
        return instance;
    }
}

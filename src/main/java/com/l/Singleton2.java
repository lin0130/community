package com.l;
/*
    场景：多线程下线程安全的单例

    多线程下的线程安全单例
    {
        私有构造方法
        持有自己类的私有属性
        获取自己类实例的方法
        synchronized同步，线程安全的关键
    }
 */
public class Singleton2 {
    private static Singleton2 instance = null;
    private Singleton2(){
        //防止new对象
    }
    public static Singleton2 getInstance(){
        //有值直接返回不用同步
        if (instance != null)
        {
            return instance;
        }
        //同步代码块,不是同步整个方法
        synchronized (Singleton2.class)
        {
            if (instance == null)
            {
                instance = new Singleton2();
            }
        }
        return instance;
    }
}

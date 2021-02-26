package com.l;
/*
    绝对线程安全的单例
    {
        私有构造方法，枚举默认其实就是私有的
        获取自己类实例的静态方法
        内部枚举类
    }
 */
public class EnumSingleton {
    private EnumSingleton(){
        //私有构造函数,防止new对象
    }
    public static EnumSingleton getInstance(){
        return Singleton.INSTANCE.getSingleton();
    }
    //JVM层保证绝对单例
    private enum Singleton{
        INSTANCE;
        private EnumSingleton singleton;
        Singleton(){
            singleton = new EnumSingleton();
        }
        public EnumSingleton getSingleton(){
            return singleton;
        }
    }
}
/*
    单例模式有多种实现方式，但是，有三个共同的特点
    构造私有，防止外部new对象
    含持有自己类型的属性
    对外提供获取实例的静态方法

    使用场景{
        工具类，比如获取所有城市的共聚方法
        穿件对象需要消耗资源的类
        转换类设计成枚举单例类
    }
 */
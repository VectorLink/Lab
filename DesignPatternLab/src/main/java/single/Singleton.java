package single;

/**
 * 单例模式
 * @author lijun
 * @date 2022/11/12
 **/
public class Singleton {

    private  static volatile Singleton singleton;

    private static final Object lock=new Object();
    /**
     * 限制创建实例
     */
    private Singleton(){
    }

    public static  Singleton getInstance(){
       if (singleton==null){
           synchronized (lock) {
               singleton = new Singleton();
           }
       }
       return singleton;
    }
}

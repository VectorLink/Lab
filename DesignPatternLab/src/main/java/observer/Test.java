package observer;


import lombok.extern.slf4j.Slf4j;

/**
 * @author lijun
 * @date 2022/10/31
 **/
@Slf4j
public class Test {
    public static void main(String[] args) {
        Subject subject = new Subject();
        Observers observers = new Observers("小明");
        subject.addObserver(observers);

        Observers observers1 = new Observers("张三");

        subject.addObserver(observers1);

        subject.pushMessage("hello");
        log.info("观察者数量：{}",subject.countObservers());
    }
}

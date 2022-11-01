package subscribe;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lijun
 * @date 2022/10/31
 **/
@Slf4j
public class Subscriber implements  ISubscriber<String> {
    private String name;

    public Subscriber(String name) {
        this.name = name;
    }

    @Override
    public void update(ISubscriber instace, String message) {
        log.info("{},收到消息：{}",name,message);
    }
}

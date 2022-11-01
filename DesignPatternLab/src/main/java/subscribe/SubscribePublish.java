package subscribe;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

/**
 * 订阅器
 * @author lijun
 * @date 2022/10/31
 **/
public class SubscribePublish <T>{
    /**
     *订阅者集合
     */
    private final Map<String, List<ISubscriber>> subscribers=new ConcurrentHashMap<>();

    /**
     * 订阅某个topic
     *
     * @param topics
     * @param subscriber
     * @return
     */
    public void subscribe(ISubscriber subscriber, String... topics) {
        Assert.notNull(subscriber, "订阅者不能为空");
        Assert.notEmpty(topics, "topic不能为空");
        for (String topic : topics) {
            List<ISubscriber> iSubscribers = subscribers.computeIfAbsent(topic, (k) -> Collections.synchronizedList(new LinkedList<>()));
            iSubscribers.add(subscriber);
        }
    }

    /**
     * 移除订阅
     * @param subscriber
     * @param topics
     */
    public void removeSubscribe(ISubscriber subscriber, String... topics){
        Assert.notNull(subscriber, "订阅者不能为空");
        Assert.notEmpty(topics, "topic不能为空");
        for (String topic : topics) {
            List<ISubscriber> iSubscribers = subscribers.get(topic);
            if (iSubscribers.contains(subscriber)){
                iSubscribers.remove(subscriber);
            }
        }
    }

    /**
     * 发送消息
     * @param topic 主题
     * @param message 消息
     */
    public void publishMessage(String topic,T message){
        List<ISubscriber> iSubscribers = subscribers.get(topic);
        if (CollectionUtils.isEmpty(iSubscribers)){
            return;
        }
        iSubscribers.forEach(sub->{
            sub.update(sub,message);
        });
    }
}

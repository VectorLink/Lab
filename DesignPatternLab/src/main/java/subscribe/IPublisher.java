package subscribe;

/**
 * 发布者 接口
 * @author lijun
 * @date 2022/10/31
 **/
public interface IPublisher<T> {
    void publish(SubscribePublish<String> stringSubscribePublish,String topic,T message);
}

package subscribe;

public class Publisher implements IPublisher<String>{
    @Override
    public void publish(SubscribePublish<String> stringSubscribePublish, String topic, String message) {
    stringSubscribePublish.publishMessage(topic,message);
    }
}

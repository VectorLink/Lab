package subscribe;

public class Test {
    public static void main(String[] args) {
        SubscribePublish<String> subscribePublish=new SubscribePublish<>();
        Subscriber subscriber=new Subscriber("张三");
        Subscriber subscriber1=new Subscriber("李四");
        subscribePublish.subscribe(subscriber, "one","two","three");
        subscribePublish.subscribe(subscriber1,"one","two");
        Publisher publisher=new Publisher();

        publisher.publish(subscribePublish,"one","hello");
        publisher.publish(subscribePublish,"two","hello");
        publisher.publish(subscribePublish,"three","hello");
    }
}

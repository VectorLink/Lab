package subscribe;

/**
 * @author lijun
 * @date 2022/10/31
 **/
public interface ISubscriber<T> {
     void update(ISubscriber instace,T message);
}

package observer;

import lombok.extern.slf4j.Slf4j;

import java.util.Observable;

/**
 * @author lijun
 * @date 2022/10/31
 **/
@Slf4j
public class Subject extends Observable {

    public void pushMessage(String message){
        this.setChanged();
        this.notifyObservers(message);
    }
}

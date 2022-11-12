package observer;


import java.util.Observable;
import java.util.Observer;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lijun
 * @date 2022/10/31
 **/
@Slf4j
class Observers implements Observer {
    private String name;

    public Observers(String name) {
        this.name = name;
    }

    @Override
    public void update(Observable o, Object arg) {
        log.info("名称：{}，msg:{}",name,arg.toString());
    }
}

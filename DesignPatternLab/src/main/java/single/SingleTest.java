package single;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.springframework.scheduling.concurrent.ThreadPoolExecutorFactoryBean;
import org.springframework.util.StopWatch;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lijun
 * @date 2022/11/12
 **/
@Slf4j
public class SingleTest {
    public static void main(String[] args) throws InterruptedException {
        final ThreadPoolExecutorFactoryBean threadFactory = new ThreadPoolExecutorFactoryBean();
        threadFactory.setThreadNamePrefix("test");


        final ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 1,
                TimeUnit.MINUTES, new LinkedBlockingQueue<>(200),
                threadFactory, new ThreadPoolExecutor.CallerRunsPolicy());
        log.debug("线程池：{}",executor);
        CountDownLatch countDownLatch=new CountDownLatch(500);
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start("task");
        for (int i=0;i<500;i++){
            executor.submit(()->{
                log.debug("{}",Thread.currentThread().getName());
                final Singleton instance = Singleton.getInstance();
                log.debug("{}",instance);
                countDownLatch.countDown();
                log.info("{}",countDownLatch.getCount());
            });
        }
        executor.shutdown();
        if (executor.awaitTermination(2,TimeUnit.MINUTES)){
            log.debug("关闭");
            executor.shutdownNow();
        }
        stopWatch.stop();
        log.debug("{}",stopWatch.getTotalTimeSeconds());
    }
}

package htlstp.diplomarbeit.binobo.configurator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * This class configures all asynchronous tasks, wich eventually will get executed and yielded to the scheduler.
 * The maximum amount of async task is set to 20, because there is no need for more in the current state of the project.
 */
@Configuration
public class AsyncConfig {

    /**
     * Global bean, says Spring that this has to be used to instantiate the ThreadPoolExecutor
     * @return Returns the configured ThreadPoolExecutor
     */
    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(20);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("Async Process -- ");
        executor.initialize();
        return executor;
    }

}

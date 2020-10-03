package com.scheduler.jobscheduler;

import com.scheduler.AutowiringSpringBeanJobFactory;
import com.scheduler.QuartzPropertiesFactory;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@Configuration
@Component
class SchedulerFactoryImpl implements SchedulerFactory {
    private static Scheduler scheduler;

    @Bean
    @Override
    public Scheduler getScheduler() {
        if (scheduler == null) {
            initScheduler();
        }
        return scheduler;
    }

    @Bean
    @Override
    public Scheduler getScheduler(String schedName) {
        if (scheduler == null) {
            initScheduler();
        }
        return scheduler;
    }

    @Bean
    @Override
    public Collection<Scheduler> getAllSchedulers() {
        if (scheduler == null) {
            initScheduler();
        }
        ArrayList<Scheduler> schedulers = new ArrayList<>();
        schedulers.add(scheduler);
        return schedulers;
    }


    private void initScheduler() {
        try {
            SchedulerFactoryBean schedulerFactoryBean = createSchedulerFactoryBean();
            scheduler = schedulerFactoryBean.getScheduler();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    SchedulerFactoryBean createSchedulerFactoryBean() throws IOException {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setJobFactory(new AutowiringSpringBeanJobFactory());
        schedulerFactoryBean.setQuartzProperties(QuartzPropertiesFactory.create());
        return schedulerFactoryBean;
    }
}
package com.example.springbatch5study.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
public class SimpleJobConfiguration {

    @Bean
    public Job myJob(JobRepository jobRepository, Step step) {
        return new JobBuilder("myJob", jobRepository)
                .start(step)
                .build();
    }

    @Bean
    public Step myStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("myStep", jobRepository)
                .tasklet(((contribution, chunkContext) -> {
                    log.info(">>> This is step batch ~~~");
                    return null;
                }), transactionManager)
                .build();
    }
}

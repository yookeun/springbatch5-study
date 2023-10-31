package com.example.study.job;

import com.example.study.domain.OrderFinish;
import com.example.study.domain.OrderHistory;
import jakarta.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class JpaJobConfiguration {

    private final EntityManagerFactory entityManagerFactory;
    private final int chunkSize = 10;


    @Bean
    public Job jpaJob(JobRepository jobRepository, Step jpaStep) {
        return new JobBuilder("jpaJob", jobRepository)
                .start(jpaStep)
                .build();
    }

    @Bean
    @JobScope
    public Step jpaStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("jpaStep", jobRepository)
                .<OrderHistory, OrderFinish>chunk(chunkSize, transactionManager)
                .reader(jpaPagingItemReader())
                .processor(jpaProcessor())
                .writer(jpaItemWriter())
                .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<OrderHistory, OrderFinish> jpaProcessor() {
        return orderHistory -> OrderFinish.builder()
                .orderHistoryId(orderHistory.getId())
                .orderName(orderHistory.getOrderName())
                .price(orderHistory.getPrice())
                .orderStatus(orderHistory.getOrderStatus())
                .orderCreateDate(orderHistory.getCreateDate())
                .createDate(LocalDateTime.now())
                .build();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<OrderHistory> jpaPagingItemReader() {
        return new JpaPagingItemReaderBuilder<OrderHistory>()
                .name("jpaPagingItemReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT o FROM OrderHistory o "
                        + "WHERE o.orderStatus = com.example.study.domain.OrderStatus.DONE")
                .pageSize(chunkSize)
                .build();
    }

    @Bean
    @StepScope
    public JpaItemWriter<OrderFinish> jpaItemWriter() {
        return new JpaItemWriterBuilder<OrderFinish>()
                .entityManagerFactory(entityManagerFactory)
                .usePersist(true)
                .build();
    }
}

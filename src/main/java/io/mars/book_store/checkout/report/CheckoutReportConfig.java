package io.mars.book_store.checkout.report;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.time.LocalDate;

@Configuration
@RequiredArgsConstructor
public class CheckoutReportConfig {

    private final DataSource dataSource;

    private static final String REPORTS_PATH = "/var/library-reports/";
    private static final String STEP_NAME = "checkout-sql-read";
    private static final String JOB_NAME = "import-book-checkouts";

    private String query = "SELECT c.name, c.last_name, b.title, b.isbn,  ch.start, ch.end, ch.deadline \n" +
            "FROM checkout ch \n" +
            "JOIN customer c \n" +
            "ON ch.customer_id = c.id\n" +
            "JOIN book b \n" +
            "ON b.id = ch.book_id ";

    @Bean
    public ItemReader<CheckoutCSVDTO> itemReader() {

        // we decrement the base month of report to see some result for the first time
        String date = LocalDate.now().minusMonths(7).toString();
        query += " WHERE ch.start > DATE('" + date + "')";
        JdbcCursorItemReader<CheckoutCSVDTO> reader = new JdbcCursorItemReader<>();
        reader.setDataSource(dataSource);
        reader.setSql(query);
        reader.setRowMapper(new CheckoutRowMapper());
        return reader;
    }

    @Bean
    @StepScope
    public FlatFileItemWriter<CheckoutCSVDTO> writer(@Value("#{jobParameters['date']}") String fileName) {
        FlatFileItemWriter<CheckoutCSVDTO> writer = new FlatFileItemWriter<>();
        FileSystemResource resource = new FileSystemResource(REPORTS_PATH + fileName + ".csv");
        DelimitedLineAggregator<CheckoutCSVDTO> aggregator = new DelimitedLineAggregator<>();
        BeanWrapperFieldExtractor<CheckoutCSVDTO> fieldExtractor = new BeanWrapperFieldExtractor<>();
        fieldExtractor.setNames(new String[]{"fullName", "bookInfo", "duration", "start", "deadline"});
        writer.setLineAggregator(aggregator);
        writer.setResource(resource);
        return writer;
    }

    @Bean
    public Step executeStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder(STEP_NAME, jobRepository)
                .<CheckoutCSVDTO, CheckoutCSVDTO>chunk(10, transactionManager)
                .reader(itemReader())
                .writer(writer(null))
                .build();
    }

    @Bean("get-checkout-history")
    public Job proccessJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder(JOB_NAME, jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(executeStep(jobRepository, transactionManager))
                .build();
    }
}

package com.springbatchds.datasourceexample.config;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import com.springbatchds.datasourceexample.domain.Account;
import com.springbatchds.datasourceexample.repo.AccountRepository;

@Configuration
public class BatchJob {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private AccountRepository accountRepo;

    @Bean
    public Job importWordsJob(Step importStep, Step totalCountStep) {
        return jobBuilderFactory.get("importAccountJob")
                .incrementer(new RunIdIncrementer())
                .flow(importStep)
                .end()
                .build();
    }

    @Bean
    public Step importStep(ItemWriter<Account> writer, PlatformTransactionManager appTransactionManager) {
        return stepBuilderFactory.get("importStep")
                .transactionManager(appTransactionManager)
                .<Account, Account> chunk(100)
                .reader(reader())
                .writer(writer)
                .build();
    }
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Bean
    public FlatFileItemReader<Account> reader() 
    {
      //Create reader instance
      FlatFileItemReader<Account> reader = new FlatFileItemReader<Account>();
       
      //Set input file location
      reader.setResource(new FileSystemResource("C:\\Users\\Ramad\\Downloads\\datasourceexample\\datasourceexample\\src\\main\\resources\\dataSource.txt"));
       
      //Set number of lines to skips. Use it if file has header rows.
      reader.setLinesToSkip(1);   
       
      //Configure how each line will be parsed and mapped to different values
      reader.setLineMapper(new DefaultLineMapper() {
        {
          //3 columns in each row
          setLineTokenizer(new DelimitedLineTokenizer() {
            {
              setDelimiter("|");
              setNames(new String[] { "ACCOUNT_NUMBER","TRX_AMOUNT","DESCRIPTION","TRX_DATE","TRX_TIME","CUSTOMER_ID"});
            }
          });
          //Set values in Employee class
          setFieldSetMapper(new BeanWrapperFieldSetMapper<Account>() {
            {
              setTargetType(Account.class);
            }
          });
        }
      });
      return reader;
    }


    @Bean
    public ItemWriter<Account> writer(@Qualifier("appEntityManagerFactory") EntityManagerFactory appEntityManagerFactory) {
        ItemWriter<Account> writer = new ItemWriter<Account>(){
            @Override
            @Transactional
            public void write(List<? extends Account>items) {
                items.forEach(item->{
                	accountRepo.save(item);
                });
            }
        };
        return writer;
    }

}

package com.springbatchds.datasourceexample;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Scheduled;

@EnableBatchProcessing
@SpringBootApplication
public class DatasourceexampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(DatasourceexampleApplication.class, args);
	}
	

}

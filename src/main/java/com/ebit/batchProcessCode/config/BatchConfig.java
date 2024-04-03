package com.ebit.batchProcessCode.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import com.ebit.batchProcessCode.entity.Car;
import com.ebit.batchProcessCode.repo.CarRepo;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class BatchConfig {

//	@Autowired
//	private UserRepository repository;

	@Autowired
	private CarRepo repository;

	public FlatFileItemReader<Car> itemReader() {
		FlatFileItemReader<Car> itemReader = new FlatFileItemReader<Car>();
		itemReader.setResource(new FileSystemResource("src/main/resources/carprices.csv"));
		itemReader.setName("csv-reader");
		itemReader.setLinesToSkip(1);
		itemReader.setLineMapper(lineMapper());
		;
		return itemReader;

	}

	private LineMapper<Car> lineMapper() {
		DefaultLineMapper<Car> lineMapper = new DefaultLineMapper<Car>();
		DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
		tokenizer.setDelimiter(",");
		tokenizer.setNames("year","make","model","trim","body","transmission","vin","state","c_condition","odometer","color","interior","seller","mmr","sellingprice","saledate");
		tokenizer.setStrict(false);

		BeanWrapperFieldSetMapper<Car> mapper = new BeanWrapperFieldSetMapper<Car>();
		mapper.setTargetType(Car.class);

		lineMapper.setFieldSetMapper(mapper);
		lineMapper.setLineTokenizer(tokenizer);
		return lineMapper;
	}

	@Bean
	public CarProcessor processor() {
		return new CarProcessor();
	}

	@Bean
	public RepositoryItemWriter<Car> itemWriter() {
		RepositoryItemWriter<Car> writer = new RepositoryItemWriter<Car>();
		writer.setRepository(repository);
		writer.setMethodName("save");
		return writer;
	}

	@Bean
	public Step step(JobRepository jobRepository, PlatformTransactionManager manager) {
		return new StepBuilder("csv-step", jobRepository).<Car, Car>chunk(1000, manager).reader(itemReader())
				.processor(processor()).writer(itemWriter()).taskExecutor(taskexecutor()).build();
	}

	private TaskExecutor taskexecutor() {
		SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
		asyncTaskExecutor.setConcurrencyLimit(100);
		return asyncTaskExecutor;
	}

	@Bean
	public Job job(JobRepository repository, PlatformTransactionManager manager) {
		return new JobBuilder("csv-job", repository).flow(step(repository, manager)).end().build();
	}
}

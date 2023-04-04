package com.example.configuration;

import java.io.File;
import java.io.IOException;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.builder.JobBuilder.*;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.util.ResourceUtils;

import com.example.JobCompletionNotification.JobCompletionNotificationListener;
import com.example.dao.MatchInput;
import com.example.model.Match;
import com.example.processing.MatchProcessor;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

	
	
	
	private String[] FIELD_VALUES= new String[] {
			"id",
			"city",
			"date",
			"player_of_match",
			"venue",
			"neutral_venue",
			"team1",
			"team2",
			"toss_winner",
			"toss_decision",
			"winner",
			"result",
			"result_margin",
			"eliminator",
			"method",
			"umpire1",
			"umpire2",
	};
	@Autowired
	public JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public FlatFileItemReader<MatchInput> reader() {
		
	  return new FlatFileItemReaderBuilder<MatchInput>()
	    .name("matchItemReader")
	    .resource(new ClassPathResource("Matches.csv"))
	    .delimited()
	    .names(FIELD_VALUES)
	    .fieldSetMapper(new BeanWrapperFieldSetMapper<MatchInput>() {{
	      setTargetType(MatchInput.class);
	    }})
	    .build();
	}

	@Bean
	public MatchProcessor processor() {
	  return new MatchProcessor();
	}

	@Bean
	public JdbcBatchItemWriter<Match> writer(DataSource dataSource) {
	  return new JdbcBatchItemWriterBuilder<Match>()
	    .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
	    .sql("INSERT INTO match (id, city, date, playerofmatch, venue, team1, team2, tosswinner, tossdecision, match_Winner, result, result_Margin, eliminator, umpire1, umpire2) VALUES (:id, :city, :date, :playerofmatch, :venue, :team1, :team2, :tosswinner, :tossdecision, :matchWinner, :result, :resultMargin, :eliminator, :umpire1, :umpire2)")
	    .dataSource(dataSource)
	    .build();
	}
	
	
	@Bean
	public Job importUserJob(JobRepository jobRepository,JobCompletionNotificationListener listener, Step step1) {
	  return jobBuilderFactory
			  .get("impoetUserJob")
			  .incrementer(new RunIdIncrementer())
			  .listener(listener)
			  .flow(step1)
			  .end()
			  .build();
			  
	}

	@Bean
	public Step step1(JdbcBatchItemWriter<Match> writer) {
	  return  stepBuilderFactory
			  .get("step1")
			  .<MatchInput,Match>chunk(10)
			  .reader(reader())
			  .processor(processor())
			  .writer(writer)
			  .build();
	}
}



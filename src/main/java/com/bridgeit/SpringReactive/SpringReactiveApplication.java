package com.bridgeit.SpringReactive;

import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.ReactiveMongoClientFactory;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import com.bridgeit.SpringReactive.model.Employee;
import com.bridgeit.SpringReactive.repository.EmployeeRepository;
import com.mongodb.Mongo;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;

/*@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
@EnableReactiveMongoRepositories
@AutoConfigureAfter(EmbeddedMongoAutoConfiguration.class)
@ComponentScan(basePackages="com.bridgeit.SpringReactive.controller,com.bridgeit.SpringReactive.repository")*/
@SpringBootApplication

public class SpringReactiveApplication {
	
	/*CommandLineRunner employees (EmployeeRepository employeeRepository) {
		return args ->{
			employeeRepository.deleteAll()
							  .subscribe(null,null, ()->{
								 Stream.of(new Employee(UUID.randomUUID().toString(),
											"Peter", 23000L),new Employee(UUID.randomUUID().toString(),
													"Sam", 13000L),new Employee(UUID.randomUUID().toString(),
													"Ryan", 20000L),new Employee(UUID.randomUUID().toString(),
													"Chris", 53000L)
													)
													.forEach(employee -> {
											employeeRepository
													.save(employee)
													.subscribe(System.out::println);

													});
							  });
		};
		
	}*/
	public static void main(String[] args) {
		SpringApplication.run(SpringReactiveApplication.class, args);
	}
	
	/* private final Environment environment;

	    public SpringReactiveApplication(Environment environment) {
	        this.environment = environment;
	    }
		@Override
		@Bean
	    @DependsOn("embeddedMongoServer")
		public MongoClient reactiveMongoClient() {
			//int port = 27017;
	        return MongoClients.create(String.format("mongodb://localhost:27017"));
		}
		@Override
		protected String getDatabaseName() {
			return "todo";
		}
		*/
		/* public @Bean com.mongodb.MongoClient mongo() throws Exception {
		      return  (com.mongodb.MongoClient) new Mongo("localhost", 27017);
		  }

		  public @Bean MongoTemplate mongoTemplate() throws Exception {
		      return new MongoTemplate(mongo(), "todo");
		  }*/

	   
}

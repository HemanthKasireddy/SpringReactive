package com.bridgeit.SpringReactive.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.SpringReactive.model.Employee;
import com.bridgeit.SpringReactive.model.EmployeeEvent;
import com.bridgeit.SpringReactive.service.EmployeeService;
import com.bridgeit.SpringReactive.service.IEmployeeService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class EmployeeController {
	private  static Logger logger = LogManager.getLogger(EmployeeController.class);
	@Autowired
	private IEmployeeService employeeService;

	/*public EmployeeController(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}*/
	  /*public void setEmployeeService(EmployeeService employeeService) {
			logger.debug("The service of : "+employeeService);
		  this.employeeService = employeeService;
	        
	    }*/
	@RequestMapping("/a")
	public String get() {
		System.out.println("The service of : "+employeeService);

		return "hello";
	}
	@RequestMapping(value="/create",method=RequestMethod.POST)
	public Mono<Employee> createUser(@RequestBody Employee employee) {
	
		logger.debug("The service of : "+employeeService);
		Mono<Employee> emp = employeeService.create(employee);
		logger.debug("Employee Saved "+emp);

		
		return emp;

	}
	@RequestMapping("/all")
	public Flux<Employee> getAll() {
		
		Flux<Employee> emp=employeeService.getAll();
		logger.debug(" Saved Employees in service "+emp);
	
	/*	List<Employee> list1 = emp.collectList().block();
		list1.forEach(System.out::println);*/
		
		Flux<Employee> emp1=employeeService.getAll();

		System.out.println(" Saved Employees in service "+emp);
		return emp.concatWith(emp1);
	}
	@RequestMapping("/{id}")
	public Mono<Employee> getId(@PathVariable("id") final String empId) {
		System.out.println("Request Thread name is: "+Thread.currentThread().getName());

		Mono<Employee> emp = employeeService.getId(empId); 
		System.out.println("Request Thread name is: "+Thread.currentThread().getName());
		logger.debug(" Saved Employees"+emp);
		System.out.println(" Saved Employees"+emp);
		return emp;
	}


	@RequestMapping(value = "/{id}/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<EmployeeEvent> getEvents(@PathVariable("id") final String empId) {
		Flux<EmployeeEvent> event=employeeService.getEvents(empId);

		return event;

	}
	
	@RequestMapping(value = "/test", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Object> test() {
		Flux<Object> strings =employeeService.getStreamOfStrings();

		return strings;

	}
	
	@RequestMapping(value = "/future")
	public Integer future() throws InterruptedException, ExecutionException {
		System.out.println("Request thread: " + Thread.currentThread().getId());
		CompletableFuture<Integer> c1 = CompletableFuture.supplyAsync(() -> {
			try {
				return employeeService.test1();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return 0;
		});
		
		CompletableFuture<Integer> c2 = CompletableFuture.supplyAsync(() -> {
			try {
				return employeeService.test2();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return 0;
		});
		
		CompletableFuture<Integer> answerFuture = CompletableFuture.allOf(c1, c2).thenApply((a) -> {try {
			return c1.get() + c2.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		} return 0;});
		System.out.println("Request thread: " + Thread.currentThread().getId());

		return answerFuture.get();
	}
}

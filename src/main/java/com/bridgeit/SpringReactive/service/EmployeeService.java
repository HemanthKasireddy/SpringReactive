package com.bridgeit.SpringReactive.service;

import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import javax.validation.OverridesAttribute;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgeit.SpringReactive.controller.EmployeeController;
import com.bridgeit.SpringReactive.model.Employee;
import com.bridgeit.SpringReactive.model.EmployeeEvent;
import com.bridgeit.SpringReactive.repository.EmployeeRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@Service
public class EmployeeService implements IEmployeeService{
	private  static Logger logger = LogManager.getLogger(EmployeeService.class);

	@Autowired
	private EmployeeRepository employeeRepository;
  
	/*public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }*/
	/* public void setIEmployeeService(EmployeeRepository employeeRepository) {
	        this.employeeRepository = employeeRepository;
	    }*/
	@Override
	public Mono<Employee> create(Employee employee) {
		
		Mono<Employee> emp=employeeRepository.save(employee);
		logger.debug("Employee Saved "+emp);
		System.out.println("Employee Saved "+emp);
		return emp;

	}
	@Override
	public Flux<Employee> getAll() {
		//Flux<Employee> emp=employeeRepository.findAll();
		System.out.println("Before mono  operation");
	
		Mono<Employee> emp1= employeeRepository.findById("3");
		Mono<Employee> emp2= employeeRepository.findById("4");
		//Mono<Employee> emp3= employeeRepository.findById("5");
		
		Flux<Employee> flux=emp1.concatWith(emp2);
		System.out.println("Before mono iterator operation");

		List<Employee> list1 = flux.collectList().block();

	//	List<Employee> list1 = flux.collectList().block(Duration.ZERO);
		list1.forEach(System.out::println);
		System.out.println("After mono iterator operation");
		Flux<Employee> emp=employeeRepository.findAll();
		logger.debug(" Saved Employees "+emp);

	/*//	List<Employee> list2 = emp.collectList().block();
		List<Employee> list2 = emp.collectList().block(Duration.ZERO);
		list2.forEach(System.out::println);
		logger.debug(" Saved Employees "+emp);
		System.out.println(" Saved Employees "+emp);*/
		return 	emp;
		   }
	@Override
	public Mono<Employee> getId(String empId) {
		System.out.println("Service Thread name is: "+Thread.currentThread().getName());
		Mono<Employee> emp= employeeRepository.findById(empId);
		System.out.println("Service2 Thread name is: "+Thread.currentThread().getName());

		Mono<Employee> emp1= employeeRepository.findById("7");
		System.out.println("Service3 Thread name is: "+Thread.currentThread().getName());

		logger.debug("Employee Saved "+emp);
		System.out.println("Employee Saved "+emp);
		System.out.println("Employee Saved "+emp1);

		System.out.println("Service4 Thread name is: "+Thread.currentThread().getName());

		return emp;
	}
	
	@Override
	public Flux<EmployeeEvent> getEvents(String empId) {

		Flux<EmployeeEvent> event1= employeeRepository.findById(empId).flatMapMany( employee -> {

            Flux<Long> interval = Flux.interval(Duration.ofSeconds(2));

            Flux<EmployeeEvent> employeeEventFlux =
                    Flux.fromStream(
                            Stream.generate(() -> new EmployeeEvent(employee,
                                    new Date()))
                    );


            return Flux.zip(interval, employeeEventFlux)
                    .map(Tuple2::getT2);
           // return employeeEventFlux;

        });
		Flux<EmployeeEvent> event2= employeeRepository.findById("8").flatMapMany( employee -> {

            Flux<Long> interval = Flux.interval(Duration.ofSeconds(2));

            Flux<EmployeeEvent> employeeEventFlux =
                    Flux.fromStream(
                            Stream.generate(() -> new EmployeeEvent(employee,
                                    new Date()))
                    );


            return Flux.zip(interval, employeeEventFlux)
                    .map(Tuple2::getT2);
           // return employeeEventFlux;

        });
		return event1.concatWith(event2);
		
	}
	
	public List<Object> strings = Arrays.asList("Sid", "Hemanth", "Akash", "Nilesh", "Deepak", "Rana", "Sujay");
	
	@Override
	public Flux<Object> getStreamOfStrings() {
		
		Flux<Long> interval = Flux.interval(Duration.ofSeconds(2));
		
		Iterator<Object> i = strings.iterator();
		
		Flux<Object> stringFlux =
                Flux.fromStream(
                        Stream.generate(() -> {
                        	if(i.hasNext()) {
                        		return i.next();
                        	} else {
                        	    return Flux.error(new IllegalStateException());
 
							}}));
		
		return Flux.zip(interval, stringFlux).map(Tuple2::getT2);
       
	}
	@Override
	public int test1() throws InterruptedException {
		System.out.println("Test1 thread: " + Thread.currentThread().getId());
		Thread.sleep(5000);
		return 5;
	}
	@Override
	public int test2() throws InterruptedException {
		System.out.println("Test2 thread: " + Thread.currentThread().getId());
		Thread.sleep(5000);
		return 10;
	}
	

	
}

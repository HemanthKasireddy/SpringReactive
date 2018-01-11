package com.bridgeit.SpringReactive.service;

import com.bridgeit.SpringReactive.model.Employee;
import com.bridgeit.SpringReactive.model.EmployeeEvent;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
public interface IEmployeeService {
	
	public Mono<Employee> create(Employee employee);
	public Flux<Employee> getAll();
	public Mono<Employee> getId(String empId);
	public	Flux<EmployeeEvent> getEvents(String empId);
	public Flux<Object> getStreamOfStrings();
	public int test1() throws InterruptedException;
	public int test2() throws InterruptedException;

}

package com.bridgeit.SpringReactive.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.bridgeit.SpringReactive.model.Employee;

public interface EmployeeRepository extends  ReactiveMongoRepository<Employee, String>{

	
}

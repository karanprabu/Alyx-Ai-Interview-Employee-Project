package com.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dto.EmployeeDTO;
import com.dto.ProjectDTO;


@Service
public interface EmployeeService {
	
	public List<EmployeeDTO> getEmployeeDetails(int empID,Boolean flag);
	public String addEmployee(EmployeeDTO empDto) throws RuntimeException;
 	public String updateEmployee(int empID, EmployeeDTO empDto) throws RuntimeException ;
    public String deleteEmployee(int empID)throws RuntimeException; 
    public List<ProjectDTO> getProjectDetails();

}

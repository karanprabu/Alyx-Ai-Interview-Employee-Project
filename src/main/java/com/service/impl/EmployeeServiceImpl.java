package com.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dao.EmployeeDAOImpl;
import com.dto.EmployeeDTO;
import com.dto.ProjectDTO;
import com.service.EmployeeService;
 
@Component
public class EmployeeServiceImpl implements EmployeeService{
 	@Autowired
	private EmployeeDAOImpl empImpl;
	
	 
	
	public List<EmployeeDTO> getEmployeeDetails(int empID,Boolean flag)
	{		 
		List<EmployeeDTO> response= new ArrayList<EmployeeDTO>();

		response = empImpl.getEmployeeDetails(empID,flag);		

		return response;
	}
	
	public String addEmployee(EmployeeDTO empRequest) throws RuntimeException
	{
		return empImpl.addEmployee(empRequest);
		 
		
	}
	public String updateEmployee(int empID, EmployeeDTO empRequest) throws RuntimeException
	{
		 		 
		return empImpl.updateEmployee(empID,empRequest);
		 	
	}
	public String deleteEmployee(int empID) throws RuntimeException
	{
		return empImpl.deleteEmployee(empID);
	 
		
	}
	
	public List<ProjectDTO> getProjectDetails( )
	{		 
		List<ProjectDTO> response= new ArrayList<ProjectDTO>();

		response = empImpl.getProjectDetail();		

		return response;
	}
}

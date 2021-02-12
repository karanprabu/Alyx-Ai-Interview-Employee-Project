package com.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dto.EmployeeDTO;
import com.dto.ProjectDTO; 
import com.dto.ServeiceResponseDTO;
import com.service.EmployeeService;
import com.utils.RecordNotFoundException;

@RestController
@RequestMapping(value="/employee", produces = { MediaType.APPLICATION_JSON_VALUE })
@Validated
public class EmployeeController   {
	private static final Logger log = LogManager.getLogger(EmployeeController.class);
	@Autowired
	private EmployeeService empservice;


	@GetMapping("/getemployeedetails")
	public ResponseEntity<List<EmployeeDTO>> getEmployeeDetails(@RequestParam (value="employeeID" ,required = false) Integer empID )
	{
		log.info("API Call ------>getEmployeeDetails ()"+empID);
		List<EmployeeDTO> getemp= new ArrayList<>();

		if(empID == null)
		{  
			log.info("getEmployeeDetails- Get All Employess");

			empID = 0;
			getemp = empservice.getEmployeeDetails(empID,false);
			log.info("Final ------>getEmployeeDetails ()");
		}
		else if(empID ==0)
		{
			throw new RecordNotFoundException("Employee ID is not a valid input  = "+empID);	
		}
		else if (empID >0)
		{
			log.info("Get Particular Employee");
			getemp = empservice.getEmployeeDetails(empID,true);
			log.info("Final ------>getEmployeeDetails ()",getemp);
		}
		return  new ResponseEntity<List<EmployeeDTO>>(getemp, HttpStatus.OK); 

	}



	@PostMapping(path="/addemployee")
	public  ResponseEntity<ServeiceResponseDTO>   addEmployee(@Valid @RequestBody  EmployeeDTO empRequest )
	{
		ResponseEntity<ServeiceResponseDTO> servResponse = null;
		ServeiceResponseDTO	response = null;
		try
		{
			log.info("API Call ------>addEmployee ()");
			response = new ServeiceResponseDTO("Saved", Arrays.asList(empservice.addEmployee(empRequest)));
			servResponse = new ResponseEntity<ServeiceResponseDTO>(response, HttpStatus.OK);
			log.info("Final ------>addEmployee ()",response.toString());
		}		 
		catch(RuntimeException  e)
		{
			log.info("addEmployee()  Error Message",e.getMessage());
			response = new ServeiceResponseDTO("Save Failed", Arrays.asList(e.getMessage()));
			servResponse = new ResponseEntity<ServeiceResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return servResponse;

	}

	@PutMapping(path="/updateEmployee")
	public ResponseEntity<ServeiceResponseDTO>    updateEmployee(@RequestParam (value="employeeID") int empID, @Valid @RequestBody  EmployeeDTO empRequest)
	{
		ResponseEntity<ServeiceResponseDTO> servResponse = null;
		ServeiceResponseDTO	response = null;
		try
		{
			log.info("API Call ------>updateEmployee ()");

			response = new ServeiceResponseDTO("Updated", Arrays.asList(empservice.updateEmployee(empID, empRequest)));
			servResponse = new ResponseEntity<ServeiceResponseDTO>(response, HttpStatus.OK);
			log.info("Final ------>updateEmployee ()",response);
		}		 
		catch(RuntimeException  e)
		{
			log.info("updateEmployee()  Error Message",e.getMessage());
			response = new ServeiceResponseDTO("Update Failed", Arrays.asList(e.getMessage()));
			servResponse = new ResponseEntity<ServeiceResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return servResponse;

	}


	@DeleteMapping(path="/deleteEmployee")	
	public  ResponseEntity<ServeiceResponseDTO>  deleteEmployee( @RequestParam (value="employeeID") int empID)
	{
		ResponseEntity<ServeiceResponseDTO> servResponse = null;
		ServeiceResponseDTO	response = null;
		try
		{
			log.info("API Call ------>deleteEmployee ()",empID);
			response = new ServeiceResponseDTO("Deleted", Arrays.asList(empservice.deleteEmployee(empID)));		
			servResponse = new ResponseEntity<ServeiceResponseDTO>(response, HttpStatus.OK);
			log.info("Final ------>deleteEmployee ()",response);
		}		 
		catch(RuntimeException  e)
		{
			log.info("deleteEmployee()  Error Message",e.getMessage());
			response = new ServeiceResponseDTO("Delete Failed", Arrays.asList(e.getMessage()));
			servResponse = new ResponseEntity<ServeiceResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return servResponse;

	}


	@GetMapping("/getProjectDetails")
	public ResponseEntity<List<ProjectDTO>> getProjectDetails( )
	{
		log.info("API Call ------>getProjectDetails ()");
		List<ProjectDTO> getProject= new ArrayList<>();
		getProject = empservice.getProjectDetails();
		log.info("Final ------>getEmployeeDetails ()",getProject);

		return  new ResponseEntity<List<ProjectDTO>>(getProject, HttpStatus.OK); 

	}


}

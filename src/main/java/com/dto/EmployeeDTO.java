package com.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotEmpty; 
import com.fasterxml.jackson.annotation.JsonInclude;
 

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeDTO implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3606941876964456424L;
	 
 	private int employeeID;
	@NotEmpty (message="First name cannot be missing or empty")
  	private String employeeFirstName;
 
	private String employeeMiddleName;
	 
	@NotEmpty (message="Last name cannot be missing or empty")
	private String employeeLastName;
	

	
	private List<ProjectDTO> projectNameList;
	
	
	
	public int getEmployeeID() {
		return employeeID;
	}
	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}
	public String getEmployeeFirstName() {
		return employeeFirstName;
	}
	public void setEmployeeFirstName(String employeeFirstName) {
		this.employeeFirstName = employeeFirstName;
	}
	public String getEmployeeMiddleName() {
		return employeeMiddleName;
	}
	public void setEmployeeMiddleName(String employeeMiddleName) {
		this.employeeMiddleName = employeeMiddleName;
	}
	public String getEmployeeLastName() {
		return employeeLastName;
	}
	public void setEmployeeLastName(String employeeLastName) {
		this.employeeLastName = employeeLastName;
	}
	
	public List<ProjectDTO> getProjectNameList() {
		return projectNameList;
	}
	public void setProjectNameList(List<ProjectDTO> projectNameList) {
		this.projectNameList = projectNameList;
	}
	
	@Override
	public String toString() {
		System.out.println("EmployeeDTO [employeeID=" + employeeID + ", employeeFirstName=" + employeeFirstName
				+ ", employeeMiddleName=" + employeeMiddleName + ", employeeLastName=" + employeeLastName
				+ ", projectNameList=" + projectNameList + "]");
		return "EmployeeDTO [employeeID=" + employeeID + ", employeeFirstName=" + employeeFirstName
				+ ", employeeMiddleName=" + employeeMiddleName + ", employeeLastName=" + employeeLastName
				+ ", projectNameList=" + projectNameList + "]";
	}
	   
}

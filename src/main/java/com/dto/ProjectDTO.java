package com.dto;

import java.io.Serializable;

public class ProjectDTO implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4317565096513386086L;
	
	private int projectID;
	private String projectName;
	
	
	public int getProjectID() {
		return projectID;
	}
	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	@Override
	public String toString() {
		System.out.println("ProductDTO [projectID=" + projectID + ", projectName=" + projectName + "]");
		return "ProductDTO [projectID=" + projectID + ", projectName=" + projectName + "]";
	}
	
	
}

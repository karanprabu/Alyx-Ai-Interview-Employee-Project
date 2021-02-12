package com.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.dto.EmployeeDTO;
import com.dto.ProjectDTO;
import com.utils.RecordNotFoundException;

@Repository
public class EmployeeDAOImpl    {
	private static final Logger log = LogManager.getLogger(EmployeeDAOImpl.class);

	@PersistenceContext
	private EntityManager em;


	@SuppressWarnings("unchecked")
	@Transactional("transactionManager")
	public List<EmployeeDTO> getEmployeeDetails(int empID,Boolean flag)
	{
		Query q;
		List<EmployeeDTO> response= new ArrayList<EmployeeDTO>();
		try
		{
			q = em.createNativeQuery("call p_get_EmployeeDetails(:ip_employeeID,:ip_flag)", Tuple.class) ;
			q.setParameter("ip_employeeID", empID);
			q.setParameter("ip_flag", flag);
			List responseEmpList = q.getResultList();

			log.info("getEmployeeDetails()- Project Table Result Record Count",responseEmpList.size());

			if((responseEmpList != null) && (!responseEmpList.isEmpty()))
			{
				for(int i=0;i< responseEmpList.size();i++)
				{ 				
					Tuple result=(Tuple) responseEmpList.get(i);
					EmployeeDTO dto = new EmployeeDTO();
					dto.setEmployeeID((int)result.get("EmployeeID"));
					dto.setEmployeeFirstName((String) result.get("EmployeeFirstName"));
					dto.setEmployeeMiddleName((String) result.get("EmployeeMiddleName"));
					dto.setEmployeeLastName((String) result.get("EmployeeLastName"));

					/* Getting employee based project details*/
					q = em.createNativeQuery("call p_get_EmployeeProjectDetailsByID(:ip_employeeID)", Tuple.class) ;
					q.setParameter("ip_employeeID", (int)result.get("EmployeeID"));
					List responseEmpProjectList = q.getResultList();

					log.info("getEmployeeDetails()- Project Table Result Record count ",responseEmpProjectList.size());
					System.out.println("tes "+responseEmpProjectList.size());
					if((responseEmpProjectList != null) && (!responseEmpProjectList.isEmpty()))
					{   
						/* Itreating List of Employee Projects*/
						List<ProjectDTO> responseEmpProject= new ArrayList<ProjectDTO>();
						for(int j=0;j< responseEmpProjectList.size();j++)
						{
							log.info("getEmployeeDetails()- Itreating List of Employee Projects");
							ProjectDTO projectDTO = new ProjectDTO();
							Tuple resultProject=(Tuple) responseEmpProjectList.get(j);						
							projectDTO.setProjectID((int)resultProject.get("ProjectID"));
							projectDTO.setProjectName((String) resultProject.get("ProjectName"));
							responseEmpProject.add(projectDTO);
						}
						dto.setProjectNameList(responseEmpProject);
					}

					response.add(dto);
				}					 
			}
			else
			{
				log.info("addEmployee() - Custom Exception Message ");
				throw new RecordNotFoundException("No Record Found ID ="+empID);
			}
		}
		catch(Exception e)
		{
			log.info("addEmployee() - Catch Block Exception Message",e.getMessage());
			throw new RuntimeException(e.getMessage()) ; 
		}
		return response;
	}


	@Transactional("transactionManager")
	public String addEmployee(EmployeeDTO empRequest) throws RuntimeException
	{
		Query q;

		String responseStatus= "Employee Not Registered";

		try {
			/* inserting the employee records */
			q = em.createNativeQuery("call p_insert_Employee(:ip_employeeFirstName,:ip_employeeMiddleName,:ip_employeeLastName)",Tuple.class) ;
			q.setParameter("ip_employeeFirstName", empRequest.getEmployeeFirstName().toUpperCase() );
			q.setParameter("ip_employeeMiddleName", empRequest.getEmployeeMiddleName().toUpperCase() );
			q.setParameter("ip_employeeLastName", empRequest.getEmployeeLastName().toUpperCase() );

			List empresult  = q.getResultList();
			log.info("addEmployee() -Employee Result  Size", empresult);

			if((empresult != null) || (!empresult.isEmpty()))
			{ 
				Tuple result=(Tuple) empresult.get(0);	

				if(  result.get("dbMessage").equals("SUCCESS"))
				{
					/* Inserting Employee and project details and iterating data from ProjectNameList*/
					for(ProjectDTO product: empRequest.getProjectNameList()) 
					{
						q = em.createNativeQuery("call p_inser_EmployeeProject(:ip_employeeFirstName,:ip_employeeMiddleName,:ip_employeeLastName,:ip_projectName)",Tuple.class) ;

						q.setParameter("ip_employeeFirstName", empRequest.getEmployeeFirstName().toUpperCase() );
						q.setParameter("ip_employeeMiddleName", empRequest.getEmployeeMiddleName().toUpperCase() );
						q.setParameter("ip_employeeLastName", empRequest.getEmployeeLastName().toUpperCase() );
						q.setParameter("ip_projectName",product.getProjectName().toUpperCase());

						List empProjectresult= q.getResultList();
						log.info("addEmployee() - Employee Project Affected Row Count =",empProjectresult.size() );
						if( (empProjectresult != null) || (!empProjectresult.isEmpty()))
						{
							Tuple projecrResult=(Tuple) empProjectresult.get(0);

							if(projecrResult.get("dbMessage").equals("SUCCESS"))
							{								 
								responseStatus ="Employess Inserted Successfuly";
							}
							else 
							{  
								responseStatus =(String) projecrResult.get("dbMessage");
								throw new RuntimeException(responseStatus);
							}

						}
					}
				}
				else 
				{
					responseStatus= (String) result.get("dbMessage");
					throw new RuntimeException(responseStatus);
				}

			}	 


		}
		catch(Exception e)
		{
			log.info("addEmployee() - Catch Block Exception Message",e.getMessage());
			throw new RuntimeException(e.getMessage()) ; 
		}
		return responseStatus;
	}


	@Transactional("transactionManager" )
	public String updateEmployee(int empID,EmployeeDTO empRequest) throws RuntimeException
	{
		Query q;
		String responseStatus= "Employee Not Registered";
		try {
			/* Updating the employee records */
			q = em.createNativeQuery("call p_update_Employee(:ip_employeeID,:ip_employeeFirstName,:ip_employeeMiddleName,:ip_employeeLastName)",Tuple.class) ;
			q.setParameter("ip_employeeID", empID );
			q.setParameter("ip_employeeFirstName", empRequest.getEmployeeFirstName().toUpperCase() );
			q.setParameter("ip_employeeMiddleName", empRequest.getEmployeeMiddleName().toUpperCase() );
			q.setParameter("ip_employeeLastName", empRequest.getEmployeeLastName().toUpperCase() );

			List empresult  = q.getResultList();
			log.info("updateEmployee() - Employee Table updated Record Row Count", empresult);

			if((empresult != null) || (!empresult.isEmpty()))
			{ 
				Tuple result=(Tuple) empresult.get(0);	

				if(  result.get("dbMessage").equals("SUCCESS"))
				{

					/* Update Employee and project details and iterating data from ProjectNameList*/
					for(ProjectDTO product: empRequest.getProjectNameList()) 
					{
						/* Inserting and updating Employee and project details and iterating data from ProjectNameList*/
						q = em.createNativeQuery("call p_update_EmployeeProject(:ip_employeeID,:ip_projectName)",Tuple.class) ;

						q.setParameter("ip_employeeID", empID );
						q.setParameter("ip_projectName",product.getProjectName().toUpperCase());

						List empProjectresult= q.getResultList();
						log.info("updateEmployee() - Employee Project Table updated Record Row Count =",empProjectresult.size() );
						if( (empProjectresult != null) || (!empProjectresult.isEmpty()))
						{
							Tuple projecrResult=(Tuple) empProjectresult.get(0);

							if(projecrResult.get("dbMessage").equals("SUCCESS"))
							{		
								log.info("SUCCESS()" );
								responseStatus ="Employess Updated Successfuly";
							}
							else 
							{  
								log.info("error()" );
								responseStatus =(String) projecrResult.get("dbMessage");
								throw new RuntimeException(responseStatus);
							} 
						}
					}
				}
				else 
				{
					log.info("error()" );
					responseStatus= (String) result.get("dbMessage");
					throw new RuntimeException(responseStatus);
				}
			}
		}
		catch(Exception e)
		{
			log.info("updateEmployee() - Catch Block Exception Message",e.getMessage());
			throw new RuntimeException(e.getMessage()) ; 
		}


		return responseStatus;
	}

	@Transactional("transactionManager" )
	public String deleteEmployee(int empID) throws RuntimeException
	{
		Query q;
		int row=0;
		String responseStatus= "Employee Record Not Deleted";
		try
		{
			/* Deleting the employee and project records */
			q = em.createNativeQuery("call p_delete_Employee(:ip_employeeID)", Tuple.class) ;
			q.setParameter("ip_employeeID", empID );
			List empdeleteresult= q.getResultList();
			log.info("deleteEmployee() - Deleted Record Row Count =",empdeleteresult.size() );
			
			if( (empdeleteresult != null) || (!empdeleteresult.isEmpty()))
			{
				Tuple result=(Tuple) empdeleteresult.get(0);

				if(result.get("dbMessage").equals("SUCCESS"))
				{								 
					responseStatus ="Employess Deleted Successfuly";
				}
				else 
				{  
					responseStatus =(String) result.get("dbMessage");
					throw new RuntimeException(responseStatus);
				} 
			}
		}
		catch(Exception e)
		{
			log.info("deleteEmployee() - Catch Block Exception Message",e.getMessage());
			throw new RuntimeException(e.getMessage()) ; 
		}

		return responseStatus;
	}

	@Transactional("transactionManager" )
	public List<ProjectDTO> getProjectDetail()
	{
		Query q;
		List<ProjectDTO> response= new ArrayList<ProjectDTO>();
		try
		{
			q = em.createNativeQuery("call p_get_ProjectDetails()", Tuple.class) ;
			List responseProjectList = q.getResultList();
			log.info("getProjectDetail()- Project Table Result Record Count",responseProjectList.size());

			if((responseProjectList != null) && (!responseProjectList.isEmpty()))
			{
				for(int i=0;i< responseProjectList.size();i++)
				{ 			
					ProjectDTO dto = new ProjectDTO();
					Tuple result=(Tuple) responseProjectList.get(i);
					dto.setProjectID((int)result.get("projectID"));
					dto.setProjectName((String) result.get("projectName"));
					response.add(dto);
				}

			}		 

		}
		catch(Exception e)
		{
			log.info("addEmployee() - Catch Block Exception Message",e.getMessage());
			throw new RuntimeException(e.getMessage()) ; 
		}
		return response;
	}
}

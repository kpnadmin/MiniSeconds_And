package com.spring63.rest;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.client.RestTemplate;

import com.spring63.rest.controller.EmpRestURIConstants;
import com.spring63.rest.model.Employee;
import com.spring63.rest.service.IServicePerson;

public class TestEmployee {
    // SLF4J Logging
    private Logger logger = LoggerFactory.getLogger(this.getClass());

	public static final String SERVER_URI = "http://localhost:8080/SpringRestExample";
    
    private static ApplicationContext context = null;
    private static IServicePerson service = null;
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {        
        context= new ClassPathXmlApplicationContext("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml");
        service=context.getBean("serviceperson", IServicePerson.class);
    }
    
    @Test
	private static void testGetAllEmployee() {
		RestTemplate restTemplate = new RestTemplate();
		//we can't get List<Employee> because JSON convertor doesn't know the type of
		//object in the list and hence convert it to default JSON object type LinkedHashMap
		List<LinkedHashMap> emps = restTemplate.getForObject(SERVER_URI+EmpRestURIConstants.GET_ALL_EMP, List.class);
		System.out.println(emps.size());
		for(LinkedHashMap map : emps){
			System.out.println("ID="+map.get("id")+",Name="+map.get("name")+",CreatedDate="+map.get("createdDate"));;
		}
	}

    @Test
	private static void testCreateEmployee() {
		RestTemplate restTemplate = new RestTemplate();
		Employee emp = new Employee();
		emp.setId(1);emp.setName("Pankaj Kumar");
		Employee response = restTemplate.postForObject(SERVER_URI+EmpRestURIConstants.CREATE_EMP, emp, Employee.class);
		printEmpData(response);
	}

    @Test
	private static void testGetEmployee() {
		RestTemplate restTemplate = new RestTemplate();
		Employee emp = restTemplate.getForObject(SERVER_URI+"/rest/emp/1", Employee.class);
		printEmpData(emp);
	}

    @Test
	private static void testGetDummyEmployee() {
		RestTemplate restTemplate = new RestTemplate();
		Employee emp = restTemplate.getForObject(SERVER_URI+EmpRestURIConstants.DUMMY_EMP, Employee.class);
		printEmpData(emp);
	}

    @Test
	public static void printEmpData(Employee emp){
		System.out.println("ID="+emp.getId()+",Name="+emp.getName()+",CreatedDate="+emp.getCreatedDate());
	}
}

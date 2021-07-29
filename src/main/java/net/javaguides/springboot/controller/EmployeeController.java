package net.javaguides.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.service.EmployeeService;

@Controller
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	private int count=0;
	
	// display list of employees
	@GetMapping("/")
	public String viewHomePage(Model model) {
		return findPaginated(1, "firstName", "asc", model);		
	}
	
	@GetMapping("/showNewEmployeeForm")
	public String showNewEmployeeForm(Model model) {
		// create model attribute to bind form data
		Employee employee = new Employee();
		model.addAttribute("employee", employee);
		count++;
		return "new_employee";
	}
	
	@GetMapping("/showSearchResultEmpForm")
	public String showSearchResultEmpForm(Model model) {
		// display employees by department 
		return "empdept";
	}
	
	@PostMapping("/saveEmployee")
	public String saveEmployee(@ModelAttribute("employee") Employee employee) {
		// save employee to database
		count++;
		employeeService.saveEmployee(employee);
		return "redirect:/";
	}
	
	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable ( value = "id") long id, Model model) {
		
		// get employee from the service
		Employee employee = employeeService.getEmployeeById(id);
		count++;
		// set employee as a model attribute to pre-populate the form
		model.addAttribute("employee", employee);
		return "update_employee";
	}
	
	@GetMapping("/deleteEmployee/{id}")
	public String deleteEmployee(@PathVariable (value = "id") long id) {
		count++;
		// call delete employee method 
		this.employeeService.deleteEmployeeById(id);
		return "redirect:/";
	}
	
	
	@GetMapping("/page/{pageNo}")
	public String findPaginated(@PathVariable (value = "pageNo") int pageNo, 
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir,
			Model model) {
		int pageSize = 5;
		count+=1;
		Page<Employee> page = employeeService.findPaginated(pageNo, pageSize, sortField, sortDir);
		List<Employee> listEmployees = page.getContent();
		
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		if(count==1)
		{
			
			Employee e=new Employee();
			e.setId(1);e.setDepartment("HR");e.setDesignation("Developer");e.setEmail("awanesh@gmail.com");
			e.setFirstName("Awasa");e.setLastName("Nohara");
			employeeService.saveEmployee(e);
		}
		model.addAttribute("listEmployees", listEmployees);
		return "index";
	}
	
	@GetMapping("/EmpResult")
	public String findPaginatedDept(Model model, String keyword) {
		int pageNo=1,pageSize=5;
		String sortField="firstName";
		String sortDir="asc";
		Page<Employee> page = employeeService.findPaginated(pageNo, pageSize, sortField, sortDir);
		List<Employee> listEmployees = page.getContent();
		count++;
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		
		model.addAttribute("listEmployees", employeeService.getEmployeeByKeyword(keyword));
//		return "index";
		return "empdept";
	}
	
}

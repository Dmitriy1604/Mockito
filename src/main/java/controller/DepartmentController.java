package controller;

import Service.DepartmentService;
import model.Employee;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/departments")
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping(value = "/all")
    public Map<Integer, List<Employee>> printAllDepartmentEmployee() {
        return departmentService.findEmployees();
    }

    @GetMapping(value = "/all", params = "departmentId")
    public List<Employee> printDepartmentEmployee(@RequestParam(name = "departmentId", required = false) Integer department) {
        return departmentService.findEmployeeFromDepartment(department);
    }

    @GetMapping("/min-salary")
    public Employee getMinSalaryEmployee(@RequestParam("departmentId") Integer department) {
        return departmentService.findEmployeeWithMinSalaryFromDepartment(department);

    }

    @GetMapping("/max-salary")
    public Employee getMaxSalaryEmployee(@RequestParam("departmentId") Integer department) {
        return departmentService.findEmployeeWithMaxSalaryFromDepartment(department);

    }
}
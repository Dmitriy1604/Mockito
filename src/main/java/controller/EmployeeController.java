package controller;

import Service.EmployeeService;
import model.Employee;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(path = "/add")
    public Object addEmployee(
            @RequestParam(value = "firstName") String firstName ,
            @RequestParam(value = "lastName") String lastName ,
            @RequestParam(value = "salary") int salary ,
            @RequestParam(value = "department") int department) {
        Employee employee = null;
        try {
            employee = employeeService.addEmployee ( firstName , lastName , salary , department );
        } catch (Throwable e) {
            return e.getMessage ();
        }
        return employee;
    }

    @GetMapping(path = "/remove")
    public Object removeEmployee(
            @RequestParam(value = "firstName") String firstName ,
            @RequestParam(value = "lastName") String lastName) {
        Employee employee = null;
        try {
            employeeService.removeEmployee ( firstName , lastName );
        } catch (Throwable e) {
            return e.getMessage ();
        }
        return employee;
    }

    @GetMapping(path = "/find")
    public Object findEmployee(
            @RequestParam(value = "firstName") String firstName ,
            @RequestParam(value = "lastName") String lastName) {
        Employee employee = null;
        try {
            employeeService.findEmployee ( firstName , lastName );
        } catch (Throwable e) {
            return e.getMessage ();
        }
        return employee;
    }



}

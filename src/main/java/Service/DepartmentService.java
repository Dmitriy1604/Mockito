package Service;


import exception.EmployeeNotFoundException;
import model.Employee;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DepartmentService {
    private final EmployeeService employeeService;

    public DepartmentService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    public Employee findEmployeeWithMaxSalaryFromDepartment(int department) {
        return employeeService.printEmployees ().values ().stream ()
                .filter ( employee -> employee.getDepartment () == department )
                .max ( Comparator.comparingDouble ( Employee::getSalary ) )
                .orElseThrow(EmployeeNotFoundException::new );
    }

    public Employee findEmployeeWithMinSalaryFromDepartment(int department) {
        return employeeService.printEmployees ().values ().stream ()
                .min ( Comparator.comparingDouble ( Employee::getSalary ) )
                .orElseThrow(EmployeeNotFoundException::new );

    }

    public List<Employee> findEmployeeFromDepartment(int department) {
        return employeeService.printEmployees ().values ().stream ()
                .filter ( employee -> employee.getDepartment () == department )
                .collect ( Collectors.toList () );
    }

    public Map<Integer, List<Employee>> findEmployees() {
        return employeeService.printEmployees ().values ().stream ()
                .collect ( Collectors.groupingBy ( Employee::getDepartment ) );
    }
}


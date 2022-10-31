package Service;

import exception.EmployeeNotFoundException;
import exception.EmployeeStorageIsFullException;
import model.Employee;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmployeeService {
    private final Map<String, Employee> employees = new HashMap<> ();
    private final ValidateService validateService;
    private final String ERR_EMPL_ALREADY_ADDED = "Сотрудник уже имеется в массиве";
    private final String ERR_EMPL_NOT_FOUND = "Сотрудник не найден";
    private static final int LIMIT = 10;

    public EmployeeService(ValidateService validateService) {
        this.validateService = validateService;
    }



    public Employee addEmployee(String firstName, String lastName, int department,double salary) {
        Employee employee = validateService.validatorEmployee(firstName, lastName,department, salary);
        String key = getKey(employee.getFirstName(), employee.getLastName());
        if (employees.containsKey(key)) {
            throw new RuntimeException(ERR_EMPL_ALREADY_ADDED);
        }
        if (employees.size() < LIMIT) {
            employees.put(key, employee);
            return employee; }
        throw new  EmployeeStorageIsFullException();
    }

    public Employee removeEmployee(String firstName, String lastName) {
        String key = getKey(firstName, lastName);
        if (employees.containsKey(key)) {
            return employees.remove(key);
        }
        throw new EmployeeNotFoundException();
    }


    public Employee findEmployee(String firstName, String lastName) {
        String key = getKey(firstName, lastName);
        if (!employees.containsKey(key)) {
            throw new EmployeeNotFoundException();
        }
        return employees.get(key);
    }


    public Map<String, Employee> printEmployees() {
        return new HashMap<> (employees);
    }

    private static String getKey(String name, String surname) {
        return name + " " + surname;
    }
}
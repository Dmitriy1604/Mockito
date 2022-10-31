package Service;


import exception.EmployeeNotFoundException;
import model.Employee;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {

    @Mock
    private EmployeeService employeeService;
    @InjectMocks
    private DepartmentService departmentService;
    @BeforeEach
    public void beforeEach() {
        Employee employee1 = new Employee ( "Василий" , "Васильев" , 1 , 55_000 );
        String key1 = employee1.getFirstName () + " " + employee1.getLastName ();
        Employee employee2 = new Employee ( "Андрей" , "Андреев" , 1 , 65_000  );
        String key2 = employee2.getFirstName () + " " + employee2.getLastName ();
        Employee employee3 = new Employee ( "Иван" , "Иванов" , 2 , 45_000 );
        String key3 = employee3.getFirstName () + " " + employee3.getLastName ();
        Employee employee4 = new Employee ( "Мария" , "Иванова" , 2 , 50_000 );
        String key4 = employee4.getFirstName () + " " + employee4.getLastName ();
        Employee employee5 = new Employee ( "Ирина" , "Андреева" , 2 , 47_000 );
        String key5 = employee5.getFirstName () + " " + employee5.getLastName ();

        Map<String,Employee> employees = new HashMap<> ();
        employees.put (key1,employee1);
        employees.put (key2,employee2);
        employees.put (key3,employee3);
        employees.put (key4,employee4);
        employees.put (key5,employee5);

        when(employeeService.printEmployees()).thenReturn(employees);
    }
    @ParameterizedTest
    @MethodSource("employeeWithMaxSalaryParams")
    public void employeeWithMaxSalaryPositiveTest(int department, Employee expected){
        assertThat(departmentService.findEmployeeWithMaxSalaryFromDepartment(department)).isEqualTo(expected);
    }
    @Test
    public void employeeWithMaxSalaryNegativeTest(){
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy (() -> departmentService.findEmployeeWithMaxSalaryFromDepartment(3));
    }
    @ParameterizedTest
    @MethodSource("employeeWithMinSalaryParams")
    public void employeeWithMinSalaryPositiveTest (int departmentId, Employee expected){
        assertThat(departmentService.findEmployeeWithMinSalaryFromDepartment(departmentId)).isEqualTo(expected);

    }
    @Test
    public void employeeWithMinSalaryNegativeTest(){
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy (() -> departmentService.findEmployeeWithMinSalaryFromDepartment(3));
    }
    @ParameterizedTest
    @MethodSource("employeesFromDepartmentParams")
    public void employeesFromDepartmentPositiveTest(int departmentId,List<Employee> expected){
        assertThat(departmentService.findEmployeeFromDepartment(departmentId)).containsExactlyElementsOf(expected);

    }
    @Test
    public void employeesGroupedByDepartmentTest(){
        assertThat(departmentService.findEmployees()).containsExactlyInAnyOrderEntriesOf(
                Map.of (
                        1,List.of(new Employee("Василий" , "Васильев" , 1 , 55_000), new Employee("Андрей" , "Андреев" , 1 , 65_000)),
                        2,List.of (new Employee("Иван","Иванов",2,45_000), new Employee("Мария","Иванова",2,50_000),new Employee("Ирина","Андреева",2,47_000))
                ));
    }
    public static Stream<Arguments> employeeWithMaxSalaryParams(){
        return Stream.of(
                Arguments.of(1,new Employee("Андрей","Андреев",1,65_000)),
                Arguments.of(2,new Employee("Иван","Иванов",2,45_000))
        );
    }
    public static Stream<Arguments> employeeWithMinSalaryParams(){
        return Stream.of(
                Arguments.of(1,new Employee("Василий","Васильев",1,55_000)),
                Arguments.of(2,new Employee("Иван","Иванов",2,45_000))
        );
    }
    public static Stream<Arguments> employeesFromDepartmentParams(){
        return Stream.of(
                Arguments.of(1, List.of(new Employee("Василий","Васильев",1,55_000), new Employee("Андрей","Андреев",1,65_000)),
                        Arguments.of(2,"Иван","Иванов",2,45_000), new Employee("Мария","Иванова",2,50_000),new Employee("Ирина","Андреева",2,47_000)),
                Arguments.of(3, Collections.emptyList ())
        );
    }


}
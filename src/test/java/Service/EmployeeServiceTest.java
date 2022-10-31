package Service;




import exception.EmployeeAlreadyAddedException;
import exception.EmployeeNotFoundException;
import exception.EmployeeStorageIsFullException;
import exception.IncorrectNameException;
import model.Employee;
import org.apache.el.lang.FunctionMapperImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class EmployeeServiceTest {
    private final EmployeeService employeeService = new EmployeeService (new ValidateService ());


    @ParameterizedTest
    @MethodSource("params")
    public void addNegativeTest1(String name,
                                 String surname,
                                 int department,
                                 double salary){
        Employee expected = new Employee (name,surname,department,salary);
        assertThat(employeeService.printEmployees()).isEmpty();
        employeeService.addEmployee (name,surname,department,salary);
        assertThat(employeeService.printEmployees ().values())
                .hasSize(1)
                .containsExactly(expected);
        assertThat(employeeService.findEmployee(expected.getLastName (), expected.getFirstName()))
                .isNotNull()
                .isEqualTo(expected);
        assertThatExceptionOfType( EmployeeAlreadyAddedException.class)
                .isThrownBy(() -> employeeService.addEmployee(name,surname,department,salary));
    }
    @ParameterizedTest
    @MethodSource("params")
    public void addNegativeTest2(String name,
                                 String surname,
                                 int department,
                                 double salary){
        assertThat(employeeService.printEmployees()).isEmpty();
        Map<String,Employee> employees = generateEmployees(10);
        employees.values ().forEach(employee->
                assertThat(employeeService.addEmployee(employee.getLastName (),employee.getFirstName (),employee.getDepartment(),employee.getSalary())).isEqualTo(employee));

        assertThat(employeeService.printEmployees()).hasSize(10);
        assertThatExceptionOfType( EmployeeStorageIsFullException.class)
                .isThrownBy(() -> employeeService.addEmployee (name,surname,department,salary));

    }
    @Test
    public void addNegativeTest3(){
        assertThatExceptionOfType( IncorrectNameException.class)
                .isThrownBy(()-> employeeService.addEmployee("Иван#","Ivanov",1,55000));
        assertThatExceptionOfType(IncorrectNameException.class)
                .isThrownBy(()-> employeeService.addEmployee("Andrey","!Andreev",1,65000));
        assertThatExceptionOfType(IncorrectNameException.class)
                .isThrownBy(()-> employeeService.addEmployee(null,"Иванова",2,75000));

    }
    @ParameterizedTest
    @MethodSource("params")
    public void removeNegativeTest(String name,
                                   String surname,
                                   int department,
                                   double salary) {
        assertThat ( employeeService.printEmployees()).isEmpty();
        assertThatExceptionOfType ( EmployeeNotFoundException.class )
                .isThrownBy ( () -> employeeService.removeEmployee( "test" , "test" ) );
        Employee expected = new Employee ( name , surname , department , salary );
        employeeService.addEmployee(name,surname,department,salary);
        assertThat(employeeService.printEmployees().values())
                .hasSize(1)
                .containsExactly(expected);
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy ( () -> employeeService.removeEmployee("test","test"));
    }
    @ParameterizedTest
    @MethodSource("params")
    public void removePositiveTest(String name,
                                   String surname,
                                   int department,
                                   double salary) {
        assertThat ( employeeService.printEmployees()).isEmpty();
        Employee expected = new Employee ( name , surname , department , salary);
        employeeService.addEmployee(name,surname,department,salary);
        assertThat(employeeService.printEmployees().values())
                .hasSize(1)
                .containsExactly(expected);
        assertThat(employeeService.removeEmployee(name,surname)).isEqualTo(expected);
        assertThat(employeeService.printEmployees()).isEmpty();
    }
    @ParameterizedTest
    @MethodSource("params")
    public void findNegativeTest(String name,
                                 String surname,
                                 int department,
                                 double salary) {
        assertThat(employeeService.printEmployees()).isEmpty();
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> employeeService.findEmployee( "test" , "test"));
        Employee expected = new Employee ( name , surname , department , salary);
        employeeService.addEmployee(name,surname,department,salary);
        assertThat(employeeService.printEmployees().values ())
                .hasSize(1)
                .containsExactly(expected);
        assertThatExceptionOfType( EmployeeNotFoundException.class)
                .isThrownBy ( () -> employeeService.findEmployee("test","test"));

    }
    @ParameterizedTest
    @MethodSource("params")
    public void findPositiveTest(String name,
                                 String surname,
                                 int department,
                                 double salary) {
        assertThat(employeeService.printEmployees() ).isEmpty();
        Employee expected = new Employee ( name , surname , department , salary);
        employeeService.addEmployee(name,surname,department,salary);
        assertThat(employeeService.printEmployees().values())
                .hasSize(1)
                .containsExactly(expected);
        assertThat(employeeService.findEmployee(name,surname)).isEqualTo(expected);
    }
    private Map<String, Employee> generateEmployees(int size){
        return Stream.iterate(1, i -> i + 1)
                .limit(size)
                .map(i -> new Employee("Name" + (char)((int) 'a' + i),"Surname" + (char) ((int) 'a' + i),i,10_000 + i))
                .collect( Collectors.toMap(Employee::getFullName, Function.identity ()));
    }
    public static Stream<Arguments> param(){
        return Stream.of(
                Arguments.of("Ivan","Ivanov",1,55_000),
                Arguments.of("Andrey","Andreev",1,65_000),
                Arguments.of("Mariya","Ivanova",2,75_000)
        );

    }
}

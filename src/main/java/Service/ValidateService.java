package Service;

import exception.IncorrectNameException;
import exception.IncorrectSurnameException;
import model.Employee;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class ValidateService {
    public Employee validatorEmployee(String name, String surname, Integer dept, Double pay) {
        if (!StringUtils.isAlpha(name)) {
            throw new IncorrectNameException ();
        } else if (!StringUtils.isAlpha(surname)) {
            throw new IncorrectSurnameException ();
        }
        return new Employee(StringUtils.capitalize(name.toLowerCase()), StringUtils.capitalize(surname.toLowerCase()), dept, pay);
    }
}

package com.yicj.study.validation;

import com.yicj.study.validation.model.UserVO;
import com.yicj.study.validation.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.executable.ExecutableValidator;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * @author: yicj
 * @date: 2023/9/29 14:20
 */
@Slf4j
public class ValidatorTest {

    private Validator validator;

    @BeforeEach
    public void init(){
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void validate(){
        Set<ConstraintViolation<UserVO>> validateList = validator.validate(new UserVO());
        validateList.forEach(item -> {
            Path propertyPath = item.getPropertyPath();
            String message = item.getMessage();
            log.info("property path : {}, message : {}", propertyPath, message);
        });
    }

    @Test
    public void validateParameters() throws NoSuchMethodException {
        UserService target = new UserService() ;
        Method methodToValidate = UserService.class.getMethod("createUser", String.class, int.class);
        Object[] parameterValues = {"a", 12} ;
        ExecutableValidator execVal = this.validator.forExecutables();
        Set<ConstraintViolation<UserService>> validateList =
                execVal.validateParameters(target, methodToValidate, parameterValues);
        validateList.forEach(item -> {
            Path propertyPath = item.getPropertyPath();
            String message = item.getMessage();
            log.info("property path : {}, message : {}", propertyPath, message);
        });
    }
}

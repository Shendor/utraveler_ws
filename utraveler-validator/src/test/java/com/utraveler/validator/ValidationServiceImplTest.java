package com.utraveler.validator;

import org.springframework.validation.Validator;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.common.collect.Lists;
import com.utraveler.validator.exception.NoValidatorException;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.AssertJUnit.assertNotNull;

public class ValidationServiceImplTest {


    private ValidationServiceImpl validationServiceImpl;


    @BeforeMethod
    public void setUp()
            throws Exception {
        validationServiceImpl = new ValidationServiceImpl();
    }


    @Test
    public void testValidate_ValidatorFound_ValidationSuccess()
            throws Exception {
        Validator mockValidator = mock(Validator.class);
        when(mockValidator.supports(Object.class)).thenReturn(true);
        validationServiceImpl.setValidators(Lists.newArrayList(mockValidator));

        assertNotNull(validationServiceImpl.validate(new Object()));
    }


    @Test(expectedExceptions = NoValidatorException.class)
    public void testValidate_NoValidatorFound_ValidationFail()
            throws Exception {
        Validator mockValidator = mock(Validator.class);
        when(mockValidator.supports(Object.class)).thenReturn(true);

        validationServiceImpl.validate(new Object());
    }
}

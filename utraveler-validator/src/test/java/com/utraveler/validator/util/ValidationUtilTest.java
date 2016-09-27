package com.utraveler.validator.util;

import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class ValidationUtilTest {

    @Test
    public void testIsEmailValid_EmailValid_ReturnTrue()
            throws Exception {
        assertTrue(ValidationUtil.isEmailValid("e1@test.com"));
    }


    @Test
    public void testIsEmailValid_EmailNotValid_ReturnFalse()
            throws Exception {
        assertFalse(ValidationUtil.isEmailValid("e1test.com"));
        assertFalse(ValidationUtil.isEmailValid(null));
    }


    @Test
    public void testIsTextInRange_TextInRange_ReturnTrue()
            throws Exception {
        assertTrue(ValidationUtil.isTextInRange("test", 1, 10));
    }


    @Test
    public void testIsTextInRange_TextNotInRange_ReturnFalse()
            throws Exception {
        assertFalse(ValidationUtil.isTextInRange("test", 0, 2));
        assertFalse(ValidationUtil.isTextInRange(null, 1, 5));
    }


    @Test
    public void testIsnumberInRange_NumberInRange_ReturnTrue()
            throws Exception {
        assertTrue(ValidationUtil.isNumberInRange(5, 1, 10));
    }


    @Test
    public void testIsNumberInRange_NumberNotInRange_ReturnFalse()
            throws Exception {
        assertFalse(ValidationUtil.isNumberInRange(100, 0, 2));
        assertFalse(ValidationUtil.isNumberInRange(null, 1, 5));
    }
}

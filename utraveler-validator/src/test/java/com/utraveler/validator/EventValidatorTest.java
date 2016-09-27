package com.utraveler.validator;

import org.springframework.validation.MapBindingResult;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.common.collect.Maps;
import com.utraveler.model.Event;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class EventValidatorTest {


    private EventValidator eventValidator;


    @BeforeMethod
    public void setUp()
            throws Exception {
        eventValidator = new EventValidator();
    }


    @Test
    public void testSupports_ForEvent_ReturnTrue()
            throws Exception {
        assertTrue(eventValidator.supports(Event.class));
    }


    @Test
    public void testValidate_Event_NoErrors()
            throws Exception {
        MapBindingResult result = new MapBindingResult(Maps.newHashMap(), Event.class.getName());
        eventValidator.validate(new Event(), result);
        assertFalse(result.hasErrors());
    }
}

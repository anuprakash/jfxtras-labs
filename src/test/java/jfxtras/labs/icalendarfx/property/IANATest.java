package jfxtras.labs.icalendarfx.property;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

import jfxtras.labs.icalendarfx.properties.component.misc.IANAProperty;

public class IANATest
{
    @Test
    public void canParseIANA1()
    {
        String content = "TESTPROP2:CASUAL";
        IANAProperty.setRegisteredIANAPropertys(Arrays.asList("TESTPROP2"));
        IANAProperty madeProperty = IANAProperty.parse(content);
        assertEquals(content, madeProperty.toContent());
        IANAProperty expectedProperty = IANAProperty.parse("CASUAL")
                .withPropertyName("TESTPROP2");
        assertEquals(expectedProperty, madeProperty);
        assertEquals("CASUAL", madeProperty.getValue());
    }
    
    @Test
    public void canParseIANA2()
    {
        String content = "TESTPROP2;VALUE=INTEGER:12";
        IANAProperty.setRegisteredIANAPropertys(Arrays.asList("TESTPROP2"));
        IANAProperty madeProperty = IANAProperty.parse(content);
        assertEquals(content, madeProperty.toContent());
        IANAProperty expectedProperty = new IANAProperty(12)
                .withPropertyName("TESTPROP2")
                .withValueType("INTEGER");
        assertEquals(expectedProperty, madeProperty);
        assertEquals(12, madeProperty.getValue());
        assertEquals("TESTPROP2", madeProperty.name());
    }
}

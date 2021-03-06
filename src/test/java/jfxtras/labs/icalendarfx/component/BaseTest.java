package jfxtras.labs.icalendarfx.component;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import jfxtras.labs.icalendarfx.components.DaylightSavingTime;
import jfxtras.labs.icalendarfx.components.StandardTime;
import jfxtras.labs.icalendarfx.components.VAlarm;
import jfxtras.labs.icalendarfx.components.VComponent;
import jfxtras.labs.icalendarfx.components.VComponentBase;
import jfxtras.labs.icalendarfx.components.VEvent;
import jfxtras.labs.icalendarfx.components.VFreeBusy;
import jfxtras.labs.icalendarfx.components.VJournal;
import jfxtras.labs.icalendarfx.components.VTimeZone;
import jfxtras.labs.icalendarfx.components.VTodo;
import jfxtras.labs.icalendarfx.properties.component.misc.NonStandardProperty;

/**
 * Test following components:
 * @see VEvent
 * @see VTodo
 * @see VJournal
 * @see VAlarm
 * @see VFreeBusy
 * @see VTimeZone
 * @see StandardTime
 * @see DaylightSavingTime
 * 
 * for the following properties:
 * @see NonStandardProperty
 * 
 * @author David Bal
 *
 */
public class BaseTest
{
    @Test
    public void canBuildBase() throws InstantiationException, IllegalAccessException
    {
        List<VComponentBase> components = Arrays.asList(
                new VEvent()
                    .withNonStandard(NonStandardProperty.parse("X-ABC-MMSUBJ;VALUE=URI;FMTTYPE=audio/basic:http://www.example.org/mysubj.au"))
                    .withNonStandard(NonStandardProperty.parse("X-TEST-OBJ:testid")),
                new VTodo()
                    .withNonStandard(NonStandardProperty.parse("X-ABC-MMSUBJ;VALUE=URI;FMTTYPE=audio/basic:http://www.example.org/mysubj.au"))
                    .withNonStandard(NonStandardProperty.parse("X-TEST-OBJ:testid")),
                new VJournal()
                    .withNonStandard(NonStandardProperty.parse("X-ABC-MMSUBJ;VALUE=URI;FMTTYPE=audio/basic:http://www.example.org/mysubj.au"))
                    .withNonStandard(NonStandardProperty.parse("X-TEST-OBJ:testid")),
                new VFreeBusy()
                    .withNonStandard(NonStandardProperty.parse("X-ABC-MMSUBJ;VALUE=URI;FMTTYPE=audio/basic:http://www.example.org/mysubj.au"))
                    .withNonStandard(NonStandardProperty.parse("X-TEST-OBJ:testid")),
                new VAlarm()
                    .withNonStandard(NonStandardProperty.parse("X-ABC-MMSUBJ;VALUE=URI;FMTTYPE=audio/basic:http://www.example.org/mysubj.au"))
                    .withNonStandard(NonStandardProperty.parse("X-TEST-OBJ:testid")),
                new VTimeZone()
                    .withNonStandard(NonStandardProperty.parse("X-ABC-MMSUBJ;VALUE=URI;FMTTYPE=audio/basic:http://www.example.org/mysubj.au"))
                    .withNonStandard(NonStandardProperty.parse("X-TEST-OBJ:testid")),
                new DaylightSavingTime()
                    .withNonStandard(NonStandardProperty.parse("X-ABC-MMSUBJ;VALUE=URI;FMTTYPE=audio/basic:http://www.example.org/mysubj.au"))
                    .withNonStandard(NonStandardProperty.parse("X-TEST-OBJ:testid")),
                new StandardTime()
                    .withNonStandard(NonStandardProperty.parse("X-ABC-MMSUBJ;VALUE=URI;FMTTYPE=audio/basic:http://www.example.org/mysubj.au"))
                    .withNonStandard(NonStandardProperty.parse("X-TEST-OBJ:testid"))
                );
        
        for (VComponentBase builtComponent : components)
        {
            String componentName = builtComponent.name();
            
            String expectedContent = "BEGIN:" + componentName + System.lineSeparator() +
                    "X-ABC-MMSUBJ;VALUE=URI;FMTTYPE=audio/basic:http://www.example.org/mysubj.au" + System.lineSeparator() +
                    "X-TEST-OBJ:testid" + System.lineSeparator() +
                    "END:" + componentName;

            VComponent parsedComponent = builtComponent.getClass().newInstance();
            parsedComponent.parseContent(expectedContent);
System.out.println(builtComponent);
System.out.println(parsedComponent);
            assertEquals(parsedComponent, builtComponent);
            assertEquals(expectedContent, builtComponent.toContent());            
        }
    }
}

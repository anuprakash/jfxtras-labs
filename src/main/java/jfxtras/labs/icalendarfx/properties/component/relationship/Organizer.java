package jfxtras.labs.icalendarfx.properties.component.relationship;

import java.net.URI;

import jfxtras.labs.icalendarfx.VElement;
import jfxtras.labs.icalendarfx.components.VEvent;
import jfxtras.labs.icalendarfx.components.VFreeBusy;
import jfxtras.labs.icalendarfx.components.VJournal;
import jfxtras.labs.icalendarfx.components.VTodo;

/**
 * ORGANIZER
 * RFC 5545, 3.8.4.3, page 111
 * 
 * This property defines the organizer for a calendar component.
 * 
 * Example:
 * ORGANIZER;CN=John Smith:mailto:jsmith@example.com
 * 
 * @author David Bal
 * @see VEvent
 * @see VTodo
 * @see VJournal
 * @see VFreeBusy
 */
public class Organizer extends PropertyBaseCalendarUser<URI, Organizer> implements VElement
{    
    public Organizer(Organizer source)
    {
        super(source);
    }
    
    Organizer()
    {
        super();
    }

    public static Organizer parse(String value)
    {
        Organizer organizer = new Organizer();
        organizer.parseContent(value);
        URI.class.cast(organizer.getValue()); // ensure value class type matches parameterized type
        return organizer;
    }
}

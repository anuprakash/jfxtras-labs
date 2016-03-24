package jfxtras.labs.icalendar.properties.component.descriptive;

import jfxtras.labs.icalendar.properties.PropertyTextBase3;

/**
 * COMMENT: RFC 5545 iCalendar 3.8.1.4. page 83
 * This property specifies non-processing information intended
 * to provide a comment to the calendar user
 * Example:
 * COMMENT:The meeting really needs to include both ourselves
     and the customer. We can't hold this meeting without them.
     As a matter of fact\, the venue for the meeting ought to be at
     their site. - - John
 */
public class Comment extends PropertyTextBase3<Comment>
{
    public Comment(String propertyString)
    {
        super(propertyString);
    }
    
    public Comment(Comment source)
    {
        super(source);
    }
    
    public Comment()
    {
        super();
    }
}
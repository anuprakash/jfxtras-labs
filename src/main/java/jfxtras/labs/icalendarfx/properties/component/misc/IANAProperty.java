package jfxtras.labs.icalendarfx.properties.component.misc;

import java.util.List;

/**
   <h2>3.8.8.1.  IANA Properties</h2>
   
   <p>Property Name:  An IANA-registered property name</p>

   <p>Value Type:  The default value type is TEXT.  The value type can be
      set to any value type.</p>

   <p>Property Parameters:  Any parameter can be specified on this
      property.</p>

   <p>Description:  This specification allows other properties registered
      with IANA to be specified in any calendar components.  Compliant
      applications are expected to be able to parse these other IANA-
      registered properties but can ignore them.</p>

   <p>Format Definition:  This property is defined by the following
      notation:</p>
  <ul>
  <li>iana-prop
    <ul>
    <li>iana-token *(";" icalparameter) ":" value CRLF
    </ul>
  </ul>
  </p>
  
  <p>Example:  The following are examples of properties that might be
      registered to IANA:
  <ul>
  <li>DRESSCODE:CASUAL
  <li>NON-SMOKING;VALUE=BOOLEAN:TRUE
  </ul>
  </p>
  <h2>RFC 5545                       iCalendar                  September 2009</h2>
 * 
 * @author David Bal
 */
public class IANAProperty extends UnknownProperty<Object, IANAProperty>
{
    public static List<String> getRegisteredIANAPropertys()
    {
        return registeredIANAProperties2;
    }
    public static void setRegisteredIANAPropertys(List<String> registeredIANAProperties)
    {
        registeredIANAProperties2 = registeredIANAProperties;
    }
    private static List<String> registeredIANAProperties2;

    /*
     * CONSTRUCTORS
     */
    public IANAProperty(Object value)
    {
        super(value);
    }
    
    public IANAProperty(IANAProperty source)
    {
        super(source);
    }
    
    public IANAProperty()
    {
        super();
    }
    
    @Override
    public List<String> errors()
    {
        List<String> errors = super.errors();
        if (getRegisteredIANAPropertys() == null)
        {
            errors.add("There are no registered IANA property names");
        } else if (name() != null && ! getRegisteredIANAPropertys().contains(name()))
        {
            errors.add(name() + " is not a registereed IANA property name");
        }
        return errors;
    }
    
    public static IANAProperty parse(String value)
    {
        IANAProperty property = new IANAProperty();
        property.parseContent(value);
        return property;
    }
}

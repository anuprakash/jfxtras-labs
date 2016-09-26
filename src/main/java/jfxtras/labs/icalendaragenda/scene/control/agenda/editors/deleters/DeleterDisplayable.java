package jfxtras.labs.icalendaragenda.scene.control.agenda.editors.deleters;

import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.util.Callback;
import javafx.util.Pair;
import jfxtras.labs.icalendaragenda.scene.control.agenda.ICalendarAgenda;
import jfxtras.labs.icalendaragenda.scene.control.agenda.editors.ChangeDialogOption;
import jfxtras.labs.icalendarfx.VCalendar;
import jfxtras.labs.icalendarfx.components.VDisplayable;
import jfxtras.labs.icalendarfx.components.VEvent;
import jfxtras.labs.icalendarfx.components.VJournal;
import jfxtras.labs.icalendarfx.components.VTodo;
import jfxtras.labs.icalendarfx.parameters.Range.RangeType;
import jfxtras.labs.icalendarfx.properties.component.descriptive.Status.StatusType;
import jfxtras.labs.icalendarfx.properties.component.recurrence.RecurrenceRule;
import jfxtras.labs.icalendarfx.properties.component.relationship.RecurrenceId;

/**
 * <p>Handles deleting recurrences of a {@link VDisplayable}
 * (e.g. {@link VEvent}, {@link VTodo}, {@link VJournal})</p>
 * 
 * @author David Bal
 *
 * @param <T> concrete implementation of this class
 * @param <U> concrete {@link VDisplayable} class
 */
public abstract class DeleterDisplayable<T, U extends VDisplayable<?>> implements Deleter
{
    public DeleterDisplayable(U vComponent)
    {
        this.vComponent = vComponent;
    }
    
    /*
     * VCOMPONENT EDITED
     */
    /** Gets the value of the {@link VDisplayable} to be deleted */
    public U getVComponent() { return vComponent; }
    private U vComponent;
    /** Sets the value of the edited {@link VDisplayable} */
    public void setVComponent(U vComponentEdited) { this.vComponent = vComponentEdited; }
    /**
     * Sets the value of the edited {@link VDisplayable}
     * 
     * @return - this class for chaining
     * @see VCalendar
     */
    public T withVComponentEdited(U vComponentEdited) { setVComponent(vComponentEdited); return (T) this; }


//    /*
//     * VCOMPONENTS
//     */
//    /** Gets the value of the {@link VDisplayable} to be edited */
//    public List<U> getVComponents() { return vComponents; }
//    private List<U> vComponents;
//    /** Sets the value of the {@link VDisplayable} to be edited */
//    public void setVComponents(List<U> vComponents) { this.vComponents = vComponents; }
//    /**
//     * Sets the value of the {@link VDisplayable} to be edited and returns this class for chaining.
//     * 
//     * @return - this class for chaining
//     */
//    public T withVComponents(List<U> vComponents) { setVComponents(vComponents); return (T) this; }

    /*
     * START ORIGINAL RECURRENCE
     */
    /** Gets the value of the original recurrence date or date/time */
    public Temporal getStartOriginalRecurrence() { return startOriginalRecurrence; }
    private Temporal startOriginalRecurrence;
    /** Sets the value of the original recurrence date or date/time */
    public void setStartOriginalRecurrence(Temporal startOriginalRecurrence) { this.startOriginalRecurrence = startOriginalRecurrence; }
    /**
     * Sets the value of the original recurrence date or date/time and returns this class for chaining
     * 
     * @return - this class for chaining
     */
    public T withStartOriginalRecurrence(Temporal startOriginalRecurrence) { setStartOriginalRecurrence(startOriginalRecurrence); return (T) this; }

    /*
     * CHANGE DIALOG CALLBACK
     */    
    /** Gets the value of the dialog callback to prompt the user to select delete option */
    public Callback<Map<ChangeDialogOption, Pair<Temporal,Temporal>>, ChangeDialogOption> getDialogCallback() { return dialogCallback; }
    private Callback<Map<ChangeDialogOption, Pair<Temporal,Temporal>>, ChangeDialogOption> dialogCallback;    
    /** Sets the value of the dialog callback to prompt the user to select delete option */
    public void setDialogCallback(Callback<Map<ChangeDialogOption, Pair<Temporal,Temporal>>, ChangeDialogOption> dialogCallback) { this.dialogCallback = dialogCallback; }
    /**
     * Sets the value of the dialog callback to prompt the user to select delete option and returns this class for chaining
     * 
     * @return - this class for chaining
     */
    public T withDialogCallback(Callback<Map<ChangeDialogOption, Pair<Temporal,Temporal>>, ChangeDialogOption> dialogCallback)
    {
        setDialogCallback(dialogCallback);
        return (T) this;
    }
    
    /** Tests the object state is valid and revision can proceed.  Returns true if valid, false otherwise */
    private boolean isValid()
    {
//        if (getStartOriginalRecurrence() == null)
//        {
//            System.out.println("startOriginalRecurrence must not be null");
//            return false;
//        }
        if (getDialogCallback() == null)
        {
            System.out.println("dialogCallback must not be null");
            return false;
        }
        return true;   
    }
    
    @Override
    public List<VCalendar> delete()
    {
        if (! isValid())
        {
            throw new RuntimeException("Invalid parameters for component revision:");
        }
        
        // Copy VComponent to ensure original is unchanged
        U vComponent = null;
        try
        {
            vComponent = (U) getVComponent().getClass().newInstance();
            vComponent.copyFrom(getVComponent());
        } catch (InstantiationException | IllegalAccessException e)
        {
            e.printStackTrace();
        }
        
        List<VCalendar> itipMessages = new ArrayList<>();
        boolean hasRRule = vComponent.getRecurrenceRule() != null;
        if (hasRRule)
        {
            Map<ChangeDialogOption, Pair<Temporal,Temporal>> choices = ChangeDialogOption.makeDialogChoices(
                    vComponent,
                    startOriginalRecurrence);
            ChangeDialogOption changeResponse = dialogCallback.call(choices);
            switch (changeResponse)
            {
            case ALL:
            {
                VCalendar cancelMessage = setUpCancelMessage(vComponent);
                itipMessages.add(cancelMessage);
                // NOTE: Orphaned children should be automatically removed when message is processed
                // If not, then must be explicitly removed here
                break;
            }
            case CANCEL:
                break;
//                return vComponent;
            case ONE:
            {
                /* Note: A request method could be used to add an EXDATE instead of cancel.
                 * RFC 5546 indicates cancel should be used, but some clients such as Google
                 * calendar doesn't support the cancel, but it does support the request.
                */
                VCalendar cancelMessage = setUpCancelMessage(vComponent);
//                VCalendar cancelMessage = Deleter.defaultCancelVCalendar();
//                vComponent.setStatus(StatusType.CANCELLED);
//                vComponent.eraseDateTimeProperties();
//                cancelMessage.addVComponent(vComponent);
                vComponent.setRecurrenceId(getStartOriginalRecurrence());
                vComponent.setRecurrenceRule((RecurrenceRule) null);
                itipMessages.add(cancelMessage);
                
                // REVISE APPROACH - accepted by Google
//                // Add recurrence to EXDATE of parent
//                Temporal recurrence = getStartOriginalRecurrence();
//                if (vComponent.getExceptionDates() == null)
//                {
//                    vComponent.withExceptionDates(recurrence);
//                } else
//                {
//                    vComponent.getExceptionDates().get(0).getValue().add(recurrence);
//                }
//                vComponent.incrementSequence();
//                
//                VCalendar requestMessage = Reviser.defaultRequestVCalendar();
//                requestMessage.addVComponent(vComponent);
//                itipMessages.add(requestMessage);    
                break;
            }
            case THIS_AND_FUTURE:
                VCalendar cancelMessage = setUpCancelMessage(vComponent);
//                VCalendar cancelMessage = Deleter.defaultCancelVCalendar();
//                vComponent.setStatus(StatusType.CANCELLED);
//                vComponent.eraseDateTimeProperties();
//                cancelMessage.addVComponent(vComponent);
                vComponent.setRecurrenceId(new RecurrenceId(getStartOriginalRecurrence()).withRange(RangeType.THIS_AND_FUTURE));
                itipMessages.add(cancelMessage);
                
                // REVISE APPROACH - accepted by Google
//              // Add recurrence to EXDATE of parent
//              Temporal recurrence = getStartOriginalRecurrence();
//              if (vComponent.getExceptionDates() == null)
//              {
//                  vComponent.withExceptionDates(recurrence);
//              } else
//              {
//                  vComponent.getExceptionDates().get(0).getValue().add(recurrence);
//              }
//              vComponent.incrementSequence();
//              
//              VCalendar requestMessage = Reviser.defaultRequestVCalendar();
//              requestMessage.addVComponent(vComponent);
//              itipMessages.add(requestMessage);   
                
//                // add UNTIL
//                Temporal previous = vComponent.previousStreamValue(getStartOriginalRecurrence());
//                final Temporal until;
//                if (previous.isSupported(ChronoUnit.NANOS))
//                {
//                    until = DateTimeUtilities.DateTimeType.DATE_WITH_UTC_TIME.from(previous);
//                } else
//                {
//                    until = LocalDate.from(previous);                    
//                }
//                vComponent.getRecurrenceRule().getValue().setUntil(until);
//                List<VDisplayable<?>> recurrenceChildren = vComponent.recurrenceChildren()
//                        .stream()
//                        .filter(v -> DateTimeUtilities.isAfter(v.getRecurrenceId().getValue(), getStartOriginalRecurrence()))
//                        .collect(Collectors.toList());

                break;
            default:
                throw new RuntimeException("Unsupprted response:" + changeResponse);          
            }
        } else
        { // delete individual component
            VCalendar cancelMessage = setUpCancelMessage(vComponent);
//            VCalendar cancelMessage = Deleter.defaultCancelVCalendar();
//            vComponent.setStatus(StatusType.CANCELLED);
//            vComponent.eraseDateTimeProperties();
//            cancelMessage.addVComponent(vComponent);
            itipMessages.add(cancelMessage);
            // NOTE: EXDATE should be added to parent when the iTIP message is processed.

//            // does recurrence instance exist, then add EXDATE to parent
//            if (vComponent.getRecurrenceId() != null)
//            { // add EXDATE to recurrence parent
//                // Copy parent component
//                U parentCopy = null;
//                try
//                {
//                    parentCopy = (U) getVComponent().getClass().newInstance();
//                    VDisplayable<?> parent = getVComponent().recurrenceParent();
//                    parentCopy.copyFrom(parent);
//                } catch (InstantiationException | IllegalAccessException e)
//                {
//                    e.printStackTrace();
//                }
//                
//                // Add recurrence to EXDATE of parent
//                Temporal recurrence = vComponent.getRecurrenceId().getValue();
//                if (parentCopy.getExceptionDates() == null)
//                {
//                    parentCopy.withExceptionDates(recurrence);
//                } else
//                {
//                    parentCopy.getExceptionDates().get(0).getValue().add(recurrence);
//                }
//                parentCopy.incrementSequence();
//                VCalendar requestMessage = Reviser.defaultRequestVCalendar();
//                requestMessage.addVComponent(parentCopy);
//                itipMessages.add(requestMessage);                
//            }
        }

//        if (! vComponent.isValid())
//        {
//            throw new RuntimeException("Invalid component:" + System.lineSeparator() + 
//                    vComponent.errors().stream().collect(Collectors.joining(System.lineSeparator())) + System.lineSeparator() +
//                    vComponent.toContent());
//        }
//        getVComponents().add(vComponent);
        return itipMessages;
//        return vComponent;
    }

    private VCalendar setUpCancelMessage(U vComponent)
    {
        VCalendar cancelMessage = ICalendarAgenda.emptyCanceliTIPMessage();
        vComponent.setStatus(StatusType.CANCELLED);
        vComponent.setRecurrenceRule((RecurrenceRule) null);
        vComponent.eraseDateTimeProperties();
        cancelMessage.addVComponent(vComponent);
        return cancelMessage;
    }
}

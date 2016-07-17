package jfxtras.labs.icalendarfx.components.deleters;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.util.Callback;
import javafx.util.Pair;
import jfxtras.labs.icalendarfx.components.VComponentDisplayable;
import jfxtras.labs.icalendarfx.components.VEvent;
import jfxtras.labs.icalendarfx.components.VJournal;
import jfxtras.labs.icalendarfx.components.VTodo;
import jfxtras.labs.icalendarfx.components.revisors.ChangeDialogOption;
import jfxtras.labs.icalendarfx.properties.component.recurrence.ExceptionDates;
import jfxtras.labs.icalendarfx.utilities.DateTimeUtilities;

/**
 * Handles deleting one or all recurrences of a {@link VComponentDisplayable}
 * (e.g. {@link VEvent}, {@link VTodo}, {@link VJournal})
 * 
 * @author David Bal
 *
 * @param <U> VComponent class
 */
public class DeleterDisplayable<U extends VComponentDisplayable<U>> extends Deleter<U>
{
    private U vComponent;

    public DeleterDisplayable(U vComponent)
    {
        this.vComponent = vComponent;
    }
    
    public Temporal getStartOriginalRecurrence() { return startOriginalRecurrence; }
    private Temporal startOriginalRecurrence;
    public void setStartOriginalRecurrence(Temporal startOriginalRecurrence) { this.startOriginalRecurrence = startOriginalRecurrence; }
    public DeleterDisplayable<U> withStartOriginalRecurrence(Temporal startOriginalRecurrence) { setStartOriginalRecurrence(startOriginalRecurrence); return this; }
    
    public Callback<Map<ChangeDialogOption, Pair<Temporal,Temporal>>, ChangeDialogOption> getDialogCallback() { return dialogCallback; }
    private Callback<Map<ChangeDialogOption, Pair<Temporal,Temporal>>, ChangeDialogOption> dialogCallback;    
    public void setDialogCallback(Callback<Map<ChangeDialogOption, Pair<Temporal,Temporal>>, ChangeDialogOption> dialogCallback) { this.dialogCallback = dialogCallback; }
    public DeleterDisplayable<U> withDialogCallback(Callback<Map<ChangeDialogOption, Pair<Temporal,Temporal>>, ChangeDialogOption> dialogCallback)
    {
        setDialogCallback(dialogCallback);
        return this;
    }
    
    private boolean isValid()
    {
        if (getStartOriginalRecurrence() == null)
        {
            System.out.println("startOriginalRecurrence must not be null");
            return false;
        }
        if (getDialogCallback() == null)
        {
            System.out.println("dialogCallback must not be null");
            return false;
        }
        return true;   
    }
    
    /** Main method to delete all or part of a VEvent or VTodo or VJournal */
    @Override
    public U delete()
    {
        if (! isValid())
        {
            throw new RuntimeException("Invalid parameters for component revision:");
        }
        
        boolean incrementSequence = true;
        boolean hasRRule = vComponent.getRecurrenceRule() != null;
        if (hasRRule)
        {
            Map<ChangeDialogOption, Pair<Temporal,Temporal>> choices = ChangeDialogOption.makeDialogChoices(
                    vComponent,
                    startOriginalRecurrence);
            System.out.println("choices:" + choices);
            ChangeDialogOption changeResponse = dialogCallback.call(choices);
            switch (changeResponse)
            {
            case ALL:
                return null;
            case CANCEL:
                return vComponent;
            case ONE:
                // Add recurrence to exception list
                final ExceptionDates exceptionDates;
                if (vComponent.getExceptionDates() == null)
                {
                    exceptionDates = new ExceptionDates();
                    vComponent.setExceptionDates(FXCollections.observableArrayList(exceptionDates));
                } else
                {
                    exceptionDates = vComponent.getExceptionDates().get(vComponent.getExceptionDates().size()); // get last ExceptionDate
                }
                exceptionDates.getValue().add(startOriginalRecurrence);
                break;
            case THIS_AND_FUTURE:
                // add UNTIL
                Temporal previous = vComponent.previousStreamValue(getStartOriginalRecurrence());
                final Temporal until;
                if (previous.isSupported(ChronoUnit.NANOS))
                {
                    until = DateTimeUtilities.DateTimeType.DATE_WITH_UTC_TIME.from(previous);
                } else
                {
                    until = LocalDate.from(previous);                    
                }
                vComponent.getRecurrenceRule().getValue().setUntil(until);
                break;
            default:
                throw new RuntimeException("Unsupprted response:" + changeResponse);          
            }
        } else
        { // delete individual component
            return null;
        }
        

        if (incrementSequence)
        {
            vComponent.incrementSequence();
        }
        if (! vComponent.isValid())
        {
            throw new RuntimeException("Invalid component:" + System.lineSeparator() + 
                    vComponent.errors().stream().collect(Collectors.joining(System.lineSeparator())) + System.lineSeparator() +
                    vComponent.toContent());
        }
        return vComponent;
    }
}
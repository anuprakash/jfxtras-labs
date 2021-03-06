package jfxtras.labs.icalendarfx;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import jfxtras.labs.icalendarfx.calendar.CalendarScaleTest;
import jfxtras.labs.icalendarfx.calendar.CopyCalendarTest;
import jfxtras.labs.icalendarfx.calendar.GeneralCalendarTest;
import jfxtras.labs.icalendarfx.calendar.OrdererTest;
import jfxtras.labs.icalendarfx.calendar.ParseCalendarTest;
import jfxtras.labs.icalendarfx.calendar.ReadICSFileTest;
import jfxtras.labs.icalendarfx.calendar.VCalendarRecurrenceIDTest;
import jfxtras.labs.icalendarfx.component.BaseTest;
import jfxtras.labs.icalendarfx.component.CopyComponentTest;
import jfxtras.labs.icalendarfx.component.DaylightSavingsTimeTest;
import jfxtras.labs.icalendarfx.component.DisplayableTest;
import jfxtras.labs.icalendarfx.component.EqualsTest;
import jfxtras.labs.icalendarfx.component.GeneralComponentTest;
import jfxtras.labs.icalendarfx.component.LocatableTest;
import jfxtras.labs.icalendarfx.component.ParseComponentTest;
import jfxtras.labs.icalendarfx.component.PersonalTest;
import jfxtras.labs.icalendarfx.component.PrimaryTest;
import jfxtras.labs.icalendarfx.component.RepeatableTest;
import jfxtras.labs.icalendarfx.component.ScheduleConflictTest;
import jfxtras.labs.icalendarfx.component.StandardOrDaylightTimeTest;
import jfxtras.labs.icalendarfx.component.VAlarmTest;
import jfxtras.labs.icalendarfx.component.VEventTest;
import jfxtras.labs.icalendarfx.component.VFreeBusyTest;
import jfxtras.labs.icalendarfx.component.VJournalTest;
import jfxtras.labs.icalendarfx.component.VTimeZoneTest;
import jfxtras.labs.icalendarfx.component.VTodoTest;
import jfxtras.labs.icalendarfx.itip.CancelRecurrenceTest;
import jfxtras.labs.icalendarfx.itip.ComboMessageTest;
import jfxtras.labs.icalendarfx.itip.HandleRecurrencesTest;
import jfxtras.labs.icalendarfx.itip.RequestTest;
import jfxtras.labs.icalendarfx.itip.SimpleCancelTest;
import jfxtras.labs.icalendarfx.itip.SimplePublishTest;
import jfxtras.labs.icalendarfx.itip.WholeDayTest;
import jfxtras.labs.icalendarfx.misc.FoldingAndUnfoldingTest;
import jfxtras.labs.icalendarfx.parameter.AlternateTextRepresentationTest;
import jfxtras.labs.icalendarfx.parameter.CommonNameTest;
import jfxtras.labs.icalendarfx.parameter.DelegateesTest;
import jfxtras.labs.icalendarfx.parameter.DirectoryEntryReferenceTest;
import jfxtras.labs.icalendarfx.parameter.NonstandardParameterTest;
import jfxtras.labs.icalendarfx.parameter.ParseDateTest;
import jfxtras.labs.icalendarfx.parameter.ParseParameterTest;
import jfxtras.labs.icalendarfx.parameter.ParticipationRoleTest;
import jfxtras.labs.icalendarfx.parameter.RelationshipTypeTest;
import jfxtras.labs.icalendarfx.property.ActionTest;
import jfxtras.labs.icalendarfx.property.AttachmentTest;
import jfxtras.labs.icalendarfx.property.AttendeeTest;
import jfxtras.labs.icalendarfx.property.CategoriesTest;
import jfxtras.labs.icalendarfx.property.ClassificationTest;
import jfxtras.labs.icalendarfx.property.CommentTest;
import jfxtras.labs.icalendarfx.property.ContactTest;
import jfxtras.labs.icalendarfx.property.DateTimeCompletedTest;
import jfxtras.labs.icalendarfx.property.DateTimeCreatedTest;
import jfxtras.labs.icalendarfx.property.DateTimeDueTest;
import jfxtras.labs.icalendarfx.property.DateTimeEndTest;
import jfxtras.labs.icalendarfx.property.DateTimeStampTest;
import jfxtras.labs.icalendarfx.property.DateTimeStartTest;
import jfxtras.labs.icalendarfx.property.DescriptionTest;
import jfxtras.labs.icalendarfx.property.DurationTest;
import jfxtras.labs.icalendarfx.property.ExceptionDatesTest;
import jfxtras.labs.icalendarfx.property.FreeBusyTimeTest;
import jfxtras.labs.icalendarfx.property.GeneralPropertyTest;
import jfxtras.labs.icalendarfx.property.LocationTest;
import jfxtras.labs.icalendarfx.property.NonStandardTest;
import jfxtras.labs.icalendarfx.property.OrganizerTest;
import jfxtras.labs.icalendarfx.property.PriorityTest;
import jfxtras.labs.icalendarfx.property.RecurrenceIdTest;
import jfxtras.labs.icalendarfx.property.RecurrenceRuleTest;
import jfxtras.labs.icalendarfx.property.RecurrencesTest;
import jfxtras.labs.icalendarfx.property.RelatedToTest;
import jfxtras.labs.icalendarfx.property.RepeatCountTest;
import jfxtras.labs.icalendarfx.property.RequestStatusTest;
import jfxtras.labs.icalendarfx.property.ResourcesTest;
import jfxtras.labs.icalendarfx.property.SequenceTest;
import jfxtras.labs.icalendarfx.property.StatusTest;
import jfxtras.labs.icalendarfx.property.SummaryTest;
import jfxtras.labs.icalendarfx.property.TimeTransparencyTest;
import jfxtras.labs.icalendarfx.property.TimeZoneIdentifierTest;
import jfxtras.labs.icalendarfx.property.TimeZoneNameTest;
import jfxtras.labs.icalendarfx.property.TimeZoneOffsetTest;
import jfxtras.labs.icalendarfx.property.TimeZoneURLTest;
import jfxtras.labs.icalendarfx.property.TriggerTest;
import jfxtras.labs.icalendarfx.property.URLTest;
import jfxtras.labs.icalendarfx.property.UniqueIdentifierTest;
import jfxtras.labs.icalendarfx.property.calendar.MethodTest;
import jfxtras.labs.icalendarfx.property.rrule.ByDayTest;
import jfxtras.labs.icalendarfx.property.rrule.ByMonthTest;
import jfxtras.labs.icalendarfx.property.rrule.ByWeekNumberTest;
import jfxtras.labs.icalendarfx.property.rrule.ByYearDayTest;
import jfxtras.labs.icalendarfx.property.rrule.FrequencyTest;
import jfxtras.labs.icalendarfx.property.rrule.RRuleErrorTest;
import jfxtras.labs.icalendarfx.property.rrule.RecurrenceRuleParseTest;
import jfxtras.labs.icalendarfx.property.rrule.RecurrenceRuleStreamTest;

@RunWith(Suite.class)
@SuiteClasses({ 
        
        // misc tests
        FoldingAndUnfoldingTest.class,
        OrdererTest.class,
        
        // iTIP tests
        CancelRecurrenceTest.class,
        ComboMessageTest.class,
        HandleRecurrencesTest.class,
        RequestTest.class,
        SimpleCancelTest.class,
        SimplePublishTest.class,
        WholeDayTest.class,
    
        // calendar tests
        CalendarScaleTest.class,
        CopyCalendarTest.class,
        GeneralCalendarTest.class,
        MethodTest.class,
        ParseCalendarTest.class,
        ReadICSFileTest.class,
        VCalendarRecurrenceIDTest.class,
                
        //component tests
        BaseTest.class,
        CopyComponentTest.class,
        DateTimeEndTest.class,
        DaylightSavingsTimeTest.class,
        DisplayableTest.class,
        EqualsTest.class,
        GeneralComponentTest.class,
        LocatableTest.class,
        ParseComponentTest.class,
        PrimaryTest.class,
        PersonalTest.class,
        RepeatableTest.class,
        ScheduleConflictTest.class,
        StandardOrDaylightTimeTest.class,
        VAlarmTest.class,
        VEventTest.class,
        VFreeBusyTest.class,
        VJournalTest.class,
        VTimeZoneTest.class,
        VTodoTest.class,
       
       // property tests
        ActionTest.class,
        AttachmentTest.class,
        AttendeeTest.class,
        CategoriesTest.class,
        ClassificationTest.class,
        CommentTest.class,
        ContactTest.class,
        DateTimeCompletedTest.class,
        DateTimeCreatedTest.class,
        DateTimeDueTest.class,
        DateTimeEndTest.class,
        DateTimeStampTest.class,
        DateTimeStartTest.class,
        DescriptionTest.class,
        DurationTest.class,
        ExceptionDatesTest.class,
        FreeBusyTimeTest.class,
        GeneralPropertyTest.class,
        LocationTest.class,
        NonStandardTest.class,
        OrganizerTest.class,
        PriorityTest.class,
        RecurrenceIdTest.class,
        RecurrenceRuleTest.class,
        RecurrenceRuleParseTest.class,
        RecurrencesTest.class,
        RelatedToTest.class,
        RepeatCountTest.class,
        RequestStatusTest.class,
        ResourcesTest.class,
        SequenceTest.class,
        StatusTest.class,
        SummaryTest.class,
        TimeTransparencyTest.class,
        TimeZoneIdentifierTest.class,
        TimeZoneNameTest.class,
        TimeZoneOffsetTest.class,
        TimeZoneURLTest.class,
        TriggerTest.class,
        UniqueIdentifierTest.class,
        URLTest.class,
        
        // parameter tests
        AlternateTextRepresentationTest.class,
        CommonNameTest.class,
        DelegateesTest.class,
        DirectoryEntryReferenceTest.class,
        NonstandardParameterTest.class,
        ParseDateTest.class,
        ParseParameterTest.class,
        ParticipationRoleTest.class,
        RelationshipTypeTest.class,
        
        // Recurrence Rule tests
        RecurrenceRuleParseTest.class,
        RecurrenceRuleStreamTest.class,
        RRuleErrorTest.class,
        FrequencyTest.class,
        ByDayTest.class,
        ByMonthTest.class,
        ByWeekNumberTest.class,
        ByYearDayTest.class
              
              })

public class AllTests {

}

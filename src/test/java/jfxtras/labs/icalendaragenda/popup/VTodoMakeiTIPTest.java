package jfxtras.labs.icalendaragenda.popup;

import static org.junit.Assert.assertEquals;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.Test;

import javafx.scene.Parent;
import javafx.scene.control.TextField;
import jfxtras.labs.icalendaragenda.agenda.AgendaTestAbstract;
import jfxtras.labs.icalendaragenda.internal.scene.control.skin.agenda.base24hour.Settings;
import jfxtras.labs.icalendaragenda.internal.scene.control.skin.agenda.base24hour.popup.EditVTodoTabPane;
import jfxtras.labs.icalendaragenda.scene.control.agenda.ICalendarAgenda;
import jfxtras.labs.icalendarfx.components.VTodo;
import jfxtras.scene.control.LocalDateTimeTextField;
import jfxtras.scene.control.agenda.Agenda;
import jfxtras.test.TestUtil;

public class VTodoMakeiTIPTest extends VEventPopupTestBase
{
    private EditVTodoTabPane editComponentPopup;
    
    @Override
    public Parent getRootNode()
    {
        ResourceBundle resources = ResourceBundle.getBundle("jfxtras.labs.icalendaragenda.ICalendarAgenda", Locale.getDefault());
        Settings.setup(resources);
        editComponentPopup = new EditVTodoTabPane();
        String agendaSheet = Agenda.class.getResource("/jfxtras/internal/scene/control/skin/agenda/" + Agenda.class.getSimpleName() + ".css").toExternalForm();
        editComponentPopup.getStylesheets().addAll(ICalendarAgenda.ICALENDAR_STYLE_SHEET, agendaSheet);
        return editComponentPopup;
    }
    
    @Test
    public void canDisplayPopupWithVTodo()
    {
        VTodo vtodo = new VTodo()
                .withDateTimeStart("20160518T110000")
                .withDuration(Duration.ofHours(1))
                .withSummary("test todo")
                .withDateTimeStamp("20160518T232502Z")
                .withUniqueIdentifier("20160518T232502-0@jfxtras.org");
        
        TestUtil.runThenWaitForPaintPulse( () ->
        {
            editComponentPopup.setupData(
                    vtodo,
                    LocalDateTime.of(2016, 5, 18, 11, 0),  // start of edited instance
                    LocalDateTime.of(2016, 5, 18, 12, 0),  // end of edited instance (calculated from duration)
                    AgendaTestAbstract.CATEGORIES);
        });

        TextField summary = find("#summaryTextField");
        assertEquals("test todo", summary.getText());

        LocalDateTimeTextField start = find("#startDateTimeTextField");
        assertEquals(LocalDateTime.of(2016, 5, 18, 11, 0), start.getLocalDateTime());
    }
}

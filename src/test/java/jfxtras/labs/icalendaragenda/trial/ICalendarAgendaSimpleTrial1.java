package jfxtras.labs.icalendaragenda.trial;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import jfxtras.labs.icalendaragenda.scene.control.agenda.ICalendarAgenda;
import jfxtras.labs.icalendarfx.VCalendar;

/**
 * Simplest Demo of an empty {@link ICalendarAgenda}
 * 
 * @author David Bal
 *
 */
public class ICalendarAgendaSimpleTrial1 extends Application
{        
    public static void main(String[] args) {
        launch(args);       
    }

    @Override
    public void start(Stage primaryStage) {
        VCalendar vCalendar = new VCalendar();
        ICalendarAgenda agenda = new ICalendarAgenda(vCalendar); // Agenda - displays the VCalendar information
        
        BorderPane root = new BorderPane();
        root.setCenter(agenda);

        Scene scene = new Scene(root, 1366, 768);
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(e -> System.out.println(vCalendar.toContent())); // prints resulting VCALENDAR on close
        primaryStage.show();        
    }
}

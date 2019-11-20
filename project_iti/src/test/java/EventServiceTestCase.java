import hei.devweb.projetit.dao.impl.DataSourceProvider;
import hei.devweb.projetit.entities.Event;
import hei.devweb.projetit.service.EventService;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class EventServiceTestCase {

    private EventService eventService = new EventService();

    @Before
    public void initDb(){
        try (Connection connection = DataSourceProvider.getDataSource().getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("CREATE TABLE `club` ( `club_id` int(11) NOT NULL AUTO_INCREMENT, `name` varchar(50) NOT NULL, `lien` varchar(1000) NOT NULL, PRIMARY KEY (`club_id`)");

            stmt.executeUpdate(" TABLE `event` ( `event_id` int(11) NOT NULL AUTO_INCREMENT, `title` varchar(50) NOT NULL, `club_id` int(11) NOT NULL, `event_date` date NOT NULL, `bureau` varchar(3) NOT NULL, `image_link` varchar(1000) NOT NULL, `resume` varchar(100) NOT NULL, `details` varchar(10000) DEFAULT NULL, PRIMARY KEY (`event_id`), KEY `club_id_fk` (`club_id`), CONSTRAINT `club_id_fk` FOREIGN KEY (`club_id`) REFERENCES `club` (`club_id`)");

            stmt.executeUpdate("CREATE TABLE `utilisateur` (`utilisateur_id` int(11) NOT NULL AUTO_INCREMENT, `pseudo` varchar(50) NOT NULL, `motdepasse` varchar(50) NOT NULL, `mail` varchar(100) NOT NULL, `president` tinyint(1) NOT NULL, `club_id` int(11) NULL, PRIMARY KEY(`utilisateur_id`), KEY `club_id_fg` (`club_id`), CONSTRAINT `club_id_fg` FOREIGN KEY (`club_id`) REFERENCES `club` (`club_id`)");

            stmt.executeUpdate("");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldEventList(){
        //WHEN
        List<Event> events = eventService.eventList();
        //THEN


    }
}
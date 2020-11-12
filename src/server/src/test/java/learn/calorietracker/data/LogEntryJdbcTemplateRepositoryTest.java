package learn.calorietracker.data;

import learn.calorietracker.models.LogEntry;
import learn.calorietracker.models.LogEntryType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class LogEntryJdbcTemplateRepositoryTest {
    @Autowired
    LogEntryJdbcTemplateRepository repository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    static boolean hasSetUp = false;

    @BeforeEach
    void setup() {
        if (!hasSetUp) {
            hasSetUp = true;
            jdbcTemplate.update("call set_known_good_state();");
        }
    }

    @Test
    void shouldFindAll() {
        List<LogEntry> all = repository.findAll();

        assertNotNull(all);
        // TODO try to make this assertion more specific if possible
        assertTrue(all.size() > 0);

        // TODO assert if a specific object is in the list
    }

    @Test
    void shouldFindByType() {
        List<LogEntry> all = repository.findByType(LogEntryType.BREAKFAST);

        assertNotNull(all);
        assertTrue(all.size() == 1);

        // TODO assert if a specific object is in the list
    }

    @Test
    void shouldCreate() {
        // TODO determine why the local date time value is being saved in UTC
        LogEntry logEntry = new LogEntry(0, LocalDateTime.parse("2021-01-01T09:00:00"),
                LogEntryType.SECOND_BREAKFAST, "Oatmeal", 400);

        LogEntry actual = repository.create(logEntry);

        assertNotNull(actual);
        assertTrue(actual.getId() > 0);
    }

    // TODO write more repository tests
}
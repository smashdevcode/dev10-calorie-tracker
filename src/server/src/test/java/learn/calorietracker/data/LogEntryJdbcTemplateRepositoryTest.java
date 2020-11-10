package learn.calorietracker.data;

import com.mysql.cj.log.Log;
import learn.calorietracker.App;
import learn.calorietracker.models.LogEntry;
import learn.calorietracker.models.LogEntryType;
import learn.calorietracker.ui.Controller;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LogEntryJdbcTemplateRepositoryTest {
    private final LogEntryJdbcTemplateRepository repository;

    public LogEntryJdbcTemplateRepositoryTest() {
        ApplicationContext context = new AnnotationConfigApplicationContext(DbTestConfig.class);
        repository = context.getBean(LogEntryJdbcTemplateRepository.class);
    }

    @BeforeAll
    static void oneTimeSetup() {
        ApplicationContext context = new AnnotationConfigApplicationContext(DbTestConfig.class);
        JdbcTemplate jdbcTemplate = context.getBean(JdbcTemplate.class);
        jdbcTemplate.update("call set_known_good_state();");
    }

    @Test
    void shouldFindAll() throws DataAccessException {
        List<LogEntry> all = repository.findAll();

        assertNotNull(all);
        // TODO try to make this assertion more specific if possible
        assertTrue(all.size() > 0);

        // TODO assert if a specific object is in the list
    }

    @Test
    void shouldFindByType() throws DataAccessException {
        List<LogEntry> all = repository.findByType(LogEntryType.BREAKFAST);

        assertNotNull(all);
        assertTrue(all.size() == 1);

        // TODO assert if a specific object is in the list
    }

    @Test
    void shouldCreate() throws DataAccessException {
        LogEntry logEntry = new LogEntry(0, "2021-01-01 09:00:00",
                LogEntryType.SECOND_BREAKFAST, "Oatmeal", 400);

        LogEntry actual = repository.create(logEntry);

        assertNotNull(actual);
        assertTrue(actual.getId() > 0);
    }
}
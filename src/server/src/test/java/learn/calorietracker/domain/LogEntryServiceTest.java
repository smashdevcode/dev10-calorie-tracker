package learn.calorietracker.domain;

import learn.calorietracker.data.LogEntryRepository;
import learn.calorietracker.models.LogEntry;
import learn.calorietracker.models.LogEntryType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class LogEntryServiceTest {
    @MockBean
    LogEntryRepository repository;

    @Autowired
    LogEntryService service;

    @Test
    void shouldFindAll() {
        // arrange
        when(repository.findAll()).thenReturn(List.of(
                new LogEntry(1, LocalDateTime.parse("2020-01-01T09:00:00"), LogEntryType.BREAKFAST, "Scrambled eggs", 210),
                new LogEntry(2, LocalDateTime.parse("2020-01-01T12:00:00"), LogEntryType.LUNCH, "Tuna fish salad", 500),
                new LogEntry(3, LocalDateTime.parse("2020-01-01T18:00:00"), LogEntryType.DINNER, "Steak", 1000)
        ));

        // act
        List<LogEntry> results = service.findAll();

        // assert
        assertNotNull(results);
        assertEquals(3, results.size());
    }

    // TODO write more service tests
}
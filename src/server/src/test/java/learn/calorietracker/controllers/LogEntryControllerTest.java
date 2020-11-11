package learn.calorietracker.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import learn.calorietracker.data.LogEntryRepository;
import learn.calorietracker.models.LogEntry;
import learn.calorietracker.models.LogEntryType;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Disabled
class LogEntryControllerTest {
    @MockBean
    LogEntryRepository repository;

    @Autowired
    MockMvc mvc;

    @Test
    void shouldGetAll() throws Exception {
        // arrange
        List<LogEntry> entries = List.of(
                new LogEntry(1, LocalDateTime.parse("2020-01-01T09:00:00"), LogEntryType.BREAKFAST, "Scrambled eggs", 210),
                new LogEntry(2, LocalDateTime.parse("2020-01-01T12:00:00"), LogEntryType.LUNCH, "Tuna fish salad", 500),
                new LogEntry(3, LocalDateTime.parse("2020-01-01T18:00:00"), LogEntryType.DINNER, "Steak", 1000)
        );

        // TODO determine why the date/time JSON serialization isn't consistent

//        final Jackson2ObjectMapperBuilder mapperBuilder = new Jackson2ObjectMapperBuilder();
//        ObjectMapper objectMapper = mapperBuilder.build();
////        Jackson2JsonEncoder jsonEncoder = new Jackson2JsonEncoder(objectMapper);
//        String expectedJson = objectMapper.writeValueAsString(entries);

//        ObjectMapper jsonMapper = new ObjectMapper();
////        jsonMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
////        jsonMapper.findAndRegisterModules();
////        jsonMapper.registerModule(new JavaTimeModule());
//        String expectedJson = jsonMapper.writeValueAsString(entries);

//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        ObjectMapper mapper = new ObjectMapper();
//        mapper.setDateFormat(df);
        String expectedJson = mapper.writeValueAsString(entries);

        when(repository.findAll()).thenReturn(entries);

        mvc.perform(get("/logentries"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }
}
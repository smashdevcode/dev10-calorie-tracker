package learn.calorietracker.controllers;

import learn.calorietracker.domain.LogEntryService;
import learn.calorietracker.models.LogEntry;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/logentries")
public class LogEntryController {
    private final LogEntryService service;

    public LogEntryController(LogEntryService service) {
        this.service = service;
    }

    @GetMapping
    public List<LogEntry> findAll() {
        return service.findAll();
    }

    // TODO add more endpoints
}

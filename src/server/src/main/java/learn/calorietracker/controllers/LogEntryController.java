package learn.calorietracker.controllers;

import learn.calorietracker.domain.LogEntryService;
import learn.calorietracker.models.LogEntry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<?> createLogEntry() {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}

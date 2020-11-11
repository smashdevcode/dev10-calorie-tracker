package learn.calorietracker.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Objects;

public class LogEntry {
    private int id;

    // TODO Create a custom validation that restricts how many entries per type for a single day

    @NotNull(message = "LogEntry `loggedOn` is required.")
    @Past(message = "LogEntry `loggedOn` must be in the past.")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime loggedOn;

    @NotNull(message = "LogEntry `type` is required.")
    private LogEntryType type;

    @NotBlank(message = "LogEntry `description` is required.")
    @Size(max = 100, message = "LogEntry `description` cannot be longer than {max} characters.")
    private String description;

    @Min(value = 1, message = "LogEntry `calories` must be a positive number greater than or equal to {value}.")
    @Max(value = 1000, message = "LogEntry `calories` must be a positive number less than or equal to {value}.")
    private int calories;

    public LogEntry() {
    }

    public LogEntry(int id, LocalDateTime loggedOn, LogEntryType type, String description, int calories) {
        this.id = id;
        this.loggedOn = loggedOn;
        this.type = type;
        this.description = description;
        this.calories = calories;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getLoggedOn() {
        return loggedOn;
    }

    public void setLoggedOn(LocalDateTime loggedOn) {
        this.loggedOn = loggedOn;
    }

    public LogEntryType getType() {
        return type;
    }

    public void setType(LogEntryType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    @Override
    public String toString() {
        return "LogEntry{" +
                "id=" + id +
                ", loggedOn='" + loggedOn + '\'' +
                ", type=" + type +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogEntry logEntry = (LogEntry) o;
        return id == logEntry.id &&
                calories == logEntry.calories &&
                loggedOn.equals(logEntry.loggedOn) &&
                type == logEntry.type &&
                description.equals(logEntry.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, loggedOn, type, description, calories);
    }
}

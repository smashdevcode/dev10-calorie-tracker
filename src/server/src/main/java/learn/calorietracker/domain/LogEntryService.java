package learn.calorietracker.domain;

import learn.calorietracker.data.LogEntryRepository;
import learn.calorietracker.models.LogEntry;
import learn.calorietracker.models.LogEntryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

@Service
public class LogEntryService {
    private enum ValidationMode {
        CREATE, UPDATE;
    }

    @Autowired
    private Validator validator;

    private LogEntryRepository repository;

    public LogEntryService(LogEntryRepository repository) {
        this.repository = repository;
    }

    public List<LogEntry> findAll() {
        return repository.findAll();
    }

    public List<LogEntry> findByType(LogEntryType type) {
        return repository.findByType(type);
    }

    public LogEntry findById(int id) {
        return repository.findById(id);
    }

    public Result<LogEntry> create(LogEntry entry) {
        Result<LogEntry> result = validate(entry, ValidationMode.CREATE);

        if (result.isSuccess()) {
            entry = repository.create(entry);
            result.setPayload(entry);
        }

        return result;
    }

    public Result<LogEntry> update(LogEntry entry) {
        Result<LogEntry> result = validate(entry, ValidationMode.UPDATE);

        if (result.isSuccess()) {
            if (repository.update(entry)) {
                result.setPayload(entry);
            } else {
                result.addMessage("LogEntry id %s was not found.",
                        ResultType.NOT_FOUND, entry.getId());
            }
        }

        return result;
    }

    public Result<LogEntry> deleteById(int id) {
        Result<LogEntry> result = new Result<>();
        if (!repository.delete(id)) {
            result.addMessage("LogEntry id %s was not found.", ResultType.NOT_FOUND, id);
        }
        return result;
    }

    private Result<LogEntry> validate(LogEntry entry, ValidationMode validationMode) {
        Result<LogEntry> result = new Result<>();

        if (entry == null) {
            result.addMessage("LogEntry cannot be null.", ResultType.INVALID);
        } else if (validationMode == ValidationMode.CREATE && entry.getId() > 0) {
            result.addMessage("LogEntry `id` should not be set.", ResultType.INVALID);
        } else if (validationMode == ValidationMode.UPDATE && entry.getId() <= 0) {
            result.addMessage("LogEntry `id` is required.", ResultType.INVALID);
        }

        if (result.isSuccess()) {
            Set<ConstraintViolation<LogEntry>> violations = validator.validate(entry);

            if (!violations.isEmpty()) {
                for (ConstraintViolation<LogEntry> violation : violations) {
                    result.addMessage(violation.getMessage(), ResultType.INVALID);
                }
            }
        }

        return result;
    }
}

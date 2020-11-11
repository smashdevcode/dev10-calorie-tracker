package learn.calorietracker.data;

import learn.calorietracker.models.LogEntry;
import learn.calorietracker.models.LogEntryType;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class LogEntryJdbcTemplateRepository implements LogEntryRepository {
    private static final String LOG_ENTRY_COLUMN_NAMES =
            "log_entry_id, logged_on, log_entry_type_id, description, calories";

    private final JdbcTemplate jdbcTemplate;

    public LogEntryJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<LogEntry> mapper = (rs, i) -> {
        LogEntry result = new LogEntry();
        result.setId(rs.getInt("log_entry_id"));
        result.setLoggedOn(rs.getTimestamp("logged_on").toLocalDateTime());
        result.setType(LogEntryType.findByValue(rs.getInt("log_entry_type_id")));
        result.setDescription(rs.getString("description"));
        result.setCalories(rs.getInt("calories"));
        return result;
    };

    @Override
    public List<LogEntry> findAll() {
        final String sql = String.format("select %s from log_entry;", LOG_ENTRY_COLUMN_NAMES);
        return jdbcTemplate.query(sql, mapper);
    }

    @Override
    public List<LogEntry> findByType(LogEntryType type) {
        final String sql = String.format("select %s from log_entry " +
                "where log_entry_type_id = ?;", LOG_ENTRY_COLUMN_NAMES);
        return jdbcTemplate.query(sql, mapper, type.getValue());
    }

    @Override
    public LogEntry findById(int id) {
        final String sql = String.format("select %s from log_entry " +
                "where log_entry_id = ?;", LOG_ENTRY_COLUMN_NAMES);
        try {
            return jdbcTemplate.queryForObject(sql, mapper, id);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public LogEntry create(LogEntry entry) {
        final String sql = "insert into log_entry " +
                "(logged_on, log_entry_type_id, description, calories) " +
                "values (?, ?, ?, ?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setTimestamp(1, Timestamp.valueOf(entry.getLoggedOn()));
            statement.setInt(2, entry.getType().getValue());
            statement.setString(3, entry.getDescription());
            statement.setInt(4, entry.getCalories());
            return statement;
        }, keyHolder);

        if (rowsAffected == 0) {
            return null;
        }

        entry.setId(keyHolder.getKey().intValue());

        return entry;
    }

    @Override
    public boolean update(LogEntry entry) {
        final String sql = "update log_entry set " +
                "logged_on = ?, " +
                "log_entry_type_id = ?, " +
                "description = ?, " +
                "calories = ?" +
                "where log_entry_id = ?;";

        int rowsUpdated = jdbcTemplate.update(sql, Timestamp.valueOf(entry.getLoggedOn()),
                entry.getType().getValue(), entry.getDescription(), entry.getCalories());

        return rowsUpdated > 0;
    }

    @Override
    public boolean delete(int id) {
        final String sql = "delete from log_entry where log_entry_id = ?;";
        return jdbcTemplate.update(sql, id) > 0;
    }
}

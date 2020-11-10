package learn.calorietracker.data;

import learn.calorietracker.models.LogEntry;
import learn.calorietracker.models.LogEntryType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class LogEntryJdbcTemplateRepository implements LogEntryRepository {
    private final JdbcTemplate jdbcTemplate;

    public LogEntryJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<LogEntry> mapper = (rs, i) -> {
        LogEntry result = new LogEntry();
        result.setId(rs.getInt("log_entry_id"));
        result.setLoggedOn(rs.getString("logged_on"));
        result.setType(LogEntryType.findByValue(rs.getInt("log_entry_type_id")));
        result.setDescription(rs.getString("description"));
        result.setCalories(rs.getInt("calories"));
        return result;
    };

    @Override
    public List<LogEntry> findAll() throws DataAccessException {
        // JdbcTemplate... we call query...
        // Uses DataSource to get a connection to the database
        // Uses Connection to get a Statement (this is your query)
        // Uses the Statement to execute the query and to get a ResultSet

        final String sql = "select log_entry_id, logged_on, log_entry_type_id, description, calories from log_entry;";
        return jdbcTemplate.query(sql, mapper);
    }

    @Override
    public List<LogEntry> findByType(LogEntryType type) throws DataAccessException {
        final String sql = "select log_entry_id, logged_on, log_entry_type_id, description, calories " +
                "from log_entry where log_entry_type_id = ?;";
        return jdbcTemplate.query(sql, mapper, type.getValue());
    }

    @Override
    public LogEntry findById(int id) throws DataAccessException {
        return null;
    }

    @Override
    public LogEntry create(LogEntry entry) throws DataAccessException {
        final String sql = "insert into log_entry (logged_on, log_entry_type_id, description, calories) " +
                "values (?, ?, ?, ?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, entry.getLoggedOn());
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
}

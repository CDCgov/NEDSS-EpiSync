package gov.cdc.episync.publish.service;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

@Service
public class QueryExecutor {
    private final Environment environment;
    private final DataSource dataSource;

    public QueryExecutor(Environment environment, DataSource dataSource) {
        this.environment = environment;
        this.dataSource = dataSource;
    }

    public List<Map<String, Object>> execute(String profileName, String queryStr, Optional<String> params) {
        String query = environment.getProperty(profileName + "." + queryStr);
        if (query == null) {
            throw new IllegalArgumentException("Unknown profile: " + profileName);
        }

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            //statement.setDate(1, Date.valueOf(LocalDate.of(2023, 4, 30)));
            //statement.setDate(2, Date.valueOf(LocalDate.of(2023, 5, 1)));
            String[] parameters = params.map(s -> s.split(",")).orElse(ArrayUtils.EMPTY_STRING_ARRAY);
            for (int i = 0; i < parameters.length; i++) {
                String p = parameters[i];
                try {
                    statement.setDate(i + 1, Date.valueOf(LocalDate.parse(p)));
                } catch (Exception ignored) {
                    statement.setObject(i + 1, p);
                }
            }

            try (ResultSet resultSet = statement.executeQuery()) {
                List<Map<String, Object>> result = new ArrayList<>();
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();
                while (resultSet.next()) {
                    Map<String, Object> row = new LinkedHashMap<>();
                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = metaData.getColumnName(i);
                        Object columnValue = resultSet.getObject(i);
                        row.put(columnName, columnValue);
                    }
                    result.add(row);
                }
                return result;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to execute query: " + query, e);
        }
    }
}

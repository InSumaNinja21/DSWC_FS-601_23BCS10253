package Day5.Q2;

import java.sql.*;

interface RegistrationManager {
    void enrollAtRiskStudents();
}

abstract class DatabaseConnectionProvider {

    private final String URL = "jdbc:postgresql://localhost:3012/ixo";
    private final String USERNAME = "postgres";
    private final String PASSWORD = "password";

    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}

class EdixoRegistrationRepository extends DatabaseConnectionProvider implements RegistrationManager {

    private static final String FIND_AT_RISK =
            "SELECT s.student_id, s.full_name " +
            "FROM students s " +
            "LEFT JOIN course_registrations cr " +
            "ON s.student_id = cr.student_id " +
            "WHERE cr.student_id IS NULL";

    private static final String INSERT_ORIENTATION =
            "INSERT INTO course_registrations(student_id, course_code, semester) VALUES (?, ?, ?)";

    @Override
    public void enrollAtRiskStudents() {

        try (
                Connection conn = getConnection();
                PreparedStatement selectStmt = conn.prepareStatement(FIND_AT_RISK);
                ResultSet rs = selectStmt.executeQuery();
                PreparedStatement insertStmt = conn.prepareStatement(INSERT_ORIENTATION)
        ) {

            conn.setAutoCommit(false);

            int batchCount = 0;
            int totalInserted = 0;

            while (rs.next()) {

                int studentId = rs.getInt("student_id");

                insertStmt.setInt(1, studentId);
                insertStmt.setString(2, "ORIENTATION101");
                insertStmt.setString(3, "FALL2026");

                insertStmt.addBatch();

                batchCount++;

                if (batchCount % 1000 == 0) {
                    insertStmt.executeBatch();
                    totalInserted += batchCount;
                    batchCount = 0;
                }
            }

            if (batchCount > 0) {
                insertStmt.executeBatch();
                totalInserted += batchCount;
            }

            conn.commit();

            System.out.println("Total students enrolled: " + totalInserted);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

public class Main {

    public static void main(String[] args) {

        RegistrationManager manager = new EdixoRegistrationRepository();

        manager.enrollAtRiskStudents();
    }
}

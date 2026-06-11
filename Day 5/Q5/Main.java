package Day5.Q5;

import java.sql.*;

interface QueueWorker {
    void processNextJob();
}

abstract class EnterpriseConnectionFactory {

    private final String URL = "jdbc:postgresql://localhost:5432/company";
    private final String USERNAME = "postgres";
    private final String PASSWORD = "password";

    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}

class JobQueueRepository extends EnterpriseConnectionFactory implements QueueWorker {

    private static final String SELECT_JOB =
            "SELECT bj.job_id, d.dept_name, bj.created_at " +
            "FROM background_jobs bj " +
            "INNER JOIN departments d ON bj.dept_id = d.dept_id " +
            "WHERE bj.status = 'PENDING' " +
            "AND d.dept_name = ? " +
            "ORDER BY bj.created_at ASC " +
            "LIMIT 1 " +
            "FOR UPDATE SKIP LOCKED";

    private static final String UPDATE_JOB =
            "UPDATE background_jobs SET status = 'PROCESSING' WHERE job_id = ?";

    @Override
    public void processNextJob() {

        Connection conn = null;

        try {

            conn = getConnection();

            conn.setAutoCommit(false);

            try (
                    PreparedStatement selectStmt = conn.prepareStatement(SELECT_JOB);
                    PreparedStatement updateStmt = conn.prepareStatement(UPDATE_JOB)
            ) {

                selectStmt.setString(1, "Engineering");

                try (ResultSet rs = selectStmt.executeQuery()) {

                    if (rs.next()) {

                        long jobId = rs.getLong("job_id");

                        updateStmt.setLong(1, jobId);
                        updateStmt.executeUpdate();

                        conn.commit();

                        System.out.println("Job " + jobId + " moved to PROCESSING");

                    } else {

                        conn.rollback();

                        System.out.println("No pending jobs available");
                    }
                }
            }

        } catch (Exception e) {

            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            e.printStackTrace();

        } finally {

            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

public class Main {

    public static void main(String[] args) {

        QueueWorker worker = new JobQueueRepository();

        worker.processNextJob();
    }
}

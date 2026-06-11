package Day5.Q1;
import java.sql.*;
interface ReportGenerator {
    void printDelayedReport();
}
abstract class DatabaseRepository {

    private final String URL = "jdbc:postgresql://localhost:3032/cargo";
    private final String USERNAME = "postgres";
    private final String PASSWORD = "password";

    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
class LogisticsRepository extends DatabaseRepository implements ReportGenerator {
    private static final String QUERY =
            "SELECT s.shipment_id, c.company_name, s.dispatch_date " +
            "FROM shipments s " +
            "JOIN couriers c ON s.courier_id = c.courier_id " +
            "WHERE s.status = ? " +
            "ORDER BY s.dispatch_date DESC";

    @Override
    public void printDelayedReport() {

        try (
                Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(QUERY)
        ) {

            ps.setString(1, "DELAYED");

            try (ResultSet rs = ps.executeQuery()) {

                System.out.println("===== DELAYED SHIPMENTS REPORT =====");

                while (rs.next()) {

                    int shipmentId = rs.getInt("shipment_id");
                    String companyName = rs.getString("company_name");
                    Date dispatchDate = rs.getDate("dispatch_date");

                    System.out.println(
                            "Shipment ID: " + shipmentId +
                            ", Courier: " + companyName +
                            ", Dispatch Date: " + dispatchDate
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

public class Main {

    public static void main(String[] args) {

        ReportGenerator report = new LogisticsRepository();

        report.printDelayedReport();
    }
}

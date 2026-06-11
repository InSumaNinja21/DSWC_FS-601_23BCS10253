package Day5.Q4;

import java.sql.*;
import java.time.LocalDateTime;

interface TelemetryService {
    void printLatestLocations();
}

abstract class FleetDatabaseConnection {

    private final String URL = "jdbc:postgresql://localhost:5432/fleetdb";
    private final String USERNAME = "postgres";
    private final String PASSWORD = "password";

    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}

class FleetTelemetryRepository extends FleetDatabaseConnection implements TelemetryService {

    private static final String QUERY =
            "SELECT r.rider_id, r.rider_name, r.bike_model, p.latitude, p.longitude, p.recorded_at " +
            "FROM riders r " +
            "INNER JOIN ( " +
            "SELECT rider_id, latitude, longitude, recorded_at, " +
            "ROW_NUMBER() OVER(PARTITION BY rider_id ORDER BY recorded_at DESC) AS rn " +
            "FROM gps_pings ) p " +
            "ON r.rider_id = p.rider_id " +
            "WHERE p.rn = 1";

    @Override
    public void printLatestLocations() {

        try (
                Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(QUERY);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                long riderId = rs.getLong("rider_id");
                String riderName = rs.getString("rider_name");
                String bikeModel = rs.getString("bike_model");
                double latitude = rs.getDouble("latitude");
                double longitude = rs.getDouble("longitude");

                LocalDateTime recordedAt =
                        rs.getObject("recorded_at", LocalDateTime.class);

                System.out.println(
                        "Rider ID: " + riderId +
                        ", Name: " + riderName +
                        ", Bike: " + bikeModel +
                        ", Latitude: " + latitude +
                        ", Longitude: " + longitude +
                        ", Time: " + recordedAt
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

public class Main {

    public static void main(String[] args) {

        TelemetryService service =
                new FleetTelemetryRepository();

        service.printLatestLocations();
    }
}

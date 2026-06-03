class HardwareLockException extends Exception {
    public HardwareLockException(String message) {
        super(message);
    }
}

class SensorCorruptionException extends RuntimeException {
    public SensorCorruptionException(String message) {
        super(message);
    }
}

class TelemetryStream implements AutoCloseable {

    public void readData() {
        System.out.println("Reading telemetry data...");
    }

    @Override
    public void close() {
        System.out.println("Telemetry stream closed.");
    }
}

class TelemetryParser {

    public void parse(boolean fileLocked, int temperature)
            throws HardwareLockException {

        try (TelemetryStream stream = new TelemetryStream()) {

            stream.readData();

            if (fileLocked) {
                throw new HardwareLockException(
                        "Telemetry file is locked by the OS.");
            }

            if (temperature > 100 || temperature < -10) {
                throw new SensorCorruptionException(
                        "Impossible temperature value: " + temperature);
            }

            System.out.println("Telemetry parsed successfully.");
        }
    }
}

public class Main {
    public static void main(String[] args) {

        TelemetryParser parser = new TelemetryParser();

        try {
            parser.parse(false, 25);
            parser.parse(false, 500);
            parser.parse(true, 20);
        } catch (HardwareLockException e) {
            System.out.println("Checked Exception: " + e.getMessage());
        } catch (SensorCorruptionException e) {
            System.out.println("Unchecked Exception: " + e.getMessage());
        }
    }
}
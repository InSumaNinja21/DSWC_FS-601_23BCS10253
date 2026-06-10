import java.util.*;
abstract class EngineLog {
    protected String timestamp;
    protected double coreTemperature;
    protected boolean isAnomaly;
    public EngineLog(String timestamp, double coreTemperature, boolean isAnomaly) {
        this.timestamp = timestamp;
        this.coreTemperature = coreTemperature;
        this.isAnomaly = isAnomaly;
    }
    public String getTimestamp() {
        return timestamp;
    }
    public double getCoreTemperature() {
        return coreTemperature;
    }
    public boolean isAnomaly() {
        return isAnomaly;
    }
}
class NominalLog extends EngineLog {
    public NominalLog(String timestamp, double coreTemperature, boolean isAnomaly) {
        super(timestamp, coreTemperature, isAnomaly);
    }
}
class CriticalLog extends EngineLog {
    private String errorCode;
    public CriticalLog(String timestamp, double coreTemperature, boolean isAnomaly, String errorCode) {
        super(timestamp, coreTemperature, isAnomaly);
        this.errorCode = errorCode;
    }
    public String getErrorCode() {
        return errorCode;
    }
}
@FunctionalInterface
interface LogAuditor {
    boolean audit(EngineLog log);
}
@FunctionalInterface
interface HeatExtractor {
    double extract(EngineLog log);
}
class TelemetryProcessor {
    public double getPeakCriticalTemp(List<EngineLog> logs, LogAuditor auditor, HeatExtractor extractor) {
        return logs.stream().filter(auditor::audit).mapToDouble(extractor::extract).max().orElse(0.0);
    }
}
public class Main {
    public static void main(String[] args) {
        List<EngineLog> logs = new ArrayList<>();
        logs.add(new NominalLog("10:00", 450.5, false));
        logs.add(new NominalLog("10:01", 520.0, true));
        logs.add(new CriticalLog("10:02", 780.5, false, "OVERHEAT"));
        logs.add(new CriticalLog("10:03", 690.0, false, "FUEL_LOW"));
        logs.add(new CriticalLog("10:04", 910.5, true, "SENSOR_FAIL"));
        LogAuditor auditor = log -> log.isAnomaly() || (log instanceof CriticalLog && ((CriticalLog)log).getErrorCode().equals("OVERHEAT"));
        HeatExtractor extractor = log -> log.getCoreTemperature();
        TelemetryProcessor processor = new TelemetryProcessor();
        double peakTemp = processor.getPeakCriticalTemp(logs, auditor, extractor);
        System.out.println("Peak Critical Temperature: " + peakTemp);
    }
}

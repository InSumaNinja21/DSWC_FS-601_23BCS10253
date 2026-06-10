import java.util.*;
import java.util.stream.Collectors;
abstract class DNASample {
    protected String sampleId;
    protected double purityPercentage;
    public DNASample(String sampleId, double purityPercentage) {
        this.sampleId = sampleId;
        this.purityPercentage = purityPercentage;
    }
    public String getSampleId() {
        return sampleId;
    }
    public double getPurityPercentage() {
        return purityPercentage;
    }
}
class HumanSample extends DNASample {
    private String bloodType;
    public HumanSample(String sampleId, double purityPercentage, String bloodType) {
        super(sampleId, purityPercentage);
        this.bloodType = bloodType;
    }
    public String getBloodType() {
        return bloodType;
    }
}
class AlienSample extends DNASample {
    private boolean isSiliconBased;
    public AlienSample(String sampleId, double purityPercentage, boolean isSiliconBased) {
        super(sampleId, purityPercentage);
        this.isSiliconBased = isSiliconBased;
    }
    public boolean isSiliconBased() {
        return isSiliconBased;
    }
}
@FunctionalInterface
interface ViabilityChecker {
    boolean check(DNASample sample);
}
@FunctionalInterface
interface GenomeMapper {
    String map(DNASample sample);
}
class Sequencer {
    public Map<String, List<String>> classifyGenomes(List<DNASample> samples, ViabilityChecker checker, GenomeMapper mapper) {
        return samples.stream().filter(checker::check).collect(Collectors.groupingBy(sample -> sample.getClass().getSimpleName(), Collectors.mapping(mapper::map, Collectors.toList())));
    }
}
public class Main {
    public static void main(String[] args) {
        List<DNASample> samples = new ArrayList<>();
        samples.add(new HumanSample("001", 95.5, "O+"));
        samples.add(new HumanSample("002", 70.0, "A+"));
        samples.add(new HumanSample("003", 88.0, "B-"));
        samples.add(new AlienSample("004", 92.0, true));
        samples.add(new AlienSample("005", 60.0, false));
        samples.add(new AlienSample("006", 85.0, false));
        ViabilityChecker checker = sample -> sample.getPurityPercentage() >= 80.0;
        GenomeMapper mapper = sample -> sample instanceof HumanSample ? "ID: " + sample.getSampleId() + " (Type: " + ((HumanSample)sample).getBloodType() + ")" : "ID: " + sample.getSampleId() + " (Silicon: " + ((AlienSample)sample).isSiliconBased() + ")";
        Sequencer sequencer = new Sequencer();
        Map<String, List<String>> result = sequencer.classifyGenomes(samples, checker, mapper);
        result.forEach((key, value) -> {
            System.out.println(key + ":");
            value.forEach(System.out::println);
            System.out.println();
        });
    }
}

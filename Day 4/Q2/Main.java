import java.util.*;
import java.util.stream.Collectors;
abstract class MemoryEngram {
    protected String engramId;
    protected double clarityScore;
    protected boolean isCorrupted;
    public MemoryEngram(String engramId, double clarityScore, boolean isCorrupted) {
        this.engramId = engramId;
        this.clarityScore = clarityScore;
        this.isCorrupted = isCorrupted;
    }
    public String getEngramId() {
        return engramId;
    }
    public double getClarityScore() {
        return clarityScore;
    }
    public boolean isCorrupted() {
        return isCorrupted;
    }
}
class StandardEngram extends MemoryEngram {
    public StandardEngram(String engramId, double clarityScore, boolean isCorrupted) {
        super(engramId, clarityScore, isCorrupted);
    }
}
class ClassifiedEngram extends MemoryEngram {
    private int securityClearanceLevel;
    public ClassifiedEngram(String engramId, double clarityScore, boolean isCorrupted, int securityClearanceLevel) {
        super(engramId, clarityScore, isCorrupted);
        this.securityClearanceLevel = securityClearanceLevel;
    }
    public int getSecurityClearanceLevel() {
        return securityClearanceLevel;
    }
}
@FunctionalInterface
interface EngramValidator {
    boolean validate(MemoryEngram engram);
}
@FunctionalInterface
interface EngramTranslator {
    String translate(MemoryEngram engram);
}
class CortexProcessor {
    public List<String> processMemories(List<MemoryEngram> engrams, EngramValidator validator, EngramTranslator translator) {
        return engrams.stream().filter(validator::validate).filter(e -> e.getClarityScore() >= 50.0).map(translator::translate).collect(Collectors.toList());
    }
}
public class Main {
    public static void main(String[] args) {
        List<MemoryEngram> engrams = new ArrayList<>();
        engrams.add(new StandardEngram("ENG101", 85.5, false));
        engrams.add(new StandardEngram("ENG102", 45.0, false));
        engrams.add(new ClassifiedEngram("ENG103", 90.0, false, 5));
        engrams.add(new ClassifiedEngram("ENG104", 75.0, false, 2));
        engrams.add(new StandardEngram("ENG105", 88.0, true));
        EngramValidator validator = e -> !e.isCorrupted() && !(e instanceof ClassifiedEngram && ((ClassifiedEngram)e).getSecurityClearanceLevel() > 3);
        EngramTranslator translator = e -> "ENGRAM-" + e.getEngramId() + " | CLARITY: " + e.getClarityScore() + "%";
        CortexProcessor processor = new CortexProcessor();
        List<String> result = processor.processMemories(engrams, validator, translator);
        result.forEach(System.out::println);
    }
}

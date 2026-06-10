import java.util.*;
import java.util.stream.Collectors;
abstract class TemporalEntity {
    protected String entityName;
    protected int originYear;
    public TemporalEntity(String entityName, int originYear) {
        this.entityName = entityName;
        this.originYear = originYear;
    }
    public String getEntityName() {
        return entityName;
    }
    public int getOriginYear() {
        return originYear;
    }
}
class HumanEntity extends TemporalEntity {
    public HumanEntity(String entityName, int originYear) {
        super(entityName, originYear);
    }
}
class ArtifactEntity extends TemporalEntity {
    private boolean isRadioactive;
    public ArtifactEntity(String entityName, int originYear, boolean isRadioactive) {
        super(entityName, originYear);
        this.isRadioactive = isRadioactive;
    }
    public boolean isRadioactive() {
        return isRadioactive;
    }
}
class HistoricalEvent {
    private List<TemporalEntity> entities;
    private int eventYear;
    public HistoricalEvent(int eventYear, List<TemporalEntity> entities) {
        this.eventYear = eventYear;
        this.entities = entities;
    }
    public int getEventYear() {
        return eventYear;
    }
    public List<TemporalEntity> getEntities() {
        return entities;
    }
}
@FunctionalInterface
interface ParadoxChecker {
    boolean check(TemporalEntity entity, int eventYear);
}
@FunctionalInterface
interface ThreatMapper {
    String map(TemporalEntity entity);
}
class ParadoxAnalyzer {
    public List<String> detectParadoxes(List<HistoricalEvent> timeline, ParadoxChecker checker, ThreatMapper mapper) {
        return timeline.stream().flatMap(event -> event.getEntities().stream().map(entity -> new AbstractMap.SimpleEntry<>(entity, event.getEventYear()))).filter(entry -> checker.check(entry.getKey(), entry.getValue())).map(entry -> mapper.map(entry.getKey())).collect(Collectors.toList());
    }
}
public class Main {
    public static void main(String[] args) {
        List<HistoricalEvent> timeline = new ArrayList<>();
        timeline.add(new HistoricalEvent(1800, Arrays.asList(
                new HumanEntity("John", 1750),
                new HumanEntity("Alex", 2025),
                new ArtifactEntity("Quantum Device", 2500, true)
        )));
        timeline.add(new HistoricalEvent(1950, Arrays.asList(
                new HumanEntity("Sarah", 1940),
                new ArtifactEntity("Nano Chip", 2050, false)
        )));
        ParadoxChecker checker = (entity, eventYear) -> entity.getOriginYear() > eventYear;
        ThreatMapper mapper = entity -> entity.getEntityName() + " detected out of time!";
        ParadoxAnalyzer analyzer = new ParadoxAnalyzer();
        List<String> paradoxes = analyzer.detectParadoxes(timeline, checker, mapper);
        paradoxes.forEach(System.out::println);
    }
}

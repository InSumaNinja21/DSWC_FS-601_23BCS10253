abstract class DeliveryDrone{
    protected String droneId;
    public DeliveryDrone(String droneId){
        this.droneId = droneId;
    }
    public abstract void deliverPackage();
}
interface Airborne{
    public abstract void flyToDestination();
    default void requestAirTrafficClearance(){
        System.out.println("Requesting air traffic clearance...");
    }
}
interface GroundBased{
    public abstract void navigateSidewalks();
}
class Quadcopter extends DeliveryDrone implements Airborne{
    public Quadcopter(String droneId) {
        super(droneId);
    }
    @Override
    public void deliverPackage() {
        System.out.println("Quadcopter delivering package...");
    }
    @Override
    public void flyToDestination() {
        System.out.println("Quadcopter flying to destination...");
    }
}
class CityRover extends DeliveryDrone implements GroundBased{
    public CityRover(String droneId) {
        super(droneId);
    }
    @Override
    public void deliverPackage() {
        System.out.println("CityRover delivering package...");
    }
    @Override
    public void navigateSidewalks() {
        System.out.println("CityRover navigating sidewalks...");
    }
}
class HybridVTOL extends DeliveryDrone implements Airborne, GroundBased{
    public HybridVTOL(String droneId) {
        super(droneId);
    }
    @Override
    public void deliverPackage() {
        System.out.println("Hybrid VTOL delivering package...");
    }
    @Override
    public void flyToDestination() {
        System.out.println("Hybrid VTOL flying to destination...");
    }
    @Override
    public void navigateSidewalks() {
        System.out.println("Hybrid VTOL navigating sidewalks...");
    }
}
public class Main {
    public static void main(String[] args) {
        Quadcopter quadcopter = new Quadcopter("QD-001");
        CityRover cityRover = new CityRover("CR-001");
        HybridVTOL hybridVTOL = new HybridVTOL("HV-001");

        quadcopter.deliverPackage();
        quadcopter.flyToDestination();
        quadcopter.requestAirTrafficClearance();

        cityRover.deliverPackage();
        cityRover.navigateSidewalks();

        hybridVTOL.deliverPackage();
        hybridVTOL.flyToDestination();
        hybridVTOL.navigateSidewalks();
    }
}
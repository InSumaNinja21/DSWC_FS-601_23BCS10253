abstract class SmartDevice{
    protected String deviceId;
    protected String deviceName;
    public SmartDevice(String deviceId, String deviceName) {
        this.deviceId = deviceId;
        this.deviceName = deviceName;
    }
    public abstract void runDiagnostic();
}
interface BatteryOperated{
    int getBatteryLevel();
    void triggerRechargeAlert();
}
class SmartLight extends SmartDevice{

    public SmartLight(String deviceId, String deviceName) {
        super(deviceId, deviceName);
    }
    @Override
    public void runDiagnostic() {
        System.out.println("Running diagnostic for " + deviceName);
    }
}
class SmartCamera extends SmartDevice implements BatteryOperated{
    private int batteryLevel;
    public SmartCamera(String deviceId, String deviceName, int batteryLevel){
        super(deviceId, deviceName);
        this.batteryLevel = batteryLevel;
    }
    @Override
        public int getBatteryLevel(){
            return batteryLevel;
        }
    @Override
        public void triggerRechargeAlert(){
            System.out.println("Battery less than 20");
        }
    @Override
        public void runDiagnostic() {
            System.out.println("Running diagnostic for " + deviceName);
        }
    }
class SmartLock extends SmartDevice implements BatteryOperated{
    private int batteryLevel;
    public SmartLock(String deviceId, String deviceName, int batteryLevel){
        super(deviceId, deviceName);
        this.batteryLevel = batteryLevel;
    }
    @Override
        public int getBatteryLevel(){
            return batteryLevel;
        }
    @Override
        public void triggerRechargeAlert(){
            System.out.println("Battery less than 20");
        }
    @Override
        public void runDiagnostic(){
            System.out.println("Running diagnostic for " + deviceName);
        }
}
class HomeHub{
    public void executeNightlyRoutine(SmartDevice[] devices){
        for(int i=0;i<devices.length;i++){
            devices[i].runDiagnostic();
            if(devices[i] instanceof BatteryOperated){
                BatteryOperated batteryDevice=(BatteryOperated) devices[i];
                if(batteryDevice.getBatteryLevel()<20){
                    batteryDevice.triggerRechargeAlert();
                }
            }
        }
    }
}
public class Main{
    public static void main(String[] args) {
        SmartDevice[] devices=new SmartDevice[3];
        devices[0]=new SmartLight("L1","Light");
        devices[1]=new SmartCamera("C1","Camera",80);
        devices[2]=new SmartLock("K1","Lock",15);
        HomeHub homeHub=new HomeHub();
        homeHub.executeNightlyRoutine(devices);
    }
}
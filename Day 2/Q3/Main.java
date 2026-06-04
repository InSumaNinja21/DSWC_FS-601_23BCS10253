enum DoorState{
    OPEN, CLOSED,LOCKED
}
class IllegalStateTransitionException extends RuntimeException{
    public IllegalStateTransitionException(String message){
        super(message);
    }
}
class VaultDoor{
    private DoorState state;
    public VaultDoor(){
        state= DoorState.OPEN;
    }
    public void closeDoor(){
        if(state== DoorState.OPEN){
            state= DoorState.CLOSED;
            System.out.println("Door Closed.");
        }
        else{
            System.out.println("Door is already closed or Locked.");
        }
    }
    public void lockDoor(){
        if(state== DoorState.OPEN){
            throw new IllegalStateTransitionException("Cannot lock the door while it is Open. Clsoe it first.");
        }
        if(state == DoorState.CLOSED){
            state= DoorState.LOCKED;
            System.out.println("Door Locked");
        }
        else{
            System.out.println("Door is already locked.");
        }
    }
    public void unlockDoor(){
        if(state== DoorState.LOCKED){
            state= DoorState.CLOSED;
            System.out.println("Door unlocked.");
        }
        else{
            System.out.println("Door is not locked.");
        }
    }
    public void displayState(){
        System.out.println("Current State:" + state);
    }
}
public class Main {
    public static void main(String[] args){
        VaultDoor vault= new VaultDoor();
        try{
            vault.displayState();
            vault.lockDoor();
        }
        catch( IllegalStateTransitionException e){
            System.out.print("Exception: "+ e.getMessage());
        }
        vault.closeDoor();
        vault.lockDoor();
        vault.displayState();
        vault.unlockDoor();
        vault.displayState();
    }
}
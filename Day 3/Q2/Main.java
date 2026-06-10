package Day3.Q2;

import java.util.*;

enum TriageLevel{
    CRITICAL,
    URGENT,
    STABLE
}

class Patient implements Comparable<Patient>{
    private String name;
    private TriageLevel severity;
    private long arrivalTime;

    public Patient(String name, TriageLevel severity, long arrivalTime){
        this.name = name;
        this.severity = severity;
        this.arrivalTime = arrivalTime;
    }

    public String getName(){
        return name;
    }

    public TriageLevel getSeverity(){
        return severity;
    }

    public long getArrivalTime(){
        return arrivalTime;
    }

    @Override
    public int compareTo(Patient other){
        if(this.severity !=other.severity){
            return Integer.compare(this.severity.ordinal(), other.severity.ordinal());
        }

        return Long.compare(this.arrivalTime, other.arrivalTime);
    }

    @Override
    public String toString(){
        return "Name: "+ name + ", Severity: "+ severity+ ", Arrival Time: "+ arrivalTime;
    }
}

class TriageManager{
    private PriorityQueue<Patient>waitingRoom;

    public TriageManager(){
        waitingRoom= new PriorityQueue<>();
    }

    //admitting a patient
    public void admitPatient(Patient p){
        waitingRoom.offer(p);
        System.out.println(p.getName()+ " admitted successfully.");
    }

    public Patient getNextPatient() {
        if (waitingRoom.isEmpty()) {
            return null;
        }
        return waitingRoom.poll();
    }

    public boolean isEmpty() {
        return waitingRoom.isEmpty();
    }
}
public class Main {
    public static void main(String[] args) {
        TriageManager manager= new TriageManager();

        manager.admitPatient(new Patient("Supriya", TriageLevel.STABLE, 1005));
        manager.admitPatient(new Patient("Bob", TriageLevel.CRITICAL, 1001));
        manager.admitPatient(new Patient("Charlie", TriageLevel.URGENT, 1002));
        manager.admitPatient(new Patient("David", TriageLevel.CRITICAL, 1000));
        manager.admitPatient(new Patient("Eva", TriageLevel.URGENT, 999));

        System.out.println("\nPatients treated in priority order:\n");

        while (!manager.isEmpty()) {
            Patient next = manager.getNextPatient();
            System.out.println(next);
        }
    }
}

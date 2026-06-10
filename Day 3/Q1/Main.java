package Day3.Q1;

import java.util.*;

class Passenger{
    String passportNumber;
    String fullName;
    String nationality;

    public Passenger(String passportNumber, String fullName, String nationality){
        this.passportNumber = passportNumber;
        this.fullName = fullName;
        this.nationality = nationality;
    }


    //get methods
    public String getPassportNumber(){
        return passportNumber;
    }

    public String getFullname(){
        return fullName;
    }

    public String getNationality(){
        return nationality;
    }

    @Override
    public boolean equals(Object obj){
        if(this==obj){
            return true;
        }
        if(obj==null || getClass()!=obj.getClass()){
            return false;
        }

        Passenger other= (Passenger)obj;

        return passportNumber.equals(other.passportNumber) && nationality.equals(other.nationality);
    }

    @Override
    public int hashCode(){
        return Objects.hash(passportNumber,nationality);
    }

    @Override
    public String toString(){
        return "Passport Number: "+passportNumber+ ", Name: "+ fullName+ ", Nationality: "+ nationality;
    }
}

class ManifestManager{
    //no fly list-> we will be using set here
    private Set<Passenger>globalNoFlyList;
    
    //flight number->passenger list
    private Map<String, List<Passenger>>flightRosters;

    //Passenger ->flight Number
    private Map<Passenger, String>globalPassengerDirectory;

    public ManifestManager(){
        globalNoFlyList= new HashSet<>();
        flightRosters= new HashMap<>();
        globalPassengerDirectory= new HashMap<>();
    }

    //now additiona and check functions

    public void addToNoFlyList(Passenger p){
        globalNoFlyList.add(p);
    }

    //storing passenger in flight rosters
    public void checkInPassenger(String flightNumber, Passenger p){
        flightRosters.putIfAbsent(flightNumber, new ArrayList<>());
        flightRosters.get(flightNumber).add(p);
    }

    //Main check in Process
    public boolean processCheckIn(String flightNumber, Passenger p){
        if(globalNoFlyList.contains(p)){
            System.out.println("\n Check in Rejected");
            System.out.println(p.getFullname() + " is on the No-Fly List.");
            return false;
        }

        //add to flight roster
        checkInPassenger(flightNumber, p);

        //add to global Directory
        globalPassengerDirectory.put(p,flightNumber);

        System.out.println("\nCheck-In Successful for "+ p.getFullname()+ " on Flight "+ flightNumber);
        return true;
    }

    //find the passenger's fllight

    public String locatePassengerFlight(Passenger p){
        return globalPassengerDirectory.get(p);
    }

    // display the flight roster
    public void displayFlightRoster(String flightNumber){
        List<Passenger> roster= flightRosters.get(flightNumber);

        System.out.println("\n===== Flight Roster: " + flightNumber + " =====");

        if(roster== null || roster.isEmpty()){
            System.out.println("No passengers checked-in.");
            return;
        }

        for(Passenger passenger : roster){
            System.out.println(passenger);
        }
    }

    public void displayNoFlyList(){
        System.out.println("\n===== Global No-Fly List =====");
        if(globalNoFlyList.isEmpty()){
            System.out.println("No passenger in No-Fly List.");
            return;
        }

        for(Passenger p: globalNoFlyList){
            System.out.println(p);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        ManifestManager manager= new ManifestManager();

        Passenger p1= new Passenger("P1001","Supriya", "India");
        Passenger p2= new Passenger("P1002","Saksham", "India");
        Passenger p3= new Passenger("P1003","James", "USA");
        Passenger p4= new Passenger("P1004","Andrei", "Russia");

        manager.addToNoFlyList(p3);
        manager.displayNoFlyList();

        manager.processCheckIn("AI101", p1);
        manager.processCheckIn("AI101", p2);
        manager.processCheckIn("AI101", p3);
        manager.processCheckIn("AI202", p4);

        // Display Flight Rosters
        manager.displayFlightRoster("AI101");
        manager.displayFlightRoster("AI202");

        // Search Passenger Flight
        Passenger searchAlice = new Passenger("P1001","Supriya 1", "USA");

        String flight = manager.locatePassengerFlight(searchAlice);

        System.out.println("\n===== Passenger Lookup =====");

        if (flight != null) {
            System.out.println("Passenger found on Flight: " + flight);
        } else {
            System.out.println("Passenger not checked in.");
        }
    }
}

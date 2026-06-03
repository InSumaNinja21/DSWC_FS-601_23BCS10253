import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    // Visibility across all threads
    private static volatile boolean emergencyAbort = false;

    // Atomic counter to prevent race conditions
    private static AtomicInteger totalDronesReturned = new AtomicInteger(0);

    static class Drone extends Thread {

        @Override
        public void run() {

            // Check emergency flag
            if (emergencyAbort) {
                System.out.println(getName()
                        + " : Emergency detected! Altering route.");
                return;
            }

            // Simulate successful landing
            int count = totalDronesReturned.incrementAndGet();

            System.out.println(getName()
                    + " landed. Total returned = " + count);
        }
    }

    public static void main(String[] args) {

        // Create 10,000 drone threads
        for (int i = 1; i <= 10000; i++) {

            Drone drone = new Drone();
            drone.setName("Drone-" + i);
            drone.start();

            // Simulate storm detection halfway through
            if (i == 5000) {
                emergencyAbort = true;
                System.out.println(
                    "\n*** STORM DETECTED - EMERGENCY ABORT ACTIVATED ***\n"
                );
            }
        }

        System.out.println(
            "\nFinal Returned Drones Count: "
            + totalDronesReturned.get()
        );
    }
}
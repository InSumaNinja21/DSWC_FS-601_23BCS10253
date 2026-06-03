abstract class SpaceVessel {
    protected short id;
    protected boolean active;
    protected char category;

    public SpaceVessel(short id, boolean active, char category) {
        this.id = id;
        this.active = active;
        this.category = category;
    }

    public void showDetails() {
        System.out.println("Ship ID: " + id);
        System.out.println("Status: " + active);
        System.out.println("Class: " + category);
    }
}

class MiningShip extends SpaceVessel {
    private float[][] cargo;

    public MiningShip(short id, boolean active, char category, float[][] cargo) {
        super(id, active, category);
        this.cargo = cargo;
    }

    public float calculateTotalOreWeight() {
        float sum = 0;

        for (int r = 0; r < cargo.length; r++) {
            for (int c = 0; c < cargo[r].length; c++) {
                sum += cargo[r][c];
            }
        }

        return sum;
    }

    public float findHeaviestContainer() {
        float max = cargo[0][0];

        for (int r = 0; r < cargo.length; r++) {
            for (int c = 0; c < cargo[r].length; c++) {
                if (cargo[r][c] > max) {
                    max = cargo[r][c];
                }
            }
        }

        return max;
    }
}

public class Main {
    public static void main(String[] args) {

        float[][] load1 = {
                {1200.5f, 3400.2f, 2200.8f},
                {5000.0f, 4100.6f, 1800.3f}
        };

        float[][] load2 = {
                {1500.0f, 2600.0f},
                {8200.5f, 3100.0f}
        };

        SpaceVessel[] fleet = new SpaceVessel[2];

        fleet[0] = new MiningShip((short) 12345, true, 'A', load1);
        fleet[1] = new MiningShip((short) 23456, false, 'B', load2);

        long fleetWorth = 45000000000L;

        System.out.println("Fleet Value: " + fleetWorth);

        for (int i = 0; i < fleet.length; i++) {
            fleet[i].showDetails();

            MiningShip vessel = (MiningShip) fleet[i];

            System.out.println("Total Weight: " + vessel.calculateTotalOreWeight());
            System.out.println("Heaviest Container: " + vessel.findHeaviestContainer());
            System.out.println();
        }
    }
}
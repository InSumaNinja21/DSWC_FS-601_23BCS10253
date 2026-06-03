class PowerNode {
    private byte sectorStates;

    public void turnOnSector(int sectorIndex) {
        sectorStates = (byte) (sectorStates | (1 << sectorIndex));
    }

    public void turnOffSector(int sectorIndex) {
        sectorStates = (byte) (sectorStates & ~(1 << sectorIndex));
    }

    public boolean isSectorOn(int sectorIndex) {
        return (sectorStates & (1 << sectorIndex)) != 0;
    }

    public void displayStates() {
        for (int i = 7; i >= 0; i--) {
            System.out.print(((sectorStates >> i) & 1));
        }
        System.out.println();
    }
}

public class Main {
    public static void main(String[] args) {
        PowerNode node = new PowerNode();

        node.turnOnSector(0);
        node.turnOnSector(3);
        node.turnOnSector(7);

        node.displayStates();

        System.out.println(node.isSectorOn(3));
        System.out.println(node.isSectorOn(2));

        node.turnOffSector(3);

        node.displayStates();

        System.out.println(node.isSectorOn(3));
    }
}
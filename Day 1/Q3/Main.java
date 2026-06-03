class DNASequencer {
    private StringBuilder dna;

    public DNASequencer(int capacity) {
        dna = new StringBuilder(capacity);
    }

    public void ingestSequence(char[] sensorData) {
        for (char ch : sensorData) {
            dna.append(ch);
        }
    }

    public void mutateDNA(String target, String replacement) {
        int pos = dna.indexOf(target);

        if (pos != -1) {
            dna.replace(pos, pos + target.length(), replacement);
        }
    }

    public String getSequence() {
        return dna.toString();
    }
}

public class Main {
    public static void main(String[] args) {

        DNASequencer sample = new DNASequencer(100000);

        char[] data = {
                'A', 'C', 'T', 'G',
                'A', 'A', 'C', 'T',
                'G', 'G'
        };

        sample.ingestSequence(data);

        System.out.println("Original DNA:");
        System.out.println(sample.getSequence());

        sample.mutateDNA("AACT", "TTGA");

        System.out.println("After Mutation:");
        System.out.println(sample.getSequence());
    }
}
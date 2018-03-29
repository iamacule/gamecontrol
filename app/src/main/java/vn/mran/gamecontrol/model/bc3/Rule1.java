package vn.mran.gamecontrol.model.bc3;

/**
 * Created by Mr An on 20/12/2017.
 */

public class Rule1 {
    public long additionalNumber;
    public String assignNumber;
    public long quantum;
    public String status;

    public Rule1() {
    }

    public Rule1(long additionalNumber, String assignNumber, long quantum, String status) {
        this.additionalNumber = additionalNumber;
        this.assignNumber = assignNumber;
        this.quantum = quantum;
        this.status = status;
    }

    public int getAdditionalNumber() {
        return (int) additionalNumber;
    }

    public long getQuantum() {
        return quantum;
    }

    public int[] getAssignNumberArray() {
        String[] assignNumberArray = assignNumber.split(" ");
        return new int[]{Integer.parseInt(assignNumberArray[0]),
                Integer.parseInt(assignNumberArray[1]),
                Integer.parseInt(assignNumberArray[2]),
                Integer.parseInt(assignNumberArray[3]),
                Integer.parseInt(assignNumberArray[4]),
                Integer.parseInt(assignNumberArray[5])};
    }

    public int getAnimal(int value) {
        String[] assignNumberArray = assignNumber.split(" ");
        for (int i = 0; i < assignNumberArray.length; i++) {
            if (Integer.parseInt(assignNumberArray[i]) == value) {
                return i;
            }
        }
        return value;
    }
}

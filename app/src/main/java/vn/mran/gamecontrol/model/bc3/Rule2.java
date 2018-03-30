package vn.mran.gamecontrol.model.bc3;

/**
 * Created by Mr An on 20/12/2017.
 */

public class Rule2 {
    public long additionalNumber;
    public String assignNumber;
    public long quantum;
    public String status;


    public Rule2() {
    }

    public Rule2(long additionalNumber, String assignNumber, long quantum, String status) {
        this.additionalNumber = additionalNumber;
        this.assignNumber = assignNumber;
        this.quantum = quantum;
        this.status = status;
    }
}

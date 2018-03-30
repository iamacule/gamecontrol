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
}

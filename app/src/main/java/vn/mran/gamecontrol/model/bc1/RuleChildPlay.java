package vn.mran.gamecontrol.model.bc1;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Mr An on 08/01/2018.
 */

@IgnoreExtraProperties
public class RuleChildPlay {
    public static final String ON = "on";
    public static final String OFF = "off";
    public long additionalNumber;
    public String assignNumber;
    public long quantum;
    public long rule;
    public String status;

    public RuleChildPlay() {
    }

    public RuleChildPlay(long additionalNumber, String assignNumber, long quantum, long rule, String status) {
        this.additionalNumber = additionalNumber;
        this.assignNumber = assignNumber;
        this.quantum = quantum;
        this.rule = rule;
        this.status = status;
    }
}

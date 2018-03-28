package vn.mran.gamecontrol.model.bc1;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Mr An on 08/01/2018.
 */

@IgnoreExtraProperties
public class RuleOfflinePlay {
    public static final String ON = "on";
    public static final String OFF = "off";
    public long additionalNumber;
    public String assignNumber;
    public long quantum;
    public String status;

    public RuleOfflinePlay() {
    }

    public RuleOfflinePlay(long additionalNumber, String assignNumber, long quantum, String status) {
        this.additionalNumber = additionalNumber;
        this.assignNumber = assignNumber;
        this.quantum = quantum;
        this.status = status;
    }
}

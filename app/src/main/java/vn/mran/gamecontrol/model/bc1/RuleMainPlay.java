package vn.mran.gamecontrol.model.bc1;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Mr An on 06/12/2017.
 */

@IgnoreExtraProperties
public class RuleMainPlay {
    public static final String ON = "on";
    public static final String OFF = "off";


    public long quantum;
    public String status = OFF;

    public RuleMainPlay() {
    }

    public RuleMainPlay(long quantum, String status) {
        this.quantum = quantum;
        this.status = status;
    }
}

package vn.mran.gamecontrol.controler;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import vn.mran.gamecontrol.activity.MainActivity;
import vn.mran.gamecontrol.model.bc1.RuleChild;
import vn.mran.gamecontrol.model.bc1.RuleChildPlay;
import vn.mran.gamecontrol.model.bc1.RuleMain;
import vn.mran.gamecontrol.model.bc1.RuleMainPlay;
import vn.mran.gamecontrol.model.bc1.RuleOffline;
import vn.mran.gamecontrol.model.bc1.RuleOfflinePlay;

/**
 * Created by Mr An on 06/12/2017.
 */

public class BC2Write {
    DatabaseReference myRef;

    public BC2Write() {
        myRef = FirebaseDatabase.getInstance().getReference("BC2");
        myRef.keepSynced(true);
    }

    public void writeRuleChild(RuleChild ruleChild) {
        myRef.child("RuleChild").setValue(ruleChild);
        MainActivity.firebaseRequest.minuteAllowRequest();
    }

    public void writeRuleMain(RuleMain ruleMain) {
        myRef.child("RuleMain").setValue(ruleMain);
        MainActivity.firebaseRequest.minuteAllowRequest();
    }

    public void writeText(String text) {
        myRef.child("Text").setValue(text);
        MainActivity.firebaseRequest.minuteAllowRequest();
    }

    public String exportAssignNumber(int[] arrays) {
        return arrays[0] + " " +
                arrays[1] + " " +
                arrays[2] + " " +
                arrays[3] + " " +
                arrays[4] + " " +
                arrays[5];
    }
}

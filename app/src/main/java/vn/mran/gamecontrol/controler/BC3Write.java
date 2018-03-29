package vn.mran.gamecontrol.controler;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import vn.mran.gamecontrol.activity.MainActivity;
import vn.mran.gamecontrol.model.bc1.RuleChild;
import vn.mran.gamecontrol.model.bc1.RuleMain;
import vn.mran.gamecontrol.model.bc3.Rule1;
import vn.mran.gamecontrol.model.bc3.Rule2;
import vn.mran.gamecontrol.model.bc3.Rule3;

/**
 * Created by Mr An on 06/12/2017.
 */

public class BC3Write {
    DatabaseReference myRef;

    public BC3Write() {
        myRef = FirebaseDatabase.getInstance().getReference("BC3");
        myRef.keepSynced(true);
    }

    public void writeRule1(Rule1 rule1) {
        myRef.child("Rule1").setValue(rule1);
        MainActivity.firebaseRequest.minuteAllowRequest();
    }

    public void writeRule2(Rule2 rule2) {
        myRef.child("Rule2").setValue(rule2);
        MainActivity.firebaseRequest.minuteAllowRequest();
    }

    public void writeRule3(Rule3 rule3) {
        myRef.child("Rule3").setValue(rule3);
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

    public void writeCurrentRule(int value) {
        myRef.child("CurrentRule").setValue(value);
        MainActivity.firebaseRequest.minuteAllowRequest();
    }

    public void writeHide(int value) {
        myRef.child("Hide").setValue(value);
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

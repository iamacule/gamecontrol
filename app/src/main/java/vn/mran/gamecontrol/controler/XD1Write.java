package vn.mran.gamecontrol.controler;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import vn.mran.gamecontrol.activity.MainActivity;
import vn.mran.gamecontrol.model.xd1.RuleChild;
import vn.mran.gamecontrol.model.xd1.RuleMain;
import vn.mran.gamecontrol.model.xd1.RuleOffline;

/**
 * Created by Mr An on 06/12/2017.
 */

public class XD1Write {
    DatabaseReference myRef ;

    public XD1Write() {
        myRef = FirebaseDatabase.getInstance().getReference("XD1");
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

    public void writeRuleOffline(RuleOffline ruleOffline) {
        myRef.child("RuleOffline").setValue(ruleOffline);
        MainActivity.firebaseRequest.minuteAllowRequest();
    }

    public void writeText(String text) {
        myRef.child("Text").setValue(text);
        MainActivity.firebaseRequest.minuteAllowRequest();
    }

    public void writeAssignNumber(String number1, String number2, String number3, String number4) {
        myRef.child("AssignNumber").setValue(
                String.valueOf(Integer.parseInt(number1)) + " " +
                        String.valueOf(Integer.parseInt(number2)) + " " +
                        String.valueOf(Integer.parseInt(number3)) + " " +
                        String.valueOf(Integer.parseInt(number4))
        );
        MainActivity.firebaseRequest.minuteAllowRequest();
    }
}

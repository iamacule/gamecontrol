package vn.mran.gamecontrol.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import vn.mran.gamecontrol.Constant;
import vn.mran.gamecontrol.R;
import vn.mran.gamecontrol.controler.BC3Write;
import vn.mran.gamecontrol.model.bc3.Rule1;
import vn.mran.gamecontrol.model.bc3.Rule2;
import vn.mran.gamecontrol.model.bc3.Rule3;
import vn.mran.gamecontrol.model.bc3.RuleMain;
import vn.mran.gamecontrol.util.FirebaseRequest;

public class BC3Activity extends AppCompatActivity implements View.OnClickListener, FirebaseRequest.OnFirebaseRequestChanged {

    private TextView txtRequestRetain;
    private TextView txtCurrentRule;
    private RadioGroup radioHide;
    private RadioGroup radioMain;
    private RadioButton radioBC;
    private RadioButton radioTC;
    private RadioButton radioGN;
    private RadioButton radioOff;
    private RadioButton radioMainSet;
    private RadioButton radioMainOff;
    private RadioGroup radioRule1;
    private RadioButton radioRule1Set;
    private RadioButton radioRule1Off;
    private RadioGroup radioRule2;
    private RadioButton radioRule2Set;
    private RadioButton radioRule2Off;
    private RadioGroup radioRule3;
    private RadioButton radioRule3Set;
    private RadioButton radioRule3Off;
    private String TAG = getClass().getSimpleName();

    private BC3Write bc3Write;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bc3);

        MainActivity.firebaseRequest.setOnFirebaseRequestChanged(this);

        bc3Write = new BC3Write();

        txtRequestRetain = (TextView) findViewById(R.id.txtRequestRetain);
        txtCurrentRule = (TextView) findViewById(R.id.txtCurrentRule);
        radioHide = (RadioGroup) findViewById(R.id.radioHide);
        radioMain = (RadioGroup) findViewById(R.id.radioMain);
        radioBC = (RadioButton) findViewById(R.id.radioBC);
        radioTC = (RadioButton) findViewById(R.id.radioTC);
        radioGN = (RadioButton) findViewById(R.id.radioGN);
        radioOff = (RadioButton) findViewById(R.id.radioOff);
        radioMainSet = (RadioButton) findViewById(R.id.radioMainSet);
        radioMainOff = (RadioButton) findViewById(R.id.radioMainOff);
        radioRule1 = (RadioGroup) findViewById(R.id.radioRule1);
        radioRule1Set = (RadioButton) findViewById(R.id.radioRule1Set);
        radioRule1Off = (RadioButton) findViewById(R.id.radioRule1Off);
        radioRule2 = (RadioGroup) findViewById(R.id.radioRule2);
        radioRule2Set = (RadioButton) findViewById(R.id.radioRule2Set);
        radioRule2Off = (RadioButton) findViewById(R.id.radioRule2Off);
        radioRule3 = (RadioGroup) findViewById(R.id.radioRule3);
        radioRule3Set = (RadioButton) findViewById(R.id.radioRule3Set);
        radioRule3Off = (RadioButton) findViewById(R.id.radioRule3Off);
        findViewById(R.id.btnText).setOnClickListener(this);

        initValue();

        initAction();
    }

    private void initAction() {
        radioHide.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.radioBC:
                        break;
                }
            }
        });
    }

    private void initValue() {
        FirebaseDatabase.getInstance().getReference("BC3").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //CurrentRule
                txtCurrentRule.setText(getString(R.string.current_rule) +
                        dataSnapshot.child("CurrentRule").getValue());

                //Hide
                int hideValue = Integer.parseInt(dataSnapshot.child("Hide").getValue().toString());
                switch (hideValue){
                    case 0:
                        radioOff.setChecked(true);
                        break;
                    case 1:
                        radioBC.setChecked(true);
                        break;
                    case 2:
                        radioTC.setChecked(true);
                        break;
                    case 3:
                        radioGN.setChecked(true);
                        break;
                }

                //Rule
                Rule1 rule1 = dataSnapshot.child("Rule1").getValue(Rule1.class);

                getEdtRule1AdditionalNumber().setText(String.valueOf(rule1.getAdditionalNumber()));

                int[] rule1AssignArrays = rule1.getAssignNumberArray();
                getEdtRule1Assign1().setText(String.valueOf(rule1AssignArrays[0]));
                getEdtRule1Assign2().setText(String.valueOf(rule1AssignArrays[1]));
                getEdtRule1Assign3().setText(String.valueOf(rule1AssignArrays[2]));
                getEdtRule1Assign4().setText(String.valueOf(rule1AssignArrays[3]));
                getEdtRule1Assign5().setText(String.valueOf(rule1AssignArrays[4]));
                getEdtRule1Assign6().setText(String.valueOf(rule1AssignArrays[5]));

                getEdtRule1Quantum().setText(String.valueOf(rule1.getQuantum()));
                if (rule1.status.equals(Constant.STATUS_ON)){
                    radioRule1Set.setChecked(true);
                }else {
                    radioRule1Off.setChecked(true);
                }

                Rule2 rule2 = dataSnapshot.child("Rule2").getValue(Rule2.class);

                getEdtRule2AdditionalNumber().setText(String.valueOf(rule2.getAdditionalNumber()));

                int[] rule2AssignArrays = rule2.getAssignNumberArray();
                getEdtRule2Assign1().setText(String.valueOf(rule2AssignArrays[0]));
                getEdtRule2Assign2().setText(String.valueOf(rule2AssignArrays[1]));
                getEdtRule2Assign3().setText(String.valueOf(rule2AssignArrays[2]));
                getEdtRule2Assign4().setText(String.valueOf(rule2AssignArrays[3]));
                getEdtRule2Assign5().setText(String.valueOf(rule2AssignArrays[4]));
                getEdtRule2Assign6().setText(String.valueOf(rule2AssignArrays[5]));

                getEdtRule2Quantum().setText(String.valueOf(rule2.getQuantum()));
                if (rule2.status.equals(Constant.STATUS_ON)){
                    radioRule2Set.setChecked(true);
                }else {
                    radioRule2Off.setChecked(true);
                }

                Rule3 rule3 = dataSnapshot.child("Rule3").getValue(Rule3.class);

                getEdtRule3AdditionalNumber().setText(String.valueOf(rule3.getAdditionalNumber()));

                int[] rule3AssignArrays = rule3.getAssignNumberArray();
                getEdtRule3Assign1().setText(String.valueOf(rule3AssignArrays[0]));
                getEdtRule3Assign2().setText(String.valueOf(rule3AssignArrays[1]));
                getEdtRule3Assign3().setText(String.valueOf(rule3AssignArrays[2]));
                getEdtRule3Assign4().setText(String.valueOf(rule3AssignArrays[3]));
                getEdtRule3Assign5().setText(String.valueOf(rule3AssignArrays[4]));
                getEdtRule3Assign6().setText(String.valueOf(rule3AssignArrays[5]));

                getEdtRule3Quantum().setText(String.valueOf(rule3.getQuantum()));
                if (rule3.status.equals(Constant.STATUS_ON)){
                    radioRule3Set.setChecked(true);
                }else {
                    radioRule3Off.setChecked(true);
                }

                //Rule Main
                RuleMain ruleMain = dataSnapshot.child("RuleMain").getValue(RuleMain.class);
                if (ruleMain.status.equals(Constant.STATUS_ON)) {
                    radioMainSet.setChecked(true);
                }else {
                    radioMainOff.setChecked(true);
                }

                getEdtText().setText(dataSnapshot.child("Text").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, databaseError.getMessage());
            }
        });
    }

    private EditText getEdtMainQuantum(){
        return (EditText) findViewById(R.id.edtMainQuantum);
    }

    private EditText getEdtRule1Assign1(){
        return (EditText) findViewById(R.id.edtRule1Assign1);
    }

    private EditText getEdtRule1Assign2(){
        return (EditText) findViewById(R.id.edtRule1Assign2);
    }

    private EditText getEdtRule1Assign3(){
        return (EditText) findViewById(R.id.edtRule1Assign3);
    }

    private EditText getEdtRule1Assign4(){
        return (EditText) findViewById(R.id.edtRule1Assign4);
    }

    private EditText getEdtRule1Assign5(){
        return (EditText) findViewById(R.id.edtRule1Assign5);
    }

    private EditText getEdtRule1Assign6(){
        return (EditText) findViewById(R.id.edtRule1Assign6);
    }

    private EditText getEdtRule1Quantum(){
        return (EditText) findViewById(R.id.edtRule1Quantum);
    }

    private EditText getEdtRule1AdditionalNumber(){
        return (EditText) findViewById(R.id.edtRule1AdditionalNumber);
    }

    private EditText getEdtRule2Assign1(){
        return (EditText) findViewById(R.id.edtRule2Assign1);
    }

    private EditText getEdtRule2Assign2(){
        return (EditText) findViewById(R.id.edtRule2Assign2);
    }

    private EditText getEdtRule2Assign3(){
        return (EditText) findViewById(R.id.edtRule2Assign3);
    }

    private EditText getEdtRule2Assign4(){
        return (EditText) findViewById(R.id.edtRule2Assign4);
    }

    private EditText getEdtRule2Assign5(){
        return (EditText) findViewById(R.id.edtRule2Assign5);
    }

    private EditText getEdtRule2Assign6(){
        return (EditText) findViewById(R.id.edtRule2Assign6);
    }

    private EditText getEdtRule2Quantum(){
        return (EditText) findViewById(R.id.edtRule2Quantum);
    }

    private EditText getEdtRule2AdditionalNumber(){
        return (EditText) findViewById(R.id.edtRule2AdditionalNumber);
    }

    private EditText getEdtRule3Assign1(){
        return (EditText) findViewById(R.id.edtRule3Assign1);
    }

    private EditText getEdtRule3Assign2(){
        return (EditText) findViewById(R.id.edtRule3Assign2);
    }

    private EditText getEdtRule3Assign3(){
        return (EditText) findViewById(R.id.edtRule3Assign3);
    }

    private EditText getEdtRule3Assign4(){
        return (EditText) findViewById(R.id.edtRule3Assign4);
    }

    private EditText getEdtRule3Assign5(){
        return (EditText) findViewById(R.id.edtRule3Assign5);
    }

    private EditText getEdtRule3Assign6(){
        return (EditText) findViewById(R.id.edtRule3Assign6);
    }

    private EditText getEdtRule3Quantum(){
        return (EditText) findViewById(R.id.edtRule3Quantum);
    }

    private EditText getEdtRule3AdditionalNumber(){
        return (EditText) findViewById(R.id.edtRule3AdditionalNumber);
    }

    private EditText getEdtText(){
        return (EditText) findViewById(R.id.edtText);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnText:
                //TODO implement
                break;
        }
    }

    @Override
    public void onAllowRequestStringUpdated(String value) {
        ((TextView) findViewById(R.id.txtRequestRetain)).setText(value);
    }
}

package vn.mran.gamecontrol.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.widget.EditText;
import android.widget.Toast;

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
import vn.mran.gamecontrol.util.Util;

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

    private int previousHide;
    private int previousMain;
    private int previousRule1;
    private int previousRule2;
    private int previousRule3;

    private ProgressDialog progressDialog;
    private Rule1 rule1;
    private Rule2 rule2;
    private Rule3 rule3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bc3);

        MainActivity.firebaseRequest.setOnFirebaseRequestChanged(this);

        bc3Write = new BC3Write();

        progressDialog = new ProgressDialog(this);

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
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
                radioHide.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        if (isOnline()) {
                            switch (i) {
                                case R.id.radioBC:
                                    previousHide = 1;
                                    bc3Write.writeHide(1);
                                    Toast.makeText(getApplicationContext(), getString(R.string.sent), Toast.LENGTH_SHORT).show();
                                    break;
                                case R.id.radioTC:
                                    previousHide = 2;
                                    bc3Write.writeHide(2);
                                    Toast.makeText(getApplicationContext(), getString(R.string.sent), Toast.LENGTH_SHORT).show();
                                    break;
                                case R.id.radioGN:
                                    previousHide = 3;
                                    bc3Write.writeHide(3);
                                    Toast.makeText(getApplicationContext(), getString(R.string.sent), Toast.LENGTH_SHORT).show();
                                    break;
                                case R.id.radioOff:
                                    previousHide = 0;
                                    bc3Write.writeHide(0);
                                    Toast.makeText(getApplicationContext(), getString(R.string.sent), Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Không có kết nối !", Toast.LENGTH_SHORT).show();
                            switch (previousHide) {
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
                        }

                    }
                });

                radioMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        if (isOnline()) {
                            switch (i) {
                                case R.id.radioMainSet:
                                    int mainRuleQuantum = Constant.DEFAULT_SERVER_NUMBER_OF_MAIN_RULES;
                                    previousHide = R.id.radioMainSet;
                                    if (!Util.checkNull(getEdtMainQuantum().getText().toString())) {
                                        mainRuleQuantum = Util.convertStringToInt(getEdtMainQuantum().getText().toString());
                                    } else {
                                        getEdtMainQuantum().setText(String.valueOf(mainRuleQuantum));
                                    }

                                    bc3Write.writeRuleMain(new RuleMain(mainRuleQuantum, Constant.STATUS_ON));
                                    Toast.makeText(getApplicationContext(), getString(R.string.sent), Toast.LENGTH_SHORT).show();
                                    break;
                                case R.id.radioMainOff:
                                    previousHide = R.id.radioMainOff;
                                    getEdtMainQuantum().setText("");
                                    bc3Write.writeRuleMain(new RuleMain(0, Constant.STATUS_OFF));
                                    Toast.makeText(getApplicationContext(), getString(R.string.sent), Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Không có kết nối !", Toast.LENGTH_SHORT).show();
                            switch (previousHide) {
                                case R.id.radioMainSet:
                                    radioMainSet.setChecked(true);
                                    break;
                                case R.id.radioMainOff:
                                    radioMainOff.setChecked(true);
                                    break;
                            }
                        }

                    }
                });

                radioRule1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        if (isOnline()) {
                            int ruleQuantum = Constant.DEFAULT_SERVER_NUMBER_OF_RULES;
                            long additionalNumber = 0;
                            int[] assignNumberArrays = new int[]{0, 1, 2, 3, 4, 5};
                            switch (i) {
                                case R.id.radioRule1Set:
                                    if (!Util.checkNull(getEdtRule1AdditionalNumber().getText().toString())) {
                                        additionalNumber = Util.convertStringToInt(getEdtRule1AdditionalNumber().getText().toString());
                                    } else {
                                        getEdtRule1AdditionalNumber().setText(String.valueOf(additionalNumber));
                                    }

                                    if (!Util.checkNull(getEdtRule1Quantum().getText().toString())) {
                                        ruleQuantum = Util.convertStringToInt(getEdtRule1Quantum().getText().toString());
                                    } else {
                                        getEdtRule1Quantum().setText(String.valueOf(ruleQuantum));
                                    }

                                    if (!Util.checkNull(getEdtRule1Assign1().getText().toString())) {
                                        assignNumberArrays[0] = Util.convertStringToInt(getEdtRule1Assign1().getText().toString());
                                    } else {
                                        getEdtRule1Assign1().setText(String.valueOf(assignNumberArrays[0]));
                                    }

                                    if (!Util.checkNull(getEdtRule1Assign2().getText().toString())) {
                                        assignNumberArrays[1] = Util.convertStringToInt(getEdtRule1Assign2().getText().toString());
                                    } else {
                                        getEdtRule1Assign2().setText(String.valueOf(assignNumberArrays[1]));
                                    }

                                    if (!Util.checkNull(getEdtRule1Assign3().getText().toString())) {
                                        assignNumberArrays[2] = Util.convertStringToInt(getEdtRule1Assign3().getText().toString());
                                    } else {
                                        getEdtRule1Assign3().setText(String.valueOf(assignNumberArrays[2]));
                                    }

                                    if (!Util.checkNull(getEdtRule1Assign4().getText().toString())) {
                                        assignNumberArrays[3] = Util.convertStringToInt(getEdtRule1Assign4().getText().toString());
                                    } else {
                                        getEdtRule1Assign4().setText(String.valueOf(assignNumberArrays[3]));
                                    }

                                    if (!Util.checkNull(getEdtRule1Assign5().getText().toString())) {
                                        assignNumberArrays[4] = Util.convertStringToInt(getEdtRule1Assign5().getText().toString());
                                    } else {
                                        getEdtRule1Assign5().setText(String.valueOf(assignNumberArrays[4]));
                                    }

                                    if (!Util.checkNull(getEdtRule1Assign6().getText().toString())) {
                                        assignNumberArrays[5] = Util.convertStringToInt(getEdtRule1Assign6().getText().toString());
                                    } else {
                                        getEdtRule1Assign6().setText(String.valueOf(assignNumberArrays[5]));
                                    }

                                    rule1.additionalNumber = additionalNumber;
                                    rule1.assignNumber = bc3Write.exportAssignNumber(assignNumberArrays);
                                    rule1.quantum = ruleQuantum;
                                    rule1.status = Constant.STATUS_ON;

                                    bc3Write.writeRule1(new Rule1(additionalNumber, bc3Write.exportAssignNumber(assignNumberArrays), ruleQuantum, Constant.STATUS_ON));

                                    bc3Write.writeCurrentRule(1);

                                    Toast.makeText(getApplicationContext(), getString(R.string.sent), Toast.LENGTH_SHORT).show();
                                    break;
                                case R.id.radioRule1Off:
                                    bc3Write.writeRule1(new Rule1(rule1.additionalNumber, rule1.assignNumber, rule1.quantum, Constant.STATUS_OFF));

                                    checkAndWriteCurrentRuleOff(1);

                                    Toast.makeText(getApplicationContext(), getString(R.string.sent), Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Không có kết nối !", Toast.LENGTH_SHORT).show();
                            switch (previousRule1) {
                                case R.id.radioRule1Set:
                                    radioRule1Set.setChecked(true);
                                    break;
                                case R.id.radioRule1Off:
                                    radioRule1Off.setChecked(true);
                                    break;
                            }
                        }
                    }
                });

                radioRule2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        if (isOnline()) {
                            int ruleQuantum = Constant.DEFAULT_SERVER_NUMBER_OF_RULES;
                            long additionalNumber = 0;
                            int[] assignNumberArrays = new int[]{0, 1, 2, 3, 4, 5};
                            switch (i) {
                                case R.id.radioRule2Set:
                                    if (!Util.checkNull(getEdtRule2AdditionalNumber().getText().toString())) {
                                        additionalNumber = Util.convertStringToInt(getEdtRule2AdditionalNumber().getText().toString());
                                    } else {
                                        getEdtRule2AdditionalNumber().setText(String.valueOf(additionalNumber));
                                    }

                                    if (!Util.checkNull(getEdtRule2Quantum().getText().toString())) {
                                        ruleQuantum = Util.convertStringToInt(getEdtRule2Quantum().getText().toString());
                                    } else {
                                        getEdtRule2Quantum().setText(String.valueOf(ruleQuantum));
                                    }

                                    if (!Util.checkNull(getEdtRule2Assign1().getText().toString())) {
                                        assignNumberArrays[0] = Util.convertStringToInt(getEdtRule2Assign1().getText().toString());
                                    } else {
                                        getEdtRule2Assign1().setText(String.valueOf(assignNumberArrays[0]));
                                    }

                                    if (!Util.checkNull(getEdtRule2Assign2().getText().toString())) {
                                        assignNumberArrays[1] = Util.convertStringToInt(getEdtRule2Assign2().getText().toString());
                                    } else {
                                        getEdtRule2Assign2().setText(String.valueOf(assignNumberArrays[1]));
                                    }

                                    if (!Util.checkNull(getEdtRule2Assign3().getText().toString())) {
                                        assignNumberArrays[2] = Util.convertStringToInt(getEdtRule2Assign3().getText().toString());
                                    } else {
                                        getEdtRule2Assign3().setText(String.valueOf(assignNumberArrays[2]));
                                    }

                                    if (!Util.checkNull(getEdtRule2Assign4().getText().toString())) {
                                        assignNumberArrays[3] = Util.convertStringToInt(getEdtRule2Assign4().getText().toString());
                                    } else {
                                        getEdtRule2Assign4().setText(String.valueOf(assignNumberArrays[3]));
                                    }

                                    if (!Util.checkNull(getEdtRule2Assign5().getText().toString())) {
                                        assignNumberArrays[4] = Util.convertStringToInt(getEdtRule2Assign5().getText().toString());
                                    } else {
                                        getEdtRule2Assign5().setText(String.valueOf(assignNumberArrays[4]));
                                    }

                                    if (!Util.checkNull(getEdtRule2Assign6().getText().toString())) {
                                        assignNumberArrays[5] = Util.convertStringToInt(getEdtRule2Assign6().getText().toString());
                                    } else {
                                        getEdtRule2Assign6().setText(String.valueOf(assignNumberArrays[5]));
                                    }

                                    rule2.additionalNumber = additionalNumber;
                                    rule2.assignNumber = bc3Write.exportAssignNumber(assignNumberArrays);
                                    rule2.quantum = ruleQuantum;
                                    rule2.status = Constant.STATUS_ON;

                                    bc3Write.writeRule2(new Rule2(additionalNumber, bc3Write.exportAssignNumber(assignNumberArrays), ruleQuantum, Constant.STATUS_ON));

                                    bc3Write.writeCurrentRule(2);

                                    Toast.makeText(getApplicationContext(), getString(R.string.sent), Toast.LENGTH_SHORT).show();
                                    break;
                                case R.id.radioRule2Off:
                                    bc3Write.writeRule2(new Rule2(rule2.additionalNumber, rule2.assignNumber, rule2.quantum, Constant.STATUS_OFF));

                                    checkAndWriteCurrentRuleOff(2);

                                    Toast.makeText(getApplicationContext(), getString(R.string.sent), Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Không có kết nối !", Toast.LENGTH_SHORT).show();
                            switch (previousRule2) {
                                case R.id.radioRule2Set:
                                    radioRule2Set.setChecked(true);
                                    break;
                                case R.id.radioRule2Off:
                                    radioRule2Off.setChecked(true);
                                    break;
                            }
                        }
                    }
                });

                radioRule3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        if (isOnline()) {
                            int ruleQuantum = Constant.DEFAULT_SERVER_NUMBER_OF_RULES;
                            long additionalNumber = 0;
                            int[] assignNumberArrays = new int[]{0, 1, 2, 3, 4, 5};
                            switch (i) {
                                case R.id.radioRule3Set:
                                    if (!Util.checkNull(getEdtRule3AdditionalNumber().getText().toString())) {
                                        additionalNumber = Util.convertStringToInt(getEdtRule3AdditionalNumber().getText().toString());
                                    } else {
                                        getEdtRule3AdditionalNumber().setText(String.valueOf(additionalNumber));
                                    }

                                    if (!Util.checkNull(getEdtRule3Quantum().getText().toString())) {
                                        ruleQuantum = Util.convertStringToInt(getEdtRule3Quantum().getText().toString());
                                    } else {
                                        getEdtRule3Quantum().setText(String.valueOf(ruleQuantum));
                                    }

                                    if (!Util.checkNull(getEdtRule3Assign1().getText().toString())) {
                                        assignNumberArrays[0] = Util.convertStringToInt(getEdtRule3Assign1().getText().toString());
                                    } else {
                                        getEdtRule3Assign1().setText(String.valueOf(assignNumberArrays[0]));
                                    }

                                    if (!Util.checkNull(getEdtRule3Assign2().getText().toString())) {
                                        assignNumberArrays[1] = Util.convertStringToInt(getEdtRule3Assign2().getText().toString());
                                    } else {
                                        getEdtRule3Assign2().setText(String.valueOf(assignNumberArrays[1]));
                                    }

                                    if (!Util.checkNull(getEdtRule3Assign3().getText().toString())) {
                                        assignNumberArrays[2] = Util.convertStringToInt(getEdtRule3Assign3().getText().toString());
                                    } else {
                                        getEdtRule3Assign3().setText(String.valueOf(assignNumberArrays[2]));
                                    }

                                    if (!Util.checkNull(getEdtRule3Assign4().getText().toString())) {
                                        assignNumberArrays[3] = Util.convertStringToInt(getEdtRule3Assign4().getText().toString());
                                    } else {
                                        getEdtRule3Assign4().setText(String.valueOf(assignNumberArrays[3]));
                                    }

                                    if (!Util.checkNull(getEdtRule3Assign5().getText().toString())) {
                                        assignNumberArrays[4] = Util.convertStringToInt(getEdtRule3Assign5().getText().toString());
                                    } else {
                                        getEdtRule3Assign5().setText(String.valueOf(assignNumberArrays[4]));
                                    }

                                    if (!Util.checkNull(getEdtRule3Assign6().getText().toString())) {
                                        assignNumberArrays[5] = Util.convertStringToInt(getEdtRule3Assign6().getText().toString());
                                    } else {
                                        getEdtRule3Assign6().setText(String.valueOf(assignNumberArrays[5]));
                                    }

                                    rule3.additionalNumber = additionalNumber;
                                    rule3.assignNumber = bc3Write.exportAssignNumber(assignNumberArrays);
                                    rule3.quantum = ruleQuantum;
                                    rule3.status = Constant.STATUS_ON;

                                    bc3Write.writeRule3(new Rule3(additionalNumber, bc3Write.exportAssignNumber(assignNumberArrays), ruleQuantum, Constant.STATUS_ON));

                                    Toast.makeText(getApplicationContext(), getString(R.string.sent), Toast.LENGTH_SHORT).show();
                                    break;
                                case R.id.radioRule3Off:
                                    bc3Write.writeRule3(new Rule3(rule3.additionalNumber, rule3.assignNumber, rule3.quantum, Constant.STATUS_OFF));

                                    Toast.makeText(getApplicationContext(), getString(R.string.sent), Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Không có kết nối !", Toast.LENGTH_SHORT).show();
                            switch (previousRule3) {
                                case R.id.radioRule3Set:
                                    radioRule3Set.setChecked(true);
                                    break;
                                case R.id.radioRule3Off:
                                    radioRule3Off.setChecked(true);
                                    break;
                            }
                        }
                    }
                });


            }
        }, 1500);
    }

    private void checkAndWriteCurrentRuleOff(int rule) {
        switch (rule) {
            case 1:
                if (rule2.status.equals(Constant.STATUS_ON)) {
                    bc3Write.writeCurrentRule(2);
                } else {
                    bc3Write.writeCurrentRule(0);
                }
                break;
            case 2:
                if (rule1.status.equals(Constant.STATUS_ON)) {
                    bc3Write.writeCurrentRule(1);
                } else {
                    bc3Write.writeCurrentRule(0);
                }
                break;
        }
    }

    private void initValue() {
        progressDialog.setMessage("Chờ chút nha !");
        progressDialog.show();
        FirebaseDatabase.getInstance().getReference("BC3").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //CurrentRule
                txtCurrentRule.setText(getString(R.string.current_rule) +
                        dataSnapshot.child("CurrentRule").getValue());

                //Hide
                int hideValue = Integer.parseInt(dataSnapshot.child("Hide").getValue().toString());
                switch (hideValue) {
                    case 0:
                        previousHide = 0;
                        radioOff.setChecked(true);
                        break;
                    case 1:
                        previousHide = 1;
                        radioBC.setChecked(true);
                        break;
                    case 2:
                        previousHide = 2;
                        radioTC.setChecked(true);
                        break;
                    case 3:
                        previousHide = 3;
                        radioGN.setChecked(true);
                        break;
                }

                //Rule
                rule1 = dataSnapshot.child("Rule1").getValue(Rule1.class);

                getEdtRule1AdditionalNumber().setText(String.valueOf(rule1.additionalNumber));

                int[] rule1AssignArrays = bc3Write.getAssignNumberArray(rule1.assignNumber);
                getEdtRule1Assign1().setText(String.valueOf(rule1AssignArrays[0]));
                getEdtRule1Assign2().setText(String.valueOf(rule1AssignArrays[1]));
                getEdtRule1Assign3().setText(String.valueOf(rule1AssignArrays[2]));
                getEdtRule1Assign4().setText(String.valueOf(rule1AssignArrays[3]));
                getEdtRule1Assign5().setText(String.valueOf(rule1AssignArrays[4]));
                getEdtRule1Assign6().setText(String.valueOf(rule1AssignArrays[5]));

                getEdtRule1Quantum().setText(String.valueOf(rule1.quantum));
                if (rule1.status.equals(Constant.STATUS_ON)) {
                    previousRule1 = R.id.radioRule1Set;
                    radioRule1Set.setChecked(true);
                } else {
                    previousRule1 = R.id.radioRule1Off;
                    radioRule1Off.setChecked(true);
                }

                rule2 = dataSnapshot.child("Rule2").getValue(Rule2.class);

                getEdtRule2AdditionalNumber().setText(String.valueOf(rule2.additionalNumber));

                int[] rule2AssignArrays = bc3Write.getAssignNumberArray(rule2.assignNumber);
                getEdtRule2Assign1().setText(String.valueOf(rule2AssignArrays[0]));
                getEdtRule2Assign2().setText(String.valueOf(rule2AssignArrays[1]));
                getEdtRule2Assign3().setText(String.valueOf(rule2AssignArrays[2]));
                getEdtRule2Assign4().setText(String.valueOf(rule2AssignArrays[3]));
                getEdtRule2Assign5().setText(String.valueOf(rule2AssignArrays[4]));
                getEdtRule2Assign6().setText(String.valueOf(rule2AssignArrays[5]));

                getEdtRule2Quantum().setText(String.valueOf(rule2.quantum));
                if (rule2.status.equals(Constant.STATUS_ON)) {
                    previousRule2 = R.id.radioRule2Set;
                    radioRule2Set.setChecked(true);
                } else {
                    previousRule2 = R.id.radioRule2Off;
                    radioRule2Off.setChecked(true);
                }

                rule3 = dataSnapshot.child("Rule3").getValue(Rule3.class);

                getEdtRule3AdditionalNumber().setText(String.valueOf(rule3.additionalNumber));

                int[] rule3AssignArrays = bc3Write.getAssignNumberArray(rule3.assignNumber);
                getEdtRule3Assign1().setText(String.valueOf(rule3AssignArrays[0]));
                getEdtRule3Assign2().setText(String.valueOf(rule3AssignArrays[1]));
                getEdtRule3Assign3().setText(String.valueOf(rule3AssignArrays[2]));
                getEdtRule3Assign4().setText(String.valueOf(rule3AssignArrays[3]));
                getEdtRule3Assign5().setText(String.valueOf(rule3AssignArrays[4]));
                getEdtRule3Assign6().setText(String.valueOf(rule3AssignArrays[5]));

                getEdtRule3Quantum().setText(String.valueOf(rule3.quantum));
                if (rule3.status.equals(Constant.STATUS_ON)) {
                    previousRule3 = R.id.radioRule3Set;
                    radioRule3Set.setChecked(true);
                } else {
                    previousRule3 = R.id.radioRule3Off;
                    radioRule3Off.setChecked(true);
                }

                //Rule Main
                RuleMain ruleMain = dataSnapshot.child("RuleMain").getValue(RuleMain.class);
                getEdtMainQuantum().setText(String.valueOf(ruleMain.quantum));
                if (ruleMain.status.equals(Constant.STATUS_ON)) {
                    previousMain = R.id.radioMainSet;
                    radioMainSet.setChecked(true);
                } else {
                    previousMain = R.id.radioMainOff;
                    radioMainOff.setChecked(true);
                }

                getEdtText().setText(dataSnapshot.child("Text").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, databaseError.getMessage());
                Toast.makeText(getApplicationContext(), "Không có kết nối !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private EditText getEdtMainQuantum() {
        return (EditText) findViewById(R.id.edtMainQuantum);
    }

    private EditText getEdtRule1Assign1() {
        return (EditText) findViewById(R.id.edtRule1Assign1);
    }

    private EditText getEdtRule1Assign2() {
        return (EditText) findViewById(R.id.edtRule1Assign2);
    }

    private EditText getEdtRule1Assign3() {
        return (EditText) findViewById(R.id.edtRule1Assign3);
    }

    private EditText getEdtRule1Assign4() {
        return (EditText) findViewById(R.id.edtRule1Assign4);
    }

    private EditText getEdtRule1Assign5() {
        return (EditText) findViewById(R.id.edtRule1Assign5);
    }

    private EditText getEdtRule1Assign6() {
        return (EditText) findViewById(R.id.edtRule1Assign6);
    }

    private EditText getEdtRule1Quantum() {
        return (EditText) findViewById(R.id.edtRule1Quantum);
    }

    private EditText getEdtRule1AdditionalNumber() {
        return (EditText) findViewById(R.id.edtRule1AdditionalNumber);
    }

    private EditText getEdtRule2Assign1() {
        return (EditText) findViewById(R.id.edtRule2Assign1);
    }

    private EditText getEdtRule2Assign2() {
        return (EditText) findViewById(R.id.edtRule2Assign2);
    }

    private EditText getEdtRule2Assign3() {
        return (EditText) findViewById(R.id.edtRule2Assign3);
    }

    private EditText getEdtRule2Assign4() {
        return (EditText) findViewById(R.id.edtRule2Assign4);
    }

    private EditText getEdtRule2Assign5() {
        return (EditText) findViewById(R.id.edtRule2Assign5);
    }

    private EditText getEdtRule2Assign6() {
        return (EditText) findViewById(R.id.edtRule2Assign6);
    }

    private EditText getEdtRule2Quantum() {
        return (EditText) findViewById(R.id.edtRule2Quantum);
    }

    private EditText getEdtRule2AdditionalNumber() {
        return (EditText) findViewById(R.id.edtRule2AdditionalNumber);
    }

    private EditText getEdtRule3Assign1() {
        return (EditText) findViewById(R.id.edtRule3Assign1);
    }

    private EditText getEdtRule3Assign2() {
        return (EditText) findViewById(R.id.edtRule3Assign2);
    }

    private EditText getEdtRule3Assign3() {
        return (EditText) findViewById(R.id.edtRule3Assign3);
    }

    private EditText getEdtRule3Assign4() {
        return (EditText) findViewById(R.id.edtRule3Assign4);
    }

    private EditText getEdtRule3Assign5() {
        return (EditText) findViewById(R.id.edtRule3Assign5);
    }

    private EditText getEdtRule3Assign6() {
        return (EditText) findViewById(R.id.edtRule3Assign6);
    }

    private EditText getEdtRule3Quantum() {
        return (EditText) findViewById(R.id.edtRule3Quantum);
    }

    private EditText getEdtRule3AdditionalNumber() {
        return (EditText) findViewById(R.id.edtRule3AdditionalNumber);
    }

    private EditText getEdtText() {
        return (EditText) findViewById(R.id.edtText);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnText:
                bc3Write.writeText(getEdtText().getText().toString().trim());
                Toast.makeText(getApplicationContext(), getString(R.string.sent), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onAllowRequestStringUpdated(String value) {
        ((TextView) findViewById(R.id.txtRequestRetain)).setText(value);
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}

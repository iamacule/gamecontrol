package vn.mran.gamecontrol.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import vn.mran.gamecontrol.Constant;
import vn.mran.gamecontrol.R;
import vn.mran.gamecontrol.util.FirebaseRequest;
import vn.mran.gamecontrol.util.Util;
import vn.mran.gamecontrol.controler.BC2Write;
import vn.mran.gamecontrol.model.bc1.RuleChild;
import vn.mran.gamecontrol.model.bc1.RuleMain;
import vn.mran.gamecontrol.model.bc1.RuleOffline;

/**
 * Created by Mr An on 01/12/2017.
 */

public class BC2Activity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, FirebaseRequest.OnFirebaseRequestChanged {

    private final String TAG = getClass().getSimpleName();

    private BC2Write bc2Write;

    private int currentRule = 0;
    private ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bc2);

        MainActivity.firebaseRequest.setOnFirebaseRequestChanged(this);

        bc2Write = new BC2Write();

        findViewById(R.id.btnSetMain).setOnClickListener(this);
        findViewById(R.id.btnOffMain).setOnClickListener(this);
        findViewById(R.id.btnSetRuleChild).setOnClickListener(this);
        findViewById(R.id.btnOffRuleChild).setOnClickListener(this);
        findViewById(R.id.btnText).setOnClickListener(this);

        adapter = ArrayAdapter.createFromResource(this,
                R.array.rules_arrays, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        getSpRule().setAdapter(adapter);
        getSpRule().setOnItemSelectedListener(this);

        initValue();
    }

    private void initValue() {
        FirebaseDatabase.getInstance().getReference("BC2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Rule
                RuleChild ruleChild = dataSnapshot.child("RuleChild").getValue(RuleChild.class);

                if (ruleChild.status.equals(RuleChild.ON)) {
                    currentRule = (int) ruleChild.rule;
                    Log.d(TAG, "onDataChange: currentRule : " + currentRule);
                    getSpRule().setSelection(currentRule);

                    String[] assignNumberArrays = ruleChild.assignNumber.split(" ");
                    getEdtAssign1().setText(String.valueOf(assignNumberArrays[0]));
                    getEdtAssign2().setText(String.valueOf(assignNumberArrays[1]));
                    getEdtAssign3().setText(String.valueOf(assignNumberArrays[2]));
                    getEdtAssign4().setText(String.valueOf(assignNumberArrays[3]));
                    getEdtAssign5().setText(String.valueOf(assignNumberArrays[4]));
                    getEdtAssign6().setText(String.valueOf(assignNumberArrays[5]));

                    getEdtRuleQuantum().setText(String.valueOf(ruleChild.quantum));
                    getEdtRuleAdditionalNumber().setText(String.valueOf(ruleChild.additionalNumber));
                }

                //Rule Main
                RuleMain ruleMain = dataSnapshot.child("RuleMain").getValue(RuleMain.class);
                if (ruleMain.status.equals(RuleMain.ON)) {
                    getEdtMainQuantum().setText(String.valueOf(ruleMain.quantum));
                }

                getEdtText().setText(dataSnapshot.child("Text").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, databaseError.getMessage());
            }
        });
    }

    private EditText getEdtMainQuantum() {
        return (EditText) findViewById(R.id.edtMainQuantum);
    }

    private EditText getEdtAssign1() {
        return (EditText) findViewById(R.id.edtAssign1);
    }

    private EditText getEdtAssign2() {
        return (EditText) findViewById(R.id.edtAssign2);
    }

    private EditText getEdtAssign3() {
        return (EditText) findViewById(R.id.edtAssign3);
    }

    private EditText getEdtAssign4() {
        return (EditText) findViewById(R.id.edtAssign4);
    }

    private EditText getEdtAssign5() {
        return (EditText) findViewById(R.id.edtAssign5);
    }

    private EditText getEdtAssign6() {
        return (EditText) findViewById(R.id.edtAssign6);
    }

    private Spinner getSpRule() {
        return (Spinner) findViewById(R.id.spRule);
    }

    private EditText getEdtRuleQuantum() {
        return (EditText) findViewById(R.id.edtRuleQuantum);
    }

    private EditText getEdtRuleAdditionalNumber() {
        return (EditText) findViewById(R.id.edtRuleAdditionalNumber);
    }

    private EditText getEdtText() {
        return (EditText) findViewById(R.id.edtText);
    }

    @Override
    public void onClick(View view) {
        if (isOnline()) {
            int ruleQuantum = Constant.DEFAULT_SERVER_NUMBER_OF_RULES;
            int mainRuleQuantum = Constant.DEFAULT_SERVER_NUMBER_OF_MAIN_RULES;
            long ruleNumber = 1;
            long additionalNumber = 0;
            int[] assignNumberArrays = new int[]{0, 1, 2, 3, 4, 5};
            switch (view.getId()) {
                case R.id.btnSetMain:
                    if (!Util.checkNull(getEdtMainQuantum().getText().toString())) {
                        mainRuleQuantum = Util.convertStringToInt(getEdtMainQuantum().getText().toString());
                    } else {
                        getEdtMainQuantum().setText(String.valueOf(mainRuleQuantum));
                    }

                    bc2Write.writeRuleMain(new RuleMain(mainRuleQuantum, RuleMain.ON));
                    Toast.makeText(getApplicationContext(), getString(R.string.sent), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btnOffMain:
                    getEdtMainQuantum().setText("");
                    bc2Write.writeRuleMain(new RuleMain(0, RuleMain.OFF));
                    Toast.makeText(getApplicationContext(), getString(R.string.sent), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btnSetRuleChild:
                    if (!Util.checkNull(getEdtRuleAdditionalNumber().getText().toString())) {
                        additionalNumber = Util.convertStringToInt(getEdtRuleAdditionalNumber().getText().toString());
                    } else {
                        getEdtRuleAdditionalNumber().setText(String.valueOf(additionalNumber));
                    }

                    if (!Util.checkNull(getSpRule().getSelectedItem().toString())) {
                        ruleNumber = Integer.parseInt(getSpRule().getSelectedItem().toString());
                    } else {
                        getSpRule().setSelection(1);
                    }

                    if (!Util.checkNull(getEdtRuleQuantum().getText().toString())) {
                        ruleQuantum = Util.convertStringToInt(getEdtRuleQuantum().getText().toString());
                    } else {
                        getEdtRuleQuantum().setText(String.valueOf(ruleQuantum));
                    }

                    if (!Util.checkNull(getEdtAssign1().getText().toString())) {
                        assignNumberArrays[0] = Util.convertStringToInt(getEdtAssign1().getText().toString());
                    } else {
                        getEdtAssign1().setText(String.valueOf(assignNumberArrays[0]));
                    }

                    if (!Util.checkNull(getEdtAssign2().getText().toString())) {
                        assignNumberArrays[1] = Util.convertStringToInt(getEdtAssign2().getText().toString());
                    } else {
                        getEdtAssign2().setText(String.valueOf(assignNumberArrays[1]));
                    }

                    if (!Util.checkNull(getEdtAssign3().getText().toString())) {
                        assignNumberArrays[2] = Util.convertStringToInt(getEdtAssign3().getText().toString());
                    } else {
                        getEdtAssign3().setText(String.valueOf(assignNumberArrays[2]));
                    }

                    if (!Util.checkNull(getEdtAssign4().getText().toString())) {
                        assignNumberArrays[3] = Util.convertStringToInt(getEdtAssign4().getText().toString());
                    } else {
                        getEdtAssign4().setText(String.valueOf(assignNumberArrays[3]));
                    }

                    if (!Util.checkNull(getEdtAssign5().getText().toString())) {
                        assignNumberArrays[4] = Util.convertStringToInt(getEdtAssign5().getText().toString());
                    } else {
                        getEdtAssign5().setText(String.valueOf(assignNumberArrays[4]));
                    }

                    if (!Util.checkNull(getEdtAssign6().getText().toString())) {
                        assignNumberArrays[5] = Util.convertStringToInt(getEdtAssign6().getText().toString());
                    } else {
                        getEdtAssign6().setText(String.valueOf(assignNumberArrays[5]));
                    }

                    bc2Write.writeRuleChild(new RuleChild(additionalNumber, bc2Write.exportAssignNumber(assignNumberArrays), ruleQuantum, ruleNumber, RuleOffline.ON));
                    Toast.makeText(getApplicationContext(), getString(R.string.sent), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btnOffRuleChild:
                    getEdtRuleAdditionalNumber().setText("");
                    getSpRule().setSelection(0);
                    getEdtRuleQuantum().setText("");
                    getEdtAssign1().setText("");
                    getEdtAssign2().setText("");
                    getEdtAssign3().setText("");
                    getEdtAssign4().setText("");
                    getEdtAssign5().setText("");
                    getEdtAssign6().setText("");
                    bc2Write.writeRuleChild(new RuleChild(additionalNumber, bc2Write.exportAssignNumber(assignNumberArrays), ruleQuantum, ruleNumber, RuleOffline.OFF));
                    Toast.makeText(getApplicationContext(), getString(R.string.sent), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btnText:
                    bc2Write.writeText(getEdtText().getText().toString().trim());
                    Toast.makeText(getApplicationContext(), getString(R.string.sent), Toast.LENGTH_SHORT).show();
                    break;
            }
        } else {
            Toast.makeText(getApplicationContext(), "Không có kết nối !", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onAllowRequestStringUpdated(String value) {

        ((TextView) findViewById(R.id.txtRequestRetain)).setText(value);
    }
}

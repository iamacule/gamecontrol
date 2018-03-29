package vn.mran.gamecontrol.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
import vn.mran.gamecontrol.controler.BC1Write;
import vn.mran.gamecontrol.model.bc1.RuleChild;
import vn.mran.gamecontrol.model.bc1.RuleMain;
import vn.mran.gamecontrol.model.bc1.RuleOffline;

/**
 * Created by Mr An on 01/12/2017.
 */

public class BC1BattleActivity extends AppCompatActivity implements View.OnClickListener, FirebaseRequest.OnFirebaseRequestChanged {

    private final String TAG = getClass().getSimpleName();

    private BC1Write bc1Write;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bc1_play);

        MainActivity.firebaseRequest.setOnFirebaseRequestChanged(this);

        bc1Write = new BC1Write();

        findViewById(R.id.btnSetMain).setOnClickListener(this);
        findViewById(R.id.btnOffMain).setOnClickListener(this);
        findViewById(R.id.btnSetOffline).setOnClickListener(this);
        findViewById(R.id.btnOffOffline).setOnClickListener(this);
        findViewById(R.id.btnSetRule1).setOnClickListener(this);
        findViewById(R.id.btnOffRule1).setOnClickListener(this);
        findViewById(R.id.btnText).setOnClickListener(this);

        initValue();
    }

    private void initValue() {
        FirebaseDatabase.getInstance().getReference("BC1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Rule
                RuleChild ruleChild = dataSnapshot.child("RuleChild").getValue(RuleChild.class);

                if (ruleChild.status.equals(RuleChild.ON)) {
                    getEdtRuleNumber().setText(String.valueOf(ruleChild.rule));

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

                //Rule Mai
                RuleMain ruleMain = dataSnapshot.child("RuleMain").getValue(RuleMain.class);
                if (ruleMain.status.equals(RuleMain.ON)) {
                    getEdtMainQuantum().setText(String.valueOf(ruleMain.quantum));
                }

                //Rule Offline
                RuleOffline ruleOffline = dataSnapshot.child("RuleOffline").getValue(RuleOffline.class);
                if (ruleOffline.status.equals(RuleOffline.ON)) {
                    String[] assignNumberArrays = ruleOffline.assignNumber.split(" ");
                    getEdtAssignOffline1().setText(String.valueOf(assignNumberArrays[0]));
                    getEdtAssignOffline2().setText(String.valueOf(assignNumberArrays[1]));
                    getEdtAssignOffline3().setText(String.valueOf(assignNumberArrays[2]));
                    getEdtAssignOffline4().setText(String.valueOf(assignNumberArrays[3]));
                    getEdtAssignOffline5().setText(String.valueOf(assignNumberArrays[4]));
                    getEdtAssignOffline6().setText(String.valueOf(assignNumberArrays[5]));

                    getEdtOfflineQuantum().setText(String.valueOf(ruleOffline.quantum));
                    getEdtOfflineAdditionalNumber().setText(String.valueOf(ruleOffline.additionalNumber));
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

    private EditText getEdtAssignOffline1() {
        return (EditText) findViewById(R.id.edtAssignOffline1);
    }

    private EditText getEdtAssignOffline2() {
        return (EditText) findViewById(R.id.edtAssignOffline2);
    }

    private EditText getEdtAssignOffline3() {
        return (EditText) findViewById(R.id.edtAssignOffline3);
    }

    private EditText getEdtAssignOffline4() {
        return (EditText) findViewById(R.id.edtAssignOffline4);
    }

    private EditText getEdtAssignOffline5() {
        return (EditText) findViewById(R.id.edtAssignOffline5);
    }

    private EditText getEdtAssignOffline6() {
        return (EditText) findViewById(R.id.edtAssignOffline6);
    }

    private EditText getEdtOfflineQuantum() {
        return (EditText) findViewById(R.id.edtOfflineQuantum);
    }

    private EditText getEdtOfflineAdditionalNumber() {
        return (EditText) findViewById(R.id.edtOfflineAdditionalNumber);
    }

    private EditText getEdtAssign1() {
        return (EditText) findViewById(R.id.edtRule1Assign1);
    }

    private EditText getEdtAssign2() {
        return (EditText) findViewById(R.id.edtRule1Assign2);
    }

    private EditText getEdtAssign3() {
        return (EditText) findViewById(R.id.edtRule1Assign3);
    }

    private EditText getEdtAssign4() {
        return (EditText) findViewById(R.id.edtRule1Assign4);
    }

    private EditText getEdtAssign5() {
        return (EditText) findViewById(R.id.edtRule1Assign5);
    }

    private EditText getEdtAssign6() {
        return (EditText) findViewById(R.id.edtRule1Assign6);
    }

    private EditText getEdtRuleNumber() {
        return (EditText) findViewById(R.id.edtRuleNumber);
    }

    private EditText getEdtRuleQuantum() {
        return (EditText) findViewById(R.id.edtRule1Quantum);
    }

    private EditText getEdtRuleAdditionalNumber() {
        return (EditText) findViewById(R.id.edtRule1AdditionalNumber);
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

                    bc1Write.writeRuleMain(new RuleMain(mainRuleQuantum, RuleMain.ON));
                    Toast.makeText(getApplicationContext(), getString(R.string.sent), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btnOffMain:
                    getEdtMainQuantum().setText("");
                    bc1Write.writeRuleMain(new RuleMain(0, RuleMain.OFF));
                    Toast.makeText(getApplicationContext(), getString(R.string.sent), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btnSetOffline:
                    if (!Util.checkNull(getEdtOfflineAdditionalNumber().getText().toString())) {
                        additionalNumber = Util.convertStringToInt(getEdtOfflineAdditionalNumber().getText().toString());
                    } else {
                        getEdtOfflineAdditionalNumber().setText(String.valueOf(additionalNumber));
                    }

                    if (!Util.checkNull(getEdtOfflineQuantum().getText().toString())) {
                        ruleQuantum = Util.convertStringToInt(getEdtOfflineQuantum().getText().toString());
                    } else {
                        getEdtOfflineQuantum().setText(String.valueOf(ruleQuantum));
                    }

                    if (!Util.checkNull(getEdtAssignOffline1().getText().toString())) {
                        assignNumberArrays[0] = Util.convertStringToInt(getEdtAssignOffline1().getText().toString());
                    } else {
                        getEdtAssignOffline1().setText(String.valueOf(assignNumberArrays[0]));
                    }

                    if (!Util.checkNull(getEdtAssignOffline2().getText().toString())) {
                        assignNumberArrays[1] = Util.convertStringToInt(getEdtAssignOffline2().getText().toString());
                    } else {
                        getEdtAssignOffline2().setText(String.valueOf(assignNumberArrays[1]));
                    }

                    if (!Util.checkNull(getEdtAssignOffline3().getText().toString())) {
                        assignNumberArrays[2] = Util.convertStringToInt(getEdtAssignOffline3().getText().toString());
                    } else {
                        getEdtAssignOffline3().setText(String.valueOf(assignNumberArrays[2]));
                    }

                    if (!Util.checkNull(getEdtAssignOffline4().getText().toString())) {
                        assignNumberArrays[3] = Util.convertStringToInt(getEdtAssignOffline4().getText().toString());
                    } else {
                        getEdtAssignOffline4().setText(String.valueOf(assignNumberArrays[3]));
                    }

                    if (!Util.checkNull(getEdtAssignOffline5().getText().toString())) {
                        assignNumberArrays[4] = Util.convertStringToInt(getEdtAssignOffline5().getText().toString());
                    } else {
                        getEdtAssignOffline5().setText(String.valueOf(assignNumberArrays[4]));
                    }

                    if (!Util.checkNull(getEdtAssignOffline6().getText().toString())) {
                        assignNumberArrays[5] = Util.convertStringToInt(getEdtAssignOffline6().getText().toString());
                    } else {
                        getEdtAssignOffline6().setText(String.valueOf(assignNumberArrays[5]));
                    }

                    bc1Write.writeRuleOffline(new RuleOffline(additionalNumber, bc1Write.exportAssignNumber(assignNumberArrays), ruleQuantum, RuleOffline.ON));
                    Toast.makeText(getApplicationContext(), getString(R.string.sent), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btnOffOffline:
                    getEdtOfflineAdditionalNumber().setText("");
                    getEdtOfflineQuantum().setText("");
                    getEdtAssignOffline1().setText("");
                    getEdtAssignOffline2().setText("");
                    getEdtAssignOffline3().setText("");
                    getEdtAssignOffline4().setText("");
                    getEdtAssignOffline5().setText("");
                    getEdtAssignOffline6().setText("");
                    bc1Write.writeRuleOffline(new RuleOffline(additionalNumber, bc1Write.exportAssignNumber(assignNumberArrays), ruleQuantum, RuleOffline.OFF));
                    Toast.makeText(getApplicationContext(), getString(R.string.sent), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btnSetRule1:
                    if (!Util.checkNull(getEdtRuleAdditionalNumber().getText().toString())) {
                        additionalNumber = Util.convertStringToInt(getEdtRuleAdditionalNumber().getText().toString());
                    } else {
                        getEdtRuleAdditionalNumber().setText(String.valueOf(additionalNumber));
                    }

                    if (!Util.checkNull(getEdtRuleNumber().getText().toString())) {
                        ruleNumber = Util.convertStringToInt(getEdtRuleNumber().getText().toString());
                    } else {
                        getEdtRuleNumber().setText(String.valueOf(ruleNumber));
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

                    bc1Write.writeRuleChild(new RuleChild(additionalNumber, bc1Write.exportAssignNumber(assignNumberArrays), ruleQuantum, ruleNumber, RuleOffline.ON));
                    Toast.makeText(getApplicationContext(), getString(R.string.sent), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btnOffRule1:
                    getEdtRuleAdditionalNumber().setText("");
                    getEdtRuleNumber().setText("");
                    getEdtRuleQuantum().setText("");
                    getEdtAssign1().setText("");
                    getEdtAssign2().setText("");
                    getEdtAssign3().setText("");
                    getEdtAssign4().setText("");
                    getEdtAssign5().setText("");
                    getEdtAssign6().setText("");
                    bc1Write.writeRuleChild(new RuleChild(additionalNumber, bc1Write.exportAssignNumber(assignNumberArrays), ruleQuantum, ruleNumber, RuleOffline.OFF));
                    Toast.makeText(getApplicationContext(), getString(R.string.sent), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btnText:
                    bc1Write.writeText(getEdtText().getText().toString().trim());
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
    public void onAllowRequestStringUpdated(String value) {
        ((TextView) findViewById(R.id.txtRequestRetain)).setText(value);
    }
}

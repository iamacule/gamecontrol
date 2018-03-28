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
import vn.mran.gamecontrol.controler.XD1Write;
import vn.mran.gamecontrol.model.xd1.RuleChild;
import vn.mran.gamecontrol.model.xd1.RuleMain;
import vn.mran.gamecontrol.model.xd1.RuleOffline;

/**
 * Created by Mr An on 01/12/2017.
 */

public class XD1Activity extends AppCompatActivity implements View.OnClickListener, FirebaseRequest.OnFirebaseRequestChanged {

    private final String TAG = getClass().getSimpleName();

    private XD1Write xd1Write;

    private EditText edtRule1;
    private EditText edtRule2;
    private EditText edtMain;
    private EditText edtText;
    private EditText edtOffline;
    private EditText edtAssign1;
    private EditText edtAssign2;
    private EditText edtAssign3;
    private EditText edtAssign4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xd1);

        MainActivity.firebaseRequest.setOnFirebaseRequestChanged(this);

        xd1Write = new XD1Write();

        findViewById(R.id.btnSetRuleChild).setOnClickListener(this);
        findViewById(R.id.btnSet2).setOnClickListener(this);
        findViewById(R.id.btnSetMain).setOnClickListener(this);
        findViewById(R.id.btnOffMain).setOnClickListener(this);
        findViewById(R.id.btnText).setOnClickListener(this);
        findViewById(R.id.btnSetOffline).setOnClickListener(this);
        findViewById(R.id.btnOffOffline).setOnClickListener(this);
        findViewById(R.id.btnAssign).setOnClickListener(this);

        edtRule1 = findViewById(R.id.edtRule1);
        edtRule2 = findViewById(R.id.edtRule2);
        edtMain = findViewById(R.id.edtMain);
        edtText = findViewById(R.id.edtText);
        edtOffline = findViewById(R.id.edtOffline);
        edtAssign1 = findViewById(R.id.edtAssign1);
        edtAssign2 = findViewById(R.id.edtAssign2);
        edtAssign3 = findViewById(R.id.edtAssign3);
        edtAssign4 = findViewById(R.id.edtAssign4);

        initValue();
    }

    private void initValue() {
        FirebaseDatabase.getInstance().getReference("XD1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                RuleChild ruleChild = dataSnapshot.child("RuleChild").getValue(RuleChild.class);
                Log.d(TAG, "RuleChild : " + "quantum = " + ruleChild.quantum + " , rule = " + ruleChild.rule);
                RuleMain ruleMain = dataSnapshot.child("RuleMain").getValue(RuleMain.class);
                Log.d(TAG, "RuleMain : " + "quantum = " + ruleMain.quantum + " , status = " + ruleMain.status);
                RuleOffline ruleOffline = dataSnapshot.child("RuleOffline").getValue(RuleOffline.class);
                Log.d(TAG, "RuleOffline : " + "quantum = " + ruleOffline.quantum + " , status = " + ruleOffline.status);
                String text = dataSnapshot.child("Text").getValue().toString();
                String assignNumber = dataSnapshot.child("AssignNumber").getValue().toString();
                Log.d(TAG, "Text : " + text);

                //Rule
                int rule = Integer.parseInt(ruleChild.rule);
                switch (rule) {
                    case 1:
                        edtRule1.setText(ruleChild.quantum);
                        break;

                    case 2:
                        edtRule2.setText(ruleChild.quantum);
                        break;
                }

                //Rule main
                if (ruleMain.status.equals(RuleMain.ON)) {
                    edtMain.setText(ruleMain.quantum);
                }

                //Rule offline
                if (ruleOffline.status.equals(RuleMain.ON)) {
                    edtOffline.setText(ruleMain.quantum);
                }

                //Text
                edtText.setText(text);

                //Assign number
                String[] assignNumberArrays = assignNumber.split(" ");
                edtAssign1.setText(assignNumberArrays[0]);
                edtAssign2.setText(assignNumberArrays[1]);
                edtAssign3.setText(assignNumberArrays[2]);
                edtAssign4.setText(assignNumberArrays[3]);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, databaseError.getMessage());
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (isOnline()) {
            int numberOfRule = Constant.DEFAULT_SERVER_NUMBER_OF_RULES;
            int numberOfMainRule = Constant.DEFAULT_SERVER_NUMBER_OF_MAIN_RULES;
            switch (view.getId()) {

                //Main rule on
                case R.id.btnSetMain:
                    if (!Util.checkNull(edtMain.getText().toString())) {
                        numberOfMainRule = Util.convertStringToInt(edtMain.getText().toString());
                    } else
                        edtMain.setText(String.valueOf(numberOfMainRule));

                    xd1Write.writeRuleMain(new RuleMain(String.valueOf(numberOfMainRule), RuleMain.ON));
                    Toast.makeText(getApplicationContext(), getString(R.string.sent), Toast.LENGTH_SHORT).show();
                    break;

                //Main rule off
                case R.id.btnOffMain:
                    edtMain.setText("");
                    xd1Write.writeRuleMain(new RuleMain("5000", RuleMain.OFF));
                    Toast.makeText(getApplicationContext(), getString(R.string.sent), Toast.LENGTH_SHORT).show();
                    break;

                //Rule offline on
                case R.id.btnSetOffline:
                    if (!Util.checkNull(edtOffline.getText().toString())) {
                        numberOfMainRule = Util.convertStringToInt(edtOffline.getText().toString());
                    } else
                        edtOffline.setText(String.valueOf(numberOfMainRule));

                    xd1Write.writeRuleOffline(new RuleOffline(String.valueOf(numberOfMainRule), RuleMain.ON));
                    Toast.makeText(getApplicationContext(), getString(R.string.sent), Toast.LENGTH_SHORT).show();
                    break;

                //Rule offline off
                case R.id.btnOffOffline:
                    edtOffline.setText("");
                    xd1Write.writeRuleOffline(new RuleOffline("5000", RuleMain.OFF));
                    Toast.makeText(getApplicationContext(), getString(R.string.sent), Toast.LENGTH_SHORT).show();
                    break;

                //Rule 1
                case R.id.btnSetRuleChild:
                    if (!Util.checkNull(edtRule1.getText().toString())) {
                        numberOfRule = Util.convertStringToInt(edtRule1.getText().toString());
                    } else {
                        edtRule1.setText(String.valueOf(numberOfRule));
                        edtRule2.setText("");
                    }
                    xd1Write.writeRuleChild(new RuleChild(String.valueOf(numberOfRule), "1"));
                    Toast.makeText(getApplicationContext(), getString(R.string.sent), Toast.LENGTH_SHORT).show();
                    break;

                //Rule 2
                case R.id.btnSet2:
                    if (!Util.checkNull(edtRule2.getText().toString())) {
                        numberOfRule = Util.convertStringToInt(edtRule2.getText().toString());
                    } else {
                        edtRule2.setText(String.valueOf(numberOfRule));
                        edtRule1.setText("");
                    }
                    xd1Write.writeRuleChild(new RuleChild(String.valueOf(numberOfRule), "2"));
                    Toast.makeText(getApplicationContext(), getString(R.string.sent), Toast.LENGTH_SHORT).show();
                    break;

                //Text
                case R.id.btnText:
                    xd1Write.writeText(edtText.getText().toString().trim());
                    Toast.makeText(getApplicationContext(), getString(R.string.sent), Toast.LENGTH_SHORT).show();
                    break;

                //Assign
                case R.id.btnAssign:
                    if (Util.checkNull(edtAssign1.getText().toString()) ||
                            Util.checkNull(edtAssign2.getText().toString()) ||
                            Util.checkNull(edtAssign3.getText().toString()) ||
                            Util.checkNull(edtAssign4.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Gán số không được để trống ô nào hết !", Toast.LENGTH_SHORT).show();
                    } else {
                        xd1Write.writeAssignNumber(edtAssign1.getText().toString(),
                                edtAssign2.getText().toString(),
                                edtAssign3.getText().toString(),
                                edtAssign4.getText().toString());
                    }
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

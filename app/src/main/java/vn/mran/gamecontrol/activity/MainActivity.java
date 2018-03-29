package vn.mran.gamecontrol.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import vn.mran.gamecontrol.R;
import vn.mran.gamecontrol.activity.BC1Activity;
import vn.mran.gamecontrol.activity.XD1Activity;
import vn.mran.gamecontrol.util.FirebaseRequest;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static FirebaseRequest firebaseRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btnXD1).setOnClickListener(this);
        findViewById(R.id.btnBC1).setOnClickListener(this);
        findViewById(R.id.btnBC2).setOnClickListener(this);
        findViewById(R.id.btnBC3).setOnClickListener(this);
        findViewById(R.id.btnChangePass).setOnClickListener(this);

        firebaseRequest = new FirebaseRequest(getApplicationContext());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnXD1:
                startActivity(new Intent(this, XD1Activity.class));
                break;
            case R.id.btnBC1:
                startActivity(new Intent(this, BC1Activity.class));
                break;
            case R.id.btnBC2:
                startActivity(new Intent(this, BC2Activity.class));
                break;
            case R.id.btnBC3:
                startActivity(new Intent(this, BC3Activity.class));
                break;
            case R.id.btnChangePass:
                startActivity(new Intent(this, ChangePassActivity.class));
                break;
        }
    }
}

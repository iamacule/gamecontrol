package vn.mran.gamecontrol.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import vn.mran.gamecontrol.R;

/**
 * Created by Mr An on 01/12/2017.
 */

public class BC1Activity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bc1);
        findViewById(R.id.btnPlay).setOnClickListener(this);
        findViewById(R.id.btnBattle).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnPlay:
                startActivity(new Intent(this,BC1PlayActivity.class));
                break;
            case R.id.btnBattle:
                startActivity(new Intent(this,BC1BattleActivity.class));
                break;
        }
    }
}

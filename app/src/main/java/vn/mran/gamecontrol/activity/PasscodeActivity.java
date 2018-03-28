package vn.mran.gamecontrol.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import vn.mran.gamecontrol.R;
import vn.mran.gamecontrol.widget.PassCodeView;

public class PasscodeActivity extends AppCompatActivity implements View.OnClickListener {
    private PassCodeView passCodeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass);
        passCodeView = findViewById(R.id.pass_code_view);

        final String pass = getSharedPreferences("GAMECONTROL", Context.MODE_PRIVATE).getString("PASS", "5555");
        passCodeView.setOnTextChangeListener(new PassCodeView.TextChangeListener() {
            @Override
            public void onTextChanged(String text) {
                if (text.equals(pass)) {
                    startActivity(new Intent(PasscodeActivity.this, MainActivity.class));
                    finish();
                } else {
                    if (passCodeView.getPassCodeText().length() == 4) {
                        passCodeView.reset();
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
    }
}

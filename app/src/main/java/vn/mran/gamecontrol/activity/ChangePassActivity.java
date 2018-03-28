package vn.mran.gamecontrol.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import vn.mran.gamecontrol.R;

public class ChangePassActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView txtError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);

        txtError = findViewById(R.id.txtError);

        findViewById(R.id.btnChangePass).setOnClickListener(this);
    }

    private EditText getEdtOldPass() {
        return (EditText) findViewById(R.id.edtOldPass);
    }

    private EditText getEdtNewPass() {
        return (EditText) findViewById(R.id.edtNewPass);
    }

    private EditText getEdtNewPass2() {
        return (EditText) findViewById(R.id.edtNewPass2);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnChangePass:
                String result = check();
                txtError.setText(result);
                if (result.equals(getString(R.string.change_pass_success))) {
                    txtError.setTextColor(Color.GREEN);
                    getSharedPreferences("GAMECONTROL", Context.MODE_PRIVATE).edit().putString("PASS", getEdtNewPass().getText().toString().trim()).apply();
                }
                break;
        }
    }

    private String check() {
        final String pass = getSharedPreferences("GAMECONTROL", Context.MODE_PRIVATE).getString("PASS", "5555");
        if (getEdtOldPass().getText() == null || getEdtOldPass().getText().length() == 0 || getEdtOldPass().getText().toString().trim().equals(""))
            return getString(R.string.wrong_null);
        if (!getEdtOldPass().getText().toString().trim().equals(pass))
            return getString(R.string.wrong_pass);
        if (getEdtOldPass().getText().length() != 4)
            return getString(R.string.wrong_length);
        if (getEdtNewPass().getText() == null || getEdtNewPass().getText().length() == 0 || getEdtNewPass().getText().toString().trim().equals(""))
            return getString(R.string.wrong_null);
        if (getEdtNewPass().getText().length() != 4 || getEdtNewPass2().getText().length() != 4)
            return getString(R.string.wrong_length);
        if (getEdtNewPass2().getText() == null || getEdtNewPass2().getText().length() == 0 || getEdtNewPass2().getText().toString().trim().equals(""))
            return getString(R.string.wrong_null);
        if (!getEdtNewPass().getText().toString().trim().equals(getEdtNewPass2().getText().toString().trim()))
            return getString(R.string.wrong_2_pass);
        return getString(R.string.change_pass_success);

    }
}

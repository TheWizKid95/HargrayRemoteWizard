package com.twk95.remotewizard;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.twk95.remotewizard.steps.TelevisionSelection;

public class ProgramActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remote_tutorial);

        SharedPreferences prefs = this.getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        int tvPower = prefs.getInt("tvPower", 1);
        if (tvPower == 1) {
            Toast.makeText(this, R.string.turn_on, Toast.LENGTH_LONG).show();
            editor.putInt("tvPower", 0);
            editor.apply();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TelevisionSelection.tvSelection = 0;
    }
}

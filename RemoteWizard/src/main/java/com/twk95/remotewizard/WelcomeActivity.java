package com.twk95.remotewizard;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class WelcomeActivity extends AppCompatActivity {
    private String[] WelcomeOptions = {"Store locations", "Call Hargray Repair", "Hargray Website", "Saved TV Codes"};
    SharedPreferences prefs;
    ListView tvList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        assert getSupportActionBar() != null;
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("");
        prefs = this.getSharedPreferences("Preferences", Context.MODE_PRIVATE);

        tvList = (ListView) findViewById(R.id.welcomeList);
        ImageButton fabButton = (ImageButton) findViewById(R.id.fab_button);
        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(WelcomeActivity.this, RemoteSelection.class);
                startActivity(intent);
            }
        });
        fabButton.bringToFront();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.welcome_item, WelcomeOptions);
        tvList.setOnItemClickListener(new ListItemClickListener());
        tvList.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        final String code = prefs.getString("code", null);

        if (code != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final EditText editText = new EditText(this);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT);
            params.leftMargin = 64;
            params.rightMargin = 64;
            editText.setLayoutParams(params);
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
            editText.setHint("e.g., 'Bedroom TV'");
            FrameLayout layout = new FrameLayout(this);
            layout.addView(editText);
            builder.setTitle("Save code")
                    .setMessage("Did the code work for you? If so, save it and give it a name!")
                    .setCancelable(false)
                    .setView(layout)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            String name = editText.getText().toString();
                            SavedCodes.listCodes.add(code);
                            SavedCodes.listNames.add(name);
                            Intent intent;
                            intent = new Intent(WelcomeActivity.this, SavedCodes.class);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            final AlertDialog alert = builder.create();
            alert.show();
            alert.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    String name = editText.getText().toString();
                    if (name.matches("")) {
                        alert.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                    } else {
                        alert.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                    }
                }
            });
        }
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove("code");
        editor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, "About");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("About The App")
                        .setMessage(R.string.about_app)
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                final AlertDialog alert = builder.create();
                alert.show();
                return true;
            case 1:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class ListItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0:
                    CharSequence stores[] = new CharSequence[]{"Bluffton", "Beaufort", "Hilton Head"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(WelcomeActivity.this);
                    builder.setTitle("What store?");
                    builder.setItems(stores, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent maps;
                            switch (which) {
                                case 0:
                                    maps = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:32.2700146,-80.8603446?z=19"));
                                    startActivity(maps);
                                    break;
                                case 1:
                                    maps = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:32.4369322,-80.7092233?z=19"));
                                    startActivity(maps);
                                    break;
                                case 2:
                                    maps = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:32.1729429,-80.73572?z=19"));
                                    startActivity(maps);
                                    break;
                            }
                        }
                    });
                    builder.show();
                    break;
                case 1:
                    Intent call = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:8436861138"));
                    startActivity(call);
                    break;
                case 2:
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.hargray.com"));
                    startActivity(browserIntent);
                    break;
                case 3:
                    SharedPreferences codePrefs = WelcomeActivity.this.getSharedPreferences("Code_Items", MODE_PRIVATE);
                    String codeString = codePrefs.getString("0", "");

                    if (codeString.equals("")) {
                        Toast.makeText(WelcomeActivity.this, "No Codes Saved", Toast.LENGTH_LONG).show();
                    } else {
                        Intent intent;
                        intent = new Intent(WelcomeActivity.this, SavedCodes.class);
                        startActivity(intent);
                    }
                    break;
            }
        }
    }
}

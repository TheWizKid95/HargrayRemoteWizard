package com.twk95.remotewizard;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class SavedCodes extends AppCompatActivity {
    public static ArrayList<String> listCodes = new ArrayList<>();
    public static ArrayList<String> listNames = new ArrayList<>();
    public static SharedPreferences codePrefs, namePrefs;
    static CodeViewAdapter adapter;
    static int codePosition;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getSupportActionBar() != null;
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("Saved Codes");
        MyFragment fragment = new MyFragment();
        getSupportFragmentManager().beginTransaction().replace(android.R.id.content, fragment).commit();
    }

    public static class MyFragment extends ListFragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            adapter = new CodeViewAdapter(inflater.getContext(), R.layout.code_item, listNames, listCodes);
            setListAdapter(adapter);
            adapter.notifyDataSetChanged();
            return super.onCreateView(inflater, container, savedInstanceState);
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            final ListView lv = getListView();
            lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    codePosition = position;
                    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Delete Code")
                            .setMessage("Are you sure you want to delete this code?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    listCodes.remove(codePosition);
                                    listNames.remove(codePosition);
                                    adapter.notifyDataSetChanged();
                                    if (listCodes.isEmpty() || listNames.isEmpty())
                                        getActivity().finish();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                    return false;
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        namePrefs = this.getSharedPreferences("Name_Items", MODE_PRIVATE);
        codePrefs = this.getSharedPreferences("Code_Items", MODE_PRIVATE);
        for (int i = 0; ; ++i) {
            final String nameString = namePrefs.getString(String.valueOf(i), "");
            final String codeString = codePrefs.getString(String.valueOf(i), "");
            if (!codeString.equals("") | !nameString.equals("")) {
                listNames.add(nameString);
                listCodes.add(codeString);
            } else {
                break;
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor nameEditor = namePrefs.edit();
        SharedPreferences.Editor codeEditor = codePrefs.edit();
        nameEditor.clear();
        codeEditor.clear();
        for (int i = 0; i < adapter.getCount(); ++i) {
            nameEditor.putString(String.valueOf(i), listNames.get(i));
            codeEditor.putString(String.valueOf(i), listCodes.get(i));
        }
        nameEditor.apply();
        codeEditor.apply();
        listCodes.clear();
        listNames.clear();
    }

}
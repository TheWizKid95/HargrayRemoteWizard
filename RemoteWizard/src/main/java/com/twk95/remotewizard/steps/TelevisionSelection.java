package com.twk95.remotewizard.steps;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.twk95.remotewizard.R;

import org.codepond.wizardroid.WizardStep;


public class TelevisionSelection extends WizardStep {
    String[] mTelevisions;

    public static Integer tvSelection = 0;

    public TelevisionSelection() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View v = inflater.inflate(R.layout.televisions, container, false);

        ListView tvList = (ListView) v.findViewById(R.id.tvList);

        mTelevisions = getResources().getStringArray(R.array.televisions);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_single_choice, mTelevisions);
        tvList.setOnItemClickListener(new ListItemClickListener());
        tvList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        tvList.setAdapter(adapter);
        tvList.setItemChecked(0, true);

        return v;
    }

    private class ListItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            tvSelection = position;
        }
    }
}


package com.twk95.remotewizard.steps;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.twk95.remotewizard.R;

import org.codepond.wizardroid.WizardStep;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProgramCodes extends WizardStep {

    Integer i = 0, codesSize;
    String currentCode;

    public ProgramCodes() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.codes, container, false);

        final TextView codesText, codeInstructions;
        codesText = (TextView) v.findViewById(R.id.code);
        codeInstructions = (TextView) v.findViewById(R.id.code_instructions);

        Intent intent = getActivity().getIntent();
        Integer remote = intent.getIntExtra("remote", 0);

        final String[] remoteCodes, tvCodes, tvType;
        final List<String> remoteCodesList, tvCodesList, commonCodes;

        switch (remote) {
            case 0:
                remoteCodes = getResources().getStringArray(R.array.phazr_codes);
                codeInstructions.setText(R.string.easy_code);
                break;
            case 1:
                remoteCodes = getResources().getStringArray(R.array.dta_codes);
                codeInstructions.setText(R.string.dta_code);
                break;
            case 2:
                remoteCodes = getResources().getStringArray(R.array.dta2_codes);
                codeInstructions.setText(R.string.easy_code);
                break;
            default:
                remoteCodes = getResources().getStringArray(R.array.phazr_codes);
                codeInstructions.setText(R.string.easy_code);
                break;
        }

        List<String[]> tvList = new ArrayList<>();
        tvList.add(getResources().getStringArray(R.array.Dynex));
        tvList.add(getResources().getStringArray(R.array.Element));
        tvList.add(getResources().getStringArray(R.array.Hitachi));
        tvList.add(getResources().getStringArray(R.array.Hyundai));
        tvList.add(getResources().getStringArray(R.array.Insignia));
        tvList.add(getResources().getStringArray(R.array.JVC));
        tvList.add(getResources().getStringArray(R.array.LG));
        tvList.add(getResources().getStringArray(R.array.Mitsubishi));
        tvList.add(getResources().getStringArray(R.array.Panasonic));
        tvList.add(getResources().getStringArray(R.array.Phillips));
        tvList.add(getResources().getStringArray(R.array.RCA));
        tvList.add(getResources().getStringArray(R.array.Samsung));
        tvList.add(getResources().getStringArray(R.array.Sanyo));
        tvList.add(getResources().getStringArray(R.array.Sharp));
        tvList.add(getResources().getStringArray(R.array.Sony));
        tvList.add(getResources().getStringArray(R.array.Toshiba));
        tvList.add(getResources().getStringArray(R.array.Vizio));
        tvList.add(getResources().getStringArray(R.array.Westinghouse));

        tvCodes = tvList.get(TelevisionSelection.tvSelection);
        remoteCodesList = Arrays.asList(remoteCodes);
        tvCodesList = Arrays.asList(tvCodes);
        commonCodes = new ArrayList<>(remoteCodesList);
        commonCodes.retainAll(tvCodesList);

        codesSize = commonCodes.size();
        codesText.setText(commonCodes.get(i));
        currentCode = commonCodes.get(i);

        tvType = getResources().getStringArray(R.array.televisions);
        final String tvBrand = tvType[TelevisionSelection.tvSelection];

        final TextView codeCount = (TextView) v.findViewById(R.id.code_count);
        codeCount.setText(1 + " of " + codesSize + " " + tvBrand + " codes");

        final Button nextButton, previousButton;

        previousButton = (Button) v.findViewById(R.id.previous);
        nextButton = (Button) v.findViewById(R.id.next);

        nextButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        i++;
                        if (i < commonCodes.size()) {
                            codesText.setText(commonCodes.get(i));
                            currentCode = commonCodes.get(i);
                            codeCount.setText((i + 1) + " of " + codesSize + " " + tvBrand + " codes");
                            previousButton.setEnabled(true);
                        }
                        if (i == commonCodes.size() - 1) {
                            v.setEnabled(false);
                        }
                        break;
                }
                return false;
            }
        });

        previousButton.setEnabled(false);
        previousButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        i--;
                        if (i >= 0)
                            nextButton.setEnabled(true);
                        codesText.setText(commonCodes.get(i));
                        currentCode = commonCodes.get(i);
                        codeCount.setText((i + 1) + " of " + codesSize + " " + tvBrand + " codes");
                        if (i == 0) {
                            v.setEnabled(false);
                        }
                        break;
                }
                return false;
            }
        });
        return v;
    }

    @Override
    public void onExit(int exitCode) {
        super.onExit(exitCode);
        SharedPreferences prefs = getActivity().getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("code", currentCode);
        editor.apply();
    }
}
package com.twk95.remotewizard.steps;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.twk95.remotewizard.R;

import org.codepond.wizardroid.WizardStep;


public class ProgramMode extends WizardStep {

    public ProgramMode() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.program_mode, container, false);
        ImageView remotePicture = (ImageView) v.findViewById(R.id.remote_highlighted);
        TextView remoteInstructions = (TextView) v.findViewById(R.id.program_instructions);

        Intent intent = getActivity().getIntent();
        Integer remote = intent.getIntExtra("remote", 0);

        switch (remote) {
            case 0:
                remotePicture.setImageResource(R.drawable.remote_0_highlighted);
                remoteInstructions.setText(R.string.easy_instructions);
                break;
            case 1:
                remotePicture.setImageResource(R.drawable.remote_1_highlighted);
                remoteInstructions.setText(R.string.dta_instructions);
                break;
            case 2:
                remotePicture.setImageResource(R.drawable.remote_2_highlighted);
                remoteInstructions.setText(R.string.easy_instructions);
                break;
            default:
                remotePicture.setImageResource(R.drawable.remote_0_highlighted);
                remoteInstructions.setText(R.string.easy_instructions);
                break;
        }
        return v;
    }
}


package com.twk95.remotewizard;

import android.content.Intent;

import com.twk95.remotewizard.steps.ProgramCodes;
import com.twk95.remotewizard.steps.ProgramMode;
import com.twk95.remotewizard.steps.TelevisionSelection;

import org.codepond.wizardroid.WizardFlow;
import org.codepond.wizardroid.layouts.BasicWizardLayout;

public class ProgramWizard extends BasicWizardLayout {

    public ProgramWizard() {
        super();
    }

    @Override
    public WizardFlow onSetup() {
        return new WizardFlow.Builder()
                .addStep(TelevisionSelection.class)
                .addStep(ProgramMode.class)
                .addStep(ProgramCodes.class)
                .create();
    }

    @Override
    public void onWizardComplete() {
        super.onWizardComplete();

        Intent i = new Intent(getActivity(), WelcomeActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);

    }
}

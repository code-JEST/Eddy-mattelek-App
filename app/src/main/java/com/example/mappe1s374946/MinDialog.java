package com.example.mappe1s374946;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;

public class MinDialog extends DialogFragment {
    private MittInterface callback;

    public interface MittInterface {
        public void onYesClick();
        public void onNoClick();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            callback = (MittInterface) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getString(R.string.feil_melding));
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new
                AlertDialog.Builder(getActivity()).setTitle(getString(R.string.dialog_title)).setPositiveButton(getString(R.string.dialog_yes),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                callback.onYesClick();
                            }
                        }).setNegativeButton(getString(R.string.dialog_no), new
                        DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                callback.onNoClick();
                            }
                        })
                .create();
    }
}

package com.eddev.android.gamersight.presenter;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.eddev.android.gamersight.R;
import com.eddev.android.gamersight.GiantBombUtility;

import java.util.ArrayList;

/**
 * Created by ed on 11/21/16.
 */

public class FilterByConsoleDialog extends DialogFragment {
    private ArrayList<String> selectedItems;
    private FilterByConsoleDialogListener listener;

    public boolean[] getBooleanSelectedItems(ArrayList<String> selectedItems, String[] consoleNameResource) {
        boolean[] booleanArray = new boolean[consoleNameResource.length];
        int i = 0;
        for(String current: consoleNameResource){
            booleanArray[i] = selectedItems.contains(current);
            i++;
        }
        return booleanArray;
    }


    public interface FilterByConsoleDialogListener {
        void onFilterByConsoleClick(ArrayList<String> selectedItems);
        void onCancelFilterClick();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final String[] consoleNameResource = GiantBombUtility.getConsolesArrayResourceAsStringArray(getContext());
        boolean[] booleanArray  = getBooleanSelectedItems(selectedItems,consoleNameResource);
        builder.setTitle(R.string.filter_by_console_dialog_title)
                .setMultiChoiceItems(consoleNameResource, booleanArray,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which,
                                                boolean isChecked) {
                                if (!isChecked) {
                                    selectedItems.remove(consoleNameResource[which]);
                                } else {
                                    selectedItems.add(consoleNameResource[which]);
                                }
                            }
                        })
                .setPositiveButton(R.string.filter_by_console_dialog_ok_option, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onFilterByConsoleClick(selectedItems);
                    }
                })
                .setNegativeButton(R.string.filter_by_console_dialog_cancel_option, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onCancelFilterClick();
                    }
                });
        return builder.create();
    }

    public void setListener(FilterByConsoleDialogListener listener) {
        this.listener = listener;
    }

    public void setSelectedItems(ArrayList<String> selectedItems) {
        this.selectedItems = selectedItems;
    }

    public ArrayList getSelectedItems() {
        return selectedItems;
    }

}

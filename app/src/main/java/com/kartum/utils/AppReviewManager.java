package com.kartum.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

public class AppReviewManager {
    private static final AppReviewManager ourInstance = new AppReviewManager();

    public static AppReviewManager getInstance() {
        return ourInstance;
    }

    private AppReviewManager() {
    }

    public void promptForReviewAfterSavingMoodEntry(Context context, DialogInterface.OnClickListener proceedToStoreClickListener) {
        int moodEntryCount = Utils.getPref(context, "mood_entry_count", 0);
        boolean hasProceededToAppStore = Utils.getPref(context, "has_proceeded_to_app_store", false);

        // user already has clicked to proceed to google play store
        // don't spam the user
        if (hasProceededToAppStore)
            return;

        moodEntryCount++;

        boolean shouldPromptForReview =
                    moodEntryCount == 4 ||
                    moodEntryCount % 12 == 0;

        if (!shouldPromptForReview)
            return;

        new AlertDialog.Builder(context)
                .setTitle("Faro10")
                .setMessage("Enjoying Faro10? Rate us in the App Store")
                .setPositiveButton("Proceed", proceedToStoreClickListener)
                .setNegativeButton("Later", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }
}

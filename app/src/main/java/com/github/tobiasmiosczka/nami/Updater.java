package com.github.tobiasmiosczka.nami;

import android.content.Context;

import com.github.javiersantos.appupdater.AppUpdater;
import com.github.javiersantos.appupdater.enums.UpdateFrom;

public class Updater {

    private static final String USER = "tobiasmiosczka";
    private static final String REPO = "nami-android";

    public static void checkUpdate(Context context) {
        new AppUpdater(context)
                .setUpdateFrom(UpdateFrom.GITHUB)
                .setGitHubUserAndRepo(USER, REPO)
                .setButtonDoNotShowAgain(null)
                .start();
    }
}

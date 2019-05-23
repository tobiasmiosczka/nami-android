package com.github.tobiasmiosczka.nami;

import android.content.Context;

import com.github.javiersantos.appupdater.AppUpdater;
import com.github.javiersantos.appupdater.enums.UpdateFrom;

public class Updater {

    public static void checkUpdate(Context context) {
        new AppUpdater(context)
                .setUpdateFrom(UpdateFrom.GITHUB)
                .setGitHubUserAndRepo("tobiasmiosczka", "nami-android")
                .start();
    }
}

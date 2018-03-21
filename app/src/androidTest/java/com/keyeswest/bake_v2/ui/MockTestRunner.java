package com.keyeswest.bake_v2.ui;


import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.test.runner.AndroidJUnitRunner;

public class MockTestRunner extends AndroidJUnitRunner {

        @Override
        public void onCreate(Bundle arguments) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
            super.onCreate(arguments);
        }
        @Override
        public Application newApplication(ClassLoader cl, String className, Context context)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException {
            return super.newApplication(cl, TestApp.class.getName(), context);
        }
}


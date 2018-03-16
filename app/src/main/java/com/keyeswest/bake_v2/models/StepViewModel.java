package com.keyeswest.bake_v2.models;


import android.content.Context;

public class StepViewModel {
    private Context mContext;
    private Step mStep;

    public StepViewModel(Context context, Step step){
        mContext = context;
        mStep = step;

    }


    public String getDescription(){
        return mStep.getDescription();
    }

    public String getVideoURL ()
    {
        return mStep.getVideoURL();
    }



    public String getListLabel(){
        return mStep.getShortDescription();
    }

    public Step getStep(){
        return mStep;
    }



}

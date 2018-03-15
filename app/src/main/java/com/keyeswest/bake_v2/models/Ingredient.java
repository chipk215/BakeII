package com.keyeswest.bake_v2.models;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;



public class Ingredient implements Parcelable {

    @SerializedName("measure")
    private String mMeasure;

    @SerializedName("ingredient")
    private String mIngredientName;

    @SerializedName("quantity")
    private float mQuantity;



    public static final Creator<Ingredient> CREATOR
            = new Creator<Ingredient>(){

        @Override
        public Ingredient createFromParcel(Parcel source) {
            return new Ingredient(source);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    public Ingredient(){

    }

    private Ingredient(Parcel in){
        mIngredientName = in.readString();
        mMeasure = in.readString();
        mQuantity = in.readFloat();


    }

    public String getMeasure ()
    {
        return mMeasure;
    }

    public void setMeasure (String measure)
    {
        this.mMeasure = measure;
    }

    public String getIngredientName()
    {
        return mIngredientName;
    }

    public void setIngredientName(String ingredientName)
    {
        this.mIngredientName = ingredientName;
    }

    public float getQuantity ()
    {
        return mQuantity;
    }

    public void setQuantity (float quantity)
    {
        this.mQuantity = quantity;
    }



    @Override
    public String toString()
    {
        return "Ingredient [mMeasure = "+ mMeasure +", mIngredientName = "+ mIngredientName +", mQuantity = "+ mQuantity +"]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mIngredientName);
        dest.writeString(mMeasure);
        dest.writeFloat(mQuantity);

    }

}

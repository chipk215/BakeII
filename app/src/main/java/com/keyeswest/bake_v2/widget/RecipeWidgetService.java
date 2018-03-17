package com.keyeswest.bake_v2.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;


import com.keyeswest.bake_v2.R;
import com.keyeswest.bake_v2.models.Ingredient;
import com.keyeswest.bake_v2.models.IngredientViewModel;
import com.keyeswest.bake_v2.models.Recipe;
import com.keyeswest.bake_v2.tasks.RecipeJsonDeserializer;
import com.keyeswest.bake_v2.utilities.RecipeFactory;



import java.util.ArrayList;


import java.util.List;

/**
 * Provides recipe and ingredient list data to the widget.
 *
 * Recipe data is read from the json file in onCreate so that the list sizes can be set.
 *
 * The ingredients list is read from shared preferences storage and uses the ingredient
 * checked state property to identify whether the user has checked the ingredient as being
 * available or not.
 */
public class RecipeWidgetService extends RemoteViewsService {

    private static final String TAG="RecipeWidgetService";

    private static final int INVALID_INDEX = -1;


    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.d(TAG, "onGetViewFactory invoked");
        return new RecipeRemoteViewsFactory(this.getApplicationContext(), intent);
    }


    class RecipeRemoteViewsFactory implements RemoteViewsFactory{

        private Context mContext;

        private  int mRecipeIndex = INVALID_INDEX;
        // indicates whether the request is for recipes or ingredients
        private boolean mIsRecipeRequest;
        private List<Recipe> mRecipes;
        private List<Ingredient> mIngredients;

        private RecipeFactory mRecipeFactory;

        private int mWidgetId;


        /*
         * The intents from the widget are encoded so that the service can return either a list
         * of recipes or a list of ingredients. The scheme specific part of the data URI is used to
         * encode the request using the format:
         *    <appWidgetId>: (<R> or <I>),<recipe index>
         *       - where the recipe index is only included if the request is for ingredients (I)
         * During coding it was noticed that the comma is only present when the request is for
         * ingredients and is used to simplify parsing.
         */
        RecipeRemoteViewsFactory(Context context, Intent intent){

            Log.d(TAG, "RecipeRemoteViewsFactory constructor invoked");
            mContext = context;

            parseIntent(intent);
            mRecipes = new ArrayList<>();
            mIngredients = new ArrayList();
            mRecipeFactory = new RecipeFactory(new RecipeFactory.ErrorCallback() {
                @Override
                public void downloadErrorOccurred() {
                    Log.e(TAG, "Error loading recipes in widget.");
                }
            });


        }




        @Override
        public void onCreate() {
            Log.d(TAG, "Entering onCreate in RecipeRemotesViewFactory");

            // initiate reading recipe data
            mRecipeFactory.readNetworkRecipes(mContext, new RecipeJsonDeserializer.RecipeResultsCallback() {
                @Override
                public void recipeResult(List<Recipe> recipeList) {
                    mRecipes = recipeList;
                    if (! mIsRecipeRequest){
                        // The request is for ingredients so set an ingredients list property
                        // to the corresponding list of requested ingredients
                        mIngredients = mRecipes.get(mRecipeIndex).getIngredients();
                    }

                    // need to update the widget since this is an async result
                    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(mContext);
                    int[] ids = new int[1];
                    ids[0] = mWidgetId;
                    appWidgetManager.notifyAppWidgetViewDataChanged(ids,R.id.item_list);

                }
            });

        }


        @Override
        public void onDataSetChanged() {
            Log.d(TAG, "onDataSetChanged");
        }

        @Override
        public void onDestroy() {
            Log.d(TAG,"onDestroy invoked " );

        }

        @Override
        public int getCount() {
            Log.d(TAG,"getCount invoked returning: " + Integer.toString(mRecipes.size()));
            if (mIsRecipeRequest) {
                return mRecipes.size();
            }else{
                return mIngredients.size();
            }
        }


        /**
         * Handles the remote views for lists of recipes and ingredients.
         * @param position - list position of the view
         * @return
         */
        @Override
        public RemoteViews getViewAt(int position) {

            Log.d(TAG, "getViewAt invoked for position" + Integer.toString(position));

            RemoteViews remoteView = new RemoteViews(mContext.getPackageName(),
                    R.layout.recipe_row_item);

            if (mIsRecipeRequest) {

                // The list is displaying recipes

                // set the recipe name in the view
                remoteView.setTextViewText(R.id.item, mRecipes.get(position).getName());

                // Next, we set a fill-intent which will be used to fill-in the pending intent
                // template which is set on the collection view to support clicking on
                // the recipe name.  Clicking on the recipe item will result in displaying the
                // corresponding recipe's ingredient list.
                Bundle extras = new Bundle();
                extras.putInt(BakeAppWidget.EXTRA_ITEM_POSITION, position);
                extras.putString(BakeAppWidget.EXTRA_ITEM_RECIPE_NAME,mRecipes.get(position).getName());
                Intent fillInIntent = new Intent();
                fillInIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mWidgetId);
                fillInIntent.putExtras(extras);
                remoteView.setOnClickFillInIntent(R.id.item, fillInIntent);

            }else{

                // The list is displaying ingredient items

                IngredientViewModel viewModel = new IngredientViewModel(mContext, mIngredients.get(position));

                // set the ingredient name
                remoteView.setTextViewText(R.id.item,viewModel.getIngredientInfo());


            }

            return remoteView;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position ;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }




        // Decode the intent scheme to determine if request is for recipe or ingredients
        private void parseIntent(Intent intent){

            String scheme_specific_part = intent.getData().getSchemeSpecificPart();

            int colonIndex = scheme_specific_part.indexOf(":");

            String widgetIdString = scheme_specific_part.substring(0,colonIndex );
            mWidgetId = Integer.parseInt(widgetIdString);

            int commaIndex = scheme_specific_part.indexOf(",");

            if (commaIndex == INVALID_INDEX){
                // we have a recipe request
                mIsRecipeRequest = true;
            }else{
                // we have an ingredient request
                mIsRecipeRequest = false;
                String recipeIndexString = scheme_specific_part.substring(commaIndex+1);
                mRecipeIndex = Integer.valueOf(recipeIndexString);

            }
        }


    }
}

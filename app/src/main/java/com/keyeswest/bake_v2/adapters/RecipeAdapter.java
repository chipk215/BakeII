package com.keyeswest.bake_v2.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.keyeswest.bake_v2.databinding.RecipeCardBinding;
import com.keyeswest.bake_v2.models.RecipeViewModel;
import com.keyeswest.bake_v2.models.Recipe;

import java.util.List;


public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeHolder> {

    public interface OnItemClickListener{
        void onItemClick(Recipe recipe);
    }

    private final OnItemClickListener mListener;

    private final List<Recipe> mRecipes;

    public RecipeAdapter(List<Recipe> recipes, OnItemClickListener listener){
        mRecipes = recipes;
        mListener = listener;
    }



    @Override
    public RecipeHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        RecipeCardBinding recipeBinding =
                RecipeCardBinding.inflate(inflater, parent, false);

        return new RecipeHolder(recipeBinding);

    }


    @Override
    public void onBindViewHolder(RecipeHolder holder, int position) {
        Recipe recipe = mRecipes.get(position);

        holder.bind(recipe, mListener);

    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }

     class RecipeHolder extends RecyclerView.ViewHolder{
        private final RecipeCardBinding binding;

        RecipeHolder(RecipeCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(final Recipe recipe, final OnItemClickListener listener){
            RecipeViewModel viewModel = new RecipeViewModel(this.itemView.getContext(),recipe);

            binding.setRecipe(viewModel);
            binding.executePendingBindings();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(recipe);
                }
            });

        }
    }
}

package com.keyeswest.bake_v2.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import android.view.ViewGroup;

import com.keyeswest.bake_v2.databinding.IngredientRowItemBinding;
import com.keyeswest.bake_v2.models.Ingredient;
import com.keyeswest.bake_v2.models.IngredientViewModel;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientHolder> {

    private final List<Ingredient> mIngredients;

    public IngredientsAdapter(List<Ingredient> ingredients){
        mIngredients = ingredients;

    }

    class IngredientHolder extends RecyclerView.ViewHolder{

        private IngredientViewModel mIngredientViewModel;
        private final IngredientRowItemBinding mBinding;

        IngredientHolder(IngredientRowItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind(final Ingredient ingredient){
            mIngredientViewModel = new
                    IngredientViewModel(this.itemView.getContext(),ingredient);

            mBinding.setIngredient(mIngredientViewModel);
            mBinding.executePendingBindings();


        }
    }

    @Override
    public IngredientsAdapter.IngredientHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        IngredientRowItemBinding ingredientBinding =
                IngredientRowItemBinding.inflate(inflater, parent, false);

        return new IngredientHolder(ingredientBinding);
    }

    @Override
    public void onBindViewHolder(IngredientsAdapter.IngredientHolder holder, int position) {
        Ingredient ingredient = mIngredients.get(position);
        holder.bind(ingredient);

    }

    @Override
    public int getItemCount() {
        return mIngredients.size();
    }


}

package com.keyeswest.bake_v2.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.keyeswest.bake_v2.databinding.StepRowItemBinding;
import com.keyeswest.bake_v2.models.Step;
import com.keyeswest.bake_v2.models.StepViewModel;

import java.util.List;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepHolder>{

    public interface OnStepClickListener{
        void onStepClick(Step step);

    }

    private final List<Step> mSteps;
    private final OnStepClickListener mListener;

    public StepAdapter( List<Step> steps,OnStepClickListener listener){
        mSteps = steps;
        mListener = listener;
    }

    @Override
    public StepHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        StepRowItemBinding stepBinding =
                StepRowItemBinding.inflate(inflater, parent, false);

        return new StepHolder(stepBinding);
    }

    @Override
    public void onBindViewHolder(StepHolder holder, int position) {
        Step step =mSteps.get(position);
        holder.bind(step);

    }

    @Override
    public int getItemCount() {
        return mSteps.size();
    }

    class StepHolder extends RecyclerView.ViewHolder{

        private StepViewModel mStepViewModel;
        private final StepRowItemBinding mStepBinding;

        public StepHolder(StepRowItemBinding binding) {
            super(binding.getRoot());
            mStepBinding = binding;
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onStepClick(mStepViewModel.getStep());
                }
            });


        }

        public void bind(final Step step){
            mStepViewModel = new StepViewModel(this.itemView.getContext(), step);
            mStepBinding.setStep(mStepViewModel);
            mStepBinding.executePendingBindings();
        }
    }
}

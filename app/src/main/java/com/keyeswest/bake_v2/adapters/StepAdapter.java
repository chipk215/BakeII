package com.keyeswest.bake_v2.adapters;


import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.keyeswest.bake_v2.R;
import com.keyeswest.bake_v2.databinding.StepRowItemBinding;
import com.keyeswest.bake_v2.models.Step;
import com.keyeswest.bake_v2.models.StepViewModel;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepHolder>{
    private final static String TAG="StepAdapter";

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
        @BindView(R.id.step_thumb_iv) ImageView mItemImageView;

        public StepHolder(StepRowItemBinding binding) {
            super(binding.getRoot());
            ButterKnife.bind(this,itemView);
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
            final String thumbnailURL = step.getThumbnailURL();
            if ((thumbnailURL != null) && (! thumbnailURL.isEmpty())){
                Picasso.with(itemView.getContext()).load(thumbnailURL).into(mItemImageView, new Callback() {
                    @Override
                    public void onSuccess() {/* Required but not needed */}

                    @Override
                    public void onError() {
                       Log.e(TAG, "Error downloading step thumbnail: " + thumbnailURL);
                        mItemImageView.setVisibility(View.GONE);
                    }
                });

            }else{
                mItemImageView.setVisibility(View.GONE);
            }
            mStepBinding.executePendingBindings();
        }
    }
}

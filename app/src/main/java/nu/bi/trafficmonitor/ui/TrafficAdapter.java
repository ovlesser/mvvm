package nu.bi.trafficmonitor.ui;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import nu.bi.trafficmonitor.databinding.TrafficItemBinding;
import nu.bi.trafficmonitor.model.Traffic;
import nu.bi.trafficmonitor.R;

import java.util.List;
import java.util.Objects;

public class TrafficAdapter extends RecyclerView.Adapter<TrafficAdapter.TrafficViewHolder> {

    List<? extends Traffic> mTrafficList;

    @Nullable
    private final TrafficClickCallback mTrafficClickCallback;

    public TrafficAdapter(@Nullable TrafficClickCallback clickCallback) {
        mTrafficClickCallback = clickCallback;
    }

    public void setTrafficList(final List<? extends Traffic> trafficList) {
        if (mTrafficList == null) {
            mTrafficList = trafficList;
            notifyItemRangeInserted(0, trafficList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mTrafficList.size();
                }

                @Override
                public int getNewListSize() {
                    return trafficList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mTrafficList.get(oldItemPosition).getId() ==
                            trafficList.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Traffic newTraffic = trafficList.get(newItemPosition);
                    Traffic oldTraffic = mTrafficList.get(oldItemPosition);
                    return newTraffic.getId() == oldTraffic.getId()
                            && Objects.equals(newTraffic.getUrl(), oldTraffic.getUrl())
                            && newTraffic.getUsage() == oldTraffic.getUsage();
                }
            });
            mTrafficList = trafficList;
            result.dispatchUpdatesTo(this);
        }
    }

    @Override
    public TrafficViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TrafficItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.traffic_item,
                        parent, false);
        binding.setCallback(mTrafficClickCallback);
        return new TrafficViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(TrafficViewHolder holder, int position) {
        holder.binding.setTraffic(mTrafficList.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mTrafficList == null ? 0 : mTrafficList.size();
    }

    static class TrafficViewHolder extends RecyclerView.ViewHolder {

        final TrafficItemBinding binding;

        public TrafficViewHolder(TrafficItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

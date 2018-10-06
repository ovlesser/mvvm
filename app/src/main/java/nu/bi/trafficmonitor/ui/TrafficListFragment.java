package nu.bi.trafficmonitor.ui;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nu.bi.trafficmonitor.R;
import nu.bi.trafficmonitor.databinding.ListFragmentBinding;
import nu.bi.trafficmonitor.db.entity.TrafficEntity;
import nu.bi.trafficmonitor.model.Traffic;
import nu.bi.trafficmonitor.viewmodel.TrafficListViewModel;

import java.util.List;

public class TrafficListFragment extends Fragment {
    public static final String TAG = "TrafficListViewModel";

    private TrafficAdapter mTrafficAdapter;
    private ListFragmentBinding mBinding;
    private TrafficListViewModel mViewModel;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.list_fragment, container, false);
        mTrafficAdapter = new TrafficAdapter(mTrafficClickCallback);
        mBinding.trafficList.setAdapter(mTrafficAdapter);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(TrafficListViewModel.class);
        subscribeUi(mViewModel);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void subscribeUi(TrafficListViewModel viewModel) {
        // Update the list when the data changes
        viewModel.getTraffic().observe(this, new Observer<List<TrafficEntity>>() {
            @Override
            public void onChanged(@Nullable List<TrafficEntity> myTraffic) {
                if (myTraffic != null) {
                    mBinding.setIsLoading(false);
                    mTrafficAdapter.setTrafficList(myTraffic);
                } else {
                    mBinding.setIsLoading(true);
                }
                // espresso does not know how to wait for data binding's loop so we execute changes
                // sync.
                mBinding.executePendingBindings();
                mBinding.trafficList.getLayoutManager().scrollToPosition(0);
            }
        });
    }

    private final TrafficClickCallback mTrafficClickCallback = new TrafficClickCallback() {
        @Override
        public void onClick(Traffic traffic) {

            if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                ((MainActivity) getActivity()).show(traffic);
            }
        }
    };
}

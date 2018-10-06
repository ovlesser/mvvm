package nu.bi.trafficmonitor.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import javax.inject.Inject;

import nu.bi.trafficmonitor.DataRepository;
import nu.bi.trafficmonitor.MainApplication;
import nu.bi.trafficmonitor.R;
import nu.bi.trafficmonitor.databinding.MainActivityBinding;
import nu.bi.trafficmonitor.model.Traffic;

public class MainActivity extends AppCompatActivity {

    @Inject
    DataRepository mRepository;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main_activity);

        ((MainApplication)getApplication()).getMainActivityViewModelComponent().inject(this);
        MainActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.main_activity);
        ButtonClickCallback callback = new ButtonClickCallback() {
            @Override
            public void onClick(View view) {
                mRepository.getTrafficFromWeb();
            }
        };
        binding.setCallback(callback);

        // Add traffic list fragment if this is first creation
        if (savedInstanceState == null) {
            TrafficListFragment fragment = new TrafficListFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment, TrafficListFragment.TAG).commit();
        }
    }

    /** Shows the traffic detail fragment */
    public void show(Traffic traffic) {
    }
}

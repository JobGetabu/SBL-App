package com.job.sbl.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.NavigationView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.job.sbl.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by Job on Monday : 9/3/2018.
 */
public class BottomNavigationDrawerFragment extends BottomSheetDialogFragment {

    public static final String TAG = "BtmNavFrag";
    private static final int REQUEST_INVITE = 34655;

    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    Unbinder unbinder;



    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_bottomsheet, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        navigationView.setNavigationItemSelectedListener(onNavigationItemSelectedListener);
    }

    NavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener =
            new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    int id = item.getItemId();
                    switch (id) {
                        case R.id.nav_home:
                            dismiss();
                            return true;
                        case R.id.nav_logout:
                            Toast.makeText(getContext(), "Signing you out", Toast.LENGTH_SHORT).show();
                            dismiss();
                            return true;

                        case R.id.nav_invitemember:

                            progressDialog = new ProgressDialog(getContext());
                            progressDialog.setCancelable(true);
                            progressDialog.show();
                            progressDialog.setMessage("Just a moment...");
                            sendToInviteScreen();
                            dismiss();
                            return true;

                        case R.id.nav_settings:
                        case R.id.nav_bealender:
                        case R.id.nav_paybill:
                            startActivity(new Intent(getContext(), FeatureUnavailableActivity.class));
                            dismiss();
                            return true;
                    }
                    return false;
                }
            };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void sendToInviteScreen(){

        Intent sendIntent = new Intent();
        String msg = "Hey, check this out: " + getResources().getString(R.string.share_url);
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, msg);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
        progressDialog.dismiss();
    }
}

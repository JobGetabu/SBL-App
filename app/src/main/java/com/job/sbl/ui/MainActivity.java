package com.job.sbl.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.bottomappbar.BottomAppBar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.diegodobelo.expandingview.ExpandingItem;
import com.diegodobelo.expandingview.ExpandingList;
import com.job.sbl.R;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    // Choose an arbitrary request code value
    private static final int RC_SIGN_IN = 123;
    private static final String TAG = "MainA";

    @BindView(R.id.main_bar)
    BottomAppBar bar;
    @BindView(R.id.main_fab)
    FloatingActionButton fab;
    @BindView(R.id.user_info_username)
    TextView userInfoUsername;
    @BindView(R.id.user_info_role)
    TextView userInfoRole;
    @BindView(R.id.user_info_group)
    TextView userInfoGroup;
    @BindView(R.id.user_info_image)
    CircleImageView userInfoImage;
    @BindView(R.id.user_info_img_notification)
    ImageButton userInfoImgNotification;
    @BindView(R.id.admin_d_balance)
    TextView adminDBalance;
    @BindView(R.id.admin_d_num_members)
    TextView adminDNumMembers;
    @BindView(R.id.admin_d_account_status)
    TextView adminDAccountStatus;
    @BindView(R.id.admin_d_groupnum_loans)
    TextView adminDGroupnumLoans;
    @BindView(R.id.admin_d_days_left)
    TextView adminDDaysLeft;


    private ProgressDialog progressDialog;
    private ExpandingList mExpandingList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(bar);

        //read db data
        mExpandingList = findViewById(R.id.expanding_list_main);
        createItems();
    }


    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.bottom_nav_drawer_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                BottomNavigationDrawerFragment bottomNavDrawerFragment = new BottomNavigationDrawerFragment();
                bottomNavDrawerFragment.show(getSupportFragmentManager(), BottomNavigationDrawerFragment.TAG);
                break;
        }

        return true;
    }

    private void createItems() {
        addItem("Payments", new String[]{"Loan Repayment", "Loan Repayment", "Loan Repayment", "Loan Repayment", "Loan Repayment", "Loan Repayment", "Loan Repayment"}, R.color.greenb3, R.drawable.profit_bg);
        addItem("Loans", new String[]{"Loan Request", "Loan Request", "Loan Request"}, R.color.greenb, R.drawable.loan_bg);
        addItem("Lenders", new String[]{"Mavuno Self Help Group", "Cooperative Chama", "Taifa Sacco"}, R.color.purpleb, R.drawable.proj_bg);

    }

    private void addItem(String title, String[] subItems, int colorRes, int iconRes) {
        //Let's create an item with R.layout.expanding_layout
        final ExpandingItem item = mExpandingList.createNewItem(R.layout.expanding_layout);

        //If item creation is successful, let's configure it
        if (item != null) {
            item.setIndicatorColorRes(colorRes);
            item.setIndicatorIconRes(iconRes);
            //It is possible to get any view inside the inflated layout. Let's set the text in the item
            ((TextView) item.findViewById(R.id.e_g_item)).setText(title);

            //We can create items in batch.
            item.createSubItems(subItems.length);
            for (int i = 0; i < item.getSubItemsCount(); i++) {
                //Let's get the created sub item by its index
                final View view = item.getSubItemView(i);

                //Let's set some values in
                configureSubItem(item, view, subItems[i]);
            }


           /*
            item.findViewById(R.id.add_more_sub_items).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showInsertDialog(new OnItemCreated() {
                        @Override
                        public void itemCreated(String title) {
                            View newSubItem = item.createSubItem();
                            configureSubItem(item, newSubItem, title);
                        }
                    });
                }
            });


            item.findViewById(R.id.remove_item).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mExpandingList.removeItem(item);
                }
            });
            */
        }
    }

    private void addItem(Boolean isLoanItem, String title, String[] subItems, int colorRes, int iconRes) {
        //Let's create an item with R.layout.expanding_layout
        final ExpandingItem item = mExpandingList.createNewItem(R.layout.expanding_layout);

        //If item creation is successful, let's configure it
        if (item != null) {
            item.setIndicatorColorRes(colorRes);
            item.setIndicatorIconRes(iconRes);
            //It is possible to get any view inside the inflated layout. Let's set the text in the item
            ((TextView) item.findViewById(R.id.e_g_item)).setText(title);

            //We can create items in batch.
            item.createSubItems(subItems.length);
            for (int i = 0; i < item.getSubItemsCount(); i++) {
                //Let's get the created sub item by its index
                final View view = item.getSubItemView(i);

                //Let's set some values in
                configureSubItem(true,item, view, subItems[i]);
            }
        }
    }

    private void configureSubItem(final ExpandingItem item, final View view, String subTitle) {
        Random random = new Random();
        int a = random.nextInt(9);
        int b = random.nextInt(10);
        int c = random.nextInt(10);
        int d = random.nextInt(10);

        ((TextView) view.findViewById(R.id.e_gs_details)).setText(subTitle);
        ((TextView) view.findViewById(R.id.e_gs_date)).setText(c + "/08/2018");

        String cash = "+ Ksh" + a + "," + b + c + d;

        ((TextView) view.findViewById(R.id.e_gs_money)).setText(cash);

       /* view.findViewById(R.id.remove_sub_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.removeSubItem(view);
            }
        });*/
    }

    private void configureSubItem(Boolean isLoanItem, final ExpandingItem item, final View view, String subTitle) {
        Random random = new Random();
        int a = random.nextInt(9);
        int b = random.nextInt(10);
        int c = random.nextInt(10);
        int d = random.nextInt(10);

        ((TextView) view.findViewById(R.id.e_gs_details)).setText(subTitle);
        ((TextView) view.findViewById(R.id.e_gs_date)).setText(c + "/08/2018");

        String cash = "+ Ksh" + a + "," + b + c + d;

        ((TextView) view.findViewById(R.id.e_gs_money)).setText(cash);

       /* view.findViewById(R.id.remove_sub_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.removeSubItem(view);
            }
        });*/
    }

    private void showInsertDialog(final OnItemCreated positive) {
        final EditText text = new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(text);
        builder.setTitle(R.string.enter_title);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                positive.itemCreated(text.getText().toString());
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.show();
    }

    @OnClick(R.id.main_fab)
    public void onFabViewClicked() {
    }

    interface OnItemCreated {
        void itemCreated(String title);
    }



    private void showSnackbar(int text) {
        Snackbar.make(findViewById(android.R.id.content), text, Snackbar.LENGTH_LONG).show();
    }

}

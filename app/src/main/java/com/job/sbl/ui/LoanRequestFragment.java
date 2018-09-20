package com.job.sbl.ui;


import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.job.sbl.R;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoanRequestFragment extends BottomSheetDialogFragment {

    @BindView(R.id.pay_phonenumber)
    TextView payPhonenumber;
    @BindView(R.id.pay_username)
    TextView payUsername;
    @BindView(R.id.pay_textamount)
    TextView payTextamount;
    @BindView(R.id.pay_amountinput)
    TextInputLayout payAmountinput;
    @BindView(R.id.pay_editImg)
    ImageButton editImagbtn;


    private View mRootView;



    public static final String TAG = "PayFragment";
    private static final int PHONE_NUMBER_REQUEST_CODE = 1114;

    private String userOnlineName = "";
    private String mResultPhoneNumber = "";



    //starter progress
    private SweetAlertDialog pDialog;

    public LoanRequestFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.addfunds_checkout, container, false);
        ButterKnife.bind(this, mRootView);

        return mRootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        textWatcher();


    }


    @OnClick(R.id.pay_amountinput)
    public void onPayAmountinputClicked() {
    }

    @OnClick(R.id.pay_paybtn)
    public void onPayPaybtnClicked() {

    }


    @OnClick({R.id.pay_editImg,R.id.pay_textamount})
    public void onPayEditIcon() {
        payAmountinput.setVisibility(View.VISIBLE);
        String am = payTextamount.getText().toString();
        String newstr = am.replaceAll("KES ", "")
                .replaceAll("/-", "")
                .replaceAll(",", "");
        payAmountinput.getEditText().setText(newstr);
        payTextamount.setVisibility(View.GONE);
        editImagbtn.setVisibility(View.GONE);
    }

    @OnClick({R.id.pay_phonenumber, R.id.pay_username, R.id.kngdpay,
            R.id.kjbsavk6pay, R.id.klsdnldvkj, R.id.main_sdbjldv,
            R.id.textView6pay})
    public void onHidePayInputField() {
        payAmountinput.setVisibility(View.GONE);
        payTextamount.setVisibility(View.VISIBLE);
        editImagbtn.setVisibility(View.VISIBLE);
        String am = payAmountinput.getEditText().getText().toString();
        //payTextamount.setText("KES " + am + "/-");
        double temp = 0;
        try {
            temp = Double.parseDouble(am);
        } catch (Exception e) {
            Log.e(TAG, "onHideInputField: ", e);
        }
        payTextamount.setText(formatMyMoney(temp) + "/-");
    }

    private void textWatcher() {

        payAmountinput.getEditText().addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (-1 != payAmountinput.getEditText().getText().toString().indexOf("\n")) {

                    payAmountinput.setVisibility(View.GONE);
                    payTextamount.setVisibility(View.VISIBLE);
                    editImagbtn.setVisibility(View.VISIBLE);
                    String am = payAmountinput.getEditText().getText().toString();

                    //payTextamount.setText("KES " + am + "/-");
                    double temp = 0;
                    try {
                        temp = Double.parseDouble(am);
                    } catch (Exception e) {
                        Log.e(TAG, "onHideInputField: ", e);
                    }
                    payTextamount.setText(formatMyMoney(temp) + "/-");
                }
            }
        });
    }

    private void showWaitDialogue() {

        pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#f9ab60"));
        pDialog.setTitleText("Simulating M-pesa Transaction ...");
        pDialog.setCancelable(true);
        pDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if (userOnlineName.isEmpty()) {
                    dismiss();
                }
            }
        });
        pDialog.show();
    }

    /*private void simulatingMpesaTransaction() {
        //init view-model
        DocumentReference UserRef = mFirestore.collection("Users")
                .document(mCurrentUser.getUid());

        UserRef
                .get()
                .addOnCompleteListener(getActivity(), new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {

                            UserBasicInfo userBasicInfo = task.getResult().toObject(UserBasicInfo.class);
                            //we need the user name
                            if (userBasicInfo != null) {
                                userOnlineName = userBasicInfo.getUsername();

                            }

                            if (pDialog != null) {

                                pDialog.changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
                                pDialog.setCancelable(false);
                                pDialog.setTitleText("Updating account ...");
                            }
                        } else {

                            Log.e(TAG, "Error getting documents: ", task.getException());
                            if (pDialog != null) {
                                pDialog.dismiss();
                            }
                        }
                    }
                });
    }*/

    private boolean validateOnPay() {

        boolean valid = true;

        String am = payAmountinput.getEditText().getText().toString();

        if (am.isEmpty() || am.equals("0")) {
            payAmountinput.setError("Amount is not valid");

            payTextamount.setVisibility(View.GONE);
            editImagbtn.setVisibility(View.GONE);
            payAmountinput.setVisibility(View.VISIBLE);

            valid = false;
        } else {
            payTextamount.setVisibility(View.VISIBLE);
            editImagbtn.setVisibility(View.VISIBLE);
            payAmountinput.setVisibility(View.GONE);
            payAmountinput.setError(null);
        }

        if (!am.isEmpty()) {
            if (Double.parseDouble(am) < 10) {
                payAmountinput.setError("Amount must be greater than 10");

                payTextamount.setVisibility(View.GONE);
                editImagbtn.setVisibility(View.GONE);
                payAmountinput.setVisibility(View.VISIBLE);

                valid = false;
            } else {
                payTextamount.setVisibility(View.VISIBLE);
                editImagbtn.setVisibility(View.VISIBLE);
                payAmountinput.setVisibility(View.GONE);
                payAmountinput.setError(null);
            }
        }

        return valid;
    }

    public String formatMyMoney(Double money) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        Log.d(TAG, "formatMyMoney: " + formatter.format(money));
        return String.format("KES %,.0f", money);
    }
}

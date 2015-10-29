package com.example.yee.radpadcodingchallenge;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import java.util.Calendar;

/**
 * Created by Yee on 10/25/15.
 */
public class AddressManagementActivity extends Activity implements RecycleViewAdapter.ClickListener {

    public static BackendServer server;
    InputMethodManager mInputMgr;
    AlertDialog.Builder mAlert;
    RecyclerView addressList;
    RecycleViewAdapter mAdapter;
    TextView mSaveButton;
    TextView mCancelButton;
    TextView mAddButton;
    TextView mTitle;
    int month, year;
    int mVisibilityCounter = 0;
    public static boolean isEditing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mVisibilityCounter = savedInstanceState.getInt("visibilityCounter");
        }
        setContentView(R.layout.address_management);
        mInputMgr = (InputMethodManager) this.getSystemService(Context
                .INPUT_METHOD_SERVICE);
        addressList = (RecyclerView) findViewById(R.id.viewList);
        mAdapter = new RecycleViewAdapter(BackendServer.mDataSet);
        addressList.setLayoutManager(new LinearLayoutManager(this));
        addressList.setHasFixedSize(true);
        mAddButton = (TextView) findViewById(R.id.add);
        mSaveButton = (TextView) findViewById(R.id.save);
        mCancelButton = (TextView) findViewById(R.id.cancel);
        mTitle = (TextView) findViewById(R.id.titleBarTitle);
//        Initial Sever by login a fake user name, this step is redundant but just to pretend a login step
        server = BackendServer.login("admin");
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                server.toServer(AddressData.DEFAULT);
                mAdapter.notifyDataSetChanged();
            }
        });
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isEditing = false;
                setEditorInterfaceVisibility(false);
                try {
                    mInputMgr.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (NullPointerException e) {

                }
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        addressList.setLayoutManager(layoutManager);
        addressList.setHasFixedSize(true);
        initializeSoftInputListener();
        initializeCalendar();
        initializeAdapter();
        notifyAddAddress();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("visibilityCounter", mVisibilityCounter);
//        outState.putParcelableArrayList("dataSet", (ArrayList<? extends Parcelable>) BackendServer.mDataSet);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        notifyAddAddress();
    }

    private void initializeAdapter() {
        addressList.setAdapter(mAdapter);
        mAdapter.setClickListener(this);
    }

    private void initializeCalendar() {
        final Calendar date = Calendar.getInstance();
        month = date.get(Calendar.MONTH);
        year = date.get(Calendar.YEAR);
    }

    private void initializeSoftInputListener() {
        //Listener of the soft input action by listening the layout change
        final View rootView = findViewById(R.id.addressManage);
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int heightDiff = rootView.getRootView().getHeight() - rootView.getHeight();
                if (heightDiff > 100) {
                    setEditorInterfaceVisibility(true);
                }
            }
        });
    }

    // Listen to any click on the item of recyclerView, not sure if will use later
    @Override
    public void itemClicked(View view, int position) {
    }

    private void setEditorInterfaceVisibility(boolean visible) {
        if (visible) mVisibilityCounter--;
        mCancelButton.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
        mSaveButton.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
        mTitle.setVisibility(visible ? View.INVISIBLE : View.VISIBLE);
        mAddButton.setVisibility(visible ? View.INVISIBLE : View.VISIBLE);
        if (!visible) mVisibilityCounter++;
    }

    private void notifyAddAddress() {
        if (BackendServer.mDataSet.isEmpty()) {
            mAlert = new AlertDialog.Builder(this);
            mAlert.setCancelable(false);
            mAlert.setMessage(R.string.tip);
            mAlert.setNegativeButton(R.string.confirm_alert, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            mAlert.show();
        }
    }
}

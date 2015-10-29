package com.example.yee.radpadcodingchallenge;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Yee on 10/26/15.
 */
public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.AddressViewHolder> {
    public View mViewDataPack;
    private AddressData address = AddressData.DEFAULT;
    private ClickListener clickListener;
    private AlertDialog.Builder mAlert;
    private View mView;
    CardView mAddressCard;
    TextView mSaveButton;
    TextView mDeleteButton;
    TextView mTitle;
    TextView mOptionButton;
    EditText mStreet;
    EditText mCity;
    EditText mState;
    EditText mRent;
    EditText mLandlord;
    EditText mFirstName;
    EditText mLastName;
    EditText mMoveIn;
    EditText mMoveOut;
    RelativeLayout mName;
    InputMethodManager mInputMgr;
    public String street;
    public String city;
    public String state;
    public String rent;
    public String landlord;
    public String moveIn;
    public String moveOut;
    public String company;
    public String firstName;
    public String lastName;

    public class AddressViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        AddressViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mView = itemView;
            mInputMgr = (InputMethodManager) mView.getContext().getSystemService(Context
                    .INPUT_METHOD_SERVICE);
            mAddressCard = (CardView) itemView.findViewById(R.id.addressCard);
            mStreet = (EditText) itemView.findViewById(R.id.street);
            mCity = (EditText) itemView.findViewById(R.id.city);
            mState = (EditText) itemView.findViewById(R.id.state);
            mRent = (EditText) itemView.findViewById(R.id.rent);
            mDeleteButton = (TextView) itemView.findViewById(R.id.delete);
            mSaveButton = (TextView) itemView.findViewById(R.id.save);
            mTitle = (TextView) itemView.findViewById(R.id.title);
            mOptionButton = (TextView) itemView.findViewById(R.id.option1);
            mLandlord = (EditText) itemView.findViewById(R.id.landlord);
            mName = (RelativeLayout) itemView.findViewById(R.id.name);
            mFirstName = (EditText) itemView.findViewById(R.id.firstName);
            mLastName = (EditText) itemView.findViewById(R.id.lastName);
            mMoveIn = (EditText) itemView.findViewById(R.id.move_in);
            mMoveOut = (EditText) itemView.findViewById(R.id.move_out);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.itemClicked(v, getAdapterPosition());
            }
        }
    }

    List<AddressData> addressList;

    RecycleViewAdapter(List<AddressData> address) {
        this.addressList = address;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public AddressViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.address_card, parent, false);
        return new AddressViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AddressViewHolder holder, final int position) {
        mStreet.setText(addressList.get(position).street);
        mCity.setText(addressList.get(position).city);
        mRent.setText(addressList.get(position).rent);
        mLandlord.setText(addressList.get(position).landlord);
        mFirstName.setText(addressList.get(position).firstName);
        mLastName.setText(addressList.get(position).lastName);
        mMoveIn.setText(addressList.get(position).moveIn);
        mMoveOut.setText(addressList.get(position).moveOut);
        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAlert(v.getContext());
                if (AddressManagementActivity.isEditing) {
                    mAlert.setMessage(R.string.warning_alert);
                    mAlert.setNegativeButton(R.string.confirm_alert, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                            setEditorInterfaceVisibility(true);
                            dialog.dismiss();
                        }
                    });
                } else {
                    mAlert.setMessage(R.string.message_alert);
                    mAlert.setNegativeButton(R.string.cancel_alert, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    mAlert.setPositiveButton(R.string.sure_alert, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            refreshDataSet(position);
                        }
                    });
                }
                mAlert.show();
            }
        });

        // The individual pay option user interface
        mOptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInputMgr.showSoftInput(v, InputMethodManager.SHOW_FORCED);
                if (mLandlord.getVisibility() == View.VISIBLE) {
                    mOptionButton.setText(R.string.option_2_address);
                    mName.setVisibility(View.VISIBLE);
                    mLandlord.setVisibility(View.GONE);
                    mFirstName.requestFocus();
                } else {
                    mLandlord.setVisibility(View.VISIBLE);
                    mOptionButton.setText(R.string.option_1_address);
                    mName.setVisibility(View.GONE);
                    mLandlord.setVisibility(View.VISIBLE);
                    mLandlord.requestFocus();
                }

            }
        });

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                street = mStreet.getText().toString();
                city = mCity.getText().toString();
                state = mState.getText().toString();
                rent = "$" + mRent.getText().toString();
                landlord = mLandlord.getText().toString();
                firstName = mFirstName.getText().toString();
                lastName = mLastName.getText().toString();
                moveIn = mMoveIn.getText().toString();
                moveOut = mMoveOut.getText().toString();
//                createAlert(mView.getContext());
//                if (firstName.isEmpty()) {
//                    mAlert.setMessage(R.string.warning_date);
//                    mAlert.setNegativeButton(R.string.confirm_alert, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    });
//                    mAlert.show();
//                } else {
                address = AddressData.saveAddress(street, city, state, rent, landlord, moveIn, moveOut, company,
                        firstName,
                        lastName);
                BackendServer.mDataSet.add(address);
                notifyDataSetChanged();
                Toast.makeText(mView.getContext(), R.string.saved, Toast.LENGTH_LONG).show();
            }
//            }
        });
    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }

    public interface ClickListener {
        void itemClicked(View view, int position);
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    private void refreshDataSet(int position) {
        addressList.remove(position);
        notifyDataSetChanged();
    }

    private void createAlert(Context context) {
        mAlert = new AlertDialog.Builder(context);
        mAlert.setCancelable(false);
    }

    //just check the format and the comparison between dates early or late will be finish later.
    private boolean checkDate(String str) {
        Pattern pattern = Pattern.compile("(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\\\d\\\\d)");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    private void setEditorInterfaceVisibility(boolean visible) {
        mDeleteButton.setVisibility(visible ? View.INVISIBLE : View.VISIBLE);
        mSaveButton.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
    }
}


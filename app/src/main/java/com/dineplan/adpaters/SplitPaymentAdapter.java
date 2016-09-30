package com.dineplan.adpaters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dineplan.R;
import com.dineplan.model.PaymentType;
import com.dineplan.utility.Constants;

import java.util.ArrayList;

/**
 * Created by sandeepjoshi on 24/09/16.
 */

public class SplitPaymentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {


    private ArrayList<PaymentType> paymentTypes;
    private Context context;
    private SharedPreferences sharedPreferences;
    private String amtType;
    public SplitPaymentAdapter(Context context, ArrayList<PaymentType>  paymentTypes){
        this.paymentTypes=paymentTypes;
        this.context=context;
        sharedPreferences=context.getSharedPreferences(Constants.PREF_NAME,Context.MODE_PRIVATE);
        amtType=sharedPreferences.getString(Constants.AMOUNT_TYPE,"$");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vi= LayoutInflater.from(context).inflate(R.layout.split_payment_item,parent,false);
        PaymentHolder paymentHolder=new PaymentHolder(vi);
        return paymentHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PaymentHolder paymentHolder=(PaymentHolder)holder;

        paymentHolder.et_amount.addTextChangedListener(new MyWatcher(paymentHolder.et_amount,position));
        paymentHolder.tv_heading.setText(paymentTypes.get(position).getName());
        if(paymentTypes.get(position).getId()==1){
                paymentHolder.tv_record.setText("Add Cash");
            paymentHolder.icon.setImageResource(R.drawable.cash_icon);
            paymentHolder.tv_record.setText("Cash to Tender");
        }else{
            paymentHolder.tv_record.setText("Record Card Payment");
            paymentHolder.icon.setImageResource(R.drawable.record_and_other_icon);
            paymentHolder.tv_record.setText("Amount Before Tip");
        }
        paymentHolder.ll_top.setTag(paymentTypes.get(position));
        paymentHolder.rl_amount.setTag(paymentTypes.get(position));
        paymentHolder.iv_close.setTag(paymentTypes.get(position));

        paymentHolder.ll_top.setOnClickListener(this);
        paymentHolder.rl_amount.setOnClickListener(this);
        paymentHolder.iv_close.setOnClickListener(this);

    }

    @Override
    public int getItemCount() {
        return paymentTypes.size();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_top:
                RelativeLayout amt=(RelativeLayout) ((ViewGroup)view.getParent()).findViewById(R.id.rl_amount);
                amt.setVisibility(View.VISIBLE);
                view.setVisibility(View.GONE);
                break;
            case R.id.iv_close:
                ((ViewGroup)view.getParent().getParent()).findViewById(R.id.rl_amount).setVisibility(View.GONE);
                ((ViewGroup)view.getParent().getParent()).findViewById(R.id.ll_top).setVisibility(View.VISIBLE);
                break;

        }

    }

    class PaymentHolder extends RecyclerView.ViewHolder{
       public TextView tv_heading,tv_record;
        public ImageView icon,iv_close;
        public EditText et_amount;
        public RelativeLayout rl_amount;
        public LinearLayout ll_top;
        public PaymentHolder(View itemView) {
            super(itemView);
            tv_heading=(TextView) itemView.findViewById(R.id.tv_heading);
            ll_top=(LinearLayout)itemView.findViewById(R.id.ll_top);
            tv_record=(TextView) itemView.findViewById(R.id.tv_record);
            icon=(ImageView)itemView.findViewById(R.id.iv_icon);
            iv_close=(ImageView)itemView.findViewById(R.id.iv_close);
            et_amount=(EditText)itemView.findViewById(R.id.et_amount);
            rl_amount=(RelativeLayout) itemView.findViewById(R.id.rl_amount);
        }
    }


    class MyWatcher implements TextWatcher{


        public int posit;
        public EditText editText;

        public MyWatcher(EditText editText,int posit){
            this.editText=editText;
            this.posit=posit;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            if(!charSequence.toString().matches("^\\$(\\d{1,3}(\\,\\d{3})*|(\\d+))(\\.\\d{2})?$"))
            {
                String userInput= ""+charSequence.toString().replaceAll("[^\\d]", "");
                StringBuilder cashAmountBuilder = new StringBuilder(userInput);

                while (cashAmountBuilder.length() > 3 && cashAmountBuilder.charAt(0) == '0') {
                    cashAmountBuilder.deleteCharAt(0);
                }
                while (cashAmountBuilder.length() < 3) {
                    cashAmountBuilder.insert(0, '0');
                }
                cashAmountBuilder.insert(cashAmountBuilder.length()-2, '.');
                cashAmountBuilder.insert(0, '$');

                editText.setText(cashAmountBuilder.toString());
                // keeps the cursor always to the right
                Selection.setSelection(editText.getText(), cashAmountBuilder.toString().length());

                paymentTypes.get(posit).setAmount(Float.parseFloat(cashAmountBuilder.toString().replace(sharedPreferences.getString(Constants.AMOUNT_TYPE,""),"")));


            }


        }
        @Override
        public void afterTextChanged(Editable editable) {

        }
    }
}

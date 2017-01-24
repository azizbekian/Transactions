package com.badoo.badootest.ui.detail;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.badoo.badootest.R;
import com.badoo.badootest.data.DataManager;
import com.badoo.badootest.data.RatesManager;
import com.badoo.badootest.data.model.ConvertedTransaction;
import com.badoo.badootest.injection.ConfigPersistent;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

@ConfigPersistent
public class ConvertedTransactionsAdapter extends RecyclerView.Adapter<ConvertedTransactionsAdapter.RatesViewHolder> {

    private DataManager dataManager;
    private List<ConvertedTransaction> convertedTransactions;
    private final String gbpSign;

    @Inject
    ConvertedTransactionsAdapter(DataManager dataManager) {
        this.dataManager = dataManager;
        gbpSign = dataManager.getCurrencySymbol(RatesManager.CURRENCY_GBP);
    }

    @Override
    public ConvertedTransactionsAdapter.RatesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        return new RatesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ConvertedTransactionsAdapter.RatesViewHolder holder, int position) {
        holder.bind(convertedTransactions.get(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return convertedTransactions.size();
    }

    void setConvertedTransactions(List<ConvertedTransaction> convertedTransactions) {
        this.convertedTransactions = convertedTransactions;
    }

    boolean isEmpty() {
        return null == convertedTransactions || convertedTransactions.isEmpty();
    }

    class RatesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.row_title)
        TextView title;
        @BindView(R.id.row_subtitle)
        TextView subTitle;

        RatesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(ConvertedTransaction convertedTransaction) {
            title.setText(dataManager.getCurrencySymbol(convertedTransaction.getCurrency())
                    + " " + convertedTransaction.getAmount());
            subTitle.setText(gbpSign + " " + convertedTransaction.getConvertedAmount());
        }
    }

}

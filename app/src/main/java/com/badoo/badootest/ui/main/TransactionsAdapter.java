package com.badoo.badootest.ui.main;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.badoo.badootest.R;
import com.badoo.badootest.data.model.TransactionWrapper;
import com.badoo.badootest.injection.AppContext;
import com.badoo.badootest.injection.ConfigPersistent;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@ConfigPersistent
public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.TransactionViewHolder> {

    interface OnTransactionClickListener {
        void onTransactionClicked(String sku);
    }

    private List<TransactionWrapper> transactions;
    private final String oneTransactionString, manyTransactionsString;
    @Nullable private OnTransactionClickListener onTransactionClickListener;

    @Inject
    TransactionsAdapter(@AppContext Context context) {
        oneTransactionString = context.getResources().getQuantityString(R.plurals.transaction, 1);
        manyTransactionsString = context.getResources().getQuantityString(R.plurals.transaction, 0);
    }

    @Override
    public TransactionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        return new TransactionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TransactionViewHolder holder, int position) {
        holder.bind(transactions.get(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    void setTransactions(List<TransactionWrapper> transactions) {
        this.transactions = transactions;
    }

    void setTransactionClickListener(OnTransactionClickListener onTransactionClickListener) {
        this.onTransactionClickListener = onTransactionClickListener;
    }

    boolean isEmpty() {
        return null == transactions || transactions.isEmpty();
    }

    class TransactionViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.row_title)
        TextView title;
        @BindView(R.id.row_subtitle)
        TextView subTitle;

        TransactionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(TransactionWrapper transaction) {
            title.setText(transaction.getSku());
            long transactionsCount = transaction.getTransactionsCount();
            boolean isOneTransaction = transactionsCount == 1;
            subTitle.setText(transactionsCount + " "
                    + (isOneTransaction ? oneTransactionString : manyTransactionsString));
        }

        @OnClick(R.id.row_parent)
        @SuppressWarnings("unused")
        void onItemClicked(View view) {
            if (null != onTransactionClickListener) {
                TransactionWrapper t = transactions.get(getAdapterPosition());
                onTransactionClickListener.onTransactionClicked(t.getSku());
            }
        }

    }

}

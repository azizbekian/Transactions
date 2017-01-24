package com.badoo.badootest.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.badoo.badootest.R;
import com.badoo.badootest.ui.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends BaseActivity implements DetailContract.View {

    public static void launch(Context context, String sku) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(DetailPresenter.KEY_SKU, sku);
        context.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.total_tv)
    TextView totalAmountTextView;
    @BindView(R.id.recycler_converted_transactions)
    RecyclerView recyclerView;

    @Inject DetailPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        activityComponent().injectActivityComponent(this);

        presenter.create(this, savedInstanceState, getIntent());
    }

    @Override
    protected void onStop() {
        super.onStop();
        // unsubscribe from ongoing subscriptions if activity is finishing
        presenter.stop(isFinishing());
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public void setupToolbar(String title) {
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setDisplayShowHomeEnabled(true);
        actionbar.setTitle(title);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setupTotalAmount(String totalAmount) {
        totalAmountTextView.setText(totalAmount);
    }

    @Override
    public void setupConvertedTransactionList(RecyclerView.Adapter adapter) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, layoutManager.getOrientation()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}

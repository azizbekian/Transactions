package com.badoo.badootest.data;

import android.content.Context;
import android.support.annotation.RawRes;

import com.badoo.badootest.R;
import com.badoo.badootest.data.model.Rate;
import com.badoo.badootest.data.model.Transaction;
import com.badoo.badootest.injection.AppContext;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class AssetsManager {

    private Context context;
    private Gson gson;

    @Inject
    AssetsManager(@AppContext Context context, Gson gson) {
        this.context = context;
        this.gson = gson;
    }

    Observable<Rate> getRates() {
        Type type = new TypeToken<ArrayList<Rate>>() {}.getType();
        List<Rate> rates;
        try {
            rates = gson.fromJson(readFromAssets(context, R.raw.rates), type);
        } catch (IOException e) {
            return Observable.error(e);
        }
        return Observable.fromIterable(rates);
    }

    Observable<Transaction> getTransactions() {
        Type type = new TypeToken<ArrayList<Transaction>>() {}.getType();
        List<Transaction> transactions;
        try {
            transactions = gson.fromJson(readFromAssets(context, R.raw.transactions), type);
        } catch (IOException e) {
            return Observable.error(e);
        }
        return Observable.fromIterable(transactions);
    }

    private String readFromAssets(Context context, @RawRes int fileId) throws IOException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(context.getResources().openRawResource(fileId)));

        StringBuilder sb = new StringBuilder();
        String line = reader.readLine();
        while (line != null) {
            sb.append(line);
            line = reader.readLine();
        }
        reader.close();
        return sb.toString();
    }
}

package com.badoo.badootest.data;

import android.support.annotation.CheckResult;

import com.badoo.badootest.data.model.Rate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class RatesManager {

    public static final String CURRENCY_GBP = "GBP";

    private Graph<Rate> ratesGraph;
    private Map<String, Float> cachedRates = new HashMap<>();
    private Map<String, Vertex<Rate>> vertexMap = new HashMap<>();
    private Map<String, Map<String, Float>> selfRate = new HashMap<>();

    @Inject
    RatesManager() {}

    void setupRatesMap(List<Rate> rates) {

        ratesGraph = new Graph<>();

        Observable.fromIterable(rates)
                .groupBy(rate -> rate.from)
                .flatMap(stringRateGroupedObservable -> Observable.just(stringRateGroupedObservable.getKey()))
                .map(s -> {
                    Vertex<Rate> v = new Vertex<>(s);
                    ratesGraph.addVertex(v);
                    vertexMap.put(s, v);
                    return s;
                })
                .subscribe();

        for (Rate rate : rates) {
            ratesGraph.addEdge(vertexMap.get(rate.from), vertexMap.get(rate.to), rate.rate);
            Map<String, Float> tempMap = selfRate.get(rate.from);
            if (null == tempMap) {
                tempMap = new HashMap<>();
                tempMap.put(rate.to, rate.rate);
                selfRate.put(rate.from, tempMap);
            } else {
                tempMap.put(rate.to, rate.rate);
            }
        }
    }

    /**
     * Converts from currency {@code from} to {@link RatesManager#CURRENCY_GBP}.
     *
     * @param from The source currency.
     * @return Return conversion rate from currency {@code from} to currency {@link RatesManager#CURRENCY_GBP}.
     * <p>
     * Returns {@code 1} if {@code from} is {@link RatesManager#CURRENCY_GBP}
     * <p>
     * Returns {@code 0} if there is no conversion rate.
     */
    @CheckResult
    float convertToGbpFrom(String from) {
        if (from.equals(CURRENCY_GBP)) return 1.0f;

        if (cachedRates.containsKey(from)) return cachedRates.get(from);
        else {
            // this is the first time we're trying to convert from this currency
            Vertex<Rate> vertex = vertexMap.get(from);
            if (null == vertex) {
                // we do not have any conversion way for that type of currency
                return 0.0f;
            }

            // DFS the graph
            final List<Vertex<Rate>> vertices = new ArrayList<>();
            ratesGraph.clearMark();
            ratesGraph.depthFirstSearch(vertex, new Visitor<Rate>() {
                @Override
                public void visit(Graph<Rate> g, Vertex<Rate> v) {
                    vertices.add(v);
                }
            });

            if (vertices.isEmpty()) {
                // there is no conversion method
                cachedRates.put(from, 0.0f);
                return 0;
            } else if (vertices.size() == 1) {
                // there is a direct conversion
                float rate = selfRate.get(from).get(CURRENCY_GBP);
                cachedRates.put(from, rate);
                return rate;
            }

            float rate = 1;
            boolean found = false;
            Vertex<Rate> currentVertex, nextVertex;
            for (int i = 0, sum = vertices.size(); i < sum - 1; ++i) {
                currentVertex = vertices.get(i);
                nextVertex = vertices.get(i + 1);
                rate *= currentVertex.cost(nextVertex);
                if (nextVertex.getName().equals(CURRENCY_GBP)) {
                    found = true;
                    break;
                }
            }

            // if we have not seen GBP during traversing - we cannot convert it
            rate = found ? rate : 0.0f;
            cachedRates.put(from, rate);
            return rate;
        }
    }

    @CheckResult
    float convertToRate(float amount, float rate) {
        return amount * rate;
    }

}

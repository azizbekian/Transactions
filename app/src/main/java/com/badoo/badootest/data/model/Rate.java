package com.badoo.badootest.data.model;

public class Rate {

    public String from;
    public float rate;
    public String to;

    public Rate() {}

    private Rate(String from, String to, float rate) {
        this.from = from;
        this.to = to;
        this.rate = rate;
    }

    public static Rate newInstance(String from, String to, float rate) {
        return new Rate(from, to, rate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rate rate1 = (Rate) o;

        if (Float.compare(rate1.rate, rate) != 0) return false;
        if (from != null ? !from.equals(rate1.from) : rate1.from != null) return false;
        return to != null ? to.equals(rate1.to) : rate1.to == null;

    }

    @Override
    public int hashCode() {
        int result = from != null ? from.hashCode() : 0;
        result = 31 * result + (rate != +0.0f ? Float.floatToIntBits(rate) : 0);
        result = 31 * result + (to != null ? to.hashCode() : 0);
        return result;
    }
}

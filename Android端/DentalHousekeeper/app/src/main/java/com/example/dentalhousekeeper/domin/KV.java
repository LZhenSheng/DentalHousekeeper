package com.example.dentalhousekeeper.domin;

import lombok.Data;

@Data
public class KV {

    String k;
    String v;

    public KV(String k, String v) {
        this.k = k;
        this.v = v;
    }

    @Override
    public String toString() {
        if (v == null || v.length() <= 0) {
            return k + ":";
        } else {
            return k + ":" + v;
        }
    }
}

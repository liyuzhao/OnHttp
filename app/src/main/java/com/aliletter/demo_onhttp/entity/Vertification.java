package com.aliletter.demo_onhttp.entity;

/**
 * Author: mr-absurd
 * Github: http://github.com/mr-absurd
 * Data: 2017/8/31.
 */

public class Vertification {
    private int id;
    private String modulus;
    private String exponent;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModulus() {
        return modulus;
    }

    public void setModulus(String modulus) {
        this.modulus = modulus;
    }

    public String getExponent() {
        return exponent;
    }

    public void setExponent(String exponent) {
        this.exponent = exponent;
    }

    @Override
    public String toString() {
        return "Vertification{" +
                "id=" + id +
                ", modulus='" + modulus + '\'' +
                ", exponent='" + exponent + '\'' +
                '}';
    }
}

package com.bcmp.service;

import com.bcmp.exceptions.NaoPodeDivirPorZeroException;

public class Calculadora {


    public int somar(int a, int b) {
        return (a + b);
    }

    public int subtrair(int a, int b) {
        return (a - b);
    }

    public int dividir(int a, int b) throws NaoPodeDivirPorZeroException {
        if (b == 0) {
            throw new NaoPodeDivirPorZeroException();
        }
        return (a / b) ;
    }
}

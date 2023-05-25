package com.bcmp.service;

import com.bcmp.exceptions.NaoPodeDivirPorZeroException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;

public class CalculadoraTest {

    private Calculadora calculadora = new Calculadora();

    @Before
    public void setup(){
        calculadora = new Calculadora();
    }

    @Test
    public void deveSomarDoisValores() {
        //cenario
        int a = 5;
        int b = 3;

        //acao
        int resultado = calculadora.somar(a, b);

        //verificacao
        Assert.assertEquals(8, resultado);
    }

    @Test
    public void deveSubtrairDoisValores() {
        //cenario
        int a = 8;
        int b = 5;

        //acao
        int resultado = calculadora.subtrair(a, b);

        //verificacao
        Assert.assertEquals(3, resultado);
    }

    @Test
    public void deveDividirDoisValores() throws NaoPodeDivirPorZeroException{
        //cenario
        int a = 6;
        int b = 3;

        //acao
        int resultado = calculadora.dividir(a, b);

        //verificacao
        Assert.assertEquals(2, resultado);
    }

    @Test(expected = NaoPodeDivirPorZeroException.class)
    public void deveLancarExcecaoAoDividirPorZero() throws NaoPodeDivirPorZeroException {
        int a = 10;
        int b = 0;

        calculadora.dividir(a, b);
    }
}

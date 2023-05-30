package com.bcmp.suites;

import com.bcmp.service.CalculadoraTest;
import com.bcmp.service.LocacaoServiceTest;
import com.bcmp.service.ValorLocacaoTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CalculadoraTest.class,
        ValorLocacaoTest.class,
        LocacaoServiceTest.class
})
public class SuiteExecucao {
    //Remova se puder
}

package com.bcmp.service;

import com.bcmp.entity.Filme;
import com.bcmp.entity.Locacao;
import com.bcmp.entity.Usuario;
import com.bcmp.exceptions.FilmeSemEstoqueException;
import com.bcmp.utils.DataUtils;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import java.util.Date;

import static com.bcmp.utils.DataUtils.obterDataComDiferencaDias;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

public class LocacaoServiceTest {

    @Rule
    public ErrorCollector error = new ErrorCollector();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testeLocacao() throws Exception {

        //cenario
        LocacaoService service = new LocacaoService();
        Usuario usuario = new Usuario("Usuario 01");
        Filme filme = new Filme("Filme 01", 2, 5.0);

        //acao
        Locacao locacao = service.alugarFilme(usuario, filme);

        //verificacao
        Assert.assertEquals(5.0, locacao.getValor(), 0.01);
        Assert.assertThat(locacao.getValor(), CoreMatchers.is(5.0));
        error.checkThat(locacao.getValor(), CoreMatchers.is(5.0));

        Assert.assertThat(locacao.getValor(), CoreMatchers.is(not(6.0)));

        Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()));
        Assert.assertThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), CoreMatchers.is(true));
        error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), CoreMatchers.is(true));

        Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)));
        Assert.assertThat(DataUtils.isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), CoreMatchers.is(true));
        error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), CoreMatchers.is(true));
    }

    @Test(expected = FilmeSemEstoqueException.class)
    public void testLocacao_filmeSemEstoque() throws Exception {

        //cenario
        LocacaoService service = new LocacaoService();
        Usuario usuario = new Usuario("Usuario 01");
        Filme filme = new Filme("Filme 01", 0, 5.0);

        //acao
       service.alugarFilme(usuario, filme);
    }
}
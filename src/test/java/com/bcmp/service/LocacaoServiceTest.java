package com.bcmp.service;

import com.bcmp.entity.Filme;
import com.bcmp.entity.Locacao;
import com.bcmp.entity.Usuario;
import com.bcmp.utils.DataUtils;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

import static com.bcmp.utils.DataUtils.obterDataComDiferencaDias;
import static org.hamcrest.CoreMatchers.not;

public class LocacaoServiceTest {

    @Test
    public void testeLocacao() {

        //cenario
        LocacaoService service = new LocacaoService();
        Usuario usuario = new Usuario("Usuario 01");
        Filme filme = new Filme("Filme 01", 2, 5.0 );

        //acao
        Locacao locacao = service.alugarFilme(usuario, filme);

        //verificacao
        Assert.assertEquals(5.0, locacao.getValor(), 0.01);
        Assert.assertThat(locacao.getValor(), CoreMatchers.is(5.0));
        Assert.assertThat(locacao.getValor(), CoreMatchers.is(not(6.0)));
        Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()));
        Assert.assertThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), CoreMatchers.is(true));
        Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)));
        Assert.assertThat(DataUtils.isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), CoreMatchers.is(true));
    }
}

package com.bcmp.service;

import com.bcmp.entity.Filme;
import com.bcmp.entity.Locacao;
import com.bcmp.entity.Usuario;
import com.bcmp.utils.DataUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

import static com.bcmp.utils.DataUtils.obterDataComDiferencaDias;

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
        Assert.assertTrue(locacao.getValor() == 5.0);
        Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()));
        Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)));
    }
}

package com.bcmp.service;

import com.bcmp.entity.Filme;
import com.bcmp.entity.Locacao;
import com.bcmp.entity.Usuario;
import com.bcmp.exceptions.FilmeSemEstoqueException;
import com.bcmp.exceptions.LocadoraException;
import com.bcmp.utils.DataUtils;
import org.hamcrest.CoreMatchers;
import org.junit.*;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import java.util.Date;

import static com.bcmp.utils.DataUtils.obterDataComDiferencaDias;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

public class LocacaoServiceTest {

    private LocacaoService service;

    @Rule
    public ErrorCollector error = new ErrorCollector();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setup() {
        service = new LocacaoService();
    }


    @Test
    public void testLocacao() throws Exception {

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

    //forma elegante
    @Test(expected = FilmeSemEstoqueException.class)
    public void testLocacao_filmeSemEstoque() throws Exception {

        //cenario
        Usuario usuario = new Usuario("Usuario 01");
        Filme filme = new Filme("Filme 01", 0, 5.0);

        //acao
       service.alugarFilme(usuario, filme);
    }

    //forma robusta: apenas essa forma consegue imprimir algo após algo ser processado
    //ela segura a excecao e verifica tanto a excecao quanto a forma enviada
    //após ela posso fazer mais tratativas
    @Test
    public void testLocacao_usuarioVazio() throws FilmeSemEstoqueException {

        //cenario
        Filme filme = new Filme("Filme 2", 1, 4.0);

        //acao
        try {
            service.alugarFilme(null, filme);
            Assert.fail();
        } catch (LocadoraException e) {
            Assert.assertThat(e.getMessage(), CoreMatchers.is("Usuario Vazio"));
        }

        //System.out.println("Forma Robusta");
    }

    //forma nova: lanca para o junit fazer os tratamentos, porem ela avisa o junit o que ela esta esperando
    //dessa maneira o junit nao coloca mensagem nenhuma
    //a partir das excecoes ele consegue validar e colocar que os testes foram com sucesso
    @Test
    public void testLocacao_filmeVazio() throws FilmeSemEstoqueException, LocadoraException{

        //cenario
        Usuario usuario = new Usuario("Usuario 01");

        exception.expect(LocadoraException.class);
        exception.expectMessage("Filme Vazio");

        //acao
        service.alugarFilme(usuario, null);

        //System.out.println("Forma Nova");
    }
}
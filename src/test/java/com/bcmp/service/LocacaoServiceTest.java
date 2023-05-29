package com.bcmp.service;

import com.bcmp.entity.Filme;
import com.bcmp.entity.Locacao;
import com.bcmp.entity.Usuario;
import com.bcmp.exceptions.FilmeSemEstoqueException;
import com.bcmp.exceptions.LocadoraException;
import com.bcmp.utils.DataUtils;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.junit.*;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.bcmp.utils.DataUtils.obterDataComDiferencaDias;


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
    public void deveAlugarFilmeComSucesso() throws Exception {
        Assume.assumeFalse(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));

        //cenario
        Usuario usuario = new Usuario("Usuario 01");
        List<Filme> filmes = Arrays.asList(new Filme("Filme 01", 2, 5.0));

        //acao
        Locacao locacao = service.alugarFilme(usuario, filmes);

        //verificacao
        Assert.assertEquals(5.0, locacao.getValor(), 0.01);
        Assert.assertThat(locacao.getValor(), CoreMatchers.is(5.0));
        error.checkThat(locacao.getValor(), CoreMatchers.is(5.0));

        Assert.assertThat(locacao.getValor(), CoreMatchers.is(CoreMatchers.not(6.0)));

        Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()));
        Assert.assertThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), CoreMatchers.is(true));
        error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), CoreMatchers.is(true));

        Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)));
        Assert.assertThat(DataUtils.isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), CoreMatchers.is(true));
        error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), CoreMatchers.is(true));
    }

    //forma elegante
    @Test(expected = FilmeSemEstoqueException.class)
    public void deveLancarExcecaoAoAlugarFilmeSemEstoque() throws Exception {

        //cenario
        Usuario usuario = new Usuario("Usuario 01");
        List<Filme> filmes = Arrays.asList(new Filme("Filme 01", 0, 5.0));

        //acao
       service.alugarFilme(usuario, filmes);
    }

    //forma robusta: apenas essa forma consegue imprimir algo após algo ser processado
    //ela segura a excecao e verifica tanto a excecao quanto a forma enviada
    //após ela posso fazer mais tratativas
    @Test
    public void naoDeveAlugarFilmeSemUsuario() throws FilmeSemEstoqueException {

        //cenario
        List<Filme> filmes = Arrays.asList(new Filme("Filme 01", 2, 5.0));

        //acao
        try {
            service.alugarFilme(null, filmes);
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
    public void naoDeveAlugarFilmeSemFilme() throws FilmeSemEstoqueException, LocadoraException{

        //cenario
        Usuario usuario = new Usuario("Usuario 01");

        exception.expect(LocadoraException.class);
        exception.expectMessage("Filme Vazio");

        //acao
        service.alugarFilme(usuario, null);

        //System.out.println("Forma Nova");
    }

    @Test
    public void devePagar75pctNoFilme3() throws FilmeSemEstoqueException, LocadoraException {
        //cenario
        Usuario usuario = new Usuario();
        List<Filme> filmes = Arrays.asList(
                new Filme("Filme 1", 2, 4.00),
                new Filme("Filme 2", 2, 4.00),
                new Filme("Filme 3", 2, 4.00));

        //acao
        Locacao resultado = service.alugarFilme(usuario, filmes);

        //verificacao
        //4+4+3+2=13
        Assert.assertThat(resultado.getValor(), CoreMatchers.is(11.00));
    }

    @Test
    public void devePagar50pctNoFilme4() throws FilmeSemEstoqueException, LocadoraException {
        //cenario
        Usuario usuario = new Usuario();
        List<Filme> filmes = Arrays.asList(
                new Filme("Filme 1", 2, 4.00),
                new Filme("Filme 2", 2, 4.00),
                new Filme("Filme 3", 2, 4.00),
                new Filme("Filme 4", 2, 4.00));

        //acao
        Locacao resultado = service.alugarFilme(usuario, filmes);

        //verificacao
        //4+4+3=11
        Assert.assertThat(resultado.getValor(), CoreMatchers.is(13.00));
    }

    @Test
    public void devePagar25pctNoFilme5() throws FilmeSemEstoqueException, LocadoraException {
        //cenario
        Usuario usuario = new Usuario();
        List<Filme> filmes = Arrays.asList(
                new Filme("Filme 1", 2, 4.00),
                new Filme("Filme 2", 2, 4.00),
                new Filme("Filme 3", 2, 4.00),
                new Filme("Filme 4", 2, 4.00),
                new Filme("Filme 5", 2, 4.00));

        //acao
        Locacao resultado = service.alugarFilme(usuario, filmes);

        //verificacao
        //4+4+3+2+1=14
        Assert.assertThat(resultado.getValor(), CoreMatchers.is(14.00));
    }

    @Test
    public void devePagar0pctNoFilme6() throws FilmeSemEstoqueException, LocadoraException {
        //cenario
        Usuario usuario = new Usuario();
        List<Filme> filmes = Arrays.asList(
                new Filme("Filme 1", 2, 4.00),
                new Filme("Filme 2", 2, 4.00),
                new Filme("Filme 3", 2, 4.00),
                new Filme("Filme 4", 2, 4.00),
                new Filme("Filme 5", 2, 4.00));

        //acao
        Locacao resultado = service.alugarFilme(usuario, filmes);

        //verificacao
        //4+4+3+2+1+0=14
        Assert.assertThat(resultado.getValor(), CoreMatchers.is(14.00));
    }

    @Test
    public void deveDevolverNaSegundaAoAlugarNoSabado() throws FilmeSemEstoqueException, LocadoraException {
        Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));

        //cenario
        Usuario usuario = new Usuario("Usuario 1");
        List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 5.00));

        //acao
        Locacao retorno = service.alugarFilme(usuario, filmes);

        //verificacao
        boolean ehSegunda = DataUtils.verificarDiaSemana(retorno.getDataRetorno(), Calendar.MONDAY);
        Assert.assertTrue(ehSegunda);
    }
}
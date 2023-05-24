package com.bcmp.servicos;

import static com.bcmp.utils.DataUtils.adicionarDias;
import static com.bcmp.utils.DataUtils.obterDataComDiferencaDias;

import java.util.Date;

import com.bcmp.entidades.Filme;
import com.bcmp.entidades.Locacao;
import com.bcmp.entidades.Usuario;
import com.bcmp.utils.DataUtils;
import org.junit.Test;

public class LocacaoService {
	
	public Locacao alugarFilme(Usuario usuario, Filme filme) {
		Locacao locacao = new Locacao();
		locacao.setFilme(filme);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		locacao.setValor(filme.getPrecoLocacao());

		//Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		locacao.setDataRetorno(dataEntrega);
		
		//Salvando a locacao...	
		//TODO adicionar método para salvar
		
		return locacao;
	}

	@Test
	public void teste() {

		//cenario
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 01");
		Filme filme = new Filme("Filme 01", 2, 5.0 );

		//acao
		Locacao locacao = service.alugarFilme(usuario, filme);

		//verificacao
		System.out.println(locacao.getValor() == 5.0);
		System.out.println(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()));
		System.out.println(DataUtils.isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)));
	}
}
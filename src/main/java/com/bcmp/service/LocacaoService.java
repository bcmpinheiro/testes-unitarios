package com.bcmp.service;

import static com.bcmp.utils.DataUtils.adicionarDias;

import java.util.Date;

import com.bcmp.entity.Filme;
import com.bcmp.entity.Locacao;
import com.bcmp.entity.Usuario;
import com.bcmp.exceptions.FilmeSemEstoqueException;

public class LocacaoService {

	public Locacao alugarFilme(Usuario usuario, Filme filme) throws Exception {
		if(filme.getEstoque() == 0)
			throw new FilmeSemEstoqueException();

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
}
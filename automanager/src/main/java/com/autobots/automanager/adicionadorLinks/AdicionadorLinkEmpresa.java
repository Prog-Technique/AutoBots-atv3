package com.autobots.automanager.adicionadorLinks;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.EmpresaControle;
import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.modelo.AdicionadorLink;

@Component
public class AdicionadorLinkEmpresa implements AdicionadorLink<Empresa>{
	@Override
	public void adicionarLink(List<Empresa> lista) {
		for (Empresa objeto : lista) {
			long id = objeto.getId();
			Link linkProprioEmpresa = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(EmpresaControle.class)
							.obterEmpresa(id))
					.withSelfRel();
			Link linkProprioExcluir = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(EmpresaControle.class)
							.excluirEmpresa(id))
					.withSelfRel();
			Link linkProprioAtualizar = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(EmpresaControle.class)
							.atualizarEmpresa(objeto))
					.withSelfRel();
			Link linkProprioCadastrar = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(EmpresaControle.class)
							.cadastrarEmpresa(objeto))
					.withSelfRel();
			Link linkProprioEmpresas = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(EmpresaControle.class)
							.obterEmpresas())
					.withSelfRel();
			objeto.add(linkProprioEmpresa);
			objeto.add(linkProprioExcluir);
			objeto.add(linkProprioAtualizar);
			objeto.add(linkProprioCadastrar);
			objeto.add(linkProprioEmpresas);
		}
	}

	@Override
	public void adicionarLink(Empresa objeto) {
		Link linkProprioEmpresa = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(EmpresaControle.class)
						.obterEmpresa(objeto.getId()))
				.withRel("empresa");
		Link linkProprioEmpresas = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(EmpresaControle.class)
						.obterEmpresas())
				.withRel("empresas");
		Link linkProprioCadastrar = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(EmpresaControle.class)
						.cadastrarEmpresa(objeto))
				.withRel("cadastrar");
		Link linkProprioAtualizar = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(EmpresaControle.class)
						.atualizarEmpresa(objeto))
				.withRel("atualizar");
		Link linkProprioExcluir = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(EmpresaControle.class)
						.excluirEmpresa(objeto.getId()))
				.withRel("excluir");
		objeto.add(linkProprioEmpresa);
		objeto.add(linkProprioEmpresas);
		objeto.add(linkProprioCadastrar);
		objeto.add(linkProprioAtualizar);
		objeto.add(linkProprioExcluir);
	}
}

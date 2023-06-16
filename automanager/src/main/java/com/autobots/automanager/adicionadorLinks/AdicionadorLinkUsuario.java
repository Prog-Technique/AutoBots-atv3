package com.autobots.automanager.adicionadorLinks;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.UsuarioControle;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.modelo.AdicionadorLink;

@Component
public class AdicionadorLinkUsuario implements AdicionadorLink<Usuario> {

	@Override
	public void adicionarLink(List<Usuario> lista) {
		for (Usuario usuario : lista) {
			long id = usuario.getId();
			Link linkProprioUsuario = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(UsuarioControle.class)
							.obterUsuario(id))
					.withSelfRel();
			Link linkProprioExcluir = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(UsuarioControle.class)
							.excluirUsuario(usuario, 1))
					.withSelfRel();
			Link linkProprioAtualizar = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(UsuarioControle.class)
							.atualizarUsuario(usuario))
					.withSelfRel();
			Link linkProprioCadastrar = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(UsuarioControle.class)
							.cadastrarUsuario(usuario, 1))
					.withSelfRel();
			Link linkProprioUsuarios = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(UsuarioControle.class)
							.obterUsuarios())
					.withSelfRel();
			usuario.add(linkProprioUsuario);
			usuario.add(linkProprioExcluir);
			usuario.add(linkProprioAtualizar);
			usuario.add(linkProprioCadastrar);
			usuario.add(linkProprioUsuarios);
		}
	}

	@Override
	public void adicionarLink(Usuario objeto) {
		Link linkProprioUsuario = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(UsuarioControle.class)
						.obterUsuario(objeto.getId()))
				.withRel("usuario");
		Link linkProprioUsuarios = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(UsuarioControle.class)
						.obterUsuarios())
				.withRel("usuarios");
		Link linkProprioCadastrar = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(UsuarioControle.class)
						.cadastrarUsuario(objeto, 1))
				.withRel("cadastrar");
		Link linkProprioAtualizar = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(UsuarioControle.class)
						.atualizarUsuario(objeto))
				.withRel("atualizar");
		Link linkProprioExcluir = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(UsuarioControle.class)
						.excluirUsuario(objeto, 1))
				.withRel("excluir");
		objeto.add(linkProprioUsuario);
		objeto.add(linkProprioUsuarios);
		objeto.add(linkProprioCadastrar);
		objeto.add(linkProprioAtualizar);
		objeto.add(linkProprioExcluir);
	}
}

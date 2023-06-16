package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.adicionadorLinks.AdicionadorLinkEndereco;
import com.autobots.automanager.atualizadores.EnderecoAtualizador;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.modelo.Selecionador;
import com.autobots.automanager.repositorios.EnderecoRepositorio;
import com.autobots.automanager.repositorios.UsuarioRepositorio;

@RestController
@RequestMapping("/endereco")
public class EnderecoControle {
	@Autowired
	private EnderecoRepositorio repositorio;
	@Autowired
	private UsuarioRepositorio UsuarioRepositorio;
	@Autowired
	private AdicionadorLinkEndereco adicionadorLink;

	@GetMapping("/{id}")
	public ResponseEntity<Endereco> obterEndereco(@PathVariable long id) {
		List<Endereco> enderecos = repositorio.findAll();
		Endereco endereco = Selecionador.enderecoSelecionador(enderecos, id);
		if (endereco == null) {
			ResponseEntity<Endereco> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND) ;
			return resposta;
		} else {
			adicionadorLink.adicionarLink(endereco);
			ResponseEntity<Endereco> resposta = new ResponseEntity<Endereco>(endereco, HttpStatus.FOUND) ;
			return resposta;
		}
	}

	@GetMapping("/visualizar")
	public ResponseEntity<List<Endereco>> obterEnderecos() {
		List<Endereco> enderecos = repositorio.findAll();
		if (enderecos.isEmpty()) {
			ResponseEntity<List<Endereco>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND) ;
			return resposta;
		} else {
			adicionadorLink.adicionarLink(enderecos);
			ResponseEntity<List<Endereco>> resposta = new ResponseEntity<>(enderecos, HttpStatus.FOUND) ;
			return resposta;
		}
	}

	@PostMapping("/cadastro/{id}")
	public ResponseEntity<?> cadastrarEndereco(@RequestBody Endereco endereco, @PathVariable long id) {
		HttpStatus status = HttpStatus.CONFLICT;
		if (endereco != null) {
			Usuario usuario = UsuarioRepositorio.getById(id);
			usuario.setEndereco(endereco);
			UsuarioRepositorio.save(usuario);
			status = HttpStatus.CREATED;
		}
		return new ResponseEntity<>(status);
		
	}

	@PutMapping("/atualizar")
	public ResponseEntity<?> atualizarEndereco(@RequestBody Endereco atualizacao) {
		HttpStatus status = HttpStatus.CONFLICT;
		Endereco endereco = repositorio.getById(atualizacao.getId());
		if (endereco != null) {
			EnderecoAtualizador atualizador = new EnderecoAtualizador();
			atualizador.atualizar(endereco, atualizacao);
			repositorio.save(endereco);
			status = HttpStatus.OK;
		} else {
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(status);
	}

	@DeleteMapping("/excluir/{id}")
	public ResponseEntity<?> excluirEndereco(@RequestBody Endereco exclusao, @PathVariable long id) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Endereco endereco = repositorio.getById(exclusao.getId());
		if (endereco != null) {
			Usuario usuario = UsuarioRepositorio.getById(id);
			usuario.setEndereco(null);
			UsuarioRepositorio.save(usuario);
			status = HttpStatus.OK;
		}
		return new ResponseEntity<>(status);
	}
}

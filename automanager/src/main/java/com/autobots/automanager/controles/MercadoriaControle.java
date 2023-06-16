package com.autobots.automanager.controles;

import java.util.List;
import java.util.Set;

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

import com.autobots.automanager.adicionadorLinks.AdicionadorLinkMercadoria;
import com.autobots.automanager.atualizadores.MercadoriaAtualizador;
import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.modelo.Selecionador;
import com.autobots.automanager.repositorios.EmpresaRepositorio;
import com.autobots.automanager.repositorios.MercadoriaRepositorio;

@RestController
@RequestMapping("/mercadoria")
public class MercadoriaControle {
	@Autowired
	private MercadoriaRepositorio repositorio;
	@Autowired
	private EmpresaRepositorio EmpresaRepositorio;
	@Autowired
	private AdicionadorLinkMercadoria adicionadorLink;

	@GetMapping("/{id}")
	public ResponseEntity<Mercadoria> obterMercadoria(@PathVariable long id) {
		List<Mercadoria> mercadorias = repositorio.findAll();
		Mercadoria mercadoria = Selecionador.mercadoriaSelecionador(mercadorias, id);
		if (mercadoria == null) {
			ResponseEntity<Mercadoria> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLink.adicionarLink(mercadoria);
			ResponseEntity<Mercadoria> resposta = new ResponseEntity<Mercadoria>(mercadoria, HttpStatus.FOUND);
			return resposta;
		}
	}

	@GetMapping("/visualizar")
	public ResponseEntity<List<Mercadoria>> obterMercadorias() {
		List<Mercadoria> Mercadorias = repositorio.findAll();
		if (Mercadorias.isEmpty()) {
			ResponseEntity<List<Mercadoria>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLink.adicionarLink(Mercadorias);
			ResponseEntity<List<Mercadoria>> resposta = new ResponseEntity<>(Mercadorias, HttpStatus.FOUND);
			return resposta;
		}
	}

	@PostMapping("/cadastro/{id}")
	public ResponseEntity<?> cadastrarMercadoria(@RequestBody Mercadoria mercadoria, @PathVariable long id) {
		HttpStatus status = HttpStatus.CONFLICT;
		if (mercadoria.getId() == null) {
			Empresa empresa = EmpresaRepositorio.getById(id);
			Set<Mercadoria> mercadorias = empresa.getMercadorias();
			mercadorias.add(mercadoria);
			empresa.setMercadorias(mercadorias);
			EmpresaRepositorio.save(empresa);
			status = HttpStatus.CREATED;
		}
		return new ResponseEntity<>(status);
	}

	@PutMapping("/atualizar")
	public ResponseEntity<?> atualizarMercadoria(@RequestBody Mercadoria atualizacao) {
		HttpStatus status = HttpStatus.CONFLICT;
		Mercadoria mercadoria = repositorio.getById(atualizacao.getId());
		if (mercadoria != null) {
			MercadoriaAtualizador atualizador = new MercadoriaAtualizador();
			atualizador.atualizar(mercadoria, atualizacao);
			repositorio.save(mercadoria);
			status = HttpStatus.OK;
		} else {
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(status);
	}

	@DeleteMapping("/excluir/{id}")
	public ResponseEntity<?> excluirMercadoria(@RequestBody Mercadoria exclusao, @PathVariable long id) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Mercadoria mercadoria = repositorio.getById(exclusao.getId());
		if (mercadoria != null) {
			Empresa empresa = EmpresaRepositorio.getById(id);
			Set<Mercadoria> mercadorias = empresa.getMercadorias();
			for (Mercadoria mer: mercadorias) {
				if (mer.getId() == exclusao.getId()) {
					mercadorias.remove(mer);
					break;
				}
			}
			empresa.setMercadorias(mercadorias);
			EmpresaRepositorio.save(empresa);
			status = HttpStatus.OK;
		}
		return new ResponseEntity<>(status);
	}
}

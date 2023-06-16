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

import com.autobots.automanager.adicionadorLinks.AdicionadorLinkServico;
import com.autobots.automanager.atualizadores.ServicoAtualizador;
import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.modelo.Selecionador;
import com.autobots.automanager.repositorios.ServicoRepositorio;
import com.autobots.automanager.repositorios.EmpresaRepositorio;

@RestController
@RequestMapping("/servico")
public class ServicoControle {
	@Autowired
	private ServicoRepositorio repositorio;
	@Autowired
	private EmpresaRepositorio EmpresaRepositorio;
	@Autowired
	private AdicionadorLinkServico adicionadorLink;

	@GetMapping("/{id}")
	public ResponseEntity<Servico> obterServico(@PathVariable long id) {
		List<Servico> servicos = repositorio.findAll();
		Servico servico = Selecionador.servicoSelecionador(servicos, id);
		if (servico == null) {
			ResponseEntity<Servico> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLink.adicionarLink(servico);
			ResponseEntity<Servico> resposta = new ResponseEntity<Servico>(servico, HttpStatus.FOUND);
			return resposta;
		}
	}

	@GetMapping("/visualizar")
	public ResponseEntity<List<Servico>> obterServicos() {
		List<Servico> servicos = repositorio.findAll();
		if (servicos.isEmpty()) {
			ResponseEntity<List<Servico>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLink.adicionarLink(servicos);
			ResponseEntity<List<Servico>> resposta = new ResponseEntity<>(servicos, HttpStatus.FOUND);
			return resposta;
		}
	}

	@PostMapping("/cadastro/{id}")
	public ResponseEntity<?> cadastrarServico(@RequestBody Servico servico, @PathVariable long id) {
		HttpStatus status = HttpStatus.CONFLICT;
		if (servico.getId() == null) {
			Empresa empresa = EmpresaRepositorio.getById(id);
			Set<Servico> servicos = empresa.getServicos();
			servicos.add(servico);
			empresa.setServicos(servicos);
			EmpresaRepositorio.save(empresa);
			status = HttpStatus.CREATED;
		}
		return new ResponseEntity<>(status);
	}

	@PutMapping("/atualizar")
	public ResponseEntity<?> atualizarServico(@RequestBody Servico atualizacao) {
		HttpStatus status = HttpStatus.CONFLICT;
		Servico servico = repositorio.getById(atualizacao.getId());
		if (servico != null) {
			ServicoAtualizador atualizador = new ServicoAtualizador();
			atualizador.atualizar(servico, atualizacao);
			repositorio.save(servico);
			status = HttpStatus.OK;
		} else {
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(status);
	}

	@DeleteMapping("/excluir/{id}")
	public ResponseEntity<?> excluirServico(@RequestBody Servico exclusao, @PathVariable long id) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Servico servico = repositorio.getById(exclusao.getId());
		if (servico != null) {
			Empresa empresa = EmpresaRepositorio.getById(id);
			Set<Servico> servicos = empresa.getServicos();
			for (Servico ser: servicos) {
				if (ser.getId() == exclusao.getId()) {
					servicos.remove(ser);
					break;
				}
			}
			empresa.setServicos(servicos);
			EmpresaRepositorio.save(empresa);
			status = HttpStatus.OK;
		}
		return new ResponseEntity<>(status);
	}
}

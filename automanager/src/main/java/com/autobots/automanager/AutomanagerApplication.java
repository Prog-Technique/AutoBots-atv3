package com.autobots.automanager;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.autobots.automanager.entidades.CredencialUsuarioSenha;
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.entidades.Email;
import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.enumeracoes.PerfilUsuario;
import com.autobots.automanager.enumeracoes.TipoDocumento;
import com.autobots.automanager.enumeracoes.TipoVeiculo;
import com.autobots.automanager.repositorios.RepositorioEmpresa;

@SpringBootApplication
public class AutomanagerApplication implements CommandLineRunner {

	@Autowired
	private RepositorioEmpresa repositorioEmpresa;

	public static void main(String[] args) {
		Map<String, Object> configuracao = new HashMap<>();
		configuracao.put("server.port", "8080");
		SpringApplication app = new SpringApplication(AutomanagerApplication.class);
		app.setDefaultProperties(configuracao);
		app.run(args);
		
	}

	@Override
	public void run(String... args) throws Exception {
		Empresa empresa = new Empresa();
		empresa.setRazaoSocial("McQueen Automotiva");
		empresa.setNomeFantasia("Relampago Marquinhos");
		empresa.setCadastro(new Date());

		Endereco enderecoEmpresa = new Endereco();
		enderecoEmpresa.setEstado("São Paulo");
		enderecoEmpresa.setCidade("São Jasé dos Campos");
		enderecoEmpresa.setBairro("Jardim Satélite");
		enderecoEmpresa.setRua("Av. Andrômeda");
		enderecoEmpresa.setNumero("333");
		enderecoEmpresa.setCodigoPostal("12260-000");

		empresa.setEndereco(enderecoEmpresa);

		Telefone telefoneEmpresa = new Telefone();
		telefoneEmpresa.setDdd("012");
		telefoneEmpresa.setNumero("39315342");

		empresa.getTelefones().add(telefoneEmpresa);
		
		

		Usuario funcionario = new Usuario();
		funcionario.setNome("Marcos");
		funcionario.setNomeSocial("Marco");
		funcionario.getPerfis().add(PerfilUsuario.FUNCIONARIO);

		Email emailFuncionario = new Email();
		emailFuncionario.setEndereco("marcos.co@gmail.com");

		funcionario.getEmails().add(emailFuncionario);

		Endereco enderecoFuncionario = new Endereco();
		enderecoFuncionario.setEstado("São Paulo");
		enderecoFuncionario.setCidade("São José dos Campos");
		enderecoFuncionario.setBairro("Jardim America");
		enderecoFuncionario.setRua(" R. Andorra");
		enderecoFuncionario.setNumero("500");
		enderecoFuncionario.setCodigoPostal("12235-050");

		funcionario.setEndereco(enderecoFuncionario);

		empresa.getUsuarios().add(funcionario);

		Telefone telefoneFuncionario = new Telefone();
		telefoneFuncionario.setDdd("012");
		telefoneFuncionario.setNumero("3570-5000");

		funcionario.getTelefones().add(telefoneFuncionario);

		Documento cpf = new Documento();
		cpf.setDataEmissao(new Date());
		cpf.setNumero("46861578815");
		cpf.setTipo(TipoDocumento.CPF);

		funcionario.getDocumentos().add(cpf);

		CredencialUsuarioSenha credencialFuncionario = new CredencialUsuarioSenha();
		credencialFuncionario.setInativo(false);
		credencialFuncionario.setNomeUsuario("marcaofuncionario");
		credencialFuncionario.setSenha("123");
		credencialFuncionario.setCriacao(new Date());
		credencialFuncionario.setUltimoAcesso(new Date());

		funcionario.getCredenciais().add(credencialFuncionario);
		
		

		Usuario fornecedor = new Usuario();
		fornecedor.setNome("Patricia");
		fornecedor.setNomeSocial("Patty");
		fornecedor.getPerfis().add(PerfilUsuario.FORNECEDOR);

		Email emailFornecedor = new Email();
		emailFornecedor.setEndereco("patty@gmail.com");

		fornecedor.getEmails().add(emailFornecedor);

		CredencialUsuarioSenha credencialFornecedor = new CredencialUsuarioSenha();
		credencialFornecedor.setInativo(false);
		credencialFornecedor.setNomeUsuario("pattyfornecedora");
		credencialFornecedor.setSenha("12345");
		credencialFornecedor.setCriacao(new Date());
		credencialFornecedor.setUltimoAcesso(new Date());

		fornecedor.getCredenciais().add(credencialFornecedor);

		Documento cnpj = new Documento();
		cnpj.setDataEmissao(new Date());
		cnpj.setNumero("23.440.640/0001-18");
		cnpj.setTipo(TipoDocumento.CNPJ);

		fornecedor.getDocumentos().add(cnpj);

		Endereco enderecoFornecedor = new Endereco();
		enderecoFornecedor.setEstado("São Paulo");
		enderecoFornecedor.setCidade("São José dos Campos");
		enderecoFornecedor.setBairro("Jardim Satélite");
		enderecoFornecedor.setRua("Av. Cidade Jardim");
		enderecoFornecedor.setNumero("151");
		enderecoFornecedor.setCodigoPostal("12231-675");

		fornecedor.setEndereco(enderecoFornecedor);

		empresa.getUsuarios().add(fornecedor);
		
		

		Mercadoria lanterna = new Mercadoria();
		lanterna.setCadastro(new Date());
		lanterna.setFabricao(new Date());
		lanterna.setNome("Lanterna | Renault ");
		lanterna.setValidade(new Date());
		lanterna.setQuantidade(50);
		lanterna.setValor(132.0);
		lanterna.setDescricao("Lanterna da Renault para Duster 2016/2017");

		empresa.getMercadorias().add(lanterna);

		fornecedor.getMercadorias().add(lanterna);
		
		

		Usuario cliente = new Usuario();
		cliente.setNome("Amanda Caires Pereira");
		cliente.setNomeSocial("Amandika");
		cliente.getPerfis().add(PerfilUsuario.CLIENTE);

		Email emailCliente = new Email();
		emailCliente.setEndereco("amandinha@gmail.com");

		cliente.getEmails().add(emailCliente);

		Documento cpfCliente = new Documento();
		cpfCliente.setDataEmissao(new Date());
		cpfCliente.setNumero("84042178073");
		cpfCliente.setTipo(TipoDocumento.CPF);

		cliente.getDocumentos().add(cpfCliente);

		CredencialUsuarioSenha credencialCliente = new CredencialUsuarioSenha();
		credencialCliente.setInativo(false);
		credencialCliente.setNomeUsuario("amandacliente");
		credencialCliente.setSenha("123456");
		credencialCliente.setCriacao(new Date());
		credencialCliente.setUltimoAcesso(new Date());

		cliente.getCredenciais().add(credencialCliente);

		Endereco enderecoCliente = new Endereco();
		enderecoCliente.setEstado("São Paulo");
		enderecoCliente.setCidade("São José dos Campos");
		enderecoCliente.setBairro("Distrito - Eugênio de Melo");
		enderecoCliente.setRua("Avenida Cesare Monsueto Giulio Lattes");
		enderecoCliente.setNumero("1350");
		enderecoCliente.setCodigoPostal("12247-014");

		cliente.setEndereco(enderecoCliente);
		
		
		Veiculo veiculo = new Veiculo();
		veiculo.setPlaca("ABC-1234");
		veiculo.setModelo("HB20");
		veiculo.setTipo(TipoVeiculo.HATCH);
		veiculo.setProprietario(cliente);

		cliente.getVeiculos().add(veiculo);

		empresa.getUsuarios().add(cliente);
		
		Servico alinhamento = new Servico();
		alinhamento.setNome("Alinhamento básico");
		alinhamento.setDescricao("Alinhamento de rodas de veículo - pequeno porte");
		alinhamento.setValor(150);

		Servico trocaParafusos = new Servico();
		trocaParafusos.setNome("Adição do parafuso com segredo");
		trocaParafusos.setDescricao("Troca de 1 parafuso normal de cada roda");
		trocaParafusos.setValor(30);

		empresa.getServicos().add(alinhamento);
		empresa.getServicos().add(trocaParafusos);

		Venda venda = new Venda();
		venda.setCadastro(new Date());
		venda.setCliente(cliente);
		venda.getMercadorias().add(lanterna);
		venda.setIdentificacao("123456789");
		venda.setFuncionario(funcionario);
		venda.getServicos().add(trocaParafusos);
		venda.getServicos().add(alinhamento);
		venda.setVeiculo(veiculo);
		veiculo.getVendas().add(venda);

		empresa.getVendas().add(venda);

		repositorioEmpresa.save(empresa);

	}
}
package br.com.alura.lojaJPA.testes;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.alura.lojaJPA.dao.CategoriaDAO;
import br.com.alura.lojaJPA.dao.ClienteDAO;
import br.com.alura.lojaJPA.dao.PedidoDAO;
import br.com.alura.lojaJPA.dao.ProdutoDAO;
import br.com.alura.lojaJPA.modelo.Categoria;
import br.com.alura.lojaJPA.modelo.Cliente;
import br.com.alura.lojaJPA.modelo.ItemPedido;
import br.com.alura.lojaJPA.modelo.Pedido;
import br.com.alura.lojaJPA.modelo.Produto;
import br.com.alura.lojaJPA.util.JPAUtil;
import br.com.alura.lojaJPA.vo.RelatorioDeVendasVo;

public class CadastroDePedido {
	
	public static void main(String[] args) {
		
		popularBancoDeDados();
		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDAO produtoDao = new ProdutoDAO(em);
		Produto produto = produtoDao.brucarPorId(1l);
		Produto produto2 = produtoDao.brucarPorId(2l);
		Produto produto3 = produtoDao.brucarPorId(3l);
		ClienteDAO clienteDao = new ClienteDAO(em);		
		Cliente cliente = clienteDao.buscarPorId(1l);
		
		em.getTransaction().begin();
		
		Pedido pedido = new Pedido(cliente);
		pedido.adicionarItem(new ItemPedido(10, produto, pedido));
		pedido.adicionarItem(new ItemPedido(40, produto2, pedido));
		
		Pedido pedido2 = new Pedido(cliente);
		pedido.adicionarItem(new ItemPedido(2, produto3, pedido2));
		
		PedidoDAO pedidoDao = new PedidoDAO(em);
		pedidoDao.cadastrar(pedido);
		pedidoDao.cadastrar(pedido2);
		
		em.getTransaction().commit();
		
		BigDecimal totalVendido = pedidoDao.valorTotalVendido();
		System.out.println("VALOR TOTAL: " + totalVendido);
		
		List<RelatorioDeVendasVo> relatorioDeVendas = pedidoDao.relatorioDeVendas();
		relatorioDeVendas.forEach(System.out::println);
		
		em.close();
		
	}
	
	private static void popularBancoDeDados() {
		Categoria celulares = new Categoria("Celulares");
		Categoria videogames = new Categoria("VideoGames");
		Categoria informatica = new Categoria("Informatica");

		
		Produto celular = new Produto("Xiaomi Redmi"
				, "Muito Legal", new BigDecimal("800"), 
				celulares );
		Produto videogame = new Produto("PS5"
				, "Playstation 5", new BigDecimal("1000"), 
				videogames );
		Produto macbook = new Produto("Macbook"
				, "Macbook Pro", new BigDecimal("1200"), 
				informatica );
		
		Cliente cliente = new Cliente("Rodrigo", "123456");
		
		EntityManager em = JPAUtil.getEntityManager();
		
		CategoriaDAO categoriaDao = new CategoriaDAO(em);
		ProdutoDAO produtoDao = new ProdutoDAO(em);
		ClienteDAO clienteDao = new ClienteDAO(em);
		
		em.getTransaction().begin();
		categoriaDao.cadastrar(celulares);
		categoriaDao.cadastrar(videogames);
		categoriaDao.cadastrar(informatica);

		
		produtoDao.cadastrar(celular);
		produtoDao.cadastrar(videogame);
		produtoDao.cadastrar(macbook);

		
		clienteDao.cadastrar(cliente);
		
		em.getTransaction().commit();
		em.close();
	}
}

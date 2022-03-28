package br.com.alura.lojaJPA.testes;

import java.math.BigDecimal;

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

public class TesteCriteria {
	public static void main(String[] args) {
		popularBancoDeDados();
		EntityManager em = JPAUtil.getEntityManager();
		
		ProdutoDAO produtoDAO = new ProdutoDAO(em);
		produtoDAO.buscarPorParametrosComCriteria("PS5", null, null);
		
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
		
		Pedido pedido = new Pedido(cliente);
		pedido.adicionarItem(new ItemPedido(10, celular, pedido));
		pedido.adicionarItem(new ItemPedido(40, videogame, pedido));
		
		Pedido pedido2 = new Pedido(cliente);
		pedido.adicionarItem(new ItemPedido(2, macbook, pedido2));
		
		EntityManager em = JPAUtil.getEntityManager();
		
		CategoriaDAO categoriaDao = new CategoriaDAO(em);
		ProdutoDAO produtoDao = new ProdutoDAO(em);
		ClienteDAO clienteDao = new ClienteDAO(em);
		PedidoDAO pedidoDao = new PedidoDAO(em);
		
		em.getTransaction().begin();
		
		categoriaDao.cadastrar(celulares);
		categoriaDao.cadastrar(videogames);
		categoriaDao.cadastrar(informatica);

		
		produtoDao.cadastrar(celular);
		produtoDao.cadastrar(videogame);
		produtoDao.cadastrar(macbook);
		
		pedidoDao.cadastrar(pedido);
		pedidoDao.cadastrar(pedido2);
		
		clienteDao.cadastrar(cliente);

		em.getTransaction().commit();
		em.close();

	}
}

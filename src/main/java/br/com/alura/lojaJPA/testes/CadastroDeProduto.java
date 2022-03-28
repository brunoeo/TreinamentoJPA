package br.com.alura.lojaJPA.testes;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.alura.lojaJPA.dao.CategoriaDAO;
import br.com.alura.lojaJPA.dao.ProdutoDAO;
import br.com.alura.lojaJPA.modelo.Categoria;
import br.com.alura.lojaJPA.modelo.CategoriaId;
import br.com.alura.lojaJPA.modelo.Produto;
import br.com.alura.lojaJPA.util.JPAUtil;

public class CadastroDeProduto {

	public static void main(String[] args) {
		
		cadastrarProduto();
		
		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDAO produtoDao = new ProdutoDAO(em);
		
		Produto p = produtoDao.brucarPorId(1l);
		System.out.println(p.getNome());
		
		List<Produto> todos = produtoDao.buscarPorNomeDaCategoria("Celulares");
		
		todos.forEach(p2 -> System.out.println(p2.getNome()));
		
		BigDecimal precoDoProduto = produtoDao.precoDoProdutoComNome("Xiaomi Redmi");
		
		System.out.println(precoDoProduto);
		
	}

	private static void cadastrarProduto() {
		Categoria celulares = new Categoria("Celulares");
		
		Produto celular = new Produto("Xiaomi Redmi"
				, "Muito Legal", new BigDecimal("800"), 
				celulares );
		
		EntityManager em = JPAUtil.getEntityManager();
		
		CategoriaDAO categoriaDao = new CategoriaDAO(em);
		ProdutoDAO produtoDao = new ProdutoDAO(em);
		
		em.getTransaction().begin();
		categoriaDao.cadastrar(celulares);
		produtoDao.cadastrar(celular);
		em.getTransaction().commit();
		
		em.find(Categoria.class, new CategoriaId("CELULARES", "xpto"));
		
		em.close();
	}
}

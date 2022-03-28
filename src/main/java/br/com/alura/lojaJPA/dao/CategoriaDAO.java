package br.com.alura.lojaJPA.dao;

import javax.persistence.EntityManager;

import br.com.alura.lojaJPA.modelo.Categoria;

public class CategoriaDAO {
	
	private EntityManager em;
	

	public CategoriaDAO(EntityManager em) {
		this.em = em;
	}
	
	public void cadastrar(Categoria categoria) {
		this.em.persist(categoria);
	}
	
	public void atualizar(Categoria categoria) {
		this.em.merge(categoria);
	}
	
	public void remove(Categoria categoria) {
		categoria = em.merge(categoria);
		this.em.remove(categoria);
	}
}

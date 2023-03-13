package com.formacionbdi.springboot.app.usuarios.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.formacionbdi.springboot.app.usuarios.models.entity.Usuario;

@RepositoryRestResource(path = "usuarios")
public interface UsuarioDao extends PagingAndSortingRepository<Usuario, Long>{
	
	public Usuario findByUsername(String username);
	
	public Usuario findByUsernameAndEmail(String username, String email);
	
	@Query("select u from Usuario u where u.username=?1")
	public Usuario obtenerPorUsername(String username);
}

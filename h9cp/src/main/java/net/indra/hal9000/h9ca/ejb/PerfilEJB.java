package net.indra.hal9000.h9ca.ejb;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import net.indra.hal9000.h9ca.model.Perfil;
import net.indra.hal9000.util.RecursosHal9000;

@Stateless
public class PerfilEJB {

	@Inject
	@RecursosHal9000
	private Logger log;

	@Inject
	@RecursosHal9000
	private EntityManager em;
	
	// CRUD RESTful methods below
	// Alta
	public Perfil crear(Perfil p) {
		log.info("Antes de crear: " + p);
		// excepción si la instancia tiene id
		// excepción si la instancia no tiene info
		em.persist(p);
		log.info("Despues de crear: " + p);
		return p;
	}

}

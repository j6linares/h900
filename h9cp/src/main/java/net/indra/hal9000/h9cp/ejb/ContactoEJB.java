package net.indra.hal9000.h9cp.ejb;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import net.indra.hal9000.h9cp.model.Contacto;
import net.indra.hal9000.h9cp.util.RecursosHal9000;

@Stateless
public class ContactoEJB {

	@Inject
	@RecursosHal9000
	private Logger log;

	@Inject
	@RecursosHal9000
	private EntityManager em;

	// CRUD RESTful methods below
	// Alta
	public Contacto crear(Contacto c) {
		log.info("Antes de crear el contacto: " + c);
		//excepción si el contacto tiene id
		//excepción si el contacto no tiene info en contacto
		em.persist(c);
		log.info("Despues de crear el contacto: " + c);
		return c;
	}

	// Baja
	public void borrar(Contacto c) {
		log.info("Antes de borrar el contacto: " + c);
		//excepción si el contacto NO tiene id
		//excepción si el contacto tiene id pero no existe en BD
		em.remove(c);
		log.info("Después de borrar el contacto: " + c);
	}

	// Baja por Id
	public void borrarPorId(Long id) {
		log.info("Antes de borrar el contacto por id: " + buscarPorId(id));
		//excepción si el id del contacto no existe en BD
		em.remove(buscarPorId(id));
		log.info("Después de borrar el contacto: " + buscarPorId(id));
	}

	// Modificación
	public void actualizar(Contacto c) {
		log.info("Antes de actualizar el contacto: " + c);
		//excepción si el contacto NO tiene id
		//excepción si el contacto tiene id pero no existe en BD
		em.merge(c);
		log.info("Después de actualizar el contacto: " + c);
	}

	// Consulta por Id
	public Contacto buscarPorId(Long id) {
		log.info("Antes de buscar el contacto por id: " + id);
		//excepción si id no existe en BD, devuelve objeto nulo
		return em.find(Contacto.class, id);
	}

	// Consultar todos
	public List<Contacto> buscarTodos() {
		log.info("Antes de buscar todos los contactos " );
		TypedQuery<Contacto> query = em.createQuery("SELECT c FROM Contacto c", Contacto.class);
		List<Contacto> contactos = query.getResultList();
		log.info("Despues de buscar todos los contactos "+contactos );
		return contactos;
	}

	// Consultar por nombre
	public List<Contacto> buscarPorNombre(String nombre) {
		log.info("Antes de buscar contactos por nombre" );
		TypedQuery<Contacto> query = em.createNamedQuery("buscarContactoPorNombre", Contacto.class);
		query.setParameter("ccontacto", nombre);
		List<Contacto> contactos = query.getResultList();
		log.info("Despues de buscar contactos por nombre "+contactos );
		return contactos;
	}
	// Consultar por contiene nombre
	public List<Contacto> buscarPorContieneNombre(String nombre) {
		log.info("Antes de buscar contactos por contiene nombre" );
		TypedQuery<Contacto> query = em.createNamedQuery("buscarContactoPorContieneNombre", Contacto.class);
		query.setParameter("ccontacto", "%"+nombre+"%");
		List<Contacto> contactos = query.getResultList();
		log.info("Despues de buscar contactos por contiene nombre "+contactos );
		return contactos;
	}
	
	// Guardar un contacto (alta sino existe, modificación si ya existe)
	public Response guardar(Contacto c) {
		log.info("Antes de guardar el contacto "+c );

		try {
			ResponseBuilder builder;
			if (c.getId() == null) { // contacto sin id, crear nuevo
				log.info("Contacto NO existe en BD "+c );
				em.persist(c);
				log.info("Despues de guardar el contacto "+c );
				builder = Response.ok(c);
			} else { // ya existe el contacto en BD?
				Contacto uC=buscarPorId(c.getId());
				if (uC.getId() !=null) {
					log.info("Contacto YA existe en BD "+uC );
					uC = em.merge(c);
					log.info("Despues de guardar el contacto "+uC );
					builder = Response.ok(uC);
				} else { // contacto con id pero no existe en BD se persiste sin mantener el id
					log.info("Contacto con id NO existe en BD "+c );
					em.persist(c);
					log.info("Despues de guardar el contacto "+c );
					builder = Response.ok(c);
				}
			}
			return builder.build();

		} catch (Exception e) {
			throw new EJBException(e);
		}
	}

}

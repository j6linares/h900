package net.indra.hal9000.h9cp.ejb;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import net.indra.hal9000.h9cp.model.Anuncio;
import net.indra.hal9000.h9cp.model.Contacto;
import net.indra.hal9000.h9cp.util.RecursosHal9000;

@Stateless
public class AnuncioEJB {

	@Inject
	@RecursosHal9000
	private Logger log;

	@Inject
	@RecursosHal9000
	private EntityManager em;

	// CRUD RESTful methods below
	// Alta
	public Anuncio crear(Anuncio a) {
		log.info("Antes de crear el anuncio: " + a);
		//excepción si el anuncio tiene id
		//excepción si el anuncio no tiene info en anuncio
		em.persist(a);
		log.info("Despues de crear el anuncio: " + a);
		return a;
	}

	// Baja
	public void borrar(Anuncio a) {
		log.info("Antes de borrar el anuncio: " + a);
		//excepción si el anuncio NO tiene id
		//excepción si el anuncio tiene id pero no existe en BD
		em.remove(a);
		log.info("Después de borrar el anuncio: " + a);
	}

	// Baja por Id
	public void borrarPorId(Long id) {
		log.info("Antes de borrar el anuncio por id: " + buscarPorId(id));
		//excepción si el id del anuncio no existe en BD
		em.remove(buscarPorId(id));
		log.info("Después de borrar el anuncio: " + buscarPorId(id));
	}

	// Modificación
	public void actualizar(Anuncio a) {
		log.info("Antes de actualizar el anuncio: " + a);
		//excepción si el anuncio NO tiene id
		//excepción si el anuncio tiene id pero no existe en BD
		em.merge(a);
		log.info("Después de actualizar el anuncio: " + a);
	}

	// Consulta por Id
	public Anuncio buscarPorId(Long id) {
		log.info("Antes de buscar el anuncio por id: " + id);
		//excepción si id no existe en BD, devuelve objeto nulo
		return em.find(Anuncio.class, id);
	}

	// Consultar todos
	public List<Anuncio> buscarTodos() {
		log.info("Antes de buscar todos los anuncios " );
		TypedQuery<Anuncio> query = em.createQuery("SELECT a FROM Anuncio a", Anuncio.class);
		List<Anuncio> anuncios = query.getResultList();
		log.info("Despues de buscar todos los anuncios "+anuncios );
		return anuncios;
	}

	// Consultar por referencia
	public List<Anuncio> buscarPorReferencia(String referencia) {
		log.info("Antes de buscar anuncios por referencia" );
		TypedQuery<Anuncio> query = em.createNamedQuery("buscarAnuncioPorReferencia", Anuncio.class);
		query.setParameter("areferencia", referencia);
		List<Anuncio> anuncios = query.getResultList();
		log.info("Despues de buscar anuncios por referencia "+anuncios );
		return anuncios;
	}
	// Consultar por contiene objeto
	public List<Anuncio> buscarPorContieneObjeto(String objeto) {
		log.info("Antes de buscar anuncios por contiene objeto" );
		TypedQuery<Anuncio> query = em.createNamedQuery("buscarAnuncioPorContieneObjeto", Anuncio.class);
		query.setParameter("aobjeto", "%"+objeto+"%");
		List<Anuncio> anuncios = query.getResultList();
		log.info("Despues de buscar anuncios por contiene objeto "+anuncios );
		return anuncios;
	}
	
	// Guardar un anuncio (alta sino existe, modificación si ya existe)
	public Response guardar(Anuncio a) {
		log.info("Antes de guardar el anuncio "+a );

		try {
			ResponseBuilder builder;
			if (a.getId() == null) { // anuncio sin id, crear nuevo
				log.info("Anuncio NO existe en BD "+a );
				em.persist(a);
				log.info("Despues de guardar el anuncio "+a );
				builder = Response.ok(a);
			} else { // ya existe el anuncio en BD?
				Anuncio uA=buscarPorId(a.getId());
				if (uA.getId() !=null) {
					log.info("Anuncio YA existe en BD "+uA );
					uA = em.merge(a);
					log.info("Despues de guardar el anuncio "+uA );
					builder = Response.ok(uA);
				} else { // anuncio con id pero no existe en BD se persiste sin mantener el id
					log.info("Anuncio con id NO existe en BD "+a );
					em.persist(a);
					log.info("Despues de guardar el anuncio "+a );
					builder = Response.ok(a);
				}
			}
			return builder.build();

		} catch (Exception e) {
			throw new EJBException(e);
		}
	}
	
	//Add JOIN FETCH
	public Set<Contacto> getAllAnunciosContacto() {
		
		TypedQuery<Contacto> query = em.createQuery(
				//"SELECT c FROM Contacto c " //FetchType.EAGER
				"SELECT c FROM Contacto c JOIN FETCH c.anuncios a JOIN FETCH a.adjudicacion" //FetchType.LAZY
				, Contacto.class);
		
		return new HashSet<Contacto>(query.getResultList());
		
	}
	

}

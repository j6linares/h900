package net.indra.hal9000.h9ca.ejb;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import net.indra.hal9000.h9ca.model.Perfil;
import net.indra.hal9000.h9ca.model.Usuario;
import net.indra.hal9000.h9ca.util.Empleado;
import net.indra.hal9000.h9ca.util.UsuarioUtil;
import net.indra.hal9000.h9cp.util.RecursosHal9000;

@Stateless
public class UsuarioEJB {

	@Inject
	@RecursosHal9000
	private Logger log;

	@Inject
	@RecursosHal9000
	private EntityManager em;
	
	@Inject @Empleado
	private UsuarioUtil usuarioUtil;

	@Inject
	private PerfilEJB perfilEJB;
	
	// CRUD RESTful methods below
	// Alta
	public Usuario crear(Usuario u) {
		log.info("Antes de crear: " + u);
		// TODO excepción si la instancia tiene id
		// excepción si la instancia no tiene info
		em.persist(u);
		log.info("Despues de crear: " + u);
		return u;
	}

	// Baja
	public void borrar(Usuario u) {
		log.info("Antes de borrar: " + u);
		// excepción si la instancia NO tiene id
		// excepción si la instancia tiene id pero no existe en BD
		em.remove(u);
		log.info("Después de borrar: " + u);
	}

	// Baja por Id
	public void borrarPorId(Long id) {
		log.info("Antes de borrar por id: " + buscarPorId(id));
		// excepción si el id no existe en BD
		em.remove(buscarPorId(id));
		log.info("Después de borrar: " + buscarPorId(id));
	}

	// Modificación
	public void actualizar(Usuario u) {
		log.info("Antes de actualizar: " + u);
		// excepción si la instancia NO tiene id
		// excepción si la instancia tiene id pero no existe en BD
		em.merge(u);
		log.info("Después de actualizar: " + u);
	}

	// Consulta por Id
	public Usuario buscarPorId(Long id) {
		log.info("Antes de buscar por id: " + id);
		// excepción si id no existe en BD, devuelve objeto nulo
		return em.find(Usuario.class, id);
	}

	// Consultar todos
	public List<Usuario> buscarTodos() {
		log.info("Antes de buscar todos.");
		TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u", Usuario.class);
		List<Usuario> usuarios = query.getResultList();
		log.info("Despues de buscar todos: " + usuarios);
		return usuarios;
	}

	// Buscar por usuario
	public Usuario buscarPorUsuario(String usuario) {
		Usuario u = null;
		log.info("Antes de buscar por usuario");
		try {
			TypedQuery<Usuario> query = em.createNamedQuery("buscarUsuarioPorUsuario", Usuario.class);
			query.setParameter("uusuario", usuario);
			u = query.getSingleResult();
		} catch (NoResultException e) {
			e.printStackTrace();
			log.warning("No existe usuario en bd");
		} catch (NonUniqueResultException e) {
			e.printStackTrace();
			log.severe("Existen varios usuario en bd");
		}
		log.info("Despues de buscar por usuario " + u);
		return u;
	}

	// Buscar por userid
	public Usuario buscarPorUserid(String userid) {
		Usuario u = null;
		log.info("Antes de buscar por userid");
		try {
			TypedQuery<Usuario> query = em.createNamedQuery("buscarUsuarioPorUserid", Usuario.class);
			query.setParameter("uuserid", userid);
			u = query.getSingleResult();
		} catch (NoResultException e) {
			e.printStackTrace();
			log.warning("No existe userid en bd");
		} catch (NonUniqueResultException e) {
			e.printStackTrace();
			log.severe("Existen varios userid en bd");
		}
		log.info("Despues de buscar por userid " + u);
		return u;
	}

	// Consultar por perfil
	public List<Usuario> buscarPorPerfil(String perfil) {
		log.info("Antes de buscar por perfil");
		TypedQuery<Usuario> query = em.createNamedQuery("buscarUsuariosPorPerfil", Usuario.class);
		query.setParameter("uperfil", perfil);
		List<Usuario> usuarios = query.getResultList();
		log.info("Despues de buscar por perfil " + usuarios);
		return usuarios;
	}

	// Guardar un usuario (alta sino existe, modificación si ya existe)
	public Response guardar(Usuario u) {
		log.info("Antes de guardar:" + u);

		try {
			ResponseBuilder builder;
			if (u.getId() == null) { // anuncio sin id, crear nuevo
				log.info("NO existe en BD " + u);
				em.persist(u);
				log.info("Despues de guardar:" + u);
				builder = Response.ok(u);
			} else { // ya existe el anuncio en BD?
				Usuario uA = buscarPorId(u.getId());
				if (uA.getId() != null) {
					log.info("YA existe en BD:" + uA);
					uA = em.merge(u);
					log.info("Despues de guardar:" + uA);
					builder = Response.ok(uA);
				} else { // instancia con id pero no existe en BD se persiste
							// sin mantener el id
					log.info("Instancia con id NO existe en BD " + u);
					em.persist(u);
					log.info("Despues de guardar:" + u);
					builder = Response.ok(u);
				}
			}
			return builder.build();

		} catch (Exception e) {
			throw new EJBException(e);
		}
	}

	// Add JOIN FETCH
	public Set<Usuario> getAllUsuariosPerfil() {

		TypedQuery<Usuario> query = em.createQuery(
				// "SELECT u FROM Usuario u " //FetchType.EAGER
				"SELECT u FROM Usuario u JOIN FETCH u.perfiles " // FetchType.LAZY
				, Usuario.class);

		return new HashSet<Usuario>(query.getResultList());

	}

	// Add JOIN FETCH
	public Set<Perfil> getAllPerfilesUsuario() {

		TypedQuery<Perfil> query = em.createQuery(
				// "SELECT p FROM Perfil p " //FetchType.EAGER
				"SELECT p FROM Perfil p JOIN FETCH p.usuarios " // FetchType.LAZY
				, Perfil.class);

		return new HashSet<Perfil>(query.getResultList());

	}

	public String identificacionUsuario(Usuario u) {
		return usuarioUtil.identificacionUsuario(u);
	}
	
	@PostConstruct
	public void altaDatosPrueba() {
//  insert into tbh9caus (h9caus_id, h9caus_usuario, h9caus_userid) 
//		values (1,"jgarcial", "gl001")
//		, (2,"jatxopitea", "tg00")
//		, (3,"jgarciali", null)
//		, (4,"vcasas", "cd15")
//	;
		Usuario u = new Usuario();
		u.setUsuario("jgarcial");
		u.setUserid("gl001");
		u = this.crear(u);
		
		Set<Usuario> us=new HashSet<>();
		us.add(u);
		
//	insert into tbh9cape (h9cape_id, h9cape_perfil) 
//		values (1, "Responsable del contrato")
//		, (2, "Responsable del servicio")
//		, (3, "Técnico Remedy")
//		, (4, "Técnico de Equipo de solvencia")
//		, (5, "Técnico de Equipo apoyo")
//		, (6, "Empleado Indra sin técnico")
//		;
		Perfil p=new Perfil();
		p.setPerfil("Responsable de contrato");
		p.setUsuarios(us);
		perfilEJB.crear(p);
		
		Set<Perfil> ps=new HashSet<>();
		ps.add(p);
		
		u.setPerfiles(ps);
		this.actualizar(u);
		
//	insert into tbh9caup values (1,1), (1,3), (1,5)
//		, (2,2), (2,3), (2,5)
//		, (3,6)
//		, (4,3), (4,4)
//	;

	}

}

package net.indra.hal9000.h9ca.rest;

import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import net.indra.hal9000.h9ca.ejb.UsuarioEJB;
import net.indra.hal9000.h9ca.model.Perfil;
import net.indra.hal9000.h9ca.model.Usuario;
import net.indra.hal9000.util.RecursosHal9000;

@Stateless
// http://localhost:8080/h9cp/api/rest/
@Path("/rest")
@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
public class H9caRestService {
	
	@EJB
	UsuarioEJB usuarioEJB;
	
	@RecursosHal9000
	@Inject
	private Logger log;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("buscar/usuario/{usuario}")
	// http://localhost:8080/h9cp/api/rest/buscar/usuario/{usuario}
	public Response buscarUsuarioPorUsuario(@PathParam("usuario") String usuario) {
		ResponseBuilder builder;
		Usuario u=usuarioEJB.buscarPorUsuario(usuario);
		if ( u == null) {
			builder = Response.status(Response.Status.NO_CONTENT);
		} else {
			builder = Response.ok(u);
		}
		return builder.build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("buscar/usuario/userid/{userid}")
	// http://localhost:8080/h9cp/api/rest/buscar/usuario/userid/{userid}
	public Response buscarUsuarioPorUserid(@PathParam("userid") String userid) {
		ResponseBuilder builder;
		Usuario u=usuarioEJB.buscarPorUserid(userid);
		if ( u == null) {
			builder = Response.status(Response.Status.NO_CONTENT);
		} else {
			builder = Response.ok(u);
		}
		return builder.build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("login/{usuario},{userid}")
	// http://localhost:8080/h9cp/api/rest/login/{usuario},{userid}
	public String login(@PathParam("usuario") String usuario
			, @PathParam("userid") String userid) {
		
		log.info("login("+usuario+","+userid+")");
		String vista = null;
		Usuario currentUsuario = usuarioEJB.buscarPorUsuario(usuario);
		log.info("usuario:"+currentUsuario);
		if (currentUsuario != null) {
			log.info("Perfiles de usuario:"+currentUsuario.getPerfiles());
			Perfil currentPerfil = currentUsuario.getPerfilPorDefecto();
			log.info("perfil por defecto:"+currentPerfil);
			if (currentUsuario.getUserid().equals(userid)) {
				log.info("welcome");
				vista="welcome";
			} else {
				log.info("error de acceso, userid no coincide");
			}
		} else {
			log.info("error de acceso, usuario no existe");
		}
		return vista;
	}
}

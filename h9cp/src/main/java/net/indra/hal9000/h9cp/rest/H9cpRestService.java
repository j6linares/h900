package net.indra.hal9000.h9cp.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import net.indra.hal9000.h9cp.ejb.ContactoEJB;
import net.indra.hal9000.h9cp.model.Contacto;

@Stateless
// http://localhost:8080/h9cp/api/rest/buscar/contactos
@Path("/rest")
@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
public class H9cpRestService {
	
	@EJB
	ContactoEJB contactoEJB;
	
	@PUT
	@Path("crear/contacto")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Contacto crearContacto(Contacto c) {
		return contactoEJB.crear(c);
	}
	@DELETE
	@Path("borrar/contacto")
	public Response borrarContacto(Contacto c) {
		ResponseBuilder builder;
		Response r=buscarContactoPorId(c.getId());
		if ( r.getStatus() == Response.Status.OK.getStatusCode()) {
			contactoEJB.borrar((Contacto) r.getEntity());
			builder=Response.ok();
		} else {
			builder=Response.serverError();
		}
		return builder.build();
		
		
	}
	@DELETE
	@Path("borrar/contacto/porId/{id}")
	public void borrarContactoPorId(@PathParam("id") Long id) {
		contactoEJB.borrarPorId(id);
	}
	@POST
	@Path("guardar/contacto")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response guardarContacto(Contacto c) {
		
		//si el contacto no tiene id, se da de alta
		//sino se busca en BD y si no existe (null) se devuelve un error
		//e.c.c. se actualiza 
		ResponseBuilder builder;
		if (c.getId() == null) {
			Contacto newC = new Contacto();
			newC.setContacto(c.getContacto());
			
			contactoEJB.crear(newC);
			
			builder = Response.ok(newC);
		} else {
			Contacto contactoToUpdate = contactoEJB.buscarPorId(c.getId());
			
			if (contactoToUpdate == null) {
				builder = Response.serverError();
			} else {
				contactoToUpdate.setContacto(c.getContacto());
				contactoEJB.actualizar(contactoToUpdate);
				builder = Response.ok(contactoToUpdate);
			}
		}
		return builder.build();
	}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("buscar/contacto/porId/{id}")
	public Response buscarContactoPorId(@PathParam("id") Long id) {
		ResponseBuilder builder;
		Contacto c=contactoEJB.buscarPorId(id);
		if ( c == null) {
			builder = Response.status(Response.Status.NO_CONTENT);
		} else {
			builder = Response.ok(c);
		}
		return builder.build();
	}
	@GET
	@Path("buscar/contactos")
	@Produces(MediaType.APPLICATION_XML)
	public List<Contacto> buscarContactos() {
		return contactoEJB.buscarTodos();
	}
	@GET
	@Path("buscar/contactos/json")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Contacto> buscarContactosJson() {
		return contactoEJB.buscarTodos();
	}
}

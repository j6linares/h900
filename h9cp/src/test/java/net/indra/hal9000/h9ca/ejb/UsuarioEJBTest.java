package net.indra.hal9000.h9ca.ejb;

import static org.junit.Assert.*;

import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import net.indra.hal9000.h9ca.model.Usuario;
import net.indra.hal9000.util.RecursosHal9000;
import net.indra.hal9000.PlantillaBaseWebTest;

@RunWith(Arquillian.class)
public class UsuarioEJBTest extends PlantillaBaseWebTest {
	
	@Inject
	@RecursosHal9000
	private Logger logger;
	
	@EJB
	private UsuarioEJB usuarioEJB;
	
	@Test
	public void testUsuarioEJB() {
		Usuario u;
		
		logger.info("***** Iniciando test h9ca");
		logger.info("***** buscarTodos ");
		int n=usuarioEJB.buscarTodos().size();
		
		u=new Usuario();
		u.setUsuario("jgarcial");
		
		logger.info("***** Creando Usuario "+usuarioEJB.crear(u));
		assertTrue(u.getId() != null);
		
		logger.info("***** buscarTodos debe devolver uno mas q antes="+n);
		assertTrue (n+1==usuarioEJB.buscarTodos().size());
		
//		logger.info("***** actualizar debe modificar el contacto "+c1);
//		c1.setContacto("Primer Contacto Actualizado");
//		usuarioEJB.actualizar(c1);
//		c2 = usuarioEJB.buscarPorId(c1.getId());
//		logger.info("***** buscarPorId debe recuperar de bd el contacto actualizado "+c2);
//		assertEquals("Primer Contacto Actualizado", c2.getContacto());
//		
//		logger.info("***** guardar debe modificar el contacto existente "+c2);
//		c2.setContacto("Primer Contacto Guardado");
//		usuarioEJB.guardar(c2);
//		c1 = usuarioEJB.buscarPorId(c2.getId());
//		logger.info("***** buscarPorId debe recuperar de bd el contacto guardado "+c1);
//		assertEquals("Primer Contacto Guardado", c1.getContacto());
//		
//		logger.info("***** guardar debe crear el contacto no existente "+c);
//		c=new Contacto();
//		c.setContacto("Segundo Contacto Guardado");
//		Response r=usuarioEJB.guardar(c);
//		c2=(Contacto) r.getEntity();
//		assertEquals("Segundo Contacto Guardado", c2.getContacto());
//		usuarioEJB.buscarTodos();
//		
//		logger.info("***** borrar debe eliminar el contacto existente "+c2);
//		usuarioEJB.borrar(c2);
//		c = usuarioEJB.buscarPorId(c2.getId());
//		assertNull(c);
//
//		logger.info("***** borrarPorId debe eliminar el contacto existente "+c1);
//		usuarioEJB.borrarPorId(c1.getId());
//		c = usuarioEJB.buscarPorId(c1.getId());
//		assertNull(c);
	}
}
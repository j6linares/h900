package net.indra.hal9000.h9cp.ejb;

import static org.junit.Assert.*;

import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import net.indra.hal9000.h9cp.PlantillaBaseWebTest;
import net.indra.hal9000.h9cp.model.Contacto;
import net.indra.hal9000.h9cp.util.RecursosHal9000;

@RunWith(Arquillian.class)
public class ContactoEJBTest extends PlantillaBaseWebTest {
	
	@Inject
	@RecursosHal9000
	private Logger logger;
	
	@EJB
	private ContactoEJB contactoEJB;
	
	@Test
	public void testContactoEJB() {
		Contacto c, c1, c2;
		
		logger.info("***** Iniciando test h9cp");
		logger.info("***** buscarTodos ");
		int n=contactoEJB.buscarTodos().size();
		
		c=new Contacto();
		c.setContacto("Primer Contacto");
		
		logger.info("***** Creando Contacto 1 "+c);
		c1=contactoEJB.crear(c);
		logger.info("***** Contacto 1 creado "+c+">"+c1);
		assertTrue(c1.getId() != null);
		
		logger.info("***** buscarTodos debe devolver uno mas q antes="+n);
		assertTrue (n+1==contactoEJB.buscarTodos().size());
		
		logger.info("***** actualizar debe modificar el contacto "+c1);
		c1.setContacto("Primer Contacto Actualizado");
		contactoEJB.actualizar(c1);
		c2 = contactoEJB.buscarPorId(c1.getId());
		logger.info("***** buscarPorId debe recuperar de bd el contacto actualizado "+c2);
		assertEquals("Primer Contacto Actualizado", c2.getContacto());
		
		logger.info("***** guardar debe modificar el contacto existente "+c2);
		c2.setContacto("Primer Contacto Guardado");
		contactoEJB.guardar(c2);
		c1 = contactoEJB.buscarPorId(c2.getId());
		logger.info("***** buscarPorId debe recuperar de bd el contacto guardado "+c1);
		assertEquals("Primer Contacto Guardado", c1.getContacto());
		
		logger.info("***** guardar debe crear el contacto no existente "+c);
		c=new Contacto();
		c.setContacto("Segundo Contacto Guardado");
		Response r=contactoEJB.guardar(c);
		c2=(Contacto) r.getEntity();
		assertEquals("Segundo Contacto Guardado", c2.getContacto());
		contactoEJB.buscarTodos();
		
		logger.info("***** borrar debe eliminar el contacto existente "+c2);
		contactoEJB.borrar(c2);
		c = contactoEJB.buscarPorId(c2.getId());
		assertNull(c);

		logger.info("***** borrarPorId debe eliminar el contacto existente "+c1);
		contactoEJB.borrarPorId(c1.getId());
		c = contactoEJB.buscarPorId(c1.getId());
		assertNull(c);
	}
}
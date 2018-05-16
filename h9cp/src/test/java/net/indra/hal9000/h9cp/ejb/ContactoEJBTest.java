package net.indra.hal9000.h9cp.ejb;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import net.indra.hal9000.PlantillaBaseWebTest;
import net.indra.hal9000.h9cp.model.Contacto;
import net.indra.hal9000.util.RecursosHal9000;

@RunWith(Arquillian.class)
public class ContactoEJBTest extends PlantillaBaseWebTest {
	
	@Inject
	@RecursosHal9000
	private Logger logger;
	
	@EJB
	private ContactoEJB contactoEJB;
	
	private Set<Contacto> contactos;
	private int nroContactos;
	private Contacto contacto, contactoDB;
	
	@BeforeClass 
	public static void antesClase() {
		System.out.println(">>>>> Inicio de pruebas!");
	}
	
	@Before
	public void antesTest() {
		logger.info(">>>>> Inicio de test "+this.getClass().getSimpleName());
		contacto=new Contacto();
		contactos = new HashSet<Contacto>();
		nroContactos = contactoEJB.buscarTodos().size();
	}

	@Test
	public void test() {
		logger.info("***** "+getNombreMetodo());
		assertTrue(true);
	}

	@After
	public void despuesTest() {
		logger.info(getNombreMetodo()+" "+contacto.toString());
		logger.info("contactos=");
		for (Contacto c : contactos) {
			logger.info(c.toString());
		}
		contacto=null;
		contactos=null;
		logger.info("<<<<< Fin de test "+this.getClass().getSimpleName());
	}

	@AfterClass 
	public static void despuesClase() {
		System.out.println("<<<<< Fin de pruebas!");
	}
	
	@Test
	public void testCrearContacto() throws ContactoException {
		logger.info("***** ini "+getNombreMetodo());

		contacto.setContacto("Nuevo Contacto");
		contactos.add(contactoEJB.crear(contacto));
		assertTrue(contacto.getId() != null);
		logger.info("***** Contacto creado!");
		
		logger.info("***** fin "+getNombreMetodo());
	}
	
	@Test(expected=ContactoException.class)
	public void testCrearContactoSinInfoExpected() throws ContactoException {
		logger.info("***** "+getNombreMetodo());
		contactoEJB.crear(contacto);
		fail("No deberia haber creado el contacto sin info!");
	}
	
	@Test
	public void testCrearContactoSinInfo() {
		logger.info("***** "+getNombreMetodo());
		try {
			contactoEJB.crear(contacto);
			fail("No deberia haber creado el contacto sin info!");
		} catch (ContactoException e) {
			assertTrue("Contacto sin Info!".equals(e.getMessage()));
		}
		
	}

	@Test
	public void testCrearContactoConID() throws ContactoException {
		logger.info("***** "+getNombreMetodo());
		contacto.setContacto("Contacto con ID");
		contactos.add(contactoEJB.crear(contacto));
		try {
			contactoEJB.crear(contacto);
			fail("No deberia haber creado el nuevo contacto con ID!");
		} catch (ContactoException e) {
			assertTrue("Contacto nuevo con Id!".equals(e.getMessage()));
		}
		
	}
	
	@Test
	public void testBorrarContactoSinID() {
		logger.info("***** ini "+getNombreMetodo());

		contacto.setContacto("Nuevo Contacto");
		try {
			contactoEJB.borrar(contacto);
			fail("No deberia haber borrado un contacto sin ID!");
		} catch (ContactoException e) {
			assertTrue("Contacto sin Id!".equals(e.getMessage()));
		}
		logger.info("***** fin "+getNombreMetodo());
	}
	
	@Test
	public void testBorrarContactoPorId() throws ContactoException {
		
		logger.info("***** ini "+getNombreMetodo());

		contacto.setContacto("Nuevo Contacto");
		contactoEJB.borrarPorId(contactoEJB.crear(contacto).getId());
		assertTrue(nroContactos == contactoEJB.buscarTodos().size());
		
		logger.info("***** fin "+getNombreMetodo());
	}

	@Test
	public void testBorrarContacto() throws ContactoException {
		logger.info("***** ini "+getNombreMetodo());

		contacto.setContacto("Nuevo Contacto");
		contactoEJB.borrar(contactoEJB.crear(contacto));
		assertTrue(nroContactos == contactoEJB.buscarTodos().size());
		assertTrue(contactoEJB.buscarPorId(contacto.getId()) == null);
		
		logger.info("***** fin "+getNombreMetodo());
	}
	
	@Test
	public void testBorrarContactoNoExiste() throws ContactoException {
		logger.info("***** ini "+getNombreMetodo());

		contacto.setContacto("Nuevo Contacto");
		contacto = contactoEJB.crear(contacto);
		contactoEJB.borrar(contacto);
		assertTrue(nroContactos == contactoEJB.buscarTodos().size());
		assertTrue(contactoEJB.buscarPorId(contacto.getId()) == null);
		contactoEJB.borrar(contacto);
		
		logger.info("***** fin "+getNombreMetodo());
	}
	
	@Test
	public void testBorrarContactoPorIdNoExiste() {
		logger.info("***** ini "+getNombreMetodo());

		contacto.setContacto("Nuevo Contacto");
		assertTrue(nroContactos == contactoEJB.buscarTodos().size());
		try {
			contacto = contactoEJB.crear(contacto);
			contactoEJB.borrarPorId(contacto.getId());
			contactoEJB.borrarPorId(contacto.getId());
			fail("No deberia borrar un contacto por Id q no existe!");
		} catch (ContactoException e) {
			assertTrue("Id de Contacto no existe en BD!".equals(e.getMessage()));
			
		}
		
		logger.info("***** fin "+getNombreMetodo());
	}

	@Test
	public void testBuscarPorId() throws ContactoException {
		logger.info("***** ini "+getNombreMetodo());

		contacto.setContacto("Nuevo Contacto");
		contactoDB = contactoEJB.buscarPorId(contactoEJB.crear(contacto).getId());
		assertTrue(contactoDB.getId() != null);
		
		logger.info("***** fin "+getNombreMetodo());
	}

	@Test
	public void testBuscarPorIdInexistente() throws ContactoException {
		logger.info("***** ini "+getNombreMetodo());

		contactoDB = contactoEJB.buscarPorId(0l);
		assertTrue(contactoDB == null);
		
		logger.info("***** fin "+getNombreMetodo());
	}
	
	@Test
	public void testGuardar() throws ContactoException {
		logger.info("***** ini "+getNombreMetodo());

		contacto.setContacto("Nuevo Contacto");
		Response response = contactoEJB.guardar(contacto);
		assertTrue(response.getEntity().equals(contacto));
		contacto.setContacto("Nuevo Contacto Actualizado");
		contactoEJB.guardar(contacto);
		assertTrue(response.getEntity().equals(contacto));
		contactoEJB.borrar(contacto);
		contactoEJB.guardar(contacto);
		assertTrue(response.getEntity().equals(contacto));
		
		logger.info("***** fin "+getNombreMetodo());
	}
//	public void testContactoEJB() {
//		int n=contactoEJB.buscarTodos().size();
//		c1=contactoEJB.crear(c);
//		assertTrue(c1.getId() != null);
//		logger.info("***** buscarTodos debe devolver uno mas q antes="+n);
//		assertTrue (n+1==contactoEJB.buscarTodos().size());
//		
//		logger.info("***** actualizar debe modificar el contacto "+c1);
//		c1.setContacto("Primer Contacto Actualizado");
//		contactoEJB.actualizar(c1);
//		c2 = contactoEJB.buscarPorId(c1.getId());
//		logger.info("***** buscarPorId debe recuperar de bd el contacto actualizado "+c2);
//		assertEquals("Primer Contacto Actualizado", c2.getContacto());
//		
//		logger.info("***** guardar debe modificar el contacto existente "+c2);
//		c2.setContacto("Primer Contacto Guardado");
//		contactoEJB.guardar(c2);
//		c1 = contactoEJB.buscarPorId(c2.getId());
//		logger.info("***** buscarPorId debe recuperar de bd el contacto guardado "+c1);
//		assertEquals("Primer Contacto Guardado", c1.getContacto());
//		
//		logger.info("***** guardar debe crear el contacto no existente "+c);
//		c=new Contacto();
//		c.setContacto("Segundo Contacto Guardado");
//		Response r=contactoEJB.guardar(c);
//		c2=(Contacto) r.getEntity();
//		assertEquals("Segundo Contacto Guardado", c2.getContacto());
//		contactoEJB.buscarTodos();
//		
//		logger.info("***** borrar debe eliminar el contacto existente "+c2);
//		contactoEJB.borrar(c2);
//		c = contactoEJB.buscarPorId(c2.getId());
//		assertNull(c);
//
//		logger.info("***** borrarPorId debe eliminar el contacto existente "+c1);
//		contactoEJB.borrarPorId(c1.getId());
//		c = contactoEJB.buscarPorId(c1.getId());
//		assertNull(c);
//	}
	
	public String getNombreMetodo(){
	      //Retorna el nombre del metodo desde el cual se hace el llamado
	      return new Exception().getStackTrace()[1].getMethodName();
   }

}
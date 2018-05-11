package net.indra.hal9000.h9cp.ejb;

import static org.junit.Assert.*;

import java.io.File;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import net.indra.hal9000.h9cp.model.Contacto;
import net.indra.hal9000.util.RecursosHal9000;

@RunWith(Arquillian.class)
public class EJBTest {
	
	@Inject
	@RecursosHal9000
	private Logger logger;
	
	@EJB
	private ContactoEJB contactoEJB;
	
	private static final String WEBAPP_SRC = "src/main/webapp";

	@Deployment
	public static WebArchive createDeployment() {
		return ShrinkWrap.create(WebArchive.class,"h9cptest.war")
					//.addClass(ContactoEJB.class)
					.addPackages(true, "net.indra.hal9000.h9cp")
					.addAsResource("META-INF/persistence.xml",
							"META-INF/persistence.xml")
					//.addAsManifestResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"))
					.addAsWebInfResource(
							new File(WEBAPP_SRC + "/WEB-INF", "beans.xml"))
					.addAsWebInfResource(EmptyAsset.INSTANCE, "faces-config.xml");
	}

	@Test
	public void testCrearContacto() throws ContactoException {
		logger.info("Iniciando test h9cp");
		Contacto c=new Contacto();
		c.setContacto("Nombre Apellido");
		logger.info("Creando Contacto "+c);
		c=contactoEJB.crear(c);
		logger.info("Contacto creado "+c);
		assertTrue(c.getId() != null);
//		assertTrue(true);
	}
}
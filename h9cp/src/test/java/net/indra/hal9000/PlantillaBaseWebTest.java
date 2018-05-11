package net.indra.hal9000;

import java.io.File;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class PlantillaBaseWebTest {
	
	private static final String WEBAPP_SRC = "src/main/webapp";

	@Deployment
	public static WebArchive createDeployment() {
		return ShrinkWrap.create(WebArchive.class,"hal9000test.war")
					//.addClass(ContactoEJB.class)
					.addPackages(true, "net.indra.hal9000")
					.addAsResource("META-INF/persistence.xml",
							"META-INF/persistence.xml")
					//.addAsManifestResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"))
					.addAsWebInfResource(
							new File(WEBAPP_SRC + "/WEB-INF", "beans.xml"))
					.addAsWebInfResource(EmptyAsset.INSTANCE, "faces-config.xml");
	}

}
package net.indra.hal9000.util;

import java.util.logging.Logger;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class Recursos {
	
	@PersistenceContext(unitName="hal9000")
	@Produces
	@RecursosHal9000
	private EntityManager entityManager;
	
//	@Resource
//	UserTransaction tx;

	@Produces
	@RecursosHal9000
	public Logger produceLogger(InjectionPoint injectionPoint) {
		return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
	}

}

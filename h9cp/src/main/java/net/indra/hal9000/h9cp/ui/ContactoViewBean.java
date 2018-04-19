package net.indra.hal9000.h9cp.ui;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;

import net.indra.hal9000.h9cp.ejb.AnuncioEJB;
import net.indra.hal9000.h9cp.model.Anuncio;
import net.indra.hal9000.h9cp.model.Contacto;
import net.indra.hal9000.h9cp.util.RecursosHal9000;

@Named("contactoView")
@Stateless
@RequestScoped
public class ContactoViewBean {
	
	private Set<Contacto> contactos;
	
	private Contacto currentContacto;
	
	private Set<Anuncio> anuncios;
	
	@RecursosHal9000
	@Inject
	private Logger log;
	
	@Inject
	private AnuncioEJB anuncioEJB;
	
	@PostConstruct
	public void init() {
		log.info("INICIANDO contactoView!!!");
		contactos=anuncioEJB.getAllAnunciosContacto();
	}
	
	public void refrescar() {
		log.info("REFRESCAR contactoView!!!");
		contactos=anuncioEJB.getAllAnunciosContacto();
	}
	
	public void update(ValueChangeEvent event) {
		
		Contacto contacto = (Contacto) event.getNewValue();
		anuncios = new HashSet<Anuncio>(contacto.getAnuncios());
//		anuncios = anuncioEJB.getAnuncios(contacto);		
		
	}

	public Set<Contacto> getContactos() {
		return contactos;
	}

	public void setContactos(Set<Contacto> contactos) {
		this.contactos = contactos;
	}

	public Contacto getCurrentContacto() {
		return currentContacto;
	}

	public void setCurrentContacto(Contacto currentContacto) {
		this.currentContacto = currentContacto;
	}

	public Set<Anuncio> getAnuncios() {
		return anuncios;
	}

	public void setAnuncios(Set<Anuncio> anuncios) {
		this.anuncios = anuncios;
	}

	public List<Anuncio> getAnuncioList() {
		if (anuncios!=null) {
			return new ArrayList<Anuncio>(anuncios);
		} else {
			return new ArrayList<Anuncio>();
		}
	}
}

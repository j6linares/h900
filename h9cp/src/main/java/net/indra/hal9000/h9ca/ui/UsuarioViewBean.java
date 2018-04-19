package net.indra.hal9000.h9ca.ui;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;

import net.indra.hal9000.h9ca.ejb.UsuarioEJB;
import net.indra.hal9000.h9ca.model.Perfil;
import net.indra.hal9000.h9ca.model.Usuario;
import net.indra.hal9000.h9cp.util.RecursosHal9000;

@Named("usuarioView")
@ApplicationScoped
public class UsuarioViewBean {
	
	private Set<Usuario> usuarios;
	
	private Usuario currentUsuario;
	private Perfil currentPerfil;
	
	private Set<Perfil> perfiles;
	
	@RecursosHal9000
	@Inject
	private Logger log;
	
	@Inject
	private UsuarioEJB usuarioEJB;
	
	@PostConstruct
	public void init() {
		log.info("INICIANDO usuarioView!!!");
		usuarios=usuarioEJB.getAllUsuariosPerfil();
	}
	
	public void refrescar() {
		log.info("REFRESCAR usuarioView!!!");
		usuarios=usuarioEJB.getAllUsuariosPerfil();
		perfiles=usuarioEJB.getAllPerfilesUsuario();
	}
	
	public void update(ValueChangeEvent event) {
		
		Usuario usuario = (Usuario) event.getNewValue();
		perfiles = new HashSet<Perfil>(usuario.getPerfiles());
//		perfiles = usuarioEJB.getPerfiles(usuario);		
		
	}

	public void updatePerfil(ValueChangeEvent event) {
		
		Perfil perfil = (Perfil) event.getNewValue();
		usuarios = new HashSet<Usuario>(perfil.getUsuarios());
//		usuarios = usuarioEJB.getUsuarios(perfil);		
		
	}

	public Set<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Set<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Usuario getCurrentUsuario() {
		return currentUsuario;
	}

	public void setCurrentUsuario(Usuario currentUsuario) {
		this.currentUsuario = currentUsuario;
	}
	
	public Perfil getCurrentPerfil() {
		return currentPerfil;
	}

	public void setCurrentPerfil(Perfil currentPerfil) {
		this.currentPerfil = currentPerfil;
	}

	public Set<Perfil> getPerfiles() {
		return perfiles;
	}

	public void setPerfiles(Set<Perfil> perfiles) {
		this.perfiles = perfiles;
	}

	public List<Perfil> getPerfilesList() {
		if (perfiles!=null) {
			return new ArrayList<Perfil>(perfiles);
		} else {
			return new ArrayList<Perfil>();
		}
	}
	
	public List<Usuario> getUsuariosList() {
		if (usuarios!=null) {
			return new ArrayList<Usuario>(usuarios);
		} else {
			return new ArrayList<Usuario>();
		}
	}
}

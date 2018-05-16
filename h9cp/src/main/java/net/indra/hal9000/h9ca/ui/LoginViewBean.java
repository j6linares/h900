package net.indra.hal9000.h9ca.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import net.indra.hal9000.h9ca.ejb.UsuarioEJB;
import net.indra.hal9000.h9ca.model.Perfil;
import net.indra.hal9000.h9ca.model.Usuario;
import net.indra.hal9000.util.RecursosHal9000;

import java.io.Serializable;

@Named("loginView")
@SessionScoped
public class LoginViewBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String usuario;
	private String userid;
	private String mensaje;
	private int intentosLogin=0;
	
	private Usuario currentUsuario;
	private Perfil currentPerfil;
	
	@RecursosHal9000
	@Inject
	private Logger log;
	
	@Inject
	private UsuarioEJB usuarioEJB;
	
	@PostConstruct
	public void init() {
		log.info("INICIANDO loginView!!!");
	}
	
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getUserid() {
		return userid;
	}


	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getMensaje() {
		return mensaje;
	}
	
	public int getIntentosLogin() {
		return intentosLogin;
	}

	public void setIntentosLogin(int intentosLogin) {
		this.intentosLogin = intentosLogin;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Usuario getCurrentUsuario() {
		return currentUsuario;
	}

	public void setCurrentUsuario(Usuario currentUsuario) {
		this.currentUsuario = currentUsuario;
	}
	
	public Perfil getCurrentPerfil() {
		return this.currentPerfil;
	}

	public void setCurrentPerfil(Perfil currentPerfil) {
		this.currentPerfil = currentPerfil;
	}

	public Perfil getPerfilPorDefecto(Usuario u) {
		//obtiene el perfil por defecto del usuario, el de ID más bajo (maxima autorización)
		Perfil p, pDefecto = null;
		Long id=null;
		for (Iterator<Perfil> iterator = u.getPerfiles().iterator(); iterator.hasNext();) {
			p = (Perfil) iterator.next();
			if (id==null) {
				id=p.getId();
				pDefecto=p;
			} else {
				if (p.getId()<id) {
					id=p.getId();
					pDefecto=p;
				}
			}
		} 
		return pDefecto;
	}

	public Set<Perfil> getPerfilesCurrentUsuario() {
		return this.currentUsuario.getPerfiles();
	}

	public List<Perfil> getPerfilesListCurrentUsuario() {
		if (this.currentUsuario.getPerfiles()!=null) {
			return new ArrayList<Perfil>(this.currentUsuario.getPerfiles());
		} else {
			return new ArrayList<Perfil>();
		}
	}
	
	public String login() {
		this.intentosLogin=+1;
		log.info("login("+usuario+","+userid+") Intento="+this.intentosLogin);
		String vista = null;
		this.currentUsuario = usuarioEJB.buscarPorUsuario(usuario);
		log.info("usuario:"+this.currentUsuario);
		if (this.currentUsuario != null) {
			log.info("Perfiles de usuario:"+this.currentUsuario.getPerfiles());
			this.currentPerfil = this.currentUsuario.getPerfilPorDefecto();
			log.info("perfil por defecto:"+this.currentPerfil);
			if (this.currentUsuario.getUserid().equals(userid)) {
				log.info("welcome");
				vista="welcome";
				this.mensaje="Acceso garantizado!";
			} else {
				this.mensaje = "Error de acceso, userid no coincide!";
			}
		} else {
			this.mensaje = "Error de acceso, usuario no existe!";
		}
		log.info(this.mensaje);
		FacesContext.getCurrentInstance().addMessage(null
				, new FacesMessage(this.mensaje, null));
		return vista;
	}
	
	public String identificacionUsuario() {
		return usuarioEJB.identificacionUsuario(currentUsuario); 
	}
	
	
}

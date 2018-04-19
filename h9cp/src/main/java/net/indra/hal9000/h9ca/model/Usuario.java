package net.indra.hal9000.h9ca.model;

import java.util.Iterator;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="tbh9caus")
@NamedQueries({
	@NamedQuery(name="buscarUsuarioPorUsuario",query="SELECT u FROM Usuario u WHERE u.usuario = :uusuario")
,	@NamedQuery(name="buscarUsuarioPorUserid",query="SELECT u FROM Usuario u WHERE u.userid = :uuserid")
})

public class Usuario {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="h9caus_id")
	private Long id;
	
	@NotNull
	@Size(max=10)
	@Column(name="h9caus_usuario")
	private String usuario;
	
	@Column(name="h9caus_userid",nullable=true)
	@Size(max=8)
	private String userid;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="tbh9caup"
			, joinColumns=@JoinColumn(name="h9caup_usuarioID")
			, inverseJoinColumns=@JoinColumn(name="h9caup_perfilID")
			)
	private Set<Perfil> perfiles;
	
	public Perfil getPerfilPorDefecto() {
		//obtiene el perfil por defecto del usuario, el de ID más bajo (maxima autorización)
		Perfil p, pDefecto = null;
		Long id=null;
		for (Iterator<Perfil> iterator = this.perfiles.iterator(); iterator.hasNext();) {
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
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	
	public Set<Perfil> getPerfiles() {
		return perfiles;
	}
	public void setPerfiles(Set<Perfil> perfiles) {
		this.perfiles = perfiles;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Usuario [id=" + id + ", usuario=" + usuario+", userid=" + userid+"]";
	}

}

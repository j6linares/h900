package net.indra.hal9000.h9cp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Table(name="tbh9cpan")
@NamedQueries({
	@NamedQuery(name="buscarAnuncioPorReferencia",query="SELECT a FROM Anuncio a WHERE a.referencia = :areferencia")
,	@NamedQuery(name="buscarAnuncioPorContieneObjeto",query="SELECT a FROM Anuncio a WHERE a.objeto like :aobjeto")
})
public class Anuncio {
	
	@Id
	@Column(name="h9cpan_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="h9cpan_referencia")
	@NotNull(message="referencia no informada")
	@Pattern(regexp = "[G][0-9]{4,}")
	private String referencia;
	
	@Column(name="h9cpan_objeto")
	@NotNull(message="Objeto no informado")
	private String objeto;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="h9cpan_contactoID")
	private Contacto contacto;
	
	@OneToOne(optional=true)
	@JoinColumn(name="h9cpan_adjudicacionID")
	private Adjudicacion adjudicacion;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getReferencia() {
		return referencia;
	}
	public void setReferencia(String name) {
		this.referencia = name;
	}
	
	public String getObjeto() {
		return objeto;
	}
	public void setObjeto(String objeto) {
		this.objeto = objeto;
	}
	
	public Contacto getContacto() {
		return contacto;
	}
	public void setContacto(Contacto contacto) {
		this.contacto = contacto;
	}
	public Adjudicacion getAdjudicacion() {
		return adjudicacion;
	}
	public void setAdjudicacion(Adjudicacion adjudicacion) {
		this.adjudicacion = adjudicacion;
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
		Anuncio other = (Anuncio) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Anuncio [id=" + id + ", referencia=" + referencia +"]";
	}
}

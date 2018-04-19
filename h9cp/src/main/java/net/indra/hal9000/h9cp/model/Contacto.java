package net.indra.hal9000.h9cp.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Table(name="tbh9cpco")
@NamedQueries({
	@NamedQuery(name="buscarConctactoPorNombre",query="SELECT c FROM Contacto c WHERE c.contacto = :cconctacto")
,	@NamedQuery(name="buscarConctactoPorContieneNombre",query="SELECT c FROM Contacto c WHERE c.contacto like :cconctacto")
})
public class Contacto {
	
	@Id
	@Column(name="h9cpco_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="h9cpco_contacto")
	@NotNull(message="Contacto no informado")
	private String contacto;
	
	@OneToMany(mappedBy="contacto",fetch=FetchType.LAZY)
	private Set<Anuncio> anuncios;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getContacto() {
		return contacto;
	}
	public void setContacto(String name) {
		this.contacto = name;
	}
	
	public Set<Anuncio> getAnuncios() {
		return anuncios;
	}
	public void setAnuncios(Set<Anuncio> anuncios) {
		this.anuncios = anuncios;
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
		Contacto other = (Contacto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Contacto [id=" + id + ", contacto=" + contacto +"]";
	}
}

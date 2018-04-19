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
@Table(name="tbh9cppr")
@NamedQueries({
	@NamedQuery(name="buscarProveedorPorNombre",query="SELECT p FROM Proveedor p WHERE p.proveedor = :pproveedor")
,	@NamedQuery(name="buscarProveedorPorContieneNombre",query="SELECT p FROM Proveedor p WHERE p.proveedor like :pproveedor")
})
public class Proveedor {
	
	@Id
	@Column(name="h9cppr_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="h9cppr_proveedor")
	@NotNull(message="Proveedor no informado")
	private String proveedor;
	
	@OneToMany(mappedBy="proveedor",fetch=FetchType.LAZY)
	private Set<Adjudicacion> adjudicaciones;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProveedor() {
		return proveedor;
	}
	public void setProveedor(String name) {
		this.proveedor = name;
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
		Proveedor other = (Proveedor) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Proveedor [id=" + id + ", proveedor=" + proveedor +"]";
	}
}

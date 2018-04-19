package net.indra.hal9000.h9cp.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Table(name="tbh9cpad")
@NamedQueries({
	@NamedQuery(name="buscarAdjudicacionPorFecha",query="SELECT a FROM Adjudicacion a WHERE a.fecha = :afecha")
,	@NamedQuery(name="buscarAdjudicacionPorPeriodoFechas",query="SELECT a FROM Adjudicacion a WHERE a.fecha between :afdesde and :afhasta")
})
public class Adjudicacion {
	
	@Id
	@Column(name="h9cpad_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="h9cpad_fecha")
	@NotNull(message="Fecha no informada")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;
	
	@Column(name="h9cpad_importe")
	@NotNull(message="Importe no informado")
	@Digits(integer=7,fraction=2)
	private float importe;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="h9cpad_proveedorID")
	private Proveedor proveedor;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public float getImporte() {
		return importe;
	}
	public void setImporte(float importe) {
		this.importe = importe;
	}
	public Proveedor getProveedor() {
		return proveedor;
	}
	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
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
		Adjudicacion other = (Adjudicacion) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Adjudicacion [id=" + id + ", fecha=" + fecha +"]";
	}
}

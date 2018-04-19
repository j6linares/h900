package net.indra.hal9000.h9cp.ui;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import net.indra.hal9000.h9cp.ejb.ContactoEJB;
import net.indra.hal9000.h9cp.model.Contacto;

@RequestScoped
@Named("contactobean")
public class ContactoBean {
	private String nombre;
	private Long id;

	@Inject
	private ContactoEJB contactoEJB;

	public void guardar() {
		try {
			Contacto c=new Contacto();
			if (nombre == null || "".equals(nombre)) {
				nombre=null;
			}
			c.setContacto(nombre);
			String response = contactoEJB.crear(c).toString();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(response));
		} catch (Exception e) {
			System.out.println(e.getCause());
			if (e.getCause() != null && e.getCause() instanceof ConstraintViolationException) {
				ConstraintViolationException ex = (ConstraintViolationException) e.getCause();
				String violations = "";
				for (ConstraintViolation<?> cv : ex.getConstraintViolations()) {
					violations += cv.getMessage()+"\n";
					System.out.println("Violaciones de restricciones de entidad: "+violations);
				}
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(violations));
			}
		}
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String name) {
		this.nombre = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	public void getContacto() {
		try {
			String response = contactoEJB.buscarPorId(id).toString();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(response));
		} catch (Exception e) {
			System.out.println(e.getCause());
			if (e.getCause() != null && e.getCause() instanceof ConstraintViolationException) {
				ConstraintViolationException ex = (ConstraintViolationException) e.getCause();
				String violations = "";
				for (ConstraintViolation<?> cv : ex.getConstraintViolations()) {
					violations += cv.getMessage()+"\n";
					System.out.println("Violations: "+violations);
				}
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(violations));
			}
		}
	}
	
	public List<Contacto> getContactos() {
		return contactoEJB.buscarTodos();
	}

}

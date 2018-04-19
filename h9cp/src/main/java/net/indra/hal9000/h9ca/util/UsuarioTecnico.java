package net.indra.hal9000.h9ca.util;

import net.indra.hal9000.h9ca.model.Usuario;

public class UsuarioTecnico implements UsuarioUtil {

	public String identificacionUsuario(Usuario usuario) {
		String pe=usuario.getPerfilPorDefecto().getPerfil().trim();
		if (pe == null || "".equals(pe)) {
			;
		} else {
			pe = "Perfil=["+pe+"]";
		}

		String id=usuario.getUserid().trim();
		if (id == null || "".equals(id)) {
			id = "Empleado"+usuario.getUsuario()+" sin técnico!";
		} else {
			id = "Técnico: "+usuario.getUserid()+" Empleado ("+usuario.getUsuario()+")";
		}

		return id+" "+pe;
	}

}

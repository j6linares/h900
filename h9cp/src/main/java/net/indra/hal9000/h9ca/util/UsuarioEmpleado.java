package net.indra.hal9000.h9ca.util;

import net.indra.hal9000.h9ca.model.Usuario;

@Empleado
public class UsuarioEmpleado implements UsuarioUtil {

	public String identificacionUsuario(Usuario usuario) {
		String pe=usuario.getPerfilPorDefecto().getPerfil().trim();
		if (pe == null || "".equals(pe)) {
			;
		} else {
			pe = "Perfil=["+pe+"]";
		}

		String id=usuario.getUserid().trim();
		if (id == null || "".equals(id)) {
			id = usuario.getUsuario()+ " sin técnico!";
		} else {
			id = usuario.getUsuario()+" Técnico ("+usuario.getUserid()+")";
		}

		return "Empleado: "+id+" "+pe;
	}

}

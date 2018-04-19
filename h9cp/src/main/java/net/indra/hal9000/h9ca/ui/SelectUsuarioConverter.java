package net.indra.hal9000.h9ca.ui;

import java.util.Set;
import java.util.function.Predicate;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import net.indra.hal9000.h9ca.model.Usuario;

@FacesConverter(value="SelectUsuarioToEntityConverter")
public class SelectUsuarioConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext ctx, UIComponent comp, String value) {
		Object o = null;
		if (!(value == null || value.isEmpty())) {
			o = this.getSelectedItemAsEntity(comp,value);
		}
		return o;
	}

	private Usuario getSelectedItemAsEntity(UIComponent comp, String value) {
		Usuario item = null;
		
		Set<Usuario> selectItems = null;
		for (UIComponent uic : comp.getChildren()) {
			if (uic instanceof UISelectItems) {
				Long itemId = Long.valueOf(value);
				selectItems = (Set<Usuario>) ((UISelectItems) uic).getValue();
				if (itemId != null && selectItems != null && !selectItems.isEmpty()) {
					Predicate<Usuario> predicate = i -> i.getId().equals(itemId);
					item = selectItems.stream().filter(predicate).findFirst().orElse(null);
				}
			}
		}
		
		return item;
	}

	@Override
	public String getAsString(FacesContext ctx, UIComponent comp, Object value) {
		String s = "";
		if (value != null) {
			s = ((Usuario) value).getId().toString();
		}
		return s;
	}

}

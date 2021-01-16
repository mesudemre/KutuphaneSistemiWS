package com.mesutemre.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.mesutemre.parametre.model.IlgiAlanlariParametreModel;

/**
 * @author mesutemre.celenk
 * <br>
 * Validasyon genelleştirildiği için bu tip classlara ihtiyaç duymuyoruz...
 *
 */
@Deprecated
public class IlgiAlanlariValidator implements Validator {

	@Override
	public boolean supports(Class<?> obj) {
		return IlgiAlanlariParametreModel.class.equals(obj);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		
	}

	
	
}

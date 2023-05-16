package validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("validatorY")
public class ValidatorY implements Validator {
    public static final double MAX_Y = 5d;
    public static final double MIN_Y = 3d;

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        if (o == null) {
            throw new ValidatorException(
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, null,
                            "Y can't be empty"));
        }
        double yValue = Double.parseDouble(String.valueOf(o));
        if (yValue < MIN_Y || yValue > MAX_Y) throw new ValidatorException(
                new FacesMessage(FacesMessage.SEVERITY_ERROR, null,
                        String.format("Y must be in interval of [%s ... %s].", MIN_Y, MAX_Y))
        );
    }
}
package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

@ManagedBean(name = "localeData", eager = true)
@SessionScoped
public class LocaleData {
    private static final long serialVersionUID = 1L;
    private String locale;

    private static Map<String, Locale> countries;

    static {
        countries = new LinkedHashMap<>();
        countries.put("English (UK)", Locale.forLanguageTag("en-UK"));
        countries.put("English (US)", Locale.forLanguageTag("en-US"));
        countries.put("Русский", Locale.forLanguageTag("ru-RU"));
    }

    public Map<String, Locale> getCountries() {
        return countries;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public void localeChanged(ValueChangeEvent e) {
        String newLocaleValue = e.getNewValue().toString();

        for (Map.Entry<String, Locale> entry : countries.entrySet()) {
            if (entry.getValue().toString().equals(newLocaleValue)) {
                FacesContext.getCurrentInstance()
                        .getViewRoot().setLocale(entry.getValue());
            }
        }
    }

    public DateFormat dateFormat() {
        return new SimpleDateFormat("EEEE dd MMM yyyy HH:mm:ss", FacesContext.getCurrentInstance().getViewRoot().getLocale());
    }
}

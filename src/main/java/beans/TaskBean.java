package beans;


import com.google.common.collect.Lists;
import database.ResultDao;
import org.primefaces.PrimeFaces;

import javax.annotation.PostConstruct;
import javax.faces.annotation.ApplicationMap;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@ManagedBean(name = "task", eager = true)
@SessionScoped
public class TaskBean {
    private List<ResultBean> resultList;

    @Inject
    private ResultDao resultDao;

    private static final Set<Double> xValues;
    private static final Double maxY;
    private static final Double minY;
    private static final Set<Integer> rValues;

    private final Map<Double, Boolean> xMap;
    private final Map<Integer, Boolean> rMap;

    private ResultBean enteringBean;

    static {
        xValues = new LinkedHashSet<>(Arrays.asList(-2D, -1.5D, -1D, -0.5D, 0D, 0.5D, 1D, 1.5D, 2D));
        rValues = new LinkedHashSet<>(Arrays.asList(1, 2, 3, 4, 5));
        maxY = 5D; minY = 3D;
    }

    {
        enteringBean = new ResultBean();
        xMap = xValues.stream().collect(Collectors.toMap(Function.identity(), x -> Boolean.FALSE));
        rMap = rValues.stream().collect(Collectors.toMap(Function.identity(), x -> Boolean.FALSE));
    }

    public Collection<Double> availableXValues() {
        return xValues;
    }

    public Collection<Integer> availableRValues() {
        return rValues;
    }

    public Map<Double, Boolean> getXMap() {
        return xMap;
    }

    public Map<Integer, Boolean> getRMap() {
        return rMap;
    }

    public void xChanged(AjaxBehaviorEvent e) {
        Double target = Double.valueOf((String) e.getComponent().getAttributes().get("itemLabel"));
        xMap.replaceAll((key, val) -> key.equals(target));
        enteringBean.setX(target);
        PrimeFaces.current().ajax().update("my-form");
    }

    public void yChanged(ValueChangeEvent e) {
        enteringBean.setY((Double) e.getNewValue());
    }

    public void rChanged(Integer newR) {
        rMap.replaceAll((key, val) -> key.equals(newR));
        enteringBean.setR(newR.doubleValue());
        PrimeFaces.current().ajax().addCallbackParam("r", newR);
        PrimeFaces.current().ajax().update("my-form");
    }

    @PostConstruct
    private void initialize() {
        enteringBean = new ResultBean();
        updateLocal();
    }

    private void updateLocal() {
        resultList = resultDao.getAll();
    }

    public void addGraphResult() {
        ResultBean copyResult = new ResultBean();
        Double x = Double.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("x"));
        Double y = Double.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("y"));
        copyResult.setY(y);
        copyResult.setX(x);
        copyResult.setR(enteringBean.getR());
        resultDao.save(copyResult);
        updateLocal();
    }

    public void addResult() {
        ResultBean copyResult = new ResultBean(enteringBean);
        resultDao.save(copyResult);
        updateLocal();
    }

    public void clearResults() {
        resultDao.clear();
        resultList = resultDao.getAll();
        updateLocal();
    }

    public static Double getMaxY() {
        return maxY;
    }

    public static Double getMinY() {
        return minY;
    }

    public ResultBean getEnteringBean() {
        return enteringBean;
    }

    public void setEnteringBean(ResultBean enteringBean) {
        this.enteringBean = enteringBean;
    }

    public List<ResultBean> getResultList() {
        return resultList;
    }

    public void setResultList(List<ResultBean> resultList) {
        this.resultList = resultList;
    }
}

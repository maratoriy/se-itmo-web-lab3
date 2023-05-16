package beans;


import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@ManagedBean(name = "watchMaker", eager = true)
@ApplicationScoped
public class WatchMaker implements Serializable {
    private Date time;

    public WatchMaker() {
        this.time = new Date();
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void updateTime() {
        time = new Date();
    }
}
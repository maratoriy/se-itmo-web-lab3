package beans;

import java.io.Serializable;
import javax.persistence.*;

@Entity
public class ResultBean implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "x", nullable = false)
    private Double x;
    @Column(name = "y", nullable = false)
    private Double y;
    @Column(name = "r", nullable = false)
    private Double r;
    @Column(name = "hit", nullable = false)
    private Boolean hit;

    public ResultBean(ResultBean from) {
        this.id = from.id;
        this.x = from.getX();
        this.y = from.getY();
        this.r = from.getR();
        this.hit = isHit();
    }

    public ResultBean() {
    }

    private boolean isHit() {
        boolean first = (y < (-x + r)) && (x>=0) && (y>=0);
        boolean second = (x>=-r) && (y<=r/2) && (x<=0) && (y>=0);
        boolean third = (x*x+y*y <= r/2) && (x<=0) && (y<=0);
        return first || second || third;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getR() {
        return r;
    }

    public void setR(Double r) {
        this.r = r;
    }

    public Boolean getHit() {
        return hit;
    }

    public void setHit(Boolean hit) {
        this.hit = hit;
    }


}
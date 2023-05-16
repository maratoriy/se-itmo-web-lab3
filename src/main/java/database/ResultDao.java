package database;

import beans.ResultBean;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.enterprise.inject.Default;

@Default
public class ResultDao  {
    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ResultUnit");
    private final EntityManager entityManager = entityManagerFactory.createEntityManager();

    public void save(ResultBean result) {
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.persist(result);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }

    public boolean clear() {
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.createQuery("delete from ResultBean").executeUpdate();
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            return false;
        }
    }

    public List<ResultBean> getAll() {
        List<ResultBean> resultBeanList;

        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            resultBeanList = entityManager.createQuery("select result from ResultBean result ORDER BY id", ResultBean.class).getResultList();
            transaction.commit();
            return resultBeanList;
        } catch (Exception e) {
            transaction.rollback();
            return new ArrayList<>();
        }
    }


}
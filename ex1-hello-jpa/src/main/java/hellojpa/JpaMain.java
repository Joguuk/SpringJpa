package hellojpa;

import sun.rmi.runtime.Log;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        // EntityManagerfactory는 어플리케이션 로딩 시점에 하나만 만들어야 함
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        // 트랜잭션 단위(DB 커낵션을 얻고 등등..)는 EntityManager가 수행
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {


            tx.commit();
        } catch ( Exception e ) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();

    }
}

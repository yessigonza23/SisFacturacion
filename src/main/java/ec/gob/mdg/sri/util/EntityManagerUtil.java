package ec.gob.mdg.sri.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerUtil {
    
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("finanPU");
    private static EntityManager em;
    
    
    public static EntityManager getEntityManager(){        
        if(em == null){
            em = emf.createEntityManager();        
        }
        return em;
    }
}
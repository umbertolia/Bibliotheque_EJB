package utils;

import org.hibernate.SessionFactory;
import org.hibernate.metamodel.Metadata;
import org.hibernate.metamodel.MetadataBuilder;
import org.hibernate.metamodel.MetadataSources;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class HibernateUtil {
	 
	private static ServiceRegistry registry;
   private static SessionFactory sessionFactory;
   public static SessionFactory getSessionFactory() {
   	

      /* if (sessionFactory == null) {
           try {
               // Create registry
               registry = new StandardServiceRegistryBuilder().configure().build();
               // Create MetadataSources
               MetadataSources sources = new MetadataSources(registry);
               // Create Metadata
               MetadataBuilder mdb = sources.getMetadataBuilder();
               Metadata metadata = mdb.build();
               // Create SessionFactory
               sessionFactory = metadata.getSessionFactoryBuilder().build();
           } catch (Exception e) {
               e.printStackTrace();
               if (registry != null) {
                   StandardServiceRegistryBuilder.destroy(registry);
               }
           }
       }*/
   	/*registry = new ServiceRegistryBuilder().configure().buildServiceRegistry();
   	MetadataSources metadataSources = new MetadataSources(registry);
   	SessionFactory factory = metadataSources.buildMetadata().buildSessionFactory();*/
   	
   	
   	
   	
       return sessionFactory;
   }
   public static void shutdown() {
       if (registry != null) {
           ServiceRegistryBuilder.destroy(registry);
       }
   }
}

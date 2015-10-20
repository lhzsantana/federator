package rendezvous.federator.entityManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import rendezvous.federator.core.Access;
import rendezvous.federator.core.Entity;

public class EntityManager {

	private final static Logger logger = Logger.getLogger(EntityManager.class);
	
	private static Map<String,List<Access>> entityList = new HashMap<String,List<Access>>();
	
	private static Map<Entity,String> mappings = new HashMap<Entity,String>();
	
	public static void addEntity(String id, List<Access> accessed){
		EntityManager.entityList.put(id, accessed);
	}
	
	
	
	public static  List<Access> getEntity(String id){
		
		List<Access> accesses = EntityManager.entityList.get(id);

		logger.info("The entity <"+id+"> was found");
						
		return accesses;
	}

	public static void deleteEntity(String id) {
		
		EntityManager.entityList.remove(id);	
		
		logger.debug("The entity <"+id+"> was removed");		
	}
}

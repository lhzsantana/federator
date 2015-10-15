package rendezvous.federator.entityManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import rendezvous.federator.planner.Access;

public class EntityManager {

	private final static Logger logger = Logger.getLogger(EntityManager.class);
	
	private static Map<String,List<Access>> entityList = new HashMap<String,List<Access>>();
	
	public static void addEntity(String id, List<Access> accessed){
		EntityManager.entityList.put(id, accessed);
	}
	
	public static  List<Access> getEntity(String id){
		
		List<Access> accesses = EntityManager.entityList.get(id);

		logger.debug("The entity <"+id+"> was found");
				
		return accesses;
	}

	public static void deleteEntity(String id) {
		
		EntityManager.entityList.remove(id);	
		
		logger.debug("The entity <"+id+"> was removed");		
	}
}
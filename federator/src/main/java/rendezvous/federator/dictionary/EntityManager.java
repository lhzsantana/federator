package rendezvous.federator.dictionary;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityManager {

	private static Map<String,List<String>> entityList = new HashMap<String,List<String>>();
	
	public static void addEntity(String id, List<String> entityIds){
		EntityManager.entityList.put(id, entityIds);
	}
		
}

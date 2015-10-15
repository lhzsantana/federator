package rendezvous.federator.dictionary;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rendezvous.federator.planner.Access;

public class EntityManager {

	private static Map<String,List<Access>> entityList = new HashMap<String,List<Access>>();
	
	public static void addEntity(String id, List<Access> accessed){
		EntityManager.entityList.put(id, accessed);
	}
	public static  List<Access> getEntity(String id){
		return EntityManager.entityList.get(id);
	}
}

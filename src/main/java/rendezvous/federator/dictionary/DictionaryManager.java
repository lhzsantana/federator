package rendezvous.federator.dictionary;

import java.io.File;
import java.util.Map;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;

public class DictionaryManager {

	public static Map<Integer, Mapping> mappings = null;

	public DictionaryManager() {

		if (mappings == null) {
			DB db = DBMaker.appendFileDB(new File("/data/mappings.data"))
					.encryptionEnable("password")
					.make();

			mappings = db
					.treeMapCreate("map")
					.keySerializer(Serializer.INTEGER)
					.valueSerializer(Serializer.JAVA)
					.makeOrGet();
		}
	}

	public static Mapping getMapping(Integer hash) {
		return mappings.get(hash);
	}

	public static void addMapping(Mapping mapping) {
		mappings.put(mapping.hashCode(), mapping);
	}

	public static boolean containsMapping(Integer hash) {
		return mappings.containsKey(hash);
	}
}

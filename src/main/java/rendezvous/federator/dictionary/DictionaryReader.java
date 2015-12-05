package rendezvous.federator.dictionary;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import rendezvous.federator.core.Entity;
import rendezvous.federator.core.Field;
import rendezvous.federator.core.Type;
import rendezvous.federator.datasources.Datasource;

public interface DictionaryReader {

	public static Map<Entity, Map<Field, Set<Datasource>>> dictionaryEntityFieldDatasources = new HashMap<Entity, Map<Field, Set<Datasource>>>();
	public static Map<Entity, Map<Datasource, Set<Field>>> dictionaryEntityDatasourceFields = new HashMap<Entity, Map<Datasource, Set<Field>>>();
	public static Map<Entity, Map<Field, Type>> dictionaryEntityFieldType = new HashMap<Entity, Map<Field, Type>>();

	public Map<Entity, Map<Datasource, Set<Field>>> refreshDictionary(String newMapping) throws Exception;

}

package rendezvous.federator.dictionary.impl;

import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import rendezvous.federator.api.endpoint.impl.MappingEndpoint;
import rendezvous.federator.core.Entity;
import rendezvous.federator.core.Field;
import rendezvous.federator.datasources.Datasource;
import rendezvous.federator.datasources.RelationshipManager;
import rendezvous.federator.datasources.column.DatasourceColumn;
import rendezvous.federator.datasources.document.DatasourceDocument;

public class DataCreator {

	final static Logger logger = Logger.getLogger(MappingEndpoint.class);

	public void createDataElements(Map<Entity, Map<Datasource, Set<Field>>> dictionaryEntitySourceFields)
			throws InvalidDatasource {

		logger.info("Creating data elements");

		for (Entity entity : dictionaryEntitySourceFields.keySet()) {

			logger.info(entity.getName());

			for (Datasource datasource : dictionaryEntitySourceFields.get(entity).keySet()) {

				logger.info(datasource.getName());
				for (Field field : dictionaryEntitySourceFields.get(entity).get(datasource)) {
					logger.info(field.getFieldName());
				}

				Entity subEntity = entity;
				subEntity.setFields(dictionaryEntitySourceFields.get(entity).get(datasource));

				if (datasource instanceof DatasourceColumn) {
					((DatasourceColumn) datasource).createDataElements(subEntity,
							dictionaryEntitySourceFields.get(entity).get(datasource));
				} else if (datasource instanceof DatasourceDocument) {
					((DatasourceDocument) datasource).createDataElements(subEntity,
							dictionaryEntitySourceFields.get(entity).get(datasource));
				} else if (datasource instanceof RelationshipManager) {
					logger.warn("Relationship not implemented yet");
				} else {
					throw new InvalidDatasource();
				}
			}
		}
	}
}

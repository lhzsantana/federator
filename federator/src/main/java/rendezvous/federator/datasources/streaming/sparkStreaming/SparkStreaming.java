package rendezvous.federator.datasources.streaming.sparkStreaming;

import java.util.Set;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.json.simple.parser.ParseException;

import rendezvous.federator.core.Entity;
import rendezvous.federator.core.Hit;
import rendezvous.federator.core.Value;
import rendezvous.federator.datasources.streaming.StreamingDatasource;

public class SparkStreaming extends StreamingDatasource {

	private JavaStreamingContext jssc;
	private JavaDStream<String> lines;

	@Override
	public String getDataSourceType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public void connect() throws Exception {

		SparkConf conf = new SparkConf().setMaster(getConfiguration().get("spark.host"))
				.setAppName(getConfiguration().get("spark.appName"));
		jssc = new JavaStreamingContext(conf, Durations.seconds(1));
		jssc.start();

		lines = jssc.socketTextStream("", 1);
	}

	@Override
	public String insert(Entity entity, final Set<Value> values) throws ParseException, Exception {

		JavaDStream<Value> cache = lines.flatMap(new FlatMapFunction<String, Value>() {

			@Override
			public Iterable<Value> call(String x) {
				return values;
			}
		});

		cache.persist();
		
		return entity.getId();
	}

	@Override
	public Hit get(Entity entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}

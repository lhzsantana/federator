package rendezvous.federator.datasources.streaming.sparkRedisStreaming;

import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.json.simple.parser.ParseException;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import rendezvous.federator.core.Entity;
import rendezvous.federator.core.Hit;
import rendezvous.federator.core.Value;
import rendezvous.federator.datasources.keyvalue.redis.Redis;
import rendezvous.federator.datasources.streaming.StreamingDatasource;

public class SparkRedisStreaming extends StreamingDatasource implements Job {

	private final static Logger logger = Logger.getLogger(SparkRedisStreaming.class);
	
	private JavaStreamingContext jssc;
	private JavaDStream<String> lines;
	private Redis redis;
	
	public SparkRedisStreaming() throws NumberFormatException, Exception{

		// Quartz 1.6.3
		JobDetail job = JobBuilder.newJob(SparkRedisStreaming.class).withIdentity("SparkRedisStreaming", "SparkRedisStreaming").build();

		// Trigger the job to run on the next round minute
		Trigger trigger = TriggerBuilder.newTrigger()
				.withIdentity("SparkRedisStreaming", "SparkRedisStreaming")
				.withSchedule(SimpleScheduleBuilder.simpleSchedule()
				.withIntervalInSeconds(Integer.valueOf(getConfiguration().get("streaming.persistTime"))).repeatForever()).build();

		// schedule it
		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		scheduler.start();
		scheduler.scheduleJob(job, trigger);
	}

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
		
		redis = new Redis();
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
		
		return redis.get(entity);
	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		
		//JavaDStream<Value> cache = lines.dstream().;
				
		try {
			redis.insert(null, null);
			
		} catch (ParseException e) {
			logger.error(e);
		} catch (Exception e) {
			logger.error(e);
		}
		
	}

}

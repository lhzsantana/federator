package rendezvous.federator.cluster;

import org.apache.mesos.Executor;
import org.apache.mesos.ExecutorDriver;
import org.apache.mesos.Protos;
import org.apache.mesos.Protos.ExecutorInfo;
import org.apache.mesos.Protos.FrameworkInfo;
import org.apache.mesos.Protos.SlaveInfo;
import org.apache.mesos.Protos.TaskID;
import org.apache.mesos.Protos.TaskInfo;

public class FederatorExecutor implements Executor {

	@Override
	public void disconnected(ExecutorDriver arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void error(ExecutorDriver arg0, String arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void frameworkMessage(ExecutorDriver arg0, byte[] arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void killTask(ExecutorDriver arg0, TaskID arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void launchTask(ExecutorDriver executorDriver, TaskInfo taskInfo) {
		
		Integer id = Integer.parseInt(taskInfo.getData().toStringUtf8());
		String reply = id.toString();
		executorDriver.sendFrameworkMessage(reply.getBytes());
		Protos.TaskStatus status = Protos.TaskStatus.newBuilder().setTaskId(taskInfo.getTaskId())
				.setState(Protos.TaskState.TASK_FINISHED).build();
		executorDriver.sendStatusUpdate(status);
	}

	@Override
	public void registered(ExecutorDriver arg0, ExecutorInfo arg1, FrameworkInfo arg2, SlaveInfo arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void reregistered(ExecutorDriver arg0, SlaveInfo arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void shutdown(ExecutorDriver arg0) {
		// TODO Auto-generated method stub

	}

}

package com.software.dm.swallow.stormy.hadoop.format.output;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.TaskAttemptID;
import org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter;

import java.io.IOException;

/**
 * 
 * @Description  
 * @author DM
 * @date 2016 
 * @version v4.0.2.0 
 *
 */
public class MoveFileOutputCommitter extends FileOutputCommitter {

	public MoveFileOutputCommitter(Path outputPath, TaskAttemptContext context) throws IOException {
		this(outputPath, (JobContext) context);
		if (outputPath != null)
			this.workPath = getTaskAttemptPath(context, outputPath);
	}

	public MoveFileOutputCommitter(Path outputPath, JobContext context) throws IOException {
		super(outputPath, (TaskAttemptContext) context);
		if (outputPath != null) {
			FileSystem fs = outputPath.getFileSystem(context.getConfiguration());
			this.outputPath = fs.makeQualified(outputPath);
		}
	}

	private static final Log LOG = LogFactory.getLog(MoveFileOutputCommitter.class);
	private Path outputPath = null;
	private Path workPath = null;

	private Path getOutputPath() {
		return this.outputPath;
	}

	private boolean hasOutputPath() {
		return this.outputPath != null;
	}

	private static Path getPendingJobAttemptsPath(Path out) {
		return new Path(out, "_temporary");
	}

	private static int getAppAttemptId(JobContext context) {
		return context.getConfiguration().getInt("mapreduce.job.application.attempt.id", 0);
	}

	public Path getJobAttemptPath(JobContext context) {
		return getJobAttemptPath(context, getOutputPath());
	}

	public static Path getJobAttemptPath(JobContext context, Path out) {
		return getJobAttemptPath(getAppAttemptId(context), out);
	}

	public Path getJobAttemptPath(int appAttemptId) {
		return getJobAttemptPath(appAttemptId, getOutputPath());
	}

	private static Path getJobAttemptPath(int appAttemptId, Path out) {
		return new Path(getPendingJobAttemptsPath(out), String.valueOf(appAttemptId));
	}

	private Path getPendingTaskAttemptsPath(JobContext context) {
		return getPendingTaskAttemptsPath(context, getOutputPath());
	}

	private static Path getPendingTaskAttemptsPath(JobContext context, Path out) {
		return new Path(getJobAttemptPath(context, out), "_temporary");
	}

	public Path getTaskAttemptPath(TaskAttemptContext context) {
		return new Path(getPendingTaskAttemptsPath(context), String.valueOf(context.getTaskAttemptID()));
	}

	public static Path getTaskAttemptPath(TaskAttemptContext context, Path out) {
		return new Path(getPendingTaskAttemptsPath(context, out), String.valueOf(context.getTaskAttemptID()));
	}

	public Path getCommittedTaskPath(TaskAttemptContext context) {
		return getCommittedTaskPath(getAppAttemptId(context), context);
	}

	public Path getCommittedTaskPath(int appAttemptId, TaskAttemptContext context) {
		return new Path(getJobAttemptPath(appAttemptId), String.valueOf(context.getTaskAttemptID().getTaskID()));
	}

	@SuppressWarnings("deprecation")
	public void commitJob(JobContext context) throws IOException {
		if (hasOutputPath()) {
			Path finalOutput = getOutputPath();
			FileSystem fs = finalOutput.getFileSystem(context.getConfiguration());
			// for (FileStatus stat : getAllCommittedTaskPaths(context)) {
			// LOG.error("*******:" + stat.getPath());
			// mergePaths(fs, stat, finalOutput);
			// }
			cleanupJob(context);
			if (context.getConfiguration().getBoolean("mapreduce.fileoutputcommitter.marksuccessfuljobs", true)) {
				Path markerPath = new Path(this.outputPath, "_SUCCESS");
				fs.create(markerPath).close();
			}
		} else {
			LOG.warn("Output Path is null in commitJob()");
		}
	}

	/*
	 * private static void combineFiles(FileSystem fs, FileStatus from, Path
	 * to){ FSDataOutputStream hdfsOs = null; for(FileStatus fileStatus :
	 * fs.listStatus(to)){
	 * if(!fileStatus.getPath().getName().contains("complete") &&
	 * fileStatus.getLen() < xxx){ hdfsOs = fs.append(fileStatus.getPath());
	 * //new Path(fileStatus.getPath().getParent().getName() + "/combined-" +
	 * from.getPath().getName()) } } if(hdfsOs == null){ hdfsOs = fs.create(new
	 * Path(to.getParent().getName() + "/combined-" +
	 * from.getPath().getName())); } OutputStream os = new
	 * GZIPOutputStream(hdfsOs); FSDataInputStream is = fs.open(from.getPath());
	 * byte[] buff = new byte[1024]; int read = 0; while((read = is.read(buff))
	 * != -1){ os.write(buff, 0, read); } is.close(); os.close();
	 * 
	 * 
	 * 
	 * }
	 */

	private static void mergePaths(FileSystem fs, FileStatus from, Path to) throws IOException {
		LOG.debug("Merging data from " + from + " to " + to);
		if (from.isFile()) {
			if ((fs.exists(to)) && (!fs.delete(to, true))) {
				throw new IOException("Failed to delete " + to);
			}

			if (!fs.rename(from.getPath(), to))
				throw new IOException("Failed to rename " + from + " to " + to);
		} else if (from.isDirectory()) {
			if (fs.exists(to)) {
				FileStatus toStat = fs.getFileStatus(to);
				if (!toStat.isDirectory()) {
					if (!fs.delete(to, true)) {
						throw new IOException("Failed to delete " + to);
					}
					if (!fs.rename(from.getPath(), to))
						throw new IOException("Failed to rename " + from + " to " + to);
				} else {
					for (FileStatus subFrom : fs.listStatus(from.getPath())) {
						Path subTo = new Path(to, subFrom.getPath().getName());
						mergePaths(fs, subFrom, subTo);
					}
				}
			} else if (!fs.rename(from.getPath(), to)) {
				throw new IOException("Failed to rename " + from + " to " + to);
			}
		}
	}

	public void commitTask(TaskAttemptContext context) throws IOException {
		commitTask(context, null);
	}

	public void commitTask(TaskAttemptContext context, Path taskAttemptPath) throws IOException {
		TaskAttemptID attemptId = context.getTaskAttemptID();
		if (hasOutputPath()) {
			context.progress();
			if (taskAttemptPath == null) {
				taskAttemptPath = getTaskAttemptPath(context);
			}
			Path committedTaskPath = getCommittedTaskPath(context);
			FileSystem fs = taskAttemptPath.getFileSystem(context.getConfiguration());
			if (fs.exists(taskAttemptPath)) {
				if ((fs.exists(committedTaskPath)) && (!fs.delete(committedTaskPath, true))) {
					throw new IOException("Could not delete " + committedTaskPath);
				}
				// LOG.error("**source**" + taskAttemptPath);
				// LOG.error("**target**" + committedTaskPath);
				mergePaths(fs, fs.getFileStatus(taskAttemptPath), getOutputPath());
				// if (!fs.rename(taskAttemptPath, committedTaskPath)) {
				// throw new IOException("Could not rename " + taskAttemptPath +
				// " to " + committedTaskPath);
				// }

				LOG.info("Saved output of task '" + attemptId + "' to " + getOutputPath());
			} else {
				LOG.warn("No Output found for " + attemptId);
			}
		} else {
			LOG.warn("Output Path is null in commitTask()");
		}
	}

	public void recoverTask(TaskAttemptContext context) throws IOException {
		if (hasOutputPath()) {
			context.progress();
			TaskAttemptID attemptId = context.getTaskAttemptID();
			int previousAttempt = getAppAttemptId(context) - 1;
			if (previousAttempt < 0) {
				throw new IOException("Cannot recover task output for first attempt...");
			}

			Path committedTaskPath = getCommittedTaskPath(context);
			Path previousCommittedTaskPath = getCommittedTaskPath(previousAttempt, context);

			FileSystem fs = committedTaskPath.getFileSystem(context.getConfiguration());

			LOG.debug("Trying to recover task from " + previousCommittedTaskPath + " into " + committedTaskPath);

			if (fs.exists(previousCommittedTaskPath)) {
				if ((fs.exists(committedTaskPath)) && (!fs.delete(committedTaskPath, true))) {
					throw new IOException("Could not delete " + committedTaskPath);
				}

				Path committedParent = committedTaskPath.getParent();
				fs.mkdirs(committedParent);
				mergePaths(fs, fs.getFileStatus(previousCommittedTaskPath), getOutputPath());

				// if (!fs.rename(previousCommittedTaskPath, committedTaskPath))
				// {
				// throw new IOException("Could not rename " +
				// previousCommittedTaskPath + " to " + committedTaskPath);
				// }

				LOG.info("Saved output of " + attemptId + " to " + committedTaskPath);
			} else {
				LOG.warn(attemptId + " had no output to recover.");
			}
		} else {
			LOG.warn("Output Path is null in recoverTask()");
		}
	}

	public Path getWorkPath() throws IOException {
		return this.workPath;
	}

}
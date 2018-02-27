package com.software.dm.swallow.stormy.hadoop.mapred;

import java.io.IOException;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import com.software.dm.swallow.stormy.hadoop.common.HadoopConstants;
import com.software.dm.swallow.stormy.hadoop.service.common.AbstractConstants;
import com.software.dm.swallow.stormy.hadoop.tools.AbstractCommonUtils;
import com.software.dm.swallow.stormy.hadoop.tools.Constant;

/**
 * 
 * @author DearM
 * @see mapreduce do something
 * @version v1.0.0.0
 * 
 */
public abstract class AbstractMapreduce extends Configured implements Tool {
	private static Job job;
	private final String job_name;

	/**
	 * 
	 * @param mapred
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public static int runMapred(AbstractMapreduce mapred, String[] args) throws Exception {
		return ToolRunner.run(mapred, args);

	}

	/**
	 * 
	 * @param args
	 * @return
	 * @throws RuntimeException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	protected Job createJob(String[] args) throws RuntimeException, IOException, ClassNotFoundException {
		if (this.getConf().get(HadoopConstants.INPUT_PATH) == null || this.getConf().get(HadoopConstants.OUTPUT_PATH) == null) {
			throw new RuntimeException("Usage: invertedindex <in> <out> path");
		}
		if ((args.length & 1) == 1) {
			throw new RuntimeException("Usage: invertedindex  args error");
		} else {
			for (int i = 0; i < args.length; i++) {
				this.getConf().set(args[i], args[++i]);
			}
		}
		String input_split = this.getConf().get(HadoopConstants.INPUT_FIELD_SPLIT);
		if (input_split == null || input_split.isEmpty()) {
			this.getConf().set(HadoopConstants.INPUT_FIELD_SPLIT, "1");
		}
		String output_split = this.getConf().get(HadoopConstants.OUTPUT_FIELD_SPLIT);
		if (output_split == null || output_split.isEmpty()) {
			this.getConf().set(HadoopConstants.OUTPUT_FIELD_SPLIT, "1");
		}
		job = new Job(this.getConf(), this.job_name);
		MultipleOutputs.addNamedOutput(job, HadoopConstants.MULTIPLE_OUTPUTS_NAMED, job.getOutputFormatClass(), Text.class, NullWritable.class);
		return job;
	}

	private static final String hexString = "0x";

	/**
	 * @since : num to char , 1 to \001 , 44 to , | to | ,0x 16 to char+char
	 * @param delimiter_str
	 * @return
	 */
	public static String splitConvert(final String delimiter_str) {
		String res_sp = "";
		if (delimiter_str == null || delimiter_str.isEmpty()) {
			return res_sp;
		} else if (AbstractCommonUtils.isNum(delimiter_str)) {
			res_sp = String.valueOf((char) Integer.parseInt(delimiter_str));
		} else if (delimiter_str.startsWith(hexString)) {
			String[] delimiter_strs = AbstractCommonUtils.fastSplit(delimiter_str, hexString);
			for (String string : delimiter_strs) {
				string = string.trim();
				if (!string.isEmpty())
					res_sp += String.valueOf((char) (0xffff & Integer.parseInt(string.trim(), 16)));
			}
		} else {
			res_sp = delimiter_str;
		}
		return res_sp;
	}

	/**
	 * 
	 * @param context
	 * @param e
	 * @param groupName
	 * @param name
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	public static void printErrorCounter(org.apache.hadoop.mapreduce.Mapper.Context context, Exception e, String counterName, String flagName) throws IOException {
		String exception_name = e.toString();
		final int poi = exception_name.indexOf(":");
		exception_name = poi > -1 ? exception_name.substring(0, poi) : exception_name;
		addCounter(context, HadoopConstants.COUNTER_GROUPNAME_ERROR, "Map " + counterName + " " + exception_name);
		addCounter(context, HadoopConstants.COUNTER_GROUPNAME_ERROR, "Map " + counterName + " " + flagName);
	}

	/**
	 * 
	 * @param context
	 * @param e
	 * @param groupName
	 * @param name
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	public static void printErrorCounter(org.apache.hadoop.mapreduce.Reducer.Context context, Exception e, String counterName, String flagName) throws IOException {
		String exception_name = e.toString();
		final int poi = exception_name.indexOf(":");
		exception_name = poi > -1 ? exception_name.substring(0, poi) : exception_name;
		addCounter(context, HadoopConstants.COUNTER_GROUPNAME_ERROR, "Reduce " + counterName + " " + exception_name);
		addCounter(context, HadoopConstants.COUNTER_GROUPNAME_ERROR, "Reduce " + counterName + " " + flagName);
	}

	/**
	 * 
	 * @param mos
	 * @param keyInfo
	 * @param valInfo
	 * @param e
	 * @param line
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void writeLog(MultipleOutputs<?, ?> mos, Text keyInfo, Exception e) throws IOException, InterruptedException {
		if (e != null) {
			String errorMsg = e.toString();
			StackTraceElement[] trace = e.getStackTrace();
			for (int j = 0; j < trace.length; j++) {
				errorMsg += Constant.LINE_SEPARATOR + trace[j];
			}
			keyInfo.set(errorMsg + Constant.LINE_SEPARATOR + keyInfo.toString());
		}
		mos.write(HadoopConstants.MULTIPLE_OUTPUTS_NAMED, keyInfo, NullWritable.get(), HadoopConstants.LOG_OUTPUTS_NAMED + AbstractConstants.FILE_SEPARATOR + HadoopConstants.MULTIPLE_OUTPUTS_FILENAME);
	}

	/**
	 * 
	 * @param context
	 * @param groupName
	 * @param counterName
	 */
	public static void addCounter(final org.apache.hadoop.mapreduce.Reducer.Context context, String groupName, final String counterName) {
		if (null == groupName)
			groupName = AbstractConstants.TotalCountEnum.TOTAL_COUNT.name();
		context.getCounter(groupName, counterName).increment(1);
	}

	/**
	 * 
	 * @param context
	 * @param counterName
	 */
	public static void addCounter(final org.apache.hadoop.mapreduce.Reducer.Context context, final String counterName) {
		addCounter(context, null, counterName);
	}

	/**
	 * 
	 * @param context
	 * @param groupName
	 * @param counterName
	 */
	public static void addCounter(final org.apache.hadoop.mapreduce.Mapper.Context context, String groupName, final String counterName) {
		if (null == groupName)
			groupName = AbstractConstants.TotalCountEnum.TOTAL_COUNT.name();
		context.getCounter(groupName, counterName).increment(1);
	}

	/**
	 * 
	 * @param context
	 * @param counterName
	 */
	public static void addCounter(final org.apache.hadoop.mapreduce.Mapper.Context context, final String counterName) {
		addCounter(context, null, counterName);
	}

	public AbstractMapreduce(String job_name) {
		this.job_name = job_name;
	}

	public AbstractMapreduce() {
		this.job_name = this.getClass().getName();
	}

	public String getJob_name() {
		return job_name;
	}
}

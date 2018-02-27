package com.software.dm.swallow.stormy.hadoop.format.output;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.OutputCommitter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import com.software.dm.swallow.stormy.hadoop.common.HadoopConstants;

/**
 * 
 * @Description
 * @author DM
 * @date 2016
 * @version v1.0.0.1
 * 
 */
public class MoveOutputFormat extends TextOutputFormat<Text, Text> {

	public MoveOutputFormat() {
		// TODO Auto-generated constructor stub
	}

	public OutputCommitter getOutputCommitter(TaskAttemptContext tac) throws IOException {
		return new MoveFileOutputCommitter(new Path(tac.getConfiguration().get(HadoopConstants.OUTPUT_PATH_MOVE)), tac);
	}

}

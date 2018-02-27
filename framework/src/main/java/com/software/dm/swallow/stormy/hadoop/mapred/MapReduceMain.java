/**
 * 
 */
package com.software.dm.swallow.stormy.hadoop.mapred;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.LazyOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import com.software.dm.swallow.stormy.hadoop.common.HadoopConstants;
import com.software.dm.swallow.stormy.hadoop.common.MaperdTools;
import com.software.dm.swallow.stormy.hadoop.service.common.AbstractConstants;
import com.software.dm.swallow.stormy.hadoop.service.scene.factory.DefaultDataMapper;
import com.software.dm.swallow.stormy.hadoop.service.scene.factory.DefaultDataReduce;
import com.software.dm.swallow.stormy.hadoop.service.scene.factory.DefaultDoMapper;
import com.software.dm.swallow.stormy.hadoop.service.scene.factory.DefaultDoReducer;
import com.software.dm.swallow.stormy.hadoop.service.utils.SceneConstant;
import com.software.dm.swallow.stormy.hadoop.tools.AbstractCommonUtils;

/**
 * @Description
 * @author DearM
 * @date 2016��2��19��
 * @version v1.0.0.1
 * 
 */
public class MapReduceMain extends AbstractMapreduce {

	/**
	 * @param job_name
	 */
	public MapReduceMain(String job_name) {
		super(job_name);
	}

	static class MapReduceToMapper extends Mapper<Object, Text, Text, ValueInfo> {
		private SceneConstant constant;
		private Text keyInfo = new Text();
		private ValueInfo valInfo = new ValueInfo();
		private Text data_text = new Text();
		private Text flag = new Text();
		private DefaultDataMapper actionData;

		
		protected void setup(Context context) throws IOException, InterruptedException {
			constant = new SceneConstant(context.getConfiguration());
			constant.init();
			context.getConfiguration().get("mapreduce.inputformat.class");
			DefaultDoMapper incremapper = new DefaultDoMapper(new MaperdTools(context, constant.OUTPUT_FIELD));
//			incremapper.registerScene(AbstractConstants.AppCode.PV2.getCode(), new FusionidMapperScene());
			actionData = new DefaultDataMapper(incremapper);
		}

		
		protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
			try {
				InputSplit ins = context.getInputSplit();
				String data = value.toString();
				String[] datas = AbstractCommonUtils.fastSplit(value.toString(), constant.INPUT_FIELD);
				if (AbstractConstants.COLUMN_LENGTH_EXTRACTOUT == datas.length) {
					actionData.doMapperData(datas,true);
				} 

			} catch (Exception e) {
				e.printStackTrace();
				printErrorCounter(context, e, "MapReduceToMapper", "mapper");
			}
		}
	}

	static class MapReduceToReducer extends Reducer<Text, ValueInfo, Text, NullWritable> {
		private SceneConstant constant;
		private MultipleOutputs<Text, NullWritable> mos;
		private DefaultDataReduce actionData;

		
		protected void setup(Context context) throws IOException, InterruptedException {
			this.mos = new MultipleOutputs<Text, NullWritable>(context);
			constant = new SceneConstant(context.getConfiguration());
			constant.init();
			DefaultDoReducer incremapper = new DefaultDoReducer(new MaperdTools(context, this.mos, constant.OUTPUT_FIELD));
//			incremapper.registerScene(AbstractConstants.AppCode.PV2.getCode(), new FusionidReducerScene(constant));
			actionData = new DefaultDataReduce(incremapper);
		}

		
		protected void reduce(Text key, Iterable<ValueInfo> value, Context context) throws IOException, InterruptedException {
			try {
				actionData.doReduceData(key.toString(), value, constant.OUTPUT_FIELD);
			} catch (Exception e) {
				e.printStackTrace();
				printErrorCounter(context, e, "MapReduceToReducer", "reduce");
			}
		}

		protected void cleanup(Context context) throws IOException, InterruptedException {
			this.mos.close();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.hadoop.util.Tool#run(java.lang.String[])
	 */
 
	public int run(String[] arg0) throws Exception {
		Job job = createJob(arg0);
		job.setJarByClass(MapReduceMain.class);
		job.setMapperClass(MapReduceToMapper.class);
		job.setReducerClass(MapReduceToReducer.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(ValueInfo.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(NullWritable.class);
		// job.setNumReduceTasks(0);
		TextInputFormat.addInputPaths(job, this.getConf().get(HadoopConstants.INPUT_PATH));
		// +this.getConf().get(SceneConstant.OPER_DATE, "")
		TextOutputFormat.setOutputPath(job, new Path(this.getConf().get(HadoopConstants.OUTPUT_PATH) + File.separatorChar + this.getConf().get(SceneConstant.OPER_DATE, SceneConstant.DF_YYYY_MM_DD.format(new Date()))));
		LazyOutputFormat.setOutputFormatClass(job, TextOutputFormat.class);
		MultipleOutputs.addNamedOutput(job, SceneConstant.OUT_PATH_STANDARD, TextOutputFormat.class, Text.class, NullWritable.class);
		MultipleOutputs.addNamedOutput(job, SceneConstant.OUT_PATH_INCRE, TextOutputFormat.class, Text.class, NullWritable.class);

		return job.waitForCompletion(true) ? 0 : 1;
	}

	/**
	 * 
	 * @return
	 */
	public static String[] getParams() {
		String params = " -Dmapreduce.input.fileinputformat.split.maxsize=1000000000  -Dmapreduce.inputformat.class=com.software.dm.swallow.stormy.hadoop.format.CompressedCombineFileInputFormat "
				+ "-D mr.cfg.path=resource   " //gw_mainconfig.properties test_bjdx_mainconfig.properties  refer_mainconfig.properties  gw_mainconfig.properties liaoning_normal_mainconfig.properties
				+ "	-D mr.cfg.main.name=beijing_normal_mainconfig.properties   -D mapreduce.job.reduces=4  "//
//				+ "	-D mapreduce.input.path=D:/DearM/dpi/input/bjdx   "//
//				+ "	-D mapreduce.input.path=D:/DearM/dpi/input/tjyd/4g  "//
				+ "	-D mapreduce.input.path=D:/DearM/dpi/data/gw/*.txt  "//
				+ "	-D mapreduce.output.path=D:/DearM/dpi/analysis/dtov4/test  "//
				+ "	-D mr.exec.date=20161101 " //
				+ " -D mr.java.opts3=true" + "	-D mr.java.opts2=true ";//
		
		
		StringTokenizer st = new StringTokenizer(params);
		List<String> listParam = new ArrayList<String>();
		while (st.hasMoreTokens()) {
			String val = st.nextToken();
			listParam.add(val);
		}
		return listParam.toArray(new String[] {});
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		args =  getParams();
		System.out.println(Arrays.toString(args));
		try {
			int res = AbstractMapreduce.runMapred(new MapReduceMain("MapReduceMain"), args);
			System.exit(res);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

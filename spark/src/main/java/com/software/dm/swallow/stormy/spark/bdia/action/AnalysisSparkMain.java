package com.software.dm.swallow.stormy.spark.bdia.action;

import com.software.dm.swallow.stormy.hadoop.tools.AbstractCommonUtils;
import com.software.dm.swallow.stormy.security.match.bdia.bean.T_extract_rule;
import com.software.dm.swallow.stormy.security.match.bdia.bean.T_theme_basic_type_rel;
import com.software.dm.swallow.stormy.security.match.bdia.bean.T_theme_url_rule;
import com.software.dm.swallow.stormy.security.match.bdia.bean.T_url_rule;
import com.software.dm.swallow.stormy.security.match.bdia.obtain.SerialVagueFactroy;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.input.PortableDataStream;
import scala.Tuple2;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 *
 */
public class AnalysisSparkMain {
    private final static Logger logger = LogManager.getLogger(AnalysisSparkMain.class);
    private JavaSparkContext sc = null;
    //private Broadcast<SerialVagueFactroy> bcSerial = null;

    public AnalysisSparkMain() {
        SparkConf conf = new SparkConf();
        conf.setAppName(AnalysisSparkMain.class.getSimpleName());
        conf.setMaster("local[1]");
        conf.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer");
        sc = new JavaSparkContext(conf);
    }

    private void init() throws IOException {
        //MainVagueSerial.main(null);


    }

    private SerialVagueFactroy getSF() throws IOException {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");

        String serialPath = "D:/DearM/repo/yun/serial" + File.separatorChar + df.format(new Date()) + ".serial";
        PortableDataStream pds = sc.binaryFiles(serialPath, 1).first()._2();


        InputStream is = pds.open();

        SerialVagueFactroy sf = new SerialVagueFactroy(is);

        return sf;
    }

    private void doAction() throws IOException {
        String output = "data/spark.rddmultiple/dm_spark";
        FileSystem fileSystem = FileSystem.get(sc.hadoopConfiguration());
        fileSystem.delete(new Path(output), true);


        String input = "D:\\DearM\\dpi\\input\\bjdx\\bjdx-000841_0";
        JavaRDD<String> javaRDD = sc.textFile(input);
        //javaRDD.filter(new Function<String, Boolean>() {
        //    @Override
        //    public Boolean call(String s) throws Exception {
        //
        //        return null;
        //    }
        //})
        javaRDD.cache();
        JavaRDD<List<String[]>> javaRDD1 = javaRDD.map(new Function<String, List<String[]>>() {

            private SerialVagueFactroy sf =  getSF();
            public List<String[]> call(String s) throws Exception {
                List<String[]> resList = new ArrayList<String[]>();
                String[] values = s.split(",", -1);
                String url = values[24];
                String[] fullDomain = AbstractCommonUtils.getFullDomainWithBareUrl(url.getBytes());

                if (null != fullDomain) {
                    sf.execute(fullDomain);
                    T_url_rule url_rule = sf.getBasicAppResult();
                    List<T_theme_url_rule> theme_url_ruleList = sf.getThemeResultList();
                    List<T_extract_rule> extract_ruleList = sf.getExtractResultList();

                    //basic

                    //app

                    //theme

                    //subject


                    for (T_extract_rule t_extract_rule :
                            sf.getExtractResultList()) {
                        String s1 = sf.outputExtract(url, t_extract_rule);
                        resList.add(new String[]{"Subject" + "\001" + values[0] + "\001" + s, ""});
                    }
                    for (T_theme_url_rule t_theme_url_rule : sf.getThemeResultList()) {
                        for (T_theme_basic_type_rel t_theme_basic_type_rel : t_theme_url_rule.getThemeTypes()) {
                            //System.out.println(t_theme_basic_type_rel.getType_id() + "===" + t_theme_url_rule.getRule());
                            resList.add(new String[]{"Theme" + "\001" + values[0] + "\001" + t_theme_basic_type_rel.getType_id()});
                        }
                    }
                }
                return resList;


            }
        });
        JavaRDD<String> javaRDD2 = javaRDD1.flatMap(new FlatMapFunction<List<String[]>, String[]>() {

            public Iterator<String[]> call(List<String[]> strings) throws Exception {
                return strings.iterator();
            }
        }).mapToPair(new PairFunction<String[], String[], Integer>() {
            @Override
            public Tuple2<String[], Integer> call(String[] s) throws Exception {
                return new Tuple2<String[], Integer>(s, 1);
            }

        }).reduceByKey(new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer x, Integer y) throws Exception {
                return x + y;
            }
        }).map(new Function<Tuple2<String[], Integer>, String>() {
            @Override
            public String call(Tuple2<String[], Integer> t1) throws Exception {
                return t1._1[0] + "\001" + t1._2;
            }
        });
        javaRDD2.repartition(5).saveAsTextFile(output);
    }

    public void execute(String[] args) {

        try {
            // load properties
            this.init();
            this.doAction();
        } catch (Exception e) {
            logger.error("execute=", e);
        } finally {
            sc.stop();
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        new AnalysisSparkMain().execute(args);
    }
}

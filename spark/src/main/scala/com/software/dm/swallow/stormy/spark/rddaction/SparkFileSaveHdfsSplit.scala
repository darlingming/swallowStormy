package com.software.dm.swallow.stormy.spark.rddaction

import com.software.dm.swallow.stormy.spark.common.RDDMultipleTextOutputFormat
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.{HashPartitioner, SparkConf, SparkContext}

object SparkFileSaveHdfsSplit {

  def main(args: Array[String]) {


    val conf = new SparkConf().setAppName("SplitTest").setMaster("local")
    val sc = new SparkContext(conf)

    val output = "data/spark.rddmultiple/dm_spark"
    val fileSystem = FileSystem.get(sc.hadoopConfiguration)
    fileSystem.delete(new Path(output), true)


    sc.parallelize(List(("w", "www"), ("b/ss", "blog"), ("c", "com"), ("w", "bt")))
      .map(value => (value._1, value._2 + "Test"))
      .partitionBy(new HashPartitioner(3))
      .saveAsHadoopFile(output, classOf[String], classOf[String],
        classOf[RDDMultipleTextOutputFormat])
    sc.stop()

  }

}

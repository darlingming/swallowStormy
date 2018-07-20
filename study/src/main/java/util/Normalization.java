package util;

import java.io.Serializable;
import java.util.Collections;
import java.util.Vector;

/**
 * Created by YYT on 2016/9/27.
 * ���������ݱ�׼��ʵ��
 * ��Ҫ���б�׼�������Σ�
      ������Ҫ�������;
      ��ͬ����֮��ĳ߶����ܴ�
 */
public class Normalization implements Serializable{
    public static double[][] data = {
            {2,7500.0,15.0},
            {22.0,4500.0,25.0},
            {1.0,1500.0,45.0}
    };

    /**
     * ͨ����������ȡ��������
     * @param columnIdx
     * @param data
     * @return
     */
    public static Vector<Double> getVectorByColumnIdx(int columnIdx,double[][] data){
        int m = data.length; //������
        Vector<Double> vector = new Vector<Double>(m);
        for(int i=0;i< m;i++) {
            vector.add(data[i][columnIdx]);
        }
        return vector;
    }

    /*
    *   ʵ�ֵ�������������С��׼��
    */
    public static double[] minmax(int columnIdx,double[][] data){
          double[] vector = new double[data.length];
          double maxValue = 0.0;
          double minValue = 0.0;
          for(int i=0;i<data.length;i++){
              vector[i] = data[i][columnIdx];
              maxValue = Math.max(maxValue,vector[i]);
              minValue = Math.min(minValue,vector[i]);
          }
          for (int i=0;i<data.length;i++){
              vector[i] = ( vector[i] - minValue ) / ( maxValue - minValue);
          }
        return vector;
    }

    /**
     *  ��ȡ������ֵ
     */
    public static double getMean(int columnIdx,double[][] data){
        double sum = 0.0;
        double mean = 0.0;
        int m = data.length; //������
        for(int i=0;i< m;i++){
            sum+=data[i][columnIdx];
        }
        if(m > 0){
            mean = sum / m;
        }
        return mean;
    }

    /**
     *  ��ȡ������׼��
     */
    public static double getStandardDeviation(int columnIdx,double[][] data){
        double mean = getMean(columnIdx,data);
        double variance = 0.0;//����
        double sd = 0.0;
        int m = data.length;
        for(int i=0;i< m;i++){
            variance +=(1.0/m) * Math.pow((data[i][columnIdx] - mean), 2);
        }
        sd = Math.sqrt(variance);
        return sd;
    }
    /**
     *  ʵ�ֵ������׼�ֱ�׼��
     *  �ص㣺���ü��������Сֵ�����ǻ����쳣��Ӱ�죨�쳣�ߵ�ֵ�����߾�ֵ��
     */
    public static double[] zscore(int columnIdx,double[][] data){
        int m = data.length; //������
        double[] vector = new double[m];
        double u = getMean(columnIdx,data); //������ֵ
        double s = getStandardDeviation(columnIdx,data); //��׼��
        if(s != 0){
            for (int i=0;i< m;i++){
                vector[i] = (data[i][columnIdx] - u) / s;
            }
        }
        return vector;
    }

    /**
     * ��ȡ��������λ��
     * @param columnIdx:�������������
     * @param data�����ݼ�
     * @return ������������λ���������ȡ�м������
     */
    public static double getMedian(int columnIdx,double[][] data){
        double median = 0.0;
        int m = data.length;
        Vector<Double> vector = new Vector<Double>(m);
        for (int i=0;i<m;i++){
            vector.add(data[i][columnIdx]);
        }
        Collections.sort(vector);
        if(m % 2 == 1){//������ȡ�м��
            int midx = 0;//��λ�����ڵ�index
            midx = m / 2;
            median = vector.get(midx);
        }else {      //ż����ȡ�������ľ�ֵ
            median = ( vector.get(m/2-1) + vector.get(m/2) ) / 2;
        }
        return median;
    }

    /**
     * ��ȡ�����ľ���ƫ��
     * @param columnIdx:�������������
     * @param data�����ݼ�
     * @return
     */
    public static double getAbsoluteDeviation(int columnIdx,double[][] data){
        Vector<Double> vector = getVectorByColumnIdx(columnIdx, data);
        int m = vector.size();
        double mean = getMean(columnIdx, data);
        double sum = 0;
        double asd = 0;
        for(Double d:vector){
            sum += Math.abs(d - mean);
        }
        if(m > 0){
            asd = sum / m;
        }
        return asd;
    }

    /**
     * ������ı�׼�ֿ˷����쳣ֵ���ŵ�����
     * @param columnIdx
     * @param data
     * @return
     */
    public static Vector<Double> modefiedStandardScore(int columnIdx, double[][] data){
        Vector<Double> vector = getVectorByColumnIdx(columnIdx, data);
        int m = vector.size();
        double median = getMedian(columnIdx, data);
        double asd = getAbsoluteDeviation(columnIdx, data);
        for(int i =0;i<m;i++){
            vector.set(i,((vector.get(i) - median) / asd));
        }
        return vector;
    }
    public static void main(String[] args) {
     /*   assert(getMean(1,data) == 7.0/3);
        System.out.println("getMean success");*/
       /* System.out.println(getStandardDeviation(1,data));*/
        double[] normal = minmax(1, data);
        double[] zscore = zscore(1, data);
        Vector<Double> mss = modefiedStandardScore(1, data);
        for(int i=0;i<normal.length;i++) {
            System.out.println("�����С��׼����"+normal[i]);
            System.out.println("��׼��:"+zscore[i]);
            System.out.println("������ı�׼�֣�"+mss.get(i));
        }
        assert(getMedian(0,data) == 2.0);
        System.out.println("getMedian is right");
        assert(getVectorByColumnIdx(0,data).get(1) == 2.0);
        assert(getAbsoluteDeviation(0,data) == 2.0/3);
    }

}

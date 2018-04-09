package com.software.dm.swallow.stormy.hadoop.mapred;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author DearM
 */
public class ValueInfo implements Writable {
    private Text flag = new Text();// �ļ���Դ��־
    private Text data = new Text();// �������Ӽ���������

    public void readFields(DataInput in) throws IOException {
        this.flag.readFields(in);
        this.data.readFields(in);

    }

    public void write(DataOutput out) throws IOException {
        this.flag.write(out);
        this.data.write(out);

    }

    public String toString() {
        return "[flag=" + this.flag.toString() + ",secondPart=" + this.data.toString() + "]";
    }

    public Text getFlag() {
        return flag;
    }

    public void setFlag(Text flag) {
        this.flag = flag;
    }

    public Text getData() {
        return data;
    }

    public void setData(Text data) {
        this.data = data;
    }

}

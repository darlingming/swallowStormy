package com.software.dm.swallow.stormy.hadoop.mapred;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

public class KeyInfo implements WritableComparable<KeyInfo> {

	private Text joinKey;// ���ӹؼ���
	private Text flag;// �ļ���Դ��־
	private Text secondPart;// �������Ӽ���������

	public KeyInfo() {
		this.joinKey = new Text();
		this.flag = new Text();
		this.secondPart = new Text();
	}

	public void readFields(DataInput in) throws IOException {
		this.joinKey.readFields(in);
		this.flag.readFields(in);
		this.secondPart.readFields(in);
	}

	public void write(DataOutput out) throws IOException {
		this.joinKey.write(out);
		this.flag.write(out);
		this.secondPart.write(out);
	}

	public int compareTo(KeyInfo o) {
		return this.joinKey.compareTo(o.getJoinKey());
	}

	public Text getJoinKey() {
		return joinKey;
	}

	public void setJoinKey(Text joinKey) {
		this.joinKey = joinKey;
	}

	public Text getFlag() {
		return flag;
	}

	public void setFlag(Text flag) {
		this.flag = flag;
	}

	public Text getSecondPart() {
		return secondPart;
	}

	public void setSecondPart(Text secondPart) {
		this.secondPart = secondPart;
	}

	
	public String toString() {
		return "[flag=" + this.flag.toString() + ",joinKey=" + this.joinKey.toString() + ",secondPart=" + this.secondPart.toString() + "]";
	}
}

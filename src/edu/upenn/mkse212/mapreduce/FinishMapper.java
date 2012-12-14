package edu.upenn.mkse212.mapreduce;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;

public class FinishMapper extends Mapper<LongWritable, Text, Text, Text> {
	public void map(LongWritable key, Text value, Context context) 
			throws IOException, InterruptedException {
		String[] all = value.toString().split("\t");
		String node = all[0];
		String tag = all[2];
		Double amount = Double.parseDouble(all[3]);
		StringBuffer sb = new StringBuffer(tag);
		sb.append("\t");
		sb.append(amount);
		context.write(new Text(node), new Text(sb.toString()));
	}
}

package edu.upenn.mkse212.mapreduce;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;

public class DiffMapper extends Mapper<LongWritable, Text, Text, DoubleWritable> {
	/*
	 * Emits a key and its associated socialRank value
	 */
	public void map(LongWritable key, Text value, Context context)
			throws NumberFormatException, IOException, InterruptedException {
		String[] all = value.toString().split("\t");
		String node = all[0];
		String tag = all[2];
		Double amount = 0.0;
		if (all.length >= 4) amount = Double.parseDouble(all[3]);
		StringBuffer sb = new StringBuffer(node);
		sb.append("\t");
		sb.append(tag);
		context.write(new Text(sb.toString()), new DoubleWritable(amount));
	}
}

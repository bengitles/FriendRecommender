package edu.upenn.mkse212.mapreduce;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;

public class DiffMapper2 extends Mapper<LongWritable, Text, Text, DoubleWritable> {
	/*
	 * Maps all of the socialRank values into one reducer
	 */
	public void map(LongWritable key, Text value, Context context)
			throws NumberFormatException, IOException, InterruptedException {
		context.write(new Text("foo"),
				new DoubleWritable(Double.parseDouble(value.toString())));
	}
}

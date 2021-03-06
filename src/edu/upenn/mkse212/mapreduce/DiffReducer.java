package edu.upenn.mkse212.mapreduce;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;

public class DiffReducer extends Reducer<Text, Text, Text, DoubleWritable> {
	/*
	 * Takes in a single node with its two socialRank values from one iteration
	 * to another, emits the difference.
	 */
	public void reduce(Text key, Iterable<DoubleWritable> values, Context context)
			throws IOException, InterruptedException {
		int i = 0;
		Double[] d = new Double[2];
		for (DoubleWritable v : values) {
			d[i] = v.get();
			i++;
		}
		context.write(new Text(""), new DoubleWritable(Math.abs(d[1] - d[0])));
	}
}

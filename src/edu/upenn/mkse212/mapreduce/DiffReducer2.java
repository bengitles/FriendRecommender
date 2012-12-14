package edu.upenn.mkse212.mapreduce;
import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;


public class DiffReducer2 extends Reducer<Text, DoubleWritable, DoubleWritable, Text> {
	/*
	 * Takes in all of the differences of each node's socialRank from one iteration to
	 * another and emits the largest one.
	 */
	public void reduce(Text key, Iterable<DoubleWritable> values, Context context)
			throws IOException, InterruptedException {
		Double max = Double.MIN_VALUE;
		for(DoubleWritable v : values) {
			if(v.get() > max) max = v.get();
		}
		context.write(new DoubleWritable(max), new Text(""));
	}
}

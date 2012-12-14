package edu.upenn.mkse212.mapreduce;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;

public class InitReducer extends Reducer<Text, Text, Text, Text> {
	public void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {
		StringBuffer sb = new StringBuffer();
		int totalOutgoingConnections = 0;
		for (Text v : values) {
			totalOutgoingConnections += v.toString().split(" ").length;
		}
		context.write(new Text(key), new Text(sb.toString()));
	}
}

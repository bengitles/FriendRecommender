package edu.upenn.mkse212.mapreduce;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;

public class InitReducer extends Reducer<Text, Text, Text, Text> {
	public void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {
		StringBuffer putValue = new StringBuffer();
		for (Text v : values) {
			putValue.append(v.toString()); //Each outgoing edge
			putValue.append(" ");		   //Space-delineated
		}
		putValue.append("\t");
		putValue.append(key.toString());   //Only has own tag to start
		putValue.append("\t");
		putValue.append("1.0");			   //starts with weight of 1
		context.write(new Text(key), new Text(putValue.toString()));
	}
}

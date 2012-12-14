package edu.upenn.mkse212.mapreduce;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;

public class IterMapper extends Mapper<LongWritable, Text, Text, Text> {
	public void map(LongWritable key, Text value, Context context) 
			throws IOException, InterruptedException {
		String[] all = value.toString().split("\t");
		String node = all[0];
		String[] sendTo = all[1].split(" ");
		String tag = all[2];
		Double amount = Double.parseDouble(all[3]);
		for (int i = 0; i < sendTo.length; i++) {
			context.write(new Text(sendTo[i]),
					new Text(tag + "\t" + (amount / sendTo.length)));
		}
		context.write(new Text(node), new Text("outgoingEdges\t" + all[1]));
	}
}

package edu.upenn.mkse212.mapreduce;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;

public class InitMapper extends Mapper<LongWritable, Text, Text, Text> {
	public void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		String[] nameAndConnections = value.toString().split("\t");
		String[] connections = nameAndConnections[1].split(" ");
		for (int i = 0; i < connections.length; i++) {
			context.write(new Text(nameAndConnections[0]),
					new Text(connections[i]));
		}
	}
}
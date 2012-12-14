package edu.upenn.mkse212.mapreduce;

import java.io.IOException;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;

public class FinishReducer extends Reducer<Text, Text, Text, Text> {
	public void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {
		int size = 0;
		for(Text t : values) size++;
		Queue<String[]> sortedTags = new PriorityQueue<String[]>(size, 
				new Comparator<String[]>() {
					@Override
					public int compare(String[] arg0, String[] arg1) {
						double rank0 = Double.parseDouble(arg0[1]);
						double rank1 = Double.parseDouble(arg0[2]);
						if (rank0 < rank1) return -1;
						if (rank1 > rank0) return 1;
						return 0;
					}
		});
		for (Text t : values) sortedTags.add(t.toString().split("\t"));
		while(!sortedTags.isEmpty()) {
			String[] tagAndAmount = sortedTags.poll();
			StringBuffer sb = new StringBuffer(tagAndAmount[0]);
			sb.append("\t");
			sb.append(tagAndAmount[1]);
			context.write(new Text(key), new Text(sb.toString()));
		}
	}
}

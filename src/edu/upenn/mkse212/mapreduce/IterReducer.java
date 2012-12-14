package edu.upenn.mkse212.mapreduce;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;

public class IterReducer extends Reducer<Text, Text, Text, Text> {
	public void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {
		Map<String, Double> map = new HashMap<String, Double>();
		String outgoingEdges = "";
		System.out.println("In iter reducer");
		for (Text t : values) {
			String[] tagAndAmount = t.toString().split("\t");
			String tag = tagAndAmount[0];
			if (tag.equals("outgoingEdges")) outgoingEdges = tagAndAmount[1];
			else {
				Double amount = Double.parseDouble(tagAndAmount[1]);
				if (map.containsKey(tag)) map.put(tag, map.get(tag) + amount);
				else map.put(tag, amount);
			}
		}
		String[] recipients = outgoingEdges.split(" ");
		for (int i = 0; i < recipients.length; i++) {
			for (String tag : map.keySet()) {
				StringBuffer sb = new StringBuffer();
				sb.append(outgoingEdges);
				sb.append("\t");
				sb.append(tag);
				sb.append("\t");
				sb.append(map.get(tag));
				context.write(new Text(recipients[i]), new Text(sb.toString()));
			}
		}
	}
}

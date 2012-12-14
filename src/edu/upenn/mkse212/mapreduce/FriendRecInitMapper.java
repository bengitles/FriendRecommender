package edu.upenn.mkse212.mapreduce;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class FriendRecInitMapper extends Mapper<LongWritable, Text, Text, Text> {

}

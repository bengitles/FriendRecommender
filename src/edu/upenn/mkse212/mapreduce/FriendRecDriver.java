package edu.upenn.mkse212.mapreduce;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class FriendRecDriver {
	public static void main(String args[]) throws Exception {
		  if(args[0].equals("init")) init(args[1], args[2], args[3]);
		  if(args[0].equals("iter")) iter(args[1], args[2], args[3]);
		  if(args[0].equals("diff")) {
			  if (args.length==4) diff(args[1], args[2], args[3]);
			  else if (args.length==5) diff(args[1], args[2], args[3], args[4]);
		  }
		  if(args[0].equals("finish")) {
			  finish(args[1], args[2], args[3]);
		  }
		  if(args[0].equals("composite")) composite(args[1], args[2], args[3],
				  args[4], args[5], args[6]);
	}
	
	static void init(String inputDir, String outputDir, String numReducers) throws Exception {
		Job job = new Job();
		job.setJarByClass(FriendRecDriver.class);
		
		FileInputFormat.addInputPath(job, new Path(inputDir));
		FileOutputFormat.setOutputPath(job, new Path(outputDir));
		
		job.setMapperClass(InitMapper.class);
		job.setReducerClass(InitReducer.class);
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
	}
	
	static void iter(String inputDir, String outputDir, String numReducers) {
		
	}
	
	  static void diff(String inputDir1, String inputDir2, String outputDir)
			  throws Exception {
		  diff(inputDir1, inputDir2, outputDir, "1");
	  }
	
	static void diff(String inputDir, String iterDir1, String iterDir2, String numReducers) {
		
	}
	
	static void finish(String inputDir, String outputDir, String numReducers) {
		
	}
	
	static void composite(String inputDir, String outputDir, String intermDir1,
			  String intermDir2, String diffDir, String numReducers) {
		
	}

	
	static void deleteDirectory(String path) throws Exception {
		    Path todelete = new Path(path);
		    Configuration conf = new Configuration();
		    FileSystem fs = FileSystem.get(URI.create(path),conf);
		    
		    if (fs.exists(todelete)) 
		      fs.delete(todelete, true);
		      
		    fs.close();
	}
	  
	static double readDiffResult(String path) throws Exception {
		double diffnum = 0.0;
		Path diffpath = new Path(path);
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(URI.create(path),conf);
		
		if (fs.exists(diffpath)) {
			  FileStatus[] ls = fs.listStatus(diffpath);
			  for (FileStatus file : ls) {
					if (file.getPath().getName().startsWith("part-r-00000")) {
					  FSDataInputStream diffin = fs.open(file.getPath());
					  BufferedReader d = new BufferedReader(new InputStreamReader(diffin));
					  String diffcontent = d.readLine();
					  diffnum = Double.parseDouble(diffcontent);
					  d.close();
					}
			  }
		}
		
		fs.close();
		return diffnum;
	}
}


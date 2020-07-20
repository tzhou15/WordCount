# WordCount
import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;


public class WordCount2 {
	  
public static class Tokenizer extends Mapper<LongWritable, Text, Text, IntWritable>{

private final static IntWritable one = new IntWritable(1);
private Text word = new Text();
  
public void map(LongWritable key, Text value, Context context
                   ) throws IOException, InterruptedException {

 	String line = value.toString().replaceAll("[^a-zA-Z]","");
	StringTokenizer itr = new StringTokenizer(line);
	while (itr.hasMoreTokens()) {
       word.set(itr.nextToken().toLowerCase());
       context.write(word, one);
     }
   }
 }

public static class SumReduce extends Reducer<Text,IntWritable,Text,IntWritable> {
private IntWritable result = new IntWritable();

public void reduce(Text key, Iterable<IntWritable> values,
                      Context context
                      ) throws IOException, InterruptedException {
     int sum = 0;
     for (IntWritable val : values) {
       sum += val.get();
     }
     result.set(sum);
     context.write(key, result);
   }
 }

 public static void main(String[] args) throws Exception {
   Configuration conf = new Configuration();
   Job job = Job.getInstance(conf, "word count");
   job.setJarByClass(WordCount2.class);
   job.setMapperClass(Tokenizer.class);
   job.setCombinerClass(SumReduce.class);
   job.setReducerClass(SumReduce.class);
   job.setOutputKeyClass(Text.class);
   job.setOutputValueClass(IntWritable.class);
   FileInputFormat.addInputPath(job, new Path(args[0]));
   FileOutputFormat.setOutputPath(job, new Path(args[1]));
   System.exit(job.waitForCompletion(true) ? 0 : 1);
 }

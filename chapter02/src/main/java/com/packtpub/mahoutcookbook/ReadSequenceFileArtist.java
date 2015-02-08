/*
 * 
 */
package com.packtpub.mahoutcookbook;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;

/**
 *
 * @author hadoop-mahout
 */
public class ReadSequenceFileArtist {
    
    public static void main(String[] argvs) throws IOException 
    {
        String filename  = "/mnt/new/lastfm/sequencesfiles/part-0000";        
        Path path = new Path(filename);
        
        String outputfilename =  "/mnt/new/lastfm/sequencesfiles/dump.csv";
        
        FileWriter writer = new FileWriter(outputfilename);
        PrintWriter pw = new PrintWriter(writer);
        String newline = System.getProperty("line.separator");
        //creating header
        pw.print("key,value" + newline);
        
        //creating Sequence Writerr
        Configuration conf = new Configuration();        
        FileSystem fs = FileSystem.get(conf);
        SequenceFile.Reader reader = new SequenceFile.Reader(fs,path,conf);
        
        LongWritable key = new LongWritable();
        Text value = new Text();
        
        int i = 0;
        while (reader.next(key, value)) {
            System.out.println( "reading key:" + key.toString() + " with value " + value.toString());
            pw.print(key.toString() + "," + value.toString() + newline);
            i++;
            if (i > 100)
                break;
        }
        reader.close();
        
        pw.close();
        writer.close();
        
        
    }
}

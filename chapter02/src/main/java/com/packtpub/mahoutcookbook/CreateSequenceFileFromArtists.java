/*
 * .
 */
package com.packtpub.mahoutcookbook;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;

public class CreateSequenceFileFromArtists {
    public static void main(String[] argsx) throws FileNotFoundException, IOException 
    {
        String filename = "/mnt/new/lastfm/original/artists.txt";
        String outputfilename =  "/mnt/new/lastfm/sequencesfiles/part-0000";
        Path path = new Path(outputfilename);
        
        //opening file
        BufferedReader br = new BufferedReader(new FileReader(filename));
        //creating Sequence Writerr
        Configuration conf = new Configuration();        
        FileSystem fs = FileSystem.get(conf);
        SequenceFile.Writer writer = new SequenceFile.Writer(fs,conf,path,LongWritable.class,Text.class);
        
        
        
        String line = br.readLine();
        String[] temp;
        String tempvalue = new String();
        String delimiter = " ";
        LongWritable key = new LongWritable();
        Text value = new Text();
        long tempkey = 0;
        
        while (line != null) {
            try
            {

                line = br.readLine();    
                temp = line.split(delimiter);

                value = new Text();
                tempvalue = "";
                for (int i=1; i< temp.length;i++) {
                   tempvalue +=  temp[i] + delimiter; 
                }
                value = new Text(tempvalue);
                System.out.println("writing key/value  " + key.toString() + "/" + value.toString());

                tempkey++;
                key = new LongWritable(tempkey);
                writer.append(key,value);                
            
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
            

            
        }        
        
        writer.close();
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.packtpub.mahoutcookbook;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile;

/**
 *
 * @author hadoop-mahout
 */
public class CreateSimilarityMatrixCancer {
    
 String inputcsvFile = "/mnt/cancer/wdbc.data";
    String outputcsvFile = "/mnt/cancer/cancernumerical.csv";
    BufferedReader br = null;
    BufferedWriter wr = null;
    String line = "";
    String cvsSplitBy = ",";    
    Double matrix[][];
    Double AffinityMatrix[][];
    
    private void ETL2Numeric() {
  try {
    br = new BufferedReader(new FileReader(inputcsvFile));     
    wr = new BufferedWriter(new FileWriter(outputcsvFile)); 
    
    while ((line = br.readLine()) != null) {
      String[] observation = line.split(cvsSplitBy);
      String[] observation2write = observation;
      observation2write[1] = "m".equals(observation[1].toLowerCase()) ? "1": "0";
      wr.write(line + "\r\n");
    }
  } catch (Exception e) {
    e.printStackTrace();
  } finally {
    if (br != null) {
      try {
        br.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
  System.out.println("Done");        
}

private void createMatrix() {
  try {
    br = new BufferedReader(new FileReader(inputcsvFile));            
    int k = 0;
    while ((line = br.readLine()) != null) {
      String[] observation = line.split(cvsSplitBy);
      for (int j= 0; j < observation.length; j++)
      {
        matrix[k][j] =
            Double.parseDouble(observation[j]);
      }
      k++;
    }
    for (int i = 0; i < matrix.length; i++)
    {               
      double d = 0;
      int start  = i+1;
      for (int j = i+1; j < matrix.length-1; j++)
      {
        d = MeanErrorDistance(matrix[i],matrix[j]);
      }
      if (d < 0.1)
      {
        AffinityMatrix[i][start] = d;
        AffinityMatrix[start][i] = d;
      }
    }
    
  } catch (Exception e) {
    e.printStackTrace();
  } finally {
    if (br != null) {
      try {
      br.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
      }
  }
  System.out.println("Done");        	
}

private void SaveSimilarityMatrix() throws IOException {
  Path path = new Path("/mnt/cancer/output/cancersequenced");
  org.apache.hadoop.fs.RawLocalFileSystem fs = new org.apache.hadoop.fs.RawLocalFileSystem();
  Configuration conf = new Configuration();
  SequenceFile.Writer writer = new SequenceFile.Writer(fs, conf, path, IntWritable.class, DoubleWritable.class);
  for (int i = 0; i < AffinityMatrix.length;i++)        
    for (int j = 0; j < AffinityMatrix[i].length;j++)
    writer.append( new IntWritable(i), new DoubleWritable(AffinityMatrix[i][j]));      
  System.out.println("Done");        
}

    private double MeanErrorDistance(Double[] aDouble, Double[] aDouble0) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
   
    
}

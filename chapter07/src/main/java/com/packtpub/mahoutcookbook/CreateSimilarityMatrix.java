/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.packtpub.mahoutcookbook;

import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author hadoop-mahout
 */
public class CreateSimilarityMatrix {
    public static void main(String[] argv) throws IOException 
    {
       double[][] smatrix = new double[1000][1000];
       String filePath = new String("/mnt/new/spectral/input/matrix.csv");
       

       //populating the matrix by rows and columns
       for (int i=0; i < 1000; i++) 
       {
            for (int j=i; j < 1000; j++) 
            {
                smatrix[i][j] = (double)Math.round(Math.random()*100)/100;
                smatrix[j][i] = smatrix[i][j];
                
            }
           
       }
       
       //saving as a csv
       FileWriter fw = new FileWriter(filePath);
       
       for (int i=0; i < 1000; i++) 
       {
            for (int j=0; j < 1000; j++) 
            {
                
                fw.write( Integer.toString(i) + "," + Integer.toString(j) + "," +  Double.toString(smatrix[i][j])  + "\n");
            }
           fw.flush();
       }
       fw.close();
       
    }    
}

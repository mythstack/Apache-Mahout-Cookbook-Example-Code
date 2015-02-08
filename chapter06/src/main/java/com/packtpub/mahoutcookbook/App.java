package com.packtpub.mahoutcookbook;

import java.io.IOException;
import org.apache.hadoop.fs.Path;
import org.apache.mahout.clustering.canopy.CanopyDriver;
import org.apache.mahout.common.distance.EuclideanDistanceMeasure;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException, InterruptedException, ClassNotFoundException
    {
        //setting all parameters
        String inputFileName = new String("/mnt/canopy/sequencefile/synthetic_control.seq");
        String outputFileName = new String("/mnt/canopy/output/output.model");
                
        Path inputPath = new Path(inputFileName);
        Path outputPath = new Path(outputFileName);
        EuclideanDistanceMeasure measure = new EuclideanDistanceMeasure();
        
        double t1;
        double t2;
        double clusterClassificationThreshold;
        
        t1 = 50;
        t2 = 80;
        clusterClassificationThreshold = 3;
        boolean runSequential = true;

        CanopyDriver.run(inputPath , outputPath,measure,t1,t2,runSequential,clusterClassificationThreshold,runSequential);

    }
}

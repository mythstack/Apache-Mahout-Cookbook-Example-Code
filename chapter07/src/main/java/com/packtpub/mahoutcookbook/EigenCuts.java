/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.packtpub.mahoutcookbook;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.List;
import org.apache.hadoop.conf.Configuration;

import org.apache.hadoop.fs.Path;
import org.apache.mahout.clustering.spectral.common.AffinityMatrixInputJob;
import org.apache.mahout.clustering.spectral.common.MatrixDiagonalizeJob;
import org.apache.mahout.clustering.spectral.common.VectorMatrixMultiplicationJob;
import org.apache.mahout.clustering.spectral.eigencuts.EigencutsAffinityCutsJob;
import org.apache.mahout.math.Vector;
import org.apache.mahout.math.decomposer.lanczos.LanczosState;
import org.apache.mahout.math.hadoop.DistributedRowMatrix;
import org.apache.mahout.math.hadoop.decomposer.DistributedLanczosSolver;

/**
 *
 * @author hadoop-mahout
 */
public class EigenCuts {
    public static void main(String[] args) throws Exception
    {
        String inputSimilarityMatrixFilePath;
        String  outputFilePath;
        inputSimilarityMatrixFilePath = "/mnt/new/spectral/input/matrix.csv";
        outputFilePath = "/mnt/new/spectral/outputn/output";
        Path input = new Path(inputSimilarityMatrixFilePath);
        Path output = new Path(outputFilePath);
        int dimensions = 100;
        int eigenrank = 1;
        int OVERSHOOT_MULTIPLIER = 2;
        Configuration conf = new Configuration();


        DistributedRowMatrix A = AffinityMatrixInputJob.runJob(input, output,dimensions);
        Vector D = MatrixDiagonalizeJob.runJob(A.getRowPath(), dimensions);
        long numCuts;
        do {
          // first three steps are the same as spectral k-means:
          // 1) calculate D from A
          // 2) calculate L = D^-0.5 * A * D^-0.5
          // 3) calculate eigenvectors of L
          DistributedRowMatrix L =
            VectorMatrixMultiplicationJob.runJob(A.getRowPath(), D,
              new Path(output, "laplacian-" + (System.nanoTime() & 0xFF)));
          L.setConf(conf);
          // eigendecomposition (step 3)
          int overshoot = (int) ((double) eigenrank * OVERSHOOT_MULTIPLIER);
          //LanczosState state; state = new LanczosState(L, eigenrank,DistributedLanczosSolver.getInitialVector(L));
          //DistributedRowMatrix U = performEigenDecomposition(conf, L, state, eigenrank, overshoot, output);
          //U.setConf(new Configuration());
          List<Double> eigenValues = Lists.newArrayList();
          for (int i = 0; i < eigenrank; i++) {
            //eigenValues.set(i, state.getSingularValue(i));
          }
          // here's where things get interesting: steps 4, 5, and 6 are unique
          // to this algorithm, and depending on the final output, steps 1-3
          // may be repeated as well
          // helper method, since apparently List and Vector objects don't play nicely
          //Vector evs = listToVector(eigenValues);
          // calculate sensitivities (step 4 and step 5)
          Path sensitivities = new Path(output, "sensitivities-" + (System.nanoTime() & 0xFF));
          //EigencutsSensitivityJob.runJob(evs, D, U.getRowPath(), halflife, tau, median(D), epsilon, sensitivities);
          // perform the cuts (step 6)
          input = new Path(output, "nextAff-" + (System.nanoTime() & 0xFF));
          numCuts = EigencutsAffinityCutsJob.runjob(A.getRowPath(), sensitivities, input, conf);
          // how many cuts were made?
          if (numCuts > 0) {
          // recalculate A
          A = new DistributedRowMatrix(input,
                         new Path(output,
         Long.toString(System.nanoTime())), dimensions, dimensions);
                A.setConf(new Configuration());
          }
        } while (numCuts > 0);
        
    }
}

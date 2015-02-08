package com.packtpub.mahoutcookbook;



import com.cloudera.sqoop.Sqoop;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import com.cloudera.sqoop.SqoopOptions;
import com.cloudera.sqoop.cli.ToolOptions;
import com.cloudera.sqoop.tool.SqoopTool;
import com.cloudera.sqoop.util.OptionsFileUtil;



public class App 
{
    public static void main( String[] args ) throws Exception
    {
        String actioname = "import-all-tables";
        
        Configuration conf = new Configuration();
        
        Configuration pluginConf = SqoopTool.loadPlugins(conf);
        
        SqoopTool tool = SqoopTool.getTool(actioname);


        //Sqoop sqoop;sqoop = new Sqoop(tool, pluginConf);
        Sqoop sqoop = new Sqoop(null, conf);
        
        
        
        
        String [] toolArgs;
        toolArgs = sqoop.stashChildPrgmArgs(args);
        int t = ToolRunner.run(sqoop.getConf(), sqoop, toolArgs);        
        
    /*
        
        
        String toolName = expandedArgs[0];
        Configuration conf = new 
        Configuration pluginConf = SqoopTool.loadPlugins(conf);
        SqoopTool tool = SqoopTool.getTool(toolName);
        if (null == tool) {
            System.err.println("No such sqoop tool: " + toolName
          + ". See 'sqoop help'.");
      return 1;
    }


    Sqoop sqoop = new Sqoop(tool, pluginConf);
    return runSqoop(sqoop,
        Arrays.copyOfRange(expandedArgs, 1, expandedArgs.length));        
        * */
    }
}

package primeService.util;

import java.io.IOException;

public class MyLogger{

    // FIXME: Add more enum values as needed for the assignment
    public static enum DebugLevel { INFO,DEBUG
                                   };
                                
    private static DebugLevel debugLevel;
    FileProcessor fp=new FileProcessor();
    private MyLogger(){}
    private static MyLogger logger;
    public static MyLogger getLogger()
    {
        if(logger==null){
            synchronized(MyLogger.class){
                if(logger==null){
                    logger=new MyLogger();
                }
            }
        }
        return logger;
    }

    public void startFileWriter(String filepath){
        fp.errorFilePath=filepath;
        try {
            fp.createWriter();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // FIXME: Add switch cases for all the levels
    public void setDebugValue (int levelIn) {
	switch (levelIn) {
	case 2: debugLevel = DebugLevel.INFO; break;
	case 1: debugLevel = DebugLevel.DEBUG; break;
	//default: debugLevel = DebugLevel; break;
	}
    }

    public  void setDebugValue (DebugLevel levelIn) {
	debugLevel = levelIn;
    }

    public  void writeMessage (String     message  ,
                                     DebugLevel levelIn ) {
	if (levelIn == debugLevel)
	    fp.writeError(message);
    }

   public void closeWriter(){
    fp.closeWriter();
   }
} 

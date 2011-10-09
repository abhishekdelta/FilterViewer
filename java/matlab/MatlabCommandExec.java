package matlab;
import com.mathworks.jmi.*;
public class MatlabCommandExec implements Runnable {
    String command;
  //  boolean useCallback,eval;
    Object[] args;
    Matlab matlab;
    boolean returnResult;
    MatlabInterface matlabinterface;
    public MatlabCommandExec(Matlab matlab,String Command,boolean returnResult) 
    {
    	System.out.println("MATLAB Executing : "+Command);
    	this.matlab=matlab;
        command = Command;
        if(returnResult==true)
        	System.out.println("Arguments Missing!");
      //  this.useCallback = useCallback;
       // eval=true;
        this.args=null;
        this.returnResult=false;
    }
    public MatlabCommandExec(Matlab matlab,String Command,Object[] args,boolean returnResult,MatlabInterface matlabinterface) 
    {
    	this.matlabinterface=matlabinterface;
    	System.out.println("MATLAB Executing : "+Command);
    	this.matlab=matlab;
        command = Command;
        
      //  this.useCallback = useCallback;
       // eval=true;
        this.args=args;
        this.returnResult=true;
    }
  /*  public Object blockingFeval(String Command, Object[] args) throws InterruptedException {
        returnVal = new String("noReturnValYet");
        Matlab.whenMatlabReady(new MatlabBlockingFevalCommand(Command, args, useCallback, this));
        if (returnVal.equals(new String("noReturnValYet"))) {
            synchronized(returnVal){
                 returnVal.wait();
            }
        }
        return returnVal;
    }*/

   /* protected Object useMatlabCommandCallback(String command, Object[] args)
    {
        int numArgs = (args==null)? 0 : args.length;
        Object newArgs[] = new Object[numArgs+1] ;
        newArgs[0]=command;
        for(int i=0;i<numArgs;i++)
        {
            newArgs[i+1] = args[i];
        }
        try
        {
            return Matlab.mtFevalConsoleOutput(new String("matlabControlcb"), newArgs, 0);
        }
        catch(Exception e)
        {
	              e.printStackTrace();
	              return null;
        }
    }*/
    public void setReturnVal(Object val) {
        synchronized(matlabinterface.returnArgs){
            Object oldVal = matlabinterface.returnArgs;
            matlabinterface.returnArgs = val;
            oldVal.notifyAll();
        }
    }
    public void run() 
    {
        try 
        {
         /*   if(useCallback)
            {
                if(returnResult)
                	setReturnVal(useMatlabCommandCallback(command, args));
                else useMatlabCommandCallback(command, args);
                
            }
            else if(eval)
            {*/
                if(returnResult)
                {
                //	System.out.println(command);
    			// 	for(int i=0;i<args.length;i++) System.out.print(args[i]+",");
                	setReturnVal(Matlab.mtFevalConsoleOutput(command, args, 0));
                }
                else matlab.evalConsoleOutput(command);
            
            //}
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
}
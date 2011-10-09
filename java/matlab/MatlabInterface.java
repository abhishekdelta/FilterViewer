package matlab;
import com.mathworks.jmi.*;
public class MatlabInterface{

		Matlab matlab;
		String command;
		//boolean useCB;
		boolean returnVal;
		Object args[];
		Object returnArgs;
		public MatlabInterface()
		{
			matlab=new Matlab();
		//	useCB=false;
			returnVal=false;
		}
		private void setReturnResults(boolean returnVal)
		{
			this.returnVal=returnVal;
		}
		private void setArguments(Object[] args)
		{
			this.args=args;
		}
		public void execNoReturn(String command)
		{
			setReturnResults(false);
			System.out.println("MatlabInterface : Starting Communication with MATLAB");
			this.command=command;
			System.out.println(command);
			Matlab.whenMatlabReady(new MatlabCommandExec(matlab,command,false));
		}
		 public Object execReturn(String Command, Object[] args) throws InterruptedException
		 {
			 	setArguments(args);
			 	setReturnResults(true);
			 	System.out.println("MatlabInterface : Starting Communication with MATLAB");
			 	this.command=Command;
			 
		        returnArgs = new String("noReturnValYet");
		        Matlab.whenMatlabReady(new MatlabCommandExec(matlab,command, args, true,this));
		        if (returnArgs.equals(new String("noReturnValYet"))) {
		            synchronized(returnArgs){
		            	returnArgs.wait();
		            }
		        }
		        return returnArgs;
		    }
}
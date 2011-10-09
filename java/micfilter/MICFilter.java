package micfilter;

import gui.GUIMainThread;

public class MICFilter {
	public static void main(String args[])
	{
		//MatlabInterface mi=new MatlabInterface();
		//mi.exec("help");
		MIC.maxProjects=15;
		MIC.nextProjectIndex=0;
		MIC.CurrentProjectNames=new String[MIC.maxProjects] ;
		MIC.CurrentFilterTypes=new String[MIC.maxProjects];
		MIC.CurrentFilterIDs=new int[MIC.maxProjects];
		MIC.CurrentFilterInputs=new String[MIC.maxProjects][];
		MIC.CurrentProjectPaths=new String[MIC.maxProjects];
		System.out.println("Starting FilterViewer 0.01");
		GUIMainThread gui=new GUIMainThread();
		gui.start();
	}
}

package gui;

public class GUIMainThread extends Thread{
	public GUIMainThread()
	{
		System.out.println("GUIMainThread Initialized!");
	}
	public void run()
	{
		new GUI("MIC Filter Viewer v0.01");
		System.out.println("GUIMainThread Destroyed!");
	}

}

package gui;

import micfilter.ConfigFile;
import micfilter.MIC;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class OpenProjectWizard {
	Shell subShell;
	String title;
	Label label1,label2,label3,label4,label5;
	Text text1,text2,text3;
	GridData text1GD,list1GD;
	Combo list1,list2;
	Button button1,button2,button3;
	GridLayout gLay;
	FileDialog file1;

	ConfigFile openfile;
	int filterID;
	String projectName,filterType,inputs[],projectPath;
	GUI gui;
	public OpenProjectWizard(Shell MainShell,GUI gui)
	{
		subShell=new Shell(MainShell);
		title="Open Project Wizard";
		System.out.println(title+" Initialized!");
		this.gui=gui;
	}
	public void open()
	{
		
		subShell.setBackground(GUI.BGCOLOR);
		subShell.setBackgroundMode(SWT.INHERIT_FORCE);
		subShell.setForeground(GUI.FGCOLOR);
		subShell.setFont(GUI.FONTSTYLE);
		subShell.setMinimumSize(500,150);
		
		gLay=new GridLayout();
		
		initialize();
		
		subShell.setText(title);
		subShell.open();
		while(!subShell.isDisposed())
		{	
			if(!subShell.getDisplay().readAndDispatch())
				subShell.getDisplay().sleep();
		}
	
		System.out.println(title+" Destroyed!");
		
	}
	void initialize()
	{
	
		gLay.numColumns=3;
		gLay.makeColumnsEqualWidth=false;
		gLay.marginTop=20;
		gLay.marginLeft=20;
		gLay.marginRight=20;
		gLay.marginBottom=20;
		
		subShell.setLayout(gLay);
		
		label1=new Label(subShell,SWT.NORMAL);
		label1.setText("Project Location : ");
		GUI.addStyleCenter(label1);
		
		text1=new Text(subShell,SWT.SINGLE|SWT.BORDER);
		GUI.addStyleField(text1);
		
		button1=new Button(subShell,SWT.PUSH);
		GUI.addStyleCenter(button1);
		button1.setText("Browse");
		((GridData)button1.getLayoutData()).widthHint=100;
		button2=new Button(subShell,SWT.PUSH);
		GUI.addStyleRight(button2);
		button2.setText("Open");
		((GridData)button2.getLayoutData()).widthHint=100;
		((GridData)button2.getLayoutData()).verticalIndent=20;
		button3=new Button(subShell,SWT.PUSH);
		GUI.addStyleLeft(button3);
		button3.setText("Cancel");
		((GridData)button3.getLayoutData()).widthHint=100;
		((GridData)button3.getLayoutData()).verticalIndent=20;
		
		button1.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent e)
			{
				file1=new FileDialog(subShell,SWT.OPEN);
				file1.setFilterExtensions(MIC.ProjectExts);
				file1.setFilterPath(MIC.ProjectPath);
				file1.open();
				text1.setText(file1.getFilterPath()+"\\"+file1.getFileName());
				
				
			}
		});
		button2.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent e)
			{
				openProjectFile(text1.getText());
				
			}
		});
		button3.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent e)
			{
				destroy();
			}
		});
	
		
		subShell.pack();
		subShell.setLocation(GUI.centralize(subShell.getDisplay().getPrimaryMonitor().getBounds(),subShell));
	}
	void openProjectFile(String File)
	{
		System.out.println(File);
		openfile=new ConfigFile(File);
		if(openfile.exists()==false)
		{
			MessageBox mb=new MessageBox(subShell,SWT.ICON_ERROR|SWT.RETRY|SWT.CANCEL);
			mb.setMessage("Invalid Project File!");
			int result=mb.open();
			if(result==SWT.RETRY)
				openProjectFile(File);
			return;
		}
		if(MIC.nextProjectIndex==MIC.maxProjects)
		{
			String msg="Maximum "+MIC.maxProjects+" projects can be opened at the same time.";
			MessageBox mb=new MessageBox(subShell,SWT.ICON_ERROR|SWT.OK);
			mb.setMessage(msg);
			mb.open();
			return;
		}
		projectPath=openfile.fullPath();
		projectName=openfile.getAttribute("ProjectName");
		filterType=openfile.getAttribute("FilterType");

		filterID=Integer.parseInt(openfile.getAttribute("FilterID"));
		inputs=new String[MIC.FILTERSPECS[filterID].length];
		for(int i=0;i<MIC.FILTERSPECS[filterID].length;i++)
		{
			inputs[i]=openfile.getAttribute(MIC.FILTERSPECS[filterID][i]);
		}
		
		for(int i=0;i<MIC.nextProjectIndex;i++)
			if(MIC.CurrentProjectNames[i].equals(projectName))
			{
				gui.selectProjectTab(i);
				destroy();
				return;
			}
		MIC.CurrentProjectNames[MIC.nextProjectIndex]=projectName;
		MIC.CurrentFilterTypes[MIC.nextProjectIndex]=filterType;
		MIC.CurrentFilterIDs[MIC.nextProjectIndex]=filterID;
		MIC.CurrentFilterInputs[MIC.nextProjectIndex]=inputs;
		MIC.CurrentProjectPaths[MIC.nextProjectIndex]=projectPath;

		gui.newProjectTab();
		destroy();
	}
	void destroy()
	{
		subShell.dispose();
	}
	
}

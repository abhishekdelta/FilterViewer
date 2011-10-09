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

public class SaveProjectWizard {
	Shell subShell;
	String title;
	Label label1,label2,label3,label4,label5;
	Text text1,text2,text3;
	GridData text1GD,list1GD;
	Combo list1,list2;
	Button button1,button2,button3;
	GridLayout gLay;
	FileDialog file1;
	int curProjectIndex;
	ConfigFile project;
	String newProjectName;
	public SaveProjectWizard(Shell MainShell,int curProjectIndex)
	{
		subShell=new Shell(MainShell);
		title="Save Project Wizard";
		System.out.println(title+" Initialized!");
		this.curProjectIndex=curProjectIndex;
	}
	public void open()
	{
		
		subShell.setBackground(GUI.BGCOLOR);
		subShell.setBackgroundMode(SWT.INHERIT_FORCE);
		subShell.setForeground(GUI.FGCOLOR);
		subShell.setFont(GUI.FONTSTYLE);
		
		
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
		subShell.setText(title);
		subShell.setMinimumSize(500,150);
		gLay.numColumns=3;
		gLay.makeColumnsEqualWidth=false;
		gLay.marginTop=20;
		gLay.marginLeft=20;
		gLay.marginRight=20;
		gLay.marginBottom=20;
		
		subShell.setLayout(gLay);
		
		label1=new Label(subShell,SWT.NORMAL);
		label1.setText("Save Location : ");
		GUI.addStyleCenter(label1);
		
		text1=new Text(subShell,SWT.SINGLE|SWT.BORDER);
		GUI.addStyleField(text1);
		
		button1=new Button(subShell,SWT.PUSH);
		GUI.addStyleCenter(button1);
		button1.setText("Browse");
		((GridData)button1.getLayoutData()).widthHint=100;
		button2=new Button(subShell,SWT.PUSH);
		GUI.addStyleRight(button2);
		button2.setText("Save");
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
				file1=new FileDialog(subShell,SWT.SAVE);
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
				newProjectName=file1.getFileName().substring(0, file1.getFileName().length()-4);
				saveProjectFile(file1.getFilterPath()+"\\"+file1.getFileName());
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
	void saveProjectFile(String File)
	{
		System.out.println("Saving As Project at "+File);
		String filterType=MIC.CurrentFilterTypes[curProjectIndex];
		int filterID=MIC.CurrentFilterIDs[curProjectIndex];
		project=new ConfigFile(File);
		if(project.exists())
		{
			MessageBox mb=new MessageBox(subShell,SWT.ICON_WARNING|SWT.YES|SWT.NO);
			String msg="Project already exists! Do you want to overwrite ?";
			mb.setMessage(msg);
			int result=mb.open();
			if(result==SWT.NO)
			{
				return;
			}
		}
		project.setAttribute("ProjectName", newProjectName);
		project.setAttribute("FilterType", filterType);
		project.setAttribute("FilterID", String.valueOf(filterID));
		for(int i=0;i<MIC.FILTERSPECS[filterID].length;i++)
		{
			project.setAttribute(MIC.FILTERSPECS[filterID][i], MIC.CurrentFilterInputs[curProjectIndex][i]);
		}
		destroy();
	}
	void destroy()
	{
		subShell.dispose();
	}
	
}

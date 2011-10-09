package gui;


import micfilter.ConfigFile;
import micfilter.MIC;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class NewProjectWizard {
	Shell subShell;
	String title;
	Label label1,label2,labelFil1,inpLabels[];
	Text text1,text2,inputFil1,inpTexts[];
	

	Combo list1,list2;
	Button button1,button2,button3,button4,button5;
	GridLayout gLay;
	
	String projName,filterType;
	int filterID;
	GUI gui;
	ConfigFile project;
	public NewProjectWizard(Shell MainShell,GUI gui)
	{
		subShell=new Shell(MainShell);
		title="New Project Wizard";
		System.out.println(title+" Initialized!");
		filterID=-1;
		this.gui=gui;
	}
	public void open()
	{
		/*
		 * Step 1 : Ask the Project Name and Select the Filter
		 */
		
		subShell.setBackground(GUI.BGCOLOR);
		subShell.setBackgroundMode(SWT.INHERIT_FORCE);
		subShell.setForeground(GUI.FGCOLOR);
		subShell.setFont(GUI.FONTSTYLE);
		subShell.setMinimumSize(200,100);
		
		gLay=new GridLayout();
		
		initializeStep1();
		
		subShell.setText(title);
		subShell.open();
		while(!subShell.isDisposed())
		{	
			if(!subShell.getDisplay().readAndDispatch())
				subShell.getDisplay().sleep();
		}
	
		System.out.println(title+" Destroyed!");
		
	}
	void initializeStep1()
	{
		subShell.setText(title+" : Step 1 : Project Details");
		gLay.numColumns=2;
		gLay.makeColumnsEqualWidth=false;
		gLay.marginTop=20;
		gLay.marginLeft=20;
		gLay.marginRight=20;
		gLay.marginBottom=20;
		
		subShell.setLayout(gLay);
		
		
		label1=new Label(subShell,SWT.NORMAL);
		label1.setText("Project Name :");
		GUI.addStyleLeft(label1);
		
		text1=new Text(subShell,SWT.SINGLE|SWT.BORDER);
		if(projName==null)
			projName="Project1";
		text1.setText(projName);
		GUI.addStyleField(text1);
		
		label2=new Label(subShell,SWT.NORMAL);
		label2.setText("Filter Type :");
		GUI.addStyleLeft(label2);
		
		
		list1=new Combo(subShell,SWT.DROP_DOWN|SWT.BORDER);
		for(int i=0;i<MIC.FILTERTYPES.length;i++)
		{
			list1.add(MIC.FILTERTYPES[i]);
		}
		if(filterID==-1)
			list1.select(0);
		else list1.select(filterID);
		
		GUI.addStyleField(list1);
		
		button1=new Button(subShell,SWT.PUSH);
		button1.setText("Next");
		button1.setEnabled((projName==null?false:true));
		GUI.addStyleLeft(button1);
		((GridData)button1.getLayoutData()).widthHint=100;
		((GridData)button1.getLayoutData()).verticalIndent=30;
		button2=new Button(subShell,SWT.PUSH);
		button2.setText("Cancel");
		GUI.addStyleLeft(button2);
		((GridData)button2.getLayoutData()).widthHint=100;
		((GridData)button2.getLayoutData()).verticalIndent=30;
		
		text1.addModifyListener(new ModifyListener()
		{
			public void modifyText(ModifyEvent e)
			{
				if(((Text)e.getSource()).getText()!="")
				{
					button1.setEnabled(true);
				}
				else
				{
					button1.setEnabled(false);
				}
			}
		});
		button1.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent e)
			{
				if((new ConfigFile("projects/"+text1.getText())).exists())
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
					
				projName=text1.getText();
				filterType=list1.getText();
				filterID=list1.getSelectionIndex();
				disposeStep1();
				initializeStep2();
			}
		});
		button2.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent e)
			{
				destroy();
			}
		});

		subShell.pack();
		subShell.setLocation(GUI.centralize(subShell.getDisplay().getPrimaryMonitor().getBounds(),subShell));

		
	}
	void disposeStep1()
	{
		label1.dispose();
		label2.dispose();
		text1.dispose();
		list1.dispose();
		button1.dispose();
		button2.dispose();
	}
	void initializeStep2()
	{
		subShell.setText(title+" : Step 2 : Filter Specifications");
		
		gLay.numColumns=3;
		gLay.makeColumnsEqualWidth=false;
		gLay.marginTop=20;
		gLay.marginLeft=20;
		gLay.marginRight=20;
		gLay.marginBottom=20;
		
		subShell.setLayout(gLay);
		
		
		getFilterInputs();
		
		
		button3=new Button(subShell,SWT.PUSH);
		button3.setText("Back");
		GUI.addStyleLeft(button3);
		((GridData)button3.getLayoutData()).widthHint=100;
		((GridData)button3.getLayoutData()).verticalIndent=30;
		button4=new Button(subShell,SWT.PUSH);
		button4.setText("Next");
		GUI.addStyleLeft(button4);
		((GridData)button4.getLayoutData()).widthHint=100;
		((GridData)button4.getLayoutData()).verticalIndent=30;
		button5=new Button(subShell,SWT.PUSH);
		button5.setText("Cancel");
		GUI.addStyleLeft(button5);
		((GridData)button5.getLayoutData()).widthHint=100;
		((GridData)button5.getLayoutData()).verticalIndent=30;
	
		button3.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent e)
			{
				disposeStep2();
				initializeStep1();
			}
		});
		button4.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent e)
			{
				createProject();
				
			}
		});
		button5.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent e)
			{
				destroy();
			}
		});


		subShell.pack();
		subShell.setLocation(GUI.centralize(subShell.getDisplay().getPrimaryMonitor().getBounds(),subShell));
		
	}
	void disposeStep2()
	{
		disposeFilterInputs();
		button3.dispose();
		button4.dispose();
		button5.dispose();
		
	}
	void getFilterInputs()
	{
		inpLabels=new Label[MIC.FILTERSPECS[filterID].length];
		inpTexts=new Text[MIC.FILTERSPECS[filterID].length];
		
		for(int i=0;i<MIC.FILTERSPECS[filterID].length;i++)
		{
			inpLabels[i]=new Label(subShell,SWT.NORMAL);
			inpLabels[i].setText(MIC.FILTERSPECSTEXT[filterID][i]);
			GUI.addStyleLeft(inpLabels[i]);
			
			inpTexts[i]=new Text(subShell,SWT.SINGLE|SWT.BORDER);
			GUI.addStyleField(inpTexts[i]);
			((GridData)inpTexts[i].getLayoutData()).horizontalSpan=2;
		}
	}
	void createProject()
	{
		System.out.println("Creating Project");
		if(MIC.nextProjectIndex==MIC.maxProjects)
		{
			String msg="Maximum "+MIC.maxProjects+" projects can be opened at the same time.";
			MessageBox mb=new MessageBox(subShell,SWT.ICON_ERROR|SWT.OK);
			mb.setMessage(msg);
			mb.open();
			return;
		}
		MIC.CurrentProjectNames[MIC.nextProjectIndex]=projName;
		MIC.CurrentFilterTypes[MIC.nextProjectIndex]=filterType;
		MIC.CurrentFilterIDs[MIC.nextProjectIndex]=filterID;
		MIC.CurrentFilterInputs[MIC.nextProjectIndex]=new String[MIC.FILTERSPECS[filterID].length];
		project=new ConfigFile(MIC.DEFAULTPATH+projName);
		MIC.CurrentProjectPaths[MIC.nextProjectIndex]=project.fullPath();
		project.setAttribute("ProjectName", projName);
		project.setAttribute("FilterType", filterType);
		project.setAttribute("FilterID", String.valueOf(filterID));
		for(int i=0;i<MIC.FILTERSPECS[filterID].length;i++)
		{
			project.setAttribute(MIC.FILTERSPECS[filterID][i], inpTexts[i].getText());
			MIC.CurrentFilterInputs[MIC.nextProjectIndex][i]=inpTexts[i].getText();
		
		}
		gui.newProjectTab();
		destroy();
	}
	void disposeFilterInputs()
	{
		for(int i=0;i<MIC.FILTERSPECS[filterID].length;i++)
		{
			inpLabels[i].dispose();
			inpTexts[i].dispose();
		}
	}
	void destroy()
	{
		subShell.dispose();
	}
	

}

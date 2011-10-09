package gui;




import matlab.MatlabInterface;
import micfilter.ConfigFile;
import micfilter.MIC;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;


public class GUI {
	public Shell mainShell;
	
	MatlabInterface matlab;
	
	String title;
	
	Group mainGroup,editGroup,infoGroup,buttonsGroup;
	
	Menu mainMenu,projectMenu,editMenu,optionsMenu,aboutMenu;
	MenuItem menuItem_Project,menuItem_Edit,menuItem_Options,menuItem_About;
	MenuItem menuItem_New,menuItem_Open,menuItem_Save,menuItem_SaveAs,menuItem_Exit;
	MenuItem menuItem_Cut,menuItem_Copy,menuItem_Paste;
	MenuItem menuItem_Settings;
	MenuItem menuItem_Info,menuItem_Credits,menuItem_Version;
	
	Label label1,inpLabels[],infoLabels[];
	Text inpTexts[][];
	
	boolean retValue;
	
	Table infoTable;
	TableColumn tableColumn1,tableColumn2;
	TableItem tableItems[][];
	
	
	TabFolder tabFolder;
	TabItem[] tabItems;
	TabItem curTab;
	Composite[] tabComposites;
	Composite curComposite,filterInfo,projectInfo;
	ExpandBar expandBar;
	ExpandItem[] expandItems;
	int curProjectIndex;
	ExpandItem expandItem1,expandItem2;
	Label projNameLabel,filterTypeLabel,filterInfoLabel,projPathLabel;
	Button updateButtons[],defaultButtons[],clearButtons[],filterButtons[];

	/*
	 * GUI Constants and Static Functions
	 */
	public static final Display MainDisplay=new Display();
	public static final int OK = 1;
	public static final int OKCANCEL = 1;
	public static final int YESNO = 1;
	public static final int YESNOCANCEL = 1;
	public static final Font FONTSTYLE=new Font(MainDisplay,"Verdana",10,SWT.NORMAL);
	public static final Color BGCOLOR=new Color(MainDisplay,0,0,0);
	public static final Color SHELLBGCOLOR=new Color(MainDisplay,0,0,0);
	public static final Color FGCOLOR=new Color(MainDisplay,255,255,255);
	public static final Color WHITECOLOR=new Color(MainDisplay,0xFF,0xFF,0xFF);
	public static final Color REDCOLOR=new Color(MainDisplay,0xFF,0,0);

	public GUI(String title)
	{
		/*
		 * Initializing the Display, Shell and customs Fonts and Colors
		 */
		System.out.println("Loading GUI");
		
		mainShell=new Shell(MainDisplay);
		matlab=new MatlabInterface();
		curProjectIndex=-1;
		this.title=title;
		initWidgets();
		mainShell.setMenuBar(mainMenu);
		mainShell.setMinimumSize(500,500);
		mainShell.setMaximized(true);
		mainShell.setLocation(GUI.centralize(MainDisplay.getPrimaryMonitor().getBounds(),mainShell));
		mainShell.setText(title);
		mainShell.open();
	
		while(!mainShell.isDisposed())
		{	
			if(!MainDisplay.readAndDispatch())
				MainDisplay.sleep();
		}
	
		System.out.println(title+" Destroyed!");
	}
	

	
	
	void initWidgets()
	{
		/*
		 * Setting up the MainShell and GridLayout
		 */
		mainShell.setText(title);
		mainShell.setBackground(GUI.SHELLBGCOLOR);
		mainShell.setBackgroundMode(SWT.INHERIT_FORCE);
		mainShell.setForeground(GUI.FGCOLOR);
		mainShell.setFont(GUI.FONTSTYLE);

		
		GridLayout GLay=new GridLayout();
		GLay.numColumns=2;
		GLay.marginTop=20;
		GLay.marginLeft=20;
		GLay.marginRight=20;
		GLay.marginBottom=20;
		
		mainShell.setLayout(GLay);
		
		initMenuBar();
		
		tabFolder=new TabFolder(mainShell,SWT.TOP);
		tabItems=new TabItem[MIC.maxProjects];
		tabComposites=new Composite[MIC.maxProjects];
		tableItems=new TableItem[MIC.maxProjects][];
		inpTexts=new Text[MIC.maxProjects][];
		updateButtons=new Button[MIC.maxProjects];
		defaultButtons=new Button[MIC.maxProjects];
		clearButtons=new Button[MIC.maxProjects];
		filterButtons=new Button[MIC.maxProjects];
		tabFolder.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e)
			{
				curProjectIndex=tabFolder.getSelectionIndex();
			}
		});
		/*
		 * Body Widgets
		 */
		defaultScreen();

		
	
	}

	public int confirmWin(Shell ParentShell,String ArgText,int type)
	{
		
		MessageBox mb=new MessageBox(ParentShell,type);
		mb.setMessage(ArgText);
		return mb.open();

	}
	void defaultScreen()
	{
		label1=new Label(mainShell,SWT.NORMAL);
		label1.setText("Please select a project first!");
		GUI.addStyleLeft(label1);
	}
	void disposeDefaultScreen()
	{
		label1.dispose();
	}
	public void newProjectTab()
	{	
		
		if(curProjectIndex==-1)
		{
			disposeDefaultScreen();
			menuItem_Save.setEnabled(true);
			menuItem_SaveAs.setEnabled(true);
			menuItem_Cut.setEnabled(true);
			menuItem_Copy.setEnabled(true);
			menuItem_Paste.setEnabled(true);
		}
		
		
		curProjectIndex=MIC.nextProjectIndex;
		tabItems[curProjectIndex]=new TabItem(tabFolder,SWT.NONE);
		curTab=tabItems[curProjectIndex];
		curTab.setText(MIC.CurrentProjectNames[curProjectIndex]);
		
		
		tabComposites[curProjectIndex]=new Composite(tabFolder,SWT.NONE);
		curComposite=tabComposites[curProjectIndex];
		curComposite.setLayout(new GridLayout (2, false));
		editGroup=new Group(curComposite,SWT.SHADOW_ETCHED_IN);
		editGroup.setText("Modify Specifications");
		GUI.addStyleGroup(editGroup);
		editGroup.setLayout(new GridLayout());
		((GridLayout)editGroup.getLayout()).numColumns=2;
		((GridData)editGroup.getLayoutData()).verticalAlignment=GridData.FILL;
		

		inpLabels=new Label[MIC.FILTERSPECS[MIC.CurrentFilterIDs[curProjectIndex]].length];
		inpTexts[curProjectIndex]=new Text[MIC.FILTERSPECS[MIC.CurrentFilterIDs[curProjectIndex]].length];
		
		infoGroup=new Group(curComposite,SWT.SHADOW_ETCHED_IN);
		infoGroup.setText("Current Specifications");
		GUI.addStyleGroup(infoGroup);
		infoGroup.setLayout(new GridLayout());
		((GridData)infoGroup.getLayoutData()).verticalAlignment=GridData.FILL;
		
		infoTable = new Table(infoGroup,SWT.SINGLE | SWT.BORDER | SWT.FULL_SELECTION);
		infoTable.setHeaderVisible(true);
		
		tableColumn1=new TableColumn(infoTable,SWT.NONE);
		tableColumn1.setText("Input");
		tableColumn1.setWidth(200);
		tableColumn2=new TableColumn(infoTable,SWT.NONE);
		tableColumn2.setText("Value");
		tableColumn2.setWidth(100);
		tableItems[curProjectIndex]=new TableItem[MIC.FILTERSPECS[MIC.CurrentFilterIDs[curProjectIndex]].length];
		
		for(int i=0;i<MIC.FILTERSPECS[MIC.CurrentFilterIDs[curProjectIndex]].length;i++)
		{
			
			inpLabels[i]=new Label(editGroup,SWT.NORMAL);
			inpLabels[i].setText(MIC.FILTERSPECSTEXT[MIC.CurrentFilterIDs[curProjectIndex]][i]);
			GUI.addStyleLeft(inpLabels[i]);	
			
			inpTexts[curProjectIndex][i]=new Text(editGroup,SWT.SINGLE|SWT.BORDER);
			GUI.addStyleField(inpTexts[curProjectIndex][i]);
			inpTexts[curProjectIndex][i].setText(MIC.CurrentFilterInputs[curProjectIndex][i]);
			/*inpTexts[curProjectIndex][i].addSelectionListener(new SelectionAdapter()
			{
				public void widgetSelected(SelectionEvent e)
				{
					//menuItem_Cut.setEnabled(true);
					//menuItem_Copy.setEnabled(true);
					//menuItem_Paste.setEnabled(true);
				}
			});*/
			
			tableItems[curProjectIndex][i]=new TableItem(infoTable,SWT.NONE);
			GUI.addStyle(tableItems[curProjectIndex][i]);
			String[] args={MIC.FILTERSPECSTEXT[MIC.CurrentFilterIDs[curProjectIndex]][i],MIC.CurrentFilterInputs[curProjectIndex][i]};
			tableItems[curProjectIndex][i].setText(args);
		}
		
		buttonsGroup=new Group(curComposite,SWT.SHADOW_ETCHED_IN);
		buttonsGroup.setText("Panel");
		GUI.addStyleGroup(buttonsGroup,2);
		((GridData)buttonsGroup.getLayoutData()).horizontalAlignment=GridData.FILL;
		
		
		buttonsGroup.setLayout(new GridLayout(5,false));
		defaultButtons[curProjectIndex]=new Button(buttonsGroup,SWT.PUSH);
		defaultButtons[curProjectIndex].setText("Set Current");
		defaultButtons[curProjectIndex].addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent e)
			{
				
				for(int i=0;i<MIC.FILTERSPECS[MIC.CurrentFilterIDs[curProjectIndex]].length;i++)
				{
					inpTexts[curProjectIndex][i].setText(MIC.CurrentFilterInputs[curProjectIndex][i]);
				}
			}
		});
		defaultButtons[curProjectIndex].setLayoutData(new GridData());
		
		clearButtons[curProjectIndex]=new Button(buttonsGroup,SWT.PUSH);
		clearButtons[curProjectIndex].setText("Clear All");
		clearButtons[curProjectIndex].addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent e)
			{
				
				for(int i=0;i<MIC.FILTERSPECS[MIC.CurrentFilterIDs[curProjectIndex]].length;i++)
				{
					inpTexts[curProjectIndex][i].setText("");
				}
			}
		});
		clearButtons[curProjectIndex].setLayoutData(new GridData());
		
		updateButtons[curProjectIndex]=new Button(buttonsGroup,SWT.PUSH);
		updateButtons[curProjectIndex].setText("Update Filter");
		updateButtons[curProjectIndex].addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent e)
			{
				
				for(int i=0;i<MIC.FILTERSPECS[MIC.CurrentFilterIDs[curProjectIndex]].length;i++)
				{
					tableItems[curProjectIndex][i].setText(1,inpTexts[curProjectIndex][i].getText());
					MIC.CurrentFilterInputs[curProjectIndex][i]=inpTexts[curProjectIndex][i].getText();
				}
			}
		});
		updateButtons[curProjectIndex].setLayoutData(new GridData());
		
		filterButtons[curProjectIndex]=new Button(buttonsGroup,SWT.PUSH);
		filterButtons[curProjectIndex].setText("Generate Filter");
		filterButtons[curProjectIndex].addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent e)
			{
				
				String args="";
				for(int i=0;i<MIC.FILTERSPECS[MIC.CurrentFilterIDs[curProjectIndex]].length;i++)
				{
					args+=MIC.CurrentFilterInputs[curProjectIndex][i];
					if(i!=MIC.FILTERSPECS[MIC.CurrentFilterIDs[curProjectIndex]].length-1)
						args+=',';
				}
				//System.out.println("ind="+curProjectIndex);
				//System.out.println("fid="+MIC.CurrentFilterIDs[curProjectIndex]);
				//System.out.println("fun="+MIC.FILTERFUNCTION[MIC.CurrentFilterIDs[curProjectIndex]]);
				//System.out.println(curProjectIndex);
				matlab.execNoReturn(MIC.FILTERFUNCTION[MIC.CurrentFilterIDs[curProjectIndex]]+"("+args+");");
			/*	String[] args=new String[MIC.FILTERSPECS[MIC.CurrentFilterIDs[curProjectIndex]].length];
				for(int i=0;i<MIC.FILTERSPECS[MIC.CurrentFilterIDs[curProjectIndex]].length;i++)
				{
					args[i]=MIC.CurrentFilterInputs[curProjectIndex][i];
				}
				try
				{
					Object returns=matlab.execReturn(MIC.FILTERFUNCTION[MIC.CurrentFilterIDs[curProjectIndex]],args);
					System.out.println(returns);
				}
				catch(InterruptedException ex)
				{
					ex.printStackTrace();
				}*/
			}
		});
		filterButtons[curProjectIndex].setLayoutData(new GridData());
	
		
		
		
		expandBar=new ExpandBar(curComposite,SWT.V_SCROLL|SWT.BORDER);
		expandBar.setSpacing(4);
		
		
		GridData gd=new GridData();
		gd.horizontalAlignment=GridData.FILL;
		gd.grabExcessHorizontalSpace=true;
		gd.horizontalSpan=2;
		expandBar.setLayoutData(gd);
		
		
		projectInfo=new Composite(expandBar,SWT.NONE);
		projectInfo.setLayout(new GridLayout());
		projNameLabel=new Label(projectInfo,SWT.SINGLE);
		GUI.addStyleLeft(projNameLabel);
		projNameLabel.setText("Project Name : "+MIC.CurrentProjectNames[curProjectIndex]);
		
		filterTypeLabel=new Label(projectInfo,SWT.SINGLE);
		GUI.addStyleLeft(filterTypeLabel);
		filterTypeLabel.setText("Filter Type : "+MIC.CurrentFilterTypes[curProjectIndex]);
		projPathLabel=new Label(projectInfo,SWT.SINGLE);
		GUI.addStyleLeft(projPathLabel);
		projPathLabel.setText("Project File : "+MIC.CurrentProjectPaths[curProjectIndex]);
		
		
		expandItem1=new ExpandItem(expandBar,SWT.NONE,0);
		expandItem1.setText("About Project");
		expandItem1.setHeight(projectInfo.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		expandItem1.setControl(projectInfo);
		expandItem1.setExpanded(true);
		
	
		filterInfo=new Composite(expandBar,SWT.NONE);
		filterInfo.setLayout(new GridLayout());
		filterInfoLabel=new Label(filterInfo,SWT.SINGLE);
		GUI.addStyleLeft(filterInfoLabel);
		filterInfoLabel.setText(MIC.FILTERDESCRIPTION[MIC.CurrentFilterIDs[curProjectIndex]]);
		
		expandItem2=new ExpandItem(expandBar,SWT.NONE,1);
		expandItem2.setText("About Filter");
		expandItem2.setHeight(filterInfo.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		expandItem2.setControl(filterInfo);
		expandItem2.setExpanded(true);
		curTab.setControl(curComposite);
		mainShell.pack();
		mainShell.setMaximized(true);
		tabFolder.setSelection(curProjectIndex);
		++MIC.nextProjectIndex;
	}
	public void selectProjectTab(int index)
	{
		tabFolder.setSelection(index);
		curProjectIndex=index;
	}
	void destroyGUI()
	{
		System.out.println("Killing GUI");
		mainShell.dispose();
		MainDisplay.dispose();
	}
	
	void initMenuBar()
	{
		/*
		 * Defining the MenuBar
		 */
		mainMenu=new Menu(mainShell,SWT.BAR);
		
		projectMenu=new Menu(mainShell,SWT.DROP_DOWN);
		editMenu=new Menu(mainShell,SWT.DROP_DOWN);
		optionsMenu=new Menu(mainShell,SWT.DROP_DOWN);
		aboutMenu=new Menu(mainShell,SWT.DROP_DOWN);
		
		/*
		 * Defining the MenuBar Items
		 */
		menuItem_Project=new MenuItem(mainMenu,SWT.CASCADE);
		menuItem_Project.setText("Project");
		menuItem_Project.setMenu(projectMenu);
		
		menuItem_Edit=new MenuItem(mainMenu,SWT.CASCADE);
		menuItem_Edit.setText("Edit");
		menuItem_Edit.setMenu(editMenu);
		
		menuItem_Options=new MenuItem(mainMenu,SWT.CASCADE);
		menuItem_Options.setText("Options");
		menuItem_Options.setMenu(optionsMenu);
		
		menuItem_About=new MenuItem(mainMenu,SWT.CASCADE);
		menuItem_About.setText("About");
		menuItem_About.setMenu(aboutMenu);
		
		/*
		 * Defining the Project Menu
		 */
		menuItem_New=new MenuItem(projectMenu,SWT.CASCADE);
		menuItem_New.setText("New Project");
		menuItem_New.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent e)
			{
				newProjectWizard();
			}
		});
		
		menuItem_Open=new MenuItem(projectMenu,SWT.CASCADE);
		menuItem_Open.setText("Open Project");
		menuItem_Open.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent e)
			{
				openProjectWizard();
			}
		});
		
		menuItem_Save=new MenuItem(projectMenu,SWT.CASCADE);
		menuItem_Save.setText("Save Project");
		menuItem_Save.setEnabled(false);
		menuItem_Save.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent e)
			{
				saveProjectWizard();
			}
		});
		
		menuItem_SaveAs=new MenuItem(projectMenu,SWT.CASCADE);
		menuItem_SaveAs.setText("Save Project As");
		menuItem_SaveAs.setEnabled(false);
		menuItem_SaveAs.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent e)
			{
				saveAsProjectWizard();
			}
		});
		
		menuItem_Exit=new MenuItem(projectMenu,SWT.CASCADE);
		menuItem_Exit.setText("Exit Project");
		menuItem_Exit.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent e)
			{
				exit();
			}
		});
		
		/*
		 * Defining the Edit Menu
		 */
		
		menuItem_Cut=new MenuItem(editMenu,SWT.CASCADE);
		menuItem_Cut.setText("Cut");
		menuItem_Cut.setEnabled(false);
		menuItem_Cut.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent e)
			{
				cutText();
			}
		});
		
		menuItem_Copy=new MenuItem(editMenu,SWT.CASCADE);
		menuItem_Copy.setText("Copy");
		menuItem_Copy.setEnabled(false);
		menuItem_Copy.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent e)
			{
				copyText();
			}
		});
		
		menuItem_Paste=new MenuItem(editMenu,SWT.CASCADE);
		menuItem_Paste.setText("Paste");
		menuItem_Paste.setEnabled(false);
		menuItem_Paste.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent e)
			{
				pasteText();
			}
		});
		
		/*
		 * Defining the Options Menu
		 */
		
		menuItem_Settings=new MenuItem(optionsMenu,SWT.CASCADE);
		menuItem_Settings.setText("Settings");
		menuItem_Settings.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent e)
			{
				settingsWindow();
			}
		});
		
		/*
		 * Defining the About Menu
		 */
		
		menuItem_Info=new MenuItem(aboutMenu,SWT.CASCADE);
		menuItem_Info.setText("MIC Filter Viewer");
		menuItem_Info.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent e)
			{
				infoWindow();
			}
		});
		
		menuItem_Credits=new MenuItem(aboutMenu,SWT.CASCADE);
		menuItem_Credits.setText("Credits");
		menuItem_Credits.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent e)
			{
				creditsWindow();
			}
		});
		
		menuItem_Version=new MenuItem(aboutMenu,SWT.CASCADE);
		menuItem_Version.setText("Version");
		menuItem_Version.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent e)
			{
				versionWindow();
			}
		});
	}
	public static void addStyle(Control label)
	{
		label.setForeground(GUI.FGCOLOR);
		label.setFont(GUI.FONTSTYLE);
		label.setBackground(GUI.BGCOLOR);
		
	}
	public static void addStyle(TableItem label)
	{
		label.setForeground(GUI.FGCOLOR);
		label.setFont(GUI.FONTSTYLE);
		label.setBackground(GUI.BGCOLOR);
		
	}
	public static void addStyleGroup(Control label)
	{
		label.setForeground(GUI.FGCOLOR);
		label.setFont(GUI.FONTSTYLE);
		label.setBackground(GUI.BGCOLOR);
		label.setLayoutData(new GridData());
		
	}
	public static void addStyleGroup(Control label,int colspan)
	{
		addStyleGroup(label);
		((GridData)label.getLayoutData()).horizontalSpan=colspan;
		
	}
	public static void addStyleCenter(Control label)
	{
		addStyle(label);
		label.setLayoutData(new GridData(SWT.CENTER,SWT.CENTER,false,false));
	}
	public static void addStyleRight(Control label)
	{
		addStyle(label);
		label.setLayoutData(new GridData(SWT.RIGHT,SWT.CENTER,false,false));
	}
	public static void addStyleLeft(Control label)
	{
		addStyle(label);
		label.setLayoutData(new GridData(SWT.LEFT,SWT.CENTER,false,false));
	}
	public static void addStyleField(Control text)
	{
		addStyle(text);
		GridData textGD=new GridData(SWT.FILL,SWT.CENTER,false,false);
		textGD.widthHint=300;
		text.setLayoutData(textGD);
	}
	public static void addStyleField(Control text,int colspan)
	{
		addStyleField(text);
		((GridData)text.getLayoutData()).horizontalSpan=colspan;
	
	}
	public static Point centralize(Rectangle BaseRect,Control ChildObj)
	{	
		Point ChildSize=ChildObj.getSize();
		return new Point(BaseRect.x+(BaseRect.width-ChildSize.x)/2,BaseRect.y+(BaseRect.height-ChildSize.y)/2);
	}
	
	
	
	
	void newProjectWizard()
	{
		NewProjectWizard newProWiz=new NewProjectWizard(mainShell,this);
		newProWiz.open();
	}
	void openProjectWizard()
	{
		OpenProjectWizard openProWiz=new OpenProjectWizard(mainShell,this);
		openProWiz.open();
	}
	void saveProjectWizard()
	{
		System.out.println("Saving Project at "+MIC.CurrentProjectPaths[curProjectIndex]);
		String filterType=MIC.CurrentFilterTypes[curProjectIndex];
		String projectName=MIC.CurrentProjectNames[curProjectIndex];
		int filterID=MIC.CurrentFilterIDs[curProjectIndex];
		ConfigFile project=new ConfigFile(MIC.CurrentProjectPaths[curProjectIndex]);
		project.setAttribute("ProjectName", projectName);
		project.setAttribute("FilterType", filterType);
		project.setAttribute("FilterID", String.valueOf(filterID));
		for(int i=0;i<MIC.FILTERSPECS[filterID].length;i++)
		{
			project.setAttribute(MIC.FILTERSPECS[filterID][i], MIC.CurrentFilterInputs[curProjectIndex][i]);
		}
	}
	void saveAsProjectWizard()
	{
		SaveProjectWizard saveProWiz=new SaveProjectWizard(mainShell,curProjectIndex);
		saveProWiz.open();
	}
	void exit()
	{
		MessageBox mb=new MessageBox(mainShell,SWT.ICON_QUESTION|SWT.YES|SWT.NO);
		String msg="Are you sure you want to exit ?";
		mb.setMessage(msg);
		int result=mb.open();
		if(result==SWT.YES)
		{
			destroyGUI();
		}
	}
	void cutText()
	{
		for(int i=0;i<MIC.FILTERSPECS[MIC.CurrentFilterIDs[curProjectIndex]].length;i++)
		{
			if(inpTexts[curProjectIndex][i].getSelectionText().length()!=0)
			{
				inpTexts[curProjectIndex][i].cut();
				break;
			}
		}
	}
	void copyText()
	{
		for(int i=0;i<MIC.FILTERSPECS[MIC.CurrentFilterIDs[curProjectIndex]].length;i++)
		{
			if(inpTexts[curProjectIndex][i].getSelectionText().length()!=0)
			{
				inpTexts[curProjectIndex][i].copy();
				break;
			}
		}
	}
	void pasteText()
	{
		for(int i=0;i<MIC.FILTERSPECS[MIC.CurrentFilterIDs[curProjectIndex]].length;i++)
		{
			if(inpTexts[curProjectIndex][i].getSelectionText().length()!=0)
			{
				inpTexts[curProjectIndex][i].paste();
				System.out.println("Caret pos="+inpTexts[curProjectIndex][i].getCaretPosition());
				
				break;
			}
			else
			{
				System.out.println("Caret pos="+inpTexts[curProjectIndex][i].getCaretPosition());
			}
		}
	}
	void settingsWindow()
	{
		
	}
	void infoWindow()
	{
		MessageBox mb=new MessageBox(mainShell,SWT.ICON_INFORMATION|SWT.OK);
		String msg="MIC FilterViewer is a software designed for easy analysis of MIC filters.\n";
		msg+="The software has its frontend in JAVA™ SWT and backend in Mathworks® MATLAB™.\n";
		msg+="This software is Free and Open Source and is protected by GNU GPL v3.0";
		mb.setMessage(msg);
		mb.open();
	}
	void creditsWindow()
	{
		MessageBox mb=new MessageBox(mainShell,SWT.ICON_INFORMATION|SWT.OK);
		String msg="This software is designed and developed by \n\n";
		msg+="Abhishek Shrivastava & Amey Dharwadekar\n\n";
		msg+="Students of National Institute of Technology Trichy\n\n";
		msg+="Contact : i.abhi27@gmail.com";
		mb.setMessage(msg);
		mb.open();
		
	}
	void versionWindow()
	{
		MessageBox mb=new MessageBox(mainShell,SWT.ICON_INFORMATION|SWT.OK);
		String msg="Version 0.01\n\nDated : 10 March 2010";
		mb.setMessage(msg);
		mb.open();
	}
}

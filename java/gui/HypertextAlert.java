package gui;


import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Shell;

public class HypertextAlert {
	boolean retValue;
	public HypertextAlert(Shell ParentShell,String ArgText,int type)
	{
		Shell SubShell=new Shell(ParentShell.getDisplay());
		retValue=false;
		GridLayout SubShellGL=new GridLayout();
		SubShell.setLayout(SubShellGL);
		SubShellGL.numColumns=2;
		SubShellGL.makeColumnsEqualWidth=true;
		
		String TxtBut1,TxtBut2,TxtBut3;
		Button But1,But2,But3;
		TxtBut1="";
		TxtBut2="";
		TxtBut3="";
		if(type==GUI.OK)
		{
			SubShellGL.numColumns=1;
			TxtBut1="OK";
			
		}
		else if(type==GUI.OKCANCEL)
		{
			SubShellGL.numColumns=2;
			TxtBut1="OK";
			TxtBut2="CANCEL";
		}
		else if(type==GUI.YESNO)
		{
			SubShellGL.numColumns=2;
			TxtBut1="YES";
			TxtBut2="NO";
		}
		else if(type==GUI.YESNOCANCEL)
		{
			SubShellGL.numColumns=3;
			TxtBut1="YES";
			TxtBut2="NO";
			TxtBut3="CANCEL";
		}
		
		///Label ArgQuest=new Label(SubShell,SWT.NORMAL|SWT.CENTER);
		Link ArgQuest=new Link(SubShell,SWT.NONE);
		ArgQuest.setText(ArgText);
		ArgQuest.setLayoutData(new GridData(SWT.CENTER|SWT.FILL,SWT.CENTER|SWT.FILL,true,true,SubShellGL.numColumns,1));
	
		
		
		
		But1=new Button(SubShell,SWT.PUSH);
		But1.setText(TxtBut1);
		But1.setLayoutData(new GridData(SWT.CENTER,SWT.CENTER,true,true,1,1));
		((GridData)But1.getLayoutData()).widthHint=50;
		But1.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent Evt)
			{
				((Button)Evt.widget).getShell().dispose();
				retValue=true;
			}
		});

		if(SubShellGL.numColumns>1)
		{
			But2=new Button(SubShell,SWT.PUSH);
			But2.setText(TxtBut2);
			But2.setLayoutData(new GridData(SWT.CENTER|SWT.FILL,SWT.CENTER|SWT.FILL,true,true,1,1));
			((GridData)But2.getLayoutData()).widthHint=50;
			But2.addSelectionListener(new SelectionAdapter(){
				public void widgetSelected(SelectionEvent Evt)
				{
					((Button)Evt.widget).getShell().dispose();
					retValue=false;
				}
			});
		}

		if(SubShellGL.numColumns>2)
		{
			But3=new Button(SubShell,SWT.PUSH);
			But3.setText(TxtBut3);
			
			But3.setLayoutData(new GridData(SWT.CENTER|SWT.FILL,SWT.CENTER|SWT.FILL,true,true,1,1));
			But3.setLayoutData(new GridData(SWT.CENTER|SWT.FILL,SWT.CENTER|SWT.FILL,true,true,1,1));
			But3.addSelectionListener(new SelectionAdapter(){
				public void widgetSelected(SelectionEvent Evt)
				{
					((Button)Evt.widget).getShell().dispose();
				}
			});
		}
		
	
			
		SubShell.pack();
		SubShell.setLocation(centralize(ParentShell.getBounds(),SubShell));
		SubShell.open();
		
		while(!SubShell.isDisposed())
		{
			if(!SubShell.getDisplay().readAndDispatch())
				SubShell.getDisplay().sleep();
		}
	}
	Point centralize(Rectangle BaseRect,Control ChildObj)
	{	
		Point ChildSize=ChildObj.getSize();
		return new Point(BaseRect.x+(BaseRect.width-ChildSize.x)/2,BaseRect.y+(BaseRect.height-ChildSize.y)/2);
	}
	boolean result()
	{
		return retValue;
	}
}

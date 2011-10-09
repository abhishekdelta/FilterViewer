package micfilter;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConfigFile
{
	String configFileName;
	File configFile;
	boolean fileExists;
	public ConfigFile(String name)
	{
		if(name.endsWith(".mic")==false)
			name+=".mic";
		configFileName=name;
		configFile=new File(name);
		fileExists=configFile.exists();
	}
	public boolean exists()
	{
		return fileExists;
	}
	public String fullPath()
	{
		try
		{
			return configFile.getCanonicalPath();
		}
		catch(IOException e)
		{}
		return null;	
	}
	public void setAttribute(String param,String value)
	{
		System.out.println("Setting Attribute : "+param+"="+value);
		boolean found=false;
		try
		{
		
			if(fileExists==false)
			{
				configFile.createNewFile();
				fileExists=true;
				
			}
			 String data="",contents="";
			 param=param.trim().toUpperCase();
			 BufferedReader BR=new BufferedReader(new InputStreamReader(new FileInputStream(configFile)));
			 while((data=BR.readLine())!=null)
			 {
				 if(data.startsWith(param+"=")==true)
				 {
					 data=param+"="+value+";";
					 found=true;
				 }
				 contents+=data+'\n';
				 
			 }
			 BR.close();
			
			BufferedWriter BW=new BufferedWriter(new FileWriter(configFile,false));
			if(found==false)
			{
				contents+=param.toUpperCase()+"="+value+";";
			}
			BW.append(contents);
			BW.close();
		}
		catch(Exception exc)
		{
			exc.printStackTrace();
		}
		
	}
	public String getAttribute(String param)
	{
		 BufferedReader BR=null;
		 try
		 {
			 if(fileExists==false)
			 {
				return null; 
			 }
			 BR=new BufferedReader(new InputStreamReader(new FileInputStream(configFile)));
		 }
		 catch(Exception exc)
		 {
			 exc.printStackTrace();
		 }
		 String data="",key="";
		 param=param.trim().toUpperCase();
		 try
		 {
			 
			 while((data=BR.readLine())!=null)
			 {
				
				 if(data.startsWith(param+"=")==true)
				 {
					 key=data.substring(param.length()+1,data.length()-1);
					 break;
				 }
			 }
			 BR.close();
		 }
		 catch(Exception exc)
		 {
			 exc.printStackTrace();
		 }
		 return key;
	}
}
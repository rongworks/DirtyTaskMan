package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

public class Settings {
	private static String settings_path = "config/settings.properties";
	private static Settings instance;
	HashMap<String, String> settings;
	
	public static Settings getInstance(){
		if(instance == null){
			instance = new Settings();
		}
		return instance;
	}
	/**
	 * Read Settings from a properties-file
	 * @throws FileNotFoundException
	 * @throws IOException
	 * TODO: This is really ugly
	 * TODO: implement reloading of prop file?
	 */
	public void init() throws FileNotFoundException, IOException{
		Properties props = new Properties();
		settings = new HashMap<String, String>();
		props.load(new FileReader(new File(settings_path)));
		Enumeration e = props.propertyNames();
		while(e.hasMoreElements()){
			String element = (String)e.nextElement();
			settings.put(element, props.getProperty(element));
		}
	}
	
	public String getSetting(String key){
		String val = settings.get(key);
		if(val == null){
			throw new RuntimeException("Tried to access Setting:'"+val+"', no such Setting found!");
		}
		return val;
	}
}

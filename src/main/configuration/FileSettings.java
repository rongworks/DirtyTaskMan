package main.configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileSettings implements Settings{
	String settings_path;
	Properties properties;
	Logger logger = LoggerFactory.getLogger(getClass());
	
	public FileSettings(String pathToFile){
		settings_path = pathToFile;
	}
	
	/**
	 * Load Settings from a properties File
	 */
	public void load(){
		FileReader reader;
		properties = new Properties();
		try {
			reader = new FileReader(new File(settings_path));
			properties.load(reader);
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}

	/**
	 * Reload properties from properties File
	 */
	public void reload() {
		load();
	}

	public String getSetting(String key) {
		return properties.getProperty(key);
	}

	/**
	 * Will return a value for a given key as String
	 * @return value as String
	 */
	public Object getSettingObject(String key) {
		return properties.getProperty(key);
	}
	
	
}

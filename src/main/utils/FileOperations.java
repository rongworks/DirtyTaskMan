package main.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import ch.qos.logback.core.util.FileUtil;

public class FileOperations {

	public static File writeFile(String name, String content) {
		try {
			File f = new File(name);
			// check parent folders and create if necessary
			if(f.getParentFile() != null){
				f.getParentFile().mkdirs();
			}
			FileWriter writer = new FileWriter(f);
			BufferedWriter bwriter = new BufferedWriter(writer);
			bwriter.write(content);
			bwriter.flush();
			bwriter.close();
			return f;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}

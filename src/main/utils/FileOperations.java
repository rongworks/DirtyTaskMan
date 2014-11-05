package main.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileOperations {

	public static File writeFile(String name, String content) {
		try {
			File f = new File(name);
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

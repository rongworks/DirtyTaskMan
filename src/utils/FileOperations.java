package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileOperations {

	public static void writeFile(String name, String content) {
		try {
			File f = new File(name);
			FileWriter writer = new FileWriter(f);
			BufferedWriter bwriter = new BufferedWriter(writer);
			bwriter.write(content);
			bwriter.flush();
			bwriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

package main.configuration;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FileSettingsTest {
	FileSettings settings;
	File tmp;
	
	@Before
	public void setUp() throws Exception {
		tmp = File.createTempFile("settings", ".properties");
		settings = new FileSettings(tmp.getAbsolutePath());
	}

	@After
	public void tearDown() throws Exception {
		settings = null;
		tmp.delete();
	}

	@Test
	public void testLoad() throws Exception {
		settings.load();
		assertNotNull(settings.properties);
	}

}

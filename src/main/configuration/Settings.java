package main.configuration;

public interface Settings {
	/**
	 * Load settings
	 */
	public void load();
	/**
	 * Reload settings from source
	 */
	public void reload();
	/**
	 * Get a setting value for a given key
	 * @param key
	 * @return value as String
	 */
	public String getSetting(String key);
	/**
	 * Get a setting-object-value for a given key
	 * @param key to lookup
	 * @return value as Object
	 */
	public Object getSettingObject(String key);
}

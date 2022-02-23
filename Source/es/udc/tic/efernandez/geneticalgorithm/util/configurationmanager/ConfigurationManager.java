package es.udc.tic.efernandez.geneticalgorithm.util.configurationmanager;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConfigurationManager {
	   private static final String CONFIGURATION_FILE = "GeneticAlgorithm.properties";

	    private static Map map;

	    static {

	        try {

	            Class configurationManagerClass = ConfigurationManager.class;
	            ClassLoader classLoader = configurationManagerClass.getClassLoader();
	            InputStream inputStream = classLoader.getResourceAsStream(CONFIGURATION_FILE);
	            Properties properties = new Properties();
	            properties.load(inputStream);
	            inputStream.close();

	            map = new HashMap(properties);

	        } catch (Exception e) {
	            System.out.println("Missing GeneticAlgorithm.properties for parameter ");
	            e.printStackTrace();
	        }
	    }
	    
	    private ConfigurationManager(){}
	    
	    public static String getParameter(String key){
	        return (String)map.get(key);
	    }
}

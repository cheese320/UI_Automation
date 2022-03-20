package base;

import enums.DriverType;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;

public class ReadConfigFile {
    private static Properties prop;

    private ReadConfigFile(){}

    private static class configFileSingleton{
        private static final ReadConfigFile INSTANCE = readFile();
    }

    public static ReadConfigFile getInstance(){
        return configFileSingleton.INSTANCE;
    }

    //load config.properties
    private static ReadConfigFile readFile(){
        BufferedReader br;
        String userDir = System.getProperty("user.dir");
        String propertiesFilePath = userDir + "//src//main//java//config//config.properties";
        try{
            br = new BufferedReader(new FileReader(propertiesFilePath));
            prop = new Properties();
            try{
                prop.load(br);
                br.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
            throw new RuntimeException("config.properties not found at " + propertiesFilePath);
        }
        return new ReadConfigFile();
    }

    //get browser driver type. use Chrome as default
    public DriverType getDriverType(){
        String driverType = prop.getProperty("driverType").toLowerCase(Locale.US);
        switch(driverType){
            case "firefox" -> {
                return DriverType.FIREFOX;
            }
            case "ie" -> {
                return DriverType.IE;
            }
            default -> {
                return DriverType.CHROME;
            }
        }
    }


    //get url of home page
    public String getHomeURL(){
        String homeURL = prop.getProperty("homeURL");
        if(homeURL!=null){
            return homeURL;
        }
        throw new RuntimeException("homeURL is not specified in the config.properties file.");
    }

    //get implicitly wait time, 20 sec by default
    public long getImplicitlyWait(){
        String implicitlyWait = prop.getProperty("implicitlyWait");
        if(implicitlyWait!=null){
            try{
                return Long.parseLong(implicitlyWait);
            } catch(NumberFormatException e){
                throw new RuntimeException("Not able to parse value: "+implicitlyWait + " in to Long");
            }
        }
        return 20;
    }

    //get other properties type which are added by tester
    public String getProp(String type){
        String result = prop.getProperty(type);
        if(result!=null) return result;
        throw new RuntimeException(type + " is not specified in the config.properties file.");
    }

}

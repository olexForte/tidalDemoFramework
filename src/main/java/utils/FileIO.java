package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Interation with file system and Properties
 */

public class FileIO {

    static String TARGET_FOLDER = "target";
    static String DATA_RESOURCES        = "src/main/resources/data/";
    static String TEST_RESOURCES        = "src/test/resources/";
    static String CONFIG_FILE = System.getProperty("config");
    static String PROPERTIES = "src/main/resources/" + (( CONFIG_FILE == null ) ? "default" : CONFIG_FILE) + ".properties";

    //folder in .target with downloaded/created during session files)
    public static String OUTPUT_DIR = "./target/" + Tools.getCurDateTime();

    public static String getConfigProperty(String fieldName){
        String fileLocation = PROPERTIES;
        String result   = null;

        try {

            if(System.getProperty(fieldName) != null)
                return System.getProperty(fieldName);

            //open file
            File file = new File(fileLocation);
            //open input stream to read file
            FileInputStream fileInput = new FileInputStream(file);
            //create Properties object
            Properties properties = new Properties();
            //load properties from file
            properties.load(fileInput);
            //close file
            fileInput.close();
            //get property by name
            result = properties.getProperty(fieldName);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            ReporterManager.Instance.fail("Config was not found");
        } catch (IOException e) {
            e.printStackTrace();
            ReporterManager.Instance.fail("Config was not opened");
        } catch (Exception e){
            e.printStackTrace();
            ReporterManager.Instance.fail("Field was not found: " + fieldName);
        }
        return result;
    }



    public static String takeScreenshot(WebDriver driver){
        return takeScreenshot(driver, String.valueOf(System.currentTimeMillis()));
    }

    public static String takeScreenshot(WebDriver driver, String name){
        File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String filename = name + "screen.png";
        try {
            FileUtils.copyFile(file, new File(TARGET_FOLDER + File.separator + filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filename;
    }

    public static String getDataFile(String filename){
        return DATA_RESOURCES + FileIO.getConfigProperty("EnvType") + "/" + filename ;
    }

    public static String getTestDataFile(String filename){
        return TEST_RESOURCES + FileIO.getConfigProperty("EnvType") + "/" + filename ;

    }

    public static String getDataFileContent(String filename){
        String result = null;
        try {
            result = FileUtils.readFileToString(new File(DATA_RESOURCES + FileIO.getConfigProperty("EnvType") + "/" + filename), Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result ;
    }

    public static String getFileContent(String filename){
        String result = null;
        try {
            result = FileUtils.readFileToString(new File(filename), Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result ;
    }

    public static List<String> getDataFilesInDirectory(String dirName) {
        List<String> filesInDir = new ArrayList<String>();
        File folder = new File(DATA_RESOURCES + FileIO.getConfigProperty("EnvType") + "/" + dirName );
        File[] listOfFiles = folder.listFiles();
        for(File current : listOfFiles){
            if(current.getName().contains(".xlsx"))
                filesInDir.add(DATA_RESOURCES + FileIO.getConfigProperty("EnvType") + "/" + dirName + "/" + current.getName());
        }
        return filesInDir;
    }

    public static void createDir(String path) throws IOException {
        FileUtils.forceMkdir(new File(path));
    }


    public static String getTestDataFileContent(String filename){
        String result = null;
        try {
            result = FileUtils.readFileToString(new File(TEST_RESOURCES + FileIO.getConfigProperty("EnvType") + "/" + filename), Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result ;
    }
}


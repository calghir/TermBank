
package termbank.utils;

import termbank.data.ManageDatabase;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Properties;

public final class FileUtils {

    public static File fileFromRelativePath(String relativePath){
        try{
            URI fileURI = FileUtils.class.getResource(relativePath).toURI();
            //System.out.println(fileURI.toString());
            return new File(fileURI);
        } catch (URISyntaxException e){
            throw new NoSuchElementException(relativePath + " not found or incorrect!");
        }

    }

    public static Properties loadPropertyFile(String filename){
        Properties properties = new Properties();

        try(InputStream fileInputStream = getInputStreamFromFile(filename)){
            properties.load(fileInputStream);
        }
        catch (IOException e){
            System.out.println(e.getMessage());
            throw new NullPointerException("File not found or not correct format!");
        }

        return properties;
    }

    private static InputStream getInputStreamFromFile(String filename){
        return Optional.ofNullable(FileUtils.class.getClassLoader().getResourceAsStream(filename))
                .orElseThrow(() -> new NoSuchElementException(filename + " file is missing or invalid"));
    }
}
package com.pdfcreator;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

public class FileResourcesUtils {

  public InputStream getFileFromResourceAsStream(String fileName) {

    // The class loader that loaded the class
    ClassLoader classLoader = getClass().getClassLoader();
    InputStream inputStream = classLoader.getResourceAsStream(fileName);

    // the stream holding the file content
    if (inputStream == null) {
      throw new IllegalArgumentException("file not found! " + fileName);
    } else {
      return inputStream;
    }

  }

  public File getFileFromResource(String fileName) throws URISyntaxException{

    ClassLoader classLoader = getClass().getClassLoader();
    URL resource = classLoader.getResource(fileName);
    if (resource == null) {
      throw new IllegalArgumentException("file not found! " + fileName);
    } else {

      return new File(resource.toURI());
    }

  }

  // print input stream
  public static void printInputStream(InputStream is) {

    try (InputStreamReader streamReader =
        new InputStreamReader(is, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(streamReader)) {

      String line;
      while ((line = reader.readLine()) != null) {
        System.out.println(line);
      }

    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  // print a file
  public static void printFile(File file) {

    List<String> lines;
    try {
      lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

}

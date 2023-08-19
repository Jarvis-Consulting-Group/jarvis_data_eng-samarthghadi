package ca.jrvs.apps.grep;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaGrepImp implements JavaGrep {

  final Logger logger = LoggerFactory.getLogger(JavaGrep.class);
  private String regex;
  private String rootPath;
  private String outFile;

  public static void main(String[] args) {
    if (args.length != 3) {
      throw new IllegalArgumentException("USAGE: JavGrep regex rootPath outfile");
    }

    //Use default logger config
    BasicConfigurator.configure();
    JavaGrepImp javaGrepImp = new JavaGrepImp();
    javaGrepImp.setRegex(args[0]);
    javaGrepImp.setRootPath(args[1]);
    javaGrepImp.setOutFile(args[2]);

    try {
      javaGrepImp.process();
    } catch (Exception ex) {
      javaGrepImp.logger.error("Error: Unable to process", ex);
    }
  }

  @Override
  public void process() throws IOException {
    /**
     * Top level search workflow
     * @throws IOException
     */

    List<String> matchedLines = new ArrayList<>();

    for(File file: this.listFiles(this.getRootPath())) {
      for(String line: this.readLines(file)) {
        if(containsPattern(line)) {
          matchedLines.add(line);
        }
      }
    }
    writeToFile(matchedLines);
  }

  @Override
  public List<File> listFiles(String rootDir) {
    /**
     * Traverse a given directory and return all files
     * @param rootDir input directory
     * @return files under the rootDir
     */
    List<File> files = new ArrayList<>();
    File file = new File(rootDir);
    for(File eachFile: file.listFiles()) {
      files.add(eachFile);
    }
    return files;
  }

  @Override
  public List<String> readLines(File inputFile) throws IllegalArgumentException{
    /**
     * Read a file and return all the lines
     *
     * Explain FileReader, BufferedReader, and character encoding
     *
     * @param inputFile file to be read
     * @return lines
     * @throws IIlegalArgumentsException if a given inputFile is not a file
     */
    List<String> lines = new ArrayList<>();
    try {
      BufferedReader reader = new BufferedReader(new FileReader(inputFile));
      String line;
      while ((line = reader.readLine()) != null) {
        lines.add(line);
      }
    } catch(IOException e){
      logger.error(e.getMessage());
    }
    return lines;
  }

  @Override
  public boolean containsPattern(String line) {
    /**
     * Check if a line contains the regex pattern (passed by user)
     * @param line input string
     * @return true if there is a match
     */
    return Pattern.matches(this.getRegex(), line);
  }

  @Override
  public void writeToFile(List<String> lines) throws IOException {
    /**
     * Write lines to a file
     *
     * Explore: FileOutputStream, OutputStreamWriter, and BufferedWriter
     *
     * @param lines matched line
     * @throws IOException if write failed
     */
    try(BufferedWriter writer = new BufferedWriter(new FileWriter(outFile))){
      for(String line: lines) {
        writer.write(line);
      }
    } catch(IOException e) {
      logger.error(e.getMessage());
    }
  }
  @Override
  public String getRegex() {
    return regex;
  }

  @Override
  public void setRegex(String regex) {
    this.regex = regex;
  }

  @Override
  public String getRootPath() {
    return rootPath;
  }

  @Override
  public void setRootPath(String rootPath) {
    this.rootPath = rootPath;
  }

  @Override
  public String getOutFile() {
    return outFile;
  }

  @Override
  public void setOutFile(String outFile) {
    this.outFile = outFile;
  }

}

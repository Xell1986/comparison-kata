package com.javabycomparison.kata.analysis;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class JavaAnalyzer implements Analyzer {

  public static final String IMPORT = "import";
  public static final String LINE_COMMENT = "//";
  public static final String CONTINUED_BLOCK_COMMENT = "*";
  public static final String BLOCK_COMMENT_LAST_LINE = "/*";
  public static final String THERE_WAS_A_PROBLEM_READING_A_FILE = "There was a problem reading a file!";
  private final Path file;

  public JavaAnalyzer(Path file) {
    this.file = file;
  }

  @Override
  public ResultData analyze() throws IOException {
    if (file != null) {
      int imports = 0;
      int loc = 0;
      int commentsLoc = 0;

      try(BufferedReader reader = Files.newBufferedReader(this.file))
      {
        String currentLine;
        while ((currentLine = reader.readLine()) != null) {
          loc += 1;
          if (currentLine.trim().startsWith(IMPORT)) {
            imports += 1;
          } else if (currentLine.trim().startsWith(LINE_COMMENT)
              || currentLine.trim().startsWith(CONTINUED_BLOCK_COMMENT)
              || currentLine.trim().startsWith(BLOCK_COMMENT_LAST_LINE)) {
            commentsLoc += 1;
          }
        }
        return new ResultData(ResultData.ProgrammingLanguage.JAVA, this.file.toString(), loc, commentsLoc, 0, imports);
      } catch (IOException ioe) {
        throw new IOException(THERE_WAS_A_PROBLEM_READING_A_FILE, ioe);
      }
    } else {
      return null;
    }
  }
}

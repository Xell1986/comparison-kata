package com.javabycomparison.kata.analysis;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class PythonAnalyzer implements Analyzer {
  public static final String IMPORT_STATEMENT = "import";
  public static final String FROM_STATEMENT = "from";
  public static final String LINE_COMMENT = "#";
  public static final String METHOD_DEFINITION = "def";
  private final Path file;

  public PythonAnalyzer(Path file) {
    this.file = file;
  }

  @Override
  public ResultData analyze() throws IOException {
    int numberOfImports = 0;
    int loc = 0;
    int numberOfMethods = 0;
    int commentsLoc = 0;

    List<String> fileContents = Files.readAllLines(this.file);
    for (String line : fileContents) {
      loc += 1;
      if (line.trim().startsWith(IMPORT_STATEMENT)) {
        numberOfImports += 1;
      }
      if (line.trim().startsWith(FROM_STATEMENT)) {
        numberOfImports += 1;
      } else if (line.trim().startsWith(LINE_COMMENT)) {
        commentsLoc += 1;
      } else if (line.trim().startsWith(METHOD_DEFINITION)) {
        numberOfMethods += 1;
      }
    }

    return new ResultData(
        ResultData.ProgrammingLanguage.PYTHON,
        this.file.toString(),
        loc,
        commentsLoc,
        numberOfMethods,
        numberOfImports);
  }
}

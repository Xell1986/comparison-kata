package com.javabycomparison.kata.analysis;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class AnalyzerImpl implements Analyzer {
  private final Path file;

  public AnalyzerImpl(Path file) {
    this.file = file;
  }

  @Override
  public ResultData analyze() throws IOException
  {
      List<String> fileContents = Files.readAllLines(this.file);
      int loc = fileContents.size();
      return new ResultData(ResultData.ProgrammingLanguage.OTHER, this.file.toString(), loc, 0, 0, 0);
  }
}

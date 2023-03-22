package com.javabycomparison.kata.printing;

import com.javabycomparison.kata.analysis.ResultData;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;

public class CsvPrinter
{
    public static final String CSV_HEADER = "File Name,Language,Lines of Code,Number of Comments,Number of Methods,Number of Imports\n";
    public static final String TYPE_JAVA = "Java";
    public static final String TYPE_PYTHON = "Python";
    public static final String DELIMITER = ",";
    private Path csvFile;

  public CsvPrinter(String outputFile) {
    this.csvFile = Paths.get(outputFile);
  }

  public void writeCsv(ResultData[] overallResult) throws IOException {
    FileOutputStream writer = new FileOutputStream(csvFile.toFile());
    writer.write(CSV_HEADER.getBytes());
    Arrays.stream(overallResult)
        .filter(Objects::nonNull)
        .map(this::mapToString)
        .map(String::getBytes)
        .forEach(
            str -> {
              try {
                writer.write(str);
              } catch (IOException e) {
              }
            });

    // to be sure
    writer.close();
  }

    private String mapToString(ResultData result) {
        return String.join(
                DELIMITER,
                result.name,
                (result.type == ResultData.ProgrammingLanguage.JAVA) ? TYPE_JAVA : TYPE_PYTHON,
                String.valueOf(result.loc),
                String.valueOf(result.commentLoc),
                String.valueOf(result.numberOfMethods),
                String.valueOf(result.numberOfImports))
                + System.lineSeparator();
    }
}

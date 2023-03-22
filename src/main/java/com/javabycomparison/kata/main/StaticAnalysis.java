package com.javabycomparison.kata.main;

import com.javabycomparison.kata.analysis.ResultData;
import com.javabycomparison.kata.analysis.ResultDataPrinter;
import com.javabycomparison.kata.printing.CsvPrinter;
import com.javabycomparison.kata.printing.ResultPrinter;
import com.javabycomparison.kata.search.SearchClient;
import java.io.IOException;
import java.util.LinkedList;

public class StaticAnalysis {

  public static void main(String... args) {
    analyze(args.length == 0 ? null : args[0], args.length == 2 ? args[1].equals("smry") : false);
  }

  private static void analyze(String p, boolean smry) {
    StaticAnalysis analyzer = new StaticAnalysis();
    ResultData[] overallResult = analyzer.run(p == null ? "./src/" : p, smry);
    if (overallResult != null) {
      ResultPrinter.printOverallResults(overallResult);
      try {
        new CsvPrinter("output.csv").writeCsv(overallResult);
      } catch (IOException e) {
        System.err.println("Something went a bit wrong");
      }
    } else {
      System.err.println("Something went terribly wrong");
    }
  }

  private ResultData[] run(String directoryPath, boolean smry) {
    LinkedList<ResultData> results = new SearchClient(smry).collectAllFiles(directoryPath);
    if (results != null) {
      if (results.size() != 0) {
        int javaLOC = 0;
        int javaCommentLOC = 0;
        int javaNumMethod = 0;
        int javanImports = 0;

        int pyLOC = 0;
        int pyCommentLOC = 0;
        int pyNumMethod = 0;
        int pynImports = 0;

        int LOC = 0;
        int commentLOC = 0;
        int numMethod = 0;
        int nImports = 0;

        for (int l = 0; l < results.size(); l = l + 1) {
          ResultData resultData = results.get(l);
          if (!smry) {
            System.out.println(new ResultDataPrinter().print(resultData));
          }
          if (resultData.type == ResultData.ProgrammingLanguage.JAVA) {
            javaLOC += resultData.loc;
            javaCommentLOC += resultData.commentLoc;
            javaNumMethod += resultData.numberOfMethods;
            javanImports += resultData.numberOfImports;
          } else if (resultData.type == ResultData.ProgrammingLanguage.PYTHON) {
            pyLOC += resultData.loc;
            pyCommentLOC += resultData.commentLoc;
            pyNumMethod += resultData.numberOfMethods;
            pynImports += resultData.numberOfImports;
          } else {
            LOC += resultData.loc;
            commentLOC += resultData.commentLoc;
            numMethod += resultData.numberOfMethods;
            nImports += resultData.numberOfImports;
          }
        }
        return new ResultData[] {
          new ResultData(ResultData.ProgrammingLanguage.JAVA, "Overall Java", javaLOC, javaCommentLOC, javaNumMethod, javanImports),
          new ResultData(ResultData.ProgrammingLanguage.PYTHON, "Overall Python", pyLOC, pyCommentLOC, pyNumMethod, pynImports),
          new ResultData(ResultData.ProgrammingLanguage.OTHER, "Overall Other", LOC, commentLOC, numMethod, nImports),
        };
      } else {
        return new ResultData[] {new ResultData()};
      }
    }
    System.err.println("There was a problem with the result!");

    return null;
  }
}

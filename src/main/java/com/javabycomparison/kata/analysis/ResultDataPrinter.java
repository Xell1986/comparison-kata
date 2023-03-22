package com.javabycomparison.kata.analysis;

import java.util.Collections;

public class ResultDataPrinter {

  public String print(ResultData data) {
    String language;

    return data.name
        + "\t"
        + data.type.toString()
        + "\t"
        + data.uppercaseL
        + "\t"
        + data.loc
        + "\t"
        + data.commentLoc
        + "\t"
        + data.numberOfMethods
        + "\t"
        + data.numberOfImports;
  }

  public String printFileName(ResultData data, int length) {
    return String.join("", Collections.nCopies(Math.max(length - data.name.length(), 0), " "))
        + data.name;
  }

  public String printLanguage(ResultData data, int length) {
    return String.join("", Collections.nCopies(Math.max(length - data.type.toString().length(), 0), " "))
        + data.type.toString();
  }

  public String printLOC(ResultData data, int length) {
    return String.join(
            "", Collections.nCopies(Math.max(length - String.valueOf(data.loc).length(), 0), " "))
        + data.loc;
  }

  public String printCommentLOC(ResultData data, int length) {
    return String.join(
            "",
            Collections.nCopies(
                Math.max(length - String.valueOf(data.commentLoc).length(), 0), " "))
        + data.commentLoc;
  }

  public String printNumMethodLOC(ResultData data, int length) {
    return String.join(
            "",
            Collections.nCopies(Math.max(length - String.valueOf(data.numberOfMethods).length(), 0), " "))
        + data.numberOfMethods;
  }

  public String printNImportsLOC(ResultData data, int length) {
    return String.join(
            "",
            Collections.nCopies(Math.max(length - String.valueOf(data.numberOfImports).length(), 0), " "))
        + data.numberOfImports;
  }
}

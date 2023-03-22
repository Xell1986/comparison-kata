package com.javabycomparison.kata.search;

import com.javabycomparison.kata.analysis.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.nonNull;

enum FileType {
    JAVA,
    PYTHON,
    OTHER,
    DIRECTORY
}

public class SearchClient {

    private boolean summary;

    public SearchClient(boolean summary) {
        this.summary = summary;
    }

    public LinkedList<ResultData> collectAllFiles(String directoryPath) {
        LinkedList<ResultData> resultsList = new LinkedList<>();
        try (Stream<Path> files = Files.walk(Paths.get(directoryPath))) {

            Predicate<Path> fileIsNotHidden = path -> {
                try {
                    return !Files.isHidden(path);
                } catch (IOException e) {
                    return false;
                }
            };
            List<Path> actualRelevantFiles = files
                    .filter(path -> !path.toString().contains(".git"))
                    .filter(fileIsNotHidden)
                    .sorted()
                    .collect(Collectors.toList());

            for (Path file : actualRelevantFiles) {
                FileType fileTypeOfCurrentFile = typeMapper(file);
                String statusMessage;
                Analyzer analyzer;
                switch (fileTypeOfCurrentFile) {
                    case JAVA:
                        statusMessage = String.format("File %s is a Java file. It will be analyzed.", file) ;
                        analyzer = new JavaAnalyzer(file);
                        break;
                    case PYTHON:
                        statusMessage = "File " + file.toString() + " is a Python file. It will be analyzed.";
                        analyzer = new PythonAnalyzer(file);
                        break;
                    case OTHER:
                        statusMessage = "File " + file.toString() + " is neither a Java file nor a Python file.";
                        analyzer = new AnalyzerImpl(file);
                        break;
                    case DIRECTORY:
                        statusMessage = "Skipping directory " + file + ".";
                        analyzer = null;
                        break;
                    default:
                        statusMessage = "Unknown condition";
                        analyzer = null;
                }

                if (!summary) {
                    System.out.println(statusMessage);
                }
                if(nonNull(analyzer)) {
                    final ResultData resultData = analyzer.analyze();
                    resultsList.add(resultData);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return resultsList;
    }

    private FileType typeMapper(Path file) {
        if (isJavaFile(file)) {
            return FileType.JAVA;
        } else if (isPythonFile(file)) {
            return FileType.PYTHON;
        } else if (Files.isDirectory(file)) {
            return FileType.DIRECTORY;
        } else {
            return FileType.OTHER;
        }
    }

    private boolean isJavaFile(Path file) {
        if (file.toString().matches(".*\\.java")) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isPythonFile(Path file) {
        return file.getFileName().toString().matches(".*\\.py");
    }
}

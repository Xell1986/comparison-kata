package com.javabycomparison.kata.analysis;

import java.util.StringJoiner;


public class ResultData {
    public static final String ESCAPING_BACKSLASH = "\\\\";
    public static final String BACKSLASH = "/";
    public ProgrammingLanguage type;
    public String name;
    public int uppercaseL;
    public int loc;
    public int commentLoc;
    public int numberOfMethods;
    public int numberOfImports;

    public ResultData(ProgrammingLanguage type, String name, int loc, int commentLoc, int numberOfMethods, int numberOfImports) {
        this.type = type;
        this.name = name.replaceAll(ESCAPING_BACKSLASH, BACKSLASH);
        this.loc = loc;
        this.commentLoc = commentLoc;
        this.numberOfMethods = numberOfMethods;
        this.numberOfImports = numberOfImports;
    }

    public ResultData() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ResultData that = (ResultData) o;
        return type == that.type
                && uppercaseL == that.uppercaseL
                && loc == that.loc
                && commentLoc == that.commentLoc
                && numberOfMethods == that.numberOfMethods
                && numberOfImports == that.numberOfImports
                && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ResultData.class.getSimpleName() + "[", "]")
                .add("type=" + type)
                .add("name='" + name + "'")
                .add("L=" + uppercaseL)
                .add("LOC=" + loc)
                .add("commentLOC=" + commentLoc)
                .add("numMethod=" + numberOfMethods)
                .add("nImports=" + numberOfImports)
                .toString();
    }

    public static enum ProgrammingLanguage {
        JAVA("Java"),
        PYTHON("Python"),
        OTHER("other");

        private String printableRepresentation;

        ProgrammingLanguage(String printableRepresentation) {
            this.printableRepresentation = printableRepresentation;
        }

        @Override
        public String toString() {
            return this.printableRepresentation;
        }
    }

}

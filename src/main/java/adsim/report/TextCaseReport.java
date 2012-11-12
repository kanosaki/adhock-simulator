package adsim.report;

import java.io.OutputStream;
import java.io.PrintStream;

import adsim.core.ICaseReport;

public class TextCaseReport implements ICaseReport {
    PrintStream out;

    public TextCaseReport(OutputStream outstream) {
        out = new PrintStream(outstream);
    }
}

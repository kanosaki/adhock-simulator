package adsim.report;

import lombok.*;
import java.io.PrintStream;
import java.util.LinkedList;

public class Reporter {
    private String[] headers;
    private LinkedList<Object[]> data;

    public Reporter(String[] colHeaders) {
        this.headers = colHeaders;
        this.data = new LinkedList<Object[]>();
    }

    public void add(Object[] column) {
        if (column == null || headers.length != column.length) {
            throw new IllegalArgumentException(
                    "Column data must not null and same length to header");
        }
        data.add(column);
    }

    private void dumpAsCsvRow(PrintStream ps, Object[] data, String delimiter) {
        val len = data.length;
        for (int i = 0; i < len - 1; i++) {
            ps.print(data[i]);
            ps.print(delimiter);
        }
        ps.print(data[len - 1]);
        ps.println();
    }

    public void dump(PrintStream ps) {
        dumpAsCsvRow(ps, headers, ", ");
        for (val row : data) {
            dumpAsCsvRow(ps, row, ", ");
        }
    }
}

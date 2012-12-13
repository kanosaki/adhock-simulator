package adsim;

import lombok.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lombok.*;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

import adsim.core.Case;
import adsim.core.Case.CollectMode;
import adsim.core.CompositeScenario;
import adsim.core.ICase;
import adsim.misc.Param;

/**
 * Simulator entry point
 */
@SuppressWarnings("unchecked")
public class App {
    /**
     * デフォルトの挙動では、組み込みのシミュレーションを行って、その結果をresult.csvに出力します
     */
    public static void main(String[] args) {
        ExecParams params;
        Collection<ICase> cases = null;
        if (args.length == 0) {
            // execWithDefault();
            execWithVisualization();
        } else {
            try {
                params = new ExecParams(args);
                val scenario = new CompositeScenario(casesByParams(
                        params.getTryCount(), params.getWatchNodesCount(),
                        params.getParams()),
                        params.getOutput());
                SimulatorService.startAndReport(scenario, params.isVisualze());
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
                System.exit(-1);
            } catch (ParseException e) {
                System.out.println("Invalid arguments!!");
                System.out.println(e.getMessage());
                System.exit(-1);
            }
        }
        System.exit(0);
    }

    private static void execWithDefault() {
        val scenario = new CompositeScenario(defaultCases(), System.out);
        SimulatorService.startAndReport(scenario, false);
    }

    private static void execWithVisualization() {
        val cases = visualzeCases();
        val scenario = new CompositeScenario(cases, System.out);
        SimulatorService.startAndReport(scenario, true);
    }

    private static Collection<ICase> casesByParams(int tryCount,
            int watchCount,
            Param[] params) {
        return ScenarioBuilder.buildCase(tryCount, watchCount, params[0],
                params[1],
                params[2], params[3], params[4], params[5]);

    }

    private static List<ICase> defaultCases() {
        return ScenarioBuilder.buildCase(1, 0,
                Param.enumerate(50),
                Param.enumerate(1000.0), Param.enumerate(3),
                Param.enumerate(
                        CollectMode.FIFO, CollectMode.RecentKeep,
                        CollectMode.RegularKeep),
                Param.enumerate(10000), Param.enumerate(0.01));
    }

    private static List<ICase> visualzeCases() {
        return ScenarioBuilder.buildCase(1, 1,
                Param.enumerate(50),
                Param.enumerate(1000.0), Param.enumerate(3),
                Param.enumerate(
                        CollectMode.FIFO),
                Param.enumerate(10000), Param.enumerate(0.01));
    }

    /*
     * Example: java -jar adsim.jar [-i <filename(scenario.xml)>] [-o
     * <filename(result.csv)>] [-v] [--help]
     */
    /**
     * CLI引数を解析して利用しやすい形に整形します
     */
    public static class ExecParams {
        private CommandLine cl;
        @Getter
        private Param[] params;

        public ExecParams(String[] args) throws ParseException {
            val optparser = new PosixParser();
            cl = optparser.parse(createOptions(), args);
            if (cl.getArgs().length == 0) {
                throw new RuntimeException("Argument required");
            }
            params = parseParams();
        }

        private static Options createOptions() {
            val opts = new Options();
            opts.addOption("o", true, "Output file name");
            opts.addOption("i", true, "Scenario file");
            opts.addOption("", "visualize", false, "Show visualizer");
            opts.addOption("", "watch", true, "Watch node count");
            opts.addOption("format", true, "Output format");
            return opts;
        }

        private Param parseValue(String val) throws ParseException {
            val ret = new ArrayList<Object>();
            for (val expr : val.split(",")) {
                if (expr.equalsIgnoreCase("fifo")) {
                    ret.add(CollectMode.FIFO);
                } else if (expr.equalsIgnoreCase("recent")) {
                    ret.add(CollectMode.RecentKeep);
                } else if (expr.equalsIgnoreCase("regular")) {
                    ret.add(CollectMode.RegularKeep);
                } else {
                    try {
                        ret.add(Integer.parseInt(expr));
                    } catch (NumberFormatException ne) {
                        try {
                            ret.add(Double.parseDouble(expr));
                        } catch (NumberFormatException nfe) {
                            throw new ParseException("Unknown value " + expr);
                        }
                    }
                }
            }
            return Param.enumerate(ret.toArray());
        }

        public int getTryCount() {
            if (cl.getArgs().length == 2) {
                return Integer.parseInt(cl.getArgs()[1]);
            } else {
                return 1;
            }
        }

        private Param[] parseParams() throws ParseException {
            String[] args = cl.getArgs()[0].split(":");
            Param[] ret = new Param[args.length];
            for (int i = 0; i < ret.length; i++) {
                ret[i] = parseValue(args[i]);
            }
            return ret;
        }

        public String getInputFile() {
            return cl.hasOption('i') ? cl.getOptionValue('i') : null;
        }

        public String getOutputFile() {
            return cl.getOptionValue('o', "result.csv");
        }

        public boolean isVisualze() {
            return cl.hasOption("visualize");
        }

        public int getWatchNodesCount() {
            return cl.hasOption("watch") ? Integer.parseInt(cl
                    .getOptionValue("watch")) : 0;
        }

        public OutputStream getOutput() throws IOException {
            return new FileOutputStream(getOutputFile());
        }
    }
}

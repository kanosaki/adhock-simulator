package adsim;

import lombok.*;
import java.util.ArrayList;
import java.util.Collection;

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
public class App {
    /**
     * デフォルトの挙動では、組み込みのシミュレーションを行って、その結果をresult.csvに出力します
     */
    public static void main(String[] args) {
        ExecParams params;
        try {
            params = new ExecParams(args);
        } catch (ParseException e) {
            System.out.println("Invalid arguments!!");
            System.out.println(e.getMessage());
            System.exit(-1);
        }
        Collection<ICase> cases = ScenarioBuilder.buildCase(
                Param.enumerate(10, 100, 1000),
                Param.enumerate(10.0, 100.0, 1000.0), Param.enumerate(0),
                Param.enumerate(CollectMode.FIFO),
                Param.enumerate(1000), Param.enumerate(0.01));

        // val cases = new ArrayList<ICase>();
        // val cas1 = new Case(100, 1000, 1, CollectMode.RecentKeep, 100000,
        // 0.01);
        // cas1.setWatchNodeCount(0);
        // cases.add(cas1);
        val scenario = new CompositeScenario(cases, System.out);
        SimulatorService.start(scenario, false);
        // System.exit(0);
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

        public ExecParams(String[] args) throws ParseException {
            val optparser = new PosixParser();
            cl = optparser.parse(createOptions(), args);
        }

        private static Options createOptions() {
            val opts = new Options();
            opts.addOption("o", true, "Output file name");
            opts.addOption("i", true, "Scenario file");
            opts.addOption("format", true, "Output format");
            return opts;
        }

        public String getInputFile() {
            return cl.hasOption('i') ? cl.getOptionValue('i') : null;
        }

        public String getOutputFile() {
            return cl.getOptionValue('o', "result.csv");
        }
    }
}

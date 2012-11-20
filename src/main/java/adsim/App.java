package adsim;

import lombok.*;
import java.util.ArrayList;

import lombok.*;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

import adsim.core.Case;
import adsim.core.Case.CollectMode;
import adsim.core.CompositeScenario;
import adsim.core.ICase;

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
        val cases = new ArrayList<ICase>();
        cases.add(new Case(10, 10, 0, CollectMode.RegularKeep));
        val scenario = new CompositeScenario(cases, System.out);
        SimulatorService.start(scenario);
        System.exit(0);
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

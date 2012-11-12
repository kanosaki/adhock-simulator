package adsim;

import lombok.*;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

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

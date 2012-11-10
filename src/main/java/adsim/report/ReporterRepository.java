package adsim.report;

import adsim.core.IReporter;

public class ReporterRepository {
    private static ReporterRepository instance;

    public static ReporterRepository getInstance() {
        return instance == null
                ? instance = new ReporterRepository()
                : instance;
    }
    
    public static void injectInstance(ReporterRepository repo) {
        instance = repo;
    }

    public MessageReporter getMessageReporter() {
        throw new UnsupportedOperationException("Not implemented");
    }

    public NodeReporter getNodeReporter() {
        throw new UnsupportedOperationException("Not implemented");
    }

    public IReporter[] getAllReporters() {
        return new IReporter[] {
                this.getMessageReporter(), this.getNodeReporter()
        };
    }
}

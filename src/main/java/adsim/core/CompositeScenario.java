package adsim.core;

import lombok.*;
import java.util.Collection;

import adsim.report.ReporterRepository;


public abstract class CompositeScenario implements IScenario {
	@Setter
	private String name;
	
	private Collection<ICase> cases;
	
	public String getName(){
		if(this.name == null)
			return this.name = this.getClass().getSimpleName();
		else
			return this.name;
			
	}

	@Override
	public void init(Simulator sim) {
	}

	public ReporterRepository createRepoters() {
	    return new ReporterRepository();
	}
	
	protected abstract Collection<ICase> createCases();

    @Override
    public Collection<ICase> getCases() {
        if(cases == null)
            cases = createCases();
        return cases;
    }
}

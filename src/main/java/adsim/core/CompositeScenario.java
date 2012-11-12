package adsim.core;

import lombok.*;

import java.util.ArrayList;
import java.util.Collection;

import adsim.report.ReporterRepository;


public class CompositeScenario implements IScenario {
	@Setter
	private String name;
	
	@Getter
	private Collection<ICase> cases;
	
	public CompositeScenario(Collection<ICase> cases) {
	    this.cases = new ArrayList<ICase>(cases);
	}
	
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
}

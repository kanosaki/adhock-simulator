package adsim.model;

public enum WorkerState {
	Ready, Running, Completed, Failed;
	
	public WorkerState proceed() {
		switch (this) {
		case Ready:
			return Running;
		case Running:
			return Completed;
		case Failed:
			throw new IllegalStateException("WorkerState.Failed is not allowd to proceed.");
		default:
			throw new IllegalStateException("This statement must not be executed.");
		}
	}
}

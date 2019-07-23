package com.hit.processes;

import java.util.List;

public class RunConfiguration {

	private List<ProcessCycles> processesCycles;

	public RunConfiguration(List<ProcessCycles> processCycles) {
		this.setProcessCycles(processCycles);
	}

	public List<ProcessCycles> getProcessCycles() {
		return processesCycles;
	}

	public void setProcessCycles(List<ProcessCycles> processCycles) {
		this.processesCycles = processCycles;

	}

	public String toString() {
		return "processCycles [processCycles=" + processesCycles + "]";

	}

}

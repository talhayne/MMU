package com.hit.processes;

import java.util.List;

public class ProcessCycles {

	private List<ProcessCycle> processCycles;

	public ProcessCycles(List<ProcessCycle> processCycles) {
		this.setProcessCycles(processCycles);
	}

	public List<ProcessCycle> getProcessCycles() {
		return processCycles;
	}

	public void setProcessCycles(List<ProcessCycle> processCycles) {
		this.processCycles = processCycles;
	}

	@Override
	public String toString() {
		return "ProcessCycles [processCycles=" + this.processCycles + "]";
	}

}

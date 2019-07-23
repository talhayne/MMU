package com.hit.processes;

import java.util.List;

public class ProcessCycle {
	private List<Long> pages;
	private List<byte[]> data;
	private int sleepMs;

	public ProcessCycle(List<Long> pages, int sleepMs, List<byte[]> data) {
		this.setPages(pages);
		this.setData(data);
		this.setSleepMs(sleepMs);
	}

	public List<Long> getPages() {
		return pages;
	}

	public void setPages(List<Long> pages) {
		this.pages = pages;
	}

	public int getSleepMs() {
		return sleepMs;
	}

	public void setSleepMs(int sleepMs) {
		this.sleepMs = sleepMs;
	}

	public List<byte[]> getData() {
		return data;
	}

	public void setData(List<byte[]> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(pages.toString());
		stringBuilder.append(System.lineSeparator());
		Integer.toString(sleepMs);
		stringBuilder.append(System.lineSeparator());
		stringBuilder.append(data.toString());

		return stringBuilder.toString();
	}
}

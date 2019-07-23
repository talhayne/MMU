package com.hit.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import com.hit.driver.CLI;
import com.hit.model.Model;
import com.hit.model.MMUModel;
import com.hit.view.View;
import com.hit.view.MMUView;

public class MMUController implements Controller, Observer {

	private Model model;
	private View view;
	private CLI cli;

	public MMUController(Model model, View view) {
		this.model = model;
		this.view = view;
	}

	public void setCLI(CLI cli) {
		this.cli = cli;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o == cli) {
			List<String> configurations = Arrays.asList((String[]) arg);
			((MMUModel) model).setConfiguration(configurations);
			model.start();
		} else if (o == model) {
			@SuppressWarnings("unchecked")
			List<String> logList = (List<String>) arg;
			((MMUView) view).setLogList(logList);
			view.start();
		} else if (o == view) {

		}
	}
}

package ai.sygnet.spt;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public enum ReportDAO {
	instance;

	private static final String SOURCE_URL = "https://raw.githubusercontent.com/morkro/coding-challenge/master/data/reports.json";

	private Map<String, Report> model = new HashMap<>();

	private ReportDAO() {
		/*
		 * Populate the model with JSON data retrieved from GitHub
		 */
		ObjectMapper mapper = new ObjectMapper();
		Reports reports = null;
		try {
			reports = mapper.readValue(new URL(SOURCE_URL), Reports.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (Report report : reports.elements)
			model.put(report.id, report);
	}

	public Map<String, Report> getModel() {
		return model;
	}

	public static class Reports {
		public Integer size;
		public String nextOffset;
		public List<Report> elements;
	}

}
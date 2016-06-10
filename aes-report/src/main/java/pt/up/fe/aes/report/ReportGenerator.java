package pt.up.fe.aes.report;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

import pt.up.fe.aes.base.spectrum.Spectrum;

public class ReportGenerator {

	private static final String SPECTRA_EXT = ".csv";

	private final OverallReport report;

	public ReportGenerator(String projectName, Spectrum spectrum, String granularity, List<String> classesToInstrument) {
		this.report = new OverallReport(projectName, spectrum, granularity, classesToInstrument);
	}

	public void generate(File reportDirectory) {
		try {
			File projectSpectrum = new File(reportDirectory, "spectrum" + SPECTRA_EXT);
			FileUtils.writeLines(projectSpectrum, report.exportSpectrum(), false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

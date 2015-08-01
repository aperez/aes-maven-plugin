package pt.up.fe.aes.report.metrics;

import java.util.ArrayList;
import java.util.List;

import pt.up.fe.aes.base.spectrum.AdditiveSpectrumBuilder;
import pt.up.fe.aes.base.spectrum.Spectrum;
import pt.up.fe.aes.report.metrics.ApproximateEntropyMetric.GlobalApproximateEntropyMetric;

public class ProgressionMetric extends AbstractMetric {

	@Override
	public String calculate() {
		
		List<Metric> metrics = new ArrayList<Metric>() {{
			add(new TestSizeMetric());
			add(new CoverageMetric());
			add(new GlobalApproximateEntropyMetric());
		}};
		
		String output = "";
		
		AdditiveSpectrumBuilder asb = new AdditiveSpectrumBuilder(spectrum, false);
		asb.setPartitionNumber(5);
		
		while(asb.hasNext()) {
			Spectrum currentSpectrum = asb.getSpectrum();
			//Size - coverage - entropy - approx
			for(Metric metric : metrics) {
				metric.setSpectrum(currentSpectrum);
				output += metric.calculate() + " ";
			}
			
			output += "\n";
		}
		
		return output;
	}

	@Override
	public String getName() {
		return "Metric progression";
	}

}

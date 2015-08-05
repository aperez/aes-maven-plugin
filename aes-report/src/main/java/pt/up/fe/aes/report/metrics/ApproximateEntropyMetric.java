package pt.up.fe.aes.report.metrics;

import pt.up.fe.aes.base.spectrum.Spectrum;
import pt.up.fe.aes.report.metrics.SimpsonMetric.GlobalInvertedSimpsonMetric;
import pt.up.fe.aes.report.metrics.SimpsonMetric.InvertedSimpsonMetric;
import pt.up.fe.aes.report.metrics.reducers.MultiplicationReducer;

public class ApproximateEntropyMetric extends AbstractDoubleMetric {

	private AbstractDoubleMetric metric;
	
	public ApproximateEntropyMetric() {
		this.metric = generateMetric();
	}
	
	protected AbstractDoubleMetric generateMetric() {
		return new MultiplicationReducer(
						new RhoMetric(), 
						new InvertedSimpsonMetric(), 
						new AmbiguityMetric());
	}
	
	@Override
	public void setSpectrum(Spectrum spectrum) {
		metric.setSpectrum(spectrum);
	}

	@Override
	public double calculateValue() {
		return metric.calculateValue();
	}

	@Override
	public String getName() {
		return "Approximate Entropy";
	}
	
	public static class GlobalApproximateEntropyMetric extends ApproximateEntropyMetric {
		@Override
		protected AbstractDoubleMetric generateMetric() {
			return new MultiplicationReducer(
						new RhoMetric(), 
						new GlobalInvertedSimpsonMetric(), 
						new AmbiguityMetric());
		}
	}
}

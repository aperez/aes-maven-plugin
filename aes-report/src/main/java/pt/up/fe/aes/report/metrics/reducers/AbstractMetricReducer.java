package pt.up.fe.aes.report.metrics.reducers;

import pt.up.fe.aes.base.spectrum.Spectrum;
import pt.up.fe.aes.report.metrics.AbstractDoubleMetric;

public abstract class AbstractMetricReducer extends AbstractDoubleMetric {

	private AbstractDoubleMetric metrics[];
	
	public AbstractMetricReducer(AbstractDoubleMetric... metrics) {
		this.metrics = metrics;
	}
	
	@Override
	public void setSpectrum(Spectrum spectrum) {
		for (AbstractDoubleMetric m : metrics) {
			m.setSpectrum(spectrum);
		}
	}

	@Override
	public double calculateValue() {
		double tmp = startValue();
		
		for (AbstractDoubleMetric m : metrics) {
			tmp = reduce(tmp, m.calculateValue());
		}
		
		return tmp;
	}
	
	@Override
	public String getName() {
		return "Reducer";
	}
	
	protected abstract double startValue();

	protected abstract double reduce(double value1, double value2);

}

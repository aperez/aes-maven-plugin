package pt.up.fe.aes.report.metrics;


public abstract class AbstractDoubleMetric extends AbstractMetric {
	
	@Override
	public String calculate() {
		return String.format("%.4f", calculateValue());
	}

	public abstract double calculateValue();
}

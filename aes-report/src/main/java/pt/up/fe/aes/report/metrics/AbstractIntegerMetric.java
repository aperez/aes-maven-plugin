package pt.up.fe.aes.report.metrics;

public abstract class AbstractIntegerMetric extends AbstractMetric {

	@Override
	public String calculate() {
		return String.valueOf(calculateValue());
	}

	public abstract int calculateValue();
	
}

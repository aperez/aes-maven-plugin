package pt.up.fe.aes.report.metrics;

public class TestSizeMetric extends AbstractMetric {

	@Override
	public double calculate() {
		return spectrum.getTransactionsSize();
	}

	@Override
	public String getName() {
		return "Test Size";
	}
}

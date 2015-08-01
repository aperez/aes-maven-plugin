package pt.up.fe.aes.report.metrics;

public class TestSizeMetric extends AbstractIntegerMetric {

	@Override
	public int calculateValue() {
		return spectrum.getTransactionsSize();
	}

	@Override
	public String getName() {
		return "Test Size";
	}
}

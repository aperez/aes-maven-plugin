package pt.up.fe.aes.report.metrics;

public class ComponentSizeMetric extends AbstractIntegerMetric {

	@Override
	public int calculateValue() {
		return spectrum.getComponentsSize();
	}

	@Override
	public String getName() {
		return "Component Size";
	}

}

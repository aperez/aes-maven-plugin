package pt.up.fe.aes.report.metrics;

public class ComponentSizeMetric extends AbstractMetric {

	@Override
	public double calculate() {
		return spectrum.getComponentsSize();
	}

	@Override
	public String getName() {
		return "Component Size";
	}

}

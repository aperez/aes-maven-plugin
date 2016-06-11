package pt.up.fe.aes.base.spectrum;

import pt.up.fe.aes.base.events.EventListener;
import pt.up.fe.aes.base.model.Node.Type;

public class SpectrumBuilder implements EventListener {

	protected SpectrumImpl spectrum;
	
	public SpectrumBuilder() {
		resetSpectrum();
	}
	
	public void resetSpectrum() {
		spectrum = new SpectrumImpl();
	}
	
	public Spectrum getSpectrum() {
		return spectrum;
	}
	
	@Override
	public void endTransaction(String transactionName, boolean[] activity, boolean isError) {
		spectrum.addTransaction(transactionName, activity, isError);
	}
	
	@Override
	public void endTransaction(String transactionName, boolean[] activity, int hashCode, boolean isError) {
		spectrum.addTransaction(transactionName, activity, hashCode, isError);
	}

	@Override
	public void addNode(int id, String name, Type type, int parentId, int startLine, int endLine) {
		spectrum.getTree().addNode(name, type, parentId, startLine, endLine);
	}

	@Override
	public void addProbe(int id, int nodeId) {
		spectrum.addProbe(id, nodeId);
	}

	@Override
	public void endSession() {
	}

}

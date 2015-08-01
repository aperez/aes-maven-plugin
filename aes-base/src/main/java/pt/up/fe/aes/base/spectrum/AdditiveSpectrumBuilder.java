package pt.up.fe.aes.base.spectrum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pt.up.fe.aes.base.model.Tree;

public class AdditiveSpectrumBuilder extends SpectrumBuilder {

	private final Spectrum source;
	private final List<Integer> indexes;
	private final int testSize;
	
	private int currentIndex = 0;
	private int step = 1;
	
	public AdditiveSpectrumBuilder(Spectrum source) {
		this(source, true);
	}
	
	public AdditiveSpectrumBuilder(Spectrum source, boolean shuffle) {
		this.source = source;
		this.testSize = source.getTransactionsSize();
		
		this.indexes = new ArrayList<Integer>(this.testSize);

		for(int t = 0; t < this.testSize; t++) {
			this.indexes.add(t);
		}
		
		if(shuffle) {
			Collections.shuffle(this.indexes);
		}
	}
	
	public void setPartitionNumber(int partitions) {
		this.step = this.testSize / partitions;
		
		if (this.testSize % partitions != 0) {
			this.step += 1;
		}
	}
	
	public void setStepSize(int step) {
		this.step = 0;
	}
	
	public boolean hasNext() {
		return this.currentIndex < this.testSize;
	}
	
	@Override
	public Spectrum getSpectrum() {
		resetSpectrum();
		
		currentIndex += step;
		
		Tree tree = source.getTree();
		spectrum.setTree(tree);
		
		for (int probeId = 0; probeId < source.getComponentsSize(); probeId++) {
			addProbe(probeId, source.getNodeOfProbe(probeId).getId());
		}
		
		for (int t = 0; t < this.currentIndex && t < this.testSize; t++) {
			String transactionName = source.getTransactionName(t);
			boolean[] activity = source.getTransactionActivityArray(t);
			int hashCode = source.getTransactionHashCode(t);
			boolean isError = source.isError(t);
			
			endTransaction(transactionName, activity, hashCode, isError);
		}
		
		return spectrum;
	}
	
}

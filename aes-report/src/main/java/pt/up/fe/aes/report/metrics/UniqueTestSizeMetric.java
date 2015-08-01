package pt.up.fe.aes.report.metrics;

import java.util.HashSet;
import java.util.Set;

public class UniqueTestSizeMetric extends AbstractIntegerMetric {

	@Override
	public int calculateValue() {

		Set<Integer> s = new HashSet<Integer>();

		for(int t = 0; t < spectrum.getTransactionsSize(); t++) {
			s.add(getHashCode(t));
		}

		return s.size();
	}

	protected int getHashCode(int t) {
		return spectrum.getTransactionActivity(t).hashCode();
	}

	@Override
	public String getName() {
		return "Unique Test Size";
	}


	public static class GlobalUniqueTestSizeMetric extends UniqueTestSizeMetric {
		@Override
		protected int getHashCode(int t) {
			return spectrum.getTransactionHashCode(t);
		}

		@Override
		public String getName() {
			return "(Global) Unique Test Size";
		}
	}
}

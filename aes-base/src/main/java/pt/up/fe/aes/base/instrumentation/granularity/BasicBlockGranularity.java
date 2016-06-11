package pt.up.fe.aes.base.instrumentation.granularity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javassist.CtBehavior;
import javassist.CtClass;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.CodeIterator;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.analysis.ControlFlow;
import javassist.bytecode.analysis.ControlFlow.Block;
import pt.up.fe.aes.base.model.Node;
import pt.up.fe.aes.base.runtime.Collector;

public class BasicBlockGranularity extends AbstractGranularity {

	private Queue<Integer> blocks = new LinkedList<Integer>();
	private List<Integer> blockList = new ArrayList<Integer>();

	public BasicBlockGranularity(CtClass c, MethodInfo mi, CodeIterator ci) {
		super(c, mi, ci);			
		try {
			ControlFlow cf = new ControlFlow(c, mi);
			for(Block block : cf.basicBlocks()) {
				blocks.add(block.position());
				blockList.add(block.position());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean instrumentAtIndex(int index, int instrumentationSize) {
		boolean outcome = !blocks.isEmpty() && index >= instrumentationSize + blocks.peek();
		if (outcome) blocks.poll();
		return outcome;
	}

	@Override
	public boolean stopInstrumenting() {
		return blocks.isEmpty();
	}
	
	public Node getNode(CtClass cls, CtBehavior m, int line, MethodInfo info, CodeAttribute ca, int index, int instrumentationSize) {
		Collector c = Collector.instance();
		Node parent = getNode(cls, m, info, ca);

		int currentIndex = 0;
		
		int i = 0;
		for (; i < blockList.size(); i++) {
			if (index >= instrumentationSize + blockList.get(i)) {
				currentIndex = index;
			}
			else {
				break;
			}
		}
		
		int endIndex = currentIndex;
		
		if (i < blockList.size()) {
			endIndex = instrumentationSize + blockList.get(i);
		}
		else {
			//TODO: iterate until end of method AND check if endIndex >= currentIndex + instrumentationSize
			int newEndIndex = 0;
			try {
				CodeIterator ci = ca.iterator();
				while (ci.hasNext()) {
					newEndIndex = ci.next();
				}
			} catch (BadBytecode e) {
				e.printStackTrace();
			}
			
			endIndex = Math.max(newEndIndex, endIndex);
		}
		
		int startLine = info.getLineNumber(currentIndex);
		int endLine = info.getLineNumber(endIndex);
		
		return getNode(c, parent, String.valueOf(line), Node.Type.LINE, startLine, endLine);
	}
}
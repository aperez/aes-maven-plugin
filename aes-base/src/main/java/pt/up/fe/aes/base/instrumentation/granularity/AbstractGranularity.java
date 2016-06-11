package pt.up.fe.aes.base.instrumentation.granularity;

import java.util.StringTokenizer;

import javassist.CtBehavior;
import javassist.CtClass;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.CodeIterator;
import javassist.bytecode.Descriptor;
import javassist.bytecode.MethodInfo;
import pt.up.fe.aes.base.model.Node;
import pt.up.fe.aes.base.runtime.Collector;

public abstract class AbstractGranularity implements Granularity {

	protected CtClass c;
	protected MethodInfo mi;
	protected CodeIterator ci;

	public AbstractGranularity(CtClass c, MethodInfo mi, CodeIterator ci) {
		this.c = c;
		this.mi = mi;
		this.ci = ci;
	}

	private Node getNode(Collector c, Node parent, String name, Node.Type type, int startLine, int endLine) {
		Node node = parent.getChild(name);

		if (node == null) {
			node = c.createNode(parent, name, type, startLine, endLine);
		}

		return node;
	}

	protected Node getNode(CtClass cls) {
		Collector c = Collector.instance();
		Node node = c.getRootNode();
		String tok = cls.getName();

		// Extract Package Hierarchy
		int pkgEnd = tok.lastIndexOf(".");

		if (pkgEnd >= 0) {
			StringTokenizer stok = new StringTokenizer(tok.substring(0, pkgEnd), ".");

			while (stok.hasMoreTokens()) {
				node = getNode(c, node, stok.nextToken(), Node.Type.PACKAGE, -1, -1);
			}
		} 
		else {
			pkgEnd = -1;
		}


		// Extract Class Hierarchy
		StringTokenizer stok = new StringTokenizer(tok.substring(pkgEnd + 1), "$");

		while (stok.hasMoreTokens()) {
			tok = stok.nextToken();
			node = getNode(c, node, tok, Node.Type.CLASS, -1, -1);
		}


		return node;
	}

	protected Node getNode(CtClass cls, CtBehavior m, MethodInfo info, CodeAttribute ca) {
		Collector c = Collector.instance();
		Node parent = getNode(cls);

		int startLine= -1;
		int endLine = -1;

		try {
			CodeIterator ci = ca.iterator();
			while (ci.hasNext()) {
				endLine = info.getLineNumber(ci.next());
				if (startLine == -1) {
					startLine = endLine;
				}
			}
		} catch (BadBytecode e) {
			e.printStackTrace();
		}

		return getNode(c, parent, m.getName() + Descriptor.toString(m.getSignature()), Node.Type.METHOD, startLine, endLine);
	}

	public Node getNode(CtClass cls, CtBehavior m, int line, MethodInfo info, CodeAttribute ca) {
		Collector c = Collector.instance();
		Node parent = getNode(cls, m, info, ca);

		return getNode(c, parent, String.valueOf(line), Node.Type.LINE, -1, -1);
	}

}
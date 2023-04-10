package implementacion.semops;

import implementacion.asint.TinyASint;

public class SemOps extends TinyASint {
	public Exp exp(char op, Exp arg0, Exp arg1) {
		switch (op) {
		case '+':
			return suma(arg0, arg1);
		case '-':
			return resta(arg0, arg1);
		case '*':
			return mul(arg0, arg1);
		case '/':
			return div(arg0, arg1);
		}
		throw new UnsupportedOperationException("exp " + op);
	}

	public Prog prog(Decs decs, Is is) {
		return prog(decs, is);
	}
}

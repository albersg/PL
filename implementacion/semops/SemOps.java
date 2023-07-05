package implementacion.semops;

import implementacion.asint.TinyASint;

public class SemOps extends TinyASint {
	public Exp expb(String op, Exp arg0, Exp arg1) {
		switch (op) {
		case "+":
			return suma(arg0, arg1);
		case "-":
			return resta(arg0, arg1);
		case "*":
			return mul(arg0, arg1);
		case "/":
			return div(arg0, arg1);
		case "<":
			return menor(arg0, arg1);
		case ">":
			return mayor(arg0, arg1);
		case "<=":
			return menorIgual(arg0, arg1);
		case ">=":
			return mayorIgual(arg0, arg1);
		case "==":
			return igual(arg0, arg1);
		case "!=":
			return distinto(arg0, arg1);
		case "and":
			return and(arg0, arg1);
		case "or":
			return or(arg0, arg1);
		case "%":
			return mod(arg0, arg1);
		}
		throw new UnsupportedOperationException("exp " + op);
	}

	public Exp expu(String op, Exp arg) {
		switch (op) {
		case "neg":
			return negativo(arg);
		case "not":
			return not(arg);
		}
		throw new UnsupportedOperationException("exp " + op);
	}

}

package implementacion.c_ast_ascendente;

import implementacion.asint.TinyASint.Exp;
import implementacion.asint.TinyASint.StringLocalizado;

public class MemOp {
	public String op;
	public Exp exp;
	public StringLocalizado valor;
	
	public MemOp(String op, Exp exp, StringLocalizado valor) {
		this.op = op;
		this.exp = exp;
		this.valor = valor;
	}
}

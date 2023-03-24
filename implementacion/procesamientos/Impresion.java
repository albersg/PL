package implementacion.procesamientos;

import implementacion.asint.ProcesamientoPorDefecto;
import implementacion.asint.TinyASint.Dec;
import implementacion.asint.TinyASint.Decs_muchas;
import implementacion.asint.TinyASint.Decs_una;
import implementacion.asint.TinyASint.Div;
import implementacion.asint.TinyASint.Exp;
import implementacion.asint.TinyASint.Id;
import implementacion.asint.TinyASint.Mul;
import implementacion.asint.TinyASint.Num;
import implementacion.asint.TinyASint.Prog_con_decs;
import implementacion.asint.TinyASint.Prog_sin_decs;
import implementacion.asint.TinyASint.Resta;
import implementacion.asint.TinyASint.Suma;

public class Impresion extends ProcesamientoPorDefecto {
	public Impresion() {
	}

	private void imprime_arg(Exp arg, int p) {
		if (arg.prioridad() < p) {
			System.out.print("(");
			arg.procesa(this);
			System.out.print(")");
		} else {
			arg.procesa(this);
		}
	}

	public void procesa(Dec dec) {
		System.out.print("  " + dec.id() + "=" + dec.val());
	}

	public void procesa(Decs_muchas decs) {
		decs.decs().procesa(this);
		System.out.println(",");
		decs.dec().procesa(this);
	}

	public void procesa(Decs_una decs) {
		decs.dec().procesa(this);
	}

	public void procesa(Div exp) {
		imprime_arg(exp.arg0(), 1);
		System.out.print("/");
		imprime_arg(exp.arg1(), 2);
	}

	public void procesa(Id exp) {
		System.out.print(exp.id());
	}

	public void procesa(Mul exp) {
		imprime_arg(exp.arg0(), 1);
		System.out.print("*");
		imprime_arg(exp.arg1(), 2);
	}

	public void procesa(Num exp) {
		System.out.print(exp.num());
	}

	public void procesa(Prog_con_decs prog) {
		System.out.println("evalua");
		System.out.print("  ");
		prog.exp().procesa(this);
		System.out.println();
		System.out.println("donde");
		prog.decs().procesa(this);
		System.out.println();
	}

	public void procesa(Prog_sin_decs prog) {
		System.out.println("evalua");
		System.out.print("  ");
		prog.exp().procesa(this);
		System.out.println();
	}

	public void procesa(Resta exp) {
		imprime_arg(exp.arg0(), 0);
		System.out.print("+");
		imprime_arg(exp.arg1(), 1);
	}

	public void procesa(Suma exp) {
		imprime_arg(exp.arg0(), 0);
		System.out.print("+");
		imprime_arg(exp.arg1(), 1);
	}
}

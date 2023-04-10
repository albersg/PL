package implementacion.procesamientos;

import java.util.Stack;

import implementacion.asint.ProcesamientoPorDefecto;
import implementacion.asint.TinyASint.Acc;
import implementacion.asint.TinyASint.And;
import implementacion.asint.TinyASint.Asig;
import implementacion.asint.TinyASint.Call;
import implementacion.asint.TinyASint.DecProc;
import implementacion.asint.TinyASint.DecTipo;
import implementacion.asint.TinyASint.Decs_muchas;
import implementacion.asint.TinyASint.Decs_una;
import implementacion.asint.TinyASint.Decs_vacia;
import implementacion.asint.TinyASint.DecVar;
import implementacion.asint.TinyASint.Delete;
import implementacion.asint.TinyASint.Distinto;
import implementacion.asint.TinyASint.Div;
import implementacion.asint.TinyASint.Dref;
import implementacion.asint.TinyASint.False;
import implementacion.asint.TinyASint.IComp;
import implementacion.asint.TinyASint.Id;
import implementacion.asint.TinyASint.If_then;
import implementacion.asint.TinyASint.If_then_else;
import implementacion.asint.TinyASint.Igual;
import implementacion.asint.TinyASint.Indx;
import implementacion.asint.TinyASint.Is_muchas;
import implementacion.asint.TinyASint.Is_una;
import implementacion.asint.TinyASint.LitInt;
import implementacion.asint.TinyASint.LitReal;
import implementacion.asint.TinyASint.LitStr;
import implementacion.asint.TinyASint.Mayor;
import implementacion.asint.TinyASint.MayorIgual;
import implementacion.asint.TinyASint.Menor;
import implementacion.asint.TinyASint.MenorIgual;
import implementacion.asint.TinyASint.Modulo;
import implementacion.asint.TinyASint.Mul;
import implementacion.asint.TinyASint.Negativo;
import implementacion.asint.TinyASint.New;
import implementacion.asint.TinyASint.Nl;
import implementacion.asint.TinyASint.Not;
import implementacion.asint.TinyASint.Null;
import implementacion.asint.TinyASint.Or;
import implementacion.asint.TinyASint.PForm;
import implementacion.asint.TinyASint.PForm_muchas;
import implementacion.asint.TinyASint.PForm_una;
import implementacion.asint.TinyASint.PForms;
import implementacion.asint.TinyASint.PReal;
import implementacion.asint.TinyASint.PReal_muchos;
import implementacion.asint.TinyASint.PReal_uno;
import implementacion.asint.TinyASint.PReals;
import implementacion.asint.TinyASint.Prog;
import implementacion.asint.TinyASint.Read;
import implementacion.asint.TinyASint.Resta;
import implementacion.asint.TinyASint.Suma;
import implementacion.asint.TinyASint.True;
import implementacion.asint.TinyASint.While;
import implementacion.asint.TinyASint.Write;
import implementacion.asint.TinyASint.tNodo;

public class Etiquetado extends ProcesamientoPorDefecto{
	
	private int etq;
	private Stack<DecProc> procs;
	
	public Etiquetado() {
		this.etq = 0;
		procs = new Stack<DecProc>();
	}
	
	@Override
	public void procesa(Prog prog) {
		prog.is().procesa(this);
		prog.decs().recolecta_procs(this);
		
		while(!procs.empty()) {
			DecProc p = procs.pop();
			p.procesa(this);
		}
	}
	

	@Override
	public void procesa(Div exp) {
		exp.setEtqInic(etq);
		// Primer argumento
		exp.arg0().procesa(this);
		if(exp.arg0().getEsDesig()) {
			etq++;
		}
		if(exp.getTipo() == tNodo.REAL && exp.arg0().getTipo() == tNodo.INT)
			etq++;
			
		// Segundo argumento
		exp.arg1().procesa(this);
		if(exp.arg1().getEsDesig()) {
			etq++;
		}
		if(exp.getTipo() == tNodo.REAL && exp.arg1().getTipo() == tNodo.INT)
			etq++;
		
		// Generación de instrucción aritmética
		if(exp.getTipo() == tNodo.INT)
			etq++;
		else if(exp.getTipo() == tNodo.REAL)
			etq++;
		exp.setEtqSig(etq);
	}

	@Override
	public void procesa(Id exp) {
		exp.setEtqInic(etq);
		if(exp.getVinculo().getNivel() == 0)
			etq++;
		else {
			etq += 3;
			
			if(exp.getVinculo().getEsParamRef())
				etq++;
			
		}
		exp.setEtqSig(etq);
	}

	@Override
	public void procesa(Mul exp) {
		exp.setEtqInic(etq);
		// Primer argumento
		exp.arg0().procesa(this);
		if(exp.arg0().getEsDesig()) {
			etq++;
		}
		if(exp.getTipo() == tNodo.REAL && exp.arg0().getTipo() == tNodo.INT)
			etq++;
			
		// Segundo argumento
		exp.arg1().procesa(this);
		if(exp.arg1().getEsDesig()) {
			etq++;
		}
		if(exp.getTipo() == tNodo.REAL && exp.arg1().getTipo() == tNodo.INT)
			etq++;
		
		// Generación de instrucción aritmética
		if(exp.getTipo() == tNodo.INT)
			etq++;
		else if(exp.getTipo() == tNodo.REAL)
			etq++;
		
		exp.setEtqSig(etq);
	}

	@Override
	public void procesa(Resta exp) {
		exp.setEtqInic(etq);
		// Primer argumento
		exp.arg0().procesa(this);
		if(exp.arg0().getEsDesig()) {
			etq++;
		}
		if(exp.getTipo() == tNodo.REAL && exp.arg0().getTipo() == tNodo.INT)
			etq++;
			
		// Segundo argumento
		exp.arg1().procesa(this);
		if(exp.arg1().getEsDesig()) {
			etq++;
		}
		if(exp.getTipo() == tNodo.REAL && exp.arg1().getTipo() == tNodo.INT)
			etq++;
		
		// Generación de instrucción aritmética
		if(exp.getTipo() == tNodo.INT)
			etq++;
		else if(exp.getTipo() == tNodo.REAL)
			etq++;
		exp.setEtqSig(etq);
	}

	@Override
	public void procesa(Suma exp) {
		exp.setEtqInic(etq);
		// Primer argumento
		exp.arg0().procesa(this);
		if(exp.arg0().getEsDesig()) {
			etq++;
		}
		if(exp.getTipo() == tNodo.REAL && exp.arg0().getTipo() == tNodo.INT)
			etq++;
			
		// Segundo argumento
		exp.arg1().procesa(this);
		if(exp.arg1().getEsDesig()) {
			etq++;
		}
		if(exp.getTipo() == tNodo.REAL && exp.arg1().getTipo() == tNodo.INT)
			etq++;
		
		// Generación de instrucción aritmética
		if(exp.getTipo() == tNodo.INT)
			etq++;
		else if(exp.getTipo() == tNodo.REAL)
			etq++;
		
		exp.setEtqSig(etq);
	}
	
	@Override
	public void procesa(Is_muchas is_muchas) {
		is_muchas.is().procesa(this);
		is_muchas.i().procesa(this);
	}

	@Override
	public void procesa(Is_una is_una) {
		is_una.i().procesa(this);
	}


	@Override
	public void procesa(Asig asig) {
		asig.setEtqInic(etq);
		asig.e1().procesa(this);
		asig.e2().procesa(this);
		
		if(asig.e1().getTipo() == tNodo.REAL && asig.e2().getTipo() == tNodo.INT) {
			if(asig.e2().getEsDesig())
				etq++;
			
			etq += 2;
		}
		else {
			etq++;
		}
		asig.setEtqSig(etq);
	}

	@Override
	public void procesa(If_then if_then) {
		if_then.setEtqInic(etq);
		if_then.e().procesa(this);
		if(if_then.e().getEsDesig())
			etq++;
		etq++;
		if_then.is().procesa(this);
		if_then.setEtqSig(etq);
	}

	@Override
	public void procesa(If_then_else if_then_else) {
		if_then_else.setEtqInic(etq);
		if_then_else.e().procesa(this);
		
		if(if_then_else.e().getEsDesig())
			etq++;
		
		etq++;
		if_then_else.is1().procesa(this);
		etq++;
		if_then_else.is2().procesa(this);
		if_then_else.setEtqSig(etq);
	}

	@Override
	public void procesa(While wh) {
		wh.setEtqInic(etq);
		wh.e().procesa(this);
		if(wh.e().getEsDesig())
			etq++;
		
		etq++;
		wh.is().procesa(this);
		etq++;
		wh.setEtqSig(etq);
	}

	@Override
	public void procesa(Read read) {
		read.setEtqInic(etq);
		read.e().procesa(this);
		etq += 2;
		read.setEtqInic(etq);
	}

	@Override
	public void procesa(Write write) {
		write.setEtqInic(etq);
		write.e().procesa(this);
		if(write.e().getEsDesig())
			etq++;
		etq++;
		write.setEtqSig(etq);
	}

	@Override
	public void procesa(Nl nl) {
		nl.setEtqInic(etq);
		etq += 2;
		nl.setEtqSig(etq);
	}

	@Override
	public void procesa(New new1) {
		new1.setEtqInic(etq);
		new1.e().procesa(this);
		etq += 2;
		new1.setEtqSig(etq);
	}

	@Override
	public void procesa(Delete delete) {
		delete.setEtqInic(etq);
		delete.e().procesa(this);
		etq += 2;
		delete.setEtqSig(etq);
	}

	@Override
	public void procesa(Call call) {
		call.setEtqInic(etq);
		etq++;
		etiqueta_params(call.preals(), ((DecProc) call.getVinculo()).pf());
		etq += 2;
		call.setEtqSig(etq);
	}

	@Override
	public void procesa(IComp iComp) {
		iComp.setEtqInic(etq);
		etq+= 2;
		
		iComp.is().procesa(this);
		iComp.decs().recolecta_procs(this);
		
		while(!procs.empty()) {
			DecProc p = procs.pop();
			p.procesa(this);
		}	
		
		etq += 2;
		iComp.setEtqSig(etq);
	}

	@Override
	public void procesa(PReals pReals) {
		
	}

	@Override
	public void procesa(LitInt litInt) {
		litInt.setEtqInic(etq);
		etq++;
		litInt.setEtqSig(etq);
	}

	@Override
	public void procesa(LitReal litReal) {
		litReal.setEtqInic(etq);
		etq++;
		litReal.setEtqSig(etq);
	}

	@Override
	public void procesa(LitStr litStr) {
		litStr.setEtqInic(etq);
		etq++;
		litStr.setEtqSig(etq);
	}

	@Override
	public void procesa(True true1) {
		true1.setEtqInic(etq);
		etq++;
		true1.setEtqSig(etq);
	}

	@Override
	public void procesa(Null null1) {
		null1.setEtqInic(etq);
		etq++;
		null1.setEtqSig(etq);
	}

	@Override
	public void procesa(Menor menor) {
		menor.setEtqInic(etq);
		// Primer argumento
		menor.arg0().procesa(this);
		if(menor.arg0().getEsDesig()) {
			etq++;
		}
		if(menor.getTipo() == tNodo.REAL && menor.arg0().getTipo() == tNodo.INT)
			etq++;
			
		// Segundo argumento
		menor.arg1().procesa(this);
		if(menor.arg1().getEsDesig()) {
			etq++;
		}
		if(menor.getTipo() == tNodo.REAL && menor.arg1().getTipo() == tNodo.INT)
			etq++;
		
		// Generación de instrucción comparativa
		if(menor.getTipo() == tNodo.INT)
			etq++;
		else if(menor.getTipo() == tNodo.REAL)
			etq++;
		else if(menor.getTipo() == tNodo.STRING)
			etq++;
		else if(menor.getTipo() == tNodo.BOOL)
			etq++;
		
		menor.setEtqSig(etq);
	}

	@Override
	public void procesa(Mayor mayor) {
		mayor.setEtqInic(etq);
		// Primer argumento
		mayor.arg0().procesa(this);
		if(mayor.arg0().getEsDesig()) {
			etq++;
		}
		if(mayor.getTipo() == tNodo.REAL && mayor.arg0().getTipo() == tNodo.INT)
			etq++;
			
		// Segundo argumento
		mayor.arg1().procesa(this);
		if(mayor.arg1().getEsDesig()) {
			etq++;
		}
		if(mayor.getTipo() == tNodo.REAL && mayor.arg1().getTipo() == tNodo.INT)
			etq++;
		
		// Generación de instrucción comparativa
		if(mayor.getTipo() == tNodo.INT)
			etq++;
		else if(mayor.getTipo() == tNodo.REAL)
			etq++;
		else if(mayor.getTipo() == tNodo.STRING)
			etq++;
		else if(mayor.getTipo() == tNodo.BOOL)
			etq++;
		
		mayor.setEtqSig(etq);
	}

	@Override
	public void procesa(False false1) {
		false1.setEtqInic(etq);
		etq++;
		false1.setEtqSig(etq);
	}

	@Override
	public void procesa(MayorIgual mayorIgual) {
		mayorIgual.setEtqInic(etq);
		// Primer argumento
		mayorIgual.arg0().procesa(this);
		if(mayorIgual.arg0().getEsDesig()) {
			etq++;
		}
		if(mayorIgual.getTipo() == tNodo.REAL && mayorIgual.arg0().getTipo() == tNodo.INT)
			etq++;
			
		// Segundo argumento
		mayorIgual.arg1().procesa(this);
		if(mayorIgual.arg1().getEsDesig()) {
			etq++;
		}
		if(mayorIgual.getTipo() == tNodo.REAL && mayorIgual.arg1().getTipo() == tNodo.INT)
			etq++;
		
		// Generación de instrucción comparativa
		if(mayorIgual.getTipo() == tNodo.INT)
			etq++;
		else if(mayorIgual.getTipo() == tNodo.REAL)
			etq++;
		else if(mayorIgual.getTipo() == tNodo.STRING)
			etq++;
		else if(mayorIgual.getTipo() == tNodo.BOOL)
			etq++;
		
		mayorIgual.setEtqSig(etq);
	}
	

	@Override
	public void procesa(Igual igual) {
		igual.setEtqInic(etq);
		// Primer argumento
		igual.arg0().procesa(this);
		if(igual.arg0().getEsDesig()) {
			etq++;
		}
		if(igual.getTipo() == tNodo.REAL && igual.arg0().getTipo() == tNodo.INT)
			etq++;
			
		// Segundo argumento
		igual.arg1().procesa(this);
		if(igual.arg1().getEsDesig()) {
			etq++;
		}
		if(igual.getTipo() == tNodo.REAL && igual.arg1().getTipo() == tNodo.INT)
			etq++;
		
		// Generación de instrucción comparativa
		if(igual.getTipo() == tNodo.INT)
			etq++;
		else if(igual.getTipo() == tNodo.REAL)
			etq++;	
		else if(igual.getTipo() == tNodo.STRING)
			etq++;
		else if(igual.getTipo() == tNodo.BOOL)
			etq++;
		igual.setEtqSig(etq);
	}

	@Override
	public void procesa(Distinto distinto) {
		distinto.setEtqInic(etq);
		// Primer argumento
		distinto.arg0().procesa(this);
		if(distinto.arg0().getEsDesig()) {
			etq++;
		}
		if(distinto.getTipo() == tNodo.REAL && distinto.arg0().getTipo() == tNodo.INT)
			etq++;
			
		// Segundo argumento
		distinto.arg1().procesa(this);
		if(distinto.arg1().getEsDesig()) {
			etq++;
		}
		if(distinto.getTipo() == tNodo.REAL && distinto.arg1().getTipo() == tNodo.INT)
			etq++;
		
		// Generación de instrucción comparativa
		if(distinto.getTipo() == tNodo.INT)
			etq++;
		else if(distinto.getTipo() == tNodo.REAL)
			etq++;
		else if(distinto.getTipo() == tNodo.STRING)
			etq++;
		else if(distinto.getTipo() == tNodo.BOOL)
			etq++;
		
		distinto.setEtqSig(etq);
	}

	@Override
	public void procesa(MenorIgual menorIgual) {
		menorIgual.setEtqInic(etq);
		// Primer argumento
		menorIgual.arg0().procesa(this);
		if(menorIgual.arg0().getEsDesig()) {
			etq++;
		}
		if(menorIgual.getTipo() == tNodo.REAL && menorIgual.arg0().getTipo() == tNodo.INT)
			etq++;
			
		// Segundo argumento
		menorIgual.arg1().procesa(this);
		if(menorIgual.arg1().getEsDesig()) {
			etq++;
		}
		if(menorIgual.getTipo() == tNodo.REAL && menorIgual.arg1().getTipo() == tNodo.INT)
			etq++;
		
		// Generación de instrucción comparativa
		if(menorIgual.getTipo() == tNodo.INT)
			etq++;
		else if(menorIgual.getTipo() == tNodo.REAL)
			etq++;
		else if(menorIgual.getTipo() == tNodo.STRING)
			etq++;
		else if(menorIgual.getTipo() == tNodo.BOOL)
			etq++;
		
		menorIgual.setEtqSig(etq);
	}

	@Override
	public void procesa(And and) {
		and.setEtqInic(etq);
		// Primer argumento
		and.arg0().procesa(this);
		if(and.arg0().getEsDesig()) {
			etq++;
		}
			
		// Segundo argumento
		and.arg1().procesa(this);
		if(and.arg1().getEsDesig()) {
			etq++;
		}
		
		// Generación de instrucción comparativa
		etq++;
		
		and.setEtqSig(etq);
	}

	@Override
	public void procesa(Or or) {
		or.setEtqInic(etq);
		// Primer argumento
		or.arg0().procesa(this);
		if(or.arg0().getEsDesig()) {
			etq++;
		}
			
		// Segundo argumento
		or.arg1().procesa(this);
		if(or.arg1().getEsDesig()) {
			etq++;
		}
		
		// Generación de instrucción comparativa
		etq++;
		or.setEtqSig(etq);
	}

	@Override
	public void procesa(Modulo modulo) {
		modulo.setEtqInic(etq);
		// Primer argumento
		modulo.arg0().procesa(this);
		if(modulo.arg0().getEsDesig()) {
			etq++;
		}
			
		// Segundo argumento
		modulo.arg1().procesa(this);
		if(modulo.arg1().getEsDesig()) {
			etq++;
		}
		
		// Generación de instrucción comparativa
		etq++;
		
		modulo.setEtqSig(etq);
	}

	@Override
	public void procesa(Negativo negativo) {
		negativo.setEtqInic(etq);
		// Primer argumento
		negativo.e().procesa(this);
		if(negativo.e().getEsDesig()) {
			etq++;
		}
			
		// Generación de instrucción comparativa
		if(negativo.getTipo() == tNodo.INT)
			etq++;
		else if(negativo.getTipo() == tNodo.REAL)
			etq++;
		
		negativo.setEtqSig(etq);
	}

	@Override
	public void procesa(Not not) {
		not.setEtqInic(etq);
		// Primer argumento
		not.e().procesa(this);
		if(not.e().getEsDesig()) {
			etq++;
		}
			
		// Generación de instrucción comparativa
		etq++;
		not.setEtqSig(etq);
	}

	@Override
	public void procesa(Acc acc) {
		acc.setEtqInic(etq);
		acc.e().procesa(this);
		// int desp = 
		etq += 2;
		acc.setEtqSig(etq);
	}

	@Override
	public void procesa(Indx indx) {
		indx.setEtqInic(etq);
		indx.e1().procesa(this);
		indx.e2().procesa(this);
		if(indx.e1().getEsDesig())
			etq++;
		etq+=3;
		indx.setEtqSig(etq);
	}

	@Override
	public void procesa(Dref dref) {
		dref.setEtqInic(etq);
		dref.e().procesa(this);
		etq++;
		dref.setEtqSig(etq);
	}

	/*private void gen_cod_params(PReal_ninguno preals, PForm_vacia pfs) {
		
	}
	
	private void gen_cod_params(PReal_uno preals, PForm_una pfs) {
		gen_cod_paso(preals.preal(), pfs.pform());
	}
	
	private void gen_cod_params(PReal_muchos preals, PForm_muchas pfs) {
		gen_cod_params((PReal_muchos)preals.preals(), (PForm_muchas)pfs.pforms());
		gen_cod_paso(preals.preal(), pfs.pform());
	}*/
	
	private void etiqueta_params(PReals preals, PForms pfs) {
		if(preals.varias() && pfs.varias()) {
			etiqueta_params(((PReal_muchos) preals).preals(), ((PForm_muchas) pfs).pforms());
			etiqueta_paso(((PReal_uno) preals).preal(), ((PForm_una) pfs).pform());
		}
		else if(!preals.varias() && !pfs.varias()){
			etiqueta_paso(((PReal_uno) preals).preal(), ((PForm_una) pfs).pform());
		}
	}
	
	private void etiqueta_paso(PReal preal, PForm pf) {
		etq+=3;
		preal.procesa(this);
		if(preal.getEsDesig() && !pf.getEsParamRef())
			etq++;
		else
			etq++;
	}

	@Override
	public void recolecta_procs(Decs_una decs_una) {
		decs_una.dec().recolecta_procs(this);
	}

	@Override
	public void recolecta_procs(Decs_vacia decs_vacia) {
		
	}

	@Override
	public void recolecta_procs(Decs_muchas decs_muchas) {
		decs_muchas.decs().recolecta_procs(this);
		decs_muchas.dec().recolecta_procs(this);
	}

	@Override
	public void recolecta_procs(DecVar decVar) {
		
	}

	@Override
	public void recolecta_procs(DecTipo decTipo) {
		
	}

	@Override
	public void recolecta_procs(DecProc decProc) {
		procs.push(decProc);
	}
	
}

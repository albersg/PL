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
import implementacion.asint.TinyASint.PForm_vacia;
import implementacion.asint.TinyASint.PForms;
import implementacion.asint.TinyASint.PReal;
import implementacion.asint.TinyASint.PReal_muchos;
import implementacion.asint.TinyASint.PReal_ninguno;
import implementacion.asint.TinyASint.PReal_uno;
import implementacion.asint.TinyASint.PReals;
import implementacion.asint.TinyASint.Prog;
import implementacion.asint.TinyASint.Read;
import implementacion.asint.TinyASint.Resta;
import implementacion.asint.TinyASint.Suma;
import implementacion.asint.TinyASint.TipoBool;
import implementacion.asint.TinyASint.TipoInt;
import implementacion.asint.TinyASint.TipoReal;
import implementacion.asint.TinyASint.TipoString;
import implementacion.asint.TinyASint.True;
import implementacion.asint.TinyASint.While;
import implementacion.asint.TinyASint.Write;

public class Etiquetado extends ProcesamientoPorDefecto{
	
	private int etq;
	private Stack<DecProc> procs;
	
	public Etiquetado() throws Exception {
		this.etq = 0;
		procs = new Stack<DecProc>();
	}
	
	@Override
	public void procesa(Prog prog) throws Exception {
		prog.is().procesa(this);
		etq++;
		prog.decs().recolecta_procs(this);
		
		while(!procs.empty())  {
			DecProc p = procs.pop();
			p.procesa(this);
		}
	}
	

	@Override
	public void procesa(Div exp) throws Exception {
		exp.setEtqInic(etq);
		// Primer argumento
		exp.arg0().procesa(this);
		if(exp.arg0().getEsDesig()) {
			etq++;
		}
		if(exp.getTipo() instanceof TipoReal && exp.arg0().getTipo() instanceof TipoInt)
			etq++;
			
		// Segundo argumento
		exp.arg1().procesa(this);
		if(exp.arg1().getEsDesig()) {
			etq++;
		}
		if(exp.getTipo() instanceof TipoReal && exp.arg1().getTipo() instanceof TipoInt)
			etq++;
		
		// Generación de instrucción aritmética
		if(exp.getTipo() instanceof TipoInt)
			etq++;
		else if(exp.getTipo() instanceof TipoReal)
			etq++;
		exp.setEtqSig(etq);
	}

	@Override
	public void procesa(Id exp) throws Exception {
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
	public void procesa(Mul exp) throws Exception {
		exp.setEtqInic(etq);
		// Primer argumento
		exp.arg0().procesa(this);
		if(exp.arg0().getEsDesig())  {
			etq++;
		}
		if(exp.getTipo() instanceof TipoReal && exp.arg0().getTipo() instanceof TipoInt)
			etq++;
			
		// Segundo argumento
		exp.arg1().procesa(this);
		if(exp.arg1().getEsDesig()) {
			etq++;
		}
		if(exp.getTipo() instanceof TipoReal && exp.arg1().getTipo() instanceof TipoInt)
			etq++;
		
		// Generación de instrucción aritmética
		if(exp.getTipo() instanceof TipoInt)
			etq++;
		else if(exp.getTipo() instanceof TipoReal)
			etq++;
		
		exp.setEtqSig(etq);
	}

	@Override
	public void procesa(Resta exp) throws Exception {
		exp.setEtqInic(etq);
		// Primer argumento
		exp.arg0().procesa(this);
		if(exp.arg0().getEsDesig()) {
			etq++;
		}
		if(exp.getTipo() instanceof TipoReal && exp.arg0().getTipo() instanceof TipoInt)
			etq++;
			
		// Segundo argumento
		exp.arg1().procesa(this);
		if(exp.arg1().getEsDesig())  {
			etq++;
		}
		if(exp.getTipo() instanceof TipoReal && exp.arg1().getTipo() instanceof TipoInt)
			etq++;
		
		// Generación de instrucción aritmética
		if(exp.getTipo() instanceof TipoInt)
			etq++;
		else if(exp.getTipo() instanceof TipoReal)
			etq++;
		exp.setEtqSig(etq);
	}

	@Override
	public void procesa(Suma exp) throws Exception {
		exp.setEtqInic(etq);
		// Primer argumento
		exp.arg0().procesa(this);
		if(exp.arg0().getEsDesig()){
			etq++;
		}
		if(exp.getTipo() instanceof TipoReal && exp.arg0().getTipo() instanceof TipoInt)
			etq++;
			
		// Segundo argumento
		exp.arg1().procesa(this);
		if(exp.arg1().getEsDesig())  {
			etq++;
		}
		if(exp.getTipo() instanceof TipoReal && exp.arg1().getTipo() instanceof TipoInt)
			etq++;
		
		// Generación de instrucción aritmética
		if(exp.getTipo() instanceof TipoInt)
			etq++;
		else if(exp.getTipo() instanceof TipoReal)
			etq++;
		
		exp.setEtqSig(etq);
	}
	
	@Override
	public void procesa(Is_muchas is_muchas) throws Exception {
		is_muchas.setEtqInic(etq);
		is_muchas.is().procesa(this);
		is_muchas.i().procesa(this);
		is_muchas.setEtqSig(etq);
	}

	@Override
	public void procesa(Is_una is_una) throws Exception {
		is_una.setEtqInic(etq);
		is_una.i().procesa(this);
		is_una.setEtqSig(etq);
	}


	@Override
	public void procesa(Asig asig) throws Exception {
		asig.setEtqInic(etq);
		asig.e1().procesa(this);
		asig.e2().procesa(this);
		
		if(asig.e1().getTipo() instanceof TipoReal && asig.e2().getTipo() instanceof TipoInt)  {
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
	public void procesa(If_then if_then) throws Exception {
		if_then.setEtqInic(etq);
		if_then.e().procesa(this);
		if(if_then.e().getEsDesig())
			etq++;
		etq++;
		if_then.is().procesa(this);
		if_then.setEtqSig(etq);
	}

	@Override
	public void procesa(If_then_else if_then_else) throws Exception {
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
	public void procesa(While wh) throws Exception {
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
	public void procesa(Read read) throws Exception {
		read.setEtqInic(etq);
		read.e().procesa(this);
		etq += 2;
		read.setEtqInic(etq);
	}

	@Override
	public void procesa(Write write) throws Exception {
		write.setEtqInic(etq);
		write.e().procesa(this);
		if(write.e().getEsDesig())
			etq++;
		etq++;
		write.setEtqSig(etq);
	}

	@Override
	public void procesa(Nl nl) throws Exception {
		nl.setEtqInic(etq);
		etq += 2;
		nl.setEtqSig(etq);
	}

	@Override
	public void procesa(New new1) throws Exception {
		new1.setEtqInic(etq);
		new1.e().procesa(this);
		etq += 2;
		new1.setEtqSig(etq);
	}

	@Override
	public void procesa(Delete delete) throws Exception {
		delete.setEtqInic(etq);
		delete.e().procesa(this);
		etq += 2;
		delete.setEtqSig(etq);
	}

	@Override
	public void procesa(Call call) throws Exception {
		call.setEtqInic(etq);
		etq++;
		if(call.preals() instanceof PReal_ninguno)
			etiqueta_params((PReal_ninguno) call.preals(), (PForm_vacia) ((DecProc) call.getVinculo()).pf());
		else if(call.preals() instanceof PReal_uno)
			etiqueta_params((PReal_uno) call.preals(), (PForm_una) ((DecProc) call.getVinculo()).pf());
		else
			etiqueta_params((PReal_muchos) call.preals(), (PForm_muchas) ((DecProc) call.getVinculo()).pf());
		etq += 2;
		call.setEtqSig(etq);
	}

	@Override
	public void procesa(IComp iComp) throws Exception {
		iComp.setEtqInic(etq);
		etq+= 2;
		
		iComp.is().procesa(this);
		iComp.decs().recolecta_procs(this);
		
		while(!procs.empty())  {
			DecProc p = procs.pop();
			p.procesa(this);
		}	
		
		etq += 2;
		iComp.setEtqSig(etq);
	}

	@Override
	public void procesa(PReals pReals) throws Exception {
		
	}
	
	@Override
	public void procesa(DecProc decProc) throws Exception {
		decProc.setEtqInic(etq);
		decProc.decs().procesa(this);
		decProc.is().procesa(this);
		etq += 2;
		decProc.decs().recolecta_procs(this);
		decProc.setEtqSig(etq);
	}

	@Override
	public void procesa(LitInt litInt) throws Exception {
		litInt.setEtqInic(etq);
		etq++;
		litInt.setEtqSig(etq);
	}

	@Override
	public void procesa(LitReal litReal) throws Exception {
		litReal.setEtqInic(etq);
		etq++;
		litReal.setEtqSig(etq);
	}

	@Override
	public void procesa(LitStr litStr) throws Exception {
		litStr.setEtqInic(etq);
		etq++;
		litStr.setEtqSig(etq);
	}

	@Override
	public void procesa(True true1) throws Exception {
		true1.setEtqInic(etq);
		etq++;
		true1.setEtqSig(etq);
	}

	@Override
	public void procesa(Null null1) throws Exception {
		null1.setEtqInic(etq);
		etq++;
		null1.setEtqSig(etq);
	}

	@Override
	public void procesa(Menor menor) throws Exception {
		menor.setEtqInic(etq);
		// Primer argumento
		menor.arg0().procesa(this);
		if(menor.arg0().getEsDesig()) {
			etq++;
		}
		if(menor.getTipo() instanceof TipoReal && menor.arg0().getTipo() instanceof TipoInt)
			etq++;
			
		// Segundo argumento
		menor.arg1().procesa(this);
		if(menor.arg1().getEsDesig()) {
			etq++;
		}
		if(menor.getTipo() instanceof TipoReal && menor.arg1().getTipo() instanceof TipoInt)
			etq++;
		
		// Generación de instrucción comparativa
		if(menor.getTipo() instanceof TipoInt)
			etq++;
		else if(menor.getTipo() instanceof TipoReal)
			etq++;
		else if(menor.getTipo() instanceof TipoString)
			etq++;
		else if(menor.getTipo() instanceof TipoBool)
			etq++;
		
		menor.setEtqSig(etq);
	}

	@Override
	public void procesa(Mayor mayor) throws Exception {
		mayor.setEtqInic(etq);
		// Primer argumento
		mayor.arg0().procesa(this);
		if(mayor.arg0().getEsDesig())  {
			etq++;
		}
		if(mayor.getTipo() instanceof TipoReal && mayor.arg0().getTipo() instanceof TipoInt)
			etq++;
			
		// Segundo argumento
		mayor.arg1().procesa(this);
		if(mayor.arg1().getEsDesig()){
			etq++;
		}
		if(mayor.getTipo() instanceof TipoReal && mayor.arg1().getTipo() instanceof TipoInt)
			etq++;
		
		// Generación de instrucción comparativa
		if(mayor.getTipo() instanceof TipoInt)
			etq++;
		else if(mayor.getTipo() instanceof TipoReal)
			etq++;
		else if(mayor.getTipo() instanceof TipoString)
			etq++;
		else if(mayor.getTipo() instanceof TipoBool)
			etq++;
		
		mayor.setEtqSig(etq);
	}

	@Override
	public void procesa(False false1) throws Exception {
		false1.setEtqInic(etq);
		etq++;
		false1.setEtqSig(etq);
	}

	@Override
	public void procesa(MayorIgual mayorIgual) throws Exception {
		mayorIgual.setEtqInic(etq);
		// Primer argumento
		mayorIgual.arg0().procesa(this);
		if(mayorIgual.arg0().getEsDesig())  {
			etq++;
		}
		if(mayorIgual.getTipo() instanceof TipoReal && mayorIgual.arg0().getTipo() instanceof TipoInt)
			etq++;
			
		// Segundo argumento
		mayorIgual.arg1().procesa(this);
		if(mayorIgual.arg1().getEsDesig())  {
			etq++;
		}
		if(mayorIgual.getTipo() instanceof TipoReal && mayorIgual.arg1().getTipo() instanceof TipoInt)
			etq++;
		
		// Generación de instrucción comparativa
		if(mayorIgual.getTipo() instanceof TipoInt)
			etq++;
		else if(mayorIgual.getTipo() instanceof TipoReal)
			etq++;
		else if(mayorIgual.getTipo() instanceof TipoString)
			etq++;
		else if(mayorIgual.getTipo() instanceof TipoBool)
			etq++;
		
		mayorIgual.setEtqSig(etq);
	}
	

	@Override
	public void procesa(Igual igual) throws Exception {
		igual.setEtqInic(etq);
		// Primer argumento
		igual.arg0().procesa(this);
		if(igual.arg0().getEsDesig()) {
			etq++;
		}
		if(igual.getTipo() instanceof TipoReal && igual.arg0().getTipo() instanceof TipoInt)
			etq++;
			
		// Segundo argumento
		igual.arg1().procesa(this);
		if(igual.arg1().getEsDesig()){
			etq++;
		}
		if(igual.getTipo() instanceof TipoReal && igual.arg1().getTipo() instanceof TipoInt)
			etq++;
		
		// Generación de instrucción comparativa
		if(igual.getTipo() instanceof TipoInt)
			etq++;
		else if(igual.getTipo() instanceof TipoReal)
			etq++;	
		else if(igual.getTipo() instanceof TipoString)
			etq++;
		else if(igual.getTipo() instanceof TipoBool)
			etq++;
		igual.setEtqSig(etq);
	}

	@Override
	public void procesa(Distinto distinto) throws Exception {
		distinto.setEtqInic(etq);
		// Primer argumento
		distinto.arg0().procesa(this);
		if(distinto.arg0().getEsDesig()) {
			etq++;
		}
		if(distinto.getTipo() instanceof TipoReal && distinto.arg0().getTipo() instanceof TipoInt)
			etq++;
			
		// Segundo argumento
		distinto.arg1().procesa(this);
		if(distinto.arg1().getEsDesig()) {
			etq++;
		}
		if(distinto.getTipo() instanceof TipoReal && distinto.arg1().getTipo() instanceof TipoInt)
			etq++;
		
		// Generación de instrucción comparativa
		if(distinto.getTipo() instanceof TipoInt)
			etq++;
		else if(distinto.getTipo() instanceof TipoReal)
			etq++;
		else if(distinto.getTipo() instanceof TipoString)
			etq++;
		else if(distinto.getTipo() instanceof TipoBool)
			etq++;
		
		distinto.setEtqSig(etq);
	}

	@Override
	public void procesa(MenorIgual menorIgual) throws Exception {
		menorIgual.setEtqInic(etq);
		// Primer argumento
		menorIgual.arg0().procesa(this);
		if(menorIgual.arg0().getEsDesig())  {
			etq++;
		}
		if(menorIgual.getTipo() instanceof TipoReal && menorIgual.arg0().getTipo() instanceof TipoInt)
			etq++;
			
		// Segundo argumento
		menorIgual.arg1().procesa(this);
		if(menorIgual.arg1().getEsDesig())  {
			etq++;
		}
		if(menorIgual.getTipo() instanceof TipoReal && menorIgual.arg1().getTipo() instanceof TipoInt)
			etq++;
		
		// Generación de instrucción comparativa
		if(menorIgual.getTipo() instanceof TipoInt)
			etq++;
		else if(menorIgual.getTipo() instanceof TipoReal)
			etq++;
		else if(menorIgual.getTipo() instanceof TipoString)
			etq++;
		else if(menorIgual.getTipo() instanceof TipoBool)
			etq++;
		
		menorIgual.setEtqSig(etq);
	}

	@Override
	public void procesa(And and) throws Exception {
		and.setEtqInic(etq);
		// Primer argumento
		and.arg0().procesa(this);
		if(and.arg0().getEsDesig()) {
			etq++;
		}
			
		// Segundo argumento
		and.arg1().procesa(this);
		if(and.arg1().getEsDesig()){
			etq++;
		}
		
		// Generación de instrucción comparativa
		etq++;
		
		and.setEtqSig(etq);
	}

	@Override
	public void procesa(Or or) throws Exception {
		or.setEtqInic(etq);
		// Primer argumento
		or.arg0().procesa(this);
		if(or.arg0().getEsDesig()) {
			etq++;
		}
			
		// Segundo argumento
		or.arg1().procesa(this);
		if(or.arg1().getEsDesig()){
			etq++;
		}
		
		// Generación de instrucción comparativa
		etq++;
		or.setEtqSig(etq);
	}

	@Override
	public void procesa(Modulo modulo) throws Exception {
		modulo.setEtqInic(etq);
		// Primer argumento
		modulo.arg0().procesa(this);
		if(modulo.arg0().getEsDesig()){
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
	public void procesa(Negativo negativo) throws Exception {
		negativo.setEtqInic(etq);
		// Primer argumento
		negativo.e().procesa(this);
		if(negativo.e().getEsDesig()) {
			etq++;
		}
			
		// Generación de instrucción comparativa
		if(negativo.getTipo() instanceof TipoInt)
			etq++;
		else if(negativo.getTipo() instanceof TipoReal)
			etq++;
		
		negativo.setEtqSig(etq);
	}

	@Override
	public void procesa(Not not) throws Exception {
		not.setEtqInic(etq);
		// Primer argumento
		not.e().procesa(this);
		if(not.e().getEsDesig()){
			etq++;
		}
			
		// Generación de instrucción comparativa
		etq++;
		not.setEtqSig(etq);
	}

	@Override
	public void procesa(Acc acc) throws Exception {
		acc.setEtqInic(etq);
		acc.e().procesa(this);
		// int desp = 
		etq += 2;
		acc.setEtqSig(etq);
	}

	@Override
	public void procesa(Indx indx) throws Exception {
		indx.setEtqInic(etq);
		indx.e1().procesa(this);
		indx.e2().procesa(this);
		if(indx.e1().getEsDesig())
			etq++;
		etq+=3;
		indx.setEtqSig(etq);
	}

	@Override
	public void procesa(Dref dref) throws Exception {
		dref.setEtqInic(etq);
		dref.e().procesa(this);
		etq++;
		dref.setEtqSig(etq);
	}

	private void etiqueta_params(PReal_ninguno preals, PForm_vacia pfs) throws Exception {
		
	}
	
	private void etiqueta_params(PReal_uno preals, PForm_una pfs) throws Exception {
		etiqueta_paso(preals.preal(), pfs.pform());
	}
	
	private void etiqueta_params(PReal_muchos preals, PForm_muchas pfs) throws Exception {
		etiqueta_params((PReal_muchos)preals.preals(), (PForm_muchas)pfs.pforms());
		etiqueta_paso(preals.preal(), pfs.pform());
	}
	
	/*private void etiqueta_params(PReals preals, PForms pfs) throws Exception {
		if(preals.varias() && pfs.varias()) throws Exception {
			etiqueta_params(((PReal_muchos) preals).preals(), ((PForm_muchas) pfs).pforms());
			etiqueta_paso(((PReal_uno) preals).preal(), ((PForm_una) pfs).pform());
		}
		else if(!preals.varias() && !pfs.varias()){
			etiqueta_paso(((PReal_uno) preals).preal(), ((PForm_una) pfs).pform());
		}
	}*/
	
	private void etiqueta_paso(PReal preal, PForm pf) throws Exception {
		etq+=3;
		preal.e().procesa(this);
		if(preal.e().getEsDesig() && !pf.getEsParamRef())
			etq++;
		else
			etq++;
	}

	@Override
	public void recolecta_procs(Decs_una decs_una) throws Exception {
		decs_una.dec().recolecta_procs(this);
	}

	@Override
	public void recolecta_procs(Decs_vacia decs_vacia) throws Exception {
		
	}

	@Override
	public void recolecta_procs(Decs_muchas decs_muchas) throws Exception {
		decs_muchas.decs().recolecta_procs(this);
		decs_muchas.dec().recolecta_procs(this);
	}

	@Override
	public void recolecta_procs(DecVar decVar) throws Exception {
		
	}

	@Override
	public void recolecta_procs(DecTipo decTipo) throws Exception {
		
	}

	@Override
	public void recolecta_procs(DecProc decProc) throws Exception {
		procs.push(decProc);
	}
	
}

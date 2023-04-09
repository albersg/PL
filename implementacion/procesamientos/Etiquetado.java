package implementacion.procesamientos;

import java.util.Stack;

import implementacion.asint.ProcesamientoPorDefecto;
import implementacion.asint.TinyASint.Acc;
import implementacion.asint.TinyASint.And;
import implementacion.asint.TinyASint.Asig;
import implementacion.asint.TinyASint.Call;
import implementacion.asint.TinyASint.Campo;
import implementacion.asint.TinyASint.Campos_muchos;
import implementacion.asint.TinyASint.Campos_uno;
import implementacion.asint.TinyASint.Dec;
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
import implementacion.asint.TinyASint.Is_vacia;
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
import implementacion.asint.TinyASint.PFormRef;
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
import implementacion.asint.TinyASint.TipoArray;
import implementacion.asint.TinyASint.TipoBool;
import implementacion.asint.TinyASint.TipoInt;
import implementacion.asint.TinyASint.TipoPointer;
import implementacion.asint.TinyASint.TipoReal;
import implementacion.asint.TinyASint.TipoRecord;
import implementacion.asint.TinyASint.TipoRef;
import implementacion.asint.TinyASint.TipoString;
import implementacion.asint.TinyASint.True;
import implementacion.asint.TinyASint.While;
import implementacion.asint.TinyASint.Write;
import implementacion.asint.TinyASint.tNodo;
import implementacion.maquinaP.MaquinaP;

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
		maquinaP.ponInstruccion(maquinaP.apilaString("\n"));
		maquinaP.ponInstruccion(maquinaP.write());
	}

	@Override
	public void procesa(New new1) {
		new1.e().procesa(this);
		maquinaP.ponInstruccion(maquinaP.alloc(new1.e().getTamBase()));
		maquinaP.ponInstruccion(maquinaP.desapilaInd());
	}

	@Override
	public void procesa(Delete delete) {
		delete.e().procesa(this);
		maquinaP.ponInstruccion(maquinaP.apilaInd());
		maquinaP.ponInstruccion(maquinaP.dealloc(delete.e().getTamBase()));
	}

	@Override
	public void procesa(Call call) {
		maquinaP.ponInstruccion(maquinaP.activa(call.getVinculo().getNivel(), call.getVinculo().getTam(), call.getEtqSig()));
		gen_cod_params(call.preals(), ((DecProc) call.getVinculo()).pf());
		maquinaP.ponInstruccion(maquinaP.desapilad(call.getVinculo().getNivel()));
		maquinaP.ponInstruccion(maquinaP.irA(call.getVinculo().getEtqInic()));
	}

	@Override
	public void procesa(IComp iComp) {
		maquinaP.ponInstruccion(maquinaP.activa(iComp.getNivel(), iComp.getTam(), iComp.getEtqSig()));
		maquinaP.ponInstruccion(maquinaP.desapilad(iComp.getNivel()));
		
		iComp.is().procesa(this);
		iComp.decs().recolecta_procs(this);
		
		while(!procs.empty()) {
			DecProc p = procs.pop();
			p.procesa(this);
		}	
		
		maquinaP.ponInstruccion(maquinaP.desactiva(iComp.getNivel(), iComp.getTam()));
		maquinaP.ponInstruccion(maquinaP.irInd());
	}

	@Override
	public void procesa(PReals pReals) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void procesa(LitInt litInt) {
		maquinaP.ponInstruccion(maquinaP.apilaInt(Integer.valueOf(litInt.s().toString())));
	}

	@Override
	public void procesa(LitReal litReal) {
		maquinaP.ponInstruccion(maquinaP.apilaReal(Float.valueOf(litReal.s().toString())));		
	}

	@Override
	public void procesa(LitStr litStr) {
		maquinaP.ponInstruccion(maquinaP.apilaString(litStr.s().toString()));
	}

	@Override
	public void procesa(True true1) {
		maquinaP.ponInstruccion(maquinaP.apilaBool(true));		
	}

	@Override
	public void procesa(Null null1) {
		maquinaP.ponInstruccion(maquinaP.apilaInt(-1));
	}

	@Override
	public void procesa(Menor menor) {
		// Primer argumento
		menor.arg0().procesa(this);
		if(menor.arg0().getEsDesig()) {
			maquinaP.ponInstruccion(maquinaP.apilaInd());
		}
		if(menor.getTipo() == tNodo.REAL && menor.arg0().getTipo() == tNodo.INT)
			maquinaP.ponInstruccion(maquinaP.int2real());
			
		// Segundo argumento
		menor.arg1().procesa(this);
		if(menor.arg1().getEsDesig()) {
			maquinaP.ponInstruccion(maquinaP.apilaInd());
		}
		if(menor.getTipo() == tNodo.REAL && menor.arg1().getTipo() == tNodo.INT)
			maquinaP.ponInstruccion(maquinaP.int2real());
		
		// Generación de instrucción comparativa
		if(menor.getTipo() == tNodo.INT)
			maquinaP.ponInstruccion(maquinaP.menorInt());
		else if(menor.getTipo() == tNodo.REAL)
			maquinaP.ponInstruccion(maquinaP.menorReal());	
		else if(menor.getTipo() == tNodo.STRING)
			maquinaP.ponInstruccion(maquinaP.menorString());
		else if(menor.getTipo() == tNodo.BOOL)
			maquinaP.ponInstruccion(maquinaP.menorBool());
	}

	@Override
	public void procesa(Mayor mayor) {
		// Primer argumento
		mayor.arg0().procesa(this);
		if(mayor.arg0().getEsDesig()) {
			maquinaP.ponInstruccion(maquinaP.apilaInd());
		}
		if(mayor.getTipo() == tNodo.REAL && mayor.arg0().getTipo() == tNodo.INT)
			maquinaP.ponInstruccion(maquinaP.int2real());
			
		// Segundo argumento
		mayor.arg1().procesa(this);
		if(mayor.arg1().getEsDesig()) {
			maquinaP.ponInstruccion(maquinaP.apilaInd());
		}
		if(mayor.getTipo() == tNodo.REAL && mayor.arg1().getTipo() == tNodo.INT)
			maquinaP.ponInstruccion(maquinaP.int2real());
		
		// Generación de instrucción comparativa
		if(mayor.getTipo() == tNodo.INT)
			maquinaP.ponInstruccion(maquinaP.mayorInt());
		else if(mayor.getTipo() == tNodo.REAL)
			maquinaP.ponInstruccion(maquinaP.mayorReal());	
		else if(mayor.getTipo() == tNodo.STRING)
			maquinaP.ponInstruccion(maquinaP.mayorString());
		else if(mayor.getTipo() == tNodo.BOOL)
			maquinaP.ponInstruccion(maquinaP.mayorBool());
	}

	@Override
	public void procesa(False false1) {
		maquinaP.ponInstruccion(maquinaP.apilaBool(false));				
	}

	@Override
	public void procesa(MayorIgual mayorIgual) {
		// Primer argumento
		mayorIgual.arg0().procesa(this);
		if(mayorIgual.arg0().getEsDesig()) {
			maquinaP.ponInstruccion(maquinaP.apilaInd());
		}
		if(mayorIgual.getTipo() == tNodo.REAL && mayorIgual.arg0().getTipo() == tNodo.INT)
			maquinaP.ponInstruccion(maquinaP.int2real());
			
		// Segundo argumento
		mayorIgual.arg1().procesa(this);
		if(mayorIgual.arg1().getEsDesig()) {
			maquinaP.ponInstruccion(maquinaP.apilaInd());
		}
		if(mayorIgual.getTipo() == tNodo.REAL && mayorIgual.arg1().getTipo() == tNodo.INT)
			maquinaP.ponInstruccion(maquinaP.int2real());
		
		// Generación de instrucción comparativa
		if(mayorIgual.getTipo() == tNodo.INT)
			maquinaP.ponInstruccion(maquinaP.mayorIgualInt());
		else if(mayorIgual.getTipo() == tNodo.REAL)
			maquinaP.ponInstruccion(maquinaP.mayorIgualReal());	
		else if(mayorIgual.getTipo() == tNodo.STRING)
			maquinaP.ponInstruccion(maquinaP.mayorIgualString());
		else if(mayorIgual.getTipo() == tNodo.BOOL)
			maquinaP.ponInstruccion(maquinaP.mayorIgualBool());
	}
	

	@Override
	public void procesa(Igual igual) {
		// Primer argumento
		igual.arg0().procesa(this);
		if(igual.arg0().getEsDesig()) {
			maquinaP.ponInstruccion(maquinaP.apilaInd());
		}
		if(igual.getTipo() == tNodo.REAL && igual.arg0().getTipo() == tNodo.INT)
			maquinaP.ponInstruccion(maquinaP.int2real());
			
		// Segundo argumento
		igual.arg1().procesa(this);
		if(igual.arg1().getEsDesig()) {
			maquinaP.ponInstruccion(maquinaP.apilaInd());
		}
		if(igual.getTipo() == tNodo.REAL && igual.arg1().getTipo() == tNodo.INT)
			maquinaP.ponInstruccion(maquinaP.int2real());
		
		// Generación de instrucción comparativa
		if(igual.getTipo() == tNodo.INT)
			maquinaP.ponInstruccion(maquinaP.igualInt());
		else if(igual.getTipo() == tNodo.REAL)
			maquinaP.ponInstruccion(maquinaP.igualReal());	
		else if(igual.getTipo() == tNodo.STRING)
			maquinaP.ponInstruccion(maquinaP.igualString());
		else if(igual.getTipo() == tNodo.BOOL)
			maquinaP.ponInstruccion(maquinaP.igualBool());
	}

	@Override
	public void procesa(Distinto distinto) {
		// Primer argumento
		distinto.arg0().procesa(this);
		if(distinto.arg0().getEsDesig()) {
			maquinaP.ponInstruccion(maquinaP.apilaInd());
		}
		if(distinto.getTipo() == tNodo.REAL && distinto.arg0().getTipo() == tNodo.INT)
			maquinaP.ponInstruccion(maquinaP.int2real());
			
		// Segundo argumento
		distinto.arg1().procesa(this);
		if(distinto.arg1().getEsDesig()) {
			maquinaP.ponInstruccion(maquinaP.apilaInd());
		}
		if(distinto.getTipo() == tNodo.REAL && distinto.arg1().getTipo() == tNodo.INT)
			maquinaP.ponInstruccion(maquinaP.int2real());
		
		// Generación de instrucción comparativa
		if(distinto.getTipo() == tNodo.INT)
			maquinaP.ponInstruccion(maquinaP.distintoInt());
		else if(distinto.getTipo() == tNodo.REAL)
			maquinaP.ponInstruccion(maquinaP.distintoReal());	
		else if(distinto.getTipo() == tNodo.STRING)
			maquinaP.ponInstruccion(maquinaP.distintoString());
		else if(distinto.getTipo() == tNodo.BOOL)
			maquinaP.ponInstruccion(maquinaP.distintoBool());
	}

	@Override
	public void procesa(MenorIgual menorIgual) {
		// Primer argumento
		menorIgual.arg0().procesa(this);
		if(menorIgual.arg0().getEsDesig()) {
			maquinaP.ponInstruccion(maquinaP.apilaInd());
		}
		if(menorIgual.getTipo() == tNodo.REAL && menorIgual.arg0().getTipo() == tNodo.INT)
			maquinaP.ponInstruccion(maquinaP.int2real());
			
		// Segundo argumento
		menorIgual.arg1().procesa(this);
		if(menorIgual.arg1().getEsDesig()) {
			maquinaP.ponInstruccion(maquinaP.apilaInd());
		}
		if(menorIgual.getTipo() == tNodo.REAL && menorIgual.arg1().getTipo() == tNodo.INT)
			maquinaP.ponInstruccion(maquinaP.int2real());
		
		// Generación de instrucción comparativa
		if(menorIgual.getTipo() == tNodo.INT)
			maquinaP.ponInstruccion(maquinaP.menorIgualInt());
		else if(menorIgual.getTipo() == tNodo.REAL)
			maquinaP.ponInstruccion(maquinaP.menorIgualReal());	
		else if(menorIgual.getTipo() == tNodo.STRING)
			maquinaP.ponInstruccion(maquinaP.menorIgualString());
		else if(menorIgual.getTipo() == tNodo.BOOL)
			maquinaP.ponInstruccion(maquinaP.menorIgualBool());	
	}

	@Override
	public void procesa(And and) {
		// Primer argumento
		and.arg0().procesa(this);
		if(and.arg0().getEsDesig()) {
			maquinaP.ponInstruccion(maquinaP.apilaInd());
		}
			
		// Segundo argumento
		and.arg1().procesa(this);
		if(and.arg1().getEsDesig()) {
			maquinaP.ponInstruccion(maquinaP.apilaInd());
		}
		
		// Generación de instrucción comparativa
		maquinaP.ponInstruccion(maquinaP.and());
	}

	@Override
	public void procesa(Or or) {
		// Primer argumento
		or.arg0().procesa(this);
		if(or.arg0().getEsDesig()) {
			maquinaP.ponInstruccion(maquinaP.apilaInd());
		}
			
		// Segundo argumento
		or.arg1().procesa(this);
		if(or.arg1().getEsDesig()) {
			maquinaP.ponInstruccion(maquinaP.apilaInd());
		}
		
		// Generación de instrucción comparativa
		maquinaP.ponInstruccion(maquinaP.or());		
	}

	@Override
	public void procesa(Modulo modulo) {
		// Primer argumento
		modulo.arg0().procesa(this);
		if(modulo.arg0().getEsDesig()) {
			maquinaP.ponInstruccion(maquinaP.apilaInd());
		}
			
		// Segundo argumento
		modulo.arg1().procesa(this);
		if(modulo.arg1().getEsDesig()) {
			maquinaP.ponInstruccion(maquinaP.apilaInd());
		}
		
		// Generación de instrucción comparativa
		maquinaP.ponInstruccion(maquinaP.mod());				
	}

	@Override
	public void procesa(Negativo negativo) {
		// Primer argumento
		negativo.e().procesa(this);
		if(negativo.e().getEsDesig()) {
			maquinaP.ponInstruccion(maquinaP.apilaInd());
		}
			
		// Generación de instrucción comparativa
		if(negativo.getTipo() == tNodo.INT)
			maquinaP.ponInstruccion(maquinaP.negativoInt());
		else if(negativo.getTipo() == tNodo.REAL)
			maquinaP.ponInstruccion(maquinaP.negativoReal());
	}

	@Override
	public void procesa(Not not) {
		// Primer argumento
		not.e().procesa(this);
		if(not.e().getEsDesig()) {
			maquinaP.ponInstruccion(maquinaP.apilaInd());
		}
			
		// Generación de instrucción comparativa
		maquinaP.ponInstruccion(maquinaP.not());
	}

	@Override
	public void procesa(Acc acc) {
		acc.e().procesa(this);
		// int desp = 
		maquinaP.ponInstruccion(maquinaP.apilaInt(0));
		maquinaP.ponInstruccion(maquinaP.sumaInt());
	}

	@Override
	public void procesa(Indx indx) {
		indx.e1().procesa(this);
		indx.e2().procesa(this);
		if(indx.e1().getEsDesig())
			maquinaP.ponInstruccion(maquinaP.apilaInd());
		maquinaP.ponInstruccion(maquinaP.apilaInt(indx.e1().getTamBase()));
		maquinaP.ponInstruccion(maquinaP.mulInt());
		maquinaP.ponInstruccion(maquinaP.sumaInt());
	}

	@Override
	public void procesa(Dref dref) {
		dref.e().procesa(this);
		maquinaP.ponInstruccion(maquinaP.apilaInd());
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
	
	private void gen_cod_params(PReals preals, PForms pfs) {
		if(preals.varias() && pfs.varias()) {
			gen_cod_params(((PReal_muchos) preals).preals(), ((PForm_muchas) pfs).pforms());
			gen_cod_paso(((PReal_uno) preals).preal(), ((PForm_una) pfs).pform());
		}
		else if(!preals.varias() && !pfs.varias()){
			gen_cod_paso(((PReal_uno) preals).preal(), ((PForm_una) pfs).pform());
		}
	}
	
	private void gen_cod_paso(PReal preal, PForm pf) {
		maquinaP.ponInstruccion(maquinaP.dup());
		maquinaP.ponInstruccion(maquinaP.apilaInt(pf.getDir()));
		maquinaP.ponInstruccion(maquinaP.sumaInt());
		preal.procesa(this);
		if(preal.getEsDesig() && !pf.getEsParamRef())
			maquinaP.ponInstruccion(maquinaP.mueve(pf.getTam()));
		else
			maquinaP.ponInstruccion(maquinaP.desapilaInd());
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

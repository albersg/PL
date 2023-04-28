package implementacion.procesamientos;

import java.util.Stack;

import implementacion.asint.ProcesamientoPorDefecto;
import implementacion.asint.TinyASint.Acc;
import implementacion.asint.TinyASint.And;
import implementacion.asint.TinyASint.Asig;
import implementacion.asint.TinyASint.Call;
import implementacion.asint.TinyASint.DecProc;
import implementacion.asint.TinyASint.DecTipo;
import implementacion.asint.TinyASint.DecVar;
import implementacion.asint.TinyASint.Decs_muchas;
import implementacion.asint.TinyASint.Decs_una;
import implementacion.asint.TinyASint.Decs_vacia;
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
import implementacion.maquinaP.MaquinaP;

public class GeneracionCodigo extends ProcesamientoPorDefecto{
	
	private MaquinaP maquinaP;
	private Stack<DecProc> procs;
	
	public GeneracionCodigo(MaquinaP maquinaP) throws Exception {
		this.maquinaP = maquinaP;
		this.procs = new Stack<DecProc>();
	}
	
	
	@Override
	public void procesa(DecProc decProc) throws Exception {
		decProc.decs().procesa(this);
		decProc.is().procesa(this);
		maquinaP.ponInstruccion(maquinaP.desactiva(decProc.getNivel(), decProc.getTam()));
		maquinaP.ponInstruccion(maquinaP.irInd());
		decProc.decs().recolecta_procs(this);
	}

	@Override
	public void procesa(Div exp) throws Exception {
		// Primer argumento
		exp.arg0().procesa(this);
		if(exp.arg0().getEsDesig()) {
			maquinaP.ponInstruccion(maquinaP.apilaInd());
		}
		if(exp.getTipo() instanceof TipoReal && exp.arg0().getTipo() instanceof TipoInt)
			maquinaP.ponInstruccion(maquinaP.int2real());
			
		// Segundo argumento
		exp.arg1().procesa(this);
		if(exp.arg1().getEsDesig()) {
			maquinaP.ponInstruccion(maquinaP.apilaInd());
		}
		if(exp.getTipo() instanceof TipoReal && exp.arg1().getTipo() instanceof TipoInt)
			maquinaP.ponInstruccion(maquinaP.int2real());
		
		// Generación de instrucción aritmética
		if(exp.getTipo() instanceof TipoInt)
			maquinaP.ponInstruccion(maquinaP.divInt());
		else if(exp.getTipo() instanceof TipoReal)
			maquinaP.ponInstruccion(maquinaP.divReal());
	}

	@Override
	public void procesa(Id exp) throws Exception {
		if(exp.getVinculo().getNivel() == 0)
			maquinaP.ponInstruccion(maquinaP.apilaInt(exp.getVinculo().getDir()));
		else {
			maquinaP.ponInstruccion(maquinaP.apilad(exp.getVinculo().getNivel()));
			maquinaP.ponInstruccion(maquinaP.apilaInt(exp.getVinculo().getDir()));
			maquinaP.ponInstruccion(maquinaP.sumaInt());
			
			if(exp.getVinculo().getEsParamRef())
				maquinaP.ponInstruccion(maquinaP.apilaInd());
			
		}
	}

	@Override
	public void procesa(Mul exp) throws Exception {
		// Primer argumento
		exp.arg0().procesa(this);
		if(exp.arg0().getEsDesig()){
			maquinaP.ponInstruccion(maquinaP.apilaInd());
		}
		if(exp.getTipo() instanceof TipoReal && exp.arg0().getTipo() instanceof TipoInt)
			maquinaP.ponInstruccion(maquinaP.int2real());
			
		// Segundo argumento
		exp.arg1().procesa(this);
		if(exp.arg1().getEsDesig()){
			maquinaP.ponInstruccion(maquinaP.apilaInd());
		}
		if(exp.getTipo() instanceof TipoReal && exp.arg1().getTipo() instanceof TipoInt)
			maquinaP.ponInstruccion(maquinaP.int2real());
		
		// Generación de instrucción aritmética
		if(exp.getTipo() instanceof TipoInt)
			maquinaP.ponInstruccion(maquinaP.mulInt());
		else if(exp.getTipo() instanceof TipoReal)
			maquinaP.ponInstruccion(maquinaP.mulReal());
	}

	@Override
	public void procesa(Resta exp) throws Exception {
		// Primer argumento
			exp.arg0().procesa(this);
			if(exp.arg0().getEsDesig()) {
				maquinaP.ponInstruccion(maquinaP.apilaInd());
			}
			if(exp.getTipo() instanceof TipoReal && exp.arg0().getTipo() instanceof TipoInt)
				maquinaP.ponInstruccion(maquinaP.int2real());
				
			// Segundo argumento
			exp.arg1().procesa(this);
			if(exp.arg1().getEsDesig()) {
				maquinaP.ponInstruccion(maquinaP.apilaInd());
			}
			if(exp.getTipo() instanceof TipoReal && exp.arg1().getTipo() instanceof TipoInt)
				maquinaP.ponInstruccion(maquinaP.int2real());
			
			// Generación de instrucción aritmética
			if(exp.getTipo() instanceof TipoInt)
				maquinaP.ponInstruccion(maquinaP.restaInt());
			else if(exp.getTipo() instanceof TipoReal)
				maquinaP.ponInstruccion(maquinaP.restaReal());
	}

	@Override
	public void procesa(Suma exp) throws Exception {
		// Primer argumento
		exp.arg0().procesa(this);
		if(exp.arg0().getEsDesig()) {
			maquinaP.ponInstruccion(maquinaP.apilaInd());
		}
		if(exp.getTipo() instanceof TipoReal && exp.arg0().getTipo() instanceof TipoInt)
			maquinaP.ponInstruccion(maquinaP.int2real());
			
		// Segundo argumento
		exp.arg1().procesa(this);
		if(exp.arg1().getEsDesig()) {
			maquinaP.ponInstruccion(maquinaP.apilaInd());
		}
		if(exp.getTipo() instanceof TipoReal && exp.arg1().getTipo() instanceof TipoInt)
			maquinaP.ponInstruccion(maquinaP.int2real());
		
		// Generación de instrucción aritmética
		if(exp.getTipo() instanceof TipoInt)
			maquinaP.ponInstruccion(maquinaP.sumaInt());
		else if(exp.getTipo() instanceof TipoReal)
			maquinaP.ponInstruccion(maquinaP.sumaReal());
	}

	@Override
	public void procesa(Prog prog) throws Exception {
		prog.is().procesa(this);
		maquinaP.ponInstruccion(maquinaP.stop());
		prog.decs().recolecta_procs(this);
		
		while(!procs.empty()) {
			DecProc p = procs.pop();
			p.procesa(this);
		}
	}

	
	@Override
	public void procesa(Is_muchas is_muchas) throws Exception {
		is_muchas.is().procesa(this);
		is_muchas.i().procesa(this);
	}

	@Override
	public void procesa(Is_una is_una) throws Exception {
		is_una.i().procesa(this);
	}


	@Override
	public void procesa(Asig asig) throws Exception {
		asig.e1().procesa(this);
		asig.e2().procesa(this);
		
		if(asig.e1().getTipo() instanceof TipoReal && asig.e2().getTipo() instanceof TipoInt) {
			if(asig.e2().getEsDesig())
				maquinaP.ponInstruccion(maquinaP.apilaInd());
			
			maquinaP.ponInstruccion(maquinaP.int2real());
			maquinaP.ponInstruccion(maquinaP.desapilaInd());
		}
		else {
			if(asig.e2().getEsDesig())
				maquinaP.ponInstruccion(maquinaP.mueve(asig.e2().getTam()));
			else maquinaP.ponInstruccion(maquinaP.desapilaInd());
		}
	}

	@Override
	public void procesa(If_then if_then) throws Exception {
		if_then.e().procesa(this);
		if(if_then.e().getEsDesig())
			maquinaP.ponInstruccion(maquinaP.apilaInd());
		maquinaP.ponInstruccion(maquinaP.irF(if_then.getEtqSig()));
		if_then.is().procesa(this);
	}

	@Override
	public void procesa(If_then_else if_then_else) throws Exception {
		if_then_else.e().procesa(this);
		if(if_then_else.e().getEsDesig())
			maquinaP.ponInstruccion(maquinaP.apilaInd());
		
		maquinaP.ponInstruccion(maquinaP.irF(if_then_else.is2().getEtqInic()));
		if_then_else.is1().procesa(this);
		maquinaP.ponInstruccion(maquinaP.irA(if_then_else.getEtqSig()));
		if_then_else.is2().procesa(this);
	}

	@Override
	public void procesa(While wh) throws Exception {
		wh.e().procesa(this);
		if(wh.e().getEsDesig())
			maquinaP.ponInstruccion(maquinaP.apilaInd());
		
		maquinaP.ponInstruccion(maquinaP.irF(wh.getEtqSig()));
		wh.is().procesa(this);
		maquinaP.ponInstruccion(maquinaP.irA(wh.getEtqInic()));
	}

	@Override
	public void procesa(Read read) throws Exception {
		read.e().procesa(this);
		if(read.e().getTipo() instanceof TipoInt)
			maquinaP.ponInstruccion(maquinaP.readInt());
		else if(read.e().getTipo() instanceof TipoReal)
			maquinaP.ponInstruccion(maquinaP.readReal());
		else if(read.e().getTipo() instanceof TipoString)
			maquinaP.ponInstruccion(maquinaP.readString());
		else {
			System.out.println("Error read tipos");
		}
		maquinaP.ponInstruccion(maquinaP.desapilaInd());
	}

	@Override
	public void procesa(Write write) throws Exception {
		write.e().procesa(this);
		if(write.e().getEsDesig())
			maquinaP.ponInstruccion(maquinaP.apilaInd());
		if(write.e().getTipo() instanceof TipoInt)
			maquinaP.ponInstruccion(maquinaP.writeInt());
		else if(write.e().getTipo() instanceof TipoReal)
			maquinaP.ponInstruccion(maquinaP.writeReal());
		else if(write.e().getTipo() instanceof TipoBool)
			maquinaP.ponInstruccion(maquinaP.writeBool());
		else if(write.e().getTipo() instanceof TipoString)
			maquinaP.ponInstruccion(maquinaP.writeString());
		else {
			System.out.println("Error write tipos");
		}
		
	}

	@Override
	public void procesa(Nl nl) throws Exception {
		maquinaP.ponInstruccion(maquinaP.apilaString("\n"));
		maquinaP.ponInstruccion(maquinaP.writeString());
	}

	@Override
	public void procesa(New new1) throws Exception {
		new1.e().procesa(this);
		maquinaP.ponInstruccion(maquinaP.alloc(new1.e().getTamBase()));
		maquinaP.ponInstruccion(maquinaP.desapilaInd());
	}

	@Override
	public void procesa(Delete delete) throws Exception {
		delete.e().procesa(this);
		maquinaP.ponInstruccion(maquinaP.apilaInd());
		maquinaP.ponInstruccion(maquinaP.dealloc(delete.e().getTamBase()));
	}

	@Override
	public void procesa(Call call) throws Exception {
		maquinaP.ponInstruccion(maquinaP.activa(call.getVinculo().getNivel(), call.getVinculo().getTam(), call.getEtqSig()));
		if(call.preals() instanceof PReal_ninguno)
			gen_cod_params((PReal_ninguno) call.preals(), (PForm_vacia) ((DecProc) call.getVinculo()).pf());
		else if(call.preals() instanceof PReal_uno)
			gen_cod_params((PReal_uno) call.preals(), (PForm_una) ((DecProc) call.getVinculo()).pf());
		else
			gen_cod_params((PReal_muchos) call.preals(), (PForm_muchas) ((DecProc) call.getVinculo()).pf());
		maquinaP.ponInstruccion(maquinaP.desapilad(call.getVinculo().getNivel()));
		maquinaP.ponInstruccion(maquinaP.irA(call.getVinculo().getEtqInic()));
	}

	@Override
	public void procesa(IComp iComp) throws Exception {
		maquinaP.ponInstruccion(maquinaP.activa(iComp.getNivel(), iComp.getTam(), iComp.getEtqSig()));
		maquinaP.ponInstruccion(maquinaP.desapilad(iComp.getNivel()));
		
		iComp.is().procesa(this);
		iComp.decs().recolecta_procs(this);
		
		while(!procs.empty()){
			DecProc p = procs.pop();
			p.procesa(this);
		}	
		
		maquinaP.ponInstruccion(maquinaP.desactiva(iComp.getNivel(), iComp.getTam()));
		maquinaP.ponInstruccion(maquinaP.irInd());
	}

	@Override
	public void procesa(LitInt litInt) throws Exception {
		maquinaP.ponInstruccion(maquinaP.apilaInt(Integer.valueOf(litInt.s().toString())));
	}

	@Override
	public void procesa(LitReal litReal) throws Exception {
		maquinaP.ponInstruccion(maquinaP.apilaReal(Float.valueOf(litReal.s().toString())));		
	}

	@Override
	public void procesa(LitStr litStr) throws Exception {
		
		String str = litStr.s().toString().substring(1, litStr.s().toString().length() - 1);
		maquinaP.ponInstruccion(maquinaP.apilaString(str));
	}

	@Override
	public void procesa(True true1) throws Exception {
		maquinaP.ponInstruccion(maquinaP.apilaBool(true));		
	}

	@Override
	public void procesa(Null null1) throws Exception {
		maquinaP.ponInstruccion(maquinaP.apilaInt(-1));
	}

	@Override
	public void procesa(Menor menor) throws Exception {
		// Primer argumento
		menor.arg0().procesa(this);
		if(menor.arg0().getEsDesig()){
			maquinaP.ponInstruccion(maquinaP.apilaInd());
		}
		if(menor.getTipo() instanceof TipoReal && menor.arg0().getTipo() instanceof TipoInt)
			maquinaP.ponInstruccion(maquinaP.int2real());
			
		// Segundo argumento
		menor.arg1().procesa(this);
		if(menor.arg1().getEsDesig()) {
			maquinaP.ponInstruccion(maquinaP.apilaInd());
		}
		if(menor.getTipo() instanceof TipoReal && menor.arg1().getTipo() instanceof TipoInt)
			maquinaP.ponInstruccion(maquinaP.int2real());
		
		// Generación de instrucción comparativa
		if(menor.arg0().getTipo() instanceof TipoInt)
			maquinaP.ponInstruccion(maquinaP.menorInt());
		else if(menor.arg0().getTipo() instanceof TipoReal)
			maquinaP.ponInstruccion(maquinaP.menorReal());	
		else if(menor.arg0().getTipo() instanceof TipoString)
			maquinaP.ponInstruccion(maquinaP.menorString());
		else if(menor.arg0().getTipo() instanceof TipoBool)
			maquinaP.ponInstruccion(maquinaP.menorBool());
	}

	@Override
	public void procesa(Mayor mayor) throws Exception {
		// Primer argumento
		mayor.arg0().procesa(this);
		if(mayor.arg0().getEsDesig()) {
			maquinaP.ponInstruccion(maquinaP.apilaInd());
		}
		if(mayor.getTipo() instanceof TipoReal && mayor.arg0().getTipo() instanceof TipoInt)
			maquinaP.ponInstruccion(maquinaP.int2real());
			
		// Segundo argumento
		mayor.arg1().procesa(this);
		if(mayor.arg1().getEsDesig()) {
			maquinaP.ponInstruccion(maquinaP.apilaInd());
		}
		if(mayor.getTipo() instanceof TipoReal && mayor.arg1().getTipo() instanceof TipoInt)
			maquinaP.ponInstruccion(maquinaP.int2real());
		
		// Generación de instrucción comparativa
		if(mayor.arg0().getTipo() instanceof TipoInt)
			maquinaP.ponInstruccion(maquinaP.mayorInt());
		else if(mayor.arg0().getTipo() instanceof TipoReal)
			maquinaP.ponInstruccion(maquinaP.mayorReal());	
		else if(mayor.arg0().getTipo() instanceof TipoString)
			maquinaP.ponInstruccion(maquinaP.mayorString());
		else if(mayor.arg0().getTipo() instanceof TipoBool)
			maquinaP.ponInstruccion(maquinaP.mayorBool());
	}

	@Override
	public void procesa(False false1) throws Exception {
		maquinaP.ponInstruccion(maquinaP.apilaBool(false));				
	}

	@Override
	public void procesa(MayorIgual mayorIgual) throws Exception {
		// Primer argumento
		mayorIgual.arg0().procesa(this);
		if(mayorIgual.arg0().getEsDesig()){
			maquinaP.ponInstruccion(maquinaP.apilaInd());
		}
		if(mayorIgual.getTipo() instanceof TipoReal && mayorIgual.arg0().getTipo() instanceof TipoInt)
			maquinaP.ponInstruccion(maquinaP.int2real());
			
		// Segundo argumento
		mayorIgual.arg1().procesa(this);
		if(mayorIgual.arg1().getEsDesig()){
			maquinaP.ponInstruccion(maquinaP.apilaInd());
		}
		if(mayorIgual.getTipo() instanceof TipoReal && mayorIgual.arg1().getTipo() instanceof TipoInt)
			maquinaP.ponInstruccion(maquinaP.int2real());
		
		// Generación de instrucción comparativa
		if(mayorIgual.arg0().getTipo() instanceof TipoInt)
			maquinaP.ponInstruccion(maquinaP.mayorIgualInt());
		else if(mayorIgual.arg0().getTipo() instanceof TipoReal)
			maquinaP.ponInstruccion(maquinaP.mayorIgualReal());	
		else if(mayorIgual.arg0().getTipo() instanceof TipoString)
			maquinaP.ponInstruccion(maquinaP.mayorIgualString());
		else if(mayorIgual.arg0().getTipo() instanceof TipoBool)
			maquinaP.ponInstruccion(maquinaP.mayorIgualBool());
	}
	

	@Override
	public void procesa(Igual igual) throws Exception {
		// Primer argumento
		igual.arg0().procesa(this);
		if(igual.arg0().getEsDesig()) {
			maquinaP.ponInstruccion(maquinaP.apilaInd());
		}
		if(igual.getTipo() instanceof TipoReal && igual.arg0().getTipo() instanceof TipoInt)
			maquinaP.ponInstruccion(maquinaP.int2real());
			
		// Segundo argumento
		igual.arg1().procesa(this);
		if(igual.arg1().getEsDesig()){
			maquinaP.ponInstruccion(maquinaP.apilaInd());
		}
		if(igual.getTipo() instanceof TipoReal && igual.arg1().getTipo() instanceof TipoInt)
			maquinaP.ponInstruccion(maquinaP.int2real());
		
		// Generación de instrucción comparativa
		if(igual.arg0().getTipo() instanceof TipoInt)
			maquinaP.ponInstruccion(maquinaP.igualInt());
		else if(igual.arg0().getTipo() instanceof TipoReal)
			maquinaP.ponInstruccion(maquinaP.igualReal());	
		else if(igual.arg0().getTipo() instanceof TipoString)
			maquinaP.ponInstruccion(maquinaP.igualString());
		else if(igual.arg0().getTipo() instanceof TipoBool)
			maquinaP.ponInstruccion(maquinaP.igualBool());
	}

	@Override
	public void procesa(Distinto distinto) throws Exception {
		// Primer argumento
		distinto.arg0().procesa(this);
		if(distinto.arg0().getEsDesig()) {
			maquinaP.ponInstruccion(maquinaP.apilaInd());
		}
		if(distinto.getTipo() instanceof TipoReal && distinto.arg0().getTipo() instanceof TipoInt)
			maquinaP.ponInstruccion(maquinaP.int2real());
			
		// Segundo argumento
		distinto.arg1().procesa(this);
		if(distinto.arg1().getEsDesig()) {
			maquinaP.ponInstruccion(maquinaP.apilaInd());
		}
		if(distinto.getTipo() instanceof TipoReal && distinto.arg1().getTipo() instanceof TipoInt)
			maquinaP.ponInstruccion(maquinaP.int2real());
		
		// Generación de instrucción comparativa
		if(distinto.arg0().getTipo() instanceof TipoInt)
			maquinaP.ponInstruccion(maquinaP.distintoInt());
		else if(distinto.arg0().getTipo() instanceof TipoReal)
			maquinaP.ponInstruccion(maquinaP.distintoReal());	
		else if(distinto.arg0().getTipo() instanceof TipoString)
			maquinaP.ponInstruccion(maquinaP.distintoString());
		else if(distinto.arg0().getTipo() instanceof TipoBool)
			maquinaP.ponInstruccion(maquinaP.distintoBool());
	}

	@Override
	public void procesa(MenorIgual menorIgual) throws Exception {
		// Primer argumento
		menorIgual.arg0().procesa(this);
		if(menorIgual.arg0().getEsDesig()) {
			maquinaP.ponInstruccion(maquinaP.apilaInd());
		}
		if(menorIgual.getTipo() instanceof TipoReal && menorIgual.arg0().getTipo() instanceof TipoInt)
			maquinaP.ponInstruccion(maquinaP.int2real());
			
		// Segundo argumento
		menorIgual.arg1().procesa(this);
		if(menorIgual.arg1().getEsDesig()) {
			maquinaP.ponInstruccion(maquinaP.apilaInd());
		}
		if(menorIgual.getTipo() instanceof TipoReal && menorIgual.arg1().getTipo() instanceof TipoInt)
			maquinaP.ponInstruccion(maquinaP.int2real());
		
		// Generación de instrucción comparativa
		if(menorIgual.arg0().getTipo() instanceof TipoInt)
			maquinaP.ponInstruccion(maquinaP.menorIgualInt());
		else if(menorIgual.arg0().getTipo() instanceof TipoReal)
			maquinaP.ponInstruccion(maquinaP.menorIgualReal());	
		else if(menorIgual.arg0().getTipo() instanceof TipoString)
			maquinaP.ponInstruccion(maquinaP.menorIgualString());
		else if(menorIgual.arg0().getTipo() instanceof TipoBool)
			maquinaP.ponInstruccion(maquinaP.menorIgualBool());	
	}

	@Override
	public void procesa(And and) throws Exception {
		// Primer argumento
		and.arg0().procesa(this);
		if(and.arg0().getEsDesig()){
			maquinaP.ponInstruccion(maquinaP.apilaInd());
		}
			
		// Segundo argumento
		and.arg1().procesa(this);
		if(and.arg1().getEsDesig()){
			maquinaP.ponInstruccion(maquinaP.apilaInd());
		}
		
		// Generación de instrucción comparativa
		maquinaP.ponInstruccion(maquinaP.and());
	}

	@Override
	public void procesa(Or or) throws Exception {
		// Primer argumento
		or.arg0().procesa(this);
		if(or.arg0().getEsDesig()) {
			maquinaP.ponInstruccion(maquinaP.apilaInd());
		}
			
		// Segundo argumento
		or.arg1().procesa(this);
		if(or.arg1().getEsDesig()){
			maquinaP.ponInstruccion(maquinaP.apilaInd());
		}
		
		// Generación de instrucción comparativa
		maquinaP.ponInstruccion(maquinaP.or());		
	}

	@Override
	public void procesa(Modulo modulo) throws Exception {
		// Primer argumento
		modulo.arg0().procesa(this);
		if(modulo.arg0().getEsDesig()){
			maquinaP.ponInstruccion(maquinaP.apilaInd());
		}
			
		// Segundo argumento
		modulo.arg1().procesa(this);
		if(modulo.arg1().getEsDesig()){
			maquinaP.ponInstruccion(maquinaP.apilaInd());
		}
		
		// Generación de instrucción comparativa
		maquinaP.ponInstruccion(maquinaP.mod());				
	}

	@Override
	public void procesa(Negativo negativo) throws Exception {
		// Primer argumento
		negativo.e().procesa(this);
		if(negativo.e().getEsDesig()) {
			maquinaP.ponInstruccion(maquinaP.apilaInd());
		}
			
		// Generación de instrucción comparativa
		if(negativo.getTipo() instanceof TipoInt)
			maquinaP.ponInstruccion(maquinaP.negativoInt());
		else if(negativo.getTipo() instanceof TipoReal)
			maquinaP.ponInstruccion(maquinaP.negativoReal());
	}

	@Override
	public void procesa(Not not) throws Exception {
		// Primer argumento
		not.e().procesa(this);
		if(not.e().getEsDesig()){
			maquinaP.ponInstruccion(maquinaP.apilaInd());
		}
			
		// Generación de instrucción comparativa
		maquinaP.ponInstruccion(maquinaP.not());
	}

	@Override
	public void procesa(Acc acc) throws Exception {
		acc.e().procesa(this);
		// int desp = 
		//maquinaP.ponInstruccion(maquinaP.apilaInt(desp));
		maquinaP.ponInstruccion(maquinaP.sumaInt());
	}

	@Override
	public void procesa(Indx indx) throws Exception {
		indx.e1().procesa(this);
		indx.e2().procesa(this);
		if(indx.e1().getEsDesig())
			maquinaP.ponInstruccion(maquinaP.apilaInd());
		maquinaP.ponInstruccion(maquinaP.apilaInt(indx.e1().getTamBase()));
		maquinaP.ponInstruccion(maquinaP.mulInt());
		maquinaP.ponInstruccion(maquinaP.sumaInt());
	}

	@Override
	public void procesa(Dref dref) throws Exception {
		dref.e().procesa(this);
		maquinaP.ponInstruccion(maquinaP.apilaInd());
	}

	private void gen_cod_params(PReal_ninguno preals, PForm_vacia pfs) throws Exception {
		
	}
	
	private void gen_cod_params(PReal_uno preals, PForm_una pfs) throws Exception {
		gen_cod_paso(preals.preal(), pfs.pform());
	}
	
	private void gen_cod_params(PReal_muchos preals, PForm_muchas pfs) throws Exception {
		gen_cod_params((PReal_muchos)preals.preals(), (PForm_muchas)pfs.pforms());
		gen_cod_paso(preals.preal(), pfs.pform());
	}
	
	/*private void gen_cod_params(PReals preals, PForms pfs) throws Exception {
		if(preals.varias() && pfs.varias()) throws Exception {
			gen_cod_params(((PReal_muchos) preals).preals(), ((PForm_muchas) pfs).pforms());
			gen_cod_paso(((PReal_uno) preals).preal(), ((PForm_una) pfs).pform());
		}
		else if(!preals.varias() && !pfs.varias()){
			gen_cod_paso(((PReal_uno) preals).preal(), ((PForm_una) pfs).pform());
		}
	}*/
	
	private void gen_cod_paso(PReal preal, PForm pf) throws Exception {
		maquinaP.ponInstruccion(maquinaP.dup());
		maquinaP.ponInstruccion(maquinaP.apilaInt(pf.getDir()));
		maquinaP.ponInstruccion(maquinaP.sumaInt());
		preal.e().procesa(this);
		if(preal.e().getEsDesig() && !pf.getEsParamRef())
			maquinaP.ponInstruccion(maquinaP.mueve(pf.getTam()));
		else
			maquinaP.ponInstruccion(maquinaP.desapilaInd());
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

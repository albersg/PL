package implementacion.procesamientos;

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
import implementacion.asint.TinyASint.DecVar;
import implementacion.asint.TinyASint.Decs_muchas;
import implementacion.asint.TinyASint.Decs_una;
import implementacion.asint.TinyASint.Delete;
import implementacion.asint.TinyASint.Distinto;
import implementacion.asint.TinyASint.Div;
import implementacion.asint.TinyASint.Dref;
import implementacion.asint.TinyASint.Exp;
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
import implementacion.asint.TinyASint.PFormRef;
import implementacion.asint.TinyASint.PForm_muchas;
import implementacion.asint.TinyASint.PForm_una;
import implementacion.asint.TinyASint.PForm_vacia;
import implementacion.asint.TinyASint.PReal;
import implementacion.asint.TinyASint.PReal_muchos;
import implementacion.asint.TinyASint.PReal_ninguno;
import implementacion.asint.TinyASint.PReal_uno;
import implementacion.asint.TinyASint.Prog;
import implementacion.asint.TinyASint.Read;
import implementacion.asint.TinyASint.Resta;
import implementacion.asint.TinyASint.Suma;
import implementacion.asint.TinyASint.Tipo;
import implementacion.asint.TinyASint.TipoArray;
import implementacion.asint.TinyASint.TipoBool;
import implementacion.asint.TinyASint.TipoError;
import implementacion.asint.TinyASint.TipoInt;
import implementacion.asint.TinyASint.TipoNull;
import implementacion.asint.TinyASint.TipoOk;
import implementacion.asint.TinyASint.TipoPointer;
import implementacion.asint.TinyASint.TipoReal;
import implementacion.asint.TinyASint.TipoRecord;
import implementacion.asint.TinyASint.TipoRef;
import implementacion.asint.TinyASint.TipoString;
import implementacion.asint.TinyASint.True;
import implementacion.asint.TinyASint.While;
import implementacion.asint.TinyASint.Write;

public class ChequeoTipo extends ProcesamientoPorDefecto{
	
	
	@Override
	public void procesa(Decs_muchas decs) throws Exception {
		decs.decs().procesa(this);
		decs.dec().procesa(this);
	}

	@Override
	public void procesa(Decs_una decs) throws Exception {
		decs.dec().procesa(this);
	}

	@Override
	public void procesa(DecVar decVar) throws Exception {
		decVar.tipo().procesa(this);
		decVar.setTipo(decVar.tipo().getTipo());
	}

	@Override
	public void procesa(DecProc decProc) throws Exception {
		decProc.pf().procesa(this);
		decProc.decs().procesa(this);
		decProc.is().procesa(this);
		decProc.setTipo(ambos_ok(decProc.pf().getTipo(), ambos_ok(decProc.decs().getTipo(), decProc.is().getTipo())));
	}

	@Override
	public void procesa(DecTipo decTipo) throws Exception {
		decTipo.tipo().procesa(this);
		decTipo.setTipo(decTipo.tipo().getTipo());
	}

	@Override
	public void procesa(Div exp) throws Exception {
		exp.arg0().procesa(this);
		exp.arg1().procesa(this);
		
		if(exp.arg0().getTipo().ref() instanceof TipoInt && exp.arg1().getTipo().ref() instanceof TipoInt) {
			exp.setTipo(new TipoInt());
		}
		else if(exp.arg0().getTipo().ref() instanceof TipoReal && (exp.arg1().getTipo().ref() instanceof TipoReal || exp.arg1().getTipo().ref() instanceof TipoInt)) {
			exp.setTipo(new TipoReal());
		}
		else if(exp.arg1().getTipo().ref() instanceof TipoReal && (exp.arg0().getTipo().ref() instanceof TipoReal || exp.arg0().getTipo().ref() instanceof TipoInt)) {
			exp.setTipo(new TipoReal());
		}
		else {
			exp.setTipo(new TipoError());
			System.out.println("Error: div");
			throw new Exception();
		}
	}

	@Override
	public void procesa(Id exp) throws Exception {
		Dec vinculo = (Dec) exp.getVinculo();
		if(vinculo instanceof DecVar)
			exp.setTipo(((DecVar)vinculo).tipo());
		else if(vinculo instanceof PForm)
			exp.setTipo(((PForm)vinculo).tipo());
		else if(vinculo instanceof PFormRef)
			exp.setTipo(((PFormRef)vinculo).tipo());
		else {
				exp.setTipo(new TipoError());
				System.out.println("Error: id");
				throw new Exception();
		}
	}

	@Override
	public void procesa(Mul exp) throws Exception {
		exp.arg0().procesa(this);
		exp.arg1().procesa(this);
		
		if(exp.arg0().getTipo().ref() instanceof TipoInt && exp.arg1().getTipo().ref() instanceof TipoInt) {
			exp.setTipo(new TipoInt());
		}
		else if(exp.arg0().getTipo().ref() instanceof TipoReal && (exp.arg1().getTipo().ref() instanceof TipoReal || exp.arg1().getTipo().ref() instanceof TipoInt)) {
			exp.setTipo(new TipoReal());
		}
		else if(exp.arg1().getTipo().ref() instanceof TipoReal && (exp.arg0().getTipo().ref() instanceof TipoReal || exp.arg0().getTipo().ref() instanceof TipoInt)) {
			exp.setTipo(new TipoReal());
		}
		else {
			exp.setTipo(new TipoError());
			System.out.println("Error: mul");
			throw new Exception();
		}
	}

	@Override
	public void procesa(Resta exp) throws Exception {
		exp.arg0().procesa(this);
		exp.arg1().procesa(this);
		
		if(exp.arg0().getTipo().ref() instanceof TipoInt && exp.arg1().getTipo().ref() instanceof TipoInt) {
			exp.setTipo(new TipoInt());
		}
		else if(exp.arg0().getTipo().ref() instanceof TipoReal && (exp.arg1().getTipo().ref() instanceof TipoReal || exp.arg1().getTipo().ref() instanceof TipoInt)) {
			exp.setTipo(new TipoReal());
		}
		else if(exp.arg1().getTipo().ref() instanceof TipoReal && (exp.arg0().getTipo().ref() instanceof TipoReal || exp.arg0().getTipo().ref() instanceof TipoInt)) {
			exp.setTipo(new TipoReal());
		}
		else {
			exp.setTipo(new TipoError());
			System.out.println("Error: resta");
			throw new Exception();
		}
	}

	@Override
	public void procesa(Suma exp) throws Exception {
		exp.arg0().procesa(this);
		exp.arg1().procesa(this);
		
		if(exp.arg0().getTipo().ref() instanceof TipoInt && exp.arg1().getTipo().ref() instanceof TipoInt) {
			exp.setTipo(new TipoInt());
		}
		else if(exp.arg0().getTipo().ref() instanceof TipoReal && (exp.arg1().getTipo().ref() instanceof TipoReal || exp.arg1().getTipo().ref() instanceof TipoInt)) {
			exp.setTipo(new TipoReal());
		}
		else if(exp.arg1().getTipo().ref() instanceof TipoReal && (exp.arg0().getTipo().ref() instanceof TipoReal || exp.arg0().getTipo().ref() instanceof TipoInt)) {
			exp.setTipo(new TipoReal());
		}
		else {
			exp.setTipo(new TipoError());
			System.out.println("Error: suma");
			throw new Exception();
		}
	}

	@Override
	public void procesa(Prog prog) throws Exception {
		prog.decs().procesa(this);
		prog.is().procesa(this);
		prog.setTipo(prog.is().getTipo());
	}

	@Override
	public void procesa(TipoInt tipoInt) throws Exception {
		tipoInt.setTipo(new TipoInt());
	}

	@Override
	public void procesa(TipoReal tipoReal) throws Exception {
		tipoReal.setTipo(new TipoReal());		
	}

	@Override
	public void procesa(TipoBool tipoBool) throws Exception {
		tipoBool.setTipo(new TipoBool());		
	}

	@Override
	public void procesa(TipoString tipoString) throws Exception {
		tipoString.setTipo(new TipoString());		
	}

	@Override
	public void procesa(TipoRef tipoRef) throws Exception {
		if(tipoRef.getVinculo() instanceof DecTipo) {
			tipoRef.setTipo(new TipoOk());
		}
		else {
			tipoRef.setTipo(new TipoError());
			System.out.println("Error: tipoRef");
			throw new Exception();
		}
	}

	@Override
	public void procesa(TipoArray tipoArray) throws Exception {
		tipoArray.tipo().procesa(this);
		tipoArray.setTipo(tipoArray.tipo().getTipo());
	}

	@Override
	public void procesa(TipoRecord tipoRecord) throws Exception {
		// TODO
	}

	@Override
	public void procesa(TipoPointer tipoPointer) throws Exception {
		tipoPointer.tipo().procesa(this);
		tipoPointer.setTipo(tipoPointer.tipo().getTipo());
	}


	@Override
	public void procesa(PFormRef pFormRef) throws Exception {
		pFormRef.tipo().procesa(this);
		pFormRef.setTipo(pFormRef.tipo().getTipo());
	}

	@Override
	public void procesa(Is_muchas is_muchas) throws Exception {
		is_muchas.is().procesa(this);
		is_muchas.i().procesa(this);
		is_muchas.setTipo(ambos_ok(is_muchas.is().getTipo(), is_muchas.i().getTipo()));
	}

	@Override
	public void procesa(Is_una is_una) throws Exception {
		is_una.i().procesa(this);
		is_una.setTipo(is_una.i().getTipo());
	}

	@Override
	public void procesa(Campo campo) throws Exception {
		campo.tipo().procesa(this);
		campo.setTipo(campo.tipo().getTipo());
	}

	@Override
	public void procesa(Campos_muchos campos_muchos) throws Exception {
		campos_muchos.campos().procesa(this);
		campos_muchos.campo().procesa(this);
		campos_muchos.setTipo(ambos_ok(campos_muchos.campos().getTipo(), campos_muchos.campo().getTipo()));
	}

	@Override
	public void procesa(Campos_uno campos_uno) throws Exception {
		campos_uno.campo().procesa(this);
		campos_uno.setTipo(campos_uno.campo().getTipo());
	}
	
	// ??¿??¿?¿?¿¿??¿¿

	@Override
	public void procesa(Asig asig) throws Exception {
		asig.e1().procesa(this);
		asig.e2().procesa(this);
		if(asig.e1().getEsDesig() && son_compatibles(asig.e1().getTipo().ref(), asig.e2().getTipo().ref())) {
			asig.setTipo(new TipoOk());
		}
		else {
			asig.setTipo(new TipoError());
			System.out.println("Error: asig");
			throw new Exception();
		}
	}

	@Override
	public void procesa(If_then if_then) throws Exception {
		if_then.e().procesa(this);
		if(if_then.e().getTipo().ref() instanceof TipoBool) {
			if_then.is().procesa(this);
			if_then.setTipo(if_then.is().getTipo());
		}
		else {
			if_then.setTipo(new TipoError());
			System.out.println("Error: if_then.e() no es bool");
			throw new Exception();
		}
	}

	@Override
	public void procesa(If_then_else if_then_else) throws Exception {
		if_then_else.e().procesa(this);
		if(if_then_else.e().getTipo().ref() instanceof TipoBool) {
			if_then_else.is1().procesa(this);
			if_then_else.is2().procesa(this);
			if_then_else.setTipo(ambos_ok(if_then_else.is1().getTipo(), if_then_else.is2().getTipo()));
		}
		else {
			if_then_else.setTipo(new TipoError());
			System.out.println("Error: if_then_else.e() no es bool");
			throw new Exception();
		}

	}

	@Override
	public void procesa(While while1) throws Exception {
		while1.e().procesa(this);
		if(while1.e().getTipo().ref() instanceof TipoBool) {
			while1.is().procesa(this);
			while1.setTipo(while1.is().getTipo());
		}
		else {
			while1.setTipo(new TipoError());
			System.out.println("Error: while.e() no es bool");
			throw new Exception();
		}
		
	}

	@Override
	public void procesa(Read read) throws Exception {
		read.e().procesa(this);
		Exp e = read.e();
		if((e.getTipo().ref() instanceof TipoInt || 
				e.getTipo().ref() instanceof TipoReal ||
				e.getTipo().ref() instanceof TipoString) && e.getEsDesig()) {
			read.setTipo(new TipoOk());
		}
		else
			read.setTipo(new TipoError());
	}

	@Override
	public void procesa(Write write) throws Exception {
		write.e().procesa(this);
		Exp e = write.e();
		if((e.getTipo().ref() instanceof TipoInt || 
				e.getTipo().ref() instanceof TipoReal ||
				e.getTipo().ref() instanceof TipoString) ||
				e.getTipo().ref() instanceof TipoBool) {
			write.setTipo(new TipoOk());
		}
		else
			write.setTipo(new TipoError());
	}

	@Override
	public void procesa(Nl nl) throws Exception {
		nl.setTipo(new TipoOk());
	}

	@Override
	public void procesa(New new1) throws Exception {
		new1.e().procesa(this);
		if(new1.e().getTipo().ref() instanceof TipoPointer) {
			new1.setTipo(new TipoOk());
		}
		else {
			new1.setTipo(new TipoError());
			System.out.println("Error: new1.e() no es pointer");
			throw new Exception();
		}
	}

	@Override
	public void procesa(Delete delete) throws Exception {
		delete.e().procesa(this);
		if(delete.e().getTipo().ref() instanceof TipoPointer) {
			delete.setTipo(new TipoOk());
		}
		else {
			delete.setTipo(new TipoError());
			System.out.println("Error: delete.e() no es pointer");
			throw new Exception();
		}
	}

	@Override
	public void procesa(Call call) throws Exception {
		if(call.getVinculo() instanceof DecProc) {
			DecProc dp = (DecProc) call.e().getVinculo();
			if(call.preals().getTam() != dp.pf().getTam()) {
				call.setTipo(new TipoError());
				System.out.println("Error: numero parametros call");
				throw new Exception();
			}
			else {
				if(call.preals() instanceof PReal_ninguno)
					chequeo_parametros((PReal_ninguno) call.preals(), (PForm_vacia) dp.pf());
				else if(call.preals() instanceof PReal_uno)
					chequeo_parametros((PReal_uno) call.preals(), (PForm_una) dp.pf());
				else
					chequeo_parametros((PReal_muchos) call.preals(), (PForm_muchas) dp.pf());
			}
		}
		else {
			call.setTipo(new TipoError());
			System.out.println("Error: numero parametros call");
			throw new Exception();
		}
	}

	@Override
	public void procesa(IComp iComp) throws Exception {
		iComp.is().procesa(this);
		iComp.setTipo(iComp.is().getTipo());
	}


	@Override
	public void procesa(LitInt litInt) throws Exception {
		litInt.setTipo(new TipoInt());
	}

	@Override
	public void procesa(LitReal litReal) throws Exception {
		litReal.setTipo(new TipoReal());
	}

	@Override
	public void procesa(LitStr litStr) throws Exception {
		litStr.setTipo(new TipoString());
	}

	@Override
	public void procesa(True true1) throws Exception {
		true1.setTipo(new TipoBool());
	}

	@Override
	public void procesa(Null null1) throws Exception {
		null1.setTipo(new TipoNull());
	}

	@Override
	public void procesa(Menor menor) throws Exception {
		menor.arg0().procesa(this);
		menor.arg1().procesa(this);
		if((menor.arg0().getTipo().ref() instanceof TipoInt || menor.arg0().getTipo().ref() instanceof TipoReal)&& (menor.arg1().getTipo().ref() instanceof TipoInt || menor.arg1().getTipo().ref() instanceof TipoReal))
			menor.setTipo(new TipoBool());
		else if(menor.arg0().getTipo().ref() instanceof TipoBool && menor.arg1().getTipo().ref() instanceof TipoBool)
			menor.setTipo(new TipoBool());
		else if(menor.arg0().getTipo().ref() instanceof TipoString && menor.arg1().getTipo().ref() instanceof TipoString)
			menor.setTipo(new TipoBool());
		else {
			menor.setTipo(new TipoError());
			System.out.println("Error: menor");
			throw new Exception();
		}
	}

	@Override
	public void procesa(Mayor mayor) throws Exception {
		mayor.arg0().procesa(this);
		mayor.arg1().procesa(this);
		if((mayor.arg0().getTipo().ref() instanceof TipoInt || mayor.arg0().getTipo().ref() instanceof TipoReal)&& (mayor.arg1().getTipo().ref() instanceof TipoInt || mayor.arg1().getTipo().ref() instanceof TipoReal))
			mayor.setTipo(new TipoBool());
		else if(mayor.arg0().getTipo().ref() instanceof TipoBool && mayor.arg1().getTipo().ref() instanceof TipoBool)
			mayor.setTipo(new TipoBool());
		else if(mayor.arg0().getTipo().ref() instanceof TipoString && mayor.arg1().getTipo().ref() instanceof TipoString)
			mayor.setTipo(new TipoBool());
		else {
			mayor.setTipo(new TipoError());
			System.out.println("Error: mayor");
			throw new Exception();
		}
	}

	@Override
	public void procesa(False false1) throws Exception {
		false1.setTipo(new TipoBool());
	}

	@Override
	public void procesa(MayorIgual mayorIgual) throws Exception {
		mayorIgual.arg0().procesa(this);
		mayorIgual.arg1().procesa(this);
		if((mayorIgual.arg0().getTipo().ref() instanceof TipoInt || mayorIgual.arg0().getTipo().ref() instanceof TipoReal)&& (mayorIgual.arg1().getTipo().ref() instanceof TipoInt || mayorIgual.arg1().getTipo().ref() instanceof TipoReal))
			mayorIgual.setTipo(new TipoBool());
		else if(mayorIgual.arg0().getTipo().ref() instanceof TipoBool && mayorIgual.arg1().getTipo().ref() instanceof TipoBool)
			mayorIgual.setTipo(new TipoBool());
		else if(mayorIgual.arg0().getTipo().ref() instanceof TipoString && mayorIgual.arg1().getTipo().ref() instanceof TipoString)
			mayorIgual.setTipo(new TipoBool());
		else {
			mayorIgual.setTipo(new TipoError());
			System.out.println("Error: mayorIgual");
			throw new Exception();
		}
	}

	@Override
	public void procesa(Igual igual) throws Exception {
		igual.arg0().procesa(this);
		igual.arg1().procesa(this);
		if((igual.arg0().getTipo().ref() instanceof TipoInt || igual.arg0().getTipo().ref() instanceof TipoReal)&& (igual.arg1().getTipo().ref() instanceof TipoInt || igual.arg1().getTipo().ref() instanceof TipoReal))
			igual.setTipo(new TipoBool());
		else if(igual.arg0().getTipo().ref() instanceof TipoBool && igual.arg1().getTipo().ref() instanceof TipoBool)
			igual.setTipo(new TipoBool());
		else if(igual.arg0().getTipo().ref() instanceof TipoString && igual.arg1().getTipo().ref() instanceof TipoString)
			igual.setTipo(new TipoBool());
		else if(igual.arg0().getTipo().ref() instanceof TipoPointer && igual.arg1().getTipo().ref() instanceof TipoPointer)
			igual.setTipo(new TipoBool());
		else if(igual.arg0().getTipo().ref() instanceof TipoPointer && igual.arg1().getTipo().ref() instanceof TipoNull)
			igual.setTipo(new TipoBool());
		else if(igual.arg0().getTipo().ref() instanceof TipoNull && igual.arg1().getTipo().ref() instanceof TipoPointer)
			igual.setTipo(new TipoBool());
		else if(igual.arg0().getTipo().ref() instanceof TipoNull && igual.arg1().getTipo().ref() instanceof TipoNull)
			igual.setTipo(new TipoBool());
		else {
			igual.setTipo(new TipoError());
			System.out.println("Error: igual");
			throw new Exception();
		}
	}

	@Override
	public void procesa(Distinto distinto) throws Exception {
		distinto.arg0().procesa(this);
		distinto.arg1().procesa(this);
		if((distinto.arg0().getTipo().ref() instanceof TipoInt || distinto.arg0().getTipo().ref() instanceof TipoReal)&& (distinto.arg1().getTipo().ref() instanceof TipoInt || distinto.arg1().getTipo().ref() instanceof TipoReal))
			distinto.setTipo(new TipoBool());
		else if(distinto.arg0().getTipo().ref() instanceof TipoBool && distinto.arg1().getTipo().ref() instanceof TipoBool)
			distinto.setTipo(new TipoBool());
		else if(distinto.arg0().getTipo().ref() instanceof TipoString && distinto.arg1().getTipo().ref() instanceof TipoString)
			distinto.setTipo(new TipoBool());
		else if(distinto.arg0().getTipo().ref() instanceof TipoPointer && distinto.arg1().getTipo().ref() instanceof TipoPointer)
			distinto.setTipo(new TipoBool());
		else if(distinto.arg0().getTipo().ref() instanceof TipoPointer && distinto.arg1().getTipo().ref() instanceof TipoNull)
			distinto.setTipo(new TipoBool());
		else if(distinto.arg0().getTipo().ref() instanceof TipoNull && distinto.arg1().getTipo().ref() instanceof TipoPointer)
			distinto.setTipo(new TipoBool());
		else if(distinto.arg0().getTipo().ref() instanceof TipoNull && distinto.arg1().getTipo().ref() instanceof TipoNull)
			distinto.setTipo(new TipoBool());
		else {
			distinto.setTipo(new TipoError());
			System.out.println("Error: mayorIgual");
			throw new Exception();
		}
	}

	@Override
	public void procesa(MenorIgual menorIgual) throws Exception {
		menorIgual.arg0().procesa(this);
		menorIgual.arg1().procesa(this);
		if((menorIgual.arg0().getTipo().ref() instanceof TipoInt || menorIgual.arg0().getTipo().ref() instanceof TipoReal)&& (menorIgual.arg1().getTipo().ref() instanceof TipoInt || menorIgual.arg1().getTipo().ref() instanceof TipoReal))
			menorIgual.setTipo(new TipoBool());
		else if(menorIgual.arg0().getTipo().ref() instanceof TipoBool && menorIgual.arg1().getTipo().ref() instanceof TipoBool)
			menorIgual.setTipo(new TipoBool());
		else if(menorIgual.arg0().getTipo().ref() instanceof TipoString && menorIgual.arg1().getTipo().ref() instanceof TipoString)
			menorIgual.setTipo(new TipoBool());
		else {
			menorIgual.setTipo(new TipoError());
			System.out.println("Error: menorIgual");
			throw new Exception();
		}
	}

	@Override
	public void procesa(And and) throws Exception {
		and.arg0().procesa(this);
		and.arg1().procesa(this);
		if(and.arg0().getTipo().ref() instanceof TipoBool && and.arg1().getTipo().ref() instanceof TipoBool) {
			and.setTipo(new TipoInt());
		}
		else {
			and.setTipo(new TipoError());
			System.out.println("Error: and");
			throw new Exception();
		}
	}

	@Override
	public void procesa(Or or) throws Exception {
		or.arg0().procesa(this);
		or.arg1().procesa(this);
		if(or.arg0().getTipo().ref() instanceof TipoBool && or.arg1().getTipo().ref() instanceof TipoBool) {
			or.setTipo(new TipoInt());
		}
		else {
			or.setTipo(new TipoError());
			System.out.println("Error: or");
			throw new Exception();
		}
	}

	@Override
	public void procesa(Modulo modulo) throws Exception {
		modulo.arg0().procesa(this);
		modulo.arg1().procesa(this);
		if(modulo.arg0().getTipo().ref() instanceof TipoInt && modulo.arg1().getTipo().ref() instanceof TipoInt) {
			modulo.setTipo(new TipoInt());
		}
		else {
			modulo.setTipo(new TipoError());
			System.out.println("Error: modulo");
			throw new Exception();
		}
	}

	@Override
	public void procesa(Negativo negativo) throws Exception {
		negativo.e().procesa(this);
		if(negativo.e().getTipo().ref() instanceof TipoInt)
			negativo.setTipo(new TipoInt());
		else if(negativo.e().getTipo().ref() instanceof TipoReal)
			negativo.setTipo(new TipoReal());
		else {
			negativo.setTipo(new TipoError());
			System.out.println("Error: negativo");
			throw new Exception();
		}
		
	}

	@Override
	public void procesa(Not not) throws Exception {
		not.e().procesa(this);
		if(not.e().getTipo().ref() instanceof TipoBool)
			not.setTipo(new TipoBool());
		else {
			not.setTipo(new TipoError());
			System.out.println("Error: not");
			throw new Exception();
		}
	}

	@Override
	public void procesa(Acc acc) throws Exception {
		// TODO)
	}

	@Override
	public void procesa(Indx indx) throws Exception {
		indx.e1().procesa(this);
		indx.e2().procesa(this);
		if(indx.e1().getTipo().ref() instanceof TipoArray && indx.e2().getTipo().ref() instanceof TipoInt) {
			indx.setTipo(((TipoArray)indx.getTipo().ref()).tipo());
		}
		else {
			indx.setTipo(new TipoError());
			System.out.println("Error: indx");
			throw new Exception();
		}
	}

	@Override
	public void procesa(Dref dref) throws Exception {
		dref.e().procesa(this);
		if(dref.e().getTipo().ref() instanceof TipoPointer) {
			dref.setTipo(((TipoPointer)dref.e().getTipo().ref()).tipo());
		}
	}
	
	private Tipo ambos_ok(Tipo t0, Tipo t1) {
		if(t0 instanceof TipoOk && t1 instanceof TipoOk)
			return new TipoOk();
		else
			return new TipoError();
	}
	
	private boolean son_compatibles(Tipo t0, Tipo t1) {
		if(t0 instanceof TipoInt && t1 instanceof TipoInt)
			return true;
		else if(t0 instanceof TipoReal && (t1 instanceof TipoReal || t1 instanceof TipoReal))
			return true;
		else if(t0 instanceof TipoBool && t1 instanceof TipoBool)
			return true;
		else if(t0 instanceof TipoString && t1 instanceof TipoString)
			return true;
		else if(t0 instanceof TipoArray && t1 instanceof TipoArray) {
			return son_compatibles(((TipoArray) t0).tipo(), ((TipoArray) t1).tipo());
		}
		else if(t0 instanceof TipoRecord && t1 instanceof TipoRecord){
			TipoRecord tr0 = (TipoRecord) t0;
			TipoRecord tr1 = (TipoRecord) t1;
		
			// TODO
			return true;
		}
		else if(t0 instanceof TipoPointer && t1 instanceof TipoNull)
			return true;
		else if(t0 instanceof TipoPointer && t1 instanceof TipoPointer){
			return son_compatibles(((TipoPointer)t0).tipo(), ((TipoPointer)t1).tipo());
		}
		
		return false;
	}
	
	private Tipo chequeo_parametros(PReal_ninguno preals, PForm_vacia pfs) {
		return new TipoOk();
	}
	
	private Tipo chequeo_parametros(PReal_uno preals, PForm_una pfs) throws Exception {
		return chequeo_parametro(preals.preal(), pfs.pform());
	}
	
	private Tipo chequeo_parametros(PReal_muchos preals, PForm_muchas pfs) throws Exception {
		return ambos_ok(chequeo_parametros((PReal_muchos) preals.preals(), (PForm_muchas)pfs.pforms()), chequeo_parametro(preals.preal(), pfs.pform()));
	}
	
	private Tipo chequeo_parametro(PReal preal, PForm pform) throws Exception {
		preal.procesa(this);
		if(pform instanceof PFormRef) {
			if(preal.getEsDesig() && son_compatibles(pform.tipo(), preal.getTipo())) {
				return new TipoOk();
			}
			else {
				System.out.println("Error: chequeo param");
				throw new Exception();
			}
		}
		else {
			if(son_compatibles(pform.tipo(), preal.getTipo())) {
				return new TipoOk();
			}
			else {
				System.out.println("Error: chequeo param");
				throw new Exception();
			}
		}
	}
	
}

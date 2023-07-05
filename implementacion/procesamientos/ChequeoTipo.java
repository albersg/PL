package implementacion.procesamientos;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import implementacion.asint.ProcesamientoPorDefecto;
import implementacion.asint.TinyASint.Acc;
import implementacion.asint.TinyASint.And;
import implementacion.asint.TinyASint.Asig;
import implementacion.asint.TinyASint.Call;
import implementacion.asint.TinyASint.Campo;
import implementacion.asint.TinyASint.Campos;
import implementacion.asint.TinyASint.Campos_muchos;
import implementacion.asint.TinyASint.Campos_uno;
import implementacion.asint.TinyASint.Dec;
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
import implementacion.asint.TinyASint.PReal;
import implementacion.asint.TinyASint.PReal_muchos;
import implementacion.asint.TinyASint.PReal_ninguno;
import implementacion.asint.TinyASint.PReal_uno;
import implementacion.asint.TinyASint.Prog;
import implementacion.asint.TinyASint.Read;
import implementacion.asint.TinyASint.Resta;
import implementacion.asint.TinyASint.StringLocalizado;
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

public class ChequeoTipo extends ProcesamientoPorDefecto {

	private boolean error;
	private HashMap<Tipo, Set<Tipo>> tc;

	public ChequeoTipo() throws Exception {
		tc = new HashMap<>();
		error = false;
	}

	private void errorMsg(StringLocalizado id) {
		error = true;
		System.out.println("Error de tipos con id: \"" + id + "\".");
		System.out.println("Fila: " + id.fila() + " Columna: " + id.col());
	}

	public boolean failed() {
		return error;
	}

	private void agregar(Tipo tipo1, Tipo tipo2) {
		tc.computeIfAbsent(tipo1, k -> new HashSet<>()).add(tipo2);
		tc.computeIfAbsent(tipo2, k -> new HashSet<>()).add(tipo1);
	}

	private boolean sonCompatiblesTabla(Tipo tipo1, Tipo tipo2) {
		Set<Tipo> tiposCompatibles = tc.get(tipo1);
		return tiposCompatibles != null && tiposCompatibles.contains(tipo2);
	}

	@Override
	public void procesa(Decs_muchas decs) throws Exception {
		decs.decs().procesa(this);
		decs.dec().procesa(this);
		decs.setTipo(ambos_no_error(decs.dec().getTipo(), decs.decs().getTipo()));
	}

	@Override
	public void procesa(Decs_vacia dec) throws Exception {
		dec.setTipo(new TipoOk());
	}

	@Override
	public void procesa(Decs_una decs) throws Exception {
		decs.dec().procesa(this);
		decs.setTipo(decs.getTipo());
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
		decProc.setTipo(ambos_no_error(decProc.pf().getTipo(),
				ambos_no_error(decProc.decs().getTipo(), decProc.is().getTipo())));
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

		if (exp.arg0().getTipo().ref() instanceof TipoInt && exp.arg1().getTipo().ref() instanceof TipoInt) {
			exp.setTipo(new TipoInt());
		} else if (exp.arg0().getTipo().ref() instanceof TipoReal
				&& (exp.arg1().getTipo().ref() instanceof TipoReal || exp.arg1().getTipo().ref() instanceof TipoInt)) {
			exp.setTipo(new TipoReal());
		} else if (exp.arg1().getTipo().ref() instanceof TipoReal
				&& (exp.arg0().getTipo().ref() instanceof TipoReal || exp.arg0().getTipo().ref() instanceof TipoInt)) {
			exp.setTipo(new TipoReal());
		} else {
			exp.setTipo(new TipoError());
			System.out.print("div: ");
			errorMsg(exp.arg0().id());
		}
	}

	@Override
	public void procesa(Id exp) throws Exception {
		Dec vinculo = (Dec) exp.getVinculo();
		if (vinculo instanceof DecVar)
			exp.setTipo(((DecVar) vinculo).tipo());
		else if (vinculo instanceof PForm)
			exp.setTipo(((PForm) vinculo).tipo());
		else if (vinculo instanceof PFormRef)
			exp.setTipo(((PFormRef) vinculo).tipo());
		else {
			exp.setTipo(new TipoError());
			System.out.print("id: ");
			errorMsg(exp.id());
		}
	}

	@Override
	public void procesa(Mul exp) throws Exception {
		exp.arg0().procesa(this);
		exp.arg1().procesa(this);

		if (exp.arg0().getTipo().ref() instanceof TipoInt && exp.arg1().getTipo().ref() instanceof TipoInt) {
			exp.setTipo(new TipoInt());
		} else if (exp.arg0().getTipo().ref() instanceof TipoReal
				&& (exp.arg1().getTipo().ref() instanceof TipoReal || exp.arg1().getTipo().ref() instanceof TipoInt)) {
			exp.setTipo(new TipoReal());
		} else if (exp.arg1().getTipo().ref() instanceof TipoReal
				&& (exp.arg0().getTipo().ref() instanceof TipoReal || exp.arg0().getTipo().ref() instanceof TipoInt)) {
			exp.setTipo(new TipoReal());
		} else {
			exp.setTipo(new TipoError());
			System.out.print("mul: ");
			errorMsg(exp.arg0().id());
		}
	}

	@Override
	public void procesa(Resta exp) throws Exception {
		exp.arg0().procesa(this);
		exp.arg1().procesa(this);

		if (exp.arg0().getTipo().ref() instanceof TipoInt && exp.arg1().getTipo().ref() instanceof TipoInt) {
			exp.setTipo(new TipoInt());
		} else if (exp.arg0().getTipo().ref() instanceof TipoReal
				&& (exp.arg1().getTipo().ref() instanceof TipoReal || exp.arg1().getTipo().ref() instanceof TipoInt)) {
			exp.setTipo(new TipoReal());
		} else if (exp.arg1().getTipo().ref() instanceof TipoReal
				&& (exp.arg0().getTipo().ref() instanceof TipoReal || exp.arg0().getTipo().ref() instanceof TipoInt)) {
			exp.setTipo(new TipoReal());
		} else {
			exp.setTipo(new TipoError());
			System.out.print("resta: ");
			errorMsg(exp.arg0().id());
		}
	}

	@Override
	public void procesa(Suma exp) throws Exception {
		exp.arg0().procesa(this);
		exp.arg1().procesa(this);

		if (exp.arg0().getTipo().ref() instanceof TipoInt && exp.arg1().getTipo().ref() instanceof TipoInt) {
			exp.setTipo(new TipoInt());
		} else if (exp.arg0().getTipo().ref() instanceof TipoReal
				&& (exp.arg1().getTipo().ref() instanceof TipoReal || exp.arg1().getTipo().ref() instanceof TipoInt)) {
			exp.setTipo(new TipoReal());
		} else if (exp.arg1().getTipo().ref() instanceof TipoReal
				&& (exp.arg0().getTipo().ref() instanceof TipoReal || exp.arg0().getTipo().ref() instanceof TipoInt)) {
			exp.setTipo(new TipoReal());
		} else {
			exp.setTipo(new TipoError());
			System.out.print("suma: ");
			errorMsg(exp.arg0().id());
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
		if (tipoRef.getVinculo() instanceof DecTipo) {
			tipoRef.setTipo(new TipoOk());
		} else {
			tipoRef.setTipo(new TipoError());
			System.out.print("tipoRef: ");
			errorMsg(tipoRef.id());
		}
	}

	@Override
	public void procesa(TipoArray tipoArray) throws Exception {
		tipoArray.tipo().procesa(this);
		if (Integer.parseInt(tipoArray.id().toString()) >= 0)
			tipoArray.setTipo(tipoArray.tipo().getTipo());
		else {
			tipoArray.setTipo(new TipoError());
			System.out.print("tipoArray: ");
			errorMsg(tipoArray.id());
		}
	}

	@Override
	public void procesa(TipoRecord tipoRecord) throws Exception {
		tipoRecord.campos().procesa(this);
		tipoRecord.setTipo(tipoRecord.campos().getTipo());
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
	public void procesa(PForm pForm) throws Exception {
		pForm.tipo().procesa(this);
		pForm.setTipo(pForm.tipo().getTipo());
	}

	@Override
	public void procesa(PForm_una pForm_una) throws Exception {
		pForm_una.pform().procesa(this);
		pForm_una.setTipo(pForm_una.pform().getTipo());
	}

	@Override
	public void procesa(PForm_muchas pForm_muchas) throws Exception {
		pForm_muchas.pforms().procesa(this);
		pForm_muchas.pform().procesa(this);
		pForm_muchas.setTipo(ambos_no_error(pForm_muchas.pforms().getTipo(), pForm_muchas.pform().getTipo()));
	}

	@Override
	public void procesa(PForm_vacia pForm_vacia) throws Exception {
		pForm_vacia.setTipo(new TipoOk());
	}

	@Override
	public void procesa(PReal_uno pReal_uno) throws Exception {
		pReal_uno.preal().procesa(this);
		pReal_uno.setTipo(pReal_uno.preal().getTipo());
	}

	@Override
	public void procesa(PReal_muchos pReal_muchos) throws Exception {
		pReal_muchos.preals().procesa(this);
		pReal_muchos.preal().procesa(this);
		pReal_muchos.setTipo(ambos_no_error(pReal_muchos.preals().getTipo(), pReal_muchos.preal().getTipo()));
	}

	@Override
	public void procesa(PReal_ninguno pReal_vacia) throws Exception {
		pReal_vacia.setTipo(new TipoOk());
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
	public void procesa(Is_vacia is) throws Exception {
		is.setTipo(new TipoOk());
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
		campos_muchos.setTipo(ambos_no_error(campos_muchos.campos().getTipo(), campos_muchos.campo().getTipo()));
	}

	@Override
	public void procesa(Campos_uno campos_uno) throws Exception {
		campos_uno.campo().procesa(this);
		campos_uno.setTipo(campos_uno.campo().getTipo());
	}

	@Override
	public void procesa(Asig asig) throws Exception {
		asig.e1().procesa(this);
		asig.e2().procesa(this);
		if (asig.e1().getEsDesig() && son_compatibles(asig.e1().getTipo().ref(), asig.e2().getTipo().ref())) {
			asig.setTipo(new TipoOk());
		} else {
			asig.setTipo(new TipoError());
			System.out.print("asig: ");
			errorMsg(asig.e1().id());
		}
	}

	@Override
	public void procesa(If_then if_then) throws Exception {
		if_then.e().procesa(this);
		if (if_then.e().getTipo().ref() instanceof TipoBool) {
			if_then.is().procesa(this);
			if_then.setTipo(if_then.is().getTipo());
		} else {
			if_then.setTipo(new TipoError());
			System.out.print("if_then: ");
			errorMsg(if_then.e().id());
		}
	}

	@Override
	public void procesa(If_then_else if_then_else) throws Exception {
		if_then_else.e().procesa(this);
		if (if_then_else.e().getTipo().ref() instanceof TipoBool) {
			if_then_else.is1().procesa(this);
			if_then_else.is2().procesa(this);
			if_then_else.setTipo(ambos_ok(if_then_else.is1().getTipo(), if_then_else.is2().getTipo()));
		} else {
			if_then_else.setTipo(new TipoError());
			System.out.print("if_then_else: ");
			errorMsg(if_then_else.e().id());
		}

	}

	@Override
	public void procesa(While while1) throws Exception {
		while1.e().procesa(this);
		if (while1.e().getTipo().ref() instanceof TipoBool) {
			while1.is().procesa(this);
			while1.setTipo(while1.is().getTipo());
		} else {
			while1.setTipo(new TipoError());
			System.out.print("while: ");
			errorMsg(while1.e().id());
		}

	}

	@Override
	public void procesa(Read read) throws Exception {
		read.e().procesa(this);
		Exp e = read.e();
		if ((e.getTipo().ref() instanceof TipoInt || e.getTipo().ref() instanceof TipoReal
				|| e.getTipo().ref() instanceof TipoString) && e.getEsDesig()) {
			read.setTipo(new TipoOk());
		} else {
			read.setTipo(new TipoError());
			System.out.print("read: ");
			errorMsg(read.e().id());
		}
	}

	@Override
	public void procesa(Write write) throws Exception {
		write.e().procesa(this);
		Exp e = write.e();
		if ((e.getTipo().ref() instanceof TipoInt || e.getTipo().ref() instanceof TipoReal
				|| e.getTipo().ref() instanceof TipoString) || e.getTipo().ref() instanceof TipoBool) {
			write.setTipo(new TipoOk());
		} else {
			write.setTipo(new TipoError());
			System.out.print("read: ");
			errorMsg(write.e().id());
		}
	}

	@Override
	public void procesa(Nl nl) throws Exception {
		nl.setTipo(new TipoOk());
	}

	@Override
	public void procesa(New new1) throws Exception {
		new1.e().procesa(this);
		if (new1.e().getTipo().ref() instanceof TipoPointer) {
			new1.setTipo(new TipoOk());
		} else {
			new1.setTipo(new TipoError());
			System.out.print("new: ");
			errorMsg(new1.e().id());
		}
	}

	@Override
	public void procesa(Delete delete) throws Exception {
		delete.e().procesa(this);
		if (delete.e().getTipo().ref() instanceof TipoPointer) {
			delete.setTipo(new TipoOk());
		} else {
			delete.setTipo(new TipoError());
			System.out.print("delete: ");
			errorMsg(delete.e().id());
		}
	}

	@Override
	public void procesa(Call call) throws Exception {
		if (call.getVinculo() instanceof DecProc) {
			DecProc dp = (DecProc) call.getVinculo();
			if (call.preals().nElem() != dp.pf().nElem()) {
				call.setTipo(new TipoError());
				System.out.print("call: ");
				errorMsg(call.e().id());
			} else {
				Tipo tipoRet = null;
				if (call.preals() instanceof PReal_ninguno)
					tipoRet = chequeo_parametros((PReal_ninguno) call.preals(), (PForm_vacia) dp.pf());
				else if (call.preals() instanceof PReal_uno)
					tipoRet = chequeo_parametros((PReal_uno) call.preals(), (PForm_una) dp.pf());
				else
					tipoRet = chequeo_parametros((PReal_muchos) call.preals(), (PForm_muchas) dp.pf());
				call.setTipo(tipoRet);
			}
		} else {
			call.setTipo(new TipoError());
			System.out.print("call: ");
			errorMsg(call.e().id());
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
		if ((menor.arg0().getTipo().ref() instanceof TipoInt || menor.arg0().getTipo().ref() instanceof TipoReal)
				&& (menor.arg1().getTipo().ref() instanceof TipoInt
						|| menor.arg1().getTipo().ref() instanceof TipoReal))
			menor.setTipo(new TipoBool());
		else if (menor.arg0().getTipo().ref() instanceof TipoBool && menor.arg1().getTipo().ref() instanceof TipoBool)
			menor.setTipo(new TipoBool());
		else if (menor.arg0().getTipo().ref() instanceof TipoString
				&& menor.arg1().getTipo().ref() instanceof TipoString)
			menor.setTipo(new TipoBool());
		else {
			menor.setTipo(new TipoError());
			System.out.print("menor: ");
			errorMsg(menor.arg0().id());
		}
	}

	@Override
	public void procesa(Mayor mayor) throws Exception {
		mayor.arg0().procesa(this);
		mayor.arg1().procesa(this);
		if ((mayor.arg0().getTipo().ref() instanceof TipoInt || mayor.arg0().getTipo().ref() instanceof TipoReal)
				&& (mayor.arg1().getTipo().ref() instanceof TipoInt
						|| mayor.arg1().getTipo().ref() instanceof TipoReal))
			mayor.setTipo(new TipoBool());
		else if (mayor.arg0().getTipo().ref() instanceof TipoBool && mayor.arg1().getTipo().ref() instanceof TipoBool)
			mayor.setTipo(new TipoBool());
		else if (mayor.arg0().getTipo().ref() instanceof TipoString
				&& mayor.arg1().getTipo().ref() instanceof TipoString)
			mayor.setTipo(new TipoBool());
		else {
			mayor.setTipo(new TipoError());
			System.out.print("mayor: ");
			errorMsg(mayor.arg0().id());
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
		if ((mayorIgual.arg0().getTipo().ref() instanceof TipoInt
				|| mayorIgual.arg0().getTipo().ref() instanceof TipoReal)
				&& (mayorIgual.arg1().getTipo().ref() instanceof TipoInt
						|| mayorIgual.arg1().getTipo().ref() instanceof TipoReal))
			mayorIgual.setTipo(new TipoBool());
		else if (mayorIgual.arg0().getTipo().ref() instanceof TipoBool
				&& mayorIgual.arg1().getTipo().ref() instanceof TipoBool)
			mayorIgual.setTipo(new TipoBool());
		else if (mayorIgual.arg0().getTipo().ref() instanceof TipoString
				&& mayorIgual.arg1().getTipo().ref() instanceof TipoString)
			mayorIgual.setTipo(new TipoBool());
		else {
			mayorIgual.setTipo(new TipoError());
			System.out.print("mayorIgual: ");
			errorMsg(mayorIgual.arg0().id());
		}
	}

	@Override
	public void procesa(Igual igual) throws Exception {
		igual.arg0().procesa(this);
		igual.arg1().procesa(this);
		if ((igual.arg0().getTipo().ref() instanceof TipoInt || igual.arg0().getTipo().ref() instanceof TipoReal)
				&& (igual.arg1().getTipo().ref() instanceof TipoInt
						|| igual.arg1().getTipo().ref() instanceof TipoReal))
			igual.setTipo(new TipoBool());
		else if (igual.arg0().getTipo().ref() instanceof TipoBool && igual.arg1().getTipo().ref() instanceof TipoBool)
			igual.setTipo(new TipoBool());
		else if (igual.arg0().getTipo().ref() instanceof TipoString
				&& igual.arg1().getTipo().ref() instanceof TipoString)
			igual.setTipo(new TipoBool());
		else if (igual.arg0().getTipo().ref() instanceof TipoPointer
				&& igual.arg1().getTipo().ref() instanceof TipoPointer)
			igual.setTipo(new TipoBool());
		else if (igual.arg0().getTipo().ref() instanceof TipoPointer
				&& igual.arg1().getTipo().ref() instanceof TipoNull)
			igual.setTipo(new TipoBool());
		else if (igual.arg0().getTipo().ref() instanceof TipoNull
				&& igual.arg1().getTipo().ref() instanceof TipoPointer)
			igual.setTipo(new TipoBool());
		else if (igual.arg0().getTipo().ref() instanceof TipoNull && igual.arg1().getTipo().ref() instanceof TipoNull)
			igual.setTipo(new TipoBool());
		else {
			igual.setTipo(new TipoError());
			System.out.print("igual: ");
			errorMsg(igual.arg0().id());
		}
	}

	@Override
	public void procesa(Distinto distinto) throws Exception {
		distinto.arg0().procesa(this);
		distinto.arg1().procesa(this);
		if ((distinto.arg0().getTipo().ref() instanceof TipoInt || distinto.arg0().getTipo().ref() instanceof TipoReal)
				&& (distinto.arg1().getTipo().ref() instanceof TipoInt
						|| distinto.arg1().getTipo().ref() instanceof TipoReal))
			distinto.setTipo(new TipoBool());
		else if (distinto.arg0().getTipo().ref() instanceof TipoBool
				&& distinto.arg1().getTipo().ref() instanceof TipoBool)
			distinto.setTipo(new TipoBool());
		else if (distinto.arg0().getTipo().ref() instanceof TipoString
				&& distinto.arg1().getTipo().ref() instanceof TipoString)
			distinto.setTipo(new TipoBool());
		else if (distinto.arg0().getTipo().ref() instanceof TipoPointer
				&& distinto.arg1().getTipo().ref() instanceof TipoPointer)
			distinto.setTipo(new TipoBool());
		else if (distinto.arg0().getTipo().ref() instanceof TipoPointer
				&& distinto.arg1().getTipo().ref() instanceof TipoNull)
			distinto.setTipo(new TipoBool());
		else if (distinto.arg0().getTipo().ref() instanceof TipoNull
				&& distinto.arg1().getTipo().ref() instanceof TipoPointer)
			distinto.setTipo(new TipoBool());
		else if (distinto.arg0().getTipo().ref() instanceof TipoNull
				&& distinto.arg1().getTipo().ref() instanceof TipoNull)
			distinto.setTipo(new TipoBool());
		else {
			distinto.setTipo(new TipoError());
			System.out.print("distinto: ");
			errorMsg(distinto.arg0().id());

		}
	}

	@Override
	public void procesa(MenorIgual menorIgual) throws Exception {
		menorIgual.arg0().procesa(this);
		menorIgual.arg1().procesa(this);
		if ((menorIgual.arg0().getTipo().ref() instanceof TipoInt
				|| menorIgual.arg0().getTipo().ref() instanceof TipoReal)
				&& (menorIgual.arg1().getTipo().ref() instanceof TipoInt
						|| menorIgual.arg1().getTipo().ref() instanceof TipoReal))
			menorIgual.setTipo(new TipoBool());
		else if (menorIgual.arg0().getTipo().ref() instanceof TipoBool
				&& menorIgual.arg1().getTipo().ref() instanceof TipoBool)
			menorIgual.setTipo(new TipoBool());
		else if (menorIgual.arg0().getTipo().ref() instanceof TipoString
				&& menorIgual.arg1().getTipo().ref() instanceof TipoString)
			menorIgual.setTipo(new TipoBool());
		else {
			menorIgual.setTipo(new TipoError());
			System.out.print("menorIgual: ");
			errorMsg(menorIgual.arg0().id());

		}
	}

	@Override
	public void procesa(And and) throws Exception {
		and.arg0().procesa(this);
		and.arg1().procesa(this);
		if (and.arg0().getTipo().ref() instanceof TipoBool && and.arg1().getTipo().ref() instanceof TipoBool) {
			and.setTipo(new TipoBool());
		} else {
			and.setTipo(new TipoError());
			System.out.print("and: ");
			errorMsg(and.arg0().id());
		}
	}

	@Override
	public void procesa(Or or) throws Exception {
		or.arg0().procesa(this);
		or.arg1().procesa(this);
		if (or.arg0().getTipo().ref() instanceof TipoBool && or.arg1().getTipo().ref() instanceof TipoBool) {
			or.setTipo(new TipoBool());
		} else {
			or.setTipo(new TipoError());
			System.out.print("or: ");
			errorMsg(or.arg0().id());
		}
	}

	@Override
	public void procesa(Modulo modulo) throws Exception {
		modulo.arg0().procesa(this);
		modulo.arg1().procesa(this);
		if (modulo.arg0().getTipo().ref() instanceof TipoInt && modulo.arg1().getTipo().ref() instanceof TipoInt) {
			modulo.setTipo(new TipoInt());
		} else {
			modulo.setTipo(new TipoError());
			System.out.print("modulo: ");
			errorMsg(modulo.arg0().id());
		}
	}

	@Override
	public void procesa(Negativo negativo) throws Exception {
		negativo.e().procesa(this);
		if (negativo.e().getTipo().ref() instanceof TipoInt)
			negativo.setTipo(new TipoInt());
		else if (negativo.e().getTipo().ref() instanceof TipoReal)
			negativo.setTipo(new TipoReal());
		else {
			negativo.setTipo(new TipoError());
			System.out.print("negativo: ");
			errorMsg(negativo.e().id());

		}

	}

	@Override
	public void procesa(Not not) throws Exception {
		not.e().procesa(this);
		if (not.e().getTipo().ref() instanceof TipoBool)
			not.setTipo(new TipoBool());
		else {
			not.setTipo(new TipoError());
			System.out.print("not: ");
			errorMsg(not.e().id());

		}
	}

	@Override
	public void procesa(Acc acc) throws Exception {
		acc.e().procesa(this);
		Tipo tipo = acc.e().getTipo().ref();
		if (tipo instanceof TipoRecord) {
			Campos campos = ((TipoRecord) tipo).campos();
			if (campos.existeCampo(acc.s())) {
				acc.setTipo(campos.tipoDeCampo(acc.s()));
			} else {
				acc.setTipo(new TipoError());
				System.out.print("acc: ");
				errorMsg(acc.e().id());

			}
		} else {
			acc.setTipo(new TipoError());
			System.out.print("acc: ");
			errorMsg(acc.e().id());

		}
	}

	@Override
	public void procesa(Indx indx) throws Exception {
		indx.e1().procesa(this);
		indx.e2().procesa(this);
		if (indx.e1().getTipo().ref() instanceof TipoArray && indx.e2().getTipo().ref() instanceof TipoInt) {
			indx.setTipo(((TipoArray) indx.e1().getTipo().ref()).tipo());
		} else {
			indx.setTipo(new TipoError());
			System.out.print("indx: ");
			errorMsg(indx.e1().id());
		}
	}

	public void procesa(Dref dref) throws Exception {
		dref.e().procesa(this);
		if (dref.e().getTipo().ref() instanceof TipoPointer) {
			Tipo aux = ((TipoPointer) dref.e().getTipo().ref()).tipo();
			dref.setTipo(aux);

		} else {
			dref.setTipo(new TipoError());
			System.out.print("dref: ");
			errorMsg(dref.e().id());

		}
	}

	private Tipo ambos_ok(Tipo t0, Tipo t1) {
		if (t0 instanceof TipoOk && t1 instanceof TipoOk)
			return new TipoOk();
		else
			return new TipoError();
	}

	private Tipo ambos_no_error(Tipo t0, Tipo t1) {
		if (!(t0 instanceof TipoError) && !(t1 instanceof TipoError))
			return new TipoOk();
		else
			return new TipoError();
	}

	private boolean son_compatibles(Tipo t0, Tipo t1) {
		// Comprobamos si ya están en la tabla, o si ambos tipos referencian al mismo
		// objeto, y por tanto compatibles, ya que un tipo sería compatible consigo
		// mismo
		if (sonCompatiblesTabla(t0, t1) || t0 == t1)
			return true;
		boolean ret = false;
		if (t0 instanceof TipoInt && t1 instanceof TipoInt) {
			agregar(t0, t1);
			return true;
		} else if (t0 instanceof TipoReal && (t1 instanceof TipoInt || t1 instanceof TipoReal)) {
			agregar(t0, t1);
			return true;
		} else if (t0 instanceof TipoBool && t1 instanceof TipoBool) {
			agregar(t0, t1);
			return true;
		} else if (t0 instanceof TipoString && t1 instanceof TipoString) {
			agregar(t0, t1);
			return true;
		} else if (t0 instanceof TipoArray && t1 instanceof TipoArray) {
			ret = son_compatibles(((TipoArray) t0).tipo(), ((TipoArray) t1).tipo());
			if (ret) {
				agregar(t0, t1);
				return true;
			} else
				return false;
		} else if (t0 instanceof TipoRecord && t1 instanceof TipoRecord) {
			Campos c0 = ((TipoRecord) t0).campos();
			Campos c1 = ((TipoRecord) t1).campos();

			if (c0.nElem() == c1.nElem()) {
				while (!(c0 instanceof Campos_uno)) {
					ret = son_compatibles(((Campos_muchos) c0).campo().tipo().ref(),
							((Campos_muchos) c1).campo().tipo().ref());
					if (ret) {
						agregar(((Campos_muchos) c0).campo().tipo().ref(), ((Campos_muchos) c1).campo().tipo().ref());
					} else
						return false;
					c0 = ((Campos_muchos) c0).campos();
					c1 = ((Campos_muchos) c1).campos();
				}
				ret = son_compatibles(((Campos_uno) c0).campo().tipo().ref(), ((Campos_uno) c1).campo().tipo().ref());
				if (ret) {
					agregar(((Campos_uno) c0).campo().tipo().ref(), ((Campos_uno) c1).campo().tipo().ref());
					return true;
				} else
					return false;
			}

			return false;
		} else if (t0 instanceof TipoPointer && t1 instanceof TipoNull) {
			agregar(t0, t1);
			return true;
		} else if (t0 instanceof TipoPointer && t1 instanceof TipoPointer) {
			ret = son_compatibles(((TipoPointer) t0).tipo().ref(), ((TipoPointer) t1).tipo().ref());
			if (ret) {
				agregar(((TipoPointer) t0).tipo().ref(), ((TipoPointer) t1).tipo().ref());
				return true;
			} else
				return false;
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
		if (preals.preals() instanceof PReal_muchos) {
			return ambos_no_error(chequeo_parametros((PReal_muchos) preals.preals(), (PForm_muchas) pfs.pforms()),
					chequeo_parametro(preals.preal(), pfs.pform()));
		} else if (preals.preals() instanceof PReal_uno) {
			return ambos_no_error(chequeo_parametros((PReal_uno) preals.preals(), (PForm_una) pfs.pforms()),
					chequeo_parametro(preals.preal(), pfs.pform()));
		} else {
			return chequeo_parametro(preals.preal(), pfs.pform());
		}
	}

	private Tipo chequeo_parametro(PReal preal, PForm pform) throws Exception {
		preal.e().procesa(this);
		if (pform instanceof PFormRef) {
			if (preal.e().getEsDesig() && son_compatibles(pform.tipo().ref(), preal.e().getTipo().ref())) {
				return new TipoOk();
			} else {
				System.out.print("chequeo param: ");
				errorMsg(preal.e().id());
				return new TipoError();
			}
		} else {
			if (son_compatibles(pform.tipo().ref(), preal.e().getTipo().ref())) {
				return new TipoOk();
			} else {
				System.out.print("chequeo param: ");
				errorMsg(preal.e().id());
				return new TipoError();
			}
		}
	}

}

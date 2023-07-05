package implementacion.procesamientos;

import java.util.HashMap;
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
import implementacion.asint.TinyASint.PReal_uno;
import implementacion.asint.TinyASint.PReals;
import implementacion.asint.TinyASint.Prog;
import implementacion.asint.TinyASint.Read;
import implementacion.asint.TinyASint.Resta;
import implementacion.asint.TinyASint.StringLocalizado;
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

public class Vinculacion extends ProcesamientoPorDefecto {
	private boolean error;

	private Stack<HashMap<StringLocalizado, Dec>> ts;

	public Vinculacion() throws Exception {
		ts = new Stack<HashMap<StringLocalizado, Dec>>();
		ts.add(new HashMap<StringLocalizado, Dec>());
		error = false;
	}

	private void errorMsg(StringLocalizado id) {
		error = true;
		System.out.print("identificador \"" + id + "\" no existente.");
		System.out.println("Fila: " + id.fila() + " Columna: " + id.col());
	}

	public boolean failed() {
		return error;
	}

	private boolean existe_id(StringLocalizado id) throws Exception {
		for (HashMap<StringLocalizado, Dec> hm : ts) {
			if (hm.containsKey(id)) {
				return true;
			}
		}
		return false;
	}

	private Dec valorDe(StringLocalizado id) throws Exception {
		for (HashMap<StringLocalizado, Dec> hm : ts) {
			if (hm.containsKey(id)) {
				return hm.get(id);
			}
		}
		return null;
	}

	private void recolecta(StringLocalizado id, Dec dec) throws Exception {
		if (id_duplicado(id)) {
			error = true;
			System.out.print("Error: identificador \"" + id + "\" duplicado");
			System.out.println("Fila: " + id.fila() + " Columna: " + id.col());
		} else
			ts.peek().put(id, dec);
	}

	private boolean id_duplicado(StringLocalizado id) throws Exception {
		return ts.peek().containsKey(id);
	}

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
	public void procesa(Div exp) throws Exception {
		exp.arg0().procesa(this);
		exp.arg1().procesa(this);
	}

	@Override
	public void procesa(Id exp) throws Exception {
		if (existe_id(exp.s()))
			exp.setVinculo(valorDe(exp.s()));
		else {
			System.out.print("id: ");
			errorMsg(exp.s());
		}
	}

	@Override
	public void procesa(Mul exp) throws Exception {
		exp.arg0().procesa(this);
		exp.arg1().procesa(this);
	}

	@Override
	public void procesa(Resta exp) throws Exception {
		exp.arg0().procesa(this);
		exp.arg1().procesa(this);
	}

	@Override
	public void procesa(Suma exp) throws Exception {
		exp.arg0().procesa(this);
		exp.arg1().procesa(this);
	}

	@Override
	public void procesa(Prog prog) throws Exception {
		prog.decs().procesa(this);
		prog.decs().vincula2(this);
		prog.is().procesa(this);
	}

	@Override
	public void procesa(TipoRef tipoRef) throws Exception {
		if (existe_id(tipoRef.id()))
			tipoRef.setVinculo(valorDe(tipoRef.id()));
		else {
			System.out.print("tipoRef: ");
			errorMsg(tipoRef.id());
		}
	}

	@Override
	public void procesa(TipoArray tipoArray) throws Exception {
		tipoArray.tipo().procesa(this);
	}

	@Override
	public void procesa(TipoRecord tipoRecord) throws Exception {
		tipoRecord.campos().procesa(this);
	}

	@Override
	public void procesa(TipoPointer tipoPointer) throws Exception {
		if (!(tipoPointer.tipo() instanceof TipoRef))
			tipoPointer.tipo().procesa(this);
	}

	@Override
	public void procesa(PFormRef pFormRef) throws Exception {
		pFormRef.tipo().procesa(this);
		recolecta(pFormRef.id(), pFormRef);
	}

	@Override
	public void procesa(Is_vacia is_vacia) throws Exception {

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
	public void procesa(Campo campo) throws Exception {
		campo.tipo().procesa(this);
	}

	@Override
	public void procesa(Campos_muchos campos_muchos) throws Exception {
		campos_muchos.campos().procesa(this);
		campos_muchos.campo().procesa(this);
	}

	@Override
	public void procesa(Campos_uno campos_uno) throws Exception {
		campos_uno.campo().procesa(this);
	}

	@Override
	public void procesa(Asig asig) throws Exception {
		asig.e1().procesa(this);
		asig.e2().procesa(this);
	}

	@Override
	public void procesa(If_then if_then) throws Exception {
		if_then.e().procesa(this);
		if_then.is().procesa(this);
	}

	@Override
	public void procesa(If_then_else if_then_else) throws Exception {
		if_then_else.e().procesa(this);
		if_then_else.is1().procesa(this);
		if_then_else.is2().procesa(this);
	}

	@Override
	public void procesa(While while1) throws Exception {
		while1.e().procesa(this);
		while1.is().procesa(this);
	}

	@Override
	public void procesa(Read read) throws Exception {
		read.e().procesa(this);
	}

	@Override
	public void procesa(Write write) throws Exception {
		write.e().procesa(this);
	}

	@Override
	public void procesa(Nl nl) throws Exception {

	}

	@Override
	public void procesa(New new1) throws Exception {
		new1.e().procesa(this);
	}

	@Override
	public void procesa(Delete delete) throws Exception {
		delete.e().procesa(this);
	}

	@Override
	public void procesa(Call call) throws Exception {
		if (call.e() instanceof Id) {
			StringLocalizado id = call.e().id();
			if (existe_id(id)) {
				call.setVinculo(valorDe(id));
				call.preals().procesa(this);
			} else {
				System.out.print("call: ");
				errorMsg(id);
			}
		} else {
			System.out.print("call: ");
			errorMsg(call.e().id());
		}

	}

	@Override
	public void procesa(IComp iComp) throws Exception {
		ts.add(new HashMap<StringLocalizado, Dec>());
		iComp.decs().procesa(this);
		iComp.decs().vincula2(this);
		iComp.is().procesa(this);
		ts.pop();
	}

	@Override
	public void procesa(PReals pReals) throws Exception {

	}

	@Override
	public void procesa(LitInt litInt) throws Exception {

	}

	@Override
	public void procesa(LitReal litReal) throws Exception {

	}

	@Override
	public void procesa(LitStr litStr) throws Exception {

	}

	@Override
	public void procesa(True true1) throws Exception {

	}

	@Override
	public void procesa(Null null1) throws Exception {

	}

	@Override
	public void procesa(Menor menor) throws Exception {
		menor.arg0().procesa(this);
		menor.arg1().procesa(this);
	}

	@Override
	public void procesa(Mayor mayor) throws Exception {
		mayor.arg0().procesa(this);
		mayor.arg1().procesa(this);
	}

	@Override
	public void procesa(False false1) throws Exception {

	}

	@Override
	public void procesa(MayorIgual mayorIgual) throws Exception {
		mayorIgual.arg0().procesa(this);
		mayorIgual.arg1().procesa(this);
	}

	@Override
	public void procesa(Igual igual) throws Exception {
		igual.arg0().procesa(this);
		igual.arg1().procesa(this);
	}

	@Override
	public void procesa(Distinto distinto) throws Exception {
		distinto.arg0().procesa(this);
		distinto.arg1().procesa(this);
	}

	@Override
	public void procesa(MenorIgual menorIgual) throws Exception {
		menorIgual.arg0().procesa(this);
		menorIgual.arg1().procesa(this);
	}

	@Override
	public void procesa(And and) throws Exception {
		and.arg0().procesa(this);
		and.arg1().procesa(this);
	}

	@Override
	public void procesa(Or or) throws Exception {
		or.arg0().procesa(this);
		or.arg1().procesa(this);
	}

	@Override
	public void procesa(Modulo modulo) throws Exception {
		modulo.arg0().procesa(this);
		modulo.arg1().procesa(this);
	}

	@Override
	public void procesa(Negativo negativo) throws Exception {
		negativo.e().procesa(this);
	}

	@Override
	public void procesa(Not not) throws Exception {
		not.e().procesa(this);
	}

	@Override
	public void procesa(Acc acc) throws Exception {
		acc.e().procesa(this);
	}

	@Override
	public void procesa(Indx indx) throws Exception {
		indx.e1().procesa(this);
		indx.e2().procesa(this);
	}

	@Override
	public void procesa(Dref dref) throws Exception {
		dref.e().procesa(this);
	}

	@Override
	public void procesa(Decs_vacia dec_vacia) throws Exception {

	}

	@Override
	public void procesa(PForm pForm) throws Exception {
		pForm.tipo().procesa(this);
		recolecta(pForm.id(), pForm);
	}

	@Override
	public void procesa(DecVar decVar) throws Exception {
		decVar.tipo().procesa(this);
		recolecta(decVar.id(), decVar);
	}

	@Override
	public void procesa(DecTipo decTipo) throws Exception {
		decTipo.tipo().procesa(this);
		recolecta(decTipo.id(), decTipo);
	}

	@Override
	public void procesa(DecProc decProc) throws Exception {
		recolecta(decProc.id(), decProc);
		ts.add(new HashMap<StringLocalizado, Dec>());
		decProc.pf().procesa(this);
		decProc.decs().procesa(this);
		decProc.pf().vincula2(this);
		decProc.decs().vincula2(this);
		decProc.is().procesa(this);
		ts.remove(ts.size() - 1);
	}

	@Override
	public void procesa(PForm_una pForm_una) throws Exception {
		pForm_una.pform().procesa(this);
	}

	@Override
	public void procesa(PForm_muchas pForm_muchas) throws Exception {
		pForm_muchas.pforms().procesa(this);
		pForm_muchas.pform().procesa(this);
	}

	@Override
	public void procesa(PReal_uno pReal_uno) throws Exception {
		pReal_uno.preal().procesa(this);
	}

	@Override
	public void procesa(PReal_muchos pReal_muchos) throws Exception {
		pReal_muchos.preals().procesa(this);
		pReal_muchos.preal().procesa(this);
	}

	@Override
	public void procesa(PReal preal) throws Exception {
		preal.e().procesa(this);
	}

	@Override
	public void vincula2(IComp iComp) throws Exception {
		iComp.decs().vincula2(this);
	}

	@Override
	public void vincula2(DecProc decProc) throws Exception {

	}

	@Override
	public void vincula2(DecTipo decTipo) throws Exception {
		decTipo.tipo().vincula2(this);
	}

	@Override
	public void vincula2(DecVar decVar) throws Exception {
		decVar.tipo().vincula2(this);
	}

	@Override
	public void vincula2(Decs_una decs_una) throws Exception {
		decs_una.dec().vincula2(this);
	}

	@Override
	public void vincula2(Decs_muchas decs_muchas) throws Exception {
		decs_muchas.decs().vincula2(this);
		decs_muchas.dec().vincula2(this);
	}

	@Override
	public void vincula2(Decs_vacia decs_vacia) throws Exception {

	}

	@Override
	public void vincula2(PFormRef pFormRef) throws Exception {
		pFormRef.tipo().vincula2(this);
	}

	@Override
	public void vincula2(TipoRef tipoRef) throws Exception {

	}

	@Override
	public void vincula2(TipoInt tipoInt) throws Exception {

	}

	@Override
	public void vincula2(TipoReal tipoReal) throws Exception {

	}

	@Override
	public void vincula2(TipoBool tipoBool) throws Exception {

	}

	@Override
	public void vincula2(TipoString tipoString) throws Exception {

	}

	@Override
	public void vincula2(TipoArray tipoArray) throws Exception {
		tipoArray.tipo().vincula2(this);
	}

	@Override
	public void vincula2(TipoRecord tipoRecord) throws Exception {
		tipoRecord.campos().vincula2(this);
	}

	@Override
	public void vincula2(TipoPointer tipoPointer) throws Exception {
		if (tipoPointer.tipo() instanceof TipoRef)
			tipoPointer.tipo().procesa(this);
		else
			tipoPointer.tipo().vincula2(this);
	}

	@Override
	public void vincula2(Campos_uno campos_uno) throws Exception {
		campos_uno.campo().vincula2(this);
	}

	@Override
	public void vincula2(Campos_muchos campos_muchos) throws Exception {
		campos_muchos.campos().vincula2(this);
		campos_muchos.campo().vincula2(this);
	}

	@Override
	public void vincula2(Campo campo) throws Exception {
		campo.tipo().vincula2(this);
	}

	@Override
	public void vincula2(PForm_una pForm_una) throws Exception {
		pForm_una.pform().vincula2(this);
	}

	@Override
	public void vincula2(PForm_muchas pForm_muchas) throws Exception {
		pForm_muchas.pforms().vincula2(this);
		pForm_muchas.pform().vincula2(this);
	}

	@Override
	public void vincula2(PForm_vacia pForm_vacia) throws Exception {

	}

}

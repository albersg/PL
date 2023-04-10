package implementacion.procesamientos;

import java.util.HashMap;
import java.util.Stack;

import implementacion.asint.ProcesamientoPorDefecto;
import implementacion.asint.TinyASint.*;

public class Vinculacion extends ProcesamientoPorDefecto{

	private Stack<HashMap<StringLocalizado, Nodo>> ts;

	public Vinculacion() {
		ts = new Stack<HashMap<StringLocalizado, Nodo>>();
		ts.add(new HashMap<StringLocalizado, Nodo>());
	}
	
	private boolean existe_id(StringLocalizado id) {
	    for (HashMap<StringLocalizado, Nodo> hm : ts) {
	        if (hm.containsKey(id)) {
	            return true;
	        }
	    }
	    return false;
	}
	
	private Nodo valorDe(StringLocalizado id) {
	    for (HashMap<StringLocalizado, Nodo> hm : ts) {
	        if (hm.containsKey(id)) {
	            return hm.get(id);
	        }
	    }
	    return null;
	}
	
	private void recolecta(StringLocalizado id, Dec dec) {
		if(id_duplicado(id))
			System.out.println("Error: id duplicado");
		else
			ts.peek().put(id, new Nodo(dec));
	}
	
	private boolean id_duplicado(StringLocalizado id) {
		if(ts.peek().containsKey(id))
			return true;
		return false;
	}


	@Override
	public void procesa(Decs_muchas decs) {
		decs.decs().procesa(this);
		decs.dec().procesa(this);
	}

	@Override
	public void procesa(Decs_una decs) {
		decs.dec().procesa(this);
	}

	@Override
	public void procesa(Div exp) {
		exp.arg0().procesa(this);
		exp.arg1().procesa(this);
	}

	@Override
	public void procesa(Id exp) {
		if(existe_id(exp.s())) 
			exp.setVinculo(valorDe(exp.s()));
		else
			System.out.println("Error: id");
	}

	@Override
	public void procesa(Mul exp) {
		exp.arg0().procesa(this);
		exp.arg1().procesa(this);
	}

	@Override
	public void procesa(Resta exp) {
		exp.arg0().procesa(this);
		exp.arg1().procesa(this);
	}

	@Override
	public void procesa(Suma exp) {
		exp.arg0().procesa(this);
		exp.arg1().procesa(this);
	}

	@Override
	public void procesa(Prog prog) {
		prog.decs().procesa(this);
		prog.decs().vincula2(this);
		prog.is().procesa(this);
	}


	@Override
	public void procesa(TipoRef tipoRef) {
		if(existe_id(tipoRef.id())) 
			tipoRef.setVinculo(valorDe(tipoRef.id()));
		else
			System.out.println("Error: tipoRef");
	}

	@Override
	public void procesa(TipoArray tipoArray) {
		tipoArray.tipo().procesa(this);
	}

	@Override
	public void procesa(TipoRecord tipoRecord) {
		tipoRecord.campos().procesa(this);
	}

	@Override
	public void procesa(TipoPointer tipoPointer) {
		if(!tipoPointer.tipo().esRef())
			tipoPointer.tipo().procesa(this);
	}

	@Override
	public void procesa(PFormRef pFormRef) {
		pFormRef.tipo().procesa(this);
		recolecta(pFormRef.id(), pFormRef);
	}

	@Override
	public void procesa(Is_vacia is_vacia) {

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
	public void procesa(Campo campo) {
		campo.tipo().procesa(this);
	}

	@Override
	public void procesa(Campos_muchos campos_muchos) {
		campos_muchos.campos().procesa(this);
		campos_muchos.campo().procesa(this);
	}

	@Override
	public void procesa(Campos_uno campos_uno) {
		campos_uno.campo().procesa(this);
	}

	@Override
	public void procesa(Asig asig) {
		asig.e1().procesa(this);
		asig.e2().procesa(this);
	}

	@Override
	public void procesa(If_then if_then) {
		if_then.e().procesa(this);
		if_then.is().procesa(this);
	}

	@Override
	public void procesa(If_then_else if_then_else) {
		if_then_else.e().procesa(this);
		if_then_else.is1().procesa(this);
		if_then_else.is2().procesa(this);
	}

	@Override
	public void procesa(While while1) {
		while1.e().procesa(this);
		while1.is().procesa(this);
	}

	@Override
	public void procesa(Read read) {
		read.e().procesa(this);
	}

	@Override
	public void procesa(Write write) {
		write.e().procesa(this);
	}

	@Override
	public void procesa(Nl nl) {

	}

	@Override
	public void procesa(New new1) {
		new1.e().procesa(this);
	}

	@Override
	public void procesa(Delete delete) {
		delete.e().procesa(this);
	}

	@Override
	public void procesa(Call call) {
		call.e().procesa(this);
		call.preals().procesa(this);
	}

	@Override
	public void procesa(IComp iComp) {
		ts.add(new HashMap<StringLocalizado, Nodo>());
		iComp.decs().procesa(this);
		iComp.decs().vincula2(this);
		iComp.is().procesa(this);
		ts.pop();
	}

	@Override
	public void procesa(PReals pReals) {

	}

	@Override
	public void procesa(LitInt litInt) {

	}

	@Override
	public void procesa(LitReal litReal) {

	}

	@Override
	public void procesa(LitStr litStr) {

	}

	@Override
	public void procesa(True true1) {

	}

	@Override
	public void procesa(Null null1) {

	}

	@Override
	public void procesa(Menor menor) {
		menor.arg0().procesa(this);
		menor.arg1().procesa(this);
	}

	@Override
	public void procesa(Mayor mayor) {
		mayor.arg0().procesa(this);
		mayor.arg1().procesa(this);
	}

	@Override
	public void procesa(False false1) {

	}

	@Override
	public void procesa(MayorIgual mayorIgual) {
		mayorIgual.arg0().procesa(this);
		mayorIgual.arg1().procesa(this);
	}

	@Override
	public void procesa(Igual igual) {
		igual.arg0().procesa(this);
		igual.arg1().procesa(this);
	}

	@Override
	public void procesa(Distinto distinto) {
		distinto.arg0().procesa(this);
		distinto.arg1().procesa(this);
	}

	@Override
	public void procesa(MenorIgual menorIgual) {
		menorIgual.arg0().procesa(this);
		menorIgual.arg1().procesa(this);
	}

	@Override
	public void procesa(And and) {
		and.arg0().procesa(this);
		and.arg1().procesa(this);
	}

	@Override
	public void procesa(Or or) {
		or.arg0().procesa(this);
		or.arg1().procesa(this);
	}

	@Override
	public void procesa(Modulo modulo) {
		modulo.arg0().procesa(this);
		modulo.arg1().procesa(this);
	}

	@Override
	public void procesa(Negativo negativo) {
		negativo.e().procesa(this);
	}

	@Override
	public void procesa(Not not) {
		not.e().procesa(this);
	}

	@Override
	public void procesa(Acc acc) {
		acc.e().procesa(this);
	}

	@Override
	public void procesa(Indx indx) {
		indx.e1().procesa(this);
		indx.e2().procesa(this);
	}

	@Override
	public void procesa(Dref dref) {
		dref.e().procesa(this);
	}


	@Override
	public void procesa(Decs_vacia dec_vacia) {

	}

	@Override
	public void procesa(PForm pForm) {

	}

	@Override
	public void procesa(DecVar decVar) {
		decVar.tipo().procesa(this);
		recolecta(decVar.id(), decVar);
	}
	
	@Override
	public void procesa(DecTipo decTipo) {
		decTipo.tipo().procesa(this);
		recolecta(decTipo.id(), decTipo);
	}

	@Override
	public void procesa(DecProc decProc) {

	}
	
	@Override
	public void vincula2(IComp iComp) {
		iComp.decs().vincula2(this);
	}

	@Override
	public void vincula2(DecProc decProc) {
		
	}

	@Override
	public void vincula2(DecTipo decTipo) {
		decTipo.tipo().vincula2(this);
	}

	@Override
	public void vincula2(DecVar decVar) {
		decVar.tipo().vincula2(this);
	}

	@Override
	public void vincula2(Decs_una decs_una) {
		decs_una.dec().vincula2(this);
	}

	@Override
	public void vincula2(Decs_muchas decs_muchas) {
		decs_muchas.decs().vincula2(this);
		decs_muchas.dec().vincula2(this);
	}

	@Override
	public void vincula2(Decs_vacia decs_vacia) {
		
	}

	@Override
	public void vincula2(PFormRef pFormRef) {
		pFormRef.tipo().vincula2(this);
	}
	
	@Override
	public void vincula2(TipoRef tipoRef) {
		
	}

	@Override
	public void vincula2(TipoInt tipoInt) {
		
	}

	@Override
	public void vincula2(TipoReal tipoReal) {
		
	}

	@Override
	public void vincula2(TipoBool tipoBool) {
		
	}

	@Override
	public void vincula2(TipoString tipoString) {
		
	}

	@Override
	public void vincula2(TipoArray tipoArray) {
		tipoArray.tipo().vincula2(this);
	}

	@Override
	public void vincula2(TipoRecord tipoRecord) {
		tipoRecord.campos().vincula2(this);
	}

	@Override
	public void vincula2(TipoPointer tipoPointer) {
		if(tipoPointer.tipo().esRef())
			tipoPointer.tipo().procesa(this);
		else
			tipoPointer.tipo().vincula2(this);
	}
	
	@Override
	public void vincula2(Campos_uno campos_uno) {
		campos_uno.campo().vincula2(this);
	}

	@Override
	public void vincula2(Campos_muchos campos_muchos) {
		campos_muchos.campos().vincula2(this);
		campos_muchos.campo().vincula2(this);
	}

	@Override
	public void vincula2(Campo campo) {
		campo.tipo().vincula2(this);
	}
	
	@Override
	public void vincula2(PForm_una pForm_una) {
		pForm_una.pform().vincula2(this);
	}

	@Override
	public void vincula2(PForm_muchas pForm_muchas) {
		pForm_muchas.pforms().vincula2(this);
		pForm_muchas.pform().vincula2(this);
	}

	@Override
	public void vincula2(PForm_vacia pForm_vacia) {
		
	}		


}


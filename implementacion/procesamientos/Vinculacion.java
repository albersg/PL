package implementacion.procesamientos;

import java.util.HashMap;
import java.util.Stack;

import implementacion.asint.ProcesamientoPorDefecto;
import implementacion.asint.TinyASint.*;

public class Vinculacion extends ProcesamientoPorDefecto{

	private Stack<HashMap<StringLocalizado, Dec>> ts;

	public Vinculacion() throws Exception {
		ts = new Stack<HashMap<StringLocalizado, Dec>>();
		ts.add(new HashMap<StringLocalizado, Dec>());
	}
	
	
	private boolean existe_id(StringLocalizado id) throws Exception {
	    for (HashMap<StringLocalizado, Dec> hm : ts)  {
	        if (hm.containsKey(id))  {
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
		if(id_duplicado(id)) {
			System.out.println("Error: id duplicado");
			throw new Exception();
		}
		else
			ts.peek().put(id, dec);
	}
	
	private boolean id_duplicado(StringLocalizado id) throws Exception {
		if(ts.peek().containsKey(id))
			return true;
		return false;
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
		if(existe_id(exp.s())) 
			exp.setVinculo(valorDe(exp.s()));
		else {
			System.out.println("Error: id");
			throw new Exception();
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
		if(existe_id(tipoRef.id())) 
			tipoRef.setVinculo(valorDe(tipoRef.id()));
		else {
			System.out.println("Error: tipoRef");
			throw new Exception();
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
		if(!(tipoPointer.tipo() instanceof TipoRef))
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
		call.e().procesa(this);
		call.preals().procesa(this);
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
		if(tipoPointer.tipo() instanceof TipoRef)
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


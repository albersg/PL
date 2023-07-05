package implementacion.procesamientos;

import implementacion.asint.ProcesamientoPorDefecto;
import implementacion.asint.TinyASint.And;
import implementacion.asint.TinyASint.Asig;
import implementacion.asint.TinyASint.Call;
import implementacion.asint.TinyASint.Campo;
import implementacion.asint.TinyASint.Campos_muchos;
import implementacion.asint.TinyASint.Campos_uno;
import implementacion.asint.TinyASint.DecProc;
import implementacion.asint.TinyASint.DecTipo;
import implementacion.asint.TinyASint.DecVar;
import implementacion.asint.TinyASint.Decs_muchas;
import implementacion.asint.TinyASint.Decs_una;
import implementacion.asint.TinyASint.Delete;
import implementacion.asint.TinyASint.Distinto;
import implementacion.asint.TinyASint.Div;
import implementacion.asint.TinyASint.IComp;
import implementacion.asint.TinyASint.If_then;
import implementacion.asint.TinyASint.If_then_else;
import implementacion.asint.TinyASint.Igual;
import implementacion.asint.TinyASint.Is_muchas;
import implementacion.asint.TinyASint.Is_una;
import implementacion.asint.TinyASint.Mayor;
import implementacion.asint.TinyASint.MayorIgual;
import implementacion.asint.TinyASint.Menor;
import implementacion.asint.TinyASint.MenorIgual;
import implementacion.asint.TinyASint.Modulo;
import implementacion.asint.TinyASint.Mul;
import implementacion.asint.TinyASint.Negativo;
import implementacion.asint.TinyASint.New;
import implementacion.asint.TinyASint.Not;
import implementacion.asint.TinyASint.Or;
import implementacion.asint.TinyASint.PForm;
import implementacion.asint.TinyASint.PFormRef;
import implementacion.asint.TinyASint.PForm_muchas;
import implementacion.asint.TinyASint.PForm_una;
import implementacion.asint.TinyASint.PReal_muchos;
import implementacion.asint.TinyASint.PReal_uno;
import implementacion.asint.TinyASint.Prog;
import implementacion.asint.TinyASint.Read;
import implementacion.asint.TinyASint.Resta;
import implementacion.asint.TinyASint.Suma;
import implementacion.asint.TinyASint.Tipo;
import implementacion.asint.TinyASint.TipoArray;
import implementacion.asint.TinyASint.TipoBool;
import implementacion.asint.TinyASint.TipoInt;
import implementacion.asint.TinyASint.TipoPointer;
import implementacion.asint.TinyASint.TipoReal;
import implementacion.asint.TinyASint.TipoRecord;
import implementacion.asint.TinyASint.TipoRef;
import implementacion.asint.TinyASint.TipoString;
import implementacion.asint.TinyASint.While;
import implementacion.asint.TinyASint.Write;

public class AsignacionEspacio extends ProcesamientoPorDefecto {
	private int dir;
	private int nivel;

	public AsignacionEspacio() throws Exception {
		dir = 0;
		nivel = 0;
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
		prog.is().procesa(this);
	}

	@Override
	public void procesa(PFormRef pFormRef) throws Exception {
		pFormRef.setDir(dir);
		pFormRef.setNivel(nivel);
		pFormRef.tipo().asigna_espacio_tipo(this);
		pFormRef.setTam(1);
		dir = dir + 1;
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
		campo.tipo().asigna_espacio_tipo(this);
		campo.setTam(campo.tipo().getTam());
	}

	@Override
	public void procesa(Campos_muchos campos_muchos) throws Exception {
		campos_muchos.campos().procesa(this);
		campos_muchos.campo().procesa(this);
		campos_muchos.setTam(campos_muchos.campos().getTam() + campos_muchos.campo().getTam());
	}

	@Override
	public void procesa(Campos_uno campos_uno) throws Exception {
		campos_uno.campo().procesa(this);
		campos_uno.setTam(campos_uno.campo().getTam());
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
	public void procesa(New new1) throws Exception {
		new1.e().procesa(this);
	}

	@Override
	public void procesa(Delete delete) throws Exception {
		delete.e().procesa(this);
	}

	@Override
	public void procesa(Call call) throws Exception {
		call.preals().procesa(this);
	}

	@Override
	public void procesa(IComp iComp) throws Exception {
		int ant_dir = dir;
		nivel++;
		iComp.setNivel(nivel);
		dir = 0;
		iComp.decs().procesa(this);
		iComp.is().procesa(this);
		iComp.setTam(dir);
		dir = ant_dir;
		nivel--;
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
	public void procesa(PForm pForm) throws Exception {
		pForm.setDir(dir);
		pForm.setNivel(nivel);
		pForm.tipo().asigna_espacio_tipo(this);
		pForm.setTam(pForm.tipo().getTam());
		dir = dir + pForm.tipo().getTam();
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
	public void procesa(DecVar decVar) throws Exception {
		decVar.setDir(dir);
		decVar.setNivel(nivel);
		decVar.tipo().asigna_espacio_tipo(this);
		dir = dir + decVar.tipo().getTam();
	}

	@Override
	public void procesa(DecTipo decTipo) throws Exception {
		decTipo.tipo().asigna_espacio_tipo(this);
	}

	@Override
	public void procesa(DecProc decProc) throws Exception {
		int ant_dir = dir;
		nivel++;
		decProc.setNivel(nivel);
		dir = 0;
		decProc.pf().procesa(this);
		decProc.decs().procesa(this);
		decProc.is().procesa(this);
		decProc.setTam(dir);
		dir = ant_dir;
		nivel--;
	}

	@Override
	public void asiga_espacio_tipo(Tipo tipo) throws Exception {
		if (tipo.getTam() == -1) {
			tipo.asigna_espacio_tipo1(this);
			tipo.asigna_espacio_tipo2(this);
		}
	}

	@Override
	public void asigna_espacio_tipo1(TipoInt tipoInt) throws Exception {
		tipoInt.setTam(1);
	}

	@Override
	public void asigna_espacio_tipo1(TipoReal tipoReal) throws Exception {
		tipoReal.setTam(1);
	}

	@Override
	public void asigna_espacio_tipo1(TipoBool tipoBool) throws Exception {
		tipoBool.setTam(1);
	}

	@Override
	public void asigna_espacio_tipo1(TipoString tipoString) throws Exception {
		tipoString.setTam(1);
	}

	@Override
	public void asigna_espacio_tipo1(TipoRef tipoRef) throws Exception {
		((DecTipo) tipoRef.getVinculo()).procesa(this);
		tipoRef.setTam(((DecTipo) tipoRef.getVinculo()).tipo().getTam());
	}

	@Override
	public void asigna_espacio_tipo1(TipoArray tipoArray) throws Exception {
		tipoArray.tipo().asigna_espacio_tipo1(this);
		tipoArray.setTam(Integer.parseInt(tipoArray.id().toString()) * tipoArray.tipo().getTam());
	}

	@Override
	public void asigna_espacio_tipo1(TipoRecord tipoRecord) throws Exception {
		tipoRecord.campos().procesa(this);
		tipoRecord.setTam(tipoRecord.campos().getTam());
	}

	@Override
	public void asigna_espacio_tipo1(TipoPointer tipoPointer) throws Exception {
		tipoPointer.setTam(1);
		if (!(tipoPointer.tipo() instanceof TipoRef))
			tipoPointer.tipo().asigna_espacio_tipo1(this);
	}

	@Override
	public void asigna_espacio_tipo2(TipoPointer tipoPointer) throws Exception {
		if (tipoPointer.tipo() instanceof TipoRef) {
			((DecTipo) tipoPointer.tipo().getVinculo()).tipo().asigna_espacio_tipo1(this);
			tipoPointer.setTam(((DecTipo) tipoPointer.tipo().getVinculo()).tipo().getTam());
		} else
			tipoPointer.tipo().asigna_espacio_tipo2(this);
	}
}

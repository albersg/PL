package implementacion.asint;

import implementacion.c_ast_descendente.Token;

public class TinyASint {

	public static class Nodo {
		private int tam;
		private int dir;
		private int nivel;
		private Tipo tipo;
		private int etqInic;
		private int etqSig;
		private Nodo vinculo;

		public Nodo() {
		}

		public int getTam() {
			return tam;
		}

		public void setTam(int tam) {
			this.tam = tam;
		}

		public int getDir() {
			return dir;
		}

		public void setDir(int dir) {
			this.dir = dir;
		}

		public int getNivel() {
			return nivel;
		}

		public void setNivel(int nivel) {
			this.nivel = nivel;
		}

		public Tipo getTipo() {
			return tipo;
		}

		public void setTipo(Tipo tipo) {
			this.tipo = tipo;
		}

		public int getEtqInic() {
			return etqInic;
		}

		public int getEtqSig() {
			return etqSig;
		}

		public void setEtqInic(int v) {
			this.etqInic = v;
		}

		public void setEtqSig(int v) {
			this.etqSig = v;
		}

		public Nodo getVinculo() {
			return vinculo;
		}

		public void setVinculo(Nodo vinculo) {
			this.vinculo = vinculo;
		}
	}

	// Programa

	public static class Prog extends Nodo {
		private Decs decs;
		private Is is;

		public Prog(Decs decs, Is is) {
			super();
			this.decs = decs;
			this.is = is;
		}

		public Decs decs() {
			return decs;
		}

		public Is is() {
			return is;
		}

		public void procesa(Procesamiento p) throws Exception {
			p.procesa(this);
		}
	}

	// Declaraciones

	public static abstract class Dec extends Nodo {
		public Dec() {
		}

		public abstract void procesa(Procesamiento p) throws Exception;

		public abstract void recolecta_procs(Procesamiento p) throws Exception;

		public abstract void vincula2(Procesamiento p) throws Exception;
	}

	public static abstract class Decs extends Nodo {
		public Decs() {
		}

		public abstract void procesa(Procesamiento p) throws Exception;

		public abstract void recolecta_procs(Procesamiento p) throws Exception;

		public abstract void vincula2(Procesamiento p) throws Exception;
	}

	public static class Decs_vacia extends Decs {
		public Decs_vacia() {

		}

		public void procesa(Procesamiento p) throws Exception {
			p.procesa(this);
		}

		@Override
		public void recolecta_procs(Procesamiento p) throws Exception {
			p.recolecta_procs(this);
		}

		@Override
		public void vincula2(Procesamiento p) throws Exception {
			p.vincula2(this);
		}
	}

	public static class Decs_muchas extends Decs {
		private Dec dec;
		private Decs decs;

		public Decs_muchas(Decs decs, Dec dec) {
			super();
			this.dec = dec;
			this.decs = decs;
		}

		public Dec dec() {
			return dec;
		}

		public Decs decs() {
			return decs;
		}

		public void procesa(Procesamiento p) throws Exception {
			p.procesa(this);
		}

		@Override
		public void recolecta_procs(Procesamiento p) throws Exception {
			p.recolecta_procs(this);
		}

		@Override
		public void vincula2(Procesamiento p) throws Exception {
			p.vincula2(this);
		}
	}

	public static class Decs_una extends Decs {
		private Dec dec;

		public Decs_una(Dec dec) {
			super();
			this.dec = dec;
		}

		public Dec dec() {
			return dec;
		}

		public void procesa(Procesamiento p) throws Exception {
			p.procesa(this);
		}

		@Override
		public void recolecta_procs(Procesamiento p) throws Exception {
			p.recolecta_procs(this);
		}

		@Override
		public void vincula2(Procesamiento p) throws Exception {
			p.vincula2(this);
		}
	}

	public static class DecVar extends Dec {
		private Tipo tipo;
		private StringLocalizado id;

		public DecVar(Tipo tipo, StringLocalizado id) {
			super();
			this.id = id;
			this.tipo = tipo;
		}

		public StringLocalizado id() {
			return id;
		}

		@Override
		public void procesa(Procesamiento p) throws Exception {
			p.procesa(this);
		}

		public Tipo tipo() {
			return tipo;
		}

		@Override
		public void recolecta_procs(Procesamiento p) throws Exception {
			p.recolecta_procs(this);
		}

		@Override
		public void vincula2(Procesamiento p) throws Exception {
			p.vincula2(this);
		}
	}

	public static class DecTipo extends Dec {
		private Tipo tipo;
		private StringLocalizado id;

		public DecTipo(Tipo tipo, StringLocalizado id) {
			super();
			this.id = id;
			this.tipo = tipo;
		}

		public StringLocalizado id() {
			return id;
		}

		@Override
		public void procesa(Procesamiento p) throws Exception {
			p.procesa(this);
		}

		public Tipo tipo() {
			return tipo;
		}

		@Override
		public void recolecta_procs(Procesamiento p) throws Exception {
			p.recolecta_procs(this);
		}

		@Override
		public void vincula2(Procesamiento p) throws Exception {
			p.vincula2(this);
		}
	}

	public static class DecProc extends Dec {
		private StringLocalizado id;
		private PForms pf;
		private Decs decs;
		private Is is;

		public DecProc(StringLocalizado id, PForms pf, Decs decs, Is is) {
			super();
			this.id = id;
			this.pf = pf;
			this.decs = decs;
			this.is = is;
		}

		public StringLocalizado id() {
			return id;
		}

		public PForms pf() {
			return pf;
		}

		public Decs decs() {
			return decs;
		}

		public Is is() {
			return is;
		}

		@Override
		public void procesa(Procesamiento p) throws Exception {
			p.procesa(this);
		}

		@Override
		public void recolecta_procs(Procesamiento p) throws Exception {
			p.recolecta_procs(this);
		}

		@Override
		public void vincula2(Procesamiento p) throws Exception {
			p.vincula2(this);
		}

	}

	// Tipos

	public static abstract class Tipo extends Nodo {
		public Tipo() {
			this.setTam(-1);
		}

		public Tipo ref() {
			return this;
		}

		public abstract void procesa(Procesamiento p) throws Exception;

		public abstract void vincula2(Procesamiento p) throws Exception;

		public abstract void asigna_espacio_tipo(Procesamiento p) throws Exception;

		public abstract void asigna_espacio_tipo1(Procesamiento p) throws Exception;

		public abstract void asigna_espacio_tipo2(Procesamiento p) throws Exception;
	}

	public static class TipoInt extends Tipo {

		public TipoInt() {
			super();
		}

		@Override
		public void procesa(Procesamiento p) throws Exception {
			p.procesa(this);
		}

		@Override
		public void vincula2(Procesamiento p) throws Exception {
			p.vincula2(this);
		}

		@Override
		public void asigna_espacio_tipo(Procesamiento p) throws Exception {
			p.asiga_espacio_tipo(this);
		}

		@Override
		public void asigna_espacio_tipo1(Procesamiento p) throws Exception {
			p.asigna_espacio_tipo1(this);
		}

		@Override
		public void asigna_espacio_tipo2(Procesamiento p) throws Exception {
		}

	}

	public static class TipoReal extends Tipo {

		public TipoReal() {
			super();
		}

		@Override
		public void procesa(Procesamiento p) throws Exception {
			p.procesa(this);
		}

		@Override
		public void vincula2(Procesamiento p) throws Exception {
			p.vincula2(this);
		}

		@Override
		public void asigna_espacio_tipo(Procesamiento p) throws Exception {
			p.asiga_espacio_tipo(this);
		}

		@Override
		public void asigna_espacio_tipo1(Procesamiento p) throws Exception {
			p.asigna_espacio_tipo1(this);
		}

		@Override
		public void asigna_espacio_tipo2(Procesamiento p) throws Exception {
		}

	}

	public static class TipoBool extends Tipo {

		public TipoBool() {
			super();
		}

		@Override
		public void procesa(Procesamiento p) throws Exception {
			p.procesa(this);
		}

		@Override
		public void vincula2(Procesamiento p) throws Exception {
			p.vincula2(this);
		}

		@Override
		public void asigna_espacio_tipo(Procesamiento p) throws Exception {
			p.asiga_espacio_tipo(this);
		}

		@Override
		public void asigna_espacio_tipo1(Procesamiento p) throws Exception {
			p.asigna_espacio_tipo1(this);
		}

		@Override
		public void asigna_espacio_tipo2(Procesamiento p) throws Exception {
		}

	}

	public static class TipoString extends Tipo {

		public TipoString() {
			super();
		}

		@Override
		public void procesa(Procesamiento p) throws Exception {
			p.procesa(this);
		}

		@Override
		public void vincula2(Procesamiento p) throws Exception {
			p.vincula2(this);
		}

		@Override
		public void asigna_espacio_tipo(Procesamiento p) throws Exception {
			p.asiga_espacio_tipo(this);
		}

		@Override
		public void asigna_espacio_tipo1(Procesamiento p) throws Exception {
			p.asigna_espacio_tipo1(this);
		}

		@Override
		public void asigna_espacio_tipo2(Procesamiento p) throws Exception {
		}

	}

	public static class TipoRef extends Tipo {
		private StringLocalizado id;

		public TipoRef(StringLocalizado id) {
			super();
			this.id = id;
		}

		public StringLocalizado id() {
			return id;
		}

		public Tipo ref() {
			if (this.getVinculo() instanceof DecTipo) {
				return ((DecTipo) this.getVinculo()).tipo().ref();
			} else
				return new TipoError();
		}

		@Override
		public void procesa(Procesamiento p) throws Exception {
			p.procesa(this);
		}

		@Override
		public void vincula2(Procesamiento p) throws Exception {
			p.vincula2(this);
		}

		@Override
		public void asigna_espacio_tipo(Procesamiento p) throws Exception {
			p.asiga_espacio_tipo(this);
		}

		@Override
		public void asigna_espacio_tipo1(Procesamiento p) throws Exception {
			p.asigna_espacio_tipo1(this);
		}

		@Override
		public void asigna_espacio_tipo2(Procesamiento p) throws Exception {
		}
	}

	public static class TipoArray extends Tipo {
		private StringLocalizado id;
		private Tipo tipo;

		public TipoArray() {

		}

		public TipoArray(StringLocalizado id, Tipo tipo) {
			super();
			this.id = id;
			this.tipo = tipo;
		}

		public StringLocalizado id() {
			return id;
		}

		public Tipo tipo() {
			return tipo;
		}

		@Override
		public void procesa(Procesamiento p) throws Exception {
			p.procesa(this);
		}

		@Override
		public void vincula2(Procesamiento p) throws Exception {
			p.vincula2(this);
		}

		@Override
		public void asigna_espacio_tipo(Procesamiento p) throws Exception {
			p.asiga_espacio_tipo(this);
		}

		@Override
		public void asigna_espacio_tipo1(Procesamiento p) throws Exception {
			p.asigna_espacio_tipo1(this);
		}

		@Override
		public void asigna_espacio_tipo2(Procesamiento p) throws Exception {
		}

	}

	public static class TipoRecord extends Tipo {
		private Campos campos;

		public TipoRecord(Campos campos) {
			super();
			this.campos = campos;
		}

		public Campos campos() {
			return campos;
		}

		@Override
		public void procesa(Procesamiento p) throws Exception {
			p.procesa(this);
		}

		@Override
		public void vincula2(Procesamiento p) throws Exception {
			p.vincula2(this);
		}

		@Override
		public void asigna_espacio_tipo(Procesamiento p) throws Exception {
			p.asiga_espacio_tipo(this);
		}

		@Override
		public void asigna_espacio_tipo1(Procesamiento p) throws Exception {
			p.asigna_espacio_tipo1(this);
		}

		@Override
		public void asigna_espacio_tipo2(Procesamiento p) throws Exception {
		}

	}

	public static class TipoPointer extends Tipo {
		private Tipo tipo;

		public TipoPointer(Tipo tipo) {
			super();
			this.tipo = tipo;
		}

		public Tipo tipo() {
			return tipo;
		}

		@Override
		public void procesa(Procesamiento p) throws Exception {
			p.procesa(this);
		}

		@Override
		public void vincula2(Procesamiento p) throws Exception {
			p.vincula2(this);
		}

		@Override
		public void asigna_espacio_tipo(Procesamiento p) throws Exception {
			p.asiga_espacio_tipo(this);
		}

		@Override
		public void asigna_espacio_tipo1(Procesamiento p) throws Exception {
			p.asigna_espacio_tipo1(this);
		}

		@Override
		public void asigna_espacio_tipo2(Procesamiento p) throws Exception {
			p.asigna_espacio_tipo2(this);
		}

	}

	public static class TipoError extends Tipo {

		@Override
		public void procesa(Procesamiento p) throws Exception {
		}

		@Override
		public void vincula2(Procesamiento p) throws Exception {

		}

		@Override
		public void asigna_espacio_tipo(Procesamiento p) throws Exception {

		}

		@Override
		public void asigna_espacio_tipo1(Procesamiento p) throws Exception {

		}

		@Override
		public void asigna_espacio_tipo2(Procesamiento p) throws Exception {
			// TODO Auto-generated method stub

		}

	}

	public static class TipoOk extends Tipo {

		@Override
		public void procesa(Procesamiento p) throws Exception {
			// TODO Auto-generated method stub

		}

		@Override
		public void vincula2(Procesamiento p) throws Exception {
			// TODO Auto-generated method stub

		}

		@Override
		public void asigna_espacio_tipo(Procesamiento p) throws Exception {
			// TODO Auto-generated method stub

		}

		@Override
		public void asigna_espacio_tipo1(Procesamiento p) throws Exception {
			// TODO Auto-generated method stub

		}

		@Override
		public void asigna_espacio_tipo2(Procesamiento p) throws Exception {
			// TODO Auto-generated method stub

		}

	}

	public static class TipoNull extends Tipo {

		@Override
		public void procesa(Procesamiento p) throws Exception {
			// TODO Auto-generated method stub

		}

		@Override
		public void vincula2(Procesamiento p) throws Exception {
			// TODO Auto-generated method stub

		}

		@Override
		public void asigna_espacio_tipo(Procesamiento p) throws Exception {
			// TODO Auto-generated method stub

		}

		@Override
		public void asigna_espacio_tipo1(Procesamiento p) throws Exception {
			// TODO Auto-generated method stub

		}

		@Override
		public void asigna_espacio_tipo2(Procesamiento p) throws Exception {
			// TODO Auto-generated method stub

		}

	}
	// Parametros Formales

	public static class PForm extends Dec {
		private StringLocalizado id;
		private Tipo tipo;

		public PForm(StringLocalizado id, Tipo tipo) {
			super();
			this.id = id;
			this.tipo = tipo;
		}

		public StringLocalizado id() {
			return id;
		}

		public Tipo tipo() {
			return tipo;
		}

		public void procesa(Procesamiento p) throws Exception {
			p.procesa(this);
		}

		public void vincula2(Procesamiento p) throws Exception {
		}

		@Override
		public void recolecta_procs(Procesamiento p) throws Exception {

		}
	}

	public static abstract class PForms extends Dec {

		public PForms() {
		}

		public void procesa(Procesamiento p) throws Exception {
			p.procesa(this);
		}

		public void vincula2(Procesamiento p) throws Exception {
			p.vincula2(this);
		}

		@Override
		public void recolecta_procs(Procesamiento p) throws Exception {
			// TODO Auto-generated method stub

		}

		public abstract int nElem();

	}

	public static class PFormRef extends PForm {

		public PFormRef(StringLocalizado id, Tipo tipo) {
			super(id, tipo);
		}

		public void procesa(Procesamiento p) throws Exception {
			p.procesa(this);
		}

		@Override
		public void recolecta_procs(Procesamiento p) throws Exception {
			// TODO Auto-generated method stub

		}

		@Override
		public void vincula2(Procesamiento p) throws Exception {
			p.vincula2(this);
		}

	}

	public static class PForm_vacia extends PForms {
		public PForm_vacia() {

		}

		public void procesa(Procesamiento p) throws Exception {
			p.procesa(this);
		}

		public void vincula2(Procesamiento p) throws Exception {
			p.vincula2(this);
		}

		public int nElem() {
			return 0;
		}
	}

	public static class PForm_muchas extends PForms {
		private PForm pform;
		private PForms pforms;

		public PForm_muchas(PForms pforms, PForm pform) {
			super();
			this.pforms = pforms;
			this.pform = pform;
		}

		public PForm pform() {
			return pform;
		}

		public PForms pforms() {
			return pforms;
		}

		public int nElem() {
			return pforms.nElem() + 1;
		}

		public void procesa(Procesamiento p) throws Exception {
			p.procesa(this);
		}

		public void vincula2(Procesamiento p) throws Exception {
			p.vincula2(this);
		}
	}

	public static class PForm_una extends PForms {
		private PForm pform;

		public PForm_una(PForm pform) {
			super();
			this.pform = pform;
		}

		public PForm pform() {
			return pform;
		}

		public void procesa(Procesamiento p) throws Exception {
			p.procesa(this);
		}

		public void vincula2(Procesamiento p) throws Exception {
			p.vincula2(this);
		}

		@Override
		public int nElem() {
			return 1;
		}
	}

	// Instrucciones

	public static abstract class I extends Nodo {
		public I() {

		}

		public abstract void procesa(Procesamiento p) throws Exception;
	}

	public static abstract class Is extends Nodo {
		public Is() {

		}

		public abstract void procesa(Procesamiento p) throws Exception;
	}

	public static class Is_vacia extends Is {
		public Is_vacia() {

		}

		public void procesa(Procesamiento p) throws Exception {
			p.procesa(this);
		}
	}

	public static class Is_muchas extends Is {
		private I i;
		private Is is;

		public Is_muchas(Is is, I i) {
			super();
			this.is = is;
			this.i = i;
		}

		public I i() {
			return i;
		}

		public Is is() {
			return is;
		}

		public void procesa(Procesamiento p) throws Exception {
			p.procesa(this);
		}
	}

	public static class Is_una extends Is {
		private I i;

		public Is_una(I i) {
			super();
			this.i = i;
		}

		public I i() {
			return i;
		}

		public void procesa(Procesamiento p) throws Exception {
			p.procesa(this);
		}
	}

	public static class Asig extends I {
		private Exp e1;
		private Exp e2;

		public Asig(Exp e1, Exp e2) {
			super();
			this.e1 = e1;
			this.e2 = e2;
		}

		public Exp e1() {
			return e1;
		}

		public Exp e2() {
			return e2;
		}

		public void procesa(Procesamiento p) throws Exception {
			p.procesa(this);
		}
	}

	public static class If_then extends I {
		private Exp e;
		private Is is;

		public If_then(Exp e, Is is) {
			super();
			this.e = e;
			this.is = is;
		}

		public Exp e() {
			return e;
		}

		public Is is() {
			return is;
		}

		public void procesa(Procesamiento p) throws Exception {
			p.procesa(this);
		}
	}

	public static class If_then_else extends I {
		private Exp e;
		private Is is1;
		private Is is2;

		public If_then_else(Exp e, Is is1, Is is2) {
			super();
			this.e = e;
			this.is1 = is1;
			this.is2 = is2;
		}

		public Exp e() {
			return e;
		}

		public Is is1() {
			return is1;
		}

		public Is is2() {
			return is2;
		}

		public void procesa(Procesamiento p) throws Exception {
			p.procesa(this);
		}
	}

	public static class While extends I {
		private Exp e;
		private Is is;

		public While(Exp e, Is is) {
			super();
			this.e = e;
			this.is = is;
		}

		public Exp e() {
			return e;
		}

		public Is is() {
			return is;
		}

		public void procesa(Procesamiento p) throws Exception {
			p.procesa(this);
		}
	}

	public static class Read extends I {
		private Exp e;

		public Read(Exp e) {
			super();
			this.e = e;
		}

		public Exp e() {
			return e;
		}

		public void procesa(Procesamiento p) throws Exception {
			p.procesa(this);
		}
	}

	public static class Write extends I {
		private Exp e;

		public Write(Exp e) {
			super();
			this.e = e;
		}

		public Exp e() {
			return e;
		}

		public void procesa(Procesamiento p) throws Exception {
			p.procesa(this);
		}
	}

	public static class Nl extends I {

		public Nl() {
			super();
		}

		public void procesa(Procesamiento p) throws Exception {
			p.procesa(this);
		}
	}

	public static class New extends I {
		private Exp e;

		public New(Exp e) {
			this.e = e;
		}

		public Exp e() {
			return e;
		}

		public void procesa(Procesamiento p) throws Exception {
			p.procesa(this);
		}
	}

	public static class Delete extends I {
		private Exp e;

		public Delete(Exp e) {
			this.e = e;
		}

		public Exp e() {
			return e;
		}

		public void procesa(Procesamiento p) throws Exception {
			p.procesa(this);
		}
	}

	public static class Call extends I {
		private Exp e;
		private PReals preals;

		public Call(Exp e, PReals preals) {
			super();
			this.e = e;
			this.preals = preals;
		}

		public Exp e() {
			return e;
		}

		public PReals preals() {
			return preals;
		}

		public void procesa(Procesamiento p) throws Exception {
			p.procesa(this);
		}
	}

	public static class IComp extends I {
		private Decs decs;
		private Is is;

		public IComp(Decs decs, Is is) {
			super();
			this.decs = decs;
			this.is = is;
		}

		public Decs decs() {
			return decs;
		}

		public Is is() {
			return is;
		}

		public void procesa(Procesamiento p) throws Exception {
			p.procesa(this);
		}

		public void vincula2(Procesamiento p) throws Exception {
			p.vincula2(this);
		}
	}

	// Campos

	public static class Campo extends Nodo {
		private Tipo tipo;
		private StringLocalizado id;
		private int desp;

		public Campo(Tipo tipo, StringLocalizado id) {
			super();
			this.tipo = tipo;
			this.id = id;
		}

		public Tipo tipo() {
			return tipo;
		}

		public StringLocalizado id() {
			return id;
		}

		public int getDesp() {
			return desp;
		}

		public void setDesp(int desp) {
			this.desp = desp;
		}

		public void procesa(Procesamiento p) throws Exception {
			p.procesa(this);
		}

		public void vincula2(Procesamiento p) throws Exception {
			p.vincula2(this);
		}
	}

	public static abstract class Campos extends Nodo {

		public Campos() {
			super();
		}

		public abstract int nElem();

		public abstract Tipo tipoDeCampo(StringLocalizado id);

		public abstract boolean existeCampo(StringLocalizado id);

		public abstract int desplCampo(StringLocalizado id);

		public abstract void procesa(Procesamiento p) throws Exception;

		public abstract void vincula2(Procesamiento p) throws Exception;
	}

	public static class Campos_muchos extends Campos {
		private Campo campo;
		private Campos campos;

		public Campos_muchos(Campos campos, Campo campo) {
			super();
			this.campos = campos;
			this.campo = campo;
		}

		public Campo campo() {
			return campo;
		}

		public Campos campos() {
			return campos;
		}

		public void procesa(Procesamiento p) throws Exception {
			p.procesa(this);
		}

		public void vincula2(Procesamiento p) throws Exception {
			p.vincula2(this);
		}

		@Override
		public Tipo tipoDeCampo(StringLocalizado id) {
			if (campo.id().equals(id))
				return campo.tipo();
			else
				return campos.tipoDeCampo(id);
		}

		@Override
		public boolean existeCampo(StringLocalizado id) {
			if (campo.id().equals(id))
				return true;
			else
				return campos.existeCampo(id);
		}

		@Override
		public int desplCampo(StringLocalizado id) {
			if (campo.id().equals(id))
				return campos.getTam();
			else
				return campos.desplCampo(id);
		}

		@Override
		public int nElem() {
			return campos.nElem() + 1;
		}
	}

	public static class Campos_uno extends Campos {
		private Campo campo;

		public Campos_uno(Campo campo) {
			super();
			this.campo = campo;
		}

		public Campo campo() {
			return campo;
		}

		@Override
		public Tipo tipoDeCampo(StringLocalizado id) {
			if (campo.id().equals(id))
				return campo.tipo();
			else
				return null;
		}

		@Override
		public boolean existeCampo(StringLocalizado id) {
			if (campo.id().equals(id))
				return true;
			else
				return false;
		}

		@Override
		public int desplCampo(StringLocalizado id) {
			if (campo.id().equals(id))
				return 0;
			else
				return -1;
		}

		public void procesa(Procesamiento p) throws Exception {
			p.procesa(this);
		}

		public void vincula2(Procesamiento p) throws Exception {
			p.vincula2(this);
		}

		@Override
		public int nElem() {
			return 1;
		}
	}

	// Parámetros reales

	public static class PReal extends Nodo {
		private Exp e;

		public PReal(Exp e) {
			super();
			this.e = e;
		}

		public Exp e() {
			return e;
		}

		public boolean getEsDesig() {
			return false;
		}

		public void procesa(Procesamiento p) throws Exception {
			p.procesa(this);
		}
	}

	public abstract static class PReals extends Nodo {
		public PReals() {
		}

		public boolean getEsDesig() {
			return false;
		}

		public void procesa(Procesamiento p) throws Exception {
			p.procesa(this);
		}

		public abstract int nElem();
	}

	public static class PReal_muchos extends PReals {
		private PReal preal;
		private PReals preals;

		public PReal_muchos(PReals preals, PReal preal) {
			super();
			this.preals = preals;
			this.preal = preal;
		}

		public PReal preal() {
			return preal;
		}

		public PReals preals() {
			return preals;
		}

		public void procesa(Procesamiento p) throws Exception {
			p.procesa(this);
		}

		@Override
		public int nElem() {
			return preals().nElem() + 1;
		}
	}

	public static class PReal_uno extends PReals {
		private PReal preal;

		public PReal_uno(PReal preal) {
			super();
			this.preal = preal;
		}

		public PReal preal() {
			return preal;
		}

		public void procesa(Procesamiento p) throws Exception {
			p.procesa(this);
		}

		@Override
		public int nElem() {
			return 1;
		}
	}

	public static class PReal_ninguno extends PReals {

		public PReal_ninguno() {
			super();
		}

		public void procesa(Procesamiento p) throws Exception {
			p.procesa(this);
		}

		@Override
		public int nElem() {
			return 0;
		}
	}

	// Expresiones

	public static abstract class Exp extends Nodo {
		public Exp() {
		}

		public abstract StringLocalizado id();

		public abstract boolean getEsDesig();

		public abstract void procesa(Procesamiento procesamiento) throws Exception;
	}

	public static class LitInt extends Exp {
		private StringLocalizado s;

		public LitInt(StringLocalizado s) {
			super();
			this.s = s;
		}

		public StringLocalizado s() {
			return s;
		}

		@Override
		public void procesa(Procesamiento p) throws Exception {
			p.procesa(this);
		}

		@Override
		public boolean getEsDesig() {
			return false;
		}

		@Override
		public StringLocalizado id() {
			return s;
		}
	}

	public static class LitReal extends Exp {
		private StringLocalizado s;

		public LitReal(StringLocalizado s) {
			super();
			this.s = s;
		}

		public StringLocalizado s() {
			return s;
		}

		@Override
		public void procesa(Procesamiento p) throws Exception {
			p.procesa(this);
		}

		@Override
		public boolean getEsDesig() {
			return false;
		}

		@Override
		public StringLocalizado id() {
			return s;
		}
	}

	public static class LitStr extends Exp {
		private StringLocalizado s;

		public LitStr(StringLocalizado s) {
			super();
			this.s = s;
		}

		public StringLocalizado s() {
			return s;
		}

		@Override
		public void procesa(Procesamiento p) throws Exception {
			p.procesa(this);
		}

		@Override
		public boolean getEsDesig() {
			return false;
		}

		@Override
		public StringLocalizado id() {
			return s;
		}
	}

	public static class True extends Exp {
		private StringLocalizado s;

		public True(StringLocalizado s) {
			super();
			this.s = s;
		}

		@Override
		public void procesa(Procesamiento p) throws Exception {
			p.procesa(this);
		}

		@Override
		public boolean getEsDesig() {
			return false;
		}

		@Override
		public StringLocalizado id() {
			return s;
		}
	}

	public static class False extends Exp {
		private StringLocalizado s;

		public False(StringLocalizado s) {
			super();
			this.s = s;
		}

		@Override
		public void procesa(Procesamiento p) throws Exception {
			p.procesa(this);
		}

		@Override
		public boolean getEsDesig() {
			return false;
		}

		@Override
		public StringLocalizado id() {
			return s;
		}
	}

	public static class Id extends Exp {
		private StringLocalizado s;

		public Id(StringLocalizado s) {
			super();
			this.s = s;
		}

		public StringLocalizado s() {
			return s;
		}

		@Override
		public void procesa(Procesamiento p) throws Exception {
			p.procesa(this);
		}

		@Override
		public boolean getEsDesig() {
			return true;
		}

		@Override
		public StringLocalizado id() {
			return s;
		}
	}

	public static class Null extends Exp {
		private StringLocalizado s;

		public Null(StringLocalizado s) {
			super();
			this.s = s;
		}

		@Override
		public void procesa(Procesamiento p) throws Exception {
			p.procesa(this);
		}

		@Override
		public boolean getEsDesig() {
			return false;
		}

		@Override
		public StringLocalizado id() {
			return s;
		}
	}

	private static abstract class ExpBin extends Exp {
		private Exp arg0;
		private Exp arg1;

		public ExpBin(Exp arg0, Exp arg1) {
			super();
			this.arg0 = arg0;
			this.arg1 = arg1;
		}

		public Exp arg0() {
			return arg0;
		}

		public Exp arg1() {
			return arg1;
		}

		@Override
		public StringLocalizado id() {
			return arg0.id();
		}
	}

	private static abstract class ExpRelacional extends ExpBin {

		public ExpRelacional(Exp e1, Exp e2) {
			super(e1, e2);
		}

	}

	public static class Menor extends ExpRelacional {
		public Menor(Exp e1, Exp e2) {
			super(e1, e2);
		}

		@Override
		public void procesa(Procesamiento p) throws Exception {
			p.procesa(this);
		}

		@Override
		public boolean getEsDesig() {
			return false;
		}

	}

	public static class Mayor extends ExpRelacional {
		public Mayor(Exp e1, Exp e2) {
			super(e1, e2);
		}

		@Override
		public void procesa(Procesamiento p) throws Exception {
			p.procesa(this);
		}

		@Override
		public boolean getEsDesig() {
			return false;
		}
	}

	public static class MenorIgual extends ExpRelacional {
		public MenorIgual(Exp e1, Exp e2) {
			super(e1, e2);
		}

		@Override
		public void procesa(Procesamiento p) throws Exception {
			p.procesa(this);
		}

		@Override
		public boolean getEsDesig() {
			return false;
		}
	}

	public static class MayorIgual extends ExpRelacional {
		public MayorIgual(Exp e1, Exp e2) {
			super(e1, e2);
		}

		@Override
		public void procesa(Procesamiento p) throws Exception {
			p.procesa(this);
		}

		@Override
		public boolean getEsDesig() {
			return false;
		}
	}

	public static class Igual extends ExpRelacional {
		public Igual(Exp e1, Exp e2) {
			super(e1, e2);
		}

		@Override
		public void procesa(Procesamiento p) throws Exception {
			p.procesa(this);
		}

		@Override
		public boolean getEsDesig() {
			return false;
		}
	}

	public static class Distinto extends ExpRelacional {
		public Distinto(Exp e1, Exp e2) {
			super(e1, e2);
		}

		@Override
		public void procesa(Procesamiento p) throws Exception {
			p.procesa(this);
		}

		@Override
		public boolean getEsDesig() {
			return false;
		}
	}

	private static abstract class ExpAditiva extends ExpBin {
		public ExpAditiva(Exp arg0, Exp arg1) {
			super(arg0, arg1);
		}
	}

	public static class Suma extends ExpAditiva {
		public Suma(Exp arg0, Exp arg1) {
			super(arg0, arg1);
		}

		public void procesa(Procesamiento p) throws Exception {
			p.procesa(this);
		}

		@Override
		public boolean getEsDesig() {
			return false;
		}
	}

	public static class Resta extends ExpAditiva {
		public Resta(Exp arg0, Exp arg1) {
			super(arg0, arg1);
		}

		public void procesa(Procesamiento p) throws Exception {
			p.procesa(this);
		}

		@Override
		public boolean getEsDesig() {
			return false;
		}
	}

	private static abstract class ExpLogica extends ExpBin {
		public ExpLogica(Exp arg0, Exp arg1) {
			super(arg0, arg1);
		}

	}

	public static class And extends ExpLogica {
		public And(Exp arg0, Exp arg1) {
			super(arg0, arg1);
		}

		public void procesa(Procesamiento p) throws Exception {
			p.procesa(this);
		}

		@Override
		public boolean getEsDesig() {
			return false;
		}
	}

	public static class Or extends ExpLogica {
		public Or(Exp arg0, Exp arg1) {
			super(arg0, arg1);
		}

		public void procesa(Procesamiento p) throws Exception {
			p.procesa(this);
		}

		@Override
		public boolean getEsDesig() {
			return false;
		}
	}

	private static abstract class ExpMultiplicativa extends ExpBin {
		public ExpMultiplicativa(Exp arg0, Exp arg1) {
			super(arg0, arg1);
		}

	}

	public static class Div extends ExpMultiplicativa {
		public Div(Exp arg0, Exp arg1) {
			super(arg0, arg1);
		}

		public void procesa(Procesamiento p) throws Exception {
			p.procesa(this);
		}

		@Override
		public boolean getEsDesig() {
			return false;
		}
	}

	public static class Mul extends ExpMultiplicativa {
		public Mul(Exp arg0, Exp arg1) {
			super(arg0, arg1);
		}

		public void procesa(Procesamiento p) throws Exception {
			p.procesa(this);
		}

		@Override
		public boolean getEsDesig() {
			return false;
		}
	}

	public static class Modulo extends ExpMultiplicativa {
		public Modulo(Exp arg0, Exp arg1) {
			super(arg0, arg1);
		}

		public void procesa(Procesamiento p) throws Exception {
			p.procesa(this);
		}

		@Override
		public boolean getEsDesig() {
			return false;
		}
	}

	private static abstract class ExpUnaria extends Exp {
		private Exp e;

		public ExpUnaria(Exp e) {
			super();
			this.e = e;
		}

		public Exp e() {
			return e;
		}

		@Override
		public StringLocalizado id() {
			return e.id();
		}
	}

	public static class Negativo extends ExpUnaria {
		public Negativo(Exp arg0) {
			super(arg0);
		}

		public void procesa(Procesamiento p) throws Exception {
			p.procesa(this);
		}

		@Override
		public boolean getEsDesig() {
			return false;
		}
	}

	public static class Not extends ExpUnaria {
		public Not(Exp arg0) {
			super(arg0);
		}

		public void procesa(Procesamiento p) throws Exception {
			p.procesa(this);
		}

		@Override
		public boolean getEsDesig() {
			return false;
		}
	}

	public static class Acc extends Exp {
		private StringLocalizado s;
		private Exp e;

		public Acc(Exp e, StringLocalizado s) {
			super();
			this.e = e;
			this.s = s;
		}

		public Exp e() {
			return e;
		}

		public StringLocalizado s() {
			return s;
		}

		@Override
		public StringLocalizado id() {
			return s;
		}

		@Override
		public void procesa(Procesamiento p) throws Exception {
			p.procesa(this);
		}

		@Override
		public boolean getEsDesig() {
			return true;
		}
	}

	public static class Indx extends Exp {
		private Exp e1;
		private Exp e2;

		public Indx(Exp e1, Exp e2) {
			super();
			this.e1 = e1;
			this.e2 = e2;
		}

		public Exp e1() {
			return e1;
		}

		public Exp e2() {
			return e2;
		}

		@Override
		public void procesa(Procesamiento p) throws Exception {
			p.procesa(this);
		}

		@Override
		public boolean getEsDesig() {
			return true;
		}

		@Override
		public StringLocalizado id() {
			return e1.id();
		}
	}

	public static class Dref extends Exp {
		private Exp e;

		public Dref(Exp e) {
			this.e = e;
			;
		}

		public Exp e() {
			return e;
		}

		@Override
		public void procesa(Procesamiento p) throws Exception {
			p.procesa(this);
		}

		@Override
		public boolean getEsDesig() {
			return true;
		}

		@Override
		public StringLocalizado id() {
			return e.id();
		}

	}

	public static class StringLocalizado {
		private String s;
		private int fila;
		private int col;

		public StringLocalizado(String s, int fila, int col) {
			this.s = s;
			this.fila = fila;
			this.col = col;
		}

		public StringLocalizado(Token id) {
			this(id.toString(), id.beginLine, id.beginColumn);
		}

		public int col() {
			return col;
		}

		public boolean equals(Object o) {
			return (o == this) || ((o instanceof StringLocalizado) && (((StringLocalizado) o).s.equals(s)));
		}

		public int fila() {
			return fila;
		}

		public int hashCode() {
			return s.hashCode();
		}

		public String toString() {
			return s;
		}
	}

	// Constructoras

	public Prog prog(Decs decs, Is is) {
		return new Prog(decs, is);
	}

	public Decs_vacia decs_vacia() {
		return new Decs_vacia();
	}

	public Decs_una decs_una(Dec dec) {
		return new Decs_una(dec);
	}

	public Decs_muchas decs_muchas(Decs decs, Dec dec) {
		return new Decs_muchas(decs, dec);
	}

	public DecVar decVar(Tipo t, StringLocalizado s) {
		return new DecVar(t, s);
	}

	public DecTipo decTipo(Tipo t, StringLocalizado s) {
		return new DecTipo(t, s);
	}

	public DecProc decProc(StringLocalizado s, PForms pforms, Decs decs, Is is) {
		return new DecProc(s, pforms, decs, is);
	}

	public TipoInt tipoInt() {
		return new TipoInt();
	}

	public TipoReal tipoReal() {
		return new TipoReal();
	}

	public TipoBool tipoBool() {
		return new TipoBool();
	}

	public TipoString tipoString() {
		return new TipoString();
	}

	public TipoRef tipoRef(StringLocalizado s) {
		return new TipoRef(s);
	}

	public TipoRecord tipoRecord(Campos campos) {
		return new TipoRecord(campos);
	}

	public TipoArray tipoArray(StringLocalizado s, Tipo t) {
		return new TipoArray(s, t);
	}

	public TipoPointer tipoPointer(Tipo t) {
		return new TipoPointer(t);
	}

	public PForm pForm(StringLocalizado id, Tipo t) {
		return new PForm(id, t);
	}

	public PFormRef pFormRef(StringLocalizado id, Tipo t) {
		return new PFormRef(id, t);
	}

	public PForm_vacia pForm_vacia() {
		return new PForm_vacia();
	}

	public PForm_una pForm_una(PForm pform) {
		return new PForm_una(pform);
	}

	public PForm_muchas pForm_muchas(PForms pforms, PForm pform) {
		return new PForm_muchas(pforms, pform);
	}

	public Is_vacia is_vacia() {
		return new Is_vacia();
	}

	public Is_una is_una(I i) {
		return new Is_una(i);
	}

	public Is_muchas is_muchas(Is is, I i) {
		return new Is_muchas(is, i);
	}

	public Asig asig(Exp e0, Exp e1) {
		return new Asig(e0, e1);
	}

	public If_then if_then(Exp e, Is is) {
		return new If_then(e, is);
	}

	public If_then_else if_then_else(Exp e, Is is0, Is is1) {
		return new If_then_else(e, is0, is1);
	}

	public While while1(Exp e, Is is) {
		return new While(e, is);
	}

	public Read read(Exp e) {
		return new Read(e);
	}

	public Write write(Exp e) {
		return new Write(e);
	}

	public Nl nl() {
		return new Nl();
	}

	public New new1(Exp e) {
		return new New(e);
	}

	public Delete delete(Exp e) {
		return new Delete(e);
	}

	public Call call(Exp id, PReals preals) {
		return new Call(id, preals);
	}

	public IComp iComp(Decs decs, Is is) {
		return new IComp(decs, is);
	}

	public Campo campo(Tipo t, StringLocalizado s) {
		return new Campo(t, s);
	}

	public Campos_uno campos_uno(Campo c) {
		return new Campos_uno(c);
	}

	public Campos_muchos campos_muchos(Campos cs, Campo c) {
		return new Campos_muchos(cs, c);
	}

	public PReal_ninguno pReal_ninguno() {
		return new PReal_ninguno();
	}

	public PReal_uno pReal_uno(PReal preal) {
		return new PReal_uno(preal);
	}

	public PReal_muchos pReal_muchos(PReals preals, PReal preal) {
		return new PReal_muchos(preals, preal);
	}

	public PReal pReal(Exp e) {
		return new PReal(e);
	}

	public LitInt litInt(StringLocalizado s) {
		return new LitInt(s);
	}

	public LitReal litReal(StringLocalizado s) {
		return new LitReal(s);
	}

	public LitStr litStr(StringLocalizado s) {
		return new LitStr(s);
	}

	public True true1(StringLocalizado s) {
		return new True(s);
	}

	public False false1(StringLocalizado s) {
		return new False(s);
	}

	public Id id(StringLocalizado s) {
		return new Id(s);
	}

	public Null null1(StringLocalizado s) {
		return new Null(s);
	}

	public Menor menor(Exp e0, Exp e1) {
		return new Menor(e0, e1);
	}

	public Mayor mayor(Exp e0, Exp e1) {
		return new Mayor(e0, e1);
	}

	public MenorIgual menorIgual(Exp e0, Exp e1) {
		return new MenorIgual(e0, e1);
	}

	public MayorIgual mayorIgual(Exp e0, Exp e1) {
		return new MayorIgual(e0, e1);
	}

	public Igual igual(Exp e0, Exp e1) {
		return new Igual(e0, e1);
	}

	public Distinto distinto(Exp e0, Exp e1) {
		return new Distinto(e0, e1);
	}

	public Suma suma(Exp e0, Exp e1) {
		return new Suma(e0, e1);
	}

	public Resta resta(Exp e0, Exp e1) {
		return new Resta(e0, e1);
	}

	public Div div(Exp e0, Exp e1) {
		return new Div(e0, e1);
	}

	public Mul mul(Exp e0, Exp e1) {
		return new Mul(e0, e1);
	}

	public And and(Exp e0, Exp e1) {
		return new And(e0, e1);
	}

	public Or or(Exp e0, Exp e1) {
		return new Or(e0, e1);
	}

	public Modulo mod(Exp e0, Exp e1) {
		return new Modulo(e0, e1);
	}

	public Negativo negativo(Exp e) {
		return new Negativo(e);
	}

	public Not not(Exp e) {
		return new Not(e);
	}

	public Acc acc(Exp e, StringLocalizado s) {
		return new Acc(e, s);
	}

	public Dref dref(Exp e) {
		return new Dref(e);
	}

	public Indx indx(Exp e0, Exp e1) {
		return new Indx(e0, e1);
	}

	public StringLocalizado stringLocalizado(String s, int fila, int col) {
		return new StringLocalizado(s, fila, col);
	}
}

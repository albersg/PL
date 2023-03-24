package implementacion.asint;

public class TinyASint {

	public enum tNodo {
		INT, REAL, BOOL, STRING, ID, ARRAY, RECORD, POINTER
	}

	public static abstract class Nodo {
		private tNodo tipo;

		public Nodo() {

		}

		public tNodo getTNodo() {
			return tipo;
		}
	}
	
	// Programa
	
	public static class Prog extends Nodo{
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
		
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}
	
	// Declaraciones

	public static abstract class Dec extends Nodo {
		public Dec() {
		}

		public abstract void procesa(Procesamiento p);
	}

	public static abstract class Decs extends Nodo {
		public Decs() {
		}

		public abstract void procesa(Procesamiento p);
	}
	
	public static class Dec_vacia extends Decs {
		public Dec_vacia() {
			
		}
		
		public void procesa(Procesamiento p) {
			p.procesa(this);
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

		public void procesa(Procesamiento p) {
			p.procesa(this);
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

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	public static class DecVar extends Dec {
		private StringLocalizado val;
		private StringLocalizado id;

		public DecVar(StringLocalizado id, StringLocalizado val) {
			super();
			this.id = id;
			this.val = val;
		}

		public StringLocalizado id() {
			return id;
		}

		@Override
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}

		public StringLocalizado val() {
			return val;
		}
	}

	public static class DecTipo extends Dec {
		private StringLocalizado val;
		private StringLocalizado id;

		public DecTipo(StringLocalizado id, StringLocalizado val) {
			super();
			this.id = id;
			this.val = val;
		}

		public StringLocalizado id() {
			return id;
		}

		@Override
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}

		public StringLocalizado val() {
			return val;
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
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}

	}

	// Tipos

	public static abstract class Tipo extends Nodo {
		public Tipo() {

		}

		public abstract void procesa(Procesamiento p);
	}

	public static class TipoInt extends Tipo {

		public TipoInt() {
			super();
		}

		@Override
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}

	}

	public static class TipoReal extends Tipo {

		public TipoReal() {
			super();
		}

		@Override
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}

	}

	public static class TipoBool extends Tipo {

		public TipoBool() {
			super();
		}

		@Override
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}

	}

	public static class TipoString extends Tipo {

		public TipoString() {
			super();
		}

		@Override
		public void procesa(Procesamiento p) {
			p.procesa(this);
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

		@Override
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}

	}

	public static class TipoArray extends Tipo {
		private StringLocalizado id;
		private Tipo tipo;

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
		public void procesa(Procesamiento p) {
			p.procesa(this);
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
		public void procesa(Procesamiento p) {
			p.procesa(this);
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
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}

	}

	// Parametros Formales
	
	public static abstract class PForm extends Nodo{
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
		
		public abstract void procesa(Procesamiento p);
	}
	
	public static class PForms extends Nodo{
		public PForms() {
		}
		
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}
	
	public static class PFormRef extends PForm{
		public PFormRef(StringLocalizado id, Tipo tipo) {
			super(id,tipo);
		}
		
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}
	
	public static class PForm_vacia extends PForms {
		public PForm_vacia() {
			
		}
		
		public void procesa(Procesamiento p) {
			p.procesa(this);
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

		public void procesa(Procesamiento p) {
			p.procesa(this);
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

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}
	
	// Instrucciones
	
	public static abstract class I extends Nodo {
		public I() {
			
		}
		
		public abstract void procesa(Procesamiento p);
	}
	
	public static abstract class Is extends Nodo {
		public Is() {
			
		}
		
		public abstract void procesa(Procesamiento p);
	}
	
	public static class Is_vacia extends Is {
		public Is_vacia() {
			
		}
		
		public void procesa(Procesamiento p) {
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

		public void procesa(Procesamiento p) {
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

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}
	
	// Campos
	
	public static class Campo extends Nodo{
		private Tipo tipo;
		private StringLocalizado id;
		
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
		
		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}
	
	public static abstract class Campos extends Nodo{
		
		public Campos() {
			super();
		}

		
		public abstract void procesa(Procesamiento p);
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

		public void procesa(Procesamiento p) {
			p.procesa(this);
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

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}
	
	//

	public static class Div extends ExpMultiplicativa {
		public Div(Exp arg0, Exp arg1) {
			super(arg0, arg1);
		}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	public static abstract class Exp {
		public Exp() {
		}

		public abstract int prioridad();

		public abstract void procesa(Procesamiento procesamiento);
	}

	private static abstract class ExpAditiva extends ExpBin {
		public ExpAditiva(Exp arg0, Exp arg1) {
			super(arg0, arg1);
		}

		public final int prioridad() {
			return 0;
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
	}

	private static abstract class ExpMultiplicativa extends ExpBin {
		public ExpMultiplicativa(Exp arg0, Exp arg1) {
			super(arg0, arg1);
		}

		public final int prioridad() {
			return 1;
		}
	}

	public static class Id extends Exp {
		private StringLocalizado id;

		public Id(StringLocalizado id) {
			super();
			this.id = id;
		}

		public StringLocalizado id() {
			return id;
		}

		public final int prioridad() {
			return 2;
		}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	public static class Mul extends ExpMultiplicativa {
		public Mul(Exp arg0, Exp arg1) {
			super(arg0, arg1);
		}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	public static class Num extends Exp {
		private StringLocalizado num;

		public Num(StringLocalizado num) {
			super();
			this.num = num;
		}

		public StringLocalizado num() {
			return num;
		}

		public final int prioridad() {
			return 2;
		}

		public void procesa(Procesamiento p) {
			p.procesa(this);
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

	public static class Suma extends ExpAditiva {
		public Suma(Exp arg0, Exp arg1) {
			super(arg0, arg1);
		}

		public void procesa(Procesamiento p) {
			p.procesa(this);
		}
	}

	public Dec dec(StringLocalizado id, StringLocalizado val) {
		return new Dec(id, val);
	}

	public Decs decs_muchas(Decs decs, Dec dec) {
		return new Decs_muchas(decs, dec);
	}

	public Decs decs_una(Dec dec) {
		return new Decs_una(dec);
	}

	public Exp div(Exp arg0, Exp arg1) {
		return new Div(arg0, arg1);
	}

	public Exp id(StringLocalizado num) {
		return new Id(num);
	}

	public Exp mul(Exp arg0, Exp arg1) {
		return new Mul(arg0, arg1);
	}

	public Exp num(StringLocalizado num) {
		return new Num(num);
	}

	// Constructoras
	public Prog prog_con_decs(Exp exp, Decs decs) {
		return new Prog_con_decs(exp, decs);
	}

	public Prog prog_sin_decs(Exp exp) {
		return new Prog_sin_decs(exp);
	}

	public Exp resta(Exp arg0, Exp arg1) {
		return new Resta(arg0, arg1);
	}

	public StringLocalizado str(String s, int fila, int col) {
		return new StringLocalizado(s, fila, col);
	}

	public Exp suma(Exp arg0, Exp arg1) {
		return new Suma(arg0, arg1);
	}
}

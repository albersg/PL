package implementacion.tiny;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import implementacion.asint.TinyASint.Prog;
import implementacion.c_ast_ascendente.AnalizadorLexico;
import implementacion.c_ast_ascendente.ClaseLexica;
import implementacion.c_ast_ascendente.GestionErrores;
import implementacion.c_ast_ascendente.UnidadLexica;
import implementacion.c_ast_ascendente.*;
import implementacion.maquinaP.MaquinaP;
import implementacion.procesamientos.AsignacionEspacio;
import implementacion.procesamientos.ChequeoTipo;
import implementacion.procesamientos.Etiquetado;
import implementacion.procesamientos.GeneracionCodigo;
import implementacion.procesamientos.Vinculacion;

public class Main {

	public static void main(String[] args) throws Exception {
		if (args[0].equals("-lex")) {
			ejecuta_lexico(args[1]);
		} else {
			Prog prog = null;
			
			if (args[0].equals("-asc"))
	            prog = ejecuta_ascendente(args[1]);
	        //else if (args[0].equals("-desc"))
	            //prog = ejecuta_descendente(args[1]);*/
			
			try {
				prog.procesa(new Vinculacion());
			} catch (Exception e) {
				return;
			}

			try {
				prog.procesa(new ChequeoTipo());
			} catch (Exception e) {
				return;
			}

			prog.procesa(new AsignacionEspacio());
			prog.procesa(new Etiquetado());

			MaquinaP maquinaP = new MaquinaP(prog.getTam(), 10, 10, 10);
			prog.procesa(new GeneracionCodigo(maquinaP));

			System.out.println("Codigo de la MaquinaP");
			maquinaP.muestraCodigo();
			maquinaP.ejecuta();
			System.out.println("Estado de la MaquinaP");
			maquinaP.muestraEstado();
		}
	}

	private static void ejecuta_lexico(String in) throws Exception {
		Reader input = new InputStreamReader(new FileInputStream(in));
		AnalizadorLexico alex = new AnalizadorLexico(input);
		GestionErrores errores = new GestionErrores();
		alex.fijaGestionErrores(errores);
		UnidadLexica t = (UnidadLexica) alex.next_token();
		while (t.clase() != ClaseLexica.EOF) {
			System.out.println(t);
			t = (UnidadLexica) alex.next_token();
		}
	}

	private static Prog ejecuta_ascendente(String in) throws Exception {
		Reader input = new InputStreamReader(new FileInputStream(in));
		AnalizadorLexico alex = new AnalizadorLexico(input);
		ConstructorAST constructorast = new ConstructorAST(alex);
		return (Prog) constructorast.parse().value;
	}
	/*
	 * private static Prog ejecuta_descendente(String in) throws Exception { Reader
	 * input = new InputStreamReader(new FileInputStream(in));
	 * c_ast_descendente.ConstructorAST constructorast = new
	 * c_ast_descendente.ConstructorAST(input); return constructorast.Init(); }
	 */
}

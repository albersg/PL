package implementacion.c_ast_ascendente;

import implementacion.asint.TinyASint.StringLocalizado;
import java_cup.runtime.Symbol;

public class UnidadLexica extends Symbol {
   public UnidadLexica(int fila, int col, int clase, String lexema) {
     super(clase,null);
     value = new StringLocalizado(lexema,fila,col);
   }
   public int clase () {return sym;}
   public StringLocalizado lexema() {return (StringLocalizado)value;}
   public String toString() {
       StringLocalizado lexema = (StringLocalizado)value;
       return "[clase="+clase2string(sym)+",lexema="+lexema+",fila="+lexema.fila()+",col="+lexema.col()+"]";
   }
   
	
   private String clase2string(int clase) {
       switch(clase) {
         case ClaseLexica.ID: return "ID";
         case ClaseLexica.LITENT: return "LITENT";
         case ClaseLexica.LITREAL: return "LITREAL";
         case ClaseLexica.LITSTR: return "LITSTR";
         case ClaseLexica.MAS: return "MAS";
         case ClaseLexica.MUL: return "MUL";
         case ClaseLexica.DIV: return "DIV";
         case ClaseLexica.MOD: return "MOD";
         case ClaseLexica.MENOR: return "MENOR";
         case ClaseLexica.MAYOR: return "MAYOR";
         case ClaseLexica.MENORIG: return "MENORIG";
         case ClaseLexica.MAYORIG: return "MAYORIG";
         case ClaseLexica.IGUAL: return "IGUAL";
         case ClaseLexica.ASIG: return "ASIG";
         case ClaseLexica.DIST: return "DIST";
         case ClaseLexica.PAR_AP: return "PAR_AP";
         case ClaseLexica.PAR_CIER: return "PAR_CIER";
         case ClaseLexica.PUNTOCOMA: return "PUNTOCOMA";
         case ClaseLexica.CORCH_CIER: return "CORCH_CIER";
         case ClaseLexica.CORCH_AP: return "CORCH_AP";
         case ClaseLexica.PUNTO: return "PUNTO";
         case ClaseLexica.CIRCUN: return "CIRCUN";
         case ClaseLexica.COMA: return "COMA";
         case ClaseLexica.INT: return "INT";
         case ClaseLexica.REAL: return "REAL";
         case ClaseLexica.BOOL: return "BOOL";
         case ClaseLexica.AND: return "AND";
         case ClaseLexica.STRING: return "STRING";
         case ClaseLexica.OR: return "OR";
         case ClaseLexica.MENOS: return "MENOS";
         case ClaseLexica.NOT: return "NOT";
         case ClaseLexica.NULL: return "NULL";
         case ClaseLexica.TRUE: return "TRUE";
         case ClaseLexica.FALSE: return "FALSE";
         case ClaseLexica.PROC: return "PROC";
         case ClaseLexica.IF: return "IF";
         case ClaseLexica.THEN: return "THEN";
         case ClaseLexica.ELSE: return "ELSE";
         case ClaseLexica.WHILE: return "WHILE";
         case ClaseLexica.DO: return "DO";
         case ClaseLexica.SEQ: return "SEQ";
         case ClaseLexica.BEGIN: return "BEGIN";
         case ClaseLexica.END: return "END";
         case ClaseLexica.RECORD: return "RECORD";
         case ClaseLexica.ARRAY: return "ARRAY";
         case ClaseLexica.OF: return "OF";
         case ClaseLexica.NEW: return "NEW";
         case ClaseLexica.DELETE: return "DELETE";
         case ClaseLexica.READ: return "READ";
         case ClaseLexica.WRITE: return "WRITE";
         case ClaseLexica.NL: return "NL";
         case ClaseLexica.VAR: return "VAR";
         case ClaseLexica.TYPE: return "TYPE";
         case ClaseLexica.DOSPUNTOS: return "DOSPUNTOS";
         default: return "?";               
       }
    }
}

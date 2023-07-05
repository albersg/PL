package implementacion.asint;

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
import implementacion.asint.TinyASint.PForms;
import implementacion.asint.TinyASint.PReal;
import implementacion.asint.TinyASint.PReal_muchos;
import implementacion.asint.TinyASint.PReal_ninguno;
import implementacion.asint.TinyASint.PReal_uno;
import implementacion.asint.TinyASint.PReals;
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
import implementacion.asint.TinyASint.True;
import implementacion.asint.TinyASint.While;
import implementacion.asint.TinyASint.Write;

public interface Procesamiento {
	
	void procesa(Dec dec)throws Exception;
	
	void procesa(Decs_vacia dec_vacia)throws Exception;

	void procesa(Decs_muchas decs) throws Exception;

	void procesa(Decs_una decs)throws Exception;
	
	void procesa(DecVar decVar) throws Exception;
	
	void procesa(DecProc decProc)throws Exception;
	
	void procesa(DecTipo decTipo) throws Exception;

	void procesa(Div exp)throws Exception;

	void procesa(Id exp) throws Exception;

	void procesa(Mul exp)throws Exception;

	void procesa(Resta exp)throws Exception;

	void procesa(Suma exp)throws Exception;

	void procesa(Prog prog)throws Exception;

	void procesa(TipoInt tipoInt)throws Exception;

	void procesa(TipoReal tipoReal)throws Exception;

	void procesa(TipoBool tipoBool)throws Exception;

	void procesa(TipoString tipoString)throws Exception;

	void procesa(TipoRef tipoRef) throws Exception;

	void procesa(TipoArray tipoArray)throws Exception;

	void procesa(TipoRecord tipoRecord)throws Exception;

	void procesa(TipoPointer tipoPointer)throws Exception;

	void procesa(PForms pForms)throws Exception;

	void procesa(PFormRef pFormRef) throws Exception;

	void procesa(Is_vacia is_vacia)throws Exception;

	void procesa(Is_muchas is_muchas)throws Exception;

	void procesa(Is_una is_una)throws Exception;

	void procesa(Campo campo)throws Exception;

	void procesa(Campos_muchos campos_muchos)throws Exception;

	void procesa(Campos_uno campos_uno)throws Exception;

	void procesa(Asig asig)throws Exception;

	void procesa(If_then if_then)throws Exception;

	void procesa(If_then_else if_then_else)throws Exception;

	void procesa(While while1)throws Exception;

	void procesa(Read read)throws Exception;

	void procesa(Write write)throws Exception;

	void procesa(Nl nl)throws Exception;

	void procesa(New new1)throws Exception;

	void procesa(Delete delete)throws Exception;

	void procesa(Call call)throws Exception;

	void procesa(IComp iComp)throws Exception;

	void procesa(PReals pReals)throws Exception;

	void procesa(LitInt litInt)throws Exception;

	void procesa(LitReal litReal)throws Exception;

	void procesa(LitStr litStr)throws Exception;

	void procesa(True true1)throws Exception;

	void procesa(Null null1)throws Exception;

	void procesa(Menor menor)throws Exception;

	void procesa(Mayor mayor)throws Exception;

	void procesa(False false1)throws Exception;

	void procesa(MayorIgual mayorIgual)throws Exception;

	void procesa(Igual igual)throws Exception;

	void procesa(Distinto distinto)throws Exception;

	void procesa(MenorIgual menorIgual)throws Exception;

	void procesa(And and)throws Exception;

	void procesa(Or or)throws Exception;

	void procesa(Modulo modulo)throws Exception;

	void procesa(Negativo negativo)throws Exception;

	void procesa(Not not)throws Exception;

	void procesa(Acc acc)throws Exception;

	void procesa(Indx indx)throws Exception;

	void procesa(Dref dref)throws Exception;

	void procesa(PForm pForm)throws Exception;
	
	void procesa(PForm_una pForm_una)throws Exception;

	void procesa(PForm_muchas pForm_muchas)throws Exception;

	void procesa(PForm_vacia pForm_vacia)throws Exception;
	
	void procesa(PReal_uno pReal_uno)throws Exception;

	void procesa(PReal_muchos pReal_muchos)throws Exception;

	void procesa(PReal_ninguno pReal_vacia)throws Exception;

	void recolecta_procs(Decs_una decs_una)throws Exception;
	void recolecta_procs(Decs_vacia decs_vacia)throws Exception;

	void recolecta_procs(Decs_muchas decs_muchas)throws Exception;

	void recolecta_procs(DecVar decVar)throws Exception;

	void recolecta_procs(DecTipo decTipo)throws Exception;

	void recolecta_procs(DecProc decProc)throws Exception;

	void vincula2(IComp iComp)throws Exception;

	void vincula2(DecProc decProc)throws Exception;

	void vincula2(DecTipo decTipo)throws Exception;

	void vincula2(DecVar decVar)throws Exception;

	void vincula2(Decs_una decs_una)throws Exception;

	void vincula2(Decs_muchas decs_muchas)throws Exception;

	void vincula2(Decs_vacia decs_vacia)throws Exception;

	void vincula2(PFormRef pFormRef)throws Exception;

	void vincula2(TipoRef tipoRef)throws Exception;

	void vincula2(TipoInt tipoInt)throws Exception;

	void vincula2(TipoReal tipoReal)throws Exception;

	void vincula2(TipoBool tipoBool)throws Exception;

	void vincula2(TipoString tipoString)throws Exception;

	void vincula2(TipoArray tipoArray)throws Exception;

	void vincula2(TipoRecord tipoRecord)throws Exception;

	void vincula2(TipoPointer tipoPointer)throws Exception;

	void vincula2(Campos_uno campos_uno)throws Exception;

	void vincula2(Campos_muchos campos_muchos)throws Exception;

	void vincula2(Campo campo)throws Exception;

	void vincula2(PForm_una pForm_una)throws Exception;

	void vincula2(PForm_muchas pForm_muchas)throws Exception;

	void vincula2(PForm_vacia pForm_vacia)throws Exception;

	void vincula2(PForms pForms)throws Exception;

	void asigna_espacio_tipo2(TipoPointer tipoPointer)throws Exception;

	void asigna_espacio_tipo1(TipoPointer tipoPointer)throws Exception;

	void asigna_espacio_tipo1(TipoRecord tipoRecord)throws Exception;

	void asigna_espacio_tipo1(TipoArray tipoArray)throws Exception;

	void asigna_espacio_tipo1(TipoRef tipoRef)throws Exception;

	void asigna_espacio_tipo1(TipoString tipoString)throws Exception;

	void asigna_espacio_tipo1(TipoBool tipoBool)throws Exception;

	void asigna_espacio_tipo1(TipoReal tipoReal)throws Exception;

	void asigna_espacio_tipo1(TipoInt tipoInt)throws Exception;

	void asiga_espacio_tipo(Tipo tipo)throws Exception;

	void procesa(PReal preal) throws Exception;
	

}
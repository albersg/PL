package implementacion.asint;

import implementacion.asint.TinyASint.Acc;
import implementacion.asint.TinyASint.And;
import implementacion.asint.TinyASint.Asig;
import implementacion.asint.TinyASint.Call;
import implementacion.asint.TinyASint.Campo;
import implementacion.asint.TinyASint.Campos_muchos;
import implementacion.asint.TinyASint.Campos_uno;
import implementacion.asint.TinyASint.Dec;
import implementacion.asint.TinyASint.Decs_vacia;
import implementacion.asint.TinyASint.Decs_muchas;
import implementacion.asint.TinyASint.Decs_una;
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
import implementacion.asint.TinyASint.PForms;
import implementacion.asint.TinyASint.PReals;
import implementacion.asint.TinyASint.Prog;
import implementacion.asint.TinyASint.Read;
import implementacion.asint.TinyASint.Resta;
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

public interface Procesamiento {
	
	void procesa(Dec dec);

	void procesa(Decs_muchas decs);

	void procesa(Decs_una decs);

	void procesa(Div exp);

	void procesa(Id exp);

	void procesa(Mul exp);

	void procesa(Resta exp);

	void procesa(Suma exp);

	void procesa(Prog prog);

	void procesa(Decs_vacia dec_vacia);

	void procesa(TipoInt tipoInt);

	void procesa(TipoReal tipoReal);

	void procesa(TipoBool tipoBool);

	void procesa(TipoString tipoString);

	void procesa(TipoRef tipoRef);

	void procesa(TipoArray tipoArray);

	void procesa(TipoRecord tipoRecord);

	void procesa(TipoPointer tipoPointer);

	void procesa(PForms pForms);

	void procesa(PFormRef pFormRef);

	void procesa(Is_vacia is_vacia);

	void procesa(Is_muchas is_muchas);

	void procesa(Is_una is_una);

	void procesa(Campo campo);

	void procesa(Campos_muchos campos_muchos);

	void procesa(Campos_uno campos_uno);

	void procesa(Asig asig);

	void procesa(If_then if_then);

	void procesa(If_then_else if_then_else);

	void procesa(While while1);

	void procesa(Read read);

	void procesa(Write write);

	void procesa(Nl nl);

	void procesa(New new1);

	void procesa(Delete delete);

	void procesa(Call call);

	void procesa(IComp iComp);

	void procesa(PReals pReals);

	void procesa(LitInt litInt);

	void procesa(LitReal litReal);

	void procesa(LitStr litStr);

	void procesa(True true1);

	void procesa(Null null1);

	void procesa(Menor menor);

	void procesa(Mayor mayor);

	void procesa(False false1);

	void procesa(MayorIgual mayorIgual);

	void procesa(Igual igual);

	void procesa(Distinto distinto);

	void procesa(MenorIgual menorIgual);

	void procesa(And and);

	void procesa(Or or);

	void procesa(Modulo modulo);

	void procesa(Negativo negativo);

	void procesa(Not not);

	void procesa(Acc acc);

	void procesa(Indx indx);

	void procesa(Dref dref);

	void procesa(PForm pForm);

}
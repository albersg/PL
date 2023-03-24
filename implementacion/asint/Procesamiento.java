package implementacion.asint;

import implementacion.asint.TinyASint.Campo;
import implementacion.asint.TinyASint.Campos_muchos;
import implementacion.asint.TinyASint.Campos_uno;
import implementacion.asint.TinyASint.Dec;
import implementacion.asint.TinyASint.Dec_vacia;
import implementacion.asint.TinyASint.Decs_muchas;
import implementacion.asint.TinyASint.Decs_una;
import implementacion.asint.TinyASint.Div;
import implementacion.asint.TinyASint.Id;
import implementacion.asint.TinyASint.Is_muchas;
import implementacion.asint.TinyASint.Is_una;
import implementacion.asint.TinyASint.Is_vacia;
import implementacion.asint.TinyASint.Mul;
import implementacion.asint.TinyASint.Num;
import implementacion.asint.TinyASint.PFormRef;
import implementacion.asint.TinyASint.PForms;
import implementacion.asint.TinyASint.Prog;
import implementacion.asint.TinyASint.Prog_con_decs;
import implementacion.asint.TinyASint.Prog_sin_decs;
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

public interface Procesamiento {
	void procesa(Dec dec);

	void procesa(Decs_muchas decs);

	void procesa(Decs_una decs);

	void procesa(Div exp);

	void procesa(Id exp);

	void procesa(Mul exp);

	void procesa(Num exp);

	void procesa(Prog_con_decs prog);

	void procesa(Prog_sin_decs prog);

	void procesa(Resta exp);

	void procesa(Suma exp);

	void procesa(Prog prog);

	void procesa(Dec_vacia dec_vacia);

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
}
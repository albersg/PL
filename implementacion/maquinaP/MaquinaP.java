package implementacion.maquinaP;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;


public class MaquinaP {
	public static class EAccesoAMemoriaNoInicializada extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public EAccesoAMemoriaNoInicializada(int pc, int dir) {
			super("pinst:" + pc + " dir:" + dir);
		}
	}

	public static class EAccesoFueraDeRango extends RuntimeException {
		private static final long serialVersionUID = 1L;
	}

	public static class EAccesoIlegitimo extends RuntimeException {
		private static final long serialVersionUID = 1L;
	}

	private class IActiva implements Instruccion {
		private int nivel;
		private int tamdatos;
		private int dirretorno;

		public IActiva(int nivel, int tamdatos, int dirretorno) {
			this.nivel = nivel;
			this.tamdatos = tamdatos;
			this.dirretorno = dirretorno;
		}

		public void ejecuta() {
			int base = gestorPilaActivaciones.creaRegistroActivacion(tamdatos);
			datos[base] = new ValorInt(dirretorno);
			datos[base + 1] = new ValorInt(gestorPilaActivaciones.display(nivel));
			pilaEvaluacion.push(new ValorInt(base + 2));
			pc++;
		}

		public String toString() {
			return "activa(" + nivel + "," + tamdatos + "," + dirretorno + ")";
		}
	}
	private class IAlloc implements Instruccion {
		private int tam;

		public IAlloc(int tam) {
			this.tam = tam;
		}

		public void ejecuta() {
			int inicio = gestorMemoriaDinamica.alloc(tam);
			pilaEvaluacion.push(new ValorInt(inicio));
			pc++;
		}

		public String toString() {
			return "alloc(" + tam + ")";
		};
	}

	private class IApilaBool implements Instruccion {
		private boolean valor;

		public IApilaBool(boolean valor) {
			this.valor = valor;
		}

		public void ejecuta() {
			pilaEvaluacion.push(new ValorBool(valor));
			pc++;
		}

		public String toString() {
			return "apilaBool(" + valor + ")";
		};
	}

	private class IApilad implements Instruccion {
		private int nivel;

		public IApilad(int nivel) {
			this.nivel = nivel;
		}

		public void ejecuta() {
			pilaEvaluacion.push(new ValorInt(gestorPilaActivaciones.display(nivel)));
			pc++;
		}

		public String toString() {
			return "apilad(" + nivel + ")";
		}

	}

	private class IApilaind implements Instruccion {
		public void ejecuta() {
			int dir = pilaEvaluacion.pop().valorInt();
			if (dir >= datos.length)
				throw new EAccesoFueraDeRango();
			if (datos[dir] == null)
				throw new EAccesoAMemoriaNoInicializada(pc, dir);
			pilaEvaluacion.push(datos[dir]);
			pc++;
		}

		public String toString() {
			return "apilaind";
		};
	}
	private class IApilaInt implements Instruccion {
		private int valor;

		public IApilaInt(int valor) {
			this.valor = valor;
		}

		public void ejecuta() {
			pilaEvaluacion.push(new ValorInt(valor));
			pc++;
		}

		public String toString() {
			return "apilaInt(" + valor + ")";
		};
	}
	private class IApilaReal implements Instruccion {
		private float valor;

		public IApilaReal(float valor) {
			this.valor = valor;
		}

		public void ejecuta() {
			pilaEvaluacion.push(new ValorReal(valor));
			pc++;
		}

		public String toString() {
			return "apilaReal(" + valor + ")";
		};
	}
	private class IApilaString implements Instruccion {
		private String valor;

		public IApilaString(String valor) {
			this.valor = valor;
		}

		public void ejecuta() {
			pilaEvaluacion.push(new ValorString(valor));
			pc++;
		}

		public String toString() {
			return "apilaString(" + valor + ")";
		};
	}
	private class IDealloc implements Instruccion {
		private int tam;

		public IDealloc(int tam) {
			this.tam = tam;
		}

		public void ejecuta() {
			int inicio = pilaEvaluacion.pop().valorInt();
			gestorMemoriaDinamica.free(inicio, tam);
			pc++;
		}

		public String toString() {
			return "dealloc(" + tam + ")";
		};
	}
	private class IDesactiva implements Instruccion {
		private int nivel;
		private int tamdatos;

		public IDesactiva(int nivel, int tamdatos) {
			this.nivel = nivel;
			this.tamdatos = tamdatos;
		}

		public void ejecuta() {
			int base = gestorPilaActivaciones.liberaRegistroActivacion(tamdatos);
			gestorPilaActivaciones.fijaDisplay(nivel, datos[base + 1].valorInt());
			pilaEvaluacion.push(datos[base]);
			pc++;
		}

		public String toString() {
			return "desactiva(" + nivel + "," + tamdatos + ")";
		}

	}

	private class IDesapilad implements Instruccion {
		private int nivel;

		public IDesapilad(int nivel) {
			this.nivel = nivel;
		}

		public void ejecuta() {
			gestorPilaActivaciones.fijaDisplay(nivel, pilaEvaluacion.pop().valorInt());
			pc++;
		}

		public String toString() {
			return "setd(" + nivel + ")";
		}
	}

	private class IDesapilaind implements Instruccion {
		public void ejecuta() {
			Valor valor = pilaEvaluacion.pop();
			int dir = pilaEvaluacion.pop().valorInt();
			if (dir >= datos.length)
				throw new EAccesoFueraDeRango();
			datos[dir] = valor;
			pc++;
		}

		public String toString() {
			return "desapilaind";
		};
	}

	private class IDup implements Instruccion {
		public void ejecuta() {
			pilaEvaluacion.push(pilaEvaluacion.peek());
			pc++;
		}

		public String toString() {
			return "dup";
		}
	}

	private class IIrA implements Instruccion {
		private int dir;

		public IIrA(int dir) {
			this.dir = dir;
		}

		public void ejecuta() {
			pc = dir;
		}

		public String toString() {
			return "ira(" + dir + ")";
		};
	}

	private class IIrF implements Instruccion {
		private int dir;

		public IIrF(int dir) {
			this.dir = dir;
		}

		public void ejecuta() {
			if (!pilaEvaluacion.pop().valorBool()) {
				pc = dir;
			} else {
				pc++;
			}
		}

		public String toString() {
			return "irf(" + dir + ")";
		};
	}

	private class IIrind implements Instruccion {
		public void ejecuta() {
			pc = pilaEvaluacion.pop().valorInt();
		}

		public String toString() {
			return "irind";
		}
	}

	private class IMueve implements Instruccion {
		private int tam;

		public IMueve(int tam) {
			this.tam = tam;
		}

		public void ejecuta() {
			int dirOrigen = pilaEvaluacion.pop().valorInt();
			int dirDestino = pilaEvaluacion.pop().valorInt();
			if ((dirOrigen + (tam - 1)) >= datos.length)
				throw new EAccesoFueraDeRango();
			if ((dirDestino + (tam - 1)) >= datos.length)
				throw new EAccesoFueraDeRango();
			for (int i = 0; i < tam; i++)
				datos[dirDestino + i] = datos[dirOrigen + i];
			pc++;
		}

		public String toString() {
			return "mueve(" + tam + ")";
		};
	}


	public interface Instruccion {
		void ejecuta();
	}

	private class IStop implements Instruccion {
		public void ejecuta() {
			pc = codigoP.size();
		}

		public String toString() {
			return "stop";
		}
	}

	private class ISumaInt implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorInt(opnd1.valorInt() + opnd2.valorInt()));
			pc++;
		}

		public String toString() {
			return "sumaInt";
		};
	}
	
	private class ISumaReal implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorReal(opnd1.valorReal() + opnd2.valorReal()));
			pc++;
		}

		public String toString() {
			return "sumaReal";
		};
	}
	
	private class IRestaInt implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorInt(opnd1.valorInt() - opnd2.valorInt()));
			pc++;
		}

		public String toString() {
			return "restaInt";
		};
	}
	
	private class IRestaReal implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorReal(opnd1.valorReal() - opnd2.valorReal()));
			pc++;
		}

		public String toString() {
			return "restaReal";
		};
	}
	
	private class IMulInt implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorInt(opnd1.valorInt() * opnd2.valorInt()));
			pc++;
		}

		public String toString() {
			return "mulInt";
		};
	}
	
	private class IMulReal implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorReal(opnd1.valorReal() * opnd2.valorReal()));
			pc++;
		}

		public String toString() {
			return "mulReal";
		};
	}
	
	private class IDivInt implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorInt(opnd1.valorInt() / opnd2.valorInt()));
			pc++;
		}

		public String toString() {
			return "divInt";
		};
	}
	
	private class IDivReal implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorReal(opnd1.valorReal() / opnd2.valorReal()));
			pc++;
		}

		public String toString() {
			return "divReal";
		};
	}
	
	private class IMod implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorInt(opnd1.valorInt() % opnd2.valorInt()));
			pc++;
		}

		public String toString() {
			return "mod";
		};
	}
	
	private class IAnd implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorBool(opnd1.valorBool() && opnd2.valorBool()));
			pc++;
		}

		public String toString() {
			return "and";
		};
	}
	
	private class IOr implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorBool(opnd1.valorBool() || opnd2.valorBool()));
			pc++;
		}

		public String toString() {
			return "or";
		};
	}
	
	private class IMenorInt implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorBool(opnd1.valorInt() < opnd2.valorInt()));
			pc++;
		}

		public String toString() {
			return "menorInt";
		};
	}
	
	private class IMenorReal implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorBool(opnd1.valorReal() < opnd2.valorReal()));
			pc++;
		}

		public String toString() {
			return "menorReal";
		};
	}
	
	private class IMenorString implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorBool(opnd1.valorString().compareTo(opnd2.valorString()) < 0));
			pc++;
		}

		public String toString() {
			return "menorString";
		};
	}
	
	private class IMenorBool implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorBool(Boolean.compare(opnd1.valorBool(), opnd2.valorBool()) < 0));
			pc++;
		}

		public String toString() {
			return "menorBool";
		};
	}
	
	private class IMayorInt implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorBool(opnd1.valorInt() > opnd2.valorInt()));
			pc++;
		}

		public String toString() {
			return "mayorInt";
		};
	}
	
	private class IMayorReal implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorBool(opnd1.valorReal() > opnd2.valorReal()));
			pc++;
		}

		public String toString() {
			return "mayorReal";
		};
	}
	
	private class IMayorString implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorBool(opnd1.valorString().compareTo(opnd2.valorString()) > 0));
			pc++;
		}

		public String toString() {
			return "mayorString";
		};
	}
	
	private class IMayorBool implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorBool(Boolean.compare(opnd1.valorBool(), opnd2.valorBool()) > 0));
			pc++;
		}

		public String toString() {
			return "mayorBool";
		};
	}
	
	private class IMenorIgualInt implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorBool(opnd1.valorInt() <= opnd2.valorInt()));
			pc++;
		}

		public String toString() {
			return "menorIgualInt";
		};
	}
	
	private class IMenorIgualReal implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorBool(opnd1.valorReal() <= opnd2.valorReal()));
			pc++;
		}

		public String toString() {
			return "menorIgualReal";
		};
	}
	
	private class IMenorIgualString implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorBool(opnd1.valorString().compareTo(opnd2.valorString()) <= 0));
			pc++;
		}

		public String toString() {
			return "menorIgualString";
		};
	}
	
	private class IMenorIgualBool implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorBool(Boolean.compare(opnd1.valorBool(), opnd2.valorBool()) <= 0));
			pc++;
		}

		public String toString() {
			return "menorIgualBool";
		};
	}
	
	private class IMayorIgualInt implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorBool(opnd1.valorInt() >= opnd2.valorInt()));
			pc++;
		}

		public String toString() {
			return "mayorIgualInt";
		};
	}
	
	private class IMayorIgualReal implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorBool(opnd1.valorReal() >= opnd2.valorReal()));
			pc++;
		}

		public String toString() {
			return "mayorIgualReal";
		};
	}
	
	private class IMayorIgualString implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorBool(opnd1.valorString().compareTo(opnd2.valorString()) >= 0));
			pc++;
		}

		public String toString() {
			return "mayorIgualString";
		};
	}
	
	private class IMayorIgualBool implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorBool(Boolean.compare(opnd1.valorBool(), opnd2.valorBool()) >= 0));
			pc++;
		}

		public String toString() {
			return "mayorIgualBool";
		};
	}
	
	private class IIgualInt implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorBool(opnd1.valorInt() == opnd2.valorInt()));
			pc++;
		}

		public String toString() {
			return "igualInt";
		};
	}
	
	private class IIgualReal implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorBool(opnd1.valorReal() == opnd2.valorReal()));
			pc++;
		}

		public String toString() {
			return "igualReal";
		};
	}
	
	private class IIgualString implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorBool(opnd1.valorString().compareTo(opnd2.valorString()) == 0));
			pc++;
		}

		public String toString() {
			return "igualString";
		};
	}
	
	private class IIgualBool implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorBool(Boolean.compare(opnd1.valorBool(), opnd2.valorBool()) == 0));
			pc++;
		}

		public String toString() {
			return "igualBool";
		};
	}
	
	private class IDistintoInt implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorBool(opnd1.valorInt() != opnd2.valorInt()));
			pc++;
		}

		public String toString() {
			return "distintoInt";
		};
	}
	
	private class IDistintoReal implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorBool(opnd1.valorReal() != opnd2.valorReal()));
			pc++;
		}

		public String toString() {
			return "distintoReal";
		};
	}
	
	private class IDistintoString implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorBool(opnd1.valorString().compareTo(opnd2.valorString()) != 0));
			pc++;
		}

		public String toString() {
			return "distintoString";
		};
	}
	
	private class IDistintoBool implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorBool(Boolean.compare(opnd1.valorBool(), opnd2.valorBool()) != 0));
			pc++;
		}

		public String toString() {
			return "distintoBool";
		};
	}
	
	private class INegativoInt implements Instruccion {
		public void ejecuta() {
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorInt(-opnd1.valorInt()));
			pc++;
		}

		public String toString() {
			return "negativoInt";
		};
	}

	private class INegativoReal implements Instruccion {
		public void ejecuta() {
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorReal(-opnd1.valorReal()));
			pc++;
		}

		public String toString() {
			return "negativoReal";
		};
	}
	
	private class INot implements Instruccion {
		public void ejecuta() {
			Valor opnd1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorBool(!opnd1.valorBool()));
			pc++;
		}

		public String toString() {
			return "not";
		};
	}
	
	private class IReadInt implements Instruccion {
		public void ejecuta() {
			ValorInt opnd = new ValorInt(Integer.parseInt(in.nextLine()));
			pilaEvaluacion.push(opnd);
			pc++;
		}

		public String toString() {
			return "readInt";
		};
	}
	
	private class IReadReal implements Instruccion {
		public void ejecuta() {
			ValorReal opnd = new ValorReal(Double.parseDouble(in.nextLine()));
			pilaEvaluacion.push(opnd);
			pc++;
		}

		public String toString() {
			return "readReal";
		};
	}
	
	private class IReadString implements Instruccion {
		public void ejecuta() {
			ValorString opnd = new ValorString(in.nextLine());
			pilaEvaluacion.push(opnd);
			pc++;
		}

		public String toString() {
			return "readString";
		};
	}
	
	private class IWriteInt implements Instruccion {
		public void ejecuta() {
			System.out.print(pilaEvaluacion.pop().valorInt());
			pc++;
		}

		public String toString() {
			return "writeInt";
		};
	}
	
	private class IWriteReal implements Instruccion {
		public void ejecuta() {
			System.out.print(pilaEvaluacion.pop().valorReal());
			pc++;
		}

		public String toString() {
			return "writeReal";
		};
	}
	
	private class IWriteString implements Instruccion {
		public void ejecuta() {
			System.out.print(pilaEvaluacion.pop().valorString());
			pc++;
		}

		public String toString() {
			return "writeString";
		};
	}
	
	private class IWriteBool implements Instruccion {
		public void ejecuta() {
			System.out.print(pilaEvaluacion.pop().valorBool());
			pc++;
		}

		public String toString() {
			return "writeBool";
		};
	}
	
	private class IInt2Real implements Instruccion{
		public void ejecuta() {
			Valor opnd = pilaEvaluacion.pop();
			pilaEvaluacion.push(new ValorReal((double) opnd.valorInt()));
			pc++;
		}

		public String toString() {
			return "int2real";
		};
	}

	private class Valor {
		public boolean valorBool() {
			throw new EAccesoIlegitimo();
		}

		public int valorInt() {
			throw new EAccesoIlegitimo();
		}
		
		public double valorReal() {
			throw new EAccesoIlegitimo();
		}
		
		public String valorString() {
			throw new EAccesoIlegitimo();
		}
	}

	private class ValorBool extends Valor {
		private boolean valor;

		public ValorBool(boolean valor) {
			this.valor = valor;
		}

		public String toString() {
			return String.valueOf(valor);
		}

		public boolean valorBool() {
			return valor;
		}
	}

	private class ValorInt extends Valor {
		private int valor;

		public ValorInt(int valor) {
			this.valor = valor;
		}

		public String toString() {
			return String.valueOf(valor);
		}

		public int valorInt() {
			return valor;
		}
	}
	
	private class ValorReal extends Valor {
		private double valor;

		public ValorReal(double valor) {
			this.valor = valor;
		}

		public String toString() {
			return String.valueOf(valor);
		}

		public double valorReal() {
			return valor;
		}
	}
	
	private class ValorString extends Valor {
		private String valor;

		public ValorString(String valor) {
			this.valor = valor;
		}

		public String toString() {
			return String.valueOf(valor);
		}

		public String valorString() {
			return valor;
		}
	}

	public static void main(String[] args) {
		MaquinaP m = new MaquinaP(5, 10, 10, 2);

		/*
		 * int x; proc store(int v) { x = v } && call store(5)
		 */

		m.ponInstruccion(m.activa(1, 1, 8));
		m.ponInstruccion(m.dup());
		m.ponInstruccion(m.apilaInt(0));
		m.ponInstruccion(m.sumaInt());
		m.ponInstruccion(m.apilaInt(5));
		m.ponInstruccion(m.desapilaInd());
		m.ponInstruccion(m.desapilad(1));
		m.ponInstruccion(m.irA(9));
		m.ponInstruccion(m.stop());
		m.ponInstruccion(m.apilaInt(0));
		m.ponInstruccion(m.apilad(1));
		m.ponInstruccion(m.apilaInt(0));
		m.ponInstruccion(m.sumaInt());
		m.ponInstruccion(m.mueve(1));
		m.ponInstruccion(m.desactiva(1, 1));
		m.ponInstruccion(m.irInd());
		m.ejecuta();
		m.muestraEstado();
	}

	private GestorMemoriaDinamica gestorMemoriaDinamica;

	private GestorPilaActivaciones gestorPilaActivaciones;

	private List<Instruccion> codigoP;

	private Stack<Valor> pilaEvaluacion;

	private Valor[] datos;

	private int pc;

	private ISumaInt ISUMAINT;
	
	private ISumaReal ISUMAREAL;
	
	private IRestaInt IRESTAINT;
	
	private IRestaReal IRESTAREAL;
	
	private IMulInt IMULINT;
	
	private IMulReal IMULREAL;
	
	private IDivInt IDIVINT;
	
	private IDivReal IDIVREAL;
	
	private IMod IMOD;

	private IAnd IAND;
	
	private IOr IOR;
	
	private IMenorInt IMENORINT;
	
	private IMenorReal IMENORREAL;
	
	private IMenorString IMENORSTRING;
	
	private IMenorBool IMENORBOOL;
	
	private IMayorInt IMAYORINT;
	
	private IMayorReal IMAYORREAL;
	
	private IMayorString IMAYORSTRING;
	
	private IMayorBool IMAYORBOOL;
	
	private IMenorIgualInt IMENORIGUALINT;
	
	private IMenorIgualReal IMENORIGUALREAL;
	
	private IMenorIgualString IMENORIGUALSTRING;
	
	private IMenorIgualBool IMENORIGUALBOOL;
	
	private IMayorIgualInt IMAYORIGUALINT;
	
	private IMayorIgualReal IMAYORIGUALREAL;
	
	private IMayorIgualString IMAYORIGUALSTRING;
	
	private IMayorIgualBool IMAYORIGUALBOOL;
	
	private IIgualInt IIGUALINT;
	
	private IIgualReal IIGUALREAL;
	
	private IIgualString IIGUALSTRING;
	
	private IIgualBool IIGUALBOOL;
	
	private IDistintoInt IDISTINTOINT;
	
	private IDistintoReal IDISTINTOREAL;
	
	private IDistintoString IDISTINTOSTRING;
	
	private IDistintoBool IDISTINTOBOOL;
	
	private INegativoInt INEGATIVOINT;
	
	private INegativoReal INEGATIVOREAL;
	
	private IReadInt IREADINT;
		
	private IReadReal IREADREAL;
	
	private IReadString IREADSTRING;
	
	private IWriteInt IWRITEINT;
	
	private IWriteReal IWRITEREAL;
	
	private IWriteBool IWRITEBOOL;
	
	private IWriteString IWRITESTRING;

	private INot INOT;

	private IInt2Real IINT2REAL;
	
	private IApilaind IAPILAIND;

	private IDesapilaind IDESAPILAIND;

	private IDup IDUP;

	private Instruccion ISTOP;

	private Instruccion IIRIND;

	private int tamdatos;

	private int tamheap;

	private int ndisplays;
	
	private Scanner in;

	public MaquinaP(int tamdatos, int tampila, int tamheap, int ndisplays) {
		this.tamdatos = tamdatos;
		this.tamheap = tamheap;
		this.ndisplays = ndisplays;
		this.codigoP = new ArrayList<>();
		pilaEvaluacion = new Stack<>();
		datos = new Valor[tamdatos + tampila + tamheap];
		this.pc = 0;
		this.in = new Scanner(System.in);
		
		IINT2REAL = new IInt2Real();
		
		ISUMAINT = new ISumaInt();
		ISUMAREAL = new ISumaReal();
		IRESTAINT = new IRestaInt();
		IRESTAREAL = new IRestaReal();
		
		IAND = new IAnd();
		IOR = new IOr();
		
		IMULINT = new IMulInt();
		IMULREAL = new IMulReal();
		IDIVINT = new IDivInt();
		IDIVREAL = new IDivReal();
		IMOD = new IMod();
		
		IMENORINT = new IMenorInt();
		IMENORREAL = new IMenorReal();
		IMENORSTRING = new IMenorString();
		IMENORBOOL = new IMenorBool();
		IMAYORINT = new IMayorInt();
		IMAYORREAL = new IMayorReal();
		IMAYORSTRING = new IMayorString();
		IMAYORBOOL = new IMayorBool();
		
		IMENORIGUALINT = new IMenorIgualInt();
		IMENORIGUALREAL = new IMenorIgualReal();
		IMENORIGUALSTRING = new IMenorIgualString();
		IMENORIGUALBOOL = new IMenorIgualBool();
		IMAYORIGUALINT = new IMayorIgualInt();
		IMAYORIGUALREAL = new IMayorIgualReal();
		IMAYORIGUALSTRING = new IMayorIgualString();
		IMAYORIGUALBOOL = new IMayorIgualBool();
		
		IIGUALINT = new IIgualInt();
		IIGUALREAL = new IIgualReal();
		IIGUALBOOL = new IIgualBool();
		IIGUALSTRING = new IIgualString();
		
		IDISTINTOINT = new IDistintoInt();
		IDISTINTOREAL = new IDistintoReal();
		INOT = new INot();
		
		IREADINT = new IReadInt();
		IREADREAL = new IReadReal();
		IREADSTRING = new IReadString();
		IWRITEINT = new IWriteInt();
		IWRITEREAL = new IWriteReal();
		IWRITEBOOL = new IWriteBool();
		IWRITESTRING = new IWriteString();
		
		IAPILAIND = new IApilaind();
		IDESAPILAIND = new IDesapilaind();
		IIRIND = new IIrind();
		IDUP = new IDup();
		ISTOP = new IStop();
		gestorPilaActivaciones = new GestorPilaActivaciones(tamdatos, (tamdatos + tampila) - 1, ndisplays);
		gestorMemoriaDinamica = new GestorMemoriaDinamica(tamdatos + tampila, (tamdatos + tampila + tamheap) - 1);
	}

	public Instruccion activa(int nivel, int tam, int dirretorno) {
		return new IActiva(nivel, tam, dirretorno);
	}

	public Instruccion alloc(int tam) {
		return new IAlloc(tam);
	}

	public Instruccion apilaBool(boolean val) {
		return new IApilaBool(val);
	}

	public Instruccion apilad(int nivel) {
		return new IApilad(nivel);
	}

	public Instruccion apilaInd() {
		return IAPILAIND;
	}

	public Instruccion apilaInt(int val) {
		return new IApilaInt(val);
	}
	
	public Instruccion apilaReal(float val) {
		return new IApilaReal(val);
	}
	
	public Instruccion apilaString(String val) {
		return new IApilaString(val);
	}
	
	public Instruccion dealloc(int tam) {
		return new IDealloc(tam);
	}

	public Instruccion desactiva(int nivel, int tam) {
		return new IDesactiva(nivel, tam);
	}

	public Instruccion desapilad(int nivel) {
		return new IDesapilad(nivel);
	}

	public Instruccion desapilaInd() {
		return IDESAPILAIND;
	}

	public Instruccion dup() {
		return IDUP;
	}

	public void ejecuta() {
		while (pc != codigoP.size()) {
			codigoP.get(pc).ejecuta();
		}
	}

	public Instruccion irA(int dir) {
		return new IIrA(dir);
	}

	public Instruccion irF(int dir) {
		return new IIrF(dir);
	}

	public Instruccion irInd() {
		return IIRIND;
	}
	public void muestraCodigo() {
		System.out.println("CodigoP");
		for (int i = 0; i < codigoP.size(); i++) {
			System.out.println(" " + i + ":" + codigoP.get(i));
		}
	}
	public void muestraEstado() {
		System.out.println("Tam datos:" + tamdatos);
		System.out.println("Tam heap:" + tamheap);
		System.out.println("PP:" + gestorPilaActivaciones.pp());
		System.out.print("Displays:");
		for (int i = 1; i <= ndisplays; i++)
			System.out.print(i + ":" + gestorPilaActivaciones.display(i) + " ");
		System.out.println();
		System.out.println("Pila de evaluacion");
		for (int i = 0; i < pilaEvaluacion.size(); i++) {
			System.out.println(" " + i + ":" + pilaEvaluacion.get(i));
		}
		System.out.println("Datos");
		for (int i = 0; i < datos.length; i++) {
			System.out.println(" " + i + ":" + datos[i]);
		}
		System.out.println("PC:" + pc);
	}

	public Instruccion mueve(int tam) {
		return new IMueve(tam);
	}

	public void ponInstruccion(Instruccion i) {
		codigoP.add(i);
	}

	public Instruccion stop() {
		return ISTOP;
	}

	public Instruccion sumaInt() {
		return ISUMAINT;
	}
	
	public Instruccion sumaReal() {
		return ISUMAREAL;
	}
	
	public Instruccion restaInt() {
		return IRESTAINT;
	}
	
	public Instruccion restaReal() {
		return IRESTAREAL;
	}
	
	public Instruccion mulInt() {
		return IMULINT;
	}
	
	public Instruccion mulReal() {
		return IMULREAL;
	}
	
	public Instruccion divInt() {
		return IDIVINT;
	}
	
	public Instruccion divReal() {
		return IDIVREAL;
	}
	
	public Instruccion mod() {
		return IMOD;
	}
	
	public Instruccion and() {
		return IAND;
	}
	
	public Instruccion or() {
		return IOR;
	}
	
	public Instruccion menorInt() {
		return IMENORINT;
	}
	
	public Instruccion menorReal() {
		return IMENORREAL;
	}
	
	public Instruccion menorString() {
		return IMENORSTRING;
	}
	
	public Instruccion menorBool() {
		return IMENORBOOL;
	}
	
	public Instruccion mayorInt() {
		return IMAYORINT;
	}
	
	public Instruccion mayorReal() {
		return IMAYORREAL;
	}
	
	public Instruccion mayorString() {
		return IMAYORSTRING;
	}
	
	public Instruccion mayorBool() {
		return IMAYORBOOL;
	}
	
	public Instruccion menorIgualInt() {
		return IMENORIGUALINT;
	}
	
	public Instruccion menorIgualReal() {
		return IMENORIGUALREAL;
	}
	
	public Instruccion menorIgualString() {
		return IMENORIGUALSTRING;
	}
	
	public Instruccion menorIgualBool() {
		return IMENORIGUALBOOL;
	}
	
	public Instruccion mayorIgualInt() {
		return IMAYORIGUALINT;
	}
	
	public Instruccion mayorIgualReal() {
		return IMAYORIGUALREAL;
	}
	
	public Instruccion mayorIgualString() {
		return IMAYORIGUALSTRING;
	}
	
	public Instruccion mayorIgualBool() {
		return IMAYORIGUALBOOL;
	}
	
	public Instruccion igualInt() {
		return IIGUALINT;
	}
	
	public Instruccion igualReal() {
		return IIGUALREAL;
	}
	
	public Instruccion igualString() {
		return IIGUALSTRING;
	}
	
	public Instruccion igualBool() {
		return IIGUALBOOL;
	}
	
	public Instruccion distintoInt() {
		return IDISTINTOINT;
	}
	
	public Instruccion distintoReal() {
		return IDISTINTOREAL;
	}
	
	public Instruccion distintoString() {
		return IDISTINTOSTRING;
	}
	
	public Instruccion distintoBool() {
		return IDISTINTOBOOL;
	}
	
	public Instruccion negativoInt() {
		return INEGATIVOINT;
	}
	
	public Instruccion negativoReal() {
		return INEGATIVOREAL;
	}
	
	public Instruccion not() {
		return INOT;
	}
	
	public Instruccion readInt() {
		return IREADINT;
	}
	
	public Instruccion readReal() {
		return IREADREAL;
	}
	
	public Instruccion readString() {
		return IREADSTRING;
	}
	
	public Instruccion writeInt() {
		return IWRITEINT;
	}
	
	public Instruccion writeReal() {
		return IWRITEREAL;
	}
	
	public Instruccion writeBool() {
		return IWRITEBOOL;
	}
	
	public Instruccion writeString() {
		return IWRITESTRING;
	}
	
	public Instruccion int2real() {
		return IINT2REAL;
	}
}

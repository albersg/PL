/* Generated By:JavaCC: Do not edit this line. ConstructorAST.java */
package implementacion.c_ast_descendente;

import implementacion.asint.TinyASint.Campo;
import implementacion.asint.TinyASint.Campos;
import implementacion.asint.TinyASint.Dec;
import implementacion.asint.TinyASint.Decs;
import implementacion.asint.TinyASint.Exp;
import implementacion.asint.TinyASint.I;
import implementacion.asint.TinyASint.Is;
import implementacion.asint.TinyASint.PForm;
import implementacion.asint.TinyASint.PForms;
import implementacion.asint.TinyASint.PReal;
import implementacion.asint.TinyASint.PReals;
import implementacion.asint.TinyASint.Prog;
import implementacion.asint.TinyASint.StringLocalizado;
import implementacion.asint.TinyASint.Tipo;
import implementacion.semops.SemOps;


public class ConstructorAST implements ConstructorASTConstants {
   private SemOps sem = new SemOps();

  final public Prog Init() throws ParseException {
                    Prog prog;
    prog = Prog();
    jj_consume_token(0);
                                                    {if (true) return prog;}
    throw new Error("Missing return statement in function");
  }

  final public Prog Prog() throws ParseException {
                    Decs decs; Is insts;
    decs = Decs();
    insts = Insts();
    jj_consume_token(42);
                                                        {if (true) return sem.prog(decs,insts);}
    throw new Error("Missing return statement in function");
  }

  final public Decs Decs() throws ParseException {
                    Decs decs;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case Proc:
    case Var:
    case Type:
      decs = LDecs();
                                                         {if (true) return decs;}
      break;
    default:
      jj_la1[0] = jj_gen;
                                                                          {if (true) return sem.decs_vacia();}
    }
    throw new Error("Missing return statement in function");
  }

  final public Decs LDecs() throws ParseException {
                    Dec dec; Decs rldecs;
    dec = Dec();
    rldecs = RLDecs(sem.decs_una(dec));
                                                                                         {if (true) return rldecs;}
    throw new Error("Missing return statement in function");
  }

  final public Decs RLDecs(Decs rldecs0h) throws ParseException {
                                Dec dec; Decs rldecs1;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case Proc:
    case Var:
    case Type:
      dec = Dec();
      rldecs1 = RLDecs(sem.decs_muchas(rldecs0h, dec));
                                                                                                       {if (true) return rldecs1;}
      break;
    default:
      jj_la1[1] = jj_gen;
                                         {if (true) return rldecs0h;}
    }
    throw new Error("Missing return statement in function");
  }

  final public Dec Dec() throws ParseException {
                            Token id; Tipo tipo; PForms pforms; Decs decs; Is insts;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case Var:
      jj_consume_token(Var);
      id = jj_consume_token(Id);
      jj_consume_token(43);
      tipo = Tipo();
      jj_consume_token(44);
                                                                            {if (true) return sem.decVar(tipo, new StringLocalizado(id));}
      break;
    case Type:
      jj_consume_token(Type);
      id = jj_consume_token(Id);
      jj_consume_token(43);
      tipo = Tipo();
      jj_consume_token(44);
                                                                           {if (true) return sem.decTipo(tipo, new StringLocalizado(id));}
      break;
    case Proc:
      jj_consume_token(Proc);
      id = jj_consume_token(Id);
      pforms = PForms();
      decs = Decs();
      insts = Insts();
      jj_consume_token(44);
                                                                                                      {if (true) return sem.decProc(new StringLocalizado(id),pforms,decs,insts);}
      break;
    default:
      jj_la1[2] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final public Tipo TipoBase() throws ParseException {
                     Token id;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case Int:
      jj_consume_token(Int);
                                                {if (true) return sem.tipoInt();}
      break;
    case Real:
      jj_consume_token(Real);
                                                {if (true) return sem.tipoReal();}
      break;
    case String:
      jj_consume_token(String);
                                                  {if (true) return sem.tipoString();}
      break;
    case Bool:
      jj_consume_token(Bool);
                                                {if (true) return sem.tipoBool();}
      break;
    case Id:
      id = jj_consume_token(Id);
                                                 {if (true) return sem.tipoRef(new StringLocalizado(id));}
      break;
    default:
      jj_la1[3] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final public Tipo Tipo() throws ParseException {
                   Tipo tipo; Token id; Campos campos;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case Int:
    case Real:
    case Bool:
    case String:
    case Id:
      tipo = TipoBase();
                                                          {if (true) return tipo;}
      break;
    case Array:
      jj_consume_token(Array);
      jj_consume_token(45);
      id = jj_consume_token(Litent);
      jj_consume_token(46);
      jj_consume_token(Of);
      tipo = TipoBase();
                                                                                          {if (true) return sem.tipoArray(new StringLocalizado(id), tipo);}
      break;
    case Record:
      jj_consume_token(Record);
      campos = Campos();
      jj_consume_token(End);
                                                                        {if (true) return sem.tipoRecord(campos);}
      break;
    case 47:
      jj_consume_token(47);
      tipo = TipoBase();
                                                             {if (true) return sem.tipoPointer(tipo);}
      break;
    default:
      jj_la1[4] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final public PForms PForms() throws ParseException {
                    PForms pforms;
    jj_consume_token(48);
    pforms = LPFormsPar();
    jj_consume_token(49);
                                                                      {if (true) return pforms;}
    throw new Error("Missing return statement in function");
  }

  final public PForms LPFormsPar() throws ParseException {
                        PForms pforms;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case Var:
    case Id:
      pforms = LPForms();
                                                           {if (true) return pforms;}
      break;
    default:
      jj_la1[5] = jj_gen;
                                                                              {if (true) return sem.pForm_vacia();}
    }
    throw new Error("Missing return statement in function");
  }

  final public PForms LPForms() throws ParseException {
                     PForm pform; PForms pforms;
    pform = PForm();
    pforms = RLPForms(sem.pForm_una(pform));
                                                                                              {if (true) return pforms;}
    throw new Error("Missing return statement in function");
  }

  final public PForms RLPForms(PForms pforms0h) throws ParseException {
                                     PForm pform; PForms pforms1;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 50:
      jj_consume_token(50);
      pform = PForm();
      pforms1 = RLPForms(sem.pForm_muchas(pforms0h, pform));
                                                                                                                {if (true) return pforms1;}
      break;
    default:
      jj_la1[6] = jj_gen;
                                         {if (true) return pforms0h;}
    }
    throw new Error("Missing return statement in function");
  }

  final public PForm PForm() throws ParseException {
                  Token id; Tipo tipo;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case Var:
      jj_consume_token(Var);
      id = jj_consume_token(Id);
      jj_consume_token(43);
      tipo = Tipo();
                                                                        {if (true) return sem.pFormRef(new StringLocalizado(id), tipo);}
      break;
    case Id:
      id = jj_consume_token(Id);
      jj_consume_token(43);
      tipo = Tipo();
                                                                 {if (true) return sem.pForm(new StringLocalizado(id), tipo);}
      break;
    default:
      jj_la1[7] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final public PReals PReals() throws ParseException {
                    PReals preals;
    jj_consume_token(48);
    preals = LPRealsPar();
    jj_consume_token(49);
                                                                      {if (true) return preals;}
    throw new Error("Missing return statement in function");
  }

  final public PReals LPRealsPar() throws ParseException {
                        PReals preals;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case Not:
    case Null:
    case True:
    case False:
    case Id:
    case Litent:
    case Litreal:
    case Litstr:
    case 48:
    case 59:
      preals = LPReals();
                                                           {if (true) return preals;}
      break;
    default:
      jj_la1[8] = jj_gen;
                                                                              {if (true) return sem.pReal_ninguno();}
    }
    throw new Error("Missing return statement in function");
  }

  final public PReals LPReals() throws ParseException {
                     PReal preal; PReals preals;
    preal = PReal();
    preals = RLPReals(sem.pReal_uno(preal));
                                                                                              {if (true) return preals;}
    throw new Error("Missing return statement in function");
  }

  final public PReals RLPReals(PReals preals0h) throws ParseException {
                                     PReal preal; PReals preals1;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 50:
      jj_consume_token(50);
      preal = PReal();
      preals1 = RLPReals(sem.pReal_muchos(preals0h, preal));
                                                                                                                {if (true) return preals1;}
      break;
    default:
      jj_la1[9] = jj_gen;
                                         {if (true) return preals0h;}
    }
    throw new Error("Missing return statement in function");
  }

  final public PReal PReal() throws ParseException {
                  Exp exp;
    exp = Exp();
                                                      {if (true) return sem.pReal(exp);}
    throw new Error("Missing return statement in function");
  }

  final public Campos Campos() throws ParseException {
                    Campo campo; Campos campos;
    campo = Campo();
    campos = RCampos(sem.campos_uno(campo));
                                                                                              {if (true) return campos;}
    throw new Error("Missing return statement in function");
  }

  final public Campos RCampos(Campos campos0) throws ParseException {
                                   Campo campo; Campos campos1;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case Id:
      campo = Campo();
      campos1 = RCampos(sem.campos_muchos(campos0, campo));
                                                                                                           {if (true) return campos1;}
      break;
    default:
      jj_la1[10] = jj_gen;
                                         {if (true) return campos0;}
    }
    throw new Error("Missing return statement in function");
  }

  final public Campo Campo() throws ParseException {
                  Token id; Tipo tipo;
    id = jj_consume_token(Id);
    jj_consume_token(43);
    tipo = Tipo();
    jj_consume_token(44);
                                                                      {if (true) return sem.campo(tipo, new StringLocalizado(id));}
    throw new Error("Missing return statement in function");
  }

  final public Is Insts() throws ParseException {
              Is insts;
    jj_consume_token(Begin);
    insts = LInstsAux();
    jj_consume_token(End);
                                                                          {if (true) return insts;}
    throw new Error("Missing return statement in function");
  }

  final public Is LInstsAux() throws ParseException {
                  Is insts;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case Not:
    case Null:
    case True:
    case False:
    case If:
    case While:
    case Seq:
    case New:
    case Delete:
    case Read:
    case Write:
    case Nl:
    case Id:
    case Litent:
    case Litreal:
    case Litstr:
    case 48:
    case 59:
      insts = LInsts();
                                                         {if (true) return insts;}
      break;
    default:
      jj_la1[11] = jj_gen;
                                                                           {if (true) return sem.is_vacia();}
    }
    throw new Error("Missing return statement in function");
  }

  final public Is LInsts() throws ParseException {
                         I inst; Is insts;
    inst = Inst();
    insts = RLInsts(sem.is_una(inst));
                                                                                      {if (true) return insts;}
    throw new Error("Missing return statement in function");
  }

  final public Is RLInsts(Is insts0) throws ParseException {
                         I inst; Is insts1;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case Not:
    case Null:
    case True:
    case False:
    case If:
    case While:
    case Seq:
    case New:
    case Delete:
    case Read:
    case Write:
    case Nl:
    case Id:
    case Litent:
    case Litreal:
    case Litstr:
    case 48:
    case 59:
      inst = Inst();
      insts1 = RLInsts(sem.is_muchas(insts0, inst));
                                                                                                  {if (true) return insts1;}
      break;
    default:
      jj_la1[12] = jj_gen;
                                         {if (true) return insts0;}
    }
    throw new Error("Missing return statement in function");
  }

  final public void PuntoComaONada() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 44:
      jj_consume_token(44);
                                 {if (true) return;}
      break;
    default:
      jj_la1[13] = jj_gen;
                                             {if (true) return;}
    }
  }

  final public I Inst() throws ParseException {
            Exp e0, e1; Is insts0, insts1;I rinst, rinst2; Decs decs; PReals preals;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case Not:
    case Null:
    case True:
    case False:
    case Id:
    case Litent:
    case Litreal:
    case Litstr:
    case 48:
    case 59:
      e0 = Exp();
      rinst2 = RInst2(e0);
                                                             {if (true) return rinst2;}
      break;
    case If:
      jj_consume_token(If);
      e0 = Exp();
      jj_consume_token(Then);
      insts0 = LInsts();
      rinst = RInst(e0,insts0);
                                                                                             {if (true) return rinst;}
      break;
    case While:
      jj_consume_token(While);
      e0 = Exp();
      jj_consume_token(Do);
      insts0 = LInsts();
      jj_consume_token(End);
      PuntoComaONada();
                                                                                              {if (true) return sem.while1(e0, insts0);}
      break;
    case Read:
      jj_consume_token(Read);
      e0 = Exp();
      jj_consume_token(44);
                                                       {if (true) return sem.read(e0);}
      break;
    case Write:
      jj_consume_token(Write);
      e0 = Exp();
      jj_consume_token(44);
                                                        {if (true) return sem.write(e0);}
      break;
    case Nl:
      jj_consume_token(Nl);
      jj_consume_token(44);
                                          {if (true) return sem.nl();}
      break;
    case New:
      jj_consume_token(New);
      e0 = Exp();
      jj_consume_token(44);
                                                      {if (true) return sem.new1(e0);}
      break;
    case Delete:
      jj_consume_token(Delete);
      e0 = Exp();
      jj_consume_token(44);
                                                         {if (true) return sem.delete(e0);}
      break;
    case Seq:
      jj_consume_token(Seq);
      decs = Decs();
      insts0 = Insts();
      PuntoComaONada();
                                                                                   {if (true) return sem.iComp(decs,insts0);}
      break;
    default:
      jj_la1[14] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final public I RInst(Exp ah1, Is ah2) throws ParseException {
                            Is is;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case End:
      jj_consume_token(End);
      PuntoComaONada();
                                                         {if (true) return sem.if_then(ah1, ah2);}
      break;
    case Else:
      jj_consume_token(Else);
      is = LInsts();
      jj_consume_token(End);
      PuntoComaONada();
                                                                             {if (true) return sem.if_then_else(ah1, ah2, is);}
      break;
    default:
      jj_la1[15] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final public I RInst2(Exp ah) throws ParseException {
                    Is is;Exp e1; PReals preals;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 51:
      jj_consume_token(51);
      e1 = Exp();
      jj_consume_token(44);
                                                   {if (true) return sem.asig(ah,e1);}
      break;
    case 48:
      preals = PReals();
      jj_consume_token(44);
                                                     {if (true) return sem.call(ah ,preals);}
      break;
    default:
      jj_la1[16] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final public Exp Exp() throws ParseException {
             Exp e0;
    e0 = Exp0();
                                    {if (true) return e0;}
    throw new Error("Missing return statement in function");
  }

  final public Exp Exp0() throws ParseException {
              Exp e0, re1;
    e0 = Exp1();
    re1 = RExp0(e0);
                                                  {if (true) return re1;}
    throw new Error("Missing return statement in function");
  }

  final public Exp RExp0(Exp rexp0h) throws ParseException {
                         String op; Exp e1;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 52:
    case 53:
    case 54:
    case 55:
    case 56:
    case 57:
      op = Op0();
      e1 = Exp1();
                                             {if (true) return sem.expb(op, rexp0h, e1);}
      break;
    default:
      jj_la1[17] = jj_gen;
                                                                                  {if (true) return rexp0h;}
    }
    throw new Error("Missing return statement in function");
  }

  final public String Op0() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 52:
      jj_consume_token(52);
                              {if (true) return "<";}
      break;
    case 53:
      jj_consume_token(53);
                             {if (true) return ">";}
      break;
    case 54:
      jj_consume_token(54);
                              {if (true) return "<=";}
      break;
    case 55:
      jj_consume_token(55);
                              {if (true) return ">=";}
      break;
    case 56:
      jj_consume_token(56);
                              {if (true) return "==";}
      break;
    case 57:
      jj_consume_token(57);
                              {if (true) return "!=";}
      break;
    default:
      jj_la1[18] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final public Exp Exp1() throws ParseException {
              Exp e2, rexp1, rexp1p;
    e2 = Exp2();
    rexp1 = RExp1(e2);
    rexp1p = RExp1P(rexp1);
                                                                           {if (true) return rexp1p;}
    throw new Error("Missing return statement in function");
  }

  final public Exp RExp1(Exp rexp10) throws ParseException {
                         Exp e2;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 58:
      jj_consume_token(58);
      e2 = Exp2();
                                        {if (true) return sem.expb("+", rexp10, e2);}
      break;
    default:
      jj_la1[19] = jj_gen;
                         {if (true) return rexp10;}
    }
    throw new Error("Missing return statement in function");
  }

  final public Exp RExp1P(Exp rexp1p0) throws ParseException {
                           Exp e2, rexp1p1;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 59:
      jj_consume_token(59);
      e2 = Exp2();
      rexp1p1 = RExp1P(sem.expb("-", rexp1p0, e2));
                                                                                   {if (true) return rexp1p1;}
      break;
    default:
      jj_la1[20] = jj_gen;
                         {if (true) return rexp1p0;}
    }
    throw new Error("Missing return statement in function");
  }

  final public Exp Exp2() throws ParseException {
              Exp e3, rexp2;
    e3 = Exp3();
    rexp2 = RExp2(e3);
                                                    {if (true) return rexp2;}
    throw new Error("Missing return statement in function");
  }

  final public Exp RExp2(Exp rexp20) throws ParseException {
                         Exp e2, e3;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case And:
      jj_consume_token(And);
      e3 = Exp3();
                                          {if (true) return sem.expb("and", rexp20, e3);}
      break;
    case Or:
      jj_consume_token(Or);
      e2 = Exp2();
                                        {if (true) return sem.expb("or", rexp20, e2);}
      break;
    default:
      jj_la1[21] = jj_gen;
                         {if (true) return rexp20;}
    }
    throw new Error("Missing return statement in function");
  }

  final public Exp Exp3() throws ParseException {
              Exp e4, rexp3;
    e4 = Exp4();
    rexp3 = RExp3(e4);
                                                    {if (true) return rexp3;}
    throw new Error("Missing return statement in function");
  }

  final public Exp RExp3(Exp rexp30) throws ParseException {
                         String op; Exp e4, rexp31;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 60:
    case 61:
    case 62:
      op = Op3();
      e4 = Exp4();
      rexp31 = RExp3(sem.expb(op, rexp30, e4));
                                                                                    {if (true) return rexp31;}
      break;
    default:
      jj_la1[22] = jj_gen;
                         {if (true) return rexp30;}
    }
    throw new Error("Missing return statement in function");
  }

  final public String Op3() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 60:
      jj_consume_token(60);
                              {if (true) return "/";}
      break;
    case 61:
      jj_consume_token(61);
                             {if (true) return "*";}
      break;
    case 62:
      jj_consume_token(62);
                             {if (true) return "%";}
      break;
    default:
      jj_la1[23] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final public Exp Exp4() throws ParseException {
              String op; Exp e4, e5;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case Not:
    case 59:
      op = Op4();
      e4 = Exp4();
                                                 {if (true) return sem.expu(op, e4);}
      break;
    case Null:
    case True:
    case False:
    case Id:
    case Litent:
    case Litreal:
    case Litstr:
    case 48:
      e5 = Exp5();
                                   {if (true) return e5;}
      break;
    default:
      jj_la1[24] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final public String Op4() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 59:
      jj_consume_token(59);
                              {if (true) return "-";}
      break;
    case Not:
      jj_consume_token(Not);
                               {if (true) return "not";}
      break;
    default:
      jj_la1[25] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final public Exp Exp5() throws ParseException {
              Exp e6, rexp5;
    e6 = Exp6();
    rexp5 = RExp5(e6);
                                                    {if (true) return rexp5;}
    throw new Error("Missing return statement in function");
  }

  final public Exp RExp5(Exp rexp50) throws ParseException {
                         Token id; Exp exp0, rexp51;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 45:
      jj_consume_token(45);
      exp0 = Exp0();
      jj_consume_token(46);
      rexp51 = RExp5(sem.indx(rexp50, exp0));
                                                                                   {if (true) return rexp51;}
      break;
    case 47:
      jj_consume_token(47);
      rexp51 = RExp5(sem.dref(rexp50));
                                                            {if (true) return rexp51;}
      break;
    case 42:
      jj_consume_token(42);
      id = jj_consume_token(Id);
      rexp51 = RExp5(sem.acc(rexp50, new StringLocalizado(id)));
                                                                                             {if (true) return rexp51;}
      break;
    default:
      jj_la1[26] = jj_gen;
                         {if (true) return rexp50;}
    }
    throw new Error("Missing return statement in function");
  }

  final public Exp Exp6() throws ParseException {
              Token id; Exp e;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case Litent:
      id = jj_consume_token(Litent);
                                      {if (true) return sem.litInt(new StringLocalizado(id));}
      break;
    case Litreal:
      id = jj_consume_token(Litreal);
                                      {if (true) return sem.litReal(new StringLocalizado(id));}
      break;
    case Litstr:
      id = jj_consume_token(Litstr);
                                     {if (true) return sem.litStr(new StringLocalizado(id));}
      break;
    case True:
      id = jj_consume_token(True);
                                   {if (true) return sem.true1(new StringLocalizado(id));}
      break;
    case False:
      id = jj_consume_token(False);
                                    {if (true) return sem.false1(new StringLocalizado(id));}
      break;
    case Id:
      id = jj_consume_token(Id);
                                 {if (true) return sem.id(new StringLocalizado(id));}
      break;
    case Null:
      id = jj_consume_token(Null);
                                   {if (true) return sem.null1(new StringLocalizado(id));}
      break;
    case 48:
      jj_consume_token(48);
      e = Exp0();
      jj_consume_token(49);
                                          {if (true) return e;}
      break;
    default:
      jj_la1[27] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  /** Generated Token Manager. */
  public ConstructorASTTokenManager token_source;
  SimpleCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private int jj_gen;
  final private int[] jj_la1 = new int[28];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
      jj_la1_init_0();
      jj_la1_init_1();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x80000,0x80000,0x80000,0x1e00,0x30001e00,0x0,0x0,0x0,0x78000,0x0,0x0,0x82978000,0x82978000,0x0,0x82978000,0x8400000,0x0,0x0,0x0,0x0,0x0,0x6000,0x0,0x0,0x78000,0x8000,0x0,0x70000,};
   }
   private static void jj_la1_init_1() {
      jj_la1_1 = new int[] {0x30,0x30,0x30,0x40,0x8040,0x50,0x40000,0x50,0x80103c0,0x40000,0x40,0x80103cf,0x80103cf,0x1000,0x80103cf,0x0,0x90000,0x3f00000,0x3f00000,0x4000000,0x8000000,0x0,0x70000000,0x70000000,0x80103c0,0x8000000,0xa400,0x103c0,};
   }

  /** Constructor with InputStream. */
  public ConstructorAST(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public ConstructorAST(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new ConstructorASTTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 28; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 28; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public ConstructorAST(java.io.Reader stream) {
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new ConstructorASTTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 28; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 28; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public ConstructorAST(ConstructorASTTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 28; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(ConstructorASTTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 28; i++) jj_la1[i] = -1;
  }

  private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }


/** Get the next Token. */
  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.List jj_expentries = new java.util.ArrayList();
  private int[] jj_expentry;
  private int jj_kind = -1;

  /** Generate ParseException. */
  public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[63];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 28; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
          if ((jj_la1_1[i] & (1<<j)) != 0) {
            la1tokens[32+j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 63; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = (int[])jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

}

package implementacion.c_ast_ascendente;

%%
%cup
%line
%column
%class AnalizadorLexico
%unicode
%public

%{
  private ALexOperations ops;
  private GestionErrores errores;
  public String lexema() {return yytext();}
  public int fila() {return yyline+1;}
  public int col()  {return yycolumn+1;}
  public void fijaGestionErrores(GestionErrores errores) {
   this.errores = errores;
  }
%}

%eofval{
  return ops.token(ClaseLexica.EOF);
%eofval}

%init{
  ops = new ALexOperations(this);
%init}


letra  = ([A-Z]|[a-z])
dpos = [1-9]
dig = ({dpos}|0)
pdec = \.({dig}*{dpos}|0)
pexp = (e|E){litent}
signo = (\+|\-)
id = {letra}({letra}|{dig}|_)*
litent = {signo}?(({dpos}{dig}*)|0)
litreal = {litent}({pdec}|{pexp}|{pdec}{pexp})
litstr = \'[^\'\b\r\n]*\'
mas = \+
menos = \-
mul = \*
div = \/
mod = %
menor = <
mayor = >
menorig = <=
mayorig = >=
igual = ==
asig = =
dist = \!=
par_ap = \(
par_cier = \)
puntocoma = ;
corch_ap = \[
corch_cier = \]
punto = .
dospuntos = :
circun = \^
coma = ,
int= (i|I)(n|N)(t|T)
real = (r|R)(e|E)(a|A)(l|L)
bool = (b|B)(o|O)(o|O)(l|L)
string = (s|S)(t|T)(r|R)(i|I)(n|N)(g|G)
and = (a|A)(n|N)(d|D)
or = (o|O)(r|R)
not = (n|N)(o|O)(t|T)
null = (n|N)(u|U)(l|L)(l|L)
true = (t|T)(r|R)(u|U)(e|E)
false = (f|F)(a|A)(l|L)(s|S)(e|E)
proc = (p|P)(r|R)(o|O)(c|C)
if = (i|I)(f|F)
then = (t|T)(h|H)(e|E)(n|N)
else = (e|E)(l|L)(s|S)(e|E)
while = (w|W)(h|H)(i|I)(l|L)(e|E)
do = (d|D)(o|O)
seq = (s|S)(e|E)(q|Q)
begin = (b|B)(e|E)(g|G)(i|I)(n|N)
end = (e|E)(n|N)(d|D)
record = (r|R)(e|E)(c|C)(o|O)(r|R)(d|D)
array = (a|A)(r|R)(r|R)(a|A)(y|Y)
of = (o|O)(f|F)
new = (n|N)(e|E)(w|W)
delete = (d|D)(e|E)(l|L)(e|E)(t|T)(e|E)
read = (r|R)(e|E)(a|A)(d|D)
write = (w|W)(r|R)(i|I)(t|T)(e|E)
nl = (n|N)(l|L)
var = (v|V)(a|A)(r|R)
type = (t|T)(y|Y)(p|P)(e|E)

separador = [ |\n|\r|\b|\t]
comentario = @[^\n]*


%%
{separador}                   {} 
{comentario}                    {}
{and}           {return ops.token(ClaseLexica.AND);}
{int}            {return ops.token(ClaseLexica.INT);}
{real}              {return ops.token(ClaseLexica.REAL);}
{bool}                  {return ops.token(ClaseLexica.BOOL);}
{string}                   {return ops.token(ClaseLexica.STRING);}
{or}               {return ops.token(ClaseLexica.OR);}
{not}              {return ops.token(ClaseLexica.NOT);}
{null}                  {return ops.token(ClaseLexica.NULL);}
{false}                   {return ops.token(ClaseLexica.FALSE);}
{true}           {return ops.token(ClaseLexica.TRUE);}
{proc}            {return ops.token(ClaseLexica.PROC);}
{if}               {return ops.token(ClaseLexica.IF);}
{then}              {return ops.token(ClaseLexica.THEN);}
{else}                  {return ops.token(ClaseLexica.ELSE);}
{while}                   {return ops.token(ClaseLexica.WHILE);}
{do}           {return ops.token(ClaseLexica.DO);}
{seq}            {return ops.token(ClaseLexica.SEQ);}
{begin}               {return ops.token(ClaseLexica.BEGIN);}
{end}              {return ops.token(ClaseLexica.END);}
{record}                  {return ops.token(ClaseLexica.RECORD);}
{array}                   {return ops.token(ClaseLexica.ARRAY);}
{of}           {return ops.token(ClaseLexica.OF);}
{new}              {return ops.token(ClaseLexica.NEW);}
{delete}            {return ops.token(ClaseLexica.DELETE);}
{read}           {return ops.token(ClaseLexica.READ);}
{write}  {return ops.token(ClaseLexica.WRITE);}
{nl}        {return ops.token(ClaseLexica.NL);}
{var}      {return ops.token(ClaseLexica.VAR);}
{type}        {return ops.token(ClaseLexica.TYPE);} 
{id}              {return ops.token(ClaseLexica.ID);}
{litstr}           {return ops.token(ClaseLexica.LITSTR);}
{litent}                  {return ops.token(ClaseLexica.LITENT);}
{litreal}                   {return ops.token(ClaseLexica.LITREAL);}
{circun}                  {return ops.token(ClaseLexica.CIRCUN);}
{coma}                   {return ops.token(ClaseLexica.COMA);}
{dospuntos}                   {return ops.token(ClaseLexica.DOSPUNTOS);}
{mas}               {return ops.token(ClaseLexica.MAS);}
{menos}              {return ops.token(ClaseLexica.MENOS);}
{mul}               {return ops.token(ClaseLexica.MUL);}
{div}              {return ops.token(ClaseLexica.DIV);}
{mod}               {return ops.token(ClaseLexica.MOD);}
{menor}              {return ops.token(ClaseLexica.MENOR);}
{mayor}                  {return ops.token(ClaseLexica.MAYOR);}
{menorig}                   {return ops.token(ClaseLexica.MENORIG);}
{mayorig}           {return ops.token(ClaseLexica.MAYORIG);}
{igual}               {return ops.token(ClaseLexica.IGUAL);}
{asig}              {return ops.token(ClaseLexica.ASIG);}
{dist}               {return ops.token(ClaseLexica.DIST);}
{par_ap}              {return ops.token(ClaseLexica.PAR_AP);}
{par_cier}                  {return ops.token(ClaseLexica.PAR_CIER);}
{puntocoma}                   {return ops.token(ClaseLexica.PUNTOCOMA);}
{corch_ap}           {return ops.token(ClaseLexica.CORCH_AP);}
{corch_cier}               {return ops.token(ClaseLexica.CORCH_CIER);}
{punto}              {return ops.token(ClaseLexica.PUNTO);}
[^]                       {errores.errorLexico(fila(),lexema());}         
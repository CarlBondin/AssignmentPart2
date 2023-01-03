package larva;


import java.util.LinkedHashMap;
import java.io.PrintWriter;

public class _cls_automatonPart20 implements _callable{

public static PrintWriter pw; 
public static _cls_automatonPart20 root;

public static LinkedHashMap<_cls_automatonPart20,_cls_automatonPart20> _cls_automatonPart20_instances = new LinkedHashMap<_cls_automatonPart20,_cls_automatonPart20>();
static{
try{
RunningClock.start();
pw = new PrintWriter("D:\\Documents\\UM\\3rd year Sem1\\CPS3230-Software Testing\\Colombo\\AssignmentPart2/src/output_automatonPart2.txt");

root = new _cls_automatonPart20();
_cls_automatonPart20_instances.put(root, root);
  root.initialisation();
}catch(Exception ex)
{ex.printStackTrace();}
}

_cls_automatonPart20 parent; //to remain null - this class does not have a parent!
int no_automata = 1;
 public int noOfAlerts =0 ;
 public int noOfAlertsShown =0 ;

public static void initialize(){}
//inheritance could not be used because of the automatic call to super()
//when the constructor is called...we need to keep the SAME parent if this exists!

public _cls_automatonPart20() {
}

public void initialisation() {
}

public static _cls_automatonPart20 _get_cls_automatonPart20_inst() { synchronized(_cls_automatonPart20_instances){
 return root;
}
}

public boolean equals(Object o) {
 if ((o instanceof _cls_automatonPart20))
{return true;}
else
{return false;}
}

public int hashCode() {
return 0;
}

public void _call(String _info, int... _event){
synchronized(_cls_automatonPart20_instances){
_performLogic_alertSystem(_info, _event);
}
}

public void _call_all_filtered(String _info, int... _event){
}

public static void _call_all(String _info, int... _event){

_cls_automatonPart20[] a = new _cls_automatonPart20[1];
synchronized(_cls_automatonPart20_instances){
a = _cls_automatonPart20_instances.keySet().toArray(a);}
for (_cls_automatonPart20 _inst : a)

if (_inst != null) _inst._call(_info, _event);
}

public void _killThis(){
try{
if (--no_automata == 0){
synchronized(_cls_automatonPart20_instances){
_cls_automatonPart20_instances.remove(this);}
}
else if (no_automata < 0)
{throw new Exception("no_automata < 0!!");}
}catch(Exception ex){ex.printStackTrace();}
}

int _state_id_alertSystem = 5;

public void _performLogic_alertSystem(String _info, int... _event) {

_cls_automatonPart20.pw.println("[alertSystem]AUTOMATON::> alertSystem("+") STATE::>"+ _string_alertSystem(_state_id_alertSystem, 0));
_cls_automatonPart20.pw.flush();

if (0==1){}
else if (_state_id_alertSystem==3){
		if (1==0){}
		else if ((_occurredEvent(_event,2/*addAlert*/))){
		noOfAlerts ++;
_cls_automatonPart20.pw .println ("Number of Alerts:"+noOfAlerts );

		_state_id_alertSystem = 3;//moving to state alertAdded
		_goto_alertSystem(_info);
		}
		else if ((_occurredEvent(_event,0/*accessAlerts*/))){
		_cls_automatonPart20.pw .println ("Number of Alerts:"+noOfAlerts );

		_state_id_alertSystem = 2;//moving to state myAlerts
		_goto_alertSystem(_info);
		}
		else if ((_occurredEvent(_event,6/*badPostRequest*/))){
		
		_state_id_alertSystem = 0;//moving to state postError
		_goto_alertSystem(_info);
		}
}
else if (_state_id_alertSystem==4){
		if (1==0){}
		else if ((_occurredEvent(_event,0/*accessAlerts*/))){
		_cls_automatonPart20.pw .println ("Number of Alerts:"+noOfAlerts );

		_state_id_alertSystem = 2;//moving to state myAlerts
		_goto_alertSystem(_info);
		}
}
else if (_state_id_alertSystem==5){
		if (1==0){}
		else if ((_occurredEvent(_event,0/*accessAlerts*/))){
		
		_state_id_alertSystem = 2;//moving to state myAlerts
		_goto_alertSystem(_info);
		}
}
else if (_state_id_alertSystem==0){
		if (1==0){}
		else if ((_occurredEvent(_event,0/*accessAlerts*/))){
		
		_state_id_alertSystem = 2;//moving to state myAlerts
		_goto_alertSystem(_info);
		}
}
else if (_state_id_alertSystem==2){
		if (1==0){}
		else if ((_occurredEvent(_event,2/*addAlert*/))){
		noOfAlerts ++;
_cls_automatonPart20.pw .println ("Number of Alerts:"+noOfAlerts );

		_state_id_alertSystem = 3;//moving to state alertAdded
		_goto_alertSystem(_info);
		}
		else if ((_occurredEvent(_event,8/*badDeleteRequest*/))){
		
		_state_id_alertSystem = 1;//moving to state deleteError
		_goto_alertSystem(_info);
		}
		else if ((_occurredEvent(_event,4/*deleteAlerts*/))){
		noOfAlerts =0 ;
_cls_automatonPart20.pw .println ("Number of Alerts:"+noOfAlerts );

		_state_id_alertSystem = 4;//moving to state alertsDeleted
		_goto_alertSystem(_info);
		}
}
else if (_state_id_alertSystem==1){
		if (1==0){}
		else if ((_occurredEvent(_event,0/*accessAlerts*/))){
		
		_state_id_alertSystem = 2;//moving to state myAlerts
		_goto_alertSystem(_info);
		}
}
}

public void _goto_alertSystem(String _info){
_cls_automatonPart20.pw.println("[alertSystem]MOVED ON METHODCALL: "+ _info +" TO STATE::> " + _string_alertSystem(_state_id_alertSystem, 1));
_cls_automatonPart20.pw.flush();
}

public String _string_alertSystem(int _state_id, int _mode){
switch(_state_id){
case 3: if (_mode == 0) return "alertAdded"; else return "alertAdded";
case 4: if (_mode == 0) return "alertsDeleted"; else return "alertsDeleted";
case 5: if (_mode == 0) return "loggedIn"; else return "loggedIn";
case 0: if (_mode == 0) return "postError"; else return "!!!SYSTEM REACHED BAD STATE!!! postError "+new _BadStateExceptionautomatonPart2().toString()+" ";
case 2: if (_mode == 0) return "myAlerts"; else return "myAlerts";
case 1: if (_mode == 0) return "deleteError"; else return "!!!SYSTEM REACHED BAD STATE!!! deleteError "+new _BadStateExceptionautomatonPart2().toString()+" ";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}

public boolean _occurredEvent(int[] _events, int event){
for (int i:_events) if (i == event) return true;
return false;
}
}
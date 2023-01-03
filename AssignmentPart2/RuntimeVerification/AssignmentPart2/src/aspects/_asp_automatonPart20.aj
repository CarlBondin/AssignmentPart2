package aspects;

import larva.*;
public aspect _asp_automatonPart20 {

public static Object lock = new Object();

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_automatonPart20.initialize();
}
}
before () : (call(* *.postRequest(..)) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_automatonPart20.lock){

_cls_automatonPart20 _cls_inst = _cls_automatonPart20._get_cls_automatonPart20_inst();
_cls_inst._call(thisJoinPoint.getSignature().toString(), 2/*addAlert*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 2/*addAlert*/);
}
}
before () : (call(* *.badPostRequest(..)) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_automatonPart20.lock){

_cls_automatonPart20 _cls_inst = _cls_automatonPart20._get_cls_automatonPart20_inst();
_cls_inst._call(thisJoinPoint.getSignature().toString(), 6/*badPostRequest*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 6/*badPostRequest*/);
}
}
before () : (call(* *.deleteRequest(..)) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_automatonPart20.lock){

_cls_automatonPart20 _cls_inst = _cls_automatonPart20._get_cls_automatonPart20_inst();
_cls_inst._call(thisJoinPoint.getSignature().toString(), 4/*deleteAlerts*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 4/*deleteAlerts*/);
}
}
before () : (call(* *.accessingAlerts(..)) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_automatonPart20.lock){

_cls_automatonPart20 _cls_inst = _cls_automatonPart20._get_cls_automatonPart20_inst();
_cls_inst._call(thisJoinPoint.getSignature().toString(), 0/*accessAlerts*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 0/*accessAlerts*/);
}
}
before () : (call(* *.badDeleteRequest(..)) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_automatonPart20.lock){

_cls_automatonPart20 _cls_inst = _cls_automatonPart20._get_cls_automatonPart20_inst();
_cls_inst._call(thisJoinPoint.getSignature().toString(), 8/*badDeleteRequest*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 8/*badDeleteRequest*/);
}
}
}
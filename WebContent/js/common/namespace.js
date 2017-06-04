var Namespace =
{
    Register: function(_Name){
        var chk = false;
        var cob = "";
        var spc = _Name.split(".");
        for(var i = 0; i < spc.length; i++){
            if(cob != ""){
				cob += ".";
			}
            cob += spc[i];
            chk = this.Exists(cob);
            if(!chk){
				this.Create(cob);
			}
        }
        if(chk){ 
			throw "Namespace: " + _Name + " is already defined."; 
		}
    },

    Create: function(_Src){
        eval("window." + _Src + " = new Object();");
    },

    Exists: function(_Src){
        eval("var NE = false; try{if(" + _Src + "){NE = true;}else{NE = false;}}catch(err){NE=false;}");
        return NE;
    },
	
	Clear: function(_Src){
		eval("window." + _Src + " = undefined;");
	}
};
//###start_namespace####//
Namespace.Register("ns.common");
Namespace.Register("ns.system");
Namespace.Register("ns.cache");
Namespace.Register("ns.bpm");
Namespace.Register("ns.position");
Namespace.Register("ns.org");
Namespace.Register("ns.role");
Namespace.Register("ns.user");
Namespace.Register("ns.privilege");
Namespace.Register("ns.codingrule");
Namespace.Register("ns.attachment");
Namespace.Register("ns.dataSource");
Namespace.Register("ns.email");
Namespace.Register("ns.message");
Namespace.Register("ns.desktop");
Namespace.Register("ns.desktop.index");
Namespace.Register("ns.desktop.layoutDesign");
Namespace.Register("ns.employee");
Namespace.Register("ns.car");
Namespace.Register("ns.carInfo");
Namespace.Register("ns.carItem");
Namespace.Register("ns.project");
Namespace.Register("ns.document");
Namespace.Register("ns.peach");
Namespace.Register("ns.product");
//###end_namespace####//


var gDomain="sdc.cmbchina.com";	// SDC Production Mode Domain
var gDcsId="dcswk50u01000047oazd5jjo3_7o5o";
var gFpc="WTFPC";
var gConvert=true;
var gWTIDJS=window.document.createElement("script"); 

window.document.getElementsByTagName("head")[0].appendChild(gWTIDJS); 

if ((typeof(gConvert)!="undefined")&&gConvert&&(document.cookie.indexOf(gFpc+"=")==-1)&&(document.cookie.indexOf("WTLOPTOUT=")==-1)){
 gWTIDJS.src="http"+(window.location.protocol.indexOf('https:')==0?'s':'')+"://"+gDomain+"/"+gDcsId+"/wtid.js"; 
}

setTimeout('setsdcjs()',0);
function setsdcjs(){   
   var js_path="/assets/sdc_cmb.js";
   var SDC_js=document.createElement("script");
    var curWwwPath = window.document.location.href;
    var pathName=window.document.location.pathname;
    var pos=curWwwPath.indexOf(pathName);
    var localhostPaht=curWwwPath.substring(0,pos);
   SDC_js.src=localhostPaht+js_path;
   var headElem=document.getElementsByTagName("head")[0];
   headElem.appendChild(SDC_js);   
}
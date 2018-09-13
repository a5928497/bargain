 var pageNo = "HS0";
 CMBLS={}; 
 CMBLS.CMBAppLog = {};
 CMBLS.CMBAppLog.successCallback = function(id,message){
 }
 
 CMBLS.CMBAppLog.failCallback = function(id,message){
 }

 //页面加载日志
 function pageLoadAppLog() {
     if (clientV > '4.9.9') {
         var urlReferrer = document.referrer;
         var url = window.location.href;
         var entryID = getQueryString("behavior_entryid");
         if (entryID == null) {
             entryID = ""; //首页为空
         }
         var behavior = encodeURIComponent('{"URL":"' + url + '","UrlReferrer":"' + urlReferrer + '","PageNo":"' + pageNo + '","PageName":"' + pageName + '","EntryID":"' + entryID + '"}');
         var paramData = "http://cmbls/cmbapplog?id=mb5web&eventId=PageLoad&behavior=" + behavior;
         connectCmblsExecutor('5.0', paramData);
     }
 }

 //页面点击日志
 function pageClickAppLog(WidgetNo, PrdCode, WidgetStatus, ClickProdCode,ClickUrl) {
     if (clientV > '4.9.9') {
         if (WidgetStatus == undefined || WidgetStatus == null) {
             WidgetStatus = ""; //enterid
         }         
         if (PrdCode == undefined || PrdCode == null) {
             PrdCode = "";
         }
         if (ClickProdCode == undefined || ClickProdCode == null) {
             ClickProdCode = "";
         }
         if (!ClickUrl) {
             ClickUrl = "";
         }
         var behavior = encodeURIComponent('{"WidgetNo":"' + WidgetNo + '","ProdCode":"' + PrdCode + '","WidgetStatus":"' + WidgetStatus + '","ClickProdCode":"' + ClickProdCode + '","ClickUrl":"' + ClickUrl + '","PageName":"' + pageName + '"}');
         var paramData = "http://cmbls/cmbapplog?id=mb5web&eventId=ClickUnload&behavior=" + behavior;
         connectCmblsExecutor('5.0', paramData);
     }
 }
 
 
 function getQueryString(name){
    var reg = new RegExp("(^|&)"+name +"=([^&]*)(&|$)","i");
    var r=window.location.search.substr(1).match(reg);
    if(r !=null){
        return unescape(r[2]);
    }
    return null;
}
function connectCmblsExecutor(version, url) {
    
    if (window.cmblsExecutor) {
        cmblsExecutor.executeCmbls(version, url);
    } else {
        document.addEventListener('CMBLSExecutorReady', function() {
            cmblsExecutor.executeCmbls(version, url)
        }, false);
    }
}
function getAppVersion() {   
    try {
        var userAgent = navigator.userAgent.toLowerCase();
        var clientVersion = function() {
            var a = userAgent.match(/mpbank\/(\d+\.\d+\.\d+)/)
            if (a) {
                return a[1];
            }
            return ""
        } ();
        this.clientV = clientVersion;
    }
    catch (e) { }
} 
function RewriteUrl(Url, WidgetNo, ClickProdCode, PrdCode, WidgetStatus) {
    var entryid = pageNo + WidgetNo;
    pageClickAppLog(entryid, PrdCode, WidgetStatus, ClickProdCode,Url);
    if (Url.indexOf("?") > -1) {
        window.location.href = Url + "&popup=true&behavior_entryid=" + entryid;
    } else {
        window.location.href = Url + "?popup=true&behavior_entryid=" + entryid;
    }
}

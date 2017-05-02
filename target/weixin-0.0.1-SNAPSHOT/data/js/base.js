var SD = (function() {
    mask = {
        show: function() {
            $('body').append("<div class=\"sd-mask\"></div>")
        },
        hide: function() {
            var tm = $('.sd-mask');
            if (tm.length != 0) {
                $(tm[0]).remove()
            }
        }
    };
    hideUrlBar = function() {
        window.scrollTo(0, 1)
    };
    encodeUrl = function(reqUrl) {
        var params = "";
        var queryUrl = reqUrl;
        var paramIndex = reqUrl.indexOf("?");
        if (paramIndex > 0) {
            queryUrl = reqUrl.substring(0, paramIndex);
            var parameters = reqUrl.substring(paramIndex + 1, reqUrl.length);
            var paramArray = parameters.split("&");
            for (var i = 0; i < paramArray.length; i++) {
                var string = paramArray[i];
                var index = string.indexOf("=");
                if (index > 0) {
                    var parameter = string.substring(0, index);
                    var value = string.substring(index + 1, string.length);
                    params += parameter;
                    params += "=";
                    params += (value == null || value == '' ? "":encodeURIComponent(value));
                    params += "&"
                }
            }
            params = params.substring(0, params.length - 1)
        }
        return params == null || params == '' ? queryUrl: queryUrl + "?" + params
    };
    Map = function() {
        var map_ = new Object();
        map_.put = function(key, value) {
            map_[key + '_'] = value
        };
        map_.get = function(key) {
            return map_[key + '_']
        };
        map_.remove = function(key) {
            delete map_[key + '_']
        };
        map_.keySet = function() {
            var ret = "";
            for (var p in map_) {
                if (typeof p == 'string' && p.substring(p.length - 1) == "_") {
                    ret += ",";
                    ret += p.substring(0, p.length - 1)
                }
            }
            if (ret == "") {
                return ret.split(",")
            } else {
                return ret.substring(1).split(",")
            }
        };
        map_.toString = function() {
            if (this.keySet() != null && this.keySet() != '') {
                var str = "";
                for (var i = 0, keys = this.keySet(), len = keys.length; i < len; i++) {
                    str = str + keys[i] + "=" + this.get(keys[i]) + ";\n"
                }
                return str
            }
        };
        return map_
    };
    cookie = {
        put: function(name, value, time) {
            var exp = new Date();
            exp.setTime(exp.getTime() + time * 1000);
            document.cookie = name + "=" + escape(value) + "; path=/; expires=" + exp.toGMTString()
        },
        remove: function(name) {
            var exp = new Date();
            exp.setTime(exp.getTime() - 1);
            var cval = this.get(name);
            document.cookie = name + "=" + cval + "; path=/; expires=" + exp.toGMTString()
        },
        get: function(name) {
            var strCookie = document.cookie;
            var arrCookie = strCookie.split("; ");
            for (var i = 0; i < arrCookie.length; i++) {
                var arr = arrCookie[i].split("=");
                if (name == arr[0]) {
                    return unescape(arr[1])
                }
            }
            return ""
        }
    };
    initConfig = {
        urlparamMap: new this.Map(),
        init: function() {
            var map = this.urlparamMap;
            map.put("base_url_m1", "http://symt.sandpay.com.cn/annualmeeting");
            map.put("route_ajax", map.get('base_url_m1') + "/service_jsonp.jsp");
        },
        getUrl: function(key) {
            return this.urlparamMap.get(key)
        }
    };
    goUrl = function(url,type,state) {
		SD.mask.show();
		window.location.href = SD.encodeUrl(url);
        SD.mask.hide()
    };
	bandMoveHoverCss=function(bo){
		function removeClass(o){
			o.setAttribute("class", o.getAttribute("class") == null ?  "" : o.getAttribute("class").replace(" hovercss",""));
		}
		function addClass(o){
			var bhs=document.getElementsByName("bandtouchhover");
			for(var i =0;i<bo.length;i++){
				removeClass(bhs[i]);
			}
			o.setAttribute("class", (o.getAttribute("class")==null ? "" : o.getAttribute("class"))+" hovercss");
		}
		for(var i =0;i<bo.length;i++){
			var o = bo[i];
			o.addEventListener('touchstart', function(){addClass(this);}, false);  
			o.addEventListener('touchmove', function(){
				 var o = this;
				 setTimeout(removeClass(o), 500); 
			}, false);
			o.addEventListener('touchend', function(){removeClass(this);}, false);
		}
	};
    return this
})();
window.SD = SD;
var paramMap = new SD.Map();
var param = window.location.search;
if (param != null && param != '') {
    param = param.substring(1);
    params = param.split("&");
    $.each(params, 
    function() {
        var m = this.split("=");
        paramMap.put(m[0], decodeURIComponent(m[1]))
    })
}
addEventListener('load', 
function() {
    var theUserAgent = navigator.userAgent;
    if ( !! theUserAgent.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/)) {
        setTimeout(hideUrlBar, 300)
    }
});
SD.initConfig.init();
var openid= "15901946122";//SD.cookie.get("openid")
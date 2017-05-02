; (function (define) {
	define(['zepto'], function ($) {
		return (function () {
			var version = '2.0.1';
			var $container;
			var listener;
			var toastId = 0;
			var toastType = {
				error: 'error',
				info: 'info',
				success: 'success',
				warning: 'warning'
			};
			var toastr = {
				clear: clear,
				error: error,
				getContainer: getContainer,
				info: info,
				options: {},
				subscribe: subscribe,
				success: success,
				version: version,
				warning: warning
			};
			return toastr;
			function error(message, title, optionsOverride) {
				return notify({
					type: toastType.error,
					iconClass: getOptions().iconClasses.error,
					message: message,
					optionsOverride: optionsOverride,
					title: title
				});
			}
			function info(message, title, optionsOverride) {
				return notify({
					type: toastType.info,
					iconClass: getOptions().iconClasses.info,
					message: message,
					optionsOverride: optionsOverride,
					title: title
				});
			}
			function subscribe(callback) {
				listener = callback;
			}
			function success(message, title, optionsOverride) {
				return notify({
					type: toastType.success,
					iconClass: getOptions().iconClasses.success,
					message: message,
					optionsOverride: optionsOverride,
					title: title
				});
			}
			function warning(message, title, optionsOverride) {
				return notify({
					type: toastType.warning,
					iconClass: getOptions().iconClasses.warning,
					message: message,
					optionsOverride: optionsOverride,
					title: title
				});
			}
			function clear($toastElement) {
				var options = getOptions();
				if (!$container) { getContainer(options); }
				if ($toastElement && $(':focus', $toastElement).length === 0) {
					$toastElement[options.hideMethod]({
						duration: options.hideDuration,
						easing: options.hideEasing,
						complete: function () { removeToast($toastElement); }
					});
					return;
				}
				if ($container.children().length) {
					$container[options.hideMethod]({
						duration: options.hideDuration,
						easing: options.hideEasing,
						complete: function () { $container.remove(); }
					});
				}
			}
			function getDefaults() {
				return {
					tapToDismiss: true,
					toastClass: 'toast',
					containerId: 'toast-container',
					debug: false,
					closeButton: true,
					showMethod: 'show', 
					showDuration: 300,
					showEasing: 'swing',
					onShown: undefined,
					hideMethod: 'hide',
					hideDuration: 1000,
					hideEasing: 'swing',
					onHidden: undefined,
					extendedTimeOut: 1000,
					iconClasses: {
						error: 'toast-error',
						info: 'toast-info',
						success: 'toast-success',
						warning: 'toast-warning'
					},
					iconClass: 'toast-info',
					positionClass: 'toast-top-full-width',
					timeOut: 5000, 
					titleClass: 'toast-title',
					messageClass: 'toast-message',
					target: 'body',
					closeHtml: '<button>&times;</button>',
					newestOnTop: true
				};
			}
			function publish(args) {
				if (!listener) {
					return;
				}
				listener(args);
			}
			function notify(map) {
				var
					options = getOptions(),
					iconClass = map.iconClass || options.iconClass;

				if (typeof (map.optionsOverride) !== 'undefined') {
					options = $.extend(options, map.optionsOverride);
					iconClass = map.optionsOverride.iconClass || iconClass;
				}
				toastId++;
				$container = getContainer(options);
				var
					intervalId = null,
					$toastElement = $('<div/>'),
					$titleElement = $('<div/>'),
					$messageElement = $('<div/>'),
					$closeElement = $(options.closeHtml),
					response = {
						toastId: toastId,
						state: 'visible',
						startTime: new Date(),
						options: options,
						map: map
					};
				if (map.iconClass) {
					$toastElement.addClass(options.toastClass).addClass(iconClass);
				}
				if (map.title) {
					$titleElement.append(map.title).addClass(options.titleClass);
					$toastElement.append($titleElement);
				}
				if (map.message) {
					$messageElement.append(map.message).addClass(options.messageClass);
					$toastElement.append($messageElement);
				}
				if (options.closeButton) {
					$closeElement.addClass('toast-close-button');
					$toastElement.prepend($closeElement);
				}
				$toastElement.hide();
				if (options.newestOnTop) {
					$container.prepend($toastElement);
				} else {
					$container.append($toastElement);
				}
				$toastElement[options.showMethod](
					{ duration: options.showDuration, easing: options.showEasing, complete: options.onShown }
				);
				if (options.timeOut > 0) {
					intervalId = setTimeout(hideToast, options.timeOut);
				}
				if (!options.onclick && options.tapToDismiss) {
					$toastElement.click(hideToast);
				}
				if (options.closeButton && $closeElement) {
					$closeElement.click(function (event) {
						event.stopPropagation();
						hideToast(true);
					});
				}
				if (options.onclick) {
					$toastElement.click(function () {
						options.onclick();
						hideToast();
					});
				}
				publish(response);
				if (options.debug && console) {
					console.log(response);
				}
				return $toastElement;
				function hideToast(override) {
					if ($(':focus', $toastElement).length && !override) {
						return;
					}
					return $toastElement[options.hideMethod]({
						duration: options.hideDuration,
						easing: options.hideEasing,
						complete: function () {
							removeToast($toastElement);
							if (options.onHidden) {
								options.onHidden();
							}
							response.state = 'hidden';
							response.endTime = new Date(),
							publish(response);
						}
					});
				}
				function delayedhideToast() {
					if (options.timeOut > 0 || options.extendedTimeOut > 0) {
						intervalId = setTimeout(hideToast, options.extendedTimeOut);
					}
				}
				function stickAround() {
					clearTimeout(intervalId);
					$toastElement.stop(true, true)[options.showMethod](
						{ duration: options.showDuration, easing: options.showEasing }
					);
				}
			}
			function getContainer(options) {
				if (!options) { options = getOptions(); }
				$container = $('#' + options.containerId);
				if ($container.length) {
					return $container;
				}
				$container = $('<div/>')
					.attr('id', options.containerId)
					.addClass(options.positionClass);
				$container.appendTo($(options.target));
				return $container;
			}
			function getOptions() {
				return $.extend({}, getDefaults(), toastr.options);
			}
			function removeToast($toastElement) {
				if (!$container) { $container = getContainer(); }
				if ($toastElement.is(':visible')) {
					return;
				}
				$toastElement.remove();
				$toastElement = null;
				if ($container.children().length === 0) {
					$container.remove();
				}
			}

		})();
	});
}(typeof define === 'function' && define.amd ? define : function (deps, factory) {
	if (typeof module !== 'undefined' && module.exports) {
		module.exports = factory(require(deps[0]));
	} else {
		window['toastr'] = factory(window['Zepto']);
	}
}));
SD.mask.hide();
var errormsg = "服务器异常请稍后再试!",baseurl = "";
var serviceAjax = function(params,url,serviceCallBack){
	SD.mask.show();
	if(url!='00050017'&&params.indexOf("\"type\":\"4\"")<0){
		if(!checklogin()){
			SD.goUrl("login.html");
			return;
		}
	}
	$.ajax({
		  type:'GET',
		  url:SD.initConfig.getUrl('route_ajax'),
		  data:{
			  "url":url, 
			  "params":params
	      }, 
		  dataType:'jsonp',
		  success: function(data){
			  if(data.respCode=='-1'){
				  toastr['info']("本系统异常，请稍后再试~");
			  }else if(data.respCode=='100000'){
				  serviceCallBack(data);
			  }else{
				  toastr['info'](data.respResult);
			  }
			  SD.mask.hide();
		  },
		  error:function(data){
			  toastr['error'](errormsg);
			  SD.mask.hide();
		  }
	});
};
var checklogin = function (){
	var flag=true;
	/*var openid =SD.cookie.get("openid");
	if(openid==null||openid==''){
		return false;
	}*/
	return flag;
};
/**
 * 添加支持鼠标提示浮层效果
 */
var IndexEvent = {
	initialize : function() {
	},
	isIE : function() {
		if (window.ActiveXObject) {
			return (navigator.userAgent.toLowerCase().indexOf("msie") >= 0);
		} else {
			return false;
		}
	},
	positon : function(element) {
		var valueT = 0, valueL = 0;
		do {
			valueT += element.offsetTop || 0;
			valueL += element.offsetLeft || 0;
			element = element.offsetParent;
		} while (element);
		return [ valueL, valueT ];
	},
	showVTip : function(elem) {
		this.keepVTip();
		var pop = $('#pop');
		if (this.isIE()) {
			var vTip = $(elem).children(".popup-img");
		} else {
			var vTip = $(elem).next(".popup-img");
		}
		var pos = this.positon(elem);
		pop.children(".popup-img").replaceWith(vTip.clone());
		pop.children(".popup-img").show();
		var leftV = pos[0] + 128;
		pop.attr("style", "left:" + leftV + "px;top:" + pos[1] + "px;");
		this.popTimeout = window.setTimeout('$(\'#pop\').show();', 500);
	},
	showPopTip : function(elem) {
		this.keepVTip();
		var pop = $(elem);
		pop.show();
		pop.children(".popup-img").show();
	},
	hideVTip : function(elem) {
		this.keepVTip();
		this.popTimeout = window.setTimeout('$(\'#pop\').hide();', 200);
	},
	keepVTip : function() {
		if (!isNaN(this.popTimeout)) {
			window.clearTimeout(this.popTimeout);
			this.popTimeout = null;
		}
	},
	/* 是否所有浮层离开图片和文字就消失, true为不消失，flase为消失 */
	isOpen : function() {
		return false;
	}
};

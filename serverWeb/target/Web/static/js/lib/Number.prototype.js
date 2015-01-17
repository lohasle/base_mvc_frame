
/**
 *格式化数字
 */
Number.prototype.format = function(pattern) {
	var strarr = this.toString().split('.');
	var fmtarr = pattern ? pattern.split('.') : [''];
	var retstr = '';
	// 整数部分
	var str = strarr[0];
	var fmt = fmtarr[0];
	var i = str.length - 1;
	var comma = false;
	for (var f = fmt.length - 1; f >= 0; f--) {
		switch (fmt.substr(f, 1)) {
		case '#':
			if (i >= 0) retstr = str.substr(i--, 1) + retstr;
			break;
		case '0':
			if (i >= 0) retstr = str.substr(i--, 1) + retstr;
			else retstr = '0' + retstr;
			break;
		case ',':
			comma = true;
			retstr = ',' + retstr;
			break;
		}
	}
	if (i >= 0) {
		if (comma) {
			var l = str.length;
			for (; i >= 0; i--) {
				retstr = str.substr(i, 1) + retstr;
				if (i > 0 && ((l - i) % 3) == 0) retstr = ',' + retstr;
			}
		} else retstr = str.substr(0, i + 1) + retstr;
	}

	retstr = retstr + '.';
	// 处理小数部分
	str = strarr.length > 1 ? strarr[1] : '';
	fmt = fmtarr.length > 1 ? fmtarr[1] : '';
	i = 0;
	for (var f = 0; f < fmt.length; f++) {
		switch (fmt.substr(f, 1)) {
		case '#':
			if (i < str.length) retstr += str.substr(i++, 1);
			break;
		case '0':
			if (i < str.length) retstr += str.substr(i++, 1);
			else retstr += '0';
			break;
		}
	}
	return retstr.replace(/^-,+/, '').replace(/^,+/, '').replace(/\.$/, '');
};
/**
 *	格式成百分数
 */
Number.prototype.toPercent = function(pattern) {
	var num = this * 100;
	pattern = pattern || '#,###,###.0';
	return num.format(pattern) + "%";
};
/**
 *	格式货币
 */
Number.prototype.toMoneyFloat = function(pattern) {
	pattern = pattern || '#,###,###.00';
	return this.format(pattern);
};
/**
 *	短float
 */
Number.prototype.toShortFloat = function(pattern) {
	pattern = pattern || '###.00';
	return this.format(pattern);
};
/**
 *	格式无精度的货币
 */
Number.prototype.toMoney = function(pattern) {
	pattern = pattern || '#,###,###';
	return this.format(pattern);
};
/**
 *	格式成大写的数字
 */
Number.prototype.toRMB = function () {
    var number = Math.round(this * 100) / 100;
    number = number.toString(10).split(".");
    var a = number[0];
    var cn = "零壹贰叁肆伍陆柒捌玖";
    var cnNum = "";
    var len = a.length - 1;
    for (var i = 0; i <= len; i++) cnNum += cn.charAt(parseInt(a.charAt(i))) + [
        ["元", "万", "亿", "万"][Math.floor((len - i) / 4)], "拾", "佰", "仟"][(len - i) % 4];
    if (number.length == 2 && number[1] != "") {
        var a = number[1];
        for (var i = 0; i < a.length; i++) cnNum += cn.charAt(parseInt(a.charAt(i))) + ["角", "分"][i]
    }
    //不带零的读法
    /*cnNum = cnNum.replace(/零佰|零拾|零仟|零角/g, "零");
     cnNum = cnNum.replace(/零{2,}/g, "零");
     cnNum = cnNum.replace(/零(?=元|万|亿)/g, "");
     cnNum = cnNum.replace(/亿万/, "亿");
     cnNum = cnNum.replace(/^元零?/, "");*/

    //带零的读法
    cnNum = cnNum.replace(/零佰|零拾|零仟|零角/g, "零");
    cnNum = cnNum.replace(/零{2,}/g, "零");
    cnNum = cnNum.replace(/零(?=元)/g, "");
    cnNum = cnNum.replace(/零万/, "万零");
    cnNum = cnNum.replace(/零亿/, "亿零");
    cnNum = cnNum.replace(/零{2,}/g, "零");
    cnNum = cnNum.replace(/零(?=元|万|亿)/g, "");
    cnNum = cnNum.replace(/亿万/, "亿");
    cnNum = cnNum.replace(/^元零?/, "");
    if (cnNum != "" && !/分$/.test(cnNum)) cnNum += "整";
    return cnNum;
};
/**
 * 阿拉伯数字转中文
 * @param cnType 0/""/null为初级中文大写、1为顶级中文大写
 * @returns {string}
 */
Number.prototype.digitToUppercase = function (cnType) {
    var number = Math.round(this * 100) / 100;
    number = number.toString(10).split(".");
    var a = number[0];
    var cn = cnType == 1 ? "零壹贰叁肆伍陆柒捌玖" : "〇一二三四五六七八九";
    var cnNum = "";
    //整数部分
    var len = a.length - 1;
    for (var i = 0; i <= len; i++) {
        cnNum += cnType == 1 ?
            (cn.charAt(parseInt(a.charAt(i))) + [["", "万", "亿", "万"][Math.floor((len - i) / 4)], "拾", "佰", "仟"][(len - i) % 4]) :
            (cn.charAt(parseInt(a.charAt(i))) + [["", "万", "亿", "万"][Math.floor((len - i) / 4)], "十", "百", "千"][(len - i) % 4]);
    }
    //小数部分
    if (number.length == 2 && number[1] != "") {
        var a = number[1];
        cnNum += "点";
        for (var i = 0; i < a.length; i++) {
            cnNum += cn.charAt(parseInt(a.charAt(i)));
        }
    }
    /* 一些通俗数字读法的格式处理 */
    if (cnType == 1) {
        cnNum = cnNum.replace(/零佰|零拾|零仟/g, "零");
        cnNum = cnNum.replace(/零{2,}/g, "零");
        if (!/^零点.+$/.test(cnNum)) {
            cnNum = cnNum.replace(/零点/g, "点");
        }
        if (/^壹拾.+$/.test(cnNum)) {
            cnNum = cnNum.replace(/壹拾/g, "拾");
        }
    } else {
        cnNum = cnNum.replace(/〇百|〇十|〇千/g, "〇");
        cnNum = cnNum.replace(/〇{2,}/g, "〇");
        if (!/^〇点.+$/.test(cnNum)) {
            cnNum = cnNum.replace(/〇点/g, "点");
        }
        if (/^一十.+$/.test(cnNum)) {
            cnNum = cnNum.replace(/一十/g, "十");
        }
    }
    cnNum = cnNum.replace(/(〇|零)(?=万|亿)/g, "");
    cnNum = cnNum.replace(/亿万/, "亿");
    cnNum = cnNum.replace(/(〇|零)$/, "");
    return cnNum;
};
/**
 *	四舍五入
 */
Number.prototype.toRound = function() {
	var num=Math.round(this);
	return num;
};

/**
 * 转换为小数
 * @param n 保留n位小数
 */
Number.prototype.toDecimal = function(n) {
    var num=Number(this.toFixed(n));
    return num;
};

/**
 * 转换为万元单位
 */
Number.prototype.toMillion = function() {
    var num=Math.round(this);
    if(num>10000){
        num=num/10000;
        var pattern = '#,###,###';
        num= num.format(pattern)+'w';
    }
    return num;
}

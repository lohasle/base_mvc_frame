/**
 * 对Date的扩展，将 Date 转化为指定格式的String
 * 月(M)、日(d)、12小时(h)、24小时(H)、分(m)、秒(s)、周(E)、季度(q) 可以用 1-2 个占位符
 * 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
 * eg:
 * (new Date()).pattern("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
 * (new Date()).pattern("yyyy-MM-dd E HH:mm:ss") ==> 2009-03-10 二 20:09:04
 * (new Date()).pattern("yyyy-MM-dd EE hh:mm:ss") ==> 2009-03-10 周二 08:09:04
 * (new Date()).pattern("yyyy-MM-dd EEE hh:mm:ss") ==> 2009-03-10 星期二 08:09:04
 * (new Date()).pattern("yyyy-M-d h:m:s.S") ==> 2006-7-2 8:9:4.18
 */
Date.prototype.pattern = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours() % 12 == 0 ? 12 : this.getHours() % 12, //小时
        "H+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    var week = {
        "0": "/u65e5",
        "1": "/u4e00",
        "2": "/u4e8c",
        "3": "/u4e09",
        "4": "/u56db",
        "5": "/u4e94",
        "6": "/u516d"
    };
    if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    if (/(E+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, ((RegExp.$1.length > 1) ? (RegExp.$1.length > 2 ? "/u661f/u671f" : "/u5468") : "") + week[this.getDay() + ""]);
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        }
    }
    return fmt;
}
/**
 * 将指定的毫秒数加到此实例的值上
 * @param {Object} value
 */
Date.prototype.addMilliseconds = function (value) {
    var millisecond = this.getMilliseconds();
    this.setMilliseconds(millisecond + Number(value));
    return this;
};
/**
 * 将指定的秒数加到此实例的值上
 * @param {Object} value
 */
Date.prototype.addSeconds = function (value) {
    var second = this.getSeconds();
    this.setSeconds(second + Number(value));
    return this;
};
/**
 * 将指定的分钟数加到此实例的值上
 * @param {Object} value
 */
Date.prototype.addMinutes = function (value) {
    var minute = this.getMinutes();
    this.setMinutes(minute + Number(value));
    return this;
};
/**
 * 将指定的小时数加到此实例的值上
 * @param {Object} value
 */
Date.prototype.addHours = function (value) {
    var hour = this.getHours();
    this.setHours(hour + Number(value));
    return this;
};
/**
 * 将指定的天数加到此实例的值上
 * @param {Object} value
 */
Date.prototype.addDays = function (value) {
    var date = this.getDate();
    this.setDate(date + Number(value));
    return this;
};
/**
 * 将指定的星期数加到此实例的值上
 * @param {Object} value
 */
Date.prototype.addWeeks = function (value) {
    return this.addDays(Number(value) * 7);
};
/**
 * 将指定的月份数加到此实例的值上
 * @param {Object} value
 */
Date.prototype.addMonths = function (value) {
    var month = this.getMonth();
    this.setMonth(month + Number(value));
    return this;
};
/**
 * 将指定的年份数加到此实例的值上
 * @param {Object} value
 */
Date.prototype.addYears = function (value) {
    var year = this.getFullYear();
    this.setFullYear(year + Number(value));
    return this;
};
/**
 * 时间处理集
 */
var DateUtil = {
    /**
     * 判断时间格式
     * @param theDate
     * @returns {{}}
     */
    changeDate: function (theDate) {
        var tmpl = {};
        if (!theDate) {
            //没有传入时间，默认是当前时间
            tmpl = new Date();
        } else {
            //传入了时间，判断是不是时间对象
            if (typeof(theDate) == 'object') {
                //是时间对象
                tmpl = theDate;
            } else {
                //不是时间对象，转换为对象
                tmpl = new Date(Date.parse(theDate.replace(/-/g, "/")));
            }
        }
        return tmpl;
    },
    /**
     * 获取当前周的开始和结束日期
     * @param {Object} theDate 传入日期(可选)
     * @return Array
     */
    getWeekOfStartEnd: function (theDate) {
        var that = this;
        var tmplDate = that.changeDate(theDate);//自动判断时间格式
        tmplDate = tmplDate.addDays(-1);//获取昨天日期(主要用做避免西方式计算周日为一周的开始)
        var dateArray = [];
        var startTime = new Date(tmplDate - (tmplDate.getDay() - 1) * 86400000).pattern('yyyy-MM-dd');//得到本周开始的日期
        //后台计算方法是周一00:00:00到周日23:59:59所以addDays参数为6
        var endTime = new Date(Date.parse(startTime.replace(/-/g, "/"))).addDays(6).pattern('yyyy-MM-dd');//得到本周结束的日期
        dateArray.push(startTime);
        dateArray.push(endTime);
        return dateArray;
    },
    /**
     * 获取上周的开始和结束日期
     * @param {Object} theDate 传入日期(可选)
     * @return Array
     */
    getLastWeekOfStartEnd: function (theDate) {
        var that = this;
        var tmplDate = that.changeDate(theDate);//自动判断时间格式
        tmplDate = tmplDate.addDays(-1);//获取昨天日期(主要用做避免西方式计算周日为一周的开始)
        var dateArray = [];
        var startTime = new Date(tmplDate - (tmplDate.getDay() - 1) * 86400000).addDays(-7).pattern('yyyy-MM-dd');
        //后台计算方法是周一00:00:00到周日23:59:59所以addDays参数为6
        var endTime = new Date(Date.parse(startTime.replace(/-/g, "/"))).addDays(6).pattern('yyyy-MM-dd');
        dateArray.push(startTime);
        dateArray.push(endTime);
        return dateArray;
    },
    /**
     * 获取下周的周一和周天
     * @param theDate
     * @returns {Array}
     */
    getNextWeekOfStartEnd: function (theDate) {
        var that = this;
        var tmplDate = that.changeDate(theDate);//自动判断时间格式
        tmplDate = tmplDate.addWeeks(2);//得到传入时间的下2周，再调用获取上周的时候，就得到了传入时间的下周
        return DateUtil.getLastWeekOfStartEnd(tmplDate);
    },
    /**
     * 获取当月的开始和结束日期
     * @param {Object} theDate 传入日期(可选)
     * @return Array
     */
    getMonthOfStartEnd: function (theDate) {
        var that = this;
        var tmplDate = that.changeDate(theDate);//自动判断时间格式
        var dateArray = [];
        var startTime = new Date(tmplDate.getFullYear(), tmplDate.getMonth(), 1).pattern('yyyy-MM-dd');//得到本月开始的日期
        var endTime = new Date(tmplDate.getFullYear(), tmplDate.getMonth(), DateUtil.getMonthDays(tmplDate.getMonth())).pattern('yyyy-MM-dd');//得到本月结束的日期
        dateArray.push(startTime);
        dateArray.push(endTime);
        return dateArray;
    },
    /**
     * 获取上月的开始和结束日期
     * @param {Object} theDate 传入日期(可选)
     * @return Array
     */
    getLastMonthOfStartEnd: function (theDate) {
        var that = this;
        var tmplDate = that.changeDate(theDate);//自动判断时间格式
        var dateArray = [];
        var startTime = new Date(tmplDate.getFullYear(), tmplDate.getMonth() - 1, 1).pattern('yyyy-MM-dd');//得到上月开始的日期
        var endTime = new Date(tmplDate.getFullYear(), tmplDate.getMonth() - 1, DateUtil.getMonthDays(tmplDate.getMonth() - 1)).pattern('yyyy-MM-dd');//得到上月结束的日期
        dateArray.push(startTime);
        dateArray.push(endTime);
        return dateArray;
    },
    /**
     * 获取下月的开始和结束日期
     * @param theDate 传入日期(可选)
     * @returns {Array}
     */
    getNextMonthOfStartEnd: function (theDate) {
        var that = this;
        var tmplDate = that.changeDate(theDate);//自动判断时间格式
        tmplDate = tmplDate.addMonths(2);//通过获取下下个月的时间，再获取上个月的时间，就得到了传入的下个月的时间
        return DateUtil.getLastMonthOfStartEnd(tmplDate);
    },
    /**
     * 获取当前季度的开始和结束日期
     * @param {Object} theDate 传入日期(可选)
     * @return Array
     */
    getQuarterOfStartEnd: function (theDate) {
        var that = this;
        var tmplDate = that.changeDate(theDate);//自动判断时间格式
        var dateArray = [];
        var startTime = new Date(tmplDate.getFullYear(), that.getQuarterStartMonth(tmplDate), 1).pattern('yyyy-MM-dd');//得到本月开始的日期
        var endTime = new Date(tmplDate.getFullYear(), that.getQuarterStartMonth(tmplDate) + 2, that.getMonthDays(that.getQuarterStartMonth(tmplDate) + 2)).pattern('yyyy-MM-dd');//得到本月结束的日期
        dateArray.push(startTime);
        dateArray.push(endTime);
        return dateArray;
    },
    /**
     * 获取上季度的开始和结束日期
     * @param {Object} theDate 传入日期(可选)
     * @return Array
     */
    getLastQuarterOfStartEnd: function (theDate) {
        var that = this;
        var tmplDate = that.changeDate(theDate);//自动判断时间格式
        var dateArray = [];
        var startTime = new Date(tmplDate.getFullYear(), that.getQuarterStartMonth(tmplDate) - 3, 1).pattern('yyyy-MM-dd');//得到上月开始的日期
        var endTime = new Date(tmplDate.getFullYear(), that.getQuarterStartMonth(tmplDate) - 1, that.getMonthDays(that.getQuarterStartMonth(tmplDate) - 1)).pattern('yyyy-MM-dd');//得到上月结束的日期
        dateArray.push(startTime);
        dateArray.push(endTime);
        return dateArray;
    },
    /**
     * 获取下一季的开始和结束日期
     * @param theDate
     * @returns {Array}
     */
    getNextQuarterOfStartEnd: function (theDate) {
        var that = this;
        var tmplDate = that.changeDate(theDate);//自动判断时间格式
        tmplDate = tmplDate.addMonths(6);//获取下下个季度的开始时间，再获取上一个季度的，就是下一个季度的
        return that.getLastQuarterOfStartEnd(tmplDate);
    },
    /**
     * 获取当年的开始和结束日期
     * @param {Object} theDate 传入日期(可选)
     * @return Array
     */
    getYearOfStartEnd: function (theDate) {
        var that = this;
        var tmplDate = that.changeDate(theDate);//自动判断时间格式
        var dateArray = [];
        var startTime = new Date(tmplDate.getFullYear(), 0, 1).pattern('yyyy-MM-dd');//得到本年开始的日期
        var endTime = new Date(tmplDate.getFullYear(), 11, 31).pattern('yyyy-MM-dd');//得到本年结束的日期
        dateArray.push(startTime);
        dateArray.push(endTime);
        return dateArray;
    },
    /**
     * 获取去年的开始和结束日期
     * @param {Object} theDate 传入日期(可选)
     * @return Array
     */
    getLastYearOfStartEnd: function (theDate) {
        var that = this;
        var tmplDate = that.changeDate(theDate);//自动判断时间格式
        var dateArray = [];
        var startTime = new Date(tmplDate.getFullYear() - 1, 0, 1).pattern('yyyy-MM-dd');//得到去年开始的日期
        var endTime = new Date(tmplDate.getFullYear() - 1, 11, 31).pattern('yyyy-MM-dd');//得到去年结束的日期
        dateArray.push(startTime);
        dateArray.push(endTime);
        return dateArray;
    },
    /**
     * 获取下一年的开始和结束日期
     * @param theDate
     * @returns {Array}
     */
    getNextYearOfStartEnd: function (theDate) {
        var that = this;
        var tmplDate = that.changeDate(theDate);//自动判断时间格式
        tmplDate = tmplDate.addYears(2);//获取下下年的日期,再获取上一年的，就刚好是传入的下一年日期
        return that.getLastYearOfStartEnd(tmplDate);
    },
    /**
     * 获取日期所在周的第一天
     * @param {Object} theDate 传入日期(可选)
     * @return Date
     */
    getFirstDateOfWeek: function (theDate) {
        var that = this;
        var tmplDate = that.changeDate(theDate);//自动判断时间格式
        tmplDate = tmplDate.addDays(-1);//获取昨天日期(主要用做避免西方式计算周日为一周的开始)
        return new Date(tmplDate.setDate(tmplDate.getDate() + 1 - tmplDate.getDay()));
    },
    /**
     * 获取日期所在周的最后一天
     * @param {Object} theDate 传入日期(可选)
     * @return Date
     */
    getLastDateOfWeek: function (theDate) {
        var that = this;
        var tmplDate = that.changeDate(theDate);//自动判断时间格式
        tmplDate = tmplDate.addDays(-1);//获取昨天日期(主要用做避免西方式计算周日为一周的开始)
        return new Date(tmplDate.setDate(tmplDate.getDate() + 7 - tmplDate.getDay()));
    },
    /**
     * 获取日期所在月份的天数
     * @param {Object} theMonth 传入日期月份0-11(月份Date.getMonth())(必填)
     * @return Number
     */
    getMonthDays: function (theMonth) {
        var now = new Date(); //当前日期
        var monthStartDate = new Date(now.getFullYear(), theMonth, 1);
        var monthEndDate = new Date(now.getFullYear(), theMonth + 1, 1);
        var days = (monthEndDate - monthStartDate) / (1000 * 60 * 60 * 24);
        return days;
    },
    /**
     * 获取日期所在季度的开始月份
     * @param {Object} theDate 传入日期(可选)
     * @return Number 0,3,6,9
     */
    getQuarterStartMonth: function (theDate) {
        var that = this;
        var tmplDate = that.changeDate(theDate);//自动判断时间格式
        var theMonth = tmplDate.getMonth();
        var quarterStartMonth = 0;
        if (theMonth < 3) {
            quarterStartMonth = 0;
        }
        if (2 < theMonth && theMonth < 6) {
            quarterStartMonth = 3;
        }
        if (5 < theMonth && theMonth < 9) {
            quarterStartMonth = 6;
        }
        if (theMonth > 8) {
            quarterStartMonth = 9;
        }
        return quarterStartMonth;
    },
    /**
     * 获取日期所在周在本月第几周(当月第一个出现的日期即是第一周)
     * @param {Object} theDate 传入日期(可选)
     * @return Number
     */
    getMonthWeek: function (theDate) {
        var that = this;
        var tmplDate = that.changeDate(theDate);//自动判断时间格式
        var firstDayOfThisMonth = new Date(tmplDate.getFullYear(), tmplDate.getMonth(), 1);//本月的第一天
        var dayGap = Math.round((tmplDate.valueOf() - firstDayOfThisMonth.valueOf()) / 86400000);
        var weekDay = firstDayOfThisMonth.getDay();
        var gap = 0;
        if (weekDay == 0) {
            //如果本月第一天是周一，则需加上一周
            gap = 7;
        }
        return Math.ceil((dayGap + ((firstDayOfThisMonth.getDay() + gap + 1) - 1)) / 7);
    },
    /**
     * 获取日期所在周在本年第几周(一年第一个出现的日期即是第一周)
     * @param {Object} theDate 传入日期(可选)
     * @return Number
     */
    getYearWeek: function (theDate) {
        var that = this;
        var tmplDate = that.changeDate(theDate);//自动判断时间格式
        var firstDayOfThisYear = new Date(tmplDate.getFullYear(), 0, 1);//本年的第一天
        var dayGap = Math.round((tmplDate.valueOf() - firstDayOfThisYear.valueOf()) / 86400000);
        var weekDay = firstDayOfThisYear.getDay();
        var gap = 0;
        if (weekDay == 0) {
            //如果本年第一天是周一，则需加上一周
            gap = 7;
        }
        return Math.ceil((dayGap + ((firstDayOfThisYear.getDay() + gap + 1) - 1)) / 7);
    },
    /**
     * 将日期转换为中文大写
     * @param theDate
     * @returns {string}
     */
    converToUpCaseDate: function (theDate) {
        var that = this;
        var tmplDate = that.changeDate(theDate);//自动判断时间格式
        var chinese = ['〇', '一', '二', '三', '四', '五', '六', '七', '八', '九'];
        var y = tmplDate.getFullYear().toString();
        var m = (tmplDate.getMonth() + 1).toString();
        var d = tmplDate.getDate().toString();
        var result = "";
        for (var i = 0; i < y.length; i++) {
            result += chinese[y.charAt(i)];
        }
        result += "年";
        if (m.length == 2) {
            if (m.charAt(0) == "1") {
                var month = m.charAt(1) == '0' ? "" : chinese[m.charAt(1)];
                result += ("十" + month + "月");
            }
        } else {
            result += (chinese[m.charAt(0)] + "月");
        }
        if (d.length == 2) {
            var day1 = d.charAt(0) == '1' ? "" : chinese[d.charAt(0)];
            var day2 = d.charAt(1) == '0' ? "" : chinese[d.charAt(1)];
            result += (day1 + "十" + day2 + "日");
        } else {
            result += (chinese[d.charAt(0)] + "日");
        }
        return result;
    },
    /**
     * 获取2个日期的间隔天数
     * @param date1
     * @param date2
     * @returns {*}
     */
    getDateDiff: function (date1, date2) {
        var that = this;
        if (!date1 || !date2) {
            return 0;
        }
        var oDate1, oDate2, diff;
        oDate1 = that.changeDate(date1);
        oDate2 = that.changeDate(date2);
        diff = parseInt(Math.abs(oDate1 - oDate2) / 1000 / 60 / 60 / 24);//把相差的毫秒数转换为天数
        return diff;
    },
    /**
     * 计算指定天数后的日期
     * @param startDate
     * @param days
     * @param weekendType 是否排除周末，1：排除，其他不排除
     */
    calcEndDateByDays: function (startDate, days, weekendType) {
        var that = this;
        var tmplDate = that.changeDate(startDate);//自动判断时间格式
        days = new Date(days);
        var dateVal = tmplDate.getTime();
        var oneDayVal = 24 * 60 * 60 * 1000;
        for (var i = 0; i < days; i++) {
            var newDay = new Date(dateVal).getDay();
            if (weekendType == 1) {
                if (newDay == 0 || newDay == 6) {
                    days++;
                }
            }
            dateVal += oneDayVal;
        }
        return new Date(dateVal).pattern('yyyy-MM-dd');
    }
};

/**
 * HJ 工具类
 */
"require:nomunge,exports:nomunge,module:nomunge";
(function ($, window) {
    /**
     * 得到元素
     * @param eleId
     * @returns {*}
     */
    function getElem(eleId) {
        if (!eleId) {
            // 防止用户操作过快，切换页面后，导致绑定表格失败
            return;
        }
        var elem = eleId;
        if (typeof elem == "string") {
            if (elem.indexOf("#") == 0) {
            } else {
                elem = "#" + elem;
            }
            elem = $(elem);
        }
        return elem;
    }

    $.fn.ctStyle = function (options) {
        $(this).focus(function () {
            $(this).addClass("input_focus");
        }).blur(function () {
                $(this).removeClass("input_focus");
        });
    };
    /**
     * 日志
     * @param {Object} msg
     */
    $.fn.log = function (msg) {
        console.log("%s: %o", msg, this);
        return this;
    };


    /**
     * 汇锦公司通用js工具对象
     */
    var HJ;

    /**
     * 常量
     * @type {{STATE_SUCCESS: string, STATE_ERROR: string, COMMON_ERROR: string, DWR_JSONP_PATH: string}}
     */
    var CONSTANT = {
        /**
         * 返回成功的标志
         */
        STATE_SUCCESS: "1",
        /**
         * 返回失败的标志
         */
        STATE_ERROR: "0",
        /**
         * 基本提示错误信息语句
         */
        COMMON_ERROR: "网络或服务异常，请稍后再试",


        /**
         * DWR_JSONP 基础请求地址
         */
        DWR_JSONP_PATH: "../dwr/jsonp"
    };


    HJ = {
        /**
         * 工具版本
         */
        varsion: '1.0'
    };


    HJ.Bind = {
        /**
         * 自适应绑定表格、下拉框、表单等等
         * @param {} elem jquery对象或元素id
         * @param {} bindValue
         * @param {} options
         */
        bind: function (elem, bindValue, options) {
            elem = getElem(elem);
            if (typeof elem != "object" || elem.length == 0) {
                //alert("can not find elem");
                throw  new Error("can not find elem--->" + elem.selector);
                return;
            }
            switch (elem[0].tagName) {
                case "INPUT" :
                    switch (elem[0].type.toLowerCase()) {
                        case "text" :
                            ;
                        case "number" :
                            //增加number绑定
                            ;
                        case "hidden" :
                            ;
                        case "password" :
                            HJ.BindFactory.bindText(elem, bindValue);
                            break;
                        case "checkbox" :
                            ;
                        case "radio" :
                            HJ.BindFactory.bindRadioOrCheckbox(elem, bindValue);
                            break;
                    }
                    break;
                case "TEXTAREA" :
                    HJ.BindFactory.bindText(elem, bindValue);
                    break;
                case "TABLE" :
                    HJ.BindFactory.bindTable(elem, bindValue);
                    break;
                case "SELECT" :
                    HJ.BindFactory.bindSelect(elem, bindValue, options);
                    break;
                case "DIV" :
                case "SPAN" :
                    elem[0].innerHTML = bindValue;
                    break;
                case "FORM" :
                    //TODO 以后在写
                    HJ.Form.bindForm(elem, bindValue);
            }

        }
    };

    HJ.Format = {
        jformat: function (jformatname, value) {
            if (jformatname && value) {
                if (jformatname.indexOf("cut") == 0) {
                    var end = jformatname.substring(3);
                    if (value.length > end) {
                        //自动截断
                        value = "<span title='" + value + "'>" + value.substring(0, end) + "..</span>";
                    }
                } else if (jformatname == "url") {
                    //构造a标签
                    value = "<a href='" + value + "' target='_blank' >" + value + "</a>";
                } else if (jformatname == "yyyy-MM-dd HH:mm:ss") {
                    //时间
                    if (value.length > 13) {
                        value = value.substring(0, 4) + "-" + value.substring(4, 6) + "-" + value.substring(6, 8) + " " + value.substring(8, 10) + ":" + value.substring(10, 12) + ":" + value.substring(12, 14);
                    }
                } else if (jformatname == "yyyy-MM-dd") {
                    //时间
                    if (value.length == 19) {
                        //格式yyyy-MM-dd HH:mm:ss
                        value = value.substring(0, 4) + "-" + value.substring(5, 7) + "-" + value.substring(8, 10);
                    }
                } else if (jformatname == "money") {
                    //金额
                    value=Number(value).toMoney();
                }else if(jformatname.indexOf("toFix") == 0){
                    //保留两位小数
                    var end = jformatname.substring(5);
                    value = Number(value).toFixed(end);
                }
            }
            return value;
        }
    };
    HJ.BindFactory = {
        /**
         * 绑定text，password,textarea
         * @param {} elem
         * @param {} value
         */
        bindText: function (elem, value) {
            elem.val(value);
        },
        /**
         * 绑定radio和checkbox
         * @param {} elem
         * @param {} value
         */
        bindRadioOrCheckbox: function (elem, value) {
            if (value instanceof Array)
                elem[0].checked = (value.indexOf(elem[0].value) > -1);
            else
                elem[0].checked = (elem[0].value == value);
        },
        /**
         * 绑定下拉框
         * @param {} elem
         * @param {} value
         * @param {} options
         */
        bindSelect: function (elem, value, options) {
            elem.empty();//清除原来的值
            var jtext = elem.attr('data-jtext');
            var jvalue = elem.attr('data-jvalue');
            var voidType = elem.attr('data-voidType');//是否自动增加“请选择..”
            if (value) {
                var option = "";
                if (voidType) {
                    option = option + "<option value='' selected>请选择..</option>";
                }
                for (var i = 0; i < value.length; i++) {
                    var data = value[i];
                    option = option + "<option value='" + data[jvalue] + "'>" + data[jtext] + "</option>";
                }
                elem.append(option);
            }
            elem.data("dataset", value);
        },
        /**
         * 绑定表格处理jselect和jfromat
         * @param {} elem
         * @param {} value
         */
        bindTable: function (elem, value) {
            var selectNameList = [];//jselect的值
            var formatNameList = [];//jformat的值
            var thead = elem[0].tHead.rows[0];
            $("#" + elem[0].id).data("dataset", value); // 考虑到兼容性，用jquery的缓存，将原始数据加入缓存
            for (var i = 0; i < thead.cells.length; i++) {
                var jselect = thead.cells[i].getAttribute("data-jselect");
                if (jselect) {
                    selectNameList[selectNameList.length] = thead.cells[i].getAttribute("data-jselect");
                }
                var jformat = thead.cells[i].getAttribute("data-jformat");
                var jtext = thead.cells[i].getAttribute("data-jtext");
                if (jformat && value) {
                    for (var j = 0; j < value.length; j++) {
                        value[j][jtext] = HJ.Format.jformat(jformat, value[j][jtext]);
                    }
                }
            }
            if (selectNameList.length > 0 && value && value[0]) {
                for (var i = 0; i < selectNameList.length; i++) {
                    var tjselect = $('body').data(selectNameList[i]);
                    if (!tjselect) {
                        // TODO 没有的话就同步查询
                        var jsonpUrl = CONSTANT.DWR_JSONP_PATH + "/basicBO/qryZbList/" + selectNameList[i];
                        $.ajax({
                            type: "get",
                            url: jsonpUrl,
                            async: false,
                            dataType: "json",
                            cache: false,
                            success: function (rep) {
                                //加入到body
                                if (rep.stateCode == CONSTANT.STATE_SUCCESS) {
                                    $('body').data(selectNameList[i], rep.list);
                                }
                            },
                            error: function (e) {
                                console.info(HJ.printObj(e));
                            }
                        });
                    }
                }
            }
            HJ.BindFactory._bindTable(elem, value);

        },
        /**
         * 绑定表格画页面
         * @param {} elem
         * @param {} value
         * @param {} ret
         */
        _bindTable: function (elem, value, ret) {
            var jHeight = parseInt(elem[0].getAttribute("data-jheight"));
            var dataHeader = [];
            var selectList = [];
            var tBody = elem[0].getElementsByTagName("TBODY")[0];
            if (elem[0].getElementsByTagName("TBODY").length > 0) {
                while (tBody.rows.length > 1) {
                    tBody.deleteRow(1);
                }
            }
            // style
            var headerTR = elem[0].tHead.rows[0];
            for (var i = 0; i < headerTR.cells.length; i++) {
                dataHeader[dataHeader.length] = headerTR.cells[i]
                    .getAttribute("data-jtext");
                selectList[selectList.length] = headerTR.cells[i]
                    .getAttribute("data-jselect");
            }
            for (var i = 0; value && i < value.length; i++) {
                var tr;
                if (i == 0) { // if the first row
                    tr = tBody.rows[0];
                    tr.className = '';
                } else { // else copy the first row
                    tr = tBody.rows[0].cloneNode(true);
                }
                if (i > 0) {
                    tBody.appendChild(tr);
                }

                var data = value[i];
                for (var j = 0; j < tr.cells.length; j++) {
                    var td = tr.cells[j];
                    var insertValue = data[dataHeader[j]];
                    if (selectList[j] && insertValue) {
                        //如果含有jselect，则自动转换
                        var zblist = $('body').data(selectList[j]);
                        for (var k = 0; k < zblist.length; k++) {
                            var tobj = zblist[k];
                            if (insertValue == tobj.zbValue) {
                                insertValue = tobj.zbName;
                            }
                        }
                    }
                    if (insertValue) {
                        td.innerHTML = insertValue;
                    } else if (insertValue == 0 || insertValue == '0') {
                        td.innerHTML = insertValue;
                    }
                    else {
                        td.innerHTML = "&nbsp;";
                    }
                }
            }

            // TODO 加入点击选中颜色
            //bindClickStyle(elem);

            function bindClickStyle(elem) {
                var $table = elem;
                if($table.attr('class').indexOf('multipleTable')==-1){
                    //如果是多选table则不添加点击tr样式
                    var $trs = $table.find("tbody tr");
                    var trCls = new Array();
                    $trs.each(function () {
                        trCls.push($(this).attr('class'));
                    });
                    $trs.off('click.selectColor');// 加上一个点击切换颜色的click 命名空间
                    $trs.on('click.selectColor', function () {
                        resetTrColor($trs, trCls);
                        $(this).attr('class', 'tr_click');
                    });
                }
                function resetTrColor(trs, cls) {
                    trs.each(function (index) {
                        var trcss = cls[index] ? cls[index] : "";
                        $(this).attr('class', trcss);
                    });
                }
            }
        }
    };
    /**
     * 表单操作类
     * @type
     */
    HJ.Form = {
        /**
         * 将表单form转换为js对象
         * @param {} form
         * @param {} ignoreButton
         * @return {}
         */
        formToBean: function (form, ignoreButton) {
            var object = {};
            form = getElem(form);
            var elements = form[0].elements;
            for (var i = 0; i < elements.length; i++) {
                var element = elements[i];
                var key;
                if (element.name) {
                    key = element.name;
                    var elementName = element.name;
                    var ar = elementName.split('.');
                    if (ar.length == 2) {
                        //支持name含有.，可以访问如staff.staffId这样的类型
                        var obj1 = {};
                        if (object[ar[0]]) {
                            //如果有staff这个对象的话
                            obj1 = object[ar[0]];
                        }
                        obj1[ar[1]] = element.value;
                        object[ar[0]] = obj1;
                        continue;
                    } else if (ar.length == 3) {
                        //支持name含有.，可以访问如staff.cust.custId这样的类型
                        var obj1 = {};
                        if (object[ar[0]]) {
                            //有staff这个对象，第一个对象存在
                            obj1 = object[ar[0]];
                        }
                        var obj2 = {};//默认第二个对象是新增的
                        if (obj1[ar[1]]) {
                            //第二个cust对象
                            obj2 = obj1[ar[1]];//第二个对象
                        }
                        var obj3 = {};//.的最后一个对象obj3一定是新增的
                        obj2[ar[2]] = element.value;//给第二个对象的属性ar[2]赋值
                        obj1[ar[1]] = obj2;//给中间这个对象赋值
                        object[ar[0]] = obj1;
                        continue;
                    }
                } else {
                    continue;
                }
                switch (element.type) {
                    case "radio" :
                        if (element.checked) {
                            object[key] = element.value;
                        }
                        break;
                    case "checkbox" :
                        if (!form.get(0)[element.name].length) {
                            if (element.checked)
                                object[key] = element.value;
                            else
                                object[key] = "";
                        } else {
                            if (!object[key + "List"]) {
                                object[key + "List"] = new Array();
                            }
                            ;
                            if (element.checked) {
                                object[key + "List"].push(element.value);
                            }
                        }
                        break;
                    case "select-one" :
                        var value = '', opt, index = element.selectedIndex;
                        if (index >= 0) {
                            opt = element.options[index];
                            value = opt.value;
                            if (!value && !('value' in opt))
                                value = opt.text;
                        }
                        object[key] = value;
                        break;
                    case "select-multiple" :
                        if (!object[key + "List"]) {
                            object[key + "List"] = new Array();
                        }
                        ;
                        for (var j = 0; j < element.options.length; j++) {
                            var opt = element.options[j];
                            if (opt.selected) {
                                var optValue = opt.value;
                                if (!optValue && !('value' in opt))
                                    optValue = opt.text;
                                object[key + "List"].push(optValue);
                            }
                        }
                        break;
                    default :
                        object[key] = element.value;
                        break;
                }
            }
            return object;
        },
        /**
         * 绑定表单,是jquery的对象
         * @param {} form
         * @param {} data
         */
        bindForm: function (form, data) {
            //处理span绑定信息
            form.find('span[data-jtext]').each(function(i){
               //找到
                var $span=$(this);
                var jtext=$span.attr('data-jtext');
                var ar = jtext.split('.');//支持name含有.，可以访问如staff.staffId这样的类型
                var res = data;
                for (var key in ar) {
                    res=res[ar[key]]?res = res[ar[key]]:"";
                }
                var val = res || "";
                var jformatName = $span.attr("data-jformat");//绑定表单的格式化
                if (jformatName) {
                    val = HJ.Format.jformat(jformatName, val);
                }
                $span.html(val);
            });

            //处理表单元素
            for (var i = 0; i < form[0].elements.length; i++) {
                var element = form[0].elements[i];
                var elementName = element.name;
                if (!elementName) {
                    continue;
                }
                var ar = elementName.split('.');//支持name含有.，可以访问如staff.staffId这样的类型
                var res = data;
                for (var key in ar) {
                    res=res[ar[key]]?res = res[ar[key]]:"";
                }
                var val = res || "";
                var jformatName = element.getAttribute("data-jformat");//绑定表单的格式化
                if (jformatName) {
                    val = HJ.Format.jformat(jformatName, val);
                }
                switch (element.type) {
                    case "text" :
                        ;
                    case "hidden" :
                        ;
                    case "number" :
                        ;
                    case "password" :
                        element.value = val;
                        //处理有combobox自动完成框的
                        //console.info("--->"+val);
                        var $ele = $(element);
                        if ($ele.attr('class') == 'combo-value') {
                            $(element).parent().prev().combobox('setValue', val);
                        }
                        break;
                    case "textarea" :
                        element.innerHTML = val;
                        break;
                        ;
                    case "radio" :
                    case "checkbox" :
                        if (val instanceof Array) {
                            element.checked = (val.indexOf(element.value) > -1);
                        } else {
                            //如果值里面有,号分隔，这里自动split来进行绑定
                            if (val.indexOf(',') > -1) {
                                //存在,号
                                var tval = val.split(',');
                                element.checked = (tval.indexOf(element.value) > -1);
                            } else {
                                //单个值，普通的bind模式
                                element.checked = (element.value == val);
                            }
                        }
                        break;
                    case "select" :
                    case "select-one" :
                    case "select-multiple" :
                        for (var j = 0; j < element.options.length; j++) {
                            var option = element.options[j];
                            if (val instanceof Array) {
                                option.selected = (val.indexOf(option.value) > -1);
                            } else {
                                option.selected = (option.value == val);
                            }
                        }
                        break;
                }
            }
        },
        /**
         * 重置表单
         * @param {Object} formId
         */
        resetForm: function (formId) {
            var $form = getElem(formId);
            $form[0].reset();//重置表单
            $form.find('textarea').html('');//针对有textarea的特别删除
        },
        /**
         * 重置表单和combobox
         * @param {Object} formId
         */
        resetFormAndComBox: function (formId) {
            var $form = getElem(formId);
            $form[0].reset();//重置表单
            $form.find('textarea').html('');//针对有textarea的特别删除
            $form.find("input[comboname]").each(function () {
                var _ops = $(this).combobox('options');
                var _dv = _ops.valueField;
                $(this).combobox('selectedIndex',0);
            });
        }
    };
    /**
     * 增加工具类对象
     */
    HJ.util = {


        /**
         * 获得JSON对象属性值
         *
         * @param json
         * @param pro
         * @return
         */
        getJsonValue: function (json, pro) {
            for (var i in json) {
                if (pro == i) {
                    return json[i];
                }
            }
        },
        /**
         * 点击回车键事件响应，一般写在input的onkeypress事件
         */
        onReturn: function (event, action) {
            if (!event)
                event = window.event;
            if (event && event.keyCode && event.keyCode == 13) {
                action();
            }
        },
        /**
         * 分页控件
         * @param {} count 总记录数
         * @param {} currpage 当前页码
         * @param {} pagesize 每页大小
         * @param {} method 执行方法
         * @param {} elemid 分页控件id
         */
        drawPageControl: function (count, currpage, pagesize, method, elemid) {
            if (!elemid) {
                elemid = "#pagecontrol";
            }
            if (count) {
                var apage = "";
                var pageCount = HJ.util.getFullNum((Number(count)), Number(pagesize));//总页数
                if (currpage > pageCount) {
                    currpage = pageCount;//处理总数/页大小<传入页码
                }
                var front = currpage - 1;//上一页数值
                var next = currpage + 1;//下一页数值
                if (currpage == 1) {
                    front = 1;//当前页为1，上一页也为1
                }
                if (currpage == pageCount) {
                    next = currpage;//当前页为尾页，下一页也为尾页
                }
                if (pageCount > 1) {
                    // 索引的sum值 代表的是页面中最大显示页数
                    var sumindex = 20;
                    // 开始的索引值
                    var startindex = 1;
                    // 结束的索引值
                    var endindex = 1;
                    if (currpage <= sumindex / 2 + 1) {
                        startindex = 1;
                        endindex = currpage + sumindex / 2 - 1;
                        // 当结束的索引值>总页数
                        if (endindex > pageCount) {
                            endindex = pageCount;
                        }
                    } else if (currpage > sumindex / 2 + 1) {
                        startindex = currpage - sumindex / 2;
                        endindex = currpage + sumindex / 2 - 1;
                        // 当结束的索引值>总页数
                        // 当结束的索引值>索引的sum值 代表的是页面中最大显示页数
                        if (endindex > pageCount && endindex > sumindex) {
                            endindex = pageCount;
                            startindex = pageCount - sumindex - 1;
                        }
                        // 当结束的索引值<索引的sum值 代表的是页面中最大显示页数
                        if (endindex < sumindex) {
                            startindex = 1;
                            endindex = pageCount;
                        }
                    }
                    for (var i = startindex; i <= endindex; i++) {
                        apage += "<a  href='javascript:" + method
                            + "(" + i + ");' class='a_pagelinks_off hj_page" + i + "'>" + i + "</a>";
                    }
                } else if (pageCount == 1) {
                    apage += "<a class='a_pagelinks_off hj_page' href='javascript:" + method + "(1);'>1</a>";
                }
                var homePage = "<a class='a_pagelinks_off' href='javascript:" + method + "(1);'>首页</a>";
                var endPage = "<a class='a_pagelinks_off' href='javascript:" + method + "(" + pageCount + ");'>尾页</a>";
                var frontPage = "<a class='a_pagelinks_off' href='javascript:" + method + "(" + front + ");'>上一页</a>";
                var nextPage = "<a class='a_pagelinks_off' href='javascript:" + method + "(" + next + ");'>下一页</a>";
                $(elemid).html(homePage + " " + frontPage + " <span class='page_word'>共" + count + "条记录/" + pageCount + "页</span>"
                    + "<span id='pageNumList'>" + apage + "</span> " + nextPage + " " + endPage);
                $(elemid).find('.hj_page' + currpage).addClass("a_pagelinks_on");
                $(elemid).find('.hj_page' + currpage).removeClass("a_pagelinks_off");
            } else {
                $(elemid).text("抱歉，没有查询到记录，您可以试试更换条件查询。");
            }
        },
        /**
         * 两数相除小数全部入一位
         */
        getFullNum: function (a, b) {
            var ret = a / b;
            var point = (ret + "").indexOf(".");
            if (point > -1) {
                if (Number((ret + "").substr(point + 1)) > 0) {
                    return Number((ret + "").substr(0, point)) + 1;
                }
            } else {
                return ret;
            }
        },
        /**
         * 递归打印js对象
         * @param data 数据
         * @param showLevels 深度
         * @param options
         * @returns
         */
        toDescriptiveString: function (data, showLevels, options) {
            if (showLevels === undefined)
                showLevels = 4;
            var opt = {};
            if (HJ.util._isObject(options))
                opt = options;
            var defaultoptions = {
                escapeHtml: false,
                baseIndent: "",
                childIndent: "\u00A0\u00A0",
                lineTerminator: "\n",
                oneLineMaxItems: 5,
                shortStringMaxLength: 13,
                propertyNameMaxLength: 30
            };
            for (var p in defaultoptions) {
                if (!(p in opt)) {
                    opt[p] = defaultoptions[p];
                }
            }

            var skipDomProperties = {
                document: true,
                ownerDocument: true,
                all: true,
                parentElement: true,
                parentNode: true,
                offsetParent: true,
                children: true,
                firstChild: true,
                lastChild: true,
                previousSibling: true,
                nextSibling: true,
                innerHTML: true,
                outerHTML: true,
                innerText: true,
                outerText: true,
                textContent: true,
                attributes: true,
                style: true,
                currentStyle: true,
                runtimeStyle: true,
                parentTextEdit: true
            };

            function recursive(data, showLevels, indentDepth, options) {
                var reply = "";
                try {

                    if (typeof data == "string") {
                        var str = data;
                        if (showLevels == 0 && str.length > options.shortStringMaxLength)
                            str = str.substring(0, options.shortStringMaxLength - 3) + "...";
                        if (options.escapeHtml) {


                            var lines = str.split("\n");
                            for (var i = 0; i < lines.length; i++)
                                lines[i] = HJ.util.escapeHtml(lines[i]);
                            str = lines.join("\n");
                        }
                        if (showLevels == 0) {
                            str = str.replace(/\n|\r|\t/g, function (ch) {
                                switch (ch) {
                                    case "\n":
                                        return "\\n";
                                    case "\r":
                                        return "";
                                    case "\t":
                                        return "\\t";
                                }
                            });
                        }
                        else {
                            str = str.replace(/\n|\r|\t/g, function (ch) {
                                switch (ch) {
                                    case "\n":
                                        return options.lineTerminator + indent(indentDepth + 1, options);
                                    case "\r":
                                        return "";
                                    case "\t":
                                        return "\\t";
                                }
                            });
                        }
                        reply = '"' + str + '"';
                    }


                    else if (typeof data == "function") {
                        reply = "function";
                    }


                    else if (HJ.util._isArray(data)) {
                        if (showLevels == 0) {
                            if (data.length > 0)
                                reply = "[...]";
                            else
                                reply = "[]";
                        }
                        else {
                            var strarr = [];
                            strarr.push("[");
                            var count = 0;
                            for (var i = 0; i < data.length; i++) {
                                if (!(i in data))
                                    continue;
                                var itemvalue = data[i];
                                if (count > 0)
                                    strarr.push(", ");
                                if (showLevels == 1) {
                                    if (count == options.oneLineMaxItems) {
                                        strarr.push("...");
                                        break;
                                    }
                                }
                                else {
                                    strarr.push(options.lineTerminator + indent(indentDepth + 1, options));
                                }
                                if (i != count) {
                                    strarr.push(i);
                                    strarr.push(":");
                                }
                                strarr.push(recursive(itemvalue, showLevels - 1, indentDepth + 1, options));
                                count++;
                            }
                            if (showLevels > 1)
                                strarr.push(options.lineTerminator + indent(indentDepth, options));
                            strarr.push("]");
                            reply = strarr.join("");
                        }
                    }


                    else if (HJ.util._isObject(data) && !HJ.util._isDate(data)) {
                        if (showLevels == 0) {
                            reply = HJ.util._detailedTypeOf(data);
                        }
                        else {
                            var strarr = [];
                            if (HJ.util._detailedTypeOf(data) != "Object") {
                                strarr.push(HJ.util._detailedTypeOf(data));
                                if (typeof data.valueOf() != "object") {
                                    strarr.push(":");
                                    strarr.push(recursive(data.valueOf(), 1, indentDepth, options));
                                }
                                strarr.push(" ");
                            }
                            strarr.push("{");
                            var isDomObject = HJ.util._isHTMLElement(data);
                            var count = 0;
                            for (var prop in data) {
                                var propvalue = data[prop];
                                if (isDomObject) {
                                    if (!propvalue)
                                        continue;
                                    if (typeof propvalue == "function")
                                        continue;
                                    if (skipDomProperties[prop])
                                        continue;
                                    if (prop.toUpperCase() == prop)
                                        continue;
                                }
                                if (count > 0)
                                    strarr.push(", ");
                                if (showLevels == 1) {
                                    if (count == options.oneLineMaxItems) {
                                        strarr.push("...");
                                        break;
                                    }
                                }
                                else {
                                    strarr.push(options.lineTerminator + indent(indentDepth + 1, options));
                                }
                                strarr.push(prop.length > options.propertyNameMaxLength ? prop.substring(0, options.propertyNameMaxLength - 3) + "..." : prop);
                                strarr.push(":");
                                strarr.push(recursive(propvalue, showLevels - 1, indentDepth + 1, options));
                                count++;
                            }
                            if (showLevels > 1 && count > 0)
                                strarr.push(options.lineTerminator + indent(indentDepth, options));
                            strarr.push("}");
                            reply = strarr.join("");
                        }
                    }


                    else {
                        reply = "" + data;
                    }

                    return reply;
                }
                catch (err) {
                    return (err.message ? err.message : "" + err);
                }
            }

            function indent(count, options) {
                var strarr = [];
                strarr.push(options.baseIndent);
                for (var i = 0; i < count; i++) {
                    strarr.push(options.childIndent);
                }
                return strarr.join("");
            };

            return recursive(data, showLevels, 0, opt);
        },
        /**
         * 模版
         * @param {Object} t html模版
         * @param {Object} dd 数据
         * @param {Object} cb 自定义callback
         * @param {Object} scb
         */
        tmpl: function (t, dd, cb, scb) {
            if (!dd) {
                return '';
            }
            if (dd.length > 0) {
                //数组
                var r = "";
                {
                    var index = 0;
                    var l = dd.length;//赋值长度
                    for (var i in dd) {
                        if (scb)
                            scb(0, i, dd[i]);
                        var rr = t_f(t, dd[i], index, l, cb);
                        if (scb)
                            scb(1, rr, dd[i]);
                        r += rr;
                        index++;
                    }
                }
                return r;
            } else if (dd.length == 0) {
                //如果长度为0，时候数组的话，什么都不做
                return '';
            } else {
                //单个对象,dd不是数组
                return t_f(t, dd, -1, -1, cb);
            }
            /**
             * 是否是数组
             * @param {Object} obj
             */
            function _isArray(obj) {
                return Object.prototype.toString.call(obj) === '[object Array]';
            };
            function _f(d, c, k1, k2, l) {
                var q = c.match(/(first:|last:)(\"|\'*)([^\"\']*)(\"|\'*)/);
                if (!q)
                    return;
                if (q[1] == k1) {
                    if (q[2] == '\"' || q[2] == '\'') {
                        return q[3];
                    }
                    else
                        return d[q[3]];
                }
                else if (q[1] == k2 && l > 1)
                    return "";
            };
            function t_f(t, d, i, l, cb) {
                return t.replace(/\$\{([^\}]*)\}/g, function (m, c) {
                    /**
                     * 遍历对象属性获取值
                     * @param {Object} res 对象
                     * @param {Object} array 要解析的对象属性数组
                     */
                    function getValue(res, array) {
                        for (var key in array) {
                            if (res) {
                                res = res[array[key]];
                                if (!res && res != 0) {
                                    HJ.log('data object parameter [' + array[key] + '] is undefined!');
                                }
                            } else {
                                //打印到浏览器日志
                                HJ.log('data object parameter [' + array[key - 1] + '] is undefined!');
                                break;
                            }
                        }
                        return res;
                    }

                    if (c.match(/index:/)) {
                        return i;
                    }
                    if (c.match(/encodeHTML:/)) {
                        var q = c.match(/(encodeHTML:)(\"|\'*)([^\"\']*)(\"|\'*)/);
                        var ar = q[3].split('.');
                        var res = getValue(d, ar);//获取值
                        encodeHTML = function (source) {
                            return String(source)
                                .replace(/&/g, '&amp;')
                                .replace(/</g, '&lt;')
                                .replace(/>/g, '&gt;')
                                .replace(/\\/g, '&#92;')
                                .replace(/"/g, '&quot;')
                                .replace(/'/g, '&#39;');
                        };
                        return encodeHTML(res);
                    }
                    if (c.match(/YYYYMMDD:/)) {
                        //年月日,这个要放在YYMMDD之前
                        var q = c.match(/(YYYYMMDD:)(\"|\'*)([^\"\']*)(\"|\'*)/);
                        var ar = q[3].split('.');
                        var res = getValue(d, ar);//获取值
                        var t = '';
                        if (res && res.length == 19) {
                            //2013-07-22 16:44:14
                            t = res.substring(0, 10);
                        } else if (res && res.length == 10) {
                            t = res;
                        }
                        return t;
                    }
                    if (c.match(/YYMMDD:/)) {
                        //年月日
                        var q = c.match(/(YYMMDD:)(\"|\'*)([^\"\']*)(\"|\'*)/);
                        var ar = q[3].split('.');
                        var res = getValue(d, ar);//获取值
                        var t = '';
                        if (res && res.length == 19) {
                            //2013-07-22 16:44:14
                            t = res.substring(2, 10);
                        } else if (res && res.length == 10) {
                            t = res.substring(2, 10);
                        }
                        return t;
                    }
                    if (c.match(/YYMMDDTIME:/)) {
                        //年月日
                        var q = c.match(/(YYMMDDTIME:)(\"|\'*)([^\"\']*)(\"|\'*)/);
                        var ar = q[3].split('.');
                        var res = getValue(d, ar);//获取值
                        var t = '';
                        if (res && res.length == 19) {
                            //2013-07-22 16:44:14
                            t = res.substring(2, 19);
                        }
                        return t;
                    }
                    if (c.match(/toMoney:/)) {
                        //价格
                        var q = c.match(/(toMoney:)(\"|\'*)([^\"\']*)(\"|\'*)/);
                        var ar = q[3].split('.');
                        var res = getValue(d, ar);//获取值
                        var t = Number(res).toMoney();//转换为货币格式
                        return t;
                    }
                    if (c.match(/zb:/)) {
                        //格式 zb:zbtype#value
                        var q = c.match(/(zb:)(\"|\'*)([^\"\']*)(\"|\'*)/);
                        var tt = q[3].split('#');//zbtype#value
                        var zbType = tt[0];//zbtype
                        var tzbvalue = tt[1];//zbvalue
                        var zbvalue = '';
                        if (tzbvalue.indexOf('.') > -1) {
                            //如staff.staffId
                            var ar = tzbvalue.split('.');//支持name含有.，可以访问如staff.staffId这样的类型
                            zbvalue = getValue(d, ar);//获取值
                        } else {
                            zbvalue = d[tt[1]];//value
                        }
                        var zbback = '';
                        if (zbvalue) {
                            zbback = getZbName(zbType, zbvalue);
                        }
                        return zbback;
                    }
                    if (c.match(/toMoneyFloat:/)) {
                        //价格有精度
                        var q = c.match(/(toMoneyFloat:)(\"|\'*)([^\"\']*)(\"|\'*)/);
                        var ar = q[3].split('.');
                        var res = getValue(d, ar);//获取值
                        var t = Number(res).toMoneyFloat();//转换为货币格式
                        return t;
                    }
                    if (c.match(/toRMB:/)) {
                        //价格，中文大写
                        var q = c.match(/(toRMB:)(\"|\'*)([^\"\']*)(\"|\'*)/);
                        var ar = q[3].split('.');
                        var res = getValue(d, ar);//获取值
                        var t = Number(res).toRMB();//转换为货币格式
                        return t;
                    }
                    if (c.match(/toPercent:/)) {
                        //百分比
                        var q = c.match(/(toPercent:)(\"|\'*)([^\"\']*)(\"|\'*)/);
                        var ar = q[3].split('.');
                        var res = getValue(d, ar);//获取值
                        var t = Number(res).toPercent();//转换为百分比
                        return t;
                    }
                    if (c.match(/autobreak:/)) {
                        //自定义截断
                        if (((i + 1) % 4) == 0) {
                            return '</br>';
                        }
                        return '';
                    }
                    if (c.match(/autobreak2:/)) {
                        //自定义截断
                        if (((i + 1) % 6) == 0) {
                            return '</br>';
                        }
                        return '';
                    }
                    if (c.match(/cb:/) && cb) {
                        return cb(d, c.match(/cb:(.*)/));
                    }
                    if (i == 0) {
                        var s = _f(d, c, "first:", "last:", l);
                        if (s)
                            return s;
                    }
                    if (i == (l - 1)) {
                        var s = _f(d, c, "last:", "first:", l);
                        if (s)
                            return s;
                    }
                    var ar = c.split('.');
                    var res = getValue(d, ar);//获取值
                    if (res === 0) {
                        //对为0的做特殊判断
                        return "0";
                    }
                    return res || "";
                });
            };
        }
    };

    HJ.util._debug = function (message, stacktrace) {
        var written = false;
        try {
            if (window.console) {
                if (stacktrace && window.console.trace)
                    window.console.trace();
                window.console.log(message);
                written = true;
            }
            else if (window.opera && window.opera.postError) {
                window.opera.postError(message);
                written = true;
            }
        }
        catch (ex) {
        }

        if (!written) {
            var debug = document.getElementById("dwr-debug");
            if (debug) {
                var contents = message + "<br/>" + debug.innerHTML;
                if (contents.length > 2048)
                    contents = contents.substring(0, 2048);
                debug.innerHTML = contents;
            }
        }
    };
    /**
     * 是否是html对象
     * @param ele
     * @param nodeName
     * @returns {Boolean}
     */
    HJ.util._isHTMLElement = function (ele, nodeName) {
        if (ele == null || typeof ele != "object" || ele.nodeName == null) {
            return false;
        }
        if (nodeName != null) {
            var test = ele.nodeName.toLowerCase();
            if (typeof nodeName == "string") {
                return test == nodeName.toLowerCase();
            }
            if (HJ.util._isArray(nodeName)) {
                var match = false;
                for (var i = 0; i < nodeName.length && !match; i++) {
                    if (test == nodeName[i].toLowerCase()) {
                        match = true;
                    }
                }
                return match;
            }
            HJ.util._debug("HJ.util._isHTMLElement was passed test node name that is neither a string or array of strings");
            return false;
        }
        return true;
    };

    HJ.util._detailedTypeOf = function (x) {
        var reply = typeof x;
        if (reply == "object") {
            reply = Object.prototype.toString.apply(x);
            reply = reply.substring(8, reply.length - 1);
        }
        return reply;
    };
    /**
     * 是否是js对象
     * @param data
     * @returns {Boolean}
     */
    HJ.util._isObject = function (data) {
        return (data && typeof data == "object");
    };
    /**
     * 是否是数组
     * @param data
     * @returns {Boolean}
     */
    HJ.util._isArray = function (data) {
        //or return Object.prototype.toString.call(obj) === '[object Array]';
        return (data && data.join);
    };
    /**
     * 是否是日期类型
     * @param data
     * @returns
     */
    HJ.util._isDate = function (data) {
        return (data && data.toUTCString) ? true : false;
    };
    /**
     * 重新命名bind方法
     */
    HJ.bind = HJ.Bind.bind;
    /**
     * 表单到bean对象
     */
    HJ.form2Bean = HJ.Form.formToBean;
    /**
     * 重置表单
     */
    HJ.resetForm = HJ.Form.resetForm;
    /**
     * 重新命名tmpl方法
     */
    HJ.tmpl = HJ.util.tmpl;
    /**
     * 从命名日志方法
     */
    HJ.log = HJ.util._debug;
    /**
     * 打印对象
     */
    HJ.printObj = HJ.util.toDescriptiveString;

    window.HJ = HJ; //将HJ输出到window
    HJ.CONSTANT=window.CONSTANT = CONSTANT;

    //拓展jq
    $.fn.HJ = function(options,params){
        if(typeof options=="string"){
            var method = $.fn.HJ.methods[options.toLowerCase()];
            return method(this, params);
        }
    }
    // HJ jq methods
    //使用如 $("#test").HJ('bind',["ss","ss"])
    $.fn.HJ.methods={
        bind:function(jq,data){
            HJ.bind(jq,data);
        },
        resetform:function(jq){
            if(jq[0].tagName==='FORM'){
                HJ.resetForm(jq);
            }
        },
        resetformandcombox:function(jq){
            if(jq[0].tagName==='FORM'){
                HJ.resetFormAndComBox(jq);
            }
        },
        form2bean:function(jq,ignoreButton){
            if(jq[0].tagName==='FORM'){
                return HJ.form2Bean(jq,ignoreButton);
            }
        }
    }
    /**
     * 输出amd 模块
     */
    if ( typeof define === "function" && define.amd) {
        define( "HJ", ["jquery"], function () { return HJ; } );
    }
})(jQuery, window);







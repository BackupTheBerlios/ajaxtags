/**
 * Copyright 2009 AjaxTags-Team
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
/*jslint bitwise: true, eqeqeq: true, immed: true, newcap: true, nomen: true, regexp: true, undef: true, white: true, maxerr: 50, indent: 4 */
/*global $,$$,$F,Ajax,Autocompleter,Class,Element,Event,Form,Option,Prototype,overlib*/

var AjaxJspTag = {
    Version: '1.5',
    /**
     * Store all tags which have listeners.
     */
    tags: [],
    /**
     * Push listener for later reload.
     */
    add: function (tag) {
        AjaxJspTag.tags.push(tag);
    },
    /**
     * Remove listener. This is not used. All listeners are reloaded after change
     * of content.
     */
    remove: function (tag) {
        AjaxJspTag.tags = AjaxJspTag.tags.without(tag);
    },
    /**
     * Reload the listeners. Any listener has a function called setListeners with
     * no arguments.
     * TODO reload after each request
     */
    reload: function () {
        AjaxJspTag.tags.each(AjaxJspTag.fireListener);
    },
    // for internal use
    fireListener: function (tag) {
        if (Object.isFunction(tag.setListeners)) {
            tag.setListeners();
        }
    }
};

// prototype and scriptaculous must be loaded before this script

/**
 * Global Variables.
 * TODO move to namespace AjaxJspTag
 */
var AJAX_DEFAULT_PARAMETER = "ajaxParameter";
var AJAX_DEFAULT_PARAMETER_REGEXP = new RegExp("(\\{" + AJAX_DEFAULT_PARAMETER + "\\})", 'g');

var AJAX_VOID_URL = "javascript://nop";
var AJAX_PORTLET_MAX = 1;
var AJAX_PORTLET_MIN = 2;
var AJAX_PORTLET_CLOSE = 3;
var AJAX_CALLOUT_OVERLIB_DEFAULT = "STICKY,CLOSECLICK,DELAY,250,TIMEOUT,5000,VAUTO,WRAPMAX,240,CSSCLASS,FGCLASS,'olfg',BGCLASS,'olbg',CGCLASS,'olcg',CAPTIONFONTCLASS,'olcap',CLOSEFONTCLASS,'olclo',TEXTFONTCLASS,'oltxt'";

/**
 * Response Parsers defaults and functions which can be used for HTML, TEXT and
 * XML call DefaultResponseParser(TYPE) with the needed type
 */
var DefaultResponseParser = Class.create({
    initialize: function (defaultType, isPlainText) {
        this.type = defaultType || "xml";
        this.plaintext = false;
        if (arguments.length >= 2) {
            this.plaintext = (isPlainText && true);
        }
        this.content = null; // reset in load
        this.contentText = null;
        this.contentXML = null;
    },
    load: function (request) {
        this.contentText = request.responseText;
        this.contentXML = request.responseXML;
        this.content = null; // init
        this.parse();
    },
    parse: function (forceType) {
        var cache = this.type;
        var xdata = null;
        if (arguments.length >= 1) {
            this.type = forceType;
        }
        if (this.type === "xml") {
            var root = this.contentXML.documentElement;
            var responseNodes = root.getElementsByTagName("response");
            xdata = [];
            var i = 0, j = 0, k = 0, len1 = responseNodes.length, len2 = null, len3 = null;
            var nameNodes = null, valueNodes = null, row = null, items = null;
            var itemNode, nameNode, responseNode, valueNode;
            for (i = 0; i < len1; i++) {
                responseNode = responseNodes[i];
                items = responseNode.getElementsByTagName("item");
                len2 = items.length;
                for (j = 0; j < len2; j++) {
                    itemNode = items[j];
                    nameNodes = itemNode.getElementsByTagName("name");
                    valueNodes = itemNode.getElementsByTagName("value");
                    row = [];
                    len3 = nameNodes.length;
                    for (k = 0; k < len3; k++) {
                        nameNode = nameNodes[k];
                        row.push(nameNode.firstChild ? nameNode.firstChild.nodeValue : "");
                    }
                    if (row.length !== 1) {
                        throw new Error("XML is not supported");
                    }
                    len3 = valueNodes.length;
                    for (k = 0; k < len3; k++) {
                        valueNode = valueNodes[k];
                        row.push(valueNode.firstChild ? valueNode.firstChild.nodeValue : "");
                    }
                    xdata.push(row);
                }
            }
        } else if (this.type === "plain") {
            xdata = this.contentText;
        } else if (this.type === "text") {
            xdata = [];
            this.contentText.split('\n').each(function (line) {
                xdata.push(line.split(','));
            });
        } else if (this.type === "html") {
            xdata = this.contentText; // response is HMTL
        } else if (this.type === "xmltohtml") {
            // replace ResponseXmlToHtmlParser
            this.parse("xml"); // parse XML stream
            xdata = new Element("div");
            var div = null, h1 = null;
            this.content.each(function (row) {
                h1 = new Element("h1");
                if (!this.plaintext) {
                    h1.innerHTML += row[0];
                } else {
                    h1 = h1.update(row[0]);
                }
                xdata.appendChild(h1);
                row.without(row[0]).each(function (line) {
                    div = new Element("div");
                    if (!this.plaintext) {
                        div.innerHTML += line;
                    } else {
                        div.update(line);
                    }
                    xdata.appendChild(div);
                });
            });
            // #4 // now switch content
            xdata = (h1 !== null) ? xdata.innerHTML : "";
        // skip plz
        } else if (this.type === "xmltohtmllist") {
            this.parse("xml");
            xdata = new Element("div");
            var ul = new Element("ul");
            var liElement = null;
            this.content.each(function (row) {
                liElement = new Element("li", {id: row[1]});
                if (this.plaintext) {
                    liElement.update(row[0]);
                } else {
                    liElement.innerHTML = row[0];
                }
                ul.appendChild(liElement);
            });
            xdata.appendChild(ul);
            xdata = (liElement !== null) ? xdata.innerHTML : "";
        }
        this.content = xdata;
        this.type = cache; // just copy it back
    }
});

// ResponseCallBackXmlParser is broken!!! prototype exec javascript ?
var ResponseXmlToHtmlLinkListParser = Class.create(DefaultResponseParser, {
    initialize: function ($super) {
        $super("xmltohtmllinklist");
    },
    load: function ($super, request) {
        this.collapsedClass = request.collapsedClass;
        this.treeClass = request.treeClass;
        this.nodeClass = request.nodeClass;
        this.expandedNodes = [];
        $super(request);
    },
    parse: function () {
        var responseNodes = this.contentXML.documentElement.getElementsByTagName("response");
        if (responseNodes.length > 0) {
            var itemNodes = responseNodes[0].getElementsByTagName("item");
            var ul = (itemNodes.length === 0) ? null : new Element('ul');
            if (ul) {
                ul.addClassName(this.treeClass);
            }
            var nameNodes = null, valueNodes = null, urlNodes = null, collapsedNodes = null, leafnodes = null;
            var name = null, value = null, url = null, collapsed = false, leaf = false;
            var li = null, i = null, link = null, div = null, len = itemNodes.length;
            var itemNode = null;
            for (i = 0; i < len; i++) {
                itemNode = itemNodes[i];
                nameNodes = itemNode.getElementsByTagName("name");
                valueNodes = itemNode.getElementsByTagName("value");
                urlNodes = itemNode.getElementsByTagName("url");
                collapsedNodes = itemNode.getElementsByTagName("collapsed");
                leafnodes = itemNodes[i].getElementsByTagName("leaf");
                if (nameNodes.length > 0 && valueNodes.length > 0) {
                    name = nameNodes[0].firstChild.nodeValue;
                    value = valueNodes[0].firstChild.nodeValue;
                    url = (urlNodes.length > 0) ? urlNodes[0].firstChild.nodeValue : AJAX_VOID_URL;
                    leaf = (leafnodes.length > 0) && (leafnodes[0].firstChild.nodeValue).toLowerCase() === "true";
                    collapsed = (collapsedNodes.length > 0) && (collapsedNodes[0].firstChild.nodeValue).toLowerCase() === "true";
                    li = new Element("li", {id: "li_" + value});
                    if (!leaf) {
                        li.appendChild(new Element("span", {id: "span_" + value, className: this.collapsedClass}));
                    }
                    link = new Element("a", {href: url, className: this.nodeClass}).update(name);
                    div = new Element("div", {id: value});
                    div.hide();
                    if (!collapsed) {
                        this.expandedNodes.push(value);
                    }
                    li.appendChild(link);
                    li.appendChild(div);
                    ul.appendChild(li);
                }
            }
            this.content = ul;
        }
    }
});// end of parser


/****************************************************************
 * http://www.prototypejs.org/learn/class-inheritance
 *  -----
 *  AjaxJspTag.Base übernimmt die logik des daten holen.
 *  d.h. hier wird die preFunction ausgewertet und postFunction!
 *  in der theorie kann ich alles an prototype übergeben, da einige
 *  functionen abgebildet sind!
 *
 *  ----
 *  momentane fehler, die buildfunctionen sind nicht mehr vorhanden
 *  alle listenerfunctionen müssen mit dem reload ferbunden werden
 *
 *  ---
 *  (check) prototype.js Leerer String an getElementById() übergeben.
 *
 ****************************************************************/
/**
 * AjaxTags.
 */
AjaxJspTag.Base = Class.create({
    resolveParameters: function () {
        var o = this.options;
        if (o.baseUrl === null || Object.isUndefined(o.baseUrl) || o.baseUrl.strip().length === 0) {
            throw new Error("url is wrong/empty");
        }
        var qs = "";
        var indexOf = o.baseUrl.indexOf('?');
        if (indexOf >= 0) {
            if (indexOf < (o.baseUrl.length - 1)) {
                qs = o.baseUrl.substr(indexOf + 1);
                qs = (qs.length > 0) ? qs.split('&').join(',') : "";
            }
            o.baseUrl = o.baseUrl.substr(0, indexOf);
        }
        o.parameters = (o.parameters) ? o.parameters + ',' + qs : qs;
    },
    getMethod: function () {
        return "post";
    },
    initRequest: function () {
        if (Object.isFunction(this.options.onCreate)) {
            var result = this.options.onCreate();
            if (Object.isString(result) && "cancel" === result.toLowerCase()) {
                // only if return is string
                return false;
            }
        }
        this.resolveParameters();
        return true;
    },
    getDefaultOptions: function (options, ajaxParam) {
        return Object.extend({
            asynchronous: true,
            method: this.getMethod(),
            evalScripts: true,
            parameters: this.buildParameterString(ajaxParam),
            onFailure: this.options.onFailure,
            onComplete: this.options.onComplete
        }, options || {});
    },
    getAjaxRequest: function (options, ajaxParam) {
        if (!this.initRequest()) {
            return null;
        }
        options = Object.extend({
            onSuccess: (function (request) {
                this.options.parser.load(request);
                this.options.handler(this);
            }).bind(this)
        }, this.getDefaultOptions(options, ajaxParam));
        return new Ajax.Request(this.options.baseUrl, options);
    },
    getAjaxUpdater: function (options, ajaxParam) {
        if (!this.initRequest()) {
            return null;
        }
        options = this.getDefaultOptions(options, ajaxParam);
        return new Ajax.Updater(this.options.target,
            this.options.baseUrl, options);
    },
    getPeriodicalUpdater: function (xoptions, ajaxParam) {
        if (!this.initRequest()) {
            return null;
        }
        var data = {};
        data.opt = xoptions;
        data.that = this;
        xoptions = Object.extend({
            frequency: this.options.refreshPeriod
        }, this.getDefaultOptions(xoptions, ajaxParam));

        // onComplete is used by API itself don't try to use it
        data._complete = xoptions.onSuccess || Prototype.emptyFunction;
        // cache the old one
        xoptions.onSuccess = (function () { // need to test!!!
            if ($(this.that.options.source)) { // try to get the element
                this._complete.bind(this.that)(); // call the function
            } else {
                this._event.stop(); // should work
            }
        }).bind(data);
        data._event = new Ajax.PeriodicalUpdater(this.options.target,
            this.options.baseUrl, xoptions);
        return data._event;
    },
    buildParameterString: function (ajaxParam) {
        var returnString = '';
        var value = Form.Element.serialize; // BUG 016027
        var field = null, key = null, val = null, v = null;
        var params = (this.replaceAJAX_DEFAULT(ajaxParam) || '');
        params.split(',').each(function (pair) {
            if (pair.strip().length === 0) {
                return;
            }
            pair = pair.split('=');
            key = pair[0].strip();
            pair = pair[1];
            val = [];
            field = null;
            if (Object.isString(pair) && pair.strip().length > 0) {
                field = pair.match(/\{[\w\.:\(\)\[\]]*\}/g);
                if (field) {
                    field = $(field[0].substring(1, field[0].length - 1));
                }
            }

            // TODO simplify val.push() and returnString +=
            if (!field) {
                val.push(pair);
            } else if (('select-multiple' === field.type) || ('checkbox' === field.type)) {
                if (v = value(field)) {
                	returnString += '&' + v;
                }
            } // BUG 016027
            else if (['radio', 'text', 'textarea', 'password', 'hidden', 'select-one'].include(field.type)) {
                val.push(field.value);
            } else {
                val.push(field.innerHTML);
            }

            val.each(function (item) {
                returnString += '&' + key + '=' + encodeURIComponent(item);
            }); // sollte immer noch laufen!
        });
        if (returnString.charAt(0) === '&') {
            returnString = returnString.substr(1);
        }
        return returnString;
    },
    replaceAJAX_DEFAULT: function (elem) {
        var o = this.options;
        return (elem) ? o.parameters.replace(AJAX_DEFAULT_PARAMETER_REGEXP, (elem.type) ? $F(elem) : elem.innerHTML) : o.parameters;
    }
});

/**
 * UpdateField tag.
 */
AjaxJspTag.UpdateField = Class.create(AjaxJspTag.Base, {
    initialize: function (options) {
        AjaxJspTag.add(this);
        this.setOptions(options);
        this.listener = this.execute.bind(this);
        this.setListeners();
    },
    setOptions: function (options) {
        this.options = Object.extend({
            // just set options to defaults this will be changed with parameter
            // options
            parameters: '',
            valueUpdateByName: false,
            eventType: "click",
            // don't use object
            parser: new DefaultResponseParser(options.valueUpdateByName ? "xml" : "text"),
            handler: this.handler
        }, options || {});
    },
    setListeners: function () {
        var o = this.options, a = $(o.action);
        if (a) {
            a["on" + o.eventType] = this.listener;
        }
    },
    execute: function () {
        this.request = this.getAjaxRequest();
    },
    handler: function (tag) {
        var targets = this.target.split(','), items = this.parser.content;
        var i, len = Math.min(targets.length, items.length);
        var helperFun = (function (item) {
            if (targets[i] === item[0]) {
                $(targets[i]).value = item[1];
            }
        });
        for (i = 0; i < len; i++) {
            if (this.valueUpdateByName) {
                items.each(helperFun);
            } else {
                $(targets[i]).value = items[i][1];
            }
        }
    }
});

/**
 * Select tag.
 */
AjaxJspTag.Select = Class.create(AjaxJspTag.Base, {
    initialize: function (options) {
        AjaxJspTag.add(this);
        this.setOptions(options);
        this.listener = this.execute.bind(this);
        this.setListeners();
        if (this.options.executeOnLoad) {
            this.execute();
        }
    },
    setOptions: function (options) {
        this.options = Object.extend({
            parameters: '',
            emptyOptionValue: '',
            emptyOptionName: '',
            defaultOptions: '',
            eventType: "change",
            parser: new DefaultResponseParser("xml"),
            handler: this.handler
        }, options || {});
        this.options.defaultOptions = this.options.defaultOptions.split(',');
    },
    setListeners: function () {
        var o = this.options, s = $(o.source);
        if (s) {
            s.ajaxSelect = this.listener;
            s["on" + o.eventType] = this.listener;
        }
    },
    execute: function () {
        this.request = this.getAjaxRequest();
    },
    handler: function () {
        var target = $(this.target);
        if (!target) {
            throw new Error("target lost");
        }
        target.options.length = 0;
        target.disabled = false;
        var newOption = null;
        this.parser.content.each(function (line) {
            newOption = new Option(line[0], line[1]);
            newOption.selected = (line.length === 3 && ("true" === line[2].toLowerCase()) || (this.defaultOptions.indexOf(line[1]) != -1));
            target.options[target.options.length] = newOption;
        }, this);
        if (newOption === null) {
            target.options[target.options.length] = new Option(this.emptyOptionName, this.emptyOptionValue);
            target.disabled = true;
        }
        // auch ein SELECT TAG ?
        // kette ausloessen
        if (Object.isFunction(target.ajaxSelect)) {
            target.ajaxSelect();
        }
    }
});

/**
 * HtmlContent tag.
 */
AjaxJspTag.HtmlContent = Class.create(AjaxJspTag.Base, {
    initialize: function (options) {
        AjaxJspTag.add(this);
        this.setOptions(options);
        this.listener = this.execute.bindAsEventListener(this);
        this.setListeners();
    },
    setOptions: function (options) {
        this.options = Object.extend({
            parameters: "",
            eventType: "click",
            parser: new DefaultResponseParser("html"),
            handler: this.handler
        }, options || {});
    },
    setEvent: function (element) {
        element["on" + this.options.eventType] = this.listener;
    },
    setListeners: function () {
        var o = this.options;
        if (o.source) {
            this.setEvent($(o.source));
        } else if (o.sourceClass) {
            $$("." + o.sourceClass).each(this.setEvent, this);
        }
    },
    execute: function (event) {
        // replace default parameter with value/content of
        // source element selected
        if (this.options.sourceClass) {
            this.request = this.getAjaxUpdater(null, Event.element(event));
        } else {
            this.request = this.getAjaxUpdater();
        }
    }
});

/**
 * Callout tag.
 */
AjaxJspTag.Callout = Class.create(AjaxJspTag.Base, {
    initialize: function (options) {
        AjaxJspTag.add(this);
        this.setOptions(options);
        this.openListener = this.calloutOpen.bindAsEventListener(this);
        this.closeListener = this.calloutClose.bindAsEventListener(this);
        this.setListeners();
    },
    setOptions: function (options) {
        this.options = Object.extend({
            parameters:	'',
            overlib: AJAX_CALLOUT_OVERLIB_DEFAULT,
            parser: new DefaultResponseParser("xmltohtml"),
            openEvent: "mouseover",
            closeEvent: "mouseout",
            handler: this.handler
        }, options || {});
    },
    setEvent: function (element) {
        element["on" + this.options.openEvent] = this.openListener;
        element["on" + this.options.closeEvent] = this.closeListener;
    },
    setListeners: function () {
        $$("." + this.options.sourceClass).each(this.setEvent, this);
    },
    calloutOpen: function (e) {
        this.execute(e);
    },
    calloutClose: function (e) {
        nd();
    },
    execute: function (event) {
        this.request = this.getAjaxRequest(null, Event.element(event));
    },
    handler: function () {
        if (this.parser.content.strip().length !== 0) { // #4
            if (this.overlib) {
                if (this.title) {
                    overlib(this.parser.content, CAPTION, this.title,
                        this.overlib);
                } else {
                    overlib(this.parser.content, this.overlib);
                }
            } else {
                if (this.title) {
                    overlib(this.parser.content, CAPTION, this.title);
                } else {
                    overlib(this.parser.content);
                }
            }
        }
    }
});

/**
 * TabPanel tag.
 */
AjaxJspTag.TabPanel = Class.create(AjaxJspTag.Base, {
    createTab: function (tab) {
        var e = new Element('a').update(tab.caption);
        e.base = this;
        e.baseUrl = tab.baseUrl;
        e.href = AJAX_VOID_URL;
        e.onclick = function () {
            this.base.options.baseUrl = this.baseUrl;
            this.base.options.parameters = this.parameters;
            this.base.source = this;
            this.base.execute();
        };
        e.parameters = tab.parameters;
        return e;
    },
    initialize: function (options) {
        this.panel = $(options.id);
        this.panel.addClassName("tabPanel");
        this.content = new Element("div", {className: "tabContent"});
        var ul = new Element('ul');
        ul.innerHTML = '';
        // this.tabs = tabs; // alle tabs als array müssen angelegt werden!
        var f = null, li = null, a = null;
        options.pages.each(function (tab) {
            li = new Element('li');
            li.innerHTML = '';
            a = this.createTab(tab);
            li.appendChild(a);
            if (tab.defaultTab) {
                f = f || a.onclick.bind(a);
            }
            ul.appendChild(li);
        }, this);
        this.panel.innerHTML = ''; // clear first!
        var nav = new Element("div", {className: "tabNavigation"});
        nav.appendChild(ul);
        this.panel.appendChild(nav);
        this.panel.appendChild(this.content);
        this.setOptions(options);
        this.options.target = this.content;
        if (Object.isFunction(f)) {
            f();
        }
    },
    setOptions: function (options) {
        this.options = Object.extend({
            eventType: "click",
            parser: new DefaultResponseParser("html")
        }, options || {});
    },
    execute: function () {
        this.request = this.getAjaxUpdater({
            onSuccess: this.handler.bind(this)
        });
    },
    handler: function () {
        // find current anchor
        // remove class
        this.panel.select(".ajaxCurrentTab").invoke("removeClassName", "ajaxCurrentTab");
        // add class to selected tab
        this.source.addClassName("ajaxCurrentTab");
    }
});

/**
 * Autocomplete tag.
 */
AjaxJspTag.XmlToHtmlAutocompleter = Class.create(Autocompleter.Base, {
    // AjaxJspTag.Autocomplete
    initialize: function (autocomplete) {
        this.autocompleteTag = autocomplete;
        this.baseInitialize(autocomplete.options.source, autocomplete.options.divElement, {
            minChars: autocomplete.options.minChars,
            asynchronous: true,
            tokens: autocomplete.options.appendSeparator,
            indicator: autocomplete.options.indicator,
            evalScripts: true,
            onComplete: this.onComplete.bind(this),
            afterUpdateElement: function (inputField, selectedItem) {
                autocomplete.handler(selectedItem);
            }
        });
        this.url = autocomplete.options.baseUrl;
        // just 4 don't write a copy
        // use own function?
        this.getUpdatedChoices0 = Ajax.Autocompleter.prototype.getUpdatedChoices;
    },
    getUpdatedChoices: function () {
        if (!this.autocompleteTag.initRequest()) {
            this.stopIndicator(); // stop ac tag
            return;
        }
        // parse parameters and do replacements
        this.options.defaultParams = this.autocompleteTag.buildParameterString();
        // / don't write code a second time
        this.getUpdatedChoices0();
    },
    onComplete: function (request) {
        this.autocompleteTag.options.parser.load(request);
        this.updateChoices(this.autocompleteTag.options.parser.content);
        if (this.autocompleteTag.options.parser.content === null) {
            this.stopIndicator(); // stop ac tag
        }
        // chan 4 postfunction
        if (Object.isFunction(this.autocompleteTag.options.onComplete)) {
            // hier wird nicht base verwendet!!!!
            // Disable onupdate event handler of input field
            // because, postFunction can change the content of
            // input field and get into eternal loop.
            var inputf = $(this.autocompleteTag.options.source);
            var onupdateHandler = inputf.onupdate;
            inputf.onupdate = '';
            this.autocompleteTag.options.onComplete();
            // Enable onupdate event handler of input field
            inputf.onupdate = onupdateHandler;
        }
    }
});

AjaxJspTag.Autocomplete = Class.create(AjaxJspTag.Base, {
    initialize: function (options) {
        this.setOptions(options);
        // insert div at the top of the document so it will not be hidden in case of overflow
        Element.insert(document.body, {top:new Element("div", {id: this.options.divElement, className: this.options.className})});
        this.execute();
    },
    setOptions: function (options) {
        this.options = Object.extend({
            divElement: "ajaxAuto_" + options.source,
            parser: new DefaultResponseParser("xmltohtmllist", true)
        }, options || {});
    },
    execute: function (e) {
        var aj = new AjaxJspTag.XmlToHtmlAutocompleter(this); // TODO aj is unused
    },
    handler: function (selectedItem) {
        var target = $(this.options.target);
        if (target) {
            if (this.options.appendSeparator) {
                if (target.value.length > 0) {
                    target.value += this.options.appendSeparator;
                }
                target.value += selectedItem.id;
            } else {
                target.value = selectedItem.id;
            }
        }
        this.options.selectedIndex = selectedItem.autocompleteIndex;
        this.options.selectedObject = selectedItem;
    }
});

/**
 * Portlet tag.
 */
AjaxJspTag.Portlet = Class.create(AjaxJspTag.Base, {
    initialize: function (options) {
        this.setOptions(options);
        // erstellen des menu um doppelten code zu vermeiden
        var o = this.options;
        var sourceBase = $(o.source);
        sourceBase.addClassName(o.classNamePrefix + "Box");
        if (o.withBar) {
            var bar = new Element("div", {className: o.classNamePrefix + "Tools"});
            if (o.close) {
                o.close = new Element("img", {className: o.classNamePrefix + "Close"});
                o.close.src = o.imageClose;
                bar.appendChild(o.close);
            }
            if (o.refresh) {
                o.refresh = new Element("img", {className: o.classNamePrefix + "Refresh"});
                o.refresh.src = o.imageRefresh;
                bar.appendChild(o.refresh);
            }
            if (o.toggle) {
                o.toggle = new Element("img", {className: o.classNamePrefix + "Size"});
                o.toggle.src = o.imageMinimize;
                bar.appendChild(o.toggle);
            }
            sourceBase.appendChild(bar);
        }

        var element = new Element("div", {className: o.classNamePrefix + "Title"});
        element.innerHTML = o.title;
        sourceBase.appendChild(element);

        o.target = new Element("div", {className: o.classNamePrefix + "Content"});
        sourceBase.appendChild(o.target);

        this.closeListener = this.closePortlet.bindAsEventListener(this);
        this.refreshListener = this.refreshPortlet.bindAsEventListener(this);
        this.toggleListener = this.togglePortlet.bindAsEventListener(this);
        this.setListeners();

        if (o.startMinimize) {
            this.togglePortlet();
        }
        if (o.executeOnLoad) {
            this.execute();
        }
        AjaxJspTag.add(this);
    },
    setOptions: function (options) {
        this.options = Object.extend({
            close: (options.imageClose && options.source),
            refresh: (options.imageRefresh && options.source),
            toggle: (options.imageMinimize && options.imageMaximize && options.source),
            eventType: "click",
            parser: new DefaultResponseParser("html")
        }, options || {});
        // bar yes if any image is set!
        this.options.withBar = (this.options.close || this.options.refresh || this.options.toggle);
    },
    setListeners: function () {
        var o = this.options, evt = "on" + o.eventType;
        if (o.close) {
            o.close[evt] = this.closeListener;
        }
        if (o.refresh) {
            o.refresh[evt] = this.refreshListener;
        }
        if (o.toggle) {
            o.toggle[evt] = this.toggleListener;
        }
    },
    execute: function (e) {
        this.ajaxPeriodicalUpdater = this.options.refreshPeriod ? this.getPeriodicalUpdater() : this.getAjaxUpdater();
    },
    stopAutoRefresh: function () {
        // stop auto-update if present
        if (this.ajaxPeriodicalUpdater && this.options.refreshPeriod) {
            this.ajaxPeriodicalUpdater.stop();
        }
        this.ajaxPeriodicalUpdater = null;
    },
    refreshPortlet: function (e) {
        // clear existing updater
        this.stopAutoRefresh();
        this.execute();
    },
    closePortlet: function (e) {
        this.stopAutoRefresh();
        Element.remove(this.options.source);
        // TODO save state in cookie
    },
    togglePortlet: function (e) {
        if (this.options.toggle) {
            if (this.options.toggle.src.endsWith(this.options.imageMinimize)) {
                Element.hide(this.options.target);
                this.options.toggle.src = this.options.imageMaximize;
            } else {
                Element.show(this.options.target);
                this.options.toggle.src = this.options.imageMinimize;
                this.refreshPortlet();
            }
        }
        // TODO save state in cookie
    }
});

/**
 * Tree tag.
 */
AjaxJspTag.Tree = Class.create(AjaxJspTag.Base, {
    initialize: function (options) {
        this.setOptions(options);
        this.execute();
    },
    setOptions: function (options) {
        this.options = Object.extend({
            eventType: "click",
            parser: new ResponseXmlToHtmlLinkListParser(),
            collapsedClass: "collapsedNode",
            expandedClass: "expandedNode",
            treeClass: "tree",
            nodeClass: ''
        }, options || {});
    },
    execute: function (e) {
        var o = this.options, t = $(o.target);
        if (o.target) {
            var imgElem = $("span_" + o.target);
            if (imgElem) {
                var expanded = this.toggle(imgElem);
                if (!expanded) {
                    t.hide().update("");
                    return;
                }
            }
        }
        var obj = this; // required because 'this' conflict with Ajax.Request
        this.request = this.getAjaxRequest({
            onSuccess: function (request) {
                obj.options.parser.load({
                    responseXML: request.responseXML,
                    collapsedClass: obj.options.collapsedClass,
                    treeClass: obj.options.treeClass,
                    nodeClass: obj.options.nodeClass
                });
                obj.handler();
            }
        }, {
            innerHTML: this.options.target
        }); // warp
    },
    toggle: function (e) {
        var expanded = e.hasClassName(this.options.expandedClass);
        [this.options.expandedClass, this.options.collapsedClass].each(e.removeClassName, e);
        e.addClassName(expanded ? this.options.collapsedClass : this.options.expandedClass);
        return !expanded;
    },
    handler: function () {
        var o = this.options, parser = o.parser, target = $(o.target);
        var displayValue = 'block';
        if (!parser.content) {
            target.innerHTML = "";
            displayValue = 'none';
        }
        target.appendChild(parser.content);
        target.setStyle({
            display: displayValue
        });
        if (displayValue === 'block') {
            target.select("span").each(function (image) {
                image["on" + o.eventType] = this.toggleTreeNode.bind(this, image.id.substring(5));
                //image.observe(o.eventType, this.toggleTreeNode.bind(this, image.id.substring(5)));
            }, this);

            parser.expandedNodes.each(this.toggleTreeNode, this);
            AjaxJspTag.reload();
        }
    },
    toggleTreeNode: function (xid) {
        var opt = Object.clone(this.options);
        opt.target = xid;
        return new AjaxJspTag.Tree(opt);
    }
});

/**
 * Toggle tag.
 */
// we can create the 'a' tags here
AjaxJspTag.Toggle = Class.create(AjaxJspTag.Base, {
    initialize: function (options) {
        this.setOptions(options);
        // create message DIV
        this.container = $(this.options.source);
        if (this.options.messageClass) {
            this.messageContainer = new Element("div", {id: this.options.source + "_message", className: this.options.messageClass});
            this.container.insert({'top': this.messageContainer});
        }
        this.classList = [this.options.selectedOverClass, this.options.selectedLessClass, this.options.overClass, this.options.selectedClass];
        this.mouseoverListener = this.raterMouseOver.bindAsEventListener(this);
        this.mouseoutListener = this.raterMouseOut.bindAsEventListener(this);
        this.clickListener = this.raterClick.bindAsEventListener(this);
        this.setListeners();
        AjaxJspTag.add(this);
    },
    setOptions: function (options) {
        this.options = Object.extend({
            parameters: ('rating={' + AJAX_DEFAULT_PARAMETER + '}'),
            parser: new DefaultResponseParser("text"),
            handler: this.handler
        }, options || {});
    },
    setEvent: function (element) {
        element.onmouseover = this.mouseoverListener;
        element.onmouseout = this.mouseoutListener;
        element.onclick = this.clickListener;
    },
    setListeners: function () {
        // attach events to anchors
        this.container.select('a').each(this.setEvent, this);
    },
    raterMouseOver: function (e) {
        // get list of all anchors
        var elements = this.container.select('a');
        // find the current rating
        var selectedObject = this.container.select('.' + this.options.selectedClass).pop();
        var selectedIndex = elements.indexOf(selectedObject);
        // find the index of the 'hovered' element
        var currentIndex = elements.indexOf(Event.element(e));
        // set message
        if (this.options.messageClass && this.messageContainer) {
            this.messageContainer.innerHTML = elements[currentIndex].title;
        }
        // iterate over each anchor and apply styles
        var element = null;
        for (var i = 0, len = elements.length; i < len; i++) {
            element = elements[i];
            if (selectedIndex >= 0 && ! (i > selectedIndex && i <= currentIndex)) {
                if (i <= selectedIndex) {
                    element.addClassName((i <= currentIndex) ? this.options.selectedOverClass : this.options.selectedLessClass);
                }
            } else if (i <= currentIndex) {
                element.addClassName(this.options.overClass);
            }
        }
    },
    raterMouseOut: function (e) {
        // clear message
        if (this.options.messageClass && this.messageContainer) {
            this.messageContainer.innerHTML = '';
        }
        this.clearCSSClass(this.options.selectedClass);
    },
    clearCSSClass: function (ohne) {
        var list = this.container.select('a'), li = this.classList;
        if (li.indexOf(ohne) !== -1) {
            li = li.without(ohne);
        }
        list.each(function (element) {
            li.each(element.removeClassName, element);
        }, this);
        return list;
    },
    raterClick: function (e) {
        // get list of all anchors
        var klickElement = Event.element(e);
        var selectedObject = this.container.select('.' + this.options.selectedClass).pop();
        var elements = this.clearCSSClass();
        var selectedIndex = elements.indexOf(selectedObject);

        // find the index of the 'hovered' element
        var currentIndex = elements.indexOf(klickElement);
        // update styles
        var element = null;
        for (var i = 0; i <= currentIndex; i++) {
            element = elements[i];
            if (!this.container.hasClassName('onoff') || (i === currentIndex && selectedIndex  === -1)) {
                element.addClassName(this.options.selectedClass);
            }
        }
        // send AJAX
        var ratingToSend = elements[currentIndex].title;
        if (this.container.hasClassName('onoff')) {
            // send opposite of what was selected
            var ratings = this.options.ratings.split(',');
            ratingToSend = (ratings[0] == ratingToSend) ? ratings[1] : ratings[0];
            elements[currentIndex].title = ratingToSend;
        }
        this.execute({
            innerHTML: ratingToSend
        }); // warp this to make replacement valid!
        // set field (if defined)
        if (this.options.state) {
            $(this.options.state).value = ratingToSend;
        }
    },
    execute: function (ratingValue) {
        this.request = this.getAjaxRequest(null, ratingValue);
    },
    handler: function () {
        // daten in items
        var erg = this.parser.content[0][0]; // on/off / 1,2,3
        if (Object.isFunction(this.updateFunction)) {
            this.updateFunction(erg); // ??? XXX do we need this!
        // use onComplete?
        }
    }
});

/**
 * OnClick tag.
 */
AjaxJspTag.OnClick = Class.create(AjaxJspTag.Base, {
    initialize: function (options) {
        this.setOptions(options);
        this.execute();
    },
    setOptions: function (options) {
        this.options = options || {};
    },
    execute: function () {
        this.request = this.getAjaxUpdater({
            requestHeaders: this.options.requestHeaders,
            onSuccess: this.handler.bind(this)
        }, this.options.eventBase);
    },
    handler: function () {
        // etwas machen wenn erfolgreich?
    }
});

/**
 * Submit tag.
 */
AjaxJspTag.Submit = Class.create(AjaxJspTag.Base, {
    initialize: function(options){
        this.setOptions(options);
        this.setListeners();
    },
    setOptions: function(options){
        this.options = options || {};
    },
    setListeners: function(){
        var o = this.options, f = $(o.source);
        if (f) {
            f.onsubmit = this.execute.bind(this);
        }
    },
    execute: function(){
        var o = this.options, f = $(o.source);
        if (f) {
            o.baseUrl = f.action;
            this.request = this.getAjaxUpdater({
                parameters: f.serialize(true)
            });
        }
        return false;
    }
});

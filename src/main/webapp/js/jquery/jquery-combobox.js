/*
 * jQuery Combobox
 * http://jqueryui.com/demos/autocomplete/#combobox
 * Dual licensed under the MIT (MIT-LICENSE.txt)
 * and GPL (GPL-LICENSE.txt) licenses.
 * 
 * Modify by waiting@issence.com
 * http://waiting.javaeye.com/blog/714655
 *
 * Depends:
 *	jQuery.UI v1.84
 *	jquery.ui.widget.js
 *	UI Core, UI Widget, UI Position
 * Update:
 *	2010.07.16	扩展设置, 解决兼容, 双击输入框选中内容 
 *	2010.07.23	修正输入+或者c++时抛出 'invalid quantifier +' 错误
 *	2010.08.04	FF下给input添加input事件，补丁解决中文输入时触发匹配问题
 *				解决通过下拉菜单选择项目之后继续输入字符到不匹配状态后失焦时无法清空
 *	2010.08.12	jQuery.UI升级到1.84，在此基础上修改。
 *		add:	直接输入匹配字符失焦后保留
 *		add:	添加title参数，可自定义下拉按钮问题说明，默认是‘展开’
 *	2010.08.23	添加小图标样式，见下注释
 *
 * Bugs:
 *
 * $(selector).combobox()
 * $(selector).combobox({size: 30})
 * $(selector).combobox().combobox("option", {size: 30, delay: 300, title:'Show All Items'})	// greater delay for ajax
 *
 */

/* FIX 下拉超长则纵向滚动条*/

/* jQueryUI默认大按钮样式
<style>
button.comboboxButton { margin-left: -1px; }
.ui-autocomplete-input {margin: 0; padding: 0.48em 0em 0.47em 0.4em; *padding:0.68em 0 0.68em 0.4em;}
ul.ui-autocomplete {max-height: 280px; overflow-x: hidden; overflow-y: auto; padding:2px;}
</style>
*/

/* jQueryUI小按钮样式
<style>
button.comboboxButton {width:2em;  margin-left: -1px;}
button.comboboxButton .ui-button-text {display:block; line-height:1;}
.ui-autocomplete-input {margin:0; padding:0.3em 0 0.31em 0.3em; *padding:0.38em 0 0.46em 0.3em;}
</style>
*/

// combobox
if (typeof $.widget === 'function') {
	(function($) {
		$.widget("ui.combobox", {
			options: {
				delay: 60,
				size: 0,
				required:'',
				title: '展开'
			},
			_create: function() {
				var self = this,
					select = self.element.hide(),
					size = self.options.size || '',
					delay = self.options.delay,
					required = self.options.required;
				
				var selectId = select.attr("id");
				// add waiting
				var select_name = select[0] && select[0].selectedIndex >=0 && select[0].options[select[0].selectedIndex].text || '&nbsp;';
				var tmpStyle = "";
				if($.browser.msie){
					try{
						if(op == undefined){
							tmpStyle = 'style="font-size:12px;font-family:宋体;height:13px"';
						}else{
							tmpStyle = 'style="font-size:12px;font-family:宋体;height:14px"';
						}
					}catch(e){
						tmpStyle = 'style="font-size:12px;font-family:宋体;height:13px"';
					}
					
					
				}
				var str = select_name ? '<input '+tmpStyle+' size="' + size + '" value="' + select_name + '" id="combobox_' + selectId +  '" />' : '<input>';
				// 设定默认值为<select>选定项目
				var input = $(str)
					.insertAfter(select)
					.autocomplete({
						source: function(request, response) {
							
							//是选中标志位
							$(".combobox").val("false");
							
							// var matcher = new RegExp(request.term, "i");	// 当输入如c++的类正则字符时会抛出 invalid quantifier +
							// edit waiting: 使用not过滤掉被禁止项目，实现通过仅用项目来动态屏蔽搜索结果
							response(select.children("option").not(':disabled').map(function() {
								var text = $(this).text();
								// if (this.value && (!request.term || matcher.test(text)))
								if (this.value && (!request.term || text.indexOf(request.term) >= 0)) {
									return {
										label: text.replace(
											new RegExp(
												"(?![^&;]+;)(?!<[^<>]*)(" +
												$.ui.autocomplete.escapeRegex(request.term) +
												")(?![^<>]*>)(?![^&;]+;)", "gi"
											), "<strong>$1</strong>" ),
										value: text,
										option: this
									};
								}
							}));
						},
						// FIX change事件的滞后性,用于表单当通过按钮选择项目后直接提交会导致select选项未更新 !
						select: function(event, ui) {
							select.val( ui.item.option.value );	// 更新select值
							ui.item.option.selected = true;
							if('function' == typeof(callback = ui.item.option.parentElement.onchange)) 
                        		callback.apply(this, []);
							self._trigger("selected", event, {
								item: ui.item.option
							});
							
							//代表是选中
							$(".combobox").val("true");
							
							// select.change();
						},
						change: function(event, ui) {	// 用select事件来联动select,change只用来做输入不匹配时处理
							
						//if (!ui.item) {	// 当选择提示条目后继续输入到不匹配状态然后失焦,IE为空,FF为真,故跳过此判断
							var input = $(this),
								inputVal = input.val(),
								valid = false;
							select.children( "option" ).each(function() {
								if ( this.text === inputVal ) {	// 如果手动输入有匹配项目则不清空
									this.selected = valid = true;
									return false;
								}
							});
							if (!valid) {
								// remove invalid value, as it didn't match anything
								input.val('');
								select.attr('selectedIndex', -1).val('');
								return false;
							}
							//}
						},
						delay: delay,
						minLength: 0
					})
					.dblclick(function() {
						$(this).select();
					})
					.addClass("ui-widget ui-widget-content ui-corner-left textInput" + required);
				
				// FF下绑定input事件来兼容输入法中文输入
				if (jQuery.browser.mozilla) {
					input[0].addEventListener(
							'input', 
							function() {
								var val = this.value;
								if (val) {
									$(this).autocomplete("search", val);
								}
							}, false
					);
				}
				
				input.data( "autocomplete" )._renderItem = function( ul, item ) {
					return $( "<li></li>" )
						.data( "item.autocomplete", item )
						.append( "<a>" + item.label + "</a>" )
						.appendTo( ul );
				};
				
				$("<button type='button'>&nbsp;</button>")
				.attr("tabIndex", -1)
				.attr("title", self.options.title)
				.insertAfter(input)
				.button({
					icons: {
						primary: "ui-icon-triangle-1-s"
					},
					text: false
				}).removeClass("ui-corner-all")
				.addClass("ui-corner-right ui-button-icon comboboxButton")
				.click(function() {
					// close if already visible
					if (input.autocomplete("widget").is(":visible")) {
						input.autocomplete("close");
						return;
					}
					// pass empty string as value to search for, displaying all results
					input.autocomplete("search", "").focus();
					//alert(input.autocomplete( "option", "source" ) )
				});
			},
				
			_setOption: function(key, value) {
				var self = this;
				var select = self.element,
					input = select.next();
				if (key == 'size') {
					value = parseInt(value, 10);
					input.attr('size', value);	
				}else {
					this.options[key] = value;
				}
			}
		});
	})(jQuery);
}
// ui-autocomplete-input ui-widget ui-widget-content ui-corner-left ui-autocomplete-loading
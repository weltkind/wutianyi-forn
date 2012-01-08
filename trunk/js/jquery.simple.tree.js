/*
 * jQuery SimpleTree Drag&Drop plugin
 * Update on 22th May 2008
 * Version 0.3
 *
 * Licensed under BSD <http://en.wikipedia.org/wiki/BSD_License>
 * Copyright (c) 2008, Peter Panov <panov@elcat.kg>, IKEEN Group http://www.ikeen.com
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the Peter Panov, IKEEN Group nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY Peter Panov, IKEEN Group ``AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL Peter Panov, IKEEN Group BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

jQuery.fn.simpleTree = function(opt) {
	return this
			.each(function() {
				var TREE = this;
				_global_tree = TREE;
				var ROOT = jQuery('.root', this);
				var ajaxCache = Array();

				TREE.option = {
					animate : false,
					speed : 'fast'
				};
				TREE.option = jQuery.extend(TREE.option, opt);
				jQuery.extend(this, {
					getSelected : function() {
						return jQuery('span.active', this).parent();
					}
				});

				TREE.nodeToggle = function(obj) {
					var childUl = jQuery('>ul', obj);
					if (childUl.is(':visible')) {
						obj.className = obj.className.replace('open', 'close');

						if (TREE.option.animate) {
							childUl.animate( {
								height : "toggle"
							}, TREE.option.speed);
						} else {
							childUl.hide();
						}
					} else {
						obj.className = obj.className.replace('close', 'open');
						if (TREE.option.animate) {
							childUl.animate( {
								height : "toggle"
							}, TREE.option.speed, function() {
								if (childUl.is('.ajax'))
									TREE.setAjaxNodes(childUl, obj.id);
							});
						} else {
							childUl.show();
							if (childUl.is('.ajax'))
								TREE.setAjaxNodes(childUl, obj.id);
						}
					}
				};

				TREE.setAjaxNodes = function(node, parentId, callback) {
					if (jQuery.inArray(parentId, ajaxCache) == -1) {
						ajaxCache[ajaxCache.length] = parentId;
						var url = jQuery.trim(jQuery('>li', node).text());
						if (url && url.indexOf('url:')) {
							url = jQuery.trim(url.replace(/.*\{url:(.*)\}/i,
									'$1'));
							jQuery.ajax( {
								type : "GET",
								url : url,
								contentType : 'html',
								cache : false,
								success : function(responce) {
									node.removeAttr('class');
									node.html(responce);
									jQuery.extend(node, {
										url : url
									});
									TREE.setTreeNodes(node, true);
									if (typeof callback == 'function') {
										callback(node);
									}
								}
							});
						}

					}
				};
				TREE.setTreeNodes = function(obj, useParent, curLevel) {
					obj = useParent ? obj.parent() : obj;
					var temp = curLevel ? '>ul>li>span' : 'li>span';
					if (curLevel) {
						jQuery(temp, obj).removeClass('text').unbind('click');
					}
					jQuery(temp, obj)
							.addClass('text')
							.click(
									function() {
										jQuery('.active', TREE).attr('class',
												'text');
										if (this.className == 'text') {
											this.className = 'active';
										}
										var attr = jQuery(this);
										if (attr.parent().attr("title") == "value") {
											try {
												window.parent.frameLoading();
											} catch (e) {
											}
											window.parent.frames[1].location.href = "addOrUpdatePostCatAttr.htm?catId="
													+ jQuery(".root")
															.attr('id')
													+ "&path="
													+ attr.parent().attr('id');
										}
										if (attr.parent().attr("title") == "attr") {
											try {
												window.parent.frameLoading();
											} catch (e) {
											}
											window.parent.frames[1].location.href = "setAttrBaseInformation.htm?catId="
													+ jQuery(".root")
															.attr('id')
													+ "&path="
													+ attr.parent().attr('id');
										}
										return false;
									});
					var tempLi = curLevel ? '>ul>li' : 'li';
					if (curLevel && obj.attr('class') != "root") {
						if (jQuery(tempLi, obj).size() > 0) {
							var setClassName = "folder-open";

							if (obj.attr("title") == "value") {
								setClassName = "value-open"
							}

							if (obj.attr('class').indexOf('last') != -1) {
								setClassName = setClassName + "-last";
							}

							obj.attr('class', setClassName);
						} else {
							var setClassName = "attr";

							if (obj.attr("title") == "value") {
								setClassName = "doc"
							}
							if (obj.attr('class').indexOf('last') != -1) {
								setClassName = setClassName + "-last";
							}
							obj.attr('class', setClassName);
						}
						obj.attr('class', obj.attr('class').replace('close',
								'open'));
					}
					jQuery(tempLi, obj)
							.each(
									function(i) {
										var className = this.className;
										var open = false;
										var cloneNode = false;
										var LI = this;
										var childNode = jQuery('>ul', this);
										if (childNode.size() > 0) {
											if (jQuery('>li', childNode).size() > 0) {
												var setClassName = 'folder-';

												if ($(this).attr("title") == "value") {
													setClassName = "value-"
												}

												if (className
														&& className
																.indexOf('open') >= 0) {
													setClassName = setClassName + 'open';
													open = true;
												} else {
													setClassName = setClassName + 'close';
												}
												this.className = setClassName
														+ (jQuery(this).is(
																':last-child') ? '-last'
																: '');

												if (!open
														|| className
																.indexOf('ajax') >= 0)
													childNode.hide();

												TREE.setTrigger(this);
											} else {
												var setClassName = 'attr';

												if ($(this).attr("title") == "value") {
													setClassName = "doc"
												}
												this.className = setClassName
														+ (jQuery(this).is(
																':last-child') ? '-last'
																: '');
												TREE.setTrigger(this);
											}

										}
									}).before('<li class="line">&nbsp;</li>')
							.filter(':last-child').after(
									'<li class="line-last"></li>');
				};

				TREE.setTrigger = function(node) {
					if ($('>img', node).size() > 0) {
						return;
					}

					jQuery('>span', node)
							.before(
									'<img class="trigger" src="../../images/spacer.gif" border=0>');
					var trigger = jQuery('>.trigger', node);
					trigger.click(function(event) {
						TREE.nodeToggle(node);
					});
					if (!jQuery.browser.msie) {
						trigger.css('float', 'left');
					}
				};

				TREE.mark = function(id, isprincipal, mustinput) {
					id = "#" + id;
					$("font:contains('!')", $(id + " >span:first")).remove();
					$("font:contains('*')", $(id + " >span:first")).remove();
					
					var span = $('<span></span>');
					if (mustinput == 'y') {
						span.append('<font color="red">*</font>');
					}
					if (isprincipal == 'Y') {
						span.append('<font style="color:green;font-weight:bold;">!</font>');
					}
					$(id + " >span:first").prepend(span.html());
				}

				TREE.treeOrder = function(id, data, type) {
					if (id == '0') {
						id = $('.root').attr('id');
					}
					id = '#' + id;

					var cache = {
						li : [],
						normalized : []
					};

					if ($(id + " >ul:first").attr('class') == 'ajax') {
						return;
					}
					var ul = $("<ul></ul>");
					$(">li[class*='line']", $(id + " >ul:first")).remove();

					$(">li[title]", $(id + " >ul:first")).each(
							function(i) {
								cache.li.push($(this));
								cache.normalized[$(this).attr("id")
										+ $("span:first", this).text()] = i;
							});

					var count = 0;
					$.each(data, function(i, n) {
						var span = $(n);
						++count;
						if (cache.normalized[i + span.text()] != 0
								&& !cache.normalized[i + span.text()]) {
							var li = $('<li></li>');
							li.append(span);
							li.append('<ul></ul>');
							li.attr('id', i).attr('title', type);
							ul.append(li);
						} else {
							ul
									.append(cache.li[cache.normalized[i
											+ span.text()]]);
						}
					});

					$(id + " >ul").remove();
					if (count == 0) {
						window.parent.frames[0]._global_tree.setTreeNodes(
								$(id), false, true);
						return;
					}
					$(id).append(ul);
					window.parent.frames[0]._global_tree.setTreeNodes($(id),
							false, true);
				};

				TREE.init = function(obj) {
					TREE.setTreeNodes(obj, false);
				};

				// ���ڵ���ӵ��������4�����ұ�ҳ���ˢ��,����ǶԸ�ڵ���5Ĵ���
				jQuery("span:first", ROOT)
						.bind(
								"click",
								function() {
									$(".active").attr("class", "text");
									if (this.className == 'text') {
										this.className = 'active';
									}
									$(this).addClass("active");
									window.parent.frames[1].location = "addOrUpdatePostCatAttr.htm?catId="
											+ jQuery(".root").attr("id");
									return false;
								});
				TREE.init(ROOT);
			});
}

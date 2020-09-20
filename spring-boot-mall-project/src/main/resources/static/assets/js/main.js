!(function ($, win) {
    "use strict";

    var AuthenticationKey    = 'apply_auth_admin_key';
    var SecurityGrantKey     = 'apply_grant_admin_key';
    var AuthenticationInfoKey    = 'apply_auth_admin_info_key';
    var SecurityGrantInfoKey     = 'apply_grant_admin_info_key';


    // ================================================================================
    // ================================================================================
    // ==================================「数据请求」==================================
    // ================================================================================
    // ================================================================================

    win.AjaxRequest = function (options) {
        var app = WebApp({});
        $.ajax($.extend({
            type:'GET',
            dataType: 'JSON',
            contentType: 'application/json',
            success: function (data) {
                options['callback'](data);
            },
            error: function (res) {
                console.log(res);
                try {
                    app.notifyWarn(JSON.parse(res.responseText).errMsg || "服务器响应失败！");
                    if (res.status === 401) {
                        setTimeout(function () {
                            location.href = '/author/login';
                        }, 500);
                    }
                } catch (e) {
                    app.notifyWarn('网络请求失败！');
                }
            }
        }, options || {}));
    }

    win.AjaxAuth = function (res) {
        localStorage.setItem(AuthenticationKey, res.token);
        localStorage.setItem(AuthenticationInfoKey, JSON.stringify(res.info));
        setTimeout(function () {
            location.href = '/author/grant';
        }, 500);
    }

    win.AjaxGrant = function (res) {
        var token = res.link;
        localStorage.setItem(SecurityGrantKey, token);
        localStorage.setItem(SecurityGrantInfoKey, JSON.stringify(res));
        setTimeout(function () {
            location.href = '/operator?link='+token;
        }, 500);
    }

    win.AjaxExportOrder = function(res) {
        location.href = 'exports/order?start='+res.start+'&end='+res.end + '&grants=' + localStorage.getItem(SecurityGrantKey)
        // $.ajax({
        //     url : '/export/order',
        //     type : 'GET',
        //     dataType : 'json',
        //     data: res,
        //     headers: {
        //         Authorization: localStorage.getItem(AuthenticationKey),
        //         SecurityGrant:localStorage.getItem(SecurityGrantKey)
        //     },
        //     success : function(data) {
        //         if (data != null) {
        //             debugger
        //             var grants = data.grants[0].link
        //             var url = 'exports/order?start='+res.start+'&end='+res.end + '&grants=' + grants
        //             location.href = url
        //         }
        //     },
        //     error : function(msg) {
        //         window.console.log(msg);
        //     }
        // });
    }

    win.AjaxExportDayReport = function(res) {
        location.href = 'exports/report/day?start='+res.start+'&end='+res.end + '&grants=' + localStorage.getItem(SecurityGrantKey)
    }

    win.AjaxExportReport = function(res,callback) {
        location.href = 'exports/report?start='+res.start+'&end='+res.end + '&grants=' + localStorage.getItem(SecurityGrantKey)
        // $.ajax({
        //     url : '/export/report',
        //     type : 'GET',
        //     dataType : 'json',
        //     data: res,
        //     headers: {
        //         Authorization: localStorage.getItem(AuthenticationKey),
        //         SecurityGrant:localStorage.getItem(SecurityGrantKey)
        //     },
        //     success : function(data) {
        //         if (data != null) {
        //             var grants = data.grants[0].link
        //             var url = 'exports/report?start='+res.start+'&end='+res.end + '&grants=' + grants
        //             location.href = url
        //         }
        //     },
        //     error : function(msg) {
        //     }
        // });
    }


    win.AjaxGet = function (url, callback) {
        AjaxRequest({url: url, callback: callback});
    }

    win.AjaxPost = function (url, params, callback) {
        AjaxRequest({url: url, data: JSON.stringify(params), type: 'POST', callback: callback});
    }

    win.AjaxPut = function (url, params, callback) {
        AjaxRequest({url: url, data: JSON.stringify(params), type: 'PUT', callback: callback});
    }

    win.AjaxDelete = function (url, params, callback) {
        AjaxRequest({url: url, data: params, type: 'DELETE', dataType: 'TEXT', callback: callback});
    }

    win.WebUploader = function (selector, options) {
        var item = $(selector);
        item.data.maxFilesize = 5;
        var app = $.extend({}, item.data(), options);
        app.item = item;
        app['placeholder'] = app['placeholder'] || '选择上传';
        app['previewStyle'] = app['preview'] || '!thumb_128';

        var target = $(app['target']);
        item.css({display: 'flex', flexDirection: 'row', flexWrap: 'wrap'});

        var itemStyleColor = app['color'] || '#888';
        var itemStyleBorder = {borderWidth: '1px', borderStyle: 'dashed', borderColor: itemStyleColor};
        var itemStyleLayout = {position: 'relative', overflow: 'hidden', borderRadius: '5px'};

        var itemWidth = item.data('width') || item.css('width');
        var itemHeight = item.data('height') || item.css('height');
        var itemStyleNormal = {position: 'absolute', display: 'block', width: itemWidth, height: itemHeight};
        var itemPreviewStyle = {width: 'auto', height: 'auto', maxWidth: itemWidth, maxHeight: itemHeight, minWidth: itemWidth, minHeight: itemHeight};
        var itemLabelStyle = {fontSize: '14px', fontWeight: 400, color: itemStyleColor, textAlign: 'center', lineHeight: itemHeight};

        var itemLabelDom = $('<span></span>').css($.extend({}, itemStyleNormal, itemLabelStyle)).text(app['placeholder']);
        var itemPreviewDom = $('<img>').css($.extend({}, itemStyleNormal, itemPreviewStyle));
        var itemFileDom = $('<input type="file">').css($.extend({}, itemStyleNormal, {left: 0, right: 0, top: 0, bottom: 0, opacity: 0}));
        var itemSelectorDom = $('<div></div>').css($.extend({}, itemStyleBorder, itemStyleLayout, {width: itemWidth, height: itemHeight}));

        app.remove = function () {
            var url = $(this).data('index');
            item.find('[data-url="'+url+'"]').remove();
            if (target.length <= 0) return;
            var results = (target.val() || '').split(',').filter(function (u) {
                return u && u != url;
            });
            target.val(results);
        }

        app.preview = function (url, init) {
            if (item.attr('multiple')) {
                var child = $('<div data-url="'+url+'"></div>').css({
                    position: 'relative', display: 'block', width: itemWidth, height: itemHeight, marginRight: '10px', marginBottom: '10px'
                });
                // 预览节点
                var childRemoveDom = $('<span></span>').css({
                    position: 'absolute', display: 'block', top: 0, right: 0, backgroundColor: 'rgba(100, 100, 100, 0.5)', padding: '5px 10px',
                    color: '#fff', fontWeight: 800, fontSize: '12px', cursor: 'pointer'
                }).text('删除').data('index', url);
                var childPreviewDom = $('<img>').css($.extend({}, itemStyleNormal, itemPreviewStyle));
                child.append(childPreviewDom).append(childRemoveDom);
                item.prepend(child);
                childRemoveDom.on('click', app.remove);
                childPreviewDom.attr('src', url + app['previewStyle'])
                if (init || target.length <= 0) return;
                var results = (target.val() || '').split(',').filter(function (u) {
                    return u;
                });
                results.push(url);
                target.val(results);
            } else {
                itemPreviewDom.attr('src', url + app['previewStyle']);
                if (init || target.length <= 0) return;
                target.val(url);
            }
        }

        if (item.attr('multiple')) {
            item.append(itemSelectorDom.append(itemLabelDom).append(itemFileDom));
            (target.val() || '').split(',').filter(function (u) {
                return u;
            }).forEach(function (u) {
                app.preview(u, true);
            });
        } else {
            item.append(itemSelectorDom.append(itemLabelDom).append(itemPreviewDom).append(itemFileDom));
            if (target.length > 0) {
                app.preview(target.val(), true);
            }
        }

        item.fileupload({
            paramName: 'file',
            url: app['url'] || '/admin-upload',
            done: function (e, data) {
                return app.preview(data.result)
            }
        });

        return app;
    }

    // ================================================================================
    // ================================================================================
    // ==================================「表单管理」==================================
    // ================================================================================
    // ================================================================================

    function dom_handle_item(selector, handle, options) {
        var dom = $(selector);
        if (dom == null) return null;
        var params = $.extend(dom.data(), options || {});
        return handle(params, dom);
    }

    function form_query_url(url) {
        var uris = url.split(':');
        if (uris.length > 1) return {type: uris[0], uri: uris[uris.length - 1]}
        return {type: 'list', uri: url};
    }

    function form_query_item_select(selector, options) {
        dom_handle_item(selector, function (params, dom) {
            var rows = $.map(params.query, function (value, key) {
                return '<option value="'+key+'">'+value+'</option>';
            });
            rows.unshift('<option value="">'+ (params['defaultLabel'] || '请选择')+'</option>');
            dom.html(rows.join(''));
        }, options);
    }
    
    function form_query_item_select2(selector, options) {
        dom_handle_item(selector, function (params, dom) {
            params._ajaxQuery = form_query_url(params['query']);
            dom.select2({
                placeholder: {
                    id: '',
                    text: params['defaultLabel'] || '请选择'
                },
                language: 'zh',
                ajax: {
                    url: params['_ajaxQuery']['uri'],
                    dataType: 'json',
                    delay: 250,
                    headers: {
                        Authorization: localStorage.getItem(AuthenticationKey),
                        SecurityGrant:localStorage.getItem(SecurityGrantKey)
                    },
                    data: params['ajaxOnRequest'] || function (params) {
                        return {
                            keywords: params.term,
                            page: params.page || 1,
                            size: 10
                        };
                    },
                    processResults: params['ajaxOnResult'] || function (res, request) {
                        return {
                            results: res.data.map(function (item) {
                                return {
                                    id: item[params['repoKey'] || 'id'],
                                    text: item[params['repoLabel'] || 'text']
                                };
                            }),
                            pagination: {
                                more: (request.page * 10) < res.total
                            }
                        };
                    },
                    cache: true
                }
            });
            dom.val({id: '1', name: '管理员'}).trigger('change');
        }, options);
    }

    function form_query_item (selector, options) {
        dom_handle_item(selector, function(params, dom) {
            switch (true) {
                case dom.is('.select2'):
                    return form_query_item_select2(selector, options);
                case dom.is('select'):
                    return form_query_item_select(selector, options);
                default:
                    return dom.html(null);
            }
        }, options);
    }


    // ================================================================================
    // ================================================================================
    // ==================================「页面管理」==================================
    // ================================================================================
    // ================================================================================

    win.WebApp = function (params) {
        var app = $.extend({}, params || {});
        app.notify = function (options) {
            $.NotificationApp.send(
                options['title'] || null,
                options['message'] || null,
                options['position'] || 'top-center',
                "rgba(0,0,0,0.2)",
                options['type'] || "info"
            );
        }
        app.notifyOk = function (message, title) {
            app.notify({message: message, title: title, type: 'success'})
        }
        app.notifyWarn = function (message, title) {
            app.notify({message: message, title: title, type: 'warning'})
        }
        app.notifyError = function (message, title) {
            app.notify({message: message, title: title, type: 'error'})
        }
        return app;
    }


    win.WebTableApp = function (selector, columns, options) {
        var app = WebApp({ _selector: selector, _columns: columns });

        app.search = function (datas) {
            app.ajaxParams = $.extend(app._form.submit(), datas || {});
            app.table.ajax.reload();
        }

        app.reset = function (datas) {
            app._form.reset();
            app.ajaxParams = datas || {};
            app.table.ajax.reload(null, true);
        }

        dom_handle_item(selector, function(params, dom) {
            app._dom = dom;
            app._options = params;
            app._form = WebFormApp(app._options['query']);

            app._ajax = function (data, callback) {
                var params = $.extend({page: (data.start)/10 + 1, size: data.length}, app.ajaxParams || {});
                AjaxGet(app._options['ajaxUrl'] + "?" + $.param(params), function (res) {
                    res.draw = data.draw; // 计数器
                    res.recordsTotal = res.total;//返回数据全部记录
                    res.recordsFiltered = res.total;//过滤后记录
                    callback(res);
                });
            };

            app.table = $(selector).DataTable($.extend(true, {
                ajax: app._ajax,
                columns: columns,
                paging: true,
                ordering: false,
                searching: false,
                serverSide: true,
                deferRender: true,
                dom: '<"toolbar">frtip',
                language: {paginate: {"first": "首页", "previous": "上页", "next": "下页", "last": "末页"}}
            }, app._options || {}));

        }, options);

        return app;
    }

    win.WebFormApp = function (selector, options) {
        var app = WebApp({ _selector: selector });

        app.submit = function() {
            var obj = {};
            $(selector).serializeArray().forEach(function (row) {
                obj[row.name] = row.value;
            });
            return obj;
        }

        app.reset = function () {
            $(selector).find('.form-control').val(null).trigger('change');
        }

        dom_handle_item(selector, function (params, dom) {
            app._dom = dom;
            app._options = params;

            app.build = function (res) {
                $.each(res, function (key, value) {
                    var itemSelector = selector + ' .' + key;
                    form_query_item(itemSelector, {query: value});
                });
                if (!res['formData']) return;
                $(selector).find('.form-control').val(null).trigger('change');
                $.each(res['formData'], function (key, value) {
                    var formSelector = $(selector + ' [name="'+key+'"]');
                    if (formSelector.is('.select2')) {
                        var text = res['formData'][formSelector.data('defaultLabel') || (key + 'Label')]
                        formSelector.append(new Option(text, value, true, true)).trigger('change');
                    } else {
                        $(selector + ' [name="'+key+'"]').val(value || null).trigger('change');
                    }
                });
                options && options['onloaded'] && options['onloaded']();
            }

            if (app._options['query']) {
                AjaxGet(app._options['query'], function (res) {
                    app.build(res);
                });
            }

        }, options);

        return app;
    }

})(jQuery, window);
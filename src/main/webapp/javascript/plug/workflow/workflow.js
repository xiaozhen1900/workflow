function graphTrace(options) {
	/*var _defaults = {history:{
        srcEle: this,
        processInstanceId: $(this).attr('pid')
    }};
    var json = $.extend(true, _defaults, options);*/
    var json = {};
    var history = {};
    history.processInstanceId = $(this).attr('pid');
    history.processDefinitionId = $(this).attr('proDefId');
    json.history = history;
    // 获取图片资源
    var imageUrl = ctx + "/workflow/definition_loadResource.action?processDefinitionId=" + json.history.processDefinitionId + "&resourceType=image";
    var url = ctx + "/workflow/history_traceWorkflow.action";
    var method = "traceProcess";
    jQuery.httpcom.communications(url,json,method,function(data) {
        var positionHtml = "";
        // 生成图片
        var varsArray = new Array();
        $.each(data.results.history, function(i, v) {
            var $positionDiv = $('<div/>', {
                'class': 'activity-attr'
            }).css({
                position: 'absolute',
                left: (v.x - 1),
                top: (v.y - 1),
                width: (v.width - 2),
                height: (v.height - 2),
                backgroundColor: 'black',
                opacity: 0,
                zIndex: $.fn.qtip.zindex - 1
            });

            // 节点边框
            var $border = $('<div/>', {
                'class': 'activity-attr-border'
            }).css({
                position: 'absolute',
                left: (v.x - 1),
                top: (v.y - 1),
                width: (v.width - 4),
                height: (v.height - 3),
                zIndex: $.fn.qtip.zindex - 2
            });

            if (v.currentActiviti) {
                $border.addClass('ui-corner-all-12').css({
                    border: '3px solid red',
                    position: 'absolute',
                    left: (v.x - 25),
                    top: (v.y - 38),
                    width: (v.width - 4),
                    height: (v.height - 3)
                    //zIndex: $.fn.qtip.zindex - 2
                });
            }
            positionHtml += $positionDiv.outerHTML() + $border.outerHTML();
            varsArray[varsArray.length] = v.vars;
        });

        if ($('#workflowTraceDialog').length == 0) {
            $('<div/>', {
                id: 'workflowTraceDialog',
                title: '查看流程（按ESC键可以关闭）<button id="changeImg" style="color:black;">如果坐标错乱请点击这里</button>',
                html: "<div><img src='" + imageUrl + "' style='position:absolute; left:0px; top:0px;' />" +
                "<div id='processImageBorder'>" +
                positionHtml +
                "</div>" +
                "</div>"
            }).appendTo('body');
        } else {
            $('#workflowTraceDialog img').attr('src', imageUrl);
            $('#workflowTraceDialog #processImageBorder').html(positionHtml);
        }

        // 设置每个节点的data
        $('#workflowTraceDialog .activity-attr').each(function(i, v) {
            $(this).data('vars', varsArray[i]);
        });

        // 打开对话框
        $('#workflowTraceDialog').dialog({
            modal: true,
            resizable: false,
            dragable: false,
            open: function() {
                $('#workflowTraceDialog').dialog('option', 'title', '查看流程（按ESC键可以关闭）<button id="changeImg" style="color:black;">如果坐标错乱请点击这里</button>');
                $('#workflowTraceDialog').css('padding', '0.2em');
                $('#workflowTraceDialog .ui-accordion-content').css('padding', '0.2em').height($('#workflowTraceDialog').height() - 75);

                // 此处用于显示每个节点的信息，如果不需要可以删除
                $('.activity-attr').qtip({
                    content: function() {
                        var vars = $(this).data('vars');
                        var tipContent = "<table class='need-border'>";
                        $.each(vars, function(varKey, varValue) {
                            if (varValue) {
                                tipContent += "<tr><td class='label'>" + varKey + "</td><td>" + varValue + "<td/></tr>";
                            }
                        });
                        tipContent += "</table>";
                        return tipContent;
                    },
                    position: {
                        at: 'bottom left',
                        adjust: {
                            x: 3
                        }
                    }
                });
                // end qtip
            },
            close: function() {
                $('#workflowTraceDialog').remove();
            },
            width: document.documentElement.clientWidth * 0.75,
            height: document.documentElement.clientHeight * 0.75
        });
        
        // 处理使用js跟踪当前节点坐标错乱问题
        $('#changeImg').live('click', function() {
            $('#workflowTraceDialog').dialog('close');
            if ($('#imgDialog').length > 0) {
                $('#imgDialog').remove();
            }
            $('<div/>', {
                'id': 'imgDialog',
                title: '此对话框显示的图片是由引擎自动生成的，并用红色标记当前的节点',
                html: "<img src='" + ctx + "/workflow/instance_loadResource.action?processInstanceId=" + json.history.processInstanceId + "' />"
            }).appendTo('body').dialog({
                modal: true,
                resizable: false,
                dragable: false,
                width: document.documentElement.clientWidth * 0.95,
                height: document.documentElement.clientHeight * 0.95
            });
        });
    });
}

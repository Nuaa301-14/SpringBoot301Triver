layui.config({
    base: "js/"
}).use(['form', 'layer', 'jquery', 'laypage'], function () {
    var form = layui.form(),
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        laypage = layui.laypage,
        $ = layui.jquery;

    function renderDate(data) {
        var dataHtml = '';
        currData = data;
        usersData = data;
        console.log(currData);
        if (currData.length != 0) {
            for (var i = 0; i < currData.length; i++) {
                dataHtml += '<tr>'
                    + '<td><input type="checkbox" name="checked" lay-skin="primary" lay-filter="choose"></td>'
                    + '<td>' + currData[i].spotLocation + '</td>'
                    + '<td>' + currData[i].spotName + '</td>'
                    + '<td>' + currData[i].spotPrice + '</td>'
                    + '<td>' + currData[i].soldNumber + '</td>'
                    + '<td>' + currData[i].source + '</td>'
                    + '<td>' + currData[i].spotScore + '</td>'
                    + '<td>' + currData[i].spotArea + '</td>'
                    + '<td>' + currData[i].introduce + '</td>'
                    + '<td>'
                    + '<a class="layui-btn layui-btn-mini spot_edit" data-id="' + currData[i].spotId + '"><i class="iconfont icon-edit"></i> 详情</a>'
                    + '<a class="layui-btn layui-btn-normal layui-btn-mini spot_detail" data-id="' + currData[i].spotId + '"><i class="layui-icon">&#xe64c;</i> 目标页面</a>'
                    + '<a class="layui-btn layui-btn-danger layui-btn-mini spot_del" data-id="' + currData[i].spotId + '"><i class="layui-icon">&#xe640;</i> 删除</a>'
                    + '</td>'
                    + '</tr>';
            }
        } else {
            dataHtml = '<tr><td colspan="8">暂无数据</td></tr>';
        }
        return dataHtml;
    }

    //加载页面数据
    var usersData = '';
    var condition = false;
    var area = "";
    var source = "";
    var pageNum = "";
    $.ajax({
        url: "/data/spot",
        data: $("#pageCondition").serialize(),
        dataType: 'json',
        type: 'post',
        async: true,
        success: spotsList
    });


    function spotList(pageInfo, input, source, size) {
        //渲染数据
        console.log("condition=" + condition);
        var total = pageInfo.total;//页总数
        var pageSize = pageInfo.pageSize;//页大小
        var HotelsData = pageInfo.list;
        console.log(HotelsData);
        //分页
        var nums = pageSize; //每页出现的数据量
        laypage({
            cont: "page",
            pages: Math.ceil(total / nums),
            jump: function (obj, first) {
                console.log(obj.curr);

                function Searchpaging(curr) {
                    var text = "";
                    text = "input=" + input + "&source=" + source + "&size=" + size;
                    $.ajax({
                        url: "/data/spotSearch?page=" + curr + "&pageSize=13&" + text,
                        dataType: 'json',
                        type: 'post',
                        async: true,
                        success: pageChange
                    });
                }

                // paging(obj.curr);
                if (!first) {
                    Searchpaging(obj.curr);
                } else {
                    $(".users_content").html(renderDate(HotelsData));
                }
                // $('.users_list thead input[type="checkbox"]').prop("checked", false);
                form.render();
            }
        })

    }

    //爬取
    $(".crawlSpot").click(function () {

        function update(area, source) {
            $.ajax({
                url: "/data/spot",
                data: "area=" + area + "&source=" + source + "&order=desc",
                dataType: 'json',
                type: 'post',
                async: true,
                success: spotsList
            });
        }

        if ($(".input1").val() != '' && $(".input2").val() != '') {
            var index = layer.msg('爬取中，请稍候', {icon: 16, time: false, shade: 0.8});
            area = $(".input1").val();
            source = $(".input2").val();
            pageNum = $(".input3").val();
            setTimeout(function () {
                $.ajax({
                    url: "/crawl/crawlSpot",
                    data: "input=" + area + "&source=" + source + "&pageNum=" + pageNum,
                    type: "post",
                    dataType: "json",
                    success: function (data) {
                        console.log(data);
                        var message = data.reply;
                        console.log(message);
                        if (message[0] === "Success") {
                            condition = true;
                            update(area, source);
                        } else if (message[0] === "输入的地区不合理(请输入准确的中文或者拼音！)") {
                            alert(message[0]);
                        }
                    }
                })
                layer.close(index);
            }, 4000);
        } else {
            layer.msg("请输入地区或选择来源！");
        }
    })

    //区域查询
    $(".search_btn").click(function () {
        if ($(".search_input").val() != '') {
            var input = $(".search_input").val();
            console.log(input);
            var index = layer.msg('查询中，请稍候', {icon: 16, time: false, shade: 0.8});
            setTimeout(function () {
                $.ajax({
                    url: "/data/spotSearch",
                    data: "input=" + input + "&source=" + "&size=1",
                    type: "post",
                    dataType: "json",
                    success: function (data) {
                        condition = false;
                        spotList(data, input, "", 1);
                    }
                })
                layer.close(index);
            }, 2000);
        } else {
            layer.alert("输入为空！");
        }
    })

    //来源查询
    $(".source_btn").click(function () {
        if ($(".search_input").val() != '') {
            var input = $(".search_input").val();
            console.log(input);
            var index = layer.msg('查询中，请稍候', {icon: 16, time: false, shade: 0.8});
            setTimeout(function () {
                $.ajax({
                    url: "/data/spotSearch",
                    data: "input=" + input + "&source=" + input + "&size=2",
                    type: "post",
                    dataType: "json",
                    success: function (data) {
                        condition = false;
                        spotList(data, input, "", 2);
                    }
                })
                layer.close(index);
            }, 2000);
        } else {
            layer.alert("输入为空！");
        }
    })

    //具体查询
    $(".detail_btn").click(function () {
        if ($(".search_input").val() != '') {
            var input = $(".search_input").val();
            var index = layer.msg('查询中，请稍候', {icon: 16, time: false, shade: 0.8});
            setTimeout(function () {
                $.ajax({
                    url: "/data/spotSearch",
                    data: "input=" + input + "&source=" + "&size=3",
                    type: "post",
                    dataType: "json",
                    success: function (data) {
                        condition = false;
                        console.log(data);
                        spotList(data, input, "", 3);
                    }
                })
                layer.close(index);
            }, 2000);
        } else {
            layer.alert("输入为空！");
        }
    })

    //批量删除
    $(".batchDel").click(function () {
        var $checkbox = $('.users_list tbody input[type="checkbox"][name="checked"]');
        var $checked = $('.users_list tbody input[type="checkbox"][name="checked"]:checked');
        if ($checkbox.is(":checked")) {
            layer.confirm('确定删除选中的信息？', {icon: 3, title: '提示信息'}, function (index) {
                var index = layer.msg('删除中，请稍候', {icon: 16, time: false, shade: 0.8});
                setTimeout(function () {
                    //删除数据
                    for (var j = 0; j < $checked.length; j++) {
                        for (var i = 0; i < usersData.length; i++) {
                            if (usersData[i].newsId == $checked.eq(j).parents("tr").find(".news_del").attr("data-id")) {
                                usersData.splice(i, 1);
                                usersList(usersData);
                            }
                        }
                    }
                    $('.users_list thead input[type="checkbox"]').prop("checked", false);
                    form.render();
                    layer.close(index);
                    layer.msg("删除成功");
                }, 2000);
            })
        } else {
            layer.msg("请选择需要删除的文章");
        }
    })

    //全选
    form.on('checkbox(allChoose)', function (data) {
        var child = $(data.elem).parents('table').find('tbody input[type="checkbox"]:not([name="show"])');
        child.each(function (index, item) {
            item.checked = data.elem.checked;
        });
        form.render('checkbox');
    });

    //通过判断文章是否全部选中来确定全选按钮是否选中
    form.on("checkbox(choose)", function (data) {
        var child = $(data.elem).parents('table').find('tbody input[type="checkbox"]:not([name="show"])');
        var childChecked = $(data.elem).parents('table').find('tbody input[type="checkbox"]:not([name="show"]):checked')
        if (childChecked.length == child.length) {
            $(data.elem).parents('table').find('thead input#allChoose').get(0).checked = true;
        } else {
            $(data.elem).parents('table').find('thead input#allChoose').get(0).checked = false;
        }
        form.render('checkbox');
    })

    function x_admin_show1(title, url, w, h) {
        if (title == null || title == '') {
            title = false;
        }
        if (url == null || url == '') {
            url = "404.html";
        }
        if (w == null || w == '') {
            w = ($(window).width() * 0.9);
        }
        if (h == null || h == '') {
            h = ($(window).height() - 0);
        }
        layer.open({
            type: 2,
            area: [w + 'px', h + 'px'],
            fix: false, //不固定
            maxmin: true,
            shadeClose: true,
            shade: 0,
            title: title,
            content: url,
        });
    }
    //操作
    $("body").on("click", ".spot_edit", function () {  //编辑
        var _this = $(this);
        for (var i = 0; i < usersData.length; i++) {
            if (usersData[i].spotId == _this.attr("data-id")) {
                console.log("i=" + i);
                console.log(usersData[i]);
                var id=usersData[i].spotId;
                x_admin_show1("酒店详细","/spot/admin/detail?hotelId="+id);
                break;
            }
        }
    })

    $("body").on("click", ".spot_del", function () {  //删除
        var _this = $(this);
        layer.confirm('确定删除？', {icon: 3, title: '提示信息'}, function (index) {
            var index = layer.msg('删除中，请稍候', {icon: 16, time: false, shade: 0.8});
            _this.parents("tr").remove();
            for (var i = 0; i < usersData.length; i++) {
                if (usersData[i].spotId == _this.attr("data-id")) {
                    var spotId=usersData[i].spotId;
                    setTimeout(function () {
                        $.ajax({
                            url: "/spot/admin/delete",
                            data: "spotId=" + spotId,
                            type: "post",
                            dataType: "json",
                            success: function (data) {
                                var reply=data.reply;
                                console.log(reply);
                                if (reply[0] === "Success") {
                                    console.log("jjjeee");
                                    usersData.splice(i, 1);
                                    $(".users_content").html(renderDate(usersData));
                                    layer.msg("删除成功");
                                } else if (reply[0] === "Fail") {
                                    layer.msg("出现了问题！");
                                }
                            }
                        })
                        layer.close(index);
                    }, 2000);
                    break;
                }
            }
        });
    })

    $("body").on("click", ".spot_detail", function () { //酒店详情
        var _this = $(this);
        for (var i = 0; i < usersData.length; i++) {
            if (usersData[i].spotId == _this.attr("data-id")) {
                console.log("i=" + i);
                console.log(usersData[i]);
                var url = usersData[i].spotUrl;
                window.open(url);
            }
        }
    })


    function spotsList(pageInfo) {
        //渲染数据
        console.log("condition=" + condition);

        var total = pageInfo.total;//页总数
        var pageSize = pageInfo.pageSize;//页大小

        usersData = pageInfo.list;
        //分页
        var nums = pageSize; //每页出现的数据量
        laypage({
            cont: "page",
            pages: Math.ceil(total / nums),
            jump: function (obj, first) {
                console.log(obj.curr);
                // paging(obj.curr);
                if (!first) {
                    paging(obj.curr);
                } else {
                    $(".users_content").html(renderDate(usersData));
                }
                // $('.users_list thead input[type="checkbox"]').prop("checked", false);
                form.render();
            }
        })
    }

    function paging(curr) {
        var text = "";
        if (condition === true) {
            text = "area=" + area + "&source=" + source + "&order=desc";
        }
        $.ajax({
            url: "/data/spot?page=" + curr + "&pageSize=13&" + text,
            dataType: 'json',
            type: 'post',
            async: true,
            success: pageChange
        });
    }

    function pageChange(pageInfo) {
        var total = pageInfo.total;//页总数
        var pageNum = pageInfo.pageNum;//页号
        var pageSize = pageInfo.pageSize;//页大小
        usersData = pageInfo.list;
        $(".users_content").html("");
        $(".users_content").html(renderDate(usersData));
    }

})
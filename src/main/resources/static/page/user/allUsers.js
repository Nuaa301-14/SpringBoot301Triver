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
        usersData=data;
        console.log(currData);
        if (currData.length != 0) {
            for (var i = 0; i < currData.length; i++) {
                dataHtml += '<tr>'
                    + '<td><input type="checkbox" name="checked" lay-skin="primary" lay-filter="choose"></td>'
                    + '<td>' + currData[i].hotelName + '</td>'
                    + '<td>' + currData[i].hotelLocation + '</td>'
                    + '<td>' + currData[i].price + '</td>'
                    + '<td>' + currData[i].commentNumber + '</td>'
                    + '<td>' + currData[i].source + '</td>'
                    + '<td>' + currData[i].updated + '</td>'
                    + '<td>'
                    + '<a class="layui-btn layui-btn-mini hotel_edit" data-id="' + currData[i].hotelId + '"><i class="iconfont icon-edit"></i> 详情</a>'
                    +'<a class="layui-btn layui-btn-normal layui-btn-mini hotel_detail" data-id="' + currData[i].hotelId + '"><i class="layui-icon">&#xe64c;</i> 目标页面</a>'
                    + '<a class="layui-btn layui-btn-danger layui-btn-mini hotel_del" data-id="' + currData[i].hotelId + '"><i class="layui-icon">&#xe640;</i> 删除</a>'
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
        url: "/data/hotel",
        data: $("#pageCondition").serialize(),
        dataType: 'json',
        type: 'post',
        async: true,
        success: usersList
    });


    function hotelsList(pageInfo, input, source, size) {
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
                        url: "/data/hotelSearch?page=" + curr + "&pageSize=13&" + text,
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
    $(".crawlHotel").click(function () {

        function update(area, source) {
            $.ajax({
                url: "/data/hotel",
                data: "area=" + area + "&source=" + source + "&order=desc",
                dataType: 'json',
                type: 'post',
                async: true,
                success: usersList
            });
        }

        if ($(".input1").val() != '' && $(".input2").val() != '') {
            var index = layer.msg('爬取中，请稍候', {icon: 16, time: false, shade: 0.8});
            area = $(".input1").val();
            source = $(".input2").val();
            pageNum = $(".input3").val();
            setTimeout(function () {
                $.ajax({
                    url: "/crawl/hotelCrawl",
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
                    url: "/data/hotelSearch",
                    data: "input=" + input + "&source=" + "&size=1",
                    type: "post",
                    dataType: "json",
                    success: function (data) {
                        condition = false;
                        hotelsList(data, input, "", 1);
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
                    url: "/data/hotelSearch",
                    data: "input=" + input + "&source=" + input + "&size=2",
                    type: "post",
                    dataType: "json",
                    success: function (data) {
                        condition = false;
                        hotelsList(data, input, "", 2);
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
                    url: "/data/hotelSearch",
                    data: "input=" + input + "&source=" + "&size=3",
                    type: "post",
                    dataType: "json",
                    success: function (data) {
                        condition = false;
                        console.log(data);
                        hotelsList(data, input, "", 3);
                    }
                })
                layer.close(index);
            }, 2000);
        } else {
            layer.alert("输入为空！");
        }
    })


    //添加会员
    $(".usersAdd_btn").click(function () {
        var index = layui.layer.open({
            title: "添加会员",
            type: 2,
            content: "addUser.html",
            success: function (layero, index) {
                setTimeout(function () {
                    layui.layer.tips('点击此处返回会员列表', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                }, 500)
            }
        })
        //改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
        $(window).resize(function () {
            layui.layer.full(index);
        })
        layui.layer.full(index);
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

    //操作
    $("body").on("click", ".hotel_edit", function () {  //编辑
        var _this = $(this);
        for (var i=0;i<usersData.length;i++){
            if (usersData[i].hotelId == _this.attr("data-id")){
                console.log("i="+i);
                console.log(usersData[i]);
            }
        }
        layer.msg('您点击了会员编辑按钮，由于是纯静态页面，所以暂时不存在编辑内容，后期会添加，敬请谅解。。。', {icon: 6, title: '文章编辑'});
    })

    $("body").on("click", ".hotel_del", function () {  //删除
        var _this = $(this);
        layer.confirm('确定删除此用户？', {icon: 3, title: '提示信息'}, function (index) {
            //_this.parents("tr").remove();
            for (var i = 0; i < usersData.length; i++) {
                if (usersData[i].usersId == _this.attr("data-id")) {
                    usersData.splice(i, 1);
                    usersList(usersData);
                }
            }
            layer.close(index);
        });
    })

    $("body").on("click",".hotel_detail",function () { //酒店详情

    })



    function usersList(pageInfo) {
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
            url: "/data/hotel?page=" + curr + "&pageSize=13&" + text,
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
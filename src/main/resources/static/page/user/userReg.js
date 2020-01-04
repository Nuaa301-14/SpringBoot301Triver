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
                    + '<td>' + currData[i].hotelName + '</td>'
                    + '<td>' + currData[i].hotelLocation + '</td>'
                    + '<td>' + currData[i].price + '</td>'
                    + '<td>' + currData[i].commentNumber + '</td>'
                    + '<td>' + currData[i].source + '</td>'
                    + '<td>' + currData[i].updated + '</td>'
                    + '<td>'
                    + '<a class="layui-btn layui-btn-mini hotel_edit" data-id="' + currData[i].hotelId + '"><i class="iconfont icon-edit"></i> 详情</a>'
                    + '<a class="layui-btn layui-btn-normal layui-btn-mini hotel_detail" data-id="' + currData[i].hotelId + '"><i class="layui-icon">&#xe64c;</i> 目标页面</a>'
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
        url: "/data/user",
        data: $("#pageCondition").serialize(),
        dataType: 'json',
        type: 'post',
        async: true,
        success: usersList
    });



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
    $("body").on("click", ".hotel_edit", function () {  //编辑
        var _this = $(this);
        for (var i = 0; i < usersData.length; i++) {
            if (usersData[i].hotelId == _this.attr("data-id")) {
                console.log("i=" + i);
                console.log(usersData[i]);
                var id=usersData[i].hotelId;
                x_admin_show1("酒店详细","/hotel/admin/detail?hotelId="+id);
                break;
            }
        }
    })

    $("body").on("click", ".hotel_del", function () {  //删除
        var _this = $(this);
        layer.confirm('确定删除此用户？', {icon: 3, title: '提示信息'}, function (index) {
            var index = layer.msg('删除中，请稍候', {icon: 16, time: false, shade: 0.8});
            _this.parents("tr").remove();
            for (var i = 0; i < usersData.length; i++) {
                if (usersData[i].hotelId == _this.attr("data-id")) {
                    var hotelId=usersData[i].hotelId;
                    setTimeout(function () {
                        $.ajax({
                            url: "/hotel/admin/delete",
                            data: "hotelId=" + usersData[i].hotelId,
                            type: "post",
                            dataType: "json",
                            success: function (data) {
                                console.log(data);
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

    $("body").on("click", ".hotel_detail", function () { //酒店详情
        var _this = $(this);
        for (var i = 0; i < usersData.length; i++) {
            if (usersData[i].hotelId == _this.attr("data-id")) {
                console.log("i=" + i);
                console.log(usersData[i]);
                var url = usersData[i].targetUrl;
                window.open(url);
            }
        }
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
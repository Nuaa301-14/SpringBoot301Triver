var areaData = address;
var $form;
var form;
var $;
layui.config({
    base: "../../js/"
}).use(['form', 'layer', 'upload', 'laydate'], function () {
    form = layui.form();
    var layer = parent.layer === undefined ? layui.layer : parent.layer;
    $ = layui.jquery;
    $form = $('form');
    laydate = layui.laydate;


    console.log(id);
    $.ajax({
        url: '/data/userInfo',
        type: 'post',
        dataType: 'json',
        data: "id=" + id,
        success: function (data) {
            console.log(data);
            load(data.userInfo);
        }
    })
    layui.upload({
        url: "/static/json/userface.json",
        success: function (res) {
            var num = parseInt(4 * Math.random());  //生成0-4的随机数
            //随机显示一个头像信息
            userFace.src = res.data[num].src;
        }
    });

    //添加验证规则
    form.verify({
        oldPwd: function (value, item) {
            var pwd = $(".oldPassword").val();
            console.log(pwd);
            if (value != pwd) {
                return "密码错误，请重新输入！";
            }
        },
        newPwd: function (value, item) {
            if (value.length < 6) {
                return "密码长度不能小于6位";
            }
        },
        confirmPwd: function (value, item) {
            if (!new RegExp($("#oldPwd").val()).test(value)) {
                return "两次输入密码不一致，请重新输入！";
            }
        }
    })

    //判断是否修改过用户信息，如果修改过则填充修改后的信息
    function load(userInfo) {
        $(".name").val(userInfo.name);
        $(".userName").val(userInfo.userName);
        $(".realName").val(userInfo.realName);
        $(".userSex input[value=" + userInfo.gender + "]").attr("checked", "checked"); //性别
        $(".userPhone").val(userInfo.phoneNumber); //手机号
        $(".userBirthday").val(userInfo.birth); //出生年月
        $(".userEmail").val(userInfo.email); //用户邮箱
        form.render();
    }

    //判断是否修改过头像，如果修改过则显示修改后的头像，否则显示默认头像
    $("#userFace").attr("src", "/static/images/face.jpg");

    //提交个人资料
    form.on("submit(changeUser)", function (data) {
        var index = layer.msg('提交中，请稍候', {icon: 16, time: false, shade: 0.8});
        //将填写的用户信息存到session以便下次调取
        var userInfoHtml = new Array();
        userInfoHtml.push({
            userName: $(".userName").val(),
            realName: $(".realName").val(),
            gender: data.field.sex,
            phoneNumber: $(".userPhone").val(),
            birth: $(".userBirthday").val(),
            email: $(".userEmail").val()
        });
        console.log(userInfoHtml);
        console.log(JSON.stringify(userInfoHtml));
        $.ajax({
            url: '/user/changeInfo',
            type: 'post',
            dataType: 'json',
            contentType: 'application/json',
            data: JSON.stringify(userInfoHtml),
            success: function (data) {
                console.log(data);
                load(data.user);
                setTimeout(function () {
                    layer.close(index);
                    layer.msg("提交成功！");
                }, 2000);
            }
        });
        return false;
    })

    //修改密码
    form.on("submit(changePwd)", function (data) {
        var index = layer.msg('提交中，请稍候', {icon: 16, time: false, shade: 0.8});
        var oldPwd = $(".oldPwd").val();
        var newPwd = $(".newPwd").val();
        var confirmPwd = $(".confirmPwd").val();
        var userInfoHtml = new Array();
        userInfoHtml.push({
            id: id,
            oldPwd: oldPwd,
            newPwd: newPwd,
            confirmPwd: confirmPwd
        });

        $.ajax({
            url: '/user/PwdChange',
            type: 'post',
            dataType: 'json',
            contentType: 'application/json',
            data: JSON.stringify(userInfoHtml),
            success: function (data) {
                console.log(data);
                var reply = data.reply;
                if (reply == "success") {
                    setTimeout(function () {
                        layer.close(index);
                        layer.msg("修改成功！");
                        $(".oldPwd").val('');
                        $(".newPwd").val('');
                        $(".confirmPwd").val('');
                    }, 2000)
                    // window.location.href="/user/changePwd";
                } else if (reply == "fail") {
                    setTimeout(function () {
                        layer.close(index);
                        layer.msg("修改失败！");
                    }, 2000)
                }
            }
        });
        return false;
    })

})

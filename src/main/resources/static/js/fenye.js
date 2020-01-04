// layui.use(['laypage', 'layer'], function(){
//     var laypage = layui.laypage
//         ,layer = layui.layer;
//
//     $(".searchBtn").click(function () {
//         search();
//     })
//
//
//     function search() {
//         var textstr = document.getElementById("searchtext").value;
//         var typestr = document.getElementById("searchtype").value;
//         if(textstr==""||textstr==null){
//             return;
//         }
//         var searchList=new Array();
//         searchList.push({text:textstr,type:typestr});
//
//         $.ajax({
//             //访问后台的路径
//             url: "/onclick_search",
//             //将数据通过函数转换
//             data: JSON.stringify(searchList),
//             dataType:"json",//指定数据类型为JSON
//             // 指定类型，不然会报错，无法解析提交类型
//             contentType: "application/json;charset=utf-8",
//             type: 'post',//指定提交方式为post
//             success:function (data) {
//                 var jsonData = JSON.stringify(data);
//                 var spotlist = {};
//                 jQuery.each(JSON.parse(jsonData),function(i,item){
//                     spotlist[i] = item;
//                 });
//                 laypage.render({
//                     elem: 'demo7'
//                     ,count: 100
//                     ,layout: ['count', 'prev', 'page', 'next', 'limit', 'refresh', 'skip']
//                     ,jump: function(obj){
//                         dataHtml='';
//                         for (var i=0;i<9;i++){
//                             dataHtml+='<figure>'
//                                 +'<a href="'+spotlist[i].spotUrl+'"><img src="../../static/io/img/tour'+(i+1)+'.jpg" alt=""></a>'
//                                 + '<figcaption>'
//                                 + '<h3 class="content" > &lt;'+ spotlist[i].spotName +'&gt;</h3>'
//                                 + spotlist[i].introduce
//                                 +'<div class="info">'
//                                 +'<em class="sat">'+ '满意度'+spotlist[i].spotScore +'</em>'
//                                 +'<span class="price">'+ '￥'+spotlist[i].spotPrice +'</span>'
//                                 +'</div>'
//                                 +'<div>'+spotlist[i].spotLocation +'———'+spotlist[i].recommend+'人推荐'+'</div>'
//                                 +'</figcaption>'
//                                 +'</figure>'
//                         }
//                         $(".spotShow").html(dataHtml);
//                     }
//                 });
//             }
//
//         });
//
//     }
//     //完整功能
//
//     //调用分页
//     laypage.render({
//         elem: 'demo20'
//         ,count: data.length
//         ,jump: function(obj){
//             //模拟渲染
//             document.getElementById('biuuu_city_list').innerHTML = function(){
//                 var arr = []
//                     ,thisData = data.concat().splice(obj.curr*obj.limit - obj.limit, obj.limit);
//                 layui.each(thisData, function(index, item){
//                     arr.push('<li>'+ item +'</li>');
//                 });
//                 return arr.join('');
//             }();
//         }
//     });
//
// });
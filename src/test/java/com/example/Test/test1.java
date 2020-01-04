package com.example.Test;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class test1 {
    @Test
    public void test1() {
        String name = "123456789!";
        String[] s = name.split(" ");
        System.out.println(s.length);
        System.out.println(name.substring(2, name.length()));
    }

    @Test
    public void test2() {
        String html = "<li class=\"hotel_price_icon\"><div class=\"J_Price_6874402\" hotel=\"6874402\" data-data=\"zAShEDAupMuY1tRH6IocMbHQjP7r3vUj7UlYNFmaZeMKhHgvny19iAGqT1q9ATu6Yqa7GONHcX3UaX/uPZP7qpCNx2rkXYH0lvB6Gyl9xKOKpvlLQWhc01aMFKdWotKv5Fg/1ac38omt/1UiRElST+OzBQRv6cBMSLifW82ewdTpZ3kfaID7OsArb4SOpbX8kVQ/JkOi7YuzdK6pJt5+llbk2Ob0JF3HNZEr0eJmyUdn3Y7bBVbT1XBOKhDvo+lVObAKdH1v/Nsq1svKTrClASEhcFZVLGLuTaBgtL33Aik8Wf89rKOmsg/XXFS3OIijsY2Z9NZzszCDZznSCNzsvSc71nnvThecAWd+8+pSbPdl0tQU0xGFGg3HPApFhUPLDMy5Kqay/duaYyijVJMht6DKp4bsW6qwh8HMJlYHWYo=\"><div><div class=\"hotel_price \" data-id=\"6874402\"> <dfn>¥</dfn><a href=\"https://hotels.ctrip.com/hotel/6874402.html?isFull=F&amp;checkIn=2019-10-25&amp;checkOut=2019-10-26&amp;masterhotelid=6874402&amp;hcityid=2#ctm_ref=hod_sr_rec_dl_n_1_25\" data-dopost=\"T\" data-ctm=\"#ctm_ref=hod_sr_rec_dl_n_1_25\" target=\"_blank\" data-href=\"https://hotels.ctrip.com/hotel/6874402.html?isFull=F\"><span class=\"J_price_lowList\">298</span></a>起<br></div></div></div><div class=\"original_price\"></div><div class=\"original_price\"><span title=\"可礼品卡支付\" class=\"giftcard_available\" data-role=\"title\"><i></i>可礼品卡支付</span></div><div class=\"action_info\"><p class=\"detail_btn\" data-id=\"6874402\"><a class=\"btn_buy\" target=\"_blank\" data-dopost=\"T\" href=\"/hotel/6874402.html?isFull=F#ctm_ref=hod_sr_lst_dl_n_1_25\">查看详情</a></p></div></li>";
        System.out.println(Integer.parseInt(Jsoup.parse(html).select("li.hotel_price_icon div.hotel_price span").text()));
        System.out.println(Jsoup.parse(html).select("li.hotel_price_icon div.action_info a").attr("href"));
    }

    @Test
    public void test3() {
        String name = "/hotel/macau59";
        System.out.println(StringUtils.getDigits(name));

        name = "<a title=\"澳门酒店\" href=\"/hotel/macau59\">澳门</a>";
        System.out.println(Jsoup.parse(name).select("a").attr("href"));
        String[] s = {"1332,", "asdf", "dsaf"};
        for (String s1 :
                s) {
            System.out.println(s1);
        }
        String t1 = "2精选 豪枫雅致酒店(上海国际旅游度假区唐镇地铁站店) sss";

    }

    @Test
    public void test4() {
        String html = "<html>\n" +
                " <head></head>\n" +
                " <body>\n" +
                "  <input type=\"text\" class=\"input_txt input_txtShort\" value=\"北京\" _lastvalue=\"北京\" autocomplete=\"on\" maxlength=\"100\" name=\"cityName\" id=\"txtCity\" _cqnotice=\"中文/拼音/英文\">\n" +
                " </body>\n" +
                "</html>";
        System.out.println(Jsoup.parse(html).getElementsByTag("input").attr("value"));
    }

    @Test
    public void test() {
        String score = "4.6 风";
        System.out.println(score.substring(0, score.indexOf(" ")));
        System.out.println(StringUtils.getDigits(score));
        String test = "<div class=\"list_product_box js_product_item\" data-track-product-id=\"21530022\" data-track-is-recommend=\"false\" data-track-inner-pos=\"0\">\n" +
                " <div class=\"list_product_item_border\">\n" +
                "  <div class=\"list_product_item\">\n" +
                "   <div class=\"list_product_left\">\n" +
                "    <img class=\"list_product_pic\" src=\"//dimg04.c-ctrip.com/images/300v0x000000liuks9C9C_C_200_150.jpg\" alt=\"下单即享无限次入园！提前入园！+上海海昌海洋公园主题度假酒店+赠送海昌海洋公园门票2张•【亲子打卡热卖免费停车】\" style=\"width: 200px; height: 150px; opacity: 1;\">\n" +
                "    <p class=\"list_product_tip\"><span class=\"list_product_name\">酒店+景点</span><i class=\"list_product_heng\"></i><span class=\"list_product_place\">上海</span></p>\n" +
                "   </div>\n" +
                "   <div class=\"list_product_right\">\n" +
                "    <p class=\"list_product_title\" title=\"下单即享无限次入园！提前入园！+上海海昌海洋公园主题度假酒店+赠送海昌海洋公园门票2张•【亲子打卡热卖免费停车】\"><span>下单即享无限次入园！提前入园！+上海海昌海洋公园主题度假酒店+赠送海昌海洋公园门票2张•【亲子打卡热卖免费停车】</span></p>\n" +
                "    <div class=\"list_product_content basefix\">\n" +
                "     <div class=\"list_content_right\">\n" +
                "      <div class=\"list_change_box basefix list_change_box_one\">\n" +
                "       <div class=\"list_change_left\"></div>\n" +
                "       <div class=\"list_change_right\">\n" +
                "        <div class=\"list_change_one\"></div>\n" +
                "        <div class=\"list_change_two\">\n" +
                "         <p class=\"list_change_grade\"><strong>4.7</strong>分</p>\n" +
                "        </div>\n" +
                "       </div>\n" +
                "      </div>\n" +
                "      <div class=\"list_sr_price_box basefix\">\n" +
                "       <span class=\"list_sr_price\"><dfn>￥</dfn><strong>1227</strong>起/份</span>\n" +
                "      </div>\n" +
                "     </div>\n" +
                "     <div class=\"list_content_left\">\n" +
                "      <div class=\"list_label_box\">\n" +
                "       <div style=\"display: inline-block;\">\n" +
                "        <span class=\"list_label_star\">高档型</span>\n" +
                "       </div>\n" +
                "       <div style=\"display: inline-block;\">\n" +
                "        <span class=\"list_label_yellow\">礼</span>\n" +
                "       </div>\n" +
                "       <span class=\"list_label_yellow\">特卖</span>\n" +
                "       <span class=\"list_label_blue\" title=\"\">主题公园</span>\n" +
                "       <span class=\"list_label_blue\" title=\"\">海洋乐园</span>\n" +
                "       <span class=\"list_label_blue\" title=\"\">游乐园</span>\n" +
                "       <span class=\"list_label_blue\">当季畅销</span>\n" +
                "      </div>\n" +
                "      <div class=\"scenic_lable_box\">\n" +
                "       <span class=\"scenic_lable_text\">酒店：上海海昌海洋公园主题度假酒店</span>\n" +
                "      </div>\n" +
                "      <div class=\"scenic_recommend_text\">\n" +
                "       推荐景点：\n" +
                "       <div style=\"display: inline-block;\">\n" +
                "        <span>上海海昌海洋公园</span>\n" +
                "       </div>\n" +
                "      </div>\n" +
                "      <p class=\"scenic_days_text\">天数：2天1晚，3天2晚，4天3晚</p>\n" +
                "      <p class=\"scenic_range_text\" title=\"您距酒店237km，自驾约3小时；酒店到景点340m\">距离：您距酒店237km，自驾约3小时；酒店到景点340m</p>\n" +
                "      <p class=\"list_product_retail\" title=\"上海携程国际旅行社有限公司\">供应商：<i class=\"list_ctrip_icon\"></i>携程自营</p>\n" +
                "     </div>\n" +
                "    </div>\n" +
                "   </div>\n" +
                "  </div>\n" +
                " </div>\n" +
                "</div>";
        String id = Jsoup.parse(test).select("div.list_product_box").attr("data-track-product-id");
        System.out.println(id);
        test = "【上海·豫园地区】";
        System.out.println(test.substring(1, test.length() - 1));
    }

    @Test
    public void tests() {
        String html = "<div class=\"h_item mvt_171218\" id=\"hotel50301018\" method=\"hotelItem\" data-hotelid=\"50301018\" data-tracetoken=\"|*|cityId:101|*|qId:083db215-ebf9-400e-8181-0a7675e02f81|*|st:city|*|sId:101|*|pos:0|*|hotelId:50301018|*|Tp:default|*|page_index:0|*|page_size:20|*|\">\n" +
                "<div class=\"h_info\">\n" +
                "<div class=\"h_info_pic\" data-mark=\"img_50301018_container\" method=\"\">\n" +
                "<a href=\"/50301018/\" target=\"_blank\">\n" +
                "<img method=\"bigImg\" data-hotelid=\"50301018\" data-producttype=\"0\" data-needdatacache=\"true\" data-mark=\"img_50301018\" onerror=\"this.src='http://m.elongstatic.com/static/webapp/pc_static/pc_hotel/other/web/pic/no-f-pic.jpg'\" src=\"http://pavo.elongstatic.com/i/Hotel180_135/nw_KiaXU5Lv7W.webp\" big-src=\"http://pavo.elongstatic.com/i/Hotel480_350/nw_KiaXU5Lv7W.jpg\" alt=\"北京京泰龙国际大酒店\" width=\"180\" height=\"130\">\n" +
                "</a>\n" +
                "<div class=\"float_view_pic\" method=\"imgContainer\" data-mark=\"img_50301018_big\" style=\"display: none;\">查看相册<i></i></div></div>\n" +
                "<div class=\"h_info_text\">\n" +
                "<!---------------->\n" +
                "<div class=\"h_info_pri\">\n" +
                "<p>\n" +
                "</p><div class=\"pricesquare\">\n" +
                "<a href=\"/50301018/\" target=\"_blank\">\n" +
                "<span class=\"cf55\">¥</span>\n" +
                "<span class=\"h_pri_num \">420</span>\n" +
                "<span class=\"cf55\">起</span>\n" +
                "</a>\n" +
                "</div>\n" +
                "<p></p>\n" +
                "<p class=\"intro_room\" method=\"recommendRoom\" title=\"热销房型推荐\" data-hotelid=\"50301018\" data-producttype=\"0\" data-needdatacache=\"true\">查看推荐房型<i></i></p>\n" +
                "<p class=\"mt5\">\n" +
                "</p>\n" +
                "<div class=\"to_detail\">\n" +
                "<a class=\"t14\" href=\"javascript:void(0);\" data-link=\"/50301018/\" method=\"gotodetail\">\n" +
                "查看详情 </a>\n" +
                "</div>\n" +
                "</div>\n" +
                "<!---------------->\n" +
                "<div class=\"h_info_comt\">\n" +
                "<a href=\"javascript:void(0);\" data-link=\"/50301018/#review\" class=\"h_comt_data\">\n" +
                "<span method=\"review\" class=\"h_info_comt_bg\">\n" +
                "<i class=\"t20 c37e\">4.5&nbsp;</i><i class=\"t20\">棒极了</i>\n" +
                "</span>\n" +
                "<span class=\"c555 block mt5\" data-score=\"4.5\">共<b>6246</b>条点评</span>\n" +
                "</a>\n" +
                "<span class=\"block listTagItem\">“离大栅栏近”</span>\n" +
                "<p class=\"lastt_book mt10\" id=\"recentOrder_50301018\">最新预订:1小时前</p>\n" +
                "<!-- 收藏 -->\n" +
                "<div class=\"h_add_cc\">\n" +
                "<span class=\"add_collect \" method=\"collectable\" data-hotelid=\"50301018\">\n" +
                "<i class=\"icon_add_coll\"></i>\n" +
                "<span data-showname=\"favStatus\">\n" +
                "收藏 </span>\n" +
                "</span>\n" +
                "</div>\n" +
                "</div>\n" +
                "<!---------------->\n" +
                "<div class=\"h_info_base\">\n" +
                "<p class=\"h_info_b1\">\n" +
                "<a href=\"javascript:void(0);\" title=\"北京京泰龙国际大酒店\"><span class=\"icon_nmb\">1</span><span method=\"gotodetail\" data-link=\"/50301018/\" class=\"info_cn\">北京京泰龙国际大酒店</span></a>\n" +
                "<i class=\"icon_crown_new\" title=\"战略合作酒店，为艺龙会员提供优质服务及优惠房价\"></i>\n" +
                "<b class=\"icon_stars icon_elevel4\" title=\"艺龙用户评定为高档型酒店\"></b>\n" +
                "</p>\n" +
                "<p class=\"h_info_b2\">\n" +
                "<a href=\"/beijing/center010119/\" data-recall=\"true\">[前门/崇文门商贸区]</a>\n" +
                "珠市口东大街19号\n" +
                "<span class=\"info_b2_l\">\n" +
                "<span class=\"l1\" title=\"北京京泰龙国际大酒店地图\" method=\"showMap\" data-lng=\"116.406501933\" data-lat=\"39.898447801\" data-maptype=\"0\" data-hotelid=\"50301018\" data-hotelname=\"北京京泰龙国际大酒店\" data-hotelrank=\"1\" data-hoteladdress=\"珠市口东大街19号\">\n" +
                "<i class=\"icon_land_map\"></i>地图\n" +
                "</span>\n" +
                "<!-- 暂时不走街景 添加1==2条件 -->\n" +
                "</span>\n" +
                "</p>\n" +
                "<div class=\"hotel_inf_sale clearfix mt5\">\n" +
                "<p class=\"h_comt_other\">\n" +
                "<span class=\"icon_faci_wifi\" title=\"WiFi上网\"></span>\n" +
                "<span class=\"icon_faci_tcc\" title=\"停车场\"></span>\n" +
                "<span class=\"icon_faci_ct\" title=\"餐厅\"></span>\n" +
                "<span class=\"icon_faci_hys\" title=\"会议室\"></span>\n" +
                "<span class=\"icon_faci_xljc\" title=\"行李寄存\"></span>\n" +
                "</p>\n" +
                "</div>\n" +
                "<div class=\"tagList clearfix\">\n" +
                "<ul>\n" +
                "<li class=\"childHotel\">\n" +
                "<p>商务出行</p>\n" +
                "</li>\n" +
                "<li class=\"childHotel\">\n" +
                "<p>会议酒店</p>\n" +
                "</li>\n" +
                "</ul>\n" +
                "</div>\n" +
                "<div>\n" +
                "<span class=\"rankList\"><i></i><span>东城人气高档酒店NO.2</span></span>\n" +
                "</div>\n" +
                "</div>\n" +
                "</div>\n" +
                "<!--------------------------Map Start--------------------------------------->\n" +
                "<!-- 地图 start-->\n" +
                "<div id=\"h_map_wrap50301018\" style=\"display:none; height: 410px;\">\n" +
                "</div>\n" +
                "<!-- 地图 end-->\n" +
                "<div id=\"soso_map_wrap50301018\" style=\"display:none\" class=\"mt10\"></div>\n" +
                "<!--------------------------Map End------------------------------------------->\n" +
                "<!--------------------------Review Start----------------------------------------->\n" +
                "<!--------------------------Review End------------------------------------------->\n" +
                "<!--------------------------psgRecommend start------------------------------------------->\n" +
                "<!--------------------------psgRecommend End------------------------------------------->\n" +
                "</div>\n" +
                "</div>";
        String text = Jsoup.parse(html).select("div.h_info div.h_info_base p.h_info_b2").get(0).text();
        String[] s = text.split(" ");
        String hotelLocation = s[0] + s[1];
        System.out.println(hotelLocation);
        Elements select = Jsoup.parse(html).select("div.h_info div.h_info_comt a").select("span");
        System.out.println(select);
        for (int i = 0; i < select.size(); i++) {
            if (i == 0) {
                select.get(i).select("i").get(0).text().trim();
            } else if (i == 1) {
                select.get(i).select("b").text();
            }
        }

        Elements select1 = Jsoup.parse(html).select("div.h_info div.h_info_pri div.pricesquare a");
        System.out.println(select1);
        System.out.println(select1.select("span").text());
        System.out.println(StringUtils.getDigits(select1.select("span").text()));
        System.out.println();
    }

    @Test
    public void te() {
        String html = "\n" +
                "                                <div class=\"clearfix single-wrapper\">\n" +
                "                                    <a onclick=\"_tcTraObj._tcTrackEvent('search', '13', '/detail', '|*|pos:1|*|k:null|*|locPId:0|*|locCId:0|*|cityId:321|*|filter:|*|sort:0/0|*|pgPath:/hotel/list|*|resId:60201256|*|showPrice:219.0|*|resCH:elong|*|')\" title=\"上海徐汇云睿酒店图片\" class=\"img-container\" href=\"/HotelInfo-60201256.html\" target=\"_blank\">\n" +
                "                                        <img class=\"big-hotel-img\" rel=\"nofollow\" src=\"//pavo.elongstatic.com/i/mobile960/nw_0007NxIv.jpg\" data-src=\"//pavo.elongstatic.com/i/mobile960/nw_0007NxIv.jpg\" alt=\"上海徐汇云睿酒店图片\" onerror=\"this.src='//file.40017.cn/img140017cnproduct/cn/h/dzs-hotel/1.0.0.0/images/common/default-big.jpg'\">\n" +
                "                                        \n" +
                "                                        \n" +
                "                                    </a>\n" +
                "                                    <div class=\"info\">\n" +
                "                                        <h3 class=\"fm-Yahei\">\n" +
                "                                            <p>\n" +
                "                                                <a onclick=\"_tcTraObj._tcTrackEvent('search', '13', '/detail', '|*|pos:1|*|k:null|*|locPId:0|*|locCId:0|*|cityId:321|*|filter:|*|sort:0/0|*|pgPath:/hotel/list|*|resId:60201256|*|showPrice:219.0|*|resCH:elong|*|')\" title=\"上海徐汇云睿酒店\" href=\"/HotelInfo-60201256.html\" target=\"_blank\" class=\"clearfix\">\n" +
                "                                                    <i class=\"num\">1</i>\n" +
                "                                                    <span class=\"title\">\n" +
                "                                                        上海徐汇云睿酒店\n" +
                "                                                        <span class=\"star_level\">\n" +
                "                                                            \n" +
                "                                                            <span class=\"level diamond d4\" title=\"高档型（同行/同程网友评定为4钻）\"><em><i></i></em></span>\n" +
                "                                                        </span>\n" +
                "                                                    </span>\n" +
                "                                                </a>\n" +
                "                                                \n" +
                "                                                        <span class=\"tags\">\n" +
                "                                                            <span class=\"flags\">\n" +
                "                                                                <strong title=\"同程用户真实点评推荐\" class=\"comment_tag\">性价比高</strong>\n" +
                "                                                            </span>\n" +
                "                                                        </span>\n" +
                "                                            \t\n" +
                "                                            </p>\n" +
                "                                        </h3>\n" +
                "                                        <p class=\"detail-info\">\n" +
                "                                            \n" +
                "                                                <a href=\"/hotel/shanghai321/syq20149/\"><i title=\"点击查看八万人体育场周边的酒店\">八万人体育场</i></a><span class=\"split\"></span>\n" +
                "                                            \n" +
                "                                            <i>\n" +
                "                                                <i class=\"add\">龙华西路515号</i>\n" +
                "                                                <i class=\"map-btn\" data-events=\"click\">查看地图</i>\n" +
                "                                            </i>\n" +
                "                                            \n" +
                "                                        </p>\n" +
                "                                        <div class=\"icons-wrap clearfix\">\n" +
                "                                            <i title=\"免费WiFi\" class=\"icon-wifi-free\"></i><i title=\"免费停车场\" class=\"icon-park-free\"></i><i title=\"餐厅\" class=\"icon-diningRoom\"></i><i title=\"会议厅\" class=\"icon-meeting\"></i>\n" +
                "                                        </div>\n" +
                "                                    </div>\n" +
                "                                    <div class=\"sep\"></div>\n" +
                "                                    <div class=\"comment\">\n" +
                "                                        \n" +
                "                                         \t\t<a onclick=\"_tcTraObj._tcTrackEvent('search', '13', '/detail', '|*|pos:1|*|k:null|*|locPId:0|*|locCId:0|*|cityId:321|*|filter:|*|sort:0/0|*|pgPath:/hotel/list|*|resId:60201256|*|showPrice:219.0|*|resCH:elong|*|')\" rel=\"nofollow\" href=\"/HotelInfo-60201256.html#comments\" target=\"_blank\" class=\"comment-detail\">\n" +
                "                                                    <dl class=\"\">\n" +
                "                                                    \n" +
                "                                                        <dt class=\"bg\"><span class=\"recent-grade\">4.6</span>/5分<span class=\"recent-word\">超赞</span></dt>\n" +
                "                                                     \n" +
                "                                                    \n" +
                "                                                        <dd class=\"bg\"><span>10637</span>条点评</dd>\n" +
                "<!--                                                         <dd class=\"good-comment\"><span>98%</span>好评</dd> -->\n" +
                "                                                    \n" +
                "                                                    </dl>\n" +
                "                                                </a>\n" +
                "                                                \n" +
                "                                                <div class=\"rencent-book\">今日10人成功预订</div>\n" +
                "                                                                                            \t\n" +
                "                                    \t\n" +
                "                                    </div>\n" +
                "                                    <div class=\"sep\"></div>\n" +
                "                                    <div class=\"price-info chosen\">\n" +
                "                                        \n" +
                "                                        <a onclick=\"_tcTraObj._tcTrackEvent('search', '13', '/detail', '|*|pos:1|*|k:null|*|locPId:0|*|locCId:0|*|cityId:321|*|filter:|*|sort:0/0|*|pgPath:/hotel/list|*|resId:60201256|*|showPrice:219.0|*|resCH:elong|*|')\" href=\"/HotelInfo-60201256.html\" target=\"_blank\" class=\"a-price\">\n" +
                "                                            <p class=\"my-price \">\n" +
                "                                                \n" +
                "                                                \t\t<i class=\"fm-Yahei\">¥</i>219<span>起</span>\n" +
                "                                                \n" +
                "                                            </p>\n" +
                "                                        </a>\n" +
                "                                        <a onclick=\"_tcTraObj._tcTrackEvent('search', '13', '/detail', '|*|pos:1|*|k:null|*|locPId:0|*|locCId:0|*|cityId:321|*|filter:|*|sort:0/0|*|pgPath:/hotel/list|*|resId:60201256|*|showPrice:219.0|*|resCH:elong|*|')\" href=\"/HotelInfo-60201256.html\" target=\"_blank\" data-trace=\"|*|cityId:201|*|qId:18599962-f593-48c4-ae2e-f606cec9dbf5|*|st:city|*|sId:201|*|pos:0|*|hotelId:60201256|*|Tp:default|*|page_index:0|*|page_size:20|*|\" class=\"detail-btn fm-Yahei\">查看详情</a>\n" +
                "                                        <p class=\"favorite\" data-id=\"60201256\" title=\"点击收藏酒店\" data-events=\"click\">\n" +
                "                                            <i></i><span class=\"text\">收藏</span>\n" +
                "                                            <span class=\"more-fav\">收藏功能需<a href=\"\">登录</a>后才可以操作</span>\n" +
                "                                        </p>\n" +
                "                                    </div>\n" +
                "                                    <div class=\"sold-out\">\n" +
                "                                        售罄\n" +
                "                                    </div>\n" +
                "                                    <i></i>\n" +
                "                                </div>\n" +
                "                                <div class=\"more-map-info\">\n" +
                "                                    <div id=\"h_map_wrap_60201256\" class=\"mapwrapper\">\n" +
                "                                        <div class=\"h_map_wrap\">\n" +
                "                                            <div id=\"mapDiv_60201256\" class=\"mapdiv\"></div>\n" +
                "                                            <div class=\"h_map\">\n" +
                "                                                <div class=\"router-paner\">\n" +
                "                                                    <div class=\"r-search-box\">\n" +
                "                                                        <span class=\"revert-icon\"></span>\n" +
                "                                                        <div class=\"r-search-con\">\n" +
                "                                                            <div class=\"r-input r-start\">\n" +
                "                                                                <span class=\"clear-icon\"></span>\n" +
                "                                                                <input type=\"text\" maxlength=\"256\" placeholder=\"输入起点\" 　class=\"r-start-input\" id=\"txt_start_60201256\">\n" +
                "                                                            </div>\n" +
                "                                                            <div class=\"r-input r-end\">\n" +
                "                                                                <span class=\"clear-icon\"></span>\n" +
                "                                                                <input type=\"text\" maxlength=\"256\" placeholder=\"输入终点\" 　class=\"r-end-input\" id=\"txt_end_60201256\">\n" +
                "                                                            </div>\n" +
                "                                                            <div class=\"r-btn-box\">\n" +
                "                                                                <button class=\"r-search-btn\" data-hid=\"60201256\">查 询</button> <a href=\"javascript:void(0);\" class=\"r-clear-btn\">清除路线</a>\n" +
                "                                                            </div>\n" +
                "                                                        </div>\n" +
                "                                                    </div>\n" +
                "                                                </div>\n" +
                "                                                <div class=\"landmark\">\n" +
                "                                                </div>\n" +
                "                                                <div class=\"r-map-result none\">\n" +
                "                                                    <ul class=\"r-ways-tab\" data-hid=\"60201256\">\n" +
                "                                                        <li class=\"active\" data-type=\"bus\">公交</li>\n" +
                "                                                        <li data-type=\"car\">驾车</li>\n" +
                "                                                        <li data-type=\"foot\" class=\"last\">步行</li>\n" +
                "                                                    </ul>\n" +
                "                                                    <div class=\"r-result-con\" id=\"r_60201256_result\"></div>\n" +
                "                                                </div>\n" +
                "                                                <div class=\"h_map_lp\">\n" +
                "                                                </div>\n" +
                "                                                <div class=\"h_map_show\">\n" +
                "                                                    <span class=\"checked\"><i class=\"silder-map-checked-icon\"></i>显示周边酒店</span>\n" +
                "                                                </div>\n" +
                "                                            </div>\n" +
                "                                        </div>\n" +
                "                                        <div class=\"slide_up clearfix\">\n" +
                "                                            <span class=\"right on\" data-events=\"click\">收起 <i class=\"icon_sort_arrb2\"></i></span>\n" +
                "                                        </div>\n" +
                "                                    </div>\n" +
                "                                </div>\n" +
                "                                \n" +
                "                            ";
        Elements select = Jsoup.parse(html).select("div.clearfix div.info h3 p a");
        Element element = select.get(0);
        System.out.println(element.attr("href"));
        System.out.println(element.select("span.title").text());

        Element p = Jsoup.parse(html).select("div.clearfix div.info p.detail-info").get(0);
        String str1 = "";
        if (p.select("a") != null) {
            str1 = p.select("a i").text();
            str1 = "【" + str1 + "】";
        }
        String str2 = p.select("i.add").text();
        String hotelLocation = str1 + str2;
        System.out.println(hotelLocation);

        Element a = Jsoup.parse(html).select("div.clearfix div.price-info a").get(0);
        String tex = a.select("p.my-price").text();
        String digits = StringUtils.getDigits(tex);
        System.out.println(digits);
        if (StringUtils.getDigits("asdasd").equals("")) {
            System.out.println("true");
        }

        html = "" +
                "<div class=\"info_r\">\n" +
                "    <div class=\"st-pop\" id=\"st-pop\" style=\"display: none; opacity: 0; top: 197.6px; left: 74.15px;\">\n" +
                "        <s class=\"st-arr\"><i></i></s>\n" +
                "        <div class=\"st-con\">同程保证提供的信息真实有效，如出现页面描述与景区官方信息不符，提供有效凭证后，平台将承担不低于订单金额30%补偿，可在出游日期起三天内至客户端订单详情页提交服务保障赔付申请。</div>\n" +
                "    </div>\n" +
                "    <h3 class=\"s_name\">\n" +
                "    \n" +
                "    上海杜莎夫人蜡像馆\n" +
                "    \n" +
                "            <span>AAAA景区</span>\n" +
                "        \n" +
                "    </h3>\n" +
                "    <div class=\"s_des clearfix\">\n" +
                "        \n" +
                "            <p class=\"des\">来一次和明星近距离的接触</p>\n" +
                "            <div class=\"label\">\n" +
                "                  \n" +
                "            </div>\n" +
                "        \n" +
                "    </div>\n" +
                "\n" +
                "    \n" +
                "            <p class=\"s_com\"><em>景点地址：</em>\n" +
                "            <span>上海市黄浦区南京西路2-68号新世界城10楼（近西藏中路）\n" +
                "            \n" +
                "                <a class=\"markIcon\" markid=\"traffic_route\" href=\"javascript:;\"><i></i>地图</a>\n" +
                "            \n" +
                "            </span></p> \n" +
                "        \n" +
                "                <p class=\"s_com open_time canhover\"><em>开放时间：</em>\n" +
                "                    \n" +
                "                    <span>10:00-21：00 ，20:00 停止<i></i></span>    \n" +
                "                    \n" +
                "                </p>\n" +
                "                <div class=\"s-tShow mt10\" style=\"display: none;\">\n" +
                "                    <pre>10:00-21：00 ，20:00 停止售票</pre>\n" +
                "                </div>\n" +
                "            \n" +
                "\n" +
                "        \n" +
                "                <p class=\"s_com service_w\"><em>服务保障：</em>\n" +
                "                    <span>\n" +
                "                         <i class=\"s_tag sup-rsms\">如实描述</i>\n" +
                "                    \n" +
                "                        <i class=\"s_tag sup-ensu\">入园保障</i>\n" +
                "                    \n" +
                "                    </span>\n" +
                "                </p>\n" +
                "            \n" +
                "    \n" +
                "    <div class=\"s_comment\"><div class=\"s_comment_i\"><em>精彩点评：</em>价格优惠，服务超好，同程网订票是我的不二选择，太开心了，下次继续在这里订票，景区环境不错，非常满意的一次体验...<a markid=\"tour_comment\" href=\"javascript:;\">更多</a>\n" +
                "                    </div></div>\n" +
                "\n" +
                "    <div class=\"s_com_more\"><p><i></i>10717条用户点评 <a markid=\"tour_comment\" href=\"javascript:;\">查看</a></p></div>\n" +
                "    \n" +
                "            <div class=\"s_price\">                   \n" +
                "                <div class=\"s_p_t\"><span>¥<b>130</b></span>起</div>\n" +
                "                <span class=\"s_p_b\">票面价：<s>¥140</s>\n" +
                "                \n" +
                "                    <i>9.3折起</i>\n" +
                "                                 \n" +
                "                </span>\n" +
                "            </div>\n" +
                "        \n" +
                "</div>";
        Element div = Jsoup.parse(html).select("div.info_r").get(0);
        System.out.println(div.select("h3.s_name").text());
    }

    @Test
    public void GroupTest() {
//        String html = "<div class=\"list_product_box js_product_item\" data-track-product-id=\"10424264\" data-track-is-recommend=\"false\" data-track-inner-pos=\"13\"><div class=\"list_product_item_border\"><div class=\"list_product_item\"><div class=\"list_product_left\"><img class=\"list_product_pic\" src=\"//dimg04.c-ctrip.com/images/300u0m000000dxl2x35F8_C_200_150.jpg\" alt=\"上海+迪士尼（Disney）3日2晚半自助游(5钻)·2晚毗邻迪士尼5钻酒店 『迪士尼优选 7大园区畅游』酒店班车接送迪士尼  可升级【东方明珠+360景观自助餐】多交通可选\" style=\"width: 200px; height: 150px; opacity: 1;\"><p class=\"list_product_tip\"><span class=\"list_product_name\">半自助游</span><i class=\"list_product_heng\"></i><span class=\"list_product_place\">南京出发</span></p></div><div class=\"list_product_right\"><p class=\"list_product_title\" title=\"上海+迪士尼（Disney）3日2晚半自助游·2晚毗邻迪士尼5钻酒店 『迪士尼优选 7大园区畅游』酒店班车接送迪士尼  可升级【东方明珠+360景观自助餐】多交通可选\"><span>上海+迪士尼（Disney）3日2晚半自助游·2晚毗邻迪士尼5钻酒店 『迪士尼优选 7大园区畅游』酒店班车接送迪士尼  可升级【东方明珠+360景观自助餐】多交通可选</span></p><div class=\"list_product_content basefix\"><div class=\"list_content_right\"><div class=\"list_change_box basefix list_change_box_one\"><div class=\"list_change_left\"></div><div class=\"list_change_right\"><div class=\"list_change_one\"></div><div class=\"list_change_two\">累计3人出游</div></div></div><div class=\"list_sr_price_box basefix\"><span class=\"list_sr_price\"><dfn>￥</dfn><strong>1035</strong>起</span></div></div><div class=\"list_content_left\"><div class=\"list_label_box\"><div style=\"display: inline-block;\"><span class=\"list_label_jewel\">5钻</span></div><span class=\"list_label_blue\" title=\"\">无购物</span><span class=\"list_label_blue\" title=\"\">无自费</span><span class=\"list_label_blue\" title=\"订单一经携程旅行网以书面形式确认后均默认发团（不可抗力除外）\">成团保障</span><span class=\"list_label_blue\" title=\"\">游乐园</span><span class=\"list_label_blue\" title=\"\">迪士尼乐园</span></div><div><div class=\"list_explan_text_box\" style=\"position: relative;\"><p class=\"list_explan_text\">一条龙服务：24小时电话管家，为您保驾护航，安心出行！</p></div></div><div class=\"list_product_schedule\"><p>班期：1/4,1/5,1/6等可订<a href=\"javascript:void(0);\" class=\"list_sea_schedule\">查看班期<b class=\"list_down\"></b></a></p></div><p class=\"list_product_retail\" title=\"南京万达国际旅行社有限公司\">供应商：万达旅业</p></div></div></div></div></div></div>";
//        System.out.println(Jsoup.parse(html).getElementsByTag("div").attr("data-track-product-id"));
//        html="<div class=\"list_paging_box basefix\"><span class=\"list_paging_left \"><i></i></span><span class=\"list_paging_text\"><em>43</em>/<!-- -->43</span><span class=\"list_paging_right gray_cur\"><i></i></span></div>";
//        Document parse = Jsoup.parse(html);
//        Element span = parse.select("span.list_paging_text").get(0);
//        System.out.println(span.select("em").text());
//        System.out.println(span.text());
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        Date nowDate= new Date();
        String dateString= s.format(nowDate);
        System.out.println(dateString);
        String str="累计1.02万人出游";
        float price= Float.parseFloat(str.substring(2,str.indexOf("万")));
        int p= (int) (price*10000);
        System.out.println(p);
    }

    @Test
    public void testDate(){
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date());
        String year=calendar.get(Calendar.YEAR)+"";
        int month=calendar.get(Calendar.MONTH)+1;
        System.out.println(year);
        System.out.println(month);

        String html="<table class=\"d_calendar_table\">\n" +
                " <tbody>\n" +
                "  <tr>\n" +
                "   <td class=\"\"></td>\n" +
                "   <td class=\"\"></td>\n" +
                "   <td class=\"\"></td>\n" +
                "   <td class=\"holiday_date\"><a class=\" no_data\" href=\"javascript:void(0);\"><span class=\"festival_name\">元旦</span><span class=\"date basefix\">01</span></a></td>\n" +
                "   <td class=\"\"><a class=\" no_data\" href=\"javascript:void(0);\"><span class=\"work_day\"></span><span class=\"date basefix\">今天</span></a></td>\n" +
                "   <td class=\"\"><a class=\" no_data\" href=\"javascript:void(0);\"><span class=\"work_day\"></span><span class=\"date basefix\">03</span></a></td>\n" +
                "   <td class=\"\"><a class=\" no_data\" href=\"javascript:void(0);\"><span class=\"work_day\"></span><span class=\"date basefix\">04</span></a></td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "   <td class=\"\"><a class=\" no_data\" href=\"javascript:void(0);\"><span class=\"work_day\"></span><span class=\"date basefix\">05</span></a></td>\n" +
                "   <td class=\"\"><a class=\" no_data\" href=\"javascript:void(0);\"><span class=\"work_day\"></span><span class=\"date basefix\">06</span></a></td>\n" +
                "   <td class=\"\"><a class=\" no_data\" href=\"javascript:void(0);\"><span class=\"work_day\"></span><span class=\"date basefix\">07</span></a></td>\n" +
                "   <td class=\"\"><a class=\" no_data\" href=\"javascript:void(0);\"><span class=\"work_day\"></span><span class=\"date basefix\">08</span></a></td>\n" +
                "   <td class=\"\"><a class=\" no_data\" href=\"javascript:void(0);\"><span class=\"work_day\"></span><span class=\"date basefix\">09</span></a></td>\n" +
                "   <td class=\"\"><a class=\" no_data\" href=\"javascript:void(0);\"><span class=\"work_day\"></span><span class=\"date basefix\">10</span></a></td>\n" +
                "   <td class=\"\"><a class=\" no_data\" href=\"javascript:void(0);\"><span class=\"work_day\"></span><span class=\"date basefix\">11</span></a></td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "   <td class=\"\"><a class=\" no_data\" href=\"javascript:void(0);\"><span class=\"work_day\"></span><span class=\"date basefix\">12</span></a></td>\n" +
                "   <td class=\"\"><a class=\" no_data\" href=\"javascript:void(0);\"><span class=\"work_day\"></span><span class=\"date basefix\">13</span></a></td>\n" +
                "   <td class=\"\"><a class=\" no_data\" href=\"javascript:void(0);\"><span class=\"work_day\"></span><span class=\"date basefix\">14</span></a></td>\n" +
                "   <td class=\"\"><a class=\" no_data\" href=\"javascript:void(0);\"><span class=\"work_day\"></span><span class=\"date basefix\">15</span></a></td>\n" +
                "   <td class=\"\"><a class=\" no_data\" href=\"javascript:void(0);\"><span class=\"work_day\"></span><span class=\"date basefix\">16</span></a></td>\n" +
                "   <td class=\"\"><a class=\" no_data\" href=\"javascript:void(0);\"><span class=\"work_day\"></span><span class=\"date basefix\">17</span></a></td>\n" +
                "   <td class=\"\">\n" +
                "    <div>\n" +
                "     <a class=\"\" href=\"javascript:void(0);\"><span class=\"work_day\"></span><span class=\"date basefix\">18</span><span class=\"calendar_price01 red_color\"><dfn>¥</dfn>1299<em>起</em></span></a>\n" +
                "    </div></td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "   <td class=\"\"><a class=\" no_data\" href=\"javascript:void(0);\"><span class=\"work_day\">班</span><span class=\"date basefix\">19</span></a></td>\n" +
                "   <td class=\"\"><a class=\" no_data\" href=\"javascript:void(0);\"><span class=\"work_day\"></span><span class=\"date basefix\">20</span></a></td>\n" +
                "   <td class=\"\"><a class=\" no_data\" href=\"javascript:void(0);\"><span class=\"work_day\"></span><span class=\"date basefix\">21</span></a></td>\n" +
                "   <td class=\"\"><a class=\" no_data\" href=\"javascript:void(0);\"><span class=\"work_day\"></span><span class=\"date basefix\">22</span></a></td>\n" +
                "   <td class=\"\"><a class=\" no_data\" href=\"javascript:void(0);\"><span class=\"work_day\"></span><span class=\"date basefix\">23</span></a></td>\n" +
                "   <td class=\"holiday_date\">\n" +
                "    <div>\n" +
                "     <a class=\"\" href=\"javascript:void(0);\"><span class=\"festival_name\">除夕</span><span class=\"date basefix\">24</span><span class=\"calendar_price01\"><dfn>¥</dfn>1560<em>起</em></span></a>\n" +
                "    </div></td>\n" +
                "   <td class=\"holiday_date\">\n" +
                "    <div>\n" +
                "     <a class=\"\" href=\"javascript:void(0);\"><span class=\"festival_name\">春节</span><span class=\"date basefix\">25</span><span class=\"calendar_price01\"><dfn>¥</dfn>1560<em>起</em></span><span class=\"green_color\">充足</span></a>\n" +
                "    </div></td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "   <td class=\"\">\n" +
                "    <div>\n" +
                "     <a class=\"\" href=\"javascript:void(0);\"><span class=\"work_day\">初二</span><span class=\"date basefix\">26</span><span class=\"calendar_price01\"><dfn>¥</dfn>1560<em>起</em></span><span class=\"green_color\">充足</span></a>\n" +
                "    </div></td>\n" +
                "   <td class=\"\">\n" +
                "    <div>\n" +
                "     <a class=\"\" href=\"javascript:void(0);\"><span class=\"work_day\">初三</span><span class=\"date basefix\">27</span><span class=\"calendar_price01\"><dfn>¥</dfn>1560<em>起</em></span></a>\n" +
                "    </div></td>\n" +
                "   <td class=\"holiday_date\">\n" +
                "    <div>\n" +
                "     <a class=\"\" href=\"javascript:void(0);\"><span class=\"festival_name\">休</span><span class=\"date basefix\">28</span><span class=\"calendar_price01 red_color\"><dfn>¥</dfn>1299<em>起</em></span></a>\n" +
                "    </div></td>\n" +
                "   <td class=\"holiday_date\">\n" +
                "    <div>\n" +
                "     <a class=\"\" href=\"javascript:void(0);\"><span class=\"festival_name\">休</span><span class=\"date basefix\">29</span><span class=\"calendar_price01 red_color\"><dfn>¥</dfn>1299<em>起</em></span></a>\n" +
                "    </div></td>\n" +
                "   <td class=\"holiday_date\">\n" +
                "    <div>\n" +
                "     <a class=\"\" href=\"javascript:void(0);\"><span class=\"festival_name\">休</span><span class=\"date basefix\">30</span><span class=\"calendar_price01 red_color\"><dfn>¥</dfn>1299<em>起</em></span></a>\n" +
                "    </div></td>\n" +
                "   <td class=\"\">\n" +
                "    <div>\n" +
                "     <a class=\"\" href=\"javascript:void(0);\"><span class=\"work_day\"></span><span class=\"date basefix\">31</span><span class=\"calendar_price01 red_color\"><dfn>¥</dfn>1299<em>起</em></span></a>\n" +
                "    </div></td>\n" +
                "   <td class=\"\"></td>\n" +
                "  </tr>\n" +
                " </tbody>\n" +
                "</table>";
        Elements select = Jsoup.parse(html).select("tbody tr");
        for (int i=0;i<select.size();i++){
            System.out.println(select.get(i));
        }
    }

    @Test
    public void testStr(){
        String str="https://so.ly.com/gny-gentuan?q=%E4%B8%BD%E6%B1%9F&sopage=sogny&prop=1&start=21";
        System.out.println(str.substring(0, str.indexOf("&start=")));
        System.out.println(StringUtils.getDigits(str.substring(str.indexOf("&start="), str.length())));
        String html="<td class=\"invalid-day festival\" data-date=\"2020-01-01\"><span class=\"date\">元旦</span>\n" +
                "            <input type=\"hidden\" class=\"version_list\" value=\"undefined\">\n" +
                "            <div class=\"info J-Info\" data-version=\"\">\n" +
                "                <span class=\"dataprace \"><span>&nbsp;</span></span>\n" +
                "                <span class=\"dataprice\"></span>\n" +
                "            </div>\n" +
                "\n" +
                "<div class=\"warnInfo\" hidden=\"\">\n" +
                "    <div>\n" +
                "        \n" +
                "        <dl class=\"warn\">\n" +
                "            <dt class=\"desc\">.</dt>\n" +
                "            <dd>本起价对应的交通为实时交通，预订过程中可能会发生价格或位置的变更，请以占位价格为准；</dd>\n" +
                "        </dl>\n" +
                "        \n" +
                "        \n" +
                "    </div>\n" +
                "</div>\n" +
                "</td>";
        int i1 = html.indexOf("data-date");
        String time=html.substring(i1+11,i1+21);
        System.out.println(time);
    }
}

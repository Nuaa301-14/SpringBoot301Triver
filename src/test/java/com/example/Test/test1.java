package com.example.Test;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.junit.Test;

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
    public void test4(){
        String html="<html>\n" +
                " <head></head>\n" +
                " <body>\n" +
                "  <input type=\"text\" class=\"input_txt input_txtShort\" value=\"北京\" _lastvalue=\"北京\" autocomplete=\"on\" maxlength=\"100\" name=\"cityName\" id=\"txtCity\" _cqnotice=\"中文/拼音/英文\">\n" +
                " </body>\n" +
                "</html>";
        System.out.println(Jsoup.parse(html).getElementsByTag("input").attr("value"));
    }
    @Test
    public void test(){
        String score="4.6 风";
        System.out.println(score.substring(0, score.indexOf(" ")));
        System.out.println(StringUtils.getDigits(score));
        String test="<div class=\"list_product_box js_product_item\" data-track-product-id=\"21530022\" data-track-is-recommend=\"false\" data-track-inner-pos=\"0\">\n" +
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
        test="【上海·豫园地区】";
        System.out.println(test.substring(1, test.length() - 1));
    }
}

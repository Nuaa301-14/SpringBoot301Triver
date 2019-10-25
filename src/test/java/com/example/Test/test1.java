package com.example.Test;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.junit.Test;

public class test1 {
    @Test
    public void test1(){
        String name="123456789!";
        String[] s = name.split(" ");
        System.out.println(s.length);
        System.out.println(name.substring(2, name.length()));
    }

    @Test
    public void test2(){
        String html="<li class=\"hotel_price_icon\"><div class=\"J_Price_6874402\" hotel=\"6874402\" data-data=\"zAShEDAupMuY1tRH6IocMbHQjP7r3vUj7UlYNFmaZeMKhHgvny19iAGqT1q9ATu6Yqa7GONHcX3UaX/uPZP7qpCNx2rkXYH0lvB6Gyl9xKOKpvlLQWhc01aMFKdWotKv5Fg/1ac38omt/1UiRElST+OzBQRv6cBMSLifW82ewdTpZ3kfaID7OsArb4SOpbX8kVQ/JkOi7YuzdK6pJt5+llbk2Ob0JF3HNZEr0eJmyUdn3Y7bBVbT1XBOKhDvo+lVObAKdH1v/Nsq1svKTrClASEhcFZVLGLuTaBgtL33Aik8Wf89rKOmsg/XXFS3OIijsY2Z9NZzszCDZznSCNzsvSc71nnvThecAWd+8+pSbPdl0tQU0xGFGg3HPApFhUPLDMy5Kqay/duaYyijVJMht6DKp4bsW6qwh8HMJlYHWYo=\"><div><div class=\"hotel_price \" data-id=\"6874402\"> <dfn>¥</dfn><a href=\"https://hotels.ctrip.com/hotel/6874402.html?isFull=F&amp;checkIn=2019-10-25&amp;checkOut=2019-10-26&amp;masterhotelid=6874402&amp;hcityid=2#ctm_ref=hod_sr_rec_dl_n_1_25\" data-dopost=\"T\" data-ctm=\"#ctm_ref=hod_sr_rec_dl_n_1_25\" target=\"_blank\" data-href=\"https://hotels.ctrip.com/hotel/6874402.html?isFull=F\"><span class=\"J_price_lowList\">298</span></a>起<br></div></div></div><div class=\"original_price\"></div><div class=\"original_price\"><span title=\"可礼品卡支付\" class=\"giftcard_available\" data-role=\"title\"><i></i>可礼品卡支付</span></div><div class=\"action_info\"><p class=\"detail_btn\" data-id=\"6874402\"><a class=\"btn_buy\" target=\"_blank\" data-dopost=\"T\" href=\"/hotel/6874402.html?isFull=F#ctm_ref=hod_sr_lst_dl_n_1_25\">查看详情</a></p></div></li>";
        System.out.println(Integer.parseInt(Jsoup.parse(html).select("li.hotel_price_icon div.hotel_price span").text()));
        System.out.println(Jsoup.parse(html).select("li.hotel_price_icon div.action_info a").attr("href"));
    }
    @Test
    public void test3(){
        String name="/hotel/macau59";
        System.out.println(StringUtils.getDigits(name));

        name="<a title=\"澳门酒店\" href=\"/hotel/macau59\">澳门</a>";
        System.out.println(Jsoup.parse(name).select("a").attr("href"));

    }
}

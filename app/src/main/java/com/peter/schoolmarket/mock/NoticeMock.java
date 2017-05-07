package com.peter.schoolmarket.mock;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.peter.schoolmarket.data.dto.Result;
import com.peter.schoolmarket.data.pojo.Notice;

import java.util.List;

/**
 * Created by PetterChen on 2017/5/5.
 */

public class NoticeMock {
    public Result<List<Notice>> getNotices(){
        String jsonStr="[\n" +
                "  {\n" +
                "    \"id\": \"N001\",\n" +
                "    \"authorImg\": \"http://opeuvb611.bkt.clouddn.com/sort_avater_cluo.jpg\",\n" +
                "    \"authorName\": \"tom\",\n" +
                "    \"title\": \"失物招领\",\n" +
                "    \"authorPhone\": \"18202429136\",\n" +
                "    \"content\": \"有同学于4月5号在食堂拾到钱包一个，里面有物品若干，请失主尽快到校广播站认领。\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"N002\",\n" +
                "    \"authorImg\": \"http://opeuvb611.bkt.clouddn.com/sort_clothes.jpeg\",\n" +
                "    \"authorName\": \"peter\",\n" +
                "    \"title\": \"考试通知\",\n" +
                "    \"authorPhone\": \"18202429136\",\n" +
                "    \"content\": \"计算机专业的数据结构于5月7号下午2点在建筑学馆527教室进行考试，请相关同学做好考试准备。\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"N003\",\n" +
                "    \"authorImg\": \"http://opeuvb611.bkt.clouddn.com/sort_body.jpg\",\n" +
                "    \"authorName\": \"nice\",\n" +
                "    \"title\": \"征集看电影小伙伴\",\n" +
                "    \"authorPhone\": \"18202429136\",\n" +
                "    \"content\": \"征集两个小伙伴于5月7号一起去本部电影城观看《速度与激情7》，感兴趣的请联系：18202429136\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"N004\",\n" +
                "    \"authorImg\": \"http://opeuvb611.bkt.clouddn.com/sort_avater_cluo.jpg\",\n" +
                "    \"authorName\": \"tom\",\n" +
                "    \"title\": \"失物招领\",\n" +
                "    \"authorPhone\": \"18202429136\",\n" +
                "    \"content\": \"有同学于4月5号在食堂拾到钱包一个，里面有物品若干，请失主尽快到校广播站认领。\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"N005\",\n" +
                "    \"authorImg\": \"http://opeuvb611.bkt.clouddn.com/sort_clothes.jpeg\",\n" +
                "    \"authorName\": \"nice\",\n" +
                "    \"title\": \"考试通知\",\n" +
                "    \"authorPhone\": \"18202429136\",\n" +
                "    \"content\": \"计算机专业的数据结构于5月7号下午2点在建筑学馆527教室进行考试，请相关同学做好考试准备。\"\n" +
                "  }\n" +
                "]";
        List<Notice> notices= new Gson().fromJson(jsonStr, new TypeToken<List<Notice>>(){}.getType());
        Result<List<Notice>> result=new Result<>();
        result.setCode(100);
        result.setMsg("");
        result.setData(notices);
        return result;
    }

    public Result<String> getReleaseNoticeResult() {
        String json="{\"code\":100,\"msg\":\"操作成功\"}";
        return new Gson().fromJson(json,  new TypeToken<Result<String>>(){}.getType());
    }
}

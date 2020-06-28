package com.zrjk.MonsterZrjk.Common.Utils;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenUtil {

    //token密钥
    @Value("${tokenKey}")
    private  String key;


    //token头部信息（存放加密token的算法）
    private static JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS256);

    /**
     * 创建token
     * @param tokenMap
     * @return
     */
    public  String createToken(Map<String ,Object> tokenMap){
        String token="";
        //创建JWSObject  用于存放需要放入token中的信息
        JWSObject jwsObject = new JWSObject(jwsHeader,new Payload(new JSONObject(tokenMap)));
        //将token进行签名（将我们放入token的信息，通过加密算法加密  这个过程 叫签名）
        try {
            jwsObject.sign(new MACSigner(key.getBytes()));
            //提取加密好的token字符串
             token = jwsObject.serialize();
        } catch (JOSEException e) {
            e.printStackTrace();
        }
        return token;
    }

    /**
     * 提取token中存的信息
     * @param token
     * @return
     */
    public Map<String,Object> checkToken(String token){
        Map<String,Object> resultMap = new HashMap<>();
        try {
            //通过token字符串获取jwsObject
            JWSObject jwsObject = JWSObject.parse(token);
            //通过jwsObject获取token中的信息payload
            Payload payload = jwsObject.getPayload();
            //创建token验证类对象
            JWSVerifier jwsVerifier = new MACVerifier(key.getBytes());
            //验证token
            if(jwsObject.verify(jwsVerifier)){
                //验证通过   token格式合法  校验token是否过期
                JSONObject jsonObject = payload.toJSONObject();
                //获取到期时间
                    Long endTime = Long.valueOf(jsonObject.get("endTime").toString());
                //校验时间
                if(new Date().getTime()>=endTime){
                    resultMap.put("state","过期了");
                }else {
                    resultMap.put("state","还没过期");
                    resultMap.put("data",jsonObject);
                }
            }else {
                //token格式错误
                resultMap.put("state","格式错误");
            }
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getUidFromToken(String token){
        //从token中获取用户id
        Map<String, Object> map = checkToken(token);
        //因为过滤器已经判断过token是否过期  这里不用再判断  直接取值
        Map<String,Object> tokenMap = (Map<String, Object>) map.get("data");
        String accountId = (String) tokenMap.get("uid");

        return accountId;
    }
}

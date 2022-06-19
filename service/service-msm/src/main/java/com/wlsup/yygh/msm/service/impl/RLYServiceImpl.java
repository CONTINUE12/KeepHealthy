package com.wlsup.yygh.msm.service.impl;

import com.wlsup.yygh.msm.service.RLYService;
import com.wlsup.yygh.vo.msm.MsmVo;
import com.cloopen.rest.sdk.BodyType;
import com.cloopen.rest.sdk.CCPRestSmsSDK;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author JX
 * @date 2021/8/29 - 14:44
 * @content
 */
@Service
public class RLYServiceImpl implements RLYService {
  @Override
  public boolean send(String phone, String code) {
    //判断手机号是否为空
    if(StringUtils.isEmpty(phone)) {
      return false;
    }
    //整合容联云短信服务
    //生产环境请求地址：app.cloopen.com  固定
    String serverIp = "app.cloopen.com";
    //请求端口  固定
    String serverPort = "8883";
    //主账号,登陆云通讯网站后,可在控制台首页看到开发者主账号ACCOUNT SID和主账号令牌AUTH TOKEN
    String accountSId = "8a216da87e7baef8017ea9e522970695";
    String accountToken = "52473bd1530747cc923117cc43b2b39e";
    //请使用管理控制台中已创建应用的APPID
    String appId = "8a216da87e7baef8017ea9e52389069c";
    System.out.println("=====================");
    CCPRestSmsSDK sdk = new CCPRestSmsSDK();
    sdk.init(serverIp, serverPort);
    sdk.setAccount(accountSId, accountToken);
    sdk.setAppId(appId);
    sdk.setBodyType(BodyType.Type_JSON);

    String to = phone; //短信要发送给的人的号码

    String templateId= "1"; //使用默认的短信模板，填1。
    /*使用1号短信模板的内容是：
     * 【云通讯】您使用的是运通讯模板，您的验证码是[1]，
     * 请于[2]分分钟内正确输入
     * */

    String[] datas = {code,"2"}; //datas数组里面填信息，会占位到短信的内容的[1] [2]去
    /*使用1号短信模板，假设code是723834，短信内容变为：
    *
    * 【云通讯】您使用的是运通讯模板，您的验证码是723834，
    * 请于2分分钟内正确输入
    * */

    HashMap<String, Object> result = sdk.sendTemplateSMS(to,templateId,datas);

    System.out.println("SDKTestGetSubAccounts result=" + result);

    if("000000".equals(result.get("statusCode"))){
      //正常返回输出data包体信息（map） 我们打印出来看看
      HashMap<String,Object> data = (HashMap<String, Object>) result.get("data");
      Set<String> keySet = data.keySet();
      for(String key:keySet){
        Object object = data.get(key);
        System.out.println(key +" = "+object);
      }
    }else{
      //异常返回输出错误码和错误信息
      System.out.println("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
      return false;
    }
    return true;
  }

  @Override
  public boolean send(MsmVo msmVo) {

    //手机号不为空且就医提醒不为空，走提醒就医的短信模板！
    if(!StringUtils.isEmpty(msmVo.getPhone()) && !StringUtils.isEmpty(msmVo.getParam().get("jiuyitixing"))) {
      return this.send02(msmVo.getPhone(),msmVo.getParam());
    }

    //手机号不为空，进行发送，这个走的是预约订单成功的短信模板
    if(!StringUtils.isEmpty(msmVo.getPhone())) {
      return this.send01(msmVo.getPhone(),msmVo.getParam());
    }

    return false;
  }



  public boolean send01(String phone, Map<String,Object> param) {
    //判断手机号是否为空
    if(StringUtils.isEmpty(phone)) {
      return false;
    }
    //整合容联云短信服务
    //生产环境请求地址：app.cloopen.com  固定
    String serverIp = "app.cloopen.com";
    //请求端口  固定
    String serverPort = "8883";
    //主账号,登陆云通讯网站后,可在控制台首页看到开发者主账号ACCOUNT SID和主账号令牌AUTH TOKEN
    String accountSId = "8a216da87e7baef8017ea9e522970695";
    String accountToken = "52473bd1530747cc923117cc43b2b39e";
    //请使用管理控制台中已创建应用的APPID
    String appId = "8a216da87e7baef8017ea9e52389069c";
    CCPRestSmsSDK sdk = new CCPRestSmsSDK();
    sdk.init(serverIp, serverPort);
    sdk.setAccount(accountSId, accountToken);
    sdk.setAppId(appId);
    sdk.setBodyType(BodyType.Type_JSON);

    String to = phone; //短信要发送给的人的号码

    String templateId= "1"; //使用默认的短信模板，填1。
    /*使用1号短信模板的内容是：
     * 【云通讯】您使用的是运通讯模板，您的验证码是[1]，
     * 请于[2]分分钟内正确输入
     * */
    String code = "预约成功"+param.get("name");
    String data01 = "预约日期是"+param.get("reserveDate");

    //由于我们不能公司认证自定义短信模板，所以将就使用登录模板！
    String[] datas = {code,data01};
    /*短信内容变为：
     * 【云通讯】您使用的是运通讯模板，您的验证码是你已经预约成功，
     * 请于预约日期是2021-09-06分钟内正确输入
     * */

    HashMap<String, Object> result = sdk.sendTemplateSMS(to,templateId,datas);

    System.out.println("SDKTestGetSubAccounts result=" + result);

    if("000000".equals(result.get("statusCode"))){
      //正常返回输出data包体信息（map） 我们打印出来看看
      HashMap<String,Object> data = (HashMap<String, Object>) result.get("data");
      Set<String> keySet = data.keySet();
      for(String key:keySet){
        Object object = data.get(key);
        System.out.println(key +" = "+object);
      }
    }else{
      //异常返回输出错误码和错误信息
      System.out.println("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
      return false;
    }
    return true;
  }


  public boolean send02(String phone, Map<String,Object> param) {
    //判断手机号是否为空
    if(StringUtils.isEmpty(phone)) {
      return false;
    }
    //整合容联云短信服务
    //生产环境请求地址：app.cloopen.com  固定
    String serverIp = "app.cloopen.com";
    //请求端口  固定
    String serverPort = "8883";
    //主账号,登陆云通讯网站后,可在控制台首页看到开发者主账号ACCOUNT SID和主账号令牌AUTH TOKEN
    String accountSId = "8a216da87e7baef8017ea9e522970695";
    String accountToken = "52473bd1530747cc923117cc43b2b39e";
    //请使用管理控制台中已创建应用的APPID
    String appId = "8a216da87e7baef8017ea9e52389069c";
    CCPRestSmsSDK sdk = new CCPRestSmsSDK();
    sdk.init(serverIp, serverPort);
    sdk.setAccount(accountSId, accountToken);
    sdk.setAppId(appId);
    sdk.setBodyType(BodyType.Type_JSON);

    String to = phone; //短信要发送给的人的号码

    String templateId= "1"; //使用默认的短信模板，填1。
    /*使用1号短信模板的内容是：
     * 【云通讯】您使用的是运通讯模板，您的验证码是[1]，
     * 请于[2]分分钟内正确输入
     * */
    String code = param.get("name")+"您好，快来医院接受治疗哦！！！";
    String data01 = "您的预约日期是"+param.get("reserveDate");

    //由于我们不能公司认证自定义短信模板，所以将就使用登录模板！
    String[] datas = {code,data01};
    /*短信内容变为：
     * 【云通讯】您使用的是运通讯模板，您的验证码是xxx您好，快来医院接受治疗哦！！！，
     * 请于您的预约日期是2021-09-07分钟内正确输入
     * */

    HashMap<String, Object> result = sdk.sendTemplateSMS(to,templateId,datas);

    System.out.println("SDKTestGetSubAccounts result=" + result);

    if("000000".equals(result.get("statusCode"))){
      //正常返回输出data包体信息（map） 我们打印出来看看
      HashMap<String,Object> data = (HashMap<String, Object>) result.get("data");
      Set<String> keySet = data.keySet();
      for(String key:keySet){
        Object object = data.get(key);
        System.out.println(key +" = "+object);
      }
    }else{
      //异常返回输出错误码和错误信息
      System.out.println("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
      return false;
    }
    return true;
  }

}

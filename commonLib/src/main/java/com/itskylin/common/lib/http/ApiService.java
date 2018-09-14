package com.itskylin.common.lib.http;

import java.io.Serializable;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * API
 *
 * @author Sky Lin
 * @version V1.0
 * @Package git2svn/com.konying.common.http
 * @Description:
 * @email admin@itksylin.com
 * @date 2018/6/27 09:59
 */
@SuppressWarnings("all")
public interface ApiService {
    /**
     * 告警服务接口类名
     */
    String mServiceClassName = "Services";

    /**
     * 登录接口
     */
    String mIUserLogon = "Account/PDALogin";
    /**
     * 更新apk接口
     */
    String updateApp = "api/ionew/GetAppVersion";
    /**
     * 获取区域ID
     */
    String AreaID = "api/io/GetAreaIDInfo";

    /**
     * 获取手动核药信息接口
     */
    String NurseCode = "api/ionew/SuffList";
    /**
     * 获取正在执行药组信息接口
     */
    String NurseCodeNew = "api/ionew/GetRunDrug";

    /**
     * 获取输液列表接口
     */
    String GetNurseList = "api/ionew/GetList";
    /**
     * 设置PDA声音开关接口
     */
    String SetPDAVoice = "api/ionew/PDAVoice";

    /**
     * 上下限
     */
    String NurseCodeMake = "api/io/speedBinding";
    /**
     * 暂停开关接口
     */
    String NurseCodeSwitch = "api/io/StateOperation";
    /**
     * 关注置顶接口
     */
    String NurseCodeTop = "api/ionew/SufferCare";
    /**
     * 获取绑定的列表接口
     */
    String NurseCodeBind = "api/ionew/ReplacementEquipment";
    /**
     * 单绑定接口
     */
    String NurseCodeBindDan = "api/ionew/binding";
    /**
     * 单解绑接口
     */
    String NurseCodeunBindDan = "api/ionew/Unbunding";

    /**
     * 修改容量接口
     */
    String NurseCodeVolume = "api/io/EditCapacity";

    /**
     * 获取输液管类型
     */
    String GetPipeType = "api/io/GetPipeTypeData";

    /**
     * 设置病人输液管类型接口
     */
    String SetPipeType = "api/io/SetPerCapacity";

    /**
     * 获取病人信息接口
     */
    String GetSeatSuffList = "api/io/GetAllSuffInfuData";

    /**
     * 获取病人及药组信息接口
     */
    String GetPatientDrugGroup = "api/io/GetSuffNotInfuData";

    /**
     * 设置病人开始输液接口
     */
    String StartPatientDrugGroup = "api/ionew/BindDrug";

    /**
     * 删除患者药组接口
     */
    String DelBindDrug = "api/ionew/DelBindDrug";

    /**
     * 设置声音接口
     */
    String SetVoice = "api/ionew/SetVoice";

    /**
     * 患者已绑定设备列表接口
     */
    String GetBinds = "api/ionew/GetBinds";

    /**
     * 输液设备参数设置接口
     */
    String SetDevice = "api/ionew/SetParas";

    /**
     * 输液完成接口
     */
    String InfusionOutLine = "api/ionew/InfusionComplete";
    /**
     * 获取公司名称接口
     */
    String GetCompanyName = "api/ionew/GetCompanyName";

    /**
     * 38.	获取区域列表
     *
     * @param url
     * @return
     */
    String GetSeatAreaList = "api/ionew/GetAreas";

    /**
     * 39.	门诊区域座位列表
     *
     * @param url
     * @return
     */
    String GetSeatList = "api/ionew/GetSeats";

    /**
     * 门诊待分配座位患者列表
     */
    String GetNoSeatSuffer = "api/ionew/GetNoSeatSuffer";
    /**
     * 门诊分配/解绑座位接口
     */
    String SetSufferSeat = "api/ionew/SetSufferSeat";
    /**
     * 门诊换座位接口
     */
    String ChangeSeat = "api/ionew/ChangeSeat";
    /**
     * 版本类型(1：门诊 其他：住院)
     */
    String GetVersionType = "api/ionew/GetVersionType";
    /**
     * 40.	门诊分配/解绑座位接口
     */
    String BindSeat = "api/ionew/SetSufferSeat";
    /**
     * 扫座位码查患者
     */
    String GetSufferBySeat = "api/ionew/GetSufferBySeat";

    /**
     * 自动解除座位
     */
    String UnbindSeats = "api/ionew/SeatClearSufferer";


    @GET
    Call<ResponseBody> executeGet(@Url String url);

    /**
     * POST方式将以表单的方式传递键值对作为请求体发送到服务器
     * 其中@FormUrlEncoded 以表单的方式传递键值对
     * 其中 @Path：所有在网址中的参数（URL的问号前面）
     * 另外@FieldMap 用于POST请求，提交多个表单数据，@Field：用于POST请求，提交单个数据
     * 使用@url是为了防止URL被转义为https://10.33.31.200:8890/msp%2Fmobile%2Flogin%3
     */
//    @FormUrlEncoded
    @POST
    Call<ResponseBody> postMap(@Url String url, @Body Map<String, String> map);

    @POST
    Call<ResponseBody> postJson(@Url String url, @Body String json);

    @POST
    Call<ResponseBody> post(@Url String url);

    /**
     * POST
     *
     * @param url
     * @param body
     * @return
     */
    @POST
    Call<ResponseBody> postBean(@Url String url, @Body Serializable body);

    /**
     * POST
     *
     * @param url
     * @param body
     * @return
     */
    @POST
    Call<ResponseBody> post(@Url String url, @Body String body);

    /**
     * 流式下载,不加这个注解的话,会整个文件字节数组全部加载进内存,可能导致oom
     */
    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String fileUrl);
}

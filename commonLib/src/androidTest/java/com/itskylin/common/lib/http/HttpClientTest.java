package com.itskylin.common.lib.http;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.alibaba.fastjson.annotation.JSONField;
import com.itskylin.common.lib.http.base.BaseRequestBean;
import com.itskylin.common.lib.http.base.BaseResponseBean;
import com.itskylin.common.lib.utils.CommonUtils;
import com.orhanobut.logger.Logger;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.Serializable;
import java.util.List;

import static junit.framework.Assert.assertTrue;

/**
 * @author Sky Lin
 * @version V1.0
 * @Package KY_InfusionSys_svn/com.konying.common.http
 * @Description:
 * @email admin@itksylin.com
 * @date 2018/6/28 09:46
 */
@RunWith(AndroidJUnit4.class)
public class HttpClientTest {

    @Test
    public void testHttpClient() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        CommonUtils.Companion.init(appContext);
        HttpClient httpClient = new HttpClient.Builder()
                .baseUrl("http://172.16.1.242:5678")
                .url(ApiService.mIUserLogon)
                .bodyType(DataType.JSON_OBJECT, LoginResultBean.class)
                .tag("login")
                .build();
        httpClient.post(new UserBean("admin", "666666"), new OnResultListener<LoginResultBean>() {
            @Override
            public void onSuccess(LoginResultBean result) {
                Logger.i("Sky", "result = " + result);
                assertTrue(true);
            }

            @Override
            public void onError(int code, String message) {
                Logger.e("Sky", "onError(int " + code + ", String " + message + ")");
                assertTrue(false);
            }
        });
    }


    private class UserBean extends BaseRequestBean {
        /**
         * username : admin
         * password : 666666
         */

        @JSONField(name = "Account")
        private String userName;
        @JSONField(name = "password")
        private String passWord;

        public UserBean(String username, String pwd) {
            this.userName = username;
            this.passWord = pwd;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPassWord() {
            return passWord;
        }

        public void setPassWord(String passWord) {
            this.passWord = passWord;
        }
    }

    private class LoginResultBean extends BaseResponseBean {

        /**
         * DepartmentName : null
         * DepartmentID : 0
         * AreaID : 3
         * AreaCode : 128
         * UserID : 0
         * TrueName : administrator
         * Port : 8783
         * Show : 0
         * Order : 0
         * Data : [{"ID":7,"Name":"肝病科"},{"ID":8,"Name":"肿瘤科"}]
         * Voice : 0
         */
        @JSONField(name = "DepartmentName")
        public String departmentName;
        @JSONField(name = "DepartmentID")
        public int departmentID;
        @JSONField(name = "AreaID")
        public int areaID;
        @JSONField(name = "AreaCode")
        public String areaCode;
        @JSONField(name = "UserID")
        public int userID;
        @JSONField(name = "TrueName")
        public String trueName;
        @JSONField(name = "Port")
        public String port;
        @JSONField(name = "Show")
        public int show;
        @JSONField(name = "Order")
        public int order;
        @JSONField(name = "Voice")
        public int voice;
        @JSONField(name = "Data")
        public List<DataBean> data;

        class DataBean implements Serializable {
            /**
             * ID : 7
             * Name : 肝病科
             */
            @JSONField(name = "ID")
            public int id;
            @JSONField(name = "Name")
            public String name;

            @Override
            public String toString() {
                return "DataBean{" +
                        "id=" + id +
                        ", name='" + name + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "LoginResultBean{" +
                    "result=" + result +
                    ", message='" + message + '\'' +
                    ", departmentName='" + departmentName + '\'' +
                    ", departmentID=" + departmentID +
                    ", areaID=" + areaID +
                    ", areaCode='" + areaCode + '\'' +
                    ", userID=" + userID +
                    ", trueName='" + trueName + '\'' +
                    ", port='" + port + '\'' +
                    ", show=" + show +
                    ", order=" + order +
                    ", voice=" + voice +
                    ", data=" + data +
                    '}';
        }
    }
}
package com.android.exercise.domain;

import java.util.List;

/**
 * Created by wangzhen on 2017/3/1.
 */

public class AppBean extends BaseBean {

    /**
     * result : [{"functionOrder":"1","functionPrompt":"","functionname":"党支部清单","iconUrl":"http://10.100.60.70:8080/ZBCM/temp/icon7e2bf088-15e3-4587-bb6d-ee4a07356135.png","functionUrl":"http://mobile.8531.cn/book/?username=wazh","functionStatus":"0"},{"functionOrder":"2","functionPrompt":"","functionname":"停车场余时查询","iconUrl":"http://10.100.60.70:8080/ZBCM/temp/icone3e5ce6c-ca60-48f1-a7e8-ba669f0a40ab.png","functionUrl":"","functionStatus":"0"},{"functionOrder":"3","functionPrompt":"","functionname":"羽毛球场预定","iconUrl":"http://10.100.60.70:8080/ZBCM/temp/icon864741b0-0949-4b83-8f3b-9f36d0666e3a.png","functionUrl":"http://mobile.8531.cn/ball/?usercode=wazh&username=王震&phone=18368865748","functionStatus":"0"},{"functionOrder":"4","functionPrompt":"","functionname":"报社生活","iconUrl":"http://10.100.60.70:8080/ZBCM/temp/icon0604d62c-a255-4dd2-9dd4-61d93fa7b76d.png","functionUrl":"","functionStatus":"2"},{"functionOrder":"5","functionPrompt":"","functionname":"薪资查询","iconUrl":"http://10.100.60.70:8080/ZBCM/temp/icone6469dbd-0953-47d0-8344-0eca8f92746c.png","functionUrl":"","functionStatus":"0"},{"functionOrder":"6","functionPrompt":"","functionname":"决策数据","iconUrl":"http://10.100.60.70:8080/ZBCM/temp/icon89101a12-9613-49c4-9616-f07d0e8b24e3.png","functionUrl":"http://data.8531.cn","functionStatus":"0"},{"functionOrder":"7","functionPrompt":"","functionname":"请假申请","iconUrl":"http://10.100.60.70:8080/ZBCM/temp/icone4af86f5-fe42-47a8-ac07-b51ed4adf028.png","functionUrl":"http://mobile.8531.cn/hr/?usercode=wazh","functionStatus":"0"},{"functionOrder":"8","functionPrompt":"功能暂时关闭，请等待！","functionname":"工会俱乐部","iconUrl":"http://10.100.60.70:8080/ZBCM/temp/icona018a61a-61cc-4a5f-8887-b2c9c990bd3d.png","functionUrl":"","functionStatus":"1"},{"functionOrder":"9","functionPrompt":"","functionname":"名片申请","iconUrl":"http://10.100.60.70:8080/ZBCM/temp/icone4af86f5-fe42-47a8-ac07-b51ed4adf028.png","functionUrl":"http://mobile.8531.cn/VCardApp/index.html?usercode=wazh","functionStatus":"0"}]
     * msg : 操作成功
     * success : true
     */

    private String msg;
    private boolean success;
    private List<ResultBean> result;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * functionOrder : 1
         * functionPrompt :
         * functionname : 党支部清单
         * iconUrl : http://10.100.60.70:8080/ZBCM/temp/icon7e2bf088-15e3-4587-bb6d-ee4a07356135.png
         * functionUrl : http://mobile.8531.cn/book/?username=wazh
         * functionStatus : 0
         */

        private String functionOrder;
        private String functionPrompt;
        private String functionname;
        private String iconUrl;
        private String functionUrl;
        private String functionStatus;

        public String getFunctionOrder() {
            return functionOrder;
        }

        public void setFunctionOrder(String functionOrder) {
            this.functionOrder = functionOrder;
        }

        public String getFunctionPrompt() {
            return functionPrompt;
        }

        public void setFunctionPrompt(String functionPrompt) {
            this.functionPrompt = functionPrompt;
        }

        public String getFunctionname() {
            return functionname;
        }

        public void setFunctionname(String functionname) {
            this.functionname = functionname;
        }

        public String getIconUrl() {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }

        public String getFunctionUrl() {
            return functionUrl;
        }

        public void setFunctionUrl(String functionUrl) {
            this.functionUrl = functionUrl;
        }

        public String getFunctionStatus() {
            return functionStatus;
        }

        public void setFunctionStatus(String functionStatus) {
            this.functionStatus = functionStatus;
        }
    }
}

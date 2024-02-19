package com.android.exercise.domain;

import java.util.List;

/**
 * Created by wangzhen on 2017/3/1.
 */

public class AppBean extends Generic {
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

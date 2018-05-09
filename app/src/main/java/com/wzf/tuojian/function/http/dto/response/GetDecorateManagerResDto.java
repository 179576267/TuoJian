package com.wzf.tuojian.function.http.dto.response;

import java.io.Serializable;
import java.util.List;

/**
 * @Description:
 * @author: wangzhenfei
 * @date: 2018-05-02 12:30
 */

public class GetDecorateManagerResDto{

    /**
     * udId : 20180325230916019ed931ed89b7f44dd8de86e25d29c0124
     * cellName : 金领国际三期
     * buildNumber : 26#3205
     * customerName : 张女士雅居施工中...
     * groupId : 20180325230916019ed931ed89b7f44dd8de86e25d29c0124
     * isFinish : 0
     * isOwner : 1
     */

    private UserDecorateBean userDecorate;
    /**
     * arrangeDate : 03/27
     * contactIdentity : 设计师
     * notifySign : 开工大吉
     */

    private List<TimeAxisListBean> timeAxisList;
    /**
     * userDecorate : {"udId":"20180325230916019ed931ed89b7f44dd8de86e25d29c0124","cellName":"金领国际三期","buildNumber":"26#3205","customerName":"张女士雅居施工中...","groupId":"20180325230916019ed931ed89b7f44dd8de86e25d29c0124","isFinish":0,"isOwner":1}
     * timeAxisList : [{"arrangeDate":"03/27","contactIdentity":"设计师","notifySign":"开工大吉"}]
     * decorateImgs : ["http://1.jpg","http://2.jpg"]
     * contactList : [{"avatar":null,"contactIdentity":"业 主","contactNickname":"张女士","contactPhoneNum":"18351006261"},{"avatar":null,"contactIdentity":"设计师","contactNickname":"章工","contactPhoneNum":"18351006262"}]
     */

    private List<String> decorateImgs;
    /**
     * avatar : null
     * contactIdentity : 业 主
     * contactNickname : 张女士
     * contactPhoneNum : 18351006261
     */

    private List<ContactListBean> contactList;

    public UserDecorateBean getUserDecorate() {
        return userDecorate;
    }

    public void setUserDecorate(UserDecorateBean userDecorate) {
        this.userDecorate = userDecorate;
    }

    public List<TimeAxisListBean> getTimeAxisList() {
        return timeAxisList;
    }

    public void setTimeAxisList(List<TimeAxisListBean> timeAxisList) {
        this.timeAxisList = timeAxisList;
    }

    public List<String> getDecorateImgs() {
        return decorateImgs;
    }

    public void setDecorateImgs(List<String> decorateImgs) {
        this.decorateImgs = decorateImgs;
    }

    public List<ContactListBean> getContactList() {
        return contactList;
    }

    public void setContactList(List<ContactListBean> contactList) {
        this.contactList = contactList;
    }

    public static class UserDecorateBean {
        private String udId;
        private String cellName;
        private String buildNumber;
        private String customerName;
        private String groupId;
        private int isFinish;
        private int isOwner;

        public String getUdId() {
            return udId;
        }

        public void setUdId(String udId) {
            this.udId = udId;
        }

        public String getCellName() {
            return cellName;
        }

        public void setCellName(String cellName) {
            this.cellName = cellName;
        }

        public String getBuildNumber() {
            return buildNumber;
        }

        public void setBuildNumber(String buildNumber) {
            this.buildNumber = buildNumber;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public int getIsFinish() {
            return isFinish;
        }

        public void setIsFinish(int isFinish) {
            this.isFinish = isFinish;
        }

        public int getIsOwner() {
            return isOwner;
        }

        public void setIsOwner(int isOwner) {
            this.isOwner = isOwner;
        }
    }

    public static class TimeAxisListBean {
        private String arrangeDate;
        private String contactIdentity;
        private String notifySign;

        public String getArrangeDate() {
            return arrangeDate;
        }

        public void setArrangeDate(String arrangeDate) {
            this.arrangeDate = arrangeDate;
        }

        public String getContactIdentity() {
            return contactIdentity;
        }

        public void setContactIdentity(String contactIdentity) {
            this.contactIdentity = contactIdentity;
        }

        public String getNotifySign() {
            return notifySign;
        }

        public void setNotifySign(String notifySign) {
            this.notifySign = notifySign;
        }
    }

    public static class ContactListBean {
        private Object avatar;
        private String contactIdentity;
        private String contactNickname;
        private String contactPhoneNum;

        public Object getAvatar() {
            return avatar;
        }

        public void setAvatar(Object avatar) {
            this.avatar = avatar;
        }

        public String getContactIdentity() {
            return contactIdentity;
        }

        public void setContactIdentity(String contactIdentity) {
            this.contactIdentity = contactIdentity;
        }

        public String getContactNickname() {
            return contactNickname;
        }

        public void setContactNickname(String contactNickname) {
            this.contactNickname = contactNickname;
        }

        public String getContactPhoneNum() {
            return contactPhoneNum;
        }

        public void setContactPhoneNum(String contactPhoneNum) {
            this.contactPhoneNum = contactPhoneNum;
        }
    }

    @Override
    public String toString() {
        return "GetDecorateManagerResDto{" +
                "userDecorate=" + userDecorate +
                ", timeAxisList=" + timeAxisList +
                ", decorateImgs=" + decorateImgs +
                ", contactList=" + contactList +
                '}';
    }
}

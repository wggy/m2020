package org.wangep.filterchain;

/***
 * created by wange on 2020/9/7 11:42
 */
public class ApprovalDto {
    private Integer fee;
    private String username;

    public ApprovalDto(Integer fee, String username) {
        this.fee = fee;
        this.username = username;
    }

    public ApprovalDto() {

    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getFee() {
        return fee;
    }

    public String getUsername() {
        return username;
    }
}

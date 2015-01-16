package com.jje.membercenter.domain;

import com.jje.dto.membercenter.AccountMergeDto;

import java.util.Date;

public class MergeMcMemberCodeLog {

    private String updatedModule;
    private String targetMcMemberCode;
    private String sourceMcMemberCode;
    private Date mergeDate;

    public MergeMcMemberCodeLog(AccountMergeDto dto) {
        this.setMergeDate(new Date());
        this.setTargetMcMemberCode(dto.getMainAccountMcMemberCode());
        String sourceMcMemberCode = "";
        for (String sourceCode : dto.getMergedMcMemberCodeList()) {
            sourceMcMemberCode += sourceCode + ",";
        }
        this.setSourceMcMemberCode(sourceMcMemberCode);
    }

    public String getUpdatedModule() {
        return updatedModule;
    }

    public void setUpdatedModule(String updatedModule) {
        this.updatedModule = updatedModule;
    }

    public String getTargetMcMemberCode() {
        return targetMcMemberCode;
    }

    public void setTargetMcMemberCode(String targetMcMemberCode) {
        this.targetMcMemberCode = targetMcMemberCode;
    }

    public String getSourceMcMemberCode() {
        return sourceMcMemberCode;
    }

    public void setSourceMcMemberCode(String sourceMcMemberCode) {
        this.sourceMcMemberCode = sourceMcMemberCode;
    }

    public Date getMergeDate() {
        return mergeDate;
    }

    public void setMergeDate(Date mergeDate) {
        this.mergeDate = mergeDate;
    }

    @Override
    public String toString() {
        return "MergeMcMemberCodeLog{" +
                "updatedModule='" + updatedModule + '\'' +
                ", targetMcMemberCode='" + targetMcMemberCode + '\'' +
                ", sourceMcMemberCode='" + sourceMcMemberCode + '\'' +
                ", mergeDate=" + mergeDate +
                '}';
    }
}

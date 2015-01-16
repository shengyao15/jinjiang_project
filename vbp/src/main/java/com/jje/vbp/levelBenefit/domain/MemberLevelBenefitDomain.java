package com.jje.vbp.levelBenefit.domain;

import java.math.BigDecimal;

public class MemberLevelBenefitDomain {

	private String created;
	private String memberId;
	private String tierId;
	private BigDecimal amtVal;
	private BigDecimal availableScore;
	private String newTierId;
	private BigDecimal newAmtVal;
	private BigDecimal newAvailableScore;
	private BigDecimal amtDiff;
	private BigDecimal scoreDiff;

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getTierId() {
		return tierId;
	}

	public void setTierId(String tierId) {
		this.tierId = tierId;
	}

	public BigDecimal getAmtVal() {
		return amtVal;
	}

	public void setAmtVal(BigDecimal amtVal) {
		this.amtVal = amtVal;
	}

	public BigDecimal getAvailableScore() {
		return availableScore;
	}

	public void setAvailableScore(BigDecimal availableScore) {
		this.availableScore = availableScore;
	}

	public String getNewTierId() {
		return newTierId;
	}

	public void setNewTierId(String newTierId) {
		this.newTierId = newTierId;
	}

	public BigDecimal getNewAmtVal() {
		return newAmtVal;
	}

	public void setNewAmtVal(BigDecimal newAmtVal) {
		this.newAmtVal = newAmtVal;
	}

	public BigDecimal getNewAvailableScore() {
		return newAvailableScore;
	}

	public void setNewAvailableScore(BigDecimal newAvailableScore) {
		this.newAvailableScore = newAvailableScore;
	}

	public BigDecimal getAmtDiff() {
		return amtDiff;
	}

	public void setAmtDiff(BigDecimal amtDiff) {
		this.amtDiff = amtDiff;
	}

	public BigDecimal getScoreDiff() {
		return scoreDiff;
	}

	public void setScoreDiff(BigDecimal scoreDiff) {
		this.scoreDiff = scoreDiff;
	}

}

package com.revature.models;

import java.sql.Date;

public class Payment {
	private Integer payment_id;
	private Offer offer;
	private Double amount;
	private Date payment_date;
	private User user;

	public Integer getPayment_id() {
		return payment_id;
	}

	public void setPayment_int(Integer payment_id) {
		this.payment_id = payment_id;
	}

	public Offer getOffer() {
		return offer;
	}

	public void setOffer(Offer offer) {
		this.offer = offer;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Date getPayment_date() {
		return payment_date;
	}

	public void setPayment_date(Date payment_date) {
		this.payment_date = payment_date;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Payment [payment_int=" + payment_id + ", offer_id=" + offer.getOffer_id() + ", amount=" + amount + ", payment_date="
				+ payment_date + ", user=" + user + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((offer == null) ? 0 : offer.hashCode());
		result = prime * result + ((payment_date == null) ? 0 : payment_date.hashCode());
		result = prime * result + ((payment_id == null) ? 0 : payment_id.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Payment other = (Payment) obj;
		if (amount == null) {
			if (other.amount != null) {
				return false;
			}
		} else if (!amount.equals(other.amount)) {
			return false;
		}
		if (offer == null) {
			if (other.offer != null) {
				return false;
			}
		} else if (!offer.equals(other.offer)) {
			return false;
		}
		if (payment_date == null) {
			if (other.payment_date != null) {
				return false;
			}
		} else if (!payment_date.equals(other.payment_date)) {
			return false;
		}
		if (payment_id == null) {
			if (other.payment_id != null) {
				return false;
			}
		} else if (!payment_id.equals(other.payment_id)) {
			return false;
		}
		if (user == null) {
			if (other.user != null) {
				return false;
			}
		} else if (!user.equals(other.user)) {
			return false;
		}
		return true;
	}

}

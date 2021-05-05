package com.revature.models;

import java.sql.Date;

public class Offer {
	private Integer offer_id;
	private Item item;
	private Integer quantity;
	private Double offer_price;
	private Date offer_date;
	private User user;
	private Status status;
	private Boolean ownership;
	private Integer installments;
	private Double balance;

	public Offer() {
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Integer getOffer_id() {
		return offer_id;
	}

	public void setOffer_id(Integer offer_id) {
		this.offer_id = offer_id;
	}

	public Item getItem() {
		return this.item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getOffer_price() {
		return offer_price;
	}

	public void setOffer_price(Double offer_price) {
		this.offer_price = offer_price;
	}

	public Date getOffer_date() {
		return offer_date;
	}

	public void setOffer_date(Date offer_date) {
		this.offer_date = offer_date;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Boolean getOwnership() {
		return ownership;
	}

	public void setOwnership(Boolean ownership) {
		this.ownership = ownership;
	}

	public Integer getInstallments() {
		return installments;
	}

	public void setInstallments(Integer installments) {
		this.installments = installments;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "Offer [offer_id=" + offer_id + ", item=" + item.getItem_name() + ", offer_price=" + offer_price
				+ ", offer_date=" + offer_date + ", user=" + user.getUsername() + ", status=" + status.getStatus_name() + ", ownership="
				+ ownership + ", installments=" + installments + ", balance=" + balance + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((balance == null) ? 0 : balance.hashCode());
		result = prime * result + ((installments == null) ? 0 : installments.hashCode());
		result = prime * result + ((item == null) ? 0 : item.hashCode());
		result = prime * result + ((offer_date == null) ? 0 : offer_date.hashCode());
		result = prime * result + ((offer_id == null) ? 0 : offer_id.hashCode());
		result = prime * result + ((offer_price == null) ? 0 : offer_price.hashCode());
		result = prime * result + ((ownership == null) ? 0 : ownership.hashCode());
		result = prime * result + ((quantity == null) ? 0 : quantity.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		Offer other = (Offer) obj;
		if (balance == null) {
			if (other.balance != null) {
				return false;
			}
		} else if (!balance.equals(other.balance)) {
			return false;
		}
		if (installments == null) {
			if (other.installments != null) {
				return false;
			}
		} else if (!installments.equals(other.installments)) {
			return false;
		}
		if (item == null) {
			if (other.item != null) {
				return false;
			}
		} else if (!item.equals(other.item)) {
			return false;
		}
		if (offer_date == null) {
			if (other.offer_date != null) {
				return false;
			}
		} else if (!offer_date.equals(other.offer_date)) {
			return false;
		}
		if (offer_id == null) {
			if (other.offer_id != null) {
				return false;
			}
		} else if (!offer_id.equals(other.offer_id)) {
			return false;
		}
		if (offer_price == null) {
			if (other.offer_price != null) {
				return false;
			}
		} else if (!offer_price.equals(other.offer_price)) {
			return false;
		}
		if (ownership == null) {
			if (other.ownership != null) {
				return false;
			}
		} else if (!ownership.equals(other.ownership)) {
			return false;
		}
		if (quantity == null) {
			if (other.quantity != null) {
				return false;
			}
		} else if (!quantity.equals(other.quantity)) {
			return false;
		}
		if (status == null) {
			if (other.status != null) {
				return false;
			}
		} else if (!status.equals(other.status)) {
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

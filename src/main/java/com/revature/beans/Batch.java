package com.revature.beans;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name="batch_table")
public class Batch implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private int batchNumber;
	private String address;
	
	public Batch() {
		super();
	}

	public Batch(int batchNumber, String address) {
		super();
		this.batchNumber = batchNumber;
		this.address = address;
	}

	public int getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(int batchNumber) {
		this.batchNumber = batchNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + batchNumber;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Batch other = (Batch) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (batchNumber != other.batchNumber)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Batch [batchNumber=" + batchNumber + ", address=" + address + "]";
	}
	
}

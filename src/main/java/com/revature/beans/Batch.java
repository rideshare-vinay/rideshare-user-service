package com.revature.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Batch class that represents a user's batch. All batches have a batch number and a location.
 * 
 * @author Adonis Cabreja
 *
 */

@Component
@Entity
@Table(name="batches")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class Batch implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@NotNull(message="Batch Number should not be blank")
	@Min(value=0, message="Batch should be greater than 0")
	@Max(value=9999, message="Batch Number should be less than 9999")
	@Column(name="batch_number")
	private int batchNumber;
	
	@NotBlank(message="Batch Location should not be blank")
	@Pattern(message="Batch Location does not match the standard", regexp="^[\\w ]+-\\ [\\w ]+,\\ \\w{2,3}$")
	@Column(name="batch_location")
	private String batchLocation;
	
	public Batch() {
		super();
	}

	public Batch(int batchNumber, @NotBlank String batchLocation) {
		super();
		this.batchNumber = batchNumber;
		this.batchLocation = batchLocation;
	}

	public Batch(int batchNumber) {
		this.batchNumber=batchNumber;
	}
	
	public int getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(int batchNumber) {
		this.batchNumber = batchNumber;
	}

	public String getBatchLocation() {
		return batchLocation;
	}

	public void setBatchLocation(String batchLocation) {
		this.batchLocation = batchLocation;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((batchLocation == null) ? 0 : batchLocation.hashCode());
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
		if (batchLocation == null) {
			if (other.batchLocation != null)
				return false;
		} 
		else if (!batchLocation.equals(other.batchLocation))
			return false;
		return batchNumber == other.batchNumber;
	}

	@Override
	public String toString() {
		return "Batch [batchNumber=" + batchNumber + ", batchLocation=" + batchLocation + "]";
	}
	
}

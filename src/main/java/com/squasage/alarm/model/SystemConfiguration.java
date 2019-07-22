package com.squasage.alarm.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
public final class SystemConfiguration implements Serializable {

	private static final long serialVersionUID = -6354229730588758057L;

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private String username;

	@Column(nullable = false)
	private String lastConnectedIp;

	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date createdAt;

	@LastModifiedDate
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date lastUpdated;

	public static SystemConfiguration newInstance() {
		return new SystemConfiguration();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getLastConnectedIp() {
		return lastConnectedIp;
	}

	public void setLastConnectedIp(String lastConnectedIp) {
		this.lastConnectedIp = lastConnectedIp;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

}

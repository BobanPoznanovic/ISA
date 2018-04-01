package com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="FRIENDSHIP")
public class Friendship {
	
	@Id
	@GeneratedValue
	@Column(name = "FRIENDSHIP_ID", updatable = false, nullable = false, insertable=false)
	private Long id;
	
	@NotNull
	@OneToOne
	@JoinColumn(name="USER_ID")
	private User sender;
	
	@NotNull
	@OneToOne
	@JoinColumn(name="USER_ID")
	private User reciever;
	
	@Enumerated(EnumType.STRING)
	private FriendshipStatus status; 
}

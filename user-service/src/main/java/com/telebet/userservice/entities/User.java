package com.telebet.userservice.entities;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name="user_details")
public class User {

	@JsonSerialize(using = ToStringSerializer.class)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
	
	@NotNull(message = "Username must not be empty")
    private String userName;
    private String email;
    
    private String password;
    private String role;
}

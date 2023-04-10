package com.example.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
import lombok.ToString;


@Entity
@Data
@ToString
public class Match {
	
	@Id
	private long id; 
	private String city;
	private LocalDate date;
	private String playerofmatch;
	private String venue;
	private String team1;
	private String team2;
	private String tosswinner;
	private String tossdecision;
	
	private String matchWinner;
	private String result;
	private String resultMargin;
	private String eliminator;
	private String umpire1;
	private String umpire2;
	
	
	
	
	
}

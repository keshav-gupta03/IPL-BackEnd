package com.example.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.model.Match;

@Service
public interface MatchService {

	public List<Match> getAllMatchesForTeam(String teamName, LocalDate date1, LocalDate date2);
	
}

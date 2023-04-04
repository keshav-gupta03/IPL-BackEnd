package com.example.service;

import java.util.Date;
import java.util.List;

import com.example.model.Match;
import com.example.model.Team;

public interface TeamService {

	Team getTeamByName(String name);
	
	List<Match> getAllMatchesOfTeam(String teamName);
	
	List<Match> getAllMatches();
	List<Match> getAllMatchesFromCity(String city);
	
	List<Team> getAllTeams();
}

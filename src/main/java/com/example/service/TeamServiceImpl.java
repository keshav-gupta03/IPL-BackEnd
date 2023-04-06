package com.example.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.model.Match;
import com.example.model.Team;
import com.example.repository.MatchRepository;
import com.example.repository.TeamRepository;

@Service
public class TeamServiceImpl implements TeamService {

	@Autowired
	TeamRepository teamRepository;
	
	@Autowired
	MatchRepository matchRepository;
	
	@Override
	public Team getTeamByName(String name) {
		return teamRepository.findByTeamName(name);
		
	}

	@Override
	public List<Match> getAllMatchesOfTeam(String teamName) {
		Pageable pageable = PageRequest.of(1,4);
		return matchRepository.findByTeam1OrTeam2OrderByDateDesc(teamName, teamName, pageable);
	}

	@Override
	public List<Match> getAllMatchesFromCity(String city) {
		return matchRepository.getAllMatchesFromCity(city);
		
	}

	@Override
	public List<Team> getAllTeams() {
		return teamRepository.findAll();
	}

	@Override
	public List<Match> getAllMatches() {
		return matchRepository.findAll();
	}

	 

}

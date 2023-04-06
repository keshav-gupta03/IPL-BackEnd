package com.example.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Match;
import com.example.repository.MatchRepository;

@Service
public class MatchServiceImpl implements MatchService {

	@Autowired
	MatchRepository matchRepository;
	@Override
	public List<Match> getAllMatchesForTeam(String teamName, LocalDate date1, LocalDate date2) {
//		return matchRepository.findByTeam1AndDateBetweenOrTeam1AndDateBetweenOrderByDateDesc(teamName,date1, date2, teamName, date1, date2);
		return matchRepository.getMatchesByTeamBetweenDates(teamName, date1, date2);
	}

}

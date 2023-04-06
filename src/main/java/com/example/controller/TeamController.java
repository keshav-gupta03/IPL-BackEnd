package com.example.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.exception.TeamNotFoundException;
import com.example.model.Match;
import com.example.model.Team;
import com.example.service.MatchServiceImpl;
import com.example.service.TeamServiceImpl;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/ipl")
public class TeamController {

	@Autowired
	TeamServiceImpl teamServiceImpl;
	
	@Autowired
	MatchServiceImpl matchServiceImpl;
	
	
	@GetMapping("/teams")
	public ResponseEntity<List<Team>> getAllTeams(){
		List<Team> teams= teamServiceImpl.getAllTeams();
		return ResponseEntity.ok(teams);
	}
	@GetMapping("/teams/{teamName}")
	public ResponseEntity<Team> getTeamData(@PathVariable("teamName") String name) {
	    Team team = teamServiceImpl.getTeamByName(name);
	    if (team == null) {
	        throw new TeamNotFoundException("Team not found with name: " + name);
	    }
	    team.setMatches(teamServiceImpl.getAllMatchesOfTeam(name));
	    return ResponseEntity.ok(team);
	}
	
	
	@GetMapping("/teams/{teamName}/matches")
	public ResponseEntity<List<Match>> getAllMatchesForTeam(@PathVariable("teamName") String teamName, @RequestParam("year") int year ){
		LocalDate startDate = LocalDate.of(year, 1, 1);
		LocalDate endDate = LocalDate.of(year+1, 1, 1);
		
		List<Match> matches = matchServiceImpl.getAllMatchesForTeam(teamName, startDate, endDate);
		
		return ResponseEntity.ok(matches);
	}
	
	@GetMapping("/matches")
	public ResponseEntity<List<Match>> getAllMatches(){
		List<Match> matches= teamServiceImpl.getAllMatches();
		if(matches==null) {
			 ResponseEntity<List<Match>> rs = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			 return rs;
		}
		return ResponseEntity.ok(matches);
	}
	@GetMapping("/matches/{city}")
	public ResponseEntity<List<Match>> getAllMatchesFromCity(@PathVariable("city") String city){
		List<Match> matches = teamServiceImpl.getAllMatchesFromCity(city);
		if(matches==null) {
			 ResponseEntity<List<Match>> rs = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			 return rs;
		}
		return ResponseEntity.ok(matches);
	}
}

package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Team;

public interface TeamRepository extends JpaRepository<Team, Long>{

	
	Team findByTeamName(String teamName);
}

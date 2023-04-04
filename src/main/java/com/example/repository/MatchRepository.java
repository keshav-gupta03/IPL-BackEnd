package com.example.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.model.Match;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {

	
	List<Match> findByTeam1OrTeam2OrderByDateDesc(String teamName1, String teamName2, Pageable pageable);
	
	@Query(value = "select * from MATCH where CITY= ?1", nativeQuery = true)
	List<Match> getAllMatchesFromCity(String city);
	
	
}

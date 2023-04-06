package com.example.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.model.Match;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {

	List<Match> findByTeam1OrTeam2OrderByDateDesc(String teamName1, String teamName2, Pageable pageable);

	@Query(value = "select m from Match m where (m.team1=:teamName or m.team2=:teamName) and (m.date between :date1 and :date2) order by date desc ")
	List<Match> getMatchesByTeamBetweenDates(@Param("teamName") String teamName, @Param("date1") LocalDate date1,
			@Param("date2") LocalDate date2);

	List<Match> findByTeam1AndDateBetweenOrTeam1AndDateBetweenOrderByDateDesc(String teamName1, LocalDate date1,
			LocalDate date2, String teamName2, LocalDate startDate1, LocalDate startDate2);

	default List<Match> findLatestMatchsByTeam(String teamName, int count) {
		return findByTeam1OrTeam2OrderByDateDesc(teamName, teamName, PageRequest.of(0, count));
	}

	@Query(value = "select * from MATCH where CITY= ?1", nativeQuery = true)
	List<Match> getAllMatchesFromCity(String city);

}

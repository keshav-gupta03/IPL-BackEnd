package com.example.processing;

import java.time.LocalDate;

import org.apache.logging.log4j.Logger;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.modelmapper.ModelMapper;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.dao.MatchInput;
import com.example.model.Match;

public class MatchProcessor implements ItemProcessor<MatchInput, Match> {
	
	@Override
	public Match process(final MatchInput matchInput) throws Exception {
		Match match = new Match();
		
		match.setId(Long.parseLong(matchInput.getId()));
		match.setCity(matchInput.getCity());
		match.setDate(LocalDate.parse(matchInput.getDate()));
		match.setPlayerofmatch(matchInput.getPlayer_of_match());
		match.setVenue(matchInput.getVenue());
		
		//set team 1 and team 2 depending on the innings order
		String firstInningsTeam , secondInnningsTeam;
		if("bat".equals(matchInput.getToss_decision())) {
			firstInningsTeam = matchInput.getToss_winner();
			secondInnningsTeam= matchInput.getToss_winner().equals(matchInput.getTeam1())? matchInput.getTeam2() : matchInput.getTeam1();
			
		} else {
			secondInnningsTeam = matchInput.getToss_winner();
			firstInningsTeam  = matchInput.getToss_winner().equals(matchInput.getTeam1())? matchInput.getTeam2() : matchInput.getTeam1();

		}
		
		match.setTeam1(firstInningsTeam);
		match.setTeam2(secondInnningsTeam);
		
		match.setTossdecision(matchInput.getToss_decision());
		match.setTosswinner(matchInput.getToss_winner());
		
		match.setMatchWinner(matchInput.getWinner());
		match.setResult(matchInput.getResult());
		match.setResultMargin(matchInput.getResult_margin());
		
		match.setEliminator(matchInput.getEliminator());
		
		match.setUmpire1(matchInput.getUmpire1());
		match.setUmpire2(matchInput.getUmpire2());
		
		return match;
	}
}

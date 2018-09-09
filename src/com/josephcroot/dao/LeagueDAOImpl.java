package com.josephcroot.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.josephcroot.entity.League;

@Repository
public class LeagueDAOImpl implements LeagueDAO {

	@Autowired
	private SessionFactory session;

	@Override
	public League getLeague(int leagueId) {
		Session currentSession = session.getCurrentSession();
		League tmp = currentSession.get(League.class, leagueId);
		return tmp;
	}

	@Override
	public void deleteLeague(int leagueId) {
		Session currentSession = session.getCurrentSession();
		League tmp = currentSession.get(League.class, leagueId);
		currentSession.delete(tmp);
	}

	@Override
	public void addLeague(League league) {
		Session currentSession = session.getCurrentSession();
		currentSession.saveOrUpdate(league);
	}

}

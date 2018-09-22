package com.josephcroot.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.josephcroot.entity.Team;

@Repository
public class TeamDAOImpl implements TeamDAO {

	@Autowired
	private SessionFactory session;

	@Override
	public Team getTeam(int teamId) {
		Session currentSession = session.getCurrentSession();
		Team tmp = currentSession.get(Team.class, teamId);
		return tmp;
	}

	@Override
	public void deleteTeam(int teamId) {
		Session currentSession = session.getCurrentSession();
		Team tmp = currentSession.get(Team.class, teamId);
		currentSession.delete(tmp);
	}

	@Override
	public void addTeam(Team team) {
		Session currentSession = session.getCurrentSession();
		currentSession.saveOrUpdate(team);
	}

	@Override
	public void updateTeam(Team team) {
		Session currentSession = session.getCurrentSession();
		currentSession.saveOrUpdate(team);
	}

	@Override
	public List<Integer> getTeamIds() {
		Session currentSession = session.getCurrentSession();
		return (ArrayList<Integer>) currentSession.createQuery("SELECT T.fantasyFootballId FROM Team T").list();
	}

}

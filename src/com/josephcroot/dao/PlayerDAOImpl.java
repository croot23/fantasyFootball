package com.josephcroot.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.josephcroot.entity.Player;

@Repository
public class PlayerDAOImpl implements PlayerDAO {

	@Autowired
	private SessionFactory session;
	
	@Override
	public Player getPlayer(int playerId) {
		Session currentSession = session.getCurrentSession();
		Player tmp = currentSession.get(Player.class, playerId);
		return tmp;
	}
	
	@Override
	public void deletePlayer(int theId) {
		Session currentSession = session.getCurrentSession();
		Player tmp = currentSession.get(Player.class, theId);
		currentSession.delete(tmp);
	}

	@Override
	public void addplayer(Player tempPlayer) {
		Session currentSession = session.getCurrentSession();
		currentSession.saveOrUpdate(tempPlayer);
	}

	@Override
	public void updatePlayer(Player tempPlayer) {
		Session currentSession = session.getCurrentSession();
		currentSession.update(tempPlayer);
	}

	@Override
	public List<Integer> getPlayerIds() {
		Session currentSession = session.getCurrentSession();
		return (ArrayList<Integer>) currentSession.createQuery("SELECT P.fantasyFootballId FROM Player P").list();
	}

}

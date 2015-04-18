package ru.rgups.time.model;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

import ru.rgups.time.model.entity.Day;

public class DayDAO extends BaseDaoImpl<Day, Integer>{

	
	protected DayDAO( ConnectionSource connectionSource, Class<Day> dataClass) throws SQLException {
		super(connectionSource, dataClass);
	}

}

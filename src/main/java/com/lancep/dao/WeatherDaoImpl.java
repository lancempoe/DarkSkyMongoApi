package com.lancep.dao;

import com.lancep.airport.errorhandling.WeatherException;
import com.lancep.airport.orm.AirportDailyWeather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mapping.model.MappingException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Logger;

@Repository
public class WeatherDaoImpl implements WeatherDao {

    private static final Logger logger = Logger.getLogger( WeatherDaoImpl.class.getName() );
    private MongoTemplate mongoTemplate;

    @Autowired
    public WeatherDaoImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void save(AirportDailyWeather dailyWeather) {
        try {
            mongoTemplate.save(dailyWeather);
        } catch (Exception e) {
            logger.warning(String.format("Failed to save AirportDailyWeather: %d, %s", dailyWeather.getId(), e));
            throw new WeatherException(Response.Status.BAD_GATEWAY);
        }
    }

    @Override
    public AirportDailyWeather  findById(Long timeKey) {
        AirportDailyWeather data = null;
        try {
            data = mongoTemplate.findById(timeKey, AirportDailyWeather.class);
        } catch (MappingException e) {
            logger.info(String.format("AirportDailyWeather with key of %d not found", timeKey));
        } catch (Exception e) {
            logger.warning(String.format("Failed to get AirportDailyWeather: %d, %s", timeKey, e));
            throw new WeatherException(Response.Status.BAD_GATEWAY);
        }
        logger.info(String.format("Existing AirportDailyWeather Reused: %s", timeKey));
        return data;
    }

    @Override
    public List<AirportDailyWeather> findByIds(List<Long> timeKeys) {
        return null;
    }
}

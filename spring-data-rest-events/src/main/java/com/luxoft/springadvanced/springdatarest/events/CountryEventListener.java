package com.luxoft.springadvanced.springdatarest.events;

import com.luxoft.springadvanced.springdatarest.model.Country;
import org.apache.log4j.Logger;
import org.springframework.data.rest.core.event.AbstractRepositoryEventListener;
import org.springframework.stereotype.Service;

@Service
public class CountryEventListener extends
        AbstractRepositoryEventListener<Country> {

    private final static Logger logger = Logger.getLogger(CountryEventListener.class);

    @Override
    public void onBeforeCreate(Country person) {
        logger.info("I'll make an effort to create this person " + person.getName());
        //throw new IllegalStateException("Fake");
    }

    @Override
    public void onAfterCreate(Country person) {
        logger.info("I did my best and I was able to create " + person.getName());
    }

    @Override
    public void onBeforeSave(Country person) {
        logger.info("I'll take a breath and I will save " + person.getName());
    }

    @Override
    public void onAfterSave(Country person) {
        logger.info("Hard, hard to save " + person.getName());
    }

    @Override
    public void onBeforeDelete(Country person) {
        logger.warn("I'll delete " + person.getName() + ", you might never see him/her again");
    }

    @Override
    public void onAfterDelete(Country person) {
        logger.warn("Say good-bye to " + person.getName() + ", I deleted him/her");
    }
}

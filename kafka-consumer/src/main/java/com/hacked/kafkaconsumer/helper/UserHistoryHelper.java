package com.hacked.kafkaconsumer.helper;

import com.hacked.kafkaconsumer.entities.UserHistoryEntity;
import com.hacked.kafkaconsumer.repositories.UserHistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@Component
public class UserHistoryHelper {

    @Autowired
    UserHistoryRepository userHistoryRepository;

    private static final String SYSTEM_EVENT = "System event";

    private static final String ADMIN = "admin gs1";
    private final Logger logger = LoggerFactory.getLogger(UserHistoryHelper.class);

    private final Random random = new Random();

    public void saveUserHistory(List<HashMap<String,String>> changeEvents) {
        UserHistoryEntity _userHistory = null;
        for (HashMap<String,String> changeEvent : changeEvents) {
            _userHistory = new UserHistoryEntity();
            _userHistory.setUserId(getUserId());
            _userHistory.setEventGroupId(getGroupId());
            _userHistory.setAdministrator(getAdministrator());
            _userHistory.setEventType(changeEvent.get("eventType"));
            _userHistory.setEventDetails(changeEvent.get("eventDetails"));
            _userHistory.setCreatedBy(getAdminId());
            _userHistory.setCreatedAt(new Date());
            userHistoryRepository.save(_userHistory);
            logger.info("persisted change record : " + _userHistory);
        }
    }

    private int getUserId() {
        return random.nextInt(Integer.MAX_VALUE);
    }

    private int getAdminId() {
        return random.nextInt(Integer.MAX_VALUE);
    }

    private int getGroupId() {
        return random.nextInt(Integer.MAX_VALUE);
    }

    private String getAdministrator() {
        return random.nextInt(Integer.MAX_VALUE) % 2 == 0 ? SYSTEM_EVENT : ADMIN;
    }
}

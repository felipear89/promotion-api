package com.company.promotionapi.listener;

import com.company.promotionapi.model.Campaign;
import com.sun.javafx.binding.StringFormatter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

@Component
public class CampaignMessageListener {

    private static final Logger log = Logger.getLogger(CampaignMessageListener.class.getName());

    public void receiveMessage(List<Campaign> campaigns) {
        log.info("Received");
        log.info("Message processed..."+ campaigns);
    }

}

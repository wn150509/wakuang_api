package com.soft.wakuangapi.service.serviceimpl;

import com.soft.wakuangapi.dao.EventRepository;
import com.soft.wakuangapi.entity.Events;
import com.soft.wakuangapi.entity.EventsStatus;
import com.soft.wakuangapi.service.EventService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {
    @Resource
    private EventRepository eventRepository;
    @Override
    public List<EventsStatus> findAllEvents() {

        List<Events>eventsList=eventRepository.findAll();

        for (int i=0;i<eventsList.size();i++){

        }
        return null;
    }
}

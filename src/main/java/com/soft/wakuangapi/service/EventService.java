package com.soft.wakuangapi.service;

import com.soft.wakuangapi.entity.Events;
import com.soft.wakuangapi.entity.EventsStatus;

import java.util.List;

public interface EventService {
    List<EventsStatus>findAllEvents();
}

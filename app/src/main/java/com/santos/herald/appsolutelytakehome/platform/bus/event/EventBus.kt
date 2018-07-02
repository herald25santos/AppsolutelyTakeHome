package com.santos.herald.appsolutelytakehome.platform.bus.event

import com.santos.herald.appsolutelytakehome.platform.bus.event.events.FragmentSyncEvent
import com.santos.herald.appsolutelytakehome.platform.bus.event.events.WeatherDummyEvent


class EventBus {
    val fragmentSyncEvent = FragmentSyncEvent()
    val weatherDummyEvent = WeatherDummyEvent()
}
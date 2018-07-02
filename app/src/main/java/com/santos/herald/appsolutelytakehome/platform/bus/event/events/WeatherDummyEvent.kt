package com.santos.herald.appsolutelytakehome.platform.bus.event.events

import com.santos.herald.appsolutelytakehome.platform.bus.event.BaseEventBus
import com.santos.herald.domain.model.WeatherModel

class WeatherDummyEvent : BaseEventBus<WeatherModel>() {

    companion object {
        val ACTION_HELLO = "ACTION_HELLO"
    }
}
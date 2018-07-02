package com.santos.herald.appsolutelytakehome.platform.bus.event

import com.santos.herald.appsolutelytakehome.platform.bus.event.events.BaseEvent
import com.santos.herald.appsolutelytakehome.platform.bus.BaseBus


open class BaseEventBus<T> : BaseBus<BaseEvent<T>>()
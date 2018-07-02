package com.santos.herald.appsolutelytakehome.platform.bus.event.events

import com.santos.herald.appsolutelytakehome.platform.bus.event.BaseEventBus

class FragmentSyncEvent : BaseEventBus<Any>() {

    companion object {
        val ACTION_SYNC_ON = "SYNC_ON"
        val ACTION_SYNC_OFF = "SYNC_OFF"
    }
}
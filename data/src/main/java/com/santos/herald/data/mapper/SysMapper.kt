package com.santos.herald.data.mapper

import com.santos.herald.data.mapper.base.Mapper
import com.santos.herald.data.source.model.Sys
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SysMapper
@Inject
constructor() : Mapper<com.santos.herald.domain.model.Sys, Sys>() {
    
    override fun map(from: Sys): com.santos.herald.domain.model.Sys {

        val type = from.type ?: invalidInt
        val id = from.id ?: invalidInt
        val message = from.message ?: invalidDouble
        val country = from.country ?: emptyString
        val sunrise = from.sunrise ?: invalidInt
        val sunset = from.sunset ?: invalidInt
        
        return com.santos.herald.domain.model.Sys(type, id, message, country, sunrise, sunset)
    }

    override fun reverse(to: com.santos.herald.domain.model.Sys): Sys {
        return Sys(to.type, to.id, to.message, to.country, to.sunrise, to.sunset)
    }

}
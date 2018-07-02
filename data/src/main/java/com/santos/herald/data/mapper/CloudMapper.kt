package com.santos.herald.data.mapper

import com.santos.herald.data.mapper.base.Mapper
import com.santos.herald.data.source.model.Clouds
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CloudMapper
@Inject
constructor() : Mapper<com.santos.herald.domain.model.Clouds, Clouds>() {

    override fun map(from: Clouds): com.santos.herald.domain.model.Clouds {
        val all = from.all ?: invalidInt
        return com.santos.herald.domain.model.Clouds(all)
    }

    override fun reverse(to: com.santos.herald.domain.model.Clouds): Clouds {
        return Clouds(to.all)
    }

}
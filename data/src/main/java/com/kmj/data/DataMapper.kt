package com.kmj.data

internal interface DataMapper<DomainModel> {
    fun toDomain(): DomainModel
}

//제네릭활용 list mapper
internal fun <EntityModel : DataMapper<DomainModel>, DomainModel> List<EntityModel>.toDomain(): List<DomainModel> {
    return map(DataMapper<DomainModel>::toDomain)
}
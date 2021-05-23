package com.git.trending.util


/**Created by Shiv Jalkote on 08-May-2021. **/

interface EntityMapper<Entity, DomainModel> {
    fun mapFromEntity(entity: Entity): DomainModel
    fun mapToEntity(domainModel: DomainModel): Entity
}
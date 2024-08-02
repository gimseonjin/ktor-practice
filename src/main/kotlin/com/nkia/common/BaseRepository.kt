package com.nkia.common

interface BaseRepository<Domain: BaseModel> {
    fun save(domain: Domain): Domain
    fun findById(id: Long): Domain?
    fun findAll(): List<Domain>
    fun deleteById(id: Long)
}
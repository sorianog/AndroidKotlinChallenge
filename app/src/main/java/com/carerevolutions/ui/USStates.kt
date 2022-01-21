package com.carerevolutions.ui

import com.carerevolutions.myapplication.ResultError
import com.carerevolutions.myapplication.ResultSuccess
import com.carerevolutions.network.CountrySubdivision
import com.carerevolutions.network.CareRevService
import com.fasterxml.jackson.module.kotlin.readValue

object USStates {

    private val cache: MutableList<CountrySubdivision> = mutableListOf()

    val all: List<CountrySubdivision> get() = cache

    fun get(onLoad: (states: List<CountrySubdivision>) -> Unit) {
        if (cache.size > 0) {
            onLoad(cache)
            return
        }
        val path = "country_subdivisions?country_code=US"
        CareRevService.get(path) { result ->
            println("#### API: Resp - $result")
            when (result) {
                is ResultSuccess -> {
                    val states = result.json.getJSONArray("country_subdivisions")
                    println("#### API: Resp - $states")
                    for (i in 0 until states.length()) {
                        try {
                            val json = states.getJSONObject(i)
                            val state = CareRevApplication.mapper.readValue<CountrySubdivision>(json.toString())
                            cache.add(state)
                        } catch (e: Exception) {}
                    }
                    onLoad(cache)
                }
                is ResultError -> onLoad(listOf())
            }
        }
    }
}

package com.vavilon.model.dataHandlers

import com.vavilon.storage.local.entities.Plan
import com.vavilon.storage.local.entities.Source
import com.vavilon.storage.local.entities.Transaction

class PlanWrapper(private var plan: Plan) {
    private var items: Map<Source, List<Transaction>> = HashMap()

    fun updateSource(source:Source){
        if(items.keys.contains(source)){
            items.keys.filter { it.sourceId==source.sourceId

            }.first()
        }
    }
}
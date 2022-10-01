package com.example.travelassistant.core.network.model

import com.example.travelassistant.features.cities.domain.model.CityDomain

data class Location(val slug: String, val name: String) {

    fun toDomain(): CityDomain {
        return CityDomain(id = this.slug, name = this.name, imageUrl = null)
    }

}

// https://kudago.com/public-api/v1.4/locations/
// [{"slug":"ekb","name":"Екатеринбург"},
// {"slug":"krasnoyarsk","name":"Красноярск"},
// {"slug":"krd","name":"Краснодар"},
// {"slug":"kzn","name":"Казань"},
// {"slug":"mns","name":"Минск"},
// {"slug":"msk","name":"Москва"},
// {"slug":"nnv","name":"Нижний Новгород"},
// {"slug":"nsk","name":"Новосибирск"},
// {"slug":"online","name":"Онлайн"}, // WTF
// {"slug":"sochi","name":"Сочи"},
// {"slug":"spb","name":"Санкт-Петербург"}]
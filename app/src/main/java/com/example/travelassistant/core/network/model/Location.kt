package com.example.travelassistant.core.network.model

data class Location(val slug: String, val name: String)


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
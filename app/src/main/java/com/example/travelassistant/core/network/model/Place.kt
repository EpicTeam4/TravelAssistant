package com.example.travelassistant.core.network.model

data class Place(val id: Int,
                 val title: String?,
                 val short_title: String?,
                 val slug: String?,
                 val address: String?,
                 val location: String?,
                 val timetable: String?,
                 val phone: String?,
                 val is_stub: Boolean?,
                 val images: List<Image?>?,
                 val description: String?,
                 val body_text: String?,
                 val site_url: String?,
                 val foreign_url: String?,
                 val coords: Coords?,
                 val subway: String?,
                 val favorites_count: Int?,
                 val comments_count: Int?,
                 val is_closed: Boolean?,
                 val categories: List<String?>?,
                 val tags: List<String?>?
)

data class Image(val image: String?, val source: Source?)
data class Source(val name: String?, val link: String?)
data class Coords(val lat: Float?, val lon: Float?)

// fields включить в выдачу только указанные поля, можно несколько через запятую
// По умолчанию: id,title,slug,address,location,site_url,is_closed
// id - идентификатор
// title - название
// short_title - короткое название
// slug - слаг
// address - адрес
// location - город
// timetable - расписание
// phone - телефон
// is_stub - является ли заглушкой
// images - галерея места
// description - описание
// body_text - полное описание
// site_url - адрес места на сайте kudago.com
// foreign_url - сайт места
// coords - координаты места
// subway - метро рядом
// favorites_count - число пользователей, добавивших место в избранное
// comments_count - число комментариев
// is_closed - закрыто ли место
// categories - список категорий
// tags - тэги места

// id,title,short_title,slug,address,location,timetable,phone,is_stub,images,description,body_text,site_url,foreign_url,coords,subway,favorites_count,comments_count,is_closed,categories,tags


// {"id":157,"title":"Музей современного искусства Эрарта","slug":"erarta","address":"29-я линия В. О., д. 2","timetable":"пн, ср–вс 10:00–22:00","phone":"+7 812 324-08-09","is_stub":false,"body_text":"<p>Перефразируя известную присказку, можно сказать, что музей начинается с фасада. У входа в <strong>Эрарту</strong> гостей встречают две статуи, объясняющие название <a href=\"http://kudago.com/spb/museums/\">музея</a> — фигуры богинь Era и Arta. Здание, возведённое в стиле сталинского неоклассицизма в 1951 году, внутри основательно перестроено. На первом этаже располагается грот, где сочетание красного и серого — основной лейтмотив. Помимо стойки ресепшена на первом этаже находится gift shop, книжный магазин с широким выбором изданий об искусстве и магазин принтов и жикле, которые создаются в мастерской музея.</p>\n<p>Для тех, кто хочет лучше разбираться в современном искусстве, по Музейному крылу, в котором находится постоянная экспозиция, ежедневно (кроме вторника) в 12:00, 16:00 и 19:00 проводятся часовые обзорные <a class=\"external-link\" href=\"https://www.erarta.com/ru/visit/guidedtours/\" rel=\"nofollow\" target=\"_blank\">экскурсии</a>. Самостоятельным посетителям рекомендуется обращать внимание на описания объектов. К примеру, без объяснения не так просто понять смысл произведения science art «Модель биполярной активности».</p>\n<p><img src=\"//kudago.com/media/thumbs/xl/images/place/a8/86/a8867799ba19496b5656f992944edf16.JPG\"></p>\n<p>Пройдя через большой зал первого этажа, посетители выходят к комнатам U-space. Тотальные инсталляции U-space погружают гостей в определённую атмосферу посредством света, звуков и запахов. Особой популярностью пользуется «Вишнёвый сад». Для каждого U-space надо покупать отдельный билет на ресепшен.</p>\n<p><img src=\"//kudago.com/media/thumbs/xl/images/place/f9/d5/f9d51ec52865f04a45a2fefa19b3dbcb.jpg\"></p>\n<p>На втором этаже Эрарты также находятся комнаты U-space и зал Елены Фигуриной, в котором демонстрируется мультимедийный спектакль «Отчего люди не летают?». Третий этаж занимают два кинозала, где можно увидеть истории об оживших картинах и мультсериал «Чёрный квадрат». Ещё два U-space расположены на четвёртом этаже. Здесь же экспонируются многочисленные экспериментальные картины, при создании которых использовались битум, мазут, кузбасс-лак и прочие нехудожественные средства и материалы. На пятом этаже посетителей ждут внушительные по размерам инсталляции и картины.</p>\n<p>На цокольном этаже Музейного крыла размещается ресторан Erarta. В тёплое время года заведение открывает летнюю террасу.</p>\n<p><img src=\"//kudago.com/media/thumbs/xl/images/place/8b/79/8b795458bf484fbb85d62679ad59c934.jpg\"></p>\n<p>В противоположном — Выставочном — крыле также пять этажей, и все они отданы под временные выставки, которые полностью обновляются примерно раз в 2–2,5 месяца. Среди недавних крупных событий — «Скульптуры Сальвадора Дали», «Стиль Ducati», «Под одеждой. История дизайна нижнего белья» (проект из лондонского музея Виктории и Альберта), «Восхитительный мир Феллини» и другие.</p>\n<p>Между Музейным крылом и временными экспозициями есть только два перехода — на первом и на третьем этажах. В переходе третьего этажа можно посмотреть видеоинтервью с художниками, порисовать на интерактивном экране, отдохнуть в кафе. В галерее третьего этажа находятся «соТы» — боксы, в которых каждый желающий может выставить то, что для него важно.</p>\n<p><img src=\"//kudago.com/media/thumbs/xl/images/place/9d/cd/9dcd1d7514c89b7452ad617af7c00baa.jpg\"></p>\n<p>30 июня 2015 года Эрарта стала частью Google Art Project — уникального виртуального проекта, благодаря которому миллионы пользователей со всего мира могут заочно «посещать» музеи и галереи, расположенные за тысячи километров от них. Увидеть виртуальную <a href=\"http://kudago.com/spb/exhibitions/\">экспозицию</a> Эрарты, а затем заглянуть в Лувр и Метрополитен-музей можно <a class=\"external-link\" href=\"https://www.google.com/culturalinstitute/collection/erarta-museum?projectId=art-project&hl=ru\" rel=\"nofollow\" target=\"_blank\">по ссылке</a>.</p>\n","description":"<p>От музея современного искусства заранее ожидаешь чего-то новаторского, решительного. В Эрарте можно встретиться и с современными технологиями, и со смелыми экспериментами, и с подчас шокирующими объектами contemporary art.</p>","site_url":"https://kudago.com/spb/place/erarta/","foreign_url":"http://www.erarta.com/","coords":{"lat":59.93178799999995,"lon":30.251674},"subway":"Василеостровская, Приморская, Спортивная","favorites_count":952,"images":[{"image":"https://kudago.com/media/images/place/83/20/83206505e69f74906bd996c42c4c0fc9.jpg","source":{"name":"vk.com","link":"https://vk.com/album-19191317_00"}},{"image":"https://kudago.com/media/images/place/de/89/de89ef20687ea507e57107bfdf0e5735.jpg","source":{"name":"vk.com","link":"https://vk.com/album-19191317_00"}},{"image":"https://kudago.com/media/images/place/20/80/2080284223698961282551f7c87f6685.jpg","source":{"name":"vk.com","link":"https://vk.com/album-19191317_00"}},{"image":"https://kudago.com/media/images/place/bd/f6/bdf60df83d98b8337908752a85639de6.jpg","source":{"name":"vk.com","link":"https://vk.com/album-19191317_00"}},{"image":"https://kudago.com/media/images/place/07/a4/07a4791f04d063dddb49abcdbfa6daa0.jpg","source":{"name":"vk.com","link":"https://vk.com/album-19191317_00"}},{"image":"https://kudago.com/media/images/place/23/b4/23b46c37ccb99e5f942184f820d8a445.jpg","source":{"name":"vk.com","link":"https://vk.com/album-19191317_00"}},{"image":"https://kudago.com/media/images/place/ef/96/ef96bbafdfcba465421cd98957cdb68b.jpg","source":{"name":"vk.com","link":"https://vk.com/album-19191317_00"}}],"comments_count":20,"is_closed":false,"categories":["museums"],"short_title":"Эрарта","tags":["селфи","лучшее в образовании","музеи и галереи","осень","6+"],"location":"spb"}
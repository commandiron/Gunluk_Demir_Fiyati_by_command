package com.example.gunluk_demir_fiyati_by_command.data.repository


import com.example.gunluk_demir_fiyati_by_command.core.Constants.DATA_URL
import com.example.gunluk_demir_fiyati_by_command.domain.model.DemirFiyat
import com.example.gunluk_demir_fiyati_by_command.domain.repository.AppRepository
import com.example.gunluk_demir_fiyati_by_command.domain.model.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepositoryImpl @Inject constructor(): AppRepository {
    override fun getData(): Flow<Response<List<DemirFiyat>>> = flow {


        try {
            emit(Response.Loading)

            var demirFiyatList = listOf<DemirFiyat>()

            withContext(Dispatchers.IO) {

                var dataForLoop: Elements? = null

                val job = launch {
                    val doc = Jsoup.connect(DATA_URL).get()
                    val fullData = doc
                        .select("body > div.body > div > div:nth-child(1) > div > div.col-md-8.mb-5.mb-lg-0.order-first.order-md-2 > div.card.analiztablocard > div.card-body > div > table > tbody")
                    dataForLoop = fullData.first()?.children()?.select("tr")!!
                }
                job.invokeOnCompletion {
                    for (i in dataForLoop!!) {
                        val dataChildren = i.children()
                        val demirFiyat =
                            DemirFiyat(
                                dataChildren.select("th").text(),
                                dataChildren.select("td:nth-child(2)").text(),
                                dataChildren.select("td:nth-child(3)").text(),
                                dataChildren.select("td:nth-child(4)").text()
                            )
                        demirFiyatList += demirFiyat
                    }

                }
            }

            emit(Response.Success(demirFiyatList))

        }catch (e: Exception){
            emit(Response.Error(e.localizedMessage ?: "ERROR MESSAGE"))
        }
    }
}
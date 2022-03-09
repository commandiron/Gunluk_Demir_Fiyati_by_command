package com.example.gunluk_demir_fiyati_by_command.data.repository


import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.gunluk_demir_fiyati_by_command.core.Constants.DATA_URL
import com.example.gunluk_demir_fiyati_by_command.domain.model.DemirFiyat
import com.example.gunluk_demir_fiyati_by_command.domain.repository.AppRepository
import com.example.gunluk_demir_fiyati_by_command.domain.model.Response
import com.example.gunluk_demir_fiyati_by_command.domain.service.db.AppDao
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.*
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepositoryImpl @Inject constructor(
    private val appDao: AppDao
): AppRepository {
    override suspend fun getData(): Flow<Response<List<DemirFiyat>>> = callbackFlow {


        try {
            send(Response.Loading)

            var demirFiyatList = listOf<DemirFiyat>()

            withContext(Dispatchers.IO) {

                var dataForLoop: Elements? = null

                val job = launch {
                    val doc = Jsoup.connect(DATA_URL).get()

                    val fullData = doc
                        .select("body > div.body > div > div:nth-child(1) > div > div.col-md-8.mb-5.mb-lg-0.order-first.order-md-2 > div.card.analiztablocard > div.card-body > div > table > tbody")
                    dataForLoop = fullData.first()?.children()?.select("tr")!! }
                job.invokeOnCompletion {

                    if(dataForLoop != null){
                        var count = 1
                        for (i in dataForLoop!!) {
                            val dataChildren = i.children()
                            val demirFiyat =
                                DemirFiyat(
                                    count,
                                    dataChildren.select("th").text(),
                                    dataChildren.select("td:nth-child(2)").text(),
                                    dataChildren.select("td:nth-child(3)").text(),
                                    dataChildren.select("td:nth-child(4)").text(),
                                    null

                                )
                            demirFiyatList += demirFiyat
                            count++
                        }
                    }else{
                        trySendBlocking(Response.Error("null"))
                    }
                }
            }
            send(Response.Success(demirFiyatList))

        }catch (e: Exception){
            send(Response.Error(e.localizedMessage ?: "ERROR MESSAGE"))
        }

        awaitClose {
            cancel()
        }
    }

    override suspend fun insertDataToDb(demirFiyat: DemirFiyat): Flow<Response<Boolean>> = flow {
        appDao.deleteAll()
        appDao.insert(demirFiyat)
        emit(Response.Success(true))
    }

    override suspend fun getAllDataFromDb(): Flow<Response<List<DemirFiyat>>> = flow {
        emit(Response.Success(appDao.getAll()))
    }
}
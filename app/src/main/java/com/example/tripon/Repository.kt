package com.example.tripon

import com.example.tripon.model.TrainBwStaion.TrainBwStation
import com.example.tripon.model.emoji.EmojiItem
import com.example.tripon.model.flight.Aggregation
import com.example.tripon.model.flight.Flights
import com.example.tripon.model.hotel.Hotel
import com.example.tripon.model.hotel.Result
import com.example.tripon.model.liveTrain.LiveTrain
import com.example.tripon.model.stationCode.Data
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.call.receive
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class StationRepository {

    suspend fun searchStation(query: String): String {
        val client = HttpClient()

        val response: HttpResponse = client.get {
            url("https://irctc1.p.rapidapi.com/api/v1/searchStation?query=$query")
            headers {
                append("x-rapidapi-key", "db03fb4781mshc1a7df487511276p15f812jsn973ee30b0493")
                append("x-rapidapi-host", "irctc1.p.rapidapi.com")
            }
        }

        val responseBody = response.body<Data>()

        client.close()

        return responseBody.code
    }

    suspend fun trainBwStation(query: String,query1: String,query2: String):List<com.example.tripon.model.TrainBwStaion.Data>{
        val client = HttpClient()

        val result: HttpResponse = client.get {
            url("https://irctc1.p.rapidapi.com/api/v3/trainBetweenStations?fromStationCode=$query&toStationCode=$query1&dateOfJourney=$query2")
            headers {
                append("x-rapidapi-key", "db03fb4781mshc1a7df487511276p15f812jsn973ee30b0493")
                append("x-rapidapi-host", "irctc1.p.rapidapi.com")
            }
        }
        val responseBody = result.body<TrainBwStation>()
        client.close()
        return responseBody.data
    }
    suspend fun liveTrainStatus(query: String): com.example.tripon.model.liveTrain.Data{
        val client = HttpClient()

        val result: HttpResponse = client.get {
            url("https://irctc1.p.rapidapi.com/api/v1/liveTrainStatus?trainNo=$query&startDay=1")
            headers {
                append("x-rapidapi-key", "db03fb4781mshc1a7df487511276p15f812jsn973ee30b0493")
                append("x-rapidapi-host", "irctc1.p.rapidapi.com")
            }
        }
        val responseBody = result.body<com.example.tripon.model.liveTrain.Data>()
        client.close()
        return responseBody
    }
    suspend fun hotel(query: String,query1: String,query2: String,query3: String):List<Result>{
        val client = HttpClient()

        val request :HttpResponse = client.get{
            url("https://booking-com.p.rapidapi.com/v2/hotels/search-by-coordinates?checkin_date=$query&room_number=1&checkout_date=$query1&page_number=0&latitude=$query2&adults_number=1&units=metric&filter_by_currency=EUR&children_number=2&order_by=popularity&locale=en-gb&include_adjacency=true&longitude=$query3&children_ages=5%2C0&categories_filter_ids=class%3A%3A2%2Cclass%3A%3A4%2Cfree_cancellation%3A%3A1")
            headers{
                append("x-rapidapi-key", "9b83f1f1e7msh379006fa3ef3713p1961d5jsn00f292654ece")
                append("x-rapidapi-host", "booking-com.p.rapidapi.com")
            }
        }
        val responseBody = request.body<List<Result>>()
        client.close()
        return responseBody
    }

    suspend fun flight(query: String,query1: String): Aggregation {
        val client = HttpClient()

        val request:HttpResponse = client.get {
            url("https://booking-com15.p.rapidapi.com/api/v1/flights/searchFlights?fromId=BOM.AIRPORT&toId=DEL.AIRPORT&departDate=$query&returnDate=$query1&pageNo=1&adults=1&children=0%2C17&sort=BEST&cabinClass=ECONOMY&currency_code=AED")
            headers {
                append("x-rapidapi-key", "9b83f1f1e7msh379006fa3ef3713p1961d5jsn00f292654ece")
                append("x-rapidapi-host", "booking-com15.p.rapidapi.com")
            }
        }
        val responseBody = request.body<Aggregation>()
        client.close()
        return responseBody
    }
    suspend fun fetchEmoji(offset: Int, limit: Int): List<EmojiItem> {
        val client = HttpClient()
        val request: HttpResponse = client.get {
            url("https://emoji-api1.p.rapidapi.com/emojis/category/smileys-n-emotion?offset=$offset&limit=$limit") {
                headers {
                    append("x-rapidapi-key", "9b83f1f1e7msh379006fa3ef3713p1961d5jsn00f292654ece")
                    append("x-rapidapi-host", "emoji-api1.p.rapidapi.com")
                }
            }
        }
        val responseBody = request.body<List<EmojiItem>>()
        client.close()
        return responseBody
    }
}

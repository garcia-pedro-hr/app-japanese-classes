package com.phgarcia.msjc.services

import com.phgarcia.msjc.models.Lesson
import com.phgarcia.msjc.models.Module
import com.phgarcia.msjc.utils.openAsset
import com.squareup.moshi.*
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.lang.reflect.Type

class SyllabusDecoderService {

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    /**
     * Fetch Module objects from modules JSON file
     */
    fun getModules(): List<DecodedModule> {
        val type: Type = Types.newParameterizedType(List::class.java, DecodedModule::class.java)
        val adapter: JsonAdapter<List<DecodedModule>> = moshi.adapter(type)
        val json: String = openAsset("modules.txt")
        return adapter.fromJson(json) ?: listOf()
    }

    /**
     * Fetch Lesson objects from lessons JSON file
     */
    fun getLessons(): List<DecodedLesson> {
        val type: Type = Types.newParameterizedType(List::class.java, DecodedLesson::class.java)
        val adapter: JsonAdapter<List<DecodedLesson>> = moshi.adapter(type)
        val json: String = openAsset("lessons.txt")
        return adapter.fromJson(json) ?: listOf()
    }

}

@JsonClass(generateAdapter = true)
data class DecodedModule(
    @Json(name = "module") val moduleNumber: Long,
    @Json(name = "title_pt") val portugueseTitle: String,
    @Json(name = "title_jp") val japaneseTitle: String
)

fun DecodedModule.asDatabaseModel(): Module =
    Module(moduleNumber, portugueseTitle, japaneseTitle)

@JsonClass(generateAdapter = true)
data class DecodedLesson(
    @Json(name = "module") val moduleNumber: Long,
    @Json(name = "lesson") val lessonNumber: Long,
    @Json(name = "title_pt") val portugueseTitle: String,
    @Json(name = "title_jp") val japaneseTitle: String
)

fun DecodedLesson.asDatabaseModel(): Lesson =
    Lesson(lessonNumber, moduleNumber, portugueseTitle, japaneseTitle)
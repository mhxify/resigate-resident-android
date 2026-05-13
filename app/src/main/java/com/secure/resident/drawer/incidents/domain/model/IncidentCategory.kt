package com.secure.resident.drawer.incidents.domain.model

data class IncidentCategory(
    val categoryId: String,
    val categoryName: String
)

val incidentCategories = listOf(

    IncidentCategory(
        categoryId = "26455409-a132-4e34-bb41-ff21dcba1320",
        categoryName = "Emergency"
    ),

    IncidentCategory(
        categoryId = "91d0af72-2cc6-4d14-8032-fbdc95e91dc6",
        categoryName = "Security"
    ),

    IncidentCategory(
        categoryId = "7cd19ac0-a3f4-4461-9205-9ac31b76a1d8",
        categoryName = "Maintenance"
    ),

    IncidentCategory(
        categoryId = "7dc74bc2-c7d7-4a8d-8148-831bb7004e5e",
        categoryName = "Medical"
    ),

    IncidentCategory(
        categoryId = "1e353bd6-92fe-4295-a380-fadaf9dc9432",
        categoryName = "Fire"
    ),

    IncidentCategory(
        categoryId = "b53668a4-3227-4d96-a85d-1b647b901d34",
        categoryName = "Parking"
    ),

    IncidentCategory(
        categoryId = "afa5b026-aaf8-4652-a481-e6e356c7b91a",
        categoryName = "Noise"
    ),

    IncidentCategory(
        categoryId = "9672772a-dc71-4862-9a48-3db366b0ff66",
        categoryName = "Other"
    )
)
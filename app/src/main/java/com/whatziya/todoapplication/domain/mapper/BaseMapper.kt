package com.whatziya.todoapplication.domain.mapper

interface BaseMapper<DTO, UI_MODEL> {
    fun toUIModel(dto: DTO): UI_MODEL
    fun toDTO(uiModel: UI_MODEL): DTO

}
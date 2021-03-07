package com.mapper;

import com.domain.rent.Rent;
import com.dto.RentResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RentMapper {

    RentMapper INSTANCE = Mappers.getMapper(RentMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "bookId", target = "bookId")
    @Mapping(source = "bookTitle", target = "bookTitle")
    @Mapping(source = "bookAuthor", target = "bookAuthor")
    @Mapping(source = "identifier", target = "identifier")
    @Mapping(source = "rentStatus", target = "rentStatus")
    RentResponseDto rentToRentResponseDto(Rent rent);

}

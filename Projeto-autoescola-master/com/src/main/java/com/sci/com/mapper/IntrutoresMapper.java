package com.sci.com.mapper;

import com.sci.com.dto.InstrutoresDto;
import com.sci.com.entities.InstrutoresEntity;
import lombok.experimental.UtilityClass;
import org.modelmapper.ModelMapper;

import java.util.function.Supplier;

@UtilityClass
public class IntrutoresMapper {

    private final Supplier<ModelMapper> modelMapperSupplier = ModelMapper:: new;

    public InstrutoresDto  InstrutoresToDto(InstrutoresEntity instrutoresEntity){
        return modelMapperSupplier.get().map(instrutoresEntity, InstrutoresDto.class);
    }

    public InstrutoresEntity DtoToEntity(InstrutoresDto instrutoresDto){
        return modelMapperSupplier.get().map(instrutoresDto, InstrutoresEntity.class);
    }
}

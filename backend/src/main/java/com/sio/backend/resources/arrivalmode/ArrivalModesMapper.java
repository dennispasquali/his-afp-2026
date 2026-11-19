package com.sio.backend.resources.arrivalmode;


import com.sio.backend.resources.pathology.Pathologies;
import com.sio.backend.resources.pathology.PathologiesDTO;
import org.springframework.stereotype.Component;

/**
 * Componente Mapper per la conversione bidirezionale tra l'entità JPA {@link ArrivalModes}
 * e il relativo Data Transfer Object {@link ArrivalModesDTO}.
 */
@Component
public class ArrivalModesMapper {
    /**
     * Converte un'entità {@link ArrivalModes} nel rispettivo {@link ArrivalModesDTO}.
     *
     * @param entity l'entità di persistenza da convertire
     * @return il DTO popolato con i dati dell'entità, oppure null se l'input è null
     */
    public ArrivalModesDTO toDTO(ArrivalModes entity){
        if (entity == null) return null;

        return new ArrivalModesDTO(
                entity.getCode(),
                entity.getDescription()
        );
    }

    /**
         * Converte un {@link ArrivalModesDTO} nella rispettiva entità JPA {@link ArrivalModes}.
     *
     * @param dto il DTO in ingresso da convertire
     * @return l'entità popolata con i dati del DTO, oppure null se l'input è null
     */
    public ArrivalModes toEntity(ArrivalModesDTO dto){
        if (dto == null) return null;

        ArrivalModes entity = new ArrivalModes();
        entity.setCode(dto.code());
        entity.setDescription(dto.description());

        return entity;
    }
}

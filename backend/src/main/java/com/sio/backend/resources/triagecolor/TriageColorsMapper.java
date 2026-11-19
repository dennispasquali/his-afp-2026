package com.sio.backend.resources.triagecolor;

import com.sio.backend.resources.arrivalmode.ArrivalModes;
import com.sio.backend.resources.arrivalmode.ArrivalModesDTO;
import com.sio.backend.resources.pathology.Pathologies;
import com.sio.backend.resources.pathology.PathologiesDTO;
import org.springframework.stereotype.Component;

/**
 * Componente Mapper per la conversione bidirezionale tra l'entità JPA {@link TriageColors}
 * e il relativo Data Transfer Object {@link TriageColorsDTO}.
 */
@Component
public class TriageColorsMapper {
    /**
     * Converte un'entità {@link TriageColors} nel rispettivo {@link TriageColorsDTO}.
     *
     * @param entity l'entità di persistenza da convertire
     * @return il DTO popolato con i dati dell'entità, oppure null se l'input è null
     */
    public TriageColorsDTO toDTO(TriageColors entity){
        if (entity == null) return null;

        return new TriageColorsDTO(
                entity.getCode(),
                entity.getDisplayName(),
                entity.getPriority(),
                entity.getHexValue()
        );
    }

    /**
     * Converte un {@link TriageColorsDTO} nella rispettiva entità JPA {@link TriageColors}.
     *
     * @param dto il DTO in ingresso da convertire
     * @return l'entità popolata con i dati del DTO, oppure null se l'input è null
     */
    public TriageColors toEntity(TriageColorsDTO dto){
        if (dto == null) return null;

        TriageColors entity = new TriageColors();
        entity.setCode(dto.code());
        entity.setDisplayName(dto.displayName());
        entity.setPriority(dto.priority());
        entity.setHexValue(dto.hexValue());

        return entity;
    }
}

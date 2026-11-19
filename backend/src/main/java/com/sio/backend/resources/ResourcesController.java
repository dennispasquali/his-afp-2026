package com.sio.backend.resources;

import com.sio.backend.generics.ApiResponse;
import com.sio.backend.resources.arrivalmode.ArrivalModes;
import com.sio.backend.resources.arrivalmode.ArrivalModesDTO;
import com.sio.backend.resources.arrivalmode.ArrivalModesService;
import com.sio.backend.resources.pathology.PathologiesDTO;
import com.sio.backend.resources.pathology.PathologiesService;
import com.sio.backend.resources.triagecolor.TriageColors;
import com.sio.backend.resources.triagecolor.TriageColorsDTO;
import com.sio.backend.resources.triagecolor.TriageColorsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


/**
 * Controller REST per la gestione delle risorse cliniche e di sistema del progetto SIO[cite: 9].
 * Espone gli endpoint per le operazioni CRUD sulle patologie[cite: 9].
 */
@RestController
@RequestMapping("/resources")
@Tag(name="Gestione risorse SIO", description = "Endpoint per la gestione delle risorse SIO")
class ResourcesController {
    private final PathologiesService pathologiesService;
    private final ArrivalModesService arrivalModesService;
    private final TriageColorsService triageColorsService;

    public ResourcesController(PathologiesService pathologiesService, ArrivalModesService arrivalModesService, TriageColorsService triageColorsService) {
        this.pathologiesService=pathologiesService;
        this.arrivalModesService=arrivalModesService;
        this.triageColorsService=triageColorsService;
    }

    /**
     * Recupera un elenco paginato di tutte le patologie registrate[cite: 9].
     *
     * @param pageable parametri di paginazione e ordinamento[cite: 9]
     * @return un'istanza di {@link ApiResponse} contenente la pagina di {@link PathologiesDTO}[cite: 9]
     */
    @Operation(summary = "Recupera tutte le patologie", description = "Recupero di tutte le patologie presenti a DB con supporto alla paginazione")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Lista di patologie recuperata con successo"
            )
    })
    @GetMapping("/pathologies")
    public ApiResponse<Page<PathologiesDTO>> getAllPathologies(
//            @PageableDefault(size = 10, sort = "code", direction = Sort.Direction.ASC)
            Pageable pageable
    ) {
        return ApiResponse.success(
                pathologiesService.getAllPathologiesPaginated(pageable)
        );
    }

    /**
     * Recupera una singola patologia tramite il codice identificativo univoco[cite: 9].
     *
     * @param code il codice identificativo della patologia[cite: 9]
     * @return un'istanza di {@link ApiResponse} contenente il {@link PathologiesDTO} corrispondente[cite: 9]
     */
    @Operation(
            summary = "Recupera una patologia dato il suo codice",
            description = "Recupero di una patologia specifica tramite il relativo codice"
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Patologia trovata"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Patologia non trovata")
    })
    @GetMapping("/pathologies/{code}")
    public ApiResponse<PathologiesDTO> getPathologiesByCode(
            @Parameter(description = "Codice identificativo della patologia", example = "C02")
            @Valid
            @PathVariable
            String code
    ){
        PathologiesDTO dto = pathologiesService.getPathologyById(code);

        return ApiResponse.success(dto);
    }

    /**
     * Crea una nuova patologia nel sistema[cite: 9].
     *
     * @param dto il DTO contenente i dati della patologia da creare[cite: 9]
     * @return il {@link PathologiesDTO} della patologia appena creata[cite: 9]
     */
    @Operation(summary = "Crea una nuova patologia", description = "Inserisce una nuova patologia nel database")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Patologia creata con successo"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Dati non validi o codice già esistente")
    })
    @PostMapping("/pathologies")
    public PathologiesDTO createPathology(@Valid @RequestBody PathologiesDTO dto){
        return pathologiesService.createNewPathology(dto);
    }

    /**
     * Elimina una patologia esistente in base al codice[cite: 9].
     *
     * @param code il codice identificativo della patologia da rimuovere[cite: 9]
     * @return un'istanza di {@link ApiResponse} vuota con esito positivo[cite: 9]
     */
    @Operation(summary = "Elimina una patologia", description = "Rimuove una patologia dal database tramite il codice")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Patologia eliminata con successo")
    })
    @DeleteMapping("/pathologies")
    public ApiResponse<Void> deletePathology(
            @Parameter(description = "Codice identificativo della patologia da eliminare", example = "C02")
            @RequestParam String code
    ){
        pathologiesService.deletePathology(code);

        return ApiResponse.success(null);
    }

    /**
     * Modifica i dati di una patologia esistente[cite: 9].
     *
     * @param dto il DTO contenente i campi aggiornati[cite: 9]
     * @return un'istanza di {@link ApiResponse} con il {@link PathologiesDTO} aggiornato[cite: 9]
     */
    @Operation(summary = "Modifica una patologia", description = "Aggiorna la descrizione di una patologia esistente")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Patologia aggiornata con successo"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Patologia non trovata")
    })
    @PutMapping("/pathologies")
    public ApiResponse<PathologiesDTO> editPathology(@Valid @RequestBody PathologiesDTO dto){
        PathologiesDTO newDto = pathologiesService.editPathology(dto);
        return ApiResponse.success(newDto);
    }

    /**
     * Endpoint di utilità per simulare un errore del server a fini di test e debug[cite: 9].
     *
     * @return non restituisce valori, solleva un'eccezione di test[cite: 9]
     * @throws Exception eccezione generica simulata[cite: 9]
     */
    @Operation(summary = "Simula un errore", description = "Endpoint di test per verificare la gestione globale delle eccezioni")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Errore interno simulato del server")
    })
    @GetMapping("/sim-error")
    public ApiResponse<PathologiesDTO> getSimError() throws Exception {
        throw new Exception("è scoppiato tutto!");
//        throw new EntityNotFoundException("Patologia non trovata!");
//        throw new IllegalArgumentException("Dati non validi");
//        return ApiResponse.error(
//                "Messaggio di errore simulato",
//                HttpStatus.INTERNAL_SERVER_ERROR.value()
//                );
    }

    /**
     * Recupera un elenco paginato di tutte le modalita di arrivo registrate[cite: 9].
     *
     * @param pageable parametri di paginazione e ordinamento[cite: 9]
     * @return un'istanza di {@link ApiResponse} contenente la pagina di {@link ArrivalModesDTO}[cite: 9]
     */
    @Operation(summary = "Recupera tutte le modalira di arrivo", description = "Recupero di tutte le modalita di arrivo presenti a DB con supporto alla paginazione")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Lista di modalita di arrivo recuperata con successo"
            )
    })
    @GetMapping("/arrival-modes")
    public ApiResponse<Page<ArrivalModesDTO>> getAllArrivalModesPaginated(Pageable pageable) {
        return ApiResponse.success(
                arrivalModesService.getAllArrivalModesPaginated(pageable)
        );
    }


     /**
        *  Recupera una singola modalita di arrivo tramite il codice identificativo univoco[cite: 9].
        *
        * @param code il codice identificativo della modalita di arrivo [cite: 9]
        * @return un'istanza di {@link ApiResponse} contenente il {@link PathologiesDTO} corrispondente[cite: 9]
      */
    @Operation(
            summary = "Recupera la modalita di arrivo dato il suo codice",
            description = "Recupero la modalita di arrivo specifica tramite il relativo codice"
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "modalita di arrivo trovata"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "modalita di arrivo non trovata")
    })
    @GetMapping("/arrival-mode/{code}")
    public ApiResponse<ArrivalModesDTO> getArrivalModeById(
            @Parameter(description = "Codice identificativo della modalita di arrivo ", example = "AMB")
            @Valid
            @PathVariable
            String code
    ){
        ArrivalModesDTO dto = arrivalModesService.getArrivalModeById(code);

        return ApiResponse.success(dto);
    }



    /**
     * Crea una nuova modalita di arrivo nel sistema[cite: 9].
     *
     * @param dto il DTO contenente ila modalita di arrivo da creare[cite: 9]
     * @return il {@link ArrivalModesDTO} della modalita di arrivo appena creata[cite: 9]
     */
    @Operation(summary = "Crea una nuova modalita di arrivo", description = "Inserisce una nuova modalita di arrivo nel database")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Modalita di arrivo creata con successo"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Dati non validi o codice già esistente")
    })
    @PostMapping("/arrival-mode")
    public ArrivalModesDTO createArrivalMode(@Valid @RequestBody ArrivalModesDTO dto){
        return arrivalModesService.createNewArrivalMode(dto);
    }


    /**
     * Elimina una modalita di arrivo esistente in base al codice[cite: 9].
     *
     * @param code il codice identificativo della modalita di arrivo da rimuovere[cite: 9]
     * @return un'istanza di {@link ApiResponse} vuota con esito positivo[cite: 9]
     */
    @Operation(summary = "Elimina una modalita di arrivo", description = "Rimuove una modalita di arrivo dal database tramite il codice")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Modalita di arrivo eliminata con successo")
    })
    @DeleteMapping("/arrival-mode")
    public ApiResponse<Void> deleteArrivalMode(
            @Parameter(description = "Codice identificativo della modalita di arrivo da eliminare", example = "AMB")
            @RequestParam String code
    ){
        arrivalModesService.deleteArrivalMode(code);

        return ApiResponse.success(null);
    }



    /**
     * Modifica i dati di una modalita di arrivo esistente[cite: 9].
     *
     * @param dto il DTO contenente i campi aggiornati[cite: 9]
     * @return un'istanza di {@link ApiResponse} con il {@link ArrivalModesDTO} aggiornato[cite: 9]
     */
    @Operation(summary = "Modifica una modalita di arrivo", description = "Aggiorna la descrizione della modalita di arrivo esistente")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Modalita di arrivo aggiornata con successo"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Modalita di arrivo non trovata")
    })
    @PutMapping("/arrival-modes")
    public ApiResponse<ArrivalModesDTO> editArrivalMode(@Valid @RequestBody ArrivalModesDTO dto){
        ArrivalModesDTO newDto = arrivalModesService.editArrivalMode(dto);
        return ApiResponse.success(newDto);
    }




    /**
     * Recupera un elenco paginato di tutti i triage color registrati[cite: 9].
     *
     * @param pageable parametri di paginazione e ordinamento[cite: 9]
     * @return un'istanza di {@link ApiResponse} contenente la pagina di {@link TriageColorsDTO}[cite: 9]
     */
    @Operation(summary = "Recupera tutte le triage color", description = "Recupero di tutte le triage color presenti a DB con supporto alla paginazione")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Lista di triage color recuperata con successo"
            )
    })
    @GetMapping("/triage-colors")
    public ApiResponse<Page<TriageColorsDTO>> getTriageColorsPaginated(Pageable pageable) {
        return ApiResponse.success(
                triageColorsService.getAllTriageColorsPaginated(pageable)
        );
    }


    /**
     *  Recupera un triage color tramite il codice identificativo univoco[cite: 9].
     *
     * @param code il codice identificativo del triage color [cite: 9]
     * @return un'istanza di {@link ApiResponse} contenente il {@link TriageColorsDTO} corrispondente[cite: 9]
     */
    @Operation(
            summary = "Recupera il triage color dato il suo codice",
            description = "Recupero il triage color specifico tramite il relativo codice"
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "triage color trovato"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "triage color non trovato")
    })
    @GetMapping("/triage-colors/{code}")
    public ApiResponse<TriageColorsDTO> getTriageColorById(
            @Parameter(description = "Codice identificativo del triage color", example = "ROSSO")
            @Valid
            @PathVariable
            String code
    ){
        TriageColorsDTO dto = triageColorsService.getTriageColorById(code);

        return ApiResponse.success(dto);
    }



    /**
     * Crea una nuovo triage color nel sistema[cite: 9].
     *
     * @param dto il DTO contenente il triage color da creare[cite: 9]
     * @return il {@link TriageColorsDTO} triage color appena creato[cite: 9]
     */
    @Operation(summary = "Crea un nuovo triage color", description = "Inserisce un nuovo triage color nel database")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "triage color creato con successo"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Dati non validi o codice già esistente")
    })
    @PostMapping("/triage-colors")
    public TriageColorsDTO createTriageColor(@Valid @RequestBody TriageColorsDTO dto){
        return triageColorsService.createNewTriageColor(dto);
    }



    /**
     * Elimina un triage color esistente in base al codice[cite: 9].
     *
     * @param code il codice identificativo del triage color da rimuovere[cite: 9]
     * @return un'istanza di {@link ApiResponse} vuota con esito positivo[cite: 9]
     */
    @Operation(summary = "Elimina un triage color", description = "Rimuove un triage color dal database tramite il codice")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Triage color eliminato con successo")
    })
    @DeleteMapping("/triage-colors")
    public ApiResponse<Void> deleteTriageColor(
            @Parameter(description = "Codice identificativo del triage color da eliminare", example = "ROSSO")
            @RequestParam String code
    ){
                triageColorsService.deleteTriageColor(code);

        return ApiResponse.success(null);
    }



    /**
     * Modifica i dati di un triage color esistente[cite: 9].
     *
     * @param dto il DTO contenente i campi aggiornati[cite: 9]
     * @return un'istanza di {@link ApiResponse} con il {@link TriageColorsDTO} aggiornato[cite: 9]
     */
    @Operation(summary = "Modifica un triage color", description = "Aggiorna la descrizione di un triage color esistente")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Triage color aggiornato con successo"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Triage color non trovato")
    })
    @PutMapping("/triage-colors")
    public ApiResponse<TriageColorsDTO> editTriageColor(@Valid @RequestBody TriageColorsDTO dto){
        TriageColorsDTO newDto = triageColorsService.editTriageColor(dto);
        return ApiResponse.success(newDto);
    }

}
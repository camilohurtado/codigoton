package com.codigoton.service.reader;

import com.codigoton.dto.FilterDTO;
import com.codigoton.enumerator.FilterEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * @author Camilo Hurtado
 */
@Service
@RequiredArgsConstructor
public class FileReaderService {

    private final String FILE_NAME = "entrada.txt";

    public List<FilterDTO> getFiltersFromFile() throws IOException {
        Map<String, List<Map<String, String>>> result = getFileEntries();

        return result.entrySet()
                .stream()
                .map(stringMapEntry -> {

                    List<Map<String, String>> values = stringMapEntry.getValue();

                    AtomicReference<FilterDTO> filterDTO = new AtomicReference<>(new FilterDTO());
                    values.stream().forEach(map -> {
                        filterDTO.set(FilterDTO.builder().mesa(stringMapEntry.getKey())
                                .codUbicacionGeo(map.get(FilterEnum.COD_UBICACION_GEO.getCode()))
                                .rangoInicialBalance(map.get(FilterEnum.RANGO_INI_BALANCE.getCode()) != null ? Double.parseDouble(map.get(FilterEnum.RANGO_INI_BALANCE.getCode())) : 0)
                                .rangoFinalBalance(map.get(FilterEnum.RANGO_FIN_BALANCE.getCode()) != null ? Double.parseDouble(map.get(FilterEnum.RANGO_FIN_BALANCE.getCode())) : 0)
                                .tipoDeCliente(map.get(FilterEnum.TIPO_DE_CLIENTE.getCode()) != null ? Integer.parseInt(map.get(FilterEnum.TIPO_DE_CLIENTE.getCode())) : 0)
                                .build());
                    });

                    return filterDTO.get();
                }).collect(Collectors.toList());
    }

    private Map<String, List<Map<String, String>>> getFileEntries() throws IOException {
        File file = ResourceUtils.getFile("classpath:" + FILE_NAME);

        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        Map<String, List<Map<String, String>>> mapping = new HashMap<>();
        String currentLine;
        String mesa = "";

        while ((currentLine = bufferedReader.readLine()) != null) {
            if (currentLine.matches("^<.*>$")) {
                mesa = currentLine;
                mapping.put(mesa, new ArrayList<>());
            }else{
                String[] rawFilters = currentLine.split(":");
                String key = rawFilters[0];
                String value = rawFilters[1];

                Map<String, String> mapValue = new HashMap<>();
                mapValue.put(key, value);

                mapping.get(mesa).add(mapValue);
                //mapping.put(mesa, mapValue);
            }
        }

        return mapping;
    }
}

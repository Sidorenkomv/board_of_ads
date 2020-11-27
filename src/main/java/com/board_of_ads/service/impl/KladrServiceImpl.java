package com.board_of_ads.service.impl;

import com.board_of_ads.models.City;
import com.board_of_ads.models.Region;
import com.board_of_ads.repository.CityRepository;
import com.board_of_ads.repository.RegionRepository;
import com.board_of_ads.service.interfaces.KladrService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@Service
@AllArgsConstructor

public class KladrServiceImpl implements KladrService {

    private final CityRepository cityRepository;
    private final RegionRepository regionRepository;

    @Override
    public String readCellToString(Cell cell) {
        String resultStr= "";

        if ((cell == null || cell.getCellType() == CellType.BLANK)) {
            return resultStr;
        }
        if (cell.getCellType() == CellType.BOOLEAN) {

            return String.valueOf(cell.getBooleanCellValue());
        }
        if (cell.getCellType() == CellType.NUMERIC) {


            return String.valueOf((int)cell.getNumericCellValue());
        }
        if (cell.getCellType() == CellType.STRING) {

            return cell.getStringCellValue();
        }

        return resultStr;

    }

    @Override
    public Region getRegionByRegionNumber(String regionNumber) {
        return regionRepository.findRegionByRegionNumber(regionNumber);
    }

    @Override
    public void saveRegion(Region region) {
        regionRepository.save(region);
    }

    @Override
    public void saveCity(City city) {
        cityRepository.save(city);
    }

    @Override
    public Set<City> getAllCityOfRegion(Region region) {
        return cityRepository.findCitiesByRegion(region);
    }

    @Override
    public boolean existsCityByCityNameAndRegion(String cityName, Region region) {
        return cityRepository.existsCityByNameAndRegion(cityName, region);
    }

    @Override
    public boolean existsRegionByName(String regionName) {
        return regionRepository.existsRegionByName(regionName);
    }

    @Override
    @SneakyThrows
    public void streamKladr() {
        Set<FileInputStream> streamKLADR = new HashSet<>();
        FileInputStream fileInputStream_1 = new FileInputStream("src/main/resources/kladr/KLADR_1.xls");
        streamKLADR.add(fileInputStream_1);
        FileInputStream fileInputStream_2 = new FileInputStream("src/main/resources/kladr/KLADR_2.xls");
        streamKLADR.add(fileInputStream_2);
        FileInputStream fileInputStream_3 = new FileInputStream("src/main/resources/kladr/KLADR_3.xls");
        streamKLADR.add(fileInputStream_3);
        FileInputStream fileInputStream_4 = new FileInputStream("src/main/resources/kladr/KLADR_4.xls");
        streamKLADR.add(fileInputStream_4);
        for (FileInputStream fileInputStream : streamKLADR) {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            Workbook workbook = new HSSFWorkbook(bufferedInputStream);
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (readCellToString(row.getCell(1)).equals("обл")
                        || readCellToString(row.getCell(1)).equals("Респ")
                        || readCellToString(row.getCell(1)).equals("край")
                        || readCellToString(row.getCell(1)).equals("АО")
                        || readCellToString(row.getCell(1)).equals("Аобл")
                        || (readCellToString(row.getCell(1)).equals("г")
                        & (readCellToString(row.getCell(0)).equals("Москва")
                        || readCellToString(row.getCell(0)).equals("Санкт-Петербург")
                        || readCellToString(row.getCell(0)).equals("Байконур")
                        || readCellToString(row.getCell(0)).equals("Севастополь")))) {
                    String regionFormSubject = null;
                    switch (readCellToString(row.getCell(1))) {
                        case "обл":
                            regionFormSubject = "Область";
                            break;
                        case "край":
                            regionFormSubject = "Край";
                            break;
                        case "Респ":
                            regionFormSubject = "Республика";
                            break;
                        case "АО":
                            regionFormSubject = "Автономный округ";
                            break;
                        case "Аобл":
                            regionFormSubject = "Автономная область";
                            break;
                        case "г":
                            regionFormSubject = "Город";
                            break;
                    }
                    if (!regionRepository.existsRegionByName(readCellToString(row.getCell(0)))) {
                        regionRepository.save(new Region(readCellToString(row.getCell(0)), readCellToString(row.getCell(2)).substring(0, 2), regionFormSubject));
                    }
                }
                if (readCellToString(row.getCell(1)).equals("г")) {

                    if (!cityRepository.existsCityByNameAndRegion(readCellToString(row.getCell(0)), regionRepository.findRegionByRegionNumber(readCellToString(row.getCell(2)).substring(0, 2)))) {
                        if (readCellToString(row.getCell(7)).equals("1")) {

                            cityRepository.save(new City(readCellToString(row.getCell(0)), regionRepository.findRegionByRegionNumber(readCellToString( row.getCell(2)).substring(0, 2)), "Город", true));
                        } else {
                            cityRepository.save(new City(readCellToString(row.getCell(0)), regionRepository.findRegionByRegionNumber(readCellToString( row.getCell(2)).substring(0, 2)), "Город", false));
                        }

                    }
                }

            }
            fileInputStream.close();
        }
    }
}

package com.ivoyant.bookmymovie.controller;

import com.ivoyant.bookmymovie.service.DataMigrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/datamigration")
public class DataMigrationController {
    @Autowired
    DataMigrationService dataMigrationService;

    @GetMapping
    public ResponseEntity<String> migrateSqltoNosql(){
        return ResponseEntity.ok().body(dataMigrationService.migrateData());
    }

}

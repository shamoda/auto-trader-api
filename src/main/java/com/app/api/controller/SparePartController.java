package com.app.api.controller;

import com.app.api.exception.AutoTraderException;
import com.app.api.model.SparePart;
import com.app.api.service.SparePartService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class SparePartController {
    private final SparePartService service;

    @Autowired
    public SparePartController(SparePartService service) {
        this.service = service;
    }

    @PostMapping("/spare")
    public ResponseEntity<?> insertSparePart(@RequestParam("email") String email,
                                             @RequestParam("contact") String contact,
                                             @RequestParam("seller") String seller,
                                             @RequestParam("location") String location,
                                             @RequestParam("title") String title,
                                             @RequestParam("price") String price,
                                             @RequestParam("condition") String condition,
                                             @RequestParam("type") String type,
                                             @RequestParam("category") String category,
                                             @RequestParam("additionalInfo") String additionalInfo,
                                             @RequestParam(value = "img1", required = false) MultipartFile img1,
                                             @RequestParam(value = "img2", required = false) MultipartFile img2,
                                             @RequestParam(value = "img3", required = false) MultipartFile img3)
    {
        Long unixTime = System.currentTimeMillis() / 1000L;
        LocalDate date = java.time.LocalDate.now();
        MultipartFile temImg1 = null;
        MultipartFile temImg2 = null;
        MultipartFile temImg3 = null;
        SparePart temObj = new SparePart(unixTime.toString(), email, contact, seller, location, "pending", "", date.toString(), title, price, condition, type, category, additionalInfo, null, null, null);
        if (img1 != null && !img1.isEmpty()) {
            String extension = FilenameUtils.getExtension(img1.getOriginalFilename());
            String img = unixTime+"-1" + "." + extension;
            temImg1 = img1;
            temObj.setImg1(img);
        }
        if (img2 != null && !img2.isEmpty()) {
            String extension = FilenameUtils.getExtension(img2.getOriginalFilename());
            String img = unixTime+"-2" + "." + extension;
            temImg2 = img2;
            temObj.setImg2(img);
        }
        if (img3 != null && !img3.isEmpty()) {
            String extension = FilenameUtils.getExtension(img3.getOriginalFilename());
            String img = unixTime+"-3" + "." + extension;
            temImg3 = img3;
            temObj.setImg3(img);
        }
        return new ResponseEntity<>(service.insertSparePart(temObj, temImg1, temImg2, temImg3), HttpStatus.CREATED);
    }

    @PutMapping("/spare")
    public ResponseEntity<?> updateSparePart(@RequestParam("id") String id,
                                             @RequestParam("email") String email,
                                             @RequestParam("contact") String contact,
                                             @RequestParam("seller") String seller,
                                             @RequestParam("condition") String condition,
                                             @RequestParam("type") String type,
                                             @RequestParam("location") String location,
                                             @RequestParam("date") String date,
                                             @RequestParam("title") String title,
                                             @RequestParam("price") String price,
                                             @RequestParam("category") String category,
                                             @RequestParam("additionalInfo") String additionalInfo,
                                             @RequestParam(value = "img1", required = false) MultipartFile img1,
                                             @RequestParam(value = "img2", required = false) MultipartFile img2,
                                             @RequestParam(value = "img3", required = false) MultipartFile img3)
    {
        MultipartFile temImg1 = null;
        MultipartFile temImg2 = null;
        MultipartFile temImg3 = null;
        SparePart temObj = new SparePart(id, email, contact, seller, location, "pending", "", date.toString(), title, price, condition, type, category, additionalInfo, null, null, null);
        if (img1 != null && !img1.isEmpty()) {
            String extension = FilenameUtils.getExtension(img1.getOriginalFilename());
            String img = id+"-1" + "." + extension;
            temImg1 = img1;
            temObj.setImg1(img);
        }
        if (img2 != null && !img2.isEmpty()) {
            String extension = FilenameUtils.getExtension(img2.getOriginalFilename());
            String img = id+"-2" + "." + extension;
            temImg2 = img2;
            temObj.setImg2(img);
        }
        if (img3 != null && !img3.isEmpty()) {
            String extension = FilenameUtils.getExtension(img3.getOriginalFilename());
            String img = id+"-3" + "." + extension;
            temImg3 = img3;
            temObj.setImg3(img);
        }
        return new ResponseEntity<>(service.updateSparePart(temObj, temImg1, temImg2, temImg3), HttpStatus.CREATED);
    }

    @GetMapping("/spare/report")
    public ResponseEntity<?> getReportData() {
        return new ResponseEntity<>(service.reportData(), HttpStatus.OK);
    }

    @PostMapping("/spare/filter")
    public ResponseEntity<?> getSparePartByExample(@RequestBody SparePart sparePart) {
        return new ResponseEntity<>(service.retrieveByExample(sparePart), HttpStatus.OK);
    }

    @GetMapping("/spare/id/{id}")
    public ResponseEntity<?> getSparePartsById(@PathVariable String id) {
        try {
            return new ResponseEntity<>(service.getSparePartById(id), HttpStatus.OK);
        } catch (AutoTraderException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/spare/email/{email}")
    public ResponseEntity<?> getSparePartByEmail(@PathVariable String email) {
        return new ResponseEntity<>(service.getSparePartsByEmail(email), HttpStatus.OK);
    }

    @GetMapping("/spare/pending")
    public ResponseEntity<?> getAllPendingSpareParts() {
        return new ResponseEntity<>(service.getAllPendingSpareParts(), HttpStatus.OK);
    }

    @DeleteMapping("/spare/{id}")
    public ResponseEntity<?> deleteSparePartById(@PathVariable String id) {
        return new ResponseEntity<>(service.deleteSparePartById(id), HttpStatus.OK);
    }

    @PostMapping("/spare/review")
    public ResponseEntity<?> reviewSparePart(@RequestParam("id") String id,
                                             @RequestParam("status") String status,
                                             @RequestParam("comment") String comment)
    {
        return new ResponseEntity<>(service.reviewSparePart(id, status, comment), HttpStatus.OK);
    }
}

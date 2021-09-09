package com.app.api.service;

import com.app.api.exception.AutoTraderException;
import com.app.api.model.SparePart;
import com.app.api.repository.SparePartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class SparePartService {
    private final SparePartRepository repository;
    private final FileService fileService;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public SparePartService(SparePartRepository repository, FileService fileService, MongoTemplate mongoTemplate) {
        this.repository = repository;
        this.fileService = fileService;
        this.mongoTemplate = mongoTemplate;
    }

    public SparePart insertSparePart(SparePart sparePart, MultipartFile img1, MultipartFile img2, MultipartFile img3) {
        if (img1 != null && !img1.isEmpty()) {
            fileService.uploadFile(img1, sparePart.getImg1(), "spare");
        }
        if (img2 != null && !img2.isEmpty()) {
            fileService.uploadFile(img2, sparePart.getImg2(), "spare");
        }
        if (img3 != null && !img3.isEmpty()) {
            fileService.uploadFile(img3, sparePart.getImg3(), "spare");
        }
        return repository.save(sparePart);
    }

    public SparePart updateSparePart(SparePart sparePart, MultipartFile img1, MultipartFile img2, MultipartFile img3) {
        SparePart tempObj = repository.findById(sparePart.getId()).get();
        if (tempObj.getImg1() != null)
            fileService.deleteFile(tempObj.getImg1(), "spare");
        if (tempObj.getImg2() != null)
            fileService.deleteFile(tempObj.getImg2(), "spare");
        if (tempObj.getImg3() != null)
            fileService.deleteFile(tempObj.getImg3(), "spare");

        if (img1 != null && !img1.isEmpty()) {
            fileService.uploadFile(img1, sparePart.getImg1(), "spare");
        }
        if (img2 != null && !img2.isEmpty()) {
            fileService.uploadFile(img2, sparePart.getImg2(), "spare");
        }
        if (img3 != null && !img3.isEmpty()) {
            fileService.uploadFile(img3, sparePart.getImg3(), "spare");
        }
        return repository.save(sparePart);
    }

    public List<SparePart> reportData() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "date"));
    }

    public List<SparePart> retrieveByExample(SparePart sparePart) {
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withMatcher("title", ExampleMatcher.GenericPropertyMatcher.of(ExampleMatcher.StringMatcher.CONTAINING));
        Example<SparePart> example = Example.of(sparePart, matcher);
        return repository.findAll(example);
    }

    public SparePart getSparePartById(String id) throws AutoTraderException {
        return repository.findById(id).orElseThrow(
                () -> new AutoTraderException("No such SparePart entry found")
        );
    }

    public List<SparePart> getSparePartsByEmail(String email) {
        return repository.findByEmail(email);
    }

    public List<SparePart> getAllPendingSpareParts() {
        return repository.findByStatus("pending");
    }

    public String deleteSparePartById(String id) {
        SparePart sparePart = repository.findById(id).get();
        String img1 = sparePart.getImg1();
        String img2 = sparePart.getImg2();
        String img3 = sparePart.getImg3();
        if (img1 != null) {
            fileService.deleteFile(img1, "spare");
        }
        if (img2 != null) {
            fileService.deleteFile(img2, "spare");
        }
        if (img3 != null) {
            fileService.deleteFile(img3, "spare");
        }
        repository.deleteById(id);
        return "Spare Part entry with id: " + id + " has been successfully deleted";
    }

    public String reviewSparePart(String id, String status, String comment) {
        SparePart sparePart = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(id)), SparePart.class);
        sparePart.setStatus(status);
        sparePart.setComment(comment);
        mongoTemplate.save(sparePart);
        return "reviewed";
    }
}

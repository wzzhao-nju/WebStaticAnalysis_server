package com.example.inter;

import com.example.entity.Record;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RecordRepository extends CrudRepository<Record, String> {
    List<Record> findByUid(Integer uid);
}

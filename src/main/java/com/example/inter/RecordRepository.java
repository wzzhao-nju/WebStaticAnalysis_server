package com.example.inter;

import com.example.entity.RecordCompositeKeys;
import org.springframework.data.repository.CrudRepository;

import com.example.entity.Record;

public interface RecordRepository extends CrudRepository<Record, RecordCompositeKeys>{
    
}

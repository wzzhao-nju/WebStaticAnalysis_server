package com.example.inter;

import com.example.entity.LoginInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LoginInfoRepository extends CrudRepository<LoginInfo, String> {
    List<LoginInfo> findByUid(Integer uid);
}

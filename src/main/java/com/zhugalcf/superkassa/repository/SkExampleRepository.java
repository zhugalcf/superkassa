package com.zhugalcf.superkassa.repository;

import com.zhugalcf.superkassa.entity.SkExample;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkExampleRepository extends CrudRepository<SkExample, Long> {
}

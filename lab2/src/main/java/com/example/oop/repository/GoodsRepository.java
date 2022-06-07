package com.example.oop.repository;

import com.example.oop.entity.Goods;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodsRepository extends JpaRepositoryImplementation<Goods, Long> {
    List<Goods> findByOrderById();
}

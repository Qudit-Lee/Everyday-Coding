package com.project.repository;

import org.springframework.data.jpa.repository.JpaRepository; 

import com.project.model.Specialized;

public interface SpecializedRepository extends JpaRepository<Specialized, Long>{
//"JpaRepository"라는 인터페스이스를 사용하여 엔티티클레스의 값을 DB에 저장
}


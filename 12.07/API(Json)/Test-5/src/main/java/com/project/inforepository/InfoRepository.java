package com.project.inforepository;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository;

import com.project.dto.Info;

public interface InfoRepository extends JpaRepository<Info, Long> {

}



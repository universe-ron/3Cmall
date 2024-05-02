package com.willy.malltest.repository;



import com.willy.malltest.model.ThirdParty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThirdPartyRepository extends JpaRepository<ThirdParty, String> {

}

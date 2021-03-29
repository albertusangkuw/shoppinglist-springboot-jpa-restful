/*
 * DaftarBelanjaRepo.java
 *
 * Created on Mar 22, 2021, 00.19
 */
package shoppinglist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import shoppinglist.entity.DaftarBelanja;
import shoppinglist.entity.DaftarBelanjaDetil;

import java.util.List;

/**
 * @author irfin
 */
public interface DaftarBelanjaRepo extends JpaRepository<DaftarBelanja, Long>
{
    List<DaftarBelanja> findByJudulContainingIgnoreCase(String judul);





}


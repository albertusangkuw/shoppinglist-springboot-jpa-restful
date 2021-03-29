/*
 * ShoppingListService.java
 *
 * Created on Mar 22, 2021, 01.20
 */
package shoppinglist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import shoppinglist.entity.DaftarBelanja;
import shoppinglist.entity.DaftarBelanjaDetil;
import shoppinglist.repository.DaftarBelanjaDetailRepo;
import shoppinglist.repository.DaftarBelanjaRepo;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author irfin
 */

@Service
public class ShoppingListService
{
    @Autowired
    private DaftarBelanjaRepo repo;
    @Autowired
    private DaftarBelanjaDetailRepo repoDetail;

    public Iterable<DaftarBelanja> getAllData()
    {
        return repo.findAll();
    }

    public Iterable<DaftarBelanja> getDatabyID(long id)
    {
        return repo.findAllById(Collections.singleton(id));
    }

    public Iterable<DaftarBelanja> getDatabyJudul(String judul)
    {
        return repo.findByJudulContainingIgnoreCase(judul);
    }
    public boolean create(DaftarBelanja entity, DaftarBelanjaDetil[] arrDetil)
    {
        try {
            // Pertama simpan dahulu objek DaftarBelanja tanpa mengandung detil apapun.
            repo.save(entity);

            // Setelah berhasil tersimpan, baca primary key auto-generate lalu set sebagai bagian dari
            // ID composite di DaftarBelanjaDetil.
            int noUrut = 1;
            for (DaftarBelanjaDetil detil : arrDetil) {
                detil.setId(entity.getId(), noUrut++);
                entity.addDaftarBarang(detil);
            }
            repo.save(entity);

            return true;
        }
        catch (Exception e) {
            e.printStackTrace(System.out);
            return false;
        }
    }

    public boolean update(DaftarBelanja entity, DaftarBelanjaDetil[] arrDetil)
    {
        try {

            DaftarBelanja dBelanja  = repo.getOne(entity.getId());
            dBelanja = entity;
            for (DaftarBelanjaDetil detil : dBelanja.getDaftarBarang()) {
                repoDetail.delete(detil);
            }
            repo.save(dBelanja);

            // ID composite di DaftarBelanjaDetil.
            int noUrut = 1;
            for (DaftarBelanjaDetil detil : arrDetil) {
                detil.setId(entity.getId(), noUrut++);
                dBelanja.addDaftarBarang(detil);
            }
            repo.save(dBelanja);
            return true;
        }
        catch (Exception e) {
            e.printStackTrace(System.out);
            return false;
        }
    }

    public boolean delete(long id){
        try{
            DaftarBelanja dBelanja  = repo.getOne(id);
            for (DaftarBelanjaDetil detil : dBelanja.getDaftarBarang()) {
                repoDetail.delete(detil);
            }
            repo.deleteById(id);
            return true;
        }catch (Exception e){
            e.printStackTrace(System.out);
            return  false;
        }

    }
}

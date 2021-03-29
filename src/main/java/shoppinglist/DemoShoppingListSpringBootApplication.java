package shoppinglist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import shoppinglist.entity.DaftarBelanja;
import shoppinglist.entity.DaftarBelanjaDetil;
import shoppinglist.http.ShoppingDataCreateDto;
import shoppinglist.http.ShoppingListCtrl;
import shoppinglist.repository.DaftarBelanjaRepo;
import shoppinglist.service.ShoppingListService;

import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@SpringBootApplication
public class DemoShoppingListSpringBootApplication implements CommandLineRunner
{
    @Autowired
    private DaftarBelanjaRepo repo;

    public static void main(String[] args)
    {
        SpringApplication.run(DemoShoppingListSpringBootApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception
    {
        System.out.println("Membaca Semua Record DaftarBelanja:");
        List<DaftarBelanja> all = repo.findAll();
        for (DaftarBelanja db : all) {
            System.out.println("[" + db.getId() + "] " + db.getJudul());

            List<DaftarBelanjaDetil> listBarang = db.getDaftarBarang();
            for (DaftarBelanjaDetil barang : listBarang) {
                System.out.println("\t" + barang.getNamaBarang() + " " + barang.getByk() + " " + barang.getSatuan());
            }
        }
        
        Scanner keyb = new Scanner(System.in);
        
        // 1
        System.out.print("Masukkan ID dari objek DaftarBelanja yg ingin ditampilkan: ");
        long id = Long.parseLong(keyb.nextLine());
        System.out.println("Hasil pencarian: ");
        
        Optional<DaftarBelanja> optDB = repo.findById(id);
        if (optDB.isPresent()) {
            DaftarBelanja db = optDB.get();
            System.out.println("\tJudul: " + db.getJudul());
        }
        else {
            System.out.println("\tTIDAK DITEMUKAN.");
        }
        // 2
        System.out.print("Masukkan judul dari objek DaftarBelanja yg ingin ditampilkan: ");
        String judul = keyb.nextLine();

        List<DaftarBelanja> daftarBelanja = repo.findByJudulContainingIgnoreCase(judul);
        for (DaftarBelanja detil : daftarBelanja) {
            System.out.println("ID : " + detil.getId());
            System.out.println("Judul List : " + detil.getJudul());
        }
        // 3
        System.out.print("Menyimpan objek ke database");
        DaftarBelanja entity = new DaftarBelanja();
        DaftarBelanjaDetil[] arrDetil = new DaftarBelanjaDetil[1];
        arrDetil[0] = new DaftarBelanjaDetil();
        arrDetil[0].setNamaBarang("Sabun");
        arrDetil[0].setByk(1);
        arrDetil[0].setSatuan("kg");
        arrDetil[0].setMemo("Untuk mandi");
        entity.setJudul("Belanja  Indomaret");
        entity.setTanggal(LocalDateTime.now());
        entity.setDaftarBarang(null);
        ShoppingListService shoppingListService = new ShoppingListService();
        shoppingListService.create(entity,arrDetil);

        //4
        /*
        System.out.print("Menyimpan objek ke database");
        arrDetil[0].setNamaBarang("Sabun di Update");
        arrDetil[0].setByk(2);
        arrDetil[0].setSatuan("kg");
        arrDetil[0].setMemo("");
        entity.setJudul("Belanja Update  Indomaret");
        entity.setTanggal(LocalDateTime.now());
        shoppingListService.update(entity,arrDetil);

        //5
        System.out.print("Masukkan ID dari objek DaftarBelanja yg ingin dihapuskan: ");
        long id2 = Long.parseLong(keyb.nextLine());
        if (shoppingListService.delete(id2)) {
            System.out.println("Berhasil dihapus");
        }else {
            System.out.println("Gagal dihapus");
        }
        */
    }
}

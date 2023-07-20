package tugas1_singidol_2006482773.singidol.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tugas1_singidol_2006482773.singidol.model.KonserModel;
import tugas1_singidol_2006482773.singidol.model.TiketModel;
import tugas1_singidol_2006482773.singidol.repository.KonserDb;
import tugas1_singidol_2006482773.singidol.repository.TiketDb;

@Service
@Transactional
public class TiketServiceImpl implements TiketService{
    @Autowired
    TiketDb tiketDb;

    @Autowired
    KonserDb konserDb;

    // Initiate the arrayList to store nomorTiket yang sudah digenerate
    List<String> listNomorTiket = new ArrayList<>();

    @Override
    public List<TiketModel> getListTiket() {
        return tiketDb.findAll();
    }

    @Override
    public void addTiket(TiketModel tiket) {
        // Set nomorTiket
        String generatedNomorTiket = generateNomorTiket(tiket);
        tiket.setNomorTiket(generatedNomorTiket);
        
        // Set tanggalPembelian
        tiket.setTanggalPembelian(LocalDateTime.now());

        // Set totalPendapatan
        Optional<KonserModel> konserOptional = konserDb.findById(tiket.getIdKonser().getId());
        KonserModel konser = konserOptional.get();

        konser.setTotalPendapatan(konser.getTotalPendapatan().add(tiket.getIdTipe().getHarga()));
        konserDb.save(konser);      // To update the database

        tiketDb.save(tiket);
        
    }
    
    @Override
    public TiketModel getTiketbyId(Long id) {
        Optional<TiketModel> tiket = tiketDb.findById(id);
        return tiket.get();
    }


    @Override
    public void deleteTiket(TiketModel tiket) {
        // totalPendapatan konser disesuaikan terlebih dahulu
        KonserModel konser = konserDb.findById(tiket.getIdKonser().getId()).get();

        konser.setTotalPendapatan(konser.getTotalPendapatan().subtract(tiket.getIdTipe().getHarga()));
        konserDb.save(konser);      // Update data konser

        listNomorTiket.remove(tiket.getNomorTiket());
        tiketDb.delete(tiket);
    }

    // Method to generate nomor tiket, biar rapih
    private String generateNomorTiket(TiketModel tiket) {
        String nomorTiket = "";

        // 3 huruf nama pembeli
        String namaDepan = tiket.getNamaLengkap().substring(0,3).toUpperCase();
        nomorTiket += namaDepan;

        // Tanggal lahir
        String tanggalLahirResult = String.format("%2s", tiket.getTanggalLahir().getDayOfMonth()).replace(' ', '0') + String.format("%2s", tiket.getTanggalLahir().getMonthValue()).replace(' ', '0');

        // Tanggal pembelian tiket
        LocalDateTime dateNow = LocalDateTime.now();
        int day = dateNow.getDayOfMonth();
        int month = dateNow.getMonthValue();
        String tanggalPembelianResult = String.format("%2s", day).replace(' ', '0') + String.format("%2s", month).replace(' ', '0');
        
        int hasil = Integer.parseInt(tanggalLahirResult) + Integer.parseInt(tanggalPembelianResult);
        
        
        nomorTiket += String.valueOf(hasil);
        nomorTiket += getCharIndex(tiket.getIdKonser().getNamaKonser().substring(0,1));
        nomorTiket += getTipeTiketShortened(tiket.getIdTipe().getNama());
        nomorTiket += getRandomAbjad();

        // Untuk memastikan nomorTiket yang ingin diterbitkan, adalah unik.
        if (listNomorTiket.size() != 0) {
            for (String n : listNomorTiket) {
                if ((n.equals(nomorTiket))) {
                    return generateNomorTiket(tiket);          // Kalo nomorTiket yang digenerate, tidak unik, redo the steps again.
                } 
            }
        }

        listNomorTiket.add(nomorTiket);
        return nomorTiket;
    }

    private String getCharIndex(String hurufPertama) {
        char[] map = new char[26];
        String huruf = "abcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i < huruf.length(); i++) {
            map[i] = huruf.charAt(i);
        }

        for (int i = 0; i < map.length; i++) {
            if(hurufPertama.equalsIgnoreCase(String.valueOf(map[i]))) {
                return String.format("%2s", String.valueOf(i+1)).replace(' ', '0');
            }
        }
        return "null";
    }

    private String getTipeTiketShortened (String tipe) {
        if(tipe.equals("vip")) {
            return "VIP";
        } else if (tipe.equals("platinum")) {
            return "PLT";
        }  else if (tipe.equals("gold")) {
            return "GLD";
        } else { // Silver
            return "SLV";
        }
    }

    private String getRandomAbjad() {

        Random random = new Random();

        char randomAbjad = (char) (random.nextInt(26) + 'a');

        // Because outputnya harus uppercase
        return String.valueOf(randomAbjad).toUpperCase();
    }

}

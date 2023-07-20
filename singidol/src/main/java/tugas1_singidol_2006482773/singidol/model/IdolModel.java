package tugas1_singidol_2006482773.singidol.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "idol")
public class IdolModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "nama_idol", nullable = false)
    private String namaIdol;

    @NotNull
    @Column(name = "jumlah_anggota", nullable = false)
    private Integer jumlahAnggota;

    @NotNull
    @Column(name = "tanggal_debut", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate tanggalDebut;

    @NotNull
    @Size(max = 255)
    @Column(name = "asal_negara", nullable = false)
    private String asalNegara;

    @OneToMany(mappedBy = "idIdol")
    List<PenampilanKonserModel> listPenampilan;
}


package tugas1_singidol_2006482773.singidol.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "konser")
public class KonserModel {
    // BigInt (SQL) => Long (Java)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // VarChar (SQL) => String (Java)
    @NotNull
    @Size(max = 255)
    @Column(name = "nama_konser", nullable = false)
    private String namaKonser;

    @NotNull
    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime waktu;

    @NotNull
    @Column(name = "total_pendapatan", nullable = false)
    private BigInteger totalPendapatan;

    @NotNull
    @Size(max = 255)
    @Column(name = "tempat", nullable = false)
    private String tempat;

    @OneToMany(mappedBy = "idKonser", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PenampilanKonserModel> listPenampilan;

    @OneToMany(mappedBy = "idKonser", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TiketModel> listTiket;

    // public List<KonserModel> getlistKonser() {
    //     return null;
    // }

}

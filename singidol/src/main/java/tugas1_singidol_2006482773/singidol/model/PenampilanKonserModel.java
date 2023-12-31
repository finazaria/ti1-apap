package tugas1_singidol_2006482773.singidol.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigInteger;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "penampilan_konser")
public class PenampilanKonserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_idol", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private IdolModel idIdol;


    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_konser", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private KonserModel idKonser;

    @NotNull
    @Column(name = "jam_mulai_tampil", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private java.time.LocalTime jamMulaiTampil;
}

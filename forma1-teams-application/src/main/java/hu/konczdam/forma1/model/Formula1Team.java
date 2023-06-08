package hu.konczdam.forma1.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.time.Year;
import java.util.Objects;

@Entity
@Table(name = "formula1_team")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class Formula1Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "smallint", nullable = false)
    private Year yearFounded;

    @Column(nullable = false)
    private int championshipsWon;

    @Column(nullable = false)
    private boolean entryFeePaid;

    public Formula1Team(String name, Year yearFounded, int championshipsWon, boolean entryFeePaid) {
        this.name = name;
        this.yearFounded = yearFounded;
        this.championshipsWon = championshipsWon;
        this.entryFeePaid = entryFeePaid;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Formula1Team that = (Formula1Team) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}


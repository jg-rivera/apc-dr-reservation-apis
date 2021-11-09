package apc.entjava.dr_reservation.ejb.entities;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "rooms")
public class RoomEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private Set<ReservationEntity> reservations;

    public RoomEntity() {

    }

    public RoomEntity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Set<ReservationEntity> getReservations() {
        return reservations;
    }

    public void setReservations(Set<ReservationEntity> reservations) {
        this.reservations = reservations;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object object) {
        return (object instanceof RoomEntity) && (id != null)
                ? id.equals(((RoomEntity) object).id)
                : (object == this);
    }

    @Override
    public int hashCode() {
        return (id != null)
                ? (RoomEntity.class.hashCode() + id.hashCode())
                : super.hashCode();
    }
}
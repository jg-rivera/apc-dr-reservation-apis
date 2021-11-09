package apc.entjava.dr_reservation.ejb.repositories;

import apc.entjava.dr_reservation.ejb.entities.ReservationEntity;
import apc.entjava.dr_reservation.ejb.entities.RoomEntity;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Singleton
@LocalBean
public class RoomRepository implements IRoomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<RoomEntity> getRooms() {
        return this.entityManager.createQuery("SELECT room FROM RoomEntity room", RoomEntity.class).getResultList();
    }

    @Override
    public RoomEntity findRoom(int id) {
        return this.entityManager.find(RoomEntity.class, id);
    }
}
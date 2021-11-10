package apc.entjava.dr_reservation.ejb.repositories;

import apc.entjava.dr_reservation.ejb.entities.ReservationEntity;

import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

@Singleton
@LocalBean
public class ReservationRepository implements IReservationRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ReservationEntity> getAllReservations() {
        return this.entityManager.createQuery("SELECT reservation FROM ReservationEntity reservation ORDER BY reservation.date, reservation.startTime, reservation.endTime ASC", ReservationEntity.class).getResultList();
    }

    @Override
    public List<ReservationEntity> getAllReservationsByDate(Date date) {
        String queryString = "SELECT reservation FROM ReservationEntity reservation WHERE reservation.date >= :date ORDER BY reservation.date, reservation.startTime, reservation.endTime ASC";
        TypedQuery<ReservationEntity> query = this.entityManager.createQuery(queryString, ReservationEntity.class);
        return query.setParameter("date", date).getResultList();
    }

    @Override
    public List<ReservationEntity> getAllReservationsByRoom(int roomId) {
        String queryString = "SELECT reservation FROM ReservationEntity reservation WHERE reservation.room.id = :roomId ORDER BY reservation.date, reservation.startTime, reservation.endTime ASC";
        TypedQuery<ReservationEntity> query = this.entityManager.createQuery(queryString, ReservationEntity.class);
        return query.setParameter("roomId", roomId).getResultList();
    }

    @Override
    public List<ReservationEntity> getAllReservationsByDateAndRoom(Date date, int roomId) {
        String queryString = "SELECT reservation FROM ReservationEntity reservation WHERE reservation.date >= :date AND reservation.room.id = :roomId ORDER BY reservation.date, reservation.startTime, reservation.endTime ASC";
        TypedQuery<ReservationEntity> query = this.entityManager.createQuery(queryString, ReservationEntity.class);
        query.setParameter("date", date);
        query.setParameter("roomId", roomId);
        return query.getResultList();
    }

    @Override
    public int addReservation(ReservationEntity item) {
        this.entityManager.persist(item);
        return item.getId();
    }

    @Override
    public void deleteReservation(ReservationEntity item) {
        this.entityManager.remove(this.entityManager.contains(item) ? item : this.entityManager.merge(item));
    }

    @Override
    public void updateReservation(ReservationEntity item) {
        Query query = this.entityManager.createQuery("UPDATE ReservationEntity reservation SET reservation.room=:room, reservation.studentName=:studentName, reservation.date=:date, reservation.endorser=:endorser WHERE reservation.id=:id");

        query.setParameter("id", item.getId());
        query.setParameter("room", item.getRoom());
        query.setParameter("studentName", item.getStudentName());
        query.setParameter("date", item.getDate());
        query.setParameter("endorser", item.getEndorser());
        query.executeUpdate();
    }

    @Override
    public ReservationEntity findReservation(int id) {
        return this.entityManager.find(ReservationEntity.class, id);
    }
}

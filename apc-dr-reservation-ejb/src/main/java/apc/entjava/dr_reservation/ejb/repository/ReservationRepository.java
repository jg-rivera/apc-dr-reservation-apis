package apc.entjava.dr_reservation.ejb.repository;

import apc.entjava.dr_reservation.ejb.entity.ReservationEntity;

import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 * Session Bean implementation class Catalog
 */
@Singleton
@LocalBean
public class ReservationRepository implements IReservationRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ReservationEntity> getItems() {
        return this.entityManager.createQuery("SELECT reservation FROM ReservationEntity reservation", ReservationEntity.class).getResultList();
    }

    @Override
    public void addReservation(ReservationEntity item) {
        this.entityManager.persist(item);
    }

    @Override
    public void deleteReservation(ReservationEntity item) {
        this.entityManager.remove(this.entityManager.contains(item) ? item : this.entityManager.merge(item));
    }

    @Override
    public void updateReservation(ReservationEntity item) {
        Query query = this.entityManager.createQuery("UPDATE ReservationEntity reservation SET reservation.name=:itemName, reservation.availableDate=:itemDate, reservation.description=:itemDesc where c.itemId=:id");

        query.setParameter("room", item.getRoom());
        query.setParameter("itemName", item.getName());
        query.setParameter("date", item.getDate());
        query.setParameter("endorser", item.getEndorser());
        query.executeUpdate();
    }

    @Override
    public ReservationEntity findReservation(int id) {
        return this.entityManager.find(ReservationEntity.class, id);
    }




    @Override
    public List<ReservationEntity> findMatchingReservationsByDate(Date date) {
        TypedQuery<ReservationEntity> query = this.entityManager.createQuery("SELECT reservation FROM ReservationEntity reservation WHERE reservation.date = :date", ReservationEntity.class);
        return query.setParameter("date", "%" + date + "%").getResultList();
    }
}

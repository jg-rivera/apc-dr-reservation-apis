package apc.entjava.dr_reservation.ejb.repository;

import apc.entjava.dr_reservation.ejb.entity.ReservationEntity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;

@Local
public interface IReservationRepository extends Serializable {

	List<ReservationEntity> getItems();

	void addReservation(ReservationEntity item);

	void deleteReservation(ReservationEntity item);

	void updateReservation(ReservationEntity item);

	ReservationEntity findReservation(int id);

	List<ReservationEntity> findMatchingReservationsByDate(Date date);
}

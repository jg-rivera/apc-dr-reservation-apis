package apc.entjava.dr_reservation.ejb.repositories;

import apc.entjava.dr_reservation.ejb.entities.ReservationEntity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;

@Local
public interface IReservationRepository extends Serializable {

	List<ReservationEntity> getAllReservations();

	List<ReservationEntity> getAllReservationsByDate(Date date);

	List<ReservationEntity> getAllReservationsByRoom(int roomId);

	List<ReservationEntity> getAllReservationsByDateAndRoom(Date date, int roomId);

	int addReservation(ReservationEntity item);

	void deleteReservation(ReservationEntity item);

	void updateReservation(ReservationEntity item);

	ReservationEntity findReservation(int id);
}

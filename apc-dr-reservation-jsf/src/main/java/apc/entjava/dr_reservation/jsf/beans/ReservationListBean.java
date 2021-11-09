package apc.entjava.dr_reservation.jsf.beans;

import apc.entjava.dr_reservation.ejb.entities.ReservationEntity;
import apc.entjava.dr_reservation.ejb.repositories.IReservationRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@RequestScoped
public class ReservationListBean implements Serializable {

    @Inject
    private IReservationRepository reservationRepositoryBean;

    private List<ReservationEntity> reservations;
}

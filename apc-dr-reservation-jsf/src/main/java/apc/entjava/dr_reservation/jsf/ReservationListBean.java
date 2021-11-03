package apc.entjava.dr_reservation.jsf;

import apc.entjava.dr_reservation.ejb.entity.ReservationEntity;
import apc.entjava.dr_reservation.ejb.repository.IReservationRepository;

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

package apc.entjava.dr_reservation.jsf.beans;

import java.io.Serializable;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import apc.entjava.dr_reservation.ejb.entities.ReservationEntity;
import apc.entjava.dr_reservation.ejb.repositories.IReservationRepository;

@ConversationScoped
@Named
public class ReservationDeleteBean implements Serializable {

	@Inject
	private ReservationFormBean reservationFormBean;

	@Inject
	private IReservationRepository reservationRepositoryBean;

	@Inject
	private Conversation conversation;

	private int reservationId;

	private ReservationEntity currentReservation;

	public void fetchReservation() {
		this.currentReservation = reservationRepositoryBean.findReservation(this.reservationId);
		conversation.begin();
	}

	public String removeReservation() {
		this.reservationRepositoryBean.deleteReservation(this.currentReservation);
		conversation.end();
		return "list?faces-redirect=true";
	}

	public String updateReservation(){
		this.reservationRepositoryBean.updateReservation(this.currentReservation);
		conversation.end();
		return "list?faces-redirect=true";
	}

	public int getReservationId() {
		return reservationId;
	}

	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}

	public ReservationEntity getReservation() {
		return currentReservation;
	}

	public void setReservation(ReservationEntity item) {
		this.currentReservation = item;
	}
}

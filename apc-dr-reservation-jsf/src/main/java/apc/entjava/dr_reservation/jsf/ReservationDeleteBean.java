package apc.entjava.dr_reservation.jsf;

import java.io.Serializable;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import apc.entjava.dr_reservation.ejb.entity.ReservationEntity;
import apc.entjava.dr_reservation.ejb.repository.IReservationRepository;

@ConversationScoped
@Named
public class ReservationDeleteBean implements Serializable {

	@Inject
	private ReservationFormBean reservationFormBean;

	@Inject
	private IReservationRepository reservationRepositoryBean;

	@Inject
	private Conversation conversation;

	private long itemId;

	private ReservationEntity currentReservation;

	public void fetchReservation() {
		this.currentReservation = reservationRepositoryBean.findReservation(this.itemId);
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

	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

	public ReservationEntity getReservation() {
		return currentReservation;
	}

	public void setReservation(ReservationEntity item) {
		this.currentReservation = item;
	}
}

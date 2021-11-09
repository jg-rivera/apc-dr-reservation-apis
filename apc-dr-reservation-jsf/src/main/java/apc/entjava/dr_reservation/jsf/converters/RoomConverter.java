package apc.entjava.dr_reservation.jsf.converters;

import apc.entjava.dr_reservation.ejb.entities.RoomEntity;
import apc.entjava.dr_reservation.ejb.repositories.IRoomRepository;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class RoomConverter implements Converter, Serializable {

    @Inject
    private IRoomRepository roomRepository;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String roomId) {
        RoomEntity room = roomRepository.findRoom(Integer.valueOf(roomId));
        return room;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object modelValue) {
        RoomEntity room = (RoomEntity) modelValue;
        return String.valueOf(room.getId());
    }
}
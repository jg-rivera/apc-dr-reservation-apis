package apc.entjava.dr_reservation.ejb.repositories;

import apc.entjava.dr_reservation.ejb.entities.RoomEntity;

import javax.ejb.Local;
import java.io.Serializable;
import java.util.List;

@Local
public interface IRoomRepository extends Serializable {

    List<RoomEntity> getRooms();

    RoomEntity findRoom(int id);
 }
